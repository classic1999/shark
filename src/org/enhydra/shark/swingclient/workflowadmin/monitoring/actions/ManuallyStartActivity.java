package org.enhydra.shark.swingclient.workflowadmin.monitoring.actions;

import java.awt.event.*;


import org.enhydra.jawe.xml.elements.*;
import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;
import org.enhydra.shark.swingclient.workflowadmin.monitoring.*;

/**
 * Manually starts selected activity.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class ManuallyStartActivity extends ActionBase {

   public ManuallyStartActivity (ManageActivities ma) {
      super(ma);
   }

   public void actionPerformed(ActionEvent e) {
      ManageActivities ma=(ManageActivities)actionPanel;
      Activity selAct=ma.getSelectedActivity();
      if (selAct!=null) {
         try {
            SharkAdmin.getExecAmin().startActivity(ma.getProcess().key(),"",selAct.getID());
         } catch (Exception ex){}
         ma.updateListDisplay(selAct);
         ma.updateProcessView();
      }
   }

}

