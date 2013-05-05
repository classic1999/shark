package org.enhydra.shark.corbaclient.workflowadmin.definition.actions;



import java.awt.event.ActionEvent;
import org.enhydra.shark.corbaclient.ActionBase;
import org.enhydra.shark.corbaclient.ResourceManager;
import org.enhydra.shark.corbaclient.SplashScreen;
import org.enhydra.shark.corbaclient.workflowadmin.SharkAdmin;
import org.enhydra.shark.corbaclient.workflowadmin.definition.PackageManagement;

/**
 * Synchronizes engine's XPDL cache.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class SynchronizePackageCache extends ActionBase {

   public SynchronizePackageCache(PackageManagement pdm) {
      super(pdm);
   }

   public void actionPerformed(ActionEvent e) {
      PackageManagement pdm=(PackageManagement)actionPanel;
      SplashScreen ss=pdm.getWorkflowAdmin().getSplashScreen();
      ss.show("WaitImage",
            pdm.getWorkflowAdmin().getFullUserName(),
            ResourceManager.getLanguageDependentString("MessagePleaseWait"));
      try {
         SharkAdmin.getPackageAmin().synchronizeXPDLCache();
      } catch (Exception ex) {
         ex.printStackTrace();
      }
      ss.hide();
   }

}
