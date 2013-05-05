package org.enhydra.shark.api.internal.instancepersistence;


public interface ProcessPersistenceInterface {

   public void setId (String id);

   public String getId ();

   public void setProcessMgrName (String mgrName);

   public String getProcessMgrName ();

   public void setExternalRequesterClassName (String extReqFullClassName);

   public String getExternalRequesterClassName ();

   public void setActivityRequesterId (String actReqId);

   public String getActivityRequesterId ();

   public void setActivityRequestersProcessId (String actReqProcId);

   public String getActivityRequestersProcessId ();

   public void setResourceRequesterId (String resReqId);

   public String getResourceRequesterId ();

   public void setState (String state);

   public String getState ();

   public String getName ();

   public void setName (String name);

   public String getDescription ();

   public void setDescription (String desc);

   public short getPriority ();

   public void setPriority (short priority);

   public long getLastStateTime();

   public void setLastStateTime(long timestamp);

   public long getCreatedTime();

   public void setCreatedTime(long timestamp);

   public long getStartedTime();

   public void setStartedTime(long timestamp);

   public long getLimitTime();

   public void setLimitTime(long timestamp);

}
