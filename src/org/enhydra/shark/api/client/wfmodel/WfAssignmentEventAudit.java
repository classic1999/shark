package org.enhydra.shark.api.client.wfmodel;


import org.enhydra.shark.api.client.wfbase.*;

/**
 OMG definition: This interface specializes WfEventAudit for assignment change events. The event
 records resource and assignment status before and after the change. The event_type
 is activityAssignmentChanged.
 An assignment change event is signaled when assignments for an activity are created
 (in this case the old_... data is NULL), when the status of an assignment is changed, or
 when an existing assignment is reassigned to another resource. The WfActivity
 associated with the assignment is reported as the source of the event.
 <p>We extended OMG's interface by adding additional method for getting
 accepted status property.
 */
public interface WfAssignmentEventAudit  extends WfEventAudit {

   /**
    Returns the resource key of the assignment before the change may be recorded. This event also
    covers creation of a new assignment; in this case, the before event information is
    NULL.
    */
   String old_resource_key () throws BaseException;

   /**
    Returns the resource name of the assignment before the change may be recorded. This event also
    covers creation of a new assignment; in this case, the before event information is
    NULL.
    */
   String old_resource_name () throws BaseException;

   /** Returns the resource key of the assignment after the change is recorded.*/
   String new_resource_key () throws BaseException;
   /** Returns the resource name of the assignment after the change is recorded.*/
   String new_resource_name () throws BaseException;

   // doesn't exist in original OMG spec.
   /** Returns the accepted status of assignment after the change is recorded.*/
   boolean is_accepted () throws BaseException;
} // interface WfAssignmentEventAudit
