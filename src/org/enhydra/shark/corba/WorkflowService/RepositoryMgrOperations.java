package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/RepositoryMgrOperations.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowService.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��33�� CST
*/

public interface RepositoryMgrOperations 
{
  void connect (String userId, String password, String engineName, String scope) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.ConnectFailed;
  void disconnect () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  String[] getPackagePaths () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.omg.WfBase.NameValue[] getPackagePathToIdMapping () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  String getPackageId (String relativePath) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  void uploadPkg (byte[] pkg, String relativePath) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected, org.enhydra.shark.corba.WorkflowService.RepositoryInvalid;
  void deletePkg (String relativePath) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected, org.enhydra.shark.corba.WorkflowService.RepositoryInvalid;
} // interface RepositoryMgrOperations
