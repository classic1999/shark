package org.enhydra.shark.instancepersistence;


import org.enhydra.shark.api.internal.instancepersistence.*;

public class DODSDeadline implements DeadlinePersistenceInterface {

   private String processId;
   private String activityId;
   private long timeLimit;
   private String exceptionName;
   private boolean isSynchronous;
   private boolean isExecuted;
   private String uniqueId;
   
   public void setProcessId (String procId) {
      this.processId=procId;
   }

   public String getProcessId () {
      return processId;
   }

   public void setActivityId (String actId) {
      this.activityId=actId;
   }

   public String getActivityId () {
      return activityId;
   }

   public void setTimeLimit (long timeLimit) {
      this.timeLimit=timeLimit;
   }

   public long getTimeLimit () {
      return timeLimit;
   }

   public void setExceptionName (String exceptionName) {
      this.exceptionName=exceptionName;
   }

   public String getExceptionName () {
      return exceptionName;
   }

   public void setSynchronous (boolean synchronous) {
      this.isSynchronous=synchronous;
   }

   public boolean isSynchronous () {
      return isSynchronous;
   }

   public void setExecuted (boolean isExecuted) {
      this.isExecuted=isExecuted;
   }
   
   public boolean isExecuted () {
      return isExecuted;
   }
   
   public void setUniqueId (String uniqueId) {
      this.uniqueId=uniqueId;
   }
   
   public String getUniqueId () {
      return uniqueId;
   }
   
}
