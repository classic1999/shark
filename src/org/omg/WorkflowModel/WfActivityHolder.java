package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/WfActivityHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class WfActivityHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.WfActivity value = null;

  public WfActivityHolder ()
  {
  }

  public WfActivityHolder (org.omg.WorkflowModel.WfActivity initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.WfActivityHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.WfActivityHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.WfActivityHelper.type ();
  }

}
