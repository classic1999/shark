package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/RepositoryInvalid.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分32秒 CST
*/

public final class RepositoryInvalid extends org.omg.CORBA.UserException
{
  public String XPDLValidationErrors = null;

  public RepositoryInvalid ()
  {
    super(RepositoryInvalidHelper.id());
  } // ctor

  public RepositoryInvalid (String _XPDLValidationErrors)
  {
    super(RepositoryInvalidHelper.id());
    XPDLValidationErrors = _XPDLValidationErrors;
  } // ctor


  public RepositoryInvalid (String $reason, String _XPDLValidationErrors)
  {
    super(RepositoryInvalidHelper.id() + "  " + $reason);
    XPDLValidationErrors = _XPDLValidationErrors;
  } // ctor

} // class RepositoryInvalid
