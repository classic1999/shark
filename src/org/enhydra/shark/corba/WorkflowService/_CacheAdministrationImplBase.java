package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/_CacheAdministrationImplBase.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分32秒 CST
*/

public abstract class _CacheAdministrationImplBase extends org.omg.CORBA.portable.ObjectImpl
                implements org.enhydra.shark.corba.WorkflowService.CacheAdministration, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors
  public _CacheAdministrationImplBase ()
  {
  }

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("connect", new java.lang.Integer (0));
    _methods.put ("disconnect", new java.lang.Integer (1));
    _methods.put ("getProcessCacheSize", new java.lang.Integer (2));
    _methods.put ("setProcessCacheSize", new java.lang.Integer (3));
    _methods.put ("howManyCachedProcesses", new java.lang.Integer (4));
    _methods.put ("clearProcessCache", new java.lang.Integer (5));
    _methods.put ("getResourceCacheSize", new java.lang.Integer (6));
    _methods.put ("setResourceCacheSize", new java.lang.Integer (7));
    _methods.put ("howManyCachedResources", new java.lang.Integer (8));
    _methods.put ("clearResourceCache", new java.lang.Integer (9));
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
       case 0:  // WorkflowService/CacheAdministration/connect
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

       case 1:  // WorkflowService/CacheAdministration/disconnect
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

       case 2:  // WorkflowService/CacheAdministration/getProcessCacheSize
       {
         try {
           int $result = (int)0;
           $result = this.getProcessCacheSize ();
           out = $rh.createReply();
           out.write_long ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 3:  // WorkflowService/CacheAdministration/setProcessCacheSize
       {
         try {
           int size = in.read_long ();
           this.setProcessCacheSize (size);
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

       case 4:  // WorkflowService/CacheAdministration/howManyCachedProcesses
       {
         try {
           int $result = (int)0;
           $result = this.howManyCachedProcesses ();
           out = $rh.createReply();
           out.write_long ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 5:  // WorkflowService/CacheAdministration/clearProcessCache
       {
         try {
           this.clearProcessCache ();
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

       case 6:  // WorkflowService/CacheAdministration/getResourceCacheSize
       {
         try {
           int $result = (int)0;
           $result = this.getResourceCacheSize ();
           out = $rh.createReply();
           out.write_long ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 7:  // WorkflowService/CacheAdministration/setResourceCacheSize
       {
         try {
           int size = in.read_long ();
           this.setResourceCacheSize (size);
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

       case 8:  // WorkflowService/CacheAdministration/howManyCachedResources
       {
         try {
           int $result = (int)0;
           $result = this.howManyCachedResources ();
           out = $rh.createReply();
           out.write_long ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 9:  // WorkflowService/CacheAdministration/clearResourceCache
       {
         try {
           this.clearResourceCache ();
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
    "IDL:WorkflowService/CacheAdministration:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }


} // class _CacheAdministrationImplBase
