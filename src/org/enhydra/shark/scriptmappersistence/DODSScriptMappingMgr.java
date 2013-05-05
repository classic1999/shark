
package org.enhydra.shark.scriptmappersistence;

import org.enhydra.shark.api.*;
import org.enhydra.shark.api.internal.scriptmappersistence.*;
import org.enhydra.shark.api.internal.working.CallbackUtilities;
import org.enhydra.dods.DODS;


/**
 * Implementation of ScriptMappingsManager interface
 * @author Zoran Milakovic
 */
public class DODSScriptMappingMgr implements ScriptMappingManager
{
   public static boolean _debug_ = false;

   private static final String DBG_PARAM_NAME = "DODSScriptMappingMgr.debug";

   public void configure (CallbackUtilities cus) throws RootException {
      if (null == cus)
             throw new RootException("Cannot configure without call back impl.");
           _debug_ = Boolean
             .valueOf(cus.getProperty(DBG_PARAM_NAME, "false"))
             .booleanValue();
   }

   public ScriptMappingTransaction getScriptMappingTransaction() throws TransactionException {
             try {
               return new DODSScriptMappingTransaction(DODS.getDatabaseManager().createTransaction());
             } catch (Exception ex) {
               throw new TransactionException(ex);
             }
   }
}

