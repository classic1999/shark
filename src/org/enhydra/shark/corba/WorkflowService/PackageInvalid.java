package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/PackageInvalid.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowService.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��32�� CST
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
