package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/CannotResumeHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class CannotResumeHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.CannotResume value = null;

  public CannotResumeHolder ()
  {
  }

  public CannotResumeHolder (org.omg.WorkflowModel.CannotResume initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.CannotResumeHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.CannotResumeHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.CannotResumeHelper.type ();
  }

}
