/* TransactionFactory.java */

package org.enhydra.shark.api.internal.transaction;

import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.TransactionException;
import org.enhydra.shark.api.internal.transaction.SharkInternalTransaction;
import org.enhydra.shark.api.internal.working.CallbackUtilities;


/**
 * TransactionFactory interface defines methods for creating SharkTransaction.
 * @author Sasa Bojanic
 * @author Vladimir Puskas
 */
public interface TransactionFactory {

   /**
    * Method configure is called at Shark start up, to configure
    * implementation of TransactionFactory.
    *
    * @param cus an instance of CallbackUtilities used to get
    *     properties for configuring shark transaction manager and
    *     transactions in Shark.
    *
    * @exception RootException Thrown if configuring doesn't succeed.
    */
   void configure (CallbackUtilities cus) throws RootException;

   /**
    * Creates shark transaction.
    *
    * @return Created shark transaction.
    *
    * @exception TransactionException If something unexpected happens.
    */
   public SharkInternalTransaction createTransaction() throws TransactionException;

}

