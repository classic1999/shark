package org.enhydra.shark.swingclient.workflowadmin.user.actions;

import java.awt.event.*;
import java.util.*;



import org.enhydra.shark.api.client.wfservice.*;
import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;
import org.enhydra.shark.swingclient.workflowadmin.user.*;

import javax.swing.JOptionPane;

/**
 * Changes the user account settings.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class ChangeUserAccountSettings extends ActionBase {

   public ChangeUserAccountSettings (UserAccountManagement uam) {
      super(uam);
   }

   public void actionPerformed(ActionEvent e) {
      UserGroupAdministration uga=SharkAdmin.getUserGroupAmin();
      UserAccountManagement uam=(UserAccountManagement)actionPanel;
      SharkAdmin workflowAdmin=uam.getWorkflowAdmin();
      TablePanel eup=uam.getExistingUsersPanel();

      String selectedUsername=(String)eup.getColumnValueOfSelectedRow(0);

      try {
         UserAccountSettings uas=new UserAccountSettings();
         uas.username=selectedUsername;
         uas.firstname=(String)eup.getColumnValueOfSelectedRow(1);
         uas.lastname=(String)eup.getColumnValueOfSelectedRow(2);
         uas.email=(String)eup.getColumnValueOfSelectedRow(3);
         UserAccount uad=null;
         try {
            uad=new UserAccount(workflowAdmin.getFrame(),
                                uas,UserAccount.CHANGE_SETTINGS);
            uad.showDialog();
         } catch (Exception ex) {
            System.out.println("Cannot create dialog of given type");
            return;
         }
         if (uas.username==null) return;
         try {
            uga.updateUser(uas.username,uas.firstname,uas.lastname,uas.email);
            eup.setColumnValueOfSelectedRow(1,uas.firstname);
            eup.setColumnValueOfSelectedRow(2,uas.lastname);
            eup.setColumnValueOfSelectedRow(3,uas.email);
         } catch (Exception ex) {
            JOptionPane.showMessageDialog(workflowAdmin,
                                          ResourceManager.getLanguageDependentString("MessageCannotChangeUserSettings"),
                                          ResourceManager.getLanguageDependentString("WorkflowAdminTitle"),
                                          JOptionPane.ERROR_MESSAGE);
         }
      } catch (Exception ex) {}

   }
}
