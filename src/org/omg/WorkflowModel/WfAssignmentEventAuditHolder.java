package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/WfAssignmentEventAuditHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class WfAssignmentEventAuditHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.WfAssignmentEventAudit value = null;

  public WfAssignmentEventAuditHolder ()
  {
  }

  public WfAssignmentEventAuditHolder (org.omg.WorkflowModel.WfAssignmentEventAudit initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.WfAssignmentEventAuditHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.WfAssignmentEventAuditHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.WfAssignmentEventAuditHelper.type ();
  }

}
