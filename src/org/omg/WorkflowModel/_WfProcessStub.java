package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/_WfProcessStub.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

public class _WfProcessStub extends org.omg.CORBA.portable.ObjectImpl implements org.omg.WorkflowModel.WfProcess
{

  public org.omg.WorkflowModel.WfRequester requester () throws org.omg.WfBase.BaseException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("requester", true);
                $in = _invoke ($out);
                org.omg.WorkflowModel.WfRequester $result = org.omg.WorkflowModel.WfRequesterHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return requester (        );
            } finally {
                _releaseReply ($in);
            }
  } // requester

  public void set_requester (org.omg.WorkflowModel.WfRequester new_value) throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.CannotChangeRequester
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("set_requester", true);
                org.omg.WorkflowModel.WfRequesterHelper.write ($out, new_value);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:omg.org/WorkflowModel/CannotChangeRequester:1.0"))
                    throw org.omg.WorkflowModel.CannotChangeRequesterHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                set_requester (new_value        );
            } finally {
                _releaseReply ($in);
            }
  } // set_requester

  public int how_many_step () throws org.omg.WfBase.BaseException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("how_many_step", true);
                $in = _invoke ($out);
                int $result = $in.read_long ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return how_many_step (        );
            } finally {
                _releaseReply ($in);
            }
  } // how_many_step

  public org.omg.WorkflowModel.WfActivityIterator get_iterator_step () throws org.omg.WfBase.BaseException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("get_iterator_step", true);
                $in = _invoke ($out);
                org.omg.WorkflowModel.WfActivityIterator $result = org.omg.WorkflowModel.WfActivityIteratorHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return get_iterator_step (        );
            } finally {
                _releaseReply ($in);
            }
  } // get_iterator_step

  public org.omg.WorkflowModel.WfActivity[] get_sequence_step (int max_number) throws org.omg.WfBase.BaseException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("get_sequence_step", true);
                $out.write_long (max_number);
                $in = _invoke ($out);
                org.omg.WorkflowModel.WfActivity $result[] = org.omg.WorkflowModel.WfActivitySequenceHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return get_sequence_step (max_number        );
            } finally {
                _releaseReply ($in);
            }
  } // get_sequence_step

  public boolean is_member_of_step (org.omg.WorkflowModel.WfActivity member) throws org.omg.WfBase.BaseException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("is_member_of_step", true);
                org.omg.WorkflowModel.WfActivityHelper.write ($out, member);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return is_member_of_step (member        );
            } finally {
                _releaseReply ($in);
            }
  } // is_member_of_step

  public org.omg.WorkflowModel.WfProcessMgr manager () throws org.omg.WfBase.BaseException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("manager", true);
                $in = _invoke ($out);
                org.omg.WorkflowModel.WfProcessMgr $result = org.omg.WorkflowModel.WfProcessMgrHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return manager (        );
            } finally {
                _releaseReply ($in);
            }
  } // manager

  public org.omg.WfBase.NameValue[] result () throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.ResultNotAvailable
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("result", true);
                $in = _invoke ($out);
                org.omg.WfBase.NameValue $result[] = org.omg.WorkflowModel.ProcessDataHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:omg.org/WorkflowModel/ResultNotAvailable:1.0"))
                    throw org.omg.WorkflowModel.ResultNotAvailableHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return result (        );
            } finally {
                _releaseReply ($in);
            }
  } // result

  public void start () throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.CannotStart, org.omg.WorkflowModel.AlreadyRunning
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("start", true);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:omg.org/WorkflowModel/CannotStart:1.0"))
                    throw org.omg.WorkflowModel.CannotStartHelper.read ($in);
                else if (_id.equals ("IDL:omg.org/WorkflowModel/AlreadyRunning:1.0"))
                    throw org.omg.WorkflowModel.AlreadyRunningHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                start (        );
            } finally {
                _releaseReply ($in);
            }
  } // start

  public org.omg.WorkflowModel.WfActivityIterator get_activities_in_state (String state) throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.InvalidState
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("get_activities_in_state", true);
                $out.write_wstring (state);
                $in = _invoke ($out);
                org.omg.WorkflowModel.WfActivityIterator $result = org.omg.WorkflowModel.WfActivityIteratorHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:omg.org/WorkflowModel/InvalidState:1.0"))
                    throw org.omg.WorkflowModel.InvalidStateHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return get_activities_in_state (state        );
            } finally {
                _releaseReply ($in);
            }
  } // get_activities_in_state

  public org.omg.WorkflowModel.workflow_stateType workflow_state () throws org.omg.WfBase.BaseException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("workflow_state", true);
                $in = _invoke ($out);
                org.omg.WorkflowModel.workflow_stateType $result = org.omg.WorkflowModel.workflow_stateTypeHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return workflow_state (        );
            } finally {
                _releaseReply ($in);
            }
  } // workflow_state

  public org.omg.WorkflowModel.while_openType while_open () throws org.omg.WfBase.BaseException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("while_open", true);
                $in = _invoke ($out);
                org.omg.WorkflowModel.while_openType $result = org.omg.WorkflowModel.while_openTypeHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return while_open (        );
            } finally {
                _releaseReply ($in);
            }
  } // while_open

  public org.omg.WorkflowModel.why_not_runningType why_not_running () throws org.omg.WfBase.BaseException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("why_not_running", true);
                $in = _invoke ($out);
                org.omg.WorkflowModel.why_not_runningType $result = org.omg.WorkflowModel.why_not_runningTypeHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return why_not_running (        );
            } finally {
                _releaseReply ($in);
            }
  } // why_not_running

  public org.omg.WorkflowModel.how_closedType how_closed () throws org.omg.WfBase.BaseException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("how_closed", true);
                $in = _invoke ($out);
                org.omg.WorkflowModel.how_closedType $result = org.omg.WorkflowModel.how_closedTypeHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return how_closed (        );
            } finally {
                _releaseReply ($in);
            }
  } // how_closed

  public String[] valid_states () throws org.omg.WfBase.BaseException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("valid_states", true);
                $in = _invoke ($out);
                String $result[] = org.omg.WfBase.NameSequenceHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return valid_states (        );
            } finally {
                _releaseReply ($in);
            }
  } // valid_states

  public String state () throws org.omg.WfBase.BaseException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("state", true);
                $in = _invoke ($out);
                String $result = $in.read_wstring ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return state (        );
            } finally {
                _releaseReply ($in);
            }
  } // state

  public void change_state (String new_state) throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.InvalidState, org.omg.WorkflowModel.TransitionNotAllowed
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("change_state", true);
                $out.write_wstring (new_state);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:omg.org/WorkflowModel/InvalidState:1.0"))
                    throw org.omg.WorkflowModel.InvalidStateHelper.read ($in);
                else if (_id.equals ("IDL:omg.org/WorkflowModel/TransitionNotAllowed:1.0"))
                    throw org.omg.WorkflowModel.TransitionNotAllowedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                change_state (new_state        );
            } finally {
                _releaseReply ($in);
            }
  } // change_state

  public String name () throws org.omg.WfBase.BaseException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("name", true);
                $in = _invoke ($out);
                String $result = $in.read_wstring ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return name (        );
            } finally {
                _releaseReply ($in);
            }
  } // name

  public void set_name (String new_value) throws org.omg.WfBase.BaseException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("set_name", true);
                $out.write_wstring (new_value);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                set_name (new_value        );
            } finally {
                _releaseReply ($in);
            }
  } // set_name

  public String key () throws org.omg.WfBase.BaseException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("key", true);
                $in = _invoke ($out);
                String $result = $in.read_wstring ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return key (        );
            } finally {
                _releaseReply ($in);
            }
  } // key

  public String description () throws org.omg.WfBase.BaseException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("description", true);
                $in = _invoke ($out);
                String $result = $in.read_wstring ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return description (        );
            } finally {
                _releaseReply ($in);
            }
  } // description

  public void set_description (String new_value) throws org.omg.WfBase.BaseException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("set_description", true);
                $out.write_wstring (new_value);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                set_description (new_value        );
            } finally {
                _releaseReply ($in);
            }
  } // set_description

  public org.omg.WfBase.NameValue[] process_context () throws org.omg.WfBase.BaseException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("process_context", true);
                $in = _invoke ($out);
                org.omg.WfBase.NameValue $result[] = org.omg.WorkflowModel.ProcessDataHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return process_context (        );
            } finally {
                _releaseReply ($in);
            }
  } // process_context

  public void set_process_context (org.omg.WfBase.NameValue[] new_value) throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.InvalidData, org.omg.WorkflowModel.UpdateNotAllowed
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("set_process_context", true);
                org.omg.WorkflowModel.ProcessDataHelper.write ($out, new_value);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:omg.org/WorkflowModel/InvalidData:1.0"))
                    throw org.omg.WorkflowModel.InvalidDataHelper.read ($in);
                else if (_id.equals ("IDL:omg.org/WorkflowModel/UpdateNotAllowed:1.0"))
                    throw org.omg.WorkflowModel.UpdateNotAllowedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                set_process_context (new_value        );
            } finally {
                _releaseReply ($in);
            }
  } // set_process_context

  public short priority () throws org.omg.WfBase.BaseException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("priority", true);
                $in = _invoke ($out);
                short $result = $in.read_ushort ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return priority (        );
            } finally {
                _releaseReply ($in);
            }
  } // priority

  public void set_priority (short new_value) throws org.omg.WfBase.BaseException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("set_priority", true);
                $out.write_ushort (new_value);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                set_priority (new_value        );
            } finally {
                _releaseReply ($in);
            }
  } // set_priority

  public void resume () throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.CannotResume, org.omg.WorkflowModel.NotSuspended
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("resume", true);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:omg.org/WorkflowModel/CannotResume:1.0"))
                    throw org.omg.WorkflowModel.CannotResumeHelper.read ($in);
                else if (_id.equals ("IDL:omg.org/WorkflowModel/NotSuspended:1.0"))
                    throw org.omg.WorkflowModel.NotSuspendedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                resume (        );
            } finally {
                _releaseReply ($in);
            }
  } // resume

  public void suspend () throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.CannotSuspend, org.omg.WorkflowModel.NotRunning, org.omg.WorkflowModel.AlreadySuspended
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("suspend", true);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:omg.org/WorkflowModel/CannotSuspend:1.0"))
                    throw org.omg.WorkflowModel.CannotSuspendHelper.read ($in);
                else if (_id.equals ("IDL:omg.org/WorkflowModel/NotRunning:1.0"))
                    throw org.omg.WorkflowModel.NotRunningHelper.read ($in);
                else if (_id.equals ("IDL:omg.org/WorkflowModel/AlreadySuspended:1.0"))
                    throw org.omg.WorkflowModel.AlreadySuspendedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                suspend (        );
            } finally {
                _releaseReply ($in);
            }
  } // suspend

  public void terminate () throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.CannotStop, org.omg.WorkflowModel.NotRunning
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("terminate", true);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:omg.org/WorkflowModel/CannotStop:1.0"))
                    throw org.omg.WorkflowModel.CannotStopHelper.read ($in);
                else if (_id.equals ("IDL:omg.org/WorkflowModel/NotRunning:1.0"))
                    throw org.omg.WorkflowModel.NotRunningHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                terminate (        );
            } finally {
                _releaseReply ($in);
            }
  } // terminate

  public void abort () throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.CannotStop, org.omg.WorkflowModel.NotRunning
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("abort", true);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:omg.org/WorkflowModel/CannotStop:1.0"))
                    throw org.omg.WorkflowModel.CannotStopHelper.read ($in);
                else if (_id.equals ("IDL:omg.org/WorkflowModel/NotRunning:1.0"))
                    throw org.omg.WorkflowModel.NotRunningHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                abort (        );
            } finally {
                _releaseReply ($in);
            }
  } // abort

  public int how_many_history () throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.HistoryNotAvailable
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("how_many_history", true);
                $in = _invoke ($out);
                int $result = $in.read_long ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:omg.org/WorkflowModel/HistoryNotAvailable:1.0"))
                    throw org.omg.WorkflowModel.HistoryNotAvailableHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return how_many_history (        );
            } finally {
                _releaseReply ($in);
            }
  } // how_many_history

  public org.omg.WorkflowModel.WfEventAuditIterator get_iterator_history (String query, org.omg.WfBase.NameValue[] names_in_query) throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.HistoryNotAvailable
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("get_iterator_history", true);
                $out.write_wstring (query);
                org.omg.WfBase.NameValueSequenceHelper.write ($out, names_in_query);
                $in = _invoke ($out);
                org.omg.WorkflowModel.WfEventAuditIterator $result = org.omg.WorkflowModel.WfEventAuditIteratorHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:omg.org/WorkflowModel/HistoryNotAvailable:1.0"))
                    throw org.omg.WorkflowModel.HistoryNotAvailableHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return get_iterator_history (query, names_in_query        );
            } finally {
                _releaseReply ($in);
            }
  } // get_iterator_history

  public org.omg.WorkflowModel.WfEventAudit[] get_sequence_history (int max_number) throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.HistoryNotAvailable
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("get_sequence_history", true);
                $out.write_long (max_number);
                $in = _invoke ($out);
                org.omg.WorkflowModel.WfEventAudit $result[] = org.omg.WorkflowModel.WfEventAuditSequenceHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:omg.org/WorkflowModel/HistoryNotAvailable:1.0"))
                    throw org.omg.WorkflowModel.HistoryNotAvailableHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return get_sequence_history (max_number        );
            } finally {
                _releaseReply ($in);
            }
  } // get_sequence_history

  public boolean is_member_of_history (org.omg.WorkflowModel.WfExecutionObject member) throws org.omg.WfBase.BaseException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("is_member_of_history", true);
                org.omg.WorkflowModel.WfExecutionObjectHelper.write ($out, member);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return is_member_of_history (member        );
            } finally {
                _releaseReply ($in);
            }
  } // is_member_of_history

  public org.omg.TimeBase.UtcT last_state_time () throws org.omg.WfBase.BaseException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("last_state_time", true);
                $in = _invoke ($out);
                org.omg.TimeBase.UtcT $result = org.omg.TimeBase.UtcTHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return last_state_time (        );
            } finally {
                _releaseReply ($in);
            }
  } // last_state_time

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:omg.org/WorkflowModel/WfProcess:1.0", 
    "IDL:omg.org/WorkflowModel/WfExecutionObject:1.0", 
    "IDL:omg.org/WfBase/BaseBusinessObject:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.Object obj = org.omg.CORBA.ORB.init (args, props).string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     String str = org.omg.CORBA.ORB.init (args, props).object_to_string (this);
     s.writeUTF (str);
  }
} // class _WfProcessStub
