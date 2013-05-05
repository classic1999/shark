package org.enhydra.shark.swingclient.workflowadmin.monitoring.actions;

import java.awt.event.*;
import java.util.Map;



import org.enhydra.shark.api.client.wfmodel.*;
import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;
import org.enhydra.shark.swingclient.workflowadmin.monitoring.*;

/**
 * Shows a dialog for editing selected process variables.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class ProcessVariables extends ActionBase {

   public ProcessVariables (ProcessMonitor pm) {
      super(pm);
   }
   public void actionPerformed(ActionEvent e) {
      ProcessMonitor pm=(ProcessMonitor)actionPanel;
      SharkAdmin workflowAdmin=pm.getWorkflowAdmin();

      WfProcess proc=pm.getProcessViewer().getCurrentProcess();
      if (proc!=null) {
         try {
            Map procContext=proc.process_context();
            UpdateVariables upvd=
               new UpdateVariables(
               workflowAdmin.getFrame(),
               ResourceManager.getLanguageDependentString("DialogUpdateProcessVariables"),
               proc.key(),
               procContext,
               null);
            upvd.showDialog();
            if (procContext!=null) {
               proc.set_process_context(procContext);
            }
         } catch (Exception ex) {}
      }
   }

}
