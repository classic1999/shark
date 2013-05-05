package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/PackageHasActiveProcessesHelper.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowService.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��32�� CST
*/

abstract public class PackageHasActiveProcessesHelper
{
  private static String  _id = "IDL:WorkflowService/PackageHasActiveProcesses:1.0";

  public static void insert (org.omg.CORBA.Any a, org.enhydra.shark.corba.WorkflowService.PackageHasActiveProcesses that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static org.enhydra.shark.corba.WorkflowService.PackageHasActiveProcesses extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [0];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          __typeCode = org.omg.CORBA.ORB.init ().create_exception_tc (org.enhydra.shark.corba.WorkflowService.PackageHasActiveProcessesHelper.id (), "PackageHasActiveProcesses", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static org.enhydra.shark.corba.WorkflowService.PackageHasActiveProcesses read (org.omg.CORBA.portable.InputStream istream)
  {
    org.enhydra.shark.corba.WorkflowService.PackageHasActiveProcesses value = new org.enhydra.shark.corba.WorkflowService.PackageHasActiveProcesses ();
    // read and discard the repository ID
    istream.read_string ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, org.enhydra.shark.corba.WorkflowService.PackageHasActiveProcesses value)
  {
    // write the repository ID
    ostream.write_string (id ());
  }

}