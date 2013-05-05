package org.omg.WfBase;


/**
* org/omg/WfBase/BaseBusinessObjectPOA.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WfBase.idl
* 2009年2月3日 星期二 下午05时32分27秒 CST
*/


// Interfaces
public abstract class BaseBusinessObjectPOA extends org.omg.PortableServer.Servant
 implements org.omg.WfBase.BaseBusinessObjectOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:omg.org/WfBase/BaseBusinessObject:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public BaseBusinessObject _this() 
  {
    return BaseBusinessObjectHelper.narrow(
    super._this_object());
  }

  public BaseBusinessObject _this(org.omg.CORBA.ORB orb) 
  {
    return BaseBusinessObjectHelper.narrow(
    super._this_object(orb));
  }


} // class BaseBusinessObjectPOA
