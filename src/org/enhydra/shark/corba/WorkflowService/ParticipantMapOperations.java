package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/ParticipantMapOperations.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowService.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��33�� CST
*/

public interface ParticipantMapOperations 
{
  void setParticipantId (String id);
  String getParticipantId ();
  void setPackageId (String id);
  String getPackageId ();
  void setProcessDefinitionId (String id);
  String getProcessDefinitionId ();
  void setUsername (String username);
  String getUsername ();
  boolean getIsGroupUser ();
  void setIsGroupUser (boolean isGroupUser);
} // interface ParticipantMapOperations
