package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/NotEnabledHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class NotEnabledHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.NotEnabled value = null;

  public NotEnabledHolder ()
  {
  }

  public NotEnabledHolder (org.omg.WorkflowModel.NotEnabled initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.NotEnabledHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.NotEnabledHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.NotEnabledHelper.type ();
  }

}
