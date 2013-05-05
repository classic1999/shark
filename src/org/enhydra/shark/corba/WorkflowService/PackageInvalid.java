package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/PackageInvalid.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分32秒 CST
*/

public final class PackageInvalid extends org.omg.CORBA.UserException
{
  public String XPDLValidationErrors = null;

  public PackageInvalid ()
  {
    super(PackageInvalidHelper.id());
  } // ctor

  public PackageInvalid (String _XPDLValidationErrors)
  {
    super(PackageInvalidHelper.id());
    XPDLValidationErrors = _XPDLValidationErrors;
  } // ctor


  public PackageInvalid (String $reason, String _XPDLValidationErrors)
  {
    super(PackageInvalidHelper.id() + "  " + $reason);
    XPDLValidationErrors = _XPDLValidationErrors;
  } // ctor

} // class PackageInvalid
