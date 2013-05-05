package org.enhydra.shark;


import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.TransactionException;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.common.SharkConstants;
import org.enhydra.shark.api.internal.eventaudit.CreateProcessEventAuditPersistenceInterface;
import org.enhydra.shark.api.internal.eventaudit.EventAuditException;
import org.enhydra.shark.api.internal.eventaudit.EventAuditManagerInterface;
import org.enhydra.shark.api.internal.eventaudit.EventAuditPersistenceInterface;
import org.enhydra.shark.api.internal.working.WfActivityInternal;
import org.enhydra.shark.api.internal.working.WfCreateProcessEventAuditInternal;
import org.enhydra.shark.api.internal.working.WfProcessInternal;
import org.enhydra.shark.api.internal.working.WfRequesterInternal;

/**
 * WfCreateProcessEventAuditImpl - Workflow Event Audit implementation
 *
 * @author Sasa Bojanic
 */
public class WfCreateProcessEventAuditWrapper extends WfEventAuditWrapper
   implements WfCreateProcessEventAuditInternal {

   private String pActivityId;
   private String pProcessId;
   private String pProcessName;
   private String pProcessMgrName;
   private String pProcessMgrVersion;
   private String pActivityDefinitionId;
   private String pActivitySetDefinitionId;
   private String pProcessDefinitionId;
   private String pPackageId;

   protected WfCreateProcessEventAuditWrapper(SharkTransaction t,WfProcessInternal process,WfRequesterInternal requester)
   throws BaseException {
      super(t,process,SharkConstants.EVENT_PROCESS_CREATED);

      try {
         if ((requester!=null) && (requester instanceof WfActivityInternal)) {
            WfActivityInternal req=(WfActivityInternal)requester;
            pActivityId=req.key(t);
            pProcessId=req.container(t).key(t);
            pProcessName=req.container(t).name(t);
            pProcessMgrName=req.container(t).manager_name(t);
            pProcessMgrVersion=req.container(t).manager_version(t);
            pActivityDefinitionId=req.activity_definition_id(t);
            pActivitySetDefinitionId=req.activity_set_definition_id(t);
            pProcessDefinitionId=req.container(t).process_definition_id(t);
            pPackageId=req.container(t).package_id(t);
         }
         persist(t);
      } catch (Exception pe) {
         throw new BaseException(pe);
      }
   }

   /**
    * Used to create object when restoring it from database.
    */
   protected WfCreateProcessEventAuditWrapper (String userAuth,CreateProcessEventAuditPersistenceInterface po) {
      super(userAuth,po);
   }

   public String p_activity_key () throws BaseException {
      return pActivityId;
   }

   public String p_process_key () throws BaseException {
      return pProcessId;
   }

   public String p_process_name () throws BaseException {
      return pProcessName;
   }

   public String p_process_mgr_name () throws BaseException {
      return pProcessMgrName;
   }

   public String p_process_mgr_version () throws BaseException {
      return pProcessMgrVersion;
   }

   public String p_activity_definition_id () throws BaseException {
      return pActivityDefinitionId;
   }

   public String p_activity_set_definition_id () throws BaseException {
      return pActivitySetDefinitionId;
   }
   public String p_process_definition_id () throws BaseException {
      return pProcessMgrVersion;
   }

   public String p_package_id () throws BaseException {
      return pProcessMgrVersion;
   }

   public void persist (SharkTransaction t) throws TransactionException {
      try {
         EventAuditManagerInterface eam=SharkEngineManager.getInstance().getEventAuditManager();
         if (null == eam)
            return;
         CreateProcessEventAuditPersistenceInterface po=
            eam.createCreateProcessEventAudit();
         fillPersistentObject(po);
         eam.persist(po,t);
      } catch (EventAuditException pe) {
         throw new TransactionException(pe);
      }
   }

   public void refresh () {
      //
   }

   public void delete (SharkTransaction t) throws TransactionException {
      //
   }

   protected void fillPersistentObject (EventAuditPersistenceInterface po) {
      super.fillPersistentObject(po);
      CreateProcessEventAuditPersistenceInterface cpo=(CreateProcessEventAuditPersistenceInterface)po;
      cpo.setPActivityId(this.pActivityId);
      cpo.setPProcessId(this.pProcessId);
      cpo.setPProcessName(this.pProcessName);
      cpo.setPProcessDefinitionName(this.pProcessMgrName);
      cpo.setPProcessDefinitionVersion(this.pProcessMgrVersion);
      cpo.setPActivityDefinitionId(this.pActivityDefinitionId);
      cpo.setPActivitySetDefinitionId(this.pActivitySetDefinitionId);
      cpo.setPProcessDefinitionId(this.pProcessDefinitionId);
      cpo.setPPackageId(this.pPackageId);
   }

   protected void restore (EventAuditPersistenceInterface po) {
      super.restore(po);
      CreateProcessEventAuditPersistenceInterface cpo=(CreateProcessEventAuditPersistenceInterface)po;
      this.pActivityId=cpo.getPActivityId();
      this.pProcessId=cpo.getPProcessId();
      this.pProcessName=cpo.getPProcessName();
      this.pProcessMgrName=cpo.getPProcessDefinitionName();
      this.pProcessMgrVersion=cpo.getPProcessDefinitionVersion();
      this.pActivityDefinitionId=cpo.getPActivityDefinitionId();
      this.pActivitySetDefinitionId=cpo.getPActivitySetDefinitionId();
      this.pProcessDefinitionId=cpo.getPProcessDefinitionId();
      this.pPackageId=cpo.getPPackageId();
   }

}
