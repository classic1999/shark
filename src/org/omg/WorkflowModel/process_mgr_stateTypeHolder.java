package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/process_mgr_stateTypeHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class process_mgr_stateTypeHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.process_mgr_stateType value = null;

  public process_mgr_stateTypeHolder ()
  {
  }

  public process_mgr_stateTypeHolder (org.omg.WorkflowModel.process_mgr_stateType initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.process_mgr_stateTypeHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.process_mgr_stateTypeHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.process_mgr_stateTypeHelper.type ();
  }

}
