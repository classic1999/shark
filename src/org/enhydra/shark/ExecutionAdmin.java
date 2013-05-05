package org.enhydra.shark;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfmodel.WfActivity;
import org.enhydra.shark.api.client.wfmodel.WfActivityIterator;
import org.enhydra.shark.api.client.wfmodel.WfAssignment;
import org.enhydra.shark.api.client.wfmodel.WfAssignmentIterator;
import org.enhydra.shark.api.client.wfmodel.WfProcess;
import org.enhydra.shark.api.client.wfmodel.WfProcessIterator;
import org.enhydra.shark.api.client.wfmodel.WfProcessMgr;
import org.enhydra.shark.api.client.wfmodel.WfResource;
import org.enhydra.shark.api.client.wfservice.ConnectFailed;
import org.enhydra.shark.api.client.wfservice.ExecutionAdministration;
import org.enhydra.shark.api.client.wfservice.NotConnected;
import org.enhydra.shark.api.client.wfservice.WfProcessMgrIterator;
import org.enhydra.shark.api.client.wfservice.WfResourceIterator;
import org.enhydra.shark.api.common.SharkConstants;
import org.enhydra.shark.api.internal.caching.CacheMgr;
import org.enhydra.shark.api.internal.instancepersistence.ActivityPersistenceInterface;
import org.enhydra.shark.api.internal.instancepersistence.ActivityVariablePersistenceInterface;
import org.enhydra.shark.api.internal.instancepersistence.PersistenceException;
import org.enhydra.shark.api.internal.instancepersistence.PersistentManagerInterface;
import org.enhydra.shark.api.internal.instancepersistence.ProcessPersistenceInterface;
import org.enhydra.shark.api.internal.instancepersistence.ProcessVariablePersistenceInterface;
import org.enhydra.shark.api.internal.processlocking.LockMaster;
import org.enhydra.shark.api.internal.security.SecurityManager;
import org.enhydra.shark.api.internal.working.CallbackUtilities;
import org.enhydra.shark.api.internal.working.WfActivityInternal;
import org.enhydra.shark.api.internal.working.WfAssignmentInternal;
import org.enhydra.shark.api.internal.working.WfProcessInternal;
import org.enhydra.shark.api.internal.working.WfProcessMgrInternal;
import org.enhydra.shark.api.internal.working.WfResourceInternal;

/**
 * The client interface through which client accesses the engine
 * objects, and performs the various actions on engine.
 *
 * @author Sasa Bojanic
 * @version 1.1
 */
public class ExecutionAdmin implements ExecutionAdministration {

   private String userId;
   private boolean connected=false;

   private String connectionKey;


   private CallbackUtilities cus;
   protected ExecutionAdmin () {
      this.cus=SharkEngineManager.getInstance().getCallbackUtilities();
   }

   public void connect (String userId,String password,String engineName,String scope) throws BaseException, ConnectFailed {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         connect(t, userId, password, engineName, scope);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof ConnectFailed)
            throw (ConnectFailed)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void connect (SharkTransaction t,String userId,String password,String engineName,String scope) throws BaseException, ConnectFailed {
      this.userId=userId;

      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_executionadministration_connect(t,userId);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      try {
         if (!SharkUtilities.validateUser(userId,password)) {
            cus.info("ExecutionAdmin -> Login failed: Invalid username or password"+userId);
            throw new ConnectFailed("Connection failed, invalid username or password");
         }

         if (SharkUtilities.getResource(t, userId)==null) {
            WfResourceInternal resInternal=SharkEngineManager
               .getInstance()
               .getObjectFactory()
               .createResource(t,userId);
         }
         connectionKey=SharkUtilities.connect(userId);
         connected=true;
         cus.info("ExecutionAdmin -> User "+userId+" with connection key "+connectionKey+" is logged on");
      } catch (Exception ex) {
         if (ex instanceof ConnectFailed) {
            throw (ConnectFailed)ex;
         }
         cus.info("ExecutionAdmin -> Unexpected error while user "+userId+" loggs on");
         ex.printStackTrace();
         throw new BaseException(ex);
      }
   }

   public void disconnect () throws BaseException, NotConnected {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         disconnect(t);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void disconnect (SharkTransaction t) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         SharkUtilities.disconnect(connectionKey);
         connected=false;
         cus.info("ExecutionAdmin -> User "+userId+" with connection key "+connectionKey+" is logged off");
      } catch (Exception ex) {
         if (ex instanceof NotConnected) {
            throw (NotConnected)ex;
         }
         cus.info("ExecutionAdmin -> Unexpected error while user "+userId+" loggs off");
         throw new BaseException(ex);
      }
   }

   public WfProcessMgrIterator get_iterator_processmgr () throws BaseException, NotConnected {
      WfProcessMgrIterator ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_iterator_processmgr(t);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public WfProcessMgrIterator get_iterator_processmgr (SharkTransaction t) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_executionadministration_get_iterator_processmgr(t,userId);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      try {
         WfProcessMgrIterator ret=SharkEngineManager.getInstance().
            getObjectFactory().createProcessMgrIteratorWrapper(t,userId);
         return ret;
      } catch (Exception ex) {
         cus.info("ExecutionAdmin -> Unexpected error while user tries to get process managers iterator");
         throw new BaseException(ex);
      }
   }

   public WfProcessMgr[] get_sequence_processmgr (int max_number) throws BaseException, NotConnected {
      WfProcessMgr[] ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_sequence_processmgr(t, max_number);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public WfProcessMgr[] get_sequence_processmgr (SharkTransaction t,int max_number) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_executionadministration_get_sequence_processmgr(t,userId);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      try {
         return SharkEngineManager
            .getInstance()
            .getObjectFactory()
            .createProcessMgrIteratorWrapper(t,userId)
            .get_next_n_sequence (t,max_number);
      } catch (Exception ex) {
         cus.info("ExecutionAdmin -> Unexpected error while user tries to get the list of process managers");
         throw new BaseException(ex);
      }
   }

   public WfResourceIterator get_iterator_resource () throws BaseException, NotConnected {
      WfResourceIterator ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_iterator_resource(t);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public WfResourceIterator get_iterator_resource (SharkTransaction t) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_executionadministration_get_iterator_resource(t,userId);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      try {
         return SharkEngineManager
            .getInstance()
            .getObjectFactory()
            .createResourceIteratorWrapper(t,userId);
      } catch (Exception ex) {
         cus.info("ExecutionAdmin -> Unexpected error while user tries to get resource's iterator");
         throw new BaseException(ex);
      }
   }

   public WfResource[] get_sequence_resource (int max_number) throws BaseException, NotConnected {
      WfResource[] ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_sequence_resource(t, max_number);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public WfResource[] get_sequence_resource (SharkTransaction t,int max_number) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_executionadministration_get_sequence_resource(t,userId);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      try {
         return SharkEngineManager
            .getInstance()
            .getObjectFactory()
            .createResourceIteratorWrapper(t,userId)
            .get_next_n_sequence (max_number);
      } catch (Exception ex) {
         cus.info("ExecutionAdmin -> Unexpected error while user tries to get the list of resources");
         throw new BaseException(ex);
      }
   }

   public Map getLoggedUsers () throws BaseException, NotConnected {
      Map ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getLoggedUsers(t);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public Map getLoggedUsers(SharkTransaction t) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_executionadministration_getLoggedUsers(t,userId);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      try {
         Map ret=SharkUtilities.getLoggedUsers();
         return ret;
      } catch (Exception ex) {
         cus.info("ExecutionAdmin -> Unexpected error while user tries to get logged users");
         throw new BaseException(ex);
      }
   }

   public void startActivity (String procId,String blockActId,String actDefId) throws BaseException, NotConnected {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         startActivity(t, procId, blockActId, actDefId);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void startActivity (SharkTransaction t,String procId,String blockActId,String actDefId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         WfProcessInternal proc=SharkUtilities.getProcess(t, procId);
         SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
         if (sm!=null) {
            sm.check_executionadministration_startActivity(t,
                                                           procId,
                                                           userId,
                                                           proc.requester(t).getResourceRequesterUsername(t));
         }
         proc.start_activity(t, actDefId, blockActId);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public WfProcessMgr getProcessMgr (String name) throws BaseException, NotConnected {
      WfProcessMgr ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getProcessMgr(t, name);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public WfProcessMgr getProcessMgr (SharkTransaction t,String name) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_executionadministration_getProcessMgr(t,name,userId);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      try {
         return (null != SharkUtilities.getProcessMgr(t, name))
            ? SharkEngineManager.getInstance().getObjectFactory().createProcessMgrWrapper(userId,name)
            : null;
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public WfProcessMgr getProcessMgr (String pkgId,String pDefId) throws BaseException, NotConnected {
      WfProcessMgr ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getProcessMgr(t, pkgId, pDefId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public WfProcessMgr getProcessMgr (SharkTransaction t,String pkgId,String pDefId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         String curVer=SharkUtilities.getCurrentPkgVersion(pkgId,false);
         String mgrName=SharkUtilities.createProcessMgrKey(pkgId,curVer,pDefId);
         SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
         if (sm!=null) {
            sm.check_executionadministration_getProcessMgr(t,mgrName,userId);
         }
         return (null != SharkUtilities.getProcessMgr(t, mgrName))
            ? SharkEngineManager.getInstance().getObjectFactory().createProcessMgrWrapper(userId,mgrName)
            : null;
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public WfProcessMgr getProcessMgr (String pkgId,String pkgVer,String pDefId) throws BaseException, NotConnected {
      WfProcessMgr ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getProcessMgr(t, pkgId, pkgVer, pDefId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public WfProcessMgr getProcessMgr (SharkTransaction t,String pkgId,String pkgVer,String pDefId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         String mgrName=SharkUtilities.createProcessMgrKey(pkgId,pkgVer,pDefId);
         SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
         if (sm!=null) {
            sm.check_executionadministration_getProcessMgr(t,mgrName,userId);
         }
         return (null != SharkUtilities.getProcessMgr(t, mgrName))
            ? SharkEngineManager.getInstance().getObjectFactory().createProcessMgrWrapper(userId,mgrName)
            : null;
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public WfProcess getProcess (String procId) throws BaseException, NotConnected {
      WfProcess ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getProcess(t, procId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public WfProcess getProcess (SharkTransaction t,String procId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         WfProcessInternal proc=SharkUtilities.getProcess(t, procId);
         SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
         if (sm!=null && proc!=null) {
            sm.check_executionadministration_getProcess(t,
                                                        procId,
                                                        userId,
                                                        proc.requester(t).getResourceRequesterUsername(t));
         }
         return (null != proc)
            ? SharkEngineManager.getInstance().getObjectFactory().createProcessWrapper(userId,proc.manager_name(t),procId)
            : null;
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public WfActivity getActivity (String procId,String actId) throws BaseException, NotConnected {
      WfActivity ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getActivity(t, procId, actId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public WfActivity getActivity (SharkTransaction t,String procId,String actId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         WfActivityInternal act=SharkUtilities.getActivity(t, procId, actId);
         SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
         if (sm!=null && act!=null) {
            sm.check_executionadministration_getActivity(t,
                                                         procId,
                                                         actId,
                                                         userId,
                                                         act.container(t).requester(t).getResourceRequesterUsername(t),
                                                         act.getResourceUsername(t),
                                                         act.getAssignmentResourceIds(t));
         }
         return (null != act)
            ? SharkEngineManager.getInstance().getObjectFactory().createActivityWrapper(userId,act.manager_name(t),procId,actId)
            : null;
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public WfResource getResource (String username) throws BaseException, NotConnected {
      WfResource ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getResource(t, username);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public WfResource getResource (SharkTransaction t,String username) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_executionadministration_getResource(t,username,userId);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      try {
         return (null != SharkUtilities.getResource(t,username))
            ? SharkEngineManager.getInstance().getObjectFactory().createResourceWrapper(userId,username)
            : null;
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public WfAssignment getAssignment (String procId,String actId,String username) throws BaseException, NotConnected {
      WfAssignment ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getAssignment(t, procId, actId, username);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public WfAssignment getAssignment (SharkTransaction t,String procId,String actId,String username) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         WfAssignmentInternal ass=SharkUtilities.getAssignment(t, procId, actId, username);
         SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
         if (sm!=null && ass!=null) {
            sm.check_executionadministration_getAssignment(t,
                                                           procId,
                                                           actId,
                                                           username,
                                                           userId,
                                                           ass.activity(t).container(t).requester(t).getResourceRequesterUsername(t),
                                                           ass.activity(t).getResourceUsername(t),
                                                           ass.activity(t).getAssignmentResourceIds(t));
         }
         return (null != ass)
            ? SharkEngineManager.getInstance().getObjectFactory().createAssignmentWrapper(userId,ass.managerName(t),procId,actId,username)
            : null;
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public WfAssignment getAssignment (String procId,String assId) throws BaseException, NotConnected {
      WfAssignment ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getAssignment(t, procId, assId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public WfAssignment getAssignment (SharkTransaction t,String procId,String assId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         WfProcessInternal proc=SharkUtilities.getProcess(t,procId);
         SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
         if (sm!=null && proc!=null) {
            sm.check_executionadministration_getAssignment(t,
                                                           procId,
                                                           assId,
                                                           userId,
                                                           proc.requester(t).getResourceRequesterUsername(t));
         }

         return (null != proc)
            ? SharkUtilities.getAssignmentWrapper(t, userId, procId, assId)
            : null;
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public void reevaluateAssignments () throws BaseException, NotConnected {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         reevaluateAssignments(t);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void reevaluateAssignments (SharkTransaction t) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_executionadministration_reevaluateAssignments(t,userId);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      SharkUtilities.reevaluateAssignments(t);
   }

   public void reevaluateAssignments (String pkgId) throws BaseException, NotConnected {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         reevaluateAssignments(t,pkgId);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void reevaluateAssignments (SharkTransaction t,String pkgId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_executionadministration_reevaluateAssignments(t,userId);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      WfProcessMgrIterator iter=SharkEngineManager.getInstance().
         getObjectFactory().createProcessMgrIteratorWrapper(t,userId);
      String query="packageId.equals(\""+pkgId+"\")";
      try {
         iter.set_query_expression(t,query);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
      WfProcessMgr[] mgrs=iter.get_next_n_sequence(t,0);
      if (mgrs!=null) {
         for (int i=0; i<mgrs.length; i++) {
            String mgrName=mgrs[i].name(t);
            SharkUtilities.reevalAssignments(t,mgrName);
         }
      }
   }

   public void reevaluateAssignments (String pkgId,String pDefId) throws BaseException, NotConnected {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         reevaluateAssignments(t,pkgId,pDefId);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void reevaluateAssignments (SharkTransaction t,String pkgId,String pDefId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_executionadministration_reevaluateAssignments(t,userId);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      WfProcessMgrIterator iter=SharkEngineManager.getInstance().
         getObjectFactory().createProcessMgrIteratorWrapper(t,userId);
      String query="packageId.equals(\""+pkgId+"\") && processDefinitionId.equals(\""+pDefId+"\")";
      try {
         iter.set_query_expression(t,query);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
      WfProcessMgr[] mgrs=iter.get_next_n_sequence(t,0);
      if (mgrs!=null) {
         for (int i=0; i<mgrs.length; i++) {
            String mgrName=mgrs[i].name(t);
            SharkUtilities.reevalAssignments(t,mgrName);
         }
      }
   }

   public void reevaluateAssignments (String pkgId,String pDefId,String aDefId) throws BaseException, NotConnected {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         reevaluateAssignments(t,pkgId,pDefId,aDefId);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void reevaluateAssignments (SharkTransaction t,String pkgId,String pDefId,String aDefId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_executionadministration_reevaluateAssignments(t,userId);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      WfProcessMgrIterator iter=SharkEngineManager.getInstance().
         getObjectFactory().createProcessMgrIteratorWrapper(t,userId);
      String query="packageId.equals(\""+pkgId+"\") && processDefinitionId.equals(\""+pDefId+"\")";
      try {
         iter.set_query_expression(t,query);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
      WfProcessMgr[] mgrs=iter.get_next_n_sequence(t,0);
      if (mgrs!=null) {
         for (int i=0; i<mgrs.length; i++) {
            String mgrName=mgrs[i].name(t);

            List l=SharkUtilities.createProcessMgrsProcessWrappers(t,userId,mgrName);
            Iterator it=l.iterator();
            while (it.hasNext()) {
               WfProcess p=(WfProcess)it.next();
               String procId=p.key(t);
               WfActivityIterator actIter=SharkEngineManager
                  .getInstance()
                  .getObjectFactory()
                  .createActivityIteratorWrapper(t,userId,procId);
               String actQuery="definitionId.equals(\""+aDefId+"\")";
               try {
                  actIter.set_query_expression(t,actQuery);
               } catch (Exception ex) {
                  throw new BaseException(ex);
               }
               WfActivity[] acts=actIter.get_next_n_sequence(t,0);
               for (int j=0; j<acts.length; j++) {
                  WfActivity act=acts[j];
                  WfActivityInternal aint=SharkUtilities.getActivity(t,procId,act.key(t));
                  aint.reevaluateAssignments(t);
               }
            }
         }
      }
   }

   public void deleteClosedProcesses () throws BaseException, NotConnected {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         deleteClosedProcesses(t);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void deleteClosedProcesses (SharkTransaction t) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_executionadministration_reevaluateAssignments(t,userId);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      LockMaster lm=SharkEngineManager.getInstance().getLockMaster();
      try {
         PersistentManagerInterface ipm=SharkEngineManager.getInstance().getInstancePersistenceManager();
         List procs=ipm.getAllFinishedProcesses(t);
         cus.info("ExecutionAdmin -> Deleting "+procs.size()+" closed processes:");
         for (Iterator i=procs.iterator(); i.hasNext();) {
            ProcessPersistenceInterface po=(ProcessPersistenceInterface)i.next();
            if (po.getActivityRequesterId()!=null) {
               ActivityPersistenceInterface apo=ipm.restoreActivity(po.getActivityRequesterId(),t);
               if (apo!=null && apo.getState().startsWith(SharkConstants.STATEPREFIX_OPEN)) {
                  cus.info("... Process " + po.getId()+" can't be deleted yet because it has active activity requester!");
                  continue;
               }
            }
            cus.info("... Deleting process "+po.getId());
            if (lm!=null) {
               lm.lock(t,po.getId());
            }
            ipm.deleteProcess(po.getId(),true,t);
            CacheMgr cm=SharkEngineManager.getInstance().getCacheManager();
            if (cm!=null) {
               cm.getProcessCache().remove(po.getId());
            }
         }
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public void deleteClosedProcesses (java.util.Date closedBefore) throws BaseException, NotConnected {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         deleteClosedProcesses(t,closedBefore);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void deleteClosedProcesses (SharkTransaction t,java.util.Date closedBefore) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_executionadministration_deleteClosedProcesses(t,userId);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      LockMaster lm=SharkEngineManager.getInstance().getLockMaster();
      try {
         PersistentManagerInterface ipm=SharkEngineManager.getInstance().getInstancePersistenceManager();
         List procs=ipm.getAllFinishedProcesses(t,closedBefore);
         cus.info("ExecutionAdmin -> Deleting "+procs.size()+" processes closed before "+closedBefore+":");
         for (Iterator i=procs.iterator(); i.hasNext();) {
            ProcessPersistenceInterface po=(ProcessPersistenceInterface)i.next();
            if (po.getActivityRequesterId()!=null) {
               ActivityPersistenceInterface apo=ipm.restoreActivity(po.getActivityRequesterId(),t);
               if (apo!=null && apo.getState().startsWith(SharkConstants.STATEPREFIX_OPEN)) {
                  cus.info("... Process " + po.getId()+" can't be deleted yet because it has active activity requester!");
                  continue;
               }
            }
            cus.info("... Deleting process "+po.getId());
            if (lm!=null) {
               lm.lock(t,po.getId());
            }
            ipm.deleteProcess(po.getId(),true,t);
            CacheMgr cm=SharkEngineManager.getInstance().getCacheManager();
            if (cm!=null) {
               cm.getProcessCache().remove(po.getId());
            }
         }
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public void deleteClosedProcesses (String pkgId) throws BaseException, NotConnected {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         deleteClosedProcesses(t,pkgId);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void deleteClosedProcesses (SharkTransaction t,String pkgId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_executionadministration_deleteClosedProcesses(t,userId);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      LockMaster lm=SharkEngineManager.getInstance().getLockMaster();
      try {
         PersistentManagerInterface ipm=SharkEngineManager.getInstance().getInstancePersistenceManager();
         List procs=ipm.getAllFinishedProcesses(t,pkgId);
         cus.info("ExecutionAdmin -> Deleting "+procs.size()+" closed processes for package Id="+pkgId+":");
         for (Iterator i=procs.iterator(); i.hasNext();) {
            ProcessPersistenceInterface po=(ProcessPersistenceInterface)i.next();
            if (po.getActivityRequesterId()!=null) {
               ActivityPersistenceInterface apo=ipm.restoreActivity(po.getActivityRequesterId(),t);
               if (apo!=null && apo.getState().startsWith(SharkConstants.STATEPREFIX_OPEN)) {
                  cus.info("... Process " + po.getId()+" can't be deleted yet because it has active activity requester!");
                  continue;
               }
            }
            cus.info("... Deleting process "+po.getId());
            if (lm!=null) {
               lm.lock(t,po.getId());
            }
            ipm.deleteProcess(po.getId(),true,t);
            CacheMgr cm=SharkEngineManager.getInstance().getCacheManager();
            if (cm!=null) {
               cm.getProcessCache().remove(po.getId());
            }
         }
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public void deleteClosedProcessesForMgr (String mgrName) throws BaseException, NotConnected {
      SharkTransaction t=null;
      try {
         t = SharkUtilities.createTransaction();
         deleteClosedProcessesForMgr(t,mgrName);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void deleteClosedProcessesForMgr (SharkTransaction t,String mgrName) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_executionadministration_deleteClosedProcesses(t,userId);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      LockMaster lm=SharkEngineManager.getInstance().getLockMaster();
      try {
         String pkgId=SharkUtilities.getProcessMgrPkgId(mgrName);
         String pDefId=SharkUtilities.getProcessMgrProcDefId(mgrName);
         String pkgVer=SharkUtilities.getProcessMgrVersion(mgrName);


         PersistentManagerInterface ipm=SharkEngineManager.getInstance().getInstancePersistenceManager();
         List procs=ipm.getAllFinishedProcesses(t,pkgId,pDefId,pkgVer);
         cus.info("ExecutionAdmin -> Deleting "+procs.size()+" closed processes for package Id="+pkgId+", version="+pkgVer+", pDefId= :"+pDefId);
         for (Iterator i=procs.iterator(); i.hasNext();) {
            ProcessPersistenceInterface po=(ProcessPersistenceInterface)i.next();
            if (po.getActivityRequesterId()!=null) {
               ActivityPersistenceInterface apo=ipm.restoreActivity(po.getActivityRequesterId(),t);
               if (apo!=null && apo.getState().startsWith(SharkConstants.STATEPREFIX_OPEN)) {
                  cus.info("... Process " + po.getId()+" can't be deleted yet because it has active activity requester!");
                  continue;
               }
            }
            cus.info("... Deleting process "+po.getId());
            if (lm!=null) {
               lm.lock(t,po.getId());
            }
            ipm.deleteProcess(po.getId(),true,t);
            CacheMgr cm=SharkEngineManager.getInstance().getCacheManager();
            if (cm!=null) {
               cm.getProcessCache().remove(po.getId());
            }
         }
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public void deleteClosedProcessesWithVersion (String pkgId,String pkgVer) throws BaseException, NotConnected {
      SharkTransaction t=null;
      try {
         t = SharkUtilities.createTransaction();
         deleteClosedProcessesWithVersion(t,pkgId,pkgVer);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void deleteClosedProcessesWithVersion (SharkTransaction t,String pkgId,String pkgVer) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_executionadministration_deleteClosedProcesses(t,userId);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      LockMaster lm=SharkEngineManager.getInstance().getLockMaster();
      try {
         PersistentManagerInterface ipm=SharkEngineManager.getInstance().getInstancePersistenceManager();
         List procs=ipm.getAllFinishedProcesses(t,pkgId,null,pkgVer);
         cus.info("ExecutionAdmin -> Deleting "+procs.size()+" closed processes for package Id="+pkgId+", version="+pkgVer+" :");
         for (Iterator i=procs.iterator(); i.hasNext();) {
            ProcessPersistenceInterface po=(ProcessPersistenceInterface)i.next();
            if (po.getActivityRequesterId()!=null) {
               ActivityPersistenceInterface apo=ipm.restoreActivity(po.getActivityRequesterId(),t);
               if (apo!=null && apo.getState().startsWith(SharkConstants.STATEPREFIX_OPEN)) {
                  cus.info("... Process " + po.getId()+" can't be deleted yet because it has active activity requester!");
                  continue;
               }
            }
            cus.info("... Deleting process "+po.getId());
            if (lm!=null) {
               lm.lock(t,po.getId());
            }
            ipm.deleteProcess(po.getId(),true,t);
            CacheMgr cm=SharkEngineManager.getInstance().getCacheManager();
            if (cm!=null) {
               cm.getProcessCache().remove(po.getId());
            }
         }
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public void deleteClosedProcesses (String pkgId,String pDefId) throws BaseException, NotConnected {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         deleteClosedProcesses(t,pkgId,pDefId);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void deleteClosedProcesses (SharkTransaction t,String pkgId,String pDefId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_executionadministration_deleteClosedProcesses(t,userId);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      LockMaster lm=SharkEngineManager.getInstance().getLockMaster();
      try {
         PersistentManagerInterface ipm=SharkEngineManager.getInstance().getInstancePersistenceManager();
         List procs=ipm.getAllFinishedProcesses(t,pkgId,pDefId);
         cus.info("ExecutionAdmin -> Deleting "+procs.size()+" closed processes for packageId="+pkgId+"and processDefinitionId="+pDefId+":");
         for (Iterator i=procs.iterator(); i.hasNext();) {
            ProcessPersistenceInterface po=(ProcessPersistenceInterface)i.next();
            if (po.getActivityRequesterId()!=null) {
               ActivityPersistenceInterface apo=ipm.restoreActivity(po.getActivityRequesterId(),t);
               if (apo!=null && apo.getState().startsWith(SharkConstants.STATEPREFIX_OPEN)) {
                  cus.info("... Process " + po.getId()+" can't be deleted yet because it has active activity requester!");
                  continue;
               }
            }
            cus.info("... Deleting process "+po.getId());
            if (lm!=null) {
               lm.lock(t,po.getId());
            }
            ipm.deleteProcess(po.getId(),true,t);
            CacheMgr cm=SharkEngineManager.getInstance().getCacheManager();
            if (cm!=null) {
               cm.getProcessCache().remove(po.getId());
            }
         }
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public void deleteClosedProcess (String procId) throws BaseException, NotConnected {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         deleteClosedProcess(t, procId);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void deleteClosedProcess (SharkTransaction t,String procId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_executionadministration_deleteClosedProcesses(t,userId);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      LockMaster lm=SharkEngineManager.getInstance().getLockMaster();
      try {

         PersistentManagerInterface ipm=SharkEngineManager.getInstance().getInstancePersistenceManager();
         ProcessPersistenceInterface po=ipm.restoreProcess(procId,t);
         if (po.getState().startsWith(SharkConstants.STATEPREFIX_OPEN)) {
            throw new BaseException("Can't delete processes which are not closed");
         }
         if (po.getActivityRequesterId()!=null) {
            ActivityPersistenceInterface apo=ipm.restoreActivity(po.getActivityRequesterId(),t);
            if (apo!=null && apo.getState().startsWith(SharkConstants.STATEPREFIX_OPEN)) {
               cus.info("... Process " + po.getId()+" can't be deleted yet because it has active activity requester!");
               return;
            }
         }
         cus.info("... Deleting process "+po.getId());
         if (lm!=null) {
            lm.lock(t,po.getId());
         }
         ipm.deleteProcess(po.getId(),true,t);
         CacheMgr cm=SharkEngineManager.getInstance().getCacheManager();
         if (cm!=null) {
            cm.getProcessCache().remove(po.getId());
         }
      } catch (Exception ex) {
         throw new BaseException("Problems with deleting process for Id "+procId,ex);
      }
   }

   public String[] deleteClosedProcessesForMgr(String mgrName,
                                               int procPerTrans,
                                               int failures2ignore) throws BaseException,
      NotConnected {
      if (!connected) { throw new NotConnected("The connection is not established..."); }
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         SharkTransaction t=null;
         try {
            t = SharkUtilities.createTransaction();
            sm.check_executionadministration_deleteClosedProcesses(t,userId);
            SharkUtilities.commitTransaction(t);
         } catch (RootException e) {
            SharkUtilities.rollbackTransaction(t,e);
            if (e instanceof NotConnected)
               throw (NotConnected)e;
            else if (e instanceof BaseException)
               throw (BaseException)e;
            else
               throw new BaseException(e);
         } finally {
            SharkUtilities.releaseTransaction(t);
         }
      }
      List instancesFailed2check = new ArrayList();
      Iterator iterProcesses = null;
      List currentBatch = null;

      List procs=null;
      SharkTransaction t = null;
      PersistentManagerInterface ipm=SharkEngineManager.getInstance().getInstancePersistenceManager();
      try {
         t = SharkUtilities.createTransaction();
         procs = ipm.getAllFinishedProcesses(t);
         cus.info("ExecutionAdmin -> Deleting "
                     + procs.size() + " closed processes:");
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      LockMaster lm=SharkEngineManager.getInstance().getLockMaster();
      do {
         t = null;
         currentBatch = new ArrayList();
         try {
            t = SharkUtilities.createTransaction();
            String pkgId = SharkUtilities.getProcessMgrPkgId(mgrName);
            String pDefId = SharkUtilities.getProcessMgrProcDefId(mgrName);
            String pkgVer = SharkUtilities.getProcessMgrVersion(mgrName);

            iterProcesses = procs.iterator();
            for (int n = 0; n < procPerTrans; ++n) {
               if (!iterProcesses.hasNext()) {
                  break;
               }
               ProcessPersistenceInterface po = (ProcessPersistenceInterface) iterProcesses.next();
               iterProcesses.remove();
               if (po.getActivityRequesterId()!=null) {
                  ActivityPersistenceInterface apo=ipm.restoreActivity(po.getActivityRequesterId(),t);
                  if (apo!=null && apo.getState().startsWith(SharkConstants.STATEPREFIX_OPEN)) {
                     cus.info("... Process " + po.getId()+" can't be deleted yet because it has active activity requester!");
                     continue;
                  }
               }
               cus.info("... Deleting process "+po.getId());
               if (lm!=null) {
                  lm.lock(t,po.getId());
               }
               ipm.deleteProcess(po.getId(),true,t);
               CacheMgr cm=SharkEngineManager.getInstance().getCacheManager();
               if (cm!=null) {
                  cm.getProcessCache().remove(po.getId());
               }
               currentBatch.add(po.getId());
            }
            SharkUtilities.commitTransaction(t);
         } catch (RootException _) {
            SharkUtilities.rollbackTransaction(t,_);
            instancesFailed2check.addAll(currentBatch);
            // may log something
         } finally {
            SharkUtilities.releaseTransaction(t);
         }
         System.err.println("\ttransaction finished: batch size:"+currentBatch.size());
      } while (instancesFailed2check.size() <= failures2ignore
               && iterProcesses.hasNext());
      String[] ret = new String[instancesFailed2check.size()];
      instancesFailed2check.toArray(ret);
      System.err.println("  deleting finished: failed:"+ret.length);
      return ret;
   }

   public String[] deleteClosedProcesses (
      int procPerTrans,
      int failures2ignore) throws BaseException, NotConnected {
      if (!connected) { throw new NotConnected("The connection is not established..."); }
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         SharkTransaction t=null;
         try {
            t = SharkUtilities.createTransaction();
            sm.check_executionadministration_deleteClosedProcesses(t,userId);
            SharkUtilities.commitTransaction(t);
         } catch (RootException e) {
            SharkUtilities.rollbackTransaction(t,e);
            if (e instanceof NotConnected)
               throw (NotConnected)e;
            else if (e instanceof BaseException)
               throw (BaseException)e;
            else
               throw new BaseException(e);
         } finally {
            SharkUtilities.releaseTransaction(t);
         }
      }
      List instancesFailed2check = new ArrayList();
      Iterator iterProcesses = null;
      List currentBatch = null;

      List procs=null;
      SharkTransaction t = null;
      PersistentManagerInterface ipm=SharkEngineManager.getInstance().getInstancePersistenceManager();
//long tg0=System.currentTimeMillis();
      try {
         t = SharkUtilities.createTransaction();
         procs = ipm.getAllFinishedProcesses(t);
         cus.info("ExecutionAdmin -> Deleting "
                     + procs.size() + " closed processes:");
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
//System.out.println("GAFPST="+(System.currentTimeMillis()-tg0));
      LockMaster lm=SharkEngineManager.getInstance().getLockMaster();
//      long t0, t1, t2, t3, t4, t5, t6, tc=0;
//      long td1=0, td2=0, td3=0;
      
      do {
//         t0=System.currentTimeMillis();
         t=null;
         currentBatch = new ArrayList();
         try {
            t = SharkUtilities.createTransaction();

            iterProcesses = procs.iterator();
            for (int n = 0; n < procPerTrans; ++n) {
//               t1=System.currentTimeMillis();
               if (!iterProcesses.hasNext()) {
                  break;
               }
               ProcessPersistenceInterface po = (ProcessPersistenceInterface) iterProcesses.next();
               iterProcesses.remove();

               /*if (po.getActivityRequesterId()!=null) {
                  ActivityPersistenceInterface apo=ipm.restoreActivity(po.getActivityRequesterId(),t);
                  if (apo!=null && apo.getState().startsWith(SharkConstants.STATEPREFIX_OPEN)) {
                     cus.info("... Process " + po.getId()+" can't be deleted yet because it has active activity requester!");
                     continue;
                  }
               }*/
               cus.info("... Deleting process "+po.getId());
               if (lm!=null) {
                  lm.lock(t,po.getId());
               }
//               t2=System.currentTimeMillis();
//               td1+=(t2-t1);
               ipm.deleteProcess(po.getId(),true,t);
//               t3=System.currentTimeMillis();
//               td2+=(t3-t2);
               CacheMgr cm=SharkEngineManager.getInstance().getCacheManager();
               if (cm!=null) {
                  cm.getProcessCache().remove(po.getId());
               }
               currentBatch.add(po.getId());
//               t4=System.currentTimeMillis();
//               td3+=(t4-t3);
               
            }
//            t5=System.currentTimeMillis();
            SharkUtilities.commitTransaction(t);
//            t6=System.currentTimeMillis();
//            tc+=(t6-t5);
         } catch (RootException _) {
            SharkUtilities.rollbackTransaction(t,_);
            instancesFailed2check.addAll(currentBatch);
//            _.printStackTrace();
            // may log something
         } finally {
            SharkUtilities.releaseTransaction(t);
         }
//         System.out.println("CBT="+(System.currentTimeMillis()-t0)+", TD1="+td1+", TD2="+td2+", TD3="+td3+", TC="+tc);
         System.err.println("\ttransaction finished: batch size:"+currentBatch.size());
      } while (instancesFailed2check.size() <= failures2ignore
               && iterProcesses.hasNext());
      String[] ret = new String[instancesFailed2check.size()];
      instancesFailed2check.toArray(ret);
      System.err.println("  deleting finished: failed:"+ret.length);
//System.out.println("TD1="+td1+", TD2="+td2+", TD3="+td3);
      return ret;
   }

   public Map getProcessMgrInputSignature(String name) throws BaseException, NotConnected {
      Map ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getProcessMgrInputSignature(t, name);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public Map getProcessMgrInputSignature(SharkTransaction t, String name) throws BaseException, NotConnected {
      return getProcessMgrInputSignature(t,
                                         SharkUtilities.getProcessMgrPkgId(name),
                                         SharkUtilities.getProcessMgrVersion(name),
                                         SharkUtilities.getProcessMgrProcDefId(name));
   }

   public Map getProcessMgrInputSignature(String pkgId, String pDefId) throws BaseException, NotConnected {
      Map ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getProcessMgrInputSignature(t, pkgId, pDefId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public Map getProcessMgrInputSignature(SharkTransaction t, String pkgId, String pDefId) throws BaseException, NotConnected {
      return getProcessMgrInputSignature(t,
                                         pkgId,
                                         SharkUtilities.getCurrentPkgVersion(pkgId,
                                                                             false),
                                         pDefId);

   }

   public Map getProcessMgrInputSignature(String pkgId, String pkgVer, String pDefId) throws BaseException, NotConnected {
      Map ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getProcessMgrInputSignature(t, pkgId, pkgVer, pDefId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public Map getProcessMgrInputSignature(SharkTransaction t,
                                          String pkgId,
                                          String pkgVer,
                                          String pDefId) throws BaseException,
      NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         String mgrName=SharkUtilities.createProcessMgrKey(pkgId,pkgVer,pDefId);
         SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
         if (sm!=null) {
            sm.check_executionadministration_getProcessMgr(t,mgrName,userId);
         }
         WfProcessMgrInternal a = SharkUtilities.getProcessMgr(t, mgrName);
         if (null != a) {
            return a.input_signature(t);
         }
         return null;
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public Object getProcessContext(String procId, String variableName) throws BaseException, NotConnected {
      Object ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getProcessContext(t, procId, variableName);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public Object getProcessContext(SharkTransaction t,
                                   String procId,
                                   String variableName) throws BaseException,
      NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         //         SecurityManager
         // sm=SharkEngineManager.getInstance().getSecurityManager();
         //         if (sm!=null) {
         //            sm.check_executionadministration_getProcessMgr(t,mgrName,userId);
         //         }

         PersistentManagerInterface ipm = SharkEngineManager.getInstance()
            .getInstancePersistenceManager();
         ProcessVariablePersistenceInterface a = ipm.createProcessVariable();
         a.setDefinitionId(variableName);
         a.setProcessId(procId);
         if (ipm.restore(a, t)) {
            return a.getValue();
         }
      } catch (PersistenceException ex) {
         throw new BaseException(ex);
      }
      throw new BaseException("variable not found");
   }

   public Map getProcessContext(String procId, String[] variableNames) throws BaseException, NotConnected {
      Map ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getProcessContext(t, procId, variableNames);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public Map getProcessContext(SharkTransaction t, String procId, String[] variableNames) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      Map ret = new HashMap();
      //         SecurityManager
      // sm=SharkEngineManager.getInstance().getSecurityManager();
      //         if (sm!=null) {
      //            sm.check_executionadministration_getProcessMgr(t,mgrName,userId);
      //         }

      for (int i = 0; i < variableNames.length; i++) {
         ret.put(variableNames[i],getProcessContext(t,procId,variableNames[i]));
      }
      return ret;
   }

   public Object getActivityContext(String procId, String actId, String variableName) throws BaseException, NotConnected {
      Object ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getActivityContext(t, procId, actId, variableName);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public Object getActivityContext(SharkTransaction t, String procId, String actId, String variableName) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         //         SecurityManager
         // sm=SharkEngineManager.getInstance().getSecurityManager();
         //         if (sm!=null) {
         //            sm.check_executionadministration_getProcessMgr(t,mgrName,userId);
         //         }

         PersistentManagerInterface ipm = SharkEngineManager.getInstance()
            .getInstancePersistenceManager();
         ActivityVariablePersistenceInterface a = ipm.createActivityVariable();
         a.setDefinitionId(variableName);
         a.setActivityId(actId);
         if (ipm.restore(a, t)) {
            return a.getValue();
         }
      } catch (PersistenceException ex) {
         throw new BaseException(ex);
      }
      throw new BaseException("variable not found");
   }

   public Map getActivityContext(String procId,
                                 String actId,
                                 String[] variableNames) throws BaseException,
      NotConnected {
      Map ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getActivityContext(t, procId, actId, variableNames);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof NotConnected)
            throw (NotConnected) e;
         else if (e instanceof BaseException)
            throw (BaseException) e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public Map getActivityContext(SharkTransaction t, String procId, String actId, String[] variableNames) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      Map ret = new HashMap();
      //         SecurityManager
      // sm=SharkEngineManager.getInstance().getSecurityManager();
      //         if (sm!=null) {
      //            sm.check_executionadministration_getProcessMgr(t,mgrName,userId);
      //         }

      for (int i = 0; i < variableNames.length; i++) {
         ret.put(variableNames[i],getActivityContext(t,procId, actId,variableNames[i]));
      }
      return ret;
   }

   public WfAssignmentIterator get_iterator_assignment() throws NotConnected, BaseException {
      WfAssignmentIterator ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_iterator_assignment(t);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof NotConnected)
            throw (NotConnected) e;
         else if (e instanceof BaseException)
            throw (BaseException) e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public WfAssignmentIterator get_iterator_assignment (SharkTransaction t) throws NotConnected, BaseException {
      return SharkEngineManager.getInstance()
         .getObjectFactory()
         .createAssignmentIteratorWrapper(t, userId, null);
   }

   public WfProcessIterator get_iterator_process() throws NotConnected, BaseException {
      WfProcessIterator ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_iterator_process(t);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof NotConnected)
            throw (NotConnected) e;
         else if (e instanceof BaseException)
            throw (BaseException) e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public WfProcessIterator get_iterator_process (SharkTransaction t) throws NotConnected, BaseException {
      return SharkEngineManager.getInstance()
         .getObjectFactory()
         .createProcessIteratorWrapper(t, userId, null, false);
   }

   public WfActivityIterator get_iterator_activity() throws NotConnected, BaseException {
      WfActivityIterator ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_iterator_activity(t);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof NotConnected)
            throw (NotConnected) e;
         else if (e instanceof BaseException)
            throw (BaseException) e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public WfActivityIterator get_iterator_activity (SharkTransaction t) throws NotConnected, BaseException {
      return SharkEngineManager.getInstance()
         .getObjectFactory()
         .createActivityIteratorWrapper(t, userId, null);
   }

   // Prevents memory leak if client forgets to disconnect
   public void finalize() throws Throwable {
      if (connected) {
         connected=false;
         SharkUtilities.disconnect(connectionKey);
         cus.info("ExecutionAdmin -> User "+userId+" with connection key "+connectionKey+" is automatically disconnected");
      }
   }
}

