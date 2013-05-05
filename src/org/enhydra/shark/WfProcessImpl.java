package org.enhydra.shark;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.TransactionException;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfmodel.AlreadyRunning;
import org.enhydra.shark.api.client.wfmodel.AlreadySuspended;
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
import org.enhydra.shark.api.client.wfmodel.WfCreateProcessEventAudit;
import org.enhydra.shark.api.client.wfmodel.WfDataEventAudit;
import org.enhydra.shark.api.client.wfmodel.WfEventAudit;
import org.enhydra.shark.api.client.wfmodel.WfRequester;
import org.enhydra.shark.api.client.wfmodel.WfStateEventAudit;
import org.enhydra.shark.api.common.SharkConstants;
import org.enhydra.shark.api.internal.instancepersistence.ActivityPersistenceInterface;
import org.enhydra.shark.api.internal.instancepersistence.AndJoinEntryInterface;
import org.enhydra.shark.api.internal.instancepersistence.PersistenceException;
import org.enhydra.shark.api.internal.instancepersistence.PersistentManagerInterface;
import org.enhydra.shark.api.internal.instancepersistence.ProcessPersistenceInterface;
import org.enhydra.shark.api.internal.instancepersistence.ProcessVariablePersistenceInterface;
import org.enhydra.shark.api.internal.limitagent.LimitAgentException;
import org.enhydra.shark.api.internal.limitagent.LimitAgentManager;
import org.enhydra.shark.api.internal.scripting.Evaluator;
import org.enhydra.shark.api.internal.toolagent.ToolAgentGeneralException;
import org.enhydra.shark.api.internal.working.WfActivityInternal;
import org.enhydra.shark.api.internal.working.WfProcessInternal;
import org.enhydra.shark.api.internal.working.WfProcessMgrInternal;
import org.enhydra.shark.api.internal.working.WfRequesterInternal;
import org.enhydra.shark.utilities.MiscUtilities;
import org.enhydra.shark.xpdl.XMLCollectionElement;
import org.enhydra.shark.xpdl.XMLUtil;
import org.enhydra.shark.xpdl.XPDLConstants;
import org.enhydra.shark.xpdl.elements.Activities;
import org.enhydra.shark.xpdl.elements.Activity;
import org.enhydra.shark.xpdl.elements.ActivitySet;
import org.enhydra.shark.xpdl.elements.BlockActivity;
import org.enhydra.shark.xpdl.elements.Condition;
import org.enhydra.shark.xpdl.elements.ProcessHeader;
import org.enhydra.shark.xpdl.elements.Transition;
import org.enhydra.shark.xpdl.elements.Transitions;
import org.enhydra.shark.xpdl.elements.WorkflowProcess;

/**
 * WfProcessImpl - Workflow Process Object implementation
 * @author Sasa Bojanic, Vladimir Puskas
 * @version 1.0.1
 */
public class WfProcessImpl extends WfExecutionObjectImpl implements WfProcessInternal {

   private String actRequesterId;
   private String actRequestersProcessId;

   private String resRequesterId;
   private String managerName;
   private String pkgId;
   private String pDefId;
   private String mgrVer;

   protected Map processContext;

   // condition evaluation
   private Evaluator evaluator;
   private List lastFinishedActivities=new ArrayList();
   private WfProcessMgrInternal manager;
   private Map activeActivitiesMap;
   private Map tmpActivitiesMap;
   private boolean isRunning=false;

   private long creationTime=Long.MAX_VALUE/2;
   private long startTime=Long.MAX_VALUE/2;

   protected WorkflowProcess xpdlProcess;

   protected Set variableIdsToPersist=new HashSet();
   protected Map activityToFollowedTransitions=new HashMap();
   protected Map newActivityToFollowedTransitions=new HashMap();

   protected SharkTransaction initialTransaction;
   protected Thread startingThread=null;
   protected WfActivityInternal actRequester;

   private boolean terminateOrAbortFromActivity=false;

   private String externalRequesterClassName=null;

   protected boolean justCreated=false;
   protected boolean justCreatedVariables=false;
   /**
    * Creates new WfProcessImpl
    *
    * @param    t                   a  SharkTransaction
    * @param    manager             a  WfProcessMgrInternal
    * @param    requester           a  WfRequester
    * @param    key                 a  String
    *
    * @exception   BaseException
    *
    */
   protected WfProcessImpl(SharkTransaction t,
                           WfProcessMgrInternal manager,
                           WfRequesterInternal requester,
                           String key) throws BaseException {
      this.key=key;
      this.manager=manager;
      this.managerName=manager.name(t);
      this.justCreated=true;
      this.justCreatedVariables=true;
      setXPDLAttribs();
      if (requester.getExternalRequester(t)!=null) {
         this.externalRequesterClassName=requester.getExternalRequester(t).getClass().getName();
      }
      if (requester !=null && requester instanceof WfActivityInternal) {
         this.actRequesterId=((WfActivityInternal)requester).key(t);
         this.actRequestersProcessId=((WfActivityInternal)requester).process_id(t);
         this.actRequester=(WfActivityInternal)requester;
         this.resRequesterId=this.actRequester.getResourceRequesterUsername(t);
         this.initialTransaction=t;
      } else if (requester !=null && requester instanceof WfDefaultRequester) {
         this.resRequesterId=((WfDefaultRequester)requester).getResourceRequesterUsername(t);
      }

      SharkUtilities.addProcessToCache(t,this);
      name=getProcessDefinition(t).getName();
      if (name.equals("")) {
         name=getProcessDefinition(t).getId();
      }
      ProcessHeader ph=((WorkflowProcess)getXPDLObject(t)).getProcessHeader();
      description=ph.getDescription();
      if (description!=null && description.length()>254) {
         description=description.substring(0,253);
      }
      try {
         priority=Integer.valueOf(ph.getPriority()).shortValue();
      } catch (Exception ex) {
         priority=3;
      }

      lastStateTime = System.currentTimeMillis();
      creationTime= lastStateTime;
      if (SharkEngineManager.getInstance().getEventAuditManager()!=null || externalRequesterClassName!=null) {
         WfCreateProcessEventAudit cpea=SharkEngineManager
            .getInstance()
            .getObjectFactory()
            .createCreateProcessEventAuditWrapper(t,this,requester);
         if (externalRequesterClassName!=null) {
            notifyRequester(t,cpea);
         }
         
      }
      lastStateEventAudit=SharkEngineManager.
         getInstance().
         getObjectFactory().
         createStateEventAuditWrapper(t,
                                   this,
                                   SharkConstants.EVENT_PROCESS_STATE_CHANGED,
                                   null,
                                   state);
      if (externalRequesterClassName!=null) {
         notifyRequester(t,lastStateEventAudit);
      }
      activeActivitiesMap=new HashMap();

      initializeProcessContext(t);
      if (processContext.size()>0) {
         variableIdsToPersist.addAll(getContext(t).keySet());

         if (SharkEngineManager.getInstance().getEventAuditManager()!=null || externalRequesterClassName!=null) {
            WfDataEventAudit dea=SharkEngineManager
               .getInstance()
               .getObjectFactory()
               .createDataEventAuditWrapper(t,
                                            this,
                                            SharkConstants.EVENT_PROCESS_CONTEXT_CHANGED,
                                            null,
                                            new HashMap(processContext));
            if (externalRequesterClassName!=null) {
               notifyRequester(t, dea);
            }
         }
      }
      activityToFollowedTransitions=new HashMap();
      try {
         persist(t);
         persistProcessContext(t);
      } catch (TransactionException tme) {
         throw new BaseException(tme);
      }

      SharkEngineManager.getInstance().getCallbackUtilities().info("Process "+this+" is created");
   }

   /**
    * Used to create object when restoring it from database.
    */
   protected WfProcessImpl (ProcessPersistenceInterface po) {
      restore(po);
   }

   /**
    * Retrieve the requestor of this process.
    *
    * @param    t                   a  SharkTransaction
    *
    * @return   a WfRequester
    *
    * @exception   BaseException
    */
   public WfRequesterInternal requester (SharkTransaction t) throws BaseException {
      WfRequesterInternal requester=null;
      if (this.actRequesterId!=null) {
         if (actRequester!=null &&
                ((initialTransaction!=null && initialTransaction.equals(t)) ||
                    (startingThread!=null && startingThread.equals(Thread.currentThread())))) {
            requester=actRequester;
         } else {
            WfProcessInternal pReq=SharkUtilities.getProcess(t,this.actRequestersProcessId);
            if (pReq!=null) {
               requester=pReq.getActivity(t,this.actRequesterId);
            }
         }
      }
      WfRequester extReq=null;
      if (requester==null) {
         if (externalRequesterClassName!=null) {
            try {
               extReq=(WfRequester)Class.forName(externalRequesterClassName).newInstance();
            } catch (Exception ex) {
               SharkEngineManager.getInstance().getCallbackUtilities().warn("Can't create external requester - "+externalRequesterClassName+" is not in the classpath, or it doesn't have default constructor.");
            }
         }
         if (this.resRequesterId!=null) {               
            requester=SharkEngineManager.getInstance().getObjectFactory().createDefaultRequester(this.resRequesterId,extReq);
         } else {
            //throw new BaseException("Process must have some requester");
            System.err.println("Process Requester is missing - maybe the parent process is deleted. Empty requester will be returned !");
            requester=SharkEngineManager.getInstance().getObjectFactory().createDefaultRequester("",extReq);
            
         }
      }
      return requester;
   }

   public void setExternalRequesterClassName (SharkTransaction t,String extReqClassName) throws BaseException {
      this.externalRequesterClassName=extReqClassName;
      try {
         persist(t);
      } catch (TransactionException tme) {
         throw new BaseException(tme);
      }
   }

   /**
    * Retrieve the no of activities in this process.
    *
    * @param    t                   a  SharkTransaction
    *
    * @return   an int
    *
    * @exception   BaseException
    *
    */
   public int how_many_step (SharkTransaction t) throws BaseException {
      // TODO: see OMG/WfMC docu which acitivities to consider:
      // all or active ones only
      return getActiveActivitiesMap(t).size();
   }

   /**
    * Retrieve the WfProcessMgr of this process.
    *
    * @param    t                   a  SharkTransaction
    *
    * @return   a WfProcessMgr
    *
    * @exception   BaseException
    */
   public WfProcessMgrInternal manager (SharkTransaction t) throws BaseException {
      if (manager==null) {
         manager=SharkUtilities.getProcessMgr(t, managerName);
         if (manager==null) {
            throw new BaseException("process "+this+" - can't find manager "+managerName);
         }
      }
      return manager;
   }


   /**
    * Retrieve the result for this process.
    *
    * @param    t                   a  SharkTransaction
    *
    * @return   a Map
    *
    * @exception   BaseException
    * @exception   ResultNotAvailable
    */
   public Map result (SharkTransaction t) throws BaseException, ResultNotAvailable {
      Map resultSigLHM = manager(t).result_signature(t);
      Map results = new HashMap();

      if (resultSigLHM != null) {
         Set resultKeys = resultSigLHM.keySet();
         Iterator i = resultKeys.iterator();
         while (i.hasNext()) {
            String fpId =(String)i.next();
            try {
               results.put(fpId,MiscUtilities.cloneWRD(getContext(t).get(fpId)));
            } catch (Throwable thr) {
               throw new BaseException(thr);
            }
         }
      }
      return results;
   }

   /**
    * Starts the process.
    *
    * @param    t                   a  SharkTransaction
    *
    * @exception   BaseException
    * @exception   CannotStart
    * @exception   AlreadyRunning
    *
    */
   public void start (SharkTransaction t) throws BaseException, CannotStart, AlreadyRunning, ToolAgentGeneralException {
      if (state(t).equals(SharkConstants.STATE_OPEN_RUNNING)) {
         throw new AlreadyRunning("The process is already running - can't start again!");
      }
      if (state(t).startsWith(SharkConstants.STATEPREFIX_CLOSED)) {
         throw new CannotStart("The process is closed - can't start it!");
      }

      if (getProcessDefinition(t).getStartingActivities().size()==0) {
         throw new CannotStart("There are no starting activities in the process - can't start it!");
      }

      try {
         startingThread=Thread.currentThread();
         initialTransaction=null;
         change_state(t,SharkConstants.STATE_OPEN_RUNNING);
         SharkEngineManager.getInstance().getCallbackUtilities().info("Starting Process "+this);
         run(t, null);
      } catch (InvalidState is) {
         throw new CannotStart(is);
      } catch (TransitionNotAllowed tna) {
         throw new CannotStart(tna);
      } catch (ToolAgentGeneralException tage) {
         try {
            change_state(t,SharkConstants.STATE_CLOSED_TERMINATED);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
         throw tage;
      } finally{
         actRequester=null;
         startingThread=null;
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
   protected void change_state (SharkTransaction t,String new_state) throws BaseException, InvalidState, TransitionNotAllowed {
      //System.out.println(this+" - changing state from "+state+" to "+new_state);
      if (!SharkUtilities.valid_process_states(state(t)).contains(new_state)) {
         throw new TransitionNotAllowed("Can't change to state "+new_state+", from state "+state+" !");
      }

      if (new_state.equals(SharkConstants.STATE_OPEN_RUNNING)) {
         startTime = System.currentTimeMillis();
         // call the limit agent manager here - this is the only central point
         // for both automatic and manual activties; start time is set based
         // on the process start time
         // NOTE: there is no sense to have limits for automatic activities in
         // standard shark implementation
         if (SharkEngineManager.getInstance().getLimitAgentManager()!=null) {
            this.activateLimitAgent(t);
         }         
      }
      // persisting the state change
      String oldState=state;
      state=new_state;

      lastStateTime = System.currentTimeMillis();
      try {
         persist(t);
      } catch (TransactionException tme) {
         throw new BaseException(tme);
      }

      String eventType=SharkConstants.EVENT_PROCESS_STATE_CHANGED;
      lastStateEventAudit = SharkEngineManager
         .getInstance()
         .getObjectFactory()
         .createStateEventAuditWrapper(t, this, eventType,oldState,new_state);
      if (state.startsWith(SharkConstants.STATEPREFIX_CLOSED)) {
         if (!terminateOrAbortFromActivity && (externalRequesterClassName!=null || actRequesterId!=null)) {
            notifyRequester(t,lastStateEventAudit);
         }
         if (state.equals(SharkConstants.STATE_CLOSED_COMPLETED)) {
            try {
               delete(t);
            } catch (TransactionException te) {
               throw new BaseException(te);
            }
         }

         // cancel the limit agent when we fall out of running state
         LimitAgentManager mgr = SharkEngineManager.getInstance().getLimitAgentManager();
         if (mgr != null) {
            try {
               mgr.notifyStop(key,null);
            } catch (LimitAgentException e) {
               throw new BaseException(e);
            }
         }
      } else {
         if (externalRequesterClassName!=null) {         
            notifyRequester(t, lastStateEventAudit);
         }
      }

   }

   /**
    * Asignes the new values to some process context variables. The variable
    * can be given by it's name or it's id.
    *
    * @param    t                   a  SharkTransaction
    * @param    newValue            a  Map
    *
    * @exception   BaseException
    * @exception   InvalidData
    * @exception   UpdateNotAllowed
    *
    */
   public void set_process_context (SharkTransaction t,Map newValue) throws BaseException, InvalidData, UpdateNotAllowed {
      Map oldValues=new HashMap();
      Map newChanged=new HashMap();
      Iterator it=newValue.entrySet().iterator();
      while (it.hasNext()) {
         Map.Entry me=(Map.Entry)it.next();
         String id=(String)me.getKey();
         Object val=me.getValue();
         // this happens when passing the OUT type formal param. for the process
         //if (val==null) continue;
         if (getContext(t).containsKey(id)) {
            //type checking
            Object oldVal=getContext(t).get(id);
            if (SharkUtilities.checkDataType(t,getProcessDefinition(t),id,oldVal,val)) {
               //System.out.println("var "+id+"["+oldVal+","+val+"]");
               if ((oldVal!=null && !oldVal.equals(val)) || (oldVal==null && val!=null)) {
                  oldValues.put(id,oldVal);
                  newChanged.put(id,val);
               }
            } else {
               throw new InvalidData("Invalid data type for process variable "+id);
            }
         } else {
            throw new UpdateNotAllowed("Context attribute "+id+" does not exist in process context - adding new attributes to the process context is not allowed");
         }
      }
      
      if (newChanged.size()>0) {
         getContext(t).putAll(newChanged);
         variableIdsToPersist.addAll(newChanged.keySet());
         persistProcessContext(t);
         if (SharkEngineManager.getInstance().getEventAuditManager()!=null || externalRequesterClassName!=null) {
            boolean persistOldEventAuditData=new Boolean(               
                  SharkEngineManager
                     .getInstance()
                     .getCallbackUtilities()
                     .getProperty("PERSIST_OLD_EVENT_AUDIT_DATA","true")).booleanValue();
            if (!persistOldEventAuditData) {
               oldValues=null;
            }
            WfDataEventAudit dea=SharkEngineManager.getInstance().getObjectFactory().
               createDataEventAuditWrapper(t, this,
                                           SharkConstants.EVENT_PROCESS_CONTEXT_CHANGED,
                                           oldValues,
                                           newChanged);
            if (externalRequesterClassName!=null) {
               notifyRequester(t, dea);
            }
         }
      }
   }

   /**
    * Resume this process.
    */
   public void resume(SharkTransaction t) throws BaseException, CannotResume, NotSuspended {
      try {
         if (!state(t).equals(SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED)) {
            throw new NotSuspended("The process is not suspended - can't resume it!");
         }
         // if this is a subflow process of suspended activity, and it is
         // executed as SYNCHRONOUS, do not allow process to resume
         WfRequesterInternal requester=requester(t);
         if (requester!=null && (requester instanceof WfActivityInternal)) {
            WfActivityInternal waImpl=(WfActivityInternal)requester;
            if (waImpl.state(t).equals(SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED)) {
               // Determine subflow type
               if (waImpl.isPerformerSynchronous(t)) {
                  SharkEngineManager.getInstance().getCallbackUtilities().error("Process"+toString()+" - Cannot resume because the requester activity is suspended");
                  throw new CannotResume("Cannot resume - The requester activity is suspended");
               }
            }
         }
         SharkEngineManager.getInstance().getCallbackUtilities().info("Resuming process "+this);
         change_state(t, SharkConstants.STATE_OPEN_RUNNING);

         Iterator it=getActiveActivities(t).iterator();
         while (it.hasNext()) {
            WfActivityInternal act=(WfActivityInternal)it.next();
            if (act.state(t).equals(SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED)) {
               if (act.block_activity_id(t)==null) {
                  act.resume(t);
               }
            }
         }
      } catch (InvalidState is) {
         throw new CannotResume(is);
      } catch (TransitionNotAllowed tna) {
         throw new CannotResume(tna);
      }
   }

   /**
    * Suspend this process.
    */
   public void suspend(SharkTransaction t) throws BaseException, CannotSuspend, NotRunning, AlreadySuspended {
      if (state(t).equals(SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED)) {
         throw new AlreadySuspended("The process is already suspended - can't suspend it twice!");
      }
      if (state(t).startsWith(SharkConstants.STATEPREFIX_OPEN_NOT_RUNNING)) {
         throw new NotRunning("The process is not in the running state");
      }
      try {
         SharkEngineManager.getInstance().getCallbackUtilities().info("Suspending process "+this);
         change_state(t,SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED);

         Iterator it=getActiveActivities(t).iterator();
         while (it.hasNext()) {
            WfActivityInternal act=(WfActivityInternal)it.next();
            String actState=act.state(t);
            if (!actState.equals(SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED)) {
               if (act.block_activity_id(t)==null) {
                  act.suspend(t);
               }
            }
         }
      } catch (InvalidState is) {
         throw new CannotSuspend(is);
      } catch (TransitionNotAllowed tna) {
         throw new CannotSuspend(tna);
      }
   }

   public void terminateFromActivity (SharkTransaction t) throws BaseException, CannotStop, NotRunning {
      terminateOrAbortFromActivity=true;
      terminate(t);
   }

   /**
    * Terminate this process.
    */
   public void terminate(SharkTransaction t) throws BaseException, CannotStop, NotRunning {
      try {
         String stateStr = SharkConstants.STATE_CLOSED_TERMINATED;
         if (!state(t).startsWith(SharkConstants.STATEPREFIX_OPEN)) {
            throw new CannotStop("The process is already closed - can't terminate it!");
         }
         SharkEngineManager.getInstance().getCallbackUtilities().info("Terminating process "+this);

         change_state(t,stateStr);

         Iterator it=getActiveActivities(t).iterator();
         while (it.hasNext()) {
            WfActivityInternal act=(WfActivityInternal)it.next();
            if (act.block_activity_id(t)==null) {
               act.terminateFromProcess(t);
            }
         }
         lastFinishedActivities.clear();

         if (activeActivitiesMap!=null) {
            activeActivitiesMap.clear();
         }

         //delete(t);

      } catch (InvalidState is) {
         throw new CannotStop(is);
      } catch (TransitionNotAllowed tna) {
         throw new CannotStop(tna);
      } /*catch (TransactionException tme) {
       throw new BaseException(tme);
       }*/
   }

   public void abortFromActivity (SharkTransaction t) throws BaseException, CannotStop, NotRunning {
      terminateOrAbortFromActivity=true;
      abort(t);
   }

   /**
    * Abort the execution of this process.
    *
    * @param    t                   a  SharkTransaction
    *
    * @exception   BaseException
    * @exception   CannotStop
    * @exception   NotRunning
    */
   public void abort(SharkTransaction t) throws BaseException, CannotStop, NotRunning {
      String stateStr = SharkConstants.STATE_CLOSED_ABORTED;
      if (!state(t).startsWith(SharkConstants.STATEPREFIX_OPEN)) {
         throw new CannotStop("The process is already closed - can't abort it!");
      }
      try {
         SharkEngineManager.getInstance().getCallbackUtilities().info("Aborting process "+this);
         change_state(t,stateStr);

         Iterator it=getActiveActivities(t).iterator();

         while (it.hasNext()) {
            WfActivityInternal act=(WfActivityInternal)it.next();
            if (act.block_activity_id(t)==null) {
               act.abortFromProcess(t);
            }
         }

         lastFinishedActivities.clear();

         if (activeActivitiesMap!=null) {
            activeActivitiesMap.clear();
         }

         //delete(t);

      } catch (InvalidState is) {
         throw new CannotStop(is);
      } catch (TransitionNotAllowed tna) {
         throw new CannotStop(tna);
      } /*catch (TransactionException tme) {
       throw new BaseException(tme);
       }*/
   }

   protected void run (SharkTransaction t, WfActivityInternal lastFinishedActivity) throws BaseException, ToolAgentGeneralException {
      isRunning=true;
      try {
         if (lastFinishedActivity==null) {
            List starts=getProcessDefinition(t).getStartingActivities();

            for (Iterator it=starts.iterator(); it.hasNext();) {
               String asDefId=null;
               Activity actDef=(Activity)it.next();
               Object owner=actDef.getParent().getParent();
               if (owner instanceof ActivitySet) {
                  asDefId=((ActivitySet)owner).getId();
               }
               startActivity(t,asDefId,actDef,null);
            }
            //return;
         }
         while (lastFinishedActivities.size()>0) {
            if (!state.equals(SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED)) {
               //System.out.println("QN for lfa "+lastFinishedActivities.get(0));
               if(!state.startsWith(SharkConstants.STATEPREFIX_CLOSED)) {
                  queueNext(t, (WfActivityInternal)lastFinishedActivities.get(0));
                  //System.out.println("QN for lfa "+lastFinishedActivities.get(0)+" finished");
               }
               lastFinishedActivities.remove(0);
            } else {
               //System.out.println("ELSE");
               return;
            }
         }
         if (state.startsWith(SharkConstants.STATEPREFIX_CLOSED)) {
            /*try {
             delete(t);
             } catch (TransactionException tme) {
             throw new BaseException(tme);
             }*/
            return;
         }
      } finally {
         isRunning=false;
      }

   }

   /**
    * Activates an activity object
    *
    * @param    t                   a  SharkTransaction
    * @param    actDefId            a  String
    * @param    blockActId          a  String
    *
    * @exception   BaseException
    */
   public void start_activity(SharkTransaction t, String actDefId,String blockActId)  throws BaseException, ToolAgentGeneralException {
      if (state(t).startsWith(SharkConstants.STATEPREFIX_CLOSED)) {
         throw new BaseException("Can't start the activity of closed process!");
      }
      WorkflowProcess wp=getProcessDefinition(t);

      Activities acts=null;
      String asDefId=null;
      // if we must start activity that is inside some block
      if (blockActId!=null && blockActId.length()>0) {
         WfActivityInternal baImpl = getActiveActivity(t, blockActId);
         if (baImpl!=null) {
            BlockActivity ba=SharkUtilities.getActivityDefinition(t,baImpl,wp,baImpl.block_activity(t)).getActivityTypes().getBlockActivity();
            asDefId=ba.getBlockId();
            ActivitySet aSet=wp.getActivitySet(asDefId);
            acts=aSet.getActivities();
         }
      }
      if (acts==null) {
         acts=wp.getActivities();
      }
      Activity activity=acts.getActivity(actDefId);
      WfActivityInternal ba = getActiveActivity(t, blockActId);

      if (state(t).equals(SharkConstants.STATE_OPEN_NOT_RUNNING_NOT_STARTED)) {
         try {
            change_state(t,SharkConstants.STATE_OPEN_RUNNING);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
         // call the limit agent manager here
         if (SharkEngineManager.getInstance().getLimitAgentManager()!=null) {
            this.activateLimitAgent(t);
         }
      }

      startActivity(t,asDefId,activity,ba);
   }

   /**
    * Method process_definition_id
    *
    * @param    t                   a  SharkTransaction
    *
    * @return   a String
    *
    * @exception   BaseException
    */
   public final String process_definition_id(SharkTransaction t) throws BaseException {
      return pDefId;
   }

   public final String manager_name(SharkTransaction t) throws BaseException {
      return managerName;
   }

   public final String manager_version (SharkTransaction t) throws BaseException {
      return mgrVer;
   }

   // Activates an activity object
   protected void startActivity(SharkTransaction t,
                                String asDefId,
                                Activity activity,
                                WfActivityInternal blockActivity) throws BaseException, ToolAgentGeneralException {
      // if activity is already activated (the case when XOR Join is reached twice or
      // more times, before activity is executed), do nothing
      if (isActivityDefinitionActive(t,activity,blockActivity)) {
         SharkEngineManager.getInstance().getCallbackUtilities().warn("Activity "+activity+" is already started - can't start it twice");
         return;
      }

      WfActivityInternal act = SharkEngineManager
         .getInstance()
         .getObjectFactory()
         .createActivity(t, this, getNextWorkItemId(activity.getId()), asDefId, activity.getId(), blockActivity);
      SharkEngineManager.getInstance().getCallbackUtilities().info("Process"+toString()+" - Activity"+act.toString()+" is created");
      addToActiveActivities(t,activity,act);

      try {
         //System.out.println("Activating act "+act);
         act.activate(t);
         //System.out.println("Finished activation of act "+act);
         String causeClassName=null;
         // handling block activity start and exc. handling
         if (activity.getActivityType()==XPDLConstants.ACTIVITY_TYPE_BLOCK) {
            try {
               runBlock(t,activity,act);
            } catch (ToolAgentGeneralException ex) {
               causeClassName=SharkUtilities.extractExceptionName(ex);
               act.setExceptionName(t,causeClassName);
               act.setToolAgentException(t,ex);
            }
         } else {
            causeClassName=act.getExceptionName(t);
         }

         if (causeClassName!=null) {
            if (getExceptionTransFrom(t,act,activity,causeClassName).size()==0) {
               ToolAgentGeneralException tage=act.getToolAgentException(t);
               if (tage!=null) {
                  throw act.getToolAgentException(t);
               } else {
                  throw new BaseException("Unexpected exception from WfProcessImpl.startActivity()");
               }
            }
         }
      } catch (AlreadyRunning ar) {
         throw new BaseException(ar);
      } catch (CannotStart e) {
         throw new BaseException(e);
      }
   }

   /**
    * Receives notification when an activity has completed.
    */
   public void activity_complete(SharkTransaction t, WfActivityInternal activity) throws Exception {
      SharkEngineManager.getInstance().getCallbackUtilities().info("Process"+toString()+" - Activity"+activity.toString()+" is completed.");

      //System.out.println("Activity "+activity+" signals its completion");
      lastFinishedActivities.add(activity);

      getActiveActivitiesMap(t).remove(activity.key(t));

      if (!isRunning) {
         //System.out.println("Calling run after completion of Activity "+activity);
         run(t, activity);
         //System.out.println("Run after completion of Activity "+activity+"finished");
      }
      //System.out.println("Returning to the caller after completion of Activity "+activity+"finished");
      //SharkServer.getLogger().error("Process"+toString()+" - Exception while completing activity");
   }

   /**
    * Receives notification when an activity has terminated.
    */
   public void activity_terminate(SharkTransaction t, WfActivityInternal activity) throws Exception {
      SharkEngineManager.getInstance().getCallbackUtilities().info("Process"+toString()+" - Activity"+activity.toString()+" is terminated.");

      lastFinishedActivities.add(activity);
      
      getActiveActivitiesMap(t).remove(activity.key(t));
      // FIXING BUG WHEN DEADLINE HAPPENS ON BLOCK ACTIVITY
      Activity activityDefinition=
         SharkUtilities.
         getActivityDefinition(t, activity,getProcessDefinition(t), activity.block_activity(t));
      if (activityDefinition.getActivityType()==XPDLConstants.ACTIVITY_TYPE_BLOCK) { 
         List bas=getAllActiveActivitiesForBlockActivity(t, activity.key(t));
         Iterator it=bas.iterator();
         while (it.hasNext()) {
            WfActivityInternal baMember=(WfActivityInternal)it.next();
            getActiveActivitiesMap(t).remove(baMember.key(t));
         }         
      }
      
      if (!isRunning) {
         run(t, activity);
      }
   }

   /**
    * Receives notification when an activity has aborted.
    */
   public void activity_abort(SharkTransaction t, WfActivityInternal activity) throws Exception {
      if (!activity.state(t).equals(SharkConstants.STATE_CLOSED_ABORTED)) {
         throw new Exception("Activity state is not aborted");
      }
      SharkEngineManager.getInstance().getCallbackUtilities().info("Process"+toString()+" - Aborting activity"+activity.toString());

      getActiveActivitiesMap(t).remove(activity.key(t));
   }

   // Queues the next activities for processing
   protected void queueNext(SharkTransaction t, WfActivityInternal fromActivity) throws BaseException, ToolAgentGeneralException {
      //System.err.println("QUEINGNEXT FOR "+fromActivity);
      //      try {
      WorkflowProcess wp=getProcessDefinition(t);
      Activity aDef=SharkUtilities.getActivityDefinition(t, fromActivity, wp, fromActivity.block_activity(t));
      Activities activityDefs;
      Transitions transitions;
      // the next transitions from 'fromActivity'
      List nextTrans;
      WfActivityInternal blockActivity=fromActivity.block_activity(t);
      ActivitySet aSet=null;
      String asDefId=null;
      if (blockActivity!=null) {
         aSet=(ActivitySet)aDef.getParent().getParent();
         asDefId=aSet.getId();
         activityDefs=aSet.getActivities();
         transitions=aSet.getTransitions();
         // all transitions from activity
      } else {
         activityDefs=wp.getActivities();
         transitions=wp.getTransitions();
      }
      String causeClassName=fromActivity.getExceptionName(t);
      boolean checkIfSomethingWrong=true;
      if (causeClassName==null) {
         nextTrans=getTransFrom(t,fromActivity,aDef);
         //System.out.println("NEXTTRANS="+nextTrans);
      } else {
         nextTrans=getExceptionTransFrom(t,fromActivity,aDef,causeClassName);
         //System.out.println("ETs="+nextTrans);
         if (nextTrans.size()==0) {
            if (blockActivity!=null && blockActivity.getToolAgentException(t)!=null) {
               nextTrans=new ArrayList();
               checkIfSomethingWrong=false;
            } else {
               ToolAgentGeneralException tage=blockActivity.getToolAgentException(t);
               if (tage!=null) {
                  throw tage;
               } else {
                  throw new BaseException("Unexpected exception from WfProcessImpl.queryNext()");
               }
            }
         }
      }

      // if something went wrong
      if (checkIfSomethingWrong && nextTrans.size()==0) {
         //System.out.println("CSW ts0");
         XMLCollectionElement wpOrASet=wp;
         if (aSet!=null) {
            wpOrASet=aSet;
         }
         List endingActDefs=null;
         if (wpOrASet instanceof WorkflowProcess) {
            endingActDefs=((WorkflowProcess)wpOrASet).getEndingActivities();
            //System.out.println("ead1="+endingActDefs);
         } else {
            endingActDefs=((ActivitySet)wpOrASet).getEndingActivities();
            //System.out.println("ead2="+endingActDefs);
         }
         if (!endingActDefs.contains(aDef)) {
            String unsatSplitHandling=
               SharkEngineManager
               .getInstance()
               .getCallbackUtilities()
               .getProperty("SharkKernel.UnsatisfiedSplitConditionsHandling",
                            SharkConstants.UNSATISFIED_SPLIT_CONDITIONS_HANDLING_FINISH_IF_POSSIBLE);
            if (unsatSplitHandling.equals(SharkConstants.UNSATISFIED_SPLIT_CONDITIONS_HANDLING_IGNORE)) {
               SharkEngineManager.getInstance().getCallbackUtilities().warn("Process "+this+" could hang forever in "+fromActivity+" branch, after this activity is finished");
               return;
            } else if (unsatSplitHandling.equals(SharkConstants.UNSATISFIED_SPLIT_CONDITIONS_HANDLING_ROLLBACK)) {
               throw new BaseException("Shark kernel is configured not to allow hanging processes!");
            } else {
               // Do nothing - process/block will finish if it can
               SharkEngineManager.getInstance().getCallbackUtilities().error("Process "+this+" will try to finish after "+fromActivity+" is executed because kernel is configured not to allow hanging and not to rollback");
            }
         }
      }

      if (nextTrans.size() > 0) {
         //System.out.println("NTS="+nextTrans.size());
         Iterator it=nextTrans.iterator();
         while (it.hasNext()) {
            Transition trans=(Transition)it.next();
            // Get the activity definition
            Activity toActivityDef=trans.getToActivity();
            if (!toActivityDef.isAndTypeJoin() || toActivityDef.getIncomingTransitions().size()==1) {
               //System.out.println("SA");
               startActivity(t,asDefId,toActivityDef,blockActivity);
            } else {
               //System.out.println("JT");
               joinTransition(t,fromActivity,toActivityDef,asDefId);
            }
         }
      } else {
         // find if there are active activities in some other branches

         // if there are other active activities, do nothing, and if this
         // is the last activity within activity set, finish block activity
         List actActs=getActiveActivities(t);
         //System.out.println("Act acts are "+actActs);
         if (actActs.size()>0) {
            if (blockActivity!=null) {
               for (int i=0; i<actActs.size(); i++) {
                  //try {
                  java.lang.Object ba = getActiveActivity(t,((WfActivityInternal)actActs.get(i)).block_activity_id(t));
                  if (ba!=null && ba.equals(blockActivity)) {
                     //System.out.println("AA is BA");
                     return;
                  }
               }
               // the activity set has no more transitions, finish the block activity
               try {
                  //System.out.println("FBA");
                  blockActivity.finish(t);
               } catch (Exception ex) {
                  throw new BaseException("Something went wrong while finishing block activity "+blockActivity,ex);
               }
               return;
            }
         } else {
            // if this is end of Activity Set, finish the block activity
            if (aSet!=null) {
               try {
                  if (blockActivity.state(t).startsWith(SharkConstants.STATEPREFIX_CLOSED)){
                     // this can happen if we have implicit AND split within
                     // the fully automatic block
                     //System.out.println("BASCL");
                     return;
                  }
                  //System.out.println("FBA2");
                  blockActivity.finish(t);
               } catch (Exception ex) {
                  throw new BaseException("Something went wrong while finishing block activity "+blockActivity,ex);
               }
               return;
               // otherwise, finish the process
            } else {
               //System.out.println("LFAS="+lastFinishedActivities);
               if (lastFinishedActivities.size()<=1) {
                  SharkEngineManager.getInstance().getCallbackUtilities().info("Process"+toString()+" - No transitions left to follow");

                  try {
                     change_state(t,SharkConstants.STATE_CLOSED_COMPLETED);
                  } catch (Exception ex) {
                     throw new BaseException("Something went wrong while changing process state to closed.completed",ex);
                  }
               }
            }
         }
      }
      /*      } catch (Throwable tr) {
       SharkEngineManager.getInstance().getCallbackUtilities().error("Process"+toString()+" - error while querying");
       throw new BaseException("Problems while querying",tr);
       }*/
   }

   // Follows the and-join transition
   protected void joinTransition(SharkTransaction t,
                                 WfActivityInternal fromActivity,
                                 Activity toActivityDef,
                                 String asDefId) throws BaseException, ToolAgentGeneralException {


      // get all incoming transitions to this activity
      List toTrans=toActivityDef.getIncomingTransitions();

      int followed=restoreActivityToFollowedTransitionsMap(t,toActivityDef,asDefId);

      SharkEngineManager.getInstance().getCallbackUtilities().info("Process"+toString()+" - "+(followed+1)+" of "+toTrans.size()+" transitions followed to activity with definition "+toActivityDef.getId());

      // check to see if all transition requirements are met
      if (toTrans.size()==followed+1) {
         SharkEngineManager.getInstance().getCallbackUtilities().info("Process"+toString()+" - All transition have been followed to activity with definition "+toActivityDef.getId());
         Set currentTrans=(Set)newActivityToFollowedTransitions.get(toActivityDef);
         if (currentTrans!=null && currentTrans.size()==followed) { // just remove from map - this is all performed in one transaction
            newActivityToFollowedTransitions.remove(toActivityDef);
         } else { // mark for delete
            if (currentTrans!=null) {
               currentTrans.clear();
            } else {
               newActivityToFollowedTransitions.put(toActivityDef,currentTrans);
            }
         }
         //lastFinishedActivities.removeAll(followedActs);
         activityToFollowedTransitions.put(toActivityDef,new Integer(0));
         try {
            persistActivityToFollowedTransitions(t);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
         startActivity(t,asDefId,toActivityDef,getActiveActivity(t, fromActivity.block_activity_id(t)));
      } else {
         activityToFollowedTransitions.put(toActivityDef,new Integer(followed+1));
         Set currentTrans=(Set)newActivityToFollowedTransitions.get(toActivityDef);
         if (currentTrans==null) {
            currentTrans=new HashSet();
            newActivityToFollowedTransitions.put(toActivityDef,currentTrans);
         }
         currentTrans.add(fromActivity.key(t));
         try {
            persistActivityToFollowedTransitions(t);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
         //System.err.println("[WfProcess.joinTransition] : Waiting for transitions to finish.");
      }
   }

   // Returns transitions to the next activity or activities to execute, based
   // on transition conditions, and split type of fromActivity
   // EXCEPTION and DEFAULTEXCEPTION transitions are not returned
   protected List getTransFrom(SharkTransaction t,
                               WfActivityInternal fromActivity,
                               Activity fromActDef) throws BaseException {

      List orderedOutTransitions=fromActDef.getNonExceptionalOutgoingTransitions();
      List transList = new ArrayList();

      boolean isAndSplit=fromActDef.isAndTypeSplit();
      Transition otherwiseTransition = null;

      Iterator transitions=orderedOutTransitions.iterator();

      while (transitions.hasNext()) {
         Transition trans=(Transition)transitions.next();
         Condition condition = trans.getCondition();
         String condType=condition.getType();
         if(condType.equals(XPDLConstants.CONDITION_TYPE_OTHERWISE)){
            otherwiseTransition = trans;
            boolean handleOtherwiseTransitionLast=new Boolean(
                  SharkEngineManager.
                     getInstance().
                     getCallbackUtilities().
                     getProperty("SharkKernel.handleOtherwiseTransitionLast","false")).booleanValue();
            if (!isAndSplit && !handleOtherwiseTransitionLast) {
               break;
            } else {
               continue;
            }
         }
         boolean evalRes=false;
         // user process_context() - we must pass cloned variables
         String cond=condition.toValue();
         if (cond.trim().length()==0) {
            evalRes=true;
         } else {
            try {
               evalRes=evaluator(t).evaluateCondition(t,cond,process_context(t));
            } catch (Exception ex) {
               throw new BaseException("Exception while evaluating transition condition",ex);
            }
         }
         if(evalRes){
            transList.add(trans);
            if (!isAndSplit) {
               break;
            }
         }
      }

      // use otherwise transitions if that is the only one transition 
      // or if no other transition condition is evaluated to true
      // NOTE: this depends on configuration how to handle OTHERWISE transition
      if (transList.size() == 0 && otherwiseTransition != null) {
         transList.add(otherwiseTransition);
         SharkEngineManager.getInstance().getCallbackUtilities().info("Process"+toString()+" - process is proceeding with otherwise transition of Activity"+fromActivity);
      }
      //System.err.println("[WfProcess.getTransFrom] : Transitions: " + transList.size());

      return transList;
   }

   // Returns exception transition where the process has to go based on
   // exception thrown from tool agent
   protected List getExceptionTransFrom(SharkTransaction t,
                                        WfActivityInternal fromActivity,
                                        Activity fromActDef,
                                        String exceptionName) throws BaseException {

      List outTransitions=fromActDef.getExceptionalOutgoingTransitions();
      List transList = new ArrayList();

      if (outTransitions.size()==0) return transList;

      Transition excTransition = null;
      Transition defaultExceptionTransition = null;
      Iterator transitions=outTransitions.iterator();

      while (transitions.hasNext()) {
         Transition trans=(Transition)transitions.next();
         Condition condition = trans.getCondition();
         String condType=condition.getType();
         if(condType.equals(XPDLConstants.CONDITION_TYPE_DEFAULTEXCEPTION)){
            defaultExceptionTransition = trans;
            continue;
         }
         boolean evalRes=false;
         String cond=condition.toValue();
         if (cond.length()==0) {
            evalRes=true;
         } else {
            evalRes=cond.equals(exceptionName);
         }
         if(evalRes){
            excTransition=trans;
            break;
         }
      }

      // if there is no exception transition found, and there is no
      // default exception transition, try to evaluate conditions
      if (excTransition==null && defaultExceptionTransition==null) {
         transitions=outTransitions.iterator();
         while (transitions.hasNext()) {
            Transition trans=(Transition)transitions.next();
            Condition condition = trans.getCondition();
            boolean evalRes=false;
            String cond=condition.toValue();
            try {
               evalRes=evaluator(t).evaluateCondition(t,cond,process_context(t));
            } catch (Exception ex) {
               // System.out.println("Condition is not satisfied");
            }
            if(evalRes){
               excTransition=trans;
               break;
            }
         }
      }

      // use default exception transitions if that is the only one transition
      if (excTransition == null && defaultExceptionTransition != null) {
         excTransition=defaultExceptionTransition;
         SharkEngineManager.getInstance().getCallbackUtilities().info("Process"+toString()+" - process is proceeding with default exception transition of Activity"+fromActivity);
      }
      //System.err.println("[WfProcess.getTransFrom] : Transitions: " + transList.size());
      if (excTransition!=null) {
         transList.add(excTransition);
      }
      return transList;
   }

   protected void notifyRequester (SharkTransaction t,WfEventAudit event) throws BaseException {
      SharkEngineManager.getInstance().getCallbackUtilities().info("Process"+toString()+" - notifying requester of the event");
      WfRequesterInternal req=requester(t);
//      SharkEngineManager.getInstance().getCallbackUtilities().info("Process"+toString()+" - requester found");

      if (req != null) {
         try {
            WfActivityInternal aReq=null;
            if (req instanceof WfActivityInternal) {
               aReq=(WfActivityInternal)req;
            }
            if (aReq==null) {
               req.receive_event(t,event,this);
//SharkEngineManager.getInstance().getCallbackUtilities().info("Process"+toString()+" - ext requester notified");
            } else {
               // if the requester activity is closed - do not notify it
               if ((event instanceof WfStateEventAudit) && aReq.state(t).startsWith(SharkConstants.STATEPREFIX_OPEN)) {
                  aReq.receive_event(t,event,this);
//SharkEngineManager.getInstance().getCallbackUtilities().info("Process"+toString()+" - act requester notified");
               }
            }
         } catch (InvalidPerformer ip) {
            throw new BaseException(ip);
         }
      }
   }
   
   /**
    * Puts the workflow relevant data and formal parameters into process context.
    * All data is put into context by id-value pairs.
    * The initial value of WRD is set if exist, or default value otherwise.
    * The formal parameters are also initialized to the default values.
    */
   protected void initializeProcessContext(SharkTransaction t) throws BaseException {
      // get all data fields (from package, and from process)
      processContext=new HashMap();
      WorkflowProcess wp=getProcessDefinition(t);
      Collection dfsAndFPs=wp.getAllVariables().values();
      Iterator itDfs=dfsAndFPs.iterator();
      while (itDfs.hasNext()) {
         XMLCollectionElement dfOrFp=(XMLCollectionElement)itDfs.next();
         Object initVal=SharkUtilities.getInitialValue(dfOrFp,false);
         String id=dfOrFp.getId();
         //out.println("putting "+dfOrFp.getClass().getName()+" "+id+"into context");
         processContext.put(id,initVal);
      }
   }

   /**
    * Get the next work item ID. Each work item will have a unique ID.
    * @return The work item ID string
    */
   protected String getNextWorkItemId(String aDefId) throws BaseException {
      String id = SharkUtilities.getNextId(SharkConstants.ACTIVITY_ID_NAME);
      id=id+"_"+key+"_"+aDefId;
      if (id.length()>100) id=id.substring(0,100);
      return id;
   }

   public String toString () {
      return "[key="+key+",mgrname="+managerName+"]";
   }

   /**
    * It is assumed that there can't be two or more
    * processes having the same key.
    */
   public boolean equals (java.lang.Object obj) {
      if (!(obj instanceof WfProcessImpl)) return false;
      return ((WfProcessImpl)obj).key.equals(key);
   }

   /**
    * Returns if there is an active activity for given definition.
    */
   protected boolean isActivityDefinitionActive (SharkTransaction t,Activity aDef,WfActivityInternal blockActivity) throws BaseException {
      String aDefId=aDef.getId();
      Iterator it=getActiveActivities(t).iterator();
      while (it.hasNext()) {
         WfActivityInternal act=(WfActivityInternal)it.next();
         if (act.activity_definition_id(t).equals(aDefId) &&
                (act.state(t).equals(SharkConstants.STATE_OPEN_RUNNING) ||
                    act.state(t).equals(SharkConstants.STATE_OPEN_NOT_RUNNING_NOT_STARTED))) {

            if ((blockActivity!=null && blockActivity.key(t).equals(act.block_activity_id(t))) ||
                   (blockActivity==null && act.block_activity_id(t)==null)) {
               return true;
            }
         }
      }
      return false;
   }

   /*private WfActivityInternal getLastFinishedActivityForDefinition (SharkTransaction t, Activity actDef) throws BaseException {
    WfActivityInternal lfa=null;
    Iterator iter=getActivityList(t).iterator();
    long maxTime=-1;
    while (iter.hasNext()) {
    try {
    WfActivityInternal act = (WfActivityInternal)iter.next();
    Activity aDef=SharkUtilities.getActivityDefinition(t,act,getProcessDefinition(t));
    if (aDef.equals(actDef) &&
    (act.state(t).equals(SharkConstants.STATE_CLOSED_COMPLETED) || act.state(t).equals(SharkConstants.STATE_CLOSED_TERMINATED))) {
    long eTime=act.last_state_time(t).time;
    if (maxTime<eTime) {
    maxTime=eTime;
    lfa=act;
    }
    }
    } catch (Exception ex) {
    //ex.printStackTrace();
    }
    }
    return lfa;
    }*/

   /* *
    * Used when restoring engine from database.
    * @param vdId The workflow relevant data or formal parameter definition Id
    * @param val The value of WRD or FP
    */
   /*private void restoreContextVariable (String vdId,String val) throws BaseException {
    XMLCollectionElement dfOrFp=((DataFields)getProcessDefinition().
    get("DataFields")).getDataField(vdId);
    if (dfOrFp==null) {
    dfOrFp=((FormalParameters)getProcessDefinition().
    get("FormalParameters")).getFormalParameter(vdId);
    }
    if (dfOrFp!=null) {
    Object anyVal=SharkWrapperUtilities.getValue(dfOrFp,val);
    getProcessContext().put(vdId,anyVal);
    }
    }*/

   protected void setProcessVariables (SharkTransaction t) throws BaseException {
      try {
         //System.out.println("SPVCVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV");
         // initialize context, just to keep the order of variables
         processContext=new HashMap();
   
         PersistentManagerInterface ipm = SharkEngineManager.getInstance()
         .getInstancePersistenceManager();

         WorkflowProcess wp=getProcessDefinition(t);
         List l=new ArrayList(wp.getAllVariables().values());
         if (l.size()==0) return;
         //System.err.println("INIT PC="+processContext);
         Iterator it=l.iterator();
         List variableIds=new ArrayList();
         while (it.hasNext()) {
            XMLCollectionElement dfOrFp=(XMLCollectionElement)it.next();
            String vdId=dfOrFp.getId();         
            variableIds.add(vdId);
         }
         l=ipm.getProcessVariables(key, variableIds, t);
         it=l.iterator();
         while (it.hasNext()) {
            ProcessVariablePersistenceInterface var=(ProcessVariablePersistenceInterface)it.next();
            //System.out.println("Restoring proc var "+var.getDefinitionId()+", val="+var.getValue());
            //if (anyVal!=null) {
            processContext.put(var.getDefinitionId(),var.getValue());
            //}
         }
      } catch (Exception ex) {
         throw new BaseException ("Restoring of process context failed!",ex);
      }
   }
   
   protected WorkflowProcess getProcessDefinition (SharkTransaction t) throws BaseException {
      if (xpdlProcess==null) {
         xpdlProcess=SharkUtilities.getWorkflowProcess(pkgId,mgrVer,pDefId);
      }
      return xpdlProcess;
   }

   public void persist(SharkTransaction t) throws TransactionException {
      try {
         //System.err.println("The proc "+this+" is being persisted with thread "+Thread.currentThread());
         //System.err.println("The proc "+key+" is being persisted:");
         SharkEngineManager
            .getInstance()
            .getInstancePersistenceManager()
            .persist(createAndFillPersistentObject(), justCreated, t);
         //persistProcessContext(t);
         //persistActivityToFollowedTransitions(t);
         this.justCreated=false;
      } catch (Exception pe) {
         //pe.printStackTrace();
         throw new TransactionException("Exc when persisting process "+key,pe);
      }
   }

   protected void persistProcessContext (SharkTransaction t) throws BaseException {
      try {
         if (variableIdsToPersist.size()==0) return;
         PersistentManagerInterface pmgr = SharkEngineManager
            .getInstance()
            .getInstancePersistenceManager();
         //System.out.println("Persisting proc var map="+getContext(t));
         Iterator it=getContext(t).entrySet().iterator();
         while (it.hasNext()) {
            Map.Entry me=(Map.Entry)it.next();
            String defId=(String)me.getKey();
            if (variableIdsToPersist.contains(defId)) {
               Object val=me.getValue();
               ProcessVariablePersistenceInterface var= pmgr.createProcessVariable();
               var.setProcessId(key);
               var.setDefinitionId(defId);
               var.setValue(val);
               //System.out.println("Persisting process variable "+defId+", val="+val+" for proc "+key);
               pmgr.persist(var, justCreatedVariables, t);
            }
         }
         variableIdsToPersist.clear();
         this.justCreatedVariables=false;
      } catch (PersistenceException pe) {
         throw new BaseException(pe);
      }
   }

   public void delete (SharkTransaction t) throws TransactionException {
      //if (isDeleted) return;
      //      try {System.err.println("Deleting process "+key);}catch (Exception ex){};
      //System.err.println("I'm going to delete activities "+getActivityList());

      try {
         //deleteProcessContext();

         //Iterator it=getActivityList(t).iterator();
         /*Iterator it=getAllActivities(t).iterator();
          while (it.hasNext()) {
          ((WfActivityInternal)it.next()).delete(t);
          }*/
         //activeActivitiesMap=null;
         SharkEngineManager
            .getInstance()
            .getInstancePersistenceManager()
            .deleteProcess(key,false,t);

         //if (processContext!=null) processContext.clear();
         //System.out.println("Removing process "+key+" from cache");
         SharkUtilities.removeProcessFromCache(t,this);
         //isDeleted=true;
         //System.out.println("Deleting of process "+key+" finished");
      } catch (Exception ex) {
         //ex.printStackTrace();
         throw new TransactionException("Exception while deleting process", ex);
      }
   }

   public void mandatoryDelete (SharkTransaction t) throws TransactionException {
      try {
         SharkEngineManager
            .getInstance()
            .getInstancePersistenceManager()
            .deleteProcess(key,true,t);

         SharkUtilities.removeProcessFromCache(t,this);
      } catch (Exception ex) {
         throw new TransactionException("Exception while deleting process", ex);
      }
   }

   /*protected void deleteProcessContext () throws TransactionException {
    try {
    PersistentManagerInterface pm=SharkEngineManager.getInstance().getPersistentManager();
    Iterator it=getContext().keySet().iterator();
    while (it.hasNext()) {
    String defId=(String)it.next();
    ProcessVariablePersistenceInterface var=pm.createProcessVariable();

    var.setProcessId(key);
    var.setDefinitionId(defId);
    pm.delete(var,SharkEngineManager.getTransaction());
    }
    } catch (Exception ex) {
    throw new TransactionException("Exception while deleting process context");
    }
    }*/

   protected XMLCollectionElement getXPDLObject (SharkTransaction t) throws BaseException {
      return getProcessDefinition(t);
   }

   public Map getContext (SharkTransaction t) throws BaseException {
      if (processContext==null) {
         setProcessVariables(t);
      }
      return processContext;
   }

   public Evaluator evaluator (SharkTransaction t) throws RootException {
      if (evaluator==null) {
         evaluator= SharkEngineManager
            .getInstance()
            .getScriptingManager()
            .getEvaluator(t,SharkUtilities.getScriptType(pkgId,mgrVer));
      }
      return evaluator;
   }

   private ProcessPersistenceInterface createAndFillPersistentObject () {
      ProcessPersistenceInterface po=
         SharkEngineManager.getInstance().getInstancePersistenceManager().createProcess();
      fillPersistentObject(po);
      return po;
   }

   private void fillPersistentObject (ProcessPersistenceInterface po) {
      po.setId(this.key);
      po.setActivityRequesterId(this.actRequesterId);
      po.setActivityRequestersProcessId(this.actRequestersProcessId);
      po.setResourceRequesterId(this.resRequesterId);
      po.setExternalRequesterClassName(this.externalRequesterClassName);
      po.setState(this.state);
      po.setLastStateTime(this.lastStateTime);
      po.setCreatedTime(this.creationTime);
      po.setStartedTime(this.startTime);
      po.setProcessMgrName(managerName);
      po.setName(this.name);
      po.setDescription(this.description);
      po.setPriority(this.priority);
      po.setLimitTime(limitTime);
   }

   private void restore (ProcessPersistenceInterface po) {
      this.key=po.getId();
      this.actRequesterId=po.getActivityRequesterId();
      this.actRequestersProcessId=po.getActivityRequestersProcessId();
      this.resRequesterId=po.getResourceRequesterId();
      this.externalRequesterClassName=po.getExternalRequesterClassName();
      this.state=po.getState();
      this.lastStateTime = po.getLastStateTime();
      this.creationTime = po.getCreatedTime();
      this.startTime = po.getStartedTime();
      this.managerName=po.getProcessMgrName();
      this.name=po.getName();
      this.description=po.getDescription();
      this.priority=po.getPriority();
      this.limitTime=po.getLimitTime();
      
      setXPDLAttribs();
   }

   public final String package_id(SharkTransaction t) throws BaseException {
      return pkgId;
   }

   public WfActivityInternal getActiveActivity (SharkTransaction t, String actId) throws BaseException {
      return (WfActivityInternal)getActiveActivitiesMap(t).get(actId);
   }

   protected Map getActiveActivitiesMap (SharkTransaction t) throws BaseException {
      if (activeActivitiesMap==null) {
         clearTmpActiveActivityMap();
         try {
            SharkEngineManager sharkEngineManager=SharkEngineManager.getInstance();
            List l=sharkEngineManager.getInstancePersistenceManager().getAllActiveActivitiesForProcess(key, t);
            activeActivitiesMap=new HashMap();
            for (int i=0; i<l.size(); i++) {
               ActivityPersistenceInterface po=(ActivityPersistenceInterface)l.get(i);
               WfActivityInternal act=sharkEngineManager.
                  getObjectFactory().
                  createActivity(po,this);
               activeActivitiesMap.put(po.getId(),act);
            }
            //System.err.println("AllProcesses for mgr "+mgr.getProcessDefinitionId()+" are "+ret);

         } catch (Exception ex) {
            throw new BaseException(ex);
         }

      }
      return activeActivitiesMap;
   }

   protected void clearTmpActiveActivityMap () {
      if (tmpActivitiesMap!=null) {
         tmpActivitiesMap.clear();
      }
   }

   protected WfActivityInternal getTmpActivity (String actId) {
      if (tmpActivitiesMap==null) {
         tmpActivitiesMap=new HashMap();
      }
      return (WfActivityInternal)tmpActivitiesMap.get(actId);
   }

   protected void addToTmpActivitiesMap (String actId,WfActivityInternal act) {
      if (tmpActivitiesMap==null) {
         tmpActivitiesMap=new HashMap();
      }
      tmpActivitiesMap.put(actId,act);
   }

   public List getActiveActivities (SharkTransaction t) throws BaseException {
      return new ArrayList(getActiveActivitiesMap(t).values());
   }

   public java.util.List getAllActivities (SharkTransaction t) throws BaseException {
      try {
         clearTmpActiveActivityMap();
         SharkEngineManager sharkEngineManager=SharkEngineManager.getInstance();
         List l=sharkEngineManager.getInstancePersistenceManager().getAllActivitiesForProcess(key, t);
         List ret=new ArrayList();
         Map tmpActActivitiesMap=new HashMap();
         for (int i=0; i<l.size(); i++) {
            ActivityPersistenceInterface po=(ActivityPersistenceInterface)l.get(i);
            WfActivityInternal act=null;
            if (activeActivitiesMap!=null) {
               act=getActiveActivity(t,po.getId());
            }
            if (act==null) {
               act=sharkEngineManager.getObjectFactory().createActivity(po,this);
            }
            if (activeActivitiesMap==null) {
               if (!po.getState().startsWith(SharkConstants.STATEPREFIX_CLOSED)) {
                  tmpActActivitiesMap.put(po.getId(),act);
               }
            }
            ret.add(act);
         }
         if (activeActivitiesMap==null) {
            activeActivitiesMap=new HashMap(tmpActActivitiesMap);
         }
         //System.err.println("AllProcesses for mgr "+mgr.getProcessDefinitionId()+" are "+ret);
         return ret;
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public WfActivityInternal getActivity (SharkTransaction t, String actId) throws BaseException {
      try {
         WfActivityInternal act=null;
         if (activeActivitiesMap!=null) {
            act=getActiveActivity(t,actId);
         } else {
            act=getTmpActivity(actId);
         }

         if (act==null) {
            ActivityPersistenceInterface po=SharkEngineManager
               .getInstance()
               .getInstancePersistenceManager()
               .restoreActivity(actId,t);
            if (po!=null) {
               act=SharkEngineManager.getInstance().getObjectFactory().createActivity(po,this);
               addToTmpActivitiesMap(actId,act);
            }
         }
         return act;
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public final long getCreationTime (SharkTransaction t) throws BaseException {
      return creationTime;
   }

   public final long getStartTime (SharkTransaction t) throws BaseException {
      return startTime;
   }

   private void setXPDLAttribs () {
      this.pkgId=SharkUtilities.getProcessMgrPkgId(this.managerName);
      this.pDefId=SharkUtilities.getProcessMgrProcDefId(this.managerName);
      this.mgrVer=SharkUtilities.getProcessMgrVersion(this.managerName);
   }

   protected int restoreActivityToFollowedTransitionsMap (SharkTransaction t,Activity actDef,String asDefId) throws BaseException {
      Integer followed=(Integer)activityToFollowedTransitions.get(actDef);
      if (followed==null) {
         PersistentManagerInterface pmi=SharkEngineManager.getInstance().getInstancePersistenceManager();
         int noOfFollowed=0;
         try {
            noOfFollowed=pmi.howManyAndJoinEntries(key,
                                                   asDefId,
                                                   actDef.getId(),
                                                   t);
         } catch (Exception ex) {
            throw new BaseException (ex);
         }
         // get a list of followed transition to this activity
         followed=new Integer(noOfFollowed);
         activityToFollowedTransitions.put(actDef,followed);
      }
      return followed.intValue();
   }

   protected void persistActivityToFollowedTransitions (SharkTransaction t) throws TransactionException {
      PersistentManagerInterface pmi=SharkEngineManager.getInstance().getInstancePersistenceManager();
      Iterator it=newActivityToFollowedTransitions.entrySet().iterator();
      while (it.hasNext()) {
         Map.Entry me=(Map.Entry)it.next();
         Activity actDef=(Activity)me.getKey();
         String asDefId=null;
         Object asOrWp=actDef.getParent().getParent();
         if (asOrWp instanceof ActivitySet) {
            asDefId=((ActivitySet)asOrWp).getId();
         }
         Set actInstances=(Set)me.getValue();
         if (actInstances==null || actInstances.size()==0) {
            try {
               pmi.deleteAndJoinEntries(key,asDefId,actDef.getId(),t);
            } catch (Exception ex) {
               throw new TransactionException(ex);
            }
         } else {
            Iterator aInst=actInstances.iterator();
            while (aInst.hasNext()) {
               String actId=(String)aInst.next();
               AndJoinEntryInterface aji=pmi.createAndJoinEntry();
               aji.setProcessId(key);
               aji.setActivitySetDefinitionId(asDefId);
               aji.setActivityDefinitionId(actDef.getId());
               aji.setActivityId(actId);
               try {
                  pmi.persist(aji,t);
               } catch (Exception ex) {
                  throw new TransactionException (ex);
               }

            }
         }
      }
      newActivityToFollowedTransitions.clear();
   }

   // Runs a BLOCK activity
   protected void runBlock(SharkTransaction t,Activity bActivity,WfActivityInternal blockActivity) throws BaseException, ToolAgentGeneralException {
      BlockActivity ba=bActivity.getActivityTypes().getBlockActivity();
      if (ba!=null) {
         String asId=ba.getBlockId();
         ActivitySet as=XMLUtil.getWorkflowProcess(bActivity).getActivitySet(asId);
         if (as!=null) {
            List starts=as.getStartingActivities();
            for (Iterator it=starts.iterator(); it.hasNext();) {
               Activity act=(Activity)it.next();
               startActivity(t,asId,act,blockActivity);
            }
         }
      }
   }

   protected void addToActiveActivities (SharkTransaction t,Activity aDef,WfActivityInternal act) throws BaseException{
      getActiveActivitiesMap(t).put(act.key(t),act);
   }

   public void checkDeadlines (SharkTransaction t) throws BaseException {
      // check only open.running processes
      if (!state(t).equals(SharkConstants.STATE_OPEN_RUNNING)) return;

      List activeActs=getActiveActivities(t);
      long timeLimitBoundary=System.currentTimeMillis();
      Map actToExcNames=new HashMap();
      for (int i=0; i<activeActs.size(); i++) {
         WfActivityInternal act=(WfActivityInternal)activeActs.get(i);
         if (act.block_activity_id(t)==null) {

            Map ataens=new HashMap();
            boolean syncDeadlineHappened=act.checkDeadlines(t,timeLimitBoundary,ataens);

            if (syncDeadlineHappened) {
               continue;
            }

            if (ataens!=null || ataens.size()>0) {
               actToExcNames.putAll(ataens);
            }
         }
      }
      if (actToExcNames.size()>0) {
         handleBrokenAsyncDeadlines(t,actToExcNames);
      }
   }

   public void checkDeadline (SharkTransaction t,String actId) throws BaseException {
      // check only open.running processes
      if (!state(t).equals(SharkConstants.STATE_OPEN_RUNNING)) return;

      WfActivityInternal act=getActiveActivity(t,actId);
      if (act==null) throw new BaseException("There is no active activity with Id="+actId+" within the process with Id="+key);
      Map actToExcNames=new HashMap();
      boolean syncDeadlineHappened=act.checkDeadlines(t,System.currentTimeMillis(),actToExcNames);
      if (syncDeadlineHappened) {
         //addToPersistenceList(act);
      }
      if (actToExcNames!=null && actToExcNames.size()>0) {
         handleBrokenAsyncDeadlines(t,actToExcNames);
      }
   }

   private void handleBrokenAsyncDeadlines(SharkTransaction t,Map actToExcNames) throws BaseException {
      WorkflowProcess wp=getProcessDefinition(t);
      Iterator excs=actToExcNames.entrySet().iterator();
      while (excs.hasNext()) {
         Map.Entry me=(Map.Entry)excs.next();
         WfActivityInternal act=(WfActivityInternal)me.getKey();
         List excNames=(List)me.getValue();
         WfActivityInternal blockActivity=act.block_activity(t);
         Activity aDef=SharkUtilities.getActivityDefinition(t,act,wp,blockActivity);
         List resultingTransitions=new ArrayList();
         for (Iterator it=excNames.iterator(); it.hasNext();) {
            List ets=getExceptionTransFrom(t,act,aDef,(String)it.next());
            Iterator itets=ets.iterator();
            while (itets.hasNext()) {
               Object trans=itets.next();
               if (!resultingTransitions.contains(trans)) {
                  resultingTransitions.add(trans);
               }
            }
         }
         for (Iterator it=resultingTransitions.iterator(); it.hasNext();) {
            Transition trans=(Transition)it.next();
            try {
               Activity actToDef=trans.getToActivity();
               String asDefId=null;
               Object owner=actToDef.getParent().getParent();
               if (owner instanceof ActivitySet) {
                  asDefId=((ActivitySet)owner).getId();
               }
               startActivity(t,asDefId,actToDef,blockActivity);
            } catch (ToolAgentGeneralException tage) {
               throw new BaseException(tage);
            }
         }
      }
   }

   public List getAllActiveActivitiesForBlockActivity (SharkTransaction t,String blockActivityId) throws BaseException {
      List allActiveActivities=getActiveActivities(t);
      Iterator it=allActiveActivities.iterator();
      List ret=new ArrayList();
      while (it.hasNext()) {
         WfActivityInternal act=(WfActivityInternal)it.next();
         if (blockActivityId.equals(act.block_activity_id(t))) {
            ret.add(act);
         }
      }
      return ret;
   }

   protected void notifyStart (SharkTransaction t,Map context,long runtime) throws BaseException {
      LimitAgentManager mgr = SharkEngineManager.getInstance().getLimitAgentManager();
      try {
         mgr.notifyStart(key,null,context,runtime);
      } catch (LimitAgentException e) {
         throw new BaseException("Unable to register time limit for process "+this+" with limit agent",e);
      }
   }

}



