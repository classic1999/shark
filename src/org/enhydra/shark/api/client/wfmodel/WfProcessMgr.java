package org.enhydra.shark.api.client.wfmodel;


import org.enhydra.shark.api.client.wfbase.*;
import org.enhydra.shark.api.SharkTransaction;

import java.util.Map;

/**
 OMG definition: A WfProcessMgr represents a template for a specific workflow process; it is used to
 create instances of a workflow process. Logically it is the factory and locator for
 WfProcess instances. It provides access to the meta information about the context a
 process requires and the result a process produces.
 A process manager is identified by its name which is unique within a given business
 domain. It could located, for example, via name using the OMG Naming Service, via
 name and other attributes (e.g., category) via the OMG Trader Service, or other
 infrastructure mechanisms.
 <p>We extended OMG's interface by duplicating methods, and adding additional
 parameter that represents transaction. If you use methods without SharkTransaction
 parameter, the transaction will be implicitly created, and if you use it with
 SharkTransaction parameter you must obey to some rules explained in HowTo documentation.
 */
public interface WfProcessMgr extends BaseBusinessObject {//, PersistenceInterface

   /**
    Zero or more WfProcesses are associated with the WfProcessMgr that was used to
    create them. The association is established when a WfProcess is created.
    <p>The following operation provide the information about the number of
    WfProcess items associated with a WfProcessMgr.
    */
   int how_many_process () throws BaseException;

   /**
    Zero or more WfProcesses are associated with the WfProcessMgr that was used to
    create them. The association is established when a WfProcess is created.
    <p>The following operation provide the information about the number of
    WfProcess items associated with a WfProcessMgr.
    */
   int how_many_process (SharkTransaction t) throws BaseException;

   /**
    Zero or more WfProcesses are associated with the WfProcessMgr that was used to
    create them. The association is established when a WfProcess is created.
    <p>The following operation returns iterator for qurying associated processes
    based on some criteria.
    */
   WfProcessIterator get_iterator_process () throws BaseException;

   /**
    Zero or more WfProcesses are associated with the WfProcessMgr that was used to
    create them. The association is established when a WfProcess is created.
    <p>The following operation returns iterator for qurying associated processes
    based on some criteria.
    */
   WfProcessIterator get_iterator_process (SharkTransaction t) throws BaseException;

   /**
    Zero or more WfProcesses are associated with the WfProcessMgr that was used to
    create them. The association is established when a WfProcess is created.
    <p>The following operation returns max_number of WfProcess objects associated
    with an WfProcessMgr. If max_number is less or eaqual to zero, or it is greater
    than the number of existing processes, all associated WfProcess objects will
    be returned.
    */
   WfProcess[] get_sequence_process (int max_number) throws BaseException;

   /**
    Zero or more WfProcesses are associated with the WfProcessMgr that was used to
    create them. The association is established when a WfProcess is created.
    <p>The following operation returns max_number of WfProcess objects associated
    with an WfProcessMgr. If max_number is less or eaqual to zero, or it is greater
    than the number of existing processes, all associated WfProcess objects will
    be returned.
    */
   WfProcess[] get_sequence_process (SharkTransaction t,int max_number) throws BaseException;

   /**
    Zero or more WfProcesses are associated with the WfProcessMgr that was used to
    create them. The association is established when a WfProcess is created.
    <p>The following operation returns true if given process is associated with WfProcessMgr.
    */
   boolean is_member_of_process (WfProcess member) throws BaseException;

   /**
    Zero or more WfProcesses are associated with the WfProcessMgr that was used to
    create them. The association is established when a WfProcess is created.
    <p>The following operation returns true if given process is associated with WfProcessMgr.
    */
   boolean is_member_of_process (SharkTransaction t,WfProcess member) throws BaseException;

   /**
    A WfProcessMgr can be enabled or disabled. This method returns its current state.
    */
   process_mgr_stateType process_mgr_state () throws BaseException;

   /**
    A WfProcessMgr can be enabled or disabled. This method returns its current state.
    */
   process_mgr_stateType process_mgr_state (SharkTransaction t) throws BaseException;

   /**
    A WfProcessMgr can be enabled or disabled. This method sets its state.
    */
   void set_process_mgr_state (process_mgr_stateType new_state) throws BaseException, TransitionNotAllowed;

   /**
    A WfProcessMgr can be enabled or disabled. This method sets its state.
    */
   void set_process_mgr_state (SharkTransaction t,process_mgr_stateType new_state) throws BaseException, TransitionNotAllowed;

   /**
    Returns the name of the process manager. The name uniquely identifies the
    process manager in a business domain. It is set when the process manager is
    initialized and cannot be modified.
    */
   String name () throws BaseException;

   /**
    Returns the name of the process manager. The name uniquely identifies the
    process manager in a business domain. It is set when the process manager is
    initialized and cannot be modified.
    */
   String name (SharkTransaction t) throws BaseException;

   /**
    Returns description of the process manager. It is set when the process manager is
    initialized and cannot be modified.
    */
   String description () throws BaseException;

   /**
    This attribute is set when the process manager is installed.
    Returns the name of the process manager. The name uniquely identifies the
    process manager in a business domain.
    */
   String description (SharkTransaction t) throws BaseException;

   /**
    The category of a process manager is used for classification of process types.
    It is set when the process manager is initialized and cannot be modified.
    */
   String category () throws BaseException;

   /**
    The category of a process manager is used for classification of process types.
    It is set when the process manager is initialized and cannot be modified.
    */
   String category (SharkTransaction t) throws BaseException;

   /**
    The version attribute of a process manager is used to distinguish between different
    versions of a process model. Note that this is a means to distinguish between different
    process managers that have the same name; it is left to the implementation to define
    the format of the version attribute. It is set when the process manager is initialized and
    cannot be modified.
    */
   String version () throws BaseException;

   /**
    The version attribute of a process manager is used to distinguish between different
    versions of a process model. Note that this is a means to distinguish between different
    process managers that have the same name; it is left to the implementation to define
    the format of the version attribute. It is set when the process manager is initialized and
    cannot be modified.
    */
   String version (SharkTransaction t) throws BaseException;

   /**
    Meta information that defines how to set the context of an instance of this
    interface is returned by this operation.
    <p>The Map entry identifies the name and the data type (represented by its string name)
    of the data item. Map contains an entry for each data item in the set of context.
    */
   Map context_signature () throws BaseException;

   /**
    Meta information that defines how to set the context of an instance of this
    interface is returned by this operation.
    <p>The Map entry identifies the name and the data type (represented by its string name)
    of the data item. Map contains an entry for each data item in the set of context
    data for the WfProcess.
    */
   Map context_signature (SharkTransaction t) throws BaseException;

   /**
    Meta information that defines how to return the result of an instance of this
    interface is returned by this operation.
    <p>The Map entry identifies the name and the data type (represented by its string name)
    of the data item. Map contains an entry for each data item in the set of result
    data for the WfProcess.
    */
   Map result_signature () throws BaseException;

   /**
    Meta information that defines how to set the context of an instance of this
    interface is returned by this operation.
    <p>The Map entry identifies the name and the data type (represented by its string name)
    of the data item. Map contains an entry for each data item in the set of context.
    */
   Map result_signature (SharkTransaction t) throws BaseException;

   /**
    This operation is used to create instances of a process model and link its requester.
    When the process is created it enters state not_running.not_started.
    <p>It is up to the implementation of the WfM Facility to decide which WfRequester
    objects to accept or not. When a WfRequester is rejected, the invoking application
    might decide not to register a WfRequester with the WfProcess.
    * @param    requester           a  WfRequester
    *
    * @return   a WfProcess
    *
    * @throws   BaseException
    * @throws   NotEnabled raised when the process manager is disabled.
    * @throws   InvalidRequester raised when a WfRequester is being identified
    that cannot be a 'parent' of instances of the process model.
    * @throws   RequesterRequired raised when the process definition requires a
    WfRequester and an invalid WfRequester is supplied in the parameter.
    *
    */
   WfProcess create_process (WfRequester requester) throws BaseException, NotEnabled, InvalidRequester, RequesterRequired;

   /**
    This operation is used to create instances of a process model and link its requester.
    When the process is created it enters state not_running.not_started.
    <p>It is up to the implementation of the WfM Facility to decide which WfRequester
    objects to accept or not. When a WfRequester is rejected, the invoking application
    might decide not to register a WfRequester with the WfProcess.
    * @param    requester           a  WfRequester
    *
    * @return   a WfProcess
    *
    * @throws   BaseException
    * @throws   NotEnabled raised when the process manager is disabled.
    * @throws   InvalidRequester raised when a WfRequester is being identified
    that cannot be a 'parent' of instances of the process model.
    * @throws   RequesterRequired raised when the process definition requires a
    WfRequester and an invalid WfRequester is supplied in the parameter.
    *
    */
   WfProcess create_process (SharkTransaction t,WfRequester requester) throws BaseException, NotEnabled, InvalidRequester, RequesterRequired;

} // interface WfProcessMgr
