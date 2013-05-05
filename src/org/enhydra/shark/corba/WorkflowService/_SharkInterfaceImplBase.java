package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/_SharkInterfaceImplBase.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分33秒 CST
*/

public abstract class _SharkInterfaceImplBase extends org.omg.CORBA.portable.ObjectImpl
                implements org.enhydra.shark.corba.WorkflowService.SharkInterface, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors
  public _SharkInterfaceImplBase ()
  {
  }

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("getSharkConnection", new java.lang.Integer (0));
    _methods.put ("getRepositoryManager", new java.lang.Integer (1));
    _methods.put ("getPackageAdministration", new java.lang.Integer (2));
    _methods.put ("getUserGroupAdministration", new java.lang.Integer (3));
    _methods.put ("getExecutionAdministration", new java.lang.Integer (4));
    _methods.put ("getMappingAdministration", new java.lang.Integer (5));
    _methods.put ("getAdminMisc", new java.lang.Integer (6));
    _methods.put ("getCacheAdministration", new java.lang.Integer (7));
    _methods.put ("getDeadlineAdministration", new java.lang.Integer (8));
    _methods.put ("getLimitAdministration", new java.lang.Integer (9));
    _methods.put ("getExpressionBuilderManager", new java.lang.Integer (10));
    _methods.put ("getProperties", new java.lang.Integer (11));
    _methods.put ("doneWith", new java.lang.Integer (12));
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
       case 0:  // WorkflowService/SharkInterface/getSharkConnection
       {
         org.enhydra.shark.corba.WorkflowService.SharkConnection $result = null;
         $result = this.getSharkConnection ();
         out = $rh.createReply();
         org.enhydra.shark.corba.WorkflowService.SharkConnectionHelper.write (out, $result);
         break;
       }

       case 1:  // WorkflowService/SharkInterface/getRepositoryManager
       {
         org.enhydra.shark.corba.WorkflowService.RepositoryMgr $result = null;
         $result = this.getRepositoryManager ();
         out = $rh.createReply();
         org.enhydra.shark.corba.WorkflowService.RepositoryMgrHelper.write (out, $result);
         break;
       }

       case 2:  // WorkflowService/SharkInterface/getPackageAdministration
       {
         org.enhydra.shark.corba.WorkflowService.PackageAdministration $result = null;
         $result = this.getPackageAdministration ();
         out = $rh.createReply();
         org.enhydra.shark.corba.WorkflowService.PackageAdministrationHelper.write (out, $result);
         break;
       }

       case 3:  // WorkflowService/SharkInterface/getUserGroupAdministration
       {
         org.enhydra.shark.corba.WorkflowService.UserGroupAdministration $result = null;
         $result = this.getUserGroupAdministration ();
         out = $rh.createReply();
         org.enhydra.shark.corba.WorkflowService.UserGroupAdministrationHelper.write (out, $result);
         break;
       }

       case 4:  // WorkflowService/SharkInterface/getExecutionAdministration
       {
         org.enhydra.shark.corba.WorkflowService.ExecutionAdministration $result = null;
         $result = this.getExecutionAdministration ();
         out = $rh.createReply();
         org.enhydra.shark.corba.WorkflowService.ExecutionAdministrationHelper.write (out, $result);
         break;
       }

       case 5:  // WorkflowService/SharkInterface/getMappingAdministration
       {
         org.enhydra.shark.corba.WorkflowService.MappingAdministration $result = null;
         $result = this.getMappingAdministration ();
         out = $rh.createReply();
         org.enhydra.shark.corba.WorkflowService.MappingAdministrationHelper.write (out, $result);
         break;
       }

       case 6:  // WorkflowService/SharkInterface/getAdminMisc
       {
         org.enhydra.shark.corba.WorkflowService.AdminMisc $result = null;
         $result = this.getAdminMisc ();
         out = $rh.createReply();
         org.enhydra.shark.corba.WorkflowService.AdminMiscHelper.write (out, $result);
         break;
       }

       case 7:  // WorkflowService/SharkInterface/getCacheAdministration
       {
         org.enhydra.shark.corba.WorkflowService.CacheAdministration $result = null;
         $result = this.getCacheAdministration ();
         out = $rh.createReply();
         org.enhydra.shark.corba.WorkflowService.CacheAdministrationHelper.write (out, $result);
         break;
       }

       case 8:  // WorkflowService/SharkInterface/getDeadlineAdministration
       {
         org.enhydra.shark.corba.WorkflowService.DeadlineAdministration $result = null;
         $result = this.getDeadlineAdministration ();
         out = $rh.createReply();
         org.enhydra.shark.corba.WorkflowService.DeadlineAdministrationHelper.write (out, $result);
         break;
       }

       case 9:  // WorkflowService/SharkInterface/getLimitAdministration
       {
         org.enhydra.shark.corba.WorkflowService.LimitAdministration $result = null;
         $result = this.getLimitAdministration ();
         out = $rh.createReply();
         org.enhydra.shark.corba.WorkflowService.LimitAdministrationHelper.write (out, $result);
         break;
       }

       case 10:  // WorkflowService/SharkInterface/getExpressionBuilderManager
       {
         org.enhydra.shark.corba.WorkflowService.ExpressionBuilderManager $result = null;
         $result = this.getExpressionBuilderManager ();
         out = $rh.createReply();
         org.enhydra.shark.corba.WorkflowService.ExpressionBuilderManagerHelper.write (out, $result);
         break;
       }

       case 11:  // WorkflowService/SharkInterface/getProperties
       {
         org.omg.WfBase.NameValueInfo $result[] = null;
         $result = this.getProperties ();
         out = $rh.createReply();
         org.omg.WfBase.NameValueInfoSequenceHelper.write (out, $result);
         break;
       }

       case 12:  // WorkflowService/SharkInterface/doneWith
       {
         org.omg.CORBA.Object toDisconnect = org.omg.CORBA.ObjectHelper.read (in);
         this.doneWith (toDisconnect);
         out = $rh.createReply();
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:WorkflowService/SharkInterface:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }


} // class _SharkInterfaceImplBase
