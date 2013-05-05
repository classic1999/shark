package org.enhydra.shark.appmappersistence;

import org.enhydra.shark.api.internal.appmappersistence.ApplicationMap;

/**
 * Implementation of ApplicationMap interface.
 * If value for field is not set, field will have value 'null'.
 * @author Zoran Milakovic
 */
public class DODSApplicationMap implements ApplicationMap {

   private String toolAgentName;
   private String username;
   private String password;
   private String applicationName;
   private Integer applicationMode;
   private String processDefId;
   private String packageId;
   private String applicationDefinitionId;

   public DODSApplicationMap() {
   }

   public DODSApplicationMap(
      String toolAgentName,
      String username,
      String password,
      String appName,
      Integer appMode ) {

      setToolAgentClassName( toolAgentName );
      setUsername( username );
      setPassword( password );
      setApplicationName( appName );
      setApplicationMode( appMode );

   }

   public String getToolAgentClassName() {
      return toolAgentName;
   }

   public void setToolAgentClassName( String name ) {
      if( name != null && !name.trim().equals("") )
         this.toolAgentName = name;
      else
         this.toolAgentName = null;
   }

   public String getPackageId() {
      return packageId;
   }

   public void setPackageId( String packageId ) {
      if ( packageId != null && !packageId.trim().equals("") )
         this.packageId = packageId;
      else
         this.packageId = null;
   }

   public String getApplicationDefinitionId() {
      return applicationDefinitionId;
   }

   public void setApplicationDefinitionId( String applicationDefinitionId ) {
      if( applicationDefinitionId != null && !applicationDefinitionId.trim().equals("") )
         this.applicationDefinitionId = applicationDefinitionId;
      else
         this.applicationDefinitionId = null;
   }

   public String getProcessDefinitionId() {
      return processDefId;
   }

   public void setProcessDefinitionId( String processDefId ) {
      if( processDefId != null && !processDefId.trim().equals("") )
         this.processDefId = processDefId;
      else
         this.processDefId = null;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername( String username ) {
      if( username != null && !username.trim().equals("") )
         this.username = username;
      else
         this.username = null;
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
      if( appName != null && !appName.trim().equals("") )
         this.applicationName = appName;
      else
         this.applicationName = null;
   }

   public Integer getApplicationMode() {
      return applicationMode;
   }

   public void setApplicationMode( Integer appMode ) {
      this.applicationMode = appMode;
   }

   public String toString() {
      String retVal = "";
      retVal += "toolAgentClassName=" + toolAgentName + "\n";
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
