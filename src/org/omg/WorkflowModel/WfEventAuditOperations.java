package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/WfEventAuditOperations.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

public interface WfEventAuditOperations  extends org.omg.WfBase.BaseBusinessObjectOperations
{
  org.omg.WorkflowModel.WfExecutionObject source () throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.SourceNotAvailable;
  org.omg.TimeBase.UtcT time_stamp () throws org.omg.WfBase.BaseException;
  String event_type () throws org.omg.WfBase.BaseException;
  String activity_key () throws org.omg.WfBase.BaseException;
  String activity_name () throws org.omg.WfBase.BaseException;
  String process_key () throws org.omg.WfBase.BaseException;
  String process_name () throws org.omg.WfBase.BaseException;
  String process_mgr_name () throws org.omg.WfBase.BaseException;
  String process_mgr_version () throws org.omg.WfBase.BaseException;
} // interface WfEventAuditOperations
