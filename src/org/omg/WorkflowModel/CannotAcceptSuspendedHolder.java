package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/CannotAcceptSuspendedHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class CannotAcceptSuspendedHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.CannotAcceptSuspended value = null;

  public CannotAcceptSuspendedHolder ()
  {
  }

  public CannotAcceptSuspendedHolder (org.omg.WorkflowModel.CannotAcceptSuspended initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.CannotAcceptSuspendedHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.CannotAcceptSuspendedHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.CannotAcceptSuspendedHelper.type ();
  }

}