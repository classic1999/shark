package org.enhydra.shark.repositorypersistence;

import org.enhydra.shark.api.*;
import com.lutris.appserver.server.sql.DBTransaction;


/**
 * Implementation of RepositoryTransaction interface
 * @author Sasa Bojanic
 */
public class DODSRepositoryTransaction implements RepositoryTransaction {

   private static int noOfCreations=0;
   private static int noOfCommits=0;
   private static int noOfRollbacks=0;
   private static int noOfReleases=0;

   private DBTransaction transaction;

   public DODSRepositoryTransaction (DBTransaction transaction) {
      if (DODSRepositoryPersistenceManager._debug_) {
         synchronized (DODSRepositoryTransaction.class) {
            noOfCreations++;
            System.out.println("CREATING Repository T No"+noOfCreations);
         }
         //Thread.dumpStack();
      }
      this.transaction=transaction;
   }

   public DBTransaction getDODSTransaction () {
      return transaction;
   }

   public void commit () throws TransactionException {
      if (DODSRepositoryPersistenceManager._debug_) {
         synchronized (DODSRepositoryTransaction.class) {
            System.out.println("COMMITING Repository T ");
         }
         //Thread.dumpStack();
      }
      try {
         transaction.commit();
         //transaction.release();
         if (DODSRepositoryPersistenceManager._debug_) {
            synchronized (DODSRepositoryTransaction.class) {
               noOfCommits++;
               System.out.println("COMMITED Repository T No"+noOfCommits);
            }
            //Thread.dumpStack();
         }
      } catch (Exception ex) {
         ex.printStackTrace();
         throw new TransactionException(ex);
      }
   }

   public void rollback () throws TransactionException {
      try {
         //transaction.rollback();
         //transaction.release();
         if (DODSRepositoryPersistenceManager._debug_) {
            synchronized (DODSRepositoryTransaction.class) {
               noOfRollbacks++;
               System.out.println("ROLLING BACK Repository T"+noOfRollbacks);
            }
            //Thread.dumpStack();
         }
      } catch (Exception ex) {
         if (DODSRepositoryPersistenceManager._debug_) {
            System.out.println("ROLLING BACK Repository T FAILED");
         }
         throw new TransactionException(ex);
      }
   }

   public void release() throws TransactionException {
      try {
         transaction.release();
         if (DODSRepositoryPersistenceManager._debug_) {
            synchronized (DODSRepositoryTransaction.class) {
               noOfReleases++;
               System.out.println("RELEASE Repository T "+noOfReleases);
            }
         }
      } catch (Exception ex) {
         if (DODSRepositoryPersistenceManager._debug_) {
            System.out.println("RELEASE Repository T FAILED");
         }
         throw new TransactionException(ex);
      }
   }


   public static synchronized void info () {
      if (noOfCreations != noOfReleases) {
         System.err.println("PANIC!!!\nI've lost repository transcaton counts.");
      }
      System.err.println("MTCRE="+noOfCreations+", MTCOMM="+noOfCommits+", MTROLL="+noOfRollbacks+", MTREL="+noOfReleases);
   }

}

