package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/WfDataEventAuditSequenceHolder.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分32秒 CST
*/

public final class WfDataEventAuditSequenceHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.WfDataEventAudit value[] = null;

  public WfDataEventAuditSequenceHolder ()
  {
  }

  public WfDataEventAuditSequenceHolder (org.omg.WorkflowModel.WfDataEventAudit[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.enhydra.shark.corba.WorkflowService.WfDataEventAuditSequenceHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.enhydra.shark.corba.WorkflowService.WfDataEventAuditSequenceHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.enhydra.shark.corba.WorkflowService.WfDataEventAuditSequenceHelper.type ();
  }

}
