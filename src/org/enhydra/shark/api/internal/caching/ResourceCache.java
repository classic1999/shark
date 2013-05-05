/* ResourceCache.java */

package org.enhydra.shark.api.internal.caching;

import org.enhydra.shark.api.internal.working.*;
import org.enhydra.shark.api.RootException;

/**
 * This interface presents cache that stores resources.
 * @author Vladimir Puskas
 * @author Tanja Jovanovic
 */
public interface ResourceCache extends ObjectCache {

   /**
    * Adds resource to the resource cache.
    *
    * @param    username username of the resource.
    * @param    res   WfResourceInternal object to be added to the resource cache.
    *
    * @exception   RootException Thrown if an error occurs.
    */
   public void add(String username, WfResourceInternal res) throws RootException;

   /**
    * Removes resource from the resource cache.
    *
    * @param    username username of the resource.
    * @exception   RootException Thrown if an error occurs.
    */
   public void remove(String username) throws RootException;

   /**
    * Returns the resource from the resource cache with username <i>username</i>.
    *
    * @param    username username of the resource.
    * @return   Resource from the cache with the username <i>username</i> if exists, 
    *  otherwise null.
    * @exception   RootException Thrown if an error occurs.
    */
   public WfResourceInternal get(String username) throws RootException;
}
/* End of ResourceCache.java */
