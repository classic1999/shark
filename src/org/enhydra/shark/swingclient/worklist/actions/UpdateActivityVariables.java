package org.enhydra.shark.swingclient.worklist.actions;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Map;



import org.enhydra.shark.api.client.wfmodel.*;
import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.worklist.*;

/**
 * Implements the action of updating workitem variables. It get's the workitem
 * that corredponds to the selecte table row, and shows the dialog for the
 * update of variables.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class UpdateActivityVariables extends ActionBase {
   public UpdateActivityVariables (Worklist worklist) {
      super(worklist);
   }

   public void actionPerformed(ActionEvent e) {
      Worklist worklist=(Worklist)actionPanel;
      WfAssignment ass=worklist.getSelectedAssignment();
      if (ass!=null) {
         // check if the workitem is accepted
         try {
            boolean accepted=ass.get_accepted_status();
            if (!accepted) {
               JOptionPane.showMessageDialog(worklist.getWindow(),
                                             ResourceManager.getLanguageDependentString(
                                                "WarningTheWorkitemMustBeAcceptedBeforeVariableUpdate"),
                                             SharkClient.getAppTitle(),JOptionPane.WARNING_MESSAGE);
               return;
            }

            WfActivity wa;
            wa=ass.activity();

            // check the context of activity
            String waKey=wa.key();
            Map actContext=null;
            if (worklist.isWorkitemContextUpdated(waKey)) {
               actContext=worklist.getWorkitemContext(waKey);
            } else {
               actContext=WorkflowUtilities.getActivityContext(wa.process_context(),
                                                               wa,WorkflowUtilities.VARIABLE_TO_PROCESS_ALL);
            }
            Map readOnlyContext=null;
            if (actContext!=null && actContext.size()>0) {
               readOnlyContext=WorkflowUtilities.getActivityContext(actContext,wa,
                                                                    WorkflowUtilities.VARIABLE_TO_PROCESS_VIEW);
            }
            Window w=worklist.getWindow();
            UpdateVariables upvd=new UpdateVariables(w,
                                                     ResourceManager.getLanguageDependentString("DialogUpdateProcessVariables"),
                                                     wa.container().key(),
                                                     actContext,
                                                     readOnlyContext);
            upvd.showDialog();
            if (actContext!=null && !upvd.isCanceled()) {
               worklist.putWorkitemContext(waKey,actContext);
               Map updContext=WorkflowUtilities.getActivityContext(actContext,
                                                                   wa,WorkflowUtilities.VARIABLE_TO_PROCESS_UPDATE);
               wa.set_process_context(updContext);
            }
         } catch (Exception ex) {}
      }
   }

}

