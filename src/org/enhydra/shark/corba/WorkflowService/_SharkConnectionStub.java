package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/_SharkConnectionStub.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分33秒 CST
*/

public class _SharkConnectionStub extends org.omg.CORBA.portable.ObjectImpl implements org.enhydra.shark.corba.WorkflowService.SharkConnection
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

  public org.omg.WorkflowModel.WfResource getResourceObject () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getResourceObject", true);
                $in = _invoke ($out);
                org.omg.WorkflowModel.WfResource $result = org.omg.WorkflowModel.WfResourceHelper.read ($in);
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
                return getResourceObject (        );
            } finally {
                _releaseReply ($in);
            }
  } // getResourceObject

  public org.omg.WorkflowModel.WfProcess createProcess (String pkgId, String pDefId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected, org.omg.WorkflowModel.NotEnabled
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("createProcess", true);
                $out.write_wstring (pkgId);
                $out.write_wstring (pDefId);
                $in = _invoke ($out);
                org.omg.WorkflowModel.WfProcess $result = org.omg.WorkflowModel.WfProcessHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else if (_id.equals ("IDL:omg.org/WorkflowModel/NotEnabled:1.0"))
                    throw org.omg.WorkflowModel.NotEnabledHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return createProcess (pkgId, pDefId        );
            } finally {
                _releaseReply ($in);
            }
  } // createProcess

  public org.omg.WorkflowModel.WfProcess createProcessWithRequester (org.omg.WorkflowModel.WfRequester requester, String pkgId, String pDefId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected, org.omg.WorkflowModel.NotEnabled, org.omg.WorkflowModel.InvalidRequester, org.omg.WorkflowModel.RequesterRequired
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("createProcessWithRequester", true);
                org.omg.WorkflowModel.WfRequesterHelper.write ($out, requester);
                $out.write_wstring (pkgId);
                $out.write_wstring (pDefId);
                $in = _invoke ($out);
                org.omg.WorkflowModel.WfProcess $result = org.omg.WorkflowModel.WfProcessHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else if (_id.equals ("IDL:omg.org/WorkflowModel/NotEnabled:1.0"))
                    throw org.omg.WorkflowModel.NotEnabledHelper.read ($in);
                else if (_id.equals ("IDL:omg.org/WorkflowModel/InvalidRequester:1.0"))
                    throw org.omg.WorkflowModel.InvalidRequesterHelper.read ($in);
                else if (_id.equals ("IDL:omg.org/WorkflowModel/RequesterRequired:1.0"))
                    throw org.omg.WorkflowModel.RequesterRequiredHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return createProcessWithRequester (requester, pkgId, pDefId        );
            } finally {
                _releaseReply ($in);
            }
  } // createProcessWithRequester

  public void doneWith (org.omg.CORBA.Object toDisconnect)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("doneWith", true);
                org.omg.CORBA.ObjectHelper.write ($out, toDisconnect);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                doneWith (toDisconnect        );
            } finally {
                _releaseReply ($in);
            }
  } // doneWith

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:WorkflowService/SharkConnection:1.0"};

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
} // class _SharkConnectionStub
