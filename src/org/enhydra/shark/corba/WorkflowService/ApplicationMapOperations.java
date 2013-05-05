package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/ApplicationMapOperations.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分32秒 CST
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
