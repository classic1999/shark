package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/WfEventAuditSequenceHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class WfEventAuditSequenceHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.WfEventAudit value[] = null;

  public WfEventAuditSequenceHolder ()
  {
  }

  public WfEventAuditSequenceHolder (org.omg.WorkflowModel.WfEventAudit[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.WfEventAuditSequenceHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.WfEventAuditSequenceHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.WfEventAuditSequenceHelper.type ();
  }

}