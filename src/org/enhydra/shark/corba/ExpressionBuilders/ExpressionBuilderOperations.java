package org.enhydra.shark.corba.ExpressionBuilders;


/**
* org/enhydra/shark/corba/ExpressionBuilders/ExpressionBuilderOperations.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� ExpressionBuilders.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��30�� CST
*/

public interface ExpressionBuilderOperations 
{
  boolean isComplete ();
  String toSQL ();
  String toScript ();
  String toExpression ();
  org.omg.CORBA.Any getTheImpl ();
} // interface ExpressionBuilderOperations
