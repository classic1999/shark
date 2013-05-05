package org.enhydra.shark.corbaclient.workflowadmin.definition.actions;

import org.omg.WfBase.*;
import org.enhydra.shark.corba.WorkflowService.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

import org.enhydra.jawe.JaWE;
import org.enhydra.shark.corbaclient.*;
import org.enhydra.shark.corbaclient.workflowadmin.*;
import org.enhydra.shark.corbaclient.workflowadmin.definition.*;

/**
 * Displays the dialog for loading the packages into the engine.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class LoadPackage extends ActionBase {

   public LoadPackage(PackageManagement pdm) {
      super(pdm);
   }

   public void actionPerformed(ActionEvent e) {
      PackageManagement pdm=(PackageManagement)actionPanel;
      SharkAdmin workflowAdmin=pdm.getWorkflowAdmin();

      // Filter the list of posible package files to display only the ones
      // with the ids different then the ones of already loaded packages.
      Collection existingIds=JaWE.getInstance().getXMLInterface().getAllPackageIds();
      java.util.List allPackages=new ArrayList();
      try {
         NameValue[] allPkgs=SharkAdmin.getRepositoryManager().getPackagePathToIdMapping();

         if (allPkgs!=null && allPkgs.length>0) {
            for (int i=0; i<allPkgs.length; i++) {
               if (!existingIds.contains(allPkgs[i].the_value.extract_wstring())) {
                  allPackages.add(WorkflowUtilities.convertNameValueToNameValueString(allPkgs[i]));
               }
            }
         }
      } catch (Exception ex) {}

      SelectPackage spd=new SelectPackage (this,pdm.getWindow(),allPackages);
      spd.showDialog();
   }

   public void loadPackage (String packageName) {
      PackageManagement pdm=(PackageManagement)actionPanel;
      SplashScreen ss=pdm.getWorkflowAdmin().getSplashScreen();
      ss.show("WaitImage",
            pdm.getWorkflowAdmin().getFullUserName(),
            ResourceManager.getLanguageDependentString("MessagePleaseWait"));
      String errMsg=null;
      String xpdlReport=null;
      try {
         SharkAdmin.getPackageAmin().openPkg(packageName);
         pdm.getWorkflowAdmin().refresh(true);
      } catch (PackageInvalid ipe) {
         errMsg=ResourceManager.getLanguageDependentString("ErrorThePackageIsInvalid");
         xpdlReport=ipe.XPDLValidationErrors;
      } catch (ExternalPackageInvalid iepe) {
         errMsg=ResourceManager.getLanguageDependentString("ErrorTheOneOfTheExternalPackagesIsInvalid");
      } catch (Exception ex) {
         errMsg=ResourceManager.getLanguageDependentString("ErrorThePackageCannotBeLoadedAtTheMoment");
         //ex.printStackTrace();
      }
      ss.hide();
      if (errMsg!=null) {
         JOptionPane.showMessageDialog(pdm.getWindow(),errMsg,
               SharkAdmin.getAppTitle(),JOptionPane.ERROR_MESSAGE);
         pdm.getWorkflowAdmin().showXPDLErrorsReport(xpdlReport);
      }

   }

}

