package org.enhydra.shark.corbaclient.workflowadmin.user.actions;

import java.awt.event.*;
import javax.swing.*;

import org.omg.WfBase.*;
import org.enhydra.shark.corbaclient.*;
import org.enhydra.shark.corbaclient.workflowadmin.*;
import org.enhydra.shark.corbaclient.workflowadmin.user.*;

/**
 * Removes user account.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class RemoveUserAccount extends ActionBase {

   public RemoveUserAccount (UserAccountManagement uam) {
      super(uam);
   }

   public void actionPerformed(ActionEvent e) {
      int r=JOptionPane.showConfirmDialog(actionPanel.getWindow(),
            ResourceManager.
               getLanguageDependentString("QuestionDoYouReallyWantToRemoveUserAccount"),
            SharkClient.getAppTitle(),JOptionPane.YES_NO_OPTION);
      if (r==JOptionPane.NO_OPTION) {
         return;
      }

      UserAccountManagement uam=(UserAccountManagement)actionPanel;
      SharkAdmin workflowAdmin=uam.getWorkflowAdmin();
      try {
         String username=(String)uam.getExistingUsersPanel().getColumnValueOfSelectedRow(0);

         SharkAdmin.getUserGroupAmin().removeUser(username);
         workflowAdmin.refresh(true);
      } catch (Exception ex) {
          JOptionPane.showMessageDialog(workflowAdmin.getFrame(),
            ResourceManager.getLanguageDependentString("MessageUserAcountCannotBeRemovedAtTheMoment"),
            workflowAdmin.getAppTitle(),JOptionPane.INFORMATION_MESSAGE);
      }
   }
}

