package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/WfProcessHolder.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

public final class WfProcessHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.WfProcess value = null;

  public WfProcessHolder ()
  {
  }

  public WfProcessHolder (org.omg.WorkflowModel.WfProcess initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.WfProcessHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.WfProcessHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.WfProcessHelper.type ();
  }

}
