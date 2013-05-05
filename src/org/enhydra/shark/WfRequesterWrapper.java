package org.enhydra.shark;

import java.util.ArrayList;
import java.util.List;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfmodel.InvalidPerformer;
import org.enhydra.shark.api.client.wfmodel.WfEventAudit;
import org.enhydra.shark.api.client.wfmodel.WfProcess;
import org.enhydra.shark.api.client.wfmodel.WfProcessIterator;
import org.enhydra.shark.api.client.wfmodel.WfRequester;
import org.enhydra.shark.api.internal.instancepersistence.ProcessPersistenceInterface;
import org.enhydra.shark.api.internal.security.SecurityManager;


/**
 * WfRequesterWrapper - Workflow Requester implementation.
 * @author Sasa Bojanic
 */
public class WfRequesterWrapper implements  WfRequester {

   private String userAuth;
   private String resourceUsername;

   protected WfRequesterWrapper (String userAuth,String resUsername) {
      this.userAuth=userAuth;
      this.resourceUsername=resUsername;
   }

   /**
    * Gets the number of processes.
    */
   public int how_many_performer () throws BaseException {
      int ret=-1;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = how_many_performer(t);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         throw (BaseException)e;
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public int how_many_performer (SharkTransaction t) throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_requester_how_many_performer(t,
                                                  resourceUsername,
                                                  userAuth);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      try {
         return SharkEngineManager
            .getInstance()
            .getInstancePersistenceManager()
            .getResourceRequestersProcessIds(resourceUsername,t).size();
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   /**
    * Gets an iterator of processes.
    */
   public WfProcessIterator get_iterator_performer () throws BaseException {
      WfProcessIterator ret=null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_iterator_performer(t);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         throw (BaseException)e;
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public WfProcessIterator get_iterator_performer (SharkTransaction t) throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_requester_get_iterator_performer(t,
                                                      resourceUsername,
                                                      userAuth);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      return SharkEngineManager.getInstance().getObjectFactory().createProcessIteratorWrapper(t,userAuth,resourceUsername,true);
   }

   /**
    * Returns a list of processes.
    * @return List of WfProcess objects.
    */
   public WfProcess[] get_sequence_performer (int max_number) throws BaseException {
      WfProcess[] ret=null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_sequence_performer(t,max_number);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         throw (BaseException)e;
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public WfProcess[] get_sequence_performer (SharkTransaction t,int max_number) throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_requester_get_sequence_performer(t,
                                                      resourceUsername,
                                                      userAuth);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      List alist = SharkUtilities.createResourceRequesterPerformersWrapper(t,userAuth,resourceUsername);
      if (max_number > alist.size() || max_number<=0) {
         max_number = alist.size();
      }
      WfProcess[] ret = new WfProcess[max_number];
      alist.subList(0, max_number).toArray(ret);
      return ret;
   }

   /**
    * Checks if a WfProcess is associated with this requester object.
    * @return true if the process is found.
    */
   public boolean is_member_of_performer (WfProcess member) throws BaseException {
      boolean ret=false;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = is_member_of_performer(t,member);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         throw (BaseException)e;
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public boolean is_member_of_performer (SharkTransaction t,WfProcess member) throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_requester_is_member_of_performer(t,
                                                      resourceUsername,
                                                      userAuth);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      try {
         String procId=member.key(t);
         List ids=SharkEngineManager.getInstance().getInstancePersistenceManager().getResourceRequestersProcessIds(resourceUsername,t);
         return ids.contains(procId);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   /**
    * Receives notice of event status changes.
    */
   public void receive_event (WfEventAudit event) throws BaseException, InvalidPerformer {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         receive_event(t,event);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         throw (BaseException)e;
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void receive_event (SharkTransaction t,WfEventAudit event) throws BaseException, InvalidPerformer {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_requester_receive_event(t,
                                             resourceUsername,
                                             userAuth);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
   }

}

