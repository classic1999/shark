package org.omg.WfBase;


/**
* org/omg/WfBase/NameMismatch.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WfBase.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��27�� CST
*/

public final class NameMismatch extends org.omg.CORBA.UserException
{

  public NameMismatch ()
  {
    super(NameMismatchHelper.id());
  } // ctor


  public NameMismatch (String $reason)
  {
    super(NameMismatchHelper.id() + "  " + $reason);
  } // ctor

} // class NameMismatch
