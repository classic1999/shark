package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/why_not_runningType.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

public class why_not_runningType implements org.omg.CORBA.portable.IDLEntity
{
  private        int __value;
  private static int __size = 2;
  private static org.omg.WorkflowModel.why_not_runningType[] __array = new org.omg.WorkflowModel.why_not_runningType [__size];

  public static final int _not_started = 0;
  public static final org.omg.WorkflowModel.why_not_runningType not_started = new org.omg.WorkflowModel.why_not_runningType(_not_started);
  public static final int _suspended = 1;
  public static final org.omg.WorkflowModel.why_not_runningType suspended = new org.omg.WorkflowModel.why_not_runningType(_suspended);

  public int value ()
  {
    return __value;
  }

  public static org.omg.WorkflowModel.why_not_runningType from_int (int value)
  {
    if (value >= 0 && value < __size)
      return __array[value];
    else
      throw new org.omg.CORBA.BAD_PARAM ();
  }

  protected why_not_runningType (int value)
  {
    __value = value;
    __array[__value] = this;
  }
} // class why_not_runningType
