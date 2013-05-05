package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/WfActivityOperations.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

public interface WfActivityOperations  extends org.omg.WorkflowModel.WfExecutionObjectOperations, org.omg.WorkflowModel.WfRequesterOperations
{
  int how_many_assignment () throws org.omg.WfBase.BaseException;
  org.omg.WorkflowModel.WfAssignmentIterator get_iterator_assignment () throws org.omg.WfBase.BaseException;
  org.omg.WorkflowModel.WfAssignment[] get_sequence_assignment (int max_number) throws org.omg.WfBase.BaseException;
  boolean is_member_of_assignment (org.omg.WorkflowModel.WfAssignment member) throws org.omg.WfBase.BaseException;
  org.omg.WorkflowModel.WfProcess container () throws org.omg.WfBase.BaseException;
  org.omg.WfBase.NameValue[] result () throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.ResultNotAvailable;
  void set_result (org.omg.WfBase.NameValue[] result) throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.InvalidData;
  void complete () throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.CannotComplete;
} // interface WfActivityOperations
