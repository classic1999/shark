package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/WfActivityIteratorHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class WfActivityIteratorHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.WfActivityIterator value = null;

  public WfActivityIteratorHolder ()
  {
  }

  public WfActivityIteratorHolder (org.omg.WorkflowModel.WfActivityIterator initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.WfActivityIteratorHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.WfActivityIteratorHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.WfActivityIteratorHelper.type ();
  }

}
