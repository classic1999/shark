package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/NotSuspendedHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class NotSuspendedHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.NotSuspended value = null;

  public NotSuspendedHolder ()
  {
  }

  public NotSuspendedHolder (org.omg.WorkflowModel.NotSuspended initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.NotSuspendedHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.NotSuspendedHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.NotSuspendedHelper.type ();
  }

}
