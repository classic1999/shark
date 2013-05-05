package org.enhydra.shark.corba.ExpressionBuilders;


/**
* org/enhydra/shark/corba/ExpressionBuilders/_ProcessMgrIteratorExpressionBuilderStub.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 ExpressionBuilders.idl
* 2009年2月3日 星期二 下午05时32分30秒 CST
*/

public class _ProcessMgrIteratorExpressionBuilderStub extends org.omg.CORBA.portable.ObjectImpl implements org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder
{

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder and ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("and", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return and (        );
            } finally {
                _releaseReply ($in);
            }
  } // and

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder or ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("or", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return or (        );
            } finally {
                _releaseReply ($in);
            }
  } // or

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder not ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("not", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return not (        );
            } finally {
                _releaseReply ($in);
            }
  } // not

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder addPackageIdEquals (String exp)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addPackageIdEquals", true);
                $out.write_wstring (exp);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addPackageIdEquals (exp        );
            } finally {
                _releaseReply ($in);
            }
  } // addPackageIdEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder addProcessDefIdEquals (String exp)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addProcessDefIdEquals", true);
                $out.write_wstring (exp);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addProcessDefIdEquals (exp        );
            } finally {
                _releaseReply ($in);
            }
  } // addProcessDefIdEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder addNameEquals (String exp)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addNameEquals", true);
                $out.write_wstring (exp);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addNameEquals (exp        );
            } finally {
                _releaseReply ($in);
            }
  } // addNameEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder addVersionEquals (String exp)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addVersionEquals", true);
                $out.write_wstring (exp);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addVersionEquals (exp        );
            } finally {
                _releaseReply ($in);
            }
  } // addVersionEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder addCreatedTimeEquals (long arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addCreatedTimeEquals", true);
                $out.write_longlong (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addCreatedTimeEquals (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addCreatedTimeEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder addCreatedTimeBefore (long arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addCreatedTimeBefore", true);
                $out.write_longlong (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addCreatedTimeBefore (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addCreatedTimeBefore

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder addCreatedTimeAfter (long arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addCreatedTimeAfter", true);
                $out.write_longlong (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addCreatedTimeAfter (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addCreatedTimeAfter

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder addIsEnabled ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addIsEnabled", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addIsEnabled (        );
            } finally {
                _releaseReply ($in);
            }
  } // addIsEnabled

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder addExpressionStr (String exp)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addExpressionStr", true);
                $out.write_wstring (exp);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addExpressionStr (exp        );
            } finally {
                _releaseReply ($in);
            }
  } // addExpressionStr

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder addExpression (org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder eb)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addExpression", true);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.write ($out, eb);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addExpression (eb        );
            } finally {
                _releaseReply ($in);
            }
  } // addExpression

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder setOrderByPackageId (boolean ascending)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("setOrderByPackageId", true);
                $out.write_boolean (ascending);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return setOrderByPackageId (ascending        );
            } finally {
                _releaseReply ($in);
            }
  } // setOrderByPackageId

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder setOrderByProcessDefId (boolean ascending)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("setOrderByProcessDefId", true);
                $out.write_boolean (ascending);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return setOrderByProcessDefId (ascending        );
            } finally {
                _releaseReply ($in);
            }
  } // setOrderByProcessDefId

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder setOrderByName (boolean ascending)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("setOrderByName", true);
                $out.write_boolean (ascending);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return setOrderByName (ascending        );
            } finally {
                _releaseReply ($in);
            }
  } // setOrderByName

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder setOrderByVersion (boolean ascending)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("setOrderByVersion", true);
                $out.write_boolean (ascending);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return setOrderByVersion (ascending        );
            } finally {
                _releaseReply ($in);
            }
  } // setOrderByVersion

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder setOrderByCreatedTime (boolean ascending)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("setOrderByCreatedTime", true);
                $out.write_boolean (ascending);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return setOrderByCreatedTime (ascending        );
            } finally {
                _releaseReply ($in);
            }
  } // setOrderByCreatedTime

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder setOrderByEnabled (boolean ascending)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("setOrderByEnabled", true);
                $out.write_boolean (ascending);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return setOrderByEnabled (ascending        );
            } finally {
                _releaseReply ($in);
            }
  } // setOrderByEnabled

  public boolean isComplete ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("isComplete", true);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return isComplete (        );
            } finally {
                _releaseReply ($in);
            }
  } // isComplete

  public String toSQL ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("toSQL", true);
                $in = _invoke ($out);
                String $result = $in.read_wstring ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return toSQL (        );
            } finally {
                _releaseReply ($in);
            }
  } // toSQL

  public String toScript ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("toScript", true);
                $in = _invoke ($out);
                String $result = $in.read_wstring ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return toScript (        );
            } finally {
                _releaseReply ($in);
            }
  } // toScript

  public String toExpression ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("toExpression", true);
                $in = _invoke ($out);
                String $result = $in.read_wstring ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return toExpression (        );
            } finally {
                _releaseReply ($in);
            }
  } // toExpression

  public org.omg.CORBA.Any getTheImpl ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getTheImpl", true);
                $in = _invoke ($out);
                org.omg.CORBA.Any $result = $in.read_any ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getTheImpl (        );
            } finally {
                _releaseReply ($in);
            }
  } // getTheImpl

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:ExpressionBuilders/ProcessMgrIteratorExpressionBuilder:1.0", 
    "IDL:ExpressionBuilders/ExpressionBuilder:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.Object obj = org.omg.CORBA.ORB.init (args, props).string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     String str = org.omg.CORBA.ORB.init (args, props).object_to_string (this);
     s.writeUTF (str);
  }
} // class _ProcessMgrIteratorExpressionBuilderStub
