
package org.enhydra.shark;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.enhydra.shark.api.ApplicationMappingTransaction;
import org.enhydra.shark.api.RepositoryTransaction;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfservice.ApplicationMap;
import org.enhydra.shark.api.client.wfservice.ApplicationMappingAdministration;
import org.enhydra.shark.api.internal.appmappersistence.ApplicationMappingManager;
import org.enhydra.shark.api.internal.repositorypersistence.RepositoryPersistenceManager;
import org.enhydra.shark.api.internal.toolagent.ToolAgentFactory;
import org.enhydra.shark.xpdl.elements.Application;
import org.enhydra.shark.xpdl.elements.Applications;
import org.enhydra.shark.xpdl.elements.WorkflowProcess;
import org.enhydra.shark.xpdl.elements.WorkflowProcesses;

/**
 * Implementation of ApplicationMappings interface.
 * Provide methods to manage mappings among XPDL application definitions and
 * shark's tool agents.
 *
 * @author Zoran Milakovic
 *
 */
public class ApplicationMappingsAdministrationImpl implements ApplicationMappingAdministration {


   private ApplicationMappingManager pmanager;
   private String userId="Unknown";

   protected ApplicationMappingsAdministrationImpl ()
   {
      pmanager=SharkEngineManager.getInstance().getApplicationMapPersistenceManager();
   }

   public void connect (String userId) {
      this.userId=userId;
   }

   public ApplicationMap[] getAllApplications () throws BaseException {
      ApplicationMap[] retVal;
      ApplicationMappingTransaction t = null;
      try {
         t = SharkUtilities.createApplicationMappingTransaction();
         retVal = getAllApplications(t);
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

   public ApplicationMap[] getAllApplications (ApplicationMappingTransaction t) throws BaseException {
      List al=new ArrayList();

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
         al.addAll(getApplicationsFromPackage(pkgId));
      }
      ApplicationMap[] ams=new ApplicationMap[al.size()];
      al.toArray(ams);
      return ams;
   }

   public ApplicationMap[] getAllApplications (String pkgId) throws BaseException {
      ApplicationMap[] retVal;
      ApplicationMappingTransaction t = null;
      try {
         t = SharkUtilities.createApplicationMappingTransaction();
         retVal = getAllApplications(t,pkgId);
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

   public ApplicationMap[] getAllApplications (ApplicationMappingTransaction t,String pkgId) throws BaseException {
      if (pkgId==null) throw new BaseException("The package Id can't be null");
      List al=getApplicationsFromPackage(pkgId);
      ApplicationMap[] ams=new ApplicationMap[al.size()];
      al.toArray(ams);
      return ams;
   }

   public ApplicationMap[] getAllApplications (String pkgId,String pDefId) throws BaseException {
      ApplicationMap[] retVal;
      ApplicationMappingTransaction t = null;
      try {
         t = SharkUtilities.createApplicationMappingTransaction();
         retVal = getAllApplications(t,pkgId,pDefId);
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

   public ApplicationMap[] getAllApplications (ApplicationMappingTransaction t,String pkgId,String pDefId) throws BaseException {
      if (pkgId==null) throw new BaseException("The package Id can't be null");
      List al=getApplicationsFromPackageProcess(pkgId,pDefId);
      ApplicationMap[] ams=new ApplicationMap[al.size()];
      al.toArray(ams);
      return ams;
   }

   private  List getApplicationsFromPackage (String pkgId) throws BaseException {
      List al=new ArrayList();
      org.enhydra.shark.xpdl.elements.Package pkg=
         SharkEngineManager.getInstance().getXMLInterface().getPackageById(pkgId);
      if (pkg==null) throw new BaseException("Can't find package with Id="+pkgId);
      Applications aps=pkg.getApplications();
      Iterator applications=aps.toElements().iterator();
      while (applications.hasNext())
      {
         Application a=(Application)applications.next();
         ApplicationMap am=SharkEngineManager
            .getInstance()
            .getObjectFactory()
            .createApplicationMap();
         am.setPackageId(pkgId);
         am.setApplicationDefinitionId(a.getId());
         al.add(am);
      }
      Iterator processes=((WorkflowProcesses)pkg.getWorkflowProcesses()).
         toElements().iterator();
      while (processes.hasNext())
      {
         String pDefId=((WorkflowProcess)processes.next()).getId();
         al.addAll(getApplicationsFromPackageProcess(pkgId,pDefId));
      }

      return al;
   }

   private  List getApplicationsFromPackageProcess (String pkgId,String pDefId) throws BaseException {
      List al=new ArrayList();
      org.enhydra.shark.xpdl.elements.Package pkg=
         SharkEngineManager.getInstance().getXMLInterface().getPackageById(pkgId);
      if (pkg==null) throw new BaseException("Can't find package with Id="+pkgId);
      WorkflowProcess wp=pkg.getWorkflowProcesses().getWorkflowProcess(pDefId);
      if (wp==null) throw new BaseException("Can't find process definition with Id="+pDefId+" in pkg "+pkgId);
      Applications aps=wp.getApplications();
      Iterator applications=aps.toElements().iterator();
      while (applications.hasNext())
      {
         Application a=(Application)applications.next();
         ApplicationMap am=SharkEngineManager
            .getInstance()
            .getObjectFactory()
            .createApplicationMap();
         am.setPackageId(pkgId);
         am.setProcessDefinitionId(wp.getId());
         am.setApplicationDefinitionId(a.getId());
         al.add(am);
      }
      return al;
   }

   public String[] getDefinedToolAgents () throws BaseException {
      String[] retVal;
      ApplicationMappingTransaction t = null;
      try {
         t = SharkUtilities.createApplicationMappingTransaction();
         retVal = getDefinedToolAgents(t);
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

   public String[] getDefinedToolAgents (ApplicationMappingTransaction t) throws BaseException {
      ToolAgentFactory taf=SharkEngineManager.getInstance().getToolAgentFactory();
      if (taf==null) throw new BaseException("Working without tool agents !");

      SharkTransaction st=null;
      try {
         st = SharkUtilities.createTransaction();
         List l=taf.getDefinedToolAgents(st);
         String[] ata=new String[l.size()];
         l.toArray(ata);
         return ata;
      } catch (Exception e) {
         throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(st);
      }
   }

   public Map getToolAgentsInfo () throws BaseException {
      Map retVal;
      ApplicationMappingTransaction t = null;
      try {
         t = SharkUtilities.createApplicationMappingTransaction();
         retVal = getToolAgentsInfo(t);
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

   public Map getToolAgentsInfo (ApplicationMappingTransaction t) throws BaseException {
      ToolAgentFactory taf=SharkEngineManager.getInstance().getToolAgentFactory();
      if (taf==null) throw new BaseException("Working without tool agents !");

      SharkTransaction st=null;
      try {
         st = SharkUtilities.createTransaction();
         List l=taf.getDefinedToolAgents(st);
         Map m=new HashMap();
         Iterator it=l.iterator();
         while (it.hasNext()) {
            String tan=(String)it.next();
            m.put(tan,taf.createToolAgent(st,tan).getInfo(st));
         }
         return m;
      } catch (Exception e) {
         throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(st);
      }
   }

   public String getToolAgentInfo (String toolAgentFullClassName) throws BaseException {
      String retVal;
      ApplicationMappingTransaction t = null;
      try {
         t = SharkUtilities.createApplicationMappingTransaction();
         retVal = getToolAgentInfo(t,toolAgentFullClassName);
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

   public String getToolAgentInfo (ApplicationMappingTransaction t,String toolAgentFullClassName) throws BaseException {
      ToolAgentFactory taf=SharkEngineManager.getInstance().getToolAgentFactory();
      if (taf==null) throw new BaseException("Working without tool agents !");

      SharkTransaction st=null;
      try {
         st = SharkUtilities.createTransaction();
         return taf.createToolAgent(st,toolAgentFullClassName).getInfo(st);
      } catch (Exception e) {
         throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(st);
      }
   }

   public void addApplicationMapping(  ApplicationMap am ) throws BaseException {
      ApplicationMappingTransaction t = null;
      try {
         t = SharkUtilities.createApplicationMappingTransaction();
         addApplicationMapping(t, am);
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

   public void addApplicationMapping( ApplicationMappingTransaction trans, ApplicationMap am ) throws BaseException {
      if (pmanager==null) throw new BaseException("The shark is configured to work without internal implementation of this API");
      try {
         pmanager.saveApplicationMapping(
            trans,
            this.createPersistentApplicationMapFromClientMap( am ) );
      } catch ( Exception e ) {
         throw new BaseException(e);
      }
   }

   public ApplicationMap getApplicationMapping(
      String pkgId,
      String pDefId,
      String appDefId) throws BaseException {
      ApplicationMap retVal;
      ApplicationMappingTransaction t = null;
      try {
         t = SharkUtilities.createApplicationMappingTransaction();
         retVal = getApplicationMapping(
            t,
            pkgId,
            pDefId,
            appDefId
         );
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

   public ApplicationMap getApplicationMapping(
      ApplicationMappingTransaction trans,
      String pkgId,
      String pDefId,
      String appDefId ) throws BaseException
   {
      if (pmanager==null) throw new BaseException("The shark is configured to work without internal implementation of this API");
      try
      {
         ApplicationMap am =
            createClientMapFromPersistentApplicationMap(
            pmanager.getApplicationMap( trans, pkgId, pDefId, appDefId ) );
         return am;
      }
      catch ( Exception e )
      {
         throw new BaseException(e);
      }
   }

   public void removeApplicationMapping(
      String packageId,
      String processDefinitionId,
      String applicationId ) throws BaseException
   {
      ApplicationMap retVal;
      ApplicationMappingTransaction t = null;
      try {
         t = SharkUtilities.createApplicationMappingTransaction();
         removeApplicationMapping(
            t,
            packageId,
            processDefinitionId,
            applicationId);
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

   public void removeApplicationMapping(
      ApplicationMappingTransaction trans,
      String packageId,
      String processDefinitionId,
      String applicationId ) throws BaseException
   {
      if (pmanager==null) throw new BaseException("The shark is configured to work without internal implementation of this API");
      try
      {
         pmanager.deleteApplicationMapping(
            trans,
            packageId,
            processDefinitionId,
            applicationId );
      }
      catch ( Exception e )
      {
         throw new BaseException(e);
      }
   }

   public ApplicationMap[] getApplicationMappings() throws BaseException {
      ApplicationMap[] retVal;
      ApplicationMappingTransaction t = null;
      try {
         t = SharkUtilities.createApplicationMappingTransaction();
         retVal = getApplicationMappings(t);
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
      return retVal;
   }

   public ApplicationMap[] getApplicationMappings(ApplicationMappingTransaction trans) throws BaseException
   {
      if (pmanager==null) throw new BaseException("The shark is configured to work without internal implementation of this API");
      try
      {
         List clientMapList = new ArrayList();
         List persistentMapList = pmanager.getAllApplicationMappings(trans);
         for ( int i = 0; i < persistentMapList.size(); i++ )
         {
            clientMapList.add( createClientMapFromPersistentApplicationMap(
                                                                             ( org.enhydra.shark.api.internal.appmappersistence.ApplicationMap )
                                    persistentMapList.get( i )
                              ) );
         }
         ApplicationMap[] ama=new ApplicationMap[clientMapList.size()];
         clientMapList.toArray(ama);
         return ama;
      }
      catch ( Exception e )
      {
         throw new BaseException(e);
      }
   }

   public ApplicationMap createApplicationMap()
   {
      return SharkEngineManager
         .getInstance()
         .getObjectFactory()
         .createApplicationMap();
   }

   public ApplicationMap createApplicationMap(ApplicationMappingTransaction t) {
      return SharkEngineManager
         .getInstance()
         .getObjectFactory()
         .createApplicationMap();
   }

   private org.enhydra.shark.api.internal.appmappersistence.ApplicationMap
      createPersistentApplicationMapFromClientMap( ApplicationMap am ) throws BaseException
   {
      if (pmanager==null) throw new BaseException("The shark is configured to work without internal implementation of this API");
      org.enhydra.shark.api.internal.appmappersistence.ApplicationMap ampersistent = null;
      try {
         ampersistent = pmanager.createApplicationMap();
         ampersistent.setUsername( am.getUsername() );
         ampersistent.setPassword( am.getPassword() );
         ampersistent.setApplicationDefinitionId( am.getApplicationDefinitionId() );
         ampersistent.setApplicationMode( am.getApplicationMode() );
         ampersistent.setApplicationName( am.getApplicationName() );
         ampersistent.setProcessDefinitionId(am.getProcessDefinitionId());
         ampersistent.setToolAgentClassName( am.getToolAgentClassName() );
         ampersistent.setPackageId( am.getPackageId() );
      }catch(Exception e) {
         throw new BaseException(e);
      }
      return ampersistent;
   }


   private ApplicationMap createClientMapFromPersistentApplicationMap(
      org.enhydra.shark.api.internal.appmappersistence.ApplicationMap am )
   {
      if (am==null) return null;
      ApplicationMap
         ampClient = createApplicationMap();
      ampClient.setUsername( am.getUsername() );
      ampClient.setPassword( am.getPassword() );
      ampClient.setApplicationDefinitionId( am.getApplicationDefinitionId() );
      ampClient.setApplicationMode( am.getApplicationMode() );
      ampClient.setApplicationName( am.getApplicationName() );
      ampClient.setProcessDefinitionId( am.getProcessDefinitionId() );
      ampClient.setToolAgentClassName( am.getToolAgentClassName() );
      ampClient.setPackageId( am.getPackageId() );
      return ampClient;
   }

}
