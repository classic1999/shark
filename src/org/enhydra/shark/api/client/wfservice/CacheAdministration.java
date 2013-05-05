package org.enhydra.shark.api.client.wfservice;

import org.enhydra.shark.api.client.wfbase.BaseException;

/**
 * Interface used to perform some administrative operations on shark's caches.
 * @author Sasa Bojanic
 */
public interface CacheAdministration {

   /**
    * This method is used to let shark know who uses this API.
    *
    * @param    userId              String representing user Id.
    *
    */
   void connect (String userId);

   /** Returns process cache size. */
   int getProcessCacheSize () throws BaseException;

   /** Changes the size of process cache. */
   void setProcessCacheSize (int size) throws BaseException;

   /** Returns the number of currently cached process objects. */
   int howManyCachedProcesses () throws BaseException;

   /** Clears process cache. */
   void clearProcessCache () throws BaseException;

   /** Returns resource cache size. */
   int getResourceCacheSize () throws BaseException;

   /** Changes the size of resource cache. */
   void setResourceCacheSize (int size) throws BaseException;

   /** Returns the number of currently cached resource objects. */
   int howManyCachedResources () throws BaseException;

   /** Clears resource cache. */
   void clearResourceCache () throws BaseException;

}
