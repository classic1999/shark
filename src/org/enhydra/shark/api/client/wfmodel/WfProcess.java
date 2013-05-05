package org.enhydra.shark.api.client.wfmodel;


import org.enhydra.shark.api.client.wfbase.*;
import org.enhydra.shark.api.SharkTransaction;

import java.util.*;

/**
 OMG definition: A WfProcess is the performer of a workflow request. All workflow objects that
 perform work implement this interface. This interface allows work to proceed
 asynchronously while being monitored and controlled.
 The WfProcess interface specializes WfExecutionObject interface by adding an
 operation to start the execution of the process, an operation to obtain the result
 produced by the process and relationships with WfRequester and WfActivity.
 <p>We extended OMG's interface by duplicating methods, and adding additional
 parameter that represents transaction. If you use methods without SharkTransaction
 parameter, the transaction will be implicitly created, and if you use it with
 SharkTransaction parameter you must obey to some rules explained in HowTo documentation.
 */
public interface WfProcess  extends WfExecutionObject {
   /**
    A WfProcess is created (using a WfProcessMgr) by a user or automated resource and
    associated with a WfRequester. The WfRequester may be a WfActivity or an adapter for
    external clients. WfProcess always has one WfRequester; an implementation of the
    WfM Facility may allow for re-assignment of the WfRequester associated with a
    WfProcess.
    <p>A WfProcess will inform its WfRequester about status changes such as modification of
    its state and its context using the requesters receive_event operation.
    <p>The following operation returns the requester.
    */
   WfRequester requester () throws BaseException;

   /**
    A WfProcess is created (using a WfProcessMgr) by a user or automated resource and
    associated with a WfRequester. The WfRequester may be a WfActivity or an adapter for
    external clients. WfProcess always has one WfRequester; an implementation of the
    WfM Facility may allow for re-assignment of the WfRequester associated with a
    WfProcess.
    <p>A WfProcess will inform its WfRequester about status changes such as modification of
    its state and its context using the requesters receive_event operation.
    <p>The following operation returns the requester.
    */
   WfRequester requester (SharkTransaction t) throws BaseException;

   /**
    A WfProcess is created (using a WfProcessMgr) by a user or automated resource and
    associated with a WfRequester. The WfRequester may be a WfActivity or an adapter for
    external clients. WfProcess always has one WfRequester; an implementation of the
    WfM Facility may allow for re-assignment of the WfRequester associated with a
    WfProcess.
    <p>A WfProcess will inform its WfRequester about status changes such as modification of
    its state and its context using the requesters receive_event operation.
    <p>The following operations changes the process requester.
    */
   void set_requester (WfRequester new_value) throws BaseException, CannotChangeRequester;

   /**
    A WfProcess is created (using a WfProcessMgr) by a user or automated resource and
    associated with a WfRequester. The WfRequester may be a WfActivity or an adapter for
    external clients. WfProcess always has one WfRequester; an implementation of the
    WfM Facility may allow for re-assignment of the WfRequester associated with a
    WfProcess.
    <p>A WfProcess will inform its WfRequester about status changes such as modification of
    its state and its context using the requesters receive_event operation.
    <p>The following operations changes the process requester.
    */
   void set_requester (SharkTransaction t,WfRequester new_value) throws BaseException, CannotChangeRequester;

   /**
    Zero or more WfActivities are associated with a WfProcess. The association is
    established when an activity is created as part of the enactment of the WfProcess.
    <p>The following operation provide the information about the number of active
    WfActivity items currently associated with a WfProcess.
    */
   int how_many_step () throws BaseException;

   /**
    Zero or more WfActivities are associated with a WfProcess. The association is
    established when an activity is created as part of the enactment of the WfProcess.
    <p>The following operation provide the information about the number of active
    WfActivity items currently associated with a WfProcess.
    */
   int how_many_step (SharkTransaction t) throws BaseException;

   /**
    Zero or more WfActivities are associated with a WfProcess. The association is
    established when an activity is created as part of the enactment of the WfProcess.
    <p>The following operation returns iterator for qurying associated activities
    based on some criteria.
    */
   WfActivityIterator get_iterator_step () throws BaseException;

   /**
    Zero or more WfActivities are associated with a WfProcess. The association is
    established when an activity is created as part of the enactment of the WfProcess.
    <p>The following operation returns iterator for qurying associated activities
    based on some criteria.
    */
   WfActivityIterator get_iterator_step (SharkTransaction t) throws BaseException;

   /**
    Zero or more WfActivities are associated with a WfProcess. The association is
    established when an activity is created as part of the enactment of the WfProcess.
    <p>The following operation returns max_number of WfActivity objects associated
    with an WfProcess (no matter in which state they are). If max_number is less
    or eaqual to zero, or it is greater than the number of existing activities,
    all associated WfActivity objects will be returned.
    */
   WfActivity[] get_sequence_step (int max_number) throws BaseException;

   /**
    Zero or more WfActivities are associated with a WfProcess. The association is
    established when an activity is created as part of the enactment of the WfProcess.
    <p>The following operation returns max_number of WfActivity objects associated
    with an WfProcess (no matter in which state they are). If max_number is less
    or eaqual to zero, or it is greater than the number of existing activities,
    all associated WfActivity objects will be returned.
    */
   WfActivity[] get_sequence_step (SharkTransaction t,int max_number) throws BaseException;

   /**
    Zero or more WfActivities are associated with a WfProcess. The association is
    established when an activity is created as part of the enactment of the WfProcess.
    <p>The following operation returns true if given activity is associated with WfProcess.
    */
   boolean is_member_of_step (WfActivity member) throws BaseException;

   /**
    Zero or more WfActivities are associated with a WfProcess. The association is
    established when an activity is created as part of the enactment of the WfProcess.
    <p>The following operation returns true if given activity is associated with WfProcess.
    */
   boolean is_member_of_step (SharkTransaction t,WfActivity member) throws BaseException;

   /**
    A process is associated with one WfProcessMgr; the association is established when
    the WfProcess is generated and cannot be modified. The following operation returns
    the WfProcessMgr associated with the WfProcess.
    */
   WfProcessMgr manager () throws BaseException;

   /**
    A process is associated with one WfProcessMgr; the association is established when
    the WfProcess is generated and cannot be modified. The following operation returns
    the WfProcessMgr associated with the WfProcess.
    */
   WfProcessMgr manager (SharkTransaction t) throws BaseException;

   /**
    The result produced by the WfProcess. In general the result is undefined until the
    process completes, but some processes may produce intermediate results.
    A ResultNotAvailable exception is raised when the result cannot be obtained yet.
    */
   Map result () throws BaseException, ResultNotAvailable;

   /**
    The result produced by the WfProcess. In general the result is undefined until the
    process completes, but some processes may produce intermediate results.
    A ResultNotAvailable exception is raised when the result cannot be obtained yet.
    */
   Map result (SharkTransaction t) throws BaseException, ResultNotAvailable;

   /**
    This operation is used to initiate enactment of a WfProcess. The state of the
    process is changed from open.not_running.not_started to open.running.
    * @throws   CannotStart raised when the process cannot be started (e.g.,
    because it is not properly initialized).
    * @throws   AlreadyRunning raised when the process has already been started.
    * @throws   BaseException
    */
   void start () throws BaseException, CannotStart, AlreadyRunning;

   /**
    This operation is used to initiate enactment of a WfProcess. The state of the
    process is changed from open.not_running.not_started to open.running.
    * @throws   CannotStart raised when the process cannot be started (e.g.,
    because it is not properly initialized).
    * @throws   AlreadyRunning raised when the process has already been started.
    * @throws   BaseException
    */
   void start (SharkTransaction t) throws BaseException, CannotStart, AlreadyRunning;

   /**
    This operation is used to get an iterator over WfActivity objects that are in a certain
    state. The state is an input parameter. In case an invalid state has been specified, the
    exception InvalidState is raised.
    */
   WfActivityIterator get_activities_in_state (String state) throws BaseException, InvalidState;

   /**
    This operation is used to get an iterator over WfActivity objects that are in a certain
    state. The state is an input parameter. In case an invalid state has been specified, the
    exception InvalidState is raised.
    */
   WfActivityIterator get_activities_in_state (SharkTransaction t,String state) throws BaseException, InvalidState;

} // interface WfProcess
