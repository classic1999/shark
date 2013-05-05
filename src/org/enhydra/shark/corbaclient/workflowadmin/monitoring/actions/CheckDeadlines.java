package org.enhydra.shark.corbaclient.workflowadmin.monitoring.actions;

import org.omg.WorkflowModel.WfProcess;
import java.awt.event.ActionEvent;
import org.enhydra.shark.corbaclient.ActionBase;
import org.enhydra.shark.corbaclient.workflowadmin.SharkAdmin;
import org.enhydra.shark.corbaclient.workflowadmin.monitoring.ProcessMonitor;

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
            SharkAdmin.getDeadlineAdmin().checkProcessDeadlines(proc.key());
         } else {
            String[] failed =  SharkAdmin.getDeadlineAdmin().checkDeadlinesMultiTrans(5,12);
            for (int i = 0; i < failed.length; i++) {
               SharkAdmin.getDeadlineAdmin().checkProcessDeadlines(failed[i]);
            }
         }
         workflowAdmin.refresh(true);
      } catch (Exception ex){
      }
   }
}
