package org.enhydra.shark;

import org.enhydra.shark.api.client.wfmodel.*;

import java.util.List;
import java.util.Map;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.internal.security.SecurityManager;
import org.enhydra.shark.api.internal.working.WfProcessInternal;
import org.enhydra.shark.api.internal.working.WfProcessMgrInternal;
import org.enhydra.shark.api.internal.working.WfRequesterInternal;
import org.enhydra.shark.xpdl.XPDLConstants;
import org.enhydra.shark.xpdl.elements.WorkflowProcess;

/**
 * WfProcessMgrImplWrapper - Workflow Process Manager implementation
 * @author Sasa Bojanic
 * @author Vladimir Puskas
 */
public class WfProcessMgrWrapper implements WfProcessMgr {

   private String userAuth;
   private String name;
   /**
    * Creates new WfProcessMgrImplWrapper
    * @param name Name of the package where process definition exists.
    */
   protected WfProcessMgrWrapper(String userAuth,String name) throws BaseException {
      this.userAuth=userAuth;
      this.name=name;
   }

   public int how_many_process () throws BaseException {
      int ret=-1;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = how_many_process(t);
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

   public int how_many_process (SharkTransaction t) throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_processmgr_how_many_process(t,
                                                 name,
                                                 userAuth,
                                                 SharkUtilities.getProcessMgrPkgId(name),
                                                 SharkUtilities.getProcessMgrVersion(name),
                                                 SharkUtilities.getProcessMgrProcDefId(name));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      try {
         return SharkEngineManager.getInstance().getInstancePersistenceManager().getAllProcessesForMgr(name,t).size();
      } catch (Exception e) {
         throw new BaseException(e);
      }
   }

   public WfProcessIterator get_iterator_process () throws BaseException {
      WfProcessIterator ret=null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_iterator_process(t);
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

   public WfProcessIterator get_iterator_process (SharkTransaction t) throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_processmgr_get_iterator_process(t,
                                                     name,
                                                     userAuth,
                                                     SharkUtilities.getProcessMgrPkgId(name),
                                                     SharkUtilities.getProcessMgrVersion(name),
                                                     SharkUtilities.getProcessMgrProcDefId(name));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      return SharkEngineManager.getInstance().getObjectFactory().createProcessIteratorWrapper(t,userAuth,name);
   }

   public WfProcess[] get_sequence_process (int max_number) throws BaseException {
      WfProcess[] ret=null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_sequence_process(t,max_number);
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

   public WfProcess[] get_sequence_process (SharkTransaction t,int max_number) throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_processmgr_get_sequence_process(t,
                                                     name,
                                                     userAuth,
                                                     SharkUtilities.getProcessMgrPkgId(name),
                                                     SharkUtilities.getProcessMgrVersion(name),
                                                     SharkUtilities.getProcessMgrProcDefId(name));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      List processes=SharkUtilities.createProcessMgrsProcessWrappers(t,userAuth,name);
      if (max_number>processes.size() || max_number<=0) {
         max_number=processes.size();
      }
      WfProcessWrapper[] procs=new WfProcessWrapper[max_number];
      processes.subList(0,max_number).toArray(procs);
      return procs;
   }

   public boolean is_member_of_process (WfProcess member) throws BaseException {
      boolean ret=false;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = is_member_of_process(t,member);
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

   public boolean is_member_of_process (SharkTransaction t,WfProcess member) throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_processmgr_is_member_of_process(t,
                                                     name,
                                                     userAuth,
                                                     SharkUtilities.getProcessMgrPkgId(name),
                                                     SharkUtilities.getProcessMgrVersion(name),
                                                     SharkUtilities.getProcessMgrProcDefId(name));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      String mgrName=member.manager(t).name(t);
      return mgrName.equals(this.name);
   }

   public process_mgr_stateType process_mgr_state () throws BaseException {
      process_mgr_stateType ret=null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = process_mgr_state(t);
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

   public process_mgr_stateType process_mgr_state (SharkTransaction t) throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_processmgr_process_mgr_state(t,
                                                  name,
                                                  userAuth,
                                                  SharkUtilities.getProcessMgrPkgId(name),
                                                  SharkUtilities.getProcessMgrVersion(name),
                                                  SharkUtilities.getProcessMgrProcDefId(name));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      return WfProcessMgrWrapper.getProcessMgrImpl(t,name).process_mgr_state(t);
   }

   public void set_process_mgr_state (process_mgr_stateType new_state) throws BaseException, TransitionNotAllowed {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         set_process_mgr_state(t,new_state);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof TransitionNotAllowed)
            throw (TransitionNotAllowed)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void set_process_mgr_state (SharkTransaction t,process_mgr_stateType new_state) throws BaseException, TransitionNotAllowed {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_processmgr_set_process_mgr_state(t,
                                                      name,
                                                      userAuth,
                                                      SharkUtilities.getProcessMgrPkgId(name),
                                                      SharkUtilities.getProcessMgrVersion(name),
                                                      SharkUtilities.getProcessMgrProcDefId(name));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      WfProcessMgrInternal mgrInternal=WfProcessMgrWrapper.getProcessMgrImpl(t,name);
      mgrInternal.set_process_mgr_state(t,new_state);
   }

   public String name () throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm==null) {
         return name;
      }
      String ret=null;
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
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_processmgr_name(t,
                                     name,
                                     userAuth,
                                     SharkUtilities.getProcessMgrPkgId(name),
                                     SharkUtilities.getProcessMgrVersion(name),
                                     SharkUtilities.getProcessMgrProcDefId(name));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      return name;
   }

   public String description () throws BaseException {
      String ret=null;
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
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_processmgr_description(t,
                                            name,
                                            userAuth,
                                            SharkUtilities.getProcessMgrPkgId(name),
                                            SharkUtilities.getProcessMgrVersion(name),
                                            SharkUtilities.getProcessMgrProcDefId(name));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      return WfProcessMgrWrapper.getProcessMgrImpl(t,name).description(t);
   }

   public String category () throws BaseException {
      String ret=null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = category(t);
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

   public String category (SharkTransaction t) throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_processmgr_category(t,
                                         name,
                                         userAuth,
                                         SharkUtilities.getProcessMgrPkgId(name),
                                         SharkUtilities.getProcessMgrVersion(name),
                                         SharkUtilities.getProcessMgrProcDefId(name));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      return WfProcessMgrWrapper.getProcessMgrImpl(t,name).category(t);
   }

   public String version () throws BaseException {
      String ret=null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = version(t);
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

   public String version (SharkTransaction t) throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_processmgr_version(t,
                                        name,
                                        userAuth,
                                        SharkUtilities.getProcessMgrPkgId(name),
                                        SharkUtilities.getProcessMgrVersion(name),
                                        SharkUtilities.getProcessMgrProcDefId(name));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      return WfProcessMgrWrapper.getProcessMgrImpl(t,name).version(t);
   }

   public Map context_signature () throws BaseException {
      Map ret=null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = context_signature(t);
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

   public Map context_signature (SharkTransaction t) throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_processmgr_context_signature(t,
                                                  name,
                                                  userAuth,
                                                  SharkUtilities.getProcessMgrPkgId(name),
                                                  SharkUtilities.getProcessMgrVersion(name),
                                                  SharkUtilities.getProcessMgrProcDefId(name));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      return WfProcessMgrWrapper.getProcessMgrImpl(t,name).context_signature(t);
   }

   public Map result_signature () throws BaseException {
      Map ret=null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = result_signature(t);
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

   public Map result_signature (SharkTransaction t) throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_processmgr_result_signature(t,
                                                 name,
                                                 userAuth,
                                                 SharkUtilities.getProcessMgrPkgId(name),
                                                 SharkUtilities.getProcessMgrVersion(name),
                                                 SharkUtilities.getProcessMgrProcDefId(name));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      return WfProcessMgrWrapper.getProcessMgrImpl(t,name).result_signature(t);
   }

   /**
    * Create a WfProcess object
    */
   public WfProcess create_process (WfRequester requester) throws BaseException,
      NotEnabled, InvalidRequester, RequesterRequired {
      WfProcess ret=null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = create_process(t,requester);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof NotEnabled)
            throw (NotEnabled)e;
         else if (e instanceof InvalidRequester)
            throw (InvalidRequester)e;
         else if (e instanceof RequesterRequired)
            throw (RequesterRequired)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public WfProcess create_process (SharkTransaction t,WfRequester requester) throws BaseException,
      NotEnabled, InvalidRequester, RequesterRequired {
      WfProcessMgrInternal mgr=WfProcessMgrWrapper.getProcessMgrImpl(t,name);

      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_processmgr_create_process(t,
                                               name,
                                               userAuth,
                                               SharkUtilities.getProcessMgrPkgId(name),
                                               SharkUtilities.getProcessMgrVersion(name),
                                               SharkUtilities.getProcessMgrProcDefId(name));
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }


      // check if this process is allowed to be created only internally
      if (mgr.category(t).equalsIgnoreCase(XPDLConstants.ACCESS_LEVEL_PRIVATE)) {
         throw new NotEnabled("The process definition defines only PRIVATE access!");
      }

      WfRequesterInternal req=SharkEngineManager
         .getInstance()
         .getObjectFactory()
         .createDefaultRequester(userAuth,requester);


      WfProcessInternal procInternal=mgr.create_process(t,req);
      WfProcess proc=SharkEngineManager
         .getInstance()
         .getObjectFactory()
         .createProcessWrapper(userAuth,procInternal.manager_name(t),procInternal.key(t));
      return proc;
   }

   public String toString () {
      return "[name="+name+"]";
   }

   /**
    * It is assumed that there can't be two or more
    * processes mgrs having the same name.
    */
   public boolean equals (Object obj) {
      if (!(obj instanceof WfProcessMgr)) return false;
      WfProcessMgr mgr=(WfProcessMgr)obj;
      try {
         if (obj instanceof WfProcessMgrWrapper) {
            return ((WfProcessMgrWrapper)obj).name.equals(name);
         } else {
            return mgr.name().equals(name);
         }
      } catch (Exception ex) {
         return false;
      }
   }

   private static WfProcessMgrInternal getProcessMgrImpl (
      SharkTransaction t,
      String name) throws BaseException {
      WfProcessMgrInternal mgr=SharkUtilities.getProcessMgr(t,name);
      if (mgr==null) throw new BaseException("ProcessMgr does not exist");
      return mgr;
   }
}

