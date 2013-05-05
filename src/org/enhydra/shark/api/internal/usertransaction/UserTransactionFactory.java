package org.enhydra.shark.api.internal.usertransaction;

import org.enhydra.shark.api.*;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

/**
 * UserTransactionFactory interface defines methods for creating UserTransaction.
 * @author Tanja Jovanovic
 */
public interface UserTransactionFactory {
   
   /**
    * Method configure is called at Shark start up, to configure
    * implementation of UserTransactionFactory.
    *
    * @param cus an instance of CallbackUtilities used to get
    *     properties for configuring user transaction manager and 
    * user transactions in Shark.
    *
    * @exception RootException Thrown if configuring doesn't succeed.
    */
   void configure (CallbackUtilities cus) throws RootException;

   /**
    * Creates user transaction.
    *
    * @return Created user transaction.
    *
    * @exception TransactionException If something unexpected happens.
    */
   public UserTransaction createTransaction() throws TransactionException;

}

