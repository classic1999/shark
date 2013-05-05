package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/_ExpressionBuilderManagerStub.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分33秒 CST
*/

public class _ExpressionBuilderManagerStub extends org.omg.CORBA.portable.ObjectImpl implements org.enhydra.shark.corba.WorkflowService.ExpressionBuilderManager
{

  public org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder getActivityIteratorExpressionBuilder ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getActivityIteratorExpressionBuilder", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getActivityIteratorExpressionBuilder (        );
            } finally {
                _releaseReply ($in);
            }
  } // getActivityIteratorExpressionBuilder

  public org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder getAssignmentIteratorExpressionBuilder ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getAssignmentIteratorExpressionBuilder", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getAssignmentIteratorExpressionBuilder (        );
            } finally {
                _releaseReply ($in);
            }
  } // getAssignmentIteratorExpressionBuilder

  public org.enhydra.shark.corba.ExpressionBuilders.EventAuditIteratorExpressionBuilder getEventAuditIteratorExpressionBuilder ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getEventAuditIteratorExpressionBuilder", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.EventAuditIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.EventAuditIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getEventAuditIteratorExpressionBuilder (        );
            } finally {
                _releaseReply ($in);
            }
  } // getEventAuditIteratorExpressionBuilder

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder getProcessIteratorExpressionBuilder ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getProcessIteratorExpressionBuilder", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getProcessIteratorExpressionBuilder (        );
            } finally {
                _releaseReply ($in);
            }
  } // getProcessIteratorExpressionBuilder

  public org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder getProcessMgrIteratorExpressionBuilder ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getProcessMgrIteratorExpressionBuilder", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getProcessMgrIteratorExpressionBuilder (        );
            } finally {
                _releaseReply ($in);
            }
  } // getProcessMgrIteratorExpressionBuilder

  public org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder getResourceIteratorExpressionBuilder ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getResourceIteratorExpressionBuilder", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder $result = org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilderHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getResourceIteratorExpressionBuilder (        );
            } finally {
                _releaseReply ($in);
            }
  } // getResourceIteratorExpressionBuilder

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:WorkflowService/ExpressionBuilderManager:1.0"};

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
} // class _ExpressionBuilderManagerStub
