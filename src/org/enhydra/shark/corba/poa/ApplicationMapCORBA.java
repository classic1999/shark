package org.enhydra.shark.corba.poa;


import org.enhydra.shark.corba.WorkflowService.ApplicationMapPOA;
import org.omg.CORBA.ORB;

/**
 * Implementation of CORBA ApplicationMap interface.
 *
 * @author David Forslund
 */
public class ApplicationMapCORBA extends ApplicationMapPOA {

   private String toolAgentClassName = "";

   private String username = "";

   private String password = "";

   private String applicationName = "";

   private int applicationMode = -Integer.MAX_VALUE;

   private String processDefId = "";

   private String packageId = "";

   private String applicationDefinitionId = "";

   public ApplicationMapCORBA(Collective toJoin) {
     // toJoin.__recruit(this);
   }

   public ApplicationMapCORBA(Collective toJoin,
                              org.enhydra.shark.api.client.wfservice.ApplicationMap am) {
    //  this(toJoin);
      setToolAgentClassName(am.getToolAgentClassName());
      setUsername(am.getUsername());
      setPassword(am.getPassword());
      setApplicationName(am.getApplicationName());
      if (am.getApplicationMode() != null) {
         setApplicationMode(am.getApplicationMode().intValue());
      }
      setPackageId(am.getPackageId());
      setProcessDefinitionId(am.getProcessDefinitionId());
      setApplicationDefinitionId(am.getApplicationDefinitionId());
   }

   public String getToolAgentClassName() {
      return toolAgentClassName;
   }

   public void setToolAgentClassName(String name) {
      if (name == null) return;
      this.toolAgentClassName = name;
   }

   public String getPackageId() {
      return packageId;
   }

   public void setPackageId(String id) {
      if (id == null) return;
      this.packageId = id;
   }

   public String getApplicationDefinitionId() {
      return applicationDefinitionId;
   }

   public void setApplicationDefinitionId(String id) {
      if (id == null) return;
      this.applicationDefinitionId = id;
   }

   public String getProcessDefinitionId() {
      return processDefId;
   }

   public void setProcessDefinitionId(String id) {
      if (id == null) return;
      this.processDefId = id;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      if (username == null) return;
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      if (password == null) return;
      this.password = password;
   }

   public String getApplicationName() {
      return applicationName;
   }

   public void setApplicationName(String appName) {
      if (applicationName == null) return;
      this.applicationName = appName;
   }

   public int getApplicationMode() {
      return applicationMode;
   }

   public void setApplicationMode(int appMode) {
      this.applicationMode = appMode;
   }

}