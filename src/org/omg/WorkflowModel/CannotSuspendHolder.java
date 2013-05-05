package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/CannotSuspendHolder.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

public final class CannotSuspendHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.CannotSuspend value = null;

  public CannotSuspendHolder ()
  {
  }

  public CannotSuspendHolder (org.omg.WorkflowModel.CannotSuspend initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.CannotSuspendHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.CannotSuspendHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.CannotSuspendHelper.type ();
  }

}
