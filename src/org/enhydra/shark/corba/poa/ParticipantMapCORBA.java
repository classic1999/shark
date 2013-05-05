package org.enhydra.shark.corba.poa;

import org.enhydra.shark.corba.WorkflowService.*;
import org.enhydra.shark.corba.poa.Collective;
import org.omg.CORBA.ORB;

/**
 * Implementation of CORBA ParticipantMap interface.
 *
 * @author David Forslund
 */
public class ParticipantMapCORBA extends ParticipantMapPOA {

   private String participantId = "";

   private String packageId = "";

   private String processDefinitionId = "";

   private String username = "";

   private boolean isGroupUser;

   public ParticipantMapCORBA() {

     // toJoin.__recruit(this);
   }

   public ParticipantMapCORBA(org.enhydra.shark.api.client.wfservice.ParticipantMap pm) {
     // this(toJoin);
      setParticipantId(pm.getParticipantId());
      setPackageId(pm.getPackageId());
      setProcessDefinitionId(pm.getProcessDefinitionId());
      setUsername(pm.getUsername());
      setIsGroupUser(pm.getIsGroupUser());
   }

   public void setParticipantId(String id) {
      if (id == null) return;
      this.participantId = id;
   }

   public String getParticipantId() {
      return this.participantId;
   }

   public void setPackageId(String id) {
      if (id == null) return;
      this.packageId = id;
   }

   public String getPackageId() {
      return this.packageId;
   }

   public void setProcessDefinitionId(String id) {
      if (id == null) return;
      this.processDefinitionId = id;
   }

   public String getProcessDefinitionId() {
      return this.processDefinitionId;
   }

   public void setUsername(String username) {
      if (username == null) return;
      this.username = username;
   }

   public String getUsername() {
      return this.username;
   }

   public void setIsGroupUser(boolean isGroupUser) {
      this.isGroupUser = isGroupUser;
   }

   public boolean getIsGroupUser() {
      return this.isGroupUser;
   }

}