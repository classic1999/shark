package org.enhydra.shark.swingclient.workflowadmin.instantiation.actions;

import java.awt.event.*;


import org.enhydra.shark.api.client.wfmodel.*;

import org.enhydra.shark.api.client.wfservice.*;
import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;
import org.enhydra.shark.swingclient.workflowadmin.instantiation.*;
import org.enhydra.jawe.xml.elements.Package;

/**
 * Changes the state of the process manager (or all process managers for
 * some package) to enabled.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class EnableDefinition extends ActionBase {

   public EnableDefinition (ProcessInstantiationManagement pim) {
      super(pim);
   }

   public void actionPerformed(ActionEvent e) {
      ProcessInstantiationManagement pim=(ProcessInstantiationManagement)actionPanel;

      Object userObject=pim.getSelectedUserObject();
      if (userObject instanceof Package) {
         Package pkg=(Package)userObject;
         try {
            WfProcessMgrIterator wpmgi=SharkClient.getCommonExpressionBuilder().getEnabledOrDisabledManagersForPackage(pkg.get("Id").toString(),false);
            WfProcessMgr[] mgrs=wpmgi.get_next_n_sequence(0);
            if (mgrs!=null) {
               for (int i=0; i<mgrs.length; i++) {
                  try {
                     mgrs[i].set_process_mgr_state(process_mgr_stateType.enabled);
                  } catch (TransitionNotAllowed tna){
                  } catch (Exception ex){}
               }
            }
         } catch (Exception ex) {}
      } else if (userObject instanceof WfProcessMgr) {
         try {
            ((WfProcessMgr)userObject).set_process_mgr_state(process_mgr_stateType.enabled);
            pim.getTreeSelectionListener().valueChanged(null);
         } catch (TransitionNotAllowed tna){
         } catch (Exception ex){}
      }

   }
}
