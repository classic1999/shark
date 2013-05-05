package org.enhydra.shark.swingclient.workflowadmin;

import java.awt.*;

import javax.swing.*;


import org.enhydra.shark.api.client.wfmodel.*;

import org.enhydra.jawe.*;
import org.enhydra.jawe.xml.elements.*;
import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.actions.*;

/**
 * The panel that is used to display the WorkflowGraph instances -
 * the Workflows defined by the ProcessEditor application.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class ProcessViewer extends ActionPanel {

   protected org.enhydra.jawe.xml.elements.Package currentPackage;
   protected WfProcessMgr currentProcessMgr;
   protected WfProcess currentProcess;
   protected ProcessGraph currentGraph;
   protected JScrollPane graphScrollPane;

   public ProcessViewer (Window parent) {
      super();
      super.init();
      super.initDialog(parent,"",false,false);
   }

   protected void createActions () {
      defaultActions=new Action[] {
         new ActualSize(this),
         new ZoomIn(this),
         new ZoomOut(this),
         new ViewBlock(this),
         new UpdateView(this)
      };
   }

   /**
   * Create the center component of this panel. This creates a scroll-
   * pane for the current graph variable and stores the scrollpane
   * in the scrollPane variable.
   */
   protected Component createCenterComponent() {
      graphScrollPane = new JScrollPane();
      JViewport port = graphScrollPane .getViewport();
      port.setScrollMode(JViewport.SIMPLE_SCROLL_MODE);

      return graphScrollPane;
   }


   /** Show dialog for editing panel. */
   public void showDialog () {
      graphScrollPane.setViewportView(currentGraph);
      // repack because the graph probably changed
      myDialog.pack();
      // set location to be centered
      //org.webdocwf.util.swingwfapp.Utils.center(myDialog);
      org.enhydra.jawe.Utils.center(myDialog,100,150);
      // set title
      setDialogTitle();
      myDialog.setVisible(true);
   }

   /** Sets the dialog title. */
   protected void setDialogTitle () {
      String pName=null;
      try {
         pName=currentProcessMgr.name();
      } catch (Exception ex){}

      myDialog.setTitle(pName);
   }


   public ProcessGraph getCurrentGraph () {
      return currentGraph;
   }

   public org.enhydra.jawe.xml.elements.Package getCurrentPackage () {
      return currentPackage;
   }

   public WfProcess getCurrentProcess () {
      return currentProcess;
   }

   public WfProcessMgr getCurrentProcessDef () {
      return currentProcessMgr;
   }

   /**
   * Displays the wanted process instance in the graph.
   */
   public void displayProcess (
      org.enhydra.jawe.xml.elements.Package pkg,
      WfProcessMgr wpm,WfProcess wpr) {

      currentPackage=pkg;
      currentProcessMgr=wpm;
      currentProcess=wpr;
      try {
         WorkflowProcesses wps=(WorkflowProcesses)pkg.get("WorkflowProcesses");
         WorkflowProcess wp=wps.getWorkflowProcess(SharkAdmin.getAdminMiscUtilities().getProcessMgrProcDefId(wpm.name()));
         currentGraph=(ProcessGraph)((org.enhydra.jawe.graph.Process)
               JaWE.getInstance().getPackageEditor().getProcessObject(wp)).
               getImplementationEditor().getGraph();
         currentGraph.setEditable(false);
         currentGraph.setMoveable(false);
         ((ProcessEditor)currentGraph.getEditor()).setButtonsEnabled(false);
//System.out.println("Selected graph="+currentGraph.get("Name").toString());
         graphScrollPane.setViewportView(currentGraph);
         updateSelection();
      } catch (Exception ex) {
         graphScrollPane.setViewportView(null);
      }
   }

   /**
   * Refreshes graph to select activities of current process insance that
   * are currently in the open.running state.
   */
   public void updateSelection () {
      if (currentGraph==null) return;
      currentGraph.clearSelection();
      if (currentProcess==null) return;

      try {
         WfActivityIterator wai=SharkClient.getCommonExpressionBuilder().getOpenActivities(currentProcess.key());
         WfActivity[] allRunningActs=wai.get_next_n_sequence(0);
         Activities acts=(Activities)currentGraph.get("Activities");
         for (int i=0; i<allRunningActs.length; i++) {
            // if exception does not occur, it means that it is activity within
            // the block, so skip it
            try {
               String blockId=SharkClient.getAdminMiscUtilities().getBlockActivityId(currentProcess.key(),allRunningActs[i].key());
               if (blockId!=null && blockId.trim().length()>0) {
                  continue;
               }
            } catch (Exception ex) {}
            Activity act=acts.getActivity(SharkClient.getAdminMiscUtilities().getActivityDefinitionId(currentProcess.key(),allRunningActs[i].key()));
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

   /** Sets the value of scale for zooming. */
   public void setScale(double scale) {
      scale = Math.max(Math.min(scale,2),0.1);
      try {
         currentGraph.setScale(scale);
      } catch (Exception ex){}
   }

   //******************** TEST
   public static void main (String[] args) {
      JFrame f=new JFrame();
      ProcessViewer pv=new ProcessViewer(f);
      f.setBackground(Color.lightGray);
      f.getContentPane().setLayout(new BorderLayout());
      f.getContentPane().add(pv,BorderLayout.CENTER);
      f.pack();
      f.setSize(1000,700);
      f.setVisible(true);
   }
}
