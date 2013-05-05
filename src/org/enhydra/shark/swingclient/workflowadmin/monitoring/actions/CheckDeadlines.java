package org.enhydra.shark.swingclient.workflowadmin.monitoring.actions;

import java.awt.event.ActionEvent;
import org.enhydra.shark.api.client.wfmodel.WfProcess;
import org.enhydra.shark.swingclient.ActionBase;
import org.enhydra.shark.swingclient.workflowadmin.SharkAdmin;
import org.enhydra.shark.swingclient.workflowadmin.monitoring.ProcessMonitor;

/**
 * Checks deadlines for selected process (if it is not finished), or for all running
 * processes if process is not selected.
 *
 * @author Sasa Bojanic
 */
public class CheckDeadlines extends ActionBase {

   public CheckDeadlines (ProcessMonitor pm) {
      super(pm);
   }

   public void actionPerformed(ActionEvent e) {
      ProcessMonitor pm=(ProcessMonitor)actionPanel;
      SharkAdmin workflowAdmin=pm.getWorkflowAdmin();

      WfProcess proc=pm.getProcessViewer().getCurrentProcess();

      try {
         if (proc!=null) {
            SharkAdmin.getDeadlineAdmin().checkDeadlines(proc.key());
         } else {
            String[] failed = SharkAdmin.getDeadlineAdmin().checkDeadlines(5, 12);
            for (int i = 0; i < failed.length; i++) {
               SharkAdmin.getDeadlineAdmin().checkDeadlines(failed[i]);
            }
         }
         workflowAdmin.refresh(true);
      } catch (Exception ex){
      }
   }
}
