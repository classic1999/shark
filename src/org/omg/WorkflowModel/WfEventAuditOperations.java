package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/WfEventAuditOperations.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
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
