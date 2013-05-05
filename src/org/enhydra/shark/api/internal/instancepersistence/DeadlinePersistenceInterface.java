package org.enhydra.shark.api.internal.instancepersistence;

public interface DeadlinePersistenceInterface {

   public void setProcessId (String procId);

   public String getProcessId ();

   public void setActivityId (String actId);

   public String getActivityId ();

   public void setTimeLimit (long timeLimit);

   public long getTimeLimit ();

   public void setExceptionName (String exceptionName);

   public String getExceptionName ();

   public void setSynchronous (boolean synchronous);

   public boolean isSynchronous ();

   public void setExecuted (boolean isExecuted);
   
   public boolean isExecuted ();
   
   public void setUniqueId (String uniqueId);
   
   public String getUniqueId ();
}
