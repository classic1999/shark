package org.enhydra.shark.api.client.wfmodel;


import org.enhydra.shark.api.client.wfbase.*;


/**
 OMG definition: This interface specializes WfEventAudit by adding information related to creation of a
 WfProcess. If the process is created as a sub-process of another process that is
 synchronized with the main process via a WfActivity requester, information on the
 requester is recorded. The WfProcess that is being created is recorded as the source of
 this event.
 <p>The event_type is set to processCreated for this event.
 */
public interface WfCreateProcessEventAudit  extends WfEventAudit {

   /**
    If the requester of the newly created workflow process is a WfActivity,
    the key of that activity is recorded.
    */
   String p_activity_key () throws BaseException;


   /**
    If the requester of the newly created workflow process is a WfActivity,
    the key of the WfProcess that contains that activity is recorded.
    */
   String p_process_key () throws BaseException;

   /**
    If the requester of the newly created workflow process is a WfActivity,
    the name of the WfProcess that contains that activity is recorded.
    */
   String p_process_name () throws BaseException;

   /**
    If the requester of the newly created workflow process is a WfActivity, name
    of the process manager of the process that contains that activity is recorded.
    */
   String p_process_mgr_name () throws BaseException;

   /**
    If the requester of the newly created workflow process is a WfActivity, version
    of the process manager of the process that contains that activity is recorded.
    */
   String p_process_mgr_version () throws BaseException;
} // interface WfCreateProcessEventAudit
