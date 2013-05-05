package org.enhydra.shark;

import java.util.Iterator;
import java.util.List;

import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.common.SharkConstants;
import org.enhydra.shark.api.internal.working.WfActivityInternal;
import org.enhydra.shark.xpdl.XMLCollectionElement;
import org.enhydra.shark.xpdl.XMLUtil;
import org.enhydra.shark.xpdl.elements.Activities;
import org.enhydra.shark.xpdl.elements.Activity;
import org.enhydra.shark.xpdl.elements.ActivitySet;
import org.enhydra.shark.xpdl.elements.WorkflowProcess;



/**
 * The class that checks if updating of package is currently possible.
 * Before calling this class, the processes from the package that is
 * being checked must be suspended.
 * <br>NOTE: This implementation only checks if definitions for all active
 * activities exists in the new package.
 * @author Sasa Bojanic
 * @version 1.0
 */
public class UpdateChecker {
   private org.enhydra.shark.xpdl.elements.Package oldPackage;
   private org.enhydra.shark.xpdl.elements.Package newPackage;
   private List processesToRestore;
   private List activitiesToRestore;

   public UpdateChecker (org.enhydra.shark.xpdl.elements.Package oldPackage,
                         org.enhydra.shark.xpdl.elements.Package newPackage,
                         List processesToRestore,List activitiesToRestore) {
      this.oldPackage=oldPackage;
      this.newPackage=newPackage;
      this.processesToRestore=processesToRestore;
      this.activitiesToRestore=activitiesToRestore;
   }

   public boolean isUpdatePossible (SharkTransaction t) throws BaseException {
      Iterator it=activitiesToRestore.iterator();
      while (it.hasNext()) {
         WfActivityInternal act=(WfActivityInternal)it.next();
         // we don't check closed activities
         try {
            if (act.state(t).startsWith(SharkConstants.STATEPREFIX_CLOSED)) continue;
         } catch (Exception ex) {}

         Activity actDef=SharkUtilities.getActivityDefinition(t,act,
                                                              SharkUtilities.getWorkflowProcess(act.container(t).package_id(t),
                                                                                                act.container(t).manager_version(t),
                                                                                                act.container(t).process_definition_id(t)),
                                                              act.block_activity(t));

         // search for the corresponding activity definition within new package
         String processId=null;
         String activitySetId=null;
         // determine if activity definition is within the process
         // or within processes activity set
         XMLCollectionElement owner=(XMLCollectionElement)actDef.getParent().getParent();
         if (owner instanceof WorkflowProcess) {
            processId=owner.getId();
         } else {
            activitySetId=owner.getId();
            processId=XMLUtil.getWorkflowProcess(actDef).getId();
         }
         WorkflowProcess wp=newPackage.getWorkflowProcess(processId);
         // if process definition can't be found within the new package, return false
         if (wp==null) {
            return false;
         }
         // get all activity definitions from the process or from the set
         Activities acts=null;
         if (activitySetId!=null) {
            ActivitySet as=wp.getActivitySet(activitySetId);
            // if the set can't be found, return false
            if (as==null) {
               return false;
            }
            acts=as.getActivities();
         } else {
            acts=wp.getActivities();
         }
         // if activity definition can't be found, return false
         if (acts.getActivity(actDef.getId())==null) {
            return false;
         }
      }
      return true;
   }


}
