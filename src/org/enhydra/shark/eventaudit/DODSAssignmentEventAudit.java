package org.enhydra.shark.eventaudit;


import org.enhydra.shark.api.internal.eventaudit.*;

public class DODSAssignmentEventAudit extends DODSEventAudit implements AssignmentEventAuditPersistenceInterface {

   private String oldResourceKey;
   private String oldResourceName;
   private String newResourceKey;
   private String newResourceName;
   private boolean isAccepted;

   public void setOldResourceUsername (String un) {
      this.oldResourceKey=un;
   }

   public String getOldResourceUsername () {
      return oldResourceKey;
   }

   public void setOldResourceName (String n) {
      this.oldResourceName=n;
   }

   public String getOldResourceName () {
      return oldResourceName;
   }

   public void setNewResourceUsername (String un) {
      this.newResourceKey=un;
   }

   public String getNewResourceUsername () {
      return newResourceKey;
   }

   public void setNewResourceName (String n) {
      this.newResourceName=n;
   }

   public String getNewResourceName () {
      return newResourceName;
   }

   public void setIsAccepted(boolean acc) {
      this.isAccepted=acc;
   }

   public boolean getIsAccepted() {
      return isAccepted;
   }

}
