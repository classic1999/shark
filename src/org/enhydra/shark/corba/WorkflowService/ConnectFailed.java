package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/ConnectFailed.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowService.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��32�� CST
*/

public final class ConnectFailed extends org.omg.CORBA.UserException
{

  public ConnectFailed ()
  {
    super(ConnectFailedHelper.id());
  } // ctor


  public ConnectFailed (String $reason)
  {
    super(ConnectFailedHelper.id() + "  " + $reason);
  } // ctor

} // class ConnectFailed
