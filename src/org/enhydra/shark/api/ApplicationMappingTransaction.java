package org.enhydra.shark.api;

/**
 * Application mapping information may be stored in separate repository
 * so it may need a different type of transaction. To represent this,
 * ApplicationMappingTransaction is another interface different from SharkTransaction
 * and other transaction interfaces.
 *
 * @author Zoran Milakovic
 */
public interface ApplicationMappingTransaction {

   /**
   * Commits mapping transaction.
   *
   * @exception TransactionException If something unexpected happens.
   */
   public void commit () throws TransactionException;

   /**
   * Rollbacks mapping transaction. This method is called when commit
   * method fails.
   *
   * @exception TransactionException If something unexpected happens.
   */
   public void rollback () throws TransactionException;

   /**
   * Releases mapping transaction. Method <b>MUST</b> be called for each
   * transaction.
   *
   * @exception TransactionException If something unexpected happens.
   */
   public void release() throws TransactionException;
}
