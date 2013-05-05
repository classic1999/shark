package org.enhydra.shark.corbaclient.worklist.actions;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.omg.WfBase.*;
import org.omg.WorkflowModel.*;
import org.enhydra.shark.corbaclient.*;
import org.enhydra.shark.corbaclient.worklist.*;

/**
 * Implements the action of completing the workitem. It get's the workitem
 * that corredponds to the selecte table row, checks if it's variables are
 * updated, and signals to the workitem that it is completed.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class CompleteWorkitem extends ActionBase {

   public CompleteWorkitem (Worklist worklist) {
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
               JOptionPane.showMessageDialog(actionPanel.getWindow(),
                                             ResourceManager.getLanguageDependentString(
                                                "WarningTheWorkitemMustBeAcceptedBeforeExecution"),
                                             SharkClient.getAppTitle(),JOptionPane.WARNING_MESSAGE);
               return;
            }

            WfActivity wa=ass.activity();
            String waKey=wa.key();
            // set the result of activity
            if (!worklist.isWorkitemContextUpdated(waKey)) {
               NameValue[] actContext=WorkflowUtilities.getActivityContext(
                  wa.process_context(),wa,WorkflowUtilities.VARIABLE_TO_PROCESS_ALL);

               if (actContext!=null && actContext.length>0) {
                  int updateVar=JOptionPane.showConfirmDialog(
                     worklist.getWindow(),
                     ResourceManager.getLanguageDependentString(
                                                                "MessageDoYouWantToUpdateProcessVariables"),
                     ResourceManager.getLanguageDependentString("WorkitemKey")+" - "+
                        wa.name(),
                     JOptionPane.YES_NO_OPTION,
                     JOptionPane.QUESTION_MESSAGE);
                  if (updateVar==JOptionPane.YES_OPTION) {
                     Window w=worklist.getWindow();
                     NameValue[] readOnlyContext=WorkflowUtilities.getActivityContext(
                        actContext,wa,WorkflowUtilities.VARIABLE_TO_PROCESS_VIEW);
                     UpdateVariables upvd=new UpdateVariables(w,
                                                              ResourceManager.getLanguageDependentString("DialogUpdateProcessVariables"),
                                                              wa.container().key(),
                                                              actContext,
                                                              readOnlyContext);
                     upvd.showDialog();
                     if (actContext!=null) {
                        worklist.putWorkitemContext(waKey,actContext);
                     }
                  }
               }
            }
            if (worklist.isWorkitemContextUpdated(waKey)) {
               NameValue[] actContext=worklist.getWorkitemContext(waKey);
               NameValue[] updContext=WorkflowUtilities.getActivityContext(actContext,
                                                                           wa,WorkflowUtilities.VARIABLE_TO_PROCESS_UPDATE);
               wa.set_result(updContext);
            }
            wa.complete();
            worklist.refresh();
         } catch (CannotComplete cnc) {
            JOptionPane.showMessageDialog(worklist.getWindow(),
                                          ResourceManager.getLanguageDependentString(
                                             "WarningTheWorkitemCannotBeCompleted"),
                                          SharkClient.getAppTitle(),JOptionPane.WARNING_MESSAGE);

         } catch (Exception ex) {ex.printStackTrace();}
      }
   }

}

