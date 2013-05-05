package org.enhydra.shark.instancepersistence;


import org.enhydra.shark.api.internal.instancepersistence.*;

public class DODSProcessMgr implements ProcessMgrPersistenceInterface {

   private String name;
   private String packageId;
   private String processDefinitionId;
   private int state;
   private String version;
   private long created;

   public void setName (String name) {
      this.name=name;
   }

   public String getName () {
      return name;
   }

   public void setPackageId (String pkgId) {
      this.packageId=pkgId;
   }

   public String getPackageId () {
      return packageId;
   }

   public void setProcessDefinitionId (String pdId) {
      this.processDefinitionId=pdId;
   }

   public String getProcessDefinitionId () {
      return processDefinitionId;
   }

   public void setState (int state) {
      this.state=state;
   }

   public int getState () {
      return state;
   }

   public String getVersion () {
      return version;
   }

   public void setVersion (String version) {
      this.version=version;
   }

   public long getCreated () {
      return created;
   }

   public void setCreated (long created) {
      this.created=created;
   }

}
