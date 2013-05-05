package org.enhydra.shark.corba.ExpressionBuilders;


/**
* org/enhydra/shark/corba/ExpressionBuilders/ResourceIteratorExpressionBuilderOperations.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 ExpressionBuilders.idl
* 2009年2月3日 星期二 下午05时32分30秒 CST
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
