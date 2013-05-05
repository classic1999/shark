package org.enhydra.shark.partmappersistence;

import org.enhydra.shark.api.internal.partmappersistence.ParticipantMap;

/**
 * Implementation of ParticipantMap interface.If value for field is
 * not set, field will have value null.
 * @author Zoran Milakovic
 */
public class DODSParticipantMap
   implements ParticipantMap {

   private String participantId;
   private String packageId;
   private String processDefinitionId;
   private String username;
   private boolean isGroupUser;

   public DODSParticipantMap() {
   }

   public DODSParticipantMap(
      String participantId,
      String packageId,
      String processDefinitionId,
      String username
   ) {
      setParticipantId( participantId );
      setPackageId( packageId );
      setProcessDefinitionId( processDefinitionId );
      setUsername( username );
   }

   public void setParticipantId( String id ) {
      if ( id != null && !id.trim().equals( "" ) )
         this.participantId = id;
      else
         this.participantId = null;
   }

   public String getParticipantId() {
      return this.participantId;
   }

   public void setPackageId( String id ) {
      if ( id != null && !id.trim().equals( "" ) )
         this.packageId = id;
      else
         this.packageId = null;
   }

   public String getPackageId() {
      return this.packageId;
   }

   public void setProcessDefinitionId( String id ) {
      if ( id != null && !id.trim().equals( "" ) )
         this.processDefinitionId = id;
      else
         this.processDefinitionId = null;
   }

   public String getProcessDefinitionId() {
      return this.processDefinitionId;
   }

   public void setUsername( String username ) {
      if ( username != null && !username.trim().equals( "" ) )
         this.username = username;
      else
         this.username = null;
   }

   public String getUsername() {
      return this.username;
   }

   public boolean getIsGroupUser() {
      return isGroupUser;
   }

   public void setIsGroupUser(boolean isGroupUser) {
      this.isGroupUser = isGroupUser;
   }

   public String toString() {
      String retVal = "";
      retVal += "\nprocessDefId = "+this.processDefinitionId;
      retVal += "\npackageId = "+this.packageId;
      retVal += "\nusername = "+this.username;
      retVal += "\nparticipantId = "+this.participantId;
      retVal += "\nisGroupUser="+this.isGroupUser;
      return retVal;
   }

}
