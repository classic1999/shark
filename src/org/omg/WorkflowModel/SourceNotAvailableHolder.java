package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/SourceNotAvailableHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class SourceNotAvailableHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.SourceNotAvailable value = null;

  public SourceNotAvailableHolder ()
  {
  }

  public SourceNotAvailableHolder (org.omg.WorkflowModel.SourceNotAvailable initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.SourceNotAvailableHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.SourceNotAvailableHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.SourceNotAvailableHelper.type ();
  }

}
