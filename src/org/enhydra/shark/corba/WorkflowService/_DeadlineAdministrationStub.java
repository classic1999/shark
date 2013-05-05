package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/_DeadlineAdministrationStub.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分32秒 CST
*/

public class _DeadlineAdministrationStub extends org.omg.CORBA.portable.ObjectImpl implements org.enhydra.shark.corba.WorkflowService.DeadlineAdministration
{

  public void connect (String userId, String password, String engineName, String scope) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.ConnectFailed
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("connect", true);
                $out.write_wstring (userId);
                $out.write_wstring (password);
                $out.write_wstring (engineName);
                $out.write_wstring (scope);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/ConnectFailed:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.ConnectFailedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                connect (userId, password, engineName, scope        );
            } finally {
                _releaseReply ($in);
            }
  } // connect

  public void disconnect () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("disconnect", true);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                disconnect (        );
            } finally {
                _releaseReply ($in);
            }
  } // disconnect

  public void checkDeadlines () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("checkDeadlines", true);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                checkDeadlines (        );
            } finally {
                _releaseReply ($in);
            }
  } // checkDeadlines

  public void checkDeadlinesForProcesses (String[] procIds) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("checkDeadlinesForProcesses", true);
                org.enhydra.shark.corba.WorkflowService.WStringSequenceHelper.write ($out, procIds);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                checkDeadlinesForProcesses (procIds        );
            } finally {
                _releaseReply ($in);
            }
  } // checkDeadlinesForProcesses

  public void checkProcessDeadlines (String procId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("checkProcessDeadlines", true);
                $out.write_wstring (procId);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                checkProcessDeadlines (procId        );
            } finally {
                _releaseReply ($in);
            }
  } // checkProcessDeadlines

  public void checkActivityDeadline (String procId, String actId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("checkActivityDeadline", true);
                $out.write_wstring (procId);
                $out.write_wstring (actId);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                checkActivityDeadline (procId, actId        );
            } finally {
                _releaseReply ($in);
            }
  } // checkActivityDeadline

  public String[] checkDeadlinesMultiTrans (int instancesPerTransaction, int failuresToIgnore) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("checkDeadlinesMultiTrans", true);
                $out.write_long (instancesPerTransaction);
                $out.write_long (failuresToIgnore);
                $in = _invoke ($out);
                String $result[] = org.enhydra.shark.corba.WorkflowService.WStringSequenceHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return checkDeadlinesMultiTrans (instancesPerTransaction, failuresToIgnore        );
            } finally {
                _releaseReply ($in);
            }
  } // checkDeadlinesMultiTrans

  public org.enhydra.shark.corba.WorkflowService.DeadlineInfo[] getDeadlineInfoForProcess (String procId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getDeadlineInfoForProcess", true);
                $out.write_wstring (procId);
                $in = _invoke ($out);
                org.enhydra.shark.corba.WorkflowService.DeadlineInfo $result[] = org.enhydra.shark.corba.WorkflowService.DeadlineInfoSequenceHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getDeadlineInfoForProcess (procId        );
            } finally {
                _releaseReply ($in);
            }
  } // getDeadlineInfoForProcess

  public org.enhydra.shark.corba.WorkflowService.DeadlineInfo[] getDeadlineInfoForActivity (String procId, String actId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getDeadlineInfoForActivity", true);
                $out.write_wstring (procId);
                $out.write_wstring (actId);
                $in = _invoke ($out);
                org.enhydra.shark.corba.WorkflowService.DeadlineInfo $result[] = org.enhydra.shark.corba.WorkflowService.DeadlineInfoSequenceHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getDeadlineInfoForActivity (procId, actId        );
            } finally {
                _releaseReply ($in);
            }
  } // getDeadlineInfoForActivity

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:WorkflowService/DeadlineAdministration:1.0"};

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
} // class _DeadlineAdministrationStub
