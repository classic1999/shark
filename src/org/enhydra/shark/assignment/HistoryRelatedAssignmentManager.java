package org.enhydra.shark.assignment;

import java.util.ArrayList;
import java.util.List;

import org.enhydra.shark.Shark;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.wfmodel.WfActivity;
import org.enhydra.shark.api.client.wfmodel.WfActivityIterator;
import org.enhydra.shark.api.client.wfmodel.WfProcess;
import org.enhydra.shark.api.client.wfservice.AdminInterface;
import org.enhydra.shark.api.client.wfservice.AdminMisc;
import org.enhydra.shark.api.client.wfservice.ExecutionAdministration;
import org.enhydra.shark.api.internal.assignment.AssignmentManager;
import org.enhydra.shark.api.internal.assignment.PerformerData;
import org.enhydra.shark.api.internal.working.CallbackUtilities;
import org.enhydra.shark.xpdl.XMLUtil;

/**
 * This class provides an extended Assignment Manager implementation via the use
 * of XPDL activity extended attributes.
 *
 * The following extended attributes can be associated with an activity to
 * affect assignments:
 *
 * ReassignToOriginalPerformer - If an activity is executed more than once in a
 * particular process, then it will only be assigned to the original performer
 * during subsequent executions. The value of this extended attribute is
 * ignored.
 *
 * AssignToPerformerOfActivity - This extended attribute can be used to force an
 * activity to be assigned to the performer of a previously-executed activity.
 * The value of this extended attribute should be the activity definition id in
 * question.
 *
 * DoNotAssignToPerformerOfActivity - This extended attribute can be used to
 * force an activity NOT to be assigned to the performer of a previously-
 * executed activity. The value of this extended attribute should be the
 * activity definition id in question.
 *
 * Note that only one of each extended attribute should be associated with any
 * single activity definition.
 *
 * Note that the above names are just the default names of these extended
 * attributes, and that they can be overriden in the configuration file
 * (Shark.conf) using the following properties:
 *
 * - HistoryRelatedAssignmentManager.extAttrReassignToOriginalPerformer
 * - HistoryRelatedAssignmentManager.extAttrAssignToPerformerOfActivity
 * - HistoryRelatedAssignmentManager.extAttrDoNotAssignToPerformerOfActivity
 *
 * Finally, note that this class needs to make a connection to the workflow
 * engine. The username and password used to connect are specified by the
 * following configuration properties:
 *
 * - HistoryRelatedAssignmentManager.username
 * - HistoryRelatedAssignmentManager.password
 *
 * If anybody wishes to extend/modify this class in any way, one obvious
 * improvment would be to allow multiple copies of each extended attribute to be
 * assigned to a single activity.
 *
 * @author Rich Robinson
 */
public class HistoryRelatedAssignmentManager implements AssignmentManager {
   private CallbackUtilities cus;

   private String            username;
   private String            password;
   private String            extAttrReassignToOriginalPerformer;
   private String            extAttrAssignToPerformerOfActivity;
   private String            extAttrDoNotAssignToPerformerOfActivity;

   public void configure(CallbackUtilities cus) throws RootException {
      this.cus = cus;

      /*
       * No default values are provided for username and password for security
       * reasons - they should always be specified in the configuration file.
       */
      username = cus.getProperty("HistoryRelatedAssignmentManager.username");
      password = cus.getProperty("HistoryRelatedAssignmentManager.password");

      extAttrReassignToOriginalPerformer = cus.getProperty(
         "HistoryRelatedAssignmentManager.extAttrReassignToOriginalPerformer",
         "ReassignToOriginalPerformer");

      extAttrAssignToPerformerOfActivity = cus.getProperty(
         "HistoryRelatedAssignmentManager.extAttrAssignToPerformerOfActivity",
         "AssignToPerformerOfActivity");

      extAttrDoNotAssignToPerformerOfActivity = cus
         .getProperty(
         "HistoryRelatedAssignmentManager.extAttrDoNotAssignToPerformerOfActivity",
         "DoNotAssignToPerformerOfActivity");
   }

   public List getAssignments(SharkTransaction t,
                              String engineName,
                              String procId,
                              String actId,
                              List userIds,
                              List responsibleIds,
                              String processRequesterId,
                              PerformerData xpdlParticipant,
                              List xpdlResponsibleParticipants) throws RootException {
      List result = null;

      Shark shark = Shark.getInstance();
      AdminInterface ai = shark.getAdminInterface();
      AdminMisc am = ai.getAdminMisc();

      String[][] actExtAttribs = null;

      try {
         actExtAttribs = am.getActivitiesExtendedAttributeNameValuePairs(t, procId, actId);
      } catch (Exception ex) {
      }

      if (actExtAttribs!=null) {
         if (XMLUtil.getExtendedAttributeValue(actExtAttribs, extAttrReassignToOriginalPerformer)!=null) {
            result = doReassignToOriginalPerformer(t, engineName, procId,
                                                   actId, userIds, responsibleIds, 
                                                   processRequesterId, ai, am);
         }
         else if (XMLUtil.getExtendedAttributeValue(actExtAttribs,extAttrAssignToPerformerOfActivity)!=null) {
            result = doAssignToPerformerOfActivity(t, engineName, procId,
                                                   actId, userIds, responsibleIds,
                                                   processRequesterId, ai, am, actExtAttribs);
         }
         else if (XMLUtil.getExtendedAttributeValue(actExtAttribs,extAttrDoNotAssignToPerformerOfActivity)!=null) {
            result = doDoNotAssignToPerformerOfActivity(t, engineName, procId,
                                                        actId, userIds, responsibleIds,
                                                        processRequesterId, ai, am, actExtAttribs);
         }
         else {
            // If this is not a special case then use the parent implementation.
            result = getDefaultAssignments(t, engineName, procId, actId,
                                           userIds, responsibleIds, processRequesterId);
         }
      } else {
         // If there were some problems then use the parent implementation.
         result = getDefaultAssignments(t, engineName, procId, actId,
                                        userIds, responsibleIds, processRequesterId);
      }

      return result;
   }

   protected List doReassignToOriginalPerformer(SharkTransaction t,
                                                String engineName, String procId, String actId, List userIds,
                                                List responsibleIds, String processRequesterId,
                                                AdminInterface ai, AdminMisc am) throws RootException {
      String actDefId = am.getActivityDefinitionId(t, procId, actId);

      return getAssignmentsForActDefId(t, engineName, procId, actId, userIds,
                                       responsibleIds, processRequesterId, ai, am,
                                       actDefId, true);
   }

   protected List doAssignToPerformerOfActivity(SharkTransaction t,
                                                String engineName, String procId, String actId, List userIds,
                                                List responsibleIds, String processRequesterId,
                                                AdminInterface ai, AdminMisc am, String[][] actExtAttribs)
      throws RootException {
      String actDefId = XMLUtil.getExtendedAttributeValue(actExtAttribs,
                                               extAttrAssignToPerformerOfActivity);

      return getAssignmentsForActDefId(t, engineName, procId, actId, userIds,
                                       responsibleIds, processRequesterId, ai, am,
                                       actDefId, true);
   }

   protected List doDoNotAssignToPerformerOfActivity(SharkTransaction t,
                                                     String engineName, String procId, String actId, List userIds,
                                                     List responsibleIds, String processRequesterId,
                                                     AdminInterface ai, AdminMisc am, String[][] actExtAttribs)
      throws RootException {
      String actDefId = XMLUtil.getExtendedAttributeValue(actExtAttribs,
                                               extAttrDoNotAssignToPerformerOfActivity);

      List doNotAssignTo = getAssignmentsForActDefId(t, engineName, procId,
                                                     actId, userIds, responsibleIds, processRequesterId,
                                                     ai, am, actDefId, false);

      List allAssignments = getDefaultAssignments(t, engineName, procId,
                                                  actId, userIds, responsibleIds, processRequesterId);

      /*
       * If we only have one assignment then we CANNOT remove the "do not
       * assign to" assignment (either it won't be the same assignment, in
       * which case there's no need to remove it, or it WILL be the same
       * assignment, and we will be left with an empty assignments list - this
       * should probably never happen).
       */
      if (allAssignments.size() > 1) {
         allAssignments.removeAll(doNotAssignTo);
      }

      return allAssignments;
   }

   /*
    * Given an activity definition id, this method returns assignments based on
    * the last resource to execute that activity definition id in this process.
    */
   protected List getAssignmentsForActDefId(SharkTransaction t,
                                            String engineName, String procId, String actId, List userIds,
                                            List responsibleIds, String processRequesterId,
                                            AdminInterface ai, AdminMisc am, String actDefId,
                                            boolean fallbackToDefault) throws RootException {
      List result = new ArrayList();

      String prevPerformer = getPrevPerformerOfActDefId(t, procId, ai, am,
                                                        actDefId);

      if (prevPerformer != null) {
         result.add(prevPerformer);
      }
      else if (fallbackToDefault) {
         /*
          * If we are falling back to the parent implementation in the case
          * that there is no resource to assign to, and if our resource list
          * is empty then we call the super implementation.
          */
         result = getDefaultAssignments(t, engineName, procId, actId,
                                        userIds, responsibleIds, processRequesterId);
      }

      return result;
   }

   protected String getPrevPerformerOfActDefId(SharkTransaction t,
                                               String procId,
                                               AdminInterface ai,
                                               AdminMisc am,
                                               String actDefId)
      throws RootException {
      String result = null;

      if (actDefId != null) {
         ExecutionAdministration ea = null;

         try {
            ea = ai.getExecutionAdministration();
            ea.connect(t, username, password, "", "");
            WfProcess proc = ea.getProcess(t, procId);

            WfActivityIterator wai = proc.get_iterator_step(t);

            String query = "state.equals(\"closed.completed\") && definitionId.equals(\"" + actDefId + "\")";

            wai.set_query_expression(t, query);

            WfActivity[] acts=wai.get_next_n_sequence(t,0);
            if (acts!=null && acts.length>0) {
               // find the last one - maybe there was some reassignments
               long maxTime=-1;
               WfActivity lastAct=null;
               for (int i=0; i<acts.length; i++) {
                  long time=acts[i].last_state_time(t).getTime();
                  if (time>maxTime) {
                     maxTime=time;
                     lastAct=acts[i];
                  }
               }
               String prevActId = lastAct.key(t);

               /*
                * Get the resource that finished the previous instance of the
                * specified activity.
                */
               result = am.getActivityResourceUsername(t, procId, prevActId);
            }
         }
         finally {
            ea.disconnect(t);
         }

      }

      return result;
   }

   public List getDefaultAssignments(SharkTransaction t, String engineName,
                                     String procId, String actId, List userIds, List responsibleIds,
                                     String processRequesterId) throws RootException {
      List result;

      if (userIds != null && userIds.size() > 0) {
         result = userIds;
      }
      else if (responsibleIds != null && responsibleIds.size() > 0) {
         result = responsibleIds;
      }
      else {
         result = new ArrayList();
         result.add(processRequesterId);
      }

      return result;
   }

}
