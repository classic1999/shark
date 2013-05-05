package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/SharkConnectionOperations.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分33秒 CST
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
