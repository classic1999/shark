package org.omg.TimeBase;


/**
* org/omg/TimeBase/UtcT.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� TimeBase.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��26�� CST
*/

public final class UtcT implements org.omg.CORBA.portable.IDLEntity
{
  public long time = (long)0;

  // 8 octets
  public int inacclo = (int)0;

  // 4 octets
  public short inacchi = (short)0;

  // 2 octets
  public short tdf = (short)0;

  public UtcT ()
  {
  } // ctor

  public UtcT (long _time, int _inacclo, short _inacchi, short _tdf)
  {
    time = _time;
    inacclo = _inacclo;
    inacchi = _inacchi;
    tdf = _tdf;
  } // ctor

} // class UtcT
