package org.enhydra.shark;

import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfmodel.CannotAcceptSuspended;
import org.enhydra.shark.api.client.wfmodel.InvalidResource;
import org.enhydra.shark.api.client.wfmodel.WfActivity;
import org.enhydra.shark.api.client.wfmodel.WfAssignment;
import org.enhydra.shark.api.client.wfmodel.WfResource;
import org.enhydra.shark.api.internal.security.SecurityManager;
import org.enhydra.shark.api.internal.working.WfActivityInternal;
import org.enhydra.shark.api.internal.working.WfAssignmentInternal;

/**
 * WfAssignmentWrapper - Workflow Assignment Object implementation
 * @author Sasa Bojanic
 * @author Vladimir Puskas
 */
public class WfAssignmentWrapper implements WfAssignment {

   private String userAuth;
   private String mgrName;
   private String processId;
   private String activityId;
   private String username;

   /**
    * Creates new WfAssignment.
    * @param    processId - process of activity of assignment
    * @param actId - activity Id for this assignment.
    * @param username
    */
   protected WfAssignmentWrapper(String userAuth,String mgrName,String processId,String actId,String username) {
      this.userAuth=userAuth;
      this.mgrName=mgrName;
      this.processId = processId;
      this.activityId = actId;
      this.username = username;
   }

   /**
    * Gets the activity object of this assignment.
    * @return WfActivity The activity object of this assignment.
    */
   public WfActivity activity () throws BaseException {
      WfActivity ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = activity(t);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   /**
    * Gets the activity object of this assignment.
    * @return WfActivity The activity object of this assignment.
    */
   public WfActivity activity (SharkTransaction t) throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         WfActivityInternal actInt=SharkUtilities.getActivity(t,processId,activityId);         try {
         sm.check_assignment_activity(t,
                                      processId,
                                      activityId,
                                      username,
                                      userAuth,
                                      actInt.container(t).requester(t).getResourceRequesterUsername(t),
                                      actInt.getResourceUsername(t),
                                      actInt.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      return SharkEngineManager.getInstance().getObjectFactory().createActivityWrapper(userAuth,mgrName,processId,activityId);
   }

   /**
    * Gets the assignee (resource) of this assignment.
    * @return WfResource The assignee of this assignment.
    */
   public WfResource assignee () throws BaseException {
      WfResource ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = assignee(t);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   /**
    * Method assignee
    *
    * @param    t                   a  SharkTransaction
    *
    * @return   a WfResource
    *
    * @exception   BaseException
    */
   public WfResource assignee (SharkTransaction t) throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         WfActivityInternal actInt=SharkUtilities.getActivity(t,processId,activityId);
         try {
            sm.check_assignment_assignee(t,
                                         processId,
                                         activityId,
                                         username,
                                         userAuth,
                                         actInt.container(t).requester(t).getResourceRequesterUsername(t),
                                         actInt.getResourceUsername(t),
                                         actInt.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      return SharkEngineManager.getInstance().getObjectFactory().createResourceWrapper(userAuth,username);
   }

   /**
    * Sets the assignee of this assignment.
    */
   public void set_assignee (WfResource new_value) throws BaseException, InvalidResource {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         set_assignee(t, new_value);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof InvalidResource)
            throw (InvalidResource)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   /**
    * Method set_assignee
    *
    * @param    t                   a  SharkTransaction
    * @param    new_value           a  WfResource
    *
    * @exception   BaseException
    * @exception   InvalidResource
    */
   public void set_assignee (SharkTransaction t,WfResource new_value) throws BaseException, InvalidResource {
      WfAssignmentInternal assInternal=WfAssignmentWrapper.getAssignmentImpl(t,processId,activityId,username);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_assignment_set_assignee(t,
                                             processId,
                                             activityId,
                                             username,
                                             userAuth,
                                             assInternal.activity(t).container(t).requester(t).getResourceRequesterUsername(t),
                                             assInternal.activity(t).getResourceUsername(t),
                                             assInternal.activity(t).getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      if (new_value==null) throw new BaseException("Assignee can't be null");

      assInternal.set_assignee(t,SharkUtilities
                                  .getResource(t, new_value.resource_key(t)));
      username = new_value.resource_key(t);
   }

   /**
    * Method set_accepted_status
    *
    * @param    accept              a  boolean
    *
    * @exception   BaseException
    * @exception   CannotAcceptSuspended
    */
   public void set_accepted_status (boolean accept)
      throws BaseException, CannotAcceptSuspended {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         set_accepted_status(t,accept);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof CannotAcceptSuspended)
            throw (CannotAcceptSuspended)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   /**
    * Method set_accepted_status
    *
    * @param    t                   a  SharkTransaction
    * @param    accept              a  boolean
    *
    * @exception   BaseException
    * @exception   CannotAcceptSuspended
    */
   public void set_accepted_status (SharkTransaction t,boolean accept)
      throws BaseException, CannotAcceptSuspended {

      WfActivityInternal actInt=SharkUtilities.getActivity(t,processId,activityId);
      if (actInt==null) {
         throw new BaseException("Assignment is not valid anymore");
      }
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_assignment_set_accepted_status(t,
                                                    processId,
                                                    activityId,
                                                    username,
                                                    userAuth,
                                                    actInt.container(t).requester(t).getResourceRequesterUsername(t),
                                                    actInt.getResourceUsername(t),
                                                    actInt.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      actInt.set_accepted_status(t,accept,username);
   }

   /**
    * Method get_accepted_status
    *
    * @return   a boolean
    *
    * @exception   BaseException
    */
   public boolean get_accepted_status() throws BaseException {
      boolean ret = false;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_accepted_status(t);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   /**
    * Method get_accepted_status
    *
    * @param    t                   a  SharkTransaction
    *
    * @return   a boolean
    *
    * @exception   BaseException
    */
   public boolean get_accepted_status (SharkTransaction t) throws BaseException {
      //WfAssignmentInternal assInternal=WfAssignmentWrapper.getAssignmentImpl(t,processId,activityId,username);
      WfActivityInternal actInt=SharkUtilities.getActivity(t,processId,activityId);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_assignment_get_accepted_status(t,
                                                    processId,
                                                    activityId,
                                                    username,
                                                    userAuth,
                                                    actInt.container(t).requester(t).getResourceRequesterUsername(t),
                                                    actInt.getResourceUsername(t),
                                                    actInt.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      //return assInternal.get_acceted_status(t);
      return actInt.accepted_status(t);
   }

   /**
    * Method toString
    *
    * @return   a String
    */
   public String toString () {
      return "[pId="+processId+",aId="+activityId+"-> un="+username+"]";
   }

   /**
    * Method equals
    *
    * @param    obj                 an Object
    *
    * @return   a boolean
    */
   public boolean equals (Object obj) {
      if (!(obj instanceof WfAssignment)) return false;
      WfAssignment ass=(WfAssignment)obj;
      try {
         if (obj instanceof WfAssignmentWrapper) {
            WfAssignmentWrapper aw=(WfAssignmentWrapper)obj;
            return aw.activityId.equals(activityId) &&
               aw.username.equals(username);
         } else {
            return (ass.activity().key().equals(activityId)
                       && ass.assignee().resource_key().equals(username));
         }
      } catch (Exception ex) {
         return false;
      }
   }

   private static WfAssignmentInternal getAssignmentImpl (
      SharkTransaction t,
      String procId,
      String actId,
      String username) throws BaseException {

      WfAssignmentInternal ass=SharkUtilities.getAssignment(t, procId, actId, username);
      if (ass==null) throw new BaseException("Assignment is not valid anymore");
      return ass;
   }

}

