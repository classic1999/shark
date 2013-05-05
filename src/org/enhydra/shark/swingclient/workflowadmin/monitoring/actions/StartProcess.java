package org.enhydra.shark.swingclient.workflowadmin.monitoring.actions;

import java.awt.event.*;


import org.enhydra.shark.api.client.wfmodel.*;
import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;
import org.enhydra.shark.swingclient.workflowadmin.monitoring.*;

/**
 * Starts selected process.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class StartProcess extends ActionBase {

   public StartProcess (ProcessMonitor pm) {
      super(pm);
   }

   public void actionPerformed(ActionEvent e) {
      ProcessMonitor pm=(ProcessMonitor)actionPanel;
      SharkAdmin workflowAdmin=pm.getWorkflowAdmin();

      try {
         WfProcess proc=pm.getProcessViewer().getCurrentProcess();
         if (proc!=null) {
            try{
               proc.start();
            } catch (Exception ex) {}
            workflowAdmin.refresh(true);
         }
      } catch (Exception ex){}
   }
}
