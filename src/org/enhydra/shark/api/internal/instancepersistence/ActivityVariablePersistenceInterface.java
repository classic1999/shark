package org.enhydra.shark.api.internal.instancepersistence;

public interface ActivityVariablePersistenceInterface {

   public void setActivityId (String aId);

   public String getActivityId ();

   public void setDefinitionId (String defId);

   public String getDefinitionId ();

   public void setValue (Object val);

   public Object getValue ();

   public void setResultVariable(boolean b);

   public boolean isResultVariable();
}
