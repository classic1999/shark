package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/InvalidDataHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class InvalidDataHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.InvalidData value = null;

  public InvalidDataHolder ()
  {
  }

  public InvalidDataHolder (org.omg.WorkflowModel.InvalidData initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.InvalidDataHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.InvalidDataHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.InvalidDataHelper.type ();
  }

}
