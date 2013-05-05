package org.enhydra.shark.usertransaction;

import org.enhydra.shark.api.UserTransaction;
import org.enhydra.shark.api.TransactionException;
import com.lutris.appserver.server.sql.DBTransaction;

/**
 * Implementation of UserTransaction interface that creates DODS transaction.
 * @author Tanja Jovanovic
 */
public class SharkDODSUserTransaction implements UserTransaction {

   private static int noOfCreations=0;
   private static int noOfCommits=0;
   private static int noOfRollbacks=0;
   private static int noOfReleases=0;

   private DBTransaction transaction;

   /**
    * Creates user transaction.
    *
    * @param transaction transaction.
    */
   public SharkDODSUserTransaction (DBTransaction transaction) {
      if (DODSUserTransactionFactory._debug_) {
         synchronized (SharkDODSUserTransaction.class) {
            noOfCreations++;
            System.out.println("CREATING User T No"+noOfCreations);
         }
         //Thread.dumpStack();
      }
      this.transaction=transaction;
   }

   /**
    * Returns DODS user transaction.
    *
    * @return DODS user transaction.
    */
   public DBTransaction getDODSTransaction () {
      return transaction;
   }

   /**
    * Commits DODS user transaction.
    *
    * @exception TransactionException If something unexpected happens.
    */
   public void commit () throws TransactionException {
      if (DODSUserTransactionFactory._debug_) {
         synchronized (SharkDODSUserTransaction.class) {
            System.out.println("COMMITING User T ");
         }
         //Thread.dumpStack();
      }
      try {
         transaction.commit();
         //transaction.release();
         if (DODSUserTransactionFactory._debug_) {
            synchronized (SharkDODSUserTransaction.class) {
               noOfCommits++;
               System.out.println("COMMITED User T No"+noOfCommits);
            }
            //Thread.dumpStack();
         }
      } catch (Exception ex) {
         throw new TransactionException(ex);
      }
   }

   /**
    * Rollbacks DODS user transaction.
    *
    * @exception TransactionException If something unexpected happens.
    */
   public void rollback () throws TransactionException {
      try {
         //transaction.rollback();
         //transaction.release();
         if (DODSUserTransactionFactory._debug_) {
            synchronized (SharkDODSUserTransaction.class) {
               noOfRollbacks++;
               System.out.println("ROLLING BACK User T"+noOfRollbacks);
            }
            //Thread.dumpStack();
   }

      } catch (Exception ex) {
         if (DODSUserTransactionFactory._debug_) {
            System.out.println("ROLLING BACK FAILED");
         }
         throw new TransactionException(ex);
      }
   }

   /**
    * Releases DODS user transaction.
    *
    * @exception TransactionException If something unexpected happens.
    */
   public void release() throws TransactionException {
      try {
         transaction.release();
         if (DODSUserTransactionFactory._debug_) {
            synchronized (SharkDODSUserTransaction.class) {
               noOfReleases++;
               System.out.println("RELEASE User T "+noOfReleases);
            }
         }
      } catch (Exception ex) {
         if (DODSUserTransactionFactory._debug_) {
            System.out.println("RELEASE User T FAILED");
         }
         throw new TransactionException(ex);
      }
   }

   /**
    * Writes info about DODS user transaction.
    */
   public static synchronized void info () {
      if (noOfCreations != noOfReleases) {
         System.err.println("PANIC!!!\nI've lost user transaction counts.");
      }
      System.err.println("UTCRE="+noOfCreations+", UTCOMM="+noOfCommits+", UTROLL="+noOfRollbacks+", UTREL="+noOfReleases);
   }
}
