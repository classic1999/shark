package org.enhydra.shark.limitagent;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.internal.limitagent.LimitAgent;
import org.enhydra.shark.api.internal.limitagent.LimitAgentException;
import org.enhydra.shark.api.internal.limitagent.LimitAgentManager;
import org.enhydra.shark.api.internal.limitagent.LimitAgentSession;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

/**
 * @author     <a href="mailto:jaz@ofbiz.org">Andy Zeneski</a>
 * @since      Mar 24, 2004
 */
public class TimerLimitAgentManager implements LimitAgentManager {

   protected CallbackUtilities cus;
   protected Map registeredObjects;

   public void configure(CallbackUtilities cus) throws RootException {
      this.registeredObjects = new HashMap();
      this.cus = cus;
   }

   public void checkLimits (SharkTransaction t) throws LimitAgentException {
      throw new LimitAgentException("This method is not supported by the timer implementation of LimitAgentManager");
   }

   public void checkLimits (SharkTransaction t,String procId) throws LimitAgentException {
      throw new LimitAgentException("This method is not supported by the timer implementation of LimitAgentManager");
   }

   public void checkProcessLimit (SharkTransaction t,String procId) throws LimitAgentException {
      throw new LimitAgentException("This method is not supported by the timer implementation of LimitAgentManager");
   }

   public void checkActivityLimit (SharkTransaction t,String procId,String actId) throws LimitAgentException {
      throw new LimitAgentException("This method is not supported by the timer implementation of LimitAgentManager");
   }

   public void notifyStop(String procId,String actId) throws LimitAgentException {
      if (procId == null && actId == null) {
         cus.error("Cannot locate limit schedule for a null WfExecutionObject ID!");
         throw new LimitAgentException("Invalid WfExecutionObject ID; cannot locate scheduled limit");
      }

      // get the timer
      Timer timer;
      if (actId!=null) {
         timer=(Timer) registeredObjects.remove(actId);
      } else {
         timer=(Timer) registeredObjects.remove(procId);
      }
      if (timer != null) {
         timer.cancel();
      }
   }

   public void notifyStart(String procId,String actId, Map context, long runtime) throws LimitAgentException {
      if (procId == null && actId == null) {
         cus.error("Cannot create limit schedule for a null WfExecutionObject ID!");
         throw new LimitAgentException("Invalid WfExecutionObject ID; cannot schedule limit");
      }

      // parse the extended attributes
      //ExtendedAttributes attrs = this.readExtendedAttributes(extAttrStr);

      // create the session
      LimitAgentSession session = new TimerLimitAgentSession(procId, actId, context, runtime);

      // create the timer
      long delay = runtime - System.currentTimeMillis();
      if (delay <= 0) {
         cus.debug("Limit for this object has already been reached; running now");
         delay = 1;
      }
      Timer timer = new Timer();
      timer.schedule(new LimitTimer(session), delay);
      cus.debug("Limit Timer scheduled in [" + delay + "ms]");

      // register the timer with the in-memory map
      if (actId!=null) {
         registeredObjects.put(actId, timer);
      } else {
         registeredObjects.put(procId, timer);
      }
   }

   class LimitTimer extends TimerTask {

      private LimitAgentSession session;

      private LimitTimer(LimitAgentSession session) {
         this.session = session;
      }

      public void run() {
         LimitAgent agent = new TimerLimitAgent();
         try {
            agent.configure(cus);
         } catch (RootException e) {
            cus.error("Unable to configure the LimitAgent", e);
         }
         try {
            agent.invoke(session);
         } catch (LimitAgentException e) {
            cus.error("Problem invoking limit notification agent", e);
         }
      }
   }
}
