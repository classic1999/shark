package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/WfResourceHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class WfResourceHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.WfResource value = null;

  public WfResourceHolder ()
  {
  }

  public WfResourceHolder (org.omg.WorkflowModel.WfResource initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.WfResourceHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.WfResourceHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.WfResourceHelper.type ();
  }

}
