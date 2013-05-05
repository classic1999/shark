package org.enhydra.shark.corbaclient.workflowadmin.definition.actions;

import java.awt.event.*;

import javax.swing.*;

import org.omg.WfBase.*;
import org.enhydra.shark.corba.WorkflowService.*;
import org.enhydra.shark.corbaclient.*;
import org.enhydra.shark.corbaclient.workflowadmin.*;
import org.enhydra.shark.corbaclient.workflowadmin.definition.*;

/**
 * Removes package imported in engine.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class UnloadPackage extends ActionBase {

   public UnloadPackage(PackageManagement pdm) {
      super(pdm);
   }

   public void actionPerformed(ActionEvent e) {
      PackageManagement pdm=(PackageManagement)actionPanel;
      SharkAdmin workflowAdmin=pdm.getWorkflowAdmin();
      try {
         String id=pdm.getSelectedPackageId();
         String ver=pdm.getSelectedPackageVersion();
//System.out.println("The package to rem="+xmlFile);

         SharkAdmin.getPackageAmin().closePkgWithVersion(id,ver);
         workflowAdmin.refresh(true);
      } catch (PackageHasActiveProcesses nc){
         JOptionPane.showMessageDialog(workflowAdmin.getFrame(),
            ResourceManager.getLanguageDependentString("ErrorTheSelectedPackageCannotBeUnloadedAtTheMoment"),
            workflowAdmin.getAppTitle(),JOptionPane.ERROR_MESSAGE);
      } catch (PackageInUse piu) {
         JOptionPane.showMessageDialog(workflowAdmin.getFrame(),
            ResourceManager.getLanguageDependentString("ErrorTheSelectedPackageCannotBeUnloadedAtTheMoment"),
            workflowAdmin.getAppTitle(),JOptionPane.ERROR_MESSAGE);
      } catch (Exception be) {
         JOptionPane.showMessageDialog(workflowAdmin.getFrame(),
            ResourceManager.getLanguageDependentString("ErrorTheSelectedPackageCannotBeUnloadedAtTheMoment"),
            workflowAdmin.getAppTitle(),JOptionPane.ERROR_MESSAGE);
      }
   }
}


