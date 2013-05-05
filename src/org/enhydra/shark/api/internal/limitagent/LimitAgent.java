package org.enhydra.shark.api.internal.limitagent;

import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

/**
 *
 * @author     <a href="mailto:jaz@ofbiz.org">Andy Zeneski</a>
 * @since      Mar 24, 2004
 */
public interface LimitAgent {

   /**
    * Method configure is called at Shark start up, to configure
    * implementation of LimitAgent.
    *
    * @param cus an instance of CallbackUtilities used to get
    *            properties for configuring.
    *
    * @exception RootException Thrown if configuring doesn't succeed.
    */
   void configure(CallbackUtilities cus) throws RootException;

   /**
    * Method invoke.
    *
    * @param    session             a  LimitAgentSession.
    *
    * @exception   LimitAgentException.
    */
   void invoke(LimitAgentSession session) throws LimitAgentException;
}
