package org.enhydra.shark;

import org.enhydra.shark.api.client.wfmodel.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.timebase.UtcT;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfbase.InvalidQuery;
import org.enhydra.shark.api.client.wfbase.NameMismatch;
import org.enhydra.shark.api.common.SharkConstants;
import org.enhydra.shark.api.internal.eventaudit.EventAuditManagerInterface;
import org.enhydra.shark.api.internal.security.SecurityManager;
import org.enhydra.shark.api.internal.toolagent.ToolAgentGeneralException;
import org.enhydra.shark.api.internal.working.WfActivityInternal;
import org.enhydra.shark.api.internal.working.WfProcessInternal;
import org.enhydra.shark.api.internal.working.WfRequesterInternal;

/**
 * WfProcessWrapper - Workflow Process Object implementation.
 * @author Sasa Bojanic, Vladimir Puskas
 * @version 1.0.1
 */
public class WfProcessWrapper implements WfProcess {

   private String userAuth;
   private String mgrName;
   private String processId;

   /**
    * Creates new WfProcessWrapper.
    */
   protected WfProcessWrapper(String userAuth,String mgrName,String processId) {
      this.userAuth=userAuth;
      this.mgrName=mgrName;
      this.processId = processId;
   }


   /**
    * Retrieves the requestor of this process.
    *
    * @return   a WfRequester.
    *
    * @exception   BaseException.
    */
   public WfRequester requester() throws BaseException {
      WfRequester ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = requester(t);
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
    * Method requester.
    *
    * @param    t                   a  SharkTransaction.
    *
    * @return   a WfRequester
    *
    * @exception   BaseException
    */
   public WfRequester requester(SharkTransaction t) throws BaseException {
      WfProcessInternal procInternal=WfProcessWrapper.getProcessImpl(t,processId);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_process_requester(t,
                                       processId,
                                       userAuth,
                                       procInternal.requester(t).getResourceRequesterUsername(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      try {
         WfRequester req=null;
         WfRequesterInternal requester=procInternal.requester(t);
         if (requester instanceof WfActivityInternal) {
            WfActivityInternal act=(WfActivityInternal)requester;
            req=SharkEngineManager.getInstance().getObjectFactory().createActivityWrapper(userAuth,act.manager_name(t),act.process_id(t),act.key(t));
         } else {
            WfRequester r=requester.getExternalRequester(t);
            if (r!=null) {
               req=r;
            } else {
               String reqUname=((WfRequesterInternal)requester).getResourceRequesterUsername(t);
               req=SharkEngineManager.getInstance().getObjectFactory().createRequesterWrapper(userAuth,reqUname);
            }
         }
         return req;
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   /**
    * Set the requester for this process.
    *
    * @param    new_value           a  WfRequester
    *
    * @exception   BaseException
    * @exception   CannotChangeRequester
    */
   public void set_requester (WfRequester new_value) throws BaseException, CannotChangeRequester {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         set_requester(t, new_value);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof CannotChangeRequester)
            throw (CannotChangeRequester)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   /**
    * Method set_requester
    *
    * @param    t                   a  SharkTransaction
    * @param    new_value           a  WfRequester
    *
    * @exception   BaseException
    * @exception   CannotChangeRequester
    */
   public void set_requester(SharkTransaction t,WfRequester new_value) throws BaseException, CannotChangeRequester {
      WfProcessInternal procInternal=WfProcessWrapper.getProcessImpl(t,processId);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_process_set_requester(t,
                                           processId,
                                           userAuth,
                                           procInternal.requester(t).getResourceRequesterUsername(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      WfRequesterInternal requester=procInternal.requester(t);
      if (requester instanceof WfActivityInternal) {
         throw new CannotChangeRequester("Can't change requester for a process instantiated as a subflow!");
      } else {
         String extReqClassName=null;
         if (new_value!=null) {
            extReqClassName=new_value.getClass().getName();
         }
         procInternal.setExternalRequesterClassName(t,extReqClassName);
      }
   }

   /**
    * Retrieve the no of activities in this process.
    *
    * @return   an int
    *
    * @exception   BaseException
    */
   public int how_many_step () throws BaseException {
      int ret = -1;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = how_many_step(t);
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
    * Method how_many_step
    *
    * @param    t                   a  SharkTransaction
    *
    * @return   an int
    *
    * @exception   BaseException
    */
   public int how_many_step (SharkTransaction t) throws BaseException {
      WfProcessInternal procInternal=WfProcessWrapper.getProcessImpl(t,processId);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_process_how_many_step(t,
                                           processId,
                                           userAuth,
                                           procInternal.requester(t).getResourceRequesterUsername(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      return procInternal.how_many_step(t);
   }

   /**
    * Retrieve the Iterator of active activities of this process.
    *
    * @return   a WfActivityIterator
    *
    * @exception   BaseException
    */
   public WfActivityIterator get_iterator_step () throws BaseException {
      WfActivityIterator ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_iterator_step(t);
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
    * Method get_iterator_step
    *
    * @param    t                   a  SharkTransaction
    *
    * @return   a WfActivityIterator
    *
    * @exception   BaseException
    */
   public WfActivityIterator get_iterator_step (SharkTransaction t) throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         WfProcessInternal procInternal=WfProcessWrapper.getProcessImpl(t,processId);
         try {
            sm.check_process_get_iterator_step(t,
                                               processId,
                                               userAuth,
                                               procInternal.requester(t).getResourceRequesterUsername(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      return SharkEngineManager.getInstance().getObjectFactory().createActivityIteratorWrapper(t,userAuth,processId);
   }

   /**
    * Retrieve the List of activities of this process.
    *
    * @param max_number High limit of elements in the result set (0->all).
    *
    * @return   a WfActivity[]
    *
    * @exception   BaseException
    */
   public WfActivity[] get_sequence_step (int max_number) throws BaseException {
      WfActivity[] ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_sequence_step(t, max_number);
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
    * Method get_sequence_step
    *
    * @param    t                   a  SharkTransaction
    * @param    max_number          an int
    *
    * @return   a WfActivity[]
    *
    * @exception   BaseException
    *
    */
   public WfActivity[] get_sequence_step (SharkTransaction t,int max_number) throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         WfProcessInternal procInternal=WfProcessWrapper.getProcessImpl(t,processId);
         try {
            sm.check_process_get_sequence_step(t,
                                               processId,
                                               userAuth,
                                               procInternal.requester(t).getResourceRequesterUsername(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      // TODO: see OMG/WfMC docu which acitivities to consider:
      // all or active ones only
      List alist = SharkUtilities.createProcessActivityWrappers(t,userAuth,processId);
      if (max_number > alist.size() || max_number<=0) {
         max_number = alist.size();
      }
      WfActivity[] ret = new WfActivity[max_number];
      alist.subList(0, max_number).toArray(ret);
      return ret;
   }

   /**
    * Check if some activity is a member of this process.
    *
    * @param    member              a  WfActivity
    *
    * @return true if the specific activity is a member of this process,
    * false otherwise.
    *
    * @exception   BaseException
    */
   public boolean is_member_of_step (WfActivity member) throws BaseException {
      boolean ret = false;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = is_member_of_step(t, member);
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
    * Method is_member_of_step
    *
    * @param    t                   a  SharkTransaction
    * @param    member              a  WfActivity
    *
    * @return   a boolean
    *
    * @exception   BaseException
    */
   public boolean is_member_of_step (SharkTransaction t,WfActivity member) throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         WfProcessInternal procInternal=WfProcessWrapper.getProcessImpl(t,processId);
         try {
            sm.check_process_is_member_of_step(t,
                                               processId,
                                               userAuth,
                                               procInternal.requester(t).getResourceRequesterUsername(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      String pId=member.container(t).key(t);
      return pId.equals(this.processId);
   }

   /**
    * Retrieve the WfProcessMgr of this process.
    *
    * @return   a WfProcessMgr
    *
    * @exception   BaseException
    */
   public WfProcessMgr manager () throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm==null) {
         return SharkEngineManager
         .getInstance()
         .getObjectFactory()
         .createProcessMgrWrapper(userAuth,mgrName);
      }
      WfProcessMgr ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = manager(t);
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
    * Method manager
    *
    * @param    t                   a  SharkTransaction
    *
    * @return   a WfProcessMgr
    *
    * @exception   BaseException
    */
   public WfProcessMgr manager (SharkTransaction t) throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            WfProcessInternal procInternal=WfProcessWrapper.getProcessImpl(t,processId);
            sm.check_process_manager(t,
                                     processId,
                                     userAuth,
                                     procInternal.requester(t).getResourceRequesterUsername(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      return SharkEngineManager
         .getInstance()
         .getObjectFactory()
         .createProcessMgrWrapper(userAuth,mgrName);
   }

   /**
    * Retrieve the result for this process.
    *
    * @return   a Map
    *
    * @exception   BaseException
    * @exception   ResultNotAvailable
    *
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

   /**
    * Method result
    *
    * @param    t                   a  SharkTransaction
    *
    * @return   a Map
    *
    * @exception   BaseException
    * @exception   ResultNotAvailable
    */
   public Map result (SharkTransaction t) throws BaseException, ResultNotAvailable {
      WfProcessInternal procInternal=WfProcessWrapper.getProcessImpl(t,processId);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_process_result(t,
                                    processId,
                                    userAuth,
                                    procInternal.requester(t).getResourceRequesterUsername(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      return procInternal.result(t);
   }

   /**
    * Starts the process - creates a separate thread.
    *
    * @exception   BaseException
    * @exception   CannotStart
    * @exception   AlreadyRunning
    */
   public void start() throws BaseException, CannotStart, AlreadyRunning {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         start(t);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof CannotStart)
            throw (CannotStart)e;
         else if (e instanceof AlreadyRunning)
            throw (AlreadyRunning)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   /**
    * Method start
    *
    * @param    t                   a  SharkTransaction
    *
    * @exception   BaseException
    * @exception   CannotStart
    * @exception   AlreadyRunning
    */
   public void start(SharkTransaction t) throws BaseException, CannotStart, AlreadyRunning {
      WfProcessInternal procInternal=WfProcessWrapper.getProcessImpl(t,processId);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_process_start(t,
                                   processId,
                                   userAuth,
                                   procInternal.requester(t).getResourceRequesterUsername(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      try {
         procInternal.start(t);
      } catch (ToolAgentGeneralException tage) {
         throw new BaseException(tage);
      }
   }

   /**
    * Retrieve the iterator of activities in some specific state.
    *
    * @param    state               a  String
    *
    * @return   a WfActivityIterator
    *
    * @exception   BaseException
    * @exception   InvalidState
    */
   public WfActivityIterator get_activities_in_state (String state) throws BaseException, InvalidState {
      WfActivityIterator ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_activities_in_state(t, state);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof InvalidState)
            throw (InvalidState)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   /**
    * Method get_activities_in_state
    *
    * @param    t                   a  SharkTransaction
    * @param    state               a  String
    *
    * @return   a WfActivityIterator
    *
    * @exception   BaseException
    * @exception   InvalidState
    */
   public WfActivityIterator get_activities_in_state (SharkTransaction t,String state) throws BaseException, InvalidState {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         WfProcessInternal procInternal=WfProcessWrapper.getProcessImpl(t,processId);
         try {
            sm.check_process_get_activities_in_state(t,
                                                     processId,
                                                     userAuth,
                                                     procInternal.requester(t).getResourceRequesterUsername(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      if (!SharkConstants.possibleActivityStates.contains(state)) {
         throw new InvalidState("The activity state "+state+" is not valid - can't get activities in such state!");
      }

      WfActivityIterator ret = SharkEngineManager.getInstance().getObjectFactory().createActivityIteratorWrapper(t,userAuth, processId);
      try {
         ret.set_query_expression(t, SharkConstants.QUERY_STATE_PREFIX+"state.equals(\""+state+"\")");
      } catch (InvalidQuery iq) {
         throw new BaseException(iq);
      }
      return ret;
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
      WfProcessInternal procInternal=WfProcessWrapper.getProcessImpl(t,processId);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_process_workflow_state(t,
                                            processId,
                                            userAuth,
                                            procInternal.requester(t).getResourceRequesterUsername(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      String state=procInternal.state(t);
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
      WfProcessInternal procInternal=WfProcessWrapper.getProcessImpl(t,processId);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_process_while_open(t,
                                        processId,
                                        userAuth,
                                        procInternal.requester(t).getResourceRequesterUsername(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      String state=procInternal.state(t);
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
      WfProcessInternal procInternal=WfProcessWrapper.getProcessImpl(t,processId);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_process_why_not_running(t,
                                             processId,
                                             userAuth,
                                             procInternal.requester(t).getResourceRequesterUsername(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      String state=procInternal.state(t);
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
      WfProcessInternal procInternal=WfProcessWrapper.getProcessImpl(t,processId);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_process_how_closed(t,
                                        processId,
                                        userAuth,
                                        procInternal.requester(t).getResourceRequesterUsername(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      String state=procInternal.state(t);
      if (state.equals(SharkConstants.STATE_CLOSED_COMPLETED)) {
         return how_closedType.completed;
      } else if (state.equals(SharkConstants.STATE_CLOSED_TERMINATED)) {
         return how_closedType.terminated;
      } else {
         return how_closedType.aborted;
      }
   }

   /**
    * Method valid_states
    *
    * @return   a String[]
    *
    * @exception   BaseException
    */
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

   /**
    * Method valid_states
    *
    * @param    t                   a  SharkTransaction
    *
    * @return   a String[]
    *
    * @exception   BaseException
    */
   public String[] valid_states (SharkTransaction t) throws BaseException {
      WfProcessInternal procInternal=WfProcessWrapper.getProcessImpl(t,processId);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_process_valid_states(t,
                                          processId,
                                          userAuth,
                                          procInternal.requester(t).getResourceRequesterUsername(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      List vs=SharkUtilities.valid_process_states(procInternal.state(t));
      String[] vss=new String[vs.size()];
      vs.toArray(vss);
      return vss;
   }

   /**
    * Method state
    *
    * @return   a String
    *
    * @exception   BaseException
    */
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

   /**
    * Method state
    *
    * @param    t                   a  SharkTransaction
    *
    * @return   a String
    *
    * @exception   BaseException
    */
   public String state (SharkTransaction t) throws BaseException {
      WfProcessInternal procInternal=WfProcessWrapper.getProcessImpl(t,processId);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_process_state(t,
                                   processId,
                                   userAuth,
                                   procInternal.requester(t).getResourceRequesterUsername(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      return procInternal.state(t);
   }

   /**
    * Method change_state
    *
    * @param    new_state           a  String
    *
    * @exception   BaseException
    * @exception   InvalidState
    * @exception   TransitionNotAllowed
    */
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

   /**
    * Method change_state
    *
    * @param    t                   a  SharkTransaction
    * @param    new_state           a  String
    *
    * @exception   BaseException
    * @exception   InvalidState
    * @exception   TransitionNotAllowed
    */
   public void change_state (SharkTransaction t,String new_state) throws BaseException, InvalidState, TransitionNotAllowed {
      WfProcessInternal procInternal=WfProcessWrapper.getProcessImpl(t,processId);
      String curState=procInternal.state(t);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_process_change_state(t,
                                          processId,
                                          userAuth,
                                          procInternal.requester(t).getResourceRequesterUsername(t),
                                          curState,
                                          new_state);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      if (!SharkConstants.possibleProcessStates.contains(new_state)) {
         throw new InvalidState("Can't change process state to "+new_state+" - no such state!");
      }
      if (!SharkUtilities.valid_process_states(procInternal.state(t)).contains(new_state)) {
         throw new TransitionNotAllowed("Current process state is "+curState+" - can't change to state "+new_state+"!");
      }
      if (new_state.equals(SharkConstants.STATE_CLOSED_ABORTED)) {
         try {
            procInternal.abort(t);
         } catch (CannotStop cns) {
            throw new TransitionNotAllowed(cns);
         } catch (NotRunning nr) {
            throw new TransitionNotAllowed(nr);
         }
      } else if (new_state.equals(SharkConstants.STATE_CLOSED_COMPLETED)) {
         throw new TransitionNotAllowed("Current process state is "+curState+" - can't change to state "+new_state+"!");
      } else if (new_state.equals(SharkConstants.STATE_CLOSED_TERMINATED)) {
         try {
            procInternal.terminate(t);
         } catch (CannotStop cns) {
            throw new TransitionNotAllowed(cns);
         } catch (NotRunning nr) {
            throw new TransitionNotAllowed(nr);
         }
      } else if (new_state.equals(SharkConstants.STATE_OPEN_NOT_RUNNING_NOT_STARTED)) {
         throw new TransitionNotAllowed("Current process state is "+curState+" - can't change to state "+new_state+"!");
      } else if (new_state.equals(SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED)) {
         try {
            procInternal.suspend(t);
         } catch (AlreadySuspended as) {
            throw new TransitionNotAllowed(as);
         } catch (CannotSuspend cns) {
            throw new TransitionNotAllowed(cns);
         } catch (NotRunning nr) {
            throw new TransitionNotAllowed(nr);
         }
      } else { //new state is open.running
         try {
            procInternal.start(t);
         } catch (CannotStart cns) {
            throw new TransitionNotAllowed(cns);
         } catch (AlreadyRunning ar) {
            throw new TransitionNotAllowed(ar);
         } catch (ToolAgentGeneralException tage) {
            throw new BaseException(tage);
         }
      }
   }

   /**
    * Method name
    *
    * @return   a String
    *
    * @exception   BaseException
    */
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

   /**
    * Method name
    *
    * @param    t                   a  SharkTransaction
    *
    * @return   a String
    *
    * @exception   BaseException
    */
   public String name (SharkTransaction t) throws BaseException {
      WfProcessInternal procInternal=WfProcessWrapper.getProcessImpl(t,processId);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_process_name(t,
                                  processId,
                                  userAuth,
                                  procInternal.requester(t).getResourceRequesterUsername(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      return procInternal.name(t);
   }

   /**
    * Method set_name
    *
    * @param    new_value           a  String
    *
    * @exception   BaseException
    */
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

   /**
    * Method set_name
    *
    * @param    t                   a  SharkTransaction
    * @param    new_value           a  String
    *
    * @exception   BaseException
    */
   public void set_name (SharkTransaction t,String new_value) throws BaseException {
      WfProcessInternal procInternal=WfProcessWrapper.getProcessImpl(t,processId);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_process_set_name(t,
                                      processId,
                                      userAuth,
                                      procInternal.requester(t).getResourceRequesterUsername(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      procInternal.set_name(t, new_value);
   }

   /**
    * Method key
    *
    * @return   a String
    *
    * @exception   BaseException
    */
   public String key () throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm==null) {
         return processId;
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

   /**
    * Method key
    *
    * @param    t                   a  SharkTransaction
    *
    * @return   a String
    *
    * @exception   BaseException
    */
   public String key (SharkTransaction t) throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         WfProcessInternal procInternal=WfProcessWrapper.getProcessImpl(t,processId);
         try {
            sm.check_process_key(t,
                                 processId,
                                 userAuth,
                                 procInternal.requester(t).getResourceRequesterUsername(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      return processId;
   }

   /**
    * Method description
    *
    * @return   a String
    *
    * @exception   BaseException
    */
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

   /**
    * Method description
    *
    * @param    t                   a  SharkTransaction
    *
    * @return   a String
    *
    * @exception   BaseException
    */
   public String description (SharkTransaction t) throws BaseException {
      WfProcessInternal procInternal=WfProcessWrapper.getProcessImpl(t,processId);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_process_description(t,
                                         processId,
                                         userAuth,
                                         procInternal.requester(t).getResourceRequesterUsername(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      return procInternal.description(t);
   }

   /**
    * Method set_description
    *
    * @param    new_value           a  String
    *
    * @exception   BaseException
    */
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

   /**
    * Method set_description
    *
    * @param    t                   a  SharkTransaction
    * @param    new_value           a  String
    *
    * @exception   BaseException
    */
   public void set_description (SharkTransaction t,String new_value) throws BaseException {
      WfProcessInternal procInternal=WfProcessWrapper.getProcessImpl(t,processId);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_process_set_description(t,
                                             processId,
                                             userAuth,
                                             procInternal.requester(t).getResourceRequesterUsername(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      procInternal.set_description(t, new_value);
   }

   /**
    * Method process_context
    *
    * @return   a Map
    *
    * @exception   BaseException
    */
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

   /**
    * Method process_context
    *
    * @param    t                   a  SharkTransaction
    *
    * @return   a Map
    *
    * @exception   BaseException
    */
   public Map process_context (SharkTransaction t) throws BaseException {
      WfProcessInternal procInternal=WfProcessWrapper.getProcessImpl(t,processId);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_process_process_context(t,
                                             processId,
                                             userAuth,
                                             procInternal.requester(t).getResourceRequesterUsername(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      return procInternal.process_context(t);
   }

   /**
    * Method set_process_context
    *
    * @param    new_value           a  Map
    *
    * @exception   BaseException
    * @exception   InvalidData
    * @exception   UpdateNotAllowed
    */
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

   /**
    * Method set_process_context
    *
    * @param    t                   a  SharkTransaction
    * @param    new_value           a  Map
    *
    * @exception   BaseException
    * @exception   InvalidData
    * @exception   UpdateNotAllowed
    */
   public void set_process_context (SharkTransaction t,Map new_value) throws BaseException, InvalidData, UpdateNotAllowed {
      WfProcessInternal procInternal=WfProcessWrapper.getProcessImpl(t,processId);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_process_set_process_context(t,
                                                 processId,
                                                 userAuth,
                                                 procInternal.requester(t).getResourceRequesterUsername(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      procInternal.set_process_context(t, new_value);
   }

   /**
    * Method priority
    *
    * @return   a short
    *
    * @exception   BaseException
    */
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

   /**
    * Method priority
    *
    * @param    t                   a  SharkTransaction
    *
    * @return   a short
    *
    * @exception   BaseException
    */
   public short priority (SharkTransaction t) throws BaseException {
      WfProcessInternal procInternal=WfProcessWrapper.getProcessImpl(t,processId);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_process_priority(t,
                                      processId,
                                      userAuth,
                                      procInternal.requester(t).getResourceRequesterUsername(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      return procInternal.priority(t);
   }

   /**
    * Method set_priority
    *
    * @param    new_value           a  short
    *
    * @exception   BaseException
    */
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

   /**
    * Method set_priority
    *
    * @param    t                   a  SharkTransaction
    * @param    new_value           a  short
    *
    * @exception   BaseException
    */
   public void set_priority (SharkTransaction t,short new_value) throws BaseException {
      WfProcessInternal procInternal=WfProcessWrapper.getProcessImpl(t,processId);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_process_set_priority(t,
                                          processId,
                                          userAuth,
                                          procInternal.requester(t).getResourceRequesterUsername(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      procInternal.set_priority(t, new_value);
   }

   /**
    * Resume this process.
    *
    * @exception   BaseException
    * @exception   CannotResume
    * @exception   NotSuspended
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

   /**
    * Method resume
    *
    * @param    t                   a  SharkTransaction
    *
    * @exception   BaseException
    * @exception   CannotResume
    * @exception   NotSuspended
    */
   public void resume(SharkTransaction t) throws BaseException, CannotResume, NotSuspended {
      WfProcessInternal procInternal=WfProcessWrapper.getProcessImpl(t,processId);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_process_resume(t,
                                    processId,
                                    userAuth,
                                    procInternal.requester(t).getResourceRequesterUsername(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      procInternal.resume(t);
   }

   /**
    * Suspend this process.
    *
    * @exception   BaseException
    * @exception   CannotSuspend
    * @exception   NotRunning
    * @exception   AlreadySuspended
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

   /**
    * Method suspend
    *
    * @param    t                   a  SharkTransaction
    *
    * @exception   BaseException
    * @exception   CannotSuspend
    * @exception   NotRunning
    * @exception   AlreadySuspended
    */
   public void suspend(SharkTransaction t) throws BaseException, CannotSuspend, NotRunning, AlreadySuspended {
      WfProcessInternal procInternal=WfProcessWrapper.getProcessImpl(t,processId);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_process_suspend(t,
                                     processId,
                                     userAuth,
                                     procInternal.requester(t).getResourceRequesterUsername(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      procInternal.suspend(t);
   }

   /**
    * Terminate this process.
    *
    * @exception   BaseException
    * @exception   CannotStop
    * @exception   NotRunning
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

   /**
    * Method terminate
    *
    * @param    t                   a  SharkTransaction
    *
    * @exception   BaseException
    * @exception   CannotStop
    * @exception   NotRunning
    */
   public void terminate(SharkTransaction t) throws BaseException, CannotStop, NotRunning {
      WfProcessInternal procInternal=WfProcessWrapper.getProcessImpl(t,processId);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_process_terminate(t,
                                       processId,
                                       userAuth,
                                       procInternal.requester(t).getResourceRequesterUsername(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      procInternal.terminate(t);
   }

   /**
    * Abort the execution of this process.
    *
    * @exception   BaseException
    * @exception   CannotStop
    * @exception   NotRunning
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

   /**
    * Method abort
    *
    * @param    t                   a  SharkTransaction
    *
    * @exception   BaseException
    * @exception   CannotStop
    * @exception   NotRunning
    */
   public void abort(SharkTransaction t) throws BaseException, CannotStop, NotRunning {
      WfProcessInternal procInternal=WfProcessWrapper.getProcessImpl(t,processId);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_process_abort(t,
                                   processId,
                                   userAuth,
                                   procInternal.requester(t).getResourceRequesterUsername(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      procInternal.abort(t);
   }

   /**
    * Method how_many_history
    *
    * @return   an int
    *
    * @exception   BaseException
    * @exception   HistoryNotAvailable
    */
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

   /**
    * Method how_many_history
    *
    * @param    t                   a  SharkTransaction
    *
    * @return   an int
    *
    * @exception   BaseException
    * @exception   HistoryNotAvailable
    */
   public int how_many_history(SharkTransaction t) throws BaseException, HistoryNotAvailable {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         WfProcessInternal procInternal=WfProcessWrapper.getProcessImpl(t,processId);
         try {
            sm.check_process_how_many_history(t,
                                              processId,
                                              userAuth,
                                              procInternal.requester(t).getResourceRequesterUsername(t));
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
         return eam.restoreProcessHistory(processId,t).size();
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   /**
    * Search in the history for specific elements.
    *
    * @param    query               a  String
    * @param    names_in_query      a  Map
    *
    * @return Found history elements that meet the search criteria.
    *
    * @exception   BaseException
    * @exception   HistoryNotAvailable
    */
   public WfEventAuditIterator get_iterator_history (String query, Map names_in_query) throws BaseException, HistoryNotAvailable {
      WfEventAuditIterator ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_iterator_history(t, query, names_in_query);
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

   /**
    * Method get_iterator_history
    *
    * @param    t                   a  SharkTransaction
    * @param    query               a  String
    * @param    names_in_query      a  Map
    *
    * @return   a WfEventAuditIterator
    *
    * @exception   BaseException
    * @exception   HistoryNotAvailable
    */
   public WfEventAuditIterator get_iterator_history (SharkTransaction t,String query, Map names_in_query) throws BaseException, HistoryNotAvailable {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         WfProcessInternal procInternal=WfProcessWrapper.getProcessImpl(t,processId);
         try {
            sm.check_process_get_iterator_history(t,
                                                  processId,
                                                  userAuth,
                                                  procInternal.requester(t).getResourceRequesterUsername(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      WfEventAuditIterator ret = SharkEngineManager.getInstance().getObjectFactory().createEventAuditIteratorWrapper(t,userAuth,processId);
      try {
         ret.set_query_expression(t, query);
         ret.set_names_in_expression(t, names_in_query);
      } catch (NameMismatch e) {
         throw new HistoryNotAvailable(e);
      } catch (InvalidQuery e) {
         throw new HistoryNotAvailable(e);
      }
      return ret;
   }

   /**
    * Getter for history sequence.
    *
    * @param    max_number          an int
    *
    * @return List of History objects.
    *
    * @exception   BaseException
    * @exception   HistoryNotAvailable
    *
    */
   public WfEventAudit[] get_sequence_history (int max_number) throws BaseException, HistoryNotAvailable {
      WfEventAudit[] ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_sequence_history(t, max_number);
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

   /**
    * Method get_sequence_history
    *
    * @param    t                   a  SharkTransaction
    * @param    max_number          an int
    *
    * @return   a WfEventAudit[]
    *
    * @exception   BaseException
    * @exception   HistoryNotAvailable
    */
   public WfEventAudit[] get_sequence_history (SharkTransaction t,int max_number) throws BaseException, HistoryNotAvailable {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         WfProcessInternal procInternal=WfProcessWrapper.getProcessImpl(t,processId);
         try {
            sm.check_process_get_sequence_history(t,
                                                  processId,
                                                  userAuth,
                                                  procInternal.requester(t).getResourceRequesterUsername(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      List history=SharkUtilities.createProcessHistoryEvents(t,userAuth,processId);
      if (max_number>history.size() || max_number<=0) {
         max_number=history.size();
      }
      WfEventAudit[] eas=new WfEventAudit[max_number];
      history.subList(0,max_number).toArray(eas);
      return eas;
   }

   /**
    * Checks if a 'member' is an element of the history.
    *
    * @param    member              a  WfExecutionObject
    *
    * @return true if the element of the history, false otherwise.
    *
    * @exception   BaseException
    */
   public boolean is_member_of_history (WfExecutionObject member) throws BaseException {
      boolean ret = false;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = is_member_of_history(t, member);
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
    * Method is_member_of_history
    *
    * @param    t                   a  SharkTransaction
    * @param    member              a  WfExecutionObject
    *
    * @return   a boolean
    *
    * @exception   BaseException
    */
   public boolean is_member_of_history (SharkTransaction t,WfExecutionObject member) throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         WfProcessInternal procInternal=WfProcessWrapper.getProcessImpl(t,processId);
         try {
            sm.check_process_is_member_of_history(t,
                                                  processId,
                                                  userAuth,
                                                  procInternal.requester(t).getResourceRequesterUsername(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      boolean ret=false;
      List history=SharkUtilities.createProcessHistoryEvents(t,userAuth,processId);
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

   /**
    * Method last_state_time
    *
    * @return   an UtcT
    *
    * @exception   BaseException
    */
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

   /**
    * Method last_state_time
    *
    * @param    t                   a  SharkTransaction
    *
    * @return   an UtcT
    *
    * @exception   BaseException
    */
   public UtcT last_state_time (SharkTransaction t) throws BaseException {
      WfProcessInternal procInternal=WfProcessWrapper.getProcessImpl(t,processId);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_process_last_state_time(t,
                                             processId,
                                             userAuth,
                                             procInternal.requester(t).getResourceRequesterUsername(t));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      return procInternal.last_state_time(t);
   }

   /**
    * Method toString
    *
    * @return   a String
    */
   public String toString () {
      return "[key="+processId+"]";
   }

   /**
    * It is assumed that there can't be two or more
    * processes having the same key.
    */
   public boolean equals (java.lang.Object obj) {
      if (!(obj instanceof WfProcess)) return false;
      WfProcess proc=(WfProcess)obj;
      try {
         if (obj instanceof WfProcessWrapper) {
            return ((WfProcessWrapper)obj).processId.equals(processId);
         } else {
            return proc.key().equals(processId);
         }
      } catch (Exception ex) {
         return false;
      }
   }

   private static WfProcessInternal getProcessImpl (
      SharkTransaction t,
      String procId) throws BaseException {
      WfProcessInternal proc=SharkUtilities.getProcess(t,procId);
      if (proc==null) throw new BaseException("Process does not exist");
      return proc;
   }

}

