package org.enhydra.shark.swingclient.workflowadmin.user;

import java.awt.*;

import javax.swing.*;

import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;

/**
 * Implements the user interface and program logic to manage the
 * users on the workflow server.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class UserManagement extends ActionPanel {

   private JTabbedPane tabbedPane;
   private SharkAdmin workflowAdmin;

   private UserAccountManagement userAccountManagement;
   private LoggedUsersManagement loggedUsersManagement;
   private UserMappingManagement userMappingManagement;

   public UserManagement (SharkAdmin wa) {
      super();

      // it is important to create managements before calling init method
      this.workflowAdmin=wa;
      userAccountManagement=new UserAccountManagement(wa);
      loggedUsersManagement=new LoggedUsersManagement(wa,userAccountManagement);
      userMappingManagement=new UserMappingManagement(wa,userAccountManagement);

      super.init();


   }

   protected void createActions () {}

   //************** CREATING TABBEDPANE *********************
   protected Component createCenterComponent () {
      tabbedPane=new JTabbedPane();
      tabbedPane.addTab(
         ResourceManager.getLanguageDependentString("AccountsKey"),
         userAccountManagement);
      tabbedPane.addTab(
         ResourceManager.getLanguageDependentString("LoggedKey"),
         loggedUsersManagement);
      tabbedPane.addTab(ResourceManager.getLanguageDependentString("MappingKey"),
         userMappingManagement);
      return tabbedPane;
   }

   public void clear () {
      tabbedPane.setSelectedIndex(0);
      userAccountManagement.clear();
      loggedUsersManagement.clear();
      userMappingManagement.clear();
   }

   public synchronized void refresh (boolean mandatoryRefreshing) {
      userAccountManagement.refresh(mandatoryRefreshing);
      loggedUsersManagement.refresh(mandatoryRefreshing);
      userMappingManagement.refresh(mandatoryRefreshing);
   }

   public UserAccountManagement getUserAccountManagement () {
      return userAccountManagement;
   }

   public LoggedUsersManagement getLoggedUsersManagement () {
      return loggedUsersManagement;
   }

   public UserMappingManagement getUserMappingManagement () {
      return userMappingManagement;
   }

}
