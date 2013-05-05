package org.enhydra.shark.corba.ExpressionBuilders;


/**
* org/enhydra/shark/corba/ExpressionBuilders/_ActivityIteratorExpressionBuilderStub.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 ExpressionBuilders.idl
* 2009年2月3日 星期二 下午05时32分30秒 CST
*/

public class _ActivityIteratorExpressionBuilderStub extends org.omg.CORBA.portable.ObjectImpl implements org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder
{

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder and ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("and", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder or ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("or", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder not ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("not", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addPackageIdEquals (String exp)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addPackageIdEquals", true);
                $out.write_wstring (exp);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessDefIdEquals (String exp)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addProcessDefIdEquals", true);
                $out.write_wstring (exp);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addMgrNameEquals (String exp)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addMgrNameEquals", true);
                $out.write_wstring (exp);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addVersionEquals (String exp)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addVersionEquals", true);
                $out.write_wstring (exp);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addIsMgrEnabled ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addIsMgrEnabled", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessStateEquals (String arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addProcessStateEquals", true);
                $out.write_wstring (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addProcessStateEquals (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addProcessStateEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessStateStartsWith (String arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addProcessStateStartsWith", true);
                $out.write_wstring (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addProcessStateStartsWith (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addProcessStateStartsWith

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessIdEquals (String arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addProcessIdEquals", true);
                $out.write_wstring (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addProcessIdEquals (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addProcessIdEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessNameEquals (String arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addProcessNameEquals", true);
                $out.write_wstring (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addProcessNameEquals (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addProcessNameEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessPriorityEquals (int arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addProcessPriorityEquals", true);
                $out.write_long (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addProcessPriorityEquals (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addProcessPriorityEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessDescriptionEquals (String arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addProcessDescriptionEquals", true);
                $out.write_wstring (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addProcessDescriptionEquals (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addProcessDescriptionEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessDescriptionContains (String arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addProcessDescriptionContains", true);
                $out.write_wstring (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addProcessDescriptionContains (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addProcessDescriptionContains

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessRequesterIdEquals (String arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addProcessRequesterIdEquals", true);
                $out.write_wstring (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addProcessRequesterIdEquals (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addProcessRequesterIdEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessCreatedTimeEquals (long arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addProcessCreatedTimeEquals", true);
                $out.write_longlong (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addProcessCreatedTimeEquals (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addProcessCreatedTimeEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessCreatedTimeBefore (long arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addProcessCreatedTimeBefore", true);
                $out.write_longlong (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addProcessCreatedTimeBefore (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addProcessCreatedTimeBefore

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessCreatedTimeAfter (long arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addProcessCreatedTimeAfter", true);
                $out.write_longlong (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addProcessCreatedTimeAfter (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addProcessCreatedTimeAfter

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessStartTimeEquals (long arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addProcessStartTimeEquals", true);
                $out.write_longlong (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addProcessStartTimeEquals (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addProcessStartTimeEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessStartTimeBefore (long arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addProcessStartTimeBefore", true);
                $out.write_longlong (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addProcessStartTimeBefore (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addProcessStartTimeBefore

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessStartTimeAfter (long arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addProcessStartTimeAfter", true);
                $out.write_longlong (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addProcessStartTimeAfter (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addProcessStartTimeAfter

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessLastStateTimeEquals (long arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addProcessLastStateTimeEquals", true);
                $out.write_longlong (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addProcessLastStateTimeEquals (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addProcessLastStateTimeEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessLastStateTimeBefore (long arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addProcessLastStateTimeBefore", true);
                $out.write_longlong (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addProcessLastStateTimeBefore (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addProcessLastStateTimeBefore

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessLastStateTimeAfter (long arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addProcessLastStateTimeAfter", true);
                $out.write_longlong (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addProcessLastStateTimeAfter (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addProcessLastStateTimeAfter

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessVariableAnyEquals (String vName, org.omg.CORBA.Any vValue) throws org.omg.WfBase.BaseException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addProcessVariableAnyEquals", true);
                $out.write_wstring (vName);
                $out.write_any (vValue);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addProcessVariableAnyEquals (vName, vValue        );
            } finally {
                _releaseReply ($in);
            }
  } // addProcessVariableAnyEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessVariableStrEquals (String vName, String vValue)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addProcessVariableStrEquals", true);
                $out.write_wstring (vName);
                $out.write_wstring (vValue);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addProcessVariableStrEquals (vName, vValue        );
            } finally {
                _releaseReply ($in);
            }
  } // addProcessVariableStrEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessVariableLngEquals (String vName, long vValue)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addProcessVariableLngEquals", true);
                $out.write_wstring (vName);
                $out.write_longlong (vValue);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addProcessVariableLngEquals (vName, vValue        );
            } finally {
                _releaseReply ($in);
            }
  } // addProcessVariableLngEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessVariableLngGreaterThan (String vName, long vValue)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addProcessVariableLngGreaterThan", true);
                $out.write_wstring (vName);
                $out.write_longlong (vValue);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addProcessVariableLngGreaterThan (vName, vValue        );
            } finally {
                _releaseReply ($in);
            }
  } // addProcessVariableLngGreaterThan

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessVariableLngLessThan (String vName, long vValue)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addProcessVariableLngLessThan", true);
                $out.write_wstring (vName);
                $out.write_longlong (vValue);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addProcessVariableLngLessThan (vName, vValue        );
            } finally {
                _releaseReply ($in);
            }
  } // addProcessVariableLngLessThan

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessVariableDblEquals (String vName, double vValue)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addProcessVariableDblEquals", true);
                $out.write_wstring (vName);
                $out.write_double (vValue);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addProcessVariableDblEquals (vName, vValue        );
            } finally {
                _releaseReply ($in);
            }
  } // addProcessVariableDblEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessVariableDblGreaterThan (String vName, double vValue)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addProcessVariableDblGreaterThan", true);
                $out.write_wstring (vName);
                $out.write_double (vValue);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addProcessVariableDblGreaterThan (vName, vValue        );
            } finally {
                _releaseReply ($in);
            }
  } // addProcessVariableDblGreaterThan

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessVariableDblLessThan (String vName, double vValue)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addProcessVariableDblLessThan", true);
                $out.write_wstring (vName);
                $out.write_double (vValue);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addProcessVariableDblLessThan (vName, vValue        );
            } finally {
                _releaseReply ($in);
            }
  } // addProcessVariableDblLessThan

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addStateEquals (String arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addStateEquals", true);
                $out.write_wstring (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addStateStartsWith (String arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addStateStartsWith", true);
                $out.write_wstring (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addIdEquals (String arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addIdEquals", true);
                $out.write_wstring (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addNameEquals (String arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addNameEquals", true);
                $out.write_wstring (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addPriorityEquals (int arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addPriorityEquals", true);
                $out.write_long (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addDescriptionEquals (String arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addDescriptionEquals", true);
                $out.write_wstring (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addDescriptionContains (String arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addDescriptionContains", true);
                $out.write_wstring (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addActivatedTimeEquals (long arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addActivatedTimeEquals", true);
                $out.write_longlong (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addActivatedTimeEquals (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addActivatedTimeEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addActivatedTimeBefore (long arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addActivatedTimeBefore", true);
                $out.write_longlong (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addActivatedTimeBefore (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addActivatedTimeBefore

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addActivatedTimeAfter (long arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addActivatedTimeAfter", true);
                $out.write_longlong (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addActivatedTimeAfter (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addActivatedTimeAfter

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addLastStateTimeEquals (long arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addLastStateTimeEquals", true);
                $out.write_longlong (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addLastStateTimeBefore (long arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addLastStateTimeBefore", true);
                $out.write_longlong (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addLastStateTimeAfter (long arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addLastStateTimeAfter", true);
                $out.write_longlong (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addAcceptedTimeEquals (long arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addAcceptedTimeEquals", true);
                $out.write_longlong (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addAcceptedTimeEquals (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addAcceptedTimeEquals

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addAcceptedTimeBefore (long arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addAcceptedTimeBefore", true);
                $out.write_longlong (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addAcceptedTimeBefore (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addAcceptedTimeBefore

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addAcceptedTimeAfter (long arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addAcceptedTimeAfter", true);
                $out.write_longlong (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addAcceptedTimeAfter (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addAcceptedTimeAfter

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addVariableAnyEquals (String vName, org.omg.CORBA.Any vValue) throws org.omg.WfBase.BaseException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addVariableAnyEquals", true);
                $out.write_wstring (vName);
                $out.write_any (vValue);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addVariableStrEquals (String vName, String vValue)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addVariableStrEquals", true);
                $out.write_wstring (vName);
                $out.write_wstring (vValue);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addVariableLngEquals (String vName, long vValue)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addVariableLngEquals", true);
                $out.write_wstring (vName);
                $out.write_longlong (vValue);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addVariableLngGreaterThan (String vName, long vValue)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addVariableLngGreaterThan", true);
                $out.write_wstring (vName);
                $out.write_longlong (vValue);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addVariableLngLessThan (String vName, long vValue)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addVariableLngLessThan", true);
                $out.write_wstring (vName);
                $out.write_longlong (vValue);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addVariableDblEquals (String vName, double vValue)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addVariableDblEquals", true);
                $out.write_wstring (vName);
                $out.write_double (vValue);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addVariableDblGreaterThan (String vName, double vValue)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addVariableDblGreaterThan", true);
                $out.write_wstring (vName);
                $out.write_double (vValue);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addVariableDblLessThan (String vName, double vValue)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addVariableDblLessThan", true);
                $out.write_wstring (vName);
                $out.write_double (vValue);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addActivitySetDefId (String arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addActivitySetDefId", true);
                $out.write_wstring (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addActivitySetDefId (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addActivitySetDefId

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addDefinitionId (String arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addDefinitionId", true);
                $out.write_wstring (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addDefinitionId (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addDefinitionId

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addIsAccepted ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addIsAccepted", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addResourceUsername (String arg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addResourceUsername", true);
                $out.write_wstring (arg);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addResourceUsername (arg        );
            } finally {
                _releaseReply ($in);
            }
  } // addResourceUsername

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addExpressionStr (String exp)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addExpressionStr", true);
                $out.write_wstring (exp);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addExpression (org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder eb)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addExpression", true);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write ($out, eb);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder setOrderById (boolean ascending)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("setOrderById", true);
                $out.write_boolean (ascending);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder setOrderByActivitySetDefId (boolean ascending)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("setOrderByActivitySetDefId", true);
                $out.write_boolean (ascending);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return setOrderByActivitySetDefId (ascending        );
            } finally {
                _releaseReply ($in);
            }
  } // setOrderByActivitySetDefId

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder setOrderByDefinitionId (boolean ascending)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("setOrderByDefinitionId", true);
                $out.write_boolean (ascending);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return setOrderByDefinitionId (ascending        );
            } finally {
                _releaseReply ($in);
            }
  } // setOrderByDefinitionId

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder setOrderByProcessId (boolean ascending)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("setOrderByProcessId", true);
                $out.write_boolean (ascending);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder setOrderByResourceUsername (boolean ascending)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("setOrderByResourceUsername", true);
                $out.write_boolean (ascending);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return setOrderByResourceUsername (ascending        );
            } finally {
                _releaseReply ($in);
            }
  } // setOrderByResourceUsername

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder setOrderByProcessDefName (boolean ascending)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("setOrderByProcessDefName", true);
                $out.write_boolean (ascending);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return setOrderByProcessDefName (ascending        );
            } finally {
                _releaseReply ($in);
            }
  } // setOrderByProcessDefName

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder setOrderByState (boolean ascending)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("setOrderByState", true);
                $out.write_boolean (ascending);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder setOrderByPerformer (boolean ascending)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("setOrderByPerformer", true);
                $out.write_boolean (ascending);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return setOrderByPerformer (ascending        );
            } finally {
                _releaseReply ($in);
            }
  } // setOrderByPerformer

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder setOrderByPriority (boolean ascending)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("setOrderByPriority", true);
                $out.write_boolean (ascending);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder setOrderByName (boolean ascending)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("setOrderByName", true);
                $out.write_boolean (ascending);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
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

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder setOrderByActivatedTime (boolean ascending)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("setOrderByActivatedTime", true);
                $out.write_boolean (ascending);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return setOrderByActivatedTime (ascending        );
            } finally {
                _releaseReply ($in);
            }
  } // setOrderByActivatedTime

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder setOrderByAcceptedTime (boolean ascending)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("setOrderByAcceptedTime", true);
                $out.write_boolean (ascending);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return setOrderByAcceptedTime (ascending        );
            } finally {
                _releaseReply ($in);
            }
  } // setOrderByAcceptedTime

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder setOrderByLastStateTime (boolean ascending)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("setOrderByLastStateTime", true);
                $out.write_boolean (ascending);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
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
    "IDL:ExpressionBuilders/ActivityIteratorExpressionBuilder:1.0", 
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
} // class _ActivityIteratorExpressionBuilderStub
