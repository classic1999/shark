package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/WfEventAuditIteratorHelper.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

abstract public class WfEventAuditIteratorHelper
{
  private static String  _id = "IDL:omg.org/WorkflowModel/WfEventAuditIterator:1.0";

  public static void insert (org.omg.CORBA.Any a, org.omg.WorkflowModel.WfEventAuditIterator that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static org.omg.WorkflowModel.WfEventAuditIterator extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (org.omg.WorkflowModel.WfEventAuditIteratorHelper.id (), "WfEventAuditIterator");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static org.omg.WorkflowModel.WfEventAuditIterator read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_WfEventAuditIteratorStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, org.omg.WorkflowModel.WfEventAuditIterator value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static org.omg.WorkflowModel.WfEventAuditIterator narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof org.omg.WorkflowModel.WfEventAuditIterator)
      return (org.omg.WorkflowModel.WfEventAuditIterator)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      org.omg.WorkflowModel._WfEventAuditIteratorStub stub = new org.omg.WorkflowModel._WfEventAuditIteratorStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static org.omg.WorkflowModel.WfEventAuditIterator unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof org.omg.WorkflowModel.WfEventAuditIterator)
      return (org.omg.WorkflowModel.WfEventAuditIterator)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      org.omg.WorkflowModel._WfEventAuditIteratorStub stub = new org.omg.WorkflowModel._WfEventAuditIteratorStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
