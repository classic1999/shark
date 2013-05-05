package org.enhydra.shark.api.client.wfmodel;


import org.enhydra.shark.api.client.wfbase.*;

import org.enhydra.shark.api.client.timebase.*;

import org.enhydra.shark.api.SharkTransaction;

import java.util.Map;

/**
 OMG definition: WfExecutionObject is an abstract base interface that defines common attributes,
 states, and operations for WfProcess and WfActivity.
 It provides the capability to get and set and internal states. Operations are provided to
 get the current state and to make a transition from the current state into another state.
 Operations are also provided for specific state transitions. These operations are
 suspend, resume, terminate, and abort. States returned by these operations should not
 be confused with the state of the process which is calculated by the top level
 WfProcess. States returned by these operations pertain only to the object they are
 returned from. For example, regardless of what activity is currently enabled, a process
 as a whole can be paused and resumed. The propagation of state change of a
 WfProcess object down to WfActivity objects or subprocesses is implementation and
 process definition dependent.
 <p>The interface includes name, description, priority, and key attributes. It also provides
 an operation for monitoring WfExecutionObject executions by returning, based on filter
 specified, event audit records that represent the history of the execution. Other
 operations include methods for getting and setting context.
 <p>We extended OMG's interface by duplicating methods, and adding additional
 parameter that represents transaction. If you use methods without SharkTransaction
 parameter, the transaction will be implicitly created, and if you use it with
 SharkTransaction parameter you must obey to some rules explained in HowTo documentation.
 */
public interface WfExecutionObject extends BaseBusinessObject {//, PersistenceInterface
   /** Returns the basic state of execution object: open or closed. */
   workflow_stateType workflow_state () throws BaseException;
   /** Returns the basic state of execution object: open or closed. */
   workflow_stateType workflow_state (SharkTransaction t) throws BaseException;

   /** Returns the sub-state of execution object that is in open state: running or not running. */
   while_openType while_open () throws BaseException;
   /** Returns the sub-state of execution object that is in open state: running or not running. */
   while_openType while_open (SharkTransaction t) throws BaseException;

   /** Returns the sub-state of execution object that is in open.not_running state: not started or suspended. */
   why_not_runningType why_not_running () throws BaseException;
   /** Returns the sub-state of execution object that is in open.not_running state: not started or suspended. */
   why_not_runningType why_not_running (SharkTransaction t) throws BaseException;

   /** Returns the sub-state of execution object that is in closed state: completed, aborted or terminated. */
   how_closedType how_closed () throws BaseException;
   /** Returns the sub-state of execution object that is in closed state: completed, aborted or terminated. */
   how_closedType how_closed (SharkTransaction t) throws BaseException;

   /**
    The following operations support access to a potentially extended set of states; a state
    is represented by a dot-notation representing hierarchical states (e.g., open.running).
    <p>Returns a list of all the valid states that can be reached from the current state. For
    example, open.running and closed.terminated would be in the list of
    valid states if the current state was open.not_running.not_started - open.not_running.suspended
    probably would not be in that list.
    */
   String[] valid_states () throws BaseException;

   /**
    The following operations support access to a potentially extended set of states; a state
    is represented by a dot-notation representing hierarchical states (e.g., open.running).
    <p>Returns a list of all the valid states that can be reached from the current state. For
    example, open.running and closed.terminated would be in the list of
    valid states if the current state was open.not_running.not_started - open.not_running.suspended
    probably would not be in that list.
    */
   String[] valid_states (SharkTransaction t) throws BaseException;

   /** Gets the current state of the object. */
   String state () throws BaseException;
   /** Gets the current state of the object. */
   String state (SharkTransaction t) throws BaseException;

   /**
    Updates the current state of the execution object. As a result the state of execution
    objects associated with this execution object might be updated, too.
    * @throws   InvalidState raised when the new_state is not a valid state for the execution object
    * @throws   TransitionNotAllowed raised when the transition from the current state to new_state is not allowed.
    * @throws   BaseException
    */
   void change_state (String new_state) throws BaseException, InvalidState, TransitionNotAllowed;
   /**
    Updates the current state of the execution object. As a result the state of execution
    objects associated with this execution object might be updated, too.
    * @throws   InvalidState raised when the new_state is not a valid state for the execution object
    * @throws   TransitionNotAllowed raised when the transition from the current state to new_state is not allowed.
    * @throws   BaseException
    */
   void change_state (SharkTransaction t,String new_state) throws BaseException, InvalidState, TransitionNotAllowed;

   /** Returns human readable, descriptive identifier of the execution object. */
   String name () throws BaseException;
   /** Returns human readable, descriptive identifier of the execution object. */
   String name (SharkTransaction t) throws BaseException;

   /** Sets the human readable, descriptive identifier of the execution object. */
   void set_name (String new_value) throws BaseException;
   /** Sets the human readable, descriptive identifier of the execution object. */
   void set_name (SharkTransaction t,String new_value) throws BaseException;

   /**
    Gets the identifier of the execution object. The key of a WfProcess is unique among the set of
    all WfProcesses created by a particular WfProcessMgr; the key of a WfActivity is
    unique within the set of all WfActivities contained in a particular WfProcess. A key is
    assigned to the execution object by its WfProcessMgr when it is created.
    <p>The key of a workflow object should not be confused with an object identifier. It is
    used for reference to the process or activity independently of the lifetime of the
    execution object.
    */
   String key () throws BaseException;
   /**
    Gets the identifier of the execution object. The key of a WfProcess is unique among the set of
    all WfProcesses created by a particular WfProcessMgr; the key of a WfActivity is
    unique within the set of all WfActivities contained in a particular WfProcess. A key is
    assigned to the execution object by its WfProcessMgr when it is created.
    <p>The key of a workflow object should not be confused with an object identifier. It is
    used for reference to the process or activity independently of the lifetime of the
    execution object.
    */
   String key (SharkTransaction t) throws BaseException;

   /** Returns description of the execution object. */
   String description () throws BaseException;
   /** Returns description of the execution object. */
   String description (SharkTransaction t) throws BaseException;

   /** Sets description of the execution object. */
   void set_description (String new_value) throws BaseException;
   /** Sets description of the execution object. */
   void set_description (SharkTransaction t,String new_value) throws BaseException;

   /**
    The process context is described by a set of named properties; the following
    operation support access to the context of an execution object. The Map structure
    identifies a set of property names and values matching the signature of the execution
    object. The signature of a WfProcess can be obtained using the get_context_signature
    operation provided by the WfProcessMgr of the process.
    */
   Map process_context () throws BaseException;

   /**
    The process context is described by a set of named properties; the following
    operation support access to the context of an execution object. The Map structure
    identifies a set of property names and values matching the signature of the execution
    object. The signature of a WfProcess can be obtained using the get_context_signature
    operation provided by the WfProcessMgr of the process.
    */
   Map process_context (SharkTransaction t) throws BaseException;

   /**
    Sets the process relevant data that define the context of the execution object.
    The process context is described by a set of named properties. The Map structure
    identifies a set of property names and values matching the signature of the execution
    object. The signature of a WfProcess can be obtained using the get_context_signature
    operation provided by the WfProcessMgr of the process.
    <p>When this method has been called, only those name-value pairs in the parameter
    will be set. Several set_process_context() calls could be used to set the entire context.
    *
    * @param    new_value           a  Map
    *
    * @throws   BaseException
    * @throws   InvalidData raised when an update request does not match this
    signature.
    * @throws   UpdateNotAllowed raised when the implementation of the WfM
    Facility or the specific workflow process does not allow an update of the context.
    */
   void set_process_context (Map new_value) throws BaseException, InvalidData, UpdateNotAllowed;

   /**
    Sets the process relevant data that define the context of the execution object.
    The process context is described by a set of named properties. The Map structure
    identifies a set of property names and values matching the signature of the execution
    object. The signature of a WfProcess can be obtained using the get_context_signature
    operation provided by the WfProcessMgr of the process.
    <p>When this method has been called, only those name-value pairs in the parameter
    will be set. Several set_process_context() calls could be used to set the entire context.
    *
    * @param    new_value           a  Map
    *
    * @throws   BaseException
    * @throws   InvalidData raised when an update request does not match this
    signature.
    * @throws   UpdateNotAllowed raised when the implementation of the WfM
    Facility or the specific workflow process does not allow an update of the context.
    */
   void set_process_context (SharkTransaction t,Map new_value) throws BaseException, InvalidData, UpdateNotAllowed;

   /**
    Returns relative priority of the execution element in the set of all execution
    objects of a given type.
    <p>Valid values are numbers between one and five, with three being 'normal' and
    one as the 'highest' priority.
    */
   short priority () throws BaseException;

   /**
    Returns relative priority of the execution element in the set of all execution
    objects of a given type.
    <p>Valid values are numbers between one and five, with three being 'normal' and
    one as the 'highest' priority.
    */
   short priority (SharkTransaction t) throws BaseException;

   /**
    Sets the relative priority of the execution element in the set of all execution
    objects of a given type.
    <p>Valid values are numbers between one and five, with three being 'normal' and
    one as the 'highest' priority.
    <p>A request for update of the priority will raise an exception when the
    specified priority is out of range, or when the priority cannot be updated.
    */
   void set_priority (short new_value) throws BaseException;

   /**
    Sets the relative priority of the execution element in the set of all execution
    objects of a given type.
    <p>Valid values are numbers between one and five, with three being 'normal' and
    one as the 'highest' priority.
    <p>A request for update of the priority will raise an exception when the
    specified priority is out of range, or when the priority cannot be updated.
    */
   void set_priority (SharkTransaction t,short new_value) throws BaseException;

   /**
    Requests enactment of a suspended execution object to be resumed. The state is set to
    open.running (or a substate) from open.not_running.suspended.
    * @throws   CannotResume raised when the execution object cannot be resumed.
    For example, resuming a WfActivity might not be allowed when the containing
    WfProcess is suspended.
    * @throws   NotSuspended raised when the object is not suspended.
    * @throws   BaseException
    */
   void resume () throws BaseException, CannotResume, NotSuspended;

   /**
    Requests enactment of a suspended execution object to be resumed. The state is set to
    open.running (or a substate) from open.not_running.suspended.
    * @throws   CannotResume raised when the execution object cannot be resumed.
    For example, resuming a WfActivity might not be allowed when the containing
    WfProcess is suspended.
    * @throws   NotSuspended raised when the object is not suspended.
    * @throws   BaseException
    */
   void resume (SharkTransaction t) throws BaseException, CannotResume, NotSuspended;

   /**
    Requests enactment of an execution object to be suspended. The state is set to
    open.not_running.suspended (or one of its substates).
    *
    * @throws   CannotSuspend exception is raised when the execution object cannot be
    suspended. For example, an implementation of the WfM Facility might not support
    suspension of a WfActivity.
    * @throws   NotRunning raised when the object is not running.
    * @throws   AlreadySuspended raised when the object is already suspended.
    * @throws   BaseException
    */
   void suspend () throws BaseException, CannotSuspend, NotRunning, AlreadySuspended;

   /**
    Requests enactment of an execution object to be suspended. The state is set to
    open.not_running.suspended (or one of its substates).
    *
    * @throws   CannotSuspend exception is raised when the execution object cannot be
    suspended. For example, an implementation of the WfM Facility might not support
    suspension of a WfActivity.
    * @throws   NotRunning raised when the object is not running.
    * @throws   AlreadySuspended raised when the object is already suspended.
    * @throws   BaseException
    */
   void suspend (SharkTransaction t) throws BaseException, CannotSuspend, NotRunning, AlreadySuspended;

   /**
    Requests enactment of an execution object to be terminated before its normal
    completion. A terminate request is different from an abort request in its effect of
    execution object associated with the current execution object.
    The state is set to closed.terminated (or one of its substates) from open.running (or
    one of its substates).
    * @throws   CannotStop raised when the execution object cannot be terminated;
    for example, termination of a WfActivity might not be allowed when its implementation
    is still active and cannot be terminated.
    * @throws   NotRunning raised when the object is not running.
    * @throws   BaseException
    */
   void terminate () throws BaseException, CannotStop, NotRunning;

   /**
    Requests enactment of an execution object to be terminated before its normal
    completion. A terminate request is different from an abort request in its effect of
    execution object associated with the current execution object.
    The state is set to closed.terminated (or one of its substates) from open.running (or
    one of its substates).
    * @throws   CannotStop raised when the execution object cannot be terminated;
    for example, termination of a WfActivity might not be allowed when its implementation
    is still active and cannot be terminated.
    * @throws   NotRunning raised when the object is not running.
    * @throws   BaseException
    */
   void terminate (SharkTransaction t) throws BaseException, CannotStop, NotRunning;

   /**
    Requests enactment of a suspended execution object to be aborted before its normal
    completion. The state is set to closed.aborted.
    * @throws   CannotStop raised when the execution object cannot be aborted.
    * @throws   NotRunning raised when the object is not running.
    * @throws   BaseException
    *
    */
   void abort () throws BaseException, CannotStop, NotRunning;

   /**
    Requests enactment of a suspended execution object to be aborted before its normal
    completion. The state is set to closed.aborted.
    * @throws   CannotStop raised when the execution object cannot be aborted.
    * @throws   NotRunning raised when the object is not running.
    * @throws   BaseException
    *
    */
   void abort (SharkTransaction t) throws BaseException, CannotStop, NotRunning;

   /**
    Zero or more WfEventAudit items can be associated with an execution object. An event
    audit item is generated (and associated with the source object) for each workflow
    relevant status change (change of state, context or result and change of resource
    assignment) of a WfExecutionObject. Status changes can be explicitly triggered by
    operations that request a change of the objects status or implicitly by the workflow
    process logic. We will indicate which operations trigger generation of WfEventAudit
    items.
    <p>The following operation provide the information about the number of
    WfEventAudit items currently associated with a WfExecutionObject.
    */
   int how_many_history () throws BaseException, HistoryNotAvailable;

   /**
    Zero or more WfEventAudit items can be associated with an execution object. An event
    audit item is generated (and associated with the source object) for each workflow
    relevant status change (change of state, context or result and change of resource
    assignment) of a WfExecutionObject. Status changes can be explicitly triggered by
    operations that request a change of the objects status or implicitly by the workflow
    process logic. We will indicate which operations trigger generation of WfEventAudit
    items.
    <p>The following operation provide the information about the number of
    WfEventAudit items currently associated with a WfExecutionObject.
    */
   int how_many_history (SharkTransaction t) throws BaseException, HistoryNotAvailable;

   /**
    Zero or more WfEventAudit items can be associated with an execution object. An event
    audit item is generated (and associated with the source object) for each workflow
    relevant status change (change of state, context or result and change of resource
    assignment) of a WfExecutionObject. Status changes can be explicitly triggered by
    operations that request a change of the objects status or implicitly by the workflow
    process logic. We will indicate which operations trigger generation of WfEventAudit
    items.
    <p>The following operation returns iterator for qurying associated event audits
    based on some criteria.
    */
   WfEventAuditIterator get_iterator_history (String query, Map names_in_query) throws BaseException, HistoryNotAvailable;

   /**
    Zero or more WfEventAudit items can be associated with an execution object. An event
    audit item is generated (and associated with the source object) for each workflow
    relevant status change (change of state, context or result and change of resource
    assignment) of a WfExecutionObject. Status changes can be explicitly triggered by
    operations that request a change of the objects status or implicitly by the workflow
    process logic. We will indicate which operations trigger generation of WfEventAudit
    items.
    <p>The following operation returns iterator for qurying associated event audits
    based on some criteria.
    */
   WfEventAuditIterator get_iterator_history (SharkTransaction t,String query, Map names_in_query) throws BaseException, HistoryNotAvailable;

   /**
    Zero or more WfEventAudit items can be associated with an execution object. An event
    audit item is generated (and associated with the source object) for each workflow
    relevant status change (change of state, context or result and change of resource
    assignment) of a WfExecutionObject. Status changes can be explicitly triggered by
    operations that request a change of the objects status or implicitly by the workflow
    process logic. We will indicate which operations trigger generation of WfEventAudit
    items.
    <p>The following operation returns max_number of WfEventAudit objects
    associated with an WfExecutionObject. If max_number is less or eaqual to zero, or it is
    greater than the number of existing event audits, all associated WfEventAudit
    objects will be returned.
    */
   WfEventAudit[] get_sequence_history (int max_number) throws BaseException, HistoryNotAvailable;

   /**
    Zero or more WfEventAudit items can be associated with an execution object. An event
    audit item is generated (and associated with the source object) for each workflow
    relevant status change (change of state, context or result and change of resource
    assignment) of a WfExecutionObject. Status changes can be explicitly triggered by
    operations that request a change of the objects status or implicitly by the workflow
    process logic. We will indicate which operations trigger generation of WfEventAudit
    items.
    <p>The following operation returns max_number of WfEventAudit objects
    associated with an WfExecutionObject. If max_number is less or eaqual to zero, or it is
    greater than the number of existing event audits, all associated WfEventAudit
    objects will be returned.
    */
   WfEventAudit[] get_sequence_history (SharkTransaction t,int max_number) throws BaseException, HistoryNotAvailable;

   /**
    Zero or more WfEventAudit items can be associated with an execution object. An event
    audit item is generated (and associated with the source object) for each workflow
    relevant status change (change of state, context or result and change of resource
    assignment) of a WfExecutionObject. Status changes can be explicitly triggered by
    operations that request a change of the objects status or implicitly by the workflow
    process logic. We will indicate which operations trigger generation of WfEventAudit
    items.
    <p>The following operation returns true if given event audit is associated with
    WfExecutionObject.
    */
   boolean is_member_of_history (WfExecutionObject member) throws BaseException;

   /**
    Zero or more WfEventAudit items can be associated with an execution object. An event
    audit item is generated (and associated with the source object) for each workflow
    relevant status change (change of state, context or result and change of resource
    assignment) of a WfExecutionObject. Status changes can be explicitly triggered by
    operations that request a change of the objects status or implicitly by the workflow
    process logic. We will indicate which operations trigger generation of WfEventAudit
    items.
    <p>The following operation returns true if given event audit is associated with
    WfExecutionObject.
    */
   boolean is_member_of_history (SharkTransaction t,WfExecutionObject member) throws BaseException;

   /** Returns the time when WfExecutionObject changed its state to the current one. */
   UtcT last_state_time () throws BaseException;
   /** Returns the time when WfExecutionObject changed its state to the current one. */
   UtcT last_state_time (SharkTransaction t) throws BaseException;

}
