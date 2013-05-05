package org.enhydra.shark.corba.poa;

import org.omg.WfBase.*;
import org.enhydra.shark.corba.WorkflowService.*;


/**
 * The client interface through which client accesses the engine objects, and
 * performs the various actions on engine.
 * @author David Forslund
 */
public class AdminMiscCORBA extends AdminMiscPOA {
   private SharkCORBAServer myServer;

   private String userId;
   private boolean connected=false;

   org.enhydra.shark.api.client.wfservice.AdminMisc myAdminMisc;

   AdminMiscCORBA (SharkCORBAServer server,org.enhydra.shark.api.client.wfservice.AdminMisc am) {
      this.myServer=server;
      this.myAdminMisc=am;

   }

   public void connect(String userId, String password, String engineName, String scope) throws BaseException, ConnectFailed {
      this.userId=userId;

      try {
         if (!myServer.validateUser(userId,password)) {
            throw new ConnectFailed("Connection failed, invalid username or password");
         }
         connected=true;
         myAdminMisc.connect(userId);
      } catch (ConnectFailed cf) {
         throw cf;
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void disconnect() throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      connected=false;
      _this()._release();
   }

   public NameValue[] getPackageExtendedAttributeNameValuePairs (String pkgId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return SharkCORBAUtilities
            .makeCORBANameValueArray(myServer.getBoundORB(),
                                     myAdminMisc.getPackageExtendedAttributeNameValuePairs(pkgId));
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String[] getPackageExtendedAttributeNames(String pkgId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getPackageExtendedAttributeNames(pkgId);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String getPackageExtendedAttributeValue(String pkgId,String extAttrName) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getPackageExtendedAttributeValue(pkgId,extAttrName);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public NameValue[] getProcessDefinitionExtendedAttributeNameValuePairs (String mgrName) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return SharkCORBAUtilities
            .makeCORBANameValueArray(myServer.getBoundORB(),
                                     myAdminMisc.getProcessDefinitionExtendedAttributeNameValuePairs(mgrName));
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String[] getProcessDefinitionExtendedAttributeNames(String mgrName) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getProcessDefinitionExtendedAttributeNames(mgrName);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String getProcessDefinitionExtendedAttributeValue(String mgrName,String extAttrName) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getProcessDefinitionExtendedAttributeValue(mgrName,extAttrName);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public NameValue[] getProcessExtendedAttributeNameValuePairs (String procId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return SharkCORBAUtilities
            .makeCORBANameValueArray(myServer.getBoundORB(),
                                     myAdminMisc.getProcessExtendedAttributeNameValuePairs(procId));
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String[] getProcessExtendedAttributeNames(String procId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getProcessExtendedAttributeNames(procId);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String getProcessExtendedAttributeValue(String procId,String extAttrName) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getProcessExtendedAttributeValue(procId,extAttrName);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String getActivitiesExtendedAttributes (String procId,String actId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getActivitiesExtendedAttributes(procId,actId);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public NameValue[] getActivitiesExtendedAttributeNameValuePairs (String procId,String actId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return SharkCORBAUtilities
            .makeCORBANameValueArray(myServer.getBoundORB(),
                                     myAdminMisc.getActivitiesExtendedAttributeNameValuePairs(procId,actId));
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String[] getActivitiesExtendedAttributeNames(String procId,String actId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getActivitiesExtendedAttributeNames(procId,actId);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String getActivitiesExtendedAttributeValue(String procId,String actId,String extAttrName) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getActivitiesExtendedAttributeValue(procId,actId,extAttrName);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public NameValue[] getDefVariableExtendedAttributeNameValuePairs (String mgrName,String variableId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return SharkCORBAUtilities
            .makeCORBANameValueArray(myServer.getBoundORB(),
                                     myAdminMisc.getDefVariableExtendedAttributeNameValuePairs(mgrName,variableId));
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String[] getDefVariableExtendedAttributeNames(String mgrName,String variableId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getDefVariableExtendedAttributeNames(mgrName,variableId);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String getDefVariableExtendedAttributeValue(String mgrName,String variableId,String extAttrName) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getDefVariableExtendedAttributeValue(mgrName,variableId,extAttrName);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public NameValue[] getVariableExtendedAttributeNameValuePairs (String procId,String variableId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return SharkCORBAUtilities
            .makeCORBANameValueArray(myServer.getBoundORB(),
                                     myAdminMisc.getVariableExtendedAttributeNameValuePairs(procId,variableId));
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String[] getVariableExtendedAttributeNames(String procId,String variableId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getVariableExtendedAttributeNames(procId,variableId);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String getVariableExtendedAttributeValue(String procId,String variableId,String extAttrName) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getVariableExtendedAttributeValue(procId,variableId,extAttrName);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public NameValue[] getParticipantExtendedAttributeNameValuePairs (String pkgId,String pDefId,String participantId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return SharkCORBAUtilities
            .makeCORBANameValueArray(myServer.getBoundORB(),
                                     myAdminMisc.getParticipantExtendedAttributeNameValuePairs(pkgId,pDefId,participantId));
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String[] getParticipantExtendedAttributeNames(String pkgId,String pDefId,String participantId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getParticipantExtendedAttributeNames(pkgId,pDefId,participantId);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String getParticipantExtendedAttributeValue(String pkgId,String pDefId,String participantId,String extAttrName) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getParticipantExtendedAttributeValue(pkgId,pDefId,participantId,extAttrName);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public NameValue[] getApplicationExtendedAttributeNameValuePairs (String pkgId,String pDefId,String applicationId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return SharkCORBAUtilities
            .makeCORBANameValueArray(myServer.getBoundORB(),
                                     myAdminMisc.getApplicationExtendedAttributeNameValuePairs(pkgId,pDefId,applicationId));
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String[] getApplicationExtendedAttributeNames(String pkgId,String pDefId,String applicationId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getApplicationExtendedAttributeNames(pkgId,pDefId,applicationId);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String getApplicationExtendedAttributeValue(String pkgId,String pDefId,String applicationId,String extAttrName) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getApplicationExtendedAttributeValue(pkgId,pDefId,applicationId,extAttrName);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String getBlockActivityId (String procId,String actId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         String id = myAdminMisc.getBlockActivityId(procId,actId);
         if (id == null) id = "";
          return id;
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String getActivityDefinitionId (String procId,String actId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getActivityDefinitionId(procId,actId);
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String getProcessDefinitionId (String procId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getProcessDefinitionId(procId);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String getDefVariableName (String mgrName,String variableId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getDefVariableName(mgrName,variableId);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String getDefVariableDescription (String mgrName,String variableId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getDefVariableDescription(mgrName,variableId);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String getDefVariableJavaClassName (String mgrName,String variableId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getDefVariableJavaClassName(mgrName,variableId);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String getVariableName (String procId,String variableId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getVariableName(procId,variableId);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String getVariableDescription (String procId,String variableId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getVariableDescription(procId,variableId);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String getVariableJavaClassName (String procId,String variableId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getVariableJavaClassName(procId,variableId);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String getParticipantName (String pkgId, String pDefId, String participantId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         if (pDefId!=null && pDefId.equals("")) {
            pDefId=null;
         }
         return myAdminMisc.getParticipantName(pkgId,pDefId,participantId);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String getApplicationName (String pkgId, String pDefId, String applicationId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         if (pDefId!=null && pDefId.equals("")) {
            pDefId=null;
         }
         return myAdminMisc.getParticipantName(pkgId,pDefId,applicationId);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String getProcessMgrPkgId (String name) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getProcessMgrPkgId(name);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String getProcessMgrProcDefId (String name) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getProcessMgrProcDefId(name);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String getProcessMgrProcDefName (String name) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getProcessMgrProcDefName(name);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String getProcessRequesterUsername (String procId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getProcessRequesterUsername(procId);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String getActivityResourceUsername(String procId,String actId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getActivityResourceUsername(procId,actId);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public long getProcessCreatedTime (String procId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getProcessCreatedTime(procId);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public long getProcessStartedTime (String procId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getProcessStartedTime(procId);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public long getProcessFinishTime (String procId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getProcessFinishTime(procId);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public long getActivityCreatedTime (String procId,String actId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getActivityCreatedTime(procId,actId);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public long getActivityStartedTime (String procId,String actId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getActivityStartedTime(procId,actId);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public long getActivityFinishTime (String procId,String actId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myAdminMisc.getActivityFinishTime(procId,actId);
      } catch (Exception e) {
         throw new BaseException();
      }
   }
}
