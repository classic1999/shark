package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/_SharkInterfaceStub.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分33秒 CST
*/

public class _SharkInterfaceStub extends org.omg.CORBA.portable.ObjectImpl implements org.enhydra.shark.corba.WorkflowService.SharkInterface
{

  public org.enhydra.shark.corba.WorkflowService.SharkConnection getSharkConnection ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getSharkConnection", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.WorkflowService.SharkConnection $result = org.enhydra.shark.corba.WorkflowService.SharkConnectionHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getSharkConnection (        );
            } finally {
                _releaseReply ($in);
            }
  } // getSharkConnection

  public org.enhydra.shark.corba.WorkflowService.RepositoryMgr getRepositoryManager ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getRepositoryManager", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.WorkflowService.RepositoryMgr $result = org.enhydra.shark.corba.WorkflowService.RepositoryMgrHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getRepositoryManager (        );
            } finally {
                _releaseReply ($in);
            }
  } // getRepositoryManager

  public org.enhydra.shark.corba.WorkflowService.PackageAdministration getPackageAdministration ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getPackageAdministration", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.WorkflowService.PackageAdministration $result = org.enhydra.shark.corba.WorkflowService.PackageAdministrationHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getPackageAdministration (        );
            } finally {
                _releaseReply ($in);
            }
  } // getPackageAdministration

  public org.enhydra.shark.corba.WorkflowService.UserGroupAdministration getUserGroupAdministration ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getUserGroupAdministration", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.WorkflowService.UserGroupAdministration $result = org.enhydra.shark.corba.WorkflowService.UserGroupAdministrationHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getUserGroupAdministration (        );
            } finally {
                _releaseReply ($in);
            }
  } // getUserGroupAdministration

  public org.enhydra.shark.corba.WorkflowService.ExecutionAdministration getExecutionAdministration ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getExecutionAdministration", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.WorkflowService.ExecutionAdministration $result = org.enhydra.shark.corba.WorkflowService.ExecutionAdministrationHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getExecutionAdministration (        );
            } finally {
                _releaseReply ($in);
            }
  } // getExecutionAdministration

  public org.enhydra.shark.corba.WorkflowService.MappingAdministration getMappingAdministration ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getMappingAdministration", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.WorkflowService.MappingAdministration $result = org.enhydra.shark.corba.WorkflowService.MappingAdministrationHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getMappingAdministration (        );
            } finally {
                _releaseReply ($in);
            }
  } // getMappingAdministration

  public org.enhydra.shark.corba.WorkflowService.AdminMisc getAdminMisc ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getAdminMisc", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.WorkflowService.AdminMisc $result = org.enhydra.shark.corba.WorkflowService.AdminMiscHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getAdminMisc (        );
            } finally {
                _releaseReply ($in);
            }
  } // getAdminMisc

  public org.enhydra.shark.corba.WorkflowService.CacheAdministration getCacheAdministration ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getCacheAdministration", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.WorkflowService.CacheAdministration $result = org.enhydra.shark.corba.WorkflowService.CacheAdministrationHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getCacheAdministration (        );
            } finally {
                _releaseReply ($in);
            }
  } // getCacheAdministration

  public org.enhydra.shark.corba.WorkflowService.DeadlineAdministration getDeadlineAdministration ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getDeadlineAdministration", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.WorkflowService.DeadlineAdministration $result = org.enhydra.shark.corba.WorkflowService.DeadlineAdministrationHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getDeadlineAdministration (        );
            } finally {
                _releaseReply ($in);
            }
  } // getDeadlineAdministration

  public org.enhydra.shark.corba.WorkflowService.LimitAdministration getLimitAdministration ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getLimitAdministration", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.WorkflowService.LimitAdministration $result = org.enhydra.shark.corba.WorkflowService.LimitAdministrationHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getLimitAdministration (        );
            } finally {
                _releaseReply ($in);
            }
  } // getLimitAdministration

  public org.enhydra.shark.corba.WorkflowService.ExpressionBuilderManager getExpressionBuilderManager ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getExpressionBuilderManager", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.WorkflowService.ExpressionBuilderManager $result = org.enhydra.shark.corba.WorkflowService.ExpressionBuilderManagerHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getExpressionBuilderManager (        );
            } finally {
                _releaseReply ($in);
            }
  } // getExpressionBuilderManager

  public org.omg.WfBase.NameValueInfo[] getProperties ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getProperties", true);
                $in = _invoke ($out);
                org.omg.WfBase.NameValueInfo $result[] = org.omg.WfBase.NameValueInfoSequenceHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getProperties (        );
            } finally {
                _releaseReply ($in);
            }
  } // getProperties

  public void doneWith (org.omg.CORBA.Object toDisconnect)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("doneWith", true);
                org.omg.CORBA.ObjectHelper.write ($out, toDisconnect);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                doneWith (toDisconnect        );
            } finally {
                _releaseReply ($in);
            }
  } // doneWith

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:WorkflowService/SharkInterface:1.0"};

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
} // class _SharkInterfaceStub
