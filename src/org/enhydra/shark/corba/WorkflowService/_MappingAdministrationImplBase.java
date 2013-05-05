package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/_MappingAdministrationImplBase.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分33秒 CST
*/

public abstract class _MappingAdministrationImplBase extends org.omg.CORBA.portable.ObjectImpl
                implements org.enhydra.shark.corba.WorkflowService.MappingAdministration, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors
  public _MappingAdministrationImplBase ()
  {
  }

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("connect", new java.lang.Integer (0));
    _methods.put ("disconnect", new java.lang.Integer (1));
    _methods.put ("getAllParticipants", new java.lang.Integer (2));
    _methods.put ("getAllParticipantsFromPkg", new java.lang.Integer (3));
    _methods.put ("getAllParticipantsFromPkgProcess", new java.lang.Integer (4));
    _methods.put ("getAllGroupnames", new java.lang.Integer (5));
    _methods.put ("getAllUsers", new java.lang.Integer (6));
    _methods.put ("getAllUsersForGroup", new java.lang.Integer (7));
    _methods.put ("getAllParticipantMappings", new java.lang.Integer (8));
    _methods.put ("addParticipantMapping", new java.lang.Integer (9));
    _methods.put ("removeParticipantMapping", new java.lang.Integer (10));
    _methods.put ("getParticipantMappings", new java.lang.Integer (11));
    _methods.put ("removeParticipantMappings", new java.lang.Integer (12));
    _methods.put ("removeParticipantMappingsForUser", new java.lang.Integer (13));
    _methods.put ("createParticipantMap", new java.lang.Integer (14));
    _methods.put ("getAllApplications", new java.lang.Integer (15));
    _methods.put ("getAllApplicationsFromPkg", new java.lang.Integer (16));
    _methods.put ("getAllApplicationsFromPkgProcess", new java.lang.Integer (17));
    _methods.put ("getDefinedToolAgents", new java.lang.Integer (18));
    _methods.put ("getToolAgentsInfo", new java.lang.Integer (19));
    _methods.put ("getToolAgentInfo", new java.lang.Integer (20));
    _methods.put ("addApplicationMapping", new java.lang.Integer (21));
    _methods.put ("getApplicationMapping", new java.lang.Integer (22));
    _methods.put ("removeApplicationMapping", new java.lang.Integer (23));
    _methods.put ("getApplicationMappings", new java.lang.Integer (24));
    _methods.put ("createApplicationMap", new java.lang.Integer (25));
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
       case 0:  // WorkflowService/MappingAdministration/connect
       {
         try {
           String userId = in.read_wstring ();
           String password = in.read_wstring ();
           String engineName = in.read_wstring ();
           String scope = in.read_wstring ();
           this.connect (userId, password, engineName, scope);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.ConnectFailed $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.ConnectFailedHelper.write (out, $ex);
         }
         break;
       }

       case 1:  // WorkflowService/MappingAdministration/disconnect
       {
         try {
           this.disconnect ();
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 2:  // WorkflowService/MappingAdministration/getAllParticipants
       {
         try {
           org.enhydra.shark.corba.WorkflowService.ParticipantMap $result[] = null;
           $result = this.getAllParticipants ();
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.PMapSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 3:  // WorkflowService/MappingAdministration/getAllParticipantsFromPkg
       {
         try {
           String pkdId = in.read_wstring ();
           org.enhydra.shark.corba.WorkflowService.ParticipantMap $result[] = null;
           $result = this.getAllParticipantsFromPkg (pkdId);
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.PMapSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 4:  // WorkflowService/MappingAdministration/getAllParticipantsFromPkgProcess
       {
         try {
           String pkgId = in.read_wstring ();
           String pDefId = in.read_wstring ();
           org.enhydra.shark.corba.WorkflowService.ParticipantMap $result[] = null;
           $result = this.getAllParticipantsFromPkgProcess (pkgId, pDefId);
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.PMapSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 5:  // WorkflowService/MappingAdministration/getAllGroupnames
       {
         try {
           String $result[] = null;
           $result = this.getAllGroupnames ();
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.WStringSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 6:  // WorkflowService/MappingAdministration/getAllUsers
       {
         try {
           String $result[] = null;
           $result = this.getAllUsers ();
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.WStringSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 7:  // WorkflowService/MappingAdministration/getAllUsersForGroup
       {
         try {
           String groupName = in.read_wstring ();
           String $result[] = null;
           $result = this.getAllUsersForGroup (groupName);
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.WStringSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 8:  // WorkflowService/MappingAdministration/getAllParticipantMappings
       {
         try {
           org.enhydra.shark.corba.WorkflowService.ParticipantMap $result[] = null;
           $result = this.getAllParticipantMappings ();
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.PMapSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 9:  // WorkflowService/MappingAdministration/addParticipantMapping
       {
         try {
           org.enhydra.shark.corba.WorkflowService.ParticipantMap pm = org.enhydra.shark.corba.WorkflowService.ParticipantMapHelper.read (in);
           this.addParticipantMapping (pm);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 10:  // WorkflowService/MappingAdministration/removeParticipantMapping
       {
         try {
           org.enhydra.shark.corba.WorkflowService.ParticipantMap pm = org.enhydra.shark.corba.WorkflowService.ParticipantMapHelper.read (in);
           this.removeParticipantMapping (pm);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 11:  // WorkflowService/MappingAdministration/getParticipantMappings
       {
         try {
           String packageId = in.read_wstring ();
           String processDefinitionId = in.read_wstring ();
           String participantId = in.read_wstring ();
           org.enhydra.shark.corba.WorkflowService.ParticipantMap $result[] = null;
           $result = this.getParticipantMappings (packageId, processDefinitionId, participantId);
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.PMapSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 12:  // WorkflowService/MappingAdministration/removeParticipantMappings
       {
         try {
           String packageId = in.read_wstring ();
           String processDefinitionId = in.read_wstring ();
           String participantId = in.read_wstring ();
           this.removeParticipantMappings (packageId, processDefinitionId, participantId);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 13:  // WorkflowService/MappingAdministration/removeParticipantMappingsForUser
       {
         try {
           String username = in.read_wstring ();
           this.removeParticipantMappingsForUser (username);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 14:  // WorkflowService/MappingAdministration/createParticipantMap
       {
         try {
           org.enhydra.shark.corba.WorkflowService.ParticipantMap $result = null;
           $result = this.createParticipantMap ();
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.ParticipantMapHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 15:  // WorkflowService/MappingAdministration/getAllApplications
       {
         try {
           org.enhydra.shark.corba.WorkflowService.ApplicationMap $result[] = null;
           $result = this.getAllApplications ();
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.AMapSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 16:  // WorkflowService/MappingAdministration/getAllApplicationsFromPkg
       {
         try {
           String pkgId = in.read_wstring ();
           org.enhydra.shark.corba.WorkflowService.ApplicationMap $result[] = null;
           $result = this.getAllApplicationsFromPkg (pkgId);
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.AMapSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 17:  // WorkflowService/MappingAdministration/getAllApplicationsFromPkgProcess
       {
         try {
           String pkgId = in.read_wstring ();
           String pDefId = in.read_wstring ();
           org.enhydra.shark.corba.WorkflowService.ApplicationMap $result[] = null;
           $result = this.getAllApplicationsFromPkgProcess (pkgId, pDefId);
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.AMapSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 18:  // WorkflowService/MappingAdministration/getDefinedToolAgents
       {
         try {
           String $result[] = null;
           $result = this.getDefinedToolAgents ();
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.WStringSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 19:  // WorkflowService/MappingAdministration/getToolAgentsInfo
       {
         try {
           org.omg.WfBase.NameValue $result[] = null;
           $result = this.getToolAgentsInfo ();
           out = $rh.createReply();
           org.omg.WfBase.NameValueSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 20:  // WorkflowService/MappingAdministration/getToolAgentInfo
       {
         try {
           String toolAgentFullClassName = in.read_wstring ();
           String $result = null;
           $result = this.getToolAgentInfo (toolAgentFullClassName);
           out = $rh.createReply();
           out.write_wstring ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 21:  // WorkflowService/MappingAdministration/addApplicationMapping
       {
         try {
           org.enhydra.shark.corba.WorkflowService.ApplicationMap am = org.enhydra.shark.corba.WorkflowService.ApplicationMapHelper.read (in);
           this.addApplicationMapping (am);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 22:  // WorkflowService/MappingAdministration/getApplicationMapping
       {
         try {
           String pkgId = in.read_wstring ();
           String pDefId = in.read_wstring ();
           String appDefId = in.read_wstring ();
           org.enhydra.shark.corba.WorkflowService.ApplicationMap $result = null;
           $result = this.getApplicationMapping (pkgId, pDefId, appDefId);
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.ApplicationMapHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 23:  // WorkflowService/MappingAdministration/removeApplicationMapping
       {
         try {
           String packageId = in.read_wstring ();
           String processDefinitionId = in.read_wstring ();
           String applicationId = in.read_wstring ();
           this.removeApplicationMapping (packageId, processDefinitionId, applicationId);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 24:  // WorkflowService/MappingAdministration/getApplicationMappings
       {
         try {
           org.enhydra.shark.corba.WorkflowService.ApplicationMap $result[] = null;
           $result = this.getApplicationMappings ();
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.AMapSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 25:  // WorkflowService/MappingAdministration/createApplicationMap
       {
         try {
           org.enhydra.shark.corba.WorkflowService.ApplicationMap $result = null;
           $result = this.createApplicationMap ();
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.ApplicationMapHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
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
    "IDL:WorkflowService/MappingAdministration:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }


} // class _MappingAdministrationImplBase
