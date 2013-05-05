package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/process_mgr_stateType.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

public class process_mgr_stateType implements org.omg.CORBA.portable.IDLEntity
{
  private        int __value;
  private static int __size = 2;
  private static org.omg.WorkflowModel.process_mgr_stateType[] __array = new org.omg.WorkflowModel.process_mgr_stateType [__size];

  public static final int _enabled = 0;
  public static final org.omg.WorkflowModel.process_mgr_stateType enabled = new org.omg.WorkflowModel.process_mgr_stateType(_enabled);
  public static final int _disabled = 1;
  public static final org.omg.WorkflowModel.process_mgr_stateType disabled = new org.omg.WorkflowModel.process_mgr_stateType(_disabled);

  public int value ()
  {
    return __value;
  }

  public static org.omg.WorkflowModel.process_mgr_stateType from_int (int value)
  {
    if (value >= 0 && value < __size)
      return __array[value];
    else
      throw new org.omg.CORBA.BAD_PARAM ();
  }

  protected process_mgr_stateType (int value)
  {
    __value = value;
    __array[__value] = this;
  }
} // class process_mgr_stateType
