package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/WfAssignmentIteratorHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class WfAssignmentIteratorHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.WfAssignmentIterator value = null;

  public WfAssignmentIteratorHolder ()
  {
  }

  public WfAssignmentIteratorHolder (org.omg.WorkflowModel.WfAssignmentIterator initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.WfAssignmentIteratorHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.WfAssignmentIteratorHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.WfAssignmentIteratorHelper.type ();
  }

}
