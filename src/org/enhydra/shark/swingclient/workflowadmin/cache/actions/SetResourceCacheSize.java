package org.enhydra.shark.swingclient.workflowadmin.cache.actions;

import java.awt.event.ActionEvent;
import org.enhydra.shark.api.client.wfservice.CacheAdministration;
import org.enhydra.shark.swingclient.ActionBase;
import org.enhydra.shark.swingclient.workflowadmin.SharkAdmin;
import org.enhydra.shark.swingclient.workflowadmin.cache.CacheManagement;
import org.enhydra.shark.swingclient.workflowadmin.cache.CacheSize;

/**
 * Sets the resource cache size
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class SetResourceCacheSize extends ActionBase {

   public SetResourceCacheSize (CacheManagement cm) {
      super(cm);
   }

   public void actionPerformed(ActionEvent e) {
      try {
         CacheManagement cm=(CacheManagement)actionPanel;
         CacheAdministration ca=SharkAdmin.getCacheAdmin();
         CacheSize csd=new CacheSize(cm.getWindow(),ca.getResourceCacheSize());
         csd.showDialog();
         ca.setResourceCacheSize(csd.getCacheSize());
         cm.refresh(true);
      } catch (Exception ex) {}
   }
}
