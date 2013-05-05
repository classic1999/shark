/* LRUResourceCache.java */

package org.enhydra.shark.caching;

import java.util.*;

import org.enhydra.shark.api.internal.caching.ResourceCache;
import org.enhydra.shark.api.internal.working.WfResourceInternal;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.utilities.LRUMap;

import org.enhydra.shark.api.internal.working.CallbackUtilities;
/**
 * This class is LRU (least recently used mechanism) cache for storing resources.
 * @author Vladimir Puskas
 * @author Tanja Jovanovic
 */
public class LRUResourceCache implements ResourceCache {

   private final int defaultCacheSize=100;

   /**
    * LRU resource cache.
    */
   protected LRUMap cache;

   /**
    * Configures resource cache.
    *
    * @param cus an instance of CallbackUtilities used to get
    *            properties for configuring resource cache.
    * @exception  RootException Thrown if an error occurs.
    */
   public void configure (CallbackUtilities cus) throws RootException {
      cus.getProperties();
      String resCacheSize=cus.getProperty("LRUResourceCache.Size");
      try {
         int cacheSize=Integer.parseInt(resCacheSize.trim());
         cache = new LRUMap(cacheSize);
      } catch (Exception ex){
         cache = new LRUMap(defaultCacheSize);
      }
      cus.info("Resource Cache configured - max. size is "+cache.getMaximumSize());
   }

   /**
    * Adds resource to the resource cache.
    *
    * @param    username Username of the resource.
    * @param    res   WfResourceInternal object to be added to the resource cache.
    *
    * @exception   RootException Thrown if an error occurs.
    */
   public void add(String username, WfResourceInternal res) throws RootException {
     synchronized(this) {
        //if (cache.get(res.resource_key())!=null) System.err.println("Warning - already have res "+res+" in cache!!!");
        cache.put(username, res);
     }
   }

   /**
    * Removes resource from the resource cache.
    *
    * @param    username Username of the resource.
    * @exception   RootException Thrown if an error occurs.
    */
   public void remove(String username) throws RootException {
     synchronized(this) {
        cache.remove(username);
     }
   }

   /**
    * Returns the resource from the resource cache with username <i>username</i>.
    *
    * @param    username Username of the resource.
    * @return   Resource from the cache with the username <i>username</i> if exists,
    *  otherwise null.
    * @exception   RootException Thrown if an error occurs.
    */
   public synchronized WfResourceInternal get(String username) throws RootException {
     WfResourceInternal res = null;
     synchronized(this) {
        res =(WfResourceInternal)cache.get(username);
     }
     return res;
   }

   /**
    * Returns all resources from the resource cache.
    *
    * @return All resources from the cache as List.
    * @exception   RootException Thrown if an error occurs.
    */
   public java.util.List getAll () throws RootException {
      if (cache.size()> 0) {
         synchronized(this) {
            return new ArrayList(cache.values());
         }
      } else {
         return new ArrayList();
      }
   }

   /**
    * Sets size of the resource cache to value <i>size</i>. The value 0 means
    * that the cache is disabled. The negative value means that the cache
    * is unbounded. The positive number defines max number of cache entries.
    *
    * @param size New size of the resource cache.
    * @exception   RootException Thrown if an error occurs.
    */
   public void setSize (int size) throws RootException {
      if (size<0) throw new RootException("Can't set negative resource cache size");
      synchronized(this) {
         cache.setMaximumSize(size);
      }
   }

   /**
    * Returns the size of the resource cache.
    *
    * @return Size of the resource cache.
    * @exception   RootException Thrown if an error occurs.
    */
   public int getSize () throws RootException {
      return cache.getMaximumSize();
   }

   public int howManyEntries() throws RootException {
      return cache.size();
   }

}
/* End of LRUResourceCache.java */

