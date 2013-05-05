package org.enhydra.shark.security;


import java.util.*;

import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.internal.security.SecurityException;
import org.enhydra.shark.api.internal.security.SecurityManager;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

/**
 *
 * @author Sasa Bojanic
 *
 */
public class StandardSecurityManager implements SecurityManager {

   private static final String SECURITY_MODE_NO_SECURITY="NO_SECURITY";
   private static final String SECURITY_MODE_NORMAL_SECURITY="NORMAL_SECURITY";
   private static final String SECURITY_MODE_ADMIN_SECURITY="ADMIN_SECURITY";

   private static final int SECURITY_MODE_NUM_NO_SECURITY=0;
   private static final int SECURITY_MODE_NUM_NORMAL_SECURITY=1;
   private static final int SECURITY_MODE_NUM_ADMIN_SECURITY=2;

   private final static String ADMIN_USER_PREFIX="StandardSecurityManager.AdminUser";

   private CallbackUtilities cus;

   private int SECURITY_MODE=SECURITY_MODE_NUM_NORMAL_SECURITY;

   private Set adminUsers=new HashSet();

   public void configure (CallbackUtilities cus) throws RootException {
      this.cus=cus;

      String sec_m=cus.getProperty("StandardSecurityManager.SECURITY_MODE","NORMAL_SECURITY");
      String sm=null;
      if (sec_m.equals(SECURITY_MODE_NO_SECURITY)) {
         SECURITY_MODE=SECURITY_MODE_NUM_NO_SECURITY;
         sm=SECURITY_MODE_NO_SECURITY;
      } else if (sec_m.equals(SECURITY_MODE_ADMIN_SECURITY)) {
         SECURITY_MODE=SECURITY_MODE_NUM_ADMIN_SECURITY;
         sm=SECURITY_MODE_ADMIN_SECURITY;
      } else {
         SECURITY_MODE=SECURITY_MODE_NUM_NORMAL_SECURITY;
         sm=SECURITY_MODE_NORMAL_SECURITY;
      }

      cus.info("StandardSecurityManager -> Security mode set to "+sm);

      Properties props= cus.getProperties();
      Iterator it=props.entrySet().iterator();
      while (it.hasNext()) {
         try {
            Map.Entry me=(Map.Entry)it.next();
            String entry=me.getKey().toString();
            if (entry.startsWith(ADMIN_USER_PREFIX)) {
               String adminUser=me.getValue().toString();
               adminUsers.add(adminUser);
            }
         } catch (Throwable ex) {
            cus.error("StandardSecurityManager -> Error when retrieving list of admins from properties!!!");
         }
      }
      System.out.println("Admin users are:"+adminUsers);
   }

   public List getAssignments (
      SharkTransaction t,
      String engineName,
      String procId,
      String actId,
      List userIds) throws RootException {

      return userIds;
   }

   // WfProcessMgr security methods
   public void check_processmgr_how_many_process (SharkTransaction t,String name,String userId,String pkgId,String version,String pDefId) throws SecurityException {

   }

   public void check_processmgr_get_iterator_process (SharkTransaction t,String name,String userId,String pkgId,String version,String pDefId) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to ask for iterator over process manager's processes!");
         }
      }
   }

   public void check_processmgr_get_sequence_process (SharkTransaction t,String name,String userId,String pkgId,String version,String pDefId) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to ask for the list of the process manager's processes!");
         }
      }
   }

   public void check_processmgr_is_member_of_process (SharkTransaction t,String name,String userId,String pkgId,String version,String pDefId) throws SecurityException {
   }

   public void check_processmgr_process_mgr_state (SharkTransaction t,String name,String userId,String pkgId,String version,String pDefId) throws SecurityException {

   }

   public void check_processmgr_set_process_mgr_state (SharkTransaction t,String name,String userId,String pkgId,String version,String pDefId) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to change process manager state!");
         }
      }
   }

   public void check_processmgr_name (SharkTransaction t,String name,String userId,String pkgId,String version,String pDefId) throws SecurityException {

   }

   public void check_processmgr_description (SharkTransaction t,String name,String userId,String pkgId,String version,String pDefId) throws SecurityException {

   }

   public void check_processmgr_category (SharkTransaction t,String name,String userId,String pkgId,String version,String pDefId) throws SecurityException {

   }

   public void check_processmgr_version (SharkTransaction t,String name,String userId,String pkgId,String version,String pDefId) throws SecurityException {

   }

   public void check_processmgr_context_signature (SharkTransaction t,String name,String userId,String pkgId,String version,String pDefId) throws SecurityException {

   }

   public void check_processmgr_result_signature (SharkTransaction t,String name,String userId,String pkgId,String version,String pDefId) throws SecurityException {

   }

   public void check_processmgr_create_process (SharkTransaction t,String name,String userId,String pkgId,String version,String pDefId) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to create processes!");
         }
      }
   }


   // WfProcess security methods

   // from WfExecutionObject
   public void check_process_workflow_state (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {

   }

   public void check_process_while_open (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {

   }

   public void check_process_why_not_running (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {

   }

   public void check_process_how_closed (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {

   }

   public void check_process_valid_states (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {

   }

   public void check_process_state (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {

   }

   public void check_process_change_state (SharkTransaction t,String procId,String userId,String procCreator,String curState,String new_state) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to change process state!");
         }
      }
   }

   public void check_process_name (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {

   }

   public void check_process_set_name (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to change process name!");
         }
      }
   }

   public void check_process_key (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {

   }

   public void check_process_description (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {

   }

   public void check_process_set_description (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to change process description!");
         }
      }
   }

   public void check_process_process_context (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to ask for process context!");
         }
      }
   }

   public void check_process_set_process_context (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to change process context!");
         }
      }
   }

   public void check_process_priority (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {

   }

   public void check_process_set_priority (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to change process priority!");
         }
      }
   }

   public void check_process_resume (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to resume process!");
         }
      }
   }

   public void check_process_suspend (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to suspend process!");
         }
      }
   }

   public void check_process_terminate (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to terminate process!");
         }
      }
   }

   public void check_process_abort (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to abort process!");
         }
      }
   }

   public void check_process_how_many_history (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {

   }

   public void check_process_get_iterator_history (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to ask for iterator over process history!");
         }
      }
   }

   public void check_process_get_sequence_history (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to ask for process history!");
         }
      }
   }

   public void check_process_is_member_of_history (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {

   }

   public void check_process_last_state_time (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {

   }

   // from WfProcess
   public void check_process_requester (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to ask for process requester!");
         }
      }
   }

   public void check_process_set_requester (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to change process requester!");
         }
      }
   }

   public void check_process_how_many_step (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {

   }

   public void check_process_get_iterator_step (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to ask for iterator over process activities!");
         }
      }
   }

   public void check_process_get_sequence_step (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to ask for process steps!");
         }
      }
   }

   public void check_process_is_member_of_step (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {

   }

   public void check_process_manager (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {

   }

   public void check_process_result (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to ask for process result!");
         }
      }
   }

   public void check_process_start (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to start the process!");
         }
      }
   }

   public void check_process_get_activities_in_state (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to ask for process activities that are in certain state!");
         }
      }
   }


   // WfActivity security methods

   // from WfExecutionObject
   public void check_activity_workflow_state (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {

   }

   public void check_activity_while_open (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {

   }

   public void check_activity_why_not_running (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {

   }

   public void check_activity_how_closed (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {

   }

   public void check_activity_valid_states (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {

   }

   public void check_activity_state (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {

   }

   public void check_activity_change_state (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners,String curState,String new_state) throws SecurityException {
      if (SECURITY_MODE>=SECURITY_MODE_NUM_NORMAL_SECURITY) {
         if (new_state.equals("closed.aborted")) {
            check_activity_abort(t,procId,actId,userId,procCreator,ownerId,possibleOwners);
         } else if (new_state.equals("closed.completed")) {
            check_activity_complete(t,procId,actId,userId,procCreator,ownerId,possibleOwners);
         } else if (new_state.equals("closed.terminated")) {
            check_activity_terminate(t,procId,actId,userId,procCreator,ownerId,possibleOwners);
         } else if (new_state.equals("open.not_running.not_started")) {
            if (!userId.equals(ownerId) && !adminUsers.contains(userId)) {
               throw new SecurityException("StandardSecurityManager -> NORMAL SECURITY RESTRICTION: Current activity state is "+curState+" - user "+userId+" can't change it to state "+new_state+". Only the owner or admin users are allowed to do it!");
            }
         } else if (new_state.equals("open.not_running.suspended")) {
            check_activity_suspend(t,procId,actId,userId,procCreator,ownerId,possibleOwners);
         } else { //new state is open.running
            if (curState.equals("open.not_running.not_started")) {
               if (!possibleOwners.contains(userId) && !adminUsers.contains(userId)) {
                  throw new SecurityException("StandardSecurityManager -> NORMAL SECURITY RESTRICTION: Current activity state is "+curState+" - user "+userId+" can't change it to state "+new_state+". Only the owner or admin users are allowed to do it!");
               }
            }
         }
      }
   }

   public void check_activity_name (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {

   }

   public void check_activity_set_name (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to change activity name!");
         }
      }
   }

   public void check_activity_key (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {

   }

   public void check_activity_description (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {

   }

   public void check_activity_set_description (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to change activity description!");
         }
      }
   }

   public void check_activity_process_context (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to ask for activity context!");
         }
      }
   }

   public void check_activity_set_process_context (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {
      if (SECURITY_MODE>=SECURITY_MODE_NUM_NORMAL_SECURITY) {
         if (!userId.equals(ownerId) && !adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> NORMAL SECURITY RESTRICTION: Only the owner or admin users are allowed to set context of an activity!");
         }
      }
   }

   public void check_activity_priority (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {

   }

   public void check_activity_set_priority (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to change activity priority!");
         }
      }
   }

   public void check_activity_resume (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to resume an activity!");
         }
      }
   }

   public void check_activity_suspend (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to suspend an activity!");
         }
      }
   }

   public void check_activity_terminate (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to terminate an activity!");
         }
      }
   }

   public void check_activity_abort (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to abort an activity!");
         }
      }
   }

   public void check_activity_how_many_history (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {

   }

   public void check_activity_get_iterator_history (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to ask for iterator over activity's history!");
         }
      }
   }

   public void check_activity_get_sequence_history (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to ask for a list of activity's history!");
         }
      }
   }

   public void check_activity_is_member_of_history (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {

   }

   public void check_activity_last_state_time (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {

   }

   // from WfRequester
   public void check_activity_how_many_performer(SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {

   }

   public void check_activity_get_iterator_performer(SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to ask for iterator over activity's performers!");
         }
      }
   }

   public void check_activity_get_sequence_performer(SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to ask for a list of performers!");
         }
      }
   }

   public void check_activity_is_member_of_performer(SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {

   }

   public void check_activity_receive_event(SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {

   }

   // from WfActivity
   public void check_activity_how_many_assignment (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {

   }

   public void check_activity_get_iterator_assignment (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to ask for iterator over activity's assignments!");
         }
      }
   }

   public void check_activity_get_sequence_assignment (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to ask for a list of activity's assignments!");
         }
      }
   }

   public void check_activity_is_member_of_assignment (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {

   }

   public void check_activity_container (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {

   }

   public void check_activity_result (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_NORMAL_SECURITY) {
         if (!userId.equals(ownerId) && !adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: The activity's result can be obtained only by the user that owns it, or by the admin users!");
         }
      }
   }

   public void check_activity_set_result (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {
      if (SECURITY_MODE>=SECURITY_MODE_NUM_NORMAL_SECURITY) {
         if (!userId.equals(ownerId) && !adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> NORMAL SECURITY RESTRICTION: The activity's result can be set only by the user that owns it, or by the admin users");
         }
      }
   }

   public void check_activity_complete (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {
      if (SECURITY_MODE>=SECURITY_MODE_NUM_NORMAL_SECURITY) {
         if (!userId.equals(ownerId) && !adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> NORMAL SECURITY RESTRICTION: The activity can be completed only by the user that accepted its assignment or by the admin users!");
         }
      }
   }


   // WfAssignment security methods
   public void check_assignment_activity (SharkTransaction t,String procId,String actId,String username,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {

   }

   public void check_assignment_assignee (SharkTransaction t,String procId,String actId,String username,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {

   }

   public void check_assignment_set_assignee (SharkTransaction t,String procId,String actId,String username,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_NORMAL_SECURITY) {
         if (ownerId!=null && !userId.equals(ownerId)) {
            throw new SecurityException("StandardSecurityManager -> NORMAL SECURITY RESTRICTION: If assignment is accepted, it can be reassigned only by the user that accepted it, or by the admin users");
         }
      }
      if (SECURITY_MODE>SECURITY_MODE_NUM_NORMAL_SECURITY) {
         if (!userId.equals(ownerId) && !adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> NORMAL SECURITY RESTRICTION: Only the owner or admin users are allowed to change assignment's status!");
         }
      }

   }

   public void check_assignment_set_accepted_status (SharkTransaction t,String procId,String actId,String username,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {
      if (SECURITY_MODE>=SECURITY_MODE_NUM_NORMAL_SECURITY) {
         if (!userId.equals(username) && !adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> NORMAL SECURITY RESTRICTION: Only the owner or admin users are allowed to change assignment's status!");
         }
      }
   }

   public void check_assignment_get_accepted_status (SharkTransaction t,String procId,String actId,String username,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {

   }


   // WfResource security methods
   public void check_resource_how_many_work_item (SharkTransaction t,String resourceId,String userId) throws SecurityException {

   }

   public void check_resource_get_iterator_work_item (SharkTransaction t,String resourceId,String userId) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to ask for iterator over resource's assignments!");
         }
      }
   }

   public void check_resource_get_sequence_work_item (SharkTransaction t,String resourceId,String userId) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to ask for the list of resource's assignments!");
         }
      }
   }

   public void check_resource_is_member_of_work_items (SharkTransaction t,String resourceId,String userId) throws SecurityException {

   }

   public void check_resource_resource_key (SharkTransaction t,String resourceId,String userId) throws SecurityException {

   }

   public void check_resource_resource_name (SharkTransaction t,String resourceId,String userId) throws SecurityException {

   }

   public void check_resource_release (SharkTransaction t,String resourceId,String userId) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to release resource's assignment!");
         }
      }
   }

   // WfRequester security methods
   public void check_requester_how_many_performer (SharkTransaction t,String requesterResourceUsername,String userId) throws SecurityException {

   }

   public void check_requester_get_iterator_performer (SharkTransaction t,String requesterResourceUsername,String userId) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to ask for iterator over requester's performers!");
         }
      }
   }

   public void check_requester_get_sequence_performer (SharkTransaction t,String requesterResourceUsername,String userId) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to get performer list!");
         }
      }
   }

   public void check_requester_is_member_of_performer (SharkTransaction t,String requesterResourceUsername,String userId) throws SecurityException {

   }

   public void check_requester_receive_event (SharkTransaction t,String requesterResourceUsername,String userId) throws SecurityException {

   }

   // SharkConnection security methods
   public void check_sharkconnection_connect (SharkTransaction t,String userId) throws SecurityException {

   }

   public void check_sharkconnection_getResourceObject (SharkTransaction t,String userId) throws SecurityException {

   }

   public void check_sharkconnection_createProcess (SharkTransaction t,String pmgrname,String userId,String pkgId,String version,String pDefId) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to create process!");
         }
      }
   }


   // ExecutionAdministration security methods
   public void check_executionadministration_connect (SharkTransaction t,String userId) throws SecurityException {
      if (SECURITY_MODE==SECURITY_MODE_NUM_ADMIN_SECURITY) {
         if (!adminUsers.contains(userId)) {
            throw new SecurityException("StandardSecurityManager -> ADMIN SECURITY RESTRICTION: Only admin users are allowed to use ExecutionAdministration interface !");
         }
      }
   }

   public void check_executionadministration_get_iterator_processmgr (SharkTransaction t,String userId) throws SecurityException {
   }

   public void check_executionadministration_get_sequence_processmgr (SharkTransaction t,String userId) throws SecurityException {
   }

   public void check_executionadministration_getLoggedUsers (SharkTransaction t,String userId) throws SecurityException {
   }

   public void check_executionadministration_get_iterator_resource (SharkTransaction t,String userId) throws SecurityException {
   }

   public void check_executionadministration_get_sequence_resource (SharkTransaction t,String userId) throws SecurityException {
   }

   public void check_executionadministration_startActivity (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {
   }

   public void check_executionadministration_getProcessMgr (SharkTransaction t,String userId,String name) throws SecurityException {
   }

   public void check_executionadministration_getResource (SharkTransaction t,String username,String userId) throws SecurityException {
   }

   public void check_executionadministration_getProcess (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException {
   }

   public void check_executionadministration_getActivity (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {
   }

   public void check_executionadministration_getAssignment (SharkTransaction t,String procId,String actId,String username,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException {
   }

   public void check_executionadministration_getAssignment (SharkTransaction t,String procId,String assId,String userId,String procCreator) throws SecurityException {
   }

   public void check_executionadministration_reevaluateAssignments (SharkTransaction t,String userId) throws SecurityException {
   }

   public void check_executionadministration_deleteClosedProcesses (SharkTransaction t,String userId) throws SecurityException {
   }

   // DeadlineAdministration security methods
   public void check_deadlines (SharkTransaction t,String userId) throws SecurityException {      
   }
   
}
