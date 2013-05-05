package org.enhydra.shark.limitagent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.internal.limitagent.LimitAgentException;
import org.enhydra.shark.api.internal.limitagent.LimitAgentManager;
import org.enhydra.shark.api.internal.limitagent.LimitAgentSession;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

/**
 * @author     Sasa Bojanic
 */
public class StandardLimitAgentManager implements LimitAgentManager {

   protected CallbackUtilities cus;
   protected Map registeredProcesses;
   protected Map registeredActivities;
   protected Map activitiesOfProcess;

   public void configure(CallbackUtilities cus) throws RootException {
      this.registeredProcesses = new HashMap();
      this.registeredActivities = new HashMap();
      this.activitiesOfProcess = new HashMap();
      this.cus = cus;
   }

   public void checkLimits (SharkTransaction t) throws LimitAgentException {
      StandardLimitAgent sla=new StandardLimitAgent();
      try {
         sla.configure(cus);
      } catch (RootException e) {
         cus.error("Unable to configure the LimitAgent", e);
      }
      Iterator processes=registeredProcesses.values().iterator();
      while (processes.hasNext()) {
         sla.invoke((LimitAgentSession)processes.next());
      }
      Iterator activities=registeredActivities.values().iterator();
      while (activities.hasNext()) {
         sla.invoke((LimitAgentSession)activities.next());
      }
   }

   public void checkLimits (SharkTransaction t,String procId) throws LimitAgentException {
      StandardLimitAgent sla=new StandardLimitAgent();
      try {
         sla.configure(cus);
      } catch (RootException e) {
         cus.error("Unable to configure the LimitAgent", e);
      }
      LimitAgentSession las=(LimitAgentSession)registeredProcesses.get(procId);
      if (las!=null) {
         sla.invoke(las);
      }
      Set s=(Set)activitiesOfProcess.get(procId);
      if (s!=null) {
         Iterator activities=s.iterator();
         while (activities.hasNext()) {
            sla.invoke((LimitAgentSession)registeredActivities.get(activities.next()));
         }
      }
   }

   public void checkProcessLimit (SharkTransaction t,String procId) throws LimitAgentException {
      StandardLimitAgent sla=new StandardLimitAgent();
      try {
         sla.configure(cus);
      } catch (RootException e) {
         cus.error("Unable to configure the LimitAgent", e);
      }
      LimitAgentSession las=(LimitAgentSession)registeredProcesses.get(procId);
      if (las!=null) {
         sla.invoke(las);
      }
   }

   public void checkActivityLimit (SharkTransaction t,String procId,String actId) throws LimitAgentException {
      StandardLimitAgent sla=new StandardLimitAgent();
      try {
         sla.configure(cus);
      } catch (RootException e) {
         cus.error("Unable to configure the LimitAgent", e);
      }
      LimitAgentSession las=(LimitAgentSession)registeredActivities.get(actId);
      if (las!=null) {
         sla.invoke(las);
      }
   }

   public void notifyStop(String procId,String actId) throws LimitAgentException {
      if (procId == null && actId == null) {
         cus.error("Cannot locate limit schedule for a null WfExecutionObject ID!");
         throw new LimitAgentException("Invalid WfExecutionObject ID; cannot locate scheduled limit");
      }

      if (actId!=null && procId==null) {
         cus.error("Cannot locate limit schedule for a null WfExecutionObject ID!");
         throw new LimitAgentException("Invalid WfExecutionObject ID; cannot locate scheduled limit");
      }

      if (actId!=null) {
         registeredActivities.remove(actId);
         Set s=(Set)activitiesOfProcess.get(procId);
         if (s!=null) {
            s.remove(actId);
            if (s.size()==0) {
               registeredActivities.remove(procId);
            }
         }
      } else {
         registeredProcesses.remove(procId);
      }
   }

   public void notifyStart(String procId,String actId, Map context, long runtime) throws LimitAgentException {
      if (procId == null && actId == null) {
         cus.error("Cannot create limit schedule for a null WfExecutionObject ID!");
         throw new LimitAgentException("Invalid WfExecutionObject ID; cannot schedule limit");
      }

      if (actId!=null && procId==null) {
         cus.error("Cannot locate limit schedule for a null WfExecutionObject ID!");
         throw new LimitAgentException("Invalid WfExecutionObject ID; cannot locate scheduled limit");
      }

      // create the session
      LimitAgentSession session = new StandardLimitAgentSession (procId, actId, context, runtime);

      // register the timer with the in-memory map
      if (actId!=null) {
         registeredActivities.put(actId,session);
         Set s=(Set)activitiesOfProcess.get(procId);
         if (s==null) {
            s=new HashSet();
            activitiesOfProcess.put(procId,s);
         }
         s.add(actId);
      } else {
         registeredProcesses.put(procId,session);
      }
   }

}
