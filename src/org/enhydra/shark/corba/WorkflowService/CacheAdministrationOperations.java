package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/CacheAdministrationOperations.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowService.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��32�� CST
*/

public interface CacheAdministrationOperations 
{
  void connect (String userId, String password, String engineName, String scope) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.ConnectFailed;
  void disconnect () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  int getProcessCacheSize () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  void setProcessCacheSize (int size) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  int howManyCachedProcesses () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  void clearProcessCache () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  int getResourceCacheSize () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  void setResourceCacheSize (int size) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  int howManyCachedResources () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  void clearResourceCache () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
} // interface CacheAdministrationOperations
