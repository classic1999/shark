package org.enhydra.shark.usertransaction;

import org.enhydra.dods.DODS;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.UserTransaction;
import org.enhydra.shark.api.TransactionException;
import org.enhydra.shark.api.internal.usertransaction.UserTransactionFactory;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

/**
 * Implementation of UserTransactionFactory interface that creates DODS transaction.
 * @author Tanja Jovanovic
 *
 */
public class DODSUserTransactionFactory implements UserTransactionFactory {
   static boolean _debug_ = false;

   private static final String DBG_PARAM_NAME = "DODSUserTransactionFactory.debug";
   
   /**
    * Configure DODSUserTransactionFactory.
    *
    * @param cus an instance of CallbackUtilities used to get
    *     properties for configuring DODS user transaction manager and 
    *  user transactions.
    *
    * @exception RootException thrown if configuring doesn't succeed.
    */
   public void configure (CallbackUtilities cus) throws RootException {
      if (null == cus)
         throw new RootException("Cannot configure without call back impl.");
      _debug_ = Boolean
         .valueOf(cus.getProperty(DBG_PARAM_NAME, "false"))
         .booleanValue();
   }

   /**
    * Creates user transaction.
    *
    * @return Created user transaction.
    *
    * @exception TransactionException If something unexpected happens.
    */
   public UserTransaction createTransaction () throws TransactionException {
      try {
         return new SharkDODSUserTransaction(DODS.getDatabaseManager().createTransaction());
      } catch (Exception ex) {
         throw new TransactionException(ex);
      }
   }
}

