package org.enhydra.shark.corbaclient.workflowadmin.repository.actions;

import java.awt.event.*;

import javax.swing.*;

import org.omg.WfBase.*;
import org.enhydra.shark.corba.WorkflowService.*;
import org.enhydra.shark.corbaclient.*;
import org.enhydra.shark.corbaclient.workflowadmin.*;
import org.enhydra.shark.corbaclient.workflowadmin.repository.*;

/**
 * Deletes package from the engine's repository.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class DeletePackage extends ActionBase {

   public DeletePackage(RepositoryManagement rpm) {
      super(rpm);
   }

   public void actionPerformed(ActionEvent e) {
      int r=JOptionPane.showConfirmDialog(actionPanel.getWindow(),
            ResourceManager.
               getLanguageDependentString("QuestionDoYouReallyWantToDeleteThePackageFileFromTheRepository"),
            SharkClient.getAppTitle(),JOptionPane.YES_NO_OPTION);
      if (r==JOptionPane.NO_OPTION) {
         return;
      }

      RepositoryManagement rpm=(RepositoryManagement)actionPanel;
      SharkAdmin workflowAdmin=rpm.getWorkflowAdmin();
      try {
         String path=rpm.getSelectedPackagePath();

         SharkClient.getRepositoryManager().deletePkg(path);
         rpm.refresh(true);
      } catch (RepositoryInvalid ri){
         JOptionPane.showMessageDialog(workflowAdmin.getFrame(),
            ResourceManager.getLanguageDependentString("ErrorNotConnectedToTheServer"),
            workflowAdmin.getAppTitle(),JOptionPane.ERROR_MESSAGE);
         workflowAdmin.showXPDLErrorsReport(ri.XPDLValidationErrors);
      } catch (Exception be){
         JOptionPane.showMessageDialog(workflowAdmin.getFrame(),
            ResourceManager.getLanguageDependentString("ErrorTheSelectedPackageCannotBeDeletedAtTheMoment"),
            workflowAdmin.getAppTitle(),JOptionPane.ERROR_MESSAGE);
      }
   }
}


