package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/SourceNotAvailable.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class SourceNotAvailable extends org.omg.CORBA.UserException
{

  public SourceNotAvailable ()
  {
    super(SourceNotAvailableHelper.id());
  } // ctor


  public SourceNotAvailable (String $reason)
  {
    super(SourceNotAvailableHelper.id() + "  " + $reason);
  } // ctor

} // class SourceNotAvailable
