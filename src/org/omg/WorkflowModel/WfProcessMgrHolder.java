package org.omg.WorkflowModel;

/**
* org/omg/WorkflowModel/WfProcessMgrHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class WfProcessMgrHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.WfProcessMgr value = null;

  public WfProcessMgrHolder ()
  {
  }

  public WfProcessMgrHolder (org.omg.WorkflowModel.WfProcessMgr initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WorkflowModel.WfProcessMgrHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WorkflowModel.WfProcessMgrHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WorkflowModel.WfProcessMgrHelper.type ();
  }

}
