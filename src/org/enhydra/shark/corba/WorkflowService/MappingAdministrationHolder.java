package org.enhydra.shark.corba.WorkflowService;

/**
* org/enhydra/shark/corba/WorkflowService/MappingAdministrationHolder.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分33秒 CST
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
