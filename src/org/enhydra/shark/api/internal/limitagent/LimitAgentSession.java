package org.enhydra.shark.api.internal.limitagent;

import java.util.Map;

/**
 *
 * @author     <a href="mailto:jaz@ofbiz.org">Andy Zeneski</a>
 * @since      Mar 24, 2004
 */
public interface LimitAgentSession {

   String[][] getExtendedAttributesNameValuePairs();
   String getProcessId();
   String getActivityId();
   Map getProcessContext();
   long getLimitTime();
}
