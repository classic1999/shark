package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/InvalidRequesterHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class InvalidRequesterHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.InvalidRequester value = null;

  public InvalidRequesterHolder ()
  {
  }

  public InvalidRequesterHolder (org.omg.WorkflowModel.InvalidRequester initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.InvalidRequesterHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.InvalidRequesterHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.InvalidRequesterHelper.type ();
  }

}
