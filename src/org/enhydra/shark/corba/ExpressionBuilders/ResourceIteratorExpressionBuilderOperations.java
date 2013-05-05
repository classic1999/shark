package org.enhydra.shark.corba.ExpressionBuilders;


/**
* org/enhydra/shark/corba/ExpressionBuilders/ResourceIteratorExpressionBuilderOperations.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� ExpressionBuilders.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��30�� CST
*/

public interface ResourceIteratorExpressionBuilderOperations  extends org.enhydra.shark.corba.ExpressionBuilders.ExpressionBuilderOperations
{
  org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder and ();
  org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder or ();
  org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder not ();
  org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder addUsernameEquals (String un);
  org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder addAssignemtCountEquals (long cnt);
  org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder addAssignemtCountLessThan (long cnt);
  org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder addAssignemtCountGreaterThan (long cnt);
  org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder addExpressionStr (String exp);
  org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder addExpression (org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder eb);
  org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder setOrderByUsername (boolean ascending);
} // interface ResourceIteratorExpressionBuilderOperations
