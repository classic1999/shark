package org.enhydra.shark.instancepersistence;


import org.enhydra.shark.api.internal.instancepersistence.*;

public class DODSProcess implements ProcessPersistenceInterface {

   private String id;
   private String managerName;
   private String extRequesterClassName;
   private String actRequesterId;
   private String actRequesterProcessId;
   private String resRequesterId;
   private String state;
   private String name;
   private String description;
   private short priority;
   private long createdTime;
   private long startedTime;
   private long lastStateTime;
   private long limitTime;

   public void setId (String id) {
      this.id=id;
   }

   public String getId () {
      return id;
   }

   public void setProcessMgrName (String mgrName) {
      this.managerName=mgrName;
   }

   public String getProcessMgrName () {
      return managerName;
   }

   public void setExternalRequesterClassName (String extReqFullClassName) {
      this.extRequesterClassName=extReqFullClassName;
   }

   public String getExternalRequesterClassName () {
      return extRequesterClassName;
   }

   public void setActivityRequesterId (String actReqId) {
      this.actRequesterId=actReqId;
   }

   public String getActivityRequesterId () {
      return actRequesterId;
   }

   public void setActivityRequestersProcessId (String actReqProcId) {
      this.actRequesterProcessId=actReqProcId;
   }

   public String getActivityRequestersProcessId () {
      return actRequesterProcessId;
   }

   public void setResourceRequesterId (String resReqId) {
      this.resRequesterId=resReqId;
   }

   public String getResourceRequesterId () {
      return resRequesterId;
   }

   public void setState (String state) {
      this.state=state;
   }

   public String getState () {
      return state;
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

   public long getCreatedTime() {
      return createdTime;
   }

   public void setCreatedTime(long timestamp) {
      createdTime=timestamp;
   }

   public long getStartedTime() {
      return startedTime;
   }

   public void setStartedTime(long timestamp) {
      startedTime = timestamp;
   }

   public long getLimitTime() {
      return limitTime;
   }

   public void setLimitTime(long timestamp) {
      limitTime=timestamp;
   }

}
