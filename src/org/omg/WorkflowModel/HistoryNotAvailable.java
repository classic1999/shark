package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/HistoryNotAvailable.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class HistoryNotAvailable extends org.omg.CORBA.UserException
{

  public HistoryNotAvailable ()
  {
    super(HistoryNotAvailableHelper.id());
  } // ctor


  public HistoryNotAvailable (String $reason)
  {
    super(HistoryNotAvailableHelper.id() + "  " + $reason);
  } // ctor

} // class HistoryNotAvailable
