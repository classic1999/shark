package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/WfProcessHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class WfProcessHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.WfProcess value = null;

  public WfProcessHolder ()
  {
  }

  public WfProcessHolder (org.omg.WorkflowModel.WfProcess initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.WfProcessHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.WfProcessHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.WfProcessHelper.type ();
  }

}
