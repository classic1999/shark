package org.enhydra.shark.corbaclient.workflowadmin.worklist.actions;

import java.awt.event.*;

import javax.swing.*;

import org.omg.WorkflowModel.*;
import org.enhydra.shark.corbaclient.*;
import org.enhydra.shark.corbaclient.workflowadmin.*;
import org.enhydra.shark.corbaclient.workflowadmin.worklist.*;
import org.enhydra.shark.corbaclient.worklist.*;

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

