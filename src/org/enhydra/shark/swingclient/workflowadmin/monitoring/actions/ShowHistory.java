package org.enhydra.shark.swingclient.workflowadmin.monitoring.actions;

import java.awt.event.*;



import org.enhydra.shark.api.client.wfmodel.*;
import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;
import org.enhydra.shark.swingclient.workflowadmin.monitoring.*;
import org.enhydra.jawe.xml.elements.*;

/**
 * Shows the history events for selected process.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class ShowHistory extends ActionBase {

   public ShowHistory (ProcessMonitor pm) {
      super(pm);
   }

   public void actionPerformed(ActionEvent e) {
      ProcessMonitor pm=(ProcessMonitor)actionPanel;
      SharkAdmin workflowAdmin=pm.getWorkflowAdmin();

      try {
         WfProcess proc=pm.getProcessViewer().getCurrentProcess();
         if (proc!=null) {
            org.enhydra.jawe.xml.elements.Package pkg=
                  pm.getProcessViewer().getCurrentPackage();
            WorkflowProcesses wps=(WorkflowProcesses)pkg.get("WorkflowProcesses");
            String pdId="";
            try {pdId=SharkAdmin.getAdminMiscUtilities().getProcessDefinitionId(proc.key());}catch (Exception ex){}
            WorkflowProcess wp=wps.getWorkflowProcess(pdId);
            HistoryTable ht=
                  new HistoryTable(pm,proc,wp);
            ht.showDialog();
         }
      } catch (Exception ex){}
   }
}
