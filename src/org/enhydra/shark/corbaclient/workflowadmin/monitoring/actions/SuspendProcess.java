package org.enhydra.shark.corbaclient.workflowadmin.monitoring.actions;

import java.awt.event.*;


import org.omg.WorkflowModel.*;
import org.enhydra.shark.corbaclient.*;
import org.enhydra.shark.corbaclient.workflowadmin.*;
import org.enhydra.shark.corbaclient.workflowadmin.monitoring.*;

/**
 * Suspend selected process.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class SuspendProcess extends ActionBase {

   public SuspendProcess (ProcessMonitor pm) {
      super(pm);
   }

   public void actionPerformed(ActionEvent e) {
      ProcessMonitor pm=(ProcessMonitor)actionPanel;
      SharkAdmin workflowAdmin=pm.getWorkflowAdmin();

      try {
         WfProcess proc=pm.getProcessViewer().getCurrentProcess();
         if (proc!=null) {
            try{
               proc.suspend();
            } catch (Exception ex) {}
            workflowAdmin.refresh(true);
         }
      } catch (Exception ex){}
   }
}
