package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/WfProcessMgrOperations.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
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
