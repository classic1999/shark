package org.enhydra.shark.corbaclient.workflowadmin.user.actions;

import java.awt.event.*;

import javax.swing.*;

import org.omg.WfBase.*;
import org.enhydra.shark.corba.WorkflowService.*;
import org.enhydra.shark.corbaclient.*;
import org.enhydra.shark.corbaclient.workflowadmin.*;
import org.enhydra.shark.corbaclient.workflowadmin.user.*;

/**
 * Creates new user account.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class CreateUserAccount extends ActionBase {

   public CreateUserAccount (UserAccountManagement uam) {
      super(uam);
   }

   public void actionPerformed(ActionEvent e) {
      UserGroupAdministration uga=SharkAdmin.getUserGroupAmin();

      SharkAdmin workflowAdmin=((UserAccountManagement)actionPanel).getWorkflowAdmin();
      UserAccountSettings uas=new UserAccountSettings();
      UserAccount uad=null;
      try {
         uad=new UserAccount(workflowAdmin.getFrame(),uas,UserAccount.CREATE_NEW);
         uad.showDialog();
      } catch (Exception ex) {
         System.out.println("Cannot create dialog of given type");
         return;
      }
      if (uas.username==null || uas.password==null) {
         return;
      }
      try {
         if (!uga.doesGroupExist(uas.groupname)) {
            uga.createGroup(uas.groupname,"");
         }
         uga.createUser(uas.groupname,uas.username,uas.password,
            uas.firstname,uas.lastname,uas.email);
         workflowAdmin.refresh(true);
      } catch (Exception ex) {
         JOptionPane.showMessageDialog(workflowAdmin.getFrame(),
            ResourceManager.getLanguageDependentString("MessageUsernameAlreadyExistsOrAccountCannotBeCreatedAtTheMoment"),
            workflowAdmin.getAppTitle(),JOptionPane.INFORMATION_MESSAGE);
      }
   }
}

