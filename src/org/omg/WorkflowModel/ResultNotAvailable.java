package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/ResultNotAvailable.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class ResultNotAvailable extends org.omg.CORBA.UserException
{

  public ResultNotAvailable ()
  {
    super(ResultNotAvailableHelper.id());
  } // ctor


  public ResultNotAvailable (String $reason)
  {
    super(ResultNotAvailableHelper.id() + "  " + $reason);
  } // ctor

} // class ResultNotAvailable
