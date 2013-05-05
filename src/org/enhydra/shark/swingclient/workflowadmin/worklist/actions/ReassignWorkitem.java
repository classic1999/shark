package org.enhydra.shark.swingclient.workflowadmin.worklist.actions;

import java.awt.event.*;

import javax.swing.*;


import org.enhydra.shark.api.client.wfmodel.*;
import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;
import org.enhydra.shark.swingclient.workflowadmin.worklist.*;
import org.enhydra.shark.swingclient.worklist.*;

/**
 * Reassigns the selected workitem to different user.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class ReassignWorkitem extends ActionBase {

   public ReassignWorkitem (WorklistManagement wm) {
      super(wm);
   }


   public void actionPerformed(ActionEvent e) {
      WorklistManagement wm=(WorklistManagement)actionPanel;
      SharkAdmin workflowAdmin=wm.getWorkflowAdmin();
      Worklist worklist=wm.getWorklist();

      WfAssignment ass=worklist.getSelectedAssignment();
      if (ass!=null) {
         Reassignment r=new Reassignment(workflowAdmin.getFrame(),ass,
               workflowAdmin.getUserManagement().getUserAccountManagement().
                  getExistingUsers());
         r.showDialog();
         worklist.refresh();
      }
   }
}

