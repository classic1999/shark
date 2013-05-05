package org.enhydra.shark.test;

import java.util.*;

import org.enhydra.shark.Shark;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfmodel.*;
import org.enhydra.shark.api.client.wfservice.*;
import org.enhydra.shark.api.common.SharkConstants;
import org.enhydra.shark.transaction.SharkDODSTransaction;

public class ManualTest extends Thread {
   //static long midtime;

   private static long startT;
   private static long endT;
   private static AdminMisc am;
   private WfResource res;
   private int actCompleted;

   public ManualTest() throws BaseException, ConnectFailed, NotConnected {
      SharkConnection sc=Shark.getInstance().getSharkConnection();
      sc.connect(username,pwd,"","");
      res=sc.getResourceObject();

      this.actCompleted = 0;
      synchronized(pwd) {
         ++counter;
      }
      start();
   }
   private String m1 = "Assignment is not valid anymore";
   private String m2 = "activity state is closed";
   private String m3 = "Can't change to state ";
   public void run () {
      for (WfAssignment[] ass=null;true;) {
         try {
            ass=res.get_sequence_work_item(0);
         } catch(BaseException e) {
            e.printStackTrace();
            continue;
         }
         if (ass!=null && ass.length>0) {
            for (int i=0; i<ass.length; i++) {
               String asss=ass[i].toString();
               try {
                  // koop
                  WfProcess _prc_ = ass[i].activity().container();
                  WfActivity [] acts =
                     _prc_.get_activities_in_state(SharkConstants.STATE_OPEN_RUNNING)
                     .get_next_n_sequence(0);
                  if (acts.length < 1) {
                     System.err.println(":::NONE");
                  } else {
                     for (int k = 0; k < acts.length; k++) {
                        WfActivity _aha_ = acts[k];
                        System.err.println
                           (":::"+ am.getActivityResourceUsername
                               (_prc_.key(), _aha_.key()));
                     }
                  }
                  if (!ass[i].get_accepted_status()) {
                     System.err.println("Thread "+this+" - Accepting ass "+asss+", active threads="+Thread.activeCount());
                     ass[i].set_accepted_status(true);
                  }
                  // koop
                  System.err.println("Thread "+this+" - Completing ass "+asss+", active threads="+Thread.activeCount());
                  ass[i].activity().complete();
                  ++actCompleted;
                  System.err.println("Thread "+this+" - Ass "+asss+" completed, active threads="+Thread.activeCount());
               } catch (Exception ex) {
                  if ((ex instanceof RootException)
                      && (m1.equals(ex.getMessage())
                             || m2.equals(ex.getMessage())
                             || (ex.getMessage()!=null && ex.getMessage().startsWith(m3)))) {
                     System.err.println("failed: yet ignoring "
                                           + ex.getMessage());
                     continue;
                  }
                  ex.printStackTrace();
                  System.err.println("Thread "+this+" - Problems while executing ass "+asss+", active threads="+Thread.activeCount()+" errMsg="+ex.getMessage());
               }
            }
         } else {
            break;
         }
      }
      //try {Thread.sleep(1500);} catch (Exception ex) {}
      System.err.print("There are "
                          + Thread.activeCount()
                          +" active threads; this one completed "
                          + actCompleted
                          +" activities ");
      synchronized(pwd) {
         --counter;
         if (0 == counter) {
            endT=System.currentTimeMillis();
            //            Shark.shutdown();
            SharkDODSTransaction.info();
            //DODSParticipantMappingTransaction.info();
            //DODSApplicationMappingTransaction.info();
            //DODSScriptMappingTransaction.info();
            //SharkDODSUserTransaction.info();
            try {
               ExecutionAdministration ea=Shark.getInstance().getAdminInterface().getExecutionAdministration();
               ea.connect(username,pwd,"","");
               WfProcessMgr mgr=ea.getProcessMgr(pkgId,pDefId);
               WfProcess[] procs=mgr.get_sequence_process(0);
               for (int i=0; i<procs.length; i++) {
                  System.out.println("Process "+procs[i]+" state is "+procs[i].state()+", last state time="+new Date(procs[i].last_state_time().getTime()));
               }
               //org.enhydra.dods.DODS.shutdown();
            } catch (Exception ex){}
            System.out.println("The exec. lasted "+(endT-startT)+" ms");//, mid time="+(midtime-startT)+" ms");
            System.exit(0);
         }
      }
   }

   static String username="admin";
   static String pwd="enhydra";

   static int counter = 0;

   static String pkgId;
   static String pDefId;
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
      String pkgId=null;
      am=ss.getAdminInterface().getAdminMisc();
      UserGroupAdministration uga=ss.getAdminInterface().getUserGroupAdministration();
      try {
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

         if (!uga.doesGroupExist("test")) {
            uga.createGroup("test","test group");
         }
         if (!uga.doesUserExist(username)) {
            uga.createUser("test", username, pwd,"Jane", "Doe","");
         }

      } catch (Throwable t) {t.printStackTrace();}

      try {
         pkgId=rm.getPackageId(xpdlName);
         if (!pa.isPackageOpened(pkgId)) {
            pa.openPackage(xpdlName);
         }
         SharkConnection sc=ss.getSharkConnection();
         sc.connect(username,pwd,"","");

         startT=System.currentTimeMillis();
         for (int i = 0; i < hm; ++i) {
            WfProcess proc=sc.createProcess(pkgId,pDefId);
            proc.set_process_context(cntxt);
            proc.start();
         }

         //midtime=System.currentTimeMillis();
         //System.out.println("Time="+(midtime-startT)+" ms");
         //if(true) System.exit(0);
         ManualTest.pkgId=pkgId;
         ManualTest.pDefId=pDefId;

         for (int i=0;i<hmThreads; i++) {
            new ManualTest();
         }
      } catch (Throwable ex) {
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
      System.err.println("This application is used to start and execute shark's processes, that contain at least one manual activity, until all of them are finished.");
      System.err.println();
      System.err.println("usage: java ManualTest configFilePath xpdlName pDefId [noOfThreads [noOfProcToStart [var1=val1 [var2=val2 ... ]]]]");
      System.err.println();
      System.err.println("arguments:");
      System.err.println("  configFilePath   the path to Shark's configuration file.");
      System.err.println("  xpdlName         the path to XPDL file where process definition for the process one want to start can be found.");
      System.err.println("                   This path has to be relative to shark's external repository folder.");
      System.err.println("  pDefId           the id of xpdl process definition.");
      System.err.println("  noOfThreads      the number of threads that will fight to execute activities (default is 1).");
      System.err.println("  noOfProcToStart  the number of processes that will be started (default is 1).");
      System.err.println("  vari=vali        the process variable id and its value.");
      System.err.println();
      System.err.println("NOTE: variable value will be interpreted as:");
      System.err.println("       Boolean - if you enter 'true' or 'false',");
      System.err.println("       Long    - if you enter only digits,");
      System.err.println("       Double  - if you enter only digits and full-stop,");
      System.err.println("       String  - otherwise.");
   }

}

