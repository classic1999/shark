package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/InvalidResourceHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class InvalidResourceHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.InvalidResource value = null;

  public InvalidResourceHolder ()
  {
  }

  public InvalidResourceHolder (org.omg.WorkflowModel.InvalidResource initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.InvalidResourceHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.InvalidResourceHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.InvalidResourceHelper.type ();
  }

}
