package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/InvalidData.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class InvalidData extends org.omg.CORBA.UserException
{

  public InvalidData ()
  {
    super(InvalidDataHelper.id());
  } // ctor


  public InvalidData (String $reason)
  {
    super(InvalidDataHelper.id() + "  " + $reason);
  } // ctor

} // class InvalidData
