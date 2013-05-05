package org.enhydra.shark.api.internal.working;

import org.enhydra.shark.api.client.wfmodel.*;

import java.util.Map;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.timebase.UtcT;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.internal.working.PersistenceInterface;

public interface WfExecutionObjectInternal extends PersistenceInterface {
   //List valid_states (SharkTransaction t) throws BaseException;

   String state (SharkTransaction t) throws BaseException;

   String name (SharkTransaction t) throws BaseException;

   void set_name (SharkTransaction t,String new_value) throws BaseException;

   String key (SharkTransaction t) throws BaseException;

   String description (SharkTransaction t) throws BaseException;

   void set_description (SharkTransaction t,String new_value) throws BaseException;

   Map process_context (SharkTransaction t) throws BaseException;

   void set_process_context (SharkTransaction t,Map new_value) throws BaseException, InvalidData, UpdateNotAllowed;

   short priority (SharkTransaction t) throws BaseException;

   void set_priority (SharkTransaction t,short new_value) throws BaseException;

   void resume (SharkTransaction t) throws BaseException, CannotResume, NotSuspended;

   void suspend (SharkTransaction t) throws BaseException, CannotSuspend, NotRunning, AlreadySuspended;

   void terminate (SharkTransaction t) throws BaseException, CannotStop, NotRunning;

   void abort (SharkTransaction t) throws BaseException, CannotStop, NotRunning;

   //int how_many_history (SharkTransaction t) throws BaseException, HistoryNotAvailable;

   //WfEventAudit[] get_sequence_history (SharkTransaction t,int max_number) throws BaseException, HistoryNotAvailable;

   //boolean is_member_of_history (SharkTransaction t,WfExecutionObjectInternal member) throws BaseException;

   UtcT last_state_time (SharkTransaction t) throws BaseException;


   // internal interface
   //WfDataEventAudit[] get_sequence_data_history (SharkTransaction t, int max_number) throws BaseException, HistoryNotAvailable;
   //WfStateEventAudit[] get_sequence_state_history (SharkTransaction t,int max_number) throws BaseException, HistoryNotAvailable;
   //java.util.List getHistory(SharkTransaction t) throws BaseException;

   void activateLimitAgent(SharkTransaction trans) throws BaseException;

   long getCreationTime (SharkTransaction trans) throws BaseException;

   long getStartTime (SharkTransaction trans) throws BaseException;

   Map getContext (SharkTransaction trans) throws BaseException;
}
