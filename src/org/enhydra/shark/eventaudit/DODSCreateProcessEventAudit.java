package org.enhydra.shark.eventaudit;





import org.enhydra.shark.api.internal.eventaudit.*;



public class DODSCreateProcessEventAudit extends DODSEventAudit implements CreateProcessEventAuditPersistenceInterface {



   private String pActivityId;

   private String pProcessId;

   private String pProcessName;

   private String pProcessMgrName;

   private String pProcessMgrVersion;

   private String pActivityDefinitionId;

   private String pActivitySetDefinitionId;

   private String pProcessDefinitionId;

   private String pPackageId;



   public void setPActivityId (String paId) {

      this.pActivityId=paId;

   }



   public String getPActivityId () {

      return pActivityId;

   }



   public void setPProcessId (String ppId) {

      this.pProcessId=ppId;

   }



   public String getPProcessId () {

      return pProcessId;

   }



   public void setPProcessName (String ppn) {

      this.pProcessName=ppn;

   }



   public String getPProcessName () {

      return pProcessName;

   }



   public void setPProcessDefinitionName (String ppdn) {

      this.pProcessMgrName=ppdn;

   }



   public String getPProcessDefinitionName () {

      return pProcessMgrName;

   }



   public void setPProcessDefinitionVersion (String ppdv) {

      this.pProcessMgrVersion=ppdv;

   }



   public String getPProcessDefinitionVersion () {

      return pProcessMgrVersion;

   }



   public void setPActivityDefinitionId (String padId) {

      pActivityDefinitionId=padId;

   }



   public String getPActivityDefinitionId () {

      return pActivityDefinitionId;

   }



   public void setPActivitySetDefinitionId (String pasdId) {

      pActivitySetDefinitionId=pasdId;

   }



   public String getPActivitySetDefinitionId () {

      return pActivitySetDefinitionId;

   }



   public void setPProcessDefinitionId (String ppdId) {

      this.pProcessDefinitionId=ppdId;

   }



   public String getPProcessDefinitionId () {

      return pProcessDefinitionId;

   }



   public void setPPackageId (String ppkgId) {

      this.pPackageId=ppkgId;

   }



   public String getPPackageId () {

      return pPackageId;

   }



}

