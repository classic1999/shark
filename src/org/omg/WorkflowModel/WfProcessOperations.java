package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/WfProcessOperations.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public interface WfProcessOperations  extends org.omg.WorkflowModel.WfExecutionObjectOperations
{
  org.omg.WorkflowModel.WfRequester requester () throws org.omg.WfBase.BaseException;
  void set_requester (org.omg.WorkflowModel.WfRequester new_value) throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.CannotChangeRequester;
  int how_many_step () throws org.omg.WfBase.BaseException;
  org.omg.WorkflowModel.WfActivityIterator get_iterator_step () throws org.omg.WfBase.BaseException;
  org.omg.WorkflowModel.WfActivity[] get_sequence_step (int max_number) throws org.omg.WfBase.BaseException;
  boolean is_member_of_step (org.omg.WorkflowModel.WfActivity member) throws org.omg.WfBase.BaseException;
  org.omg.WorkflowModel.WfProcessMgr manager () throws org.omg.WfBase.BaseException;
  org.omg.WfBase.NameValue[] result () throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.ResultNotAvailable;
  void start () throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.CannotStart, org.omg.WorkflowModel.AlreadyRunning;
  org.omg.WorkflowModel.WfActivityIterator get_activities_in_state (String state) throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.InvalidState;
} // interface WfProcessOperations
