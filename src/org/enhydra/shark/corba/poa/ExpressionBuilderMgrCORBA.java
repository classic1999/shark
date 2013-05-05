package org.enhydra.shark.corba.poa;

import org.enhydra.shark.corba.ExpressionBuilders.*;
import org.enhydra.shark.corba.WorkflowService.ConnectFailed;
import org.enhydra.shark.corba.WorkflowService.NotConnected;
import org.enhydra.shark.corba.WorkflowService.ExpressionBuilderManagerPOA;
import org.enhydra.shark.corba.poa.ActivityIteratorExpressionBuilderCORBA;
import org.enhydra.shark.corba.poa.AssignmentIteratorExpressionBuilderCORBA;
import org.enhydra.shark.corba.poa.Collective;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.WfBase.BaseException;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;

/**
 * The client interface through which client gets engine's
 * iterator expression builders.
 * @author David Forslund
 */
public class ExpressionBuilderMgrCORBA extends ExpressionBuilderManagerPOA implements Collective{

   org.enhydra.shark.api.client.wfservice.ExpressionBuilderManager myEBM;
   private SharkCORBAServer myServer;
   private Collective __collective;
   private AssignmentIteratorExpressionBuilder assignmentIteratorExpressionBuilder = null;
   private ActivityIteratorExpressionBuilder activityIteratorExpressionBuilder = null;
   private ProcessIteratorExpressionBuilder processIteratorExpressionBuilder = null;
   private ProcessMgrIteratorExpressionBuilder processMgrIteratorExpressionBuilder = null;
    private ResourceIteratorExpressionBuilder resourceIteratorExpressionBuilder = null;

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
     // if (activityIteratorExpressionBuilder != null) return activityIteratorExpressionBuilder;
      ActivityIteratorExpressionBuilderCORBA impl = new ActivityIteratorExpressionBuilderCORBA( myEBM.getActivityIteratorExpressionBuilder());
      try {
          // POA rootPOA = POAHelper.narrow(myServer._orb().resolve_initial_references("RootPOA"));
            POA rootPOA = SharkCORBAUtilities.getPOA();
            byte[] o = rootPOA.activate_object(impl);
           activityIteratorExpressionBuilder = ActivityIteratorExpressionBuilderHelper.narrow(rootPOA.id_to_reference(o));

       } catch (ServantAlreadyActive servantAlreadyActive) {
           servantAlreadyActive.printStackTrace();
       } catch (WrongPolicy wrongPolicy) {
           wrongPolicy.printStackTrace();
       }catch (ObjectNotActive objectNotActive) {
               objectNotActive.printStackTrace();
      // } catch (InvalidName in ) {
      //    in.printStackTrace();
      }

      this.__recruit(activityIteratorExpressionBuilder);
      return activityIteratorExpressionBuilder;
   }

   public AssignmentIteratorExpressionBuilder getAssignmentIteratorExpressionBuilder () {
    //   if (assignmentIteratorExpressionBuilder != null) return assignmentIteratorExpressionBuilder;
       AssignmentIteratorExpressionBuilderCORBA impl = new AssignmentIteratorExpressionBuilderCORBA( myEBM.getAssignmentIteratorExpressionBuilder());

       try {
          //  POA rootPOA = POAHelper.narrow(myServer._orb().resolve_initial_references("RootPOA"));
           POA rootPOA = SharkCORBAUtilities.getPOA();
           byte[] o =  rootPOA.activate_object(impl);
           assignmentIteratorExpressionBuilder = AssignmentIteratorExpressionBuilderHelper.narrow(rootPOA.id_to_reference(o));

       } catch (ServantAlreadyActive servantAlreadyActive) {
           servantAlreadyActive.printStackTrace();
       } catch (WrongPolicy wrongPolicy) {
           wrongPolicy.printStackTrace();
      } catch (ObjectNotActive objectNotActive) {
               objectNotActive.printStackTrace();
     //  } catch (InvalidName in ) {
     //      in.printStackTrace();
       }

      this.__recruit(assignmentIteratorExpressionBuilder);
      return assignmentIteratorExpressionBuilder;
   }

   public EventAuditIteratorExpressionBuilder getEventAuditIteratorExpressionBuilder () {
      throw new RuntimeException("Currently not implemented !");
      //ret = new EventAuditIteratorExpressionBuilderCORBA(myEBM.getEventAuditIteratorExpressionBuilder());
   }

   public ProcessIteratorExpressionBuilder getProcessIteratorExpressionBuilder () {
    //   if( processIteratorExpressionBuilder !=null) return processIteratorExpressionBuilder;
       ProcessIteratorExpressionBuilderCORBA impl = new ProcessIteratorExpressionBuilderCORBA(myEBM.getProcessIteratorExpressionBuilder());
       try {
           // POA rootPOA = POAHelper.narrow(myServer._orb().resolve_initial_references("RootPOA"));
           POA rootPOA = SharkCORBAUtilities.getPOA();
            byte[] o = rootPOA.activate_object(impl);
            processIteratorExpressionBuilder = ProcessIteratorExpressionBuilderHelper.narrow(rootPOA.id_to_reference(o));
         //  ret = impl._this();

        } catch (ServantAlreadyActive servantAlreadyActive) {
            servantAlreadyActive.printStackTrace();
        } catch (WrongPolicy wrongPolicy) {
            wrongPolicy.printStackTrace();
        } catch (ObjectNotActive objectNotActive) {
              objectNotActive.printStackTrace();
      //  }  catch (InvalidName in) {
      //      in.printStackTrace();
        }

      this.__recruit(processIteratorExpressionBuilder);
      return processIteratorExpressionBuilder;
   }

   public ProcessMgrIteratorExpressionBuilder getProcessMgrIteratorExpressionBuilder () {
     // if (processMgrIteratorExpressionBuilder != null) return processMgrIteratorExpressionBuilder;
       ProcessMgrIteratorExpressionBuilderCORBA impl = new ProcessMgrIteratorExpressionBuilderCORBA( myEBM.getProcessMgrIteratorExpressionBuilder());
       try {
        //   POA rootPOA = POAHelper.narrow(myServer._orb().resolve_initial_references("RootPOA"));
       //    rootPOA.the_POAManager().activate();
           POA rootPOA = SharkCORBAUtilities.getPOA();
           byte[] o =  rootPOA.activate_object(impl);
          // ret = impl._this();
           processMgrIteratorExpressionBuilder = ProcessMgrIteratorExpressionBuilderHelper.narrow(rootPOA.id_to_reference(o));

        } catch (ServantAlreadyActive servantAlreadyActive) {
            //servantAlreadyActive.printStackTrace();
        } catch (WrongPolicy wrongPolicy) {
            wrongPolicy.printStackTrace();
     } catch (ObjectNotActive inActive) {
               inActive.printStackTrace();
     //   } catch (InvalidName in) {
     //     in.printStackTrace();
       }
      this.__recruit(processMgrIteratorExpressionBuilder);
      return processMgrIteratorExpressionBuilder;
   }

   public ResourceIteratorExpressionBuilder getResourceIteratorExpressionBuilder () {
     //  if(resourceIteratorExpressionBuilder != null) return resourceIteratorExpressionBuilder;
       ResourceIteratorExpressionBuilderCORBA impl = new ResourceIteratorExpressionBuilderCORBA(myEBM.getResourceIteratorExpressionBuilder());
       try {
         //  POA rootPOA = POAHelper.narrow(myServer._orb().resolve_initial_references("RootPOA"));
           POA rootPOA = SharkCORBAUtilities.getPOA();
           byte[] o =  rootPOA.activate_object(impl);
           resourceIteratorExpressionBuilder = ResourceIteratorExpressionBuilderHelper.narrow(rootPOA.id_to_reference(o));
          //  ret = impl._this();
        } catch (ServantAlreadyActive servantAlreadyActive) {
            servantAlreadyActive.printStackTrace();
        } catch (WrongPolicy wrongPolicy) {
            wrongPolicy.printStackTrace();
   //    } catch (ServantNotActive notActive) {
    //            notActive.printStackTrace();
        } catch (ObjectNotActive objectNotActive) {
           objectNotActive.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
       }
      this.__recruit(resourceIteratorExpressionBuilder);
      return resourceIteratorExpressionBuilder;
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
   //    try {
           _this()._release();
   //    } catch (BaseException e) {
    //       e.printStackTrace();
    //   } catch (NotConnected notConnected) {
    //       notConnected.printStackTrace();
     //  }
   }
}
