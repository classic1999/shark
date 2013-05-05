package org.enhydra.shark.swingclient.workflowadmin.monitoring.actions;

import java.awt.event.*;



import org.enhydra.shark.api.client.wfmodel.*;
import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;
import org.enhydra.shark.swingclient.workflowadmin.monitoring.*;

import javax.swing.JOptionPane;

/**
 * Resumes selected process.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class ResumeProcess extends ActionBase {

   public ResumeProcess (ProcessMonitor pm) {
      super(pm);
   }

   public void actionPerformed(ActionEvent e) {
      ProcessMonitor pm=(ProcessMonitor)actionPanel;
      SharkAdmin workflowAdmin=pm.getWorkflowAdmin();
      try {
         WfProcess proc=pm.getProcessViewer().getCurrentProcess();
         if (proc!=null) {
            try{
               proc.resume();
            } catch (CannotResume cr) {
               JOptionPane.showMessageDialog(pm.getWindow(),
                     ResourceManager.getLanguageDependentString(
                     "MessageProcessCannotBeResumed"),
                     SharkClient.getAppTitle(),JOptionPane.WARNING_MESSAGE);

            } catch (Exception ex) {
            }

            workflowAdmin.refresh(true);
         }
      } catch (Exception ex){}
   }
}
