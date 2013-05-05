package org.enhydra.shark.swingclient.workflowadmin.monitoring.actions;

import java.awt.event.*;

import javax.swing.*;


import org.enhydra.shark.api.client.wfmodel.*;
import org.enhydra.jawe.xml.elements.*;
import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.monitoring.*;

/**
 * Resumes selected activity.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class ResumeActivity extends ActionBase {

   public ResumeActivity (ManageActivities ma) {
      super(ma);
   }

   public void actionPerformed(ActionEvent e) {
      ManageActivities ma=(ManageActivities)actionPanel;
      Activity act=ma.getSelectedActivity();
      try {
         WfActivity lastActivityForDefinition=ma.getLastActivityForDefinition(act,"open.not_running.suspended",0);
         lastActivityForDefinition.resume();
         ma.updateListDisplay(act);
         JOptionPane.showMessageDialog(ma.getWindow(),
            lastActivityForDefinition.name()+": "+
            ResourceManager.getLanguageDependentString(
            "MessageActivityIsResumed"),
            SharkClient.getAppTitle(),JOptionPane.WARNING_MESSAGE);
      } catch (Exception ex) {
         JOptionPane.showMessageDialog(ma.getWindow(),
            ResourceManager.getLanguageDependentString(
            "MessageActivityCannotBeResumed"),
            SharkClient.getAppTitle(),JOptionPane.WARNING_MESSAGE);

      }
   }

}

