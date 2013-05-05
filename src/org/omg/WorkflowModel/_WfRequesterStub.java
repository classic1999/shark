package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/_WfRequesterStub.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

public class _WfRequesterStub extends org.omg.CORBA.portable.ObjectImpl implements org.omg.WorkflowModel.WfRequester
{

  public int how_many_performer () throws org.omg.WfBase.BaseException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("how_many_performer", true);
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
                return how_many_performer (        );
            } finally {
                _releaseReply ($in);
            }
  } // how_many_performer

  public org.omg.WorkflowModel.WfProcessIterator get_iterator_performer () throws org.omg.WfBase.BaseException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("get_iterator_performer", true);
                $in = _invoke ($out);
                org.omg.WorkflowModel.WfProcessIterator $result = org.omg.WorkflowModel.WfProcessIteratorHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return get_iterator_performer (        );
            } finally {
                _releaseReply ($in);
            }
  } // get_iterator_performer

  public org.omg.WorkflowModel.WfProcess[] get_sequence_performer (int max_number) throws org.omg.WfBase.BaseException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("get_sequence_performer", true);
                $out.write_long (max_number);
                $in = _invoke ($out);
                org.omg.WorkflowModel.WfProcess $result[] = org.omg.WorkflowModel.WfProcessSequenceHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return get_sequence_performer (max_number        );
            } finally {
                _releaseReply ($in);
            }
  } // get_sequence_performer

  public boolean is_member_of_performer (org.omg.WorkflowModel.WfProcess member) throws org.omg.WfBase.BaseException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("is_member_of_performer", true);
                org.omg.WorkflowModel.WfProcessHelper.write ($out, member);
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
                return is_member_of_performer (member        );
            } finally {
                _releaseReply ($in);
            }
  } // is_member_of_performer

  public void receive_event (org.omg.WorkflowModel.WfEventAudit event) throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.InvalidPerformer
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("receive_event", true);
                org.omg.WorkflowModel.WfEventAuditHelper.write ($out, event);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:omg.org/WorkflowModel/InvalidPerformer:1.0"))
                    throw org.omg.WorkflowModel.InvalidPerformerHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                receive_event (event        );
            } finally {
                _releaseReply ($in);
            }
  } // receive_event

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:omg.org/WorkflowModel/WfRequester:1.0", 
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
} // class _WfRequesterStub
