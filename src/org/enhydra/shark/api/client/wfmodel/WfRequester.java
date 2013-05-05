package org.enhydra.shark.api.client.wfmodel;


import org.enhydra.shark.api.client.wfbase.*;
import org.enhydra.shark.api.SharkTransaction;

import java.io.Serializable;

/**
 OMG definition: WfRequester is the interface that has a direct concern with the execution and results
 of a workflow process - it represents the request for some work to be done. Its
 performer, a WfProcess, is expected to handle its request and communicate significant
 status changes; in particular to inform the requester when it has completed performing
 the requested work. A single requester can have many processes associated with it.
 <p>Often WfRequester will also be the interface to the object that starts the process. As a
 process starter some of the control actions on the process include setting up the
 context, starting the process, and getting results and status.
 There are two usage scenarios for the association of a WfProcess with a WfRequester:
 <p>1. Nesting of workflow processes - a WfActivity can be refined into a WfRequester
 and may therefore request that a WfProcess be its performer (i.e., implementation).
 In this case, the WfActivity would be registered as the requester with the
 implementing sub-process when the WfProcess is created and would receive
 notifications of status changes of that sub-process; upon completion of the subprocess,
 the WfActivity would enter completed state.
 <p>2. Linking a workflow process to another (initiating or controlling) application. When
 used as a linked process the requester should be a WfRequester, which is not the
 linking WfActivity. Requesters that are not activities are roles or adapters for
 external clients.
 <p>We extended OMG's interface by duplicating methods, and adding additional
 parameter that represents transaction. If you use methods without SharkTransaction
 parameter, the transaction will be implicitly created, and if you use it with
 SharkTransaction parameter you must obey to some rules explained in HowTo documentation.
 <p> Also, we require that WfRequester implement Serializable interface.
 */
public interface WfRequester extends BaseBusinessObject, Serializable {

   /**
    Zero or more WfProcesses can be associated with a WfRequester. A requester is
    associated with a WfProcess when the process is created.
    <p>The following operation provide the information about the number of
    WfProcess items currently associated with a WfRequester.
    */
   int how_many_performer () throws BaseException;
   /**
    Zero or more WfProcesses can be associated with a WfRequester. A requester is
    associated with a WfProcess when the process is created.
    <p>The following operation provide the information about the number of
    WfProcess items currently associated with a WfRequester.
    */
   int how_many_performer (SharkTransaction t) throws BaseException;

   /**
    Zero or more WfProcesses can be associated with a WfRequester. A requester is
    associated with a WfProcess when the process is created.
    <p>The following operation returns iterator for qurying associated processes
    based on some criteria.
    */
   WfProcessIterator get_iterator_performer () throws BaseException;

   /**
    Zero or more WfProcesses can be associated with a WfRequester. A requester is
    associated with a WfProcess when the process is created.
    <p>The following operation returns iterator for qurying associated processes
    based on some criteria.
    */
   WfProcessIterator get_iterator_performer (SharkTransaction t) throws BaseException;

   /**
    Zero or more WfProcesses can be associated with a WfRequester. A requester is
    associated with a WfProcess when the process is created.
    <p>The following operation returns max_number of WfProcess objects associated
    with an WfRequester. If max_number is less or eaqual to zero, or it is greater
    than the number of existing processes, all associated WfProcess objects will
    be returned.
    */
   WfProcess[] get_sequence_performer (int max_number) throws BaseException;

   /**
    Zero or more WfProcesses can be associated with a WfRequester. A requester is
    associated with a WfProcess when the process is created.
    <p>The following operation returns max_number of WfProcess objects associated
    with an WfRequester. If max_number is less or eaqual to zero, or it is greater
    than the number of existing processes, all associated WfProcess objects will
    be returned.
    */
   WfProcess[] get_sequence_performer (SharkTransaction t,int max_number) throws BaseException;

   /**
    Zero or more WfProcesses can be associated with a WfRequester. A requester is
    associated with a WfProcess when the process is created.
    <p>The following operation returns true if given process is associated with WfRequester.
    */
   boolean is_member_of_performer (WfProcess member) throws BaseException;

   /**
    Zero or more WfProcesses can be associated with a WfRequester. A requester is
    associated with a WfProcess when the process is created.
    <p>The following operation returns true if given process is associated with WfRequester.
    */
   boolean is_member_of_performer (SharkTransaction t,WfProcess member) throws BaseException;

   /**
    The following operation is used by WfProcess to notify its requester of workflow
    events. In particular the WfProcess must notify the requester of complete, terminate, or
    abort events or the transition to a closed state.
    <p>The workflow event contains the source of the event; an InvalidPerformer exception
    is raised if the source of the event is not a performer associated with the WfRequester.
    */
   void receive_event (WfEventAudit event) throws BaseException, InvalidPerformer;

   /**
    The following operation is used by WfProcess to notify its requester of workflow
    events. In particular the WfProcess must notify the requester of complete, terminate, or
    abort events or the transition to a closed state.
    <p>The workflow event contains the source of the event; an InvalidPerformer exception
    is raised if the source of the event is not a performer associated with the WfRequester.
    */
   void receive_event (SharkTransaction t,WfEventAudit event) throws BaseException, InvalidPerformer;
} // interface WfRequester
