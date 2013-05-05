package org.enhydra.shark.instancepersistence;


import org.enhydra.shark.api.internal.instancepersistence.*;

public class DODSActivityVariable implements ActivityVariablePersistenceInterface {

   private String activityId;
   private String definitionId;
   private Object value;
   private boolean modified;

   public void setActivityId (String actId) {
      this.activityId=actId;
   }

   public String getActivityId () {
      return activityId;
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

   public void setResultVariable(boolean b) {
      modified = b;
   }

   public boolean isResultVariable() {
      return modified;
   }

}
