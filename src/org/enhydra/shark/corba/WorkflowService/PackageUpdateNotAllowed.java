package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/PackageUpdateNotAllowed.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowService.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��32�� CST
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
