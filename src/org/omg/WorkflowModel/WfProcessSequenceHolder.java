package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/WfProcessSequenceHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/


// DataTypes
public final class WfProcessSequenceHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.WfProcess value[] = null;

  public WfProcessSequenceHolder ()
  {
  }

  public WfProcessSequenceHolder (org.omg.WorkflowModel.WfProcess[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.WfProcessSequenceHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.WfProcessSequenceHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.WfProcessSequenceHelper.type ();
  }

}
