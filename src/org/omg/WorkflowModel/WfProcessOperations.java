package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/WfProcessOperations.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
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
