package org.enhydra.shark.instancepersistence;


import org.enhydra.shark.api.internal.instancepersistence.*;

public class DODSAssignment implements AssignmentPersistenceInterface {

   private String activityId;
   private String resourceUsername;

   // this is redundant information, but we need it because of transaction locking
   private String managerName;
   private String processId;

   private boolean isValid;
   private boolean isAccepted;
   
   public void setActivityId (String actId) {
      this.activityId=actId;
   }

   public String getActivityId () {
      return activityId;
   }

   public void setResourceUsername (String resUsername) {
      this.resourceUsername=resUsername;
   }

   public String getResourceUsername () {
      return resourceUsername;
   }

   public void setProcessMgrName (String mgrName) {
      this.managerName=mgrName;
   }

   public String getProcessMgrName () {
      return managerName;
   }

   public void setProcessId (String procId) {
      this.processId=procId;
   }

   public String getProcessId () {
      return processId;
   }

   public void setValid (boolean isValid) {
      this.isValid=isValid;
   }
   
   public boolean isValid () {
      return isValid;
   }
      
   public void setAccepted (boolean isAccepted) {
      this.isAccepted=isAccepted;
   }
   
   public boolean isAccepted () {
      return isAccepted;
   }

}
