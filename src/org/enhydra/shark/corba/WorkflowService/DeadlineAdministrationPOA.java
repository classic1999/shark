package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/DeadlineAdministrationPOA.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分31秒 CST
*/

public abstract class DeadlineAdministrationPOA extends org.omg.PortableServer.Servant
 implements org.enhydra.shark.corba.WorkflowService.DeadlineAdministrationOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("connect", new java.lang.Integer (0));
    _methods.put ("disconnect", new java.lang.Integer (1));
    _methods.put ("checkDeadlines", new java.lang.Integer (2));
    _methods.put ("checkDeadlinesForProcesses", new java.lang.Integer (3));
    _methods.put ("checkProcessDeadlines", new java.lang.Integer (4));
    _methods.put ("checkActivityDeadline", new java.lang.Integer (5));
    _methods.put ("checkDeadlinesMultiTrans", new java.lang.Integer (6));
    _methods.put ("getDeadlineInfoForProcess", new java.lang.Integer (7));
    _methods.put ("getDeadlineInfoForActivity", new java.lang.Integer (8));
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
       case 0:  // WorkflowService/DeadlineAdministration/connect
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

       case 1:  // WorkflowService/DeadlineAdministration/disconnect
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

       case 2:  // WorkflowService/DeadlineAdministration/checkDeadlines
       {
         try {
           this.checkDeadlines ();
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

       case 3:  // WorkflowService/DeadlineAdministration/checkDeadlinesForProcesses
       {
         try {
           String procIds[] = org.enhydra.shark.corba.WorkflowService.WStringSequenceHelper.read (in);
           this.checkDeadlinesForProcesses (procIds);
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

       case 4:  // WorkflowService/DeadlineAdministration/checkProcessDeadlines
       {
         try {
           String procId = in.read_wstring ();
           this.checkProcessDeadlines (procId);
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

       case 5:  // WorkflowService/DeadlineAdministration/checkActivityDeadline
       {
         try {
           String procId = in.read_wstring ();
           String actId = in.read_wstring ();
           this.checkActivityDeadline (procId, actId);
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

       case 6:  // WorkflowService/DeadlineAdministration/checkDeadlinesMultiTrans
       {
         try {
           int instancesPerTransaction = in.read_long ();
           int failuresToIgnore = in.read_long ();
           String $result[] = null;
           $result = this.checkDeadlinesMultiTrans (instancesPerTransaction, failuresToIgnore);
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

       case 7:  // WorkflowService/DeadlineAdministration/getDeadlineInfoForProcess
       {
         try {
           String procId = in.read_wstring ();
           org.enhydra.shark.corba.WorkflowService.DeadlineInfo $result[] = null;
           $result = this.getDeadlineInfoForProcess (procId);
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.DeadlineInfoSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 8:  // WorkflowService/DeadlineAdministration/getDeadlineInfoForActivity
       {
         try {
           String procId = in.read_wstring ();
           String actId = in.read_wstring ();
           org.enhydra.shark.corba.WorkflowService.DeadlineInfo $result[] = null;
           $result = this.getDeadlineInfoForActivity (procId, actId);
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.DeadlineInfoSequenceHelper.write (out, $result);
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
    "IDL:WorkflowService/DeadlineAdministration:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public DeadlineAdministration _this() 
  {
    return DeadlineAdministrationHelper.narrow(
    super._this_object());
  }

  public DeadlineAdministration _this(org.omg.CORBA.ORB orb) 
  {
    return DeadlineAdministrationHelper.narrow(
    super._this_object(orb));
  }


} // class DeadlineAdministrationPOA
