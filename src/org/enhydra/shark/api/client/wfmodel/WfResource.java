package org.enhydra.shark.api.client.wfmodel;


import org.enhydra.shark.api.client.wfbase.*;
import org.enhydra.shark.api.SharkTransaction;

/**
 OMG definition: WfResource is an abstraction that represents a person or thing that will potentially
 accept an assignment to an activity. Potential and/or accepted WfAssignments are links
 between the requesting WfActivities and WfResource objects. It is expected that this
 interface will be used to implement adapters for objects representing people and things
 implemented in user, organization, and resource models. These models are outside the
 scope of this specification.
 <p>We extended OMG's interface by duplicating methods, and adding additional
 parameter that represents transaction. If you use methods without SharkTransaction
 parameter, the transaction will be implicitly created, and if you use it with
 SharkTransaction parameter you must obey to some rules explained in HowTo documentation.
 */
public interface WfResource extends BaseBusinessObject {//, PersistenceInterface

   /**
    Zero or more WfAssignments are associated with a resource. The association is
    established when the assignment is created as part of the resource selection process for
    an activity; the assignment can be reassigned to another resource at a later point in
    time.
    <p>The following operation returns the number of WfAssignments
    associated with a resource.
    */
   int how_many_work_item () throws BaseException;

   /**
    Zero or more WfAssignments are associated with a resource. The association is
    established when the assignment is created as part of the resource selection process for
    an activity; the assignment can be reassigned to another resource at a later point in
    time.
    <p>The following operation returns the number of WfAssignments
    associated with a resource.
    */
   int how_many_work_item (SharkTransaction t) throws BaseException;

   /**
    Zero or more WfAssignments are associated with a resource. The association is
    established when the assignment is created as part of the resource selection process for
    an activity; the assignment can be reassigned to another resource at a later point in
    time.
    <p>The following operation returns iterator for qurying associated
    assignments based on some criteria.
    */
   WfAssignmentIterator get_iterator_work_item () throws BaseException;

   /**
    Zero or more WfAssignments are associated with a resource. The association is
    established when the assignment is created as part of the resource selection process for
    an activity; the assignment can be reassigned to another resource at a later point in
    time.
    <p>The following operation returns iterator for qurying associated
    assignments based on some criteria.
    */
   WfAssignmentIterator get_iterator_work_item (SharkTransaction t) throws BaseException;

   /**
    Zero or more WfAssignments are associated with a resource. The association is
    established when the assignment is created as part of the resource selection process for
    an activity; the assignment can be reassigned to another resource at a later point in
    time.
    <p>The following operation returns max_number of WfAssignment objects
    associated with a resource. If max_number is less or eaqual to zero, or it is
    greater than the number of existing assignments, all associated WfAssignments
    objects will be returned.
    */
   WfAssignment[] get_sequence_work_item (int max_number) throws BaseException;

   /**
    Zero or more WfAssignments are associated with a resource. The association is
    established when the assignment is created as part of the resource selection process for
    an activity; the assignment can be reassigned to another resource at a later point in
    time.
    <p>The following operation returns max_number of WfAssignment objects
    associated with a resource. If max_number is less or eaqual to zero, or it is
    greater than the number of existing assignments, all associated WfAssignments
    objects will be returned.
    */
   WfAssignment[] get_sequence_work_item (SharkTransaction t,int max_number) throws BaseException;

   /**
    Zero or more WfAssignments are associated with a resource. The association is
    established when the assignment is created as part of the resource selection process for
    an activity; the assignment can be reassigned to another resource at a later point in
    time.
    <p>The following operation returns true if given assignment is
    associated with resource.
    */
   boolean is_member_of_work_items (WfAssignment member) throws BaseException;

   /**
    Zero or more WfAssignments are associated with a resource. The association is
    established when the assignment is created as part of the resource selection process for
    an activity; the assignment can be reassigned to another resource at a later point in
    time.
    <p>The following operation returns true if given assignment is
    associated with resource.
    */
   boolean is_member_of_work_items (SharkTransaction t,WfAssignment member) throws BaseException;

   /**
    Returns the resource key that identifies a resource within a given business domain.
    It is assumed that resources are defined in the same business domain as the workflow
    processes they are associated with.
    <p>The key is set when the object is initialized; modification of the key
    can be done in the context of a resource management facility.
    */
   String resource_key () throws BaseException;

   /**
    Returns the resource key that identifies a resource within a given business domain.
    It is assumed that resources are defined in the same business domain as the workflow
    processes they are associated with.
    <p>The key is set when the object is initialized; modification of the key
    can be done in the context of a resource management facility.
    */
   String resource_key (SharkTransaction t) throws BaseException;

   /** Returns a human readable, descriptive name of the resource.*/
   String resource_name () throws BaseException;

   /** Returns a human readable, descriptive name of the resource.*/
   String resource_name (SharkTransaction t) throws BaseException;

   /**
    The release operation is used to signal that the resource is no longer needed for a
    specific assignment. It takes the assignment that is no longer associated with the
    resource and a string that specifies additional information on the reason for realizing
    the resource as input. A NotAssigned exception is raised when the WfAssignment
    specified as input is not assigned to the WfResource.
    It is assumed that this operation is invoked when an assignment is deleted or when an
    assignment is reassigned to another resource.
    */
   void release (WfAssignment from_assigment, String release_info) throws BaseException, NotAssigned;

   /**
    The release operation is used to signal that the resource is no longer needed for a
    specific assignment. It takes the assignment that is no longer associated with the
    resource and a string that specifies additional information on the reason for realizing
    the resource as input. A NotAssigned exception is raised when the WfAssignment
    specified as input is not assigned to the WfResource.
    It is assumed that this operation is invoked when an assignment is deleted or when an
    assignment is reassigned to another resource.
    */
   void release (SharkTransaction t,WfAssignment from_assigment, String release_info) throws BaseException, NotAssigned;
} // interface WfResource

