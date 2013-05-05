package org.enhydra.shark.api.internal.instancepersistence;

public interface ProcessVariablePersistenceInterface {

   public void setProcessId (String pId);

   public String getProcessId ();

   public void setDefinitionId (String defId);

   public String getDefinitionId ();

   public void setValue (Object val);

   public Object getValue ();

}
