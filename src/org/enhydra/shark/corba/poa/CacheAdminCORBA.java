package org.enhydra.shark.corba.poa;

import org.omg.WfBase.*;
import org.enhydra.shark.corba.WorkflowService.*;


/**
 * The implementation of client interface through which client can
 * configure shark's caches.
 * @author David Forslund
 */
public class CacheAdminCORBA extends CacheAdministrationPOA {
   private SharkCORBAServer myServer;

   private String userId;
   private boolean connected=false;

   org.enhydra.shark.api.client.wfservice.CacheAdministration myCacheAdmin;

   CacheAdminCORBA (SharkCORBAServer server,org.enhydra.shark.api.client.wfservice.CacheAdministration ca) {
      this.myServer=server;
      this.myCacheAdmin=ca;

   }

   public void connect(String userId, String password, String engineName, String scope) throws BaseException, ConnectFailed {
      this.userId=userId;

      try {
         if (!myServer.validateUser(userId,password)) {
            throw new ConnectFailed("Connection failed, invalid username or password");
         }
         connected=true;
         myCacheAdmin.connect(userId);
      } catch (ConnectFailed cf) {
         throw cf;
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void disconnect() throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      connected=false;
      _this()._release();
   }


   public int getProcessCacheSize () throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myCacheAdmin.getProcessCacheSize();
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public void setProcessCacheSize (int size) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         myCacheAdmin.setProcessCacheSize(size);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public int howManyCachedProcesses() throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myCacheAdmin.howManyCachedProcesses();
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public void clearProcessCache () throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         myCacheAdmin.clearProcessCache();
      } catch (Exception e) {
         throw new BaseException();
      }
   }


   public int getResourceCacheSize () throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myCacheAdmin.getResourceCacheSize();
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public void setResourceCacheSize(int size) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         myCacheAdmin.setResourceCacheSize(size);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public int howManyCachedResources() throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myCacheAdmin.howManyCachedResources();
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public void clearResourceCache () throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         myCacheAdmin.clearResourceCache();
      } catch (Exception e) {
         throw new BaseException();
      }
   }

}
