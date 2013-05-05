/*
 * DODSApplicationMappingTransaction.java. Created on Apr 29, 2004.
 */
package org.enhydra.shark.appmappersistence;

import org.enhydra.shark.api.ApplicationMappingTransaction;
import org.enhydra.shark.api.TransactionException;

import com.lutris.appserver.server.sql.DBTransaction;

/**
 * Implementation of ApplicationMappingTransaction.
 *
 * @author Zoran Milakovic
 */
public class DODSApplicationMappingTransaction implements ApplicationMappingTransaction {

   private static int noOfCreations=0;
   private static int noOfCommits=0;
   private static int noOfRollbacks=0;
   private static int noOfReleases=0;

   private DBTransaction transaction;

   public DODSApplicationMappingTransaction (DBTransaction transaction) {
      if (DODSApplicationMappingMgr._debug_) {
         synchronized (DODSApplicationMappingTransaction.class) {
            noOfCreations++;
            System.out.println("CREATING Mapping T No"+noOfCreations);
         }
         //Thread.dumpStack();
      }
      this.transaction=transaction;
   }

   public DBTransaction getDODSTransaction () {
      return transaction;
   }

   public void commit () throws TransactionException {
      if (DODSApplicationMappingMgr._debug_) {
         synchronized (DODSApplicationMappingTransaction.class) {
            System.out.println("COMMITING Mapping T ");
         }
         //Thread.dumpStack();
      }
      try {
         transaction.commit();
         //transaction.release();
         if (DODSApplicationMappingMgr._debug_) {
            synchronized (DODSApplicationMappingTransaction.class) {
               noOfCommits++;
               System.out.println("COMMITED Mapping T No"+noOfCommits);
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
         if (DODSApplicationMappingMgr._debug_) {
            synchronized (DODSApplicationMappingTransaction.class) {
               noOfRollbacks++;
               System.out.println("ROLLING BACK Mapping T"+noOfRollbacks);
            }
            //Thread.dumpStack();
         }
      } catch (Exception ex) {
         if (DODSApplicationMappingMgr._debug_) {
            System.out.println("ROLLING BACK Mapping T FAILED");
         }
         throw new TransactionException(ex);
      }
   }

   public void release() throws TransactionException {
      try {
         transaction.release();
         if (DODSApplicationMappingMgr._debug_) {
            synchronized (DODSApplicationMappingTransaction.class) {
               noOfReleases++;
               System.out.println("RELEASE Mapping T "+noOfReleases);
            }
         }
      } catch (Exception ex) {
         if (DODSApplicationMappingMgr._debug_) {
            System.out.println("RELEASE Mapping T FAILED");
         }
         throw new TransactionException(ex);
      }
   }


   public static synchronized void info () {
      if (noOfCreations != noOfReleases) {
         System.err.println("PANIC!!!\nI've lost mapping transcaton counts.");
      }
      System.err.println("MTCRE="+noOfCreations+", MTCOMM="+noOfCommits+", MTROLL="+noOfRollbacks+", MTREL="+noOfReleases);
   }

}
