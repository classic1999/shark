package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/SharkInterfaceOperations.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowService.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��33�� CST
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
