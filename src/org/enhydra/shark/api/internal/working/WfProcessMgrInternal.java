package org.enhydra.shark.api.internal.working;

import java.util.Map;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfmodel.InvalidRequester;
import org.enhydra.shark.api.client.wfmodel.NotEnabled;
import org.enhydra.shark.api.client.wfmodel.RequesterRequired;
import org.enhydra.shark.api.client.wfmodel.TransitionNotAllowed;
import org.enhydra.shark.api.client.wfmodel.process_mgr_stateType;
import org.enhydra.shark.api.internal.working.PersistenceInterface;
/**
 * @author Vladimir Puskas
 * @author Sasa Bojanic
 */
public interface WfProcessMgrInternal extends PersistenceInterface {
   //int how_many_process (SharkTransaction t) throws BaseException;
   //WfProcessInternal[] get_sequence_process (SharkTransaction t,int max_number) throws BaseException;
   //boolean is_member_of_process (SharkTransaction t,WfProcessInternal member) throws BaseException;
   process_mgr_stateType process_mgr_state (SharkTransaction t) throws BaseException;
   void set_process_mgr_state (SharkTransaction t,process_mgr_stateType new_state) throws BaseException, TransitionNotAllowed;
   String name (SharkTransaction t) throws BaseException;
   String description (SharkTransaction t) throws BaseException;
   String category (SharkTransaction t) throws BaseException;
   String version (SharkTransaction t) throws BaseException;
   Map context_signature (SharkTransaction t) throws BaseException;
   Map result_signature (SharkTransaction t) throws BaseException;
   Map input_signature (SharkTransaction t) throws BaseException;
   WfProcessInternal create_process (SharkTransaction t,WfRequesterInternal requester) throws BaseException, NotEnabled, InvalidRequester, RequesterRequired;
   // internal interface
   String process_definition_id (SharkTransaction t) throws BaseException;
   String package_id (SharkTransaction t) throws BaseException;
   String process_definition_name (SharkTransaction t) throws BaseException;
   //java.util.List getProcessList (SharkTransaction t) throws BaseException;
} // interface WfProcessMgr
