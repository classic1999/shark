package org.enhydra.shark.api.internal.eventaudit;



public interface CreateProcessEventAuditPersistenceInterface extends EventAuditPersistenceInterface {



   public void setPActivityId (String paId);



   public String getPActivityId ();



   public void setPProcessId (String ppId);



   public String getPProcessId ();



   public void setPProcessName (String ppn);



   public String getPProcessName ();



   public void setPProcessDefinitionName (String ppdn);



   public String getPProcessDefinitionName ();



   public void setPProcessDefinitionVersion (String ppdv);



   public String getPProcessDefinitionVersion ();



   public void setPActivityDefinitionId (String padId);



   public String getPActivityDefinitionId ();



   public void setPActivitySetDefinitionId (String pasdId);



   public String getPActivitySetDefinitionId ();



   public void setPProcessDefinitionId (String ppdId);



   public String getPProcessDefinitionId ();



   public void setPPackageId (String ppkgId);



   public String getPPackageId ();



}

