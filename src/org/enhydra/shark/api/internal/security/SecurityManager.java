package org.enhydra.shark.api.internal.security;

import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

import java.util.List;

/**
 *
 * @author Sasa Bojanic
 *
 */
public interface SecurityManager {

   void configure (CallbackUtilities cus) throws RootException;

   List getAssignments (
      SharkTransaction t,
      String engineName,
      String procId,
      String actId,
      List userIds) throws RootException;

   // WfProcessMgr security methods
   void check_processmgr_how_many_process (SharkTransaction t,String name,String userId,String pkgId,String version,String pDefId) throws SecurityException;

   void check_processmgr_get_iterator_process (SharkTransaction t,String name,String userId,String pkgId,String version,String pDefId) throws SecurityException;

   void check_processmgr_get_sequence_process (SharkTransaction t,String name,String userId,String pkgId,String version,String pDefId) throws SecurityException;

   void check_processmgr_is_member_of_process (SharkTransaction t,String name,String userId,String pkgId,String version,String pDefId) throws SecurityException;

   void check_processmgr_process_mgr_state (SharkTransaction t,String name,String userId,String pkgId,String version,String pDefId) throws SecurityException;

   void check_processmgr_set_process_mgr_state (SharkTransaction t,String name,String userId,String pkgId,String version,String pDefId) throws SecurityException;

   void check_processmgr_name (SharkTransaction t,String name,String userId,String pkgId,String version,String pDefId) throws SecurityException;

   void check_processmgr_description (SharkTransaction t,String name,String userId,String pkgId,String version,String pDefId) throws SecurityException;

   void check_processmgr_category (SharkTransaction t,String name,String userId,String pkgId,String version,String pDefId) throws SecurityException;

   void check_processmgr_version (SharkTransaction t,String name,String userId,String pkgId,String version,String pDefId) throws SecurityException;

   void check_processmgr_context_signature (SharkTransaction t,String name,String userId,String pkgId,String version,String pDefId) throws SecurityException;

   void check_processmgr_result_signature (SharkTransaction t,String name,String userId,String pkgId,String version,String pDefId) throws SecurityException;

   void check_processmgr_create_process (SharkTransaction t,String name,String userId,String pkgId,String version,String pDefId) throws SecurityException;


   // WfProcess security methods

   // from WfExecutionObject
   void check_process_workflow_state (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   void check_process_while_open (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   void check_process_why_not_running (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   void check_process_how_closed (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   void check_process_valid_states (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   void check_process_state (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   void check_process_change_state (SharkTransaction t,String procId,String userId,String procCreator,String curState,String new_state) throws SecurityException;

   void check_process_name (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   void check_process_set_name (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   void check_process_key (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   void check_process_description (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   void check_process_set_description (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   void check_process_process_context (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   void check_process_set_process_context (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   void check_process_priority (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   void check_process_set_priority (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   void check_process_resume (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   void check_process_suspend (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   void check_process_terminate (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   void check_process_abort (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   void check_process_how_many_history (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   void check_process_get_iterator_history (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   void check_process_get_sequence_history (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   void check_process_is_member_of_history (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   void check_process_last_state_time (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   // from WfProcess
   void check_process_requester (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   void check_process_set_requester (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   void check_process_how_many_step (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   void check_process_get_iterator_step (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   void check_process_get_sequence_step (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   void check_process_is_member_of_step (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   void check_process_manager (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   void check_process_result (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   void check_process_start (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   void check_process_get_activities_in_state (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   
   // WfActivity security methods

   // from WfExecutionObject
   void check_activity_workflow_state (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_activity_while_open (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_activity_why_not_running (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_activity_how_closed (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_activity_valid_states (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_activity_state (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_activity_change_state (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners,String curState,String new_state) throws SecurityException;

   void check_activity_name (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_activity_set_name (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_activity_key (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_activity_description (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_activity_set_description (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_activity_process_context (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_activity_set_process_context (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_activity_priority (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_activity_set_priority (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_activity_resume (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_activity_suspend (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_activity_terminate (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_activity_abort (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_activity_how_many_history (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_activity_get_iterator_history (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_activity_get_sequence_history (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_activity_is_member_of_history (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_activity_last_state_time (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   // from WfRequester
   void check_activity_how_many_performer(SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_activity_get_iterator_performer(SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_activity_get_sequence_performer(SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_activity_is_member_of_performer(SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_activity_receive_event(SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   // from WfActivity
   void check_activity_how_many_assignment (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_activity_get_iterator_assignment (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_activity_get_sequence_assignment (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_activity_is_member_of_assignment (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_activity_container (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_activity_result (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_activity_set_result (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_activity_complete (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   // WfAssignment security methods
   void check_assignment_activity (SharkTransaction t,String procId,String actId,String username,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_assignment_assignee (SharkTransaction t,String procId,String actId,String username,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_assignment_set_assignee (SharkTransaction t,String procId,String actId,String username,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_assignment_set_accepted_status (SharkTransaction t,String procId,String actId,String username,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_assignment_get_accepted_status (SharkTransaction t,String procId,String actId,String username,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;


   // WfResource security methods
   void check_resource_how_many_work_item (SharkTransaction t,String resourceId,String userId) throws SecurityException;

   void check_resource_get_iterator_work_item (SharkTransaction t,String resourceId,String userId) throws SecurityException;

   void check_resource_get_sequence_work_item (SharkTransaction t,String resourceId,String userId) throws SecurityException;

   void check_resource_is_member_of_work_items (SharkTransaction t,String resourceId,String userId) throws SecurityException;

   void check_resource_resource_key (SharkTransaction t,String resourceId,String userId) throws SecurityException;

   void check_resource_resource_name (SharkTransaction t,String resourceId,String userId) throws SecurityException;

   void check_resource_release (SharkTransaction t,String resourceId,String userId) throws SecurityException;

   // WfRequester security methods
   void check_requester_how_many_performer (SharkTransaction t,String requesterResourceUsername,String userId) throws SecurityException;

   void check_requester_get_iterator_performer (SharkTransaction t,String requesterResourceUsername,String userId) throws SecurityException;

   void check_requester_get_sequence_performer (SharkTransaction t,String requesterResourceUsername,String userId) throws SecurityException;

   void check_requester_is_member_of_performer (SharkTransaction t,String requesterResourceUsername,String userId) throws SecurityException;

   void check_requester_receive_event (SharkTransaction t,String requesterResourceUsername,String userId) throws SecurityException;


   // SharkConnection security methods
   void check_sharkconnection_connect (SharkTransaction t,String userId) throws SecurityException;

   void check_sharkconnection_getResourceObject (SharkTransaction t,String userId) throws SecurityException;

   void check_sharkconnection_createProcess (SharkTransaction t,String pmgrname,String userId,String pkgId,String version,String pDefId) throws SecurityException;

   // ExecutionAdministration security methods
   void check_executionadministration_connect (SharkTransaction t,String userId) throws SecurityException;

   void check_executionadministration_get_iterator_processmgr (SharkTransaction t,String userId) throws SecurityException;

   void check_executionadministration_get_sequence_processmgr (SharkTransaction t,String userId) throws SecurityException;

   void check_executionadministration_getLoggedUsers (SharkTransaction t,String userId) throws SecurityException;

   void check_executionadministration_get_iterator_resource (SharkTransaction t,String userId) throws SecurityException;

   void check_executionadministration_get_sequence_resource (SharkTransaction t,String userId) throws SecurityException;

   void check_executionadministration_startActivity (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   void check_executionadministration_getProcessMgr (SharkTransaction t,String userId,String name) throws SecurityException;

   void check_executionadministration_getResource (SharkTransaction t,String username,String userId) throws SecurityException;

   void check_executionadministration_getProcess (SharkTransaction t,String procId,String userId,String procCreator) throws SecurityException;

   void check_executionadministration_getActivity (SharkTransaction t,String procId,String actId,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_executionadministration_getAssignment (SharkTransaction t,String procId,String actId,String username,String userId,String procCreator,String ownerId,List possibleOwners) throws SecurityException;

   void check_executionadministration_getAssignment (SharkTransaction t,String procId,String assId,String userId,String procCreator) throws SecurityException;

   void check_executionadministration_reevaluateAssignments (SharkTransaction t,String userId) throws SecurityException;

   void check_executionadministration_deleteClosedProcesses (SharkTransaction t,String userId) throws SecurityException;

   // DeadlineAdministration security methods
   void check_deadlines (SharkTransaction t,String userId) throws SecurityException;
      
}
