package org.enhydra.shark.api.internal.working;


import org.enhydra.shark.api.client.wfmodel.*;

import java.util.List;
import java.util.Map;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.internal.toolagent.ToolAgentGeneralException;

/**
 * @author Sasa Bojanic
 * @author Vladimir Puskas
 */
public interface WfActivityInternal extends WfRequesterInternal, WfExecutionObjectInternal  {

   //int how_many_assignment (SharkTransaction t) throws BaseException;
   //WfAssignmentInternal[] get_sequence_assignment (SharkTransaction t,int max_number) throws BaseException;
   //boolean is_member_of_assignment (SharkTransaction t,WfAssignmentInternal member) throws BaseException;
   void reevaluateAssignments (SharkTransaction t) throws BaseException;
   String getResourceUsername (SharkTransaction t) throws BaseException;
   List getAssignmentResourceIds (SharkTransaction t) throws BaseException;
   void updateAssignmentResourceIds (SharkTransaction t,String oldResUname,String newResUname) throws BaseException;

   WfProcessInternal container (SharkTransaction t) throws BaseException;

   Map result (SharkTransaction t) throws BaseException, ResultNotAvailable;

   void set_result (SharkTransaction t,Map result) throws BaseException, InvalidData;

   void complete (SharkTransaction t) throws BaseException, CannotComplete;


   // internal
   void finish (SharkTransaction t) throws BaseException, CannotComplete;

   void terminateFromProcess (SharkTransaction t) throws BaseException, CannotStop, NotRunning;

   void abortFromProcess (SharkTransaction t) throws BaseException, CannotStop, NotRunning;

   WfActivityInternal block_activity(SharkTransaction t) throws BaseException;

   String activity_set_definition_id (SharkTransaction t) throws BaseException;
   String activity_definition_id (SharkTransaction t) throws BaseException;
   String block_activity_id (SharkTransaction t) throws BaseException;
   String manager_name (SharkTransaction t) throws BaseException;
   String process_id (SharkTransaction t) throws BaseException;
   //WfAssignmentEventAudit[] get_sequence_assignment_history (SharkTransaction t,int max_number) throws BaseException, HistoryNotAvailable;

   //List getAssignments (SharkTransaction t) throws BaseException;
   String getPerformerId (SharkTransaction t);
   boolean isPerformerSynchronous(SharkTransaction t);
   void activate (SharkTransaction t) throws BaseException, CannotStart, AlreadyRunning;
   boolean accepted_status (SharkTransaction t) throws BaseException;
   void set_accepted_status (SharkTransaction t,boolean accepted,String resourceUname) throws BaseException, CannotAcceptSuspended;

   //List getPerformers (SharkTransaction t) throws BaseException;

   ToolAgentGeneralException getToolAgentException (SharkTransaction t);
   void setToolAgentException (SharkTransaction t,ToolAgentGeneralException tage);

   String getExceptionName (SharkTransaction t);
   void setExceptionName (SharkTransaction t,String exceptionName);

   boolean checkDeadlines (SharkTransaction t,long timeLimitBoundary,Map actsToAsyncExcNames) throws BaseException;

   List getDeadlineInfo (SharkTransaction t) throws BaseException;
}
