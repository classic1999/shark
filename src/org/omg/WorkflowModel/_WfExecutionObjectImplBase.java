package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/_WfExecutionObjectImplBase.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/


// Interfaces
public abstract class _WfExecutionObjectImplBase extends org.omg.CORBA.portable.ObjectImpl
                implements org.omg.WorkflowModel.WfExecutionObject, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors
  public _WfExecutionObjectImplBase ()
  {
  }

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("workflow_state", new java.lang.Integer (0));
    _methods.put ("while_open", new java.lang.Integer (1));
    _methods.put ("why_not_running", new java.lang.Integer (2));
    _methods.put ("how_closed", new java.lang.Integer (3));
    _methods.put ("valid_states", new java.lang.Integer (4));
    _methods.put ("state", new java.lang.Integer (5));
    _methods.put ("change_state", new java.lang.Integer (6));
    _methods.put ("name", new java.lang.Integer (7));
    _methods.put ("set_name", new java.lang.Integer (8));
    _methods.put ("key", new java.lang.Integer (9));
    _methods.put ("description", new java.lang.Integer (10));
    _methods.put ("set_description", new java.lang.Integer (11));
    _methods.put ("process_context", new java.lang.Integer (12));
    _methods.put ("set_process_context", new java.lang.Integer (13));
    _methods.put ("priority", new java.lang.Integer (14));
    _methods.put ("set_priority", new java.lang.Integer (15));
    _methods.put ("resume", new java.lang.Integer (16));
    _methods.put ("suspend", new java.lang.Integer (17));
    _methods.put ("terminate", new java.lang.Integer (18));
    _methods.put ("abort", new java.lang.Integer (19));
    _methods.put ("how_many_history", new java.lang.Integer (20));
    _methods.put ("get_iterator_history", new java.lang.Integer (21));
    _methods.put ("get_sequence_history", new java.lang.Integer (22));
    _methods.put ("is_member_of_history", new java.lang.Integer (23));
    _methods.put ("last_state_time", new java.lang.Integer (24));
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
       case 0:  // WorkflowModel/WfExecutionObject/workflow_state
       {
         try {
           org.omg.WorkflowModel.workflow_stateType $result = null;
           $result = this.workflow_state ();
           out = $rh.createReply();
           org.omg.WorkflowModel.workflow_stateTypeHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 1:  // WorkflowModel/WfExecutionObject/while_open
       {
         try {
           org.omg.WorkflowModel.while_openType $result = null;
           $result = this.while_open ();
           out = $rh.createReply();
           org.omg.WorkflowModel.while_openTypeHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 2:  // WorkflowModel/WfExecutionObject/why_not_running
       {
         try {
           org.omg.WorkflowModel.why_not_runningType $result = null;
           $result = this.why_not_running ();
           out = $rh.createReply();
           org.omg.WorkflowModel.why_not_runningTypeHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 3:  // WorkflowModel/WfExecutionObject/how_closed
       {
         try {
           org.omg.WorkflowModel.how_closedType $result = null;
           $result = this.how_closed ();
           out = $rh.createReply();
           org.omg.WorkflowModel.how_closedTypeHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 4:  // WorkflowModel/WfExecutionObject/valid_states
       {
         try {
           String $result[] = null;
           $result = this.valid_states ();
           out = $rh.createReply();
           org.omg.WfBase.NameSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 5:  // WorkflowModel/WfExecutionObject/state
       {
         try {
           String $result = null;
           $result = this.state ();
           out = $rh.createReply();
           out.write_wstring ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 6:  // WorkflowModel/WfExecutionObject/change_state
       {
         try {
           String new_state = in.read_wstring ();
           this.change_state (new_state);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.omg.WorkflowModel.InvalidState $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WorkflowModel.InvalidStateHelper.write (out, $ex);
         } catch (org.omg.WorkflowModel.TransitionNotAllowed $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WorkflowModel.TransitionNotAllowedHelper.write (out, $ex);
         }
         break;
       }

       case 7:  // WorkflowModel/WfExecutionObject/name
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

       case 8:  // WorkflowModel/WfExecutionObject/set_name
       {
         try {
           String new_value = in.read_wstring ();
           this.set_name (new_value);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 9:  // WorkflowModel/WfExecutionObject/key
       {
         try {
           String $result = null;
           $result = this.key ();
           out = $rh.createReply();
           out.write_wstring ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 10:  // WorkflowModel/WfExecutionObject/description
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

       case 11:  // WorkflowModel/WfExecutionObject/set_description
       {
         try {
           String new_value = in.read_wstring ();
           this.set_description (new_value);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 12:  // WorkflowModel/WfExecutionObject/process_context
       {
         try {
           org.omg.WfBase.NameValue $result[] = null;
           $result = this.process_context ();
           out = $rh.createReply();
           org.omg.WfBase.NameValueSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 13:  // WorkflowModel/WfExecutionObject/set_process_context
       {
         try {
           org.omg.WfBase.NameValue new_value[] = org.omg.WorkflowModel.ProcessDataHelper.read (in);
           this.set_process_context (new_value);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.omg.WorkflowModel.InvalidData $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WorkflowModel.InvalidDataHelper.write (out, $ex);
         } catch (org.omg.WorkflowModel.UpdateNotAllowed $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WorkflowModel.UpdateNotAllowedHelper.write (out, $ex);
         }
         break;
       }

       case 14:  // WorkflowModel/WfExecutionObject/priority
       {
         try {
           short $result = (short)0;
           $result = this.priority ();
           out = $rh.createReply();
           out.write_ushort ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 15:  // WorkflowModel/WfExecutionObject/set_priority
       {
         try {
           short new_value = in.read_ushort ();
           this.set_priority (new_value);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 16:  // WorkflowModel/WfExecutionObject/resume
       {
         try {
           this.resume ();
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.omg.WorkflowModel.CannotResume $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WorkflowModel.CannotResumeHelper.write (out, $ex);
         } catch (org.omg.WorkflowModel.NotSuspended $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WorkflowModel.NotSuspendedHelper.write (out, $ex);
         }
         break;
       }

       case 17:  // WorkflowModel/WfExecutionObject/suspend
       {
         try {
           this.suspend ();
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.omg.WorkflowModel.CannotSuspend $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WorkflowModel.CannotSuspendHelper.write (out, $ex);
         } catch (org.omg.WorkflowModel.NotRunning $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WorkflowModel.NotRunningHelper.write (out, $ex);
         } catch (org.omg.WorkflowModel.AlreadySuspended $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WorkflowModel.AlreadySuspendedHelper.write (out, $ex);
         }
         break;
       }

       case 18:  // WorkflowModel/WfExecutionObject/terminate
       {
         try {
           this.terminate ();
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.omg.WorkflowModel.CannotStop $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WorkflowModel.CannotStopHelper.write (out, $ex);
         } catch (org.omg.WorkflowModel.NotRunning $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WorkflowModel.NotRunningHelper.write (out, $ex);
         }
         break;
       }

       case 19:  // WorkflowModel/WfExecutionObject/abort
       {
         try {
           this.abort ();
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.omg.WorkflowModel.CannotStop $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WorkflowModel.CannotStopHelper.write (out, $ex);
         } catch (org.omg.WorkflowModel.NotRunning $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WorkflowModel.NotRunningHelper.write (out, $ex);
         }
         break;
       }

       case 20:  // WorkflowModel/WfExecutionObject/how_many_history
       {
         try {
           int $result = (int)0;
           $result = this.how_many_history ();
           out = $rh.createReply();
           out.write_long ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.omg.WorkflowModel.HistoryNotAvailable $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WorkflowModel.HistoryNotAvailableHelper.write (out, $ex);
         }
         break;
       }

       case 21:  // WorkflowModel/WfExecutionObject/get_iterator_history
       {
         try {
           String query = in.read_wstring ();
           org.omg.WfBase.NameValue names_in_query[] = org.omg.WfBase.NameValueSequenceHelper.read (in);
           org.omg.WorkflowModel.WfEventAuditIterator $result = null;
           $result = this.get_iterator_history (query, names_in_query);
           out = $rh.createReply();
           org.omg.WorkflowModel.WfEventAuditIteratorHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.omg.WorkflowModel.HistoryNotAvailable $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WorkflowModel.HistoryNotAvailableHelper.write (out, $ex);
         }
         break;
       }

       case 22:  // WorkflowModel/WfExecutionObject/get_sequence_history
       {
         try {
           int max_number = in.read_long ();
           org.omg.WorkflowModel.WfEventAudit $result[] = null;
           $result = this.get_sequence_history (max_number);
           out = $rh.createReply();
           org.omg.WorkflowModel.WfEventAuditSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.omg.WorkflowModel.HistoryNotAvailable $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WorkflowModel.HistoryNotAvailableHelper.write (out, $ex);
         }
         break;
       }

       case 23:  // WorkflowModel/WfExecutionObject/is_member_of_history
       {
         try {
           org.omg.WorkflowModel.WfExecutionObject member = org.omg.WorkflowModel.WfExecutionObjectHelper.read (in);
           boolean $result = false;
           $result = this.is_member_of_history (member);
           out = $rh.createReply();
           out.write_boolean ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 24:  // WorkflowModel/WfExecutionObject/last_state_time
       {
         try {
           org.omg.TimeBase.UtcT $result = null;
           $result = this.last_state_time ();
           out = $rh.createReply();
           org.omg.TimeBase.UtcTHelper.write (out, $result);
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
    "IDL:omg.org/WorkflowModel/WfExecutionObject:1.0", 
    "IDL:omg.org/WfBase/BaseBusinessObject:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }


} // class _WfExecutionObjectImplBase
