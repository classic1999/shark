package org.enhydra.shark.corba.ExpressionBuilders;

/**
* org/enhydra/shark/corba/ExpressionBuilders/ProcessIteratorExpressionBuilderHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� ExpressionBuilders.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��30�� CST
*/

public final class ProcessIteratorExpressionBuilderHolder implements org.omg.CORBA.portable.Streamable
{
  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder value = null;

  public ProcessIteratorExpressionBuilderHolder ()
  {
  }

  public ProcessIteratorExpressionBuilderHolder (org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.type ();
  }

}
