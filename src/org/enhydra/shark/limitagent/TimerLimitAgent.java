package org.enhydra.shark.limitagent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.internal.limitagent.LimitAgent;
import org.enhydra.shark.api.internal.limitagent.LimitAgentException;
import org.enhydra.shark.api.internal.limitagent.LimitAgentSession;
import org.enhydra.shark.api.internal.working.CallbackUtilities;
import org.enhydra.shark.xpdl.XMLUtil;
import org.enhydra.shark.xpdl.elements.ExtendedAttribute;
import org.enhydra.shark.xpdl.elements.ExtendedAttributes;


/**
 *
 * @author     <a href="mailto:jaz@ofbiz.org">Andy Zeneski</a>
 * @author     Sasa Bojanic
 * @since      Mar 24, 2004
 */
public class TimerLimitAgent implements LimitAgent {

   public static final String LIMIT_AGENT_MANAGER_PREFIX = "LimitAgentManager";
   public static final String LIMIT_AGENT_PREFIX = "LimitAgent";
   protected static Map limitAgents;
   protected CallbackUtilities cus;

   public void configure(CallbackUtilities cus) throws RootException {
      TimerLimitAgent.limitAgents = new HashMap();
      this.cus = cus;
      loadLimitAgents(cus, getClass().getClassLoader());
   }

   public void invoke(LimitAgentSession session) throws LimitAgentException {
      if (session.getActivityId()!=null) {
         cus.info("-----> WfActivity : " + session.getActivityId() + " has reached its limit");
      } else {
         cus.info("-----> WfProcess : " + session.getProcessId() + " has reached its limit");
      }

      // the external limit agent
      String agentClass = agentClass=XMLUtil.getExtendedAttributeValue(session.getExtendedAttributesNameValuePairs(), "LimitAgentClassName");

      // check for the agent class
      // if we have an agent to use invoke it
      if (agentClass != null) {
         LimitAgent agent = this.getLimitAgent(agentClass);
         if (agent != null) {
            agent.invoke(session);
         }
      }
   }

   private LimitAgent getLimitAgent(String agentName) {
      LimitAgent agent = null;
      // first check if the name is the value in the properties file
      agent = (LimitAgent) limitAgents.get(agentName);

      // next check the class name
      if (agent == null) {
         Iterator i = TimerLimitAgent.limitAgents.values().iterator();
         while (i.hasNext() && agent == null) {
            Object obj = i.next();
            if (obj.getClass().getName().equals(agentName)) {
               agent = (LimitAgent) obj;
            }
         }
      }
      return agent;
   }

   private static void loadLimitAgents(CallbackUtilities cus, ClassLoader cl) {
      Properties props = cus.getProperties();
      Iterator it = props.entrySet().iterator();
      while (it.hasNext()) {
         String name = null;
         String className = null;
         try {
            Map.Entry me = (Map.Entry) it.next();
            name = me.getKey().toString();
            if (name.startsWith(LIMIT_AGENT_PREFIX) && !name.startsWith(LIMIT_AGENT_MANAGER_PREFIX)) {
               name = name.substring(LIMIT_AGENT_PREFIX.length());
               className = (String) me.getValue();
               LimitAgent agent = (LimitAgent) cl.loadClass(className).newInstance();
               if (agent != null) {
                  agent.configure(cus);
               }
               TimerLimitAgent.limitAgents.put(name, agent);
            }
         } catch (Throwable ex) {
            cus.error("LimitAgentManager -> Creation of Limit Agent "+ name +" from class "+ className +" failed !!!");
         }
      }
   }

}

