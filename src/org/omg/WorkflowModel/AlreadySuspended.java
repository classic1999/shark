package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/AlreadySuspended.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class AlreadySuspended extends org.omg.CORBA.UserException
{

  public AlreadySuspended ()
  {
    super(AlreadySuspendedHelper.id());
  } // ctor


  public AlreadySuspended (String $reason)
  {
    super(AlreadySuspendedHelper.id() + "  " + $reason);
  } // ctor

} // class AlreadySuspended
