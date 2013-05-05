package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/workflow_stateTypeHolder.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

public final class workflow_stateTypeHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.workflow_stateType value = null;

  public workflow_stateTypeHolder ()
  {
  }

  public workflow_stateTypeHolder (org.omg.WorkflowModel.workflow_stateType initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.workflow_stateTypeHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.workflow_stateTypeHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.workflow_stateTypeHelper.type ();
  }

}
