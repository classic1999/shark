package org.enhydra.shark.api.internal.instancepersistence;

public interface AssignmentPersistenceInterface {

   public void setActivityId (String actId);

   public String getActivityId ();

   public void setResourceUsername (String username);

   public String getResourceUsername ();

   public void setProcessMgrName (String mgrName);

   public String getProcessMgrName ();

   public void setProcessId (String procId);

   public String getProcessId ();

   public void setValid (boolean isValid);
   
   public boolean isValid ();

   public void setAccepted (boolean isAccepted);
   
   public boolean isAccepted ();

}
