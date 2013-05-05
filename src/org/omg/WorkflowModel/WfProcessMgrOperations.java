package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/WfProcessMgrOperations.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public interface WfProcessMgrOperations  extends org.omg.WfBase.BaseBusinessObjectOperations
{
  int how_many_process () throws org.omg.WfBase.BaseException;
  org.omg.WorkflowModel.WfProcessIterator get_iterator_process () throws org.omg.WfBase.BaseException;
  org.omg.WorkflowModel.WfProcess[] get_sequence_process (int max_number) throws org.omg.WfBase.BaseException;
  boolean is_member_of_process (org.omg.WorkflowModel.WfProcess member) throws org.omg.WfBase.BaseException;
  org.omg.WorkflowModel.process_mgr_stateType process_mgr_state () throws org.omg.WfBase.BaseException;
  void set_process_mgr_state (org.omg.WorkflowModel.process_mgr_stateType new_state) throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.TransitionNotAllowed;
  String name () throws org.omg.WfBase.BaseException;
  String description () throws org.omg.WfBase.BaseException;
  String category () throws org.omg.WfBase.BaseException;
  String version () throws org.omg.WfBase.BaseException;
  org.omg.WfBase.NameValueInfo[] context_signature () throws org.omg.WfBase.BaseException;
  org.omg.WfBase.NameValueInfo[] result_signature () throws org.omg.WfBase.BaseException;
  org.omg.WorkflowModel.WfProcess create_process (org.omg.WorkflowModel.WfRequester requester) throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.NotEnabled, org.omg.WorkflowModel.InvalidRequester, org.omg.WorkflowModel.RequesterRequired;
} // interface WfProcessMgrOperations
