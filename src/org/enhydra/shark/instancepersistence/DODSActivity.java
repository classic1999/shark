package org.enhydra.shark.instancepersistence;


import org.enhydra.shark.api.internal.instancepersistence.*;

public class DODSActivity implements ActivityPersistenceInterface {

   private String id;
   private String activitySetDefinitionId;
   private String activityDefinitionId;
   private String managerName;
   private String processId;
   private String resourceUsername;
   private String subflowProcessId;
   private boolean isSubflowAsynchronous;
   private String state;
   private String blockActivityId;
   private String name;
   private String description;
   private short priority;
   private long lastStateTime;
   private long accepted;
   private long activated;
   private long limitTime;

   public void setId (String id) {
      this.id=id;
   }

   public String getId () {
      return id;
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

   public void setResourceUsername (String username) {
      this.resourceUsername=username;
   }

   public String getResourceUsername () {
      return resourceUsername;
   }

   public void setSubflowProcessId(String procId) {
      this.subflowProcessId=procId;
   }

   public String getSubflowProcessId() {
      return subflowProcessId;
   }

   public void setSubflowAsynchronous (boolean isSubflowAsynchronous) {
      this.isSubflowAsynchronous=isSubflowAsynchronous;
   }

   public boolean isSubflowAsynchronous () {
      return isSubflowAsynchronous;
   }

   public void setState (String state) {
      this.state=state;
   }

   public String getState () {
      return state;
   }

   public void setBlockActivityId (String baId) {
      blockActivityId=baId;
   }

   public String getBlockActivityId () {
      return blockActivityId;
   }

   public String getName () {
      return name;
   }

   public void setName (String name) {
      this.name=name;
   }

   public String getDescription () {
      return description;
   }

   public void setDescription (String desc) {
      this.description=desc;
   }

   public short getPriority () {
      return priority;
   }

   public void setPriority (short priority) {
      this.priority=priority;
   }

   public long getLastStateTime() {
      return lastStateTime;
   }

   public void setLastStateTime(long timestamp) {
      lastStateTime = timestamp;
   }

   public long getActivatedTime() {
      return activated;
   }

   public void setAcceptedTime(long timestamp) {
      accepted = timestamp;
   }

   public long getAcceptedTime() {
      return accepted;
   }

   public void setActivatedTime(long timestamp) {
      activated = timestamp;
   }

   public long getLimitTime() {
      return limitTime;
   }

   public void setLimitTime(long timestamp) {
      limitTime=timestamp;
   }

}
