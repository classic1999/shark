package org.enhydra.shark;

import org.enhydra.shark.api.client.wfservice.ApplicationMap;

/**
 * Implementation of ApplicationMap interface.
 * If value for some field is not set, or if value is set to 'null', field
 * will have value empty string.
 * @author Zoran Milakovic
 */
public class ApplicationMapImpl implements ApplicationMap {

   private String toolAgentName;
   private String username;
   private String password;
   private String applicationName;
   private Integer applicationMode;
   private String processDefId;
   private String packageId;
   private String applicationDefinitionId;

   protected ApplicationMapImpl() {
   }

   public String getToolAgentClassName() {
      return toolAgentName;
   }

   public void setToolAgentClassName( String name ) {
      this.toolAgentName = name;
   }

   public String getPackageId() {
      return packageId;
   }

   public void setPackageId( String packageId ) {
      this.packageId = packageId;
   }

   public String getApplicationDefinitionId() {
      return applicationDefinitionId;
   }

   public void setApplicationDefinitionId( String applicationDefinitionId ) {
      this.applicationDefinitionId = applicationDefinitionId;
   }

   public String getProcessDefinitionId() {
      return processDefId;
   }

   public void setProcessDefinitionId( String processDefId ) {
      this.processDefId = processDefId;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername( String username ) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword( String password ) {
      this.password = password;
   }

   public String getApplicationName() {
      return applicationName;
   }

   public void setApplicationName( String appName ) {
      this.applicationName = appName;
   }

   public Integer getApplicationMode() {
      return applicationMode;
   }

   public void setApplicationMode( Integer appMode ) {
      this.applicationMode = appMode;
   }


   public String toString() {
      String retVal = "";
      retVal += "toolAgentName=" + toolAgentName + "\n";
      retVal += "username=" + username + "\n";
      retVal += "password=" + password + "\n";
      retVal += "applicationName=" + applicationName + "\n";
      retVal += "applicationMode=" + applicationMode + "\n";
      retVal += "processDefId=" + processDefId + "\n";
      retVal += "packageId=" + packageId + "\n";
      retVal += "applicationDefinitionId=" + applicationDefinitionId + "\n";
      return retVal;
   }

}
