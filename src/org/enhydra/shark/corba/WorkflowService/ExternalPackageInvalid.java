package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/ExternalPackageInvalid.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分32秒 CST
*/

public final class ExternalPackageInvalid extends org.omg.CORBA.UserException
{
  public String XPDLValidationErrors = null;

  public ExternalPackageInvalid ()
  {
    super(ExternalPackageInvalidHelper.id());
  } // ctor

  public ExternalPackageInvalid (String _XPDLValidationErrors)
  {
    super(ExternalPackageInvalidHelper.id());
    XPDLValidationErrors = _XPDLValidationErrors;
  } // ctor


  public ExternalPackageInvalid (String $reason, String _XPDLValidationErrors)
  {
    super(ExternalPackageInvalidHelper.id() + "  " + $reason);
    XPDLValidationErrors = _XPDLValidationErrors;
  } // ctor

} // class ExternalPackageInvalid
