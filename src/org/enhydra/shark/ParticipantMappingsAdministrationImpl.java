
package org.enhydra.shark;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.enhydra.shark.api.ParticipantMappingTransaction;
import org.enhydra.shark.api.RepositoryTransaction;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfservice.ParticipantMap;
import org.enhydra.shark.api.client.wfservice.ParticipantMappingAdministration;
import org.enhydra.shark.api.internal.partmappersistence.ParticipantMappingManager;
import org.enhydra.shark.api.internal.repositorypersistence.RepositoryPersistenceManager;
import org.enhydra.shark.xpdl.elements.Participant;
import org.enhydra.shark.xpdl.elements.Participants;
import org.enhydra.shark.xpdl.elements.WorkflowProcess;
import org.enhydra.shark.xpdl.elements.WorkflowProcesses;

/**
 * Implementation of ParticipantMappings interface.
 * Provide methods to manage mappings among shark's users and  XPDL participant definitions.
 *
 * @author Zoran Milakovic
 */
public class ParticipantMappingsAdministrationImpl implements ParticipantMappingAdministration {


   private ParticipantMappingManager pmanager;
   private String userId="Unknown";

   protected ParticipantMappingsAdministrationImpl()
   {
      pmanager=SharkEngineManager.getInstance().getParticipantMapPersistenceManager();
   }

   public void connect (String userId) {
      this.userId=userId;
   }

   public ParticipantMap[] getAllParticipants() throws BaseException {
      ParticipantMap[] retVal;
      ParticipantMappingTransaction t = null;
      try {
         t = SharkUtilities.createParticipantMappingTransaction();
         retVal = getAllParticipants(t);
      } catch (RootException e) {
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseMappingTransaction(t);
      }
      return retVal;
   }


   public ParticipantMap[] getAllParticipants(ParticipantMappingTransaction t)
      throws BaseException {
      List pl=new ArrayList();

      List packageIds=null;

      RepositoryPersistenceManager rpm=SharkEngineManager
         .getInstance()
         .getRepositoryPersistenceManager();

      RepositoryTransaction rt=null;
      try {
         rt = SharkUtilities.createRepositoryTransaction();
         packageIds=rpm.getExistingXPDLIds(rt);
      } catch (Exception e) {
         throw new BaseException(e);
      } finally {
         SharkUtilities.releaseRepositoryTransaction(rt);
      }

      Iterator pkgIdsIter=packageIds.iterator();
      while (pkgIdsIter.hasNext())
      {
         String pkgId=(String)pkgIdsIter.next();
         pl.addAll(getParticipantsFromPackage(pkgId));
      }
      ParticipantMap[] pms=new ParticipantMap[pl.size()];
      pl.toArray(pms);
      return pms;
   }

   public ParticipantMap[] getAllParticipantMappings() throws BaseException {
      ParticipantMap[] retVal;
      ParticipantMappingTransaction t = null;
      try {
         t = SharkUtilities.createParticipantMappingTransaction();
         retVal = getAllParticipantMappings(t);
      } catch (RootException e) {
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseMappingTransaction(t);
      }
      return retVal;
   }

   public ParticipantMap[] getAllParticipantMappings(ParticipantMappingTransaction t)
      throws BaseException {
      if (pmanager==null) throw new BaseException("The shark is configured to work without internal implementation of this API");
      try
      {
         List clientMapList = new ArrayList();
         List persistentMapList = pmanager.getAllParticipantMappings(t);
         for ( int i = 0; i < persistentMapList.size(); i++ )
         {
            clientMapList.add( createClientMapFromPersistentParticipantMap(
                                                                             ( org.enhydra.shark.api.internal.partmappersistence.ParticipantMap )
                                    persistentMapList.get( i )
                              ) );
         }
         ParticipantMap[] pma=new ParticipantMap[clientMapList.size()];
         clientMapList.toArray(pma);
         return pma;
      }
      catch ( Exception e )
      {
         throw new BaseException(e);
      }
   }

   public ParticipantMap[] getAllParticipants (String pkgId) throws BaseException {
      ParticipantMap[] retVal;
      ParticipantMappingTransaction t = null;
      try {
         t = SharkUtilities.createParticipantMappingTransaction();
         retVal = getAllParticipants(t,pkgId);
      } catch (RootException e) {
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseMappingTransaction(t);
      }
      return retVal;
   }

   public ParticipantMap[] getAllParticipants (ParticipantMappingTransaction trans,String pkgId) throws BaseException
   {
      if (pkgId==null) throw new BaseException("The package Id can't be null");
      List pl=getParticipantsFromPackage(pkgId);
      ParticipantMap[] pms=new ParticipantMap[pl.size()];
      pl.toArray(pms);
      return pms;
   }

   public ParticipantMap[] getAllParticipants (String pkgId,String pDefId) throws BaseException {
      ParticipantMap[] retVal;
      ParticipantMappingTransaction t = null;
      try {
         t = SharkUtilities.createParticipantMappingTransaction();
         retVal = getAllParticipants(t,pkgId,pDefId);
      } catch (RootException e) {
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseMappingTransaction(t);
      }
      return retVal;
   }

   public ParticipantMap[] getAllParticipants (ParticipantMappingTransaction trans,String pkgId,String pDefId) throws BaseException
   {
      if (pkgId==null) throw new BaseException("The package Id can't be null");
      List pl=getParticipantsFromPackageProcess(pkgId,pDefId);
      ParticipantMap[] pms=new ParticipantMap[pl.size()];
      pl.toArray(pms);
      return pms;
   }

   private List getParticipantsFromPackageProcess (String pkgId,String pDefId) throws BaseException {
      List pl=new ArrayList();
      org.enhydra.shark.xpdl.elements.Package pkg=
         SharkEngineManager.getInstance().getXMLInterface().getPackageById(pkgId);
      if (pkg==null) throw new BaseException("Can't find package with Id="+pkgId);
      WorkflowProcess wp=pkg.getWorkflowProcesses().getWorkflowProcess(pDefId);
      if (wp==null) throw new BaseException("Can't find process definition with Id="+pDefId+" in pkg "+pkgId);

      Participants ps=wp.getParticipants();
      Iterator participants=ps.toElements().iterator();
      while (participants.hasNext()) {
         Participant p=(Participant)participants.next();
         ParticipantMap pm=SharkEngineManager
            .getInstance()
            .getObjectFactory()
            .createParticipantMap();
         pm.setPackageId(pkgId);
         pm.setProcessDefinitionId(wp.getId());
         pm.setParticipantId(p.getId());
         pl.add(pm);
      }
      return pl;
   }

   private List getParticipantsFromPackage (String pkgId) throws BaseException {
      List pl=new ArrayList();
      org.enhydra.shark.xpdl.elements.Package pkg=
         SharkEngineManager.getInstance().getXMLInterface().getPackageById(pkgId);
      if (pkg==null) throw new BaseException("Can't find package with Id="+pkgId);
      Participants ps=pkg.getParticipants();
      Iterator participants=ps.toElements().iterator();
      while (participants.hasNext())
      {
         Participant p=(Participant)participants.next();
         ParticipantMap pm=SharkEngineManager
            .getInstance()
            .getObjectFactory()
            .createParticipantMap();
         pm.setPackageId(pkgId);
         pm.setParticipantId(p.getId());
         pl.add(pm);
      }
      Iterator processes=pkg.getWorkflowProcesses().toElements().iterator();
      while (processes.hasNext())
      {
         String pDefId=((WorkflowProcess)processes.next()).getId();
         pl.addAll(getParticipantsFromPackageProcess(pkgId,pDefId));
      }
      return pl;
   }

   public void addParticipantMapping(ParticipantMap pm) throws BaseException {
      ParticipantMappingTransaction t = null;
      try {
         t = SharkUtilities.createParticipantMappingTransaction();
         addParticipantMapping(t, pm);
         SharkUtilities.commitMappingTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackMappingTransaction(t,e);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseMappingTransaction(t);
      }
   }

   public void addParticipantMapping(ParticipantMappingTransaction t, ParticipantMap pm)
      throws BaseException {
      if (pmanager==null) throw new BaseException("The shark is configured to work without internal implementation of this API");
      try
      {
         pmanager.saveParticipantMapping(
            t,
            this.createPersistentParticipantMapFromClientMap( pm ) );
      }
      catch ( Exception e )
      {
         throw new BaseException(e);
      }

   }

   public void removeParticipantMapping(ParticipantMap pm)
      throws BaseException {
      ParticipantMappingTransaction t = null;
      try {
         t = SharkUtilities.createParticipantMappingTransaction();
         removeParticipantMapping(t, pm);
         SharkUtilities.commitMappingTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackMappingTransaction(t,e);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseMappingTransaction(t);
      }
   }

   public void removeParticipantMapping(
      ParticipantMappingTransaction t,
      ParticipantMap pm)
      throws BaseException {
      if (pmanager==null) throw new BaseException("The shark is configured to work without internal implementation of this API");
      try
      {
         pmanager.deleteParticipantMapping(
            t,
            this.createPersistentParticipantMapFromClientMap( pm ) );

         //   pmanager.deleteParticipantMappings(
         //            t,
         //            pm.getUsername());

         //   pmanager.deleteParticipantMappings(
         //      t,
         //      pm.getPackageId(),
         //      pm.getProcessDefinitionId(),
         //      pm.getParticipantId());
      }
      catch ( Exception e )
      {
         throw new BaseException(e);
      }
   }

   public ParticipantMap[] getParticipantMappings(
      String packageId,
      String processDefinitionId,
      String participantId)
      throws BaseException {
      ParticipantMap[] retVal;
      ParticipantMappingTransaction t = null;
      try {
         t = SharkUtilities.createParticipantMappingTransaction();
         retVal = getParticipantMappings(
            t,
            packageId,
            processDefinitionId,
            participantId);
         //SharkUtilities.commitMappingTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackMappingTransaction(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseMappingTransaction(t);
      }
      return retVal;
   }

   public ParticipantMap[] getParticipantMappings(
      ParticipantMappingTransaction t,
      String packageId,
      String processDefinitionId,
      String participantId)
      throws BaseException {
      if (pmanager==null) throw new BaseException("The shark is configured to work without internal implementation of this API");
      ParticipantMap[] pma = null;
      try
      {
         List persistentMapList = pmanager.getParticipantMappings(
            t,
            packageId,
            processDefinitionId,
            participantId );

          List clientMapList = new ArrayList();
          for ( int i = 0; i < persistentMapList.size(); i++ )
          {
            clientMapList.add( createClientMapFromPersistentParticipantMap(
                                                             ( org.enhydra.shark.api.internal.partmappersistence.ParticipantMap )
                              persistentMapList.get( i )
                          ) );
          }
          pma=new ParticipantMap[clientMapList.size()];
          clientMapList.toArray(pma);
      }
      catch ( Exception e )
      {
         throw new BaseException(e);
      }
      return pma;
   }

   public void removeParticipantMappings(
      String packageId,
      String processDefinitionId,
      String participantId)
      throws BaseException {
      ParticipantMappingTransaction t = null;
      try {
         t = SharkUtilities.createParticipantMappingTransaction();
         removeParticipantMappings(
            t,
            packageId,
            processDefinitionId,
            participantId);
         SharkUtilities.commitMappingTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackMappingTransaction(t,e);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseMappingTransaction(t);
      }
   }


   public void removeParticipantMappings(
      ParticipantMappingTransaction t,
      String packageId,
      String processDefinitionId,
      String participantId)
      throws BaseException {
      if (pmanager==null) throw new BaseException("The shark is configured to work without internal implementation of this API");
      try
      {
         pmanager.deleteParticipantMappings(
            t,
            packageId,
            processDefinitionId,
            participantId );
      }
      catch ( Exception e )
      {
         throw new BaseException(e);
      }
   }

   public void removeParticipantMappings(String username)
      throws BaseException {
      ParticipantMappingTransaction t = null;
      try {
         t = SharkUtilities.createParticipantMappingTransaction();
         removeParticipantMappings(
            t,username);
         SharkUtilities.commitMappingTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackMappingTransaction(t,e);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseMappingTransaction(t);
      }
   }

   public void removeParticipantMappings(
      ParticipantMappingTransaction t,
      String username)
      throws BaseException {
      if (pmanager==null) throw new BaseException("The shark is configured to work without internal implementation of this API");
      try
      {
         pmanager.deleteParticipantMappings( t,username );
      }
      catch ( Exception e )
      {
         throw new BaseException(e);
      }
   }

   public ParticipantMap createParticipantMap() {
      return SharkEngineManager
         .getInstance()
         .getObjectFactory()
         .createParticipantMap();
   }

   public ParticipantMap createParticipantMap(ParticipantMappingTransaction t) {
      return SharkEngineManager
         .getInstance()
         .getObjectFactory()
         .createParticipantMap();
   }

   public String[] getAllGroupnames() throws BaseException {
      return SharkEngineManager.getInstance().getObjectFactory().
         createUserGroupAdministration().
         getAllGroupnames();
   }

   public String[] getAllGroupnames(ParticipantMappingTransaction t)
      throws BaseException {
      return SharkEngineManager.getInstance().getObjectFactory().
         createUserGroupAdministration().
         getAllGroupnames();
   }

   public String[] getAllUsers() throws BaseException {
      return SharkEngineManager.getInstance().getObjectFactory().
         createUserGroupAdministration().
         getAllUsers();
   }

   public String[] getAllUsers(ParticipantMappingTransaction t) throws BaseException {
      return SharkEngineManager.getInstance().getObjectFactory().
         createUserGroupAdministration().
         getAllUsers();
   }

   public String[] getAllUsers(String groupName) throws BaseException {
      return SharkEngineManager.getInstance().getObjectFactory().
         createUserGroupAdministration().
         getAllUsers(groupName);
   }

   public String[] getAllUsers(ParticipantMappingTransaction t, String groupName)
      throws BaseException {
      return SharkEngineManager.getInstance().getObjectFactory().
         createUserGroupAdministration().
         getAllUsers(groupName);
   }

   private org.enhydra.shark.api.internal.partmappersistence.ParticipantMap
      createPersistentParticipantMapFromClientMap( ParticipantMap pm ) throws BaseException
   {
      if (pmanager==null) throw new BaseException("The shark is configured to work without internal implementation of this API");
      org.enhydra.shark.api.internal.partmappersistence.ParticipantMap
         pmpersistent = null;
      try {
         pmpersistent = pmanager.createParticipantMap();
         pmpersistent.setUsername( pm.getUsername() );
         pmpersistent.setProcessDefinitionId( pm.getProcessDefinitionId() );
         pmpersistent.setParticipantId( pm.getParticipantId() );
         pmpersistent.setPackageId( pm.getPackageId() );
         pmpersistent.setIsGroupUser( pm.getIsGroupUser() );
      }catch(Exception e) {
         throw new BaseException(e);
      }
      return pmpersistent;
   }

   private ParticipantMap
      createClientMapFromPersistentParticipantMap( org.enhydra.shark.api.internal.partmappersistence.ParticipantMap pm )
   {
      if (pm==null) return null;
      ParticipantMap pmClient = createParticipantMap();
      pmClient.setUsername( pm.getUsername() );
      pmClient.setProcessDefinitionId( pm.getProcessDefinitionId() );
      pmClient.setParticipantId( pm.getParticipantId());
      pmClient.setPackageId( pm.getPackageId() );
      pmClient.setIsGroupUser( pm.getIsGroupUser() );
      return pmClient;
   }

}
