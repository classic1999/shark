package org.enhydra.shark.test;

import org.omg.WfBase.NameValue;
import org.omg.WfBase.NameValueInfo;
import org.omg.WorkflowModel.WfProcess;
import org.omg.WorkflowModel.WfProcessMgr;
import org.enhydra.shark.corba.WorkflowService.ConnectFailed;
import org.enhydra.shark.corba.WorkflowService.ExecutionAdministration;
import org.enhydra.shark.corba.WorkflowService.SharkConnection;
import org.enhydra.shark.corba.WorkflowService.SharkInterface;
import org.enhydra.shark.corba.WorkflowService.SharkInterfaceHelper;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;

public class CORBAProcStartClient {

   static final String HOST_PROPERTY_NAME="host";
   static final String PORT_PROPERTY_NAME="port";
   static final String ENGINENAME_PROPERTY_NAME="enginename";
   static final String USERNAME_PROPERTY_NAME="username";
   static final String PASSWORD_PROPERTY_NAME="password";

   static String lastStatusString="";

   private String confFilePath;
   private ORB orb;
   private SharkInterface shark;
   private SharkConnection sc;
   private ExecutionAdministration ea;
   private String statusString;

   private Properties props=new Properties();

   private String host;
   private String port;
   private String enginename;
   private String username;
   private String password;

   protected CORBAProcStartClient (String confFilePath) {
      this.confFilePath=confFilePath;
   }

   protected boolean init () {
      statusString="";
      try {
         CORBAProcStartClient.configure(props,confFilePath);

         host=props.getProperty(CORBAProcStartClient.HOST_PROPERTY_NAME);
         port=props.getProperty(CORBAProcStartClient.PORT_PROPERTY_NAME);
         enginename=props.getProperty(CORBAProcStartClient.ENGINENAME_PROPERTY_NAME);
         username=props.getProperty(CORBAProcStartClient.USERNAME_PROPERTY_NAME);
         password=props.getProperty(CORBAProcStartClient.PASSWORD_PROPERTY_NAME);

         String host=props.getProperty(CORBAProcStartClient.HOST_PROPERTY_NAME);
         String port=props.getProperty(CORBAProcStartClient.PORT_PROPERTY_NAME);
         String enginename=props.getProperty(CORBAProcStartClient.ENGINENAME_PROPERTY_NAME);
         String username=props.getProperty(CORBAProcStartClient.USERNAME_PROPERTY_NAME);
         String password=props.getProperty(CORBAProcStartClient.PASSWORD_PROPERTY_NAME);

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

         String[] args={"-ORBInitialHost",host,"-ORBInitialPort",port};
         // Create and initialize the ORB
         orb = ORB.init(args,null);
         // Get the root naming context
         org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
         NamingContext ncRef = NamingContextHelper.narrow(objRef);

         // Resolve the object reference in naming
         NameComponent nc = new NameComponent(enginename,"");
         NameComponent path[] = {nc};
         shark=SharkInterfaceHelper.narrow(ncRef.resolve(path));

         sc=shark.getSharkConnection();
         ea=shark.getExecutionAdministration();
         sc.connect(username,password,"","");
         ea.connect(username,password,"","");
         return true;
      } catch (Throwable thr) {
         if (host==null || port==null || enginename==null || username==null || password==null) {
            statusString=thr.getMessage();
         } else if (shark==null) {
            statusString="Connection to shark server is not established - maybe the server is down, or the connection parameters are wrong!";
         } else if (thr instanceof ConnectFailed) {
            statusString="Connection to shark server is not established - wrong username or password!";
         } else {
            statusString=thr.getMessage();
         }
         return false;
      }
   }

   protected boolean startProcess (String pkgId,String pDefId,Map vars) {
      statusString="";
      WfProcessMgr mgr=null;
      boolean mgrOK=false;

      try {
         mgr=ea.getProcessMgrByXPDLDefinition(pkgId,pDefId);

         WfProcess proc=sc.createProcess(pkgId,pDefId);

         NameValueInfo[] cs=mgr.context_signature();
         mgrOK=true;
         // converting process context to CORBA values
         if (vars!=null && vars.size()>0) {
            NameValue[] cntxt=new NameValue[vars.size()];
            Iterator it=vars.entrySet().iterator();
            int i=0;
            while(it.hasNext()) {
               Map.Entry me=(Map.Entry)it.next();
               String id=(String)me.getKey();
               java.lang.Object val=me.getValue();
               Any any=orb.create_any();
               String clsName=getClassName(cs,id);
               if (clsName==null) {
                  throw new Exception ("The process definition knows nothing about variable "+id+" - you can't put it into process context");
               }
               if (!clsName.equals(val.getClass().getName())) {
                  throw new Exception ("You are trying to set wrong type "+val.getClass().getName()+" for variable "+id+" which type is "+clsName);
               }

               if (val instanceof Long) {
                  any.insert_longlong(((Long)val).longValue());
               } else if (val instanceof Double) {
                  any.insert_double(((Double)val).doubleValue());
               } else if (val instanceof Boolean) {
                  any.insert_boolean(((Boolean)val).booleanValue());
               } else if (val instanceof String) {
                  any.insert_wstring((String)val);
               } else {
                  throw new Exception ("Unsupported type "+val.getClass().getName()+" for variable "+id);
               }
               cntxt[i]=new NameValue();
               cntxt[i].the_name=id;
               cntxt[i].the_value=any;
               i++;
            }
            proc.set_process_context(cntxt);
         }
         proc.start();
         return true;
      } catch (Throwable thr) {
         if (!mgrOK) {
            lastStatusString="Improper package or process definition Id!";
         } else {
            statusString=thr.getMessage();
            if (statusString==null || statusString.trim().equals("")) {
               //statusString=thr.printStackTrace(
               OutputStream os=new ByteArrayOutputStream();
               PrintStream ps=new PrintStream(os);
               thr.printStackTrace(ps);
               ps.close();
               statusString=os.toString();
            }
         }
      }
      return false;
   }

   public String getStatusString () {
      return statusString;
   }

   public static boolean run (String[] args) {
      if (args==null || args.length<3) {
         lastStatusString="Invalid number of parameters - must be at least 3 parameters!";
         return false;
      }

      String pkgId=args[1];
      String pDefId=args[2];

      WfProcessMgr mgr=null;
      boolean mgrOK=false;
      try {
         CORBAProcStartClient cpsc=new CORBAProcStartClient(args[0]);
         boolean isOK=cpsc.init();
         if (!isOK) {
            lastStatusString=cpsc.getStatusString();
            return false;
         }
         mgr=cpsc.ea.getProcessMgrByXPDLDefinition(pkgId,pDefId);

         NameValueInfo[] cs=mgr.context_signature();
         mgrOK=true;
         // getting initial process context
         Map cntx=new HashMap();
         if (args.length>3) {
            for (int i=3; i<args.length; i++) {
               String cnt=args[i];
               int ind=cnt.indexOf("=");
               String id=cnt.substring(0,ind);
               String val=cnt.substring(ind+1,cnt.length());
               if (getClassName(cs,id).equals("java.lang.Boolean")) {
                  if (!val.equalsIgnoreCase("true") && !val.equalsIgnoreCase("false")) {
                     throw new Exception("The value for Boolean variable "+id+" is not set properly - it must be true or false");
                  } else {
                     cntx.put(id,new Boolean(val));
                  }
               } else if (getClassName(cs,id).equals("java.lang.Long")) {
                  cntx.put(id,new Long(val));
               } else if (getClassName(cs,id).equals("java.lang.Double")) {
                  cntx.put(id,new Double(val));
               } else if (getClassName(cs,id).equals("java.lang.String")) {
                  cntx.put(id,val);
               } else {
                  throw new Exception ("Can't init variable which type is "+getClassName(cs,id).equals("java.lang.String"));
               }
            }
         }
         isOK=cpsc.startProcess(pkgId,pDefId,cntx);
         if (!isOK) {
            lastStatusString=cpsc.getStatusString();
            return false;
         }
         return true;
      } catch (Throwable ex) {
         if (!mgrOK) {
            lastStatusString="Improper package or process definition Id!";
         } else {
            lastStatusString=ex.getMessage();
         }
         return false;
      }

   }

   public static String getLastStatusString () {
      return lastStatusString;
   }

   public static void main (String[] args) {
      if (args==null || args.length<3) {
         printUsage();
         return;
      }

      if (!CORBAProcStartClient.run(args)) {
         System.out.println("Error message: "+CORBAProcStartClient.getLastStatusString()+"\n");
         printUsage();
      }

   }

   static void configure (Properties props,String filePath) throws Exception {
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

   static String getClassName (NameValueInfo[] nvia,String varId) {
      if (nvia!=null && nvia.length>0) {
         for (int i=0; i<nvia.length; i++) {
            NameValueInfo nvi=nvia[i];
            if (nvi.attribute_name.equals(varId)) {
               return nvi.type_name;
            }
         }
      }
      return null;
   }

   static void printUsage () {
      System.err.println("This application is used to start shark's process through the CORBA.");
      System.err.println("It is supposed that CORBA server is up, and that corresponding xpdl is loaded into shark.");
      System.err.println();
      System.err.println("usage: java org.enhydra.shark.test.CORBAProcStartClient configFilePath pkgId pDefId [var1=val1 [var2=val2 ... ]]");
      System.err.println();
      System.err.println("arguments:");
      System.err.println("  configFilePath   the path to the configuration file.");
      System.err.println("  pkgId            the id of xpdl package.");
      System.err.println("  pDefId           the id of xpdl process definition.");
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

