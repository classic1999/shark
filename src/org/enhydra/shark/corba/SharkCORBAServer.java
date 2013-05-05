package org.enhydra.shark.corba;

// Server will use the naming service.
// The package containing special exceptions thrown by the name service.

import java.util.Properties;

import org.enhydra.shark.Shark;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.corba.WorkflowService.*;
import org.enhydra.shark.utilities.DeadlineChecker;
import org.enhydra.shark.utilities.LimitChecker;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.WfBase.NameValueInfo;
import org.omg.WorkflowModel.WfCreateProcessEventAudit;
import org.omg.WorkflowModel.WfDataEventAudit;
import org.omg.WorkflowModel.WfStateEventAudit;
import org.omg.WorkflowModel._WfCreateProcessEventAuditStub;
import org.omg.WorkflowModel._WfDataEventAuditStub;
import org.omg.WorkflowModel._WfStateEventAuditStub;

/**
 * The server class for engine. It is registered in 'CORBA' name server.
 * The client applications has to get this object from the name server.
 * 
 * @author Sasa Bojanic
 * @author Vladimir Puskas
 * @author Rich Robinson
 * @author Andy Zeneski (jaz@ofbiz.org)
 */
public class SharkCORBAServer extends _SharkInterfaceImplBase {

   private org.enhydra.shark.Shark shark;

   private ORB orb;

   private String engineName;

   private String nsHost;

   private String nsPort;

   boolean trackObjects;

   public SharkCORBAServer(String engineName,
                           String nsHost,
                           String nsPort,
                           Shark shark) {
      // all parameters are required and cannot be null
      if (engineName == null) throw new IllegalArgumentException("Engine name cannot be null");
      if (nsHost == null) throw new IllegalArgumentException("Name server host cannot be null");
      if (nsPort == null) throw new IllegalArgumentException("Name server port cannot be null");
      if (shark == null) throw new IllegalArgumentException("Shark instance cannot be null");

      this.engineName = engineName;
      this.nsHost = nsHost;
      this.nsPort = nsPort;
      this.shark = shark;
      this.trackObjects = Boolean.valueOf(shark.getProperties()
         .getProperty("CORBAServer.TrackAndDisconnect", "false")).booleanValue();
      boolean ignoreProblematicRequester=new Boolean(shark.getProperties().getProperty("CORBAServer.ignoreProblematicRequester","true")).booleanValue();
      WfLinkingRequesterForCORBA.setIgnoreProblematicRequesterProcess(ignoreProblematicRequester);

      
      // Register a shutdown hook that cleanly shuts down Shark
      Runtime.getRuntime().addShutdownHook(new Thread() {
         public void run() {
            shutdownORB();
            if (null != p) {
               p.destroy();
            }
         }
      });
   }

   public void startCORBAServer() throws BaseException {
      // orb conf
      String[] a1 = {
                  "-ORBInitialHost", nsHost, "-ORBInitialPort", nsPort
      };
      String[] a2 = {
                  "-ORBInitRef",
                  "NameService=corbaloc::"
                                                      + nsHost + ":" + nsPort
                                                      + "/NameService"
      };

      // create and initialize the ORB
      String vers = System.getProperty("java.version");
      if (vers.compareTo("1.4") < 0) {
         orb = ORB.init(a1, null);
      } else {
         orb = ORB.init(a2, null);
      }
      // get the root naming context
      org.omg.CORBA.Object objRef = null;
      try {
         objRef = orb.resolve_initial_references("NameService");
      } catch (Exception ex) {
         // The following code is included to enable Shark Server to be
         // started
         // from Shark project script that starts all project parts at
         // ones
         for (int i = 0; i < 25; i++) {

            try {
               Thread.sleep(2500);
               objRef = orb.resolve_initial_references("NameService");
            } catch (Exception ex2) {}
            if (objRef != null) {
               break;
            }
         }
      }

      // check the root
      if (objRef == null) { throw new BaseException("Unable to obtain initial reference from orb"); }

      // get the naming context
      NamingContext ncRef = NamingContextHelper.narrow(objRef);
      if (ncRef == null) { throw new BaseException("Null NamingContext"); }

      // bind the object reference in naming
      NameComponent nc = new NameComponent(engineName, "");
      NameComponent path[] = {
         nc
      };
      try {
         ncRef.rebind(path, this);
      } catch (NotFound e) {
         throw new BaseException(e);
      } catch (CannotProceed e) {
         throw new BaseException(e);
      } catch (InvalidName e) {
         throw new BaseException(e);
      }

      // start the orb
      orb.run(); // WARNING this blocks; objects calling this method
                 // should be threaded

   }

   public ORB getBoundORB() {
      return orb;
   }

   public void shutdownORB() {
      try {
         orb.disconnect(this);
         orb.shutdown(false);
      } catch (Exception e) {
         // maybe throw some log4j in here
      }
   }

   void shutdown() {
      shutdownORB();
      System.exit(0);
   }

   public AdminMisc getAdminMisc() {
      return new AdminMiscCORBA(this, shark.getAdminInterface()
         .getAdminMisc());
   }

   public ExecutionAdministration getExecutionAdministration() {
      return new ExecutionAdminCORBA(this, shark.getAdminInterface()
         .getExecutionAdministration());
   }

   public MappingAdministration getMappingAdministration() {
      return new MappingAdminCORBA(this, shark.getAdminInterface()
         .getParticipantMappingAdministration(), shark.getAdminInterface()
         .getApplicationMappingAdministration());
   }

   public PackageAdministration getPackageAdministration() {
      return new PackageAdminCORBA(this, shark.getAdminInterface()
         .getPackageAdministration());
   }

   public UserGroupAdministration getUserGroupAdministration() {
      return new UserGroupAdminCORBA(this, shark.getAdminInterface()
         .getUserGroupAdministration());
   }

   public CacheAdministration getCacheAdministration() {
      return new CacheAdminCORBA(this, shark.getAdminInterface()
         .getCacheAdministration());
   }

   public DeadlineAdministration getDeadlineAdministration() {
      return new DeadlineAdminCORBA(this, shark.getAdminInterface()
         .getDeadlineAdministration());
   }

   public LimitAdministration getLimitAdministration() {
      return new LimitAdminCORBA(this, shark.getAdminInterface()
         .getLimitAdministration());
   }

   public RepositoryMgr getRepositoryManager() {
      return new RepositoryManagerCORBA(this, shark.getRepositoryManager());
   }

   public SharkConnection getSharkConnection() {
      return new SharkConnectionCORBA(this, shark.getSharkConnection());
   }

   public ExpressionBuilderManager getExpressionBuilderManager() {
      return new ExpressionBuilderMgrCORBA(this, shark.getExpressionBuilderManager());
   }

   public NameValueInfo[] getProperties() {
      return SharkCORBAUtilities.makeCORBANameValueInfoArray(shark.getProperties());
   }

   boolean validateUser(String username, String pwd) {
      try {
         return shark.validateUser(username, pwd);
      } catch (Exception ex) {
         return true;
      }
   }

   private static Process p;

   private static long nameserverRestart_ms = 0;

   //private static Thread penelope;
   // If exists, first argument is the path to Shark configuration file
   public static void main(String args[]) {
      try {
         if (args != null && args.length > 0) {
            Shark.configure(args[0]);
         } else {
            Shark.configure();
         }

         org.enhydra.shark.Shark shark = Shark.getInstance();

         Properties properties = shark.getProperties();
         String nameserverhost = properties.getProperty("nameserverhost",
                                                        "localhost");
         final String nameserverport = properties.getProperty("nameserverport",
                                                              "10123");
         String enginename = properties.getProperty("enginename", "Shark");
         final String nameServerExecutable = properties.getProperty("nameserver.executable",
                                                                    null);
         String nameserverRestart = properties.getProperty("nameserver.restarting_period_minutes",
                                                           "0");
         
         double nsrmin = 0;
         try {
            nsrmin = Double.parseDouble(nameserverRestart);
            nameserverRestart_ms = (long) (nsrmin * 60 * 1000);
         } catch (Exception ex) {}
         // create default user
         try {
            org.enhydra.shark.api.client.wfservice.UserGroupAdministration uga = shark.getAdminInterface()
               .getUserGroupAdministration();
            String adminGroup = properties.getProperty("DEFAULT_ADMINISTRATOR_GROUP_NAME",
                                                       "AdminGroup");
            String adminGroupDescripton = properties.getProperty("DEFAULT_ADMINISTRATOR_GROUP_DESCRIPTION",
                                                                 "Default Admin Group");
            String adminUsername = properties.getProperty("DEFAULT_ADMINISTRATOR_USERNAME",
                                                          "admin");
            String adminPwd = properties.getProperty("DEFAULT_ADMINISTRATOR_PASSWORD",
                                                     "enhydra");
            String adminFirstName = properties.getProperty("DEFAULT_ADMINISTRATOR_FIRST_NAME",
                                                           "Administrator");
            String adminLastName = properties.getProperty("DEFAULT_ADMINISTRATOR_LAST_NAME",
                                                          "Admin");
            String adminEmail = properties.getProperty("DEFAULT_ADMINISTRATOR_EMAIL",
                                                       "admin@together.at");
            try {
               if (!uga.doesGroupExist(adminGroup)) {
                  uga.createGroup(adminGroup, adminGroupDescripton);
               }
            } catch (Exception ex) {}
            if (!uga.doesUserExist(adminUsername)) {
               uga.createUser(adminGroup,
                              adminUsername,
                              adminPwd,
                              adminFirstName,
                              adminLastName,
                              adminEmail);
            }
         } catch (Exception ex) {}

         // initialize deadline timer
         String delay = null;
         if (args != null && args.length > 1) {
            delay = args[1];
         } else {
            delay = properties.getProperty("Deadlines.pollingTime", "300000");
         }
         if (properties.getProperty("Deadlines.SERVER_SIDE_CHECKING", "false")
            .equals("true")) {
            new DeadlineChecker(shark.getAdminInterface()
               .getDeadlineAdministration(), Long.parseLong(delay));
         }

         // initialize limit timer
         String ldelay = null;
         String lacn = properties.getProperty("LimitAgentManagerClassName");
         if (lacn != null) {
            if (args != null && args.length > 2) {
               ldelay = args[2];
            } else {
               ldelay = properties.getProperty("Limits.pollingTime", "60000");
            }
            if (properties.getProperty("Limits.SERVER_SIDE_CHECKING", "false")
               .equals("true")) {
               new LimitChecker(shark.getAdminInterface()
                  .getLimitAdministration(), Long.parseLong(ldelay));
            }
         }

         // create/start the server
         final SharkCORBAServer ws = new SharkCORBAServer(enginename,
                                                          nameserverhost,
                                                          nameserverport,
                                                          shark);
         if (null != nameServerExecutable) {
            if (nameserverRestart_ms > 0) {
               System.out.println("Nameserver will be restarted every "
                                  + nameserverRestart + " minutes!");
            }

            new Thread() {
               public void run() {
                  while (true) {
                     try {
                        p = Runtime.getRuntime().exec(nameServerExecutable
                                                      + " -ORBInitialPort "
                                                      + nameserverport);

                        System.out.println("Nameserver is (re)started!");
                        // (re)register with name server in a separate
                        // thread
                        new Thread() {
                           public void run() {
                              try {
                                 ws.startCORBAServer();
                              } catch (Exception ex) {
                                 ex.printStackTrace(System.out);
                                 throw new Error();

                              }
                           }
                        }.start();
                        if (nameserverRestart_ms > 0) {
                           new Thread() {
                              public void run() {
                                 try {
                                    Thread.sleep(nameserverRestart_ms);
                                 } catch (Exception ex) {} finally {
                                    p.destroy();
                                 }
                              }
                           }.start();
                        }

                     } catch (Throwable t) {
                        t.printStackTrace();
                        System.out.println("Didn't start the nameserver");
                        //throw new RootError("Didn't start the
                        // nameserver",t);
                     }

                     while (true) {
                        try {
                           p.waitFor();
                        } catch (InterruptedException e) {
                           continue;
                        }

                        // Process is terminated
                        break;
                     }
                  }
               }
            }.start();
         } else {
            ws.startCORBAServer();
         }

      } catch (Throwable e) {
         e.printStackTrace(System.out);
         throw new Error();
      }
   }

   public void doneWith(org.omg.CORBA.Object toDisconnect) {
      if (toDisconnect instanceof Collective) {
         ((Collective)toDisconnect).__disband(orb);
      } else {
         try {
            if (toDisconnect instanceof WfCreateProcessEventAudit) {
               WfLinkingRequesterForCORBA.emptyCollective(((WfCreateProcessEventAudit)toDisconnect).process_key(),getBoundORB()); 
            }
            if (toDisconnect instanceof WfDataEventAudit) {
               WfLinkingRequesterForCORBA.emptyCollective(((WfDataEventAudit)toDisconnect).process_key(),getBoundORB());              
            }
            if (toDisconnect instanceof WfStateEventAudit) {
               WfLinkingRequesterForCORBA.emptyCollective(((WfStateEventAudit)toDisconnect).process_key(),getBoundORB());             
            }
            orb.disconnect(toDisconnect);
         } catch (Exception ex) {
         }
      }
   }
}

