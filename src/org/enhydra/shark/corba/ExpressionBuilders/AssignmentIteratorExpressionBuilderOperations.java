package org.enhydra.shark.corba.ExpressionBuilders;


/**
* org/enhydra/shark/corba/ExpressionBuilders/AssignmentIteratorExpressionBuilderOperations.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� ExpressionBuilders.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��30�� CST
*/

public interface AssignmentIteratorExpressionBuilderOperations  extends org.enhydra.shark.corba.ExpressionBuilders.ExpressionBuilderOperations
{
  org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder and ();
  org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder or ();
  org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder not ();
  org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder addUsernameEquals (String un);
  org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder addProcessIdEquals (String un);
  org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder addIsAccepted ();
  org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder addPackageIdEquals (String un);
  org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder addPackageVersionEquals (String un);
  org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder addProcessDefIdEquals (String un);
  org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder addActivitySetDefIdEquals (String un);
  org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder addActivityDefIdEquals (String un);
  org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder addActivityIdEquals (String un);
  org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder setOrderByUsername (boolean ascending);
  org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder setOrderByProcessId (boolean ascending);
  org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder setOrderByCreatedTime (boolean ascending);
  org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder setOrderByAccepted (boolean ascending);
} // interface AssignmentIteratorExpressionBuilderOperations
