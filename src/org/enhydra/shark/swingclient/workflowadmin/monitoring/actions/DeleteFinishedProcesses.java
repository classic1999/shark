package org.enhydra.shark.swingclient.workflowadmin.monitoring.actions;

import java.awt.event.ActionEvent;

import org.enhydra.shark.swingclient.ActionBase;
import org.enhydra.shark.swingclient.workflowadmin.SharkAdmin;
import org.enhydra.shark.swingclient.workflowadmin.monitoring.ProcessMonitor;

/**
 * Deletes finished processes from DB.
 *
 * @author Sasa Bojanic
 */
public class DeleteFinishedProcesses extends ActionBase {

   public DeleteFinishedProcesses (ProcessMonitor pm) {
      super(pm);
   }

   public void actionPerformed(ActionEvent e) {
      ProcessMonitor pm=(ProcessMonitor)actionPanel;
      SharkAdmin workflowAdmin=pm.getWorkflowAdmin();
      try {
         String[] failed = SharkAdmin.getExecAmin().deleteClosedProcesses(5, 12);
         for (int i = 0; i < failed.length; i++) {
            SharkAdmin.getExecAmin().deleteClosedProcess(failed[i]);
         }
         workflowAdmin.getEngineTreeModel().clear();
         workflowAdmin.refresh(true);
      } catch (Exception ex){
         ex.printStackTrace();
      }
   }
}
