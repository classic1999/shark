package org.enhydra.shark.api.internal.instancepersistence;

/**
 * @author Sasa Bojanic
 */
public interface ProcessMgrPersistenceInterface {

   public void setName (String name);

   public String getName ();

   public void setPackageId (String pkgId);

   public String getPackageId ();

   public void setProcessDefinitionId (String pdId);

   public String getProcessDefinitionId ();

   public void setState (int state);

   public int getState ();

   public String getVersion ();

   public void setVersion (String version);

   public long getCreated ();

   public void setCreated (long created);

}
