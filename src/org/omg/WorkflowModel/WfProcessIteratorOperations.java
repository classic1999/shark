package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/WfProcessIteratorOperations.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

public interface WfProcessIteratorOperations  extends org.omg.WfBase.BaseIteratorOperations
{
  org.omg.WorkflowModel.WfProcess get_next_object () throws org.omg.WfBase.BaseException;
  org.omg.WorkflowModel.WfProcess get_previous_object () throws org.omg.WfBase.BaseException;
  org.omg.WorkflowModel.WfProcess[] get_next_n_sequence (int max_number) throws org.omg.WfBase.BaseException;
  org.omg.WorkflowModel.WfProcess[] get_previous_n_sequence (int max_number) throws org.omg.WfBase.BaseException;
} // interface WfProcessIteratorOperations
