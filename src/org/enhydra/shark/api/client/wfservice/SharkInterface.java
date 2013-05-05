package org.enhydra.shark.api.client.wfservice;

import org.enhydra.shark.api.*;


/**
 * The top-most client interface for accessing shark engine.
 * Every client application has to know about the implementation
 * of this interface.
 * <p>
 * When client application has the instance of this interface implementation,
 * it can call one of its methods to retrieve instances of various interfaces
 * used for managing Shark.
 * @author Sasa Bojanic
 * @author Vladimir Puskas
 */
public interface SharkInterface {

   /**
    * Returns instance of SharkConnection interface that can be
    * used by shark user to connect to shark engine and execute assignments.
    *
    * @return SharkConnection instance.
    */
   SharkConnection getSharkConnection ();


   /**
    * Returns instance of interface for managing shark's repository of
    * XPDL files.
    *
    * @return RepositoryMgr instance.
    *
    */
   RepositoryMgr getRepositoryManager ();

   /**
    * Returns instance of interface for shark administration.
    *
    * @return AdminInterface instance.
    *
    */
   AdminInterface getAdminInterface ();

   /**
    * Returns instance of interface for building expressions.
    */
   ExpressionBuilderManager getExpressionBuilderManager ();

   /**
    * Returns a shark transaction.
    *
    * @return SharkTransaction instance.
    *
    * @throws TransactionException If something unexpected happens.
    *
    */
   SharkTransaction createTransaction() throws TransactionException;

   /**
    * Returns a shark's participant mapping transaction.
    *
    * @return   ParticipantMappingTransaction instance.
    *
    * @throws   TransactionException If something unexpected happens.
    *
    */
   ParticipantMappingTransaction createParticipantMappingTransaction() throws TransactionException;

   /**
   * Returns a shark's application mapping transaction.
   *
   * @return   ApplicationMappingTransaction instance.
   *
   * @throws   TransactionException If something unexpected happens.
   *
   */
   ApplicationMappingTransaction createApplicationMappingTransaction() throws TransactionException;

   /**
   * Returns a shark's script mapping transaction.
   *
   * @return   ScriptMappingTransaction instance.
   *
   * @throws   TransactionException If something unexpected happens.
   *
   */
   ScriptMappingTransaction createScriptMappingTransaction() throws TransactionException;

   /**
    * Returns a shark's user transaction.
    *
    * @return   a UserTransaction instance.
    *
    * @throws   TransactionException If something unexpected happens.
    *
    */
   UserTransaction createUserTransaction() throws TransactionException;

   /**
    * Returns a shark's repository transaction.
    *
    * @return   a RepositoryTransaction instance.
    *
    * @throws   TransactionException If something unexpected happens.
    *
    */
   RepositoryTransaction createRepositoryTransaction() throws TransactionException;

   /**
    * Unlocks all processes locked in current transaction. This should be used
    * when external SharkTransaction are used with Shark, and it has to be used
    * along with calling release method on SharkTransaction.
    * <p>NOTE: it is supposed that transaction is bound to a thread, otherwise
    * unlocking won't work properly.
    *
    * @param    t                   a  SharkTransaction
    *
    * @throws   BaseException
    *
    */
   void unlockProcesses (SharkTransaction t) throws TransactionException;


   /**
    * Empties shark caches - this should be used if some method that uses
    * external transaction only for reading fails.
    * <p>NOTE: it is supposed that transaction is bound to a thread, otherwise
    * emptying won't work properly.
    *
    * @param    t                   a  SharkTransaction
    *
    */
   void emptyCaches (SharkTransaction t);

   /**
    * Returns shark configuration properties.
    *
    * @return   Properties object.
    *
    */
   java.util.Properties getProperties ();

}

