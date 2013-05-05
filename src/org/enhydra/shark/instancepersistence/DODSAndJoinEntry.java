package org.enhydra.shark.instancepersistence;


import org.enhydra.shark.api.internal.instancepersistence.*;

public class DODSAndJoinEntry implements AndJoinEntryInterface {

   private String processId;
   private String activitySetDefinitionId;
   private String activityDefinitionId;
   private String activityId;

   public void setProcessId (String procId) {
      this.processId=procId;
   }

   public String getProcessId () {
      return processId;
   }

   public void setActivitySetDefinitionId (String asdId) {
      this.activitySetDefinitionId=asdId;
   }

   public String getActivitySetDefinitionId () {
      return activitySetDefinitionId;
   }

   public void setActivityDefinitionId (String adId) {
      this.activityDefinitionId=adId;
   }

   public String getActivityDefinitionId () {
      return activityDefinitionId;
   }

   public void setActivityId (String actId) {
      activityId=actId;
   }

   public String getActivityId () {
      return activityId;
   }

}
