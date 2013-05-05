package org.enhydra.shark.corba.ExpressionBuilders;


/**
* org/enhydra/shark/corba/ExpressionBuilders/_ResourceIteratorExpressionBuilderStub.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 ExpressionBuilders.idl
* 2009年2月3日 星期二 下午05时32分30秒 CST
*/

public class _ResourceIteratorExpressionBuilderStub extends org.omg.CORBA.portable.ObjectImpl implements org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder
{

  public org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder and ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("and", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder or ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("or", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder not ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("not", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder addUsernameEquals (String un)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addUsernameEquals", true);
                $out.write_wstring (un);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addUsernameEquals (un        );
            } finally {
                _releaseReply ($in);
            }
  } // addUsernameEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder addAssignemtCountEquals (long cnt)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addAssignemtCountEquals", true);
                $out.write_longlong (cnt);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addAssignemtCountEquals (cnt        );
            } finally {
                _releaseReply ($in);
            }
  } // addAssignemtCountEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder addAssignemtCountLessThan (long cnt)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addAssignemtCountLessThan", true);
                $out.write_longlong (cnt);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addAssignemtCountLessThan (cnt        );
            } finally {
                _releaseReply ($in);
            }
  } // addAssignemtCountLessThan

  public org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder addAssignemtCountGreaterThan (long cnt)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addAssignemtCountGreaterThan", true);
                $out.write_longlong (cnt);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addAssignemtCountGreaterThan (cnt        );
            } finally {
                _releaseReply ($in);
            }
  } // addAssignemtCountGreaterThan

  public org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder addExpressionStr (String exp)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addExpressionStr", true);
                $out.write_wstring (exp);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder addExpression (org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder eb)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addExpression", true);
                org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilderHelper.write ($out, eb);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder setOrderByUsername (boolean ascending)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("setOrderByUsername", true);
                $out.write_boolean (ascending);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return setOrderByUsername (ascending        );
            } finally {
                _releaseReply ($in);
            }
  } // setOrderByUsername

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
    "IDL:ExpressionBuilders/ResourceIteratorExpressionBuilder:1.0", 
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
} // class _ResourceIteratorExpressionBuilderStub
