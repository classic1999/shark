package org.enhydra.shark;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;
import java.util.Map;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfmodel.WfEventAudit;
import org.enhydra.shark.api.client.wfmodel.WfEventAuditIterator;
import org.enhydra.shark.api.common.SharkConstants;
import org.enhydra.shark.api.internal.scripting.Evaluator;
import org.enhydra.shark.api.internal.working.WfEventAuditInternal;
import org.enhydra.shark.api.internal.eventaudit.*;


/**
 * Iterator for event audits of activity or the process.
 * The following names may be used: time_stamp, event_type, activity_key,
 * activity_name, process_key, process_name, process_mgr_name, process_mgr_version,
 * package_id, process_definition_id, activity_set_definition_id,
 * activity_definition_id that will be applied for all event types.
 * <p> Here are the list of attributes that if specified will be applied
 * to specific event type:
 * <p> WfCreateProcessEventAudit: p_activity_key, p_process_key, p_process_name,
 * p_process_mgr_name, p_process_mgr_version, p_package_id, p_process_definition_id,
 * p_activity_set_definition_id and p_activity_definition_id
 * <p> WfDataEventAudit: old_data_varId and new_data_varId, where varId is the
 * Id of variable from appropriate process/activity
 * <p> WfStateEventAudit: old_state and new_state
 * <p> WfAssignmentEventAudit: old_res, new_res and is_accepted
 * @author Sasa Bojanic
 */
public class WfEventAuditIteratorWrapper extends BaseIteratorWrapper implements WfEventAuditIterator {

   private String procId;
   private String actId;

   protected WfEventAuditIteratorWrapper (SharkTransaction t,String userAuth,String procId) throws BaseException {
      super(userAuth);
      this.procId=procId;
   }

   protected WfEventAuditIteratorWrapper (SharkTransaction t,String userAuth,String procId,String actId) throws BaseException {
      super(userAuth);
      this.procId=procId;
      this.actId=actId;
   }

   public WfEventAudit get_next_object () throws BaseException {
      WfEventAudit ret=null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_next_object(t);
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

   public WfEventAudit get_next_object (SharkTransaction t) throws BaseException {
      return (WfEventAudit)super.getNextObject(t);
   }


   public WfEventAudit get_previous_object () throws BaseException {
      WfEventAudit ret=null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_previous_object(t);
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

   public WfEventAudit get_previous_object (SharkTransaction t) throws BaseException {
      return (WfEventAudit)super.getPreviousObject(t);
   }

   public WfEventAudit[] get_next_n_sequence (int max_number) throws BaseException {
      WfEventAudit[] ret=null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_next_n_sequence(t,max_number);
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

   public WfEventAudit[] get_next_n_sequence (SharkTransaction t,int max_number) throws BaseException {
      List l=super.getNextNSequence(t,max_number);
      WfEventAudit[] ret=new WfEventAudit[l.size()];
      l.toArray(ret);
      return ret;
   }

   public WfEventAudit[] get_previous_n_sequence (int max_number) throws BaseException {
      WfEventAudit[] ret=null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_previous_n_sequence(t,max_number);
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

   public WfEventAudit[] get_previous_n_sequence (SharkTransaction t,int max_number) throws BaseException {
      List l=super.getPreviousNSequence(t,max_number);
      WfEventAudit[] ret=new WfEventAudit[l.size()];
      l.toArray(ret);
      return ret;
   }

   protected void fillObjectList (SharkTransaction t) throws BaseException {
      if (objectList!=null) return;
      try {
         List events=new ArrayList();

         EventAuditManagerInterface eam = SharkEngineManager
            .getInstance()
            .getEventAuditManager();
         if (null != eam) {
            List l=new ArrayList();
            if (actId==null) {
               l.addAll(eam.restoreProcessHistory(procId, t));
            } else {
               l.addAll(eam.restoreActivityHistory(procId,actId,t));
            }
            Evaluator evaluator=SharkEngineManager
               .getInstance()
               .getScriptingManager()
               .getEvaluator(t,queryGrammar);

            boolean eval=queryExpression!=null && queryExpression.trim().length()>0;

            for (int i=0; i<l.size(); i++) {
               EventAuditPersistenceInterface po=(EventAuditPersistenceInterface)l.get(i);
               boolean toAdd=true;
               if (eval) {
                  Map context=new HashMap();
                  context.put(SharkConstants.EA_TIME_STAMP,new Long(po.getUTCTime()));
                  context.put(SharkConstants.EA_EVENT_TYPE,po.getType());
                  context.put(SharkConstants.EA_ACTIVITY_KEY,po.getActivityId());
                  context.put(SharkConstants.EA_ACTIVITY_NAME,po.getActivityName());
                  context.put(SharkConstants.EA_PROCESS_KEY,po.getProcessId());
                  context.put(SharkConstants.EA_PROCESS_NAME,po.getProcessName());
                  context.put(SharkConstants.EA_PROCESS_MGR_NAME,po.getProcessDefinitionName());
                  context.put(SharkConstants.EA_PROCESS_MGR_VERSION,po.getProcessDefinitionVersion());
                  context.put(SharkConstants.EA_PACKAGE_ID,po.getPackageId());
                  context.put(SharkConstants.EA_PROCESS_DEFINITION_ID,po.getProcessDefinitionId());
                  context.put(SharkConstants.EA_ACTIVITY_SET_DEFINITION_ID,po.getActivitySetDefinitionId());
                  context.put(SharkConstants.EA_ACTIVITY_DEFINITION_ID,po.getActivityDefinitionId());

                  if (po instanceof CreateProcessEventAuditPersistenceInterface) {
                     CreateProcessEventAuditPersistenceInterface cpo=(CreateProcessEventAuditPersistenceInterface)po;
                     context.put(SharkConstants.CEA_P_ACTIVITY_KEY,cpo.getPActivityId());
                     context.put(SharkConstants.CEA_P_PROCESS_KEY,cpo.getPProcessId());
                     context.put(SharkConstants.CEA_P_PROCESS_NAME,cpo.getPProcessName());
                     context.put(SharkConstants.CEA_P_PROCESS_MGR_NAME,cpo.getPProcessDefinitionName());
                     context.put(SharkConstants.CEA_P_PROCESS_MGR_VERSION,cpo.getPProcessDefinitionVersion());
                     context.put(SharkConstants.CEA_P_PACKAGE_ID,cpo.getPPackageId());
                     context.put(SharkConstants.CEA_P_PROCESS_DEFINITION_ID,cpo.getPProcessDefinitionId());
                     context.put(SharkConstants.CEA_P_ACTIVITY_SET_DEFINITION_ID,cpo.getPActivitySetDefinitionId());
                     context.put(SharkConstants.CEA_P_ACTIVITY_DEFINITION_ID,cpo.getPActivityDefinitionId());
                  } else if (po instanceof DataEventAuditPersistenceInterface) {
                     DataEventAuditPersistenceInterface dpo=(DataEventAuditPersistenceInterface)po;
                     if (queryExpression.indexOf(SharkConstants.DEA_OLD_DATA_)!=-1) {
                        Map od=dpo.getOldData();
                        if (od!=null) {
                           Iterator it=od.entrySet().iterator();
                           while (it.hasNext()) {
                              Map.Entry me=(Map.Entry)it.next();
                              try {
                                 String name=SharkConstants.DEA_OLD_DATA_+me.getKey().toString();
                                 Object value=me.getValue();
                                 context.put(name,value);
                              } catch (Exception ex) {
                                 throw new BaseException(ex);
                              }
                           }
                        }
                     }
                     if (queryExpression.indexOf(SharkConstants.DEA_NEW_DATA_)!=-1) {
                        Map nd=dpo.getNewData();
                        if (nd!=null) {
                           Iterator it=nd.entrySet().iterator();
                           while (it.hasNext()) {
                              Map.Entry me=(Map.Entry)it.next();
                              try {
                                 String name=SharkConstants.DEA_NEW_DATA_+me.getKey().toString();
                                 Object value=me.getValue();
                                 context.put(name,value);
                              } catch (Exception ex) {
                                 throw new BaseException(ex);
                              }
                           }
                        }
                     }

                  } else if (po instanceof StateEventAuditPersistenceInterface) {
                     StateEventAuditPersistenceInterface spo=(StateEventAuditPersistenceInterface)po;
                     context.put(SharkConstants.SEA_OLD_STATE,spo.getOldState());
                     context.put(SharkConstants.SEA_NEW_STATE,spo.getNewState());
                  } else if (po instanceof AssignmentEventAuditPersistenceInterface) {
                     AssignmentEventAuditPersistenceInterface apo=(AssignmentEventAuditPersistenceInterface)po;
                     context.put(SharkConstants.AEA_OLD_RES,apo.getOldResourceUsername());
                     context.put(SharkConstants.AEA_NEW_RES,apo.getNewResourceUsername());
                     context.put(SharkConstants.AEA_IS_ACCEPTED,new Boolean(apo.getIsAccepted()));
                  }

                  toAdd=evaluator.evaluateCondition(t,queryExpression,context);
               }
               if (toAdd) {
                  if (po instanceof CreateProcessEventAuditPersistenceInterface) {
                     events.add(SharkEngineManager
                                   .getInstance()
                                   .getObjectFactory()
                                   .createCreateProcessEventAuditWrapper
                                   (userAuth,(CreateProcessEventAuditPersistenceInterface)po));
                  } else if (po instanceof DataEventAuditPersistenceInterface) {
                     events.add(SharkEngineManager
                                   .getInstance()
                                   .getObjectFactory()
                                   .createDataEventAuditWrapper
                                   (userAuth,(DataEventAuditPersistenceInterface)po));
                  } else if (po instanceof StateEventAuditPersistenceInterface) {
                     events.add(SharkEngineManager
                                   .getInstance()
                                   .getObjectFactory()
                                   .createStateEventAuditWrapper
                                   (userAuth,(StateEventAuditPersistenceInterface)po));
                  } else if (po instanceof AssignmentEventAuditPersistenceInterface) {
                     events.add(SharkEngineManager
                                   .getInstance()
                                   .getObjectFactory()
                                   .createAssignmentEventAuditWrapper
                                   (userAuth,(AssignmentEventAuditPersistenceInterface)po));
                  }
               }
            }

         }

         setObjectList(events);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

}
