package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/CannotSuspendHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class CannotSuspendHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.CannotSuspend value = null;

  public CannotSuspendHolder ()
  {
  }

  public CannotSuspendHolder (org.omg.WorkflowModel.CannotSuspend initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.CannotSuspendHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.CannotSuspendHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.CannotSuspendHelper.type ();
  }

}
