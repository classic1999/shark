package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/workflow_stateType.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public class workflow_stateType implements org.omg.CORBA.portable.IDLEntity
{
  private        int __value;
  private static int __size = 2;
  private static org.omg.WorkflowModel.workflow_stateType[] __array = new org.omg.WorkflowModel.workflow_stateType [__size];

  public static final int _open = 0;
  public static final org.omg.WorkflowModel.workflow_stateType open = new org.omg.WorkflowModel.workflow_stateType(_open);
  public static final int _closed = 1;
  public static final org.omg.WorkflowModel.workflow_stateType closed = new org.omg.WorkflowModel.workflow_stateType(_closed);

  public int value ()
  {
    return __value;
  }

  public static org.omg.WorkflowModel.workflow_stateType from_int (int value)
  {
    if (value >= 0 && value < __size)
      return __array[value];
    else
      throw new org.omg.CORBA.BAD_PARAM ();
  }

  protected workflow_stateType (int value)
  {
    __value = value;
    __array[__value] = this;
  }
} // class workflow_stateType
