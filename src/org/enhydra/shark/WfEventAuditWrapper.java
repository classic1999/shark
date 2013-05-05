package org.enhydra.shark;

import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.timebase.UtcT;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfmodel.SourceNotAvailable;
import org.enhydra.shark.api.client.wfmodel.WfExecutionObject;
import org.enhydra.shark.api.internal.eventaudit.EventAuditPersistenceInterface;
import org.enhydra.shark.api.internal.working.*;
import org.enhydra.shark.utilities.MiscUtilities;

/**
 * WfEventAuditImpl - Workflow Event Audit implementation
 *
 * @author Sasa Bojanic
 */
public abstract class WfEventAuditWrapper implements WfEventAuditInternal {

   protected String userAuth;

   protected UtcT timeStamp;
   protected String eventType;
   protected String activityId;
   protected String activityName;
   protected String processId;
   protected String processName;
   protected String processMgrName;
   protected String processMgrVersion;
   protected String activityDefinitionId;
   protected String activitySetDefinitionId;
   protected String processDefinitionId;
   protected String packageId;

   protected WfEventAuditWrapper(SharkTransaction t,WfExecutionObjectInternal object,String eventType) {
      this.timeStamp = new UtcT(
         MiscUtilities.getAbsoluteTimeInUTCFormat(),0,(short)0,(short)0);
      initEventObject(t,object,eventType);
   }

   protected WfEventAuditWrapper(String userAuth,EventAuditPersistenceInterface po){
      this.userAuth=userAuth;
      restore(po);
   }

   public WfEventAuditWrapper () {}

   // NOTE: this method should not be called from kernel
   public WfExecutionObject source () throws BaseException, SourceNotAvailable {
      WfExecutionObject ret=null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = source(t);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof SourceNotAvailable)
            throw (SourceNotAvailable)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   // NOTE: this method should not be called from kernel
   public WfExecutionObject source (SharkTransaction t) throws BaseException, SourceNotAvailable {
      if (activityId!=null) {
         WfActivityInternal act=SharkUtilities.getActivity(t,processId,activityId);
         if (act!=null) {
            return SharkEngineManager.getInstance().getObjectFactory().createActivityWrapper(userAuth,act.manager_name(t),processId,activityId);
         } else {
            throw new SourceNotAvailable("The source of event audit is not available!");
         }
      } else {
         WfProcessInternal proc=SharkUtilities.getProcess(t,processId);
         if (proc!=null) {
            return SharkEngineManager.getInstance().getObjectFactory().createProcessWrapper(userAuth,proc.manager_name(t),processId);
         } else {
            throw new SourceNotAvailable("The source of event audit is not available!");
         }
      }
   }

   public UtcT time_stamp () throws BaseException {
      return timeStamp;
   }

   public String event_type () throws BaseException {
      return eventType;
   }

   public String activity_key () throws BaseException {
      return activityId;
   }

   public String activity_name () throws BaseException {
      return activityName;
   }

   public String process_key () throws BaseException {
      return processId;
   }

   public String process_name () throws BaseException {
      return processName;
   }

   public String process_mgr_name () throws BaseException {
      return processMgrName;
   }

   public String process_mgr_version () throws BaseException {
      return processMgrVersion;
   }

   public String activity_definition_id () throws BaseException {
      return activityDefinitionId;
   }

   public String activity_set_definition_id () throws BaseException {
      return activitySetDefinitionId;
   }

   public String process_definition_id () throws BaseException {
      return processDefinitionId;
   }

   public String package_id () throws BaseException {
      return packageId;
   }

   protected void fillPersistentObject (EventAuditPersistenceInterface po) {
      po.setUTCTime(String.valueOf(this.timeStamp.time));
      po.setType(this.eventType);
      po.setActivityId(this.activityId);
      po.setActivityName(this.activityName);
      po.setProcessId(this.processId);
      po.setProcessName(this.processName);
      po.setProcessDefinitionName(this.processMgrName);
      po.setProcessDefinitionVersion(this.processMgrVersion);
      po.setActivityDefinitionId(this.activityDefinitionId);
      po.setActivitySetDefinitionId(this.activitySetDefinitionId);
      po.setProcessDefinitionId(this.processDefinitionId);
      po.setPackageId(this.packageId);
   }

   protected void restore (EventAuditPersistenceInterface po) {
      try {
         this.timeStamp = new UtcT(Long.parseLong(po.getUTCTime()),0,(short)0,(short)0);
      } catch (Exception ex) {}
      this.eventType=po.getType();
      this.activityId=po.getActivityId();
      this.activityName=po.getActivityName();
      this.processId=po.getProcessId();
      this.processName=po.getProcessName();
      this.processMgrName=po.getProcessDefinitionName();
      this.processMgrVersion=po.getProcessDefinitionVersion();
      this.activityDefinitionId=po.getActivityDefinitionId();
      this.activitySetDefinitionId=po.getActivitySetDefinitionId();
      this.processDefinitionId=po.getProcessDefinitionId();
      this.packageId=po.getPackageId();
   }

   protected void initEventObject (SharkTransaction t,WfExecutionObjectInternal object,String eventType) {
      this.eventType=eventType;
      try {
         if (object instanceof WfProcessInternal) {
            this.processId=object.key(t);
            this.processName=object.name(t);
            this.processMgrName=((WfProcessInternal)object).manager_name(t);
            this.processMgrVersion=((WfProcessInternal)object).manager_version(t);
            this.processDefinitionId=((WfProcessInternal)object).process_definition_id(t);
            this.packageId=((WfProcessInternal)object).package_id(t);
         } else if (object instanceof WfActivityInternal) {
            this.activityId=object.key(t);
            this.activityName=object.name(t);
            this.processId=((WfActivityInternal)object).container(t).key(t);
            this.processName=((WfActivityInternal)object).container(t).name(t);
            this.processMgrName=((WfActivityInternal)object).container(t).manager_name(t);
            this.processMgrVersion=((WfActivityInternal)object).container(t).manager_version(t);
            this.activityDefinitionId=((WfActivityInternal)object).activity_definition_id(t);
            this.activitySetDefinitionId=((WfActivityInternal)object).activity_set_definition_id(t);
            this.processDefinitionId=((WfActivityInternal)object).container(t).process_definition_id(t);
            this.packageId=((WfActivityInternal)object).container(t).package_id(t);
         }
      } catch (Exception ex) {ex.printStackTrace();}
   }

}
