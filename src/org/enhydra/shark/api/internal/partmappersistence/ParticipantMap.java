package org.enhydra.shark.api.internal.partmappersistence;

/**
 * Interface for mapping between participant and process.
 * @author Zoran Milakovic
 */
public interface ParticipantMap {

   public void setParticipantId(String id);

   public String getParticipantId();

   public void setPackageId(String id);

   public String getPackageId();

   public void setProcessDefinitionId(String id);

   public String getProcessDefinitionId();

   public void setUsername(String username);

   public String getUsername();

   public boolean getIsGroupUser();

   //public boolean equalsByKeys(ParticipantMap pm);

   public void setIsGroupUser(boolean isGroupUser);

}
