package org.enhydra.shark.corba.WorkflowService;

/**
* org/enhydra/shark/corba/WorkflowService/PackageInUseHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowService.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��32�� CST
*/

public final class PackageInUseHolder implements org.omg.CORBA.portable.Streamable
{
  public org.enhydra.shark.corba.WorkflowService.PackageInUse value = null;

  public PackageInUseHolder ()
  {
  }

  public PackageInUseHolder (org.enhydra.shark.corba.WorkflowService.PackageInUse initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.enhydra.shark.corba.WorkflowService.PackageInUseHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.enhydra.shark.corba.WorkflowService.PackageInUseHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.enhydra.shark.corba.WorkflowService.PackageInUseHelper.type ();
  }

}
