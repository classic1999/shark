package org.enhydra.shark;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfservice.DeadlineAdministration;
import org.enhydra.shark.api.common.DeadlineInfo;
import org.enhydra.shark.api.common.SharkConstants;
import org.enhydra.shark.api.internal.instancepersistence.ProcessPersistenceInterface;
import org.enhydra.shark.api.internal.security.SecurityManager;
import org.enhydra.shark.api.internal.working.WfActivityInternal;
import org.enhydra.shark.api.internal.working.WfProcessInternal;
import org.enhydra.shark.api.internal.working.WfRequesterInternal;

/**
 * The implementation of client interface through which client can check deadlines.
 * @author Sasa Bojanic
 */
public class DeadlineAdmin implements DeadlineAdministration {

   private String userId="Unknown";

   protected DeadlineAdmin () {
   }

   public void connect (String userId) {
      this.userId=userId;
   }

   public void checkDeadlines () throws BaseException {
      checkSecurity();
      SharkTransaction t = null;
      List pos;

      try {
         t = SharkUtilities.createTransaction();
         pos=SharkEngineManager.getInstance().getInstancePersistenceManager().getAllRunningProcesses(t);
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
      for (int i=0; i<pos.size(); i++) {
         try {
            t = SharkUtilities.createTransaction();
            ProcessPersistenceInterface po=(ProcessPersistenceInterface)pos.get(i);

            WfProcessInternal proc=SharkUtilities.getProcess(t,po.getId());
            if (proc.state(t).equals(SharkConstants.STATE_OPEN_RUNNING)) {
               proc.checkDeadlines(t);
               SharkUtilities.commitTransaction(t);
            }
         } catch (Exception e) {
            BaseException be=null;
            if (e instanceof BaseException)
               be=(BaseException)e;
            else
               be=new BaseException(e);

            SharkUtilities.rollbackTransaction(t,be);
            throw be;
         } finally {
            SharkUtilities.releaseTransaction(t);
         }
      }
   }

   public String[] checkDeadlines(final int instancesPerTransaction, int failuresToIgnore) throws BaseException {
      checkSecurity();
      
      List instancesFailed2check = new ArrayList();
       String reevalStr=SharkEngineManager
          .getInstance()
          .getCallbackUtilities()
          .getProperty("Deadlines.reevaluateDeadlines","true");
       boolean dreeval=Boolean.valueOf(reevalStr).booleanValue();

       List instancesToCheck = null;
       if (dreeval) {
          instancesToCheck=getAllRunningProcesses();
       } else {
          instancesToCheck=getAllDeadlineInvalidProcessIds();
       }

       int sizeToCheck=instancesToCheck.size();
       Iterator iterProcesses = instancesToCheck.iterator();
       List currentBatch = null;
       do {
           SharkTransaction t = null;
           String iid = null;
           currentBatch = new ArrayList();
           try {
               t = SharkUtilities.createTransaction();
               for (int n = 0; n < instancesPerTransaction; ++n) {
                   if (!iterProcesses.hasNext()) {
                       break;
                   }
                   if (dreeval) {
                      iid = ((ProcessPersistenceInterface)iterProcesses.next()).getId();
                   } else {
                      iid = (String)iterProcesses.next();
                   }
                   iterProcesses.remove();
                   currentBatch.add(iid);
                   checkDeadlines(t, iid);
               }
               SharkUtilities.commitTransaction(t);
           } catch (RootException _) {
               SharkUtilities.rollbackTransaction(t,_);
               instancesFailed2check.addAll(currentBatch);
               // may log something
           } finally {
               SharkUtilities.releaseTransaction(t);
           }
           //System.err.println("\ttransaction finished: batch size:"+currentBatch.size());
       } while (instancesFailed2check.size() <= failuresToIgnore && iterProcesses.hasNext());
       String[] ret = new String[instancesFailed2check.size()];
       instancesFailed2check.toArray(ret);
       System.out.println("  deadline check finished: checked:"+sizeToCheck+", failed:"+ret.length);
       return ret;
   }

   public String[] checkDeadlinesWithTermination() throws BaseException {
      checkSecurity();
      List instancesFailed2check = new ArrayList();
      String reevalStr=SharkEngineManager
         .getInstance()
         .getCallbackUtilities()
         .getProperty("Deadlines.reevaluateDeadlines","true");
      boolean dreeval=Boolean.valueOf(reevalStr).booleanValue();

      List instancesToCheck = null;
      if (dreeval) {
         instancesToCheck=getAllRunningProcesses();
      } else {
         instancesToCheck=getAllDeadlineInvalidProcessIds();
      }

      Iterator iterProcesses = instancesToCheck.iterator();
      String iid=null;
      while (iterProcesses.hasNext()) {
         iid = (String)iterProcesses.next();
         SharkTransaction st = null;
         try {
            st = SharkUtilities.createTransaction();
            WfProcessInternal proc=SharkUtilities.getProcess(st,iid);
            if (proc.state(st).equals(SharkConstants.STATE_OPEN_RUNNING)) {
               WfRequesterInternal req=proc.requester(st);
               if (req instanceof WfActivityInternal) {
                  WfActivityInternal act=((WfActivityInternal)req);
                  act.terminate(st);
               } else {
                  proc.terminate(st);
               }
               SharkUtilities.commitTransaction(st);
               String msg="Proc "+iid+" terminated ";
               if (req instanceof WfActivityInternal) {
                  msg+=" through his parent subflow activity "+req;
               }
               msg+=" !";
               System.out.println(msg);
            }
          } catch (RootException _) {
            SharkUtilities.rollbackTransaction(st,_);
            instancesFailed2check.add(iid);
            System.out.println("Failed to terminate process "+iid);
            // may log something
          } finally {
              SharkUtilities.releaseTransaction(st);
          }
          //System.err.println("\ttransaction finished: batch size:"+currentBatch.size());
      }
      String[] ret = new String[instancesFailed2check.size()];
      instancesFailed2check.toArray(ret);
      System.out.println("  deadline check finished: checked:"+instancesToCheck.size()+", failed:"+ret.length);
      return ret;
  }

   public void checkDeadlines (SharkTransaction t) throws BaseException {
      throw new BaseException("Not implemented - please use the method without SharkTransaction parameter");
   }

   public void checkDeadlines (String[] procIds) throws BaseException {
      checkSecurity();
      if (procIds==null) throw new BaseException("Invalid null value for parameter procIds");
      SharkTransaction t = null;
      for (int i=0; i<procIds.length; i++) {
         try {
            t = SharkUtilities.createTransaction();
            WfProcessInternal proc=SharkUtilities.getProcess(t,procIds[i]);
            if (proc.state(t).equals(SharkConstants.STATE_OPEN_RUNNING)) {
               proc.checkDeadlines(t);
               SharkUtilities.commitTransaction(t);
            }
         } catch (Exception e) {
            BaseException be=null;
            if (e instanceof BaseException)
               be=(BaseException)e;
            else
               be=new BaseException(e);

            SharkUtilities.rollbackTransaction(t,be);
            throw be;
         } finally {
            SharkUtilities.releaseTransaction(t);
         }
      }
   }

   public void checkDeadlines (SharkTransaction t,String[] procIds) throws BaseException {
      throw new BaseException("Not implemented - please use the method without SharkTransaction parameter");
   }

   public void checkDeadlines (String procId) throws BaseException {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         checkDeadlines(t,procId);
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

   public void checkDeadlines (SharkTransaction t,String procId) throws BaseException {
      checkSecurity();

      WfProcessInternal proc=SharkUtilities.getProcess(t,procId);
      if (proc==null) throw new BaseException("Deadline checking failed - can't find process with Id="+procId);
      if (proc.state(t).equals(SharkConstants.STATE_OPEN_RUNNING)) {
         proc.checkDeadlines(t);
      }
   }

   public void checkDeadline (String procId,String actId) throws BaseException {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         checkDeadline(t,procId,actId);
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

   public void checkDeadline (SharkTransaction t,String procId,String actId) throws BaseException {
      checkSecurity();

      WfProcessInternal proc=SharkUtilities.getProcess(t,procId);
      if (proc==null) throw new BaseException("Deadline checking failed - can't find process with Id="+procId);
      if (proc.state(t).equals(SharkConstants.STATE_OPEN_RUNNING)) {
         proc.checkDeadline(t,actId);
      }
   }

   public DeadlineInfo[] getDeadlineInfo(String procId) throws BaseException {
      DeadlineInfo[] ret;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getDeadlineInfo(t, procId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t,e);
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

   public DeadlineInfo[] getDeadlineInfo(SharkTransaction t,String procId) throws BaseException {
      checkSecurity();
      WfProcessInternal proc=SharkUtilities.getProcess(t, procId);
      if (proc==null) {
         throw new BaseException ("There is no process with id "+procId+" !");
      }
      List acts=proc.getActiveActivities(t);
      List dinfo=new ArrayList();
      Iterator it=acts.iterator();
      while (it.hasNext()) {
         WfActivityInternal act=(WfActivityInternal)it.next();
         dinfo.addAll(act.getDeadlineInfo(t));
      }
      DeadlineInfo[] ret=new DeadlineInfo[dinfo.size()];
      dinfo.toArray(ret);
      return ret;
   }

   public DeadlineInfo[] getDeadlineInfo(String procId, String actId) throws BaseException {
      DeadlineInfo[] ret;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getDeadlineInfo(t, procId, actId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t,e);
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

   public DeadlineInfo[] getDeadlineInfo(SharkTransaction t,String procId, String actId) throws BaseException {
      checkSecurity();
      WfActivityInternal act=SharkUtilities.getActivity(t, procId, actId);
      if (act==null) {
         throw new BaseException ("There is no activity with id "+actId+" in process "+procId+" !");
      }
      List dinfo=act.getDeadlineInfo(t);
      DeadlineInfo[] ret=new DeadlineInfo[dinfo.size()];
      dinfo.toArray(ret);
      return ret;
   }   

   protected List getAllRunningProcesses() throws BaseException {
      SharkTransaction t=null;
      try {
          t = SharkUtilities.createTransaction();
          return SharkEngineManager.getInstance().getInstancePersistenceManager().getAllRunningProcesses(t);
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
  }

  protected List getAllDeadlineInvalidProcessIds() throws BaseException {
     SharkTransaction t = null;
     try {
         t = SharkUtilities.createTransaction();
         return SharkEngineManager.getInstance().getInstancePersistenceManager().getAllIdsForProcessesWithExpiriedDeadlines(System.currentTimeMillis(), t);
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
  }

   protected void checkSecurity () throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         SharkTransaction t=null;
         try {
            t = SharkUtilities.createTransaction();
            sm.check_deadlines (t,userId);                  
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
   }

}
