package org.enhydra.shark.corbaclient.workflowadmin.report;

import java.awt.*;

import javax.swing.*;

import org.enhydra.shark.corbaclient.*;
import org.enhydra.shark.corbaclient.workflowadmin.*;

/**
 * Implements the user interface and program logic to manage the reports.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class ReportManagement extends ActionPanel {

   private JTabbedPane tabbedPane;
   private SharkAdmin workflowAdmin;

   private ProcessReportManagement processReportManagement;
   private UserReportManagement userReportManagement;
   private WorkitemReportManagement workitemReportManagement;

   public ReportManagement (SharkAdmin wa) {
      super();

      // it is important to create managements before calling init method
      this.workflowAdmin=wa;
      processReportManagement=new ProcessReportManagement(wa);
      userReportManagement=new UserReportManagement(wa);
      workitemReportManagement=new WorkitemReportManagement(wa);

      super.init();
   }

   protected void createActions () {}

   //************** CREATING TABBEDPANE *********************
   protected Component createCenterComponent () {
      tabbedPane=new JTabbedPane();
      tabbedPane.addTab(
         ResourceManager.getLanguageDependentString("ProcessKey"),
         processReportManagement);
      tabbedPane.addTab(
         ResourceManager.getLanguageDependentString("UserKey"),
         userReportManagement);
      tabbedPane.addTab(ResourceManager.getLanguageDependentString("WorkitemKey"),
         workitemReportManagement);
      return tabbedPane;
   }

   public void clear () {
      tabbedPane.setSelectedIndex(0);
      //processReportManagement.clear();
      //userReportManagement.clear();
      //workitemReportManagement.clear();
   }

   public synchronized void refresh (boolean mandatoryRefreshing) {
      //processReportManagement.refresh(mandatoryRefreshing);
      //userReportManagement.refresh(mandatoryRefreshing);
      //workitemReportManagement.refresh(mandatoryRefreshing);
   }

   public ProcessReportManagement getProcessReportManagement () {
      return processReportManagement;
   }

   public UserReportManagement getUserReportManagement () {
      return userReportManagement;
   }

   public WorkitemReportManagement getWorkitemReportManagement () {
      return workitemReportManagement;
   }

}
