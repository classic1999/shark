package org.enhydra.shark;

import java.util.Properties;
import org.enhydra.shark.api.RootError;
import org.enhydra.shark.api.internal.appmappersistence.ApplicationMappingManager;
import org.enhydra.shark.api.internal.assignment.AssignmentManager;
import org.enhydra.shark.api.internal.authentication.AuthenticationManager;
import org.enhydra.shark.api.internal.caching.CacheMgr;
import org.enhydra.shark.api.internal.eventaudit.EventAuditManagerInterface;
import org.enhydra.shark.api.internal.instancepersistence.PersistentManagerInterface;
import org.enhydra.shark.api.internal.interoperability.WfEngineInteroperability;
import org.enhydra.shark.api.internal.limitagent.LimitAgentManager;
import org.enhydra.shark.api.internal.logging.LoggingManager;
import org.enhydra.shark.api.internal.partmappersistence.ParticipantMappingManager;
import org.enhydra.shark.api.internal.processlocking.LockMaster;
import org.enhydra.shark.api.internal.repositorypersistence.RepositoryPersistenceManager;
import org.enhydra.shark.api.internal.scripting.ScriptingManager;
import org.enhydra.shark.api.internal.scriptmappersistence.ScriptMappingManager;
import org.enhydra.shark.api.internal.security.SecurityManager;
import org.enhydra.shark.api.internal.toolagent.ToolAgentFactory;
import org.enhydra.shark.api.internal.transaction.TransactionFactory;
import org.enhydra.shark.api.internal.usergroup.UserGroupManager;
import org.enhydra.shark.api.internal.usertransaction.UserTransactionFactory;
import org.enhydra.shark.api.internal.working.CallbackUtilities;
import org.enhydra.shark.api.internal.working.ObjectFactory;
import org.enhydra.shark.api.internal.working.ToolAgentManager;
import org.enhydra.shark.xpdl.XMLInterface;
import org.enhydra.shark.xpdl.XMLInterfaceForJDK13;

/**
 * This class is internally used to get all shark's managers.
 * @author Sasa Bojanic, Nenad Stefanovic, Vladimir Puskas
 */
public final class SharkEngineManager {
   private CallbackUtilities callbackUtilities;
   private ObjectFactory objectFactory;
   private ToolAgentManager toolAgentManager;
   private XMLInterface xmlInterface;


   private AssignmentManager assManager;
   private AuthenticationManager authManager;
   private CacheMgr cacheManager;
   private PersistentManagerInterface instancePersistenceMgr;
   private EventAuditManagerInterface instanceEventAuditMgr;
   private LimitAgentManager limitAgentManager;
   private LockMaster lockMaster;
   private LoggingManager logManager;
   private RepositoryPersistenceManager repPersistenceMgr;
   private ScriptingManager scriptingManager;
   private SecurityManager securityManager;
   private ToolAgentFactory toolAgentFactory;
   private TransactionFactory transactionFactory;
   private UserGroupManager userGroupManager;
   private UserTransactionFactory userTransactionFactory;
   private ParticipantMappingManager participantMappings;
   private ApplicationMappingManager applicationMappings;
   private ScriptMappingManager scriptMappings;
   private WfEngineInteroperability wfEngineInteroperabilityMgr;

   // the one and only instance of this class
   private static SharkEngineManager engineManager;

   static SharkEngineManager getInstance () {
      if (engineManager==null) {
         engineManager=new SharkEngineManager();
      }
      return engineManager;
   }


   void init (Properties properties) {
      String cbuClassName = properties.getProperty
         ("CallbackUtilitiesClassName",
          "org.enhydra.shark.CallbackUtil");

      String objectFactoryClassName=properties.getProperty
         ("ObjectFactoryClassName",
          "org.enhydra.shark.SharkObjectFactory");

      String tamClassName = properties.getProperty
         ("ToolAgentManagerClassName",
          "org.enhydra.shark.ToolAgentManagerImpl");

      String amClassName =properties.getProperty
         ("AssignmentManagerClassName");

      String auClassName =properties.getProperty
         ("AuthenticationManagerClassName");

      String cmClassName =properties.getProperty
         ("CacheManagerClassName");

      String persistentManagerClassName=properties.getProperty
         ("InstancePersistenceManagerClassName");

      String eventauditManagerClassName=properties.getProperty
         ("EventAuditManagerClassName");

      String lmClassName = properties.getProperty
         ("LimitAgentManagerClassName");

      String lockMasterClassName=properties.getProperty
         ("LockMasterClassName");

      String logClassName =properties.getProperty
         ("LoggingManagerClassName");

      String pmmClassName=properties.getProperty
         ("ParticipantMapPersistenceManagerClassName");

      String ammClassName=properties.getProperty
         ("ApplicationMapPersistenceManagerClassName");

      String smmClassName=properties.getProperty
         ("ScriptMapPersistenceManagerClassName");

      String rpClassName=properties.getProperty
         ("RepositoryPersistenceManagerClassName");

      String smClassName =properties.getProperty
         ("ScriptingManagerClassName");

      String seClassName =properties.getProperty
         ("SecurityManagerClassName");

      String tafClassName=properties.getProperty
         ("ToolAgentFactoryClassName");

      String tfClassName =properties.getProperty
         ("TransactionManagerClassName");

      String ugClassName =properties.getProperty
         ("UserGroupManagerClassName");

      String utfClassName =properties.getProperty
         ("UserTransactionManagerClassName");

      String wfEIClassName=properties.getProperty
         ("WfEngineInteroperabilityManagerClassName");

      ClassLoader cl=getClass().getClassLoader();

      try {
         callbackUtilities=(CallbackUtilities)cl.loadClass(cbuClassName).newInstance();
         callbackUtilities.setProperties(properties);
         objectFactory =(ObjectFactory)cl.loadClass(objectFactoryClassName).newInstance();
         toolAgentManager = (ToolAgentManager) cl.loadClass(tamClassName).newInstance();
         xmlInterface=new XMLInterfaceForJDK13();
      } catch (Throwable ex) {
         String msg="SharkEngineManager -> Problems instantiating core managers - ";
         if (callbackUtilities==null) {
            msg+="Can't find CallbackUtilities class '"+cbuClassName+"' in classpath!";
         } else if (objectFactory==null) {
            msg+="Can't find ObjectFactory class '"+objectFactoryClassName+"' in classpath!";
         } else if (toolAgentManager==null) {
            msg+="Can't find ToolAgentManager class '"+tamClassName+"' in classpath!";
         } else {
            msg+="Unknown problem!";
         }
         System.err.println(msg);
         throw new RootError(msg,ex);
      }

      try {
         logManager = (LoggingManager)cl.loadClass(logClassName).newInstance();
         logManager.configure(callbackUtilities);
         callbackUtilities.info("SharkEngineManager -> Working with '"+cbuClassName+"' implementation of core CallbackUtilities API");
         callbackUtilities.info("SharkEngineManager -> Working with '"+objectFactoryClassName+"' implementation of core SharkObjectFactory API");
         callbackUtilities.info("SharkEngineManager -> Working with '"+tamClassName+"' implementation of core ToolAgentManager API");
         callbackUtilities.info("SharkEngineManager -> Working with '"+logClassName+"' implementation of Logging API");
      } catch (Exception ex) {
         boolean throwError=true;
         String msg="SharkEngineManager -> Can't work - ";
         if (logClassName==null || logClassName.trim().equals("")) {
            msg="SharkEngineManager -> Working without Logging API implementation - ";
            msg+="LoggingManager is not specified.";
            throwError=false;
         } else if (logManager==null) {
            msg+="Can't find LoggingManager class '"+logClassName+"' in classpath!";
         } else {
            msg+="Problems while configuring LoggingManager!";
         }
         callbackUtilities.info(msg);
         if (throwError) {
            throw new RootError(msg,ex);
         }
      }

      try {
         assManager=(AssignmentManager)cl.loadClass(amClassName).newInstance();
         assManager.configure(callbackUtilities);
         callbackUtilities.info("SharkEngineManager -> Working with '"+amClassName+"' implementation of Assignment API");
      } catch (Exception ex) {
         boolean throwError=true;
         String msg="SharkEngineManager -> Can't work - ";
         if (amClassName==null || amClassName.trim().equals("")) {
            msg="SharkEngineManager -> Working without Assignment API implementation - ";
            msg+="AssignmentManager is not specified.";
            throwError=false;
         } else if (assManager==null) {
            msg+="Can't find AssignmentManager class '"+amClassName+"' in classpath!";
         } else {
            msg+="Problems while configuring AssignmentManager!";
         }
         callbackUtilities.info(msg);
         if (throwError) {
            throw new RootError(msg,ex);
         }
      }

      try {
         authManager=(AuthenticationManager)cl.loadClass(auClassName).newInstance();
         authManager.configure(callbackUtilities);
         callbackUtilities.info("SharkEngineManager -> Working with '"+auClassName+"' implementation of Authentication API");
      } catch (Exception ex) {
         boolean throwError=true;
         String msg="SharkEngineManager -> Can't work - ";
         if (auClassName==null || auClassName.trim().equals("")) {
            msg="SharkEngineManager -> Working without Authentication API implementation - ";
            msg+="AuthenticationManager is not specified.";
            throwError=false;
         } else if (authManager==null) {
            msg+="Can't find AuthenticationManager class '"+auClassName+"' in classpath!";
         } else {
            msg+="Problems while configuring AuthenticationManager!";
         }
         callbackUtilities.info(msg);
         if (throwError) {
            throw new RootError(msg,ex);
         }
      }

      try {
         cacheManager=(CacheMgr)cl.loadClass(cmClassName).newInstance();
         cacheManager.configure(callbackUtilities);
         callbackUtilities.info("SharkEngineManager -> Working with '"+cmClassName+"' implementation of Caching API");
      } catch (Exception ex) {
         boolean throwError=true;
         String msg="SharkEngineManager -> Can't work - ";
         if (cmClassName==null || cmClassName.trim().equals("")) {
            msg="SharkEngineManager -> Working without Caching API implementation - ";
            msg+="CacheManager is not specified.";
            throwError=false;
         } else if (cacheManager==null) {
            msg+="Can't find CacheManager class '"+cmClassName+"' in classpath!";
         } else {
            msg+="Problems while configuring CacheManager!";
         }
         callbackUtilities.info(msg);
         if (throwError) {
            throw new RootError(msg,ex);
         }
      }

      try {
         instancePersistenceMgr=(PersistentManagerInterface)cl.loadClass(persistentManagerClassName).newInstance();
         instancePersistenceMgr.configure(callbackUtilities);
         callbackUtilities.info("SharkEngineManager -> Working with '"+persistentManagerClassName+"' implementation of InstancePersistence API");
      } catch (Exception ex) {
         boolean throwError=true;
         String msg="SharkEngineManager -> Can not work - ";
         if (persistentManagerClassName==null || persistentManagerClassName.trim().equals("")) {
            msg+="InstancePersistenceManager is not specified.";
         } else if (instancePersistenceMgr==null) {
            msg+="Can't find InstancePersistenceManager class '"+persistentManagerClassName+"' in classpath";
         } else {
            msg+="Problems while configuring InstancePersistenceManager!";
         }
         callbackUtilities.error(msg,ex.getMessage());
         throw new RootError(msg,ex);
      }

      try {
         instanceEventAuditMgr=(EventAuditManagerInterface)cl.loadClass(eventauditManagerClassName).newInstance();
         instanceEventAuditMgr.configure(callbackUtilities);
         callbackUtilities.info("SharkEngineManager -> Working with '"+eventauditManagerClassName+"' implementation of EventAudit API");
      } catch (Exception ex) {
         boolean throwError=true;
         String msg="SharkEngineManager -> Can't work - ";
         if (eventauditManagerClassName==null || eventauditManagerClassName.trim().equals("")) {
            msg="SharkEngineManager -> Working without EventAudit API implementation - ";
            msg+="EventAuditManager is not specified.";
            throwError=false;
         } else if (instanceEventAuditMgr==null) {
            msg+="Can't find EventAuditManager class '"+eventauditManagerClassName+"' in classpath!";
         } else {
            msg+="Problems while configuring EventAuditManager!";
         }
         callbackUtilities.info(msg);
         if (throwError) {
            throw new RootError(msg,ex);
         }
      }

      try {
         limitAgentManager=(LimitAgentManager)cl.loadClass(lmClassName).newInstance();
         limitAgentManager.configure(callbackUtilities);
         callbackUtilities.info("SharkEngineManager -> Working with '"+lmClassName+"' implementation of Limit API");
      } catch (Exception ex) {
         boolean throwError=true;
         String msg="SharkEngineManager -> Can't work - ";
         if (lmClassName==null || lmClassName.trim().equals("")) {
            msg="SharkEngineManager -> Working without Limit API implementation - ";
            msg+="LimitAgentManager is not specified.";
            throwError=false;
         } else if (limitAgentManager==null) {
            msg+="Can't find LimitAgentManager class '"+lmClassName+"' in classpath!";
         } else {
            msg+="Problems while configuring LimitAgentManager!";
         }
         callbackUtilities.info(msg);
         if (throwError) {
            throw new RootError(msg,ex);
         }
      }

      try {
         lockMaster = (LockMaster)cl.loadClass(lockMasterClassName).newInstance();
         lockMaster.configure(callbackUtilities);
         callbackUtilities.info("SharkEngineManager -> Working with '"+lockMasterClassName+"' implementation of ProcessLocking API");
      } catch (Exception ex) {
         boolean throwError=true;
         String msg="SharkEngineManager -> Can't work - ";
         if (lockMasterClassName==null || lockMasterClassName.trim().equals("")) {
            msg="SharkEngineManager -> Working without ProcessLocking API implementation - ";
            msg+="LockMaster is not specified.";
            throwError=false;
         } else if (lockMaster==null) {
            msg+="Can't find LockMaster class '"+lockMasterClassName+"' in classpath!";
         } else {
            msg+="Problems while configuring LockMaster!";
         }
         callbackUtilities.info(msg);
         if (throwError) {
            throw new RootError(msg,ex);
         }
      }

      try {
         participantMappings =(ParticipantMappingManager)cl.loadClass(pmmClassName).newInstance();
         participantMappings.configure(callbackUtilities);
         callbackUtilities.info("SharkEngineManager -> Working with '"+pmmClassName+"' implementation of ParticipantMapPersistence API");
      } catch (Exception ex) {
         boolean throwError=true;
         String msg="SharkEngineManager -> Can't work - ";
         if (pmmClassName==null || pmmClassName.trim().equals("")) {
            msg="SharkEngineManager -> Working without ParticipantMapPersistence API implementation - ";
            msg+="ParticipantMapPersistenceManager is not specified.";
            throwError=false;
         } else if (participantMappings==null) {
            msg+="Can't find ParticipantMapPersistenceManager class '"+pmmClassName+"' in classpath!";
         } else {
            msg+="Problems while configuring ParticipantMapPersistenceManager!";
         }
         callbackUtilities.info(msg);
         if (throwError) {
            throw new RootError(msg,ex);
         }
      }

      try {
         applicationMappings=(ApplicationMappingManager)cl.loadClass(ammClassName).newInstance();
         applicationMappings.configure(callbackUtilities);
         callbackUtilities.info("SharkEngineManager -> Working with '"+ammClassName+"' implementation of ApplicationMapPersistence API");
      } catch (Exception ex) {
         boolean throwError=true;
         String msg="SharkEngineManager -> Can't work - ";
         if (ammClassName==null || ammClassName.trim().equals("")) {
            msg="SharkEngineManager -> Working without ApplicationMapPersistence API implementation - ";
            msg+="ApplicationMapPersistenceManager is not specified.";
            throwError=false;
         } else if (applicationMappings==null) {
            msg+="Can't find ApplicationMapPersistenceManager class '"+ammClassName+"' in classpath!";
         } else {
            msg+="Problems while configuring ApplicationMapPersistenceManager!";
         }
         callbackUtilities.info(msg);
         if (throwError) {
            throw new RootError(msg,ex);
         }
      }

      try {
         scriptMappings=(ScriptMappingManager)cl.loadClass(smmClassName).newInstance();
         scriptMappings.configure(callbackUtilities);
         callbackUtilities.info("SharkEngineManager -> Working with '"+smmClassName+"' implementation of ScriptMapPersistence API");
      } catch (Exception ex) {
         boolean throwError=false;
         //String msg="SharkEngineManager -> Can't work - ";
         String msg="SharkEngineManager -> ";
         if (smmClassName==null || smmClassName.trim().equals("")) {
            msg="SharkEngineManager -> Working without ScriptMapPersistence API implementation - ";
            msg+="ScriptMapPersistenceManager is not specified.";
            throwError=false;
         } else if (scriptMappings==null) {
            msg+="Can't find ScriptMapPersistenceManager class '"+smmClassName+"' in classpath!";
         } else {
            msg+="Problems while configuring ScriptMapPersistenceManager!";
         }
         callbackUtilities.info(msg);
         if (throwError) {
            throw new RootError(msg,ex);
         }
      }

      try {
         repPersistenceMgr =(RepositoryPersistenceManager)cl.loadClass(rpClassName).newInstance();
         repPersistenceMgr.configure(callbackUtilities);
         callbackUtilities.info("SharkEngineManager -> Working with '"+rpClassName+"' implementation of RepositoryPersistence API");
      } catch (Exception ex) {
         boolean throwError=true;
         String msg="SharkEngineManager -> Can not work - ";
         if (rpClassName==null || rpClassName.trim().equals("")) {
            msg+="RepositoryPersistenceManager is not specified.";
         } else if (repPersistenceMgr==null) {
            msg+="Can't find RepositoryPersistenceManager class '"+rpClassName+"' in classpath";
         } else {
            msg+="Problems while configuring RepositoryPersistenceManager!";
         }
         callbackUtilities.error(msg,ex.getMessage());
         throw new RootError(msg,ex);
      }

      try {
         scriptingManager=(ScriptingManager)cl.loadClass(smClassName).newInstance();
         scriptingManager.configure(callbackUtilities);
         callbackUtilities.info("SharkEngineManager -> Working with '"+smClassName+"' implementation of Scripting API");
      } catch (Exception ex) {
         boolean throwError=true;
         String msg="SharkEngineManager -> Can not work - ";
         if (smClassName==null || smClassName.trim().equals("")) {
            msg+="ScriptingManager is not specified.";
         } else if (scriptingManager==null) {
            msg+="Can't find ScriptingManager class '"+smClassName+"' in classpath";
         } else {
            msg+="Problems while configuring ScriptingManager!";
         }
         callbackUtilities.error(msg,ex.getMessage());
         throw new RootError(msg,ex);
      }

      try {
         securityManager=(SecurityManager)cl.loadClass(seClassName).newInstance();
         securityManager.configure(callbackUtilities);
         callbackUtilities.info("SharkEngineManager -> Working with '"+seClassName+"' implementation of Security API");
      } catch (Exception ex) {
         boolean throwError=true;
         String msg="SharkEngineManager -> Can't work - ";
         if (seClassName==null || seClassName.trim().equals("")) {
            msg="SharkEngineManager -> Working without Security API implementation - ";
            msg+="SecurityManager is not specified.";
            throwError=false;
         } else if (securityManager==null) {
            msg+="Can't find SecurityManager class '"+seClassName+"' in classpath!";
         } else {
            msg+="Problems while configuring SecurityManager!";
         }
         callbackUtilities.info(msg);
         if (throwError) {
            throw new RootError(msg,ex);
         }
      }

      try {
         toolAgentFactory=(ToolAgentFactory)cl.loadClass(tafClassName).newInstance();
         toolAgentFactory.configure(callbackUtilities);
         callbackUtilities.info("SharkEngineManager -> Working with '"+tafClassName+"' implementation of ToolAgent API");
      } catch (Exception ex) {
         boolean throwError=true;
         String msg="SharkEngineManager -> Can't work - ";
         if (tafClassName==null || tafClassName.trim().equals("")) {
            msg="SharkEngineManager -> Working without ToolAgent API implementation - ";
            msg+="ToolAgentFactory is not specified.";
            throwError=false;
         } else if (toolAgentFactory==null) {
            msg+="Can't find ToolAgentFactory class '"+tafClassName+"' in classpath!";
         } else {
            msg+="Problems while configuring ToolAgentFactory!";
         }
         callbackUtilities.info(msg);
         if (throwError) {
            throw new RootError(msg,ex);
         }
      }

      try {
         transactionFactory=(TransactionFactory)cl.loadClass(tfClassName).newInstance();
         transactionFactory.configure(callbackUtilities);
         callbackUtilities.info("SharkEngineManager -> Working with '"+tfClassName+"' implementation of Transaction API");
      } catch (Exception ex) {
         boolean throwError=true;
         String msg="SharkEngineManager -> Can't work - ";
         if (tfClassName==null || tfClassName.trim().equals("")) {
            msg="SharkEngineManager -> Working without Transaction API implementation - ";
            msg+="TransactionManager is not specified.";
            throwError=false;
         } else if (transactionFactory==null) {
            msg+="Can't find TransactionManager class '"+tfClassName+"' in classpath!";
         } else {
            msg+="Problems while configuring TransactionManager!";
         }
         callbackUtilities.info(msg);
         if (throwError) {
            throw new RootError(msg,ex);
         }
      }

      try {
         userGroupManager=(UserGroupManager)cl.loadClass(ugClassName).newInstance();
         userGroupManager.configure(callbackUtilities);
         callbackUtilities.info("SharkEngineManager -> Working with '"+ugClassName+"' implementation of UserGroup API");
      } catch (Exception ex) {
         boolean throwError=true;
         String msg="SharkEngineManager -> Can't work - ";
         if (ugClassName==null || ugClassName.trim().equals("")) {
            msg="SharkEngineManager -> Working without UserGroup API implementation - ";
            msg+="UserGroupManager is not specified.";
            throwError=false;
         } else if (userGroupManager==null) {
            msg+="Can't find UserGroupManager class '"+ugClassName+"' in classpath!";
         } else {
            msg+="Problems while configuring UserGroupManager!";
         }
         callbackUtilities.info(msg);
         if (throwError) {
            throw new RootError(msg,ex);
         }
      }

      try {
         userTransactionFactory=(UserTransactionFactory)cl.loadClass(utfClassName).newInstance();
         userTransactionFactory.configure(callbackUtilities);
         callbackUtilities.info("SharkEngineManager -> Working with '"+utfClassName+"' implementation of UserTransaction API");
      } catch (Exception ex) {
         boolean throwError=true;
         String msg="SharkEngineManager -> Can't work - ";
         if (utfClassName==null || utfClassName.trim().equals("")) {
            msg="SharkEngineManager -> Working without UserTransaction API implementation - ";
            msg+="UserTransactionManager is not specified.";
            throwError=false;
         } else if (userTransactionFactory==null) {
            msg+="Can't find UserTransactionManager class '"+utfClassName+"' in classpath!";
         } else {
            msg+="Problems while configuring UserTransactionManager!";
         }
         callbackUtilities.info(msg);
         if (throwError) {
            throw new RootError(msg,ex);
         }
      }

      try {
         wfEngineInteroperabilityMgr=(WfEngineInteroperability)cl.loadClass(wfEIClassName).newInstance();
         wfEngineInteroperabilityMgr.configure(callbackUtilities);
         callbackUtilities.info("SharkEngineManager -> Working with '"+wfEIClassName+"' implementation of WfEngineInteroperability API");
      } catch (Exception ex) {
         boolean throwError=true;
         String msg="SharkEngineManager -> Can't work - ";
         if (wfEIClassName==null || wfEIClassName.trim().equals("")) {
            msg="SharkEngineManager -> Working without wfEngineInteroperability API implementation - ";
            msg+="WfEngineInteroperability implementation is not specified.";
            throwError=false;
         } else if (wfEngineInteroperabilityMgr==null) {
            msg+="Can't find WfEngineInteroperability class '"+wfEIClassName+"' in classpath!";
         } else {
            msg+="Problems while configuring wfEngineInteroperability implementation "+wfEIClassName+"!";
         }
         callbackUtilities.info(msg);
         if (throwError) {
            throw new RootError(msg,ex);
         }
      }

   }

   CallbackUtilities getCallbackUtilities () {
      return callbackUtilities;
   }

   ObjectFactory getObjectFactory () {
      return objectFactory;
   }

   ToolAgentManager getToolAgentManager () {
      return toolAgentManager;
   }

   XMLInterface getXMLInterface () {
      return xmlInterface;
   }



   AssignmentManager getAssignmentManager(){
      return assManager;
   }

   AuthenticationManager getAuthenticationManager(){
      return authManager;
   }

   CacheMgr getCacheManager () {
      return cacheManager;
   }

   PersistentManagerInterface getInstancePersistenceManager() {
      return instancePersistenceMgr;
   }

   EventAuditManagerInterface getEventAuditManager() {
      return instanceEventAuditMgr;
   }

   LimitAgentManager getLimitAgentManager () {
      return limitAgentManager;
   }

   LockMaster getLockMaster() {
      return lockMaster;
   }

   ParticipantMappingManager getParticipantMapPersistenceManager(){
      return participantMappings;
   }

   ApplicationMappingManager getApplicationMapPersistenceManager(){
      return applicationMappings;
   }

   ScriptMappingManager getScriptMapPersistenceManager(){
      return scriptMappings;
   }

   RepositoryPersistenceManager getRepositoryPersistenceManager(){
      return repPersistenceMgr;
   }

   ScriptingManager getScriptingManager () {
      return scriptingManager;
   }

   SecurityManager getSecurityManager(){
      return securityManager;
   }

   ToolAgentFactory getToolAgentFactory() {
      return toolAgentFactory;
   }

   TransactionFactory getTransactionFactory() {
      return transactionFactory;
   }

   UserGroupManager getUserGroupManager(){
      return userGroupManager;
   }

   UserTransactionFactory getUserTransactionFactory() {
      return userTransactionFactory;
   }

   LoggingManager getLoggingManager(){
      return logManager;
   }

   WfEngineInteroperability getWfEngineInteroperabilityMgr () {
      return wfEngineInteroperabilityMgr;
   }

}
