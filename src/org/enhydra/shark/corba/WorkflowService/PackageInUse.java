package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/PackageInUse.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowService.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��32�� CST
*/

public final class PackageInUse extends org.omg.CORBA.UserException
{

  public PackageInUse ()
  {
    super(PackageInUseHelper.id());
  } // ctor


  public PackageInUse (String $reason)
  {
    super(PackageInUseHelper.id() + "  " + $reason);
  } // ctor

} // class PackageInUse
