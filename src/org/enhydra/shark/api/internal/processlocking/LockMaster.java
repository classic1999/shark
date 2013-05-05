/* LockMaster.java */

package org.enhydra.shark.api.internal.processlocking;

import java.util.List;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

/**
 * LockMaster interface defines methods a lock master
 * implementation must have.
 * <p>
 * Shark library allows multiple transactions/threads to be executed
 * simultaneously. This introduces a problem when two client invoked threads
 * want to change same process (or its contents), because one of their
 * transactions is going to fail. Anyways, simultaneous modifications of
 * the same data may induce other ambiguities, so Shark needs a tool to
 * fend itself - a lock master.
 * <p>
 * SharkEngineManager will assure that there is one, and only one instance of
 * lock master instantiated. This lock master will be asked for the exclusive
 * lock of a process, before transaction starts to do anything about it. After
 * transaction has finished (regardlessly on its own success) it releases
 * the lock, so next transaction may go forward.
 *
 * @author Vladimir Puskas
 * @version 1.0.3
 */
public interface LockMaster {

   /**
    * Method configure is called at Shark start up, to configure
    * implementation of LockMaster.
    *
    * @param cbImpl an instance of CallbackUtilities used to get
    *            properties for configuring LockMaster in Shark.
    *
    * @exception RootException Thrown if configuring doesn't succeed.
    */
   public void configure(CallbackUtilities cbImpl) throws RootException;
   
   /**
    * Method lock ensures that nobody else (other threads) has same processId.
    * This method may wait a while before acquiring lock requested.
    * Implementation may set a timeout to limit the time spent waiting.
    *
    * @param t SharkTransaction during wich lock is required.
    * @param processId lock identifier.
    * @param timeout limits waiting for this specific lock:
    *                  value < 0 means wait forever
    *                  value >= 0 specifies number of miliseconds
    *                  null value means use default.
    * @return true if lock is acquired without waiting, false otherwise.
    * @exception RootException Gets thrown if timeout expires.
    */
   public boolean lock(SharkTransaction t,
                       String processId,
                       Long timeout) throws RootException;

   /**
    * Method lock ensures that nobody else (other threads) has same processId.
    * This method may wait a while before acquiring lock requested.
    * Implementation may set a timeout to limit the time spent waiting.
    *
    * @param t SharkTransaction during which lock is required.
    * @param processId lock identifier.
    * @return   true if lock is acquired without waiting, false otherwise.
    * @exception RootException Gets thrown if timeout expires.
    */
   public boolean lock(SharkTransaction t, String processId) throws RootException;

   /**
    * Method unlock releases processId, for other threads to get it.
    *
    * @param t SharkTransaction during which lock is required.
    * @param processId lock identifier.
    * @exception   RootException If something unexpected happens.
    */
   public void unlock(SharkTransaction t, String processId) throws RootException;

   /**
    * Method unlock releases all locks SharkTransaction had acquired.
    *
    * @param t SharkTransaction during which locks had been obtained.
    * @exception   RootException If something unexpected happens.
    */
   public void unlock(SharkTransaction t) throws RootException;

   /**
    * Gets locks for the SharkTransaction.
    *
    * @param t SharkTransaction during which lock is required.
    * @return List of processIds SharkTransaction had previously obtained.
    * @exception RootException If something unexpected happens.
    */
   public List getLocks(SharkTransaction t) throws RootException;

}
/* End of LockMaster.java */

