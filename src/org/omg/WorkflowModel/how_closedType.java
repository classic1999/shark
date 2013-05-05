package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/how_closedType.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

public class how_closedType implements org.omg.CORBA.portable.IDLEntity
{
  private        int __value;
  private static int __size = 3;
  private static org.omg.WorkflowModel.how_closedType[] __array = new org.omg.WorkflowModel.how_closedType [__size];

  public static final int _completed = 0;
  public static final org.omg.WorkflowModel.how_closedType completed = new org.omg.WorkflowModel.how_closedType(_completed);
  public static final int _terminated = 1;
  public static final org.omg.WorkflowModel.how_closedType terminated = new org.omg.WorkflowModel.how_closedType(_terminated);
  public static final int _aborted = 2;
  public static final org.omg.WorkflowModel.how_closedType aborted = new org.omg.WorkflowModel.how_closedType(_aborted);

  public int value ()
  {
    return __value;
  }

  public static org.omg.WorkflowModel.how_closedType from_int (int value)
  {
    if (value >= 0 && value < __size)
      return __array[value];
    else
      throw new org.omg.CORBA.BAD_PARAM ();
  }

  protected how_closedType (int value)
  {
    __value = value;
    __array[__value] = this;
  }
} // class how_closedType
