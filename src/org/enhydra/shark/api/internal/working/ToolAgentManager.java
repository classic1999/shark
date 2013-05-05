package org.enhydra.shark.api.internal.working;

import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.internal.toolagent.ToolAgentGeneralException;


/**
 * @author Sasa Bojanic
 */
public interface ToolAgentManager {
   public void executeActivity (SharkTransaction t,WfActivityInternal act) throws BaseException, ToolAgentGeneralException;
}
