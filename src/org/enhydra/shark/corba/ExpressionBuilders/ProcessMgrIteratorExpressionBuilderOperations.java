package org.enhydra.shark.corba.ExpressionBuilders;


/**
* org/enhydra/shark/corba/ExpressionBuilders/ProcessMgrIteratorExpressionBuilderOperations.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� ExpressionBuilders.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��30�� CST
*/

public interface ProcessMgrIteratorExpressionBuilderOperations  extends org.enhydra.shark.corba.ExpressionBuilders.ExpressionBuilderOperations
{
  org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder and ();
  org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder or ();
  org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder not ();
  org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder addPackageIdEquals (String exp);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder addProcessDefIdEquals (String exp);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder addNameEquals (String exp);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder addVersionEquals (String exp);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder addCreatedTimeEquals (long arg);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder addCreatedTimeBefore (long arg);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder addCreatedTimeAfter (long arg);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder addIsEnabled ();
  org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder addExpressionStr (String exp);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder addExpression (org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder eb);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder setOrderByPackageId (boolean ascending);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder setOrderByProcessDefId (boolean ascending);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder setOrderByName (boolean ascending);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder setOrderByVersion (boolean ascending);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder setOrderByCreatedTime (boolean ascending);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder setOrderByEnabled (boolean ascending);
} // interface ProcessMgrIteratorExpressionBuilderOperations
