package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/AlreadyRunning.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class AlreadyRunning extends org.omg.CORBA.UserException
{

  public AlreadyRunning ()
  {
    super(AlreadyRunningHelper.id());
  } // ctor


  public AlreadyRunning (String $reason)
  {
    super(AlreadyRunningHelper.id() + "  " + $reason);
  } // ctor

} // class AlreadyRunning
