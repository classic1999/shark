package org.enhydra.shark;

import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.TransactionException;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.internal.eventaudit.EventAuditException;
import org.enhydra.shark.api.internal.eventaudit.EventAuditManagerInterface;
import org.enhydra.shark.api.internal.eventaudit.EventAuditPersistenceInterface;
import org.enhydra.shark.api.internal.eventaudit.StateEventAuditPersistenceInterface;
import org.enhydra.shark.api.internal.working.WfExecutionObjectInternal;
import org.enhydra.shark.api.internal.working.WfStateEventAuditInternal;

/**
 * WfStateEventAuditImpl - Workflow Event Audit implementation.
 * @author Sasa Bojanic
 */
public class WfStateEventAuditWrapper extends WfEventAuditWrapper implements WfStateEventAuditInternal {
   
   private String oldState;
   private String newState;
   
   protected WfStateEventAuditWrapper(SharkTransaction t,WfExecutionObjectInternal object,String eventType,
                                      String oldState,String newState) throws BaseException {
      super(t,object,eventType);

      this.oldState=oldState;
      this.newState=newState;

      try {
         persist(t);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   /**
    * Used to create object when restoring it from database.
    */
   protected WfStateEventAuditWrapper (String userAuth,StateEventAuditPersistenceInterface po) {
      super(userAuth,po);
   }
   
   
   public String old_state () throws BaseException {
      return oldState;
   }
   
   public String new_state () throws BaseException {
      return newState;
   }
   
   public void persist (SharkTransaction t) throws TransactionException {
      try {
         EventAuditManagerInterface eam=SharkEngineManager.getInstance().getEventAuditManager();
         if (null == eam)
            return;
         StateEventAuditPersistenceInterface po=eam.createStateEventAudit();
         fillPersistentObject(po);
         eam.persist(po, t);
      } catch (EventAuditException pe) {
         pe.printStackTrace();
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
      StateEventAuditPersistenceInterface spo=(StateEventAuditPersistenceInterface)po;
      spo.setOldState(this.oldState);
      spo.setNewState(this.newState);
   }
   
   protected void restore (EventAuditPersistenceInterface po) {
      super.restore(po);
      StateEventAuditPersistenceInterface spo=(StateEventAuditPersistenceInterface)po;
      this.oldState=spo.getOldState();
      this.newState=spo.getNewState();
   }
   
}
