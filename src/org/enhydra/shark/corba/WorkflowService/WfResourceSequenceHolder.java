package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/WfResourceSequenceHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowService.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��32�� CST
*/

public final class WfResourceSequenceHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WorkflowModel.WfResource value[] = null;

  public WfResourceSequenceHolder ()
  {
  }

  public WfResourceSequenceHolder (org.omg.WorkflowModel.WfResource[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.enhydra.shark.corba.WorkflowService.WfResourceSequenceHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.enhydra.shark.corba.WorkflowService.WfResourceSequenceHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.enhydra.shark.corba.WorkflowService.WfResourceSequenceHelper.type ();
  }

}
