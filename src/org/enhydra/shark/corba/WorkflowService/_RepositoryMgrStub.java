package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/_RepositoryMgrStub.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分33秒 CST
*/

public class _RepositoryMgrStub extends org.omg.CORBA.portable.ObjectImpl implements org.enhydra.shark.corba.WorkflowService.RepositoryMgr
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

  public String[] getPackagePaths () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getPackagePaths", true);
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
                return getPackagePaths (        );
            } finally {
                _releaseReply ($in);
            }
  } // getPackagePaths

  public org.omg.WfBase.NameValue[] getPackagePathToIdMapping () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getPackagePathToIdMapping", true);
                $in = _invoke ($out);
                org.omg.WfBase.NameValue $result[] = org.omg.WfBase.NameValueSequenceHelper.read ($in);
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
                return getPackagePathToIdMapping (        );
            } finally {
                _releaseReply ($in);
            }
  } // getPackagePathToIdMapping

  public String getPackageId (String relativePath) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getPackageId", true);
                $out.write_wstring (relativePath);
                $in = _invoke ($out);
                String $result = $in.read_wstring ();
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
                return getPackageId (relativePath        );
            } finally {
                _releaseReply ($in);
            }
  } // getPackageId

  public void uploadPkg (byte[] pkg, String relativePath) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected, org.enhydra.shark.corba.WorkflowService.RepositoryInvalid
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("uploadPkg", true);
                org.enhydra.shark.corba.WorkflowService.ByteSequenceHelper.write ($out, pkg);
                $out.write_wstring (relativePath);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/RepositoryInvalid:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.RepositoryInvalidHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                uploadPkg (pkg, relativePath        );
            } finally {
                _releaseReply ($in);
            }
  } // uploadPkg

  public void deletePkg (String relativePath) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected, org.enhydra.shark.corba.WorkflowService.RepositoryInvalid
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("deletePkg", true);
                $out.write_wstring (relativePath);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/RepositoryInvalid:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.RepositoryInvalidHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                deletePkg (relativePath        );
            } finally {
                _releaseReply ($in);
            }
  } // deletePkg

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:WorkflowService/RepositoryMgr:1.0"};

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
} // class _RepositoryMgrStub
