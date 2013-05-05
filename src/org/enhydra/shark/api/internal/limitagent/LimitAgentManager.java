package org.enhydra.shark.api.internal.limitagent;

import java.util.Map;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

/**
 * @author     <a href="mailto:jaz@ofbiz.org">Andy Zeneski</a>
 * @since      Mar 24, 2004
 */
public interface LimitAgentManager {

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
    * Method checkLimits.
    *
    * @exception   LimitAgentException.
    */
   void checkLimits (SharkTransaction t) throws LimitAgentException;

   void checkLimits (SharkTransaction t,String procId) throws LimitAgentException;

   void checkProcessLimit (SharkTransaction t,String procId) throws LimitAgentException;

   void checkActivityLimit (SharkTransaction t,String procId,String actId) throws LimitAgentException;

   /**
    * Method notifyStart
    *
    * @param    procId              a  String.
    * @param    actId               a  String.
    * @param    context             a  Map.
    * @param    runtime             a  long.
    *
    * @exception   LimitAgentException.
    */
   void notifyStart(String procId, String actId, Map context, long runtime) throws LimitAgentException;

   /**
    * Method notifyStop.
    *
    * @param    procId              a  String.
    * @param    actId               a  String.
    *
    * @exception   LimitAgentException.
    */
   void notifyStop(String procId, String actId) throws LimitAgentException;
}
