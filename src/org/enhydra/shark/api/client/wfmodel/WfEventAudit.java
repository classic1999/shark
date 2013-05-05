package org.enhydra.shark.api.client.wfmodel;


import org.enhydra.shark.api.client.wfbase.*;

import org.enhydra.shark.api.client.timebase.*;
import org.enhydra.shark.api.SharkTransaction;

/**
 OMG definition: WfEventAudit provides audit records of workflow event information. It provides
 information on the source of the event and contains specific event data. Workflow
 events include state changes, change of a resource assignment, and data changes.
 Workflow events are persistent and can be accessed navigating the history relationship
 of a WfExecutionObject. Workflow audit event objects are not part of the persistent
 state of their source workflow object.
 <p>
 A workflow event audit object is created when a workflow object changes its status
 (state change, process data change or assignment change); its lifetime is not limited by
 the lifetime of the event source object. Operations for managing the retention,
 archiving, and deletion of workflow events are not specified in this specification.
 The WfEventAudit defines a set of event properties common to all workflow audit
 events. In particular, it provides an identification of the source of the event in terms of
 (business) identifiers of the workflow entities WfProcessMgr, WfProcess, and
 WfActivity.
 <p>We extended OMG's interface by duplicating method source, and adding additional
 parameter that represents transaction. If you use method without SharkTransaction
 parameter, the transaction will be implicitly created, and if you use it with
 SharkTransaction parameter you must obey to some rules explained in HowTo documentation.
 */
public interface WfEventAudit extends BaseBusinessObject { //PersistenceInterface

   /**
    A WfEventAudit can be associated with the WfExecutionObject that triggered the
    event. Event audit items are meant to provide information on the execution history of
    workflow object even after the source object has been deleted; in this case, no source
    would be associated with the WfEventAudit.
    <p>The following operation returns the source of the event, when available; if the source is
    not available, a SourceNotAvailable exception is raised.
    */
   WfExecutionObject source () throws BaseException, SourceNotAvailable;

   /**
    A WfEventAudit can be associated with the WfExecutionObject that triggered the
    event. Event audit items are meant to provide information on the execution history of
    workflow object even after the source object has been deleted; in this case, no source
    would be associated with the WfEventAudit.
    <p>The following operation returns the source of the event, when available; if the source is
    not available, a SourceNotAvailable exception is raised.
    */
   WfExecutionObject source (SharkTransaction t) throws BaseException, SourceNotAvailable;

   /**
    Records the time the status change of the source occurred that triggered the event audit
    item to be created, using the TimeBase::UtcT data type defined by the OMG Time Service.
    */
   UtcT time_stamp () throws BaseException;

   /**
    Identifies the specific event type. The following is a set of pre-defined event types;
    implementations of the WfM Facility may decide to support additional audit event
    types.
    <ul>
    <li>processCreated - A WfProcess was created
    <li>processStateChanged - The state of a WfProcess was changed
    <li>processContextChanged - The context of a WfProcess was initialized or changed
    <li>activityStateChanged - The state of a WfActivity was changed
    <li>activityContextChanged - The context of a WfActivity was changed
    <li>activityResultChanged - The result of a WfActivity was set
    <li>activityAssigmentChanged - The status or the resource assignment of a
    WfAssignment was initialized or changed
    </ul>
    */
   String event_type () throws BaseException;

   /**
    If the event is triggered by a status change of a WfActivity, the key and the name of the
    activity is recorded with the WfEventAudit. Otherwise, the activity related attributes
    contain a NULL value.
    <p>The following operation returns the key of the WfActivity associated with the event.
    */
   String activity_key () throws BaseException;

   /**
    If the event is triggered by a status change of a WfActivity, the key and the name of the
    activity is recorded with the WfEventAudit. Otherwise, the activity related attributes
    contain a NULL value.
    <p>The following operation return the name of the WfActivity associated with the event.
    */
   String activity_name () throws BaseException;

   /**
    The key and the name of the WfProcess associated with the source of an event are
    recorded with the WfEventAudit. If the event was triggered by a WfActivity, this is the
    containing WfProcess. If it was triggered by a status change of a WfProcess, it is that
    process.
    <p>The following operation returns the key of the WfProcess associated with the event.
    */
   String process_key () throws BaseException;

   /**
    The key and the name of the WfProcess associated with the source of an event are
    recorded with the WfEventAudit. If the event was triggered by a WfActivity, this is the
    containing WfProcess. If it was triggered by a status change of a WfProcess, it is that
    process.
    <p>The following operation returns the name of the WfProcess associated with the event.
    */
   String process_name () throws BaseException;

   /**
    The WfProcessMgr associated with the workflow object that triggered the event is
    identified via its name and version. If the event was triggered by a status change of an
    activity, this is the manager of the process that contains the activity. If it was triggered
    by a status change of a process, this is the manager of that process.
    <p>The following operation returns the name of the WfProcessMgr associated with the event.
    */
   String process_mgr_name () throws BaseException;

   /**
    The WfProcessMgr associated with the workflow object that triggered the event is
    identified via its name and version. If the event was triggered by a status change of an
    activity, this is the manager of the process that contains the activity. If it was triggered
    by a status change of a process, this is the manager of that process.
    <p>The following operation returns the version of the WfProcessMgr associated with the event.
    */
   String process_mgr_version () throws BaseException;

} // interface WfEventAuditOperations
