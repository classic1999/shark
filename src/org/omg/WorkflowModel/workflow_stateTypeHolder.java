package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/workflow_stateTypeHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
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
