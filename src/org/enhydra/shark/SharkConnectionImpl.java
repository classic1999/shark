package org.enhydra.shark;

import java.io.Serializable;

import org.enhydra.shark.api.client.wfmodel.*;

import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfservice.ConnectFailed;
import org.enhydra.shark.api.client.wfservice.NotConnected;
import org.enhydra.shark.api.client.wfservice.SharkConnection;
import org.enhydra.shark.api.internal.security.SecurityManager;
import org.enhydra.shark.api.internal.working.CallbackUtilities;
import org.enhydra.shark.api.internal.working.WfProcessInternal;
import org.enhydra.shark.api.internal.working.WfProcessMgrInternal;
import org.enhydra.shark.api.internal.working.WfRequesterInternal;
import org.enhydra.shark.api.internal.working.WfResourceInternal;

/**
 * The client interface through which client accesses the engine objects, and
 * performs the various actions on engine.
 * @author Sasa Bojanic, Vladimir Puskas
 * @version 1.0
 */
public class SharkConnectionImpl implements SharkConnection, Serializable {

   private String userId;
   private boolean connected=false;

   private String connectionKey;

   private CallbackUtilities cus;

   protected SharkConnectionImpl () {
      this.cus=SharkEngineManager.getInstance().getCallbackUtilities();
   }

   public void connect (String userId,String password,String engineName,String scope) throws BaseException, ConnectFailed {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         connect(t, userId, password, engineName, scope);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof ConnectFailed)
            throw (ConnectFailed)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void connect (SharkTransaction t,String userId,String password,String engineName,String scope) throws BaseException, ConnectFailed {
      this.userId=userId;
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_sharkconnection_connect(t,userId);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      try {
         if (!SharkUtilities.validateUser(userId,password)) {
            cus.info("SharkConnectionImpl -> Login failed: Invalid username or password "+userId);
            throw new ConnectFailed("Connection failed, invalid username or password");
         }

         if (SharkUtilities.getResource(t, userId)==null) {
            WfResourceInternal resInternal=SharkEngineManager
               .getInstance()
               .getObjectFactory()
               .createResource(t,userId);
         }
         connectionKey=SharkUtilities.connect(userId);
         connected=true;
         cus.info("SharkConnectionImpl -> User "+userId+" is logged on");
      } catch (Exception ex) {
         if (ex instanceof ConnectFailed) {
            throw (ConnectFailed)ex;
         }
         cus.info("SharkConnectionImpl -> Unexpected error while user "+userId+" loggs on");
         ex.printStackTrace();
         throw new BaseException(ex);
      }
   }

   public void disconnect () throws BaseException, NotConnected {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         disconnect(t);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void disconnect (SharkTransaction t) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         SharkUtilities.disconnect(connectionKey);
         connected=false;
         cus.info("SharkConnectionImpl -> User "+userId+" with connection key "+connectionKey+" is logged off");
      } catch (Exception ex) {
         if (ex instanceof NotConnected) {
            throw (NotConnected)ex;
         }
         cus.info("SharkConnectionImpl -> Unexpected error while user "+userId+" loggs off");
         throw new BaseException(ex);
      }
   }

   public WfResource getResourceObject () throws BaseException, NotConnected {
      WfResource ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getResourceObject(t);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public WfResource getResourceObject (SharkTransaction t) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_sharkconnection_getResourceObject(t,userId);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      try {
         WfResource ret=SharkEngineManager.getInstance().getObjectFactory().createResourceWrapper(userId,userId);
         return ret;
      } catch (Exception ex) {
         cus.info("SharkConnectionImpl -> Unexpected error while user "+userId+" tries to get its resource object");
         throw new BaseException(ex);
      }
   }

   public WfProcess createProcess (String pkgId,String pDefId) throws BaseException, NotConnected, NotEnabled {
      WfProcess ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = createProcess(t, pkgId, pDefId);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof NotEnabled)
            throw (NotEnabled)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public WfProcess createProcess (SharkTransaction t,String pkgId,String pDefId) throws BaseException, NotConnected, NotEnabled {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }

      String curVer=SharkUtilities.getCurrentPkgVersion(pkgId,true);

      // TODO: use WfResource when it becomes a WfRequester
      WfRequesterInternal req=SharkEngineManager.getInstance().getObjectFactory().createDefaultRequester(userId,null);
      WfProcessMgrInternal mgr=SharkUtilities.getProcessMgr(t, SharkUtilities.createProcessMgrKey(pkgId,curVer,pDefId));
      if (mgr==null) throw new BaseException("Can't create process for given definition - can't find manager");
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_sharkconnection_createProcess(t,mgr.name(t),userId,pkgId,curVer,pDefId);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      try {
         WfProcessInternal procInternal=mgr.create_process(t,req);
         WfProcess proc=SharkEngineManager.getInstance().getObjectFactory().createProcessWrapper(userId,procInternal.manager_name(t),procInternal.key(t));
         return proc;
      } catch (InvalidRequester ir) {
         throw new BaseException(ir);
      } catch (RequesterRequired rr) {
         throw new BaseException(rr);
      }/* catch (TransactionException te) {
         throw new BaseException(te);
       }*/
   }

   public WfProcess createProcess (WfRequester r, String pkgId,String pDefId)
      throws BaseException, NotConnected , NotEnabled, InvalidRequester, RequesterRequired {
      WfProcess ret = null;
      SharkTransaction t=null;
      try {
         t = SharkUtilities.createTransaction();
         ret = createProcess(t, r, pkgId, pDefId);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof NotConnected)
            throw (NotConnected)e;
         else if (e instanceof NotEnabled)
            throw (NotEnabled)e;
         else if (e instanceof InvalidRequester)
            throw (InvalidRequester)e;
         else if (e instanceof RequesterRequired)
            throw (RequesterRequired)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   /**
    * This method is not fully worked out, however client application can pass
    * its own externally created requester, and it will receive process status
    * changes. It can work only in one virtual machine.
    */
   public WfProcess createProcess (SharkTransaction t, WfRequester r, String pkgId,String pDefId)
      throws BaseException, NotConnected, NotEnabled, InvalidRequester, RequesterRequired {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      if (r==null) throw new BaseException("Trying to create process with external requester which is null!");

      String curVer=SharkUtilities.getCurrentPkgVersion(pkgId,true);

      WfRequesterInternal req=SharkEngineManager.getInstance().getObjectFactory().createDefaultRequester(userId,r);
      WfProcessMgrInternal mgr=SharkUtilities.getProcessMgr(t, SharkUtilities.createProcessMgrKey(pkgId,curVer,pDefId));

      if (mgr==null) throw new BaseException("Can't create process for given definition - can't find manager");
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_sharkconnection_createProcess(t,mgr.name(t),userId,pkgId,curVer,pDefId);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      WfProcessInternal procInternal=mgr.create_process(t,req);
      WfProcess proc=SharkEngineManager.getInstance().getObjectFactory().createProcessWrapper(userId,procInternal.manager_name(t),procInternal.key(t));
      return proc;
   }

   // Prevents memory leak if client forgets to disconnect
   public void finalize() throws Throwable {
      if (connected) {
         connected=false;
         SharkUtilities.disconnect(connectionKey);
         cus.info("SharkConnectionImpl -> User "+userId+" with connection key "+connectionKey+" is automatically disconnected");
      }
   }

}
