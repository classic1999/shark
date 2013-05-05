package org.enhydra.shark.corba;

import org.omg.WfBase.BaseException;
import org.omg.WorkflowModel.*;

/**
 * WfAssignmentImpl - Workflow Assignment Object implementation
 * 
 * @author Sasa Bojanic
 * @version 1.0
 */
public class WfAssignmentCORBA extends _WfAssignmentImplBase {
   //private SharkCORBAServer myServer;

   org.enhydra.shark.api.client.wfmodel.WfAssignment sharkAss;

   private Collective __collective;

   /**
    * Creates new WfAssignment.
    * 
    * @param sharkAss a WfAssignment
    */
   protected WfAssignmentCORBA(Collective toJoin,
                               org.enhydra.shark.api.client.wfmodel.WfAssignment sharkAss) {
      __collective = toJoin;
      toJoin.__recruit(this);
      this.sharkAss = sharkAss;
   }

   /**
    * Gets the activity object of this assignment.
    * 
    * @return WfActivity The activity object of this assignment.
    */
   public WfActivity activity() throws BaseException {
      try {
         return new WfActivityCORBA(__collective, sharkAss.activity());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Gets the assignee (resource) of this assignment.
    * 
    * @return WfResource The assignee of this assignment.
    */
   public WfResource assignee() throws BaseException {
      try {
         return new WfResourceCORBA(__collective, sharkAss.assignee());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Sets the assignee of this assignment.
    */
   public void set_assignee(WfResource new_value) throws BaseException,
                                                 InvalidResource {
      try {
         sharkAss.set_assignee(new WfResourceHelper(new_value.resource_key()));
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public synchronized void set_accepted_status(boolean accept) throws BaseException,
                                                               CannotAcceptSuspended {
      try {
         sharkAss.set_accepted_status(accept);
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public boolean get_accepted_status() throws BaseException {
      try {
         return sharkAss.get_accepted_status();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof WfAssignment)) return false;
      WfAssignment ass = (WfAssignment) obj;
      try {
         return (ass.activity().key().equals(activity().key()) && ass.assignee()
            .resource_key()
            .equals(assignee().resource_key()));
      } catch (Exception ex) {
         return false;
      }
   }

   public String toString() {
      return sharkAss.toString();
   }

}

