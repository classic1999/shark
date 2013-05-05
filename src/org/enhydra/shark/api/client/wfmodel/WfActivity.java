package org.enhydra.shark.api.client.wfmodel;


import org.enhydra.shark.api.client.wfbase.*;
import org.enhydra.shark.api.SharkTransaction;

import java.util.Map;

/**
 OMG definition:WfActivity is a step in a process that is associated, as part of an aggregation, with a
 single WfProcess. It represents a request for work in the context of the containing
 WfProcess. There can be many active WfActivity objects within a WfProcess at a given
 point in time.
 The WfActivity interface specializes WfExecutionObject with an explicit complete
 operation to signal completion of the step, and with an operation to set the result of the
 WfActivity. It also adds relationships with WfProcess and WfAssignment.
 <p>We extended OMG's interface by duplicating methods, and adding additional
 parameter that represents transaction. If you use methods without SharkTransaction
 parameter, the transaction will be implicitly created, and if you use it with
 SharkTransaction parameter you must obey to some rules explained in HowTo documentation.
 */
public interface WfActivity extends WfExecutionObject, WfRequester {

   /**
    Zero or more WfAssignments can be associated with a WfActivity. The association is
    established when the assignment is created as part of the resource selection process for
    the activity.
    <p>The following operation returns the number of WfAssignments
    associated with an activity.
    */
   int how_many_assignment () throws BaseException;

   /**
    Zero or more WfAssignments can be associated with a WfActivity. The association is
    established when the assignment is created as part of the resource selection process for
    the activity.
    <p>The following operation returns the number of WfAssignments
    associated with an activity.
    */
   int how_many_assignment (SharkTransaction t) throws BaseException;

   /**
    Zero or more WfAssignments can be associated with a WfActivity. The association is
    established when the assignment is created as part of the resource selection process for
    the activity.
    <p>The following operation returns iterator for qurying associated
    assignments based on some criteria.
    */
   WfAssignmentIterator get_iterator_assignment () throws BaseException;

   /**
    Zero or more WfAssignments can be associated with a WfActivity. The association is
    established when the assignment is created as part of the resource selection process for
    the activity.
    <p>The following operation returns iterator for qurying associated
    assignments based on some criteria.
    */
   WfAssignmentIterator get_iterator_assignment (SharkTransaction t) throws BaseException;

   /**
    Zero or more WfAssignments can be associated with a WfActivity. The association is
    established when the assignment is created as part of the resource selection process for
    the activity.
    <p>The following operation returns max_number of WfAssignment objects
    associated with an activity. If max_number is less or eaqual to zero, or it is
    greater than the number of existing assignments, all associated WfAssignments
    objects will be returned.
    */
   WfAssignment[] get_sequence_assignment (int max_number) throws BaseException;

   /**
    Zero or more WfAssignments can be associated with a WfActivity. The association is
    established when the assignment is created as part of the resource selection process for
    the activity.
    <p>The following operation returns max_number of WfAssignment objects
    associated with an activity. If max_number is less or eaqual to zero, or it is
    greater than the number of existing assignments, all associated WfAssignments
    objects will be returned.
    */
   WfAssignment[] get_sequence_assignment (SharkTransaction t,int max_number) throws BaseException;

   /**
    Zero or more WfAssignments can be associated with a WfActivity. The association is
    established when the assignment is created as part of the resource selection process for
    the activity.
    <p>The following operation returns true if given assignment is
    associated with activity.
    */
   boolean is_member_of_assignment (WfAssignment member) throws BaseException;

   /**
    Zero or more WfAssignments can be associated with a WfActivity. The association is
    established when the assignment is created as part of the resource selection process for
    the activity.
    <p>The following operation returns true if given assignment is
    associated with activity.
    */
   boolean is_member_of_assignment (SharkTransaction t,WfAssignment member) throws BaseException;

   /** This operation returns the WfProcess that this activity is a part of. */
   WfProcess container () throws BaseException;

   /** This operation returns the WfProcess that this activity is a part of. */
   WfProcess container (SharkTransaction t) throws BaseException;

   /**
    Represents the result produced by the realization of the work request represented by an
    activity. An implementation of the WfM Facility may or may not provide access to the
    result of an activity. If it does not, or if the result data are not available yet, a
    ResultNotAvailable exception is raised by the result access operation.
    */
   Map result () throws BaseException, ResultNotAvailable;

   /**
    Represents the result produced by the realization of the work request represented by an
    activity. An implementation of the WfM Facility may or may not provide access to the
    result of an activity. If it does not, or if the result data are not available yet, a
    ResultNotAvailable exception is raised by the result access operation.
    */
   Map result (SharkTransaction t) throws BaseException, ResultNotAvailable;

   /**
    The set_result operation is used to pass process data back to the workflow process.
    An InvalidData exception is raised when the data do not match the signature of the
    activity or when an invalid attempt is made to update the results of an activity; lack of
    access rights might be one of those reasons.
    */
   void set_result (Map result) throws BaseException, InvalidData;

   /**
    The set_result operation is used to pass process data back to the workflow process.
    An InvalidData exception is raised when the data do not match the signature of the
    activity or when an invalid attempt is made to update the results of an activity; lack of
    access rights might be one of those reasons.
    */
   void set_result (SharkTransaction t,Map result) throws BaseException, InvalidData;

   /**
    This operation is used by an application to signal completion of the WfActivity. It will
    be used together with the set_result operation to pass results of the activity back to
    the workflow process. A CannotComplete exception is raised when the activity
    cannot be completed yet.
    */
   void complete () throws BaseException, CannotComplete;

   /**
    This operation is used by an application to signal completion of the WfActivity. It will
    be used together with the set_result operation to pass results of the activity back to
    the workflow process. A CannotComplete exception is raised when the activity
    cannot be completed yet.
    */
   void complete (SharkTransaction t) throws BaseException, CannotComplete;

}
