package org.enhydra.shark.corba.ExpressionBuilders;


/**
* org/enhydra/shark/corba/ExpressionBuilders/_AssignmentIteratorExpressionBuilderStub.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 ExpressionBuilders.idl
* 2009年2月3日 星期二 下午05时32分30秒 CST
*/

public class _AssignmentIteratorExpressionBuilderStub extends org.omg.CORBA.portable.ObjectImpl implements org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder
{

  public org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder and ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("and", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder or ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("or", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder not ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("not", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder addUsernameEquals (String un)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addUsernameEquals", true);
                $out.write_wstring (un);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder addProcessIdEquals (String un)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addProcessIdEquals", true);
                $out.write_wstring (un);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addProcessIdEquals (un        );
            } finally {
                _releaseReply ($in);
            }
  } // addProcessIdEquals

  public org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder addIsAccepted ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addIsAccepted", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addIsAccepted (        );
            } finally {
                _releaseReply ($in);
            }
  } // addIsAccepted

  public org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder addPackageIdEquals (String un)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addPackageIdEquals", true);
                $out.write_wstring (un);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addPackageIdEquals (un        );
            } finally {
                _releaseReply ($in);
            }
  } // addPackageIdEquals

  public org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder addPackageVersionEquals (String un)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addPackageVersionEquals", true);
                $out.write_wstring (un);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addPackageVersionEquals (un        );
            } finally {
                _releaseReply ($in);
            }
  } // addPackageVersionEquals

  public org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder addProcessDefIdEquals (String un)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addProcessDefIdEquals", true);
                $out.write_wstring (un);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addProcessDefIdEquals (un        );
            } finally {
                _releaseReply ($in);
            }
  } // addProcessDefIdEquals

  public org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder addActivitySetDefIdEquals (String un)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addActivitySetDefIdEquals", true);
                $out.write_wstring (un);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addActivitySetDefIdEquals (un        );
            } finally {
                _releaseReply ($in);
            }
  } // addActivitySetDefIdEquals

  public org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder addActivityDefIdEquals (String un)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addActivityDefIdEquals", true);
                $out.write_wstring (un);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addActivityDefIdEquals (un        );
            } finally {
                _releaseReply ($in);
            }
  } // addActivityDefIdEquals

  public org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder addActivityIdEquals (String un)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addActivityIdEquals", true);
                $out.write_wstring (un);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addActivityIdEquals (un        );
            } finally {
                _releaseReply ($in);
            }
  } // addActivityIdEquals

  public org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder setOrderByUsername (boolean ascending)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("setOrderByUsername", true);
                $out.write_boolean (ascending);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder setOrderByProcessId (boolean ascending)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("setOrderByProcessId", true);
                $out.write_boolean (ascending);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return setOrderByProcessId (ascending        );
            } finally {
                _releaseReply ($in);
            }
  } // setOrderByProcessId

  public org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder setOrderByCreatedTime (boolean ascending)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("setOrderByCreatedTime", true);
                $out.write_boolean (ascending);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder setOrderByAccepted (boolean ascending)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("setOrderByAccepted", true);
                $out.write_boolean (ascending);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return setOrderByAccepted (ascending        );
            } finally {
                _releaseReply ($in);
            }
  } // setOrderByAccepted

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
    "IDL:ExpressionBuilders/AssignmentIteratorExpressionBuilder:1.0", 
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
} // class _AssignmentIteratorExpressionBuilderStub
