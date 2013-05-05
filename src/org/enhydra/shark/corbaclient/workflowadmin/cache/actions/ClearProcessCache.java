package org.enhydra.shark.corbaclient.workflowadmin.cache.actions;

import java.awt.event.ActionEvent;
import org.enhydra.shark.corbaclient.ActionBase;
import org.enhydra.shark.corbaclient.workflowadmin.SharkAdmin;
import org.enhydra.shark.corbaclient.workflowadmin.cache.CacheManagement;

/**
 * Empties shark's process cache.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class ClearProcessCache extends ActionBase {

   public ClearProcessCache (CacheManagement cm) {
      super(cm);
   }

   public void actionPerformed(ActionEvent e) {
      try {
         CacheManagement cm=(CacheManagement)actionPanel;
         SharkAdmin.getCacheAdmin().clearProcessCache();
         cm.refresh(true);
      } catch (Exception ex){
      }
   }
}
