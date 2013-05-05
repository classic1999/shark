package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/SharkInterfaceOperations.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分33秒 CST
*/

public interface SharkInterfaceOperations 
{
  org.enhydra.shark.corba.WorkflowService.SharkConnection getSharkConnection ();
  org.enhydra.shark.corba.WorkflowService.RepositoryMgr getRepositoryManager ();
  org.enhydra.shark.corba.WorkflowService.PackageAdministration getPackageAdministration ();
  org.enhydra.shark.corba.WorkflowService.UserGroupAdministration getUserGroupAdministration ();
  org.enhydra.shark.corba.WorkflowService.ExecutionAdministration getExecutionAdministration ();
  org.enhydra.shark.corba.WorkflowService.MappingAdministration getMappingAdministration ();
  org.enhydra.shark.corba.WorkflowService.AdminMisc getAdminMisc ();
  org.enhydra.shark.corba.WorkflowService.CacheAdministration getCacheAdministration ();
  org.enhydra.shark.corba.WorkflowService.DeadlineAdministration getDeadlineAdministration ();
  org.enhydra.shark.corba.WorkflowService.LimitAdministration getLimitAdministration ();
  org.enhydra.shark.corba.WorkflowService.ExpressionBuilderManager getExpressionBuilderManager ();
  org.omg.WfBase.NameValueInfo[] getProperties ();
  void doneWith (org.omg.CORBA.Object toDisconnect);
} // interface SharkInterfaceOperations
