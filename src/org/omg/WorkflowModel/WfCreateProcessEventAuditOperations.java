package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/WfCreateProcessEventAuditOperations.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public interface WfCreateProcessEventAuditOperations  extends org.omg.WorkflowModel.WfEventAuditOperations
{
  String p_activity_key () throws org.omg.WfBase.BaseException;
  String p_process_key () throws org.omg.WfBase.BaseException;
  String p_process_name () throws org.omg.WfBase.BaseException;
  String p_process_mgr_name () throws org.omg.WfBase.BaseException;
  String p_process_mgr_version () throws org.omg.WfBase.BaseException;
} // interface WfCreateProcessEventAuditOperations
