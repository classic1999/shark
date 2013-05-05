package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/_PackageAdministrationStub.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分33秒 CST
*/

public class _PackageAdministrationStub extends org.omg.CORBA.portable.ObjectImpl implements org.enhydra.shark.corba.WorkflowService.PackageAdministration
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

  public String[] getOpenedPackageIds () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getOpenedPackageIds", true);
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
                return getOpenedPackageIds (        );
            } finally {
                _releaseReply ($in);
            }
  } // getOpenedPackageIds

  public String[] getPackageVersions (String pkgId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getPackageVersions", true);
                $out.write_wstring (pkgId);
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
                return getPackageVersions (pkgId        );
            } finally {
                _releaseReply ($in);
            }
  } // getPackageVersions

  public boolean isPackageOpened (String pkgId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("isPackageOpened", true);
                $out.write_wstring (pkgId);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
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
                return isPackageOpened (pkgId        );
            } finally {
                _releaseReply ($in);
            }
  } // isPackageOpened

  public byte[] getPackageContent (String pkgId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getPackageContent", true);
                $out.write_wstring (pkgId);
                $in = _invoke ($out);
                byte $result[] = org.enhydra.shark.corba.WorkflowService.ByteSequenceHelper.read ($in);
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
                return getPackageContent (pkgId        );
            } finally {
                _releaseReply ($in);
            }
  } // getPackageContent

  public byte[] getPackageVersionContent (String pkgId, String pkgVer) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getPackageVersionContent", true);
                $out.write_wstring (pkgId);
                $out.write_wstring (pkgVer);
                $in = _invoke ($out);
                byte $result[] = org.enhydra.shark.corba.WorkflowService.ByteSequenceHelper.read ($in);
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
                return getPackageVersionContent (pkgId, pkgVer        );
            } finally {
                _releaseReply ($in);
            }
  } // getPackageVersionContent

  public String getCurrentPackageVersion (String pkgId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getCurrentPackageVersion", true);
                $out.write_wstring (pkgId);
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
                return getCurrentPackageVersion (pkgId        );
            } finally {
                _releaseReply ($in);
            }
  } // getCurrentPackageVersion

  public String openPkg (String relativePath) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected, org.enhydra.shark.corba.WorkflowService.PackageInvalid, org.enhydra.shark.corba.WorkflowService.ExternalPackageInvalid
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("openPkg", true);
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
                else if (_id.equals ("IDL:WorkflowService/PackageInvalid:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.PackageInvalidHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/ExternalPackageInvalid:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.ExternalPackageInvalidHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return openPkg (relativePath        );
            } finally {
                _releaseReply ($in);
            }
  } // openPkg

  public void updatePackage1 (String id, String _relativePathToNewPackage) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected, org.enhydra.shark.corba.WorkflowService.PackageUpdateNotAllowed, org.enhydra.shark.corba.WorkflowService.PackageInvalid, org.enhydra.shark.corba.WorkflowService.ExternalPackageInvalid
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("updatePackage1", true);
                $out.write_wstring (id);
                $out.write_wstring (_relativePathToNewPackage);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/PackageUpdateNotAllowed:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.PackageUpdateNotAllowedHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/PackageInvalid:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.PackageInvalidHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/ExternalPackageInvalid:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.ExternalPackageInvalidHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                updatePackage1 (id, _relativePathToNewPackage        );
            } finally {
                _releaseReply ($in);
            }
  } // updatePackage1

  public void closePkg (String id) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected, org.enhydra.shark.corba.WorkflowService.PackageInUse, org.enhydra.shark.corba.WorkflowService.PackageHasActiveProcesses
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("closePkg", true);
                $out.write_wstring (id);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/PackageInUse:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.PackageInUseHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/PackageHasActiveProcesses:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.PackageHasActiveProcessesHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                closePkg (id        );
            } finally {
                _releaseReply ($in);
            }
  } // closePkg

  public void closePkgWithVersion (String id, String ver) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected, org.enhydra.shark.corba.WorkflowService.PackageInUse, org.enhydra.shark.corba.WorkflowService.PackageHasActiveProcesses
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("closePkgWithVersion", true);
                $out.write_wstring (id);
                $out.write_wstring (ver);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/PackageInUse:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.PackageInUseHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/PackageHasActiveProcesses:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.PackageHasActiveProcessesHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                closePkgWithVersion (id, ver        );
            } finally {
                _releaseReply ($in);
            }
  } // closePkgWithVersion

  public boolean isPackageReferenced (String pkgId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("isPackageReferenced", true);
                $out.write_wstring (pkgId);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
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
                return isPackageReferenced (pkgId        );
            } finally {
                _releaseReply ($in);
            }
  } // isPackageReferenced

  public void synchronizeXPDLCache () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("synchronizeXPDLCache", true);
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
                synchronizeXPDLCache (        );
            } finally {
                _releaseReply ($in);
            }
  } // synchronizeXPDLCache

  public void clearXPDLCache () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("clearXPDLCache", true);
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
                clearXPDLCache (        );
            } finally {
                _releaseReply ($in);
            }
  } // clearXPDLCache

  public void refreshXPDLCache () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("refreshXPDLCache", true);
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
                refreshXPDLCache (        );
            } finally {
                _releaseReply ($in);
            }
  } // refreshXPDLCache

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:WorkflowService/PackageAdministration:1.0"};

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
} // class _PackageAdministrationStub
