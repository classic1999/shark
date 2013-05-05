package org.enhydra.shark.api.internal.working;


import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfmodel.WfCreateProcessEventAudit;

/**
 * @author Sasa Bojanic
 */
public interface WfCreateProcessEventAuditInternal extends WfEventAuditInternal, WfCreateProcessEventAudit {

   String p_activity_definition_id () throws BaseException;
   String p_activity_set_definition_id () throws BaseException;
   String p_process_definition_id () throws BaseException;
   String p_package_id () throws BaseException;

}

