package org.enhydra.shark.corba.ExpressionBuilders;

/**
* org/enhydra/shark/corba/ExpressionBuilders/ActivityIteratorExpressionBuilderHolder.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 ExpressionBuilders.idl
* 2009年2月3日 星期二 下午05时32分30秒 CST
*/

public final class ActivityIteratorExpressionBuilderHolder implements org.omg.CORBA.portable.Streamable
{
  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder value = null;

  public ActivityIteratorExpressionBuilderHolder ()
  {
  }

  public ActivityIteratorExpressionBuilderHolder (org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.type ();
  }

}
