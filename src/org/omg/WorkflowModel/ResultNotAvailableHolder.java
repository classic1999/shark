package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/ResultNotAvailableHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class ResultNotAvailableHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.ResultNotAvailable value = null;

  public ResultNotAvailableHolder ()
  {
  }

  public ResultNotAvailableHolder (org.omg.WorkflowModel.ResultNotAvailable initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.ResultNotAvailableHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.ResultNotAvailableHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.ResultNotAvailableHelper.type ();
  }

}
