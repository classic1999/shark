package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/_AdminMiscImplBase.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分32秒 CST
*/

public abstract class _AdminMiscImplBase extends org.omg.CORBA.portable.ObjectImpl
                implements org.enhydra.shark.corba.WorkflowService.AdminMisc, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors
  public _AdminMiscImplBase ()
  {
  }

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("connect", new java.lang.Integer (0));
    _methods.put ("disconnect", new java.lang.Integer (1));
    _methods.put ("getPackageExtendedAttributeNameValuePairs", new java.lang.Integer (2));
    _methods.put ("getPackageExtendedAttributeNames", new java.lang.Integer (3));
    _methods.put ("getPackageExtendedAttributeValue", new java.lang.Integer (4));
    _methods.put ("getProcessDefinitionExtendedAttributeNameValuePairs", new java.lang.Integer (5));
    _methods.put ("getProcessDefinitionExtendedAttributeNames", new java.lang.Integer (6));
    _methods.put ("getProcessDefinitionExtendedAttributeValue", new java.lang.Integer (7));
    _methods.put ("getProcessExtendedAttributeNameValuePairs", new java.lang.Integer (8));
    _methods.put ("getProcessExtendedAttributeNames", new java.lang.Integer (9));
    _methods.put ("getProcessExtendedAttributeValue", new java.lang.Integer (10));
    _methods.put ("getActivitiesExtendedAttributes", new java.lang.Integer (11));
    _methods.put ("getActivitiesExtendedAttributeNameValuePairs", new java.lang.Integer (12));
    _methods.put ("getActivitiesExtendedAttributeNames", new java.lang.Integer (13));
    _methods.put ("getActivitiesExtendedAttributeValue", new java.lang.Integer (14));
    _methods.put ("getDefVariableExtendedAttributeNameValuePairs", new java.lang.Integer (15));
    _methods.put ("getDefVariableExtendedAttributeNames", new java.lang.Integer (16));
    _methods.put ("getDefVariableExtendedAttributeValue", new java.lang.Integer (17));
    _methods.put ("getVariableExtendedAttributeNameValuePairs", new java.lang.Integer (18));
    _methods.put ("getVariableExtendedAttributeNames", new java.lang.Integer (19));
    _methods.put ("getVariableExtendedAttributeValue", new java.lang.Integer (20));
    _methods.put ("getParticipantExtendedAttributeNameValuePairs", new java.lang.Integer (21));
    _methods.put ("getParticipantExtendedAttributeNames", new java.lang.Integer (22));
    _methods.put ("getParticipantExtendedAttributeValue", new java.lang.Integer (23));
    _methods.put ("getApplicationExtendedAttributeNameValuePairs", new java.lang.Integer (24));
    _methods.put ("getApplicationExtendedAttributeNames", new java.lang.Integer (25));
    _methods.put ("getApplicationExtendedAttributeValue", new java.lang.Integer (26));
    _methods.put ("getBlockActivityId", new java.lang.Integer (27));
    _methods.put ("getActivityDefinitionId", new java.lang.Integer (28));
    _methods.put ("getProcessDefinitionId", new java.lang.Integer (29));
    _methods.put ("getDefVariableName", new java.lang.Integer (30));
    _methods.put ("getDefVariableDescription", new java.lang.Integer (31));
    _methods.put ("getDefVariableJavaClassName", new java.lang.Integer (32));
    _methods.put ("getVariableName", new java.lang.Integer (33));
    _methods.put ("getVariableDescription", new java.lang.Integer (34));
    _methods.put ("getVariableJavaClassName", new java.lang.Integer (35));
    _methods.put ("getParticipantName", new java.lang.Integer (36));
    _methods.put ("getApplicationName", new java.lang.Integer (37));
    _methods.put ("getProcessMgrPkgId", new java.lang.Integer (38));
    _methods.put ("getProcessMgrProcDefId", new java.lang.Integer (39));
    _methods.put ("getProcessMgrProcDefName", new java.lang.Integer (40));
    _methods.put ("getProcessRequesterUsername", new java.lang.Integer (41));
    _methods.put ("getActivityResourceUsername", new java.lang.Integer (42));
    _methods.put ("getProcessCreatedTime", new java.lang.Integer (43));
    _methods.put ("getProcessStartedTime", new java.lang.Integer (44));
    _methods.put ("getProcessFinishTime", new java.lang.Integer (45));
    _methods.put ("getActivityCreatedTime", new java.lang.Integer (46));
    _methods.put ("getActivityStartedTime", new java.lang.Integer (47));
    _methods.put ("getActivityFinishTime", new java.lang.Integer (48));
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
       case 0:  // WorkflowService/AdminMisc/connect
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

       case 1:  // WorkflowService/AdminMisc/disconnect
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

       case 2:  // WorkflowService/AdminMisc/getPackageExtendedAttributeNameValuePairs
       {
         try {
           String pkgId = in.read_wstring ();
           org.omg.WfBase.NameValue $result[] = null;
           $result = this.getPackageExtendedAttributeNameValuePairs (pkgId);
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

       case 3:  // WorkflowService/AdminMisc/getPackageExtendedAttributeNames
       {
         try {
           String pkgId = in.read_wstring ();
           String $result[] = null;
           $result = this.getPackageExtendedAttributeNames (pkgId);
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

       case 4:  // WorkflowService/AdminMisc/getPackageExtendedAttributeValue
       {
         try {
           String pkgId = in.read_wstring ();
           String extAttrName = in.read_wstring ();
           String $result = null;
           $result = this.getPackageExtendedAttributeValue (pkgId, extAttrName);
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

       case 5:  // WorkflowService/AdminMisc/getProcessDefinitionExtendedAttributeNameValuePairs
       {
         try {
           String mgrName = in.read_wstring ();
           org.omg.WfBase.NameValue $result[] = null;
           $result = this.getProcessDefinitionExtendedAttributeNameValuePairs (mgrName);
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

       case 6:  // WorkflowService/AdminMisc/getProcessDefinitionExtendedAttributeNames
       {
         try {
           String mgrName = in.read_wstring ();
           String $result[] = null;
           $result = this.getProcessDefinitionExtendedAttributeNames (mgrName);
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

       case 7:  // WorkflowService/AdminMisc/getProcessDefinitionExtendedAttributeValue
       {
         try {
           String mgrName = in.read_wstring ();
           String extAttrName = in.read_wstring ();
           String $result = null;
           $result = this.getProcessDefinitionExtendedAttributeValue (mgrName, extAttrName);
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

       case 8:  // WorkflowService/AdminMisc/getProcessExtendedAttributeNameValuePairs
       {
         try {
           String procId = in.read_wstring ();
           org.omg.WfBase.NameValue $result[] = null;
           $result = this.getProcessExtendedAttributeNameValuePairs (procId);
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

       case 9:  // WorkflowService/AdminMisc/getProcessExtendedAttributeNames
       {
         try {
           String procId = in.read_wstring ();
           String $result[] = null;
           $result = this.getProcessExtendedAttributeNames (procId);
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

       case 10:  // WorkflowService/AdminMisc/getProcessExtendedAttributeValue
       {
         try {
           String procId = in.read_wstring ();
           String extAttrName = in.read_wstring ();
           String $result = null;
           $result = this.getProcessExtendedAttributeValue (procId, extAttrName);
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

       case 11:  // WorkflowService/AdminMisc/getActivitiesExtendedAttributes
       {
         try {
           String procId = in.read_wstring ();
           String actId = in.read_wstring ();
           String $result = null;
           $result = this.getActivitiesExtendedAttributes (procId, actId);
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

       case 12:  // WorkflowService/AdminMisc/getActivitiesExtendedAttributeNameValuePairs
       {
         try {
           String procId = in.read_wstring ();
           String actId = in.read_wstring ();
           org.omg.WfBase.NameValue $result[] = null;
           $result = this.getActivitiesExtendedAttributeNameValuePairs (procId, actId);
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

       case 13:  // WorkflowService/AdminMisc/getActivitiesExtendedAttributeNames
       {
         try {
           String procId = in.read_wstring ();
           String actId = in.read_wstring ();
           String $result[] = null;
           $result = this.getActivitiesExtendedAttributeNames (procId, actId);
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

       case 14:  // WorkflowService/AdminMisc/getActivitiesExtendedAttributeValue
       {
         try {
           String procId = in.read_wstring ();
           String actId = in.read_wstring ();
           String extAttrName = in.read_wstring ();
           String $result = null;
           $result = this.getActivitiesExtendedAttributeValue (procId, actId, extAttrName);
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

       case 15:  // WorkflowService/AdminMisc/getDefVariableExtendedAttributeNameValuePairs
       {
         try {
           String mgrName = in.read_wstring ();
           String variableId = in.read_wstring ();
           org.omg.WfBase.NameValue $result[] = null;
           $result = this.getDefVariableExtendedAttributeNameValuePairs (mgrName, variableId);
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

       case 16:  // WorkflowService/AdminMisc/getDefVariableExtendedAttributeNames
       {
         try {
           String mgrName = in.read_wstring ();
           String variableId = in.read_wstring ();
           String $result[] = null;
           $result = this.getDefVariableExtendedAttributeNames (mgrName, variableId);
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

       case 17:  // WorkflowService/AdminMisc/getDefVariableExtendedAttributeValue
       {
         try {
           String mgrName = in.read_wstring ();
           String variableId = in.read_wstring ();
           String extAttrName = in.read_wstring ();
           String $result = null;
           $result = this.getDefVariableExtendedAttributeValue (mgrName, variableId, extAttrName);
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

       case 18:  // WorkflowService/AdminMisc/getVariableExtendedAttributeNameValuePairs
       {
         try {
           String procId = in.read_wstring ();
           String variableId = in.read_wstring ();
           org.omg.WfBase.NameValue $result[] = null;
           $result = this.getVariableExtendedAttributeNameValuePairs (procId, variableId);
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

       case 19:  // WorkflowService/AdminMisc/getVariableExtendedAttributeNames
       {
         try {
           String procId = in.read_wstring ();
           String variableId = in.read_wstring ();
           String $result[] = null;
           $result = this.getVariableExtendedAttributeNames (procId, variableId);
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

       case 20:  // WorkflowService/AdminMisc/getVariableExtendedAttributeValue
       {
         try {
           String procId = in.read_wstring ();
           String variableId = in.read_wstring ();
           String extAttrName = in.read_wstring ();
           String $result = null;
           $result = this.getVariableExtendedAttributeValue (procId, variableId, extAttrName);
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

       case 21:  // WorkflowService/AdminMisc/getParticipantExtendedAttributeNameValuePairs
       {
         try {
           String pkgId = in.read_wstring ();
           String pDefId = in.read_wstring ();
           String participantId = in.read_wstring ();
           org.omg.WfBase.NameValue $result[] = null;
           $result = this.getParticipantExtendedAttributeNameValuePairs (pkgId, pDefId, participantId);
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

       case 22:  // WorkflowService/AdminMisc/getParticipantExtendedAttributeNames
       {
         try {
           String pkgId = in.read_wstring ();
           String pDefId = in.read_wstring ();
           String participantId = in.read_wstring ();
           String $result[] = null;
           $result = this.getParticipantExtendedAttributeNames (pkgId, pDefId, participantId);
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

       case 23:  // WorkflowService/AdminMisc/getParticipantExtendedAttributeValue
       {
         try {
           String pkgId = in.read_wstring ();
           String pDefId = in.read_wstring ();
           String participantId = in.read_wstring ();
           String extAttrName = in.read_wstring ();
           String $result = null;
           $result = this.getParticipantExtendedAttributeValue (pkgId, pDefId, participantId, extAttrName);
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

       case 24:  // WorkflowService/AdminMisc/getApplicationExtendedAttributeNameValuePairs
       {
         try {
           String pkgId = in.read_wstring ();
           String pDefId = in.read_wstring ();
           String applicationId = in.read_wstring ();
           org.omg.WfBase.NameValue $result[] = null;
           $result = this.getApplicationExtendedAttributeNameValuePairs (pkgId, pDefId, applicationId);
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

       case 25:  // WorkflowService/AdminMisc/getApplicationExtendedAttributeNames
       {
         try {
           String pkgId = in.read_wstring ();
           String pDefId = in.read_wstring ();
           String applicationId = in.read_wstring ();
           String $result[] = null;
           $result = this.getApplicationExtendedAttributeNames (pkgId, pDefId, applicationId);
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

       case 26:  // WorkflowService/AdminMisc/getApplicationExtendedAttributeValue
       {
         try {
           String pkgId = in.read_wstring ();
           String pDefId = in.read_wstring ();
           String applicationId = in.read_wstring ();
           String extAttrName = in.read_wstring ();
           String $result = null;
           $result = this.getApplicationExtendedAttributeValue (pkgId, pDefId, applicationId, extAttrName);
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

       case 27:  // WorkflowService/AdminMisc/getBlockActivityId
       {
         try {
           String procId = in.read_wstring ();
           String actId = in.read_wstring ();
           String $result = null;
           $result = this.getBlockActivityId (procId, actId);
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

       case 28:  // WorkflowService/AdminMisc/getActivityDefinitionId
       {
         try {
           String procId = in.read_wstring ();
           String actId = in.read_wstring ();
           String $result = null;
           $result = this.getActivityDefinitionId (procId, actId);
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

       case 29:  // WorkflowService/AdminMisc/getProcessDefinitionId
       {
         try {
           String procId = in.read_wstring ();
           String $result = null;
           $result = this.getProcessDefinitionId (procId);
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

       case 30:  // WorkflowService/AdminMisc/getDefVariableName
       {
         try {
           String mgrName = in.read_wstring ();
           String variableId = in.read_wstring ();
           String $result = null;
           $result = this.getDefVariableName (mgrName, variableId);
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

       case 31:  // WorkflowService/AdminMisc/getDefVariableDescription
       {
         try {
           String mgrName = in.read_wstring ();
           String variableId = in.read_wstring ();
           String $result = null;
           $result = this.getDefVariableDescription (mgrName, variableId);
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

       case 32:  // WorkflowService/AdminMisc/getDefVariableJavaClassName
       {
         try {
           String mgrName = in.read_wstring ();
           String variableId = in.read_wstring ();
           String $result = null;
           $result = this.getDefVariableJavaClassName (mgrName, variableId);
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

       case 33:  // WorkflowService/AdminMisc/getVariableName
       {
         try {
           String procId = in.read_wstring ();
           String variableId = in.read_wstring ();
           String $result = null;
           $result = this.getVariableName (procId, variableId);
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

       case 34:  // WorkflowService/AdminMisc/getVariableDescription
       {
         try {
           String procId = in.read_wstring ();
           String variableId = in.read_wstring ();
           String $result = null;
           $result = this.getVariableDescription (procId, variableId);
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

       case 35:  // WorkflowService/AdminMisc/getVariableJavaClassName
       {
         try {
           String procId = in.read_wstring ();
           String variableId = in.read_wstring ();
           String $result = null;
           $result = this.getVariableJavaClassName (procId, variableId);
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

       case 36:  // WorkflowService/AdminMisc/getParticipantName
       {
         try {
           String pkgId = in.read_wstring ();
           String pDefId = in.read_wstring ();
           String participantId = in.read_wstring ();
           String $result = null;
           $result = this.getParticipantName (pkgId, pDefId, participantId);
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

       case 37:  // WorkflowService/AdminMisc/getApplicationName
       {
         try {
           String pkgId = in.read_wstring ();
           String pDefId = in.read_wstring ();
           String applicationId = in.read_wstring ();
           String $result = null;
           $result = this.getApplicationName (pkgId, pDefId, applicationId);
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

       case 38:  // WorkflowService/AdminMisc/getProcessMgrPkgId
       {
         try {
           String name = in.read_wstring ();
           String $result = null;
           $result = this.getProcessMgrPkgId (name);
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

       case 39:  // WorkflowService/AdminMisc/getProcessMgrProcDefId
       {
         try {
           String name = in.read_wstring ();
           String $result = null;
           $result = this.getProcessMgrProcDefId (name);
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

       case 40:  // WorkflowService/AdminMisc/getProcessMgrProcDefName
       {
         try {
           String name = in.read_wstring ();
           String $result = null;
           $result = this.getProcessMgrProcDefName (name);
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

       case 41:  // WorkflowService/AdminMisc/getProcessRequesterUsername
       {
         try {
           String procId = in.read_wstring ();
           String $result = null;
           $result = this.getProcessRequesterUsername (procId);
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

       case 42:  // WorkflowService/AdminMisc/getActivityResourceUsername
       {
         try {
           String procId = in.read_wstring ();
           String actId = in.read_wstring ();
           String $result = null;
           $result = this.getActivityResourceUsername (procId, actId);
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

       case 43:  // WorkflowService/AdminMisc/getProcessCreatedTime
       {
         try {
           String procId = in.read_wstring ();
           long $result = (long)0;
           $result = this.getProcessCreatedTime (procId);
           out = $rh.createReply();
           out.write_longlong ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 44:  // WorkflowService/AdminMisc/getProcessStartedTime
       {
         try {
           String procId = in.read_wstring ();
           long $result = (long)0;
           $result = this.getProcessStartedTime (procId);
           out = $rh.createReply();
           out.write_longlong ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 45:  // WorkflowService/AdminMisc/getProcessFinishTime
       {
         try {
           String procId = in.read_wstring ();
           long $result = (long)0;
           $result = this.getProcessFinishTime (procId);
           out = $rh.createReply();
           out.write_longlong ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 46:  // WorkflowService/AdminMisc/getActivityCreatedTime
       {
         try {
           String procId = in.read_wstring ();
           String actId = in.read_wstring ();
           long $result = (long)0;
           $result = this.getActivityCreatedTime (procId, actId);
           out = $rh.createReply();
           out.write_longlong ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 47:  // WorkflowService/AdminMisc/getActivityStartedTime
       {
         try {
           String procId = in.read_wstring ();
           String actId = in.read_wstring ();
           long $result = (long)0;
           $result = this.getActivityStartedTime (procId, actId);
           out = $rh.createReply();
           out.write_longlong ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 48:  // WorkflowService/AdminMisc/getActivityFinishTime
       {
         try {
           String procId = in.read_wstring ();
           String actId = in.read_wstring ();
           long $result = (long)0;
           $result = this.getActivityFinishTime (procId, actId);
           out = $rh.createReply();
           out.write_longlong ($result);
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
    "IDL:WorkflowService/AdminMisc:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }


} // class _AdminMiscImplBase
