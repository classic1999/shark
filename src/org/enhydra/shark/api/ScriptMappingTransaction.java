/*
 * ScriptMappingTransaction.java. Created on Apr 29, 2004.
 */
package org.enhydra.shark.api;

/**
 * Script mapping information may be stored in separate repository
 * so it may need a different type of transaction. To represent this,
 * ScriptMappingTransaction is another interface different from SharkTransaction
 * and other transaction interfaces.
 *
 * @see SharkTransaction
 * @author Zoran Milakovic
 */
public interface ScriptMappingTransaction {

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
