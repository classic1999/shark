/* CacheMgr.java */

package org.enhydra.shark.api.internal.caching;


import org.enhydra.shark.api.*;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

/**
 * This inferface presents manager for process cache and recource cache. 
 *
 * @author Sasa Bojanic
 * @author Nenad Stefanovic
 * @version 1.0
 */
public interface CacheMgr {

   /**
    * Configures caches.
    *
    * @param cus an instance of CallbackUtilities used to get
    *            properties for configuring caches.
    * @exception  RootException Thrown if an error occurs.
    */
   void configure (CallbackUtilities cus) throws RootException;

   /**
    * Returns process cache.
    *
    * @return Process cache.
    */
   public ProcessCache getProcessCache ();

   /**
    * Returns resource cache.
    *
    * @return Resource cache.
    */
   public ResourceCache getResourceCache();


}
/* End of CacheMgr.java */
