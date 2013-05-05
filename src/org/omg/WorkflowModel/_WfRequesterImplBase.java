package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/_WfRequesterImplBase.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

public abstract class _WfRequesterImplBase extends org.omg.CORBA.portable.ObjectImpl
                implements org.omg.WorkflowModel.WfRequester, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors
  public _WfRequesterImplBase ()
  {
  }

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("how_many_performer", new java.lang.Integer (0));
    _methods.put ("get_iterator_performer", new java.lang.Integer (1));
    _methods.put ("get_sequence_performer", new java.lang.Integer (2));
    _methods.put ("is_member_of_performer", new java.lang.Integer (3));
    _methods.put ("receive_event", new java.lang.Integer (4));
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
       case 0:  // WorkflowModel/WfRequester/how_many_performer
       {
         try {
           int $result = (int)0;
           $result = this.how_many_performer ();
           out = $rh.createReply();
           out.write_long ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 1:  // WorkflowModel/WfRequester/get_iterator_performer
       {
         try {
           org.omg.WorkflowModel.WfProcessIterator $result = null;
           $result = this.get_iterator_performer ();
           out = $rh.createReply();
           org.omg.WorkflowModel.WfProcessIteratorHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 2:  // WorkflowModel/WfRequester/get_sequence_performer
       {
         try {
           int max_number = in.read_long ();
           org.omg.WorkflowModel.WfProcess $result[] = null;
           $result = this.get_sequence_performer (max_number);
           out = $rh.createReply();
           org.omg.WorkflowModel.WfProcessSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 3:  // WorkflowModel/WfRequester/is_member_of_performer
       {
         try {
           org.omg.WorkflowModel.WfProcess member = org.omg.WorkflowModel.WfProcessHelper.read (in);
           boolean $result = false;
           $result = this.is_member_of_performer (member);
           out = $rh.createReply();
           out.write_boolean ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 4:  // WorkflowModel/WfRequester/receive_event
       {
         try {
           org.omg.WorkflowModel.WfEventAudit event = org.omg.WorkflowModel.WfEventAuditHelper.read (in);
           this.receive_event (event);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.omg.WorkflowModel.InvalidPerformer $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WorkflowModel.InvalidPerformerHelper.write (out, $ex);
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
    "IDL:omg.org/WorkflowModel/WfRequester:1.0", 
    "IDL:omg.org/WfBase/BaseBusinessObject:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }


} // class _WfRequesterImplBase
