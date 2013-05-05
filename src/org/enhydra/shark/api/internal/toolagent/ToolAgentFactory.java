package org.enhydra.shark.api.internal.toolagent;

import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.internal.working.CallbackUtilities;
import java.util.List;

/**
 * @author Sasa Bojanic
 * @author Vladimir Puskas
 */
public interface ToolAgentFactory {
   void configure (CallbackUtilities cus) throws RootException;

   /**
    * Creates an instance of ToolAgent specified by its full class name.
    *
    * @param    t                   a  SharkTransaction
    * @param    className           a  String
    *
    * @return   a ToolAgent
    *
    * @throws   RootException
    *
    */
   ToolAgent createToolAgent (SharkTransaction t,String className) throws RootException;

   /**
    * Gets the list of full class names of tool agent implementations that are
    * explicitly known by the implementation of the factory.
    *
    * @param    t                   a  SharkTransaction
    *
    * @return   a List
    *
    * @throws   RootException
    *
    */
   List getDefinedToolAgents (SharkTransaction t) throws RootException;

}
