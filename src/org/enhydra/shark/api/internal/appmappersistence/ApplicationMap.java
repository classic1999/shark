package org.enhydra.shark.api.internal.appmappersistence;

/**
 * Interface for mapping between applications and process.
 * @author Zoran Milakovic
 */

public interface ApplicationMap {

   public void setApplicationDefinitionId(String id);

   public String getApplicationDefinitionId();

   public void setPackageId(String id);

   public String getPackageId();

   public void setProcessDefinitionId(String id);

   public String getProcessDefinitionId();

   public void setToolAgentClassName(String name);

   public String getToolAgentClassName();

   public void setUsername(String username);

   public String getUsername();

   public void setPassword(String password);

   public String getPassword();

   public void setApplicationName(String name);

   public String getApplicationName();

   public void setApplicationMode(Integer mode);

   public Integer getApplicationMode();

   //public boolean equalsByKeys(ApplicationMap am);

}
