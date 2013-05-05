package org.enhydra.shark.api.internal.instancepersistence;

public interface ActivityPersistenceInterface {

   public void setId (String id);

   public String getId ();

   public void setActivitySetDefinitionId (String asdId);

   public String getActivitySetDefinitionId ();

   public void setActivityDefinitionId (String adId);

   public String getActivityDefinitionId ();

   public void setProcessMgrName (String mgrName);

   public String getProcessMgrName ();

   public void setProcessId (String procId);

   public String getProcessId ();

   public void setSubflowProcessId (String procId);

   public String getSubflowProcessId ();

   public void setSubflowAsynchronous (boolean isSubflowAsynchronous);

   public boolean isSubflowAsynchronous ();

   public void setResourceUsername (String resourceUsername);

   public String getResourceUsername ();

   public void setState (String state);

   public String getState ();

   public void setBlockActivityId (String baId);

   public String getBlockActivityId ();

   public String getName ();

   public void setName (String name);

   public String getDescription ();

   public void setDescription (String desc);

   public short getPriority ();

   public void setPriority (short priority);

   public long getLastStateTime();

   public void setLastStateTime(long timestamp);

   public long getAcceptedTime();

   public void setAcceptedTime(long timestamp);

   public long getActivatedTime();

   public void setActivatedTime(long timestamp);

   public long getLimitTime();

   public void setLimitTime(long timestamp);

}
