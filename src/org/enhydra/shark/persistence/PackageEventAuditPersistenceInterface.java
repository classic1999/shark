package org.enhydra.shark.persistence;

import org.enhydra.shark.api.internal.eventaudit.*;

public interface PackageEventAuditPersistenceInterface extends EventAuditPersistenceInterface {

   public void setPackageVersion (String ver);

   public String getPackageVersion ();

   public void setResourceUsername (String resUsername);

   public String getResourceUsername ();

}
