package org.enhydra.shark.eventaudit;

import java.util.Map;

import org.enhydra.shark.api.internal.eventaudit.DataEventAuditPersistenceInterface;

public class DODSDataEventAudit extends DODSEventAudit implements DataEventAuditPersistenceInterface {

   private Map oldData;
   private Map newData;

   public void setOldData (Map od) {
      this.oldData=od;
   }

   public Map getOldData () {
      return oldData;
   }

   public void setNewData (Map nd) {
      this.newData=nd;
   }

   public Map getNewData () {
      return newData;
   }

}
