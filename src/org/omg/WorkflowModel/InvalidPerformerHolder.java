package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/InvalidPerformerHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class InvalidPerformerHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.InvalidPerformer value = null;

  public InvalidPerformerHolder ()
  {
  }

  public InvalidPerformerHolder (org.omg.WorkflowModel.InvalidPerformer initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.InvalidPerformerHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.InvalidPerformerHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.InvalidPerformerHelper.type ();
  }

}
