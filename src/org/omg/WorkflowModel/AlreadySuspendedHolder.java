package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/AlreadySuspendedHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class AlreadySuspendedHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.AlreadySuspended value = null;

  public AlreadySuspendedHolder ()
  {
  }

  public AlreadySuspendedHolder (org.omg.WorkflowModel.AlreadySuspended initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.AlreadySuspendedHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.AlreadySuspendedHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.AlreadySuspendedHelper.type ();
  }

}
