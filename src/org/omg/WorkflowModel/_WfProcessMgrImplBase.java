package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/_WfProcessMgrImplBase.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

public abstract class _WfProcessMgrImplBase extends org.omg.CORBA.portable.ObjectImpl
                implements org.omg.WorkflowModel.WfProcessMgr, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors
  public _WfProcessMgrImplBase ()
  {
  }

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("how_many_process", new java.lang.Integer (0));
    _methods.put ("get_iterator_process", new java.lang.Integer (1));
    _methods.put ("get_sequence_process", new java.lang.Integer (2));
    _methods.put ("is_member_of_process", new java.lang.Integer (3));
    _methods.put ("process_mgr_state", new java.lang.Integer (4));
    _methods.put ("set_process_mgr_state", new java.lang.Integer (5));
    _methods.put ("name", new java.lang.Integer (6));
    _methods.put ("description", new java.lang.Integer (7));
    _methods.put ("category", new java.lang.Integer (8));
    _methods.put ("version", new java.lang.Integer (9));
    _methods.put ("context_signature", new java.lang.Integer (10));
    _methods.put ("result_signature", new java.lang.Integer (11));
    _methods.put ("create_process", new java.lang.Integer (12));
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
       case 0:  // WorkflowModel/WfProcessMgr/how_many_process
       {
         try {
           int $result = (int)0;
           $result = this.how_many_process ();
           out = $rh.createReply();
           out.write_long ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 1:  // WorkflowModel/WfProcessMgr/get_iterator_process
       {
         try {
           org.omg.WorkflowModel.WfProcessIterator $result = null;
           $result = this.get_iterator_process ();
           out = $rh.createReply();
           org.omg.WorkflowModel.WfProcessIteratorHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 2:  // WorkflowModel/WfProcessMgr/get_sequence_process
       {
         try {
           int max_number = in.read_long ();
           org.omg.WorkflowModel.WfProcess $result[] = null;
           $result = this.get_sequence_process (max_number);
           out = $rh.createReply();
           org.omg.WorkflowModel.WfProcessSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 3:  // WorkflowModel/WfProcessMgr/is_member_of_process
       {
         try {
           org.omg.WorkflowModel.WfProcess member = org.omg.WorkflowModel.WfProcessHelper.read (in);
           boolean $result = false;
           $result = this.is_member_of_process (member);
           out = $rh.createReply();
           out.write_boolean ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 4:  // WorkflowModel/WfProcessMgr/process_mgr_state
       {
         try {
           org.omg.WorkflowModel.process_mgr_stateType $result = null;
           $result = this.process_mgr_state ();
           out = $rh.createReply();
           org.omg.WorkflowModel.process_mgr_stateTypeHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 5:  // WorkflowModel/WfProcessMgr/set_process_mgr_state
       {
         try {
           org.omg.WorkflowModel.process_mgr_stateType new_state = org.omg.WorkflowModel.process_mgr_stateTypeHelper.read (in);
           this.set_process_mgr_state (new_state);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.omg.WorkflowModel.TransitionNotAllowed $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WorkflowModel.TransitionNotAllowedHelper.write (out, $ex);
         }
         break;
       }

       case 6:  // WorkflowModel/WfProcessMgr/name
       {
         try {
           String $result = null;
           $result = this.name ();
           out = $rh.createReply();
           out.write_wstring ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 7:  // WorkflowModel/WfProcessMgr/description
       {
         try {
           String $result = null;
           $result = this.description ();
           out = $rh.createReply();
           out.write_wstring ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 8:  // WorkflowModel/WfProcessMgr/category
       {
         try {
           String $result = null;
           $result = this.category ();
           out = $rh.createReply();
           out.write_wstring ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 9:  // WorkflowModel/WfProcessMgr/version
       {
         try {
           String $result = null;
           $result = this.version ();
           out = $rh.createReply();
           out.write_wstring ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 10:  // WorkflowModel/WfProcessMgr/context_signature
       {
         try {
           org.omg.WfBase.NameValueInfo $result[] = null;
           $result = this.context_signature ();
           out = $rh.createReply();
           org.omg.WfBase.NameValueInfoSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 11:  // WorkflowModel/WfProcessMgr/result_signature
       {
         try {
           org.omg.WfBase.NameValueInfo $result[] = null;
           $result = this.result_signature ();
           out = $rh.createReply();
           org.omg.WfBase.NameValueInfoSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 12:  // WorkflowModel/WfProcessMgr/create_process
       {
         try {
           org.omg.WorkflowModel.WfRequester requester = org.omg.WorkflowModel.WfRequesterHelper.read (in);
           org.omg.WorkflowModel.WfProcess $result = null;
           $result = this.create_process (requester);
           out = $rh.createReply();
           org.omg.WorkflowModel.WfProcessHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.omg.WorkflowModel.NotEnabled $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WorkflowModel.NotEnabledHelper.write (out, $ex);
         } catch (org.omg.WorkflowModel.InvalidRequester $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WorkflowModel.InvalidRequesterHelper.write (out, $ex);
         } catch (org.omg.WorkflowModel.RequesterRequired $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WorkflowModel.RequesterRequiredHelper.write (out, $ex);
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
    "IDL:omg.org/WorkflowModel/WfProcessMgr:1.0", 
    "IDL:omg.org/WfBase/BaseBusinessObject:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }


} // class _WfProcessMgrImplBase
