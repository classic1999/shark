package org.enhydra.shark.api.internal.eventaudit;



public interface EventAuditPersistenceInterface {



   public void setUTCTime (String ts);



   public String getUTCTime ();



   public void setType (String t);



   public String getType ();



   public void setActivityId (String aId);



   public String getActivityId ();



   public void setActivityName (String an);



   public String getActivityName ();



   public void setProcessId (String pId);



   public String getProcessId ();



   public void setProcessName (String pn);



   public String getProcessName ();



   public void setProcessDefinitionName (String pdn);



   public String getProcessDefinitionName ();



   public void setProcessDefinitionVersion (String pdv);



   public String getProcessDefinitionVersion ();



   public void setActivityDefinitionId (String adId);



   public String getActivityDefinitionId ();


   public void setActivitySetDefinitionId (String asdId);


   public String getActivitySetDefinitionId ();


   public void setProcessDefinitionId (String pdId);



   public String getProcessDefinitionId ();



   public void setPackageId (String pkgId);



   public String getPackageId ();



}

