package org.enhydra.shark;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.enhydra.shark.api.ParticipantMappingTransaction;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.TransactionException;
import org.enhydra.shark.api.UserTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfmodel.AlreadyRunning;
import org.enhydra.shark.api.client.wfmodel.AlreadySuspended;
import org.enhydra.shark.api.client.wfmodel.CannotAcceptSuspended;
import org.enhydra.shark.api.client.wfmodel.CannotComplete;
import org.enhydra.shark.api.client.wfmodel.CannotResume;
import org.enhydra.shark.api.client.wfmodel.CannotStart;
import org.enhydra.shark.api.client.wfmodel.CannotStop;
import org.enhydra.shark.api.client.wfmodel.CannotSuspend;
import org.enhydra.shark.api.client.wfmodel.InvalidData;
import org.enhydra.shark.api.client.wfmodel.InvalidPerformer;
import org.enhydra.shark.api.client.wfmodel.InvalidState;
import org.enhydra.shark.api.client.wfmodel.NotRunning;
import org.enhydra.shark.api.client.wfmodel.NotSuspended;
import org.enhydra.shark.api.client.wfmodel.ResultNotAvailable;
import org.enhydra.shark.api.client.wfmodel.TransitionNotAllowed;
import org.enhydra.shark.api.client.wfmodel.UpdateNotAllowed;
import org.enhydra.shark.api.client.wfmodel.WfDataEventAudit;
import org.enhydra.shark.api.client.wfmodel.WfEventAudit;
import org.enhydra.shark.api.client.wfmodel.WfRequester;
import org.enhydra.shark.api.common.DeadlineInfo;
import org.enhydra.shark.api.common.SharkConstants;
import org.enhydra.shark.api.internal.assignment.PerformerData;
import org.enhydra.shark.api.internal.instancepersistence.ActivityPersistenceInterface;
import org.enhydra.shark.api.internal.instancepersistence.ActivityVariablePersistenceInterface;
import org.enhydra.shark.api.internal.instancepersistence.AssignmentPersistenceInterface;
import org.enhydra.shark.api.internal.instancepersistence.DeadlinePersistenceInterface;
import org.enhydra.shark.api.internal.instancepersistence.PersistenceException;
import org.enhydra.shark.api.internal.instancepersistence.PersistentManagerInterface;
import org.enhydra.shark.api.internal.limitagent.LimitAgentException;
import org.enhydra.shark.api.internal.limitagent.LimitAgentManager;
import org.enhydra.shark.api.internal.partmappersistence.ParticipantMap;
import org.enhydra.shark.api.internal.partmappersistence.ParticipantMappingManager;
import org.enhydra.shark.api.internal.scripting.Evaluator;
import org.enhydra.shark.api.internal.toolagent.ToolAgentGeneralException;
import org.enhydra.shark.api.internal.usergroup.UserGroupManager;
import org.enhydra.shark.api.internal.working.ToolAgentManager;
import org.enhydra.shark.api.internal.working.WfActivityInternal;
import org.enhydra.shark.api.internal.working.WfAssignmentInternal;
import org.enhydra.shark.api.internal.working.WfProcessInternal;
import org.enhydra.shark.api.internal.working.WfProcessMgrInternal;
import org.enhydra.shark.api.internal.working.WfResourceInternal;
import org.enhydra.shark.utilities.MiscUtilities;
import org.enhydra.shark.xpdl.XMLCollectionElement;
import org.enhydra.shark.xpdl.XMLComplexElement;
import org.enhydra.shark.xpdl.XMLUtil;
import org.enhydra.shark.xpdl.XPDLConstants;
import org.enhydra.shark.xpdl.elements.Activity;
import org.enhydra.shark.xpdl.elements.ActualParameter;
import org.enhydra.shark.xpdl.elements.ActualParameters;
import org.enhydra.shark.xpdl.elements.Deadline;
import org.enhydra.shark.xpdl.elements.FormalParameter;
import org.enhydra.shark.xpdl.elements.FormalParameters;
import org.enhydra.shark.xpdl.elements.Participant;
import org.enhydra.shark.xpdl.elements.Responsible;
import org.enhydra.shark.xpdl.elements.SubFlow;
import org.enhydra.shark.xpdl.elements.WorkflowProcess;

/**
 * WfActivityImpl - Workflow Activity Object implementation
 * @author Sasa Bojanic
 * @author Vladimir Puskas
 */
public class WfActivityImpl extends WfExecutionObjectImpl implements WfActivityInternal {

   private String mgrName;
   private String processId;

   private Activity activityDefinition;
   private WorkflowProcess processDefinition;
   private String activitySetDefinitionId;
   private String activityDefinitionId;

   private String blockActivityId;

   private boolean accepted=false;
   private String resourceUsername;

   private Map activitiesProcessContext;

   // for implementation of requester interface
   private String performerId;
   private boolean isSubflowSynchronous=true;

   // holds Ids of variables that make activity results - only those variables will be
   // returned to the process context when activity finishes
   private Set resultVariableIds;

   private Evaluator evaluator;

   private WfProcessInternal process;

   private long acceptedTime=Long.MAX_VALUE/2;

   private long activatedTime=Long.MAX_VALUE/2;

   protected List assignmentResourceIds;

   protected Set variableIdsToPersist=new HashSet();

   //private ToolRunner myToolRunner;

   protected Thread startSubflowThread=null;

   //protected boolean deleteAssignments=false;

   protected WfActivityInternal blockActivity=null;

   protected ToolAgentGeneralException toolAgentException=null;
   protected String exceptionName=null;
   protected List deadlinesInfo;

   private boolean justCreated=false;
   private boolean justCreatedVariables=false;
   private boolean justCreatedDeadlines=false;

   /**
    * Create a new WfActivityImpl
    */
   protected WfActivityImpl(SharkTransaction t,
                            WfProcessInternal process,
                            String key,
                            String activitySetDefId,
                            String activityDefId,
                            WfActivityInternal blockActivity) throws BaseException {
      this.mgrName=process.manager_name(t);
      this.processId=process.key(t);
      this.key=key;
      this.process=process;
      this.activityDefinitionId=activityDefId;
      this.blockActivity=blockActivity;
      this.justCreated=true;
      this.justCreatedVariables=true;
      this.justCreatedDeadlines=true;
      // initialize process definition
      WorkflowProcess wp=getProcessDefinition(t);
      if (this.blockActivity!=null) {
         this.blockActivityId=this.blockActivity.key(t);
         this.activitySetDefinitionId=activitySetDefId;
      }
      // initializing activity definition
      getActivityDefinition(t);
      name=activityDefinition.getName();
      if (name.equals("")) {
         name=getActivityDefinition(t).getId();
      }
      description=activityDefinition.getDescription();
      if (description!=null && description.length()>254) {
         description=description.substring(0,253);
      }
      try {
         priority=Integer.valueOf(activityDefinition.getPriority()).shortValue();
      } catch (Exception ex) {
         priority=3;
      }
      lastStateTime = System.currentTimeMillis();

      lastStateEventAudit=SharkEngineManager.
         getInstance().
         getObjectFactory().
         createStateEventAuditWrapper(t,
                                      this,
                                      SharkConstants.EVENT_ACTIVITY_STATE_CHANGED,
                                      null,
                                      state);

      activitiesProcessContext=getActivityContext(t);
      resultVariableIds=new HashSet();
      if (activitiesProcessContext.size()>0) {
         variableIdsToPersist.addAll(getContext(t).keySet());
         if (SharkEngineManager.getInstance().getEventAuditManager()!=null) {
            SharkEngineManager.
               getInstance().
               getObjectFactory().
               createDataEventAuditWrapper(t,
                                           this,
                                           SharkConstants.EVENT_ACTIVITY_CONTEXT_CHANGED,
                                           null,
                                           new HashMap(activitiesProcessContext));
         }
      }

      assignmentResourceIds=new ArrayList();

   }

   /**
    * Used to create object when restoring it from database.
    */
   protected WfActivityImpl (ActivityPersistenceInterface po,WfProcessInternal proc) {
      this.process=proc;
      restore(po);
   }

   public List getAssignmentResourceIds (SharkTransaction t) throws BaseException {
      if (assignmentResourceIds==null) {
         try {
            assignmentResourceIds=new ArrayList();
            boolean createAssignments=Boolean.valueOf(
                  SharkEngineManager.
                  getInstance().
                  getCallbackUtilities().
                  getProperty("SharkKernel.createAssignments","true")).booleanValue();
            if (createAssignments) {
               List l=SharkEngineManager
                  .getInstance()
                  .getInstancePersistenceManager().getAllAssignmentsForActivity(key,t);
               for (int i=0; i<l.size(); i++) {
                  AssignmentPersistenceInterface po=(AssignmentPersistenceInterface)l.get(i);
                  assignmentResourceIds.add(po.getResourceUsername());
               }
            }
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      return assignmentResourceIds;
   }

   /**
    * Getter for the process of this activity.
    */
   public WfProcessInternal container (SharkTransaction t) throws BaseException {
      return process;
   }

   /**
    * Retrieve the Result map of this activity.
    * @return Map of results from this activity
    */
   public Map result (SharkTransaction t) throws BaseException, ResultNotAvailable {
      Map resultMap=new HashMap();
      //System.out.println("Updating process with the variables "+getChangedContextVariableIds(t));
      Iterator it=resultMap(t).entrySet().iterator();
      while (it.hasNext()) {
         Map.Entry me=(Map.Entry)it.next();
         try {
            resultMap.put(me.getKey(),MiscUtilities.cloneWRD(me.getValue()));
         } catch (Throwable thr) {
            throw new BaseException(thr);
         }
      }
      return resultMap;
   }

   private Map resultMap (SharkTransaction t) throws BaseException {
      Map resultMap=new HashMap();
      //System.out.println("Updating process with the variables "+getChangedContextVariableIds(t));
      Iterator it=getResultVariableIds(t).iterator();
      while (it.hasNext()) {
         java.lang.Object vId=it.next();
         resultMap.put(vId,getContext(t).get(vId));
      }
      return resultMap;
   }

   /**
    * Assign Result for this activity.
    */
   public void set_result (SharkTransaction t,Map results) throws BaseException, InvalidData {
      try {
         setProcessContext(t,results,SharkConstants.EVENT_ACTIVITY_RESULT_CHANGED);
      } catch (InvalidData id) {
         SharkEngineManager.getInstance().getCallbackUtilities().error("Activity"+toString()+" - failed to set the activity result");
         throw id;
      } catch (BaseException be) {
         SharkEngineManager.getInstance().getCallbackUtilities().error("Activity"+toString()+" - failed to set the activity result");
         throw be;
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public void complete (SharkTransaction t) throws BaseException, CannotComplete {
      // get the type of this activity
      int type=getActivityDefinition(t).getActivityType();
      switch (type) {
         case XPDLConstants.ACTIVITY_TYPE_ROUTE: // Route
            throw new BaseException("Subflow activity can be finished only automatically");
            //this.finish(t); // ROUTE goes directly to complete status
         case XPDLConstants.ACTIVITY_TYPE_NO: // NoImplementation
            // NOTE: when using JaWE's code for XPDL handling, we never
            // have this activity type. NO type is representet by
            // TOOL type with zero tools
            this.finish(t); // NO impl goes directly to complete status
            break;
         case XPDLConstants.ACTIVITY_TYPE_TOOL: // Tools
            int hmt=getActivityDefinition(t).getActivityTypes().getImplementation().getImplementationTypes().getTools().size();
            // if there is no tool -> the same as No implementation
            if (hmt>0) {
               if (getActivityDefinition(t).getActivityStartMode()==XPDLConstants.ACTIVITY_MODE_MANUAL) {
                  boolean shouldFinishImmediatelly=getActivityDefinition(t).getActivityFinishMode()==XPDLConstants.ACTIVITY_MODE_AUTOMATIC;
                  try {
                     if (shouldFinishImmediatelly || getAssignmentResourceIds(t).size()>0) {
                        this.runTool(t);
                     }
                  } catch (Exception ex) {
                     if (ex instanceof ToolAgentGeneralException) {
                        toolAgentException=(ToolAgentGeneralException)ex;
                        finishImproperlyAndNotifyProcess(t,
                                                         SharkUtilities.extractExceptionName(toolAgentException));

                        return;
                     } else {
                        throw new BaseException(ex);
                     }
                  }
                  if (shouldFinishImmediatelly || getAssignmentResourceIds(t).size()==0) {
                     finish(t);
                  } else {
                     // delete assignments
                     removeAssignments(t, true, true);
                  }
                  return;
                  // the case when start mode is AUTOMATIC, but finish mode is MANUAL
               } else {
                  finish(t);
               }
               //throw new BaseException("When the start mode is automatic, Tool activity can be finished automatically");
               /*if (getStartMode()==WfActivityImpl.AUTOMATIC_MODE &&
                getFinishMode()==WfActivityImpl.MANUAL_MODE) {
                return;
                }*/
               // check the finish mode
               //this.finish(t); // this should never happen->tool agents should directly call finish()

               //}
            } else {
               this.finish(t); // NO impl goes directly to complete status
            }
            break;
         case XPDLConstants.ACTIVITY_TYPE_SUBFLOW: // SubFlow
            throw new BaseException("Subflow activity can be finished only automatically");
            //this.finish(t); // Subflow act. goes directly to complete status
            //break;
         case XPDLConstants.ACTIVITY_TYPE_BLOCK: // BlockActivity
            throw new BaseException("Block activity can be finished only automatically");
            //this.finish(t); // Block act. goes directly to complete status
            //break;
      }
   }

   /**
    * Complete this activity.
    */
   public void finish (SharkTransaction t) throws BaseException, CannotComplete {
      try {

         // remove assignments first, because method for getting assignments
         // from database depends on activity's state
         removeAssignments(t, true, true);

         change_state(t,SharkConstants.STATE_CLOSED_COMPLETED);
         process.set_process_context(t,resultMap(t));

         process.activity_complete(t,this);

      } catch (InvalidState is) {
         throw new CannotComplete(is);
      } catch (TransitionNotAllowed tna) {
         throw new CannotComplete(tna);
      } catch (InvalidData e) {
         throw new CannotComplete("Invalid result data was passed");
      } catch (ResultNotAvailable rne) {
         throw new CannotComplete("Result of activity is not available");
      } catch (UpdateNotAllowed una) {
         throw new CannotComplete("Process context update is not allowed");
      } catch (Exception ex) {
         if (ex instanceof BaseException) {
            throw (BaseException)ex;
         } else {
            throw new BaseException(ex);
         }
      }
   }

   protected void change_state (SharkTransaction t,String new_state) throws BaseException, InvalidState, TransitionNotAllowed {
      //System.out.println("Act "+this+" Curr st is "+state(t)+", new state is "+new_state+", valid states are "+SharkUtilities.valid_activity_states(state(t)));
      if (!SharkUtilities.valid_activity_states(state(t)).contains(new_state)) {
         throw new TransitionNotAllowed("Current state is "+state+", can't change to state "+new_state+"!");
      }

      // persist changes to activity state
      String oldState=state;
      state=new_state;

      lastStateTime = System.currentTimeMillis();
      try {
         persist(t);
      } catch (TransactionException te) {
         throw new BaseException(te);
      }
      lastStateEventAudit=SharkEngineManager.
         getInstance().
         getObjectFactory().
         createStateEventAuditWrapper(t,
                                      this,
                                      SharkConstants.EVENT_ACTIVITY_STATE_CHANGED,
                                      oldState,
                                      new_state);
      // cancel the limit agent when we enter closed state
      if (state.startsWith(SharkConstants.STATEPREFIX_CLOSED)) {
         LimitAgentManager mgr = SharkEngineManager.getInstance().getLimitAgentManager();
         if (mgr != null) {
            try {
               mgr.notifyStop(processId,key);
            } catch (LimitAgentException e) {
               throw new BaseException(e);
            }
         }
      }

   }

   public void set_process_context (SharkTransaction t,Map new_value) throws BaseException, InvalidData, UpdateNotAllowed {
      try {
         setProcessContext(t,new_value,SharkConstants.EVENT_ACTIVITY_CONTEXT_CHANGED);
      } catch (InvalidData id) {
         throw id;
      } catch (UpdateNotAllowed una) {
         throw una;
      } catch (BaseException be) {
         throw be;
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   private void setProcessContext (SharkTransaction t,Map newValue,String eventType)
      throws BaseException, InvalidData, UpdateNotAllowed, Exception {
      if (newValue==null) {
         throw new BaseException("new value is null");
      }
      Map oldValues=new HashMap();
      Map newChanged=new HashMap();
      Iterator it=newValue.entrySet().iterator();
      while (it.hasNext()) {
         Map.Entry me=(Map.Entry)it.next();
         String id=(String)me.getKey();
         Object val= me.getValue();

         //if (val==null) continue;
         if (getContext(t).containsKey(id)) {
            Object oldVal=getContext(t).get(id);
            //type checking
            if (SharkUtilities.checkDataType(t,getProcessDefinition(t),id,oldVal,val)) {
               //System.out.println("var "+id+"["+oldVal+","+val+"]");
               if ((oldVal!=null && !oldVal.equals(val)) || (oldVal==null && val!=null)) {
                  oldValues.put(id,oldVal);
                  newChanged.put(id,val);
               }
            } else {
               throw new InvalidData("Invalid data type for activity variable "+id);
            }
         } else {
            //System.err.println("The variable "+id+" is not in the context");
            throw new UpdateNotAllowed("Context attribute "+id+" does not exist in the activity context - adding new attributes to activity context is not allowed");
         }
      }
      if (newChanged.size()>0 || eventType.equals(SharkConstants.EVENT_ACTIVITY_RESULT_CHANGED)) {
         getContext(t).putAll(newChanged);
         Map toPersist=new HashMap(newChanged);
         Map newSRVars=null;
         if (eventType.equals(SharkConstants.EVENT_ACTIVITY_RESULT_CHANGED)) {
            newSRVars=new HashMap();
            Set oldRVIds=new HashSet(getResultVariableIds(t));
            getResultVariableIds(t).addAll(newValue.keySet());
            Set newRVIds=new HashSet(getResultVariableIds(t));
            newRVIds.removeAll(oldRVIds);
            Iterator itRV=newRVIds.iterator();
            while (itRV.hasNext()) {
               String id=(String)itRV.next();
               Object val=getContext(t).get(id);
               toPersist.put(id,val);
               newSRVars.put(id,val);
            }
         }

         variableIdsToPersist.addAll(toPersist.keySet());
         persistActivityContext(t);
         if (newChanged.size()>0) {
            if (SharkEngineManager.getInstance().getEventAuditManager()!=null) {
               boolean persistOldEventAuditData=new Boolean(               
                     SharkEngineManager
                        .getInstance()
                        .getCallbackUtilities()
                        .getProperty("PERSIST_OLD_EVENT_AUDIT_DATA","true")).booleanValue();
               if (!persistOldEventAuditData) {
                  oldValues=null;
               }
               SharkEngineManager.getInstance().getObjectFactory().
                  createDataEventAuditWrapper(t,this,SharkConstants.EVENT_ACTIVITY_CONTEXT_CHANGED,oldValues,newChanged);
            }
         }
         if (newSRVars!=null && newSRVars.size()>0) {
            if (SharkEngineManager.getInstance().getEventAuditManager()!=null) {
               boolean persistOldEventAuditData=new Boolean(               
                     SharkEngineManager
                        .getInstance()
                        .getCallbackUtilities()
                        .getProperty("PERSIST_OLD_EVENT_AUDIT_DATA","true")).booleanValue();
               if (!persistOldEventAuditData) {
                  oldValues=null;
               }
               SharkEngineManager.getInstance().getObjectFactory().
                  createDataEventAuditWrapper(t,this,eventType,null,newSRVars);
            }
         }
      }
   }

   /**
    * Resume this process or activity.
    */
   public void resume (SharkTransaction t) throws BaseException, CannotResume, NotSuspended {
      int type=getActivityDefinition(t).getActivityType();

      //      try {
      if (!state(t).equals(SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED)) {
         throw new NotSuspended("Can't resume activity that is not suspended");
      }
      if (process.state(t).equals(SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED)) {
         throw new CannotResume("Can't resume activity which process is suspended");
      }
      if (blockActivityId!=null && block_activity(t).state(t).equals(SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED)) {
         throw new CannotResume("Can't resume activity which block is suspended");
      }

      try {
         if (accepted || type==XPDLConstants.ACTIVITY_TYPE_BLOCK || type==XPDLConstants.ACTIVITY_TYPE_SUBFLOW) {
            change_state(t,SharkConstants.STATE_OPEN_RUNNING);
         } else {
            change_state(t,SharkConstants.STATE_OPEN_NOT_RUNNING_NOT_STARTED);
         }
      } catch (Exception ex) {
         throw new CannotResume(ex);
      }

      if (type==XPDLConstants.ACTIVITY_TYPE_SUBFLOW) {
         // if this is a subflow activity, resume it's process
         // if it is SYNCHRONOUS
         if (isSubflowSynchronous) {
            WfProcessInternal performer=getPerformer(t);
            if (performer==null) {
               SubFlow subflow=getActivityDefinition(t).getActivityTypes().getImplementation().getImplementationTypes().getSubFlow();
               String refSbflw=subflow.getId();
               WorkflowProcess wp=SharkUtilities.getWorkflowProcess(subflow,refSbflw);
               if (wp!=null || performerId!=null) {
                  throw new BaseException("Null performer of sync. subflow activity");
               }

               try {
                  SharkEngineManager.getInstance().getWfEngineInteroperabilityMgr().resume(t,performerId,processId,SharkUtilities.createAssignmentKey(key,getResourceRequesterUsername(t)));
               } catch (Exception ex) {
                  throw new BaseException(ex);
               }

            } else {
               if (performer.state(t).equals(SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED)) {
                  performer.resume(t);
               }
            }
         }
      } else if (type==XPDLConstants.ACTIVITY_TYPE_BLOCK) {
         List actActs=process.getAllActiveActivitiesForBlockActivity(t,key);
         Iterator it=actActs.iterator();
         while (it.hasNext()) {
            WfActivityInternal act=(WfActivityInternal)it.next();
            if (act.state(t).equals(SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED)) {
               act.resume(t);
            }
         }
      }

   }

   /**
    * Suspend this process or activity.
    */
   public void suspend(SharkTransaction t) throws BaseException, CannotSuspend, NotRunning, AlreadySuspended {
      if (state(t).equals(SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED)) {
         throw new AlreadySuspended("The activity is already suspended - can't suspend it twice!");
      }

      try {
         change_state(t,SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED);
      } catch (Exception ex) {
         throw new CannotSuspend(ex);
      }


      int type=getActivityDefinition(t).getActivityType();
      if (type==XPDLConstants.ACTIVITY_TYPE_SUBFLOW) {
         // if this is a subflow activity, suspend it's process
         // if it is SYNCHRONOUS
         if (isSubflowSynchronous) {
            WfProcessInternal performer=getPerformer(t);
            if (performer==null) {
               SubFlow subflow=getActivityDefinition(t).getActivityTypes().getImplementation().getImplementationTypes().getSubFlow();
               String refSbflw=subflow.getId();
               WorkflowProcess wp=SharkUtilities.getWorkflowProcess(subflow,refSbflw);
               if (wp!=null || performerId!=null) {
                  throw new BaseException("Null performer of sync. subflow activity");
               }

               try {
                  SharkEngineManager.getInstance().getWfEngineInteroperabilityMgr().suspend(t,performerId,processId,SharkUtilities.createAssignmentKey(key,getResourceRequesterUsername(t)));
               } catch (Exception ex) {
                  throw new BaseException(ex);
               }


            } else {
               String perfState=performer.state(t);
               if (perfState.startsWith(SharkConstants.STATEPREFIX_OPEN) &&
                   !perfState.equals(SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED)) {
                  performer.suspend(t);
               }
            }
         }
      } else if (type==XPDLConstants.ACTIVITY_TYPE_BLOCK) {
         List actActs=process.getAllActiveActivitiesForBlockActivity(t,key);
         Iterator it=actActs.iterator();
         while (it.hasNext()) {
            WfActivityInternal act=(WfActivityInternal)it.next();
            String actState=act.state(t);
            if (actState.startsWith(SharkConstants.STATEPREFIX_OPEN) &&
                !actState.equals(SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED)) {
               act.suspend(t);
            }
         }
      }

   }

   /**
    * Terminate this process or activity.
    */
   public void terminateFromProcess (SharkTransaction t) throws BaseException, CannotStop, NotRunning {
      terminateActivity(t,true);
   }
   /**
    * Terminate this process or activity.
    */
   public void terminate(SharkTransaction t) throws BaseException, CannotStop, NotRunning {
      terminateActivity(t,false);
   }

   protected void terminateActivity (SharkTransaction t,boolean fromProcess) throws BaseException, CannotStop, NotRunning {
      String stateStr = SharkConstants.STATE_CLOSED_TERMINATED;
      if (!state(t).startsWith(SharkConstants.STATEPREFIX_OPEN)) {
         throw new CannotStop("The activity is already in the closed state - can't terminate it!");
      }

      // remove assignments first, because method for getting assignments
      // from database depends on activity's state
      removeAssignments(t, true, true);

      try {
         change_state(t,stateStr);
      }catch (Exception ex) {
         throw new CannotStop(ex);
      }

      int type=getActivityDefinition(t).getActivityType();
      if (type==XPDLConstants.ACTIVITY_TYPE_SUBFLOW) {
         // if this is a subflow activity, terminate it's process
         // if it is SYNCHRONOUS
         if (isSubflowSynchronous) {
            WfProcessInternal performer=getPerformer(t);
            if (performer==null) {
               SubFlow subflow=getActivityDefinition(t).getActivityTypes().getImplementation().getImplementationTypes().getSubFlow();
               String refSbflw=subflow.getId();
               WorkflowProcess wp=SharkUtilities.getWorkflowProcess(subflow,refSbflw);
               if (wp!=null || performerId!=null) {
                  throw new BaseException("Null performer of sync. subflow activity");
               }

               try {
                  SharkEngineManager.getInstance().getWfEngineInteroperabilityMgr().terminate(t,performerId,processId,SharkUtilities.createAssignmentKey(key,getResourceRequesterUsername(t)));
               } catch (Exception ex) {
                  throw new BaseException(ex);
               }

            } else {
               if (performer.state(t).startsWith(SharkConstants.STATEPREFIX_OPEN)) {
                  performer.terminateFromActivity(t);
               }
            }
         }
      } else if (type==XPDLConstants.ACTIVITY_TYPE_BLOCK) {
         List actActs=process.getAllActiveActivitiesForBlockActivity(t,key);
         Iterator it=actActs.iterator();
         while (it.hasNext()) {
            WfActivityInternal act=(WfActivityInternal)it.next();
            if (act.state(t).startsWith(SharkConstants.STATEPREFIX_OPEN)) {
               act.terminateFromProcess(t);
            }
         }
      }

      if (!fromProcess) {
         try {
            process.activity_terminate(t,this);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
   }

   public void abortFromProcess (SharkTransaction t)  throws BaseException, CannotStop, NotRunning {
      abortActivity(t,true);
   }

   /**
    * Abort the execution of this process or activity.
    */
   public void abort(SharkTransaction t) throws BaseException, CannotStop, NotRunning {
      abortActivity(t,false);
   }

   protected void abortActivity (SharkTransaction t,boolean fromProcess) throws BaseException, CannotStop, NotRunning {
      String stateStr = SharkConstants.STATE_CLOSED_ABORTED;
      if (!state(t).startsWith(SharkConstants.STATEPREFIX_OPEN)) {
         throw new CannotStop("The activity is already in the closed state - can't abort it!");
      }

      // remove assignments first, because method for getting assignments
      // from database depends on activity's state
      removeAssignments(t, true, true);

      try {
         change_state(t,stateStr);
      } catch (Exception ex) {
         throw new CannotStop(ex);
      }

      int type=getActivityDefinition(t).getActivityType();
      if (type==XPDLConstants.ACTIVITY_TYPE_SUBFLOW) {
         // if this is a subflow activity, abort it's process
         // if it is SYNCHRONOUS
         if (isSubflowSynchronous) {
            WfProcessInternal performer=getPerformer(t);
            if (performer==null) {
               SubFlow subflow=getActivityDefinition(t).getActivityTypes().getImplementation().getImplementationTypes().getSubFlow();
               String refSbflw=subflow.getId();
               WorkflowProcess wp=SharkUtilities.getWorkflowProcess(subflow,refSbflw);
               if (wp!=null || performerId!=null) {
                  throw new BaseException("Null performer of sync. subflow activity");
               }

               try {
                  SharkEngineManager.getInstance().getWfEngineInteroperabilityMgr().abort(t,performerId,processId,SharkUtilities.createAssignmentKey(key,getResourceRequesterUsername(t)));
               } catch (Exception ex) {
                  throw new BaseException(ex);
               }

            } else {
               if (performer.state(t).startsWith(SharkConstants.STATEPREFIX_OPEN)) {
                  performer.abortFromActivity(t);
               }
            }
         }
      } else if (type==XPDLConstants.ACTIVITY_TYPE_BLOCK) {
         List actActs=process.getAllActiveActivitiesForBlockActivity(t,key);
         Iterator it=actActs.iterator();
         while (it.hasNext()) {
            WfActivityInternal act=(WfActivityInternal)it.next();
            if (act.state(t).startsWith(SharkConstants.STATEPREFIX_OPEN)) {
               act.abortFromProcess(t);
            }
         }
      }

      if (!fromProcess) {
         try {
            process.activity_abort(t,this);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
   }

   /**
    * Receives notice of event status changes. This is called when some
    * subflow process has finished its execution.
    */
   public void receive_event (SharkTransaction t,WfEventAudit event,WfProcessInternal performer) throws BaseException,InvalidPerformer {
      //System.out.println("Act "+this+" receives event from process "+performer+", ms="+state);
      Activity aDef=getActivityDefinition(t);
      SubFlow subflow=aDef.getActivityTypes().getImplementation().getImplementationTypes().getSubFlow();

      if (!isSubflowSynchronous) return;

      try {
         if (performer!=null) {
            if (performer.state(t).equals(SharkConstants.STATE_CLOSED_COMPLETED) &&
                !state(t).startsWith(SharkConstants.STATEPREFIX_CLOSED)) {
               //if (event.event_type().equals(SharkConstants.EVENT_PROCESS_STATE_CHANGED) &&
               // The Ids of variables of result from the subflow process must be
               // corrected to correspond to the actual param variables
               // In the following map are only OUT and INOUT formal params of
               // subflow process
               Map rm=performer.result(t);
               // This is a subflow activity, so it's context consists
               // of actual parameters to be passed to the referenced
               // process in exact order. The Ids of the passed values
               // are not the same as the Ids used internally by the subprocess
               // formal parameters, so we get appropriate ids by getting the
               // process context param at the appropriate place which must be
               // determined from matching activities actual parameter order
               // with the subflow process formal parameter order

               // Get actual parameters definition of the subflow activity

               Iterator actualParameters = subflow.getActualParameters().toElements().iterator();
               // make a list of actual parameter Ids
               List originalIds=new ArrayList();
               while(actualParameters.hasNext()){
                  ActualParameter ap=(ActualParameter)actualParameters.next();
                  String apId=ap.toValue();
                  originalIds.add(apId);
               }

               Map updatedContext=new HashMap();
               WorkflowProcess wp=SharkUtilities
                  .getWorkflowProcess(performer.package_id(t),
                                      performer.manager_version(t),
                                      performer.process_definition_id(t));
               Iterator it=rm.entrySet().iterator();
               while (it.hasNext()) {
                  Map.Entry me=(Map.Entry)it.next();
                  String fpId=(String)me.getKey();
                  int index=0;
                  int foundIndex=-1;
                  // find the index of formal parameter by searching for it's Id
                  Iterator fps=wp.getFormalParameters().toElements().iterator();
                  while (fps.hasNext()) {
                     FormalParameter fp=(FormalParameter)fps.next();
                     if (fpId.equals(fp.getId())) {
                        foundIndex=index;
                        break;
                     }
                     index++;
                  }
                  // if formal parameter is found, put the appropriate actual param
                  // name and formal param value into new context
                  if (foundIndex!=-1) {
                     java.lang.Object apId=originalIds.get(foundIndex);
                     updatedContext.put(apId,me.getValue());
                  }
               }
               set_result(t,updatedContext);
               finish(t);
            }
         } else {
            if (performerId==null) {
               throw new BaseException("This is not remote subflow activity!");
            }
            if (event instanceof WfDataEventAudit) {
               WfDataEventAudit dea=(WfDataEventAudit)event;
               Map res=dea.new_data();
               // TODO: in the future, we'll need another call to wfxml API, to get
               // info about variables to update
               res = SharkEngineManager.getInstance()
                  .getWfEngineInteroperabilityMgr()
                  .parseOutParams(t,
                                  processId,
                                  SharkUtilities.createAssignmentKey(key,
                                                                     getResourceRequesterUsername(t)),
                                  res,
                                  container(t).manager(t)
                                     .context_signature(t));
               set_result(t,res);
            }
            finish(t);
         }
      } catch (Exception ex) {
         SharkEngineManager.getInstance().getCallbackUtilities().error("Activity"+toString()+" - problems when receiving finishing event of subprocess");
         //System.err.println(ex.getMessage());
         //ex.printStackTrace();
         if (ex instanceof BaseException) {
            throw (BaseException)ex;
         } else {
            throw new BaseException(ex);
         }
      }
   }

   public final String activity_set_definition_id(SharkTransaction t) throws BaseException {
      return activitySetDefinitionId;
   }

   public final String activity_definition_id(SharkTransaction t) throws BaseException {
      return activityDefinitionId;
   }

   public final String block_activity_id (SharkTransaction t) throws BaseException {
      return blockActivityId;
   }

   public WfActivityInternal block_activity(SharkTransaction t) throws BaseException {
      if (blockActivity==null && blockActivityId!=null) {
         blockActivity=process.getActiveActivity(t,blockActivityId);
         if (blockActivity==null) {
            blockActivity=process.getActivity(t,blockActivityId);
         }
      }
      return blockActivity;
   }

   public final String manager_name (SharkTransaction t) throws BaseException {
      return mgrName;
   }

   public final String process_id (SharkTransaction t) throws BaseException {
      return processId;
   }


   /**
    * Returns the context of the given activity. Currently, it returns the whole
    * process context for the activities which type is not Route or Block.
    */
   private Map getActivityContext (SharkTransaction t) throws BaseException {
      // must be linked map to preserve the exact order of parameters,
      // which is important for subflow, and tool activities
      int type=getActivityDefinition(t).getActivityType();
      // set the needed process context to activity context
      if (type!=XPDLConstants.ACTIVITY_TYPE_ROUTE && type!=XPDLConstants.ACTIVITY_TYPE_BLOCK) {
         return process.process_context(t);
      } else {
         return new HashMap();
      }
   }

   // TODO: what to do with assignments that are already accepted???
   public void reevaluateAssignments (SharkTransaction t) throws BaseException {
      if (accepted) return;
      int ls=getAssignmentResourceIds(t).size();
      removeAssignments(t, true, true);
      /*
      Iterator it=l.iterator();
      while (it.hasNext()) {
         String username=(String)it.next();
         WfAssignmentInternal ass=SharkUtilities.getAssignment(t,processId,key,username);
         try {
            ass.delete(t);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }*/

      if (ls==0) return; // the case when activity start and finish mode is MANUAL
      // but activity has tool to execute (it is waiting for
      // some client to finish her

      assignmentResourceIds.clear();
      createAssignments(t);
   }

   protected void createAssignments(SharkTransaction t) throws BaseException {
      boolean createAssignments=Boolean.valueOf(
            SharkEngineManager.
            getInstance().
            getCallbackUtilities().
            getProperty("SharkKernel.createAssignments","true")).booleanValue();
      if (!createAssignments) return;

      int type=getActivityDefinition(t).getActivityType();
      if (!(type==XPDLConstants.ACTIVITY_TYPE_NO || type==XPDLConstants.ACTIVITY_TYPE_TOOL)) {
         return;
      }

      Participant p=findParticipant(t,getActivityDefinition(t).getPerformer());
      PerformerData xpdlParticipant=null;
      List xpdlResponsibleParticipants=null;

      xpdlParticipant=checkParticipant(t,p,type);

      if (xpdlParticipant==null) return;

      Set performers=findResources(t,p);

      // collect all responsibles of the activity
      Set responsibles=new HashSet();
      List resps=XMLUtil.getResponsibles(getProcessDefinition(t));
      Iterator it=resps.iterator();
      while (it.hasNext()) {
         if (xpdlResponsibleParticipants==null) {
            xpdlResponsibleParticipants=new ArrayList();
         }
         Responsible resp=(Responsible)it.next();
         // if responsible is not unresolved expression, this will return Participant object
         p=findParticipant(t,resp.toValue());

         PerformerData pd=checkParticipant(t,p,type);
         if (pd!=null) {
            xpdlResponsibleParticipants.add(pd);
            responsibles.addAll(findResources(t,p));
         }
      }

      List secUsers=null;

      try {
         List users=SharkUtilities.getAssignments(t,
                                                  SharkEngineManager.getInstance().getCallbackUtilities().getProperty("enginename",""),
                                                  processId,
                                                  key,
                                                  new ArrayList(performers),
                                                  new ArrayList(responsibles),
                                                  getResourceRequesterUsername(t),
                                                  xpdlParticipant,
                                                  xpdlResponsibleParticipants);
         if (users.size()==0) {
            users.add(getResourceRequesterUsername(t));
         }

         secUsers=SharkUtilities.getSecureAssignments(t,
                                                      SharkEngineManager.getInstance().getCallbackUtilities().getProperty("enginename",""),
                                                      processId,
                                                      key,
                                                      users);
      } catch (RootException ex) {
         throw new BaseException(ex);
      }

      Iterator resourcesIt=secUsers.iterator();
      while (resourcesIt.hasNext()) {
         String username=(String)resourcesIt.next();
         WfResourceInternal wr=SharkUtilities.getResource(t,username);
         if (wr==null) {
            try {
               wr=SharkEngineManager
                  .getInstance()
                  .getObjectFactory()
                  .createResource(t,username);
            } catch (Exception ex) {
               throw new BaseException(ex);
            }
         }
         WfAssignmentInternal ass=SharkEngineManager.getInstance().getObjectFactory().createAssignment(t,this,wr);
         wr.addAssignment(t,ass);
         assignmentResourceIds.add(username);
      }

   }

   protected PerformerData checkParticipant (SharkTransaction t,Participant p,int activityType) throws BaseException {
      if (p!=null) {
         // check if participant is SYSTEM, and if it is, do not create PerformerData,
         // which will indicate not to create any assignments
         String participantType=p.getParticipantType().getType();
         if (participantType.equals(XPDLConstants.PARTICIPANT_TYPE_SYSTEM)) {
            return null;
         }
         String pDefId=null;
         if (p.getParent().getParent() instanceof WorkflowProcess) {
            pDefId=((WorkflowProcess)p.getParent().getParent()).getId();
         }
         return new PerformerData(XMLUtil.getPackage(p).getId(),
                                  pDefId,
                                  p.getId(),
                                  false,
                                  participantType);
      } else {
         String performerExpr=getActivityDefinition(t).getPerformer();
         // check if this is a tool activity with an empty performer, and if it
         // is, do not create PerformerData, which will indicate not to create any assignments
         if (performerExpr.trim().length()==0 &&
             activityType==XPDLConstants.ACTIVITY_TYPE_TOOL &&
             getActivityDefinition(t).getActivityTypes().getImplementation().getImplementationTypes().getTools().size()>0) {
            return null;
         }

         try {
            // if participant is an expression, calculate an expression
            performerExpr=evaluateParticipantExpression(t,performerExpr);
         } catch (Exception ex) {}

         return new PerformerData(
            XMLUtil.getPackage(getActivityDefinition(t)).getId(),
            XMLUtil.getWorkflowProcess(getActivityDefinition(t)).getId(),
            performerExpr.trim(),
            true,null);
      }
   }

   protected Participant findParticipant (SharkTransaction t,String performerExpr) throws BaseException {
      Participant p=SharkUtilities.getParticipant(getActivityDefinition(t), performerExpr);
      if (p==null) {
         try {
            // if participant is an expression, calculate an expression, and
            // try to retrieve the participant
            String participantId=evaluateParticipantExpression(t,performerExpr);
            return findParticipant(t,participantId);
         } catch (Exception ex) {}
      }
      return p;
   }

   /**
    * Activates this activity.
    */
   public void activate(SharkTransaction t) throws BaseException, CannotStart, AlreadyRunning {
      // make sure we aren't already running
      if (state(t).equals(SharkConstants.STATE_OPEN_RUNNING)) {
         throw new AlreadyRunning("The activity is already running");
      }

      activatedTime = System.currentTimeMillis();

      // re-evaluate deadlines - the information will be stored into DB
      try {
         reevaluateDeadlines(t);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }

      // call the limit agent manager here - this is the only central point
      // for both automatic and manual activities; start time is set based
      // on the time when activity is activated. The limit does not have
      // any sense for automatic activities in shark's standard impl.
      if (SharkEngineManager.getInstance().getLimitAgentManager()!=null) {
         this.activateLimitAgent(t);
      }

      try {
         persist(t);
         persistActivityContext(t);
         persistDeadlines(t);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }

      createAssignments(t);

      try {
         startActivity(t);
      } catch (ToolAgentGeneralException tage) {
         toolAgentException=tage;
         finishImproperlyAndNotifyProcess(t,
                                          SharkUtilities.extractExceptionName(tage));
      }
      startSubflowThread=null;
   }

   // Starts or activates this automatic activity
   protected void startActivity(SharkTransaction t) throws BaseException, CannotStart, ToolAgentGeneralException {

      // get the type of this activity
      int type=getActivityDefinition(t).getActivityType();
      //log.info("Executing activity " + activity.getId());

      if (type!=XPDLConstants.ACTIVITY_TYPE_NO && type!=XPDLConstants.ACTIVITY_TYPE_TOOL) {
         try {
            change_state(t,SharkConstants.STATE_OPEN_RUNNING);
         } catch (InvalidState is) {
            throw new CannotStart(is.getMessage());
         } catch (TransitionNotAllowed tna) {
            throw new CannotStart(tna.getMessage());
         } catch (BaseException be) {
            throw new CannotStart(be.getMessage());
         }
      }

      // configure the limit agent - this is handled in change_state
      // this is not a point for all activities

      // set the new previousActivity

      // set the estimatedStartDate

      switch (type) {
         case XPDLConstants.ACTIVITY_TYPE_ROUTE: // Route
            try {
               this.finish(t); // ROUTE goes directly to complete status
            } catch (CannotComplete cc) {
               //System.err.println(cc.getMessage());
               throw new CannotStart(cc);
            }
            break;
         case XPDLConstants.ACTIVITY_TYPE_NO: // NoImplementation
            // NOTE: when using JaWE's code for XPDL handling, we never
            // have this activity type. NO type is representet by
            // TOOL type with zero tools
            this.runNo();
            break;
         case XPDLConstants.ACTIVITY_TYPE_TOOL: // Tools
            int hmt=getActivityDefinition(t).getActivityTypes().getImplementation().getImplementationTypes().getTools().size();
            if (hmt>0) {
               if (getActivityDefinition(t).getActivityStartMode()==XPDLConstants.ACTIVITY_MODE_AUTOMATIC) {
                  boolean shouldFinishImmediatelly=getActivityDefinition(t).getActivityFinishMode()==XPDLConstants.ACTIVITY_MODE_AUTOMATIC;
                  this.runTool(t);
                  try {
                     if (shouldFinishImmediatelly) {
                        finish(t);
                     } else {
                        removeAssignments(t, true, true);
                     }
                  } catch (CannotComplete cnc) {
                     throw new BaseException(cnc);
                  }
               }
            } else {
               Participant p=findParticipant(t,getActivityDefinition(t).getPerformer());
               if (p!=null) {
                  String participantType=p.getParticipantType().getType();
                  // THIS SHOULD NEVER HAPPEN - VALIDATOR DOES NOT ALLOW SUCH PROCESSES
                  if (participantType.equals(XPDLConstants.PARTICIPANT_TYPE_SYSTEM)) {
                     try {
                        this.finish(t);
                        return;
                     } catch (CannotComplete cc) {
                        throw new CannotStart(cc);
                     }
                  }
               }
               this.runNo();
            }
            break;
         case XPDLConstants.ACTIVITY_TYPE_SUBFLOW: // SubFlow
            startSubflowThread=Thread.currentThread();
            this.runSubFlow(t); // Begin a sub workflow
            startSubflowThread=null;
            break;
         case XPDLConstants.ACTIVITY_TYPE_BLOCK: // BlockActivity
            this.runBlock(t); // Block activity
            break;
      }
   }

   protected void runNo() throws BaseException, CannotStart {
   }

   // Runs a TOOL activity - there can be 0..n
   protected void runTool(SharkTransaction t) throws BaseException, CannotStart, ToolAgentGeneralException {

      if (!state(t).equals(SharkConstants.STATE_OPEN_RUNNING)) {
         try {
            change_state(t,SharkConstants.STATE_OPEN_RUNNING);
         } catch (InvalidState is) {
            throw new CannotStart(is.getMessage());
         } catch (TransitionNotAllowed tna) {
            throw new CannotStart(tna.getMessage());
         } catch (BaseException be) {
            throw new CannotStart(be.getMessage());
         }
      }
      ToolAgentManager tam=SharkEngineManager.getInstance().getToolAgentManager();
      tam.executeActivity(t,this);
   }

   // Runs a SUBFLOW activity
   protected void runSubFlow(SharkTransaction t) throws BaseException, ToolAgentGeneralException {
      SubFlow subflow=getActivityDefinition(t).getActivityTypes().getImplementation().getImplementationTypes().getSubFlow();
      if (subflow == null) {
         return;
      }

      // Get actual parameters definition of the subflow activity
      ActualParameters aps=subflow.getActualParameters();
      isSubflowSynchronous=getActivityDefinition(t).isSubflowSynchronous();

      String refSbflw=subflow.getId();
      WorkflowProcess wp=SharkUtilities.getWorkflowProcess(subflow,refSbflw);

      String packageId = process.package_id(t);
      String packageVersion = process.manager_version(t);

      if (wp!=null) {
         // start the process
         WfProcessMgrInternal[] mgrs;
         WfProcessMgrInternal mgr;
         WfProcessInternal subProc;
         String sbflwPkgIdWithVersion;
         String sbflwPkgId;
         String sbflwPkgVersion;
         String sbflwProcId;

         sbflwPkgId=XMLUtil.getPackage(wp).getId();
         sbflwPkgVersion=XMLUtil.getPackage(wp).getInternalVersion();
         sbflwProcId=refSbflw;
         String pmgrName=SharkUtilities.createProcessMgrKey(
            sbflwPkgId,
            sbflwPkgVersion,
            sbflwProcId);
         //System.out.println("SPIDWV="+sbflwPkgIdWithVersion+", PMGRN="+pmgrName);
         mgr=SharkUtilities.getProcessMgr(t,pmgrName);
         if (mgr==null) {
            throw new BaseException("Subflow process is not found");
         }
         try {
            subProc=mgr.create_process(t,this);
            if (new Boolean(SharkEngineManager.getInstance().getCallbackUtilities().getProperty("SharkKernel.lockSubProcesses","false")).booleanValue()) {
               SharkEngineManager.getInstance().getLockMaster().lock(t,subProc.key(t));
            }
            subProc.set_name(t,process.name(t)+"-"+this.name(t));
         } catch (Exception ex) {
            SharkEngineManager.getInstance().getCallbackUtilities().error("Activity"+toString()+" - Error instantiating sub-process");
            throw new BaseException(ex);
         }

         FormalParameters fps=
            SharkUtilities
            .getWorkflowProcess(sbflwPkgId,
                                sbflwPkgVersion,
                                sbflwProcId).getFormalParameters();

         Map m=null;
         try {
            m=SharkUtilities.createContextMap(t,
                                              this.getContext(t),
                                              aps,
                                              fps,
                                              packageId,
                                              packageVersion);
            subProc.set_process_context(t,m);
            performerId=subProc.key(t);
            persist(t);
            subProc.start(t);
         } catch (Exception ex) {
            if (ex instanceof ToolAgentGeneralException) {
               throw (ToolAgentGeneralException)ex;
            } else {
               throw new BaseException(ex);
            }
         }
         // this is a remote subflow
      } else {
         try {
            Map m=SharkUtilities.createContextMap(t,
                                                  this.getContext(t),
                                                  aps,
                                                  packageId,
                                                  packageVersion);
            String assId=SharkUtilities.createAssignmentKey(key,getResourceRequesterUsername(t));
            // check if some variable is used to define a reference to remote subflow
            if (this.getContext(t).containsKey(refSbflw)) {
               Object rsv=this.getContext(t).get(refSbflw);
               if (rsv instanceof String) {
                  refSbflw=(String)rsv;
               }               
            }
            performerId=SharkEngineManager.getInstance().getWfEngineInteroperabilityMgr().start(t,refSbflw,processId,assId,isSubflowSynchronous,m);

            persist(t);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      // if this is asynchronous execution, complete activity, otherwise
      // wait until the process calls the receive_event() method
      if (!isSubflowSynchronous) {
         try {
            this.finish(t);
         } catch (Exception ex) {
            //System.err.println(ex.getMessage());
            throw new BaseException(ex);
         }
      }
   }

   // Runs a BLOCK activity
   protected void runBlock(SharkTransaction t) throws BaseException, ToolAgentGeneralException {
   }

   /**
    * Complete this activity.
    */
   protected void finishImproperlyAndNotifyProcess (SharkTransaction t,String excName) throws BaseException {
      try {

         // remove assignments first, because method for getting assignments
         // from database depends on activity's state
         removeAssignments(t, true, true);

         change_state(t,SharkConstants.STATE_CLOSED_TERMINATED);

         this.exceptionName=excName;

         if (toolAgentException==null && excName!=null) {
            int type=getActivityDefinition(t).getActivityType();
            if (type==XPDLConstants.ACTIVITY_TYPE_SUBFLOW) {
               // if this is a subflow activity, terminate it's process
               // if it is SYNCHRONOUS
               if (isSubflowSynchronous) {
                  WfProcessInternal performer=getPerformer(t);
                  if (performer==null) {
                     SubFlow subflow=getActivityDefinition(t).getActivityTypes().getImplementation().getImplementationTypes().getSubFlow();
                     String refSbflw=subflow.getId();
                     WorkflowProcess wp=SharkUtilities.getWorkflowProcess(subflow,refSbflw);
                     if (wp!=null || performerId!=null) {
                        throw new BaseException("Null performer of sync. subflow activity");
                     }

                     SharkEngineManager.getInstance().getWfEngineInteroperabilityMgr().abort(t,performerId,processId,SharkUtilities.createAssignmentKey(key,getResourceRequesterUsername(t)));

                  } else {
                     if (performer.state(t).startsWith(SharkConstants.STATEPREFIX_OPEN)) {
                        performer.terminateFromActivity(t);
                     }
                  }
               }
            } else if (type==XPDLConstants.ACTIVITY_TYPE_BLOCK) {
               List actActs=process.getAllActiveActivitiesForBlockActivity(t,key);
               Iterator it=actActs.iterator();
               while (it.hasNext()) {
                  WfActivityInternal act=(WfActivityInternal)it.next();
                  act.terminateFromProcess(t);
               }
            }
         }

         process.activity_terminate(t,this);


      } catch (Exception ex) {
         if (ex instanceof BaseException) {
            throw (BaseException)ex;
         } else {
            throw new BaseException(ex);
         }
      }
   }

   public void set_accepted_status (SharkTransaction t,boolean accept,String resourceUname) throws BaseException, CannotAcceptSuspended {
      if (state(t).startsWith(SharkConstants.STATEPREFIX_CLOSED)) {
         throw new BaseException("Can't change accepted status - activity state is closed"); // activity is closed
      }
      if (state(t).equals(SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED)) {
         throw new CannotAcceptSuspended("Can't accept or reject suspended activity"); // activity is suspended
      }
      if (this.accepted && accept) {
         throw new BaseException("Someone else already accepted assignment!"); // activity is already accepted
      }

      if (SharkEngineManager.getInstance().getEventAuditManager()!=null) {
         WfResourceInternal res=SharkUtilities.getResource(t,resourceUname);
         SharkEngineManager.getInstance().getObjectFactory().
            createAssignmentEventAuditWrapper(t,this,res,res,accept);
      }
      // accept
      boolean deleteOtherAssignments=Boolean.valueOf(
            SharkEngineManager.
            getInstance().
            getCallbackUtilities().
            getProperty("SharkKernel.deleteOtherAssignments","true")).booleanValue();
            boolean createAssignments=Boolean.valueOf(
               SharkEngineManager.
               getInstance().
               getCallbackUtilities().
               getProperty("SharkKernel.createAssignments","true")).booleanValue();
      
      if (accept) {
         try {
            // the following three lines must come before change_state where peristing happens
            this.accepted=true;
            acceptedTime = System.currentTimeMillis();
            this.resourceUsername=resourceUname;
            if (createAssignments) {
               removeAssignments(t, false, deleteOtherAssignments);
               setAssignmentStatus(t, resourceUsername, true, true);
               WfResourceInternal res=SharkUtilities.getResourceFromCache(t,resourceUsername);
               if (res!=null) {
                  res.restoreAssignment(t, mgrName, processId, key, true);
               }
            }
            change_state(t,SharkConstants.STATE_OPEN_RUNNING);
         } catch (Exception ex) {
            throw new BaseException(ex); //
         }
         /*if (!deleteOtherAssignments) {
            try {
               String curResUsername=resourceUname;
               Iterator itAss = getAssignmentResourceIds(t).iterator();
               while (itAss.hasNext()) {
                  String resUsername=(String)itAss.next();
                  if (!resUsername.equals(curResUsername)) {
                     WfResourceInternal res=SharkUtilities.getResourceFromCache(t,resUsername);
                     if (res!=null) {
                        res.removeAssignment(t,processId,key);
                     }
                  }
               }
            } catch (Exception ex) {
               //ex.printStackTrace();
               throw new BaseException(ex);
            }
         }*/
         // reject
      } else {
         // if the previous state was accepted, create assignments again
         if (this.accepted) {
            // put activity into not_started status, or leave it
            // as it is if activities status is suspended
            //if (!state(t).equals(SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED)) {
               try {
                  change_state(t,SharkConstants.STATE_OPEN_NOT_RUNNING_NOT_STARTED);
               } catch (InvalidState is) {
                  throw new BaseException(is); //
               } catch (TransitionNotAllowed tna) {
                  throw new BaseException(tna); //
               }
            //}
            this.accepted=false;
            this.acceptedTime = Long.MAX_VALUE/2;
            this.resourceUsername=null;
            try {
               if (createAssignments && !deleteOtherAssignments) {
                  String curResUsername=resourceUname;
                  Iterator it=getAssignmentResourceIds(t).iterator();
                  while (it.hasNext()) {
                     String resUsername=(String)it.next();
                     setAssignmentStatus(t, resUsername, true, false);
                     WfResourceInternal res=SharkUtilities.getResourceFromCache(t,resUsername);
                     if (res!=null) {
                        res.restoreAssignment(t,mgrName,processId,key,false);
                     }
                  }
               }
               persist(t);
            } catch (Exception ex) {
               throw new BaseException(ex);
            }
            if (createAssignments && deleteOtherAssignments) {
               reevaluateAssignments(t);
            }
         }
      }
   }

   public final String getResourceUsername (SharkTransaction t) throws BaseException {
      return resourceUsername;
   }

   protected Activity getActivityDefinition (SharkTransaction t) throws BaseException {
      if (activityDefinition==null) {
         activityDefinition=
            SharkUtilities.getActivityDefinition(t,this,getProcessDefinition(t),block_activity(t));
      }
      return activityDefinition;
   }

   protected WorkflowProcess getProcessDefinition (SharkTransaction t) throws BaseException {
      if (processDefinition==null) {
         String packageId=process.package_id(t);
         String packageVer=process.manager_version(t);
         String pDefId=process.process_definition_id(t);
         processDefinition=SharkUtilities.getWorkflowProcess(packageId,packageVer,pDefId);
      }
      return processDefinition;
   }

   public String toString () {
      return "[Id="+key+", ba="+blockActivityId+", ActDefId="+activityDefinitionId+"]";
   }

   private void setActivityVariables (SharkTransaction t) throws BaseException {
      try {
         //System.out.println("SPVCVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV");
         // initialize context, just to keep the order of variables
         //initializeActivityContext(t);
         activitiesProcessContext=new HashMap();
         resultVariableIds=new HashSet();
   
         int type=getActivityDefinition(t).getActivityType();
         // ROUTE AND BLOCK Activities do not have a context
         if (type==XPDLConstants.ACTIVITY_TYPE_ROUTE || type==XPDLConstants.ACTIVITY_TYPE_BLOCK) {
            return;
         }
         
         WorkflowProcess wp=getProcessDefinition(t);
         List l=new ArrayList(wp.getAllVariables().values());

         if (l.size()==0) return;
         
         PersistentManagerInterface ipm = SharkEngineManager.getInstance()
         .getInstancePersistenceManager();

         //System.err.println("INIT PC="+processContext);
         Iterator it=l.iterator();
         List variableIds=new ArrayList();
         while (it.hasNext()) {
            XMLCollectionElement dfOrFp=(XMLCollectionElement)it.next();
            String vdId=dfOrFp.getId();         
            variableIds.add(vdId);
         }
         l=ipm.getActivityVariables(key, variableIds, t);
         it=l.iterator();
         while (it.hasNext()) {
            ActivityVariablePersistenceInterface var=(ActivityVariablePersistenceInterface)it.next();
            String vdId=var.getDefinitionId();
            Object val=var.getValue();
            //System.out.println("Restoring act var "+vdId+", val="+val);
            //if (anyVal!=null) {
            activitiesProcessContext.put(vdId,val);
            if (var.isResultVariable()) {
               resultVariableIds.add(vdId);
            }
            //
         }
         //System.err.println("REFILLED AC="+activitiesProcessContext);
      } catch (Exception ex) {
         throw new BaseException ("Restoring of process context failed!",ex);
      }
   }      
      
   /**
    * It is assumed that there can't be two or more
    * activities having the same key.
    */
   public boolean equals (java.lang.Object obj) {
      if (!(obj instanceof WfActivityImpl)) return false;
      return ((WfActivityImpl)obj).key.equals(key);
   }

   protected String evaluateParticipantExpression(SharkTransaction t,String expr) throws Exception {
      if (expr==null || expr.trim().length()==0) throw new Exception("Improper participant expression!");
      // use process_context - these are cloned variables
      return evaluator(t).evaluateExpression(t,expr.trim(),process_context(t),java.lang.String.class).toString();
   }

   private Evaluator evaluator (SharkTransaction t) throws Exception {
      if (evaluator==null) {
         evaluator=SharkEngineManager
            .getInstance()
            .getScriptingManager()
            .getEvaluator(t,SharkUtilities.getScriptType(
                             process.package_id(t),
                             process.manager_version(t)));
      }
      return evaluator;
   }

   private WfProcessInternal getPerformer (SharkTransaction t) throws BaseException {
      if (performerId!=null) {
         return SharkUtilities.getProcess(t,performerId);
      }
      return null;
   }

   public String getResourceRequesterUsername(SharkTransaction t) throws BaseException {
      return process.requester(t).getResourceRequesterUsername(t);
   }

   public WfRequester getExternalRequester (SharkTransaction t) throws BaseException {
      return null;
   }

   public final boolean accepted_status (SharkTransaction t) throws BaseException {
      return accepted;
   }

   public void persist (SharkTransaction t) throws TransactionException {
      try {
         //System.err.println("The act "+this+" is being persisted with thread "+Thread.currentThread());
         //System.err.println("      The act "+key+" is being persisted:");
         PersistentManagerInterface pmi=SharkEngineManager
            .getInstance()
            .getInstancePersistenceManager();

         pmi.persist(createAndFillPersistentObject(), this.justCreated, t);
         //persistActivityContext(t);
         this.justCreated=false;
         // delete assignments if needed
         /*if (deleteAssignments) {
          Iterator aRes=getAssignmentResourceIds(t).iterator();
          while (aRes.hasNext()) {
          String resKey=(String)aRes.next();
          //System.err.println("         The act "+key+" is deleting its assignment for resource "+resKey);
          pmi.deleteAssignment(key,resKey,t.getSharkTransaction());
          }
          deleteAssignments=false;
          }*/

         /*if (deadlinesInfo!=null && deadlinesInfo.size()>0) {
          persistDeadlines(t);
          }*/
      } catch (Exception pe) {
         throw new TransactionException(pe);
      }
   }

   /**
    * Method persistActivityContext stores content of the workflow relevant
    * data of this activity, either all of them, or modified only.
    *
    * @param    t                   a  SharkTransaction
    * @exception   BaseException
    */
   protected void persistActivityContext(SharkTransaction t) throws BaseException {
      try {
         if (variableIdsToPersist.size()==0) return;
         PersistentManagerInterface pmgr = SharkEngineManager
            .getInstance()
            .getInstancePersistenceManager();
         //System.out.println("Persisting act var map="+getContext(t));
         Iterator it=getContext(t).entrySet().iterator();
         while (it.hasNext()) {
            Map.Entry me=(Map.Entry)it.next();
            String defId=(String)me.getKey();
            //boolean modifiedOnly = (null != modified);
            //if (!modifiedOnly||(modifiedOnly && modified.contains(defId))) {
            if (variableIdsToPersist.contains(defId)) {
               Object val=me.getValue();
               ActivityVariablePersistenceInterface var = pmgr.createActivityVariable();
               var.setActivityId(key);
               var.setDefinitionId(defId);
               var.setValue(val);
               var.setResultVariable(getResultVariableIds(t).contains(defId));
               pmgr.persist(var, this.justCreatedVariables, t);
               //System.out.println("Persisting activity variable "+defId+", val="+val+" for activity "+key+" of process "+processId);
            }
         }
         variableIdsToPersist.clear();
         this.justCreatedVariables=false;
      } catch (PersistenceException pe) {
         throw new BaseException(pe);
      }
   }

   protected void persistDeadlines (SharkTransaction t) throws BaseException {
      if (deadlinesInfo==null || deadlinesInfo.size()==0) return;
      try {
         PersistentManagerInterface pmgr = SharkEngineManager
            .getInstance()
            .getInstancePersistenceManager();
         Iterator it=deadlinesInfo.iterator();
         while (it.hasNext()) {
            DeadlineInfo dinfo=(DeadlineInfo)it.next();
            DeadlinePersistenceInterface dpi = pmgr.createDeadline();
            dpi.setProcessId(processId);
            dpi.setActivityId(key);
            dpi.setExceptionName(dinfo.exceptionName);
            dpi.setSynchronous(dinfo.isSynchronous);
            dpi.setTimeLimit(dinfo.timeLimit);
            pmgr.persist(dpi,justCreatedDeadlines, t);
         }
         deadlinesInfo.clear();
         this.justCreatedDeadlines=false;
      } catch (PersistenceException pe) {
         throw new BaseException(pe);
      }
   }

   protected void persistExecutedDeadline (String uniqueId,SharkTransaction t) throws BaseException {
      try {
         PersistentManagerInterface pmgr = SharkEngineManager
            .getInstance()
            .getInstancePersistenceManager();
            DeadlinePersistenceInterface dpi = pmgr.createDeadline();
            dpi.setUniqueId(uniqueId);
            dpi.setExecuted(true);
            pmgr.persist(dpi,false, t);
      } catch (PersistenceException pe) {
         throw new BaseException(pe);
      }
   }

   public void delete (SharkTransaction t) throws TransactionException {
      //try {System.err.println("Deleting activity "+key());}catch (Exception ex){};
      try {
         SharkEngineManager
            .getInstance()
            .getInstancePersistenceManager()
            .deleteActivity(key,t);
      } catch (Exception ex) {
         throw new TransactionException("Exception while deleting activity",ex);
      }
   }

   protected XMLCollectionElement getXPDLObject (SharkTransaction t) throws BaseException {
      return getActivityDefinition(t);
   }

   public Map getContext (SharkTransaction t) throws BaseException {
      if (activitiesProcessContext==null) {
         setActivityVariables(t);
      }
      return activitiesProcessContext;
   }

   private Set getResultVariableIds (SharkTransaction t) throws BaseException {
      if (resultVariableIds==null) {
         setActivityVariables(t);
      }
      return resultVariableIds;
   }

   private ActivityPersistenceInterface createAndFillPersistentObject () {
      ActivityPersistenceInterface po =
         SharkEngineManager
         .getInstance()
         .getInstancePersistenceManager()
         .createActivity();
      fillPersistentObject(po);
      return po;
   }

   private void fillPersistentObject (ActivityPersistenceInterface po) {
      po.setId(this.key);
      po.setActivitySetDefinitionId(this.activitySetDefinitionId);
      po.setActivityDefinitionId(this.activityDefinitionId);
      po.setProcessMgrName(this.mgrName);
      po.setProcessId(this.processId);
      po.setResourceUsername(this.resourceUsername);
      po.setSubflowProcessId(this.performerId);
      po.setSubflowAsynchronous(!this.isSubflowSynchronous);
      po.setState(this.state);
      po.setBlockActivityId(this.blockActivityId);
      po.setName(this.name);
      po.setDescription(this.description);
      po.setPriority(this.priority);
      po.setAcceptedTime(this.acceptedTime);
      po.setActivatedTime(this.activatedTime);
      po.setLastStateTime(this.lastStateTime);
      po.setLimitTime(limitTime);      
   }

   private void restore (ActivityPersistenceInterface po) {
      this.key=po.getId();
      this.activitySetDefinitionId=po.getActivitySetDefinitionId();
      this.activityDefinitionId=po.getActivityDefinitionId();
      this.mgrName=po.getProcessMgrName();
      this.processId=po.getProcessId();
      this.resourceUsername=po.getResourceUsername();
      if (this.resourceUsername==null) {
         accepted=false;
      } else {
         accepted=true;
      }
      this.performerId=po.getSubflowProcessId();
      this.isSubflowSynchronous=!po.isSubflowAsynchronous();
      this.state=po.getState();
      this.blockActivityId=po.getBlockActivityId();
      this.name=po.getName();
      this.description=po.getDescription();
      this.priority=po.getPriority();
      this.acceptedTime = po.getAcceptedTime();
      this.activatedTime = po.getActivatedTime();
      this.lastStateTime = po.getLastStateTime();
      this.limitTime=po.getLimitTime();
   }

   /**
    * Return a resource Ids for the specified participant.
    * @return A set of resource mapping for given participant.
    */
   protected Set findResources (SharkTransaction t,Participant p) throws BaseException {
      Set ress=new HashSet();
      if (p==null) return ress;
      String participantId=p.getId();
      XMLComplexElement cOwn=(XMLComplexElement)p.getParent().getParent();
      boolean isProcessParticipant=(cOwn instanceof WorkflowProcess);
      Set usernames=new HashSet();

      // We can live without mapping manager
      List pMappings=new ArrayList();
      ParticipantMappingManager pms = SharkEngineManager.
         getInstance().
         getParticipantMapPersistenceManager();
      if (null != pms) {
         ParticipantMappingTransaction trans = null;
         try {
            trans = SharkUtilities.createParticipantMappingTransaction();
            String pkgId=XMLUtil.getPackage(p).getId();
            String pDefId=((isProcessParticipant)? cOwn.get("Id").toValue() : null);
            pMappings=pms.getParticipantMappings(trans,pkgId,pDefId,participantId);
            //SharkUtilities.commitMappingTransaction(trans);
         } catch (RootException ex){
            //SharkUtilities.rollbackMappingTransaction(trans);
            SharkEngineManager
               .getInstance()
               .getCallbackUtilities()
               .error("Error in WfActivityImpl.findResources()", ex);
            throw new BaseException(ex);
         } finally {
            SharkUtilities.releaseMappingTransaction(trans);
         }
      }
      // expanding user groups
      Iterator it=pMappings.iterator();
      UserGroupManager ugm=SharkEngineManager.
         getInstance().
         getUserGroupManager();
      // We can live without usergroup manager
      if (ugm!=null) {
         UserTransaction userTrans = null;
         try {
            userTrans = SharkUtilities.createUserTransaction();

            while (it.hasNext()) {
               ParticipantMap pm=(ParticipantMap)it.next();
               String uname=pm.getUsername();
               if (pm.getIsGroupUser()) {
                  List unames=ugm.getAllUsers(userTrans,uname);
                  //System.out.println("Expanded uname "+uname+" is "+unames);
                  usernames.addAll(unames);
               } else {
                  usernames.add(uname);
               }
            }
            //SharkUtilities.commitUserTransaction(userTrans);
         } catch (RootException ex){
            SharkEngineManager
               .getInstance()
               .getCallbackUtilities()
               .error("Error in WfActivityImpl.findResources() : ", ex);
            throw new BaseException(ex);
            //SharkUtilities.rollbackUserTransaction(userTrans);
         } finally {
            SharkUtilities.releaseUserTransaction(userTrans);
         }
      }
      return usernames;
   }

   public final String getPerformerId (SharkTransaction t) {
      return performerId;
   }

   public final boolean isPerformerSynchronous (SharkTransaction t) {
      return isSubflowSynchronous;
   }

   public final long getCreationTime (SharkTransaction t) throws BaseException {
      return activatedTime;
   }

   public final long getStartTime (SharkTransaction t) throws BaseException {
      return acceptedTime;
   }

   public void updateAssignmentResourceIds (SharkTransaction t,String oldResUname,String newResUname) throws BaseException {
      if (assignmentResourceIds!=null) {
         assignmentResourceIds.remove(oldResUname);
         if (!assignmentResourceIds.contains(newResUname)) {
            assignmentResourceIds.add(newResUname);
         }
      }
      if (this.resourceUsername!=null && this.resourceUsername.equals(oldResUname)) {
         this.resourceUsername=newResUname;
         try {
            persist(t);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
   }

   protected void removeAssignments (SharkTransaction t,boolean removeActiveOne, boolean deleteOtherAssignments) throws BaseException {
      boolean createAssignments=Boolean.valueOf(
            SharkEngineManager.
            getInstance().
            getCallbackUtilities().
            getProperty("SharkKernel.createAssignments","true")).booleanValue();
      if (!createAssignments) return;
      PersistentManagerInterface pmi=SharkEngineManager
         .getInstance()
         .getInstancePersistenceManager();
      Iterator itAss = getAssignmentResourceIds(t).iterator();
      while (itAss.hasNext()) {
         String resUsername=(String)itAss.next();
         if (!removeActiveOne && resUsername.equals(resourceUsername)) continue;
         WfResourceInternal res=null;
         try {
            res=SharkUtilities.getResourceFromCache(t,resUsername);
            //System.err.println("         The act "+key+" is deleting its assignment for resource "+resKey);
            if (deleteOtherAssignments || removeActiveOne) {
               pmi.deleteAssignment(key,resUsername,t);
            } else {
               setAssignmentStatus(t, resUsername, false, false);
            }
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
         if (res!=null) {
            res.removeAssignment(t,processId,key);
         }
      }

      if (deleteOtherAssignments) {
         assignmentResourceIds.clear();
         if (!removeActiveOne) {
            assignmentResourceIds.add(resourceUsername);
         }
      }
      //deleteAssignments=true;
   }

   protected void setAssignmentStatus (SharkTransaction t,String resUsername,boolean isValid,boolean isAccepted) throws BaseException {
      try {
         PersistentManagerInterface pmi=SharkEngineManager
            .getInstance()
            .getInstancePersistenceManager();
         AssignmentPersistenceInterface asspo=pmi.createAssignment();
         asspo.setProcessMgrName(mgrName);
         asspo.setProcessId(processId);
         asspo.setActivityId(key);
         asspo.setResourceUsername(resUsername);
         asspo.setValid(isValid);
         asspo.setAccepted(isAccepted);
         pmi.persist(asspo, false, t);
//         System.out.println("PA "+asspo.getActivityId()+", "+asspo.getResourceUsername()+", a="+asspo.isAccepted()+", v="+asspo.isValid());
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public List getDeadlineInfo (SharkTransaction t) throws BaseException {
      List ret = null;
      List pDeadlines = null;
      try {
         pDeadlines=SharkEngineManager
            .getInstance()
            .getInstancePersistenceManager()
            .getAllDeadlinesForActivity(this.processId,this.key,t);
         Collections.sort(pDeadlines,new DeadlineComparator());
      } catch (Exception ex) {
         throw new BaseException(ex);
      }        
      
      if (pDeadlines==null || pDeadlines.size()==0) return new ArrayList();
               
      if (!performDeadlineReevaluation()){
         ret = new ArrayList();
         for (Iterator iter = pDeadlines.iterator(); iter.hasNext();) {
            DeadlinePersistenceInterface dpo = (DeadlinePersistenceInterface) iter.next();
            DeadlineInfo info = new DeadlineInfo(processId,key,dpo.isExecuted(),dpo.getTimeLimit(),dpo.getExceptionName(), dpo.isSynchronous());
            ret.add(info);
         }
      } else {
   		try {
            ret=new ArrayList(reevaluateDeadlines(t));
   		} catch (Exception e) {
   			throw new BaseException(e);
   		}
   		for (int i=0; i<ret.size(); i++) {
   		   DeadlineInfo di=(DeadlineInfo)ret.get(i);
            DeadlinePersistenceInterface dpo=(DeadlinePersistenceInterface)pDeadlines.get(i);
            di.isExecuted=dpo.isExecuted();
         }
      }
      return ret;
   }
   
   public boolean checkDeadlines (SharkTransaction t,long timeLimitBoundary,Map actsToAsyncExcNames) throws BaseException {
      String syncDeadlineExcName=null;
      List brokenDeadlines=null;
      List excNames=new ArrayList();
      if (performDeadlineReevaluation()) {
         List pDeadlines = null;
         try {
            pDeadlines=SharkEngineManager
               .getInstance()
               .getInstancePersistenceManager()
               .getAllDeadlinesForActivity(this.processId,this.key,t);
            Collections.sort(pDeadlines,new DeadlineComparator());
            reevaluateDeadlines(t);
            brokenDeadlines=new ArrayList();
            for (int i=0; i<pDeadlines.size(); i++) {
               DeadlineInfo di=(DeadlineInfo)deadlinesInfo.get(i);
               DeadlinePersistenceInterface dpo=(DeadlinePersistenceInterface)pDeadlines.get(i);
               if (di.timeLimit<timeLimitBoundary) {
                  dpo.setTimeLimit(di.timeLimit);
                  brokenDeadlines.add(dpo);
               }
            }
         } catch (Exception ex) {
            throw new BaseException(ex);
         }        
         deadlinesInfo.clear();
      } else {
         try {
            brokenDeadlines=SharkEngineManager
               .getInstance()
               .getInstancePersistenceManager()
               .getAllDeadlinesForActivity(this.processId,this.key,timeLimitBoundary,t);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      if (brokenDeadlines!=null && brokenDeadlines.size()>0) {
         boolean raiseAsyncDeadlineOnce=new Boolean(SharkEngineManager
         .getInstance()
         .getCallbackUtilities()
         .getProperty("Deadlines.raiseAsyncDeadlineOnlyOnce","true")).booleanValue();
         
         for (int i=0; i<brokenDeadlines.size(); i++) {
            DeadlinePersistenceInterface dpi=(DeadlinePersistenceInterface)brokenDeadlines.get(i);
            if (dpi.isExecuted() && raiseAsyncDeadlineOnce) {
               continue;
            } else {
               persistExecutedDeadline(dpi.getUniqueId(), t);
            }
            String excName=dpi.getExceptionName();
            if (dpi.isSynchronous()) {
               syncDeadlineExcName=excName;
               break;
            } else {
               if (!excNames.contains(excName)) {
                  excNames.add(excName);
               }
            }
         }
      }
      
      if (syncDeadlineExcName!=null) {
         finishImproperlyAndNotifyProcess(t,syncDeadlineExcName);
      } else {
         if (excNames.size()>0) {
            actsToAsyncExcNames.put(this,excNames);
         }
         int type=getActivityDefinition(t).getActivityType();
         // if this is block activity, handle its content deadlines
         if (type==XPDLConstants.ACTIVITY_TYPE_BLOCK) {
            List actActs=process.getAllActiveActivitiesForBlockActivity(t,key);
            Iterator it=actActs.iterator();
            while (it.hasNext()) {
               Map ataens=new HashMap();
               WfActivityInternal act=(WfActivityInternal)it.next();
               boolean syncDeadlineHappened=act.checkDeadlines(t,timeLimitBoundary,ataens);
               if (syncDeadlineHappened) {
                  continue;
               }
               if (ataens.size()>0) {
                  actsToAsyncExcNames.putAll(ataens);
               }
            }
         }
      }
      return (syncDeadlineExcName!=null);
   }

   protected List reevaluateDeadlines (SharkTransaction t) throws Exception {
      Iterator dls=getActivityDefinition(t).getDeadlines().toElements().iterator();

      deadlinesInfo=new ArrayList();
      while (dls.hasNext()) {
         Deadline dl=(Deadline)dls.next();
         String dc=dl.getDeadlineCondition();
         String en=dl.getExceptionName();
         boolean isSynchronous=dl.getExecution().equals(XPDLConstants.EXECUTION_SYNCHR);
         Map context=null;
         String useProcessContextStr=SharkEngineManager
            .getInstance()
            .getCallbackUtilities()
            .getProperty("Deadlines.useProcessContext","false");

         if (Boolean.valueOf(useProcessContextStr).booleanValue()) {
            context=this.process.process_context(t);
         } else {
            context=this.process_context(t);
         }

         context.put(SharkConstants.PROCESS_STARTED_TIME,new java.util.Date(process.getStartTime(t)));
         context.put(SharkConstants.ACTIVITY_ACCEPTED_TIME,new java.util.Date(this.acceptedTime));
         context.put(SharkConstants.ACTIVITY_ACTIVATED_TIME,new java.util.Date(this.activatedTime));
         long timeLimit=((java.util.Date)evaluator(t).evaluateExpression(t,dc,context,java.util.Date.class)).getTime();
         DeadlineInfo dinfo=new DeadlineInfo(processId,key,false,timeLimit,en,isSynchronous);
         deadlinesInfo.add(dinfo);
         //System.out.println("Act "+this+" re-evaluated deadline "+dinfo);
      }
      return deadlinesInfo;
   }

   public final ToolAgentGeneralException getToolAgentException (SharkTransaction t) {
      return toolAgentException;
   }

   public final void setToolAgentException (SharkTransaction t,ToolAgentGeneralException  tage) {
      this.toolAgentException=tage;
   }

   public final String getExceptionName (SharkTransaction t) {
      return exceptionName;
   }

   public final void setExceptionName (SharkTransaction t,String excName) {
      this.exceptionName=excName;
   }

   protected boolean performDeadlineReevaluation () {
      String reevalStr=SharkEngineManager
         .getInstance()
         .getCallbackUtilities()
         .getProperty("Deadlines.reevaluateDeadlines","true");
      return Boolean.valueOf(reevalStr).booleanValue();
   }

   protected void notifyStart (SharkTransaction t,Map context,long runtime) throws BaseException {
      LimitAgentManager mgr = SharkEngineManager.getInstance().getLimitAgentManager();
      try {
         mgr.notifyStart(processId,key,context,runtime);
      } catch (LimitAgentException e) {
         throw new BaseException("Unable to register time limit for activity "+this+" with limit agent",e);
      }
   }

   class DeadlineComparator implements Comparator {
      public int compare(Object o1,Object o2) {
         DeadlinePersistenceInterface dd1=(DeadlinePersistenceInterface)o1;
         DeadlinePersistenceInterface dd2=(DeadlinePersistenceInterface)o2;
         String ui1=dd1.getUniqueId();
         String ui2=dd2.getUniqueId();

         return ui1.compareTo(ui2);
      }
   }
   
}

