package org.enhydra.shark.swingclient.workflowadmin.repository.actions;

import java.awt.event.*;

import javax.swing.*;



import org.enhydra.shark.api.client.wfservice.*;
import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;
import org.enhydra.shark.swingclient.workflowadmin.repository.*;

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

         SharkClient.getRepositoryManager().deletePackage(path);
         rpm.refresh(true);
      } catch (RepositoryInvalid ri){
         JOptionPane.showMessageDialog(workflowAdmin.getFrame(),
            ResourceManager.getLanguageDependentString("ErrorNotConnectedToTheServer"),
            workflowAdmin.getAppTitle(),JOptionPane.ERROR_MESSAGE);
         workflowAdmin.showXPDLErrorsReport(ri.getXPDLValidationErrors());
      } catch (Exception be){
         JOptionPane.showMessageDialog(workflowAdmin.getFrame(),
            ResourceManager.getLanguageDependentString("ErrorTheSelectedPackageCannotBeDeletedAtTheMoment"),
            workflowAdmin.getAppTitle(),JOptionPane.ERROR_MESSAGE);
       }
   }
}

