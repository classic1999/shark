package org.enhydra.shark.instancepersistence;


import org.enhydra.shark.api.internal.instancepersistence.*;

public class DODSProcessVariable implements ProcessVariablePersistenceInterface {

   private String processId;
   private String definitionId;
   private Object value;

   public void setProcessId (String procId) {
      this.processId=procId;
   }

   public String getProcessId () {
      return processId;
   }

   public void setDefinitionId (String dId) {
      this.definitionId=dId;
   }

   public String getDefinitionId () {
      return definitionId;
   }

   public void setValue (Object val) {
      this.value=val;
   }

   public Object getValue () {
      return value;
   }

}
