package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/WfExecutionObjectOperations.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/


// Interfaces
public interface WfExecutionObjectOperations  extends org.omg.WfBase.BaseBusinessObjectOperations
{
  org.omg.WorkflowModel.workflow_stateType workflow_state () throws org.omg.WfBase.BaseException;
  org.omg.WorkflowModel.while_openType while_open () throws org.omg.WfBase.BaseException;
  org.omg.WorkflowModel.why_not_runningType why_not_running () throws org.omg.WfBase.BaseException;
  org.omg.WorkflowModel.how_closedType how_closed () throws org.omg.WfBase.BaseException;
  String[] valid_states () throws org.omg.WfBase.BaseException;
  String state () throws org.omg.WfBase.BaseException;
  void change_state (String new_state) throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.InvalidState, org.omg.WorkflowModel.TransitionNotAllowed;
  String name () throws org.omg.WfBase.BaseException;
  void set_name (String new_value) throws org.omg.WfBase.BaseException;
  String key () throws org.omg.WfBase.BaseException;
  String description () throws org.omg.WfBase.BaseException;
  void set_description (String new_value) throws org.omg.WfBase.BaseException;
  org.omg.WfBase.NameValue[] process_context () throws org.omg.WfBase.BaseException;
  void set_process_context (org.omg.WfBase.NameValue[] new_value) throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.InvalidData, org.omg.WorkflowModel.UpdateNotAllowed;
  short priority () throws org.omg.WfBase.BaseException;
  void set_priority (short new_value) throws org.omg.WfBase.BaseException;
  void resume () throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.CannotResume, org.omg.WorkflowModel.NotSuspended;
  void suspend () throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.CannotSuspend, org.omg.WorkflowModel.NotRunning, org.omg.WorkflowModel.AlreadySuspended;
  void terminate () throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.CannotStop, org.omg.WorkflowModel.NotRunning;
  void abort () throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.CannotStop, org.omg.WorkflowModel.NotRunning;
  int how_many_history () throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.HistoryNotAvailable;
  org.omg.WorkflowModel.WfEventAuditIterator get_iterator_history (String query, org.omg.WfBase.NameValue[] names_in_query) throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.HistoryNotAvailable;
  org.omg.WorkflowModel.WfEventAudit[] get_sequence_history (int max_number) throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.HistoryNotAvailable;
  boolean is_member_of_history (org.omg.WorkflowModel.WfExecutionObject member) throws org.omg.WfBase.BaseException;
  org.omg.TimeBase.UtcT last_state_time () throws org.omg.WfBase.BaseException;
} // interface WfExecutionObjectOperations
