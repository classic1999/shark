package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/CannotCompleteHolder.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

public final class CannotCompleteHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.CannotComplete value = null;

  public CannotCompleteHolder ()
  {
  }

  public CannotCompleteHolder (org.omg.WorkflowModel.CannotComplete initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.CannotCompleteHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.CannotCompleteHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.CannotCompleteHelper.type ();
  }

}
