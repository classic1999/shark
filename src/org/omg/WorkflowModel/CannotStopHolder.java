package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/CannotStopHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class CannotStopHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.CannotStop value = null;

  public CannotStopHolder ()
  {
  }

  public CannotStopHolder (org.omg.WorkflowModel.CannotStop initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.CannotStopHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.CannotStopHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.CannotStopHelper.type ();
  }

}
