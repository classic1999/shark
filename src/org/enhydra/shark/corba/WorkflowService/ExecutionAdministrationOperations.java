package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/ExecutionAdministrationOperations.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowService.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��33�� CST
*/

public interface ExecutionAdministrationOperations 
{
  void connect (String userId, String password, String engineName, String scope) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.ConnectFailed;
  void disconnect () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  void shutdown () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  void removeProcessRequester (String procId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.enhydra.shark.corba.WorkflowService.WfProcessMgrIterator get_iterator_processmgr () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.omg.WorkflowModel.WfProcessMgr[] get_sequence_processmgr (int max_number) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.omg.WfBase.NameValue[] getLoggedUsers () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.enhydra.shark.corba.WorkflowService.WfResourceIterator get_iterator_resource () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.omg.WorkflowModel.WfResource[] get_sequence_resource (int max_number) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  void startActivity (String procId, String blockActIdString, String actDefId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.omg.WorkflowModel.WfProcessMgr getProcessMgrByName (String name) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.omg.WorkflowModel.WfProcessMgr getProcessMgrByXPDLDefinition (String pkgId, String pDefId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.omg.WfBase.NameValueInfo[] getProcessMgrInputSignatureByMgrName (String name) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.omg.WfBase.NameValueInfo[] getProcessMgrInputSignatureByXPDLDefinition (String pkgId, String pDefId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.omg.WfBase.NameValueInfo[] getProcessMgrInputSignatureByXPDLDefinitionWithVersion (String pkgId, String pkgVer, String pDefId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.omg.WorkflowModel.WfResource getResource (String username) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.omg.WorkflowModel.WfProcess getProcess (String procId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.omg.WorkflowModel.WfActivity getActivity (String procId, String actId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.omg.WorkflowModel.WfAssignment getAssignment (String procId, String actId, String username) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.omg.WorkflowModel.WfAssignment getAssignmentById (String procId, String assId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  void reevaluateAssignments () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  void reevaluateAssignmentsForPkg (String pkgId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  void reevaluateAssignmentsForProcessDefinition (String pkgId, String pDefId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  void reevaluateAssignmentsForActivityDefinition (String pkgId, String pDefId, String aDefId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.omg.WorkflowModel.WfCreateProcessEventAudit getCreateProcessHistory (String procId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.omg.WorkflowModel.WfDataEventAudit[] getProcessSequenceDataHistory (String procId, int max_number) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.omg.WorkflowModel.WfStateEventAudit[] getProcessSequenceStateHistory (String procId, int max_number) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.omg.WorkflowModel.WfDataEventAudit[] getActivitySequenceDataHistory (String procId, String actId, int max_number) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.omg.WorkflowModel.WfStateEventAudit[] getActivitySequenceStateHistory (String procId, String actId, int max_number) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.omg.WorkflowModel.WfAssignmentEventAudit[] getSequenceAssignmentHistory (String procId, String actId, int max_number) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  void deleteClosedProcesses () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  void deleteClosedProcessesForPkg (String pkgId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  void deleteClosedProcessesForMgr (String mgrName) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  void deleteClosedProcessesForPkgWithVersion (String pkgId, String pkgVer) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  void deleteClosedProcessesForProcessDefinition (String pkgId, String pDefId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  void deleteClosedProcess (String procId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  String[] deleteClosedProcessesMultiTrans (int instancesPerTransaction, int failuresToIgnore) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  String[] deleteClosedProcessesForMgrMultiTrans (String mgrName, int instancesPerTransaction, int failuresToIgnore) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.omg.WfBase.NameValue[] getProcessContext (String processId, String[] variablesDefIds) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.omg.WfBase.NameValue[] getActivityContext (String processId, String actId, String[] variablesDefIds) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.omg.WorkflowModel.WfAssignmentIterator get_iterator_assignment () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.omg.WorkflowModel.WfProcessIterator get_iterator_process () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.omg.WorkflowModel.WfActivityIterator get_iterator_activity () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  void doneWith (org.omg.CORBA.Object toDisconnect);
} // interface ExecutionAdministrationOperations
