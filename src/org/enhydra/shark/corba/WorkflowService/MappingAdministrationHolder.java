package org.enhydra.shark.corba.WorkflowService;

/**
* org/enhydra/shark/corba/WorkflowService/MappingAdministrationHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowService.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��33�� CST
*/

public final class MappingAdministrationHolder implements org.omg.CORBA.portable.Streamable
{
  public org.enhydra.shark.corba.WorkflowService.MappingAdministration value = null;

  public MappingAdministrationHolder ()
  {
  }

  public MappingAdministrationHolder (org.enhydra.shark.corba.WorkflowService.MappingAdministration initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.enhydra.shark.corba.WorkflowService.MappingAdministrationHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.enhydra.shark.corba.WorkflowService.MappingAdministrationHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.enhydra.shark.corba.WorkflowService.MappingAdministrationHelper.type ();
  }

}
