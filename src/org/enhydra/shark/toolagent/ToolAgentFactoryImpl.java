package org.enhydra.shark.toolagent;


import org.enhydra.shark.api.internal.toolagent.*;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

import java.util.*;

/**
 * Factory for creating tool agents.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class ToolAgentFactoryImpl implements ToolAgentFactory {
   private final static String TOOL_AGENT_PREFIX="ToolAgent.";
   private List toolAgents=new ArrayList();

   private CallbackUtilities cus;
   public void configure (CallbackUtilities cus) throws RootException {
      this.cus=cus;
      createToolAgentList();
   }

   /**
    * Returns all tool agents specified in shark's properties.
    */
   public List getDefinedToolAgents (SharkTransaction t) throws RootException {
      return Collections.unmodifiableList(toolAgents);
   }


   public ToolAgent createToolAgent(SharkTransaction t,String className) throws RootException {
      try {
         Class cls=Class.forName(className);
         ToolAgent ta=(ToolAgent)cls.newInstance();
         ta.configure(cus);
         return ta;
      } catch (Exception ex) {
         throw new RootException("Failed to create tool agent for class "+className,ex);
      }
   }

   private void createToolAgentList () {
      String taName=null;
      String className=null;
      Properties props= cus.getProperties();

      Iterator it=props.entrySet().iterator();
      while (it.hasNext()) {
         try {
            Map.Entry me=(Map.Entry)it.next();
            taName=me.getKey().toString();
            if (taName.startsWith(TOOL_AGENT_PREFIX)) {
               taName=taName.substring(TOOL_AGENT_PREFIX.length());
               className=me.getValue().toString();
               toolAgents.add(className);
            }
         } catch (Throwable ex) {
            //ex.printStackTrace();
            cus.error("ToolAgentFactoryImpl -> Error when retrieving list of tool agents from properties!!!");
         }
      }
   }

}
