package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/_WfEventAuditImplBase.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

public abstract class _WfEventAuditImplBase extends org.omg.CORBA.portable.ObjectImpl
                implements org.omg.WorkflowModel.WfEventAudit, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors
  public _WfEventAuditImplBase ()
  {
  }

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("source", new java.lang.Integer (0));
    _methods.put ("time_stamp", new java.lang.Integer (1));
    _methods.put ("event_type", new java.lang.Integer (2));
    _methods.put ("activity_key", new java.lang.Integer (3));
    _methods.put ("activity_name", new java.lang.Integer (4));
    _methods.put ("process_key", new java.lang.Integer (5));
    _methods.put ("process_name", new java.lang.Integer (6));
    _methods.put ("process_mgr_name", new java.lang.Integer (7));
    _methods.put ("process_mgr_version", new java.lang.Integer (8));
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
       case 0:  // WorkflowModel/WfEventAudit/source
       {
         try {
           org.omg.WorkflowModel.WfExecutionObject $result = null;
           $result = this.source ();
           out = $rh.createReply();
           org.omg.WorkflowModel.WfExecutionObjectHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.omg.WorkflowModel.SourceNotAvailable $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WorkflowModel.SourceNotAvailableHelper.write (out, $ex);
         }
         break;
       }

       case 1:  // WorkflowModel/WfEventAudit/time_stamp
       {
         try {
           org.omg.TimeBase.UtcT $result = null;
           $result = this.time_stamp ();
           out = $rh.createReply();
           org.omg.TimeBase.UtcTHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 2:  // WorkflowModel/WfEventAudit/event_type
       {
         try {
           String $result = null;
           $result = this.event_type ();
           out = $rh.createReply();
           out.write_wstring ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 3:  // WorkflowModel/WfEventAudit/activity_key
       {
         try {
           String $result = null;
           $result = this.activity_key ();
           out = $rh.createReply();
           out.write_wstring ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 4:  // WorkflowModel/WfEventAudit/activity_name
       {
         try {
           String $result = null;
           $result = this.activity_name ();
           out = $rh.createReply();
           out.write_wstring ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 5:  // WorkflowModel/WfEventAudit/process_key
       {
         try {
           String $result = null;
           $result = this.process_key ();
           out = $rh.createReply();
           out.write_wstring ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 6:  // WorkflowModel/WfEventAudit/process_name
       {
         try {
           String $result = null;
           $result = this.process_name ();
           out = $rh.createReply();
           out.write_wstring ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 7:  // WorkflowModel/WfEventAudit/process_mgr_name
       {
         try {
           String $result = null;
           $result = this.process_mgr_name ();
           out = $rh.createReply();
           out.write_wstring ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 8:  // WorkflowModel/WfEventAudit/process_mgr_version
       {
         try {
           String $result = null;
           $result = this.process_mgr_version ();
           out = $rh.createReply();
           out.write_wstring ($result);
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
    "IDL:omg.org/WorkflowModel/WfEventAudit:1.0", 
    "IDL:omg.org/WfBase/BaseBusinessObject:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }


} // class _WfEventAuditImplBase
