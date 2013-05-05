package org.enhydra.shark.corba.poa;

import org.enhydra.shark.corba.WorkflowService.*;
import org.enhydra.shark.corba.poa.Collective;
import org.omg.CORBA.ORB;
import org.omg.WfBase.BaseException;
import org.omg.WorkflowModel.*;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;

/**
 * The client interface through which client accesses the engine
 * objects, and performs the various actions on engine.
 *
 * @author David Forslund
 * @version 0.2
 */
public class SharkConnectionCORBA extends SharkConnectionPOA implements Collective {

   org.enhydra.shark.api.client.wfservice.SharkConnection mySharkConn;
   private SharkCORBAServer myServer;

   SharkConnectionCORBA(SharkCORBAServer server, org.enhydra.shark.api.client.wfservice.SharkConnection sc) {
      this.myServer = server;
      this.mySharkConn = sc;

      /*
       try {
           myServer._default_POA().activate_object(this) ;

       } catch (WrongPolicy wrongPolicy) {
           wrongPolicy.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
       } catch (ServantAlreadyActive servantAlreadyActive) {
           servantAlreadyActive.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
       }

       _this(myServer._orb());
       */
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
         WfResource wfRes = SharkCORBAUtilities.makeWfResource( new WfResourceCORBA(myServer.getBoundORB(), this,
                                    mySharkConn.getResourceObject()));
         __recruit(wfRes);
          return wfRes;
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
         WfProcess proc = SharkCORBAUtilities.makeWfProcess(new WfProcessCORBA(myServer.getBoundORB(),this,
                                                 mySharkConn.createProcess(pkgId,
                                                                           pDefId)));
          __recruit(proc);
          return proc;

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
          lr.setOrb(myServer._orb());
         org.enhydra.shark.api.client.wfmodel.WfProcess procInternal=
            mySharkConn.createProcess(lr,pkgId,pDefId);
         WfLinkingRequesterForCORBA.setCORBARequester(procInternal.key(), requester);
         WfProcess proc = SharkCORBAUtilities.makeWfProcess(new WfProcessCORBA(myServer.getBoundORB(), this,procInternal));
         __recruit(proc);
          return proc;
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
       _this()._release();
       /*
       try {
           this.disconnect();
       } catch (BaseException e) {
           e.printStackTrace();
       } catch (NotConnected notConnected) {
           notConnected.printStackTrace();
       }   */
       }
   }

   public void doneWith(org.omg.CORBA.Object toDisconnect) {
      myServer.doneWith(toDisconnect);
      __leave(toDisconnect);
   }
}