package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/_PackageAdministrationImplBase.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分33秒 CST
*/

public abstract class _PackageAdministrationImplBase extends org.omg.CORBA.portable.ObjectImpl
                implements org.enhydra.shark.corba.WorkflowService.PackageAdministration, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors
  public _PackageAdministrationImplBase ()
  {
  }

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("connect", new java.lang.Integer (0));
    _methods.put ("disconnect", new java.lang.Integer (1));
    _methods.put ("getOpenedPackageIds", new java.lang.Integer (2));
    _methods.put ("getPackageVersions", new java.lang.Integer (3));
    _methods.put ("isPackageOpened", new java.lang.Integer (4));
    _methods.put ("getPackageContent", new java.lang.Integer (5));
    _methods.put ("getPackageVersionContent", new java.lang.Integer (6));
    _methods.put ("getCurrentPackageVersion", new java.lang.Integer (7));
    _methods.put ("openPkg", new java.lang.Integer (8));
    _methods.put ("updatePackage1", new java.lang.Integer (9));
    _methods.put ("closePkg", new java.lang.Integer (10));
    _methods.put ("closePkgWithVersion", new java.lang.Integer (11));
    _methods.put ("isPackageReferenced", new java.lang.Integer (12));
    _methods.put ("synchronizeXPDLCache", new java.lang.Integer (13));
    _methods.put ("clearXPDLCache", new java.lang.Integer (14));
    _methods.put ("refreshXPDLCache", new java.lang.Integer (15));
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
       case 0:  // WorkflowService/PackageAdministration/connect
       {
         try {
           String userId = in.read_wstring ();
           String password = in.read_wstring ();
           String engineName = in.read_wstring ();
           String scope = in.read_wstring ();
           this.connect (userId, password, engineName, scope);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.ConnectFailed $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.ConnectFailedHelper.write (out, $ex);
         }
         break;
       }

       case 1:  // WorkflowService/PackageAdministration/disconnect
       {
         try {
           this.disconnect ();
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 2:  // WorkflowService/PackageAdministration/getOpenedPackageIds
       {
         try {
           String $result[] = null;
           $result = this.getOpenedPackageIds ();
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.WStringSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 3:  // WorkflowService/PackageAdministration/getPackageVersions
       {
         try {
           String pkgId = in.read_wstring ();
           String $result[] = null;
           $result = this.getPackageVersions (pkgId);
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.WStringSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 4:  // WorkflowService/PackageAdministration/isPackageOpened
       {
         try {
           String pkgId = in.read_wstring ();
           boolean $result = false;
           $result = this.isPackageOpened (pkgId);
           out = $rh.createReply();
           out.write_boolean ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 5:  // WorkflowService/PackageAdministration/getPackageContent
       {
         try {
           String pkgId = in.read_wstring ();
           byte $result[] = null;
           $result = this.getPackageContent (pkgId);
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.ByteSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 6:  // WorkflowService/PackageAdministration/getPackageVersionContent
       {
         try {
           String pkgId = in.read_wstring ();
           String pkgVer = in.read_wstring ();
           byte $result[] = null;
           $result = this.getPackageVersionContent (pkgId, pkgVer);
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.ByteSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 7:  // WorkflowService/PackageAdministration/getCurrentPackageVersion
       {
         try {
           String pkgId = in.read_wstring ();
           String $result = null;
           $result = this.getCurrentPackageVersion (pkgId);
           out = $rh.createReply();
           out.write_wstring ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 8:  // WorkflowService/PackageAdministration/openPkg
       {
         try {
           String relativePath = in.read_wstring ();
           String $result = null;
           $result = this.openPkg (relativePath);
           out = $rh.createReply();
           out.write_wstring ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.PackageInvalid $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.PackageInvalidHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.ExternalPackageInvalid $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.ExternalPackageInvalidHelper.write (out, $ex);
         }
         break;
       }

       case 9:  // WorkflowService/PackageAdministration/updatePackage1
       {
         try {
           String id = in.read_wstring ();
           String _relativePathToNewPackage = in.read_wstring ();
           this.updatePackage1 (id, _relativePathToNewPackage);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.PackageUpdateNotAllowed $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.PackageUpdateNotAllowedHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.PackageInvalid $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.PackageInvalidHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.ExternalPackageInvalid $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.ExternalPackageInvalidHelper.write (out, $ex);
         }
         break;
       }

       case 10:  // WorkflowService/PackageAdministration/closePkg
       {
         try {
           String id = in.read_wstring ();
           this.closePkg (id);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.PackageInUse $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.PackageInUseHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.PackageHasActiveProcesses $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.PackageHasActiveProcessesHelper.write (out, $ex);
         }
         break;
       }

       case 11:  // WorkflowService/PackageAdministration/closePkgWithVersion
       {
         try {
           String id = in.read_wstring ();
           String ver = in.read_wstring ();
           this.closePkgWithVersion (id, ver);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.PackageInUse $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.PackageInUseHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.PackageHasActiveProcesses $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.PackageHasActiveProcessesHelper.write (out, $ex);
         }
         break;
       }

       case 12:  // WorkflowService/PackageAdministration/isPackageReferenced
       {
         try {
           String pkgId = in.read_wstring ();
           boolean $result = false;
           $result = this.isPackageReferenced (pkgId);
           out = $rh.createReply();
           out.write_boolean ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 13:  // WorkflowService/PackageAdministration/synchronizeXPDLCache
       {
         try {
           this.synchronizeXPDLCache ();
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 14:  // WorkflowService/PackageAdministration/clearXPDLCache
       {
         try {
           this.clearXPDLCache ();
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 15:  // WorkflowService/PackageAdministration/refreshXPDLCache
       {
         try {
           this.refreshXPDLCache ();
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
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
    "IDL:WorkflowService/PackageAdministration:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }


} // class _PackageAdministrationImplBase
