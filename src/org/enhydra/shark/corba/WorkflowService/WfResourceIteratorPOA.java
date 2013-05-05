package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/WfResourceIteratorPOA.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowService.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��32�� CST
*/

public abstract class WfResourceIteratorPOA extends org.omg.PortableServer.Servant
 implements org.enhydra.shark.corba.WorkflowService.WfResourceIteratorOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("get_next_object", new java.lang.Integer (0));
    _methods.put ("get_previous_object", new java.lang.Integer (1));
    _methods.put ("get_next_n_sequence", new java.lang.Integer (2));
    _methods.put ("get_previous_n_sequence", new java.lang.Integer (3));
    _methods.put ("query_expression", new java.lang.Integer (4));
    _methods.put ("set_query_expression", new java.lang.Integer (5));
    _methods.put ("names_in_expression", new java.lang.Integer (6));
    _methods.put ("set_names_in_expression", new java.lang.Integer (7));
    _methods.put ("query_grammar", new java.lang.Integer (8));
    _methods.put ("set_query_grammar", new java.lang.Integer (9));
    _methods.put ("how_many", new java.lang.Integer (10));
    _methods.put ("goto_start", new java.lang.Integer (11));
    _methods.put ("goto_end", new java.lang.Integer (12));
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
       case 0:  // WorkflowService/WfResourceIterator/get_next_object
       {
         try {
           org.omg.WorkflowModel.WfResource $result = null;
           $result = this.get_next_object ();
           out = $rh.createReply();
           org.omg.WorkflowModel.WfResourceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 1:  // WorkflowService/WfResourceIterator/get_previous_object
       {
         try {
           org.omg.WorkflowModel.WfResource $result = null;
           $result = this.get_previous_object ();
           out = $rh.createReply();
           org.omg.WorkflowModel.WfResourceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 2:  // WorkflowService/WfResourceIterator/get_next_n_sequence
       {
         try {
           int max_number = in.read_long ();
           org.omg.WorkflowModel.WfResource $result[] = null;
           $result = this.get_next_n_sequence (max_number);
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.WfResourceSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 3:  // WorkflowService/WfResourceIterator/get_previous_n_sequence
       {
         try {
           int max_number = in.read_long ();
           org.omg.WorkflowModel.WfResource $result[] = null;
           $result = this.get_previous_n_sequence (max_number);
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.WfResourceSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 4:  // WfBase/BaseIterator/query_expression
       {
         try {
           String $result = null;
           $result = this.query_expression ();
           out = $rh.createReply();
           out.write_wstring ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 5:  // WfBase/BaseIterator/set_query_expression
       {
         try {
           String query = in.read_wstring ();
           this.set_query_expression (query);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.omg.WfBase.InvalidQuery $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.InvalidQueryHelper.write (out, $ex);
         }
         break;
       }

       case 6:  // WfBase/BaseIterator/names_in_expression
       {
         try {
           org.omg.WfBase.NameValue $result[] = null;
           $result = this.names_in_expression ();
           out = $rh.createReply();
           org.omg.WfBase.NameValueSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 7:  // WfBase/BaseIterator/set_names_in_expression
       {
         try {
           org.omg.WfBase.NameValue query[] = org.omg.WfBase.NameValueSequenceHelper.read (in);
           this.set_names_in_expression (query);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.omg.WfBase.NameMismatch $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.NameMismatchHelper.write (out, $ex);
         }
         break;
       }

       case 8:  // WfBase/BaseIterator/query_grammar
       {
         try {
           String $result = null;
           $result = this.query_grammar ();
           out = $rh.createReply();
           out.write_wstring ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 9:  // WfBase/BaseIterator/set_query_grammar
       {
         try {
           String query_grammmar = in.read_wstring ();
           this.set_query_grammar (query_grammmar);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.omg.WfBase.GrammarNotSupported $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.GrammarNotSupportedHelper.write (out, $ex);
         }
         break;
       }

       case 10:  // WfBase/BaseIterator/how_many
       {
         try {
           int $result = (int)0;
           $result = this.how_many ();
           out = $rh.createReply();
           out.write_long ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 11:  // WfBase/BaseIterator/goto_start
       {
         try {
           this.goto_start ();
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 12:  // WfBase/BaseIterator/goto_end
       {
         try {
           this.goto_end ();
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
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
    "IDL:WorkflowService/WfResourceIterator:1.0", 
    "IDL:omg.org/WfBase/BaseIterator:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public WfResourceIterator _this() 
  {
    return WfResourceIteratorHelper.narrow(
    super._this_object());
  }

  public WfResourceIterator _this(org.omg.CORBA.ORB orb) 
  {
    return WfResourceIteratorHelper.narrow(
    super._this_object(orb));
  }


} // class WfResourceIteratorPOA
