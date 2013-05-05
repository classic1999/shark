package org.enhydra.shark;

import org.enhydra.shark.api.client.wfmodel.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.timebase.UtcT;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.common.SharkConstants;
import org.enhydra.shark.api.internal.eventaudit.EventAuditManagerInterface;
import org.enhydra.shark.api.internal.security.SecurityManager;
import org.enhydra.shark.api.internal.working.WfActivityInternal;

/**
 * WfActivityWrapper - Workflow Activity Object implementation
 * @author Sasa Bojanic
 * @author Vladimir Puskas
 */
public class WfActivityWrapper implements WfActivity {

   private String userAuth;
   private String mgrName;
   private String processId;
   private String id;

   /**
    * Create a new WfActivityWrapper
    */
   protected WfActivityWrapper(String userAuth,String mgrName,String processId,String id) {
      this.userAuth=userAuth;
      this.mgrName=mgrName;
      this.processId=processId;
      this.id = id;
   }

   /**
    * Retrieve the no. of Assignment objects.
    * @throws BaseException General workflow exception.
    * @return No. of current assignments.
    */
   public int how_many_assignment () throws BaseException {
      int ret = -1;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = how_many_assignment(t);
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

   public int how_many_assignment (SharkTransaction t) throws BaseException {
      try {
         SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
         if (sm!=null) {
            WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
            sm.check_activity_how_many_assignment(t,
                                                  processId,
                                                  id,
                                                  userAuth,
                                                  actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                                  actInternal.getResourceUsername(t),
                                                  actInternal.getAssignmentResourceIds(t));
         }

         return SharkEngineManager
            .getInstance()
            .getInstancePersistenceManager()
            .getAllValidAssignmentsForActivity(id,t).size();
      } catch (Exception e) {
         throw new BaseException(e);
      }

   }

   /**
    * Retrieve the Iterator of Assignments objects.
    */
   public WfAssignmentIterator get_iterator_assignment () throws BaseException {
      WfAssignmentIterator ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_iterator_assignment(t);
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

   public WfAssignmentIterator get_iterator_assignment (SharkTransaction t) throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
         try {
            sm.check_activity_get_iterator_assignment(t,
                                                      processId,
                                                      id,
                                                      userAuth,
                                                      actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                                      actInternal.getResourceUsername(t),
                                                      actInternal.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      return SharkEngineManager.getInstance().getObjectFactory().createAssignmentIteratorWrapper(t,userAuth,processId,id);
   }

   /**
    * Retrieve all assignments of this activity.
    * @return  array of WfAssignment objects.
    */
   public WfAssignment[] get_sequence_assignment (int max_number) throws BaseException {
      WfAssignment[] ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_sequence_assignment(t,max_number);
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

   public WfAssignment[] get_sequence_assignment (SharkTransaction t,int max_number) throws BaseException {
      try {
         SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
         if (sm!=null) {
            WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
            sm.check_activity_get_sequence_assignment(t,
                                                      processId,
                                                      id,
                                                      userAuth,
                                                      actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                                      actInternal.getResourceUsername(t),
                                                      actInternal.getAssignmentResourceIds(t));
         }
         List l=SharkUtilities.createAssignmentWrappers(t,userAuth,processId,id);
         if (max_number > l.size() || max_number<=0) {
            max_number = l.size();
         }
         WfAssignment[] ret = new WfAssignment[l.size()];
         l.subList(0, max_number).toArray(ret);
         return ret;
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   /**
    * Check if a specific assignment is a member of this activity.
    * @return true if the assignment is a member of this activity.
    */
   public boolean is_member_of_assignment (WfAssignment member) throws BaseException {
      boolean ret = false;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = is_member_of_assignment(t,member);
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

   public boolean is_member_of_assignment (SharkTransaction t,WfAssignment member) throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
         try {
            sm.check_activity_is_member_of_assignment(t,
                                                      processId,
                                                      id,
                                                      userAuth,
                                                      actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                                      actInternal.getResourceUsername(t),
                                                      actInternal.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      String actId=member.activity(t).key(t);
      return actId.equals(id);
   }

   /**
    * Getter for the process of this activity.
    */
   public WfProcess container () throws BaseException {
      WfProcess ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = container(t);
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

   public WfProcess container (SharkTransaction t) throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
         try {
            sm.check_activity_container(t,
                                        processId,
                                        id,
                                        userAuth,
                                        actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                        actInternal.getResourceUsername(t),
                                        actInternal.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      return SharkEngineManager.getInstance().getObjectFactory().createProcessWrapper(userAuth,mgrName,processId);
   }

   /**
    * Retrieve the Result map of this activity.
    * @return Map of results from this activity
    */
   public Map result () throws BaseException, ResultNotAvailable {
      Map ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = result(t);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof ResultNotAvailable)
            throw (ResultNotAvailable)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public Map result (SharkTransaction t) throws BaseException, ResultNotAvailable {
      WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_activity_result(t,
                                     processId,
                                     id,
                                     userAuth,
                                     actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                     actInternal.getResourceUsername(t),
                                     actInternal.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      return actInternal.result(t);
   }

   /**
    * Assign Result for this activity.
    */
   public void set_result (Map results) throws BaseException, InvalidData {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         set_result(t,results);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof InvalidData)
            throw (InvalidData)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void set_result (SharkTransaction t,Map results) throws BaseException, InvalidData {
      WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_activity_set_result(t,
                                         processId,
                                         id,
                                         userAuth,
                                         actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                         actInternal.getResourceUsername(t),
                                         actInternal.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      actInternal.set_result(t,results);
   }

   /**
    * Complete this activity.
    */
   public void complete () throws BaseException, CannotComplete {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         complete(t);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //e.printStackTrace();
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof CannotComplete)
            throw (CannotComplete)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void complete (SharkTransaction t) throws BaseException, CannotComplete {
      WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_activity_complete(t,
                                       processId,
                                       id,
                                       userAuth,
                                       actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                       actInternal.getResourceUsername(t),
                                       actInternal.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      actInternal.complete(t);
   }

   /**
    * Method workflow_state
    *
    * @return   a workflow_stateType
    *
    * @exception   BaseException
    */
   public workflow_stateType workflow_state () throws BaseException {
      workflow_stateType ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = workflow_state(t);
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
    * Method workflow_state
    *
    * @param    t                   a  SharkTransaction
    *
    * @return   a workflow_stateType
    *
    * @exception   BaseException
    */
   public workflow_stateType workflow_state (SharkTransaction t) throws BaseException {
      WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_activity_workflow_state(t,
                                             processId,
                                             id,
                                             userAuth,
                                             actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                             actInternal.getResourceUsername(t),
                                             actInternal.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      String state=actInternal.state(t);

      if (state.startsWith(SharkConstants.STATEPREFIX_CLOSED)) {
         return workflow_stateType.closed;
      } else {
         return workflow_stateType.open;
      }
   }

   public while_openType while_open () throws BaseException {
      while_openType ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = while_open(t);
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
    * Method while_open
    *
    * @param    t                   a  SharkTransaction
    *
    * @return   a while_openType
    *
    * @exception   BaseException
    */
   public while_openType while_open (SharkTransaction t) throws BaseException {
      WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_activity_while_open(t,
                                         processId,
                                         id,
                                         userAuth,
                                         actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                         actInternal.getResourceUsername(t),
                                         actInternal.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      String state=actInternal.state(t);

      if (state.equals(SharkConstants.STATE_OPEN_RUNNING)) {
         return while_openType.running;
      } else {
         return while_openType.not_running;
      }
   }

   /**
    * Method why_not_running
    *
    * @return   a why_not_runningType
    *
    * @exception   BaseException
    */
   public why_not_runningType why_not_running () throws BaseException {
      why_not_runningType ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = why_not_running(t);
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
    * Method why_not_running
    *
    * @param    t                   a  SharkTransaction
    *
    * @return   a why_not_runningType
    *
    * @exception   BaseException
    */
   public why_not_runningType why_not_running (SharkTransaction t) throws BaseException {
      WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_activity_why_not_running(t,
                                              processId,
                                              id,
                                              userAuth,
                                              actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                              actInternal.getResourceUsername(t),
                                              actInternal.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      String state=actInternal.state(t);

      if (state.equals(SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED)) {
         return why_not_runningType.suspended;
      } else {
         return why_not_runningType.not_started;
      }
   }

   /**
    * Method how_closed
    *
    * @return   a how_closedType
    *
    * @exception   BaseException
    */
   public how_closedType how_closed () throws BaseException {
      how_closedType ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = how_closed(t);
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
    * Method how_closed
    *
    * @param    t                   a  SharkTransaction
    *
    * @return   a how_closedType
    *
    * @exception   BaseException
    */
   public how_closedType how_closed (SharkTransaction t) throws BaseException {
      WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_activity_how_closed(t,
                                         processId,
                                         id,
                                         userAuth,
                                         actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                         actInternal.getResourceUsername(t),
                                         actInternal.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      String state=actInternal.state(t);

      if (state.equals(SharkConstants.STATE_CLOSED_COMPLETED)) {
         return how_closedType.completed;
      } else if (state.equals(SharkConstants.STATE_CLOSED_TERMINATED)) {
         return how_closedType.terminated;
      } else {
         return how_closedType.aborted;
      }
   }


   public String[] valid_states () throws BaseException {
      String[] ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = valid_states(t);
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

   public String[] valid_states (SharkTransaction t) throws BaseException {
      WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_activity_valid_states(t,
                                           processId,
                                           id,
                                           userAuth,
                                           actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                           actInternal.getResourceUsername(t),
                                           actInternal.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      String state=actInternal.state(t);

      List vs=SharkUtilities.valid_activity_states(actInternal.state(t));
      String[] vss=new String[vs.size()];
      vs.toArray(vss);
      return vss;
   }

   public String state () throws BaseException {
      String ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = state(t);
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

   public String state (SharkTransaction t) throws BaseException {
      WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_activity_state(t,
                                    processId,
                                    id,
                                    userAuth,
                                    actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                    actInternal.getResourceUsername(t),
                                    actInternal.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      String state=actInternal.state(t);

      return actInternal.state(t);
   }

   public void change_state (String new_state) throws BaseException, InvalidState, TransitionNotAllowed {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         change_state(t, new_state);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof InvalidState)
            throw (InvalidState)e;
         else if (e instanceof TransitionNotAllowed)
            throw (TransitionNotAllowed)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void change_state (SharkTransaction t,String new_state) throws BaseException, InvalidState, TransitionNotAllowed {
      WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
      String curResUname=actInternal.getResourceUsername(t);
      String curState=actInternal.state(t);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_activity_change_state(t,
                                           processId,
                                           id,
                                           userAuth,
                                           actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                           curResUname,
                                           actInternal.getAssignmentResourceIds(t),
                                           curState,
                                           new_state);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      if (!SharkConstants.possibleActivityStates.contains(new_state)) {
         throw new InvalidState("Can't change activity state to "+new_state+" - no such state!");
      }

      if (!SharkUtilities.valid_activity_states(actInternal.state(t)).contains(new_state)) {
         throw new TransitionNotAllowed("Current activity state is "+curState+" - can't change to state "+new_state+"!");
      }
      if (new_state.equals(SharkConstants.STATE_CLOSED_ABORTED)) {
         try {
            actInternal.abort(t);
         } catch (CannotStop cns) {
            throw new TransitionNotAllowed(cns);
         } catch (NotRunning nr) {
            throw new TransitionNotAllowed(nr);
         }
      } else if (new_state.equals(SharkConstants.STATE_CLOSED_COMPLETED)) {
         try {
            actInternal.complete(t);
         } catch (CannotComplete cnc) {
            throw new TransitionNotAllowed(cnc);
         }
      } else if (new_state.equals(SharkConstants.STATE_CLOSED_TERMINATED)) {
         try {
            actInternal.terminate(t);
         } catch (CannotStop cns) {
            throw new TransitionNotAllowed(cns);
         } catch (NotRunning nr) {
            throw new TransitionNotAllowed(nr);
         }
      } else if (new_state.equals(SharkConstants.STATE_OPEN_NOT_RUNNING_NOT_STARTED)) {
         try {
            actInternal.set_accepted_status(t,false,userAuth);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      } else if (new_state.equals(SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED)) {
         try {
            actInternal.suspend(t);
         } catch (AlreadySuspended as) {
            throw new TransitionNotAllowed(as);
         } catch (CannotSuspend cns) {
            throw new TransitionNotAllowed(cns);
         } catch (NotRunning nr) {
            throw new TransitionNotAllowed(nr);
         }
      } else { //new state is open.running
         if (curState.equals(SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED)) {
            try {
               actInternal.resume(t);
            } catch (CannotResume cnr) {
               throw new TransitionNotAllowed(cnr);
            } catch (NotSuspended ns) {
               throw new TransitionNotAllowed(ns);
            }
         } else { // curState is NOT_RUNNING_NOT_STARTED
            try {
               actInternal.set_accepted_status(t,true,userAuth);
            } catch (Exception ex) {
               throw new BaseException(ex);
            }
         }
      }
   }

   public String name () throws BaseException {
      String ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = name(t);
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

   public String name (SharkTransaction t) throws BaseException {
      WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_activity_name(t,
                                   processId,
                                   id,
                                   userAuth,
                                   actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                   actInternal.getResourceUsername(t),
                                   actInternal.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      return actInternal.name(t);
   }

   public void set_name (String new_value) throws BaseException {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         set_name(t, new_value);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void set_name (SharkTransaction t,String new_value) throws BaseException {
      WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_activity_set_name(t,
                                       processId,
                                       id,
                                       userAuth,
                                       actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                       actInternal.getResourceUsername(t),
                                       actInternal.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      actInternal.set_name(t,new_value);
   }

   public String key () throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm==null) {
         return id;
      }
      String ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = key(t);
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

   public String key (SharkTransaction t) throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
         try {
            sm.check_activity_key(t,
                                  processId,
                                  id,
                                  userAuth,
                                  actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                  actInternal.getResourceUsername(t),
                                  actInternal.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      return id;
   }

   public String description () throws BaseException {
      String ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = description(t);
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

   public String description (SharkTransaction t) throws BaseException {
      WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_activity_description(t,
                                          processId,
                                          id,
                                          userAuth,
                                          actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                          actInternal.getResourceUsername(t),
                                          actInternal.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      return actInternal.description(t);
   }

   public void set_description (String new_value) throws BaseException {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         set_description(t, new_value);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void set_description (SharkTransaction t,String new_value) throws BaseException {
      WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_activity_set_description(t,
                                              processId,
                                              id,
                                              userAuth,
                                              actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                              actInternal.getResourceUsername(t),
                                              actInternal.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      actInternal.set_description(t,new_value);
   }

   public Map process_context () throws BaseException {
      Map ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = process_context(t);
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

   public Map process_context (SharkTransaction t) throws BaseException {
      WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_activity_process_context(t,
                                              processId,
                                              id,
                                              userAuth,
                                              actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                              actInternal.getResourceUsername(t),
                                              actInternal.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      return actInternal.process_context(t);
   }

   public void set_process_context (Map new_value) throws BaseException, InvalidData, UpdateNotAllowed {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         set_process_context(t, new_value);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof InvalidData)
            throw (InvalidData)e;
         else if (e instanceof UpdateNotAllowed)
            throw (UpdateNotAllowed)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void set_process_context (SharkTransaction t,Map new_value) throws BaseException, InvalidData, UpdateNotAllowed {
      WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_activity_set_process_context(t,
                                                  processId,
                                                  id,
                                                  userAuth,
                                                  actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                                  actInternal.getResourceUsername(t),
                                                  actInternal.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      actInternal.set_process_context(t,new_value);
   }

   public short priority () throws BaseException {
      short ret = -1;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = priority(t);
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

   public short priority (SharkTransaction t) throws BaseException {
      WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_activity_priority(t,
                                       processId,
                                       id,
                                       userAuth,
                                       actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                       actInternal.getResourceUsername(t),
                                       actInternal.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      return actInternal.priority(t);
   }

   public void set_priority (short new_value) throws BaseException {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         set_priority(t, new_value);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void set_priority (SharkTransaction t,short new_value) throws BaseException {
      WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_activity_set_priority(t,
                                           processId,
                                           id,
                                           userAuth,
                                           actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                           actInternal.getResourceUsername(t),
                                           actInternal.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      actInternal.set_priority(t,new_value);
   }

   /**
    * Resume this process or activity.
    */
   public void resume() throws BaseException, CannotResume, NotSuspended {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         resume(t);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof CannotResume)
            throw (CannotResume)e;
         else if (e instanceof NotSuspended)
            throw (NotSuspended)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void resume(SharkTransaction t) throws BaseException, CannotResume, NotSuspended {
      WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_activity_result(t,
                                     processId,
                                     id,
                                     userAuth,
                                     actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                     actInternal.getResourceUsername(t),
                                     actInternal.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      actInternal.resume(t);
   }

   /**
    * Suspend this process or activity.
    */
   public void suspend() throws BaseException, CannotSuspend, NotRunning, AlreadySuspended {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         suspend(t);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof CannotSuspend)
            throw (CannotSuspend)e;
         else if (e instanceof NotRunning)
            throw (NotRunning)e;
         else if (e instanceof AlreadySuspended)
            throw (AlreadySuspended)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void suspend(SharkTransaction t) throws BaseException, CannotSuspend, NotRunning, AlreadySuspended {
      WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_activity_suspend(t,
                                      processId,
                                      id,
                                      userAuth,
                                      actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                      actInternal.getResourceUsername(t),
                                      actInternal.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      actInternal.suspend(t);
   }

   /**
    * Terminate this process or activity.
    */
   public void terminate() throws BaseException, CannotStop, NotRunning {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         terminate(t);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof CannotStop)
            throw (CannotStop)e;
         else if (e instanceof NotRunning)
            throw (NotRunning)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void terminate(SharkTransaction t) throws BaseException, CannotStop, NotRunning {
      WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_activity_terminate(t,
                                        processId,
                                        id,
                                        userAuth,
                                        actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                        actInternal.getResourceUsername(t),
                                        actInternal.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      actInternal.terminate(t);
   }

   /**
    * Abort the execution of this process or activity.
    */
   public void abort() throws BaseException, CannotStop, NotRunning {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         abort(t);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof CannotStop)
            throw (CannotStop)e;
         else if (e instanceof NotRunning)
            throw (NotRunning)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void abort(SharkTransaction t) throws BaseException, CannotStop, NotRunning {
      WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_activity_abort(t,
                                    processId,
                                    id,
                                    userAuth,
                                    actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                    actInternal.getResourceUsername(t),
                                    actInternal.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      actInternal.abort(t);
   }

   public int how_many_history() throws BaseException, HistoryNotAvailable {
      int ret = -1;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = how_many_history(t);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof HistoryNotAvailable)
            throw (HistoryNotAvailable)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public int how_many_history(SharkTransaction t) throws BaseException, HistoryNotAvailable {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
         try {
            sm.check_activity_how_many_history(t,
                                               processId,
                                               id,
                                               userAuth,
                                               actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                               actInternal.getResourceUsername(t),
                                               actInternal.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      try {
         EventAuditManagerInterface eam = SharkEngineManager
            .getInstance()
            .getEventAuditManager();
         if (null == eam)
            return 0;
         return eam.restoreActivityHistory(processId,id,t).size();
      } catch (Exception ex) {
         throw new BaseException("Problems while retrieving activity's history!",ex);
      }
   }

   /**
    * Search in the history for specific elements.
    * @param query Search criteria.
    * @param names_in_query elements to search.
    * @return Found history elements that meet the search criteria.
    */
   public WfEventAuditIterator get_iterator_history (String query, Map names_in_query) throws BaseException, HistoryNotAvailable {
      WfEventAuditIterator ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_iterator_history(t,query,names_in_query);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof HistoryNotAvailable)
            throw (HistoryNotAvailable)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public WfEventAuditIterator get_iterator_history (SharkTransaction t,String query, Map names_in_query) throws BaseException, HistoryNotAvailable {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
         try {
            sm.check_activity_get_iterator_history(t,
                                                   processId,
                                                   id,
                                                   userAuth,
                                                   actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                                   actInternal.getResourceUsername(t),
                                                   actInternal.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      WfEventAuditIterator it=SharkEngineManager.
         getInstance().
         getObjectFactory().
         createEventAuditIteratorWrapper(t,userAuth,processId,id);
      try {
         it.set_query_expression(t,query);
         it.set_names_in_expression(t,names_in_query);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
      return it;
   }

   /**
    * Getter for history sequence.
    * @param max_number Maximum number of element in result list.
    * @return List of History objects.
    */
   public WfEventAudit[] get_sequence_history (int max_number) throws BaseException, HistoryNotAvailable {
      WfEventAudit[] ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_sequence_history(t,max_number);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof HistoryNotAvailable)
            throw (HistoryNotAvailable)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public WfEventAudit[] get_sequence_history (SharkTransaction t,int max_number) throws BaseException, HistoryNotAvailable {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
         try {
            sm.check_activity_get_sequence_history(t,
                                                   processId,
                                                   id,
                                                   userAuth,
                                                   actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                                   actInternal.getResourceUsername(t),
                                                   actInternal.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      List history=SharkUtilities.createActivityHistoryEvents(t,userAuth,processId,id);
      if (max_number>history.size() || max_number<=0) {
         max_number=history.size();
      }
      WfEventAudit[] eas=new WfEventAudit[max_number];
      history.subList(0,max_number).toArray(eas);
      return eas;
   }

   /**
    * Predicate to check if a 'member' is an element of the history.
    * @return true if the element of the history, false otherwise.
    */
   public boolean is_member_of_history (WfExecutionObject member) throws BaseException {
      boolean ret = false;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = is_member_of_history(t,member);
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

   public boolean is_member_of_history (SharkTransaction t,WfExecutionObject member) throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
         try {
            sm.check_activity_is_member_of_history(t,
                                                   processId,
                                                   id,
                                                   userAuth,
                                                   actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                                   actInternal.getResourceUsername(t),
                                                   actInternal.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      boolean ret=false;
      List history=SharkUtilities.createActivityHistoryEvents(t,userAuth,processId,id);
      Iterator it=history.iterator();
      while (it.hasNext()) {
         WfEventAudit ea=(WfEventAudit)it.next();
         if (member instanceof WfActivity) {
            WfActivity act=(WfActivity)member;
            if (act.container(t).key(t).equals(ea.process_key()) &&
                act.key(t).equals(ea.activity_key())) {
               ret=true;
               break;
            }
         } else {
            if (member.key(t).equals(ea.process_key())) {
               ret=true;
               break;
            }
         }
      }
      return ret;
   }

   public UtcT last_state_time () throws BaseException {
      UtcT ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = last_state_time(t);
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

   public UtcT last_state_time (SharkTransaction t) throws BaseException {
      WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_activity_last_state_time(t,
                                              processId,
                                              id,
                                              userAuth,
                                              actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                              actInternal.getResourceUsername(t),
                                              actInternal.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      return actInternal.last_state_time(t);
   }

   /**
    * Gets the number of processes.
    */
   public int how_many_performer () throws BaseException {
      int ret = -1;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = how_many_performer(t);
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

   public int how_many_performer (SharkTransaction t) throws BaseException {
      WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_activity_how_many_performer(t,
                                                 processId,
                                                 id,
                                                 userAuth,
                                                 actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                                 actInternal.getResourceUsername(t),
                                                 actInternal.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      return ((actInternal.getPerformerId(t)!=null) ? 1:0);
   }

   /**
    * Gets an iterator of processes.
    */
   public WfProcessIterator get_iterator_performer () throws BaseException {
      WfProcessIterator ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_iterator_performer(t);
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

   public WfProcessIterator get_iterator_performer (SharkTransaction t) throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
         try {
            sm.check_activity_get_iterator_performer(t,
                                                     processId,
                                                     id,
                                                     userAuth,
                                                     actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                                     actInternal.getResourceUsername(t),
                                                     actInternal.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      return SharkEngineManager.
         getInstance().
         getObjectFactory().createProcessIteratorWrapper(t,userAuth,processId,id);
   }

   /**
    * A list of processes
    * @return List of WfProcess objects.
    */
   public WfProcess[] get_sequence_performer (int max_number) throws BaseException {
      WfProcess[] ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_sequence_performer(t,max_number);
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

   public WfProcess[] get_sequence_performer (SharkTransaction t,int max_number) throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
         try {
            sm.check_activity_get_sequence_performer(t,
                                                     processId,
                                                     id,
                                                     userAuth,
                                                     actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                                     actInternal.getResourceUsername(t),
                                                     actInternal.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      try {
         List l=SharkUtilities.createActivityPerformerWrapper(t,userAuth,processId,id);
         if (max_number > l.size() || max_number<=0) {
            max_number = l.size();
         }
         WfProcess[] ret = new WfProcess[l.size()];
         l.subList(0, max_number).toArray(ret);
         return ret;
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   /**
    * Checks if a WfProcess is associated with this requester object
    * @return true if the process is found.
    */
   public boolean is_member_of_performer (WfProcess member) throws BaseException {
      boolean ret = false;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = is_member_of_performer(t,member);
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

   public boolean is_member_of_performer (SharkTransaction t,WfProcess member) throws BaseException {
      WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_activity_is_member_of_performer(t,
                                                     processId,
                                                     id,
                                                     userAuth,
                                                     actInternal.container(t).requester(t).getResourceRequesterUsername(t),
                                                     actInternal.getResourceUsername(t),
                                                     actInternal.getAssignmentResourceIds(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      String performerId=actInternal.getPerformerId(t);
      return (performerId!=null &&member.key(t).equals(performerId));
   }

   public void receive_event (WfEventAudit event) throws BaseException, InvalidPerformer {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         receive_event(t,event);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void receive_event (SharkTransaction t,WfEventAudit event) throws BaseException, InvalidPerformer {
      WfActivityInternal actInternal=WfActivityWrapper.getActivityImpl(t,processId,id);
      actInternal.receive_event(t,event,null);
   }

   /**
    * Method toString
    *
    * @return   a String
    */
   public String toString () {
      return "[pId="+processId+",aId="+id+"]";
   }

   /**
    * It is assumed that there can't be two or more
    * activities having the same key.
    */
   public boolean equals (java.lang.Object obj) {
      if (!(obj instanceof WfActivity)) return false;
      WfActivity act=(WfActivity)obj;
      try {
         if (obj instanceof WfActivityWrapper) {
            return ((WfActivityWrapper)obj).id.equals(id);
         } else {
            return act.key().equals(id);
         }
      } catch (Exception ex) {
         return false;
      }
   }

   private static WfActivityInternal getActivityImpl (SharkTransaction t,String processId,String actId) throws BaseException {
      WfActivityInternal actImpl=SharkUtilities.getActivity(t,processId,actId);
      if (actImpl==null) throw new BaseException("Activity does not exist");
      return actImpl;
   }

}
