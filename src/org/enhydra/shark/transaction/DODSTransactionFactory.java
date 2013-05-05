/* TransactionManager.java */

package org.enhydra.shark.transaction;


import org.enhydra.dods.DODS;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.TransactionException;
import org.enhydra.shark.api.internal.transaction.SharkInternalTransaction;
import org.enhydra.shark.api.internal.transaction.TransactionFactory;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

/**
 * Implementation of TransactionFactory interface that creates DODS transaction.
 *
 */
public class DODSTransactionFactory implements TransactionFactory {

   private CallbackUtilities cus;
   
   static boolean _debug_ = false;
   
   private static final String DBG_PARAM_NAME = "DODSTransactionFactory.debug";
   
   public void configure (CallbackUtilities cus) throws RootException {
      if (null == cus)
         throw new RootException("Cannot configure without call back impl.");
      this.cus=cus;
      _debug_ = Boolean
         .valueOf(cus.getProperty(DBG_PARAM_NAME, "false"))
         .booleanValue();
   }


   public SharkInternalTransaction createTransaction () throws TransactionException {
      try {
         return new SharkDODSTransaction(DODS.getDatabaseManager().createTransaction());
      } catch (Exception ex) {
         throw new TransactionException(ex);
      }
   }

}

