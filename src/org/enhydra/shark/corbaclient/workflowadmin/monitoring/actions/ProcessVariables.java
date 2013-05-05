package org.enhydra.shark.corbaclient.workflowadmin.monitoring.actions;

import java.awt.event.*;


import org.omg.WfBase.*;
import org.omg.WorkflowModel.*;
import org.enhydra.shark.corbaclient.*;
import org.enhydra.shark.corbaclient.workflowadmin.*;
import org.enhydra.shark.corbaclient.workflowadmin.monitoring.*;

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
            NameValue[] procContext=proc.process_context();
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
