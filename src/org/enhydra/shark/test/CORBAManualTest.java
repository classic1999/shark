package org.enhydra.shark.test;

import org.omg.WfBase.*;
import org.enhydra.shark.corba.WorkflowService.*;
import org.omg.WorkflowModel.*;

import org.omg.CORBA.*;      // All CORBA applications need these classes.
import org.omg.CosNaming.*;  // Client will use the naming service.

import java.util.*;
import java.io.*;

public class CORBAManualTest extends Thread {
   static final String HOST_PROPERTY_NAME="host";
   static final String PORT_PROPERTY_NAME="port";
   static final String ENGINENAME_PROPERTY_NAME="enginename";
   static final String USERNAME_PROPERTY_NAME="username";
   static final String PASSWORD_PROPERTY_NAME="password";

   static ORB orb;
   static String host;
   static String port;
   static String enginename;
   static String username;
   static String password;

   static int counter = 0;

   static String pkgId;
   static String pDefId;

   private static long startT;
   private static long endT;

   private static SharkInterface shark;
   static Properties props=new Properties();

   private SharkConnection sc;
   private WfResource res;
   private int actCompleted;

   public CORBAManualTest() throws BaseException, ConnectFailed, NotConnected {
      sc=shark.getSharkConnection();
      sc.connect(username,password,"","");
      res=sc.getResourceObject();

      this.actCompleted = 0;
      synchronized(password) {
         ++counter;
      }
      start();
   }

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
               try {
                  if (!ass[i].get_accepted_status()) {
                     System.err.println("Thread "+this+" - Accepting ass ["+ass[i].activity().key()+","+ass[i].assignee().resource_key()+"], active threads="+Thread.activeCount());
                     ass[i].set_accepted_status(true);
                  }
                  System.err.println("Thread "+this+" - Completing ass ["+ass[i].activity().key()+","+ass[i].assignee().resource_key()+"], active threads="+Thread.activeCount());
                  WfActivity act=ass[i].activity();
                  act.complete();
                  ++actCompleted;
                  sc.doneWith(act);
                  sc.doneWith(ass[i]);
                  //System.err.println("Thread "+this+" - Ass ["+ass[i].activity().key()+","+ass[i].assignee().resource_key()+"] completed, active threads="+Thread.activeCount());
               } catch (Exception ex) {
                  ex.printStackTrace();
                  System.err.println("Thread "+this+" - Problems while executing ass "+ass[i]+", active threads="+Thread.activeCount()+" errMsg="+ex.getMessage());
               }
            }
         } else {
            break;
         }
      }
      sc.doneWith(res);
      try {
         sc.disconnect();
         Thread.sleep(1500);
      } catch (Exception ex) {
      }
      System.err.print("There are "
                          + Thread.activeCount()
                          +" active threads; this one completed "
                          + actCompleted
                          +" activities ");
      synchronized(password) {
         --counter;
         if (0 == counter) {
            endT=System.currentTimeMillis();
            try {
               ExecutionAdministration ea=shark.getExecutionAdministration();
               ea.connect(username,password,"","");
               WfProcessMgr mgr=ea.getProcessMgrByXPDLDefinition(pkgId,pDefId);
               WfProcess[] procs=mgr.get_sequence_process(0);
               for (int i=0; i<procs.length; i++) {
                  System.out.println("Process "+procs[i].key()+" state is "+procs[i].state());
                  ea.doneWith(procs[i]);
               }
               ea.doneWith(mgr);
               ea.disconnect();
            } catch (Exception ex){}
            System.out.println("The exec. lasted "+(endT-startT)+" ms");
            System.exit(0);
         }
      }
   }

   public static void main (String[] args) throws Exception {
      if (args==null || args.length<3) {
         printUsage();
         return;
      }

      try {
         configure(args[0]);
      } catch (Exception ex) {
         ex.printStackTrace();
         printUsage();
      }
      host=props.getProperty(CORBAProcStartClient.HOST_PROPERTY_NAME);
      port=props.getProperty(CORBAProcStartClient.PORT_PROPERTY_NAME);
      enginename=props.getProperty(CORBAProcStartClient.ENGINENAME_PROPERTY_NAME);
      username=props.getProperty(CORBAProcStartClient.USERNAME_PROPERTY_NAME);
      password=props.getProperty(CORBAProcStartClient.PASSWORD_PROPERTY_NAME);

      if (host==null || port==null || enginename==null || username==null || password==null) {
         printUsage();
      }
      if (host==null) {
         throw new Exception("host - property is not specified in configuration file");
      }
      if (port==null) {
         throw new Exception("port - property is not specified in configuration file");
      }
      if (enginename==null) {
         throw new Exception("enginename - property is not specified in configuration file");
      }
      if (username==null) {
         throw new Exception("username - property is not specified in configuration file");
      }
      if (password==null) {
         throw new Exception("password - property is not specified in configuration file");
      }


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
      Map cntx=new HashMap();
      if (args.length>5) {
         for (int i=5; i<args.length; i++) {
            String cnt=args[i];
            int ind=cnt.indexOf("=");
            String id=cnt.substring(0,ind);
            String val=cnt.substring(ind+1,cnt.length());
            try {
               Long l=Long.valueOf(val);
               cntx.put(id,l);
            } catch (Exception ex) {
               cntx.put(id,val);
            }
         }
      }

      try {
         shark=findWorkflowServer(host,port,enginename);
      } catch (Exception ex) {
         ex.printStackTrace();
         printUsage();
      }

      NameValue[] cntxt=new NameValue[cntx.size()];
      Iterator it=cntx.entrySet().iterator();
      int j=0;
      while(it.hasNext()) {
         Map.Entry me=(Map.Entry)it.next();
         String id=(String)me.getKey();
         java.lang.Object val=me.getValue();
         Any any=orb.create_any();
         if (val instanceof Long) {
            any.insert_longlong(((Long)val).longValue());
         } else {
            any.insert_wstring((String)val);
         }
         cntxt[j]=new NameValue();
         cntxt[j].the_name=id;
         cntxt[j].the_value=any;
         j++;
      }

      PackageAdministration pa=shark.getPackageAdministration();
      RepositoryMgr rm=shark.getRepositoryManager();
      String pkgId=null;

      try {
         pa.connect(username,password,"","");
         rm.connect(username,password,"","");
         pkgId=rm.getPackageId(xpdlName);
         if (!pa.isPackageOpened(pkgId)) {
            pa.openPkg(xpdlName);
         }
         rm.disconnect();
         pa.disconnect();
         SharkConnection sc=shark.getSharkConnection();
         sc.connect(username,password,"","");

         startT=System.currentTimeMillis();

         for (int i = 0; i < hm; ++i) {
            WfProcess proc=sc.createProcess(pkgId,pDefId);
            proc.set_process_context(cntxt);
            proc.start();
            sc.doneWith(proc);
         }
         sc.disconnect();
         CORBAManualTest.pkgId=pkgId;
         CORBAManualTest.pDefId=pDefId;

         for (int i=0;i<hmThreads; i++) {
            new CORBAManualTest();
         }
      } catch (Throwable ex) {
         if (ex instanceof PackageInvalid) {
            System.out.println("PIERRS="+((PackageInvalid)ex).XPDLValidationErrors);
         }
         if (ex instanceof ExternalPackageInvalid) {
            System.out.println("PIERRS="+((ExternalPackageInvalid)ex).XPDLValidationErrors);
         }
         ex.printStackTrace();
         printUsage();
      }
   }

   static void configure (String filePath) throws Exception {
      if (filePath==null) {
         throw new Exception("Client need to be configured properly - given path to configuration file is null!!!");
      }
      File configFile=new File(filePath);
      if (!configFile.isAbsolute()) {
         configFile=configFile.getAbsoluteFile();
      }
      if (configFile.exists()) {
         FileInputStream fis=null;
         fis=new FileInputStream(configFile);
         props.load(fis);
         fis.close();
      } else {
         throw new Exception("Client need to be configured properly - configuration file "+configFile+" does not exist!!!");
      }
   }

   public static SharkInterface findWorkflowServer (String host,
                                                    String port,
                                                    String workflowServerName) throws Exception {
      String[] args={"-ORBInitialHost",host,"-ORBInitialPort",port};
      // Create and initialize the ORB
      orb = ORB.init(args,null);
      // Get the root naming context
      org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
      NamingContext ncRef = NamingContextHelper.narrow(objRef);

      // Resolve the object reference in naming
      NameComponent nc = new NameComponent(workflowServerName,"");
      NameComponent path[] = {nc};
      return SharkInterfaceHelper.narrow(ncRef.resolve(path));
   }

   static void printUsage () {
      System.err.println("This application is used to start and execute shark's processes that contain at least one manual activity through the CORBA, until all of them are finished.");
      System.err.println("It is supposed that CORBA server is up.");
      System.err.println();
      System.err.println("usage: java CORBAManualTest configFilePath xpdlName pDefId [noOfThreads [noOfProcToStart [var1=val1 [var2=val2 ... ]]]]");
      System.err.println();
      System.err.println("arguments:");
      System.err.println("  configFilePath   the path to the configuration file.");
      System.err.println("  xpdlName         the path to XPDL file where process definition for the process one want to start can be found.");
      System.err.println("                   This path has to be relative to shark's external repository folder.");
      System.err.println("  pDefId           the id of xpdl process definition.");
      System.err.println("  noOfThreads      the number of threads that will fight to execute activities (default is 1).");
      System.err.println("  noOfProcToStart  the number of processes that will be started (default is 1).");
      System.err.println("  vari=vali        the process variable id and its value.");
      System.err.println();
      System.err.println("NOTE: the configuration file should contain name-value pairs, and following should be specified:");
      System.err.println("       - host        the name of CORBA name server host.");
      System.err.println("       - port        the port number for the CORBA name server.");
      System.err.println("       - enginename  the name of shark server instance (the one registerd with nameserver).");
      System.err.println("       - username    username credential to connect to shark server.");
      System.err.println("       - password    password credential to connect to shark server.");
      System.err.println();
      System.err.println("NOTE: variable value will be interpreted as:");
      System.err.println("       Boolean - if you enter 'true' or 'false',");
      System.err.println("       Long    - if you enter only digits,");
      System.err.println("       Double  - if you enter only digits and full-stop,");
      System.err.println("       String  - otherwise.");
   }

}

