package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/while_openType.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

public class while_openType implements org.omg.CORBA.portable.IDLEntity
{
  private        int __value;
  private static int __size = 2;
  private static org.omg.WorkflowModel.while_openType[] __array = new org.omg.WorkflowModel.while_openType [__size];

  public static final int _not_running = 0;
  public static final org.omg.WorkflowModel.while_openType not_running = new org.omg.WorkflowModel.while_openType(_not_running);
  public static final int _running = 1;
  public static final org.omg.WorkflowModel.while_openType running = new org.omg.WorkflowModel.while_openType(_running);

  public int value ()
  {
    return __value;
  }

  public static org.omg.WorkflowModel.while_openType from_int (int value)
  {
    if (value >= 0 && value < __size)
      return __array[value];
    else
      throw new org.omg.CORBA.BAD_PARAM ();
  }

  protected while_openType (int value)
  {
    __value = value;
    __array[__value] = this;
  }
} // class while_openType
