package org.enhydra.shark.corba.WorkflowService;

/**
* org/enhydra/shark/corba/WorkflowService/PackageUpdateNotAllowedHolder.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分32秒 CST
*/

public final class PackageUpdateNotAllowedHolder implements org.omg.CORBA.portable.Streamable
{
  public org.enhydra.shark.corba.WorkflowService.PackageUpdateNotAllowed value = null;

  public PackageUpdateNotAllowedHolder ()
  {
  }

  public PackageUpdateNotAllowedHolder (org.enhydra.shark.corba.WorkflowService.PackageUpdateNotAllowed initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.enhydra.shark.corba.WorkflowService.PackageUpdateNotAllowedHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.enhydra.shark.corba.WorkflowService.PackageUpdateNotAllowedHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.enhydra.shark.corba.WorkflowService.PackageUpdateNotAllowedHelper.type ();
  }

}
