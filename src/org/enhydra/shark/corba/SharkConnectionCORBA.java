package org.enhydra.shark.corba;

import org.enhydra.shark.corba.WorkflowService.*;
import org.omg.CORBA.ORB;
import org.omg.WfBase.BaseException;
import org.omg.WorkflowModel.*;

/**
 * The client interface through which client accesses the engine
 * objects, and performs the various actions on engine.
 * 
 * @author Sasa Bojanic
 * @version 0.2
 */
public class SharkConnectionCORBA extends _SharkConnectionImplBase implements
                                                                  Collective {

   org.enhydra.shark.api.client.wfservice.SharkConnection mySharkConn;
   private SharkCORBAServer myServer;

   SharkConnectionCORBA(SharkCORBAServer server, org.enhydra.shark.api.client.wfservice.SharkConnection sc) {
      this.myServer = server;
      this.mySharkConn = sc;
      if (myServer.trackObjects) {
         __collective = new Collective.CollectiveCORBA();
      }
   }

   public void connect(String userId,
                       String password,
                       String engineName,
                       String scope) throws BaseException, ConnectFailed {
      try {
         mySharkConn.connect(userId, password, engineName, scope);
      } catch (org.enhydra.shark.api.client.wfservice.ConnectFailed cf) {
         throw new ConnectFailed();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void disconnect() throws BaseException, NotConnected {
      try {
         mySharkConn.disconnect();
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      } finally {
         this.__disband(this._orb());
      }
   }

   public WfResource getResourceObject() throws BaseException, NotConnected {
      try {
         return new WfResourceCORBA(this,
                                    mySharkConn.getResourceObject());
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public WfProcess createProcess(String pkgId, String pDefId) throws BaseException,
                                                              NotConnected,
                                                              NotEnabled {
      try {
         WfProcessCORBA ret = new WfProcessCORBA(this,
                                                 mySharkConn.createProcess(pkgId,
                                                                           pDefId));
         return ret;
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (org.enhydra.shark.api.client.wfmodel.NotEnabled ne) {
         throw new NotEnabled(ne.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public WfProcess createProcessWithRequester (WfRequester requester,
         String pkgId, 
         String pDefId) throws BaseException,
                               NotConnected,
                               NotEnabled,
                               InvalidRequester,
                               RequesterRequired {
      try {
         WfLinkingRequesterForCORBA lr = new WfLinkingRequesterForCORBA();
         org.enhydra.shark.api.client.wfmodel.WfProcess procInternal=
            mySharkConn.createProcess(lr,pkgId,pDefId);
         WfLinkingRequesterForCORBA.setCORBARequester(procInternal.key(), requester);
         WfProcessCORBA ret = new WfProcessCORBA(this,procInternal);
         return ret;
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (org.enhydra.shark.api.client.wfmodel.NotEnabled ne) {
         throw new NotEnabled(ne.getMessage());
      } catch (org.enhydra.shark.api.client.wfmodel.InvalidRequester ir) {
         throw new InvalidRequester(ir.getMessage());
      } catch (org.enhydra.shark.api.client.wfmodel.RequesterRequired rr) {
         throw new RequesterRequired(rr.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
      
   }   
   Collective __collective;

   public void __recruit(org.omg.CORBA.Object obj) {
      if (myServer.trackObjects)
         __collective.__recruit(obj);
   }

   public void __leave(org.omg.CORBA.Object obj) {
      if (myServer.trackObjects)
         __collective.__leave(obj);
   }

   public void __disband(ORB _orb) {
      if (myServer.trackObjects) {
         __collective.__disband(_orb);
      }
      this._orb().disconnect(this);
   }
   
   public void doneWith(org.omg.CORBA.Object toDisconnect) {
      myServer.doneWith(toDisconnect);
      __leave(toDisconnect);
   }
}