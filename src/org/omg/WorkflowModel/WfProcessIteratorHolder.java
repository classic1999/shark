package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/WfProcessIteratorHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class WfProcessIteratorHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.WfProcessIterator value = null;

  public WfProcessIteratorHolder ()
  {
  }

  public WfProcessIteratorHolder (org.omg.WorkflowModel.WfProcessIterator initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.WfProcessIteratorHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.WfProcessIteratorHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.WfProcessIteratorHelper.type ();
  }

}
