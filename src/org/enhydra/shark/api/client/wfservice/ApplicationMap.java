package org.enhydra.shark.api.client.wfservice;

/**
 * Interface used to store mapping information among XPDL application
 * definitions and ToolAgent applications.
 * @author Zoran Milakovic
 */
public interface ApplicationMap {

   /**
    * Sets the Id of application XPDL definition of this map entry.
    *
    * @param   id new application XPDL definition Id.
    */
   public void setApplicationDefinitionId(String id);

   /**
    * Returns the Id of XPDL application definition of this map entry.
    *
    * @return    Aapplication XPDL definition Id.
    */
   public String getApplicationDefinitionId();

   /**
    * Sets the Id of XPDL package containing application definition of this map 
    * entry.
    *
    * @param  id XPDL package Id.
    */
   public void setPackageId(String id);

   /**
    * Returns the Id of XPDL package containing application definition of 
    * this map entry.
    *
    * @return  XPDL package Id.
    */
   public String getPackageId();

   /**
    * Sets the Id of XPDL process definition containing application definition
    * of this map entry. If application is defined at package level, this
    * has to be set to null or to an empty string.
    *
    * @param  id XPDL process definition Id.
    */
   public void setProcessDefinitionId(String id);

   /**
    * Returns the Id of XPDL process definition containing application definition
    * of this map entry. If application is defined at package level, this
    * method returns null or empty string.
    *
    * @return  XPDL process definition Id.
    */
   public String getProcessDefinitionId();

   /**
    * Sets the name of tool agent of this map entry.
    *
    * @param  name tool agent name.
    */
   public void setToolAgentClassName(String name);

   /**
    * Returns the name of tool agent of this map entry.
    *
    * @return   Tool agent name.
    */
   public String getToolAgentClassName();

   /**
    * Sets the username needed to connect to tool agent of this map entry.
    *
    * @param  username username for connection.
    */
   public void setUsername(String username);

   /**
    * Returns the username needed to connect to tool agent of this map entry.
    *
    * @return   Username for connection.
    */
   public String getUsername();

   /**
    * Sets the password needed to connect to tool agent of this map entry.
    *
    * @param  password password for connection.
    */
   public void setPassword(String password);

   /**
    * Returns the password needed to connect to tool agent of this map entry.
    *
    * @return   Password for connection.
    */
   public String getPassword();

   /**
    * Sets the application name to be executed by tool agent of this map entry.
    *
    * @param  name name of the application to be executed.
    */
   public void setApplicationName(String name);

   /**
    * Returns the application name to be executed by tool agent of this map entry.
    *
    * @return   Name of the application to be executed.
    */
   public String getApplicationName();

   /**
    * Sets the application mode for application executed by tool agent of this 
    * map entry.
    *
    * @param  mode application mode.
    */
   public void setApplicationMode(Integer mode);

   /**
    * Returns the application mode for application executed by tool agent of 
    * this map entry.
    *
    * @return   Application mode.
    */
   public Integer getApplicationMode();

}
