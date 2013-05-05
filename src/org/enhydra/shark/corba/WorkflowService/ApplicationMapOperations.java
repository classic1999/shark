package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/ApplicationMapOperations.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowService.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��32�� CST
*/

public interface ApplicationMapOperations 
{
  void setApplicationDefinitionId (String id);
  String getApplicationDefinitionId ();
  void setPackageId (String id);
  String getPackageId ();
  void setProcessDefinitionId (String id);
  String getProcessDefinitionId ();
  void setToolAgentClassName (String name);
  String getToolAgentClassName ();
  void setUsername (String username);
  String getUsername ();
  void setPassword (String password);
  String getPassword ();
  void setApplicationName (String name);
  String getApplicationName ();
  void setApplicationMode (int mode);
  int getApplicationMode ();
} // interface ApplicationMapOperations
