package org.enhydra.shark.repositorypersistence;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;

import com.lutris.appserver.server.sql.DatabaseManagerException;
import com.lutris.appserver.server.sql.LogicalDatabase;
import com.lutris.appserver.server.sql.DBTransaction;
import org.enhydra.dods.DODS;
import org.enhydra.shark.api.RepositoryTransaction;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.TransactionException;
import org.enhydra.shark.api.internal.repositorypersistence.RepositoryException;
import org.enhydra.shark.api.internal.repositorypersistence.RepositoryPersistenceManager;
import org.enhydra.shark.api.internal.working.CallbackUtilities;
import org.enhydra.shark.utilities.MiscUtilities;
import org.enhydra.shark.utilities.dods.DODSUtilities;
import org.enhydra.shark.repositorypersistence.data.*;

/**
 * Database implementation of Repository persistence interface.
 *
 * @author Sasa Bojanic
 *
 */
public class DODSRepositoryPersistenceManager implements RepositoryPersistenceManager {

   static boolean _debug_ = false;
   private static final String DBG_PARAM_NAME = "DODSRepositoryPersistenceManager.debug";
   private static final String INITIAL_VERSION = "1";

   private static final String LDB_PARAM_NAME = "DODSRepositoryPersistenceManager.DatabaseName";
   private CallbackUtilities cus;
   private LogicalDatabase db;

   /**
    * Public constructor ().
    */
   public DODSRepositoryPersistenceManager () {
      db = null;
   }

   /**
    * Method configure is called at Shark start up, to configure
    * DODSRepositoryPersistenceManager.
    *
    * @param cus an instance of CallbackUtilities used to get
    *            properties for configuring repository manager in Shark.
    *
    * @exception RootException thrown if configuring doesn't succeed.
    */
   public void configure (CallbackUtilities cus) throws RootException {
      if (null == cus)
         throw new RootException("Cannot configure without call back impl.");
      this.cus = cus;
      _debug_ = Boolean
         .valueOf(cus.getProperty(DBG_PARAM_NAME, "false"))
         .booleanValue();
      DODSUtilities.init(cus.getProperties());
      String dbName = cus
         .getProperty(LDB_PARAM_NAME, DODS.getDatabaseManager().getDefaultDB());
      try {
         db = DODS.getDatabaseManager().findLogicalDatabase(dbName);
      } catch (DatabaseManagerException e) {
         throw new RootException("Couldn't find logical database.", e);
      }
      cus.debug("DODSRepositoryPersistenceManager configured");
   }


   public void uploadXPDL (RepositoryTransaction t,
                           String xpdlId,
                           byte[] xpdl,
                           byte[] serializedPkg,
                           long xpdlClassVer) throws RepositoryException {
      cus.info("DODSRepositoryPersistenceManager -> Storing XPDL "+xpdlId+", BLOB1 size="+xpdl.length+", BLOB2 size="+serializedPkg.length);
      try {
         DBTransaction dbt=((DODSRepositoryTransaction)t).getDODSTransaction();
         String xpdlVer=updateNextVersion(dbt,xpdlId);
         XPDLDO newXPDLDO=XPDLDO.createVirgin(dbt);
         newXPDLDO.setXPDLId(xpdlId);
         newXPDLDO.setXPDLVersion(xpdlVer);
         newXPDLDO.setXPDLClassVersion(xpdlClassVer);
         newXPDLDO.setXPDLUploadTime(new Timestamp(System.currentTimeMillis()));
         XPDLDataDO cont=XPDLDataDO.createVirgin(dbt);
         cont.setXPDLContent(xpdl);
         cont.setXPDLClassContent(serializedPkg);
         cont.setXPDL(newXPDLDO);
         cont.setCNT(DODSUtilities.getNext("_xpdldata_"));
         newXPDLDO.save(dbt);
         cont.save(dbt);                  
         dbt.write();
      } catch (Throwable thr) {
         throw new RepositoryException("DODSRepositoryPersistenceManager -> The upload of xpdl "+xpdlId+" failed",thr);
      }

   }

   public void updateXPDL (RepositoryTransaction t,
                           String xpdlId,
                           String xpdlVersion,
                           byte[] xpdl,
                           byte[] serializedPkg,
                           long xpdlClassVer) throws RepositoryException {
      try {
         DBTransaction dbt=((DODSRepositoryTransaction)t).getDODSTransaction();
         XPDLDO DO=getXPDLDO(dbt,xpdlId,xpdlVersion); // this will throw an exception if xpdl doesn't exist
         DO.setXPDLClassVersion(xpdlClassVer);
         XPDLDataDO cont=DO.getXPDLDataDO();
         cont.setXPDLContent(xpdl);
         cont.setXPDLClassContent(serializedPkg);
         cont.save(dbt);
         dbt.write();
      } catch (Exception ex) {
         cus.error("DODSRepositoryPersistenceManager -> The update of the xpdl with Id="+xpdlId+", and version="+xpdlVersion+" failed");
         throw new RepositoryException(ex);
      }
   }

   public void deleteXPDL (RepositoryTransaction t,
                           String xpdlId,
                           String xpdlVersion) throws RepositoryException {
      try {
         DBTransaction dbt=((DODSRepositoryTransaction)t).getDODSTransaction();
         XPDLDO DO=getXPDLDO(dbt,xpdlId,xpdlVersion); // this will throw an exception if xpdl doesn't exist
         DO.delete(dbt);
         dbt.write();
      } catch (Exception ex) {
         throw new RepositoryException("XPDL ["+xpdlId+","+xpdlVersion+"] is not deleted from repository",ex);
      }
   }

   public void moveToHistory (RepositoryTransaction t,
                              String xpdlId,
                              String xpdlVersion) throws RepositoryException {
      try {
         DBTransaction dbt=((DODSRepositoryTransaction)t).getDODSTransaction();
         XPDLDO DO=getXPDLDO(dbt,xpdlId,xpdlVersion); // this will throw an exception if xpdl doesn't exist
         XPDLHistoryDO xpdlHist=XPDLHistoryDO.createVirgin(dbt);
         xpdlHist.setXPDLId(DO.getXPDLId());
         xpdlHist.setXPDLVersion(DO.getXPDLVersion());
         xpdlHist.setXPDLUploadTime(DO.getXPDLUploadTime());
         xpdlHist.setXPDLHistoryUploadTime(new Timestamp(System.currentTimeMillis()));
         XPDLHistoryDataDO cont=XPDLHistoryDataDO.createVirgin(dbt);
         cont.setXPDLContent(DO.getXPDLDataDO().getXPDLContent());
         cont.setXPDLHistory(xpdlHist);
         cont.setCNT(DODSUtilities.getNext("_xpdlhistorydata_"));
         DO.delete(dbt);
         xpdlHist.save(dbt);
         cont.save(dbt);
         dbt.write();
      } catch (Exception ex) {
         throw new RepositoryException("XPDL ["+xpdlId+","+xpdlVersion+"] is not moved to history",ex);
      }
   }

   public void deleteFromHistory (RepositoryTransaction t,
                                  String xpdlId,
                                  String xpdlVersion) throws RepositoryException {
      try {
         DBTransaction dbt=((DODSRepositoryTransaction)t).getDODSTransaction();
         XPDLHistoryDO DO=getXPDLHistoryDO(dbt,xpdlId,xpdlVersion); // this will throw an exception if xpdl doesn't exist
         DO.delete(dbt);
         dbt.write();
      } catch (Exception ex) {
         throw new RepositoryException("XPDL ["+xpdlId+","+xpdlVersion+"] is not deleted from history",ex);
      }
   }

   public void clearRepository (RepositoryTransaction t) throws RepositoryException {
      try {
         DBTransaction dbt=((DODSRepositoryTransaction)t).getDODSTransaction();
         XPDLDO[] DOs=getAllXPDLs(dbt);
         if (DOs!=null) {
            for (int i=0; i<DOs.length; i++) {
               DOs[i].delete();
            }
         }
         dbt.write();
      } catch (Exception ex) {
         throw new RepositoryException("Some xpdl is not deleted from repository while clearing it",ex);
      }
   }

   public String getCurrentVersion (RepositoryTransaction t,
                                    String xpdlId) throws RepositoryException {
      try {
         DBTransaction dbt=((DODSRepositoryTransaction)t).getDODSTransaction();
         return getLastVersionXPDLDO(dbt,xpdlId).getXPDLVersion();
      } catch (Exception ex) {
         throw new RepositoryException("No xpdl with Id="+xpdlId,ex);
      }
   }

   public String getNextVersion (RepositoryTransaction t,
                                 String xpdlId) throws RepositoryException {
      try {
         DBTransaction dbt=((DODSRepositoryTransaction)t).getDODSTransaction();
         NextXPDLVersionQuery query=new NextXPDLVersionQuery(dbt);
         query.setQueryXPDLId(xpdlId);
         NextXPDLVersionDO nvDO=query.getNextDO();
         if (nvDO==null) {
            return INITIAL_VERSION;
         } else {
            return nvDO.getNextVersion();
         }
      } catch (Exception ex) {
         throw new RepositoryException(ex);
      }
   }

   public long getSerializedXPDLObjectVersion (RepositoryTransaction t,String xpdlId,String xpdlVersion) throws RepositoryException {
      try {
         DBTransaction dbt=((DODSRepositoryTransaction)t).getDODSTransaction();
         return getXPDLDO(dbt,xpdlId,xpdlVersion).getXPDLClassVersion();
      } catch (Exception ex) {
         throw new RepositoryException("No xpdl with Id="+xpdlId,ex);
      }
   }

   private String updateNextVersion (DBTransaction dbt,
                                     String xpdlId) throws Exception {
      NextXPDLVersionQuery query=new NextXPDLVersionQuery(dbt);
      query.setQueryXPDLId(xpdlId);
      NextXPDLVersionDO nvDO=query.getNextDO();
      String curVersion=INITIAL_VERSION;
      if (nvDO==null) {
         nvDO=NextXPDLVersionDO.createVirgin(dbt);
         nvDO.setXPDLId(xpdlId);
         nvDO.setNextVersion(INITIAL_VERSION);
      } else {
         curVersion=nvDO.getNextVersion();
      }
      int nver=Integer.parseInt(nvDO.getNextVersion())+1;
      String nextVersion=String.valueOf(nver);
      nvDO.setNextVersion(nextVersion);
      nvDO.save(dbt);
      dbt.write();
      return curVersion;
   }

   public byte[] getXPDL (RepositoryTransaction t,
                          String xpdlId) throws RepositoryException {
      try {
         DBTransaction dbt=((DODSRepositoryTransaction)t).getDODSTransaction();
         return getLastVersionXPDLDO(dbt,xpdlId).getXPDLDataDO().getXPDLContent();
      } catch (Exception ex) {
         throw new RepositoryException("No xpdl with Id="+xpdlId+" in repository",ex);
      }
   }

   public byte[] getSerializedXPDLObject (RepositoryTransaction t,String xpdlId) throws RepositoryException {
      try {
         DBTransaction dbt=((DODSRepositoryTransaction)t).getDODSTransaction();
         return getLastVersionXPDLDO(dbt,xpdlId).getXPDLDataDO().getXPDLClassContent();
      } catch (Exception ex) {
         throw new RepositoryException("No xpdl with Id="+xpdlId+" in repository",ex);
      }
   }

   public byte[] getXPDL (RepositoryTransaction t,
                          String xpdlId,
                          String xpdlVersion) throws RepositoryException {
      try {
         DBTransaction dbt=((DODSRepositoryTransaction)t).getDODSTransaction();
         return getXPDLDO(dbt,xpdlId,xpdlVersion).getXPDLDataDO().getXPDLContent();
      } catch (Exception ex) {
         throw new RepositoryException("No xpdl ["+xpdlId+","+xpdlVersion+"] in repository",ex);
      }
   }

   public byte[] getSerializedXPDLObject (RepositoryTransaction t,String xpdlId,String xpdlVersion) throws RepositoryException {
      try {
         DBTransaction dbt=((DODSRepositoryTransaction)t).getDODSTransaction();
         return getXPDLDO(dbt,xpdlId,xpdlVersion).getXPDLDataDO().getXPDLClassContent();
      } catch (Exception ex) {
         throw new RepositoryException("No xpdl ["+xpdlId+","+xpdlVersion+"] in repository",ex);
      }
   }
   
   public List getXPDLVersions (RepositoryTransaction t,
                                String xpdlId) throws RepositoryException {
      try {
         List xpdlVersions=new ArrayList();
         DBTransaction dbt=((DODSRepositoryTransaction)t).getDODSTransaction();
         XPDLDO[] DOs=getAllXPDLs(dbt,xpdlId);

         for (int i=0; i<DOs.length; i++) {
            xpdlVersions.add(DOs[i].getXPDLVersion());
         }
         return xpdlVersions;
      } catch (Exception ex) {
         throw new RepositoryException("No xpdl with Id="+xpdlId+" in repository",ex);
      }
   }

   public boolean doesXPDLExist (RepositoryTransaction t,
                                 String xpdlId) throws RepositoryException {
      try {
         DBTransaction dbt=((DODSRepositoryTransaction)t).getDODSTransaction();
         XPDLDO DO=getLastVersionXPDLDO(dbt,xpdlId);
         return getLastVersionXPDLDO(dbt,xpdlId)!=null;
      } catch (Exception ex) {
         return false;
      }
   }

   public boolean doesXPDLExist (RepositoryTransaction t,
                                 String xpdlId,
                                 String xpdlVersion) throws RepositoryException {
      try {
         DBTransaction dbt=((DODSRepositoryTransaction)t).getDODSTransaction();
         getXPDLDO(dbt,xpdlId,xpdlVersion);
         return true;
      } catch (Exception ex) {
         return false;
      }
   }

   public List getExistingXPDLIds (RepositoryTransaction t) throws RepositoryException {
      try {
         DBTransaction dbt=((DODSRepositoryTransaction)t).getDODSTransaction();
         XPDLDO[] DOs=getAllXPDLs(dbt);
         Set ids=new HashSet();
         if (DOs!=null) {
            for (int i=0; i<DOs.length; i++) {
               ids.add(DOs[i].getXPDLId());
            }
         }
         return new ArrayList(ids);
      } catch (Exception ex) {
         throw new RepositoryException(ex);
      }
   }

   // TODO: see about references holding version information
   public void addXPDLReference (RepositoryTransaction t,
                                 String referredXPDLId,
                                 String referringXPDLId,
                                 String referringXPDLVersion,
                                 int referredXPDLNumber) throws RepositoryException {
      try {
         DBTransaction dbt=((DODSRepositoryTransaction)t).getDODSTransaction();
         if (getXPDLReference(dbt,referredXPDLId,referringXPDLId,referringXPDLVersion)==null) {
            XPDLReferenceDO ref=XPDLReferenceDO.createVirgin(dbt);
            ref.setReferredXPDLId(referredXPDLId);
            ref.setReferringXPDL(getXPDLDO(dbt,referringXPDLId,referringXPDLVersion));
            ref.setReferredXPDLNumber(referredXPDLNumber);
            ref.save(dbt);
         }
         dbt.write();
      } catch (Exception ex) {
         throw new RepositoryException(ex);
      }
   }

   public List getReferringXPDLIds (RepositoryTransaction t,
                                    String referredXPDLId) throws RepositoryException {
      try {
         DBTransaction dbt=((DODSRepositoryTransaction)t).getDODSTransaction();
         XPDLReferenceQuery query=new XPDLReferenceQuery(dbt);
         query.setQueryReferredXPDLId(referredXPDLId);
         XPDLReferenceDO[] refs=query.getDOArray();
         Set referrers=new HashSet();
         if (refs!=null) {
            for (int i=0; i<refs.length; i++) {
               referrers.add(refs[i].getReferringXPDL().getXPDLId());
            }
         }
         return new ArrayList(referrers);

      } catch (Exception ex) {
         throw new RepositoryException(ex);
      }
   }

   public List getReferringXPDLVersions (RepositoryTransaction t,
                                         String referredXPDLId,
                                         String refferingXPDLId) throws RepositoryException {
      try {
         DBTransaction dbt=((DODSRepositoryTransaction)t).getDODSTransaction();
         XPDLReferenceQuery query=new XPDLReferenceQuery(dbt);
         query.setQueryReferredXPDLId(referredXPDLId);
         XPDLReferenceDO[] refs=query.getDOArray();
         List referrers=new ArrayList();
         if (refs!=null) {
            for (int i=0; i<refs.length; i++) {
               if (refs[i].getReferringXPDL().getXPDLId().equals(refferingXPDLId)) {
                  referrers.add(refs[i].getReferringXPDL().getXPDLVersion());
               }
            }
         }
         return referrers;

      } catch (Exception ex) {
         throw new RepositoryException(ex);
      }
   }


   public List getReferredXPDLIds (RepositoryTransaction t,
                                   String refferingXPDLId,
                                   String refferingXPDLVersion) throws RepositoryException {
      try {
         List ret=new ArrayList();
         DBTransaction dbt=((DODSRepositoryTransaction)t).getDODSTransaction();
         XPDLDO DO=getXPDLDO(dbt,refferingXPDLId,refferingXPDLVersion);
         XPDLReferenceQuery query=new XPDLReferenceQuery(dbt);
         query.setQueryReferringXPDL(DO);
         XPDLReferenceDO[] refs=query.getDOArray();
         if (refs!=null) {
            Map temp=new HashMap();
            for (int i=0; i<refs.length; i++) {
               temp.put(new Integer(refs[i].getReferredXPDLNumber()),refs[i].getReferredXPDLId());
            }
            List tmp=new ArrayList(temp.keySet());
            // sort by number
            Collections.sort(tmp);
            for (int i=0; i<tmp.size(); i++) {
               ret.add(temp.get(tmp.get(i)));
            }            
         }
         return ret;

      } catch (Exception ex) {
         throw new RepositoryException(ex);
      }
   }

   private XPDLReferenceDO getXPDLReference (DBTransaction dbt,
                                             String referredId,
                                             String referringId,
                                             String referringVersion) throws Exception {
      XPDLReferenceQuery query=new XPDLReferenceQuery(dbt);
      query.setQueryReferredXPDLId(referredId);
      query.setQueryReferringXPDL(getXPDLDO(dbt,referringId,referringVersion));
      query.requireUniqueInstance();
      XPDLReferenceDO ref=query.getNextDO();
      return ref;
   }

   public RepositoryTransaction createTransaction() throws TransactionException {
      try {
         return new DODSRepositoryTransaction(DODS.getDatabaseManager().createTransaction());
      } catch (Exception ex) {
         throw new TransactionException(ex);
      }
   }

   private XPDLDO getXPDLDO (DBTransaction dbt,String xpdlId,String xpdlVersion) throws Exception {
      XPDLQuery query=new XPDLQuery(dbt);
      //set query
      query.setQueryXPDLId(xpdlId);
      query.setQueryXPDLVersion(xpdlVersion);
      query.requireUniqueInstance();

      XPDLDO DO=query.getNextDO();
      if (DO==null) {
         throw new Exception("There is no xpdl with Id="+xpdlId+", and version "+xpdlVersion+" in the repository");
      }
      return DO;
   }

   private XPDLDO[] getAllXPDLs (DBTransaction dbt,String xpdlId) throws Exception {
      XPDLQuery query=new XPDLQuery(dbt);
      //set query
      query.setQueryXPDLId(xpdlId);
      return query.getDOArray();
   }

   private XPDLDO[] getAllXPDLs (DBTransaction dbt) throws Exception {
      XPDLQuery query=new XPDLQuery(dbt);
      return query.getDOArray();
   }

   private XPDLDO getLastVersionXPDLDO (DBTransaction dbt,String xpdlId) throws Exception {
      XPDLDO[] xpdls=getAllXPDLs(dbt,xpdlId);
      if (xpdls==null || xpdls.length==0) {
         return null;
      }

      XPDLDO lastVersionDO=null;
      int maxVer=-1;
      for (int i=0; i<xpdls.length; i++) {
         String xpdlVer=xpdls[i].getXPDLVersion();
         int ver=Integer.parseInt(xpdlVer);
         if (ver>maxVer) {
            maxVer=ver;
            lastVersionDO=xpdls[i];
         }
      }

      if (lastVersionDO==null) throw new Exception ("Something is wrong in XPDL repository - can't determine XPDL version");

      return lastVersionDO;
   }

   private XPDLHistoryDO getXPDLHistoryDO (DBTransaction dbt,String xpdlId,String xpdlVersion) throws Exception {
      XPDLHistoryQuery query=new XPDLHistoryQuery(dbt);
      //set query
      query.setQueryXPDLId(xpdlId);
      query.setQueryXPDLVersion(xpdlVersion);
      query.requireUniqueInstance();

      XPDLHistoryDO DO=query.getNextDO();
      if (DO==null) {
         throw new Exception("There is no xpdl with Id="+xpdlId+", and version "+xpdlVersion+" in the history repository");
      }
      return DO;
   }

}

