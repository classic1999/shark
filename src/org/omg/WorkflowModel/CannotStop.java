package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/CannotStop.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class CannotStop extends org.omg.CORBA.UserException
{

  public CannotStop ()
  {
    super(CannotStopHelper.id());
  } // ctor


  public CannotStop (String $reason)
  {
    super(CannotStopHelper.id() + "  " + $reason);
  } // ctor

} // class CannotStop
