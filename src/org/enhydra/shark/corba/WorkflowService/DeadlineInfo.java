package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/DeadlineInfo.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分32秒 CST
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
