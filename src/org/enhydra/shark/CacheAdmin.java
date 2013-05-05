package org.enhydra.shark;

import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfservice.CacheAdministration;
import org.enhydra.shark.api.internal.caching.CacheMgr;
import org.enhydra.shark.api.internal.caching.ProcessCache;
import org.enhydra.shark.api.internal.caching.ResourceCache;

/**
 * The implementation of client interface through which client can
 * configure shark's caches.
 *
 * @author Sasa Bojanic
 */
public class CacheAdmin implements CacheAdministration {

   private String userId="Unknown";

   protected CacheAdmin () {
   }

   public void connect (String userId) {
      this.userId=userId;
   }

   public int getProcessCacheSize () throws BaseException {
      CacheMgr mgr=SharkEngineManager.getInstance().getCacheManager();
      if (mgr==null) {
         throw new BaseException("Working without internal cache API implementation!");
      }
      try {
         return mgr.getProcessCache().getSize();
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public void setProcessCacheSize (int size) throws BaseException {
      CacheMgr mgr=SharkEngineManager.getInstance().getCacheManager();
      if (mgr==null) {
         throw new BaseException("Working without internal cache API implementation!");
      }
      try {
         mgr.getProcessCache().setSize(size);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public int howManyCachedProcesses() throws BaseException {
      CacheMgr mgr=SharkEngineManager.getInstance().getCacheManager();
      if (mgr==null) {
         throw new BaseException("Working without internal cache API implementation!");
      }
      try {
         return mgr.getProcessCache().howManyEntries();
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public void clearProcessCache () throws BaseException {
      CacheMgr mgr=SharkEngineManager.getInstance().getCacheManager();
      if (mgr==null) {
         throw new BaseException("Working without internal cache API implementation!");
      }
      try {
         ProcessCache pc=mgr.getProcessCache();
         int size=pc.getSize();
         pc.setSize(0);
         pc.setSize(size);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public int getResourceCacheSize () throws BaseException {
      CacheMgr mgr=SharkEngineManager.getInstance().getCacheManager();
      if (mgr==null) {
         throw new BaseException("Working without internal cache API implementation!");
      }
      try {
         return mgr.getResourceCache().getSize();
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public void setResourceCacheSize (int size) throws BaseException {
      CacheMgr mgr=SharkEngineManager.getInstance().getCacheManager();
      if (mgr==null) {
         throw new BaseException("Working without internal cache API implementation!");
      }
      try {
         mgr.getResourceCache().setSize(size);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public int howManyCachedResources () throws BaseException {
      CacheMgr mgr=SharkEngineManager.getInstance().getCacheManager();
      if (mgr==null) {
         throw new BaseException("Working without internal cache API implementation!");
      }
      try {
         return mgr.getResourceCache().howManyEntries();
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public void clearResourceCache () throws BaseException {
      CacheMgr mgr=SharkEngineManager.getInstance().getCacheManager();
      if (mgr==null) {
         throw new BaseException("Working without internal cache API implementation!");
      }
      try {
         ResourceCache rc=mgr.getResourceCache();
         int size=rc.getSize();
         rc.setSize(0);
         rc.setSize(size);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

}

