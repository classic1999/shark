package org.enhydra.shark.corba.ExpressionBuilders;

/**
* org/enhydra/shark/corba/ExpressionBuilders/ProcessMgrIteratorExpressionBuilderHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� ExpressionBuilders.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��30�� CST
*/

public final class ProcessMgrIteratorExpressionBuilderHolder implements org.omg.CORBA.portable.Streamable
{
  public org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder value = null;

  public ProcessMgrIteratorExpressionBuilderHolder ()
  {
  }

  public ProcessMgrIteratorExpressionBuilderHolder (org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.type ();
  }

}
