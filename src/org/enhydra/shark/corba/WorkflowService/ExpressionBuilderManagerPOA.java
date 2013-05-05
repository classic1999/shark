package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/ExpressionBuilderManagerPOA.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分32秒 CST
*/

public abstract class ExpressionBuilderManagerPOA extends org.omg.PortableServer.Servant
 implements org.enhydra.shark.corba.WorkflowService.ExpressionBuilderManagerOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("getActivityIteratorExpressionBuilder", new java.lang.Integer (0));
    _methods.put ("getAssignmentIteratorExpressionBuilder", new java.lang.Integer (1));
    _methods.put ("getEventAuditIteratorExpressionBuilder", new java.lang.Integer (2));
    _methods.put ("getProcessIteratorExpressionBuilder", new java.lang.Integer (3));
    _methods.put ("getProcessMgrIteratorExpressionBuilder", new java.lang.Integer (4));
    _methods.put ("getResourceIteratorExpressionBuilder", new java.lang.Integer (5));
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
       case 0:  // WorkflowService/ExpressionBuilderManager/getActivityIteratorExpressionBuilder
       {
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.getActivityIteratorExpressionBuilder ();
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 1:  // WorkflowService/ExpressionBuilderManager/getAssignmentIteratorExpressionBuilder
       {
         org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder $result = null;
         $result = this.getAssignmentIteratorExpressionBuilder ();
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 2:  // WorkflowService/ExpressionBuilderManager/getEventAuditIteratorExpressionBuilder
       {
         org.enhydra.shark.corba.ExpressionBuilders.EventAuditIteratorExpressionBuilder $result = null;
         $result = this.getEventAuditIteratorExpressionBuilder ();
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.EventAuditIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 3:  // WorkflowService/ExpressionBuilderManager/getProcessIteratorExpressionBuilder
       {
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.getProcessIteratorExpressionBuilder ();
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 4:  // WorkflowService/ExpressionBuilderManager/getProcessMgrIteratorExpressionBuilder
       {
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = null;
         $result = this.getProcessMgrIteratorExpressionBuilder ();
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 5:  // WorkflowService/ExpressionBuilderManager/getResourceIteratorExpressionBuilder
       {
         org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder $result = null;
         $result = this.getResourceIteratorExpressionBuilder ();
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:WorkflowService/ExpressionBuilderManager:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public ExpressionBuilderManager _this() 
  {
    return ExpressionBuilderManagerHelper.narrow(
    super._this_object());
  }

  public ExpressionBuilderManager _this(org.omg.CORBA.ORB orb) 
  {
    return ExpressionBuilderManagerHelper.narrow(
    super._this_object(orb));
  }


} // class ExpressionBuilderManagerPOA
