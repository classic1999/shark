package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/WfRequesterOperations.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

public interface WfRequesterOperations  extends org.omg.WfBase.BaseBusinessObjectOperations
{
  int how_many_performer () throws org.omg.WfBase.BaseException;
  org.omg.WorkflowModel.WfProcessIterator get_iterator_performer () throws org.omg.WfBase.BaseException;
  org.omg.WorkflowModel.WfProcess[] get_sequence_performer (int max_number) throws org.omg.WfBase.BaseException;
  boolean is_member_of_performer (org.omg.WorkflowModel.WfProcess member) throws org.omg.WfBase.BaseException;
  void receive_event (org.omg.WorkflowModel.WfEventAudit event) throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.InvalidPerformer;
} // interface WfRequesterOperations
