package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/AlreadyRunningHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class AlreadyRunningHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.AlreadyRunning value = null;

  public AlreadyRunningHolder ()
  {
  }

  public AlreadyRunningHolder (org.omg.WorkflowModel.AlreadyRunning initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.AlreadyRunningHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.AlreadyRunningHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.AlreadyRunningHelper.type ();
  }

}
