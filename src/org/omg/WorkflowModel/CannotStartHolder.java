package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/CannotStartHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class CannotStartHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.CannotStart value = null;

  public CannotStartHolder ()
  {
  }

  public CannotStartHolder (org.omg.WorkflowModel.CannotStart initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.CannotStartHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.CannotStartHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.CannotStartHelper.type ();
  }

}
