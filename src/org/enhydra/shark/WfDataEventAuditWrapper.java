package org.enhydra.shark;

import java.util.Map;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.TransactionException;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.internal.eventaudit.DataEventAuditPersistenceInterface;
import org.enhydra.shark.api.internal.eventaudit.EventAuditException;
import org.enhydra.shark.api.internal.eventaudit.EventAuditManagerInterface;
import org.enhydra.shark.api.internal.eventaudit.EventAuditPersistenceInterface;
import org.enhydra.shark.api.internal.working.WfDataEventAuditInternal;
import org.enhydra.shark.api.internal.working.WfExecutionObjectInternal;

/**
 * WfDataEventAuditImpl - Workflow Event Audit implementation
 *
 * @author Sasa Bojanic
 */
public class WfDataEventAuditWrapper extends WfEventAuditWrapper implements WfDataEventAuditInternal {

   private Map oldData;
   private Map newData;

   protected WfDataEventAuditWrapper(SharkTransaction t,WfExecutionObjectInternal object,String eventType,
                                     Map oldData,Map newData) throws BaseException {
      super(t,object,eventType);

      this.oldData=oldData;
      this.newData=newData;
      try {
         persist(t);
      } catch (Exception ex) {
         throw new BaseException(ex);
       }
   }

   /**
    * Used to create object when restoring it from database.
    */
   protected WfDataEventAuditWrapper (String userAuth,DataEventAuditPersistenceInterface po) {
      super(userAuth,po);
   }


   public Map old_data () {
      return oldData;
   }

   public Map new_data () {
      return newData;
   }

   public String package_id () throws BaseException {
      return packageId;
   }

   public void persist (SharkTransaction t) throws TransactionException {
      try {
         EventAuditManagerInterface eam=SharkEngineManager.getInstance().getEventAuditManager();
         if (null == eam)
            return;
         DataEventAuditPersistenceInterface po=eam.createDataEventAudit();
         fillPersistentObject(po);
         eam.persist(po, t);
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
      DataEventAuditPersistenceInterface dpo=(DataEventAuditPersistenceInterface)po;
      dpo.setOldData(oldData);
      dpo.setNewData(newData);
   }

   protected void restore (EventAuditPersistenceInterface po) {
      super.restore(po);
      DataEventAuditPersistenceInterface dpo=(DataEventAuditPersistenceInterface)po;
      oldData=dpo.getOldData();
      newData=dpo.getNewData();
   }

}
