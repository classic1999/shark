package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/DeadlineAdministrationOperations.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowService.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��32�� CST
*/

public interface DeadlineAdministrationOperations 
{
  void connect (String userId, String password, String engineName, String scope) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.ConnectFailed;
  void disconnect () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  void checkDeadlines () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  void checkDeadlinesForProcesses (String[] procIds) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  void checkProcessDeadlines (String procId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  void checkActivityDeadline (String procId, String actId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  String[] checkDeadlinesMultiTrans (int instancesPerTransaction, int failuresToIgnore) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.enhydra.shark.corba.WorkflowService.DeadlineInfo[] getDeadlineInfoForProcess (String procId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.enhydra.shark.corba.WorkflowService.DeadlineInfo[] getDeadlineInfoForActivity (String procId, String actId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
} // interface DeadlineAdministrationOperations
