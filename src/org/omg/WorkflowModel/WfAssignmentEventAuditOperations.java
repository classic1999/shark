package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/WfAssignmentEventAuditOperations.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public interface WfAssignmentEventAuditOperations  extends org.omg.WorkflowModel.WfEventAuditOperations
{
  String old_resource_key () throws org.omg.WfBase.BaseException;
  String old_resource_name () throws org.omg.WfBase.BaseException;
  String new_resource_key () throws org.omg.WfBase.BaseException;
  String new_resource_name () throws org.omg.WfBase.BaseException;
  boolean is_accepted () throws org.omg.WfBase.BaseException;
} // interface WfAssignmentEventAuditOperations
