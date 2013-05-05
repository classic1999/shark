package org.enhydra.shark.api.internal.working;


import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfmodel.WfEventAudit;
import org.enhydra.shark.api.internal.working.PersistenceInterface;

/**
 * @author Sasa Bojanic
 */
public interface WfEventAuditInternal extends WfEventAudit, PersistenceInterface {

   String activity_definition_id () throws BaseException;
   String activity_set_definition_id () throws BaseException;
   String process_definition_id () throws BaseException;
   String package_id () throws BaseException;

}

