package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/InvalidPerformerHolder.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

public final class InvalidPerformerHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.InvalidPerformer value = null;

  public InvalidPerformerHolder ()
  {
  }

  public InvalidPerformerHolder (org.omg.WorkflowModel.InvalidPerformer initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.InvalidPerformerHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.InvalidPerformerHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.InvalidPerformerHelper.type ();
  }

}
