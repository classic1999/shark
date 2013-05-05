package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/WfCreateProcessEventAuditHolder.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

public final class WfCreateProcessEventAuditHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.WfCreateProcessEventAudit value = null;

  public WfCreateProcessEventAuditHolder ()
  {
  }

  public WfCreateProcessEventAuditHolder (org.omg.WorkflowModel.WfCreateProcessEventAudit initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.WfCreateProcessEventAuditHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.WfCreateProcessEventAuditHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.WfCreateProcessEventAuditHelper.type ();
  }

}
