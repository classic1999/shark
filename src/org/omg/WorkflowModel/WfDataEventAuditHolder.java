package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/WfDataEventAuditHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class WfDataEventAuditHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.WfDataEventAudit value = null;

  public WfDataEventAuditHolder ()
  {
  }

  public WfDataEventAuditHolder (org.omg.WorkflowModel.WfDataEventAudit initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.WfDataEventAuditHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.WfDataEventAuditHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.WfDataEventAuditHelper.type ();
  }

}
