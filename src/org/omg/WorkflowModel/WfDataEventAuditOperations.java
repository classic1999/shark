package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/WfDataEventAuditOperations.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public interface WfDataEventAuditOperations  extends org.omg.WorkflowModel.WfEventAuditOperations
{
  org.omg.WfBase.NameValue[] old_data () throws org.omg.WfBase.BaseException;
  org.omg.WfBase.NameValue[] new_data () throws org.omg.WfBase.BaseException;
} // interface WfDataEventAuditOperations
