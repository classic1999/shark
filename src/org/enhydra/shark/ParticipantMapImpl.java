package org.enhydra.shark;

import org.enhydra.shark.api.client.wfservice.ParticipantMap;

/**
 * Implementation of ParticipantMap interface.
 * @author Zoran Milakovic
 */
public class ParticipantMapImpl
    implements ParticipantMap {

   private String participantId;
   private String packageId;
   private String processDefinitionId;
   private String username;
   private boolean isGroupUser;

   protected ParticipantMapImpl() {
   }

   public void setParticipantId( String id ) {
      this.participantId = id;
   }

   public String getParticipantId() {
      return this.participantId;
   }

   public void setPackageId( String id ) {
         this.packageId = id;
   }

   public String getPackageId() {
      return this.packageId;
   }

   public void setProcessDefinitionId( String id ) {
      this.processDefinitionId = id;
   }

   public String getProcessDefinitionId() {
      return this.processDefinitionId;
   }

   public void setUsername( String username ) {
      this.username = username;
   }

   public String getUsername() {
      return this.username;
   }

   public boolean getIsGroupUser() {
      return this.isGroupUser;
   }

   public void setIsGroupUser(boolean isGroupUser) {
      this.isGroupUser = isGroupUser;
   }

   public boolean equals(Object obj) {
      ParticipantMap pm;
      if( obj instanceof ParticipantMap ) {
         pm = ( ParticipantMap ) obj;
      if(
             compareValues(this.packageId, pm.getPackageId()) &&
             compareValues(this.participantId, pm.getParticipantId()) &&
             compareValues(this.processDefinitionId, pm.getProcessDefinitionId()) &&
             compareValues(this.username, pm.getUsername()) &&
             this.isGroupUser == pm.getIsGroupUser() )
         return true;
      }
      return false;
   }

   /**
   * Compare two values.
   * @param valA first value
   * @param valB second value
   * @return true if values are equal, false otherwise
   */
   private boolean compareValues(Object valA, Object valB) {
    boolean retVal = false;

    if( valA == null && valB == null )
      retVal = true;
    else if( valA == null && valB != null )
      retVal = false;
    else if( valA != null && valB == null )
      retVal = false;
    else if( valA.equals(valB) )
      retVal = true;

    return retVal;
  }

}
