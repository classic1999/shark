
package org.enhydra.shark.api.client.wfservice;

import org.enhydra.shark.api.ParticipantMappingTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;

/**
 *
 * Used to handle mapping among XPDL participant definitions and shark's
 * users.
 *
 * @author Zoran Milakovic
 */
public interface ParticipantMappingAdministration {

   /**
    * This method is used to let shark know who uses this API.
    *
    * @param    userId              String representing user Id.
    *
    */
   void connect (String userId);

   /**
    * Returns array of ParticipantMap objects that represent participants
    * from XPDL that are currently loaded into engine. The username and isGroupUser
    * attributes of these objects are irellevant.
    *
    * @return   array of ParticipantMap objects representing participants
    * from XPDL that are currently loaded into engine.
    *
    * @throws   BaseException If something unexpected happens.
    *
    */
   ParticipantMap[] getAllParticipants () throws BaseException;

   /**
    * Returns array of ParticipantMap objects that represent participants
    * from XPDL that are currently loaded into engine. The username and isGroupUser
    * attributes of these objects are irellevant.
    *
    * @param    t ParticipantMappingTransaction.
    * @return   array of ParticipantMap objects representing participants
    * from XPDL that are currently loaded into engine.
    *
    * @throws   BaseException If something unexpected happens.
    *
    */
   ParticipantMap[] getAllParticipants (ParticipantMappingTransaction t) throws BaseException;

   /**
    * Returns array of ParticipantMap objects that represent participants
    * from XPDL package determined by given Id. The username and isGroupUser
    * attributes of these objects are irellevant.
    *
    * @param    pkgId Package Id
    * @return   array of ParticipantMap objects representing participants
    * from XPDL package determined by given pkgId parameter.
    *
    * @throws   BaseException If something unexpected happens.
    *
    */
   ParticipantMap[] getAllParticipants (String pkgId) throws BaseException;

   /**
    * Returns array of ParticipantMap objects that represent participants
    * from XPDL that are currently loaded into engine. The username and isGroupUser
    * attributes of these objects are irellevant.
    *
    * @param    t ParticipantMappingTransaction.
    * @param    pkgId Package Id
    * @return   array of ParticipantMap objects representing participants
    * from XPDL package determined by given pkgId parameter.
    *
    * @throws   BaseException If something unexpected happens.
    *
    */
   ParticipantMap[] getAllParticipants (ParticipantMappingTransaction t,String pkgId) throws BaseException;

   /**
    * Returns array of ParticipantMap objects that represent participants
    * from XPDL package and process definition determined by given Ids.
    * The username and isGroupUser attributes of these objects are irellevant.
    *
    * @param    pkgId Package Id
    * @param    pDefId Process definition Id
    * @return   array of ParticipantMap objects representing participants
    * from XPDL package determined by given pkgId and pDefId parameters.
    *
    * @throws   BaseException If something unexpected happens.
    *
    */
   ParticipantMap[] getAllParticipants (String pkgId,String pDefId) throws BaseException;

   /**
    * Returns array of ParticipantMap objects that represent participants
    * from XPDL package and process definition determined by given Ids.
    * The username and isGroupUser attributes of these objects are irellevant.
    *
    * @param    t ParticipantMappingTransaction.
    * @param    pkgId Package Id
    * @param    pDefId Process definition Id
    * @return   array of ParticipantMap objects representing participants
    * from XPDL package determined by given pkgId and pDefId parameters.
    *
    * @throws   BaseException If something unexpected happens.
    *
    */
   ParticipantMap[] getAllParticipants (ParticipantMappingTransaction t,String pkgId,String pDefId) throws BaseException;

   /**
    * Returns Ids of user groups.
    *
    * @return   String array of user group Ids.
    *
    * @throws   BaseException If something unexpected happens.
    *
    */
   String[] getAllGroupnames () throws BaseException;

   /**
    * Returns Ids of user groups.
    *
    * @param    t ParticipantMappingTransaction.
    * @return   String array of user group Ids.
    *
    * @throws   BaseException If something unexpected happens.
    *
    */
   String[] getAllGroupnames (ParticipantMappingTransaction t) throws BaseException;

   /**
    * Returns usernames of all users of the system.
    *
    * @return   String array of user Ids.
    *
    * @throws   BaseException If something unexpected happens.
    *
    */
   String[] getAllUsers () throws BaseException;

   /**
    * Returns usernames of all users of the system.
    *
    * @param    t ParticipantMappingTransaction.
    * @return   String array of user Ids.
    *
    * @throws   BaseException If something unexpected happens.
    *
    */
   String[] getAllUsers (ParticipantMappingTransaction t) throws BaseException;

   /**
    * Returns all usernames that belong to the given group.
    *
    * @param    groupName name of the given group.
    * @return   String array of usernames that belong to the given group.
    *
    * @throws   BaseException If something unexpected happens.
    *
    */
   String[] getAllUsers (String groupName) throws BaseException;

   /**
    * Returns all usernames that belong to the given group.
    *
    * @param    t  ParticipantMappingTransaction.
    * @param    groupName name of the given group.
    * @return   String array of usernames that belong to the given group.
    *
    * @throws   BaseException If something unexpected happens.
    *
    */
   String[] getAllUsers (ParticipantMappingTransaction t,String groupName) throws BaseException;

   /**
    * Returns all mappings that map XPDL participants to users.
    *
    * @return   Array of ParticipantMap objects that represent mappings.
    * @throws   BaseException If something unexpected happens.
    */
   public ParticipantMap[] getAllParticipantMappings () throws BaseException; // returns ParticipantMap objects


   /**
    * Returns all mappings that map XPDL participants to users.
    *
    * @param    t  ParticipantMappingTransaction.
    * @return   Array of ParticipantMap objects that represent mappings.
    * @throws   BaseException If something unexpected happens.
    */
   public ParticipantMap[] getAllParticipantMappings (ParticipantMappingTransaction t) throws BaseException; // returns ParticipantMap objects

   /**
    * Adds new participant to username mapping.
    *
    * @param    pm  ParticipantMap containing mapping information.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public void addParticipantMapping (ParticipantMap pm) throws BaseException;

   /**
    * Adds new participant to username mapping.
    *
    * @param    t ParticipantMappingTransaction.
    * @param    pm  ParticipantMap containing mapping information.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public void addParticipantMapping (ParticipantMappingTransaction t,ParticipantMap pm) throws BaseException;

   /**
    * Removes participant from username mapping.
    *
    * @param    pm  ParticipantMap containing mapping information.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public void removeParticipantMapping (ParticipantMap pm) throws BaseException;

   /**
    * Removes participant from username mapping.
    *
    * @param    t ParticipantMappingTransaction.
    * @param    pm  ParticipantMap containing mapping information.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public void removeParticipantMapping (ParticipantMappingTransaction t,ParticipantMap pm) throws BaseException;

   /**
    * Returns all mappings for XPDL participant given by parameters.
    *
    * @param    packageId           Id of package containing participant.
    * @param    processDefinitionId Id of process definition that contains
    * participant. Can be empty string or null if participant is defined at
    * package level.
    * @param    participantId       Id of the participant.
    *
    * @return   Array of ParticipantMap objects that represent mappings.
    * @throws   BaseException If something unexpected happens.
    */
   public ParticipantMap[] getParticipantMappings (String packageId,
                                                   String processDefinitionId,
                                                   String participantId) throws BaseException; // returns ParticipantMap objects
   /**
    * Returns all mappings for XPDL participant given by parameters.
    *
    * @param    t ParticipantMappingTransaction.
    * @param    packageId           Id of package containing participant.
    * @param    processDefinitionId Id of process definition that contains
    * participant. Can be empty string or null if participant is defined at
    * package level.
    * @param    participantId       Id of the participant.
    *
    * @return   Array of ParticipantMap objects that represent mappings.
    * @throws   BaseException If something unexpected happens.
    */
   public ParticipantMap[] getParticipantMappings (ParticipantMappingTransaction t,
                                                   String packageId,
                                                   String processDefinitionId,
                                                   String participantId) throws BaseException; // returns ParticipantMap objects
   /**
    * Removes all mappings for XPDL participant given by parameters.
    *
    * @param    packageId           Id of package that contains participant.
    * @param    processDefinitionId Id of process definition that contains
    * participant. Can be empty string or null if participant is defined at
    * package level.
    * @param    participantId       Id of the participant.
    * @throws   BaseException If something unexpected happens.
    */
   public void removeParticipantMappings (String packageId,
                                          String processDefinitionId,
                                          String participantId) throws BaseException;
   /**
     * Removes all mappings for XPDL participant given by parameters.
    *
    * @param    t ParticipantMappingTransaction.
    * @param    packageId           Id of package that contains participant.
    * @param    processDefinitionId Id of process definition that contains
    * participant. Can be empty string or null if participant is defined at
    * package level.
    * @param    participantId       Id of the participant.
    * @throws   BaseException If something unexpected happens.
    */
   public void removeParticipantMappings (ParticipantMappingTransaction t,
                                          String packageId,
                                          String processDefinitionId,
                                          String participantId) throws BaseException;
   /**
    * Removes all mappings that map XPDL participants to a user with the given
    * username.
    *
    * @param    username String representing username we have to remove mappings for.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public void removeParticipantMappings (String username) throws BaseException;

   /**
    * Removes all mappings that map XPDL participants to a user with the given
    * username.
    *
    * @param    t ParticipantMappingTransaction.
    * @param    username String representing username we have to remove mappings for.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public void removeParticipantMappings (ParticipantMappingTransaction t,String username) throws BaseException;

   /**
    * Creates and returns an empty implementation of ParticipantMap interface.
    *
    * @return   Created empty ParticipantMap instance.
    */
   public ParticipantMap createParticipantMap ();

   /**
    * Creates and returns an empty implementation of ParticipantMap interface.
    *
    * @param    t  ParticipantMappingTransaction.
    * @return   Created empty ParticipantMap instance.
    */
   public ParticipantMap createParticipantMap (ParticipantMappingTransaction t);

}
