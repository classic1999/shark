package org.omg.TimeBase;


/**
* org/omg/TimeBase/UtcT.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 TimeBase.idl
* 2009年2月3日 星期二 下午05时32分26秒 CST
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
