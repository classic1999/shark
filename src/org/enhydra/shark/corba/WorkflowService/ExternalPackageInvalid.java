package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/ExternalPackageInvalid.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowService.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��32�� CST
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
