package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/WfEventAuditIteratorHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class WfEventAuditIteratorHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.WfEventAuditIterator value = null;

  public WfEventAuditIteratorHolder ()
  {
  }

  public WfEventAuditIteratorHolder (org.omg.WorkflowModel.WfEventAuditIterator initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.WfEventAuditIteratorHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.WfEventAuditIteratorHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.WfEventAuditIteratorHelper.type ();
  }

}
