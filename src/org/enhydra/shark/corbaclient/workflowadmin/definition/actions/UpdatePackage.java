package org.enhydra.shark.corbaclient.workflowadmin.definition.actions;

import org.omg.WfBase.*;
import org.enhydra.shark.corba.WorkflowService.*;

import java.awt.event.*;
import javax.swing.*;
import java.util.*;

import org.enhydra.shark.corbaclient.*;
import org.enhydra.shark.corbaclient.workflowadmin.*;
import org.enhydra.shark.corbaclient.workflowadmin.definition.*;

/**
 * Updates selected package from external repository.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class UpdatePackage extends ActionBase {

   public UpdatePackage (PackageManagement pdm) {
      super(pdm);
   }

   public void actionPerformed(ActionEvent e) {
      PackageManagement pdm=(PackageManagement)actionPanel;
      SharkAdmin workflowAdmin=pdm.getWorkflowAdmin();

      // check if package container is referenced by some other package
      String selPkgId=pdm.getSelectedPackageId();
      if (selPkgId==null) return;
      try {
         boolean ipr=SharkAdmin.getPackageAmin().isPackageReferenced(selPkgId);

         if (ipr) {
            String msg=ResourceManager.getLanguageDependentString("MessagePackageIsReferencedDoYouWantToProceed");
            int r=JOptionPane.showConfirmDialog(pdm.getWindow(),msg,
                  workflowAdmin.getAppTitle(),JOptionPane.YES_NO_OPTION);
            if (r==JOptionPane.NO_OPTION) return;
         }
      } catch (Exception be) {
         String errMsg=ResourceManager.getLanguageDependentString("ErrorTheUpdateOfSelectedPackageIsNotAllowedAtTheMoment");
         JOptionPane.showMessageDialog(workflowAdmin.getFrame(),errMsg,
               workflowAdmin.getAppTitle(),JOptionPane.ERROR_MESSAGE);
         return;
      }

      // Filter the list of posible package files to display only the ones
      // with the same id as the one user want to update
      List allPackages=new ArrayList();
      try {
         NameValue[] allPkgs=SharkAdmin.getRepositoryManager().getPackagePathToIdMapping();
         if (allPkgs!=null && allPkgs.length>0) {
            for (int i=0; i<allPkgs.length; i++) {
               if (allPkgs[i].the_value.extract_wstring().equals(selPkgId)) {
                  allPackages.add(WorkflowUtilities.convertNameValueToNameValueString(allPkgs[i]));
               }
            }
         }
      } catch (Exception ex) {
         String errMsg=ResourceManager.getLanguageDependentString("ErrorTheUpdateOfSelectedPackageIsNotAllowedAtTheMoment");
         JOptionPane.showMessageDialog(workflowAdmin.getFrame(),errMsg,
               workflowAdmin.getAppTitle(),JOptionPane.ERROR_MESSAGE);
         return;
      }

      SelectPackage spd=new SelectPackage (this,pdm.getWindow(),allPackages);
      spd.showDialog();
   }

   public void updatePackage (String pkgId,String newPkgFilename) {

      PackageManagement pdm=(PackageManagement)actionPanel;
      SharkAdmin workflowAdmin=pdm.getWorkflowAdmin();
      workflowAdmin.stopRefresher();
      // wait if admin is in the process of refreshing
      while (workflowAdmin.isRefresherActive());
      String selPkgId=pdm.getSelectedPackageId();
      if (selPkgId!=null) {
         SplashScreen ss=workflowAdmin.getSplashScreen();
         ss.show("WaitImage",
               workflowAdmin.getFullUserName(),
               ResourceManager.getLanguageDependentString("MessagePleaseWait"));

         String errMsg=null;
         String xpdlReport="Something went wrong";
         try {
            try {
               SharkAdmin.getPackageAmin().updatePackage1(pkgId,newPkgFilename);
            } catch (BaseException be){
               throw new PackageUpdateNotAllowed();
            }
         } catch (PackageInvalid ipe) {
            errMsg=ResourceManager.getLanguageDependentString("ErrorTheUpdaterPackageIsInvalid");
            xpdlReport=ipe.XPDLValidationErrors;
         } catch (PackageUpdateNotAllowed puna) {
            errMsg=ResourceManager.getLanguageDependentString("ErrorTheUpdateOfSelectedPackageIsNotAllowedAtTheMoment");
         } catch (Exception ex) {
            errMsg=ResourceManager.getLanguageDependentString("ErrorTheUpdateOfSelectedPackageFailed");
         }
         workflowAdmin.refreshAfterUpdate();
         workflowAdmin.requestFocus();
         ss.hide();

         if (errMsg!=null) {
            JOptionPane.showMessageDialog(workflowAdmin.getFrame(),errMsg,
                  workflowAdmin.getAppTitle(),JOptionPane.ERROR_MESSAGE);
            pdm.getWorkflowAdmin().showXPDLErrorsReport(xpdlReport);
         }
      }
      workflowAdmin.startRefresher();
   }

}

