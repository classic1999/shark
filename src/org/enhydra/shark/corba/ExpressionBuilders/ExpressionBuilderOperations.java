package org.enhydra.shark.corba.ExpressionBuilders;


/**
* org/enhydra/shark/corba/ExpressionBuilders/ExpressionBuilderOperations.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 ExpressionBuilders.idl
* 2009年2月3日 星期二 下午05时32分30秒 CST
*/

public interface ExpressionBuilderOperations 
{
  boolean isComplete ();
  String toSQL ();
  String toScript ();
  String toExpression ();
  org.omg.CORBA.Any getTheImpl ();
} // interface ExpressionBuilderOperations
