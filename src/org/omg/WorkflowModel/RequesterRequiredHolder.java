package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/RequesterRequiredHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class RequesterRequiredHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.RequesterRequired value = null;

  public RequesterRequiredHolder ()
  {
  }

  public RequesterRequiredHolder (org.omg.WorkflowModel.RequesterRequired initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.RequesterRequiredHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.RequesterRequiredHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.RequesterRequiredHelper.type ();
  }

}
