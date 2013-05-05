/* LRUProcessCache.java */

package org.enhydra.shark.caching;

import org.enhydra.shark.api.internal.caching.*;
import org.enhydra.shark.api.internal.working.WfProcessInternal;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.utilities.LRUMap;

import org.enhydra.shark.api.internal.working.CallbackUtilities;

import java.util.*;

/**
 * This class is LRU (least recently used mechanism) cache for storing processes.
 * @author Sasa Bojanic
 * @author Tanja Jovanovic
 */
public class LRUProcessCache implements ProcessCache {
   private final int defaultCacheSize=100;

   /**
    * LRU process cache.
    */
   protected LRUMap cache;

   /**
    * Configures proccess cache.
    *
    * @param cus an instance of CallbackUtilities used to get
    *            properties for configuring proccess cache.
    * @exception  RootException Thrown if an error occurs.
    */
   public void configure (CallbackUtilities cus) throws RootException {
      cus.getProperties();
      String procCacheSize=cus.getProperty("LRUProcessCache.Size");
      try {
         int cacheSize=Integer.parseInt(procCacheSize.trim());
         cache = new LRUMap(cacheSize);
      } catch (Exception ex){
         cache = new LRUMap(defaultCacheSize);
      }
      cus.info("Process Cache configured - max. size is "+cache.getMaximumSize());
   }

   /**
    * Adds process to the process cache.
    *
    * @param    procId Process id.
    * @param    proc   WfProcessInternal object to be added to the process cache.
    *
    * @exception   RootException Thrown if an error occurs.
    */
   public void add (String procId, WfProcessInternal proc) throws RootException {
      synchronized(this) {
         //if (cache.get(proc.key())!=null) System.err.println("Warining - already have proc "+proc+" in cache!!!");
         cache.put(procId, proc);
      }
   }

   /**
    * Removes process from the process cache.
    *
    * @param    procId Process id.
    *
    * @exception   RootException Thrown if an error occurs.
    */
   public void remove (String procId) throws RootException {
      synchronized(this) {
         cache.remove(procId);
      }
   }

   /**
    * Returns the process from the process cache with id <i>procId</i>.
    *
    * @param    procId Process id.
    * @return   Process from the cache with the id <i>procId</i> if exists,
    *  otherwise null.
    * @exception   RootException Thrown if an error occurs.
    */
   public WfProcessInternal get (String procId) throws RootException {
      WfProcessInternal proc = null;
      synchronized(this) {
         proc=(WfProcessInternal)cache.get(procId);
      }
      return proc;
   }

   /**
    * Sets size of the process cache to value <i>size</i>. The value 0 means
    * that the cache is disabled. The negative value means that the cache
    * is unbounded. The positive number defines max number of cache entries.
    *
    * @param size New size of the process cache.
    * @exception   RootException Thrown if an error occurs.
    */
   public void setSize (int size) throws RootException {
      if (size<0) throw new RootException("Can't set negative process cache size");
      synchronized(this) {
         cache.setMaximumSize(size);
      }
   }

   /**
    * Returns the size of the process cache.
    *
    * @return Size of the process cache.
    * @exception   RootException Thrown if an error occurs.
    */
   public int getSize () throws RootException {
      return cache.getMaximumSize();
   }

   public int howManyEntries() throws RootException {
      return cache.size();
   }

   /**
    * Returns all processes from the process cache.
    *
    * @return All processes from the cache as List.
    * @exception   RootException Thrown if an error occurs.
    */
   public java.util.List getAll() throws RootException {
      if (cache.size()>0) {
         synchronized(this) {
            return new ArrayList(cache.values());
         }
      } else {
         return new ArrayList();
      }
   }

}

