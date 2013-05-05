package org.enhydra.shark.swingclient.workflowadmin.definition.actions;

import java.awt.event.*;

import javax.swing.*;



import org.enhydra.shark.api.client.wfservice.*;
import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;
import org.enhydra.shark.swingclient.workflowadmin.definition.*;

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

         SharkAdmin.getPackageAmin().closePackage(id,ver);
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

