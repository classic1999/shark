package org.enhydra.shark.swingclient.workflowadmin.monitoring.actions;

import java.awt.event.*;



import org.enhydra.shark.api.client.wfmodel.*;
import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;
import org.enhydra.shark.swingclient.workflowadmin.monitoring.*;

/**
 * Shows a description of the selected process.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class ProcessDescription extends ActionBase {

   public ProcessDescription (ProcessMonitor pm) {
      super(pm);
   }

   public void actionPerformed(ActionEvent e) {
      ProcessMonitor pm=(ProcessMonitor)actionPanel;
      SharkAdmin workflowAdmin=pm.getWorkflowAdmin();

      WfProcess proc=pm.getProcessViewer().getCurrentProcess();
      if (proc!=null) {
         try {
            String desc=ResourceManager.getLanguageDependentString("DescriptionKey");
            ItemView iv=new ItemView(workflowAdmin.getFrame(),
               desc+" - "+proc.name(),
               desc,
               proc.description());
            iv.showDialog();
         } catch (Exception ex) {}
      }
   }

}

