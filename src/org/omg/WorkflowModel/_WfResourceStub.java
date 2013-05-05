package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/_WfResourceStub.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

public class _WfResourceStub extends org.omg.CORBA.portable.ObjectImpl implements org.omg.WorkflowModel.WfResource
{

  public int how_many_work_item () throws org.omg.WfBase.BaseException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("how_many_work_item", true);
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
                return how_many_work_item (        );
            } finally {
                _releaseReply ($in);
            }
  } // how_many_work_item

  public org.omg.WorkflowModel.WfAssignmentIterator get_iterator_work_item () throws org.omg.WfBase.BaseException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("get_iterator_work_item", true);
                $in = _invoke ($out);
                org.omg.WorkflowModel.WfAssignmentIterator $result = org.omg.WorkflowModel.WfAssignmentIteratorHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return get_iterator_work_item (        );
            } finally {
                _releaseReply ($in);
            }
  } // get_iterator_work_item

  public org.omg.WorkflowModel.WfAssignment[] get_sequence_work_item (int max_number) throws org.omg.WfBase.BaseException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("get_sequence_work_item", true);
                $out.write_long (max_number);
                $in = _invoke ($out);
                org.omg.WorkflowModel.WfAssignment $result[] = org.omg.WorkflowModel.WfAssignmentSequenceHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return get_sequence_work_item (max_number        );
            } finally {
                _releaseReply ($in);
            }
  } // get_sequence_work_item

  public boolean is_member_of_work_items (org.omg.WorkflowModel.WfAssignment member) throws org.omg.WfBase.BaseException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("is_member_of_work_items", true);
                org.omg.WorkflowModel.WfAssignmentHelper.write ($out, member);
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
                return is_member_of_work_items (member        );
            } finally {
                _releaseReply ($in);
            }
  } // is_member_of_work_items

  public String resource_key () throws org.omg.WfBase.BaseException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("resource_key", true);
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
                return resource_key (        );
            } finally {
                _releaseReply ($in);
            }
  } // resource_key

  public String resource_name () throws org.omg.WfBase.BaseException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("resource_name", true);
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
                return resource_name (        );
            } finally {
                _releaseReply ($in);
            }
  } // resource_name

  public void release (org.omg.WorkflowModel.WfAssignment from_assigment, String release_info) throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.NotAssigned
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("release", true);
                org.omg.WorkflowModel.WfAssignmentHelper.write ($out, from_assigment);
                $out.write_wstring (release_info);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:omg.org/WorkflowModel/NotAssigned:1.0"))
                    throw org.omg.WorkflowModel.NotAssignedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                release (from_assigment, release_info        );
            } finally {
                _releaseReply ($in);
            }
  } // release

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:omg.org/WorkflowModel/WfResource:1.0", 
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
} // class _WfResourceStub
