package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/while_openTypeHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class while_openTypeHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.while_openType value = null;

  public while_openTypeHolder ()
  {
  }

  public while_openTypeHolder (org.omg.WorkflowModel.while_openType initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.while_openTypeHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.while_openTypeHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.while_openTypeHelper.type ();
  }

}
