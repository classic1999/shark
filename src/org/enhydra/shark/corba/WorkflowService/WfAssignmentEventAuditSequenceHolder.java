package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/WfAssignmentEventAuditSequenceHolder.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分32秒 CST
*/

public final class WfAssignmentEventAuditSequenceHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.WfAssignmentEventAudit value[] = null;

  public WfAssignmentEventAuditSequenceHolder ()
  {
  }

  public WfAssignmentEventAuditSequenceHolder (org.omg.WorkflowModel.WfAssignmentEventAudit[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.enhydra.shark.corba.WorkflowService.WfAssignmentEventAuditSequenceHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.enhydra.shark.corba.WorkflowService.WfAssignmentEventAuditSequenceHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.enhydra.shark.corba.WorkflowService.WfAssignmentEventAuditSequenceHelper.type ();
  }

}
