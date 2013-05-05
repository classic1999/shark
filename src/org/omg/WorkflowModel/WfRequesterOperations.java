package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/WfRequesterOperations.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public interface WfRequesterOperations  extends org.omg.WfBase.BaseBusinessObjectOperations
{
  int how_many_performer () throws org.omg.WfBase.BaseException;
  org.omg.WorkflowModel.WfProcessIterator get_iterator_performer () throws org.omg.WfBase.BaseException;
  org.omg.WorkflowModel.WfProcess[] get_sequence_performer (int max_number) throws org.omg.WfBase.BaseException;
  boolean is_member_of_performer (org.omg.WorkflowModel.WfProcess member) throws org.omg.WfBase.BaseException;
  void receive_event (org.omg.WorkflowModel.WfEventAudit event) throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.InvalidPerformer;
} // interface WfRequesterOperations
