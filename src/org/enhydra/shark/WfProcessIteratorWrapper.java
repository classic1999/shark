package org.enhydra.shark;

import java.util.*;

import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfmodel.WfProcess;
import org.enhydra.shark.api.client.wfmodel.WfProcessIterator;
import org.enhydra.shark.api.common.SharkConstants;
import org.enhydra.shark.api.internal.instancepersistence.*;
import org.enhydra.shark.api.internal.scripting.Evaluator;

/**
 * Iterator for process managers processes. The following names may be
 * used in queries: key, name, priority, description, state,
 * requesterId, createdTime_ms, startTime_ms, lastStateTime_ms, activeActivitiesNo. Also
 * the names of process context variables can be used, but the
 * "context_" prefix should be placed before variable Id, i.e.
 * "context_myvariable".
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class WfProcessIteratorWrapper extends BaseIteratorWrapper implements
                                                                 WfProcessIterator {

   private String mgrName;

   private String procId;

   private String actId;

   private String username;

   protected WfProcessIteratorWrapper(SharkTransaction t,
                                      String userAuth,
                                      String mgrName) throws BaseException {
      super(userAuth);
      this.mgrName = mgrName;
   }

   protected WfProcessIteratorWrapper(SharkTransaction t,
                                      String userAuth,
                                      String procId,
                                      String actId) throws BaseException {
      super(userAuth);
      this.procId = procId;
      this.actId = actId;
   }

   protected WfProcessIteratorWrapper(SharkTransaction t,
                                      String userAuth,
                                      String resUname,
                                      boolean ext) throws BaseException {
      super(userAuth);
      this.username = resUname;
   }

   protected WfProcessIteratorWrapper(SharkTransaction t,
                                      String userAuth,
                                      List collection) throws BaseException {
      super(userAuth);
   }

   public WfProcess get_next_object() throws BaseException {
      WfProcess ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_next_object(t);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException) e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public WfProcess get_next_object(SharkTransaction t) throws BaseException {
      return (WfProcess) super.getNextObject(t);
   }

   public WfProcess get_previous_object() throws BaseException {
      WfProcess ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_previous_object(t);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException) e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public WfProcess get_previous_object(SharkTransaction t) throws BaseException {
      return (WfProcess) super.getPreviousObject(t);
   }

   public WfProcess[] get_next_n_sequence(int max_number) throws BaseException {
      WfProcess[] ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_next_n_sequence(t, max_number);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException) e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public WfProcess[] get_next_n_sequence(SharkTransaction t, int max_number) throws BaseException {
      List l = super.getNextNSequence(t, max_number);
      WfProcess[] ret = new WfProcess[l.size()];
      l.toArray(ret);
      return ret;
   }

   public WfProcess[] get_previous_n_sequence(int max_number) throws BaseException {
      WfProcess[] ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_previous_n_sequence(t, max_number);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException) e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public WfProcess[] get_previous_n_sequence(SharkTransaction t,
                                              int max_number) throws BaseException {
      List l = super.getPreviousNSequence(t, max_number);
      WfProcess[] ret = new WfProcess[l.size()];
      l.toArray(ret);
      return ret;
   }

   protected void fillObjectList(SharkTransaction t) throws BaseException {
      if (objectList != null) return;
      try {
         List processes = new ArrayList();
         PersistentManagerInterface ipm = SharkEngineManager.getInstance()
            .getInstancePersistenceManager();
         List l = new ArrayList();
         if (null!=sqlWhere) {
            l.addAll(ipm.getProcessesWhere(t, sqlWhere));
         } else if (mgrName != null) {
            l.addAll(ipm.getAllProcessesForMgr(mgrName, t));
         } else if (procId != null && actId != null) {
            ActivityPersistenceInterface apo=ipm.restoreActivity(actId, t);
            String performerId = apo.getSubflowProcessId();
            if (performerId != null) {
               processes.add(SharkEngineManager.getInstance()
                  .getObjectFactory()
                  .createProcessWrapper(userAuth, apo.getProcessMgrName(), performerId));
            }
         } else if (username != null) {
            List ids = ipm.getResourceRequestersProcessIds(username, t);
            for (int i = 0; i < ids.size(); i++) {
               String pId=(String)ids.get(i);
               ProcessPersistenceInterface ppo = ipm.restoreProcess(pId, t);
               // TODO: decide if we have to check here about external
               // requesters (or maybe already in instance persistence
               // layer). This is all due to a change of persisting
               // external requesters
               processes.add(SharkEngineManager.getInstance()
                  .getObjectFactory()
                  .createProcessWrapper(userAuth, ppo.getProcessMgrName(), ppo.getId()));
            }
         }
         Evaluator evaluator = SharkEngineManager.getInstance()
            .getScriptingManager()
            .getEvaluator(t, queryGrammar);

         for (int i = 0; i < l.size(); i++) {
            ProcessPersistenceInterface po = (ProcessPersistenceInterface) l.get(i);
            boolean toAdd = true;
            if (eval) {
               Map context = new HashMap();

               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.PROC_MGR_PACKAGE_ID))) {
                  context.put(SharkConstants.PROC_MGR_PACKAGE_ID,
                              SharkUtilities.getProcessMgrPkgId(po.getProcessMgrName()));
               }
               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.PROC_MGR_PROCESS_DEFINITION_ID))) {
                  context.put(SharkConstants.PROC_MGR_PROCESS_DEFINITION_ID,
                              SharkUtilities.getProcessMgrProcDefId(po.getProcessMgrName()));
               }
               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.PROC_MGR_VERSION))) {
                  context.put(SharkConstants.PROC_MGR_VERSION,
                              SharkUtilities.getProcessMgrVersion(po.getProcessMgrName()));
               }
               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.PROC_MGR_NAME))) {
                  context.put(SharkConstants.PROC_MGR_NAME,
                              po.getProcessMgrName());
               }

               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.PROC_STATE))) {
                  context.put(SharkConstants.PROC_STATE, po.getState());
               }
               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.PROC_KEY))) {
                  context.put(SharkConstants.PROC_KEY, po.getId());
               }
               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.PROC_NAME))) {
                  context.put(SharkConstants.PROC_NAME, po.getName());
               }
               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.PROC_PRIORITY))) {
                  context.put(SharkConstants.PROC_PRIORITY,
                              new Integer(po.getPriority()));
               }
               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.PROC_DESCRIPTION))) {
                  context.put(SharkConstants.PROC_DESCRIPTION,
                              po.getDescription());
               }
               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.PROC_REQUESTER_ID))) {
                  context.put(SharkConstants.PROC_REQUESTER_ID, 
                              po.getActivityRequesterId());
               }
			   if (ignoreUsedContext || (!ignoreUsedContext &&
					 0 <= usedContext.indexOf(SharkConstants.PROC_REQUESTER_RESOURCE))) {
				  context.put(SharkConstants.PROC_REQUESTER_RESOURCE, 
				              po.getResourceRequesterId());
			   }
               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.PROC_CREATED_TIME_MS))) {
                  context.put(SharkConstants.PROC_CREATED_TIME_MS,
                              new Long(po.getCreatedTime()));
               }
               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.PROC_START_TIME_MS))) {
                  context.put(SharkConstants.PROC_START_TIME_MS,
                              new Long(po.getStartedTime()));
               }
               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.PROC_LAST_STATE_TIME_MS))) {
                  context.put(SharkConstants.PROC_LAST_STATE_TIME_MS,
                              new Long(po.getLastStateTime()));
               }
               if (queryExpression.indexOf(SharkConstants.PROC_ACTIVE_ACTIVITIES_NO) != -1) {
                  context.put(SharkConstants.PROC_ACTIVE_ACTIVITIES_NO,
                              new Long(ipm.getAllActiveActivitiesForProcess(po.getId(),
                                                                            t)
                                 .size()));
               }
               if (queryExpression.indexOf(SharkConstants.PROC_CONTEXT_) != -1) {
                  List pc = ipm.getAllVariablesForProcess(po.getId(), t);
                  if (pc != null) {
                     Iterator iter = pc.iterator();
                     while (iter.hasNext()) {
                        ProcessVariablePersistenceInterface pvpo = (ProcessVariablePersistenceInterface) iter.next();
                        try {
                           String name = SharkConstants.PROC_CONTEXT_
                                         + pvpo.getDefinitionId();
                           Object value = pvpo.getValue();
                           context.put(name, value);
                        } catch (Exception ex) {
                           throw new BaseException(ex);
                        }
                     }
                  }
               }

               toAdd = evaluator.evaluateCondition(t,
                                                   queryExpression,
                                                   context);
            }
            if (toAdd) {
               processes.add(SharkEngineManager.getInstance()
                  .getObjectFactory()
                  .createProcessWrapper(userAuth, po.getProcessMgrName(), po.getId()));
            }

         }
         setObjectList(processes);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

}