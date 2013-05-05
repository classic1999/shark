package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/PackageHasActiveProcesses.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分32秒 CST
*/

public final class PackageHasActiveProcesses extends org.omg.CORBA.UserException
{

  public PackageHasActiveProcesses ()
  {
    super(PackageHasActiveProcessesHelper.id());
  } // ctor


  public PackageHasActiveProcesses (String $reason)
  {
    super(PackageHasActiveProcessesHelper.id() + "  " + $reason);
  } // ctor

} // class PackageHasActiveProcesses
