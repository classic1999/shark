package org.enhydra.shark.api.internal.eventaudit;

public interface StateEventAuditPersistenceInterface extends EventAuditPersistenceInterface {

   public void setOldState (String os);

   public String getOldState ();

   public void setNewState (String ns);

   public String getNewState ();

}
