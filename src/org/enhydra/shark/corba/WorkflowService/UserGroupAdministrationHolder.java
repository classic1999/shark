package org.enhydra.shark.corba.WorkflowService;

/**
* org/enhydra/shark/corba/WorkflowService/UserGroupAdministrationHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowService.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��33�� CST
*/

public final class UserGroupAdministrationHolder implements org.omg.CORBA.portable.Streamable
{
  public org.enhydra.shark.corba.WorkflowService.UserGroupAdministration value = null;

  public UserGroupAdministrationHolder ()
  {
  }

  public UserGroupAdministrationHolder (org.enhydra.shark.corba.WorkflowService.UserGroupAdministration initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.enhydra.shark.corba.WorkflowService.UserGroupAdministrationHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.enhydra.shark.corba.WorkflowService.UserGroupAdministrationHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.enhydra.shark.corba.WorkflowService.UserGroupAdministrationHelper.type ();
  }

}
