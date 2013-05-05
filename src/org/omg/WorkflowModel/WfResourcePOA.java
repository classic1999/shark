package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/WfResourcePOA.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分28秒 CST
*/

public abstract class WfResourcePOA extends org.omg.PortableServer.Servant
 implements org.omg.WorkflowModel.WfResourceOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("how_many_work_item", new java.lang.Integer (0));
    _methods.put ("get_iterator_work_item", new java.lang.Integer (1));
    _methods.put ("get_sequence_work_item", new java.lang.Integer (2));
    _methods.put ("is_member_of_work_items", new java.lang.Integer (3));
    _methods.put ("resource_key", new java.lang.Integer (4));
    _methods.put ("resource_name", new java.lang.Integer (5));
    _methods.put ("release", new java.lang.Integer (6));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // WorkflowModel/WfResource/how_many_work_item
       {
         try {
           int $result = (int)0;
           $result = this.how_many_work_item ();
           out = $rh.createReply();
           out.write_long ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 1:  // WorkflowModel/WfResource/get_iterator_work_item
       {
         try {
           org.omg.WorkflowModel.WfAssignmentIterator $result = null;
           $result = this.get_iterator_work_item ();
           out = $rh.createReply();
           org.omg.WorkflowModel.WfAssignmentIteratorHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 2:  // WorkflowModel/WfResource/get_sequence_work_item
       {
         try {
           int max_number = in.read_long ();
           org.omg.WorkflowModel.WfAssignment $result[] = null;
           $result = this.get_sequence_work_item (max_number);
           out = $rh.createReply();
           org.omg.WorkflowModel.WfAssignmentSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 3:  // WorkflowModel/WfResource/is_member_of_work_items
       {
         try {
           org.omg.WorkflowModel.WfAssignment member = org.omg.WorkflowModel.WfAssignmentHelper.read (in);
           boolean $result = false;
           $result = this.is_member_of_work_items (member);
           out = $rh.createReply();
           out.write_boolean ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 4:  // WorkflowModel/WfResource/resource_key
       {
         try {
           String $result = null;
           $result = this.resource_key ();
           out = $rh.createReply();
           out.write_wstring ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 5:  // WorkflowModel/WfResource/resource_name
       {
         try {
           String $result = null;
           $result = this.resource_name ();
           out = $rh.createReply();
           out.write_wstring ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 6:  // WorkflowModel/WfResource/release
       {
         try {
           org.omg.WorkflowModel.WfAssignment from_assigment = org.omg.WorkflowModel.WfAssignmentHelper.read (in);
           String release_info = in.read_wstring ();
           this.release (from_assigment, release_info);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.omg.WorkflowModel.NotAssigned $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WorkflowModel.NotAssignedHelper.write (out, $ex);
         }
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:omg.org/WorkflowModel/WfResource:1.0", 
    "IDL:omg.org/WfBase/BaseBusinessObject:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public WfResource _this() 
  {
    return WfResourceHelper.narrow(
    super._this_object());
  }

  public WfResource _this(org.omg.CORBA.ORB orb) 
  {
    return WfResourceHelper.narrow(
    super._this_object(orb));
  }


} // class WfResourcePOA
