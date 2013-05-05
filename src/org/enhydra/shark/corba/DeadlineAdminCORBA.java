package org.enhydra.shark.corba;

import org.omg.WfBase.*;
import org.enhydra.shark.corba.WorkflowService.*;


/**
 * The implementation of client interface through which client can check deadlines.
 * @author Sasa Bojanic
 */
public class DeadlineAdminCORBA extends _DeadlineAdministrationImplBase {
   private SharkCORBAServer myServer;

   private String userId;
   private boolean connected=false;

   org.enhydra.shark.api.client.wfservice.DeadlineAdministration myDeadlineAdmin;

   DeadlineAdminCORBA (SharkCORBAServer server,org.enhydra.shark.api.client.wfservice.DeadlineAdministration da) {
      this.myServer=server;
      this.myDeadlineAdmin=da;
   }

   public void connect(String userId, String password, String engineName, String scope) throws BaseException, ConnectFailed {
      this.userId=userId;

      try {
         if (!myServer.validateUser(userId,password)) {
            throw new ConnectFailed("Connection failed, invalid username or password");
         }
         connected=true;
         myDeadlineAdmin.connect(userId);
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
      this._orb().disconnect(this);
   }


   public void checkDeadlines () throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         myDeadlineAdmin.checkDeadlines();
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String[] checkDeadlines(final int instancesPerTransaction, int failuresToIgnore) throws BaseException,NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myDeadlineAdmin.checkDeadlines(instancesPerTransaction,failuresToIgnore);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public void checkDeadlinesForProcesses (String[] procIds) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         myDeadlineAdmin.checkDeadlines(procIds);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public void checkProcessDeadlines (String procId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         myDeadlineAdmin.checkDeadlines(procId);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public void checkActivityDeadline (String procId,String actId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         myDeadlineAdmin.checkDeadline(procId,actId);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String[] checkDeadlinesMultiTrans(int instancesPerTransaction, int failuresToIgnore) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myDeadlineAdmin.checkDeadlines(instancesPerTransaction, failuresToIgnore);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public DeadlineInfo[] getDeadlineInfoForProcess (String procId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return SharkCORBAUtilities.makeDeadlineInfoArray(myDeadlineAdmin.getDeadlineInfo(procId));
      } catch (Exception e) {
         throw new BaseException();
      }
   }
   
   public DeadlineInfo[] getDeadlineInfoForActivity (String procId,String actId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return SharkCORBAUtilities.makeDeadlineInfoArray(myDeadlineAdmin.getDeadlineInfo(procId,actId));
      } catch (Exception e) {
         throw new BaseException();
      }
   }

}
