package org.enhydra.shark.api.internal.eventaudit;

public interface AssignmentEventAuditPersistenceInterface extends EventAuditPersistenceInterface {

   public void setOldResourceUsername (String un);

   public String getOldResourceUsername ();

   public void setOldResourceName (String nm);

   public String getOldResourceName ();

   public void setNewResourceUsername (String un);

   public String getNewResourceUsername ();

   public void setNewResourceName (String nm);

   public String getNewResourceName ();

   public void setIsAccepted (boolean acc);

   public boolean getIsAccepted ();

}
