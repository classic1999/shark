package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/PackageUpdateNotAllowed.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分32秒 CST
*/

public final class PackageUpdateNotAllowed extends org.omg.CORBA.UserException
{

  public PackageUpdateNotAllowed ()
  {
    super(PackageUpdateNotAllowedHelper.id());
  } // ctor


  public PackageUpdateNotAllowed (String $reason)
  {
    super(PackageUpdateNotAllowedHelper.id() + "  " + $reason);
  } // ctor

} // class PackageUpdateNotAllowed
