package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/NotRunning.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class NotRunning extends org.omg.CORBA.UserException
{

  public NotRunning ()
  {
    super(NotRunningHelper.id());
  } // ctor


  public NotRunning (String $reason)
  {
    super(NotRunningHelper.id() + "  " + $reason);
  } // ctor

} // class NotRunning
