package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/WfAssignmentHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class WfAssignmentHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.WfAssignment value = null;

  public WfAssignmentHolder ()
  {
  }

  public WfAssignmentHolder (org.omg.WorkflowModel.WfAssignment initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.WfAssignmentHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.WfAssignmentHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.WfAssignmentHelper.type ();
  }

}
