package org.enhydra.shark.api.client.wfservice;


import org.enhydra.shark.api.client.wfbase.*;

import org.enhydra.shark.api.client.wfmodel.*;



/**
 * Specialization of WfEventAudit interface that represents events
 * considering XPDL management (load, unload and update of packages).
 */
public interface WfPackageEventAudit extends WfEventAudit {
   /** Returns package Id of package related to this event. */
   String package_id () throws BaseException;
   /** Returns package version of package related to this event. */
   String package_version () throws BaseException;
   /** Returns the username of resource that performed operation related to this event. */
   String resource_username () throws BaseException;
} // interface WfPackageEventAudit
