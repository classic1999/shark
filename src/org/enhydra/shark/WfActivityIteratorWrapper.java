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
import org.enhydra.shark.api.common.SharkConstants;
import org.enhydra.shark.api.internal.instancepersistence.ActivityPersistenceInterface;
import org.enhydra.shark.api.internal.instancepersistence.ActivityVariablePersistenceInterface;
import org.enhydra.shark.api.internal.instancepersistence.PersistentManagerInterface;
import org.enhydra.shark.api.internal.instancepersistence.ProcessPersistenceInterface;
import org.enhydra.shark.api.internal.instancepersistence.ProcessVariablePersistenceInterface;
import org.enhydra.shark.api.internal.scripting.Evaluator;

/**
 * Iterator for activities in the process. The following names may be
 * used in queries: key, name, priority, description, state,
 * activitySetDefinitionId, definitionId, activatedTime_ms,
 * lastStateTime_ms, resourceUsername, accepted, acceptedTime_ms. Also
 * the names of activity context variables can be used, but the
 * "context_" prefix should be placed before variable Id, i.e.
 * "context_myvariable".
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class WfActivityIteratorWrapper extends BaseIteratorWrapper implements
                                                                  WfActivityIterator {

   private String procId;

   protected WfActivityIteratorWrapper(SharkTransaction t,
                                       String userAuth,
                                       String procId) throws BaseException {
      super(userAuth);
      this.procId = procId;
   }

   public WfActivity get_next_object() throws BaseException {
      WfActivity ret = null;
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

   public WfActivity get_next_object(SharkTransaction t) throws BaseException {
      return (WfActivity) super.getNextObject(t);
   }

   public WfActivity get_previous_object() throws BaseException {
      WfActivity ret = null;
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

   public WfActivity get_previous_object(SharkTransaction t) throws BaseException {
      return (WfActivity) super.getPreviousObject(t);
   }

   public WfActivity[] get_next_n_sequence(int max_number) throws BaseException {
      WfActivity[] ret = null;
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

   public WfActivity[] get_next_n_sequence(SharkTransaction t, int max_number) throws BaseException {
      List l = super.getNextNSequence(t, max_number);
      WfActivity[] ret = new WfActivity[l.size()];
      l.toArray(ret);
      return ret;
   }

   public WfActivity[] get_previous_n_sequence(int max_number) throws BaseException {
      WfActivity[] ret = null;
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

   public WfActivity[] get_previous_n_sequence(SharkTransaction t,
                                               int max_number) throws BaseException {
      List l = super.getPreviousNSequence(t, max_number);
      WfActivity[] ret = new WfActivity[l.size()];
      l.toArray(ret);
      return ret;
   }

   protected void fillObjectList(SharkTransaction t) throws BaseException {
      if (objectList != null) return;
      try {
         List activities = new ArrayList();

         PersistentManagerInterface ipm = SharkEngineManager.getInstance()
            .getInstancePersistenceManager();

         String qemod = queryExpression;
         if (queryExpression.startsWith(SharkConstants.QUERY_STATE_PREFIX)) {
            qemod = queryExpression.substring(SharkConstants.QUERY_STATE_PREFIX.length());
         }
         List l = new ArrayList();
         if (null != this.sqlWhere) {
            l.addAll(ipm.getActivitiesWhere(t, sqlWhere));
         } else if (qemod.equals(queryExpression)
                    || queryGrammar.equals(SharkConstants.GRAMMAR_PYTHON_SCRIPT)) {
            l.addAll(ipm.getAllActivitiesForProcess(procId, t));
         } else {
            String actState = null;
            if (qemod.indexOf(SharkConstants.STATE_OPEN_RUNNING) != -1) {
               actState = SharkConstants.STATE_OPEN_RUNNING;
            } else if (qemod.indexOf(SharkConstants.STATE_OPEN_NOT_RUNNING_NOT_STARTED) != -1) {
               actState = SharkConstants.STATE_OPEN_NOT_RUNNING_NOT_STARTED;
            } else if (qemod.indexOf(SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED) != -1) {
               actState = SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED;
            } else if (qemod.indexOf(SharkConstants.STATE_CLOSED_COMPLETED) != -1) {
               actState = SharkConstants.STATE_CLOSED_COMPLETED;
            } else if (qemod.indexOf(SharkConstants.STATE_CLOSED_ABORTED) != -1) {
               actState = SharkConstants.STATE_CLOSED_ABORTED;
            } else if (qemod.indexOf(SharkConstants.STATE_CLOSED_TERMINATED) != -1) {
               actState = SharkConstants.STATE_CLOSED_TERMINATED;
            }
            l.addAll(ipm.getActivitiesForProcess(procId, actState, t));
            eval=false;
         }
         Evaluator evaluator = SharkEngineManager.getInstance()
            .getScriptingManager()
            .getEvaluator(t, queryGrammar);
         for (int i = 0; i < l.size(); i++) {
            ActivityPersistenceInterface po = (ActivityPersistenceInterface) l.get(i);
            boolean toAdd = true;
            if (eval) {
               Map context = new HashMap();
               ProcessPersistenceInterface ppo = null;
               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.ACT_MGR_PACKAGE_ID))) {
                  ppo = ipm.restoreProcess(po.getProcessId(), t);
                  context.put(SharkConstants.ACT_MGR_PACKAGE_ID,
                              SharkUtilities.getProcessMgrVersion(ppo.getProcessMgrName()));
               }
               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.ACT_MGR_PROCESS_DEFINITION_ID))) {
                  if (null == ppo) ppo = ipm.restoreProcess(po.getProcessId(),                                                               t);
                  context.put(SharkConstants.ACT_MGR_PROCESS_DEFINITION_ID,
                              SharkUtilities.getProcessMgrProcDefId(ppo.getProcessMgrName()));
               }
               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.ACT_MGR_VERSION))) {
                  if (null == ppo) ppo = ipm.restoreProcess(po.getProcessId(),t);
                  context.put(SharkConstants.ACT_MGR_PACKAGE_ID,
                              SharkUtilities.getProcessMgrPkgId(ppo.getProcessMgrName()));
               }
               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.ACT_MGR_NAME))) {
                  if (null == ppo) ppo = ipm.restoreProcess(po.getProcessId(),t);
                  context.put(SharkConstants.ACT_MGR_NAME,ppo.getProcessMgrName());
               }

               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.ACT_PROC_KEY))) {
                  context.put(SharkConstants.ACT_PROC_KEY,po.getProcessId());
               }
               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.ACT_PROC_STATE))) {
                  if (null == ppo) ppo = ipm.restoreProcess(po.getProcessId(),
                                                            t);
                  context.put(SharkConstants.ACT_PROC_STATE,
                              ppo.getState());
               }
               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.ACT_PROC_REQUESTER_ID))) {
                  if (null == ppo) ppo = ipm.restoreProcess(po.getProcessId(),t);
                  String reqId;
                  if (ppo.getActivityRequesterId() != null) {
                     reqId = ppo.getActivityRequesterId();
                  } else {
                     reqId = ppo.getResourceRequesterId();
                  }
                  context.put(SharkConstants.ACT_PROC_REQUESTER_ID, reqId);
               }
               if (queryExpression.indexOf(SharkConstants.ACT_PROC_CONTEXT_)!=-1) {
                  if (null == ppo) ppo = ipm.restoreProcess(po.getProcessId(),
                                                            t);
                  List pc = ipm.getAllVariablesForProcess(ppo.getId(), t);
                  if (pc != null) {
                     Iterator iter = pc.iterator();
                     while (iter.hasNext()) {
                        ProcessVariablePersistenceInterface pvpo = (ProcessVariablePersistenceInterface) iter.next();
                        try {
                           String name = SharkConstants.ACT_PROC_CONTEXT_
                                         + pvpo.getDefinitionId();
                           Object value = pvpo.getValue();
                           context.put(name, value);
                        } catch (Exception ex) {
                           throw new BaseException(ex);
                        }
                     }
                  }
               }

               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.ACT_STATE))) {
                  context.put(SharkConstants.ACT_STATE, po.getState());
               }
               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.ACT_KEY))) {
                  context.put(SharkConstants.ACT_KEY, po.getId());
               }
               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.ACT_NAME))) {
                  context.put(SharkConstants.ACT_NAME, po.getName());
               }
               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.ACT_PRIORITY))) {
                  context.put(SharkConstants.ACT_PRIORITY,
                              new Integer(po.getPriority()));
               }
               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.ACT_DESCRIPTION))) {
                  context.put(SharkConstants.ACT_DESCRIPTION,
                              po.getDescription());
               }
               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.ACT_ACTIVITY_SET_DEFINITION_ID))) {
                  context.put(SharkConstants.ACT_ACTIVITY_SET_DEFINITION_ID,
                              po.getActivitySetDefinitionId());
               }
               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.ACT_DEFINITION_ID))) {
                  context.put(SharkConstants.ACT_DEFINITION_ID,
                              po.getActivityDefinitionId());
               }
               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.ACT_ACTIVATED_TIME_MS))) {
                  context.put(SharkConstants.ACT_ACTIVATED_TIME_MS,
                              new Long(po.getActivatedTime()));
               }
               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.ACT_LAST_STATE_TIME_MS))) {
                  context.put(SharkConstants.ACT_LAST_STATE_TIME_MS,
                              new Long(po.getLastStateTime()));
               }
               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.ACT_RESOURCE_USERNAME))) {
                  context.put(SharkConstants.ACT_RESOURCE_USERNAME,
                              po.getResourceUsername());
               }
               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.ACT_ACCEPTED))) {
                  context.put(SharkConstants.ACT_ACCEPTED,
                              new Boolean(po.getResourceUsername() != null));
               }
               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.ACT_ACCEPTED_TIME_MS))) {
                  context.put(SharkConstants.ACT_ACCEPTED_TIME_MS,
                              new Long(po.getAcceptedTime()));
               }

               if (queryExpression.indexOf(SharkConstants.ACT_CONTEXT_) != -1) {
                  List ac = ipm.getAllVariablesForActivity(po.getId(), t);
                  if (ac != null) {
                     Iterator iter = ac.iterator();
                     while (iter.hasNext()) {
                        ActivityVariablePersistenceInterface avpo = (ActivityVariablePersistenceInterface) iter.next();
                        try {
                           String name = SharkConstants.ACT_CONTEXT_
                                         + avpo.getDefinitionId();
                           Object value = avpo.getValue();
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
               activities.add(SharkEngineManager.getInstance()
                  .getObjectFactory()
                  .createActivityWrapper(userAuth,
                                         po.getProcessMgrName(),
                                         po.getProcessId(),
                                         po.getId()));
            }

         }
         setObjectList(activities);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }
}