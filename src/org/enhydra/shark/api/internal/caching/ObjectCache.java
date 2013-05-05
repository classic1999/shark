/* CacheMgr.java */

package org.enhydra.shark.api.internal.caching;

import org.enhydra.shark.api.RootException;

import org.enhydra.shark.api.internal.working.CallbackUtilities;
/**
 * Interface for basic cache methods.
 * @author Sasa Bojanic
 * @author Tanja Jovanovic
 */
public interface ObjectCache {

   /**
    * Configures cache.
    *
    * @param cus an instance of CallbackUtilities used to get
    *            properties for configuring cache.
    * @exception  RootException Thrown if an error occurs.
    */
   void configure (CallbackUtilities cus) throws RootException;

   /**
    * Sets size of the cache to value <i>size</i>. The value 0 means that the
    * cache is disabled. The negative value means that the cache is unbounded.
    * The positive number defines max number of cache entries.
    *
    * @param size new size of the cache.
    * @exception   RootException Thrown if an error occurs.
    */
   public void setSize (int size) throws RootException;

   /**
    * Returns the size of the cache.
    *
    * @return  Size of the cache.
    * @exception   RootException Thrown if an error occurs.
    */
   public int getSize () throws RootException;

   /**
    * Returns the number of entries in the cache.
    *
    * @return The number of entries in the cache.
    * @exception   RootException Thrown if an error occurs.
    */
   public int howManyEntries () throws RootException;

   /**
    * Returns all values from the cache.
    *
    * @return All values from the cache as List.
    * @exception   RootException Thrown if an error occurs.
    */
   public java.util.List getAll () throws RootException;

}

