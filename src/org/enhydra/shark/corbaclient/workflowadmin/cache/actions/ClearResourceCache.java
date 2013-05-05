package org.enhydra.shark.corbaclient.workflowadmin.cache.actions;

import java.awt.event.ActionEvent;
import org.enhydra.shark.corbaclient.ActionBase;
import org.enhydra.shark.corbaclient.workflowadmin.SharkAdmin;
import org.enhydra.shark.corbaclient.workflowadmin.cache.CacheManagement;

/**
 * Empties shark's resource cache.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class ClearResourceCache extends ActionBase {

   public ClearResourceCache (CacheManagement cm) {
      super(cm);
   }

   public void actionPerformed(ActionEvent e) {
      try {
         CacheManagement cm=(CacheManagement)actionPanel;
         SharkAdmin.getCacheAdmin().clearResourceCache();
         cm.refresh(true);
      } catch (Exception ex){
      }
   }
}
