package org.enhydra.shark.swingclient.workflowadmin.monitoring.actions;

import java.awt.event.ActionEvent;
import org.enhydra.shark.api.client.wfmodel.WfProcess;
import org.enhydra.shark.swingclient.ActionBase;
import org.enhydra.shark.swingclient.workflowadmin.SharkAdmin;
import org.enhydra.shark.swingclient.workflowadmin.monitoring.ProcessMonitor;

/**
 * Checks limits for selected process and its activities (if it is not finished),
 * or for all running processes and activities if process is not selected.
 *
 * @author Sasa Bojanic
 */
public class CheckLimits extends ActionBase {

   public CheckLimits (ProcessMonitor pm) {
      super(pm);
   }

   public void actionPerformed(ActionEvent e) {
      ProcessMonitor pm=(ProcessMonitor)actionPanel;
      SharkAdmin workflowAdmin=pm.getWorkflowAdmin();

      WfProcess proc=pm.getProcessViewer().getCurrentProcess();

      try {
         if (proc!=null) {
            workflowAdmin.getLimitAdmin().checkLimits(proc.key());
         } else {
            workflowAdmin.getLimitAdmin().checkLimits();
         }
      } catch (Exception ex){
         ex.printStackTrace();
      }
   }
}
