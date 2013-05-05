package org.enhydra.shark.api;

/**
 * The XPDL packages may be stored in separate repository, so it may need a
 * different type of transaction. To represent this, RepositoryTransaction is
 * another interface different from SharkTransaction and other transaction
 * interfaces.
 *
 * @see SharkTransaction
 * @author Sasa Bojanic
 */
public interface RepositoryTransaction {

   /**
    * Commits user transaction.
    *
    * @exception TransactionException If something unexpected happens.
    */
   public void commit () throws TransactionException;

   /**
    * Rollbacks user transaction. This method is called when commit
    * method fails.
    *
    * @exception TransactionException If something unexpected happens.
    */
   public void rollback () throws TransactionException;

   /**
    * Releases user transaction. Method <b>MUST</b> be called for each transaction.
    *
    * @exception TransactionException If something unexpected happens.
    */
   public void release() throws TransactionException;

}
