package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/CannotChangeRequesterHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class CannotChangeRequesterHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.CannotChangeRequester value = null;

  public CannotChangeRequesterHolder ()
  {
  }

  public CannotChangeRequesterHolder (org.omg.WorkflowModel.CannotChangeRequester initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.CannotChangeRequesterHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.CannotChangeRequesterHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.CannotChangeRequesterHelper.type ();
  }

}
