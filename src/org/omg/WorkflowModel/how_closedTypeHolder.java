package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/how_closedTypeHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class how_closedTypeHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.how_closedType value = null;

  public how_closedTypeHolder ()
  {
  }

  public how_closedTypeHolder (org.omg.WorkflowModel.how_closedType initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.how_closedTypeHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.how_closedTypeHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.how_closedTypeHelper.type ();
  }

}
