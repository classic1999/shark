package org.enhydra.shark.corba;

import org.enhydra.shark.api.*;

import org.enhydra.shark.api.client.wfbase.*;
import org.enhydra.shark.api.client.wfmodel.*;

/**
 * WfResourceHelper - Helper class to be able to implement method
 * WfAssignment.set_assignee(). Only required method to be implemented
 * is resource_key().
 * @author Sasa Bojanic
 */
public class WfResourceHelper implements WfResource {
   private String username;

   /**
    * Creates a new WfResource
    * @param username - Uniquely identifies the resource
    */
   protected WfResourceHelper(String username) {
      this.username = username;
   }

   /**
    * Gets the number of work items currently assigned to this resource.
    */
   public int how_many_work_item () throws BaseException {
      throw new BaseException("Not implemented");
   }

   public int how_many_work_item (SharkTransaction t) throws BaseException {
      throw new BaseException("Not implemented");
   }

   /**
    * Gets an iterator of work items.
    */
   public WfAssignmentIterator get_iterator_work_item () throws BaseException {
      throw new BaseException("Not implemented");
   }

   public WfAssignmentIterator get_iterator_work_item (SharkTransaction t)
   throws BaseException {
      throw new BaseException("Not implemented");
   }

   /**
    * Gets the work items.
    * @return List of WfAssignment objects.
    */
   public WfAssignment[] get_sequence_work_item (int max_number) throws BaseException {
      throw new BaseException("Not implemented");
   }

   public WfAssignment[] get_sequence_work_item(SharkTransaction t,int max_number)
   throws BaseException {
      throw new BaseException("Not implemented");
   }

   /**
    * Checks if an assignment object is associated with this resource
    * @return true if assignment is part of the work list for this resource.
    */
   public boolean is_member_of_work_items(WfAssignment member) throws BaseException {
      throw new BaseException("Not implemented");
   }

   public boolean is_member_of_work_items(SharkTransaction t,WfAssignment member) throws BaseException {
      throw new BaseException("Not implemented");
   }

   /**
    * Gets the resource username.
    */
   public String resource_key () throws BaseException {
      return username;
   }

   public String resource_key (SharkTransaction t) throws BaseException {
      return username;
   }

   /**
    * Gets the resource name.
    */
   public String resource_name () throws BaseException {
      throw new BaseException("Not implemented");
   }

   public String resource_name (SharkTransaction t) throws BaseException {
      throw new BaseException("Not implemented");
   }

   /**
    * Release the resouce from the assignment.
    */
   public void release (WfAssignment from_assigment, String release_info) throws BaseException, NotAssigned {
      throw new BaseException("Not implemented");
   }

   public void release (SharkTransaction t,WfAssignment from_assigment, String release_info) throws BaseException, NotAssigned {
      throw new BaseException("Not implemented");
   }

   public String toString () {
      return "[Id="+username+"]";
   }

   /**
    * It is assumed that there can't be two or more
    * resources having the same resource key.
    */
   public boolean equals (Object obj) {
      if (!(obj instanceof WfResource)) return false;
      WfResource res=(WfResource)obj;
      try {
         return res.resource_key().equals(username);
      } catch (Exception ex) {
         return false;
      }
   }

}
