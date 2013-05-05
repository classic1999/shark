package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/DeadlineInfo.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowService.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��32�� CST
*/

public final class DeadlineInfo implements org.omg.CORBA.portable.IDLEntity
{
  public String procId = null;
  public String actId = null;
  public boolean isExecuted = false;
  public long timeLimit = (long)0;
  public String exceptionName = null;
  public boolean isSynchronous = false;

  public DeadlineInfo ()
  {
  } // ctor

  public DeadlineInfo (String _procId, String _actId, boolean _isExecuted, long _timeLimit, String _exceptionName, boolean _isSynchronous)
  {
    procId = _procId;
    actId = _actId;
    isExecuted = _isExecuted;
    timeLimit = _timeLimit;
    exceptionName = _exceptionName;
    isSynchronous = _isSynchronous;
  } // ctor

} // class DeadlineInfo
