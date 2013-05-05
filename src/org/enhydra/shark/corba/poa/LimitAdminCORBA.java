package org.enhydra.shark.corba.poa;

import org.omg.WfBase.*;
import org.enhydra.shark.corba.WorkflowService.*;


/**
 * The implementation of client interface through which client can check limits.
 * @author David Forslund
 */
public class LimitAdminCORBA extends LimitAdministrationPOA {
   private SharkCORBAServer myServer;

   private String userId;
   private boolean connected=false;

   org.enhydra.shark.api.client.wfservice.LimitAdministration myLimitAdmin;

   LimitAdminCORBA (SharkCORBAServer server,org.enhydra.shark.api.client.wfservice.LimitAdministration la) {
      this.myServer=server;
      this.myLimitAdmin=la;

   }

   public void connect(String userId, String password, String engineName, String scope) throws BaseException, ConnectFailed {
      this.userId=userId;

      try {
         if (!myServer.validateUser(userId,password)) {
            throw new ConnectFailed("Connection failed, invalid username or password");
         }
         connected=true;
         myLimitAdmin.connect(userId);
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



   public void checkLimits () throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         myLimitAdmin.checkLimits();
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public void checkLimitsForProcesses (String[] procIds) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         myLimitAdmin.checkLimits(procIds);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public void checkLimitsForProcess (String procId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         myLimitAdmin.checkLimits(procId);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public void checkProcessLimit (String procId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         myLimitAdmin.checkProcessLimit(procId);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public void checkActivityLimit (String procId,String actId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         myLimitAdmin.checkActivityLimit(procId,actId);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

}
