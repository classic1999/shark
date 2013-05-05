package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/DeadlineInfoSequenceHelper.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowService.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��32�� CST
*/

abstract public class DeadlineInfoSequenceHelper
{
  private static String  _id = "IDL:WorkflowService/DeadlineInfoSequence:1.0";

  public static void insert (org.omg.CORBA.Any a, org.enhydra.shark.corba.WorkflowService.DeadlineInfo[] that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static org.enhydra.shark.corba.WorkflowService.DeadlineInfo[] extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.enhydra.shark.corba.WorkflowService.DeadlineInfoHelper.type ();
      __typeCode = org.omg.CORBA.ORB.init ().create_sequence_tc (0, __typeCode);
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (org.enhydra.shark.corba.WorkflowService.DeadlineInfoSequenceHelper.id (), "DeadlineInfoSequence", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static org.enhydra.shark.corba.WorkflowService.DeadlineInfo[] read (org.omg.CORBA.portable.InputStream istream)
  {
    org.enhydra.shark.corba.WorkflowService.DeadlineInfo value[] = null;
    int _len0 = istream.read_long ();
    value = new org.enhydra.shark.corba.WorkflowService.DeadlineInfo[_len0];
    for (int _o1 = 0;_o1 < value.length; ++_o1)
      value[_o1] = org.enhydra.shark.corba.WorkflowService.DeadlineInfoHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, org.enhydra.shark.corba.WorkflowService.DeadlineInfo[] value)
  {
    ostream.write_long (value.length);
    for (int _i0 = 0;_i0 < value.length; ++_i0)
      org.enhydra.shark.corba.WorkflowService.DeadlineInfoHelper.write (ostream, value[_i0]);
  }

}
