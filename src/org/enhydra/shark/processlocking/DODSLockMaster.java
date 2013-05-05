/* DatabaseLockMaster.java */

package org.enhydra.shark.processlocking;

import java.util.*;

import com.lutris.appserver.server.sql.DBTransaction;
import com.lutris.appserver.server.sql.DatabaseManagerException;
import com.lutris.appserver.server.sql.LogicalDatabase;
import org.enhydra.dods.DODS;
import org.enhydra.shark.api.RootError;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.internal.processlocking.LockMaster;
import org.enhydra.shark.api.internal.working.CallbackUtilities;
import org.enhydra.shark.processlocking.data.LockEntryDO;
import org.enhydra.shark.processlocking.data.LockEntryDelete;
import org.enhydra.shark.processlocking.data.LockEntryQuery;
import org.enhydra.shark.utilities.dods.DODSUtilities;

/**
 * DODSLockMaster is special implementation of Shark's LockMaster, which
 * uses database (actually DODS accessed DB) as a lock repository, whereas
 * simple lock master has only Vector in memory.
 * <p>
 * DODSLockMaster completely circumvents Shark's persistence layer, communicating
 * directly to DODS classes, to store and delete lock entries in database.
 *
 * @author Vladimir Puskas
 * @version 0.2
 */
public class DODSLockMaster implements LockMaster {

   private static final String ENG_PARAM_NAME = "enginename";
   private static final String TOUT_PARM_NAME = "DODSLockMaster.Timeout";
   private static final String LWT_PARAM_NAME = "DODSLockMaster.LockWaitTime";
   private static final String DBG_PARAM_NAME = "DODSLockMaster.debug";
   private static final String LDB_PARAM_NAME = "DODSLockMaster.DatabaseName";

   private Long defaultTimeout;
   private long lockWaitTime;
   private CallbackUtilities callback;
   private LogicalDatabase db;
   private Map locks;
   private String lockMasterName;
   private boolean cleanStarted;
   private boolean _debug_;

   /**
    * Default constructor
    */
   public DODSLockMaster() {
      locks = new HashMap();
      defaultTimeout = null;
      cleanStarted = false;
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
      DODSUtilities.init(callback.getProperties());
      if (LockEntryDO
             .getConfigurationAdministration()
             .getCacheAdministration(0)
             .getMaxCacheSize() > 0) {
         callback.error("cache for lock entries isn't allowed!!!");
         throw new RootError("cache for lock entries isn't allowed!!!");
      }
      lockMasterName = callback.getProperty(ENG_PARAM_NAME,"dodsLockMaster");
      defaultTimeout = new Long(callback.getProperty(TOUT_PARM_NAME, "-1"));
      lockWaitTime = Long.parseLong(callback.getProperty(LWT_PARAM_NAME,"100"));
      String dbName = callback
         .getProperty(LDB_PARAM_NAME, DODS.getDatabaseManager().getDefaultDB());
      _debug_ = Boolean
         .valueOf(callback.getProperty(DBG_PARAM_NAME, "false"))
         .booleanValue();
      try {
         db = DODS.getDatabaseManager().findLogicalDatabase(dbName);
      } catch (DatabaseManagerException e) {
         throw new RootException("Couldn't find logical database.", e);
      }
      if (!cleanStarted) {
         _cleanAllLocks();
         cleanStarted = true;
      }
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
               callback.error("DODSLockMaster", tme);
               throw tme;
            }
         }
      }
      if (_debug_) System.err.println("LOCK:"+processId+":"+t);
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
         if (_debug_) System.err.println("UNLOCK:"+processId+":"+t);
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
      DBTransaction dbt = createTransaction();
      try {
         for (Iterator it = processLocks.iterator(); it.hasNext();) {
            String processId = (String)it.next();
            LockEntryQuery qry = new LockEntryQuery(dbt);
            qry.setQueryEngineName(lockMasterName);
            qry.setQueryId(processId);
            //qry.requireUniqueInstance();
            LockEntryDelete led = new LockEntryDelete(qry);
            led.save();
            //qry.getNextDO().delete();
            locks.remove(processId);
            if (_debug_) System.err.println("gUNLOCK:"+processId+":"+t);
         }
         dbt.commit();
      } catch (Exception e) {
         throw new RootException(e);
      } finally {
         dbt.release();
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
   private List retrieveLocks() {
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
   private synchronized boolean hasLock(String processId) throws RootException {
      Thread lockOwner = (Thread)locks.get(processId);
      if (null == lockOwner) {
         DBTransaction dbt = createTransaction();
         try {
            LockEntryDO le = LockEntryDO.createVirgin(dbt);
            le.setEngineName(lockMasterName);
            le.setId(processId);
            le.save();
            dbt.commit();
         } catch (Exception e) {
            return true;
         } finally {
            dbt.release();
         }
         locks.put(processId, Thread.currentThread());
         return false;
      } else if (lockOwner.equals(Thread.currentThread())) {
         return false;
      } else {
         return true;
      }
      /**/
   }

   /***/
   private synchronized void removeLock(String processId) throws RootException {
      Thread lockOwner = (Thread)locks.get(processId);
      if (Thread.currentThread().equals(lockOwner)) {
         DBTransaction dbt = createTransaction();
         try {
            LockEntryQuery qry = new LockEntryQuery(dbt);
            qry.setQueryEngineName(lockMasterName);
            qry.setQueryId(processId);
            qry.requireUniqueInstance();
            LockEntryDO row = qry.getNextDO();
            row.delete();
            dbt.commit();
         } catch (Exception e) {
            throw new RootException(e);
         } finally {
            dbt.release();
         }
         locks.remove(processId);
      } else {
         RootException tme = new RootException
            (new StringBuffer("Trying to unlock ")
                .append(processId)
                .append(" while it hasn't been locked ?!?")
                .toString());
         callback.error("DODSLockMaster", tme);
         throw tme;
      }
   }

   /***/
   private void _cleanAllLocks() throws RootException {
      DBTransaction dbt = createTransaction();
      try {
         LockEntryQuery leqry = new LockEntryQuery(dbt);
         leqry.setQueryEngineName(lockMasterName);
         new LockEntryDelete(leqry).save();
         dbt.commit();
      } catch (Exception e) {
         throw new RootException(e);
      } finally {
         dbt.release();
      }
   }

   /***/
   private DBTransaction createTransaction() throws RootException {
      try {
         return db.createTransaction();
      } catch (Throwable t) {
         throw new RootException
            (new StringBuffer("Didn't create transaction, there are ")
                .append(db.getActiveConnectionCount())
                .append(" active connections.")
                .toString(), t);
      }
   }
}
/* End of DatabaseLockMaster.java */

