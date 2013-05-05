package org.enhydra.shark.corba.poa;

import org.omg.WfBase.*;
import org.enhydra.shark.corba.WorkflowService.*;

/**
 * Used for work with shark's external repository.
 *
 * @author David Forslund
 */
public class RepositoryManagerCORBA extends RepositoryMgrPOA {
   private SharkCORBAServer myServer;

   private String userId;

   private boolean connected = false;

   org.enhydra.shark.api.client.wfservice.RepositoryMgr myRepMgr;

   RepositoryManagerCORBA(SharkCORBAServer server,
                          org.enhydra.shark.api.client.wfservice.RepositoryMgr rm) {
      this.myServer = server;
      this.myRepMgr = rm;

   }

   public void connect(String userId,
                       String password,
                       String engineName,
                       String scope) throws BaseException, ConnectFailed {
      this.userId = userId;

      try {
         if (!myServer.validateUser(userId, password)) { throw new ConnectFailed("Connection failed, invalid username or password"); }
         connected = true;
         myRepMgr.connect(userId);
      } catch (ConnectFailed cf) {
         throw cf;
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void disconnect() throws BaseException, NotConnected {
      if (!connected) { throw new NotConnected("The connection is not established..."); }
      connected = false;
      _this()._release();
   }

   public void deletePkg(String relativePath) throws BaseException,
                                             NotConnected,
                                             RepositoryInvalid {
      if (!connected) { throw new NotConnected("The connection is not established..."); }
      try {
         myRepMgr.deletePackage(relativePath);
      } catch (org.enhydra.shark.api.client.wfservice.RepositoryInvalid ri) {
         throw new RepositoryInvalid(ri.getXPDLValidationErrors());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void uploadPkg(byte[] pkgContent, String relativePath) throws BaseException,
                                                                NotConnected,
                                                                RepositoryInvalid {
      if (!connected) { throw new NotConnected("The connection is not established..."); }
      try {
         myRepMgr.uploadPackage(pkgContent, relativePath);
      } catch (org.enhydra.shark.api.client.wfservice.RepositoryInvalid ri) {
         throw new RepositoryInvalid(ri.getXPDLValidationErrors());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String[] getPackagePaths() throws BaseException, NotConnected {
      if (!connected) { throw new NotConnected("The connection is not established..."); }
      try {
         return myRepMgr.getPackagePaths();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public NameValue[] getPackagePathToIdMapping() throws BaseException,
                                                 NotConnected {
      if (!connected) { throw new NotConnected("The connection is not established..."); }
      try {
         return SharkCORBAUtilities.makeCORBANameValueArray(myServer._orb(),
                                                            myRepMgr.getPackagePathToIdMapping());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String getPackageId(String relativePath) throws BaseException,
                                                  NotConnected {
      if (!connected) { throw new NotConnected("The connection is not established..."); }
      try {
         return myRepMgr.getPackageId(relativePath);
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

}