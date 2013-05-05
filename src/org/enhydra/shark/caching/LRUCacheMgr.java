/* LRUCacheMgr.java */

package org.enhydra.shark.caching;


import org.enhydra.shark.api.*;
import org.enhydra.shark.api.internal.caching.*;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

/**
 * This class creates and manages process cache and recource caches. 
 * These caches are LRU (least recently used mechanism) caches. 
 *
 * @author Sasa Bojanic
 * @author Tanja Jovanovic
 */
public class LRUCacheMgr implements CacheMgr {

   protected ProcessCache processes = null;
   protected ResourceCache resources = null;

   /**
    * Configures caches.
    *
    * @param cus an instance of CallbackUtilities used to get
    *            properties for configuring caches.
    * @exception  RootException Thrown if an error occurs.
    */ 
   public void configure (CallbackUtilities cus) throws RootException {
      processes=new LRUProcessCache();
      processes.configure(cus);
      resources=new LRUResourceCache();
      resources.configure(cus);
   }

   /**
    * Returns process cache.
    *
    * @return process cache.
    */
   public ProcessCache getProcessCache () {
      return processes;
   }

   /**
    * Returns resource cache.
    *
    * @return resource cache.
    */
   public ResourceCache getResourceCache() {
      return resources;
   }

}
/* End of LRUCacheMgr.java */
