package org.enhydra.shark.test;

import java.util.HashMap;
import java.util.Map;

import org.enhydra.shark.Shark;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfmodel.WfProcess;
import org.enhydra.shark.api.client.wfmodel.WfResource;
import org.enhydra.shark.api.client.wfservice.ConnectFailed;
import org.enhydra.shark.api.client.wfservice.ExternalPackageInvalid;
import org.enhydra.shark.api.client.wfservice.NotConnected;
import org.enhydra.shark.api.client.wfservice.PackageAdministration;
import org.enhydra.shark.api.client.wfservice.PackageInvalid;
import org.enhydra.shark.api.client.wfservice.RepositoryMgr;
import org.enhydra.shark.api.client.wfservice.SharkConnection;
import org.enhydra.shark.api.client.wfservice.UserGroupAdministration;
import org.enhydra.shark.appmappersistence.DODSApplicationMappingTransaction;
import org.enhydra.shark.partmappersistence.DODSParticipantMappingTransaction;
import org.enhydra.shark.scriptmappersistence.DODSScriptMappingTransaction;
import org.enhydra.shark.transaction.SharkDODSTransaction;
import org.enhydra.shark.usertransaction.SharkDODSUserTransaction;

public class AutomaticTest extends Thread {
   private SharkConnection sc;
   private WfResource res;
   private int hm;
   private Map context;
   private String pkgId, pDefId;

   public AutomaticTest (String pkgId,String pDefId,int hm,Map context) throws BaseException, ConnectFailed, NotConnected {
      sc=Shark.getInstance().getSharkConnection();
      this.sc=sc;
      this.hm=hm;
      this.context=new HashMap(context);
      this.pkgId=pkgId;
      this.pDefId=pDefId;

      sc.connect(username,pwd,"","");
      res=sc.getResourceObject();

      synchronized(pwd) {
         ++counter;
      }
      start();
   }

   public void run () {
      for (int i=0; i<hm;i++) {
         try {
            WfProcess proc=sc.createProcess(pkgId,pDefId);
            if (context.size()>0) {
               proc.set_process_context(context);
            }
            proc.start();
            System.out.println("THE "+(i+1)+". PROCESS for Thread"+this+" has finished, active threads="+Thread.activeCount());
         } catch (Exception ex) {
            new RootException("EXITING THREAD "+Thread.currentThread()+" BECAUSE OF ERROR!!!",ex).printStackTrace();
            break;
         }
      }

      try {
         Thread.sleep(1500);
      } catch (Exception ex) {}
      System.out.println("There are "+Thread.activeCount()+","+counter+" active threads");
      synchronized(pwd) {
         --counter;
         if (0 == counter) {
            //            Shark.shutdown();
            SharkDODSTransaction.info();
            DODSParticipantMappingTransaction.info();
            DODSApplicationMappingTransaction.info();
            DODSScriptMappingTransaction.info();
            SharkDODSUserTransaction.info();
            System.exit(0);
         }
      }
   }

   static String username="admin";
   static String pwd="enhydra";
   static int counter = 0;

   public static void main (String[] args) throws Exception {
      if (args==null || args.length<3) {
         printUsage();
         return;
      }
      String confFilePath=args[0];
      String xpdlName=args[1];
      String pDefId=args[2];
      int hmThreads=1;
      if (args.length>3) {
         hmThreads=Integer.parseInt(args[3]);
      }
      int hm=1;
      if (args.length>4) {
         hm=Integer.parseInt(args[4]);
      }
      Map cntxt=new HashMap();
      if (args.length>5) {
         for (int i=5; i<args.length; i++) {
            String cnt=args[i];
            int ind=cnt.indexOf("=");
            String id=cnt.substring(0,ind);
            String val=cnt.substring(ind+1,cnt.length());
            if (val!=null && val.equals("true") || val.equals("false")) {
               Boolean bool=new Boolean(val);
               cntxt.put(id,bool);
               continue;
            }
            try {
               Double d=Double.valueOf(val);
               if (val.indexOf(".")==-1) {
                  Long l=new Long(d.longValue());
                  cntxt.put(id,l);
               } else {
                  cntxt.put(id,d);
               }
            } catch (Exception ex) {
               cntxt.put(id,val);
            }

         }
      }

      try {
         Shark.configure(confFilePath);
      } catch (Exception ex) {
         ex.printStackTrace();
         printUsage();
         return;
      }
      Shark ss=Shark.getInstance();
      PackageAdministration pa=ss.getAdminInterface().getPackageAdministration();
      RepositoryMgr rm=ss.getRepositoryManager();
      UserGroupAdministration uga=ss.getAdminInterface().getUserGroupAdministration();
      String pkgId=null;
      try {
         if (!uga.doesGroupExist("test")) {
            uga.createGroup("test","test group");
         }
         if (!uga.doesUserExist(username)) {
            uga.createUser("test", username, pwd,"Jane", "Doe","");
         }
         System.out.println("All GRPS:");
         String[] gns=uga.getAllGroupnames();
         for (int i=0; i<gns.length; i++) {
            System.out.println("Group - "+gns[i]);
         }
         System.out.println("All USERS:");
         String[] uns=uga.getAllUsers();
         for (int i=0; i<uns.length; i++) {
            System.out.println("User - "+uns[i]);
         }
      } catch (Throwable t) {}

      try {
         pkgId=rm.getPackageId(xpdlName);
         if (!pa.isPackageOpened(pkgId)) {
            pa.openPackage(xpdlName);
         }

         for (int i=0;i<hmThreads; i++) {
            new AutomaticTest(pkgId,pDefId,hm,cntxt);
         }
      } catch (Exception ex) {
         if (ex instanceof PackageInvalid) {
            System.out.println("PIERRS="+((PackageInvalid)ex).getXPDLValidationErrors());
         }
         if (ex instanceof ExternalPackageInvalid) {
            System.out.println("PIERRS="+((ExternalPackageInvalid)ex).getXPDLValidationErrors());
         }
         ex.printStackTrace();
         printUsage();
         System.exit(1);
      }
   }

   static void printUsage () {
      System.err.println("This application is used to start and execute shark's processes, that contain only automatic activities, until all of them are finished.");
      System.err.println();
      System.err.println("usage: java ManualTest configFilePath xpdlName pDefId [noOfThreads [noOfProcToStart [var1=val1 [var2=val2 ... ]]]]");
      System.err.println();
      System.err.println("arguments:");
      System.err.println("  configFilePath   the path to Shark's configuration file.");
      System.err.println("  xpdlName         the path to XPDL file where process definition for the process one want to start can be found.");
      System.err.println("                   This path has to be relative to shark's external repository folder.");
      System.err.println("  pDefId           the id of xpdl process definition.");
      System.err.println("  noOfThreads      the number of threads that will instantiate and execute processes (default is 1).");
      System.err.println("  noOfProcToStart  the number of processes that will be started by each thread (default is 1).");
      System.err.println("  vari=vali        the process variable id and its value.");
      System.err.println();
      System.err.println("NOTE: variable value will be interpreted as:");
      System.err.println("       Boolean - if you enter 'true' or 'false',");
      System.err.println("       Long    - if you enter only digits,");
      System.err.println("       Double  - if you enter only digits and full-stop,");
      System.err.println("       String  - otherwise.");
   }

}



