package org.enhydra.shark.corba;

import org.omg.WfBase.*;
import org.omg.WorkflowModel.*;

/**
 * WfResourceImpl - Workflow Resource Object implementation.
 * 
 * @author Sasa Bojanic
 * @version 1.0
 */
public class WfResourceCORBA extends _WfResourceImplBase {
   //private SharkCORBAServer myServer;

   org.enhydra.shark.api.client.wfmodel.WfResource sharkRes;
   private Collective __collective;

   /**
    * Creates a new WfResource
    * 
    * @param sharkRes a WfResource
    */
   protected WfResourceCORBA(Collective toJoin, 
                             org.enhydra.shark.api.client.wfmodel.WfResource sharkRes) {
      __collective = toJoin;
      toJoin.__recruit(this);
      this.sharkRes = sharkRes;
   }

   /**
    * Gets the number of work items currently assigned to this resource.
    */
   public int how_many_work_item() throws BaseException {
      try {
         return sharkRes.how_many_work_item();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Gets an iterator of work items.
    */
   public WfAssignmentIterator get_iterator_work_item() throws BaseException {
      try {
         return new WfAssignmentIteratorCORBA(__collective,
                                              sharkRes.get_iterator_work_item());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Gets the work items.
    * 
    * @return List of WfAssignment objects.
    */
   public WfAssignment[] get_sequence_work_item(int max_number) throws BaseException {
      try {
         return SharkCORBAUtilities.makeCORBAAssignments(__collective,
                                                         sharkRes.get_sequence_work_item(max_number));
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Checks if an assignment object is associated with this resource
    * 
    * @return true if assignment is part of the work list for this
    *         resource.
    */
   public boolean is_member_of_work_items(WfAssignment member) throws BaseException {
      try {
         WfAssignment[] ass = get_sequence_work_item(0);
         boolean ret = false;
         if (ass != null) {
            for (int i = 0; i < ass.length; i++) {
               WfAssignment as = ass[i];
               if (as.equals(member)) {
                  ret = true;
                  break;
               }
            }
         }
         return ret;
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Gets the resource username.
    */
   public String resource_key() throws BaseException {
      try {
         return sharkRes.resource_key();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Gets the resource name.
    */
   public String resource_name() throws BaseException {
      try {
         return sharkRes.resource_name();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Release the resouce from the assignment.
    */
   public void release(WfAssignment from_assigment, String release_info) throws BaseException,
                                                                        NotAssigned {
      try {
         sharkRes.release(null, release_info);
      } catch (org.enhydra.shark.api.client.wfmodel.NotAssigned na) {
         throw new NotAssigned();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * It is assumed that there can't be two or more resources having the
    * same resource key.
    */
   public boolean equals(Object obj) {
      if (!(obj instanceof WfResource)) return false;
      WfResource res = (WfResource) obj;
      try {
         return res.resource_key().equals(resource_key());
      } catch (Exception ex) {
         return false;
      }
   }

   public String toString() {
      return sharkRes.toString();
   }

}