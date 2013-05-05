package org.omg.WfBase;


/**
* org/omg/WfBase/_BaseIteratorImplBase.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WfBase.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��27�� CST
*/

public abstract class _BaseIteratorImplBase extends org.omg.CORBA.portable.ObjectImpl
                implements org.omg.WfBase.BaseIterator, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors
  public _BaseIteratorImplBase ()
  {
  }

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("query_expression", new java.lang.Integer (0));
    _methods.put ("set_query_expression", new java.lang.Integer (1));
    _methods.put ("names_in_expression", new java.lang.Integer (2));
    _methods.put ("set_names_in_expression", new java.lang.Integer (3));
    _methods.put ("query_grammar", new java.lang.Integer (4));
    _methods.put ("set_query_grammar", new java.lang.Integer (5));
    _methods.put ("how_many", new java.lang.Integer (6));
    _methods.put ("goto_start", new java.lang.Integer (7));
    _methods.put ("goto_end", new java.lang.Integer (8));
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
       case 0:  // WfBase/BaseIterator/query_expression
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

       case 1:  // WfBase/BaseIterator/set_query_expression
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

       case 2:  // WfBase/BaseIterator/names_in_expression
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

       case 3:  // WfBase/BaseIterator/set_names_in_expression
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

       case 4:  // WfBase/BaseIterator/query_grammar
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

       case 5:  // WfBase/BaseIterator/set_query_grammar
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

       case 6:  // WfBase/BaseIterator/how_many
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

       case 7:  // WfBase/BaseIterator/goto_start
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

       case 8:  // WfBase/BaseIterator/goto_end
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
    "IDL:omg.org/WfBase/BaseIterator:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }


} // class _BaseIteratorImplBase
