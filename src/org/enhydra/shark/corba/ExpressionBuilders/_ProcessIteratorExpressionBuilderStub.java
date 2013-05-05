package org.enhydra.shark.corba.ExpressionBuilders;


/**
* org/enhydra/shark/corba/ExpressionBuilders/_ProcessIteratorExpressionBuilderStub.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 ExpressionBuilders.idl
* 2009年2月3日 星期二 下午05时32分30秒 CST
*/

public class _ProcessIteratorExpressionBuilderStub extends org.omg.CORBA.portable.ObjectImpl implements org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder
{

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder and ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("and", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder or ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("or", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder not ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("not", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addPackageIdEquals (String exp)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addPackageIdEquals", true);
                $out.write_wstring (exp);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addProcessDefIdEquals (String exp)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addProcessDefIdEquals", true);
                $out.write_wstring (exp);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addMgrNameEquals (String exp)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addMgrNameEquals", true);
                $out.write_wstring (exp);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addMgrNameEquals (exp        );
            } finally {
                _releaseReply ($in);
            }
  } // addMgrNameEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addVersionEquals (String exp)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addVersionEquals", true);
                $out.write_wstring (exp);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addIsMgrEnabled ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addIsMgrEnabled", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addIsMgrEnabled (        );
            } finally {
                _releaseReply ($in);
            }
  } // addIsMgrEnabled

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addStateEquals (String arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addStateEquals", true);
                $out.write_wstring (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addStateEquals (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addStateEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addStateStartsWith (String arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addStateStartsWith", true);
                $out.write_wstring (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addStateStartsWith (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addStateStartsWith

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addIdEquals (String arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addIdEquals", true);
                $out.write_wstring (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addIdEquals (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addIdEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addNameEquals (String arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addNameEquals", true);
                $out.write_wstring (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addNameEquals (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addNameEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addPriorityEquals (int arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addPriorityEquals", true);
                $out.write_long (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addPriorityEquals (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addPriorityEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addDescriptionEquals (String arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addDescriptionEquals", true);
                $out.write_wstring (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addDescriptionEquals (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addDescriptionEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addDescriptionContains (String arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addDescriptionContains", true);
                $out.write_wstring (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addDescriptionContains (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addDescriptionContains

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addRequesterIdEquals (String arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addRequesterIdEquals", true);
                $out.write_wstring (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addRequesterIdEquals (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addRequesterIdEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addRequesterUsernameEquals (String arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addRequesterUsernameEquals", true);
                $out.write_wstring (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addRequesterUsernameEquals (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addRequesterUsernameEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addCreatedTimeEquals (long arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addCreatedTimeEquals", true);
                $out.write_longlong (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addCreatedTimeBefore (long arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addCreatedTimeBefore", true);
                $out.write_longlong (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addCreatedTimeAfter (long arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addCreatedTimeAfter", true);
                $out.write_longlong (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addStartTimeEquals (long arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addStartTimeEquals", true);
                $out.write_longlong (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addStartTimeEquals (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addStartTimeEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addStartTimeBefore (long arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addStartTimeBefore", true);
                $out.write_longlong (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addStartTimeBefore (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addStartTimeBefore

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addStartTimeAfter (long arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addStartTimeAfter", true);
                $out.write_longlong (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addStartTimeAfter (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addStartTimeAfter

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addLastStateTimeEquals (long arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addLastStateTimeEquals", true);
                $out.write_longlong (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addLastStateTimeEquals (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addLastStateTimeEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addLastStateTimeBefore (long arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addLastStateTimeBefore", true);
                $out.write_longlong (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addLastStateTimeBefore (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addLastStateTimeBefore

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addLastStateTimeAfter (long arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addLastStateTimeAfter", true);
                $out.write_longlong (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addLastStateTimeAfter (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addLastStateTimeAfter

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addActiveActivitiesCountEquals (long arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addActiveActivitiesCountEquals", true);
                $out.write_longlong (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addActiveActivitiesCountEquals (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addActiveActivitiesCountEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addActiveActivitiesCountGreaterThan (long arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addActiveActivitiesCountGreaterThan", true);
                $out.write_longlong (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addActiveActivitiesCountGreaterThan (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addActiveActivitiesCountGreaterThan

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addActiveActivitiesCountLessThan (long arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addActiveActivitiesCountLessThan", true);
                $out.write_longlong (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addActiveActivitiesCountLessThan (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addActiveActivitiesCountLessThan

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addVariableAnyEquals (String vName, org.omg.CORBA.Any vValue) throws org.omg.WfBase.BaseException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addVariableAnyEquals", true);
                $out.write_wstring (vName);
                $out.write_any (vValue);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addVariableAnyEquals (vName, vValue        );
            } finally {
                _releaseReply ($in);
            }
  } // addVariableAnyEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addVariableStrEquals (String vName, String vValue)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addVariableStrEquals", true);
                $out.write_wstring (vName);
                $out.write_wstring (vValue);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addVariableStrEquals (vName, vValue        );
            } finally {
                _releaseReply ($in);
            }
  } // addVariableStrEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addVariableLngEquals (String vName, long vValue)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addVariableLngEquals", true);
                $out.write_wstring (vName);
                $out.write_longlong (vValue);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addVariableLngEquals (vName, vValue        );
            } finally {
                _releaseReply ($in);
            }
  } // addVariableLngEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addVariableLngGreaterThan (String vName, long vValue)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addVariableLngGreaterThan", true);
                $out.write_wstring (vName);
                $out.write_longlong (vValue);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addVariableLngGreaterThan (vName, vValue        );
            } finally {
                _releaseReply ($in);
            }
  } // addVariableLngGreaterThan

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addVariableLngLessThan (String vName, long vValue)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addVariableLngLessThan", true);
                $out.write_wstring (vName);
                $out.write_longlong (vValue);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addVariableLngLessThan (vName, vValue        );
            } finally {
                _releaseReply ($in);
            }
  } // addVariableLngLessThan

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addVariableDblEquals (String vName, double vValue)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addVariableDblEquals", true);
                $out.write_wstring (vName);
                $out.write_double (vValue);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addVariableDblEquals (vName, vValue        );
            } finally {
                _releaseReply ($in);
            }
  } // addVariableDblEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addVariableDblGreaterThan (String vName, double vValue)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addVariableDblGreaterThan", true);
                $out.write_wstring (vName);
                $out.write_double (vValue);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addVariableDblGreaterThan (vName, vValue        );
            } finally {
                _releaseReply ($in);
            }
  } // addVariableDblGreaterThan

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addVariableDblLessThan (String vName, double vValue)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addVariableDblLessThan", true);
                $out.write_wstring (vName);
                $out.write_double (vValue);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addVariableDblLessThan (vName, vValue        );
            } finally {
                _releaseReply ($in);
            }
  } // addVariableDblLessThan

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addExpressionStr (String exp)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addExpressionStr", true);
                $out.write_wstring (exp);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addExpression (org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder eb)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addExpression", true);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write ($out, eb);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder setOrderByMgrName (boolean ascending)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("setOrderByMgrName", true);
                $out.write_boolean (ascending);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return setOrderByMgrName (ascending        );
            } finally {
                _releaseReply ($in);
            }
  } // setOrderByMgrName

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder setOrderById (boolean ascending)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("setOrderById", true);
                $out.write_boolean (ascending);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return setOrderById (ascending        );
            } finally {
                _releaseReply ($in);
            }
  } // setOrderById

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder setOrderByName (boolean ascending)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("setOrderByName", true);
                $out.write_boolean (ascending);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder setOrderByState (boolean ascending)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("setOrderByState", true);
                $out.write_boolean (ascending);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return setOrderByState (ascending        );
            } finally {
                _releaseReply ($in);
            }
  } // setOrderByState

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder setOrderByPriority (boolean ascending)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("setOrderByPriority", true);
                $out.write_boolean (ascending);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return setOrderByPriority (ascending        );
            } finally {
                _releaseReply ($in);
            }
  } // setOrderByPriority

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder setOrderByCreatedTime (boolean ascending)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("setOrderByCreatedTime", true);
                $out.write_boolean (ascending);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder setOrderByStartTime (boolean ascending)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("setOrderByStartTime", true);
                $out.write_boolean (ascending);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return setOrderByStartTime (ascending        );
            } finally {
                _releaseReply ($in);
            }
  } // setOrderByStartTime

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder setOrderByLastStateTime (boolean ascending)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("setOrderByLastStateTime", true);
                $out.write_boolean (ascending);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return setOrderByLastStateTime (ascending        );
            } finally {
                _releaseReply ($in);
            }
  } // setOrderByLastStateTime

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder setOrderByResourceRequesterId (boolean ascending)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("setOrderByResourceRequesterId", true);
                $out.write_boolean (ascending);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return setOrderByResourceRequesterId (ascending        );
            } finally {
                _releaseReply ($in);
            }
  } // setOrderByResourceRequesterId

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
    "IDL:ExpressionBuilders/ProcessIteratorExpressionBuilder:1.0", 
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
} // class _ProcessIteratorExpressionBuilderStub
