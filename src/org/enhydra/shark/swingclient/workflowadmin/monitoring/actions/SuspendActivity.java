package org.enhydra.shark.swingclient.workflowadmin.monitoring.actions;

import java.awt.event.*;



import org.enhydra.shark.api.client.wfmodel.*;
import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;
import org.enhydra.shark.swingclient.workflowadmin.monitoring.*;

import javax.swing.*;
import org.enhydra.jawe.xml.elements.*;

/**
 * Suspends selected activity.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class SuspendActivity extends ActionBase {

   public SuspendActivity (ManageActivities ma) {
      super(ma);
   }

   public void actionPerformed(ActionEvent e) {
      ManageActivities ma=(ManageActivities)actionPanel;
      Activity act=ma.getSelectedActivity();
      try {
         WfActivity lastActivityForDefinition=ma.getLastActivityForDefinition(act,"open",1);
         lastActivityForDefinition.suspend();
         ma.updateListDisplay(act);
         JOptionPane.showMessageDialog(ma.getWindow(),
            lastActivityForDefinition.name()+": "+
            ResourceManager.getLanguageDependentString(
            "MessageActivityIsSuspended"),
            SharkClient.getAppTitle(),JOptionPane.WARNING_MESSAGE);
      } catch (Exception ex) {
         JOptionPane.showMessageDialog(ma.getWindow(),
            ResourceManager.getLanguageDependentString(
            "MessageActivityCannotBeSuspended"),
            SharkClient.getAppTitle(),JOptionPane.WARNING_MESSAGE);

      }
   }

}

