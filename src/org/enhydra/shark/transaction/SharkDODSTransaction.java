/* SharkDODSTransaction.java */

package org.enhydra.shark.transaction;



/**
 * Implementation of TransactionFactory interface that creates DODS transaction.
 *
 */

import com.lutris.appserver.server.sql.DBTransaction;
import java.util.HashMap;
import java.util.Map;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.TransactionException;
import org.enhydra.shark.api.internal.transaction.SharkInternalTransaction;
import org.enhydra.shark.api.internal.working.WfProcessInternal;
import org.enhydra.shark.api.internal.working.WfResourceInternal;
import org.enhydra.shark.utilities.dods.Buffer;

public class SharkDODSTransaction extends Buffer implements SharkInternalTransaction {

   private Map processes=new HashMap();
   private Map resources=new HashMap();

   private static int noOfCreations=0;
   private static int noOfCommits=0;
   private static int noOfRollbacks=0;
   private static int noOfReleases=0;

   //private DBTransaction transaction;

   public SharkDODSTransaction (DBTransaction transaction) {
      super(transaction);
      if (DODSTransactionFactory._debug_) {
         synchronized (SharkDODSTransaction.class) {
            noOfCreations++;
            System.out.println("CREATING T No"+noOfCreations);
         }
         //Thread.dumpStack();
      }
      //this.transaction=transaction;
   }

   public DBTransaction getDODSTransaction () {
      return transaction;
   }

   public void commit () throws TransactionException {
      if (DODSTransactionFactory._debug_) {
         synchronized (SharkDODSTransaction.class) {
            System.out.println("COMMITING T ");
         }
         //Thread.dumpStack();
      }
      try {
         write();
         transaction.commit();
         //System.err.println("success: "+baos.toString());
         //transaction.release();
         if (DODSTransactionFactory._debug_) {
            synchronized (SharkDODSTransaction.class) {
               noOfCommits++;
               System.out.println("COMMITED T No"+noOfCommits);
            }
            //Thread.dumpStack();
         }
      } catch (Exception ex) {
         throw new TransactionException(ex);
      } finally {
         processes.clear();
         resources.clear();
      }
   }

   public void rollback () throws TransactionException {
      try {
         //transaction.rollback();
         //transaction.release();
         if (DODSTransactionFactory._debug_) {
            synchronized (SharkDODSTransaction.class) {
               noOfRollbacks++;
               System.out.println("ROLLING BACK T "+noOfRollbacks);
            }
            //Thread.dumpStack();
         }
      } catch (Exception ex) {
         if (DODSTransactionFactory._debug_) {
            System.out.println("ROLLING BACK FAILED");
         }
         throw new TransactionException(ex);
      } finally {
         processes.clear();
         resources.clear();
      }
   }

   public void release() throws TransactionException {
      try {
         clear();
         transaction.release();
         if (DODSTransactionFactory._debug_) {
            synchronized (SharkDODSTransaction.class) {
               noOfReleases++;
               System.out.println("RELEASE T "+noOfReleases);
            }
         }
      } catch (Exception ex) {
         if (DODSTransactionFactory._debug_) {
            System.out.println("RELEASE FAILED");
         }
         throw new TransactionException(ex);
      } finally {
         processes.clear();
         resources.clear();
      }
   }

   public static synchronized void info () {
      if (noOfCreations != noOfReleases) {
         System.err.println("PANIC!!!\nI've lost transcaton counts.");
      }
      System.err.println("CRE="+noOfCreations+", COMM="+noOfCommits+", ROLL="+noOfRollbacks+", REL="+noOfReleases);
   }

   public void addToTransaction (String procId,WfProcessInternal proc) throws RootException {
      processes.put(procId,proc);
   }

   public void addToTransaction (String resUname,WfResourceInternal res) throws RootException {
      resources.put(resUname,res);
   }

   public void removeProcess (String procId) throws RootException {
      processes.remove(procId);
   }

   public void removeResource (String resUname) throws RootException {
      resources.remove(resUname);
   }

   public WfProcessInternal getProcess (String procId) throws RootException {
      return (WfProcessInternal)processes.get(procId);
   }

   public WfResourceInternal getResource (String resUname) throws RootException {
      return (WfResourceInternal)resources.get(resUname);
   }


}
/* End of SharkDODSTransaction.java */


