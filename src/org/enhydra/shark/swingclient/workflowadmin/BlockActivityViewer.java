package org.enhydra.shark.swingclient.workflowadmin;

import java.awt.*;


import org.enhydra.shark.api.client.wfmodel.*;
import org.enhydra.jawe.*;
import org.enhydra.jawe.graph.BlockActivity;
import org.enhydra.jawe.xml.elements.*;
import org.enhydra.jawe.xml.elements.Activity;
import org.enhydra.shark.swingclient.*;

/**
* The panel that is used to display the BlockActivityGraph instances -
* the ActivitySets defined by the JaWE application.
*/
//TODO: Fix to always show properly currently active activities (when there
// are two activities with the same definition Id, one in the block and the
// second in it's nested block, and the activity in nested block is active
// (and one in the block is not), the activity in the block is selected
public class BlockActivityViewer extends ProcessViewer {
   private BlockActivity blockActivity;
   private org.enhydra.jawe.xml.elements.Package pkg;
   private WfProcessMgr wpm;
   private WfProcess wpr;

   private ActivitySet activitySet;

   public BlockActivityViewer (Window parent,BlockActivity ba,
         org.enhydra.jawe.xml.elements.Package pkg,
         WfProcessMgr wpm,WfProcess wpr) {
      super(parent);

      this.blockActivity=ba;
      this.pkg=pkg;
      currentProcessMgr=wpm;
      currentProcess=wpr;

      WorkflowProcesses wps=(WorkflowProcesses)pkg.get("WorkflowProcesses");
      String pdId="";
      try {pdId=SharkAdmin.getAdminMiscUtilities().getProcessMgrProcDefId(wpm.name());}catch (Exception ex) {}
      WorkflowProcess wp=wps.getWorkflowProcess(pdId);
      String baId=blockActivity.getBlockID();
      activitySet=((ActivitySets)wp.get("ActivitySets")).getActivitySet(baId);

      try {
         ProcessEditor pe=blockActivity.getImplementationEditor();
         if (pe!=null) {
            currentGraph=(ProcessGraph)pe.getGraph();
         }
         currentGraph.setEditable(false);
         currentGraph.setMoveable(false);
         ((ProcessEditor)currentGraph.getEditor()).setButtonsEnabled(false);
         graphScrollPane.setViewportView(currentGraph);
         updateSelection();
      } catch (Exception ex) {
         graphScrollPane.setViewportView(null);
      }

   }

   /** Sets the dialog title. */
   protected void setDialogTitle () {
      myDialog.setTitle(blockActivity.toString());
   }

   public void displayProcess (
      org.enhydra.jawe.xml.elements.Package pkg,
      WfProcessMgr wpm,WfProcess wpr) {
      updateSelection();
      return;
   }

   /**
   * Refreshes graph to select activities of current block activity insance that
   * are currently in the open.running state.
   */
   public void updateSelection () {
      if (currentGraph==null) return;
      currentGraph.clearSelection();
      if (currentProcess==null) return;

      try {
         WfActivityIterator wai=SharkClient.getCommonExpressionBuilder().getOpenActivities(currentProcess.key());
         WfActivity[] allRunningActs=wai.get_next_n_sequence(0);
         for (int i=0; i<allRunningActs.length; i++) {
            Activities acts=(Activities)activitySet.get("Activities");
            Activity act=acts.getActivity(
               SharkClient.getAdminMiscUtilities().getActivityDefinitionId(currentProcess.key(),
                  allRunningActs[i].key()));
            try {
               WorkflowManager wm=currentGraph.getWorkflowManager();
               Object go=wm.getActivity(act.getID());
               if (go!=null) {
                  currentGraph.addSelectionCell(go);
               }
            } catch (Exception ex) {}
         }
         //System.out.println("Selection refreshed");
      } catch (Exception ex) {
         System.err.println("Problems while updating selection");
      }
   }

}
