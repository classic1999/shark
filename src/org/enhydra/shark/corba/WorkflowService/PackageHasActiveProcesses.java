package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/PackageHasActiveProcesses.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowService.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��32�� CST
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
