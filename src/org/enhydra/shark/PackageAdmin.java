package org.enhydra.shark;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.enhydra.shark.api.ApplicationMappingTransaction;
import org.enhydra.shark.api.ParticipantMappingTransaction;
import org.enhydra.shark.api.RepositoryTransaction;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfservice.ExternalPackageInvalid;
import org.enhydra.shark.api.client.wfservice.PackageAdministration;
import org.enhydra.shark.api.client.wfservice.PackageHasActiveProcesses;
import org.enhydra.shark.api.client.wfservice.PackageInUse;
import org.enhydra.shark.api.client.wfservice.PackageInvalid;
import org.enhydra.shark.api.client.wfservice.PackageUpdateNotAllowed;
import org.enhydra.shark.api.common.SharkConstants;
import org.enhydra.shark.api.internal.appmappersistence.ApplicationMappingManager;
import org.enhydra.shark.api.internal.partmappersistence.ParticipantMappingManager;
import org.enhydra.shark.api.internal.repositorypersistence.RepositoryPersistenceManager;
import org.enhydra.shark.api.internal.working.CallbackUtilities;
import org.enhydra.shark.api.internal.working.WfProcessMgrInternal;
import org.enhydra.shark.xpdl.Version;
import org.enhydra.shark.xpdl.XMLInterface;
import org.enhydra.shark.xpdl.XMLInterfaceForJDK13;
import org.enhydra.shark.xpdl.XMLUtil;
import org.enhydra.shark.xpdl.elements.Application;
import org.enhydra.shark.xpdl.elements.Applications;
import org.enhydra.shark.xpdl.elements.ExternalPackage;
import org.enhydra.shark.xpdl.elements.Package;
import org.enhydra.shark.xpdl.elements.Participant;
import org.enhydra.shark.xpdl.elements.Participants;
import org.enhydra.shark.xpdl.elements.WorkflowProcess;
import org.enhydra.shark.xpdl.elements.WorkflowProcesses;

/**
 * @author Nenad Stefanovic, Sasa Bojanic
 *
 */
public class PackageAdmin implements PackageAdministration {

   private XMLInterface xmlInterface=SharkEngineManager.getInstance().getXMLInterface();
   private CallbackUtilities cus;
   private RepositoryPersistenceManager repMgr;
   private String userId="Unknown";

   protected PackageAdmin () {
      this.cus=SharkEngineManager.getInstance().getCallbackUtilities();
      this.repMgr=SharkEngineManager.getInstance().getRepositoryPersistenceManager();
   }

   public void connect (String userId) {
      this.userId=userId;
   }

   public String[] getOpenedPackageIds () throws BaseException {
      String[] ret = null;
      RepositoryTransaction t = null;
      try {
         t = SharkUtilities.createRepositoryTransaction();
         ret = getOpenedPackageIds(t);
         //SharkUtilities.commitRepositoryTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackRepositoryTransaction(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseRepositoryTransaction(t);
      }
      return ret;
   }

   public String[] getOpenedPackageIds (RepositoryTransaction t) throws BaseException {
      try {
         List pkgIds=repMgr.getExistingXPDLIds(t);
         Collections.sort(pkgIds);
         String[] ret=new String[pkgIds.size()];
         pkgIds.toArray(ret);
         return ret;
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String[] getPackageVersions (String pkgId) throws BaseException {
      String[] ret = null;
      RepositoryTransaction t = null;
      try {
         t = SharkUtilities.createRepositoryTransaction();
         ret = getPackageVersions(t,pkgId);
         //SharkUtilities.commitRepositoryTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackRepositoryTransaction(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseRepositoryTransaction(t);
      }
      return ret;
   }

   public String[] getPackageVersions (RepositoryTransaction t,String pkgId) throws BaseException {
      try {
         List pkgVers=repMgr.getXPDLVersions(t,pkgId);
         Collections.sort(pkgVers);
         String[] ret=new String[pkgVers.size()];
         pkgVers.toArray(ret);
         return ret;
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public boolean isPackageOpened (String pkgId)  throws BaseException {
      boolean ret = false;
      RepositoryTransaction t = null;
      try {
         t = SharkUtilities.createRepositoryTransaction();
         ret = isPackageOpened(t,pkgId);
         //SharkUtilities.commitRepositoryTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackRepositoryTransaction(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseRepositoryTransaction(t);
      }
      return ret;
   }

   public boolean isPackageOpened (RepositoryTransaction t,String pkgId)  throws BaseException {
      try {
         return repMgr.doesXPDLExist(t,pkgId);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public byte[] getPackageContent (String pkgId) throws BaseException {
      byte[] ret = null;
      RepositoryTransaction t = null;
      try {
         t = SharkUtilities.createRepositoryTransaction();
         ret = getPackageContent(t,pkgId);
         //SharkUtilities.commitRepositoryTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackRepositoryTransaction(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseRepositoryTransaction(t);
      }
      return ret;
   }

   public byte[] getPackageContent (RepositoryTransaction t,String pkgId) throws BaseException {
      try {
         return convertSharkPackageContentXPDLByteArray(repMgr.getSerializedXPDLObject(t,pkgId));
      } catch (Throwable ex) {
         ex.printStackTrace();
         throw new BaseException(ex);
      }
   }

   public byte[] getPackageContent (String pkgId,String pkgVer) throws BaseException {
      byte[] ret = null;
      RepositoryTransaction t = null;
      try {
         t = SharkUtilities.createRepositoryTransaction();
         ret = getPackageContent(t,pkgId,pkgVer);
         //SharkUtilities.commitRepositoryTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackRepositoryTransaction(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseRepositoryTransaction(t);
      }
      return ret;
   }

   public byte[] getPackageContent (RepositoryTransaction t,String pkgId,String pkgVer) throws BaseException {
      try {
         return convertSharkPackageContentXPDLByteArray(repMgr.getSerializedXPDLObject(t,pkgId,pkgVer));
      } catch (Throwable ex) {
         ex.printStackTrace();
         throw new BaseException(ex);
      }
   }

   public String getCurrentPackageVersion (String pkgId) throws BaseException {
      String ret = null;
      RepositoryTransaction t = null;
      try {
         t = SharkUtilities.createRepositoryTransaction();
         ret = getCurrentPackageVersion(t,pkgId);
         //SharkUtilities.commitRepositoryTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackRepositoryTransaction(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseRepositoryTransaction(t);
      }
      return ret;
   }

   public String getCurrentPackageVersion (RepositoryTransaction t,String pkgId) throws BaseException {
      try {
         return repMgr.getCurrentVersion(t,pkgId);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String openPackage (String relativePath) throws BaseException, PackageInvalid, ExternalPackageInvalid {
      String ret=null;
      RepositoryTransaction t = null;
      try {
         t = SharkUtilities.createRepositoryTransaction();
         ret=openPackage(t,relativePath);
         SharkUtilities.commitRepositoryTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackRepositoryTransaction(t,e);
         if (e instanceof PackageInvalid)
            throw (PackageInvalid)e;
         else if (e instanceof ExternalPackageInvalid)
            throw (ExternalPackageInvalid)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseRepositoryTransaction(t);
      }
      return ret;
   }

   public String openPackage (RepositoryTransaction t,String relativePath) throws BaseException, PackageInvalid, ExternalPackageInvalid {
      return openPackage(t,null,relativePath);
   }

   public void updatePackage (String id, String relativePathToNewPackage) throws BaseException, PackageUpdateNotAllowed, PackageInvalid, ExternalPackageInvalid {
      RepositoryTransaction t = null;
      try {
         t = SharkUtilities.createRepositoryTransaction();
         updatePackage(t,id,relativePathToNewPackage);
         SharkUtilities.commitRepositoryTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackRepositoryTransaction(t,e);
         if (e instanceof PackageUpdateNotAllowed)
            throw (PackageUpdateNotAllowed)e;
         else if (e instanceof PackageInvalid)
            throw (PackageInvalid)e;
         else if (e instanceof ExternalPackageInvalid)
            throw (ExternalPackageInvalid)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseRepositoryTransaction(t);
      }
   }

   public void updatePackage (RepositoryTransaction t,String id, String relativePathToNewPackage) throws BaseException, PackageUpdateNotAllowed, PackageInvalid, ExternalPackageInvalid {
      if (id==null) throw new BaseException("Invalid package Id");
      openPackage(t,id,relativePathToNewPackage);
   }

   public void closePackage (String pkgId) throws BaseException, PackageInUse, PackageHasActiveProcesses {
      RepositoryTransaction t = null;
      try {
         t = SharkUtilities.createRepositoryTransaction();
         closePackage(t,pkgId);
         SharkUtilities.commitRepositoryTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackRepositoryTransaction(t,e);
         if (e instanceof PackageInUse)
            throw (PackageInUse)e;
         else if (e instanceof PackageHasActiveProcesses)
            throw (PackageHasActiveProcesses)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseRepositoryTransaction(t);
      }
   }

   public synchronized void closePackage (RepositoryTransaction t,String pkgId) throws BaseException, PackageInUse, PackageHasActiveProcesses {
      cus.info("PackageAdmin -> User "+userId+" is trying to close all package versions of Package with id "+pkgId);

      try {

         SharkUtilities.synchronizeXPDLCache(t);

         if (!repMgr.doesXPDLExist(t,pkgId)) {
            throw new BaseException("Package with Id="+pkgId+" does not exist");
         }
         // check if there are any packages that uses this package as it's
         // external package
         if (repMgr.getReferringXPDLIds(t,pkgId).size()>0) {
            throw new PackageInUse("The package can't be unloaded because it is referenced");
         }

         List pkgVers=repMgr.getXPDLVersions(t,pkgId);
         String curVer=repMgr.getCurrentVersion(t,pkgId);
         Package curPkg=null;
         Map pkgs=new HashMap();
         for (int i=0; i<pkgVers.size(); i++) {
            String pkgVer=(String)pkgVers.get(i);
            Package pkg=SharkUtilities.getPackage(pkgId,pkgVer);
            if (pkg==null) {
               throw new BaseException("Package with Id="+pkgId+" - something went wrong while getting XPDL object");
            }
            pkgs.put(pkgVer,pkg);
            if (pkgVer.equals(curVer)) {
               curPkg=pkg;
            }
         }
         SharkTransaction st=null;
         boolean dpe;
         try {
            st=SharkUtilities.createTransaction();
            Iterator it=pkgs.entrySet().iterator();
            while (it.hasNext()) {
               Map.Entry me=(Map.Entry)it.next();
               String pkgVer=(String)me.getKey();
               Package pkg=(Package)me.getValue();

               // check if there are any processes that were created from this
               // packages process definition, and if so, throw an exception
               if (checkDBProcesses(st,pkg,pkgId,pkgVer)) {
                  throw new PackageHasActiveProcesses("Can't remove package for which exists process instance in DB");// can't remove package with active processes
               }
            }
         } catch (Exception ex) {
            SharkUtilities.emptyCaches(st);
            throw ex;
         } finally {
            SharkUtilities.releaseTransaction(st);
         }


         Iterator it=pkgs.entrySet().iterator();
         while (it.hasNext()) {
            Map.Entry me=(Map.Entry)it.next();
            String pkgVer=(String)me.getKey();
            Package pkg=(Package)me.getValue();
            repMgr.moveToHistory(t,pkgId,pkgVer);

            WfPackageEventAuditImpl pea=new WfPackageEventAuditImpl(pkg,SharkConstants.EVENT_PACKAGE_UNLOADED,userId);
            // remove all engine objects
            xmlInterface.closePackageVersion(pkgId, pkgVer);
         }
         removeParticipantMappingsForPackage(curPkg);
         removeApplicationMappingsForPackage(curPkg);

         removeManagersForPackages(pkgs.values());

         cus.info("PackageAdmin -> User "+userId+" has closed Package with id "+pkgId);
      } catch (Exception ex) {
         cus.warn("PackageAdmin -> User "+userId+" failed to close the Package with id "+pkgId+" because it is in use");
         //ex.printStackTrace();
         //return;
         if (ex instanceof BaseException) {
            throw (BaseException)ex;
         } else if (ex instanceof PackageInUse) {
            throw (PackageInUse)ex;
         } else if (ex instanceof PackageHasActiveProcesses) {
            throw (PackageHasActiveProcesses)ex;
         } else {
            throw new BaseException(ex);
         }
      }
   }

   public void closePackage (String pkgId,String pkgVer) throws BaseException, PackageInUse, PackageHasActiveProcesses {
      RepositoryTransaction t = null;
      try {
         t = SharkUtilities.createRepositoryTransaction();
         closePackage(t,pkgId,pkgVer);
         SharkUtilities.commitRepositoryTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackRepositoryTransaction(t,e);
         if (e instanceof PackageInUse)
            throw (PackageInUse)e;
         else if (e instanceof PackageHasActiveProcesses)
            throw (PackageHasActiveProcesses)e;
         else if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseRepositoryTransaction(t);
      }
   }

   public synchronized void closePackage (RepositoryTransaction t,String pkgId,String pkgVer) throws BaseException, PackageInUse, PackageHasActiveProcesses {
      cus.info("PackageAdmin -> User "+userId+" is trying to close Package with id "+pkgId+", and version "+pkgVer);

      try {
         SharkUtilities.synchronizeXPDLCache(t);

         if (!repMgr.doesXPDLExist(t,pkgId,pkgVer)) {
            throw new BaseException("Package with Id="+pkgId+" and version="+pkgVer+" does not exist");
         }

         int hm=repMgr.getXPDLVersions(t,pkgId).size();

         Package pkg=SharkUtilities.getPackage(pkgId,pkgVer);
         if (pkg==null) {
            throw new BaseException("Package with Id="+pkgId+" - something went wrong while getting XPDL object");
         }

         String curVer=repMgr.getCurrentVersion(t,pkgId);
         // check if there are any packages that uses this package as it's
         // external package
         if (repMgr.getReferringXPDLIds(t,pkgId).size()>0 && pkgVer.equals(curVer)) {
            throw new PackageInUse("The package can't be unloaded because it is referenced");
         }

         SharkTransaction st=null;
         boolean dpe;
         try {
            st=SharkUtilities.createTransaction();
            // check if there are any processes that were created from this
            // packages process definition, and if so, throw an exception
            dpe=checkDBProcesses(st,pkg,pkgId,pkgVer);
         } catch (Exception ex) {
            SharkUtilities.emptyCaches(st);
            throw ex;
         } finally {
            SharkUtilities.releaseTransaction(st);
         }

         if (dpe) {
            throw new PackageHasActiveProcesses("Can't remove package with processes instances in DB");// can't remove package with active processes
         }

         repMgr.moveToHistory(t,pkgId,pkgVer);

         WfPackageEventAuditImpl pea=new WfPackageEventAuditImpl(pkg,SharkConstants.EVENT_PACKAGE_UNLOADED,userId);
         // remove all engine objects
         xmlInterface.closePackageVersion(pkgId, pkgVer);

         // remove mappings only if this was the only package version
         if (hm==1) {
            removeParticipantMappingsForPackage(pkg);
            removeApplicationMappingsForPackage(pkg);
         }
         removeManagersForPackage(pkg);

         cus.info("PackageAdmin -> User "+userId+" has closed Package with id "+pkgId+" and version "+pkgVer);
      } catch (Exception ex) {
         cus.warn("PackageAdmin -> User "+userId+" failed to close the Package with id "+pkgId+" and version "+pkgVer+" because it is in use");
         //ex.printStackTrace();
         //return;
         if (ex instanceof BaseException) {
            throw (BaseException)ex;
         } else if (ex instanceof PackageInUse) {
            throw (PackageInUse)ex;
         } else if (ex instanceof PackageHasActiveProcesses) {
            throw (PackageHasActiveProcesses)ex;
         } else {
            throw new BaseException(ex);
         }
      }
   }

   // returns true if there are processes in DB
   private boolean checkDBProcesses (SharkTransaction st,Package pkg,String pkgId,String pkgVer) throws Exception {
      // check if there are any processes that were created from this
      // packages process definition, and if so, throw an exception
      Iterator processes=((WorkflowProcesses)pkg.get("WorkflowProcesses")).
         toElements().iterator();
      while (processes.hasNext()) {
         WorkflowProcess wp=(WorkflowProcess)processes.next();
         String mgrName=SharkUtilities.createProcessMgrKey(pkgId,pkgVer,wp.getId());
         WfProcessMgrInternal mgr =SharkUtilities.getProcessMgr(st,mgrName);
         List procs=SharkEngineManager
            .getInstance()
            .getInstancePersistenceManager()
            .getAllProcessesForMgr(mgrName,st);
         if (procs!=null && procs.size()>0) {
            return true;
         }
      }
      return false;
   }


   public boolean isPackageReferenced(String pkgId) throws BaseException {
      boolean ret=false;
      RepositoryTransaction t = null;
      try {
         t = SharkUtilities.createRepositoryTransaction();
         ret=isPackageReferenced(t,pkgId);
         //SharkUtilities.commitRepositoryTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackRepositoryTransaction(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseRepositoryTransaction(t);
      }
      return ret;
   }

   public boolean isPackageReferenced (RepositoryTransaction t,String pkgId) throws BaseException {
      try {
         return repMgr.getReferringXPDLIds(t,pkgId).size()>0;
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public void synchronizeXPDLCache () throws BaseException {
      RepositoryTransaction t = null;
      try {
         t = SharkUtilities.createRepositoryTransaction();
         synchronizeXPDLCache(t);
         //SharkUtilities.commitRepositoryTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackRepositoryTransaction(t,e);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseRepositoryTransaction(t);
      }
   }

   public synchronized void synchronizeXPDLCache (RepositoryTransaction t) throws BaseException {
      SharkUtilities.synchronizeXPDLCache(t);
   }

   public void clearXPDLCache () throws BaseException {
      try {
         SharkUtilities.clearProcessCache();
      } catch (Exception ex) {}
      xmlInterface.closeAllPackages();
   }

   public synchronized void clearXPDLCache (RepositoryTransaction t) throws BaseException {
      try {
         SharkUtilities.clearProcessCache();
      } catch (Exception ex) {}
      xmlInterface.closeAllPackages();
   }

   public void refreshXPDLCache () throws BaseException {
      RepositoryTransaction t = null;
      try {
         t = SharkUtilities.createRepositoryTransaction();
         refreshXPDLCache(t);
         //SharkUtilities.commitRepositoryTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackRepositoryTransaction(t,e);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseRepositoryTransaction(t);
      }
   }

   public synchronized void refreshXPDLCache (RepositoryTransaction t) throws BaseException {
      try {
         SharkUtilities.clearProcessCache();
      } catch (Exception ex) {}
      xmlInterface.closeAllPackages();
      SharkUtilities.synchronizeXPDLCache(t);
   }

   private synchronized String openPackage (RepositoryTransaction t,String id,String relativePath) throws BaseException, PackageInvalid, ExternalPackageInvalid {
      boolean update=(id!=null);
      if (update) {
         cus.info("PackageAdmin -> Trying to update Package with id="+id+" with content of file at "+relativePath);
      } else {
         cus.info("PackageAdmin -> Trying to open Package file "+relativePath);
      }
      // now really import the package(s) into engine
      int exceptionHappened=0;
      XMLInterface xpdlManager=new XMLInterfaceForJDK13();
      String pkgId=null;
      Map pkgInfo=new HashMap();

      relativePath=XMLUtil.convertToSystemPath(relativePath);

      try {
         pkgId=Shark.getInstance().getRepositoryManager().getPackageId(relativePath);

         if (id!=null && !id.equals(pkgId)) {
            String msg="The package at path="+relativePath+" does not have the id="+id;
            cus.warn("PackageAdmin -> "+msg);
            BaseException bex=new BaseException(msg);
            throw bex;

         }

         // the list of already opened package Ids
         Set currentPkgIds=new HashSet(repMgr.getExistingXPDLIds(t));

         // if package user wants to open has the same Id as the one already opened - do not proceed
         if (id!=null && !currentPkgIds.contains(pkgId)) {
            String msg="The package with id "+pkgId+" is not open";
            cus.warn("PackageAdmin -> "+msg);
            BaseException bex=new BaseException(msg);
            throw bex;
         }


         // if package user wants to open has the same Id as the one already opened - do not proceed
         if (id==null && currentPkgIds.contains(pkgId)) {
            String msg="The package with id "+pkgId+" is already open";
            cus.warn("PackageAdmin -> "+msg);
            BaseException bex=new BaseException(msg);
            throw bex;
         }

         String pkgVersion=repMgr.getNextVersion(t,pkgId);

         relativePath=SharkUtilities.convertToAbsolutePath(relativePath);


         Package pkg=xpdlManager.openPackage(relativePath,true);
         pkg.setInternalVersion(pkgVersion);
         // if there are any errors - do not proceed
         SharkPackageValidator pv=new SharkPackageValidator(pkg,xpdlManager,true);
         String xpdlValidationErrors="";
         if (pkg==null || !pv.validateAll(true)) {
            cus.info("PackageAdmin -> Package file "+relativePath+" failed to open");
            if (pkg!=null) {
               xpdlValidationErrors=pv.createXPDLValidationErrorsString();
            } else {
               xpdlValidationErrors="Fatal error while opening package from ext. rep.";
            }
            if (pkg==null) {
               exceptionHappened=1;
               throw new PackageInvalid(xpdlValidationErrors,"No package");
            } else if (pv.isExternalPackageError()) {
               exceptionHappened=2;
               //pv.printDebug();
               throw new ExternalPackageInvalid(xpdlValidationErrors,"Error in external package");
            } else {
               exceptionHappened=1;
               //pv.printDebug();
               throw new PackageInvalid(xpdlValidationErrors,"Error in package");
            }
         }

         //System.out.println("SXPDLC1");
         SharkUtilities.synchronizeXPDLCache(t);

         // flats ext. packages paths and adjusts pkg Ids (adds a version), and fills
         // the info for the packages that need to be uploaded to the internal repository
         boolean chckExt=adjustPkgsForInternalRep(t,xpdlManager,pkgInfo,pkgId,update);

         /*Set pkgIdsToUpload=new HashSet();
         Iterator it=pkgInfo.values().iterator();
         while (it.hasNext()) {
            PkgInfo pi=(PkgInfo)it.next();
            if (pi.isForUpload()) {
               pkgIdsToUpload.add(pi.getId());
            }
         }*/

         // if some of ext. pkgs are already opened - recheck
         if (chckExt) {
            // close all temporary packages
            xpdlManager.closeAllPackages();

            // add all packages from remembered context map
            List pkgContents=new ArrayList();
            Iterator info=pkgInfo.values().iterator();            
            while (info.hasNext()) {
               PkgInfo pi=(PkgInfo)info.next();
               if (pi.isForUpload()) {
                  pkgContents.add(pi.getByteContent());
               } else {
                  pkgContents.add(repMgr.getSerializedXPDLObject(t,pi.getId(),pi.getVersion()));
               }
            }

            xpdlManager.setValidation(false);
            pkg=xpdlManager.openPackagesFromStreams(pkgContents,false);
            xpdlManager.setValidation(true);
            // if there are any errors - do not proceed
            pv=new SharkPackageValidator(pkg,xpdlManager,true);
            xpdlValidationErrors="";
            if (pkg==null || !pv.validateAll(true)) {
               cus.info("PackageAdmin -> Package file "+relativePath+" failed to open");
               if (pkg!=null) {
                  xpdlValidationErrors=pv.createXPDLValidationErrorsString();
               } else {
                  xpdlValidationErrors="Fatal error while opening package";
               }
               if (pkg==null) {
                  exceptionHappened=1;
                  throw new PackageInvalid(xpdlValidationErrors,"No package");
               } else if (pv.isExternalPackageError()) {
                  cus.warn("PackageAdmin -> there is some external package with the same Id and different content as the one of already opened packages - failed to open");
                  exceptionHappened=2;
                  throw new ExternalPackageInvalid("There is some external package with the same Id and different content as the one of already opened packages - failed to open","Error with ext. package");
               } else {
                  exceptionHappened=1;
                  //pv.printDebug();
                  throw new PackageInvalid(xpdlValidationErrors,"Error in package");
               }
            }

         }

         xpdlManager.closeAllPackages();

         exceptionHappened=3;

         copyPackagesToInternalPackagesRepository(t,pkgInfo.values());

         //System.out.println("SXPDLC2");
         SharkUtilities.synchronizeXPDLCache(t);

         Iterator it=pkgInfo.values().iterator();            
         Set pkgsToUpload=new HashSet();
         while (it.hasNext()) {
            PkgInfo pinf=(PkgInfo)it.next();
            if (pinf.isForUpload()) {
               Package p=xmlInterface.getPackageByIdAndVersion(pinf.getId(), pinf.getVersion());
               String evType=SharkConstants.EVENT_PACKAGE_LOADED;
               if (update && pinf.getId().equals(id)) {
                  evType=SharkConstants.EVENT_PACKAGE_UPDATED;
               }
               new WfPackageEventAuditImpl(p,evType,userId);
               pkgsToUpload.add(p);
            }
         }


         // clearing process caches
         // THIS SHOULD BE LAST METHOD TO CALL - BECAUSE OF ROLLBACK
         addManagersForPackages(pkgsToUpload);

      } catch (Exception ex) {
         //System.err.println("Exc during opening of package "+urlToPackage);
         ex.printStackTrace();
         cus.error("PackageAdmin -> Package file "+relativePath+" failed to open");
         if (exceptionHappened==0) {
            if (ex instanceof BaseException) {
               throw (BaseException)ex;
            } else {
               throw new BaseException(ex);
            }
         } else if (exceptionHappened==1) {
            throw (PackageInvalid)ex;
         } else if (exceptionHappened==2) {
            throw (ExternalPackageInvalid)ex;
         } else {
            if (pkgInfo!=null) {
               Iterator it=pkgInfo.values().iterator();
               while (it.hasNext()) {
                  PkgInfo pi=(PkgInfo)it.next();
                  try {
                     xmlInterface.closePackageVersion(pi.getId(),pi.getVersion());
                  } catch (Exception exc2) {}
               }
            }
            throw new BaseException(ex);
         }
      } finally {
         xpdlManager.closeAllPackages();
      }
      if (update) {
         cus.info("PackageAdmin -> Package with id="+pkgId+" is updated from a file "+relativePath);
      } else {
         cus.info("PackageAdmin -> Package with id="+pkgId+" is opened from a file "+relativePath);
      }
      return pkgId;

   }
   /**
    * Add a workflow package to the engine.
    * @param packages - collection of workflow packages
    * @throws Exception
    */
   private void addManagersForPackages(Collection pkgInfo) throws Exception {
      SharkTransaction t=null;
      try {
         t = SharkUtilities.createTransaction();

         Iterator mdls=pkgInfo.iterator();
         while (mdls.hasNext()) {
            Package p=(Package)mdls.next();
            String pkgId=p.getId();
            String pkgVer=p.getInternalVersion();
            //System.out.println("Adding mgrs for pinfo="+pi);
            // creating process managers for all definitions within inserted package
            WorkflowProcesses wps=p.getWorkflowProcesses();
            Iterator it=wps.toElements().iterator();
            while (it.hasNext()) {
               WorkflowProcess wp=(WorkflowProcess)it.next();
               WfProcessMgrInternal mgrInternal=SharkEngineManager
                  .getInstance()
                  .getObjectFactory()
                  .createProcessMgr(t,pkgId,pkgVer,wp.getId());
               cus.info("PackageAdmin -> ProcessDefinition "+wp.getId()+" for package "+p.getId()+" is added");
            }

         }
         SharkUtilities.commitTransaction(t);
      } catch (Exception e) {
         BaseException be=null;
         if (e instanceof BaseException)
            be=(BaseException)e;
         else
            be=new BaseException(e);

         SharkUtilities.rollbackTransaction(t,be);
         throw e;
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   private void copyPackagesToInternalPackagesRepository (RepositoryTransaction t,
                                                          Collection pkgInfo) throws Exception {
      // uploading packages
      Iterator ptc=pkgInfo.iterator();
      while (ptc.hasNext()) {
         PkgInfo pi=(PkgInfo)ptc.next();
         if (pi.isForUpload()) {
            //System.out.println("Uploading pkg "+pi.getId()+"V="+Version.getVersion());
            repMgr.uploadXPDL(t,pi.getId(),pi.getXPDLContent(),pi.getByteContent(),Version.getVersion());
         }
      }

      // adding references
      ptc=pkgInfo.iterator();
      while (ptc.hasNext()) {
         PkgInfo pi=(PkgInfo)ptc.next();
         if (pi.isForUpload()) {
            Iterator it=pi.getExtRefIds().iterator();
            int i=0;
            while (it.hasNext()) {
               repMgr.addXPDLReference(t,(String)it.next(),pi.getId(),pi.getVersion(),i++);
            }
         }
      }
   }

   private void removeManagersForPackage (Package pkg) throws Exception {
      if(pkg != null){
         SharkTransaction t=null;
         try {
            t=SharkUtilities.createTransaction();
            WorkflowProcesses wps=pkg.getWorkflowProcesses();
            Iterator it=wps.toElements().iterator();
            while (it.hasNext()) {
               WorkflowProcess wp=(WorkflowProcess)it.next();
               WfProcessMgrInternal toRem=SharkUtilities
                  .getProcessMgr(t,
                                 SharkUtilities.createProcessMgrKey(
                                    pkg.getId(),
                                    pkg.getInternalVersion(),
                                    wp.getId()));
               // deleting database entry for this process manager
               if (toRem!=null) {
                  toRem.delete(t);
                  cus.info("PackageAdmin -> ProcessDefinition "+wp.getId()+" for package "+pkg.getId()+" is removed");
               }
            }
            SharkUtilities.commitTransaction(t);
         } catch (Exception ex) {
            BaseException be=null;
            if (ex instanceof BaseException)
               be=(BaseException)ex;
            else
               be=new BaseException(ex);

            SharkUtilities.rollbackTransaction(t,be);
            throw ex;
         } finally {
            SharkUtilities.releaseTransaction(t);
         }
      }
   }

   private void removeManagersForPackages (Collection pkgs) throws Exception {
      if(pkgs != null){
         SharkTransaction t=null;
         try {
            t=SharkUtilities.createTransaction();
            Iterator itPkgs=pkgs.iterator();
            while (itPkgs.hasNext()) {
               Package pkg=(Package)itPkgs.next();
               WorkflowProcesses wps=(WorkflowProcesses)pkg.get("WorkflowProcesses");
               Iterator it=wps.toElements().iterator();
               while (it.hasNext()) {
                  WorkflowProcess wp=(WorkflowProcess)it.next();
                  WfProcessMgrInternal toRem=SharkUtilities
                     .getProcessMgr(t,
                                    SharkUtilities.createProcessMgrKey(
                                       pkg.getId(),
                                       pkg.getInternalVersion(),
                                       wp.getId()));
                  // deleting database entry for this process manager
                  if (toRem!=null) {
                     toRem.delete(t);
                     cus.info("PackageAdmin -> ProcessDefinition "+wp.getId()+" for package "+pkg.getId()+" is removed");
                  }
               }
            }
            SharkUtilities.commitTransaction(t);
         } catch (Exception ex) {
            BaseException be=null;
            if (ex instanceof BaseException)
               be=(BaseException)ex;
            else
               be=new BaseException(ex);

            SharkUtilities.rollbackTransaction(t,be);
            throw ex;
         } finally {
            SharkUtilities.releaseTransaction(t);
         }
      }
   }


   private void removeParticipantMappingsForPackage (Package pkg) throws Exception {
      ParticipantMappingManager pms=SharkEngineManager.getInstance().getParticipantMapPersistenceManager();
      if (pms==null) return;
      ParticipantMappingTransaction t=null;
      try {
         t = SharkUtilities.createParticipantMappingTransaction();
         // remove mappings for participants contained within the removed package
         Participants ps=pkg.getParticipants();
         Iterator participants=ps.toElements().iterator();
         while (participants.hasNext()) {
            Participant p=(Participant)participants.next();
            /*
             org.enhydra.shark.api.internal.partmappersistence.ParticipantMap pm=pms.createParticipantMap();
             pm.setPackageId(pkgId);
             pm.setProcessDefinitionId(null);
             pm.setParticipantId(p.getID());
             if (pms.doesParticipantMappingExist(t,pm)) {
             */
            pms.deleteParticipantMappings(
               t,
               pkg.getId(),
               null,
               p.getId());
            //            }
            //System.out.println("Rem P["+pkgId+","+""+","+p.getID()+"]");
         }
         Iterator processes=pkg.getWorkflowProcesses().toElements().iterator();
         while (processes.hasNext()) {
            WorkflowProcess wp=(WorkflowProcess)processes.next();
            String wpId=wp.getId();
            ps=wp.getParticipants();
            participants=ps.toElements().iterator();
            while (participants.hasNext()) {
               Participant p=(Participant)participants.next();
               org.enhydra.shark.api.internal.partmappersistence.ParticipantMap pm=pms.createParticipantMap();
               pm.setPackageId(pkg.getId());
               pm.setProcessDefinitionId(wpId);
               pm.setParticipantId(p.getId());
               //if (pms.doesParticipantMappingExist(t,pm)) {
               pms.deleteParticipantMappings(
                  t,
                  pkg.getId(),
                  wpId,
                  p.getId());
               //}
            }
         }
         cus.info("PackageAdmin -> Participant mappings for package "+pkg+" are removed");
         SharkUtilities.commitMappingTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackMappingTransaction(t,e);
         e.printStackTrace();
         throw e;
      } finally {
         SharkUtilities.releaseMappingTransaction(t);
      }
   }

   private void removeApplicationMappingsForPackage (Package pkg) throws Exception {
      ApplicationMappingManager ams=SharkEngineManager.getInstance().getApplicationMapPersistenceManager();
      if (ams==null) return;
      ApplicationMappingTransaction t=null;
      try {
         t = SharkUtilities.createApplicationMappingTransaction();

         // remove mappings for participants contained within the removed package
         Applications apps=pkg.getApplications();
         Iterator applications=apps.toElements().iterator();
         while (applications.hasNext()) {
            Application app=(Application)applications.next();
            if (ams.getApplicationMap(t,pkg.getId(),"",app.getId())!=null) {
               ams.deleteApplicationMapping(
                  t,
                  pkg.getId(),
                  null,
                  app.getId());
            }
         }
         Iterator processes=pkg.getWorkflowProcesses().toElements().iterator();
         while (processes.hasNext()) {
            WorkflowProcess wp=(WorkflowProcess)processes.next();
            String wpId=wp.getId();
            apps=wp.getApplications();
            applications=apps.toElements().iterator();
            while (applications.hasNext()) {
               Application app=(Application)applications.next();
               if (ams.getApplicationMap(t,pkg.getId(),wpId,app.getId())!=null) {
                  ams.deleteApplicationMapping(
                     t,
                     pkg.getId(),
                     wpId,
                     app.getId());
               }
            }
         }
         cus.info("PackageAdmin -> Application mappings for package "+pkg+" are removed");
         SharkUtilities.commitMappingTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackMappingTransaction(t,e);
         throw e;
      } finally {
         SharkUtilities.releaseMappingTransaction(t);
      }
   }

   private boolean adjustPkgsForInternalRep (RepositoryTransaction t,XMLInterface xpdlMgr,Map pkgInfo,String mainPkgId,boolean update) throws Exception {
      boolean chckExt=false;
      // flat all external packages, and use it's Ids + proper version instead of filenames
      Collection pkgsToAdjust=new HashSet(xpdlMgr.getAllPackages());
      Iterator packages=pkgsToAdjust.iterator();
      while (packages.hasNext()) {
         Package p=(Package)packages.next();
         String pkgId=p.getId();
         String version=null;
         boolean forUpl=false;
         if (!repMgr.doesXPDLExist(t,pkgId) || (update && pkgId.equals(mainPkgId))) {
            version=repMgr.getNextVersion(t,pkgId);
            forUpl=true;
         } else {
            version=repMgr.getCurrentVersion(t,pkgId);
            chckExt=true;
         }
         p.setInternalVersion(version);

         PkgInfo pInfo=new PkgInfo(p,version,forUpl);
         //System.out.println("Added pkgInfo "+pInfo.toString());
         pkgInfo.put(pkgId,pInfo);
      }
      return chckExt;
   }

   class PkgInfo {
      private Package pkg;
      private String version;
      private boolean isForUpload;
      private byte[] content;
      private byte[] xpdlContent;

      public PkgInfo (Package pkg,String version,boolean isForUpload) throws Exception {
         this.pkg=pkg;
         this.version=version;
         this.isForUpload=isForUpload;
         if (isForUpload) {
            pkg.setReadOnly(true);
            ByteArrayOutputStream os=new ByteArrayOutputStream();
            XMLUtil.packageToStream(pkg,os);
            xpdlContent=os.toByteArray();
            os.close();            
         }
         this.content=XMLUtil.serialize(pkg);
         
      }

      public Package getPackage () {
         return pkg;
      }

      public void setPackage (Package pkg) {
         this.pkg=pkg;
      }

      public String getId () {
         return pkg.getId();
      }

      public String getVersion () {
         return version;
      }

      public Collection getExtRefIds () {
         return pkg.getExternalPackageIds();
      }

      public byte[] getXPDLContent ()  {
         return xpdlContent;
      }
      
      public byte[] getByteContent () {
         return content;
      }

      public boolean isForUpload () {
         return isForUpload;
      }

      public String toString () {
         return "PKG="+pkg+",Id="+pkg.getId()+",isForUpl="+isForUpload+"]";
      }
   }

   public static byte[] convertSharkPackageContentXPDLByteArray (byte[] sharkPkgBytes) throws Exception {
      Package shrkPkg=(Package)XMLUtil.deserialize(sharkPkgBytes);
      shrkPkg.setReadOnly(false);
      Iterator it=shrkPkg.getExternalPackages().toElements().iterator();
      while (it.hasNext()) {
         ExternalPackage ep=(ExternalPackage)it.next();
         String nhref=shrkPkg.getExternalPackageId(ep.getHref());
         ep.setHref(nhref);            
      }         
      ByteArrayOutputStream os=new ByteArrayOutputStream();
      XMLUtil.packageToStream(shrkPkg,os);
      byte[] content=os.toByteArray();
      os.close();
      return content;      
   }
   
   
}


