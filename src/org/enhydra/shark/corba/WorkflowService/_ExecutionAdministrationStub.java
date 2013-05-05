package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/_ExecutionAdministrationStub.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分32秒 CST
*/

public class _ExecutionAdministrationStub extends org.omg.CORBA.portable.ObjectImpl implements org.enhydra.shark.corba.WorkflowService.ExecutionAdministration
{

  public void connect (String userId, String password, String engineName, String scope) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.ConnectFailed
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("connect", true);
                $out.write_wstring (userId);
                $out.write_wstring (password);
                $out.write_wstring (engineName);
                $out.write_wstring (scope);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/ConnectFailed:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.ConnectFailedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                connect (userId, password, engineName, scope        );
            } finally {
                _releaseReply ($in);
            }
  } // connect

  public void disconnect () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("disconnect", true);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                disconnect (        );
            } finally {
                _releaseReply ($in);
            }
  } // disconnect

  public void shutdown () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("shutdown", true);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                shutdown (        );
            } finally {
                _releaseReply ($in);
            }
  } // shutdown

  public void removeProcessRequester (String procId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("removeProcessRequester", true);
                $out.write_wstring (procId);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                removeProcessRequester (procId        );
            } finally {
                _releaseReply ($in);
            }
  } // removeProcessRequester

  public org.enhydra.shark.corba.WorkflowService.WfProcessMgrIterator get_iterator_processmgr () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("get_iterator_processmgr", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.WorkflowService.WfProcessMgrIterator $result = org.enhydra.shark.corba.WorkflowService.WfProcessMgrIteratorHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return get_iterator_processmgr (        );
            } finally {
                _releaseReply ($in);
            }
  } // get_iterator_processmgr

  public org.omg.WorkflowModel.WfProcessMgr[] get_sequence_processmgr (int max_number) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("get_sequence_processmgr", true);
                $out.write_long (max_number);
                $in = _invoke ($out);
                org.omg.WorkflowModel.WfProcessMgr $result[] = org.enhydra.shark.corba.WorkflowService.WfProcessMgrSequenceHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return get_sequence_processmgr (max_number        );
            } finally {
                _releaseReply ($in);
            }
  } // get_sequence_processmgr

  public org.omg.WfBase.NameValue[] getLoggedUsers () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getLoggedUsers", true);
                $in = _invoke ($out);
                org.omg.WfBase.NameValue $result[] = org.omg.WfBase.NameValueSequenceHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getLoggedUsers (        );
            } finally {
                _releaseReply ($in);
            }
  } // getLoggedUsers

  public org.enhydra.shark.corba.WorkflowService.WfResourceIterator get_iterator_resource () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("get_iterator_resource", true);
                $in = _invoke ($out);
                org.enhydra.shark.corba.WorkflowService.WfResourceIterator $result = org.enhydra.shark.corba.WorkflowService.WfResourceIteratorHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return get_iterator_resource (        );
            } finally {
                _releaseReply ($in);
            }
  } // get_iterator_resource

  public org.omg.WorkflowModel.WfResource[] get_sequence_resource (int max_number) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("get_sequence_resource", true);
                $out.write_long (max_number);
                $in = _invoke ($out);
                org.omg.WorkflowModel.WfResource $result[] = org.enhydra.shark.corba.WorkflowService.WfResourceSequenceHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return get_sequence_resource (max_number        );
            } finally {
                _releaseReply ($in);
            }
  } // get_sequence_resource

  public void startActivity (String procId, String blockActIdString, String actDefId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("startActivity", true);
                $out.write_wstring (procId);
                $out.write_wstring (blockActIdString);
                $out.write_wstring (actDefId);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                startActivity (procId, blockActIdString, actDefId        );
            } finally {
                _releaseReply ($in);
            }
  } // startActivity

  public org.omg.WorkflowModel.WfProcessMgr getProcessMgrByName (String name) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getProcessMgrByName", true);
                $out.write_wstring (name);
                $in = _invoke ($out);
                org.omg.WorkflowModel.WfProcessMgr $result = org.omg.WorkflowModel.WfProcessMgrHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getProcessMgrByName (name        );
            } finally {
                _releaseReply ($in);
            }
  } // getProcessMgrByName

  public org.omg.WorkflowModel.WfProcessMgr getProcessMgrByXPDLDefinition (String pkgId, String pDefId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getProcessMgrByXPDLDefinition", true);
                $out.write_wstring (pkgId);
                $out.write_wstring (pDefId);
                $in = _invoke ($out);
                org.omg.WorkflowModel.WfProcessMgr $result = org.omg.WorkflowModel.WfProcessMgrHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getProcessMgrByXPDLDefinition (pkgId, pDefId        );
            } finally {
                _releaseReply ($in);
            }
  } // getProcessMgrByXPDLDefinition

  public org.omg.WfBase.NameValueInfo[] getProcessMgrInputSignatureByMgrName (String name) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getProcessMgrInputSignatureByMgrName", true);
                $out.write_wstring (name);
                $in = _invoke ($out);
                org.omg.WfBase.NameValueInfo $result[] = org.omg.WfBase.NameValueInfoSequenceHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getProcessMgrInputSignatureByMgrName (name        );
            } finally {
                _releaseReply ($in);
            }
  } // getProcessMgrInputSignatureByMgrName

  public org.omg.WfBase.NameValueInfo[] getProcessMgrInputSignatureByXPDLDefinition (String pkgId, String pDefId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getProcessMgrInputSignatureByXPDLDefinition", true);
                $out.write_wstring (pkgId);
                $out.write_wstring (pDefId);
                $in = _invoke ($out);
                org.omg.WfBase.NameValueInfo $result[] = org.omg.WfBase.NameValueInfoSequenceHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getProcessMgrInputSignatureByXPDLDefinition (pkgId, pDefId        );
            } finally {
                _releaseReply ($in);
            }
  } // getProcessMgrInputSignatureByXPDLDefinition

  public org.omg.WfBase.NameValueInfo[] getProcessMgrInputSignatureByXPDLDefinitionWithVersion (String pkgId, String pkgVer, String pDefId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getProcessMgrInputSignatureByXPDLDefinitionWithVersion", true);
                $out.write_wstring (pkgId);
                $out.write_wstring (pkgVer);
                $out.write_wstring (pDefId);
                $in = _invoke ($out);
                org.omg.WfBase.NameValueInfo $result[] = org.omg.WfBase.NameValueInfoSequenceHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getProcessMgrInputSignatureByXPDLDefinitionWithVersion (pkgId, pkgVer, pDefId        );
            } finally {
                _releaseReply ($in);
            }
  } // getProcessMgrInputSignatureByXPDLDefinitionWithVersion

  public org.omg.WorkflowModel.WfResource getResource (String username) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getResource", true);
                $out.write_wstring (username);
                $in = _invoke ($out);
                org.omg.WorkflowModel.WfResource $result = org.omg.WorkflowModel.WfResourceHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getResource (username        );
            } finally {
                _releaseReply ($in);
            }
  } // getResource

  public org.omg.WorkflowModel.WfProcess getProcess (String procId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getProcess", true);
                $out.write_wstring (procId);
                $in = _invoke ($out);
                org.omg.WorkflowModel.WfProcess $result = org.omg.WorkflowModel.WfProcessHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getProcess (procId        );
            } finally {
                _releaseReply ($in);
            }
  } // getProcess

  public org.omg.WorkflowModel.WfActivity getActivity (String procId, String actId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getActivity", true);
                $out.write_wstring (procId);
                $out.write_wstring (actId);
                $in = _invoke ($out);
                org.omg.WorkflowModel.WfActivity $result = org.omg.WorkflowModel.WfActivityHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getActivity (procId, actId        );
            } finally {
                _releaseReply ($in);
            }
  } // getActivity

  public org.omg.WorkflowModel.WfAssignment getAssignment (String procId, String actId, String username) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getAssignment", true);
                $out.write_wstring (procId);
                $out.write_wstring (actId);
                $out.write_wstring (username);
                $in = _invoke ($out);
                org.omg.WorkflowModel.WfAssignment $result = org.omg.WorkflowModel.WfAssignmentHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getAssignment (procId, actId, username        );
            } finally {
                _releaseReply ($in);
            }
  } // getAssignment

  public org.omg.WorkflowModel.WfAssignment getAssignmentById (String procId, String assId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getAssignmentById", true);
                $out.write_wstring (procId);
                $out.write_wstring (assId);
                $in = _invoke ($out);
                org.omg.WorkflowModel.WfAssignment $result = org.omg.WorkflowModel.WfAssignmentHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getAssignmentById (procId, assId        );
            } finally {
                _releaseReply ($in);
            }
  } // getAssignmentById

  public void reevaluateAssignments () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("reevaluateAssignments", true);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                reevaluateAssignments (        );
            } finally {
                _releaseReply ($in);
            }
  } // reevaluateAssignments

  public void reevaluateAssignmentsForPkg (String pkgId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("reevaluateAssignmentsForPkg", true);
                $out.write_wstring (pkgId);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                reevaluateAssignmentsForPkg (pkgId        );
            } finally {
                _releaseReply ($in);
            }
  } // reevaluateAssignmentsForPkg

  public void reevaluateAssignmentsForProcessDefinition (String pkgId, String pDefId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("reevaluateAssignmentsForProcessDefinition", true);
                $out.write_wstring (pkgId);
                $out.write_wstring (pDefId);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                reevaluateAssignmentsForProcessDefinition (pkgId, pDefId        );
            } finally {
                _releaseReply ($in);
            }
  } // reevaluateAssignmentsForProcessDefinition

  public void reevaluateAssignmentsForActivityDefinition (String pkgId, String pDefId, String aDefId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("reevaluateAssignmentsForActivityDefinition", true);
                $out.write_wstring (pkgId);
                $out.write_wstring (pDefId);
                $out.write_wstring (aDefId);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                reevaluateAssignmentsForActivityDefinition (pkgId, pDefId, aDefId        );
            } finally {
                _releaseReply ($in);
            }
  } // reevaluateAssignmentsForActivityDefinition

  public org.omg.WorkflowModel.WfCreateProcessEventAudit getCreateProcessHistory (String procId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getCreateProcessHistory", true);
                $out.write_wstring (procId);
                $in = _invoke ($out);
                org.omg.WorkflowModel.WfCreateProcessEventAudit $result = org.omg.WorkflowModel.WfCreateProcessEventAuditHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getCreateProcessHistory (procId        );
            } finally {
                _releaseReply ($in);
            }
  } // getCreateProcessHistory

  public org.omg.WorkflowModel.WfDataEventAudit[] getProcessSequenceDataHistory (String procId, int max_number) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getProcessSequenceDataHistory", true);
                $out.write_wstring (procId);
                $out.write_long (max_number);
                $in = _invoke ($out);
                org.omg.WorkflowModel.WfDataEventAudit $result[] = org.enhydra.shark.corba.WorkflowService.WfDataEventAuditSequenceHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getProcessSequenceDataHistory (procId, max_number        );
            } finally {
                _releaseReply ($in);
            }
  } // getProcessSequenceDataHistory

  public org.omg.WorkflowModel.WfStateEventAudit[] getProcessSequenceStateHistory (String procId, int max_number) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getProcessSequenceStateHistory", true);
                $out.write_wstring (procId);
                $out.write_long (max_number);
                $in = _invoke ($out);
                org.omg.WorkflowModel.WfStateEventAudit $result[] = org.enhydra.shark.corba.WorkflowService.WfStateEventAuditSequenceHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getProcessSequenceStateHistory (procId, max_number        );
            } finally {
                _releaseReply ($in);
            }
  } // getProcessSequenceStateHistory

  public org.omg.WorkflowModel.WfDataEventAudit[] getActivitySequenceDataHistory (String procId, String actId, int max_number) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getActivitySequenceDataHistory", true);
                $out.write_wstring (procId);
                $out.write_wstring (actId);
                $out.write_long (max_number);
                $in = _invoke ($out);
                org.omg.WorkflowModel.WfDataEventAudit $result[] = org.enhydra.shark.corba.WorkflowService.WfDataEventAuditSequenceHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getActivitySequenceDataHistory (procId, actId, max_number        );
            } finally {
                _releaseReply ($in);
            }
  } // getActivitySequenceDataHistory

  public org.omg.WorkflowModel.WfStateEventAudit[] getActivitySequenceStateHistory (String procId, String actId, int max_number) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getActivitySequenceStateHistory", true);
                $out.write_wstring (procId);
                $out.write_wstring (actId);
                $out.write_long (max_number);
                $in = _invoke ($out);
                org.omg.WorkflowModel.WfStateEventAudit $result[] = org.enhydra.shark.corba.WorkflowService.WfStateEventAuditSequenceHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getActivitySequenceStateHistory (procId, actId, max_number        );
            } finally {
                _releaseReply ($in);
            }
  } // getActivitySequenceStateHistory

  public org.omg.WorkflowModel.WfAssignmentEventAudit[] getSequenceAssignmentHistory (String procId, String actId, int max_number) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getSequenceAssignmentHistory", true);
                $out.write_wstring (procId);
                $out.write_wstring (actId);
                $out.write_long (max_number);
                $in = _invoke ($out);
                org.omg.WorkflowModel.WfAssignmentEventAudit $result[] = org.enhydra.shark.corba.WorkflowService.WfAssignmentEventAuditSequenceHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getSequenceAssignmentHistory (procId, actId, max_number        );
            } finally {
                _releaseReply ($in);
            }
  } // getSequenceAssignmentHistory

  public void deleteClosedProcesses () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("deleteClosedProcesses", true);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                deleteClosedProcesses (        );
            } finally {
                _releaseReply ($in);
            }
  } // deleteClosedProcesses

  public void deleteClosedProcessesForPkg (String pkgId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("deleteClosedProcessesForPkg", true);
                $out.write_wstring (pkgId);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                deleteClosedProcessesForPkg (pkgId        );
            } finally {
                _releaseReply ($in);
            }
  } // deleteClosedProcessesForPkg

  public void deleteClosedProcessesForMgr (String mgrName) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("deleteClosedProcessesForMgr", true);
                $out.write_wstring (mgrName);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                deleteClosedProcessesForMgr (mgrName        );
            } finally {
                _releaseReply ($in);
            }
  } // deleteClosedProcessesForMgr

  public void deleteClosedProcessesForPkgWithVersion (String pkgId, String pkgVer) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("deleteClosedProcessesForPkgWithVersion", true);
                $out.write_wstring (pkgId);
                $out.write_wstring (pkgVer);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                deleteClosedProcessesForPkgWithVersion (pkgId, pkgVer        );
            } finally {
                _releaseReply ($in);
            }
  } // deleteClosedProcessesForPkgWithVersion

  public void deleteClosedProcessesForProcessDefinition (String pkgId, String pDefId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("deleteClosedProcessesForProcessDefinition", true);
                $out.write_wstring (pkgId);
                $out.write_wstring (pDefId);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                deleteClosedProcessesForProcessDefinition (pkgId, pDefId        );
            } finally {
                _releaseReply ($in);
            }
  } // deleteClosedProcessesForProcessDefinition

  public void deleteClosedProcess (String procId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("deleteClosedProcess", true);
                $out.write_wstring (procId);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                deleteClosedProcess (procId        );
            } finally {
                _releaseReply ($in);
            }
  } // deleteClosedProcess

  public String[] deleteClosedProcessesMultiTrans (int instancesPerTransaction, int failuresToIgnore) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("deleteClosedProcessesMultiTrans", true);
                $out.write_long (instancesPerTransaction);
                $out.write_long (failuresToIgnore);
                $in = _invoke ($out);
                String $result[] = org.enhydra.shark.corba.WorkflowService.WStringSequenceHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return deleteClosedProcessesMultiTrans (instancesPerTransaction, failuresToIgnore        );
            } finally {
                _releaseReply ($in);
            }
  } // deleteClosedProcessesMultiTrans

  public String[] deleteClosedProcessesForMgrMultiTrans (String mgrName, int instancesPerTransaction, int failuresToIgnore) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("deleteClosedProcessesForMgrMultiTrans", true);
                $out.write_wstring (mgrName);
                $out.write_long (instancesPerTransaction);
                $out.write_long (failuresToIgnore);
                $in = _invoke ($out);
                String $result[] = org.enhydra.shark.corba.WorkflowService.WStringSequenceHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return deleteClosedProcessesForMgrMultiTrans (mgrName, instancesPerTransaction, failuresToIgnore        );
            } finally {
                _releaseReply ($in);
            }
  } // deleteClosedProcessesForMgrMultiTrans

  public org.omg.WfBase.NameValue[] getProcessContext (String processId, String[] variablesDefIds) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getProcessContext", true);
                $out.write_wstring (processId);
                org.enhydra.shark.corba.WorkflowService.WStringSequenceHelper.write ($out, variablesDefIds);
                $in = _invoke ($out);
                org.omg.WfBase.NameValue $result[] = org.omg.WfBase.NameValueSequenceHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getProcessContext (processId, variablesDefIds        );
            } finally {
                _releaseReply ($in);
            }
  } // getProcessContext

  public org.omg.WfBase.NameValue[] getActivityContext (String processId, String actId, String[] variablesDefIds) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getActivityContext", true);
                $out.write_wstring (processId);
                $out.write_wstring (actId);
                org.enhydra.shark.corba.WorkflowService.WStringSequenceHelper.write ($out, variablesDefIds);
                $in = _invoke ($out);
                org.omg.WfBase.NameValue $result[] = org.omg.WfBase.NameValueSequenceHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getActivityContext (processId, actId, variablesDefIds        );
            } finally {
                _releaseReply ($in);
            }
  } // getActivityContext

  public org.omg.WorkflowModel.WfAssignmentIterator get_iterator_assignment () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("get_iterator_assignment", true);
                $in = _invoke ($out);
                org.omg.WorkflowModel.WfAssignmentIterator $result = org.omg.WorkflowModel.WfAssignmentIteratorHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return get_iterator_assignment (        );
            } finally {
                _releaseReply ($in);
            }
  } // get_iterator_assignment

  public org.omg.WorkflowModel.WfProcessIterator get_iterator_process () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("get_iterator_process", true);
                $in = _invoke ($out);
                org.omg.WorkflowModel.WfProcessIterator $result = org.omg.WorkflowModel.WfProcessIteratorHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return get_iterator_process (        );
            } finally {
                _releaseReply ($in);
            }
  } // get_iterator_process

  public org.omg.WorkflowModel.WfActivityIterator get_iterator_activity () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("get_iterator_activity", true);
                $in = _invoke ($out);
                org.omg.WorkflowModel.WfActivityIterator $result = org.omg.WorkflowModel.WfActivityIteratorHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/WfBase/BaseException:1.0"))
                    throw org.omg.WfBase.BaseExceptionHelper.read ($in);
                else if (_id.equals ("IDL:WorkflowService/NotConnected:1.0"))
                    throw org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return get_iterator_activity (        );
            } finally {
                _releaseReply ($in);
            }
  } // get_iterator_activity

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
    "IDL:WorkflowService/ExecutionAdministration:1.0"};

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
} // class _ExecutionAdministrationStub
