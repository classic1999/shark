package org.enhydra.shark.api.internal.partmappersistence;

import org.enhydra.shark.api.ParticipantMappingTransaction;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.TransactionException;
import org.enhydra.shark.api.internal.working.CallbackUtilities;
import java.util.List;

/**
 * Interface represents mappings between participants and shak users.
 * @author Zoran Milakovic
 */
public interface ParticipantMappingManager
{
   void configure (CallbackUtilities cus) throws RootException;

   public boolean saveParticipantMapping( ParticipantMappingTransaction trans, ParticipantMap pm ) throws RootException;

   public boolean deleteParticipantMapping( ParticipantMappingTransaction trans, ParticipantMap pm ) throws RootException;

   public List getAllParticipantMappings( ParticipantMappingTransaction trans ) throws RootException;

   public boolean doesParticipantMappingExist (ParticipantMappingTransaction trans,ParticipantMap pm) throws RootException;

   public ParticipantMap createParticipantMap ();

   public List getParticipantMappings(
      ParticipantMappingTransaction trans,
      String packageId,
      String processDefinitionId,
      String participantId ) throws RootException;

   public boolean deleteParticipantMappings(
      ParticipantMappingTransaction trans,
      String packageId,
      String processDefinitionId,
      String participantId ) throws RootException;

   public boolean deleteParticipantMappings( ParticipantMappingTransaction trans, String username ) throws RootException;

   public ParticipantMappingTransaction getParticipantMappingTransaction() throws TransactionException;
}

