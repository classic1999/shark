package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/why_not_runningTypeHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class why_not_runningTypeHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.why_not_runningType value = null;

  public why_not_runningTypeHolder ()
  {
  }

  public why_not_runningTypeHolder (org.omg.WorkflowModel.why_not_runningType initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.why_not_runningTypeHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.why_not_runningTypeHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.why_not_runningTypeHelper.type ();
  }

}
