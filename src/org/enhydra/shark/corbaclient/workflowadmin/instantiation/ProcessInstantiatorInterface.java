package org.enhydra.shark.corbaclient.workflowadmin.instantiation;

import org.omg.WorkflowModel.WfProcess;
import org.omg.WorkflowModel.WfEventAudit;
import org.omg.WorkflowModel.InvalidPerformer;
import org.omg.WfBase.BaseException;

/**
 * Created by IntelliJ IDEA.
 * User: dwf
 * Date: Jun 29, 2005
 * Time: 5:17:41 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ProcessInstantiatorInterface {
    void addPerformer (WfProcess pr);

    void receive_event (WfEventAudit event) throws BaseException, InvalidPerformer;

    // Prevents memory leak if client forgets to disconnect
    void releaseProcesses();
}
