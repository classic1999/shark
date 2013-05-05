package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/ExpressionBuilderManagerOperations.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowService.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��33�� CST
*/

public interface ExpressionBuilderManagerOperations 
{
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder getActivityIteratorExpressionBuilder ();
  org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder getAssignmentIteratorExpressionBuilder ();
  org.enhydra.shark.corba.ExpressionBuilders.EventAuditIteratorExpressionBuilder getEventAuditIteratorExpressionBuilder ();
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder getProcessIteratorExpressionBuilder ();
  org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder getProcessMgrIteratorExpressionBuilder ();
  org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder getResourceIteratorExpressionBuilder ();
} // interface ExpressionBuilderManagerOperations
