package org.enhydra.shark.api.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The class that defines various constants used by engine.
 * @author Sasa Bojanic
 * @version 1.0
 */
public final class SharkConstants {

   public static final String STATE_OPEN_RUNNING="open.running";
   public static final String STATEPREFIX_OPEN="open";
   public static final String STATEPREFIX_OPEN_NOT_RUNNING="open.not_running";
   public static final String STATE_OPEN_NOT_RUNNING_NOT_STARTED="open.not_running.not_started";
   public static final String STATE_OPEN_NOT_RUNNING_SUSPENDED="open.not_running.suspended";
   public static final String STATEPREFIX_CLOSED="closed";
   public static final String STATE_CLOSED_COMPLETED="closed.completed";
   public static final String STATE_CLOSED_TERMINATED="closed.terminated";
   public static final String STATE_CLOSED_ABORTED="closed.aborted";

   public static final String EVENT_PACKAGE_LOADED="packageLoaded";
   public static final String EVENT_PACKAGE_UNLOADED="packageUnloaded";
   public static final String EVENT_PACKAGE_UPDATED="packageUpdated";
   public static final String EVENT_PROCESS_CREATED="processCreated";
   public static final String EVENT_PROCESS_STATE_CHANGED="processStateChanged";
   public static final String EVENT_PROCESS_CONTEXT_CHANGED="processContextChanged";
   public static final String EVENT_ACTIVITY_STATE_CHANGED="activityStateChanged";
   public static final String EVENT_ACTIVITY_CONTEXT_CHANGED="activityContextChanged";
   public static final String EVENT_ACTIVITY_RESULT_CHANGED="activityResultChanged";
   public static final String EVENT_ACTIVITY_ASSIGNMENT_CHANGED="activityAssignmentChanged";

   public static final String ROOT_DIRECTORY_PATH_PROP="RootDirectoryPath";

   public static final String PROCESS__ID_NAME = "_process_";
   public static final String ACTIVITY_ID_NAME = "_activity_";

   public static final String UNSATISFIED_SPLIT_CONDITIONS_HANDLING_IGNORE="IGNORE";
   public static final String UNSATISFIED_SPLIT_CONDITIONS_HANDLING_FINISH_IF_POSSIBLE="FINISH_IF_POSSIBLE";
   public static final String UNSATISFIED_SPLIT_CONDITIONS_HANDLING_ROLLBACK="ROLLBACK";

   public static final String[] POSSIBLE_PROCESS_STATES = {
      SharkConstants.STATE_OPEN_RUNNING,
         SharkConstants.STATE_OPEN_NOT_RUNNING_NOT_STARTED,
         SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED,
         SharkConstants.STATE_CLOSED_COMPLETED,
         SharkConstants.STATE_CLOSED_TERMINATED,
         SharkConstants.STATE_CLOSED_ABORTED};

   public static final List possibleProcessStates = Collections.unmodifiableList(Arrays.asList(SharkConstants.POSSIBLE_PROCESS_STATES));

   public static final String[] POSSIBLE_ACTIVITY_STATES = {
      SharkConstants.STATE_OPEN_RUNNING,
         SharkConstants.STATE_OPEN_NOT_RUNNING_NOT_STARTED,
         SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED,
         SharkConstants.STATE_CLOSED_COMPLETED,
         SharkConstants.STATE_CLOSED_TERMINATED,
         SharkConstants.STATE_CLOSED_ABORTED};

   public static final List possibleActivityStates=Collections.unmodifiableList(Arrays.asList(SharkConstants.POSSIBLE_ACTIVITY_STATES));

   public static final String PROCESS_STARTED_TIME="PROCESS_STARTED_TIME";
   public static final String ACTIVITY_ACTIVATED_TIME ="ACTIVITY_ACTIVATED_TIME";
   public static final String ACTIVITY_ACCEPTED_TIME="ACTIVITY_ACCEPTED_TIME";

   public static final String GRAMMAR_JAVA="text/java";
   public static final String GRAMMAR_JAVA_SCRIPT="text/javascript";
   public static final String GRAMMAR_PYTHON_SCRIPT="text/pythonscript";

   public static final String QUERY_STATE_PREFIX="QUERY_STATE_ONLY()";


   // WfProcessMgrIterator
   public static final String MGR_PACKAGE_ID="packageId";
   public static final String MGR_PROCESS_DEFINITION_ID="processDefinitionId";
   public static final String MGR_NAME="name";
   public static final String MGR_CATEGORY="category";
   public static final String MGR_CREATED_TIME_MS="createdTime_ms";
   public static final String MGR_VERSION="version";
   public static final String MGR_ENABLED="enabled";

   public static final String[] POSSIBLE_MGR_KEYS = {
      SharkConstants.MGR_PACKAGE_ID,
         SharkConstants.MGR_PROCESS_DEFINITION_ID,
         SharkConstants.MGR_NAME,
         SharkConstants.MGR_CATEGORY,
         SharkConstants.MGR_VERSION,
         SharkConstants.MGR_ENABLED};

   public static final List possibleMgrKeys = Collections.unmodifiableList(Arrays.asList(SharkConstants.POSSIBLE_MGR_KEYS));


   // WfResourceIterator
   public static final String RES_USERNAME="username";
   public static final String RES_NO_OF_ASSIGNMENTS="noOfAssignments";

   public static final String[] POSSIBLE_RES_KEYS = {
      SharkConstants.RES_USERNAME,
         SharkConstants.RES_NO_OF_ASSIGNMENTS};

   public static final List possibleResKeys = Collections.unmodifiableList(Arrays.asList(SharkConstants.POSSIBLE_RES_KEYS));

   // WfProcessIterator
   public static final String PROC_KEY="key";
   public static final String PROC_NAME="name";
   public static final String PROC_PRIORITY="priority";
   public static final String PROC_DESCRIPTION="description";
   public static final String PROC_STATE="state";
   public static final String PROC_REQUESTER_ID="requesterId";
   public static final String PROC_CREATED_TIME_MS="createdTime_ms";
   public static final String PROC_START_TIME_MS="startTime_ms";
   public static final String PROC_LAST_STATE_TIME_MS="lastStateTime_ms";
   public static final String PROC_ACTIVE_ACTIVITIES_NO="activeActivitiesNo";
   public static final String PROC_CONTEXT_="context_";
   public static final String PROC_MGR_CATEGORY = "category";
   public static final String PROC_MGR_VERSION = "version";
   public static final String PROC_MGR_PACKAGE_ID = "packageId";
   public static final String PROC_MGR_PROCESS_DEFINITION_ID = "processDefinitionId";
   public static final String PROC_MGR_ENABLED = "enabled";
   public static final String PROC_MGR_NAME = "mgrName";

   public static final String PROC_REQUESTER_RESOURCE="resourceRequesterId";

   public static final String[] POSSIBLE_PROC_KEYS = {
      SharkConstants.PROC_KEY,
         SharkConstants.PROC_NAME,
         SharkConstants.PROC_PRIORITY,
         SharkConstants.PROC_DESCRIPTION,
         SharkConstants.PROC_STATE,
         SharkConstants.PROC_REQUESTER_ID,
         SharkConstants.PROC_START_TIME_MS,
         SharkConstants.PROC_LAST_STATE_TIME_MS,
         SharkConstants.PROC_ACTIVE_ACTIVITIES_NO,
         SharkConstants.PROC_CONTEXT_};

   public static final List possibleProcKeys = Collections.unmodifiableList(Arrays.asList(SharkConstants.POSSIBLE_PROC_KEYS));

   // WfActivityIterator
   public static final String ACT_KEY="key";
   public static final String ACT_NAME="name";
   public static final String ACT_PRIORITY="priority";
   public static final String ACT_DESCRIPTION="description";
   public static final String ACT_STATE="state";
   public static final String ACT_ACTIVITY_SET_DEFINITION_ID="activitySetDefinitionId";
   public static final String ACT_DEFINITION_ID="definitionId";
   public static final String ACT_ACTIVATED_TIME_MS="activatedTime_ms";
   public static final String ACT_LAST_STATE_TIME_MS="lastStateTime_ms";
   public static final String ACT_RESOURCE_USERNAME="resourceUsername";
   public static final String ACT_ACCEPTED="accepted";
   public static final String ACT_ACCEPTED_TIME_MS="acceptedTime_ms";
   public static final String ACT_CONTEXT_="context_";
   public static final String ACT_MGR_PACKAGE_ID = "packageId";
   public static final String ACT_MGR_PROCESS_DEFINITION_ID = "processDefinitionId";
   public static final String ACT_MGR_NAME = "mgrName";
   public static final String ACT_MGR_VERSION = "version";
   public static final String ACT_MGR_ENABLED = "enabled";
   public static final String ACT_PROC_STATE = "pState";
   public static final String ACT_PROC_KEY = "pId";
   public static final String ACT_PROC_NAME = "pName";
   public static final String ACT_PROC_PRIORITY = "pPriority";
   public static final String ACT_PROC_DESCRIPTION = "pDescription";
   public static final String ACT_PROC_REQUESTER_ID = "requesterId";
   public static final String ACT_PROC_CREATED_TIME_MS = "createdTime_ms";
   public static final String ACT_PROC_START_TIME_MS = "startTime_ms";
   public static final String ACT_PROC_LAST_STATE_TIME_MS = "pLastStateTime_ms";
   public static final String ACT_PROC_CONTEXT_ = "pContext_";

   public static final String[] POSSIBLE_ACT_KEYS = {
      SharkConstants.ACT_KEY,
         SharkConstants.ACT_NAME,
         SharkConstants.ACT_PRIORITY,
         SharkConstants.ACT_DESCRIPTION,
         SharkConstants.ACT_STATE,
         SharkConstants.ACT_ACTIVITY_SET_DEFINITION_ID,
         SharkConstants.ACT_DEFINITION_ID,
         SharkConstants.ACT_ACTIVATED_TIME_MS,
         SharkConstants.ACT_LAST_STATE_TIME_MS,
         SharkConstants.ACT_RESOURCE_USERNAME,
         SharkConstants.ACT_ACCEPTED,
         SharkConstants.ACT_ACCEPTED_TIME_MS,
         SharkConstants.PROC_CONTEXT_};

   public static final List possibleActKeys = Collections.unmodifiableList(Arrays.asList(SharkConstants.POSSIBLE_ACT_KEYS));

   // WfAssignmentIterator
   public static final String ASS_PROCESS_ID="processId";
   public static final String ASS_RESOURCE_USERNAME="resourceUsername";
   public static final String ASS_ACCEPTED="accepted";
   public static final String ASS_PACKAGE_ID="packageId";
   public static final String ASS_PACKAGE_VERSION="packageVersion";
   public static final String ASS_PROCESS_DEFINITION_ID="processDefinitionId";
   public static final String ASS_ACTIVITY_SET_DEFINITION_ID="activitySetDefinitionId";
   public static final String ASS_ACTIVITY_DEFINITION_ID="activityDefinitionId";
   public static final String ASS_ACTIVITY_ID="activityId";

   public static final String[] POSSIBLE_ASS_KEYS = {
      SharkConstants.ASS_PROCESS_ID,
         SharkConstants.ASS_RESOURCE_USERNAME,
         SharkConstants.ASS_ACCEPTED,
         SharkConstants.ASS_PACKAGE_ID,
         SharkConstants.ASS_PACKAGE_VERSION,
         SharkConstants.ASS_PROCESS_DEFINITION_ID,
         SharkConstants.ASS_ACTIVITY_SET_DEFINITION_ID,
         SharkConstants.ASS_ACTIVITY_DEFINITION_ID};

   public static final List possibleAssKeys = Collections.unmodifiableList(Arrays.asList(SharkConstants.POSSIBLE_ASS_KEYS));

   // WfEventAuditIterator
   public static final String EA_TIME_STAMP="time_stamp";
   public static final String EA_EVENT_TYPE="event_type";
   public static final String EA_ACTIVITY_KEY="activity_key";
   public static final String EA_ACTIVITY_NAME="activity_name";
   public static final String EA_PROCESS_KEY="process_key";
   public static final String EA_PROCESS_NAME="process_name";
   public static final String EA_PROCESS_MGR_NAME="process_mgr_name";
   public static final String EA_PROCESS_MGR_VERSION="process_mgr_version";
   public static final String EA_PACKAGE_ID="package_id";
   public static final String EA_PROCESS_DEFINITION_ID="process_definition_id";
   public static final String EA_ACTIVITY_SET_DEFINITION_ID="activity_set_definition_id";
   public static final String EA_ACTIVITY_DEFINITION_ID="activity_definition_id";

   public static final String[] POSSIBLE_EA_KEYS = {
      SharkConstants.EA_TIME_STAMP,
         SharkConstants.EA_EVENT_TYPE,
         SharkConstants.EA_ACTIVITY_KEY,
         SharkConstants.EA_ACTIVITY_NAME,
         SharkConstants.EA_PROCESS_KEY,
         SharkConstants.EA_PROCESS_NAME,
         SharkConstants.EA_PROCESS_MGR_NAME,
         SharkConstants.EA_PROCESS_MGR_VERSION,
         SharkConstants.EA_PACKAGE_ID,
         SharkConstants.EA_PROCESS_DEFINITION_ID,
         SharkConstants.EA_ACTIVITY_SET_DEFINITION_ID,
         SharkConstants.EA_ACTIVITY_DEFINITION_ID};

   public static final List possibleEAKeys = Collections.unmodifiableList(Arrays.asList(SharkConstants.POSSIBLE_EA_KEYS));

   // CreateProcessEvent
   public static final String CEA_P_ACTIVITY_KEY="p_activity_key";
   public static final String CEA_P_PROCESS_KEY="p_process_key";
   public static final String CEA_P_PROCESS_NAME="p_process_name";
   public static final String CEA_P_PROCESS_MGR_NAME="p_process_mgr_name";
   public static final String CEA_P_PROCESS_MGR_VERSION="p_process_mgr_version";
   public static final String CEA_P_PACKAGE_ID="p_package_id";
   public static final String CEA_P_PROCESS_DEFINITION_ID="p_process_definition_id";
   public static final String CEA_P_ACTIVITY_SET_DEFINITION_ID="p_activity_set_definition_id";
   public static final String CEA_P_ACTIVITY_DEFINITION_ID="p_activity_definition_id";

   public static final String[] POSSIBLE_CEA_KEYS = {
         SharkConstants.CEA_P_ACTIVITY_KEY,
         SharkConstants.CEA_P_PROCESS_KEY,
         SharkConstants.CEA_P_PROCESS_NAME,
         SharkConstants.CEA_P_PROCESS_MGR_NAME,
         SharkConstants.CEA_P_PROCESS_MGR_VERSION,
         SharkConstants.CEA_P_PACKAGE_ID,
         SharkConstants.CEA_P_PROCESS_DEFINITION_ID,
         SharkConstants.CEA_P_ACTIVITY_SET_DEFINITION_ID,
         SharkConstants.CEA_P_ACTIVITY_DEFINITION_ID};

   public static final List possibleCEAKeys = Collections.unmodifiableList(Arrays.asList(SharkConstants.POSSIBLE_CEA_KEYS));

   // DataEvent
   public static final String DEA_OLD_DATA_="old_data";
   public static final String DEA_NEW_DATA_="new_data_";

   public static final String[] POSSIBLE_DEA_KEYS = {
         SharkConstants.DEA_OLD_DATA_,
         SharkConstants.DEA_NEW_DATA_};

   public static final List possibleDEAKeys = Collections.unmodifiableList(Arrays.asList(SharkConstants.POSSIBLE_DEA_KEYS));

   // StateEvent
   public static final String SEA_OLD_STATE="old_state";
   public static final String SEA_NEW_STATE="new_state";

   public static final String[] POSSIBLE_SEA_KEYS = {
         SharkConstants.SEA_OLD_STATE,
         SharkConstants.SEA_NEW_STATE};

   public static final List possibleSEAKeys = Collections.unmodifiableList(Arrays.asList(SharkConstants.POSSIBLE_SEA_KEYS));

   // AssignmentEvent
   public static final String AEA_OLD_RES="old_resource_key";
   public static final String AEA_NEW_RES="new_resource_key";
   public static final String AEA_IS_ACCEPTED="is_accepted";

   public static final String[] POSSIBLE_AEA_KEYS = {
         SharkConstants.AEA_OLD_RES,
         SharkConstants.AEA_NEW_RES,
         SharkConstants.AEA_IS_ACCEPTED};

   public static final List possibleAEAKeys = Collections.unmodifiableList(Arrays.asList(SharkConstants.POSSIBLE_AEA_KEYS));

   public static final String REMOTE_SUBPROCESS_EVAL_PARAM="#REMOTE_SUBPROCESS_EVAL_PARAM#";

}




