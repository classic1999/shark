package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/UpdateNotAllowedHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class UpdateNotAllowedHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.UpdateNotAllowed value = null;

  public UpdateNotAllowedHolder ()
  {
  }

  public UpdateNotAllowedHolder (org.omg.WorkflowModel.UpdateNotAllowed initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.UpdateNotAllowedHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.UpdateNotAllowedHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.UpdateNotAllowedHelper.type ();
  }

}
