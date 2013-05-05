package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/WfAssignmentPOA.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分28秒 CST
*/

public abstract class WfAssignmentPOA extends org.omg.PortableServer.Servant
 implements org.omg.WorkflowModel.WfAssignmentOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("activity", new java.lang.Integer (0));
    _methods.put ("assignee", new java.lang.Integer (1));
    _methods.put ("set_assignee", new java.lang.Integer (2));
    _methods.put ("set_accepted_status", new java.lang.Integer (3));
    _methods.put ("get_accepted_status", new java.lang.Integer (4));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // WorkflowModel/WfAssignment/activity
       {
         try {
           org.omg.WorkflowModel.WfActivity $result = null;
           $result = this.activity ();
           out = $rh.createReply();
           org.omg.WorkflowModel.WfActivityHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 1:  // WorkflowModel/WfAssignment/assignee
       {
         try {
           org.omg.WorkflowModel.WfResource $result = null;
           $result = this.assignee ();
           out = $rh.createReply();
           org.omg.WorkflowModel.WfResourceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 2:  // WorkflowModel/WfAssignment/set_assignee
       {
         try {
           org.omg.WorkflowModel.WfResource new_value = org.omg.WorkflowModel.WfResourceHelper.read (in);
           this.set_assignee (new_value);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.omg.WorkflowModel.InvalidResource $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WorkflowModel.InvalidResourceHelper.write (out, $ex);
         }
         break;
       }

       case 3:  // WorkflowModel/WfAssignment/set_accepted_status
       {
         try {
           boolean accept = in.read_boolean ();
           this.set_accepted_status (accept);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.omg.WorkflowModel.CannotAcceptSuspended $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WorkflowModel.CannotAcceptSuspendedHelper.write (out, $ex);
         }
         break;
       }

       case 4:  // WorkflowModel/WfAssignment/get_accepted_status
       {
         try {
           boolean $result = false;
           $result = this.get_accepted_status ();
           out = $rh.createReply();
           out.write_boolean ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:omg.org/WorkflowModel/WfAssignment:1.0", 
    "IDL:omg.org/WfBase/BaseBusinessObject:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public WfAssignment _this() 
  {
    return WfAssignmentHelper.narrow(
    super._this_object());
  }

  public WfAssignment _this(org.omg.CORBA.ORB orb) 
  {
    return WfAssignmentHelper.narrow(
    super._this_object(orb));
  }


} // class WfAssignmentPOA
