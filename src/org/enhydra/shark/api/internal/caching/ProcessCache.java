/* CacheMgr.java */

package org.enhydra.shark.api.internal.caching;

import org.enhydra.shark.api.internal.working.WfProcessInternal;
import org.enhydra.shark.api.RootException;

/**
 * This interface presents cache that stores processes.
 * @author Sasa Bojanic
 * @author Tanja Jovanovic
 */
public interface ProcessCache extends ObjectCache {

   /**
    * Adds process to the process cache.
    *
    * @param    procId process id.
    * @param    proc   WfProcessInternal object to be added to the process cache.
    *
    * @exception   RootException Thrown if an error occurs.
    */
   public void add (String procId, WfProcessInternal proc) throws RootException;

   /**
    * Removes process from the process cache.
    *
    * @param    procId process id.
    *
    * @exception   RootException Thrown if an error occurs.
    */
   public void remove (String procId) throws RootException;

   /**
    * Returns the process from the process cache with id <i>procId</i>.
    *
    * @param    procId process id.
    * @return   Process from the cache with the id <i>procId</i> if exists, 
    *  otherwise null.
    * @exception   RootException Thrown if an error occurs.
    */
   public WfProcessInternal get (String procId) throws RootException;
}
