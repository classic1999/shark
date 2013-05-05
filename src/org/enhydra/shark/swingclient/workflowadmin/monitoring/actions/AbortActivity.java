package org.enhydra.shark.swingclient.workflowadmin.monitoring.actions;

import java.awt.event.*;

import javax.swing.*;


import org.enhydra.shark.api.client.wfmodel.*;
import org.enhydra.jawe.xml.elements.*;
import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.monitoring.*;

/**
 * Aborts selected activity.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class AbortActivity extends ActionBase {

   public AbortActivity (ManageActivities ma) {
      super(ma);
   }

   public void actionPerformed(ActionEvent e) {
      ManageActivities ma=(ManageActivities)actionPanel;
      Activity act=ma.getSelectedActivity();
      try {
         WfActivity lastActivityForDefinition=ma.getLastActivityForDefinition(act,"open",1);
         lastActivityForDefinition.abort();
         ma.updateListDisplay(act);
         ma.updateProcessView();
         JOptionPane.showMessageDialog(ma.getWindow(),
            lastActivityForDefinition.name()+": "+
            ResourceManager.getLanguageDependentString(
            "MessageActivityIsAborted"),
            SharkClient.getAppTitle(),JOptionPane.WARNING_MESSAGE);
         ma.updateProcessView();
      } catch (Exception ex) {
         JOptionPane.showMessageDialog(ma.getWindow(),
            ResourceManager.getLanguageDependentString(
            "MessageActivityCannotBeAborted"),
            SharkClient.getAppTitle(),JOptionPane.WARNING_MESSAGE);
      }
   }

}

