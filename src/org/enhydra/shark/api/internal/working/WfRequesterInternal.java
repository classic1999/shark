package org.enhydra.shark.api.internal.working;

import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfmodel.InvalidPerformer;
import org.enhydra.shark.api.client.wfmodel.WfEventAudit;
import org.enhydra.shark.api.client.wfmodel.WfRequester;

/**
 * @author Sasa Bojanic
 */
public interface WfRequesterInternal {
   void receive_event (SharkTransaction t,WfEventAudit event,WfProcessInternal process) throws BaseException, InvalidPerformer;
   String getResourceRequesterUsername (SharkTransaction t) throws BaseException;
   WfRequester getExternalRequester (SharkTransaction t) throws BaseException;
} // interface WfRequester
