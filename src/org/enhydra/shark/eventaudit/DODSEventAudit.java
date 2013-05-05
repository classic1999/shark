package org.enhydra.shark.eventaudit;





import org.enhydra.shark.api.internal.eventaudit.*;



public class DODSEventAudit implements EventAuditPersistenceInterface {



   private String timeStamp;

   private String eventType;

   private String activityId;

   private String activityName;

   private String processId;

   private String processName;

   private String processMgrName;

   private String processMgrVersion;

   private String activityDefinitionId;

   private String activitySetDefinitionId;

   private String processDefinitionId;

   private String packageId;



   public void setUTCTime (String time) {

      this.timeStamp = time;

   }



   public String getUTCTime () {

      return timeStamp;

   }



   public void setType (String type) {

      this.eventType=type;

   }



   public String getType () {

      return eventType;

   }



   public void setActivityId (String aId) {

      this.activityId=aId;

   }



   public String getActivityId () {

      return activityId;

   }



   public void setActivityName (String an) {

      this.activityName=an;

   }



   public String getActivityName () {

      return activityName;

   }



   public void setProcessId (String pId) {

      this.processId=pId;

   }



   public String getProcessId () {

      return processId;

   }



   public void setProcessName (String n) {

      this.processName=n;

   }



   public String getProcessName () {

      return processName;

   }



   public void setProcessDefinitionName (String pn) {

      this.processMgrName=pn;

   }



   public String getProcessDefinitionName () {

      return processMgrName;

   }



   public void setProcessDefinitionVersion (String pdv) {

      this.processMgrVersion=pdv;

   }



   public String getProcessDefinitionVersion () {

      return processMgrVersion;

   }



   public void setActivityDefinitionId (String adId) {

      activityDefinitionId=adId;

   }



   public String getActivitySetDefinitionId () {

      return activitySetDefinitionId;

   }



   public void setActivitySetDefinitionId (String asdId) {

      activitySetDefinitionId=asdId;

   }


   public String getActivityDefinitionId () {

      return activityDefinitionId;

   }



   public void setProcessDefinitionId (String pdId) {

      processDefinitionId=pdId;

   }



   public String getProcessDefinitionId () {

      return processDefinitionId;

   }



   public void setPackageId (String pkgId) {

      packageId=pkgId;

   }



   public String getPackageId () {

      return packageId;

   }



}

