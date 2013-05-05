package org.enhydra.shark.corba;

import org.enhydra.shark.corba.WorkflowService.*;
import org.omg.CORBA.ORB;
import org.omg.WfBase.BaseException;
import org.omg.WfBase.NameValue;

/**
 * Create participant and procedure mapping classes and provides a way
 * to retrieve its objects.
 * 
 * @author Sasa Bojanic
 */
public class MappingAdminCORBA extends _MappingAdministrationImplBase implements
                                                                     Collective {
   private SharkCORBAServer myServer;

   private String userId;

   private boolean connected = false;

   private org.enhydra.shark.api.client.wfservice.ParticipantMappingAdministration participantMappingManager;

   private org.enhydra.shark.api.client.wfservice.ApplicationMappingAdministration applicationMappingManager;

   public MappingAdminCORBA(SharkCORBAServer server,
                            org.enhydra.shark.api.client.wfservice.ParticipantMappingAdministration mm,
                            org.enhydra.shark.api.client.wfservice.ApplicationMappingAdministration aa) {
      this.myServer = server;
      this.participantMappingManager = mm;
      this.applicationMappingManager = aa;
      if (myServer.trackObjects) {
         __collective = new Collective.CollectiveCORBA();
      }
   }

   public void connect(String userId,
                       String password,
                       String engineName,
                       String scope) throws BaseException, ConnectFailed {
      this.userId = userId;

      try {
         if (!myServer.validateUser(userId, password)) { throw new ConnectFailed("Connection failed, invalid username or password"); }
         connected = true;
         participantMappingManager.connect(userId);
         applicationMappingManager.connect(userId);
      } catch (ConnectFailed cf) {
         throw cf;
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void disconnect() throws BaseException, NotConnected {
      if (!connected) { throw new NotConnected("The connection is not established..."); }
      connected = false;
      this.__disband(this._orb());
   }

   public ParticipantMap[] getAllParticipants() throws BaseException,
                                               NotConnected {
      if (!connected) { throw new NotConnected("The connection is not established..."); }
      try {
         return SharkCORBAUtilities.makeCORBAParticipantMaps(this,
                                                             participantMappingManager.getAllParticipants());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public ParticipantMap[] getAllParticipantsFromPkg(String pkgId) throws BaseException,
                                                                  NotConnected {
      if (!connected) { throw new NotConnected("The connection is not established..."); }
      try {
         return SharkCORBAUtilities.makeCORBAParticipantMaps(this,
                                                             participantMappingManager.getAllParticipants(pkgId));
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public ParticipantMap[] getAllParticipantsFromPkgProcess(String pkgId,
                                                            String pDefId) throws BaseException,
                                                                          NotConnected {
      if (!connected) { throw new NotConnected("The connection is not established..."); }
      try {
         return SharkCORBAUtilities.makeCORBAParticipantMaps(this,
                                                             participantMappingManager.getAllParticipants(pkgId));
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String[] getAllGroupnames() throws BaseException, NotConnected {
      if (!connected) { throw new NotConnected("The connection is not established..."); }
      try {
         return participantMappingManager.getAllGroupnames();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String[] getAllUsers() throws BaseException, NotConnected {
      if (!connected) { throw new NotConnected("The connection is not established..."); }
      try {
         return participantMappingManager.getAllUsers();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String[] getAllUsersForGroup(String groupName) throws BaseException,
                                                        NotConnected {
      if (!connected) { throw new NotConnected("The connection is not established..."); }
      try {
         return participantMappingManager.getAllUsers(groupName);
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public ParticipantMap[] getAllParticipantMappings() throws BaseException,
                                                      NotConnected {
      if (!connected) { throw new NotConnected("The connection is not established..."); }
      try {
         return SharkCORBAUtilities.makeCORBAParticipantMaps(this,
                                                             participantMappingManager.getAllParticipantMappings());
      } catch (Exception ex) {
         ex.printStackTrace();
         throw new BaseException();
      }
   }

   public void addParticipantMapping(ParticipantMap pm) throws BaseException,
                                                       NotConnected {
      if (!connected) { throw new NotConnected("The connection is not established..."); }
      try {
         participantMappingManager.addParticipantMapping(SharkCORBAUtilities.fillParticipantMap(participantMappingManager.createParticipantMap(),
                                                                                                pm));
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void removeParticipantMapping(ParticipantMap pm) throws BaseException,
                                                          NotConnected {
      if (!connected) { throw new NotConnected("The connection is not established..."); }
      try {
         participantMappingManager.removeParticipantMapping(SharkCORBAUtilities.fillParticipantMap(participantMappingManager.createParticipantMap(),
                                                                                                   pm));
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public ParticipantMap[] getParticipantMappings(String packageId,
                                                  String processDefinitionId,
                                                  String participantId) throws BaseException,
                                                                       NotConnected {
      if (!connected) { throw new NotConnected("The connection is not established..."); }
      try {
         return SharkCORBAUtilities.makeCORBAParticipantMaps(this,
                                                             participantMappingManager.getParticipantMappings(packageId,
                                                                                                              processDefinitionId,
                                                                                                              participantId));
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void removeParticipantMappings(String packageId,
                                         String processDefinitionId,
                                         String participantId) throws BaseException,
                                                              NotConnected {
      if (!connected) { throw new NotConnected("The connection is not established..."); }
      try {
         participantMappingManager.removeParticipantMappings(packageId,
                                                             processDefinitionId,
                                                             participantId);
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void removeParticipantMappingsForUser(String username) throws BaseException,
                                                                NotConnected {
      if (!connected) { throw new NotConnected("The connection is not established..."); }
      try {
         participantMappingManager.removeParticipantMappings(username);
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public ParticipantMap createParticipantMap() throws BaseException,
                                               NotConnected {
      if (!connected) { throw new NotConnected("The connection is not established..."); }
      return new ParticipantMapCORBA(this);
   }

   public ApplicationMap[] getAllApplications() throws BaseException,
                                               NotConnected {
      if (!connected) { throw new NotConnected("The connection is not established..."); }
      try {
         return SharkCORBAUtilities.makeCORBAApplicationMaps(this,
                                                             applicationMappingManager.getAllApplications());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public ApplicationMap[] getAllApplicationsFromPkg(String pkgId) throws BaseException,
                                                                  NotConnected {
      if (!connected) { throw new NotConnected("The connection is not established..."); }
      try {
         return SharkCORBAUtilities.makeCORBAApplicationMaps(this,
                                                             applicationMappingManager.getAllApplications(pkgId));
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public ApplicationMap[] getAllApplicationsFromPkgProcess(String pkgId,
                                                            String pDefId) throws BaseException,
                                                                          NotConnected {
      if (!connected) { throw new NotConnected("The connection is not established..."); }
      try {
         return SharkCORBAUtilities.makeCORBAApplicationMaps(this,
                                                             applicationMappingManager.getAllApplications(pkgId,
                                                                                                          pDefId));
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String[] getDefinedToolAgents() throws BaseException, NotConnected {
      if (!connected) { throw new NotConnected("The connection is not established..."); }
      try {
         return applicationMappingManager.getDefinedToolAgents();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public NameValue[] getToolAgentsInfo() throws BaseException, NotConnected {
      if (!connected) { throw new NotConnected("The connection is not established..."); }
      try {
         return SharkCORBAUtilities.makeCORBANameValueArray(this._orb(),
                                                            applicationMappingManager.getToolAgentsInfo());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String getToolAgentInfo(String toolAgentFullClassName) throws BaseException,
                                                                NotConnected {
      if (!connected) { throw new NotConnected("The connection is not established..."); }
      try {
         return applicationMappingManager.getToolAgentInfo(toolAgentFullClassName);
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void addApplicationMapping(ApplicationMap am) throws BaseException,
                                                       NotConnected {
      if (!connected) { throw new NotConnected("The connection is not established..."); }
      try {
         applicationMappingManager.addApplicationMapping(SharkCORBAUtilities.fillApplicationMap(applicationMappingManager.createApplicationMap(),
                                                                                                am));
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public ApplicationMap getApplicationMapping(String pkgId,
                                               String pDefId,
                                               String appDefId) throws BaseException,
                                                               NotConnected {
      if (!connected) { throw new NotConnected("The connection is not established..."); }
      try {
         return new ApplicationMapCORBA(this,
                                        applicationMappingManager.getApplicationMapping(pkgId,
                                                                                        pDefId,
                                                                                        appDefId));
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void removeApplicationMapping(String packageId,
                                        String processDefinitionId,
                                        String applicationId) throws BaseException,
                                                             NotConnected {
      if (!connected) { throw new NotConnected("The connection is not established..."); }
      try {
         applicationMappingManager.removeApplicationMapping(packageId,
                                                            processDefinitionId,
                                                            applicationId);
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public ApplicationMap[] getApplicationMappings() throws BaseException,
                                                   NotConnected {
      if (!connected) { throw new NotConnected("The connection is not established..."); }
      try {
         return SharkCORBAUtilities.makeCORBAApplicationMaps(this,
                                                             applicationMappingManager.getApplicationMappings());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public ApplicationMap createApplicationMap() throws BaseException,
                                               NotConnected {
      if (!connected) { throw new NotConnected("The connection is not established..."); }
      return new ApplicationMapCORBA(this);
   }

   Collective __collective;

   public void __recruit(org.omg.CORBA.Object obj) {
      if (myServer.trackObjects)
         __collective.__recruit(obj);
   }

   public void __leave(org.omg.CORBA.Object obj) {
      if (myServer.trackObjects)
         __collective.__leave(obj);
   }

   public void __disband(ORB _orb) {
      if (myServer.trackObjects) {
         __collective.__disband(_orb);
      }
      this._orb().disconnect(this);
   }
}