package org.enhydra.shark.corba;

import org.enhydra.shark.corba.ExpressionBuilders.*;
import org.enhydra.shark.corba.WorkflowService.ConnectFailed;
import org.enhydra.shark.corba.WorkflowService.NotConnected;
import org.enhydra.shark.corba.WorkflowService._ExpressionBuilderManagerImplBase;
import org.omg.CORBA.ORB;
import org.omg.WfBase.BaseException;

/**
 * The client interface through which client gets engine's
 * iterator expression builders.
 * @author Sasa Bojanic
 */
public class ExpressionBuilderMgrCORBA extends _ExpressionBuilderManagerImplBase implements
Collective{

   org.enhydra.shark.api.client.wfservice.ExpressionBuilderManager myEBM;
   private SharkCORBAServer myServer;
   private Collective __collective;

   public ExpressionBuilderMgrCORBA (SharkCORBAServer server, org.enhydra.shark.api.client.wfservice.ExpressionBuilderManager ebm) {
      this.myEBM=ebm;
      this.myServer = server;
      if (myServer.trackObjects) {
         __collective = new Collective.CollectiveCORBA();
      }
   }

   public void connect(String userId, String password, String engineName, String scope) throws BaseException, ConnectFailed {
      try {
         if (!myServer.validateUser(userId,password)) {
            throw new ConnectFailed("Connection failed, invalid username or password");
         }
      } catch (ConnectFailed cf) {
         throw cf;
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void disconnect() throws BaseException, NotConnected {
      this.__disband(this._orb());
   }   

   public ActivityIteratorExpressionBuilder getActivityIteratorExpressionBuilder () {
      ActivityIteratorExpressionBuilderCORBA ret = new ActivityIteratorExpressionBuilderCORBA(myEBM.getActivityIteratorExpressionBuilder());
      this.__recruit(ret);
      return ret;
   }

   public AssignmentIteratorExpressionBuilder getAssignmentIteratorExpressionBuilder () {
      AssignmentIteratorExpressionBuilderCORBA ret = new AssignmentIteratorExpressionBuilderCORBA(myEBM.getAssignmentIteratorExpressionBuilder());
      this.__recruit(ret);
      return ret;
   }

   public EventAuditIteratorExpressionBuilder getEventAuditIteratorExpressionBuilder () {
      throw new RuntimeException("Currently not implemented !");
      //ret = new EventAuditIteratorExpressionBuilderCORBA(myEBM.getEventAuditIteratorExpressionBuilder());
   }

   public ProcessIteratorExpressionBuilder getProcessIteratorExpressionBuilder () {
      ProcessIteratorExpressionBuilderCORBA ret = new ProcessIteratorExpressionBuilderCORBA(myEBM.getProcessIteratorExpressionBuilder());
      this.__recruit(ret);
      return ret;
   }

   public ProcessMgrIteratorExpressionBuilder getProcessMgrIteratorExpressionBuilder () {
      ProcessMgrIteratorExpressionBuilderCORBA ret = new ProcessMgrIteratorExpressionBuilderCORBA(myEBM.getProcessMgrIteratorExpressionBuilder());
      this.__recruit(ret);
      return ret;
   }

   public ResourceIteratorExpressionBuilder getResourceIteratorExpressionBuilder () {
      ResourceIteratorExpressionBuilderCORBA ret = new ResourceIteratorExpressionBuilderCORBA(myEBM.getResourceIteratorExpressionBuilder());
      this.__recruit(ret);
      return ret;
   }

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
}
