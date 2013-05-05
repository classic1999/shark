package org.enhydra.shark.api.internal.working;


import org.enhydra.shark.api.client.wfmodel.*;

import java.util.List;
import java.util.Map;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.TransactionException;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.internal.scripting.Evaluator;
import org.enhydra.shark.api.internal.toolagent.ToolAgentGeneralException;


/**
 * @author Vladimir Puskas
 * @author Sasa Bojanic
 */
public interface WfProcessInternal extends WfExecutionObjectInternal  {

   // omg interface
   WfRequesterInternal requester (SharkTransaction t) throws BaseException;
   int how_many_step (SharkTransaction t) throws BaseException;
   //WfActivityInternal[] get_sequence_step (SharkTransaction t,int max_number) throws BaseException;
   //boolean is_member_of_step (SharkTransaction t,WfActivityInternal member) throws BaseException;
   WfProcessMgrInternal manager (SharkTransaction t) throws BaseException;
   Map result (SharkTransaction t) throws BaseException, ResultNotAvailable;
   void start (SharkTransaction t) throws BaseException, CannotStart, AlreadyRunning, ToolAgentGeneralException;
   // internal interface
   String package_id(SharkTransaction t) throws BaseException;
   String process_definition_id (SharkTransaction t) throws BaseException;
   String manager_name (SharkTransaction t) throws BaseException;
   String manager_version (SharkTransaction t) throws BaseException;
   //WfCreateProcessEventAudit get_create_process_history (SharkTransaction t) throws BaseException, HistoryNotAvailable;
   void start_activity (SharkTransaction t, String actDefId, String blockActId) throws BaseException, ToolAgentGeneralException;
   void activity_complete(SharkTransaction t, WfActivityInternal activity) throws Exception;
   void activity_terminate(SharkTransaction t, WfActivityInternal activity) throws Exception;
   void activity_abort(SharkTransaction t, WfActivityInternal activity) throws Exception;

   List getAllActivities (SharkTransaction t) throws BaseException;
   List getActiveActivities (SharkTransaction t) throws BaseException;
   WfActivityInternal getActivity (SharkTransaction t,String actId) throws BaseException;
   WfActivityInternal getActiveActivity (SharkTransaction t,String actId) throws BaseException;
   Evaluator evaluator(SharkTransaction t) throws RootException;

   void checkDeadlines (SharkTransaction trans) throws BaseException;
   void checkDeadline (SharkTransaction trans,String actId) throws BaseException;

   List getAllActiveActivitiesForBlockActivity (SharkTransaction t,String blockActivityId) throws BaseException;

   void terminateFromActivity (SharkTransaction t) throws BaseException, CannotStop, NotRunning;

   void abortFromActivity (SharkTransaction t) throws BaseException, CannotStop, NotRunning;

   void mandatoryDelete (SharkTransaction t) throws TransactionException;

   void setExternalRequesterClassName (SharkTransaction t,String reqClassName) throws BaseException;
} // interface WfProcess
