package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/_SharkConnectionImplBase.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分33秒 CST
*/

public abstract class _SharkConnectionImplBase extends org.omg.CORBA.portable.ObjectImpl
                implements org.enhydra.shark.corba.WorkflowService.SharkConnection, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors
  public _SharkConnectionImplBase ()
  {
  }

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("connect", new java.lang.Integer (0));
    _methods.put ("disconnect", new java.lang.Integer (1));
    _methods.put ("getResourceObject", new java.lang.Integer (2));
    _methods.put ("createProcess", new java.lang.Integer (3));
    _methods.put ("createProcessWithRequester", new java.lang.Integer (4));
    _methods.put ("doneWith", new java.lang.Integer (5));
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
       case 0:  // WorkflowService/SharkConnection/connect
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

       case 1:  // WorkflowService/SharkConnection/disconnect
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

       case 2:  // WorkflowService/SharkConnection/getResourceObject
       {
         try {
           org.omg.WorkflowModel.WfResource $result = null;
           $result = this.getResourceObject ();
           out = $rh.createReply();
           org.omg.WorkflowModel.WfResourceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 3:  // WorkflowService/SharkConnection/createProcess
       {
         try {
           String pkgId = in.read_wstring ();
           String pDefId = in.read_wstring ();
           org.omg.WorkflowModel.WfProcess $result = null;
           $result = this.createProcess (pkgId, pDefId);
           out = $rh.createReply();
           org.omg.WorkflowModel.WfProcessHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         } catch (org.omg.WorkflowModel.NotEnabled $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WorkflowModel.NotEnabledHelper.write (out, $ex);
         }
         break;
       }

       case 4:  // WorkflowService/SharkConnection/createProcessWithRequester
       {
         try {
           org.omg.WorkflowModel.WfRequester requester = org.omg.WorkflowModel.WfRequesterHelper.read (in);
           String pkgId = in.read_wstring ();
           String pDefId = in.read_wstring ();
           org.omg.WorkflowModel.WfProcess $result = null;
           $result = this.createProcessWithRequester (requester, pkgId, pDefId);
           out = $rh.createReply();
           org.omg.WorkflowModel.WfProcessHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         } catch (org.omg.WorkflowModel.NotEnabled $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WorkflowModel.NotEnabledHelper.write (out, $ex);
         } catch (org.omg.WorkflowModel.InvalidRequester $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WorkflowModel.InvalidRequesterHelper.write (out, $ex);
         } catch (org.omg.WorkflowModel.RequesterRequired $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WorkflowModel.RequesterRequiredHelper.write (out, $ex);
         }
         break;
       }

       case 5:  // WorkflowService/SharkConnection/doneWith
       {
         org.omg.CORBA.Object toDisconnect = org.omg.CORBA.ObjectHelper.read (in);
         this.doneWith (toDisconnect);
         out = $rh.createReply();
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:WorkflowService/SharkConnection:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }


} // class _SharkConnectionImplBase
