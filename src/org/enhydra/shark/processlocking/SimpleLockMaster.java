/* SimpleLockMaster.java */

package org.enhydra.shark.processlocking;

import java.util.*;

import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.internal.processlocking.LockMaster;
import org.enhydra.shark.api.internal.working.CallbackUtilities;


/**
 * This LockMaster implementation is good only for one virtual machine
 * running Shark.
 *
 * @author Vladimir Puskas
 * @version 0.3
 */
public class SimpleLockMaster implements LockMaster {

   private static final String ENG_PARAM_NAME = "enginename";
   private static final String TOUT_PARM_NAME = "SimpleLockMaster.Timeout";
   private static final String LWT_PARAM_NAME = "SimpleLockMaster.LockWaitTime";

   private Long defaultTimeout;
   private Map locks;
   private long lockWaitTime;
   private CallbackUtilities callback;

   /**
    * Default constructor
    */
   public SimpleLockMaster() {
      locks = new HashMap();
      defaultTimeout = null;
   }

   /**
    * Configures instance of LockMaster implementaition, setting its
    * name and default timeout.
    *
    * @param    cbImpl              a  CallbackUtilities
    *
    * @exception   RootException
    */
   public void configure(CallbackUtilities cbImpl) throws RootException {
      if (null == cbImpl)
         throw new RootException("Cannot configure without call back impl.");
      callback = cbImpl;
      String lockMasterName = callback.getProperty(ENG_PARAM_NAME,"simpleLockMaster");
      defaultTimeout = new Long(callback.getProperty(TOUT_PARM_NAME, "-1"));
      lockWaitTime = Long.parseLong(callback.getProperty(LWT_PARAM_NAME,"100"));
      callback.debug(new StringBuffer(lockMasterName)
                        .append(" startup, timeout is ")
                        .append(defaultTimeout)
                        .toString());
   }

   /**
    * Method lock ensures that nobody else (other threads) has same processId.
    * This method may wait a while before acquiring lock requested.
    * Implementation may set a timeout to limit the time spent waiting.
    *
    * @param t - SharkTransaction during wich lock is required
    * @param processId - lock identifier
    * @param timeout - limits waiting for this specific lock:
    *                  value < 0 means wait forever
    *                  value >= 0 specifies number of miliseconds
    *                  null value means use default
    * @return true if lock is acquired without waiting, false otherwise
    * @exception RootException gets thrown if timeout expires
    */
   public boolean lock(SharkTransaction t,
                       String processId,
                       Long timeout) throws RootException {
      boolean ret = true;
      if (null != processId) {
         if (null == timeout)
            timeout = defaultTimeout;
         long limit = timeout.longValue();
         boolean checkTimeout = 0 < limit;
         limit += System.currentTimeMillis();

         while (hasLock(processId)) {
            ret = false;
            try {
               Thread.sleep(lockWaitTime);
            } catch (Exception e) {}
            if (checkTimeout && (System.currentTimeMillis() > limit)) {
               RootException tme = new RootException
                  (new StringBuffer("Timeout expired waiting on ")
                      .append(processId)
                      .toString());
               callback.error("SimpleLockMaster", tme);
               throw tme;
            }
         }
      }
      return ret;
   }

   /**
    * Method lock ensures that nobody else (other threads) has same processId.
    * This method may wait a while before acquiring lock requested.
    *
    * @param    processId              lock identifier
    * @return   true if lock is acquired without waiting, false otherwise
    * @exception   TransactionMgrException
    */
   public boolean lock(SharkTransaction t,
                       String processId) throws RootException {
      return lock(t, processId, null);
   }

   /**
    * @param processId
    * @throws TransactionMgrException
    */
   public void unlock(SharkTransaction t,
                      String processId) throws RootException {
      if (null != processId) {
         removeLock(processId);
      }
   }

   /**
    * Method unlock releases all locks SharkTransaction had acquired.
    *
    * @param t - SharkTransaction during which locks had been obtained.
    * @exception   RootException
    */
   public synchronized void unlock(SharkTransaction t) throws RootException {
      List processLocks = retrieveLocks();
      if (null == processLocks) {
         throw new RootException("Transaction hasn't locked anything");
      }
      for (Iterator it = processLocks.iterator(); it.hasNext();) {
         locks.remove(it.next());
      }
   }

   /**
    * Gets locks for the SharkTransaction.
    *
    * @param t - SharkTransaction during which lock is required
    * @return a List of processIds SharkTransaction had previously obtained.
    * @exception RootException
    */
   public List getLocks(SharkTransaction t) throws RootException {
      List transactionLocks = retrieveLocks();
      if (null == transactionLocks)
         throw new RootException("Transaction hasn't locked anything");
      return transactionLocks;
   }

   /***/
   private synchronized List retrieveLocks() {
      List ret = new ArrayList();
      Set entries = locks.entrySet();
      Thread th = Thread.currentThread();
      for (Iterator it = entries.iterator(); it.hasNext();) {
         Map.Entry me = (Map.Entry)it.next();
         if (th.equals(me.getValue())) {
            ret.add(me.getKey());
         }
      }
      return ret;
   }

   /***/
   private synchronized boolean hasLock(String processId) {
      Thread lockOwner = (Thread)locks.get(processId);
      if (null == lockOwner) {
         locks.put(processId, Thread.currentThread());
         return false;
      } else if (lockOwner.equals(Thread.currentThread())) {
         return false;
      } else {
         return true;
      }
   }

   /***/
   private synchronized void removeLock(String processId) throws RootException {
      Thread lockOwner = (Thread)locks.get(processId);
      if (Thread.currentThread().equals(lockOwner)) {
         locks.remove(processId);
      } else {
         RootException tme = new RootException
            (new StringBuffer("Trying to unlock ")
                .append(processId)
                .append(" while it hasn't been locked ?!?")
                .toString());
         callback.error("SimpleLockMaster", tme);
         throw tme;
      }
   }
   synchronized void info() {
      System.err.print(locks.size()+" locks ");
   }
}
/* End of SimpleLockMaster.java */
