package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/InvalidResource.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class InvalidResource extends org.omg.CORBA.UserException
{

  public InvalidResource ()
  {
    super(InvalidResourceHelper.id());
  } // ctor


  public InvalidResource (String $reason)
  {
    super(InvalidResourceHelper.id() + "  " + $reason);
  } // ctor

} // class InvalidResource
