package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/InvalidPerformer.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class InvalidPerformer extends org.omg.CORBA.UserException
{

  public InvalidPerformer ()
  {
    super(InvalidPerformerHelper.id());
  } // ctor


  public InvalidPerformer (String $reason)
  {
    super(InvalidPerformerHelper.id() + "  " + $reason);
  } // ctor

} // class InvalidPerformer
