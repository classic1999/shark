package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/RepositoryInvalid.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowService.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��32�� CST
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
