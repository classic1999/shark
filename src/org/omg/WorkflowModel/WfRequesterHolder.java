package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/WfRequesterHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class WfRequesterHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.WfRequester value = null;

  public WfRequesterHolder ()
  {
  }

  public WfRequesterHolder (org.omg.WorkflowModel.WfRequester initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.WfRequesterHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.WfRequesterHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.WfRequesterHelper.type ();
  }

}
