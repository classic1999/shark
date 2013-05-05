package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/_ExecutionAdministrationImplBase.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分32秒 CST
*/

public abstract class _ExecutionAdministrationImplBase extends org.omg.CORBA.portable.ObjectImpl
                implements org.enhydra.shark.corba.WorkflowService.ExecutionAdministration, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors
  public _ExecutionAdministrationImplBase ()
  {
  }

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("connect", new java.lang.Integer (0));
    _methods.put ("disconnect", new java.lang.Integer (1));
    _methods.put ("shutdown", new java.lang.Integer (2));
    _methods.put ("removeProcessRequester", new java.lang.Integer (3));
    _methods.put ("get_iterator_processmgr", new java.lang.Integer (4));
    _methods.put ("get_sequence_processmgr", new java.lang.Integer (5));
    _methods.put ("getLoggedUsers", new java.lang.Integer (6));
    _methods.put ("get_iterator_resource", new java.lang.Integer (7));
    _methods.put ("get_sequence_resource", new java.lang.Integer (8));
    _methods.put ("startActivity", new java.lang.Integer (9));
    _methods.put ("getProcessMgrByName", new java.lang.Integer (10));
    _methods.put ("getProcessMgrByXPDLDefinition", new java.lang.Integer (11));
    _methods.put ("getProcessMgrInputSignatureByMgrName", new java.lang.Integer (12));
    _methods.put ("getProcessMgrInputSignatureByXPDLDefinition", new java.lang.Integer (13));
    _methods.put ("getProcessMgrInputSignatureByXPDLDefinitionWithVersion", new java.lang.Integer (14));
    _methods.put ("getResource", new java.lang.Integer (15));
    _methods.put ("getProcess", new java.lang.Integer (16));
    _methods.put ("getActivity", new java.lang.Integer (17));
    _methods.put ("getAssignment", new java.lang.Integer (18));
    _methods.put ("getAssignmentById", new java.lang.Integer (19));
    _methods.put ("reevaluateAssignments", new java.lang.Integer (20));
    _methods.put ("reevaluateAssignmentsForPkg", new java.lang.Integer (21));
    _methods.put ("reevaluateAssignmentsForProcessDefinition", new java.lang.Integer (22));
    _methods.put ("reevaluateAssignmentsForActivityDefinition", new java.lang.Integer (23));
    _methods.put ("getCreateProcessHistory", new java.lang.Integer (24));
    _methods.put ("getProcessSequenceDataHistory", new java.lang.Integer (25));
    _methods.put ("getProcessSequenceStateHistory", new java.lang.Integer (26));
    _methods.put ("getActivitySequenceDataHistory", new java.lang.Integer (27));
    _methods.put ("getActivitySequenceStateHistory", new java.lang.Integer (28));
    _methods.put ("getSequenceAssignmentHistory", new java.lang.Integer (29));
    _methods.put ("deleteClosedProcesses", new java.lang.Integer (30));
    _methods.put ("deleteClosedProcessesForPkg", new java.lang.Integer (31));
    _methods.put ("deleteClosedProcessesForMgr", new java.lang.Integer (32));
    _methods.put ("deleteClosedProcessesForPkgWithVersion", new java.lang.Integer (33));
    _methods.put ("deleteClosedProcessesForProcessDefinition", new java.lang.Integer (34));
    _methods.put ("deleteClosedProcess", new java.lang.Integer (35));
    _methods.put ("deleteClosedProcessesMultiTrans", new java.lang.Integer (36));
    _methods.put ("deleteClosedProcessesForMgrMultiTrans", new java.lang.Integer (37));
    _methods.put ("getProcessContext", new java.lang.Integer (38));
    _methods.put ("getActivityContext", new java.lang.Integer (39));
    _methods.put ("get_iterator_assignment", new java.lang.Integer (40));
    _methods.put ("get_iterator_process", new java.lang.Integer (41));
    _methods.put ("get_iterator_activity", new java.lang.Integer (42));
    _methods.put ("doneWith", new java.lang.Integer (43));
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
       case 0:  // WorkflowService/ExecutionAdministration/connect
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

       case 1:  // WorkflowService/ExecutionAdministration/disconnect
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

       case 2:  // WorkflowService/ExecutionAdministration/shutdown
       {
         try {
           this.shutdown ();
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

       case 3:  // WorkflowService/ExecutionAdministration/removeProcessRequester
       {
         try {
           String procId = in.read_wstring ();
           this.removeProcessRequester (procId);
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

       case 4:  // WorkflowService/ExecutionAdministration/get_iterator_processmgr
       {
         try {
           org.enhydra.shark.corba.WorkflowService.WfProcessMgrIterator $result = null;
           $result = this.get_iterator_processmgr ();
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.WfProcessMgrIteratorHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 5:  // WorkflowService/ExecutionAdministration/get_sequence_processmgr
       {
         try {
           int max_number = in.read_long ();
           org.omg.WorkflowModel.WfProcessMgr $result[] = null;
           $result = this.get_sequence_processmgr (max_number);
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.WfProcessMgrSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 6:  // WorkflowService/ExecutionAdministration/getLoggedUsers
       {
         try {
           org.omg.WfBase.NameValue $result[] = null;
           $result = this.getLoggedUsers ();
           out = $rh.createReply();
           org.omg.WfBase.NameValueSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 7:  // WorkflowService/ExecutionAdministration/get_iterator_resource
       {
         try {
           org.enhydra.shark.corba.WorkflowService.WfResourceIterator $result = null;
           $result = this.get_iterator_resource ();
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.WfResourceIteratorHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 8:  // WorkflowService/ExecutionAdministration/get_sequence_resource
       {
         try {
           int max_number = in.read_long ();
           org.omg.WorkflowModel.WfResource $result[] = null;
           $result = this.get_sequence_resource (max_number);
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.WfResourceSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 9:  // WorkflowService/ExecutionAdministration/startActivity
       {
         try {
           String procId = in.read_wstring ();
           String blockActIdString = in.read_wstring ();
           String actDefId = in.read_wstring ();
           this.startActivity (procId, blockActIdString, actDefId);
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

       case 10:  // WorkflowService/ExecutionAdministration/getProcessMgrByName
       {
         try {
           String name = in.read_wstring ();
           org.omg.WorkflowModel.WfProcessMgr $result = null;
           $result = this.getProcessMgrByName (name);
           out = $rh.createReply();
           org.omg.WorkflowModel.WfProcessMgrHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 11:  // WorkflowService/ExecutionAdministration/getProcessMgrByXPDLDefinition
       {
         try {
           String pkgId = in.read_wstring ();
           String pDefId = in.read_wstring ();
           org.omg.WorkflowModel.WfProcessMgr $result = null;
           $result = this.getProcessMgrByXPDLDefinition (pkgId, pDefId);
           out = $rh.createReply();
           org.omg.WorkflowModel.WfProcessMgrHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 12:  // WorkflowService/ExecutionAdministration/getProcessMgrInputSignatureByMgrName
       {
         try {
           String name = in.read_wstring ();
           org.omg.WfBase.NameValueInfo $result[] = null;
           $result = this.getProcessMgrInputSignatureByMgrName (name);
           out = $rh.createReply();
           org.omg.WfBase.NameValueInfoSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 13:  // WorkflowService/ExecutionAdministration/getProcessMgrInputSignatureByXPDLDefinition
       {
         try {
           String pkgId = in.read_wstring ();
           String pDefId = in.read_wstring ();
           org.omg.WfBase.NameValueInfo $result[] = null;
           $result = this.getProcessMgrInputSignatureByXPDLDefinition (pkgId, pDefId);
           out = $rh.createReply();
           org.omg.WfBase.NameValueInfoSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 14:  // WorkflowService/ExecutionAdministration/getProcessMgrInputSignatureByXPDLDefinitionWithVersion
       {
         try {
           String pkgId = in.read_wstring ();
           String pkgVer = in.read_wstring ();
           String pDefId = in.read_wstring ();
           org.omg.WfBase.NameValueInfo $result[] = null;
           $result = this.getProcessMgrInputSignatureByXPDLDefinitionWithVersion (pkgId, pkgVer, pDefId);
           out = $rh.createReply();
           org.omg.WfBase.NameValueInfoSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 15:  // WorkflowService/ExecutionAdministration/getResource
       {
         try {
           String username = in.read_wstring ();
           org.omg.WorkflowModel.WfResource $result = null;
           $result = this.getResource (username);
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

       case 16:  // WorkflowService/ExecutionAdministration/getProcess
       {
         try {
           String procId = in.read_wstring ();
           org.omg.WorkflowModel.WfProcess $result = null;
           $result = this.getProcess (procId);
           out = $rh.createReply();
           org.omg.WorkflowModel.WfProcessHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 17:  // WorkflowService/ExecutionAdministration/getActivity
       {
         try {
           String procId = in.read_wstring ();
           String actId = in.read_wstring ();
           org.omg.WorkflowModel.WfActivity $result = null;
           $result = this.getActivity (procId, actId);
           out = $rh.createReply();
           org.omg.WorkflowModel.WfActivityHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 18:  // WorkflowService/ExecutionAdministration/getAssignment
       {
         try {
           String procId = in.read_wstring ();
           String actId = in.read_wstring ();
           String username = in.read_wstring ();
           org.omg.WorkflowModel.WfAssignment $result = null;
           $result = this.getAssignment (procId, actId, username);
           out = $rh.createReply();
           org.omg.WorkflowModel.WfAssignmentHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 19:  // WorkflowService/ExecutionAdministration/getAssignmentById
       {
         try {
           String procId = in.read_wstring ();
           String assId = in.read_wstring ();
           org.omg.WorkflowModel.WfAssignment $result = null;
           $result = this.getAssignmentById (procId, assId);
           out = $rh.createReply();
           org.omg.WorkflowModel.WfAssignmentHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 20:  // WorkflowService/ExecutionAdministration/reevaluateAssignments
       {
         try {
           this.reevaluateAssignments ();
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

       case 21:  // WorkflowService/ExecutionAdministration/reevaluateAssignmentsForPkg
       {
         try {
           String pkgId = in.read_wstring ();
           this.reevaluateAssignmentsForPkg (pkgId);
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

       case 22:  // WorkflowService/ExecutionAdministration/reevaluateAssignmentsForProcessDefinition
       {
         try {
           String pkgId = in.read_wstring ();
           String pDefId = in.read_wstring ();
           this.reevaluateAssignmentsForProcessDefinition (pkgId, pDefId);
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

       case 23:  // WorkflowService/ExecutionAdministration/reevaluateAssignmentsForActivityDefinition
       {
         try {
           String pkgId = in.read_wstring ();
           String pDefId = in.read_wstring ();
           String aDefId = in.read_wstring ();
           this.reevaluateAssignmentsForActivityDefinition (pkgId, pDefId, aDefId);
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

       case 24:  // WorkflowService/ExecutionAdministration/getCreateProcessHistory
       {
         try {
           String procId = in.read_wstring ();
           org.omg.WorkflowModel.WfCreateProcessEventAudit $result = null;
           $result = this.getCreateProcessHistory (procId);
           out = $rh.createReply();
           org.omg.WorkflowModel.WfCreateProcessEventAuditHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 25:  // WorkflowService/ExecutionAdministration/getProcessSequenceDataHistory
       {
         try {
           String procId = in.read_wstring ();
           int max_number = in.read_long ();
           org.omg.WorkflowModel.WfDataEventAudit $result[] = null;
           $result = this.getProcessSequenceDataHistory (procId, max_number);
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.WfDataEventAuditSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 26:  // WorkflowService/ExecutionAdministration/getProcessSequenceStateHistory
       {
         try {
           String procId = in.read_wstring ();
           int max_number = in.read_long ();
           org.omg.WorkflowModel.WfStateEventAudit $result[] = null;
           $result = this.getProcessSequenceStateHistory (procId, max_number);
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.WfStateEventAuditSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 27:  // WorkflowService/ExecutionAdministration/getActivitySequenceDataHistory
       {
         try {
           String procId = in.read_wstring ();
           String actId = in.read_wstring ();
           int max_number = in.read_long ();
           org.omg.WorkflowModel.WfDataEventAudit $result[] = null;
           $result = this.getActivitySequenceDataHistory (procId, actId, max_number);
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.WfDataEventAuditSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 28:  // WorkflowService/ExecutionAdministration/getActivitySequenceStateHistory
       {
         try {
           String procId = in.read_wstring ();
           String actId = in.read_wstring ();
           int max_number = in.read_long ();
           org.omg.WorkflowModel.WfStateEventAudit $result[] = null;
           $result = this.getActivitySequenceStateHistory (procId, actId, max_number);
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.WfStateEventAuditSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 29:  // WorkflowService/ExecutionAdministration/getSequenceAssignmentHistory
       {
         try {
           String procId = in.read_wstring ();
           String actId = in.read_wstring ();
           int max_number = in.read_long ();
           org.omg.WorkflowModel.WfAssignmentEventAudit $result[] = null;
           $result = this.getSequenceAssignmentHistory (procId, actId, max_number);
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.WfAssignmentEventAuditSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 30:  // WorkflowService/ExecutionAdministration/deleteClosedProcesses
       {
         try {
           this.deleteClosedProcesses ();
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

       case 31:  // WorkflowService/ExecutionAdministration/deleteClosedProcessesForPkg
       {
         try {
           String pkgId = in.read_wstring ();
           this.deleteClosedProcessesForPkg (pkgId);
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

       case 32:  // WorkflowService/ExecutionAdministration/deleteClosedProcessesForMgr
       {
         try {
           String mgrName = in.read_wstring ();
           this.deleteClosedProcessesForMgr (mgrName);
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

       case 33:  // WorkflowService/ExecutionAdministration/deleteClosedProcessesForPkgWithVersion
       {
         try {
           String pkgId = in.read_wstring ();
           String pkgVer = in.read_wstring ();
           this.deleteClosedProcessesForPkgWithVersion (pkgId, pkgVer);
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

       case 34:  // WorkflowService/ExecutionAdministration/deleteClosedProcessesForProcessDefinition
       {
         try {
           String pkgId = in.read_wstring ();
           String pDefId = in.read_wstring ();
           this.deleteClosedProcessesForProcessDefinition (pkgId, pDefId);
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

       case 35:  // WorkflowService/ExecutionAdministration/deleteClosedProcess
       {
         try {
           String procId = in.read_wstring ();
           this.deleteClosedProcess (procId);
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

       case 36:  // WorkflowService/ExecutionAdministration/deleteClosedProcessesMultiTrans
       {
         try {
           int instancesPerTransaction = in.read_long ();
           int failuresToIgnore = in.read_long ();
           String $result[] = null;
           $result = this.deleteClosedProcessesMultiTrans (instancesPerTransaction, failuresToIgnore);
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

       case 37:  // WorkflowService/ExecutionAdministration/deleteClosedProcessesForMgrMultiTrans
       {
         try {
           String mgrName = in.read_wstring ();
           int instancesPerTransaction = in.read_long ();
           int failuresToIgnore = in.read_long ();
           String $result[] = null;
           $result = this.deleteClosedProcessesForMgrMultiTrans (mgrName, instancesPerTransaction, failuresToIgnore);
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

       case 38:  // WorkflowService/ExecutionAdministration/getProcessContext
       {
         try {
           String processId = in.read_wstring ();
           String variablesDefIds[] = org.enhydra.shark.corba.WorkflowService.WStringSequenceHelper.read (in);
           org.omg.WfBase.NameValue $result[] = null;
           $result = this.getProcessContext (processId, variablesDefIds);
           out = $rh.createReply();
           org.omg.WfBase.NameValueSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 39:  // WorkflowService/ExecutionAdministration/getActivityContext
       {
         try {
           String processId = in.read_wstring ();
           String actId = in.read_wstring ();
           String variablesDefIds[] = org.enhydra.shark.corba.WorkflowService.WStringSequenceHelper.read (in);
           org.omg.WfBase.NameValue $result[] = null;
           $result = this.getActivityContext (processId, actId, variablesDefIds);
           out = $rh.createReply();
           org.omg.WfBase.NameValueSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 40:  // WorkflowService/ExecutionAdministration/get_iterator_assignment
       {
         try {
           org.omg.WorkflowModel.WfAssignmentIterator $result = null;
           $result = this.get_iterator_assignment ();
           out = $rh.createReply();
           org.omg.WorkflowModel.WfAssignmentIteratorHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 41:  // WorkflowService/ExecutionAdministration/get_iterator_process
       {
         try {
           org.omg.WorkflowModel.WfProcessIterator $result = null;
           $result = this.get_iterator_process ();
           out = $rh.createReply();
           org.omg.WorkflowModel.WfProcessIteratorHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 42:  // WorkflowService/ExecutionAdministration/get_iterator_activity
       {
         try {
           org.omg.WorkflowModel.WfActivityIterator $result = null;
           $result = this.get_iterator_activity ();
           out = $rh.createReply();
           org.omg.WorkflowModel.WfActivityIteratorHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 43:  // WorkflowService/ExecutionAdministration/doneWith
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
    "IDL:WorkflowService/ExecutionAdministration:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }


} // class _ExecutionAdministrationImplBase
