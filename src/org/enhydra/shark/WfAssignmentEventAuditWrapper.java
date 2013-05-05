package org.enhydra.shark;

import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.TransactionException;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.common.SharkConstants;
import org.enhydra.shark.api.internal.eventaudit.AssignmentEventAuditPersistenceInterface;
import org.enhydra.shark.api.internal.eventaudit.EventAuditException;
import org.enhydra.shark.api.internal.eventaudit.EventAuditManagerInterface;
import org.enhydra.shark.api.internal.eventaudit.EventAuditPersistenceInterface;
import org.enhydra.shark.api.internal.working.WfActivityInternal;
import org.enhydra.shark.api.internal.working.WfAssignmentEventAuditInternal;
import org.enhydra.shark.api.internal.working.WfResourceInternal;

/**
 * WfEventAuditImpl - Workflow Event Audit implementation
 *
 * @author Sasa Bojanic
 */
public class WfAssignmentEventAuditWrapper extends WfEventAuditWrapper
   implements WfAssignmentEventAuditInternal {
   
   //   private WfActivity activity;
   
   private String oldResourceKey;
   private String oldResourceName;
   private String newResourceKey;
   private String newResourceName;
   private boolean isAccepted;
   
   protected WfAssignmentEventAuditWrapper(SharkTransaction t,WfActivityInternal activity,WfResourceInternal oldRes,
                                           WfResourceInternal newRes,boolean isAccepted)
      throws BaseException {
      super(t,activity,SharkConstants.EVENT_ACTIVITY_ASSIGNMENT_CHANGED);
      
      if (oldRes!=null) {
         this.oldResourceKey=oldRes.resource_key(t);
         this.oldResourceName=oldRes.resource_name(t);
      }
      this.newResourceKey=newRes.resource_key(t);
      this.newResourceName=newRes.resource_name(t);
      this.isAccepted=((WfActivityInternal)activity).accepted_status(t);
      this.isAccepted=isAccepted;
      try {
         persist(t);
       } catch (Exception ex) {
         throw new BaseException(ex);
       }
   }
   
   /**
    * Used to create object when restoring it from database.
    */
   protected WfAssignmentEventAuditWrapper (String userAuth,AssignmentEventAuditPersistenceInterface po) {
      super(userAuth,po);
   }
   
   public String old_resource_key () throws BaseException {
      return oldResourceKey;
   }
   
   public String old_resource_name () throws BaseException {
      return oldResourceName;
   }
   
   public String new_resource_key () throws BaseException {
      return newResourceKey;
   }
   
   public String new_resource_name () throws BaseException {
      return newResourceName;
   }
   
   public boolean is_accepted () throws BaseException {
      return isAccepted;
   }
   
   public void persist (SharkTransaction t) throws TransactionException {
      try {
         EventAuditManagerInterface eam=SharkEngineManager.getInstance().getEventAuditManager();
         if (null == eam)
            return;
         AssignmentEventAuditPersistenceInterface po=
            eam.createAssignmentEventAudit();
         fillPersistentObject(po);
         eam.persist(po,t);
      } catch (EventAuditException pe) {
         //pe.printStackTrace();
         throw new TransactionException(pe);
      }
   }
   
   public void refresh () {
      //
   }
   
   public void delete (SharkTransaction t) throws TransactionException{
      //
   }
   
   protected void fillPersistentObject (EventAuditPersistenceInterface po) {
      super.fillPersistentObject(po);
      AssignmentEventAuditPersistenceInterface apo=(AssignmentEventAuditPersistenceInterface)po;
      apo.setOldResourceUsername(this.oldResourceKey);
      apo.setOldResourceName(this.oldResourceName);
      apo.setNewResourceUsername(this.newResourceKey);
      apo.setNewResourceName(this.newResourceName);
      apo.setIsAccepted(this.isAccepted);
   }
   
   protected void restore (EventAuditPersistenceInterface po) {
      super.restore(po);
      AssignmentEventAuditPersistenceInterface apo=(AssignmentEventAuditPersistenceInterface)po;
      this.oldResourceKey=apo.getOldResourceUsername();
      this.oldResourceName=apo.getOldResourceName();
      this.newResourceKey=apo.getNewResourceUsername();
      this.newResourceName=apo.getNewResourceName();
      this.isAccepted=apo.getIsAccepted();
   }
   
}
