package org.omg.WfBase;


/**
* org/omg/WfBase/GrammarNotSupported.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WfBase.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��27�� CST
*/

public final class GrammarNotSupported extends org.omg.CORBA.UserException
{

  public GrammarNotSupported ()
  {
    super(GrammarNotSupportedHelper.id());
  } // ctor


  public GrammarNotSupported (String $reason)
  {
    super(GrammarNotSupportedHelper.id() + "  " + $reason);
  } // ctor

} // class GrammarNotSupported
