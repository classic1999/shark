package org.enhydra.shark.api.client.wfservice;

/**
 * Interface used to store mapping information among XPDL participant
 * definition and shark's WfResource object.
 * @author Zoran Milakovic
 */
public interface ParticipantMap {

   /**
    * Sets the Id of participant XPDL definition of this map entry.
    *
    * @param id participant XPDL definition id.
    */
   public void setParticipantId(String id);

   /**
    * Returns the Id of participant XPDL definition of this map entry.
    *
    * @return participant XPDL definition id.
    */
   public String getParticipantId();

   /**
    * Sets the Id of XPDL package containing participant of this map entry.
    *
    * @param id XPDL package id.
    */
   public void setPackageId(String id);

   /**
    * Returns the Id of XPDL package containing participant of this map entry.
    *
    * @return XPDL package id.
    */
   public String getPackageId();

   /**
    * Sets the Id of XPDL process definition containing participant
    * of this map entry. If participant is defined at package level, this
    * has to be set to null or to empty string.
    *
    * @param id XPDL process definition id.
    */
   public void setProcessDefinitionId(String id);

   /**
    * Returns the Id of XPDL process definition containing participant of this
    * map entry. If participant is defined at package level, this
    * method returns null or empty string.
    *
    * @return XPDL process definition id.
    */
   public String getProcessDefinitionId();

   /**
    * Sets the username of shark's WfResource of this map entry.
    *
    * @param username username of shark's WfResource.
    */
   public void setUsername(String username);

   /**
    * Returns the username of shark's WfResource of this map entry.
    *
    * @return username of shark's WfResource.
    */
   public String getUsername();
	
   /**
    * Returns true is username is usernmae of a group or false if username is 
    * username of a single user.
    *
    * @return true is username is usernmae of a group or false if it is 
    * username of a single user.
    */
   public boolean getIsGroupUser();
	
   /**
    * Set flag that indicate if the user is a single user or a group of users.
    *
    * @param isGroupUser flag that indicate if the user is a single user or 
    * a group of users. true means that the username is usernmae of a group, and
    * false means that the username is username of a single user.
    */
   public void setIsGroupUser(boolean isGroupUser);

}
