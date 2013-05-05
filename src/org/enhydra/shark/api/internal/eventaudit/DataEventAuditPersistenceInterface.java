package org.enhydra.shark.api.internal.eventaudit;

import java.util.*;

public interface DataEventAuditPersistenceInterface extends EventAuditPersistenceInterface {

   public void setOldData (Map od);

   public Map getOldData ();

   public void setNewData (Map nd);

   public Map getNewData ();

}
