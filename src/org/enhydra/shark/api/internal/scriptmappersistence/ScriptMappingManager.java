
package org.enhydra.shark.api.internal.scriptmappersistence;

import org.enhydra.shark.api.*;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

/**
 * Interface represents mappings for XPDL script language.
 *
 * @author Zoran Milakovic
 */
public interface ScriptMappingManager
{
   /**
    * Method configure is called at Shark start up, to configure
    * implementation of ScriptsMappings.
    *
    * @param cus an instance of CallbackUtilities used to get
    *            properties for configuring mappings for tool agents in Shark.
    *
    * @exception RootException Thrown if configuring doesn't succeed.
    */
   void configure (CallbackUtilities cus) throws RootException;

   public ScriptMappingTransaction getScriptMappingTransaction() throws TransactionException;
}

