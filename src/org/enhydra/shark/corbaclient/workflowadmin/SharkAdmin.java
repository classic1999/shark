package org.enhydra.shark.corbaclient.workflowadmin;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.Iterator;

import javax.swing.*;

import org.omg.WfBase.*;
import org.enhydra.shark.corbaclient.*;
import org.enhydra.shark.corbaclient.workflowadmin.actions.*;
import org.enhydra.shark.corbaclient.workflowadmin.definition.*;
import org.enhydra.shark.corbaclient.workflowadmin.instantiation.*;
import org.enhydra.shark.corbaclient.workflowadmin.monitoring.*;
import org.enhydra.shark.corbaclient.workflowadmin.application.*;
import org.enhydra.shark.corbaclient.workflowadmin.report.*;
import org.enhydra.shark.corbaclient.workflowadmin.repository.*;
import org.enhydra.shark.corbaclient.workflowadmin.user.*;
import org.enhydra.shark.corbaclient.workflowadmin.worklist.*;
import org.enhydra.shark.corbaclient.workflowadmin.cache.*;

import org.enhydra.jawe.xml.*;

/**
 * An implementation of client application that realizes
 * worklist administrator.
 * <p> The administrator is consisted of process definition management,
 * the process instantiation management, process monitoring and user
 * management.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class SharkAdmin extends SharkClient {

   private JTabbedPane tabbedPane;

   private EngineTreeModel engineTreeModel;
   private RepositoryManagement repositoryManagement;
   private PackageManagement packageManagement;
   private ProcessInstantiationManagement processInstantiationManagement;
   private ProcessMonitor processMonitor;
   private UserManagement userManagement;
   private ApplicationMappingManagement applicationMapping;
   private CacheManagement cacheManagement;
   private WorklistManagement worklistManagement;
   private ReportManagement reportManagement;

   private DeadlineChecker deadlineChecker;
   private LimitChecker limitChecker;

   private long deadlineCheckingTime=30000;
   private long limitCheckingTime=30000;

   //************************** CONSTRUCTOR ***************************
   /** Creates instance of main application class. */
   public SharkAdmin (JFrame frame) {
      super(frame);
   }
   //************************* END OF WORKFLOWADMIN CONSTRUCTOR ********

   protected void createActions () {
      super.createActions();
      Action[] newActions=new Action[defaultActions.length+3];
      System.arraycopy(defaultActions,0,newActions,0,defaultActions.length);
      newActions[defaultActions.length]=new AutomaticallyCheckDeadlines(this);
      newActions[defaultActions.length+1]=new AutomaticallyCheckLimits(this);
      newActions[defaultActions.length+2]=new ShowHideFinishedProcesses(this);
      defaultActions=newActions;
   }

   protected Component createCenterComponent () {
      engineTreeModel=new EngineTreeModel(this);
      repositoryManagement=new RepositoryManagement(this);
      packageManagement=new PackageManagement(this);
      processInstantiationManagement=new ProcessInstantiationManagement(this);
      processMonitor=new ProcessMonitor(this);
      userManagement=new UserManagement(this);
      applicationMapping=new ApplicationMappingManagement(this);
      cacheManagement=new CacheManagement(this);
      worklistManagement=new WorklistManagement(this);
      reportManagement=new ReportManagement(this);

      // creating tabbed panel
      tabbedPane=new JTabbedPane();
      tabbedPane.addTab(
         ResourceManager.getLanguageDependentString("RepositoryManagementKey"),
         repositoryManagement);
      tabbedPane.addTab(
         ResourceManager.getLanguageDependentString("PackageManagementKey"),
         packageManagement);
      tabbedPane.addTab(
         ResourceManager.getLanguageDependentString("ProcessInstantiationManagementKey"),
         processInstantiationManagement);
      tabbedPane.addTab(ResourceManager.getLanguageDependentString("ProcessMonitorKey"),processMonitor);
      tabbedPane.addTab(ResourceManager.getLanguageDependentString("UserManagementKey"),userManagement);
      tabbedPane.addTab(ResourceManager.getLanguageDependentString("ApplicationMappingKey"),applicationMapping);
      tabbedPane.addTab(ResourceManager.getLanguageDependentString("CacheManagementKey"),cacheManagement);
      tabbedPane.addTab(ResourceManager.getLanguageDependentString("WorklistManagementKey"),worklistManagement);
      //tabbedPane.addTab(ResourceManager.getLanguageDependentString("ReportManagementKey"),reportManagement);
      tabbedPane.setVisible(false);

      return tabbedPane;
   }

   /** The order of refreshment is important, first the
    * engine tree model has to be refreshed. Also, the
    * method must be synchronized because other instances
    * uses EngineTreeModel.getRemovedAfterRefresh() and
    * EngineTreeModel.getAddedAfterRefresh() methods
    */
   public synchronized void refresh (boolean mandatoryRefreshing) {
      tabbedPane.setVisible(true);
      engineTreeModel.refresh(mandatoryRefreshing);
      packageManagement.refresh(mandatoryRefreshing);
      processInstantiationManagement.refresh(mandatoryRefreshing);
      processMonitor.refresh(mandatoryRefreshing);
      repositoryManagement.refresh(mandatoryRefreshing);
      userManagement.refresh(mandatoryRefreshing);
      applicationMapping.refresh(mandatoryRefreshing);
      cacheManagement.refresh(mandatoryRefreshing);
      worklistManagement.refresh(mandatoryRefreshing);
      reportManagement.refresh(mandatoryRefreshing);
   }

   public void refreshAfterUpdate () {
      stopRefresher();
      // THIS IS PRECAUTION - the thread that refreshes admin can be
      // running, so we wait until the refresher thread finishs its
      // work, and then we clear the components
      while (isRefresherActive());
      engineTreeModel.clear();
      repositoryManagement.refresh(false);
      packageManagement.refresh(true);
      processInstantiationManagement.clear();
      processInstantiationManagement.refresh(true);
      processMonitor.clear();
      userManagement.clear();
      applicationMapping.clear();
      cacheManagement.clear();
      worklistManagement.clear();
      reportManagement.clear();
      startRefresher();
      refresh(true);
   }

   /** The order of clearing is important, first the
    * engine tree model has to be cleared.
    */
   public void clearComponents() {
      try {
         setTitle();
         tabbedPane.setVisible(false);
         tabbedPane.setSelectedIndex(0);
         stopRefresher();
         // THIS IS PRECAUTION - the thread that refreshes admin can be
         // running, so we wait until the refresher thread finishs its
         // work, and then we clear the components
         while (isRefresherActive());
         engineTreeModel.clear();
         repositoryManagement.clear();
         packageManagement.refresh(true);
         processInstantiationManagement.clear();
         processInstantiationManagement.refresh(true);
         processMonitor.clear();
         userManagement.clear();
         applicationMapping.clear();
         cacheManagement.clear();
         worklistManagement.clear();
         reportManagement.clear();
      } catch (Exception ex) {ex.printStackTrace();}
   }

   protected String getAppTitleResourceString() {
      return "WorkflowAdminTitle";
   }

   protected String getLogoResourceString() {
      return "workflowadminLogo";
   }

   protected String getIconResourceString() {
      return "workflowadminIcon";
   }
   protected String getMenubarResourceString() {
      return "adminmainmenubar";
   }

   protected String getRefreshingRateString () {
      return "RefreshingRateInSecondsForWorkflowAdmin";
   }

   public EngineTreeModel getEngineTreeModel() {
      return engineTreeModel;
   }

   public RepositoryManagement getRepositoryManagement () {
      return repositoryManagement;
   }

   public PackageManagement getPackageManagement () {
      return packageManagement;
   }

   public ProcessInstantiationManagement getProcessInstantiationManagement () {
      return processInstantiationManagement;
   }

   public ProcessMonitor getProcessMonitor () {
      return processMonitor;
   }

   public UserManagement getUserManagement () {
      return userManagement;
   }

   public ApplicationMappingManagement getApplicationMapping () {
      return applicationMapping;
   }

   public CacheManagement getCacheManagement () {
      return cacheManagement;
   }

   public WorklistManagement getWorklistManagement () {
      return worklistManagement;
   }

   public ReportManagement getReportManagement () {
      return reportManagement;
   }

   public void enableControls (boolean enable) {
      super.enableControls(enable);
      tabbedPane.setEnabled(enable);
   }

   public void showXPDLErrorsReport (String xpdlErrors) {
      try {
         String title=ResourceManager.getLanguageDependentString("XPDLErrorReportKey");
         final JDialog report=new JDialog(myFrame,title,true);
         JEditorPane text = new JEditorPane("text/html",xpdlErrors);
         text.setEditable(false);
         text.getCaret().setDot(0);
         JScrollPane sp = new JScrollPane(text);
         sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
         sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
         report.getContentPane().setLayout(new BorderLayout());
         report.getContentPane().add(sp);

         JPanel buttonPanel=new JPanel();
         buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
         buttonPanel.setAlignmentY(Component.TOP_ALIGNMENT);

         JButton buttonOK=new JButton(ResourceManager.getLanguageDependentString("OKKey"));
         java.net.URL u = ResourceManager.getResource("OKImage");
         if (u!=null) {
            buttonOK.setIcon(new ImageIcon(u));
         }
         buttonPanel.add(buttonOK);

         Container cp=report.getContentPane();
         cp.setLayout(new BorderLayout());
         cp.add(sp,BorderLayout.CENTER);
         cp.add(buttonPanel,BorderLayout.SOUTH);

         // action listener for confirming
         buttonOK.addActionListener(new ActionListener(){
                  public void actionPerformed( ActionEvent ae ){
                     report.dispose();
                  }
               });

         buttonOK.setDefaultCapable(true);
         report.getRootPane().setDefaultButton(buttonOK);


         report.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0,false),"Cancel");
         report.getRootPane().getActionMap().put("Cancel", new AbstractAction() {
                  public void actionPerformed(ActionEvent e) {
                     report.dispose();
                  }
               });

         org.enhydra.jawe.Utils.center(report,100,150);

         report.show();
      } catch (Exception ex) {ex.printStackTrace();}
   }

   public void showOrHideFinishedProcesses () {
      engineTreeModel.setShowFinishedProcesses(
         !engineTreeModel.getShowFinishedProcesses());
   }

   public void setAutomaticDeadlineCheck (boolean checkAutomatically) {
      if (deadlineChecker==null) {
         deadlineChecker=new DeadlineChecker(getDeadlineAdmin(),deadlineCheckingTime);
      }
      if (checkAutomatically) {
         deadlineChecker.startChecker();
      } else {
         deadlineChecker.stopChecker();
      }
   }

   public void setAutomaticLimitCheck (boolean checkAutomatically) {
      if (limitChecker==null) {
         limitChecker=new LimitChecker(getLimitAdmin(),limitCheckingTime);
      }
      if (checkAutomatically) {
         limitChecker.startChecker();
      } else {
         limitChecker.stopChecker();
      }
   }

   public void onDisconnectOrShutdown () {
      if (execAdmin==null) return;
      Iterator it=ProcessInstantiatorPOA.getInstantiators().iterator();
      while (it.hasNext()) {
         ProcessInstantiatorInterface pi=(ProcessInstantiatorInterface)it.next();
         pi.releaseProcesses();
      }
   }

   /** Main method. */
   public static void main(String[] args) throws Exception {
      // Checks if java version is >= 1.4
      String vers = System.getProperty("java.version");
      if (vers.compareTo("1.4") < 0) {
         System.out.println("!!!WARNING: Shark Workflow Administrator must be run with a " +
                               "1.4 or higher version VM!!!");
         System.exit(1);
      }
      SharkAdmin sa=new SharkAdmin(new JFrame());

      // initialize deadline timer
      if (args!=null && args.length>0) {
         sa.deadlineCheckingTime=Long.parseLong(args[0]);
      }

      // initialize limit timer
      String ldelay=null;
      if (args!=null && args.length>1) {
         sa.limitCheckingTime=Long.parseLong(args[1]);
      }
   }


}
