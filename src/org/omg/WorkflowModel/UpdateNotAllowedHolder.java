package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/UpdateNotAllowedHolder.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

public final class UpdateNotAllowedHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.UpdateNotAllowed value = null;

  public UpdateNotAllowedHolder ()
  {
  }

  public UpdateNotAllowedHolder (org.omg.WorkflowModel.UpdateNotAllowed initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.UpdateNotAllowedHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.UpdateNotAllowedHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.UpdateNotAllowedHelper.type ();
  }

}
