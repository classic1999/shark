package org.enhydra.shark.corba.poa;

// Server will use the naming service.
// The package containing special exceptions thrown by the name service.

import java.util.Properties;

import org.enhydra.shark.Shark;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.corba.WorkflowService.*;
import org.enhydra.shark.corba.poa.*;
import org.omg.WorkflowModel.*;
import org.enhydra.shark.utilities.DeadlineChecker;
import org.enhydra.shark.utilities.LimitChecker;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Policy;
//import org.omg.CORBA.ORBPackage.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
//import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.WfBase.NameValueInfo;
import org.omg.PortableServer.POAPackage.*;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.*;


/**
 * The server class for engine. It is registered in 'CORBA' name server.
 * The client applications has to get this object from the name server.
 *
 * @author Sasa Bojanic
 * @author Vladimir Puskas
 * @author Rich Robinson
 * @author Andy Zeneski (jaz@ofbiz.org)
 * @author David Forslund
 */
public class SharkCORBAServer extends SharkInterfacePOA {

    private org.enhydra.shark.Shark shark;

    private ORB orb;

    private String engineName;

    private String nsHost;

    private String nsPort;

    boolean trackObjects;

    private POA rootPOA;
    private AdminMisc adminMisc = null;
    private ExecutionAdministration executionAdmin = null;
    private MappingAdministration mappingAdmin = null;
    private PackageAdministration packageAdmin = null;
    private UserGroupAdministration userGroupAdmin = null;
    private CacheAdministration cacheAdmin = null;
    private DeadlineAdministration deadlineAdmin = null;
    private LimitAdministration limitAdmin = null;
    private RepositoryMgr repositoryMgr = null;
    private SharkConnection connect = null;
    private ExpressionBuilderManager express = null;

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
        boolean ignoreProblematicRequester = new Boolean(shark.getProperties().getProperty("CORBAServer.ignoreProblematicRequester", "true")).booleanValue();
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
        System.out.println("java.version " + vers);
        if (vers.compareTo("1.4") < 0) {
            orb = ORB.init(a1, null);
        } else {
            orb = ORB.init(a2, null);
        }
        // org.omg.PortableServer.POA rootPOA = null;
        try {
            rootPOA = org.omg.PortableServer.POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            /*
            Policy[] tpolicy = new Policy[3];
            tpolicy[0] = rootPOA.create_id_assignment_policy(IdAssignmentPolicyValue.SYSTEM_ID);
            tpolicy[1] = rootPOA.create_servant_retention_policy(ServantRetentionPolicyValue.RETAIN);
            tpolicy[2] = rootPOA.create_implicit_activation_policy(ImplicitActivationPolicyValue.IMPLICIT_ACTIVATION);
            childPOA = rootPOA.create_POA("childPOA",rootPOA.the_POAManager(), tpolicy);
            */
            // rootPOA.servant_to_reference(this);
        } catch (org.omg.CORBA.ORBPackage.InvalidName invalidName) {
            invalidName.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } /*catch (InvalidPolicy invalidPolicy) {
            invalidPolicy.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (AdapterAlreadyExists adapterAlreadyExists) {
            adapterAlreadyExists.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } /*catch (ServantNotActive servantNotActive) {
            servantNotActive.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (WrongPolicy wrongPolicy) {
            wrongPolicy.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } */
        //  _this(orb);
        //   orb.connect(this._this_object());
        // get the root naming context
        org.omg.CORBA.Object objRef = null;
        try {
            objRef = orb.resolve_initial_references("NameService");
        } catch (Exception ex) {
            System.err.println("startCORBAServer: " + ex);
            // The following code is included to enable Shark Server to be
            // started
            // from Shark project script that starts all project parts at
            // ones
            for (int i = 0; i < 2; i++) {

                try {
                    Thread.sleep(2500);
                    objRef = orb.resolve_initial_references("NameService");
                } catch (Exception ex2) {
                    System.err.println("NameService failed " + ex2);
                }
                if (objRef != null) {
                    break;
                }
            }
        }

        // check the root
        if (objRef == null) {
            throw new BaseException("Unable to obtain initial reference from orb");
        }

        // get the naming context
        NamingContext ncRef = NamingContextHelper.narrow(objRef);
        if (ncRef == null) {
            throw new BaseException("Null NamingContext");
        } else System.out.println("NameService contacted");
        // bind the object reference in naming
        NameComponent nc = new NameComponent(engineName, "");
        NameComponent path[] = {
            nc
        };
        try {
            rootPOA.the_POAManager().activate();
            rootPOA.activate_object(this);
            SharkCORBAUtilities.setPOA(rootPOA);
            SharkCORBAUtilities.setORB(orb);
            ncRef.rebind(path, this._this_object());

            System.out.println("CORBA Server registered and activated");
        } catch (NotFound e) {
            throw new BaseException(e);
        } catch (CannotProceed e) {
            throw new BaseException(e);
        } catch (InvalidName e) {
            throw new BaseException(e);
        } catch (AdapterInactive adapterInactive) {
            adapterInactive.printStackTrace();
        } catch (ServantAlreadyActive es) {
            es.printStackTrace();
        } catch (WrongPolicy wp) {
            wp.printStackTrace();
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
            orb.disconnect(this._this_object());
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
      //  if (adminMisc != null) return adminMisc;
        AdminMiscCORBA adminMiscImpl = new AdminMiscCORBA(this, shark.getAdminInterface()
                .getAdminMisc());



        try {
            byte[] o = rootPOA.activate_object(adminMiscImpl);
            adminMisc = AdminMiscHelper.narrow(rootPOA.id_to_reference(o));

        } catch (ServantAlreadyActive servantAlreadyActive) {
            servantAlreadyActive.printStackTrace();
        } catch (WrongPolicy wrongPolicy) {
            wrongPolicy.printStackTrace();
        } catch (ObjectNotActive objectNotActive) {
            objectNotActive.printStackTrace();
        }

        return adminMisc;
    }

    public ExecutionAdministration getExecutionAdministration() {

     //   if (executionAdmin != null) return executionAdmin;
        ExecutionAdminCORBA executionAdminImpl = new ExecutionAdminCORBA(this, shark.getAdminInterface()
                .getExecutionAdministration());

        try {
            rootPOA.activate_object(executionAdminImpl);
            executionAdmin = ExecutionAdministrationHelper.narrow(rootPOA.servant_to_reference(executionAdminImpl));

        } catch (ServantAlreadyActive servantAlreadyActive) {
            servantAlreadyActive.printStackTrace();
        } catch (WrongPolicy wrongPolicy) {
            wrongPolicy.printStackTrace();
        } catch (ServantNotActive servantNotActive) {
            servantNotActive.printStackTrace();
        }

        return executionAdmin;
    }

    public MappingAdministration getMappingAdministration() {

   //     if (mappingAdmin != null) return mappingAdmin;
        MappingAdminCORBA mappingAdminImpl = new MappingAdminCORBA(this, shark.getAdminInterface()
                .getParticipantMappingAdministration(), shark.getAdminInterface()
                .getApplicationMappingAdministration());

        try {
            rootPOA.activate_object(mappingAdminImpl);
            mappingAdmin = MappingAdministrationHelper.narrow(rootPOA.servant_to_reference(mappingAdminImpl));

        } catch (ServantAlreadyActive servantAlreadyActive) {
            servantAlreadyActive.printStackTrace();
        } catch (WrongPolicy wrongPolicy) {
            wrongPolicy.printStackTrace();
        } catch (ServantNotActive servantNotActive) {
            servantNotActive.printStackTrace();
        }

        return mappingAdmin;

    }

    public PackageAdministration getPackageAdministration() {
      //  if (packageAdmin != null) return packageAdmin;
        PackageAdminCORBA packageAdminImpl = new PackageAdminCORBA(this, shark.getAdminInterface()
                .getPackageAdministration());

        try {
            rootPOA.activate_object(packageAdminImpl);
            packageAdmin = PackageAdministrationHelper.narrow(rootPOA.servant_to_reference(packageAdminImpl));

        } catch (ServantAlreadyActive servantAlreadyActive) {
            servantAlreadyActive.printStackTrace();
        } catch (WrongPolicy wrongPolicy) {
            wrongPolicy.printStackTrace();
        } catch (ServantNotActive servantNotActive) {
            servantNotActive.printStackTrace();
        }

        return packageAdmin;

    }

    public UserGroupAdministration getUserGroupAdministration() {
      //  if (userGroupAdmin != null) return userGroupAdmin;
        UserGroupAdminCORBA userGroupAdminImpl = new UserGroupAdminCORBA(this, shark.getAdminInterface()
                .getUserGroupAdministration());

        try {
            rootPOA.activate_object(userGroupAdminImpl);
            userGroupAdmin = UserGroupAdministrationHelper.narrow(rootPOA.servant_to_reference(userGroupAdminImpl));

        } catch (ServantAlreadyActive servantAlreadyActive) {
            servantAlreadyActive.printStackTrace();
        } catch (WrongPolicy wrongPolicy) {
            wrongPolicy.printStackTrace();
        } catch (ServantNotActive servantNotActive) {
            servantNotActive.printStackTrace();
        }

        return userGroupAdmin;
    }

    public CacheAdministration getCacheAdministration() {

     //   if (cacheAdmin != null) return cacheAdmin;
        CacheAdminCORBA cacheAdminImpl = new CacheAdminCORBA(this, shark.getAdminInterface()
                .getCacheAdministration());

        try {
            rootPOA.activate_object(cacheAdminImpl);
            cacheAdmin = CacheAdministrationHelper.narrow(rootPOA.servant_to_reference(cacheAdminImpl));

        } catch (ServantAlreadyActive servantAlreadyActive) {
            servantAlreadyActive.printStackTrace();
        } catch (WrongPolicy wrongPolicy) {
            wrongPolicy.printStackTrace();
        } catch (ServantNotActive servantNotActive) {
            servantNotActive.printStackTrace();
        }

        return cacheAdmin;
    }

    public DeadlineAdministration getDeadlineAdministration() {

      //  if (deadlineAdmin != null) return deadlineAdmin;
        DeadlineAdminCORBA deadlineAdminImpl = new DeadlineAdminCORBA(this, shark.getAdminInterface()
                .getDeadlineAdministration());

        try {
            rootPOA.activate_object(deadlineAdminImpl);
            deadlineAdmin = DeadlineAdministrationHelper.narrow(rootPOA.servant_to_reference(deadlineAdminImpl));

        } catch (ServantAlreadyActive servantAlreadyActive) {
            servantAlreadyActive.printStackTrace();
        } catch (WrongPolicy wrongPolicy) {
            wrongPolicy.printStackTrace();
        } catch (ServantNotActive servantNotActive) {
            servantNotActive.printStackTrace();
        }

        return deadlineAdmin;
    }

    public LimitAdministration getLimitAdministration() {
      //  if (limitAdmin != null) return limitAdmin;
        LimitAdminCORBA limitAdminImpl = new LimitAdminCORBA(this, shark.getAdminInterface()
                .getLimitAdministration());

        try {
            rootPOA.activate_object(limitAdminImpl);
            limitAdmin = LimitAdministrationHelper.narrow(rootPOA.servant_to_reference(limitAdminImpl));

        } catch (ServantAlreadyActive servantAlreadyActive) {
            servantAlreadyActive.printStackTrace();
        } catch (WrongPolicy wrongPolicy) {
            wrongPolicy.printStackTrace();
        } catch (ServantNotActive servantNotActive) {
            servantNotActive.printStackTrace();
        }

        return limitAdmin;
    }

    public RepositoryMgr getRepositoryManager() {

    //    if (repositoryMgr != null) return repositoryMgr;
        RepositoryManagerCORBA repositoryManagerImpl = new RepositoryManagerCORBA(this, shark.getRepositoryManager());

        try {
            rootPOA.activate_object(repositoryManagerImpl);
            repositoryMgr = RepositoryMgrHelper.narrow(rootPOA.servant_to_reference(repositoryManagerImpl));

        } catch (ServantAlreadyActive servantAlreadyActive) {
            servantAlreadyActive.printStackTrace();
        } catch (WrongPolicy wrongPolicy) {
            wrongPolicy.printStackTrace();
        } catch (ServantNotActive servantNotActive) {
            servantNotActive.printStackTrace();
        }

        return repositoryMgr;
    }

    public SharkConnection getSharkConnection() {

     //   if (connect != null) return connect;
        SharkConnectionCORBA connectImpl = new SharkConnectionCORBA(this, shark.getSharkConnection());
        try {
            rootPOA.activate_object(connectImpl);
            connect = SharkConnectionHelper.narrow(rootPOA.servant_to_reference(connectImpl));

        } catch (ServantAlreadyActive servantAlreadyActive) {
            servantAlreadyActive.printStackTrace();
        } catch (WrongPolicy wrongPolicy) {
            wrongPolicy.printStackTrace();
        } catch (ServantNotActive servantNotActive) {
            servantNotActive.printStackTrace();
        }

        return connect;
        //   return new SharkConnectionCORBA(this, shark.getSharkConnection())._this();
    }

    public ExpressionBuilderManager getExpressionBuilderManager() {

 //       if (express != null) return express;
        ExpressionBuilderMgrCORBA expressionBuilderMgrImpl = new ExpressionBuilderMgrCORBA(this, shark.getExpressionBuilderManager());

        try {
            rootPOA.activate_object(expressionBuilderMgrImpl);
            express = ExpressionBuilderManagerHelper.narrow(rootPOA.servant_to_reference(expressionBuilderMgrImpl));
            if (express == null) System.err.println("getExpressBuilderManager: " + express);
        } catch (ServantAlreadyActive servantAlreadyActive) {
            servantAlreadyActive.printStackTrace();
        } catch (WrongPolicy wrongPolicy) {
            wrongPolicy.printStackTrace();
        } catch (ServantNotActive servantNotActive) {
            servantNotActive.printStackTrace();
        }

        return express;
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
            } catch (Exception ex) {
            }
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
                } catch (Exception ex) {
                }
                if (!uga.doesUserExist(adminUsername)) {
                    uga.createUser(adminGroup,
                            adminUsername,
                            adminPwd,
                            adminFirstName,
                            adminLastName,
                            adminEmail);
                }
            } catch (Exception ex) {
            }

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
                                            } catch (Exception ex) {
                                            } finally {
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
        // get the servant associated with this CORBA object
        Servant s = null;
        try {
            s = rootPOA.reference_to_servant(toDisconnect);
        } catch (ObjectNotActive objectNotActive) {
            objectNotActive.printStackTrace();
        } catch (WrongPolicy wrongPolicy) {
            wrongPolicy.printStackTrace();
        } catch (WrongAdapter wrongAdapter) {
            wrongAdapter.printStackTrace();
        }

        if (s instanceof Collective) {
            ((Collective) s).__disband(orb);
        } else {
            try {
                if (toDisconnect instanceof WfCreateProcessEventAudit) {
                    WfLinkingRequesterForCORBA.emptyCollective(((WfCreateProcessEventAudit) toDisconnect).process_key(), getBoundORB());
                }
                if (toDisconnect instanceof WfDataEventAudit) {
                    WfLinkingRequesterForCORBA.emptyCollective(((WfDataEventAudit) toDisconnect).process_key(), getBoundORB());
                }
                if (toDisconnect instanceof WfStateEventAudit) {
                    WfLinkingRequesterForCORBA.emptyCollective(((WfStateEventAudit) toDisconnect).process_key(), getBoundORB());
                }
                toDisconnect._release();;
               // orb.disconnect(toDisconnect);
            } catch (Exception ex) {
                System.err.println("exception in doneWith" +ex);
            }
        }
    }
}

