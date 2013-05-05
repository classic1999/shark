package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/ParticipantMapOperations.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分33秒 CST
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
