package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/TransitionNotAllowedHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class TransitionNotAllowedHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.TransitionNotAllowed value = null;

  public TransitionNotAllowedHolder ()
  {
  }

  public TransitionNotAllowedHolder (org.omg.WorkflowModel.TransitionNotAllowed initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.TransitionNotAllowedHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.TransitionNotAllowedHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.TransitionNotAllowedHelper.type ();
  }

}
