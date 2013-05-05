package org.enhydra.shark.corbaclient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.lang.reflect.Method;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import org.enhydra.shark.corba.WorkflowService.AdminMisc;
import org.enhydra.shark.corba.WorkflowService.CacheAdministration;
import org.enhydra.shark.corba.WorkflowService.DeadlineAdministration;
import org.enhydra.shark.corba.WorkflowService.ExecutionAdministration;
import org.enhydra.shark.corba.WorkflowService.LimitAdministration;
import org.enhydra.shark.corba.WorkflowService.MappingAdministration;
import org.enhydra.shark.corba.WorkflowService.PackageAdministration;
import org.enhydra.shark.corba.WorkflowService.RepositoryMgr;
import org.enhydra.shark.corba.WorkflowService.SharkConnection;
import org.enhydra.shark.corba.WorkflowService.SharkInterface;
import org.enhydra.shark.corba.WorkflowService.SharkInterfaceHelper;
import org.enhydra.shark.corba.WorkflowService.UserGroupAdministration;
import org.enhydra.shark.corbaclient.actions.Connect;
import org.enhydra.shark.corbaclient.actions.Disconnect;
import org.enhydra.shark.corbaclient.actions.Exit;
import org.enhydra.shark.corbaclient.actions.Refresh;
import org.enhydra.shark.corbaclient.actions.SetRefreshingRate;
import org.enhydra.shark.corbaclient.actions.ShutdownEngine;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;

/**
 * The base abstract class for creating workflow clients (administration,
 * monitoring, worklist handling). Implements four esential actions
 * used by every client: Connect, Disconnect, SetRefreshingRate and
 * Exit. It also defines the visual framework for clients.
 * <p> It creates and starts the Thread that is used for refreshing
 * the engine data (it calls the refresh method of this class).
 * <p> The implementation classes has to implement some abstract
 * methods, such as refresh, createCenterComponent, etc.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public abstract class SharkClient extends ActionPanel {

    // Application Icon. From resource file.
    protected static ImageIcon appIcon;
    // Application Icon. From resource file.
    protected static ImageIcon logoIcon;
    // Application Title and other stuff. From resource file.
    protected static String appTitle;

    // The frame of application
    protected JFrame myFrame;

    protected JMenuBar menubar;

    protected SplashScreen splashScreen;
    // periodical refreshment
    protected RefreshingRate refreshingRateInSeconds;

    protected boolean isRefresherActive = false;
    protected boolean isRefresherEnabled = true;

    protected Thread refresher = new Thread() {
        public void run() {
            while (true) {
                try {
                    if (isRefresherEnabled && username != null) {
                        isRefresherActive = true;
                        refresh(true);
                    }
                } catch (Exception ex) {
                }
                isRefresherActive = false;
                try {
                    Thread.sleep(refreshingRateInSeconds.getRefreshingRate() * 1000);
                } catch (Exception ex) {
                    isRefresherActive = false;
                }
            }
        }
    };

    protected static SharkInterface workflowServer;
    protected static SharkConnection workflowService;
    protected static AdminMisc adminMiscUtilities;
    protected static CacheAdministration cacheAdmin;
    protected static DeadlineAdministration deadlineAdmin;
    protected static ExecutionAdministration execAdmin;
    protected static LimitAdministration limitAdmin;
    protected static MappingAdministration mappingAdmin;
    protected static PackageAdministration pkgAdmin;
    protected static RepositoryMgr repositoryMgr;
    protected static UserGroupAdministration userGroupAdmin;
    protected static CommonExpressionBuilder commonExpressionBuilder;

    protected static String username = null;
    protected static String pwd = null;

    private static String host;
    private static String port;
    private static String workflowServerName;

    protected static ORB orb;
    public static boolean POA = false;

    public static ORB getORB() {
        return orb;
    }

    public static SharkInterface findWorkflowServer(String host, String port,
                                                    String workflowServerName) {
        if (workflowServer == null || !host.equals(SharkClient.host) ||
                !port.equals(SharkClient.port) || !workflowServerName.equals(workflowServerName)) {
            try {
                SharkClient.host = host.trim();
                SharkClient.port = port.trim();
                SharkClient.workflowServerName = workflowServerName.trim();
                String vers = System.getProperty("java.version");
                String[] args = new String[0];
                if (vers.compareTo("1.4") < 0) {
                    args = new String[]{
                                "-ORBInitialHost", SharkClient.host, "-ORBInitialPort", SharkClient.port
                        };
                } else
                    args = new String[]{
                                "-ORBInitRef", "NameService=corbaloc::" + SharkClient.host + ":" + SharkClient.port + "/NameService"};

                // Create and initialize the ORB
                orb = ORB.init(args, null);
                System.out.println("Initializing ORB");

                try {

                    Class poaHelper = ClassLoader.getSystemClassLoader().loadClass(("org.omg.PortableServer.POAHelper"));
                    Method m = poaHelper.getDeclaredMethod("narrow", new Class[]{org.omg.CORBA.Object.class});
                    Object poa = m.invoke(null, new Object[]{orb.resolve_initial_references("RootPOA")});
                    ((org.omg.PortableServer.POA) poa).the_POAManager().activate();
                    POA = true;
                } catch (Exception e) {
                    // No POA implementation
                    POA = false;
                    System.out.println("Using BOA implementation " + e);
                }

                // Get the root naming context
                org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
                NamingContext ncRef = NamingContextHelper.narrow(objRef);
                System.out.println("Shark Interface connected to NameService with " + args[1]);
                // Resolve the object reference in naming
                NameComponent nc = new NameComponent(SharkClient.workflowServerName, "");
                NameComponent path[] = {nc};
                workflowServer = SharkInterfaceHelper.narrow(ncRef.resolve(path));
                if (workflowServer != null) System.out.println("connected to " + workflowServerName);
            } catch (Exception e) {
                System.err.println("findWorkflowServer: " + e);
                //e.printStackTrace();
                return null;

            }
        }
        return workflowServer;
    }

    public static AdminMisc getAdminMiscUtilities() {
        if (adminMiscUtilities == null) {
            adminMiscUtilities = findWorkflowServer(SharkClient.host, SharkClient.port, SharkClient.workflowServerName).getAdminMisc();
            try {
                adminMiscUtilities.connect(username, pwd, "", "");
            } catch (Exception ex) {
            }
        }
        return adminMiscUtilities;
    }

    public static CacheAdministration getCacheAdmin() {
        if (cacheAdmin == null) {
            cacheAdmin = findWorkflowServer(SharkClient.host, SharkClient.port, SharkClient.workflowServerName).getCacheAdministration();
            try {
                cacheAdmin.connect(username, pwd, "", "");
            } catch (Exception ex) {
            }
        }
        return cacheAdmin;
    }

    public static DeadlineAdministration getDeadlineAdmin() {
        if (deadlineAdmin == null) {
            deadlineAdmin = findWorkflowServer(SharkClient.host, SharkClient.port, SharkClient.workflowServerName).getDeadlineAdministration();
            try {
                deadlineAdmin.connect(username, pwd, "", "");
            } catch (Exception ex) {
            }
        }
        return deadlineAdmin;
    }

    public static ExecutionAdministration getExecAmin() {
        if (execAdmin == null) {
            execAdmin = findWorkflowServer(SharkClient.host, SharkClient.port, SharkClient.workflowServerName).getExecutionAdministration();
            try {
                execAdmin.connect(username, pwd, "", "");
            } catch (Exception ex) {
            }
        }
        return execAdmin;
    }

    public static LimitAdministration getLimitAdmin() {
        if (limitAdmin == null) {
            limitAdmin = findWorkflowServer(SharkClient.host, SharkClient.port, SharkClient.workflowServerName).getLimitAdministration();
            try {
                limitAdmin.connect(username, pwd, "", "");
            } catch (Exception ex) {
            }
        }
        return limitAdmin;
    }

    public static MappingAdministration getMappingAdmin() {
        if (mappingAdmin == null) {
            mappingAdmin = findWorkflowServer(SharkClient.host, SharkClient.port, SharkClient.workflowServerName).getMappingAdministration();
            try {
                mappingAdmin.connect(username, pwd, "", "");
            } catch (Exception ex) {
            }
        }
        return mappingAdmin;
    }

    public static PackageAdministration getPackageAmin() {
        if (pkgAdmin == null) {
            pkgAdmin = findWorkflowServer(SharkClient.host, SharkClient.port, SharkClient.workflowServerName).getPackageAdministration();
            try {
                pkgAdmin.connect(username, pwd, "", "");
            } catch (Exception ex) {
            }
        }
        return pkgAdmin;
    }

    public static RepositoryMgr getRepositoryManager() {
        if (repositoryMgr == null) {
            repositoryMgr = findWorkflowServer(SharkClient.host, SharkClient.port, SharkClient.workflowServerName).getRepositoryManager();
            try {
                repositoryMgr.connect(username, pwd, "", "");
            } catch (Exception ex) {
            }
        }
        return repositoryMgr;
    }

    public static UserGroupAdministration getUserGroupAmin() {
        if (userGroupAdmin == null) {
            userGroupAdmin = findWorkflowServer(SharkClient.host, SharkClient.port, SharkClient.workflowServerName).getUserGroupAdministration();
            try {
                userGroupAdmin.connect(username, pwd, "", "");
            } catch (Exception ex) {
            }
        }
        return userGroupAdmin;
    }

    public static CommonExpressionBuilder getCommonExpressionBuilder() {
        if (commonExpressionBuilder == null) {
            ExecutionAdministration ea = getExecAmin();
            commonExpressionBuilder = new CommonExpressionBuilder(ea, findWorkflowServer(SharkClient.host, SharkClient.port, SharkClient.workflowServerName).getExpressionBuilderManager());
        }
        return commonExpressionBuilder;
    }

    public boolean isRefresherActive() {
        return isRefresherActive;
    }

    public void startRefresher() {
        isRefresherEnabled = true;
    }

    public void stopRefresher() {
        isRefresherEnabled = false;
    }

    //****************************** DEFAULT INITIALIZATION ***********************
    private void initResources() {
        try {
            String vers = System.getProperty("java.version");
            if (vers.compareTo("1.4") < 0) {
                System.out.println("!!!WARNING: application must be run with a " +
                                "1.4 or higher version VM!!!");
            }

            // application title
            appTitle = ResourceManager.getLanguageDependentString(getAppTitleResourceString());

            // Logo
            URL url = ResourceManager.getResource(getLogoResourceString());
            if (url != null) logoIcon = new ImageIcon(url);

            // Icon
            url = ResourceManager.getResource(getIconResourceString());
            if (url != null) appIcon = new ImageIcon(url);

            try {
                //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception exc) {
                System.err.println("Error loading L&F: " + exc);
            }
        } catch (Throwable t) {
            System.err.println("uncaught exception: " + t);
            t.printStackTrace();
        }
    }
    //*********************** END OF DEFAULT INITIALIZATION ***********************

    //************************** CONSTRUCTOR ***********************
    /**
     * Creates instance of main application class.
     */
    public SharkClient(JFrame frame) {
        super();
        myFrame = frame;
        splashScreen = new SplashScreen(frame);

        super.init();
        initResources();
        int rris;
        try {
            rris = Integer.parseInt(ResourceManager.getLanguageDependentString(getRefreshingRateString()));
        } catch (Exception ex) {
            rris = 50;
        }
        refreshingRateInSeconds = new RefreshingRate(frame, rris);


        menubar = BarFactory.createMenubar(getMenubarResourceString(), commands);
        // adding menubar to the main panel
        add(menubar, BorderLayout.NORTH);
        // adding center component
        add(createCenterComponent(), BorderLayout.CENTER);

        myFrame.setBackground(Color.lightGray);
        myFrame.getContentPane().setLayout(new BorderLayout());
        myFrame.getContentPane().add(this, BorderLayout.CENTER);
        myFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        myFrame.addWindowListener(new AppCloser());
        myFrame.pack();
        // set default location to be centered and size to be almost maximized
        //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenSize = GraphicsEnvironment
        .getLocalGraphicsEnvironment()
                        .getDefaultScreenDevice()
                        .getDefaultConfiguration()
                        .getBounds();

        int xMinus = 50, yMinus = 100;
        myFrame.setBounds(xMinus / 2, yMinus / 2, screenSize.width - xMinus,
                screenSize.height - yMinus);

        if (appIcon != null) myFrame.setIconImage(appIcon.getImage());
        myFrame.setTitle(appTitle + " - " + ResourceManager.getLanguageDependentString("NoneKey"));

        myFrame.setVisible(true);

        refresher.start();
        getAction(Utils.getUnqualifiedClassName(Connect.class)).actionPerformed(null);
        if (username == null) {
            System.exit(0);
        }
        requestFocus();
    }
    //************************* END OF WORKLISTHANDLER CONSTRUCTOR *****************

    //************** APPCLOSER CLASS FOR CLOSING APPLICATION WINDOW ***************
    /**
     * To shutdown when run as an application.
     */
    protected final class AppCloser extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            getAction(Utils.getUnqualifiedClassName(Exit.class)).actionPerformed(null);
        }
    }
    //**** END OF CREATING APPLICATION CLOSER COMPONENT FOR APPLICATION WINDOW ****

    protected void createActions() {
        defaultActions = new Action[]{
                    new Connect(this),
                    new Disconnect(this),
                    new ShutdownEngine(this),
                    new Refresh(this),
                    new SetRefreshingRate(this),
                    new Exit(this)
            };
    }

    public void setTitle() {
        myFrame.setTitle(appTitle + " - " + getFullUserName());
    }

    public String getFullUserName() {
        String name = ResourceManager.getLanguageDependentString("NoneKey");
        if (username != null) {
            try {
                name = getUserGroupAmin().getUserRealName(username);
                if (name == null || name.trim().length() == 0) {
                    name = username;
                }
            } catch (Exception ex) {
            }
        }
        return name;
    }

    public JFrame getFrame() {
        return myFrame;
    }

    public SplashScreen getSplashScreen() {
        return splashScreen;
    }

    public static String getAppTitle() {
        return appTitle;
    }

    public static SharkConnection getService() {
        return workflowService;
    }

    public static SharkInterface getServer() {
        return workflowServer;
    }

    public static void setService(SharkConnection wcs) {
        workflowService = wcs;
        if (wcs == null) {
            adminMiscUtilities = null;
            cacheAdmin = null;
            execAdmin = null;
            mappingAdmin = null;
            pkgAdmin = null;
            repositoryMgr = null;
            userGroupAdmin = null;
            workflowServer = null;
            commonExpressionBuilder = null;
        }
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        SharkClient.username = username;
    }

    public static void setPassword(String pwd) {
        SharkClient.pwd = pwd;
    }

    public void setORB(ORB orb) {
        this.orb = orb;
    }

    public void enableControls(boolean enable) {
        // enable/disable menubar
        for (int i = 0; i < menubar.getComponentCount(); i++) {
            menubar.getComponent(i).setEnabled(enable);
        }
    }

    public RefreshingRate getRefreshingRate() {
        return refreshingRateInSeconds;
    }

    public void onDisconnectOrShutdown() {
    }

    protected abstract String getAppTitleResourceString();

    protected abstract String getLogoResourceString();

    protected abstract String getIconResourceString();

    protected abstract String getMenubarResourceString();

    protected abstract String getRefreshingRateString();

    public abstract void clearComponents();

    public abstract void refresh(boolean mandatoryRefreshing);

}

