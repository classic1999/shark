package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/SharkConnectionOperations.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowService.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��33�� CST
*/

public interface SharkConnectionOperations 
{
  void connect (String userId, String password, String engineName, String scope) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.ConnectFailed;
  void disconnect () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.omg.WorkflowModel.WfResource getResourceObject () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.omg.WorkflowModel.WfProcess createProcess (String pkgId, String pDefId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected, org.omg.WorkflowModel.NotEnabled;
  org.omg.WorkflowModel.WfProcess createProcessWithRequester (org.omg.WorkflowModel.WfRequester requester, String pkgId, String pDefId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected, org.omg.WorkflowModel.NotEnabled, org.omg.WorkflowModel.InvalidRequester, org.omg.WorkflowModel.RequesterRequired;
  void doneWith (org.omg.CORBA.Object toDisconnect);
} // interface SharkConnectionOperations
