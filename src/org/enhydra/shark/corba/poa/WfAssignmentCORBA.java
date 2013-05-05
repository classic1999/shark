package org.enhydra.shark.corba.poa;

import org.omg.WfBase.BaseException;
import org.omg.WorkflowModel.*;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.enhydra.shark.corba.poa.Collective;
import org.enhydra.shark.corba.poa.SharkCORBAUtilities;
import org.enhydra.shark.corba.poa.WfActivityCORBA;

/**
 * WfAssignmentImpl - Workflow Assignment Object implementation
 *
 * @author David Forslund
 * @version 1.0
 */
public class WfAssignmentCORBA extends WfAssignmentPOA {
   //private SharkCORBAServer myServer;

   org.enhydra.shark.api.client.wfmodel.WfAssignment sharkAss;

   private Collective __collective;
   private ORB orb;

   /**
    * Creates new WfAssignment.
    *
    * @param orb
    * @param sharkAss a WfAssignment
    */
   protected WfAssignmentCORBA(ORB orb, Collective toJoin,
                               org.enhydra.shark.api.client.wfmodel.WfAssignment sharkAss) {
      __collective = toJoin;
      this.orb = orb;
    //  toJoin.__recruit(this);
      this.sharkAss = sharkAss;
   }

   /**
    * Gets the activity object of this assignment.
    *
    * @return WfActivity The activity object of this assignment.
    */
   public WfActivity activity() throws BaseException {
      try {
         WfActivity activity = SharkCORBAUtilities.makeWfActivity(new WfActivityCORBA(orb,  __collective, sharkAss.activity()));
          __collective.__recruit(activity);
          return activity;
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
          WfResource wfRes = SharkCORBAUtilities.makeWfResource( new WfResourceCORBA(orb, __collective, sharkAss.assignee()));
           __collective.__recruit(wfRes);
           return wfRes;
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

