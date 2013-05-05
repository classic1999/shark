package org.enhydra.shark.eventaudit;


import org.enhydra.shark.api.internal.eventaudit.*;

public class DODSStateEventAudit extends DODSEventAudit implements StateEventAuditPersistenceInterface {

   private String oldState;
   private String newState;

   public void setOldState (String os) {
      this.oldState=os;
   }

   public String getOldState () {
      return oldState;
   }

   public void setNewState (String ns) {
      this.newState=ns;
   }

   public String getNewState () {
      return newState;
   }

}
