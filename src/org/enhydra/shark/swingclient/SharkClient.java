package org.enhydra.shark.swingclient;

import java.awt.*;
import java.awt.event.*;
import java.net.*;

import javax.swing.*;

import org.enhydra.shark.Shark;

import org.enhydra.shark.api.client.wfservice.*;
import org.enhydra.shark.swingclient.actions.*;
import org.enhydra.shark.swingclient.CommonExpressionBuilder;

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

   protected boolean isRefresherActive=false;
   protected boolean isRefresherEnabled=true;

   protected Thread refresher=new Thread () {
      public void run () {
         while (true) {
            try {
               if (isRefresherEnabled && username!=null) {
                  isRefresherActive=true;
                  refresh(true);
               }
            } catch (Exception ex) {}
            isRefresherActive=false;
            try {
               Thread.sleep(refreshingRateInSeconds.getRefreshingRate()*1000);
            } catch (Exception ex){
               isRefresherActive=false;
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
   protected static ApplicationMappingAdministration applicationMappingAdmin;
   protected static ParticipantMappingAdministration participantMappingAdmin;
   protected static ScriptMappingAdministration scriptMappingAdmin;
   protected static PackageAdministration pkgAdmin;
   protected static RepositoryMgr repositoryMgr;
   protected static UserGroupAdministration userGroupAdmin;
   protected static CommonExpressionBuilder commonExpressionBuilder;

   protected static String username=null;
   protected static String pwd=null;

   protected static String configFileName="conf/Shark.conf";

   public static SharkInterface findWorkflowServer () {
      if (workflowServer==null) {
         try{
            java.io.File f=new java.io.File(SharkClient.configFileName);
            if (f.exists()) {
               Shark.configure(f);
            } else {
               Shark.configure();
            }
            workflowServer=Shark.getInstance();
            return workflowServer;
         } catch(Exception e) {
            //e.printStackTrace();
            return null;
         }
      } else {
         return workflowServer;
      }
   }

   public static AdminMisc getAdminMiscUtilities () {
      if (adminMiscUtilities==null) {
         adminMiscUtilities=findWorkflowServer().getAdminInterface().getAdminMisc();
         adminMiscUtilities.connect(username);
      }
      return adminMiscUtilities;
   }

   public static CacheAdministration getCacheAdmin () {
      if (cacheAdmin==null) {
         cacheAdmin=findWorkflowServer().getAdminInterface().getCacheAdministration();
         try {
            cacheAdmin.connect(username);
         } catch (Exception ex) {}
      }
      return cacheAdmin;
   }

   public static DeadlineAdministration getDeadlineAdmin () {
      if (deadlineAdmin==null) {
         deadlineAdmin=findWorkflowServer().getAdminInterface().getDeadlineAdministration();
         try {
            deadlineAdmin.connect(username);
         } catch (Exception ex) {}
      }
      return deadlineAdmin;
   }

   public static ExecutionAdministration getExecAmin () {
      if (execAdmin==null) {
         execAdmin=findWorkflowServer().
            getAdminInterface().getExecutionAdministration();
         try {
            execAdmin.connect(username,pwd,"","");
         } catch (Exception ex) {}
      }
      return execAdmin;
   }

   public static LimitAdministration getLimitAdmin () {
      if (limitAdmin==null) {
         limitAdmin=findWorkflowServer().getAdminInterface().getLimitAdministration();
         try {
            limitAdmin.connect(username);
         } catch (Exception ex) {}
      }
      return limitAdmin;
   }

   public static ParticipantMappingAdministration getParticipantMappingsAdmin () {
      if (participantMappingAdmin==null) {
         participantMappingAdmin=findWorkflowServer().getAdminInterface().getParticipantMappingAdministration();
         participantMappingAdmin.connect(username);
      }
      return participantMappingAdmin;
   }

   public static ApplicationMappingAdministration getApplicationMappingsAdmin () {
      if (applicationMappingAdmin==null) {
         applicationMappingAdmin=findWorkflowServer().getAdminInterface().getApplicationMappingAdministration();
         applicationMappingAdmin.connect(username);
      }
      return applicationMappingAdmin;
   }

   public static ScriptMappingAdministration getScriptMappingsAdmin () {
      if (scriptMappingAdmin==null) {
         scriptMappingAdmin=findWorkflowServer().getAdminInterface().getScriptMappingAdministration();
         scriptMappingAdmin.connect(username);
      }
      return scriptMappingAdmin;
   }

   public static PackageAdministration getPackageAmin () {
      if (pkgAdmin==null) {
         pkgAdmin=findWorkflowServer().getAdminInterface().getPackageAdministration();
         pkgAdmin.connect(username);
      }
      return pkgAdmin;
   }

   public static RepositoryMgr getRepositoryManager () {
      if (repositoryMgr==null) {
         repositoryMgr=findWorkflowServer().getRepositoryManager();
         repositoryMgr.connect(username);
      }
      return repositoryMgr;
   }

   public static UserGroupAdministration getUserGroupAmin () {
      if (userGroupAdmin==null) {
         userGroupAdmin=findWorkflowServer().getAdminInterface().getUserGroupAdministration();
         userGroupAdmin.connect(username);
      }
      return userGroupAdmin;
   }

   public static CommonExpressionBuilder getCommonExpressionBuilder () {
      if (commonExpressionBuilder==null) {
         ExecutionAdministration ea=getExecAmin();
         commonExpressionBuilder=new CommonExpressionBuilder(ea,findWorkflowServer().getExpressionBuilderManager());
      }
      return commonExpressionBuilder;
   }

   public boolean isRefresherActive () {
      return isRefresherActive;
   }

   public void startRefresher () {
      isRefresherEnabled=true;
   }

   public void stopRefresher () {
      isRefresherEnabled=false;
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
   /** Creates instance of main application class. */
   public SharkClient (JFrame frame) {
      super();
      myFrame = frame;
      splashScreen=new SplashScreen(frame);

      super.init();
      initResources();
      int rris;
      try {
         rris=Integer.parseInt(
            ResourceManager.getLanguageDependentString(getRefreshingRateString()));
      } catch (Exception ex) {
         rris=50;
      }
      refreshingRateInSeconds=new RefreshingRate(frame,rris);


      menubar = BarFactory.createMenubar(getMenubarResourceString(),commands);
      // adding menubar to the main panel
      add(menubar,BorderLayout.NORTH);
      // adding center component
      add(createCenterComponent(),BorderLayout.CENTER);

      myFrame.setBackground(Color.lightGray);
      myFrame.getContentPane().setLayout(new BorderLayout());
      myFrame.getContentPane().add(this,BorderLayout.CENTER);
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

      int xMinus=50, yMinus=100;
      myFrame.setBounds(xMinus/2, yMinus/2, screenSize.width - xMinus,
                        screenSize.height - yMinus);

      if (appIcon != null) myFrame.setIconImage(appIcon.getImage());
      myFrame.setTitle(appTitle+" - "+ResourceManager.getLanguageDependentString("NoneKey"));

      myFrame.setVisible(true);

      refresher.start();
      getAction(Utils.getUnqualifiedClassName(Connect.class)).actionPerformed(null);
      if (username==null) {
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

   protected void createActions () {
      defaultActions=new Action[] {
         new Connect(this),
            new Disconnect(this),
            new ShutdownEngine(this),
            new Refresh(this),
            new SetRefreshingRate(this),
            new Exit(this)
      };
      defaultActions[2].setEnabled(false);
   }

   public void setTitle () {
      myFrame.setTitle(appTitle+" - "+getFullUserName());
   }

   public String getFullUserName () {
      String name=ResourceManager.getLanguageDependentString("NoneKey");
      if (username!=null) {
         try {
            name=getUserGroupAmin().getUserRealName(username);
            if (name==null || name.trim().length()==0) {
               name=username;
            }
         } catch (Exception ex) {
         }
      }
      return name;
   }

   public JFrame getFrame () {
      return myFrame;
   }

   public SplashScreen getSplashScreen () {
      return splashScreen;
   }

   public static String getAppTitle () {
      return appTitle;
   }

   public static SharkConnection getService () {
      return workflowService;
   }

   public static void setService (SharkConnection wcs) {
      workflowService=wcs;
      if (wcs==null) {
         execAdmin=null;
         commonExpressionBuilder=null;
      }
   }

   public static String getUsername () {
      return username;
   }

   public static void setUsername (String username) {
      SharkClient.username=username;
   }

   public static void setPassword (String pwd) {
      SharkClient.pwd=pwd;
   }

   public void enableControls (boolean enable) {
      // enable/disable menubar
      for (int i=0; i<menubar.getComponentCount(); i++) {
         menubar.getComponent(i).setEnabled(enable);
      }
   }

   public RefreshingRate getRefreshingRate () {
      return refreshingRateInSeconds;
   }

   protected abstract String getAppTitleResourceString();
   protected abstract String getLogoResourceString();
   protected abstract String getIconResourceString();
   protected abstract String getMenubarResourceString();
   protected abstract String getRefreshingRateString();
   public abstract void clearComponents();

   public abstract void refresh (boolean mandatoryRefreshing);

}

