package org.enhydra.shark.api.client.wfmodel;


import org.enhydra.shark.api.client.wfbase.*;
import org.enhydra.shark.api.SharkTransaction;

/**
 OMG definition: WfAssignment links WfActivity objects to WfResource objects. These links represent
 real assignments for enacting the activity. This interface may be specialized by
 resource management facilities that interpret the context of the activity to create and
 negotiate assignments with resources.
 Assignments are created as part of the resource selection process before an activity
 becomes ready for execution. The lifetime of an assignment is limited by that of the
 associated activity.
 <p>We extended OMG's interface by duplicating methods, and adding additional
 parameter that represents transaction. If you use methods without SharkTransaction
 parameter, the transaction will be implicitly created, and if you use it with
 SharkTransaction parameter you must obey to some rules explained in HowTo documentation.
 <p>Also, we added two additional methods for accepting assignment and getting accepted status.
 */
public interface WfAssignment extends BaseBusinessObject {//, PersistenceInterface
   /**
    A WfAssignment is associated with one WfActivity; the association is established when
    the assignment is created as part of the resource selection process for the activity. The
    following operation returns the associated WfActivity.
    */
   WfActivity activity () throws BaseException;
   /**
    A WfAssignment is associated with one WfActivity; the association is established when
    the assignment is created as part of the resource selection process for the activity. The
    following operation returns the associated WfActivity.
    */
   WfActivity activity (SharkTransaction t) throws BaseException;

   /**
    A WfAssignment is associated with one WfResource. The association is established
    when the assignment is created as part of the resource selection process for the
    activity; the assignment can be reassigned to another resource at a later point in time.
    The following operation support the assignee relationship.
    */
   WfResource assignee () throws BaseException;

   /**
    A WfAssignment is associated with one WfResource. The association is established
    when the assignment is created as part of the resource selection process for the
    activity; the assignment can be reassigned to another resource at a later point in time.
    The following operation support the assignee relationship.
    */
   WfResource assignee (SharkTransaction t) throws BaseException;

   /**
    A WfAssignment is associated with one WfResource. The association is established
    when the assignment is created as part of the resource selection process for the
    activity; the assignment can be reassigned to another resource at a later point in time.
    The following operation support changing the assignee relationship. An InvalidResource
    exception is raised by an attempt to assign an invalid resource to the assignment.
    */
   void set_assignee (WfResource new_value) throws BaseException, InvalidResource;

   /**
    A WfAssignment is associated with one WfResource. The association is established
    when the assignment is created as part of the resource selection process for the
    activity; the assignment can be reassigned to another resource at a later point in time.
    The following operation support changing the assignee relationship. An InvalidResource
    exception is raised by an attempt to assign an invalid resource to the assignment.
    */
   void set_assignee (SharkTransaction t,WfResource new_value) throws BaseException, InvalidResource;

   // doesn't exist in original OMG spec - should we remove it???
   /**
    Extension to OMG interface. Marks assignment's activity to be accepted by
    the assignment's assignee.
    */
   void set_accepted_status (boolean accept) throws BaseException, CannotAcceptSuspended;

   /**
    Extension to OMG interface. Marks assignment's activity to be accepted by
    the assignment's assignee.
    */
   void set_accepted_status (SharkTransaction t,boolean accept) throws BaseException, CannotAcceptSuspended;

   /**
    Extension to OMG interface. Returns if assignment is accepted.
    */
   boolean get_accepted_status () throws BaseException;

   /**
    Extension to OMG interface. Returns if assignment is accepted.
    */
   boolean get_accepted_status (SharkTransaction t) throws BaseException;

} // interface WfAssignment
