package org.enhydra.shark.swingclient.workflowadmin.definition.actions;



import org.enhydra.shark.api.client.wfservice.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

import org.enhydra.jawe.JaWE;
import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;
import org.enhydra.shark.swingclient.workflowadmin.definition.*;

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
         Map allPkgs=SharkAdmin.getRepositoryManager().getPackagePathToIdMapping();

         if (allPkgs!=null) {
            for (Iterator i=allPkgs.entrySet().iterator(); i.hasNext();) {
               Map.Entry me=(Map.Entry)i.next();
               if (!existingIds.contains(me.getValue().toString())) {
                  allPackages.add(new NameValueStringMap(me.getKey().toString(),me.getValue().toString()));
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

         SharkAdmin.getPackageAmin().openPackage(packageName);
         pdm.getWorkflowAdmin().refresh(true);
      } catch (PackageInvalid ipe) {
         errMsg=ResourceManager.getLanguageDependentString("ErrorThePackageIsInvalid");
         xpdlReport=ipe.getXPDLValidationErrors();
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
