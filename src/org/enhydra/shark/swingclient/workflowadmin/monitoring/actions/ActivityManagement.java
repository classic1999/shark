package org.enhydra.shark.swingclient.workflowadmin.monitoring.actions;

import java.awt.event.*;
import java.util.*;


import org.enhydra.shark.api.client.wfmodel.*;
import org.enhydra.jawe.xml.elements.*;
import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;
import org.enhydra.shark.swingclient.workflowadmin.monitoring.*;

/**
 * Enters dialog for activity management for selected process.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class ActivityManagement extends ActionBase {

   public ActivityManagement (ProcessMonitor pm) {
      super(pm);
   }

   public void actionPerformed(ActionEvent e) {
      ProcessMonitor pm=(ProcessMonitor)actionPanel;
      SharkAdmin workflowAdmin=pm.getWorkflowAdmin();

      WfProcess proc=pm.getProcessViewer().getCurrentProcess();
      if (proc!=null) {
         List allActs=new ArrayList();

         org.enhydra.jawe.xml.elements.Package pkg=
            pm.getProcessViewer().getCurrentPackage();
         WorkflowProcesses wps=(WorkflowProcesses)pkg.get("WorkflowProcesses");
         String pdId="";
         try {pdId=SharkAdmin.getAdminMiscUtilities().getProcessDefinitionId(proc.key());}catch (Exception ex){}
         WorkflowProcess wp=wps.getWorkflowProcess(pdId);
         Activities acts=(Activities)wp.get("Activities");
         // adding all process activities
         allActs.addAll(acts.toCollection());

         // Iterating through all process Activity sets and adding it's activities
         // IMPLEMENT LATER: SOME OTHER THINGS MUST BE DONE TO MANAGE BLOCK ACTIVITY CONTENT
         /*Iterator asts=((ActivitySets)wp.get("ActivitySets")).toCollection().iterator();
         while (asts.hasNext()) {
            ActivitySet as=(ActivitySet)asts.next();
            acts=(Activities)as.get("Activities");
            allActs.addAll(acts.toCollection());
         }*/

         ManageActivities mad=
            new ManageActivities(pm,proc,allActs);
         mad.showDialog();
      } else {
         System.err.println("Process is not selected");
      }
   }

}
