package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/NotAssignedHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class NotAssignedHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.NotAssigned value = null;

  public NotAssignedHolder ()
  {
  }

  public NotAssignedHolder (org.omg.WorkflowModel.NotAssigned initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.NotAssignedHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.NotAssignedHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.NotAssignedHelper.type ();
  }

}
