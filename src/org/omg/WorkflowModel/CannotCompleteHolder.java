package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/CannotCompleteHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class CannotCompleteHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.CannotComplete value = null;

  public CannotCompleteHolder ()
  {
  }

  public CannotCompleteHolder (org.omg.WorkflowModel.CannotComplete initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.CannotCompleteHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.CannotCompleteHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.CannotCompleteHelper.type ();
  }

}
