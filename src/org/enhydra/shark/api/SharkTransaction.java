package org.enhydra.shark.api;

/**
 * Since Shark tends to be a transaction oriented, this is
 * the interface that the kernel uses to signal operations on the
 * transaction.
 *
 * @author Sasa Bojanic
 * @author Vladimir Puskas
 */
public interface SharkTransaction {

   /**
    * Method commit is invoked from the kernel when
    * it has changed something that has to be commited into
    * database.
    *
    * @exception   TransactionException thrown if anything goes wrong.
    */
   public void commit () throws TransactionException;

   /**
    * Method rollback is called by the kernel when commit
    * method fails (throws an exception).
    *
    * @exception   TransactionException thrown if anything goes wrong.
    */
   public void rollback () throws TransactionException;

   /**
    * Method release <b>MUST</b> be called (in kernel it is called)
    * for each transaction.
    *
    * @exception   TransactionException thrown if anything goes wrong.
    */
   public void release() throws TransactionException;
}
/* Enf of SharkTransaction.java */

