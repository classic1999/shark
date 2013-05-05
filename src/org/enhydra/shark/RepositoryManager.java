package org.enhydra.shark;

import java.util.*;

import java.io.File;
import java.io.FileOutputStream;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfservice.RepositoryInvalid;
import org.enhydra.shark.api.client.wfservice.RepositoryMgr;
import org.enhydra.shark.api.internal.working.CallbackUtilities;
import org.enhydra.shark.utilities.SequencedHashMap;
import org.enhydra.shark.xpdl.XMLInterface;
import org.enhydra.shark.xpdl.XMLInterfaceForJDK13;
import org.enhydra.shark.xpdl.XMLUtil;
import org.enhydra.shark.xpdl.elements.Package;


/**
 * @author Nenad Stefanovic, Sasa Bojanic
 *
 */
public class RepositoryManager implements RepositoryMgr {

   private XMLInterface xmlInterface=SharkEngineManager.getInstance().getXMLInterface();

   private CallbackUtilities cus;
   private String userId="Unknown";

   protected RepositoryManager () {
      this.cus=SharkEngineManager.getInstance().getCallbackUtilities();
   }

   public void connect (String userId) {
      this.userId=userId;
   }

   public void deletePackage (String relativePath) throws BaseException, RepositoryInvalid {
      String path=SharkUtilities.EXTERNAL_PACKAGES_REPOSITORY+File.separator+relativePath;
      File pkgFile=new File(path);
      if (!pkgFile.exists() || pkgFile.isDirectory()) {
         cus.error("RepositoryManager -> deleting of the file "+path+" failed because it does not exist, or it is a directory");
         throw new BaseException("RepositoryManager -> deleting of the file "+path+" failed because it does not exist, or it is a directory");
      }
      boolean isDeleted=false;
      try {
         isDeleted=pkgFile.delete();
      } catch (Exception ex) {
         cus.error("RepositoryManager -> deleting of the file "+path+" failed - it might be locked");
         throw new BaseException("RepositoryManager -> deleting of the file "+path+" failed - it might be locked");
      }
      if (!isDeleted) {
         throw new BaseException("RepositoryManager -> deleting of the file "+path+" NOT deleted");
      }
      cus.info("RepositoryManager -> file "+path+" is successfully deleted from repository");

   }

   public void uploadPackage (byte[] pkgContent, String relativePath) throws BaseException, RepositoryInvalid {
      String path=SharkUtilities.EXTERNAL_PACKAGES_REPOSITORY+File.separator+relativePath;
      File pkgFile=new File(path);
      File parentFile=pkgFile.getParentFile();
      // if the file exists, or the parent file exists and it is not directory -> can't create
      if (pkgFile.exists() || (parentFile.exists() && !parentFile.isDirectory())) {
         cus.error("RepositoryManager -> upload of the file "+path+" failed because it already exists or its parent is not directory");
         throw new BaseException("RepositoryManager -> upload of the file "+path+" failed because it already exists or its parent is not directory");
      }

      if (!parentFile.exists()) {
         try {
            parentFile.mkdirs();
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      try {
         FileOutputStream fos = new FileOutputStream(pkgFile);
         fos.write(pkgContent);
         // Write to file
         fos.flush();
         fos.close();
      } catch (Exception ex) {
         cus.error("RepositoryManager -> upload of the file "+path+" failed");
         throw new BaseException(ex);
      }

      // if the package user wants to upload is not valid, delete it and
      // throw proper exception
      XMLInterface xpdlManager=new XMLInterfaceForJDK13();
      Package updatedPkg=xpdlManager.openPackage(path,false);
      SharkPackageValidator pv=new SharkPackageValidator(updatedPkg,xpdlManager,true);
      String xpdlValidationErrors="";
      if (updatedPkg==null || !pv.validateAll(true)) {
         if (updatedPkg!=null) {
            xpdlValidationErrors=pv.createXPDLValidationErrorsString();
         } else {
            xpdlValidationErrors="Fatal error while checking package";
         }
         xpdlManager.closeAllPackages();

         // delete the package file
         try {
            pkgFile.delete();
         } catch (Exception ex) {}
         cus.error("RepositoryManager -> upload of the file "+path+" failed because the package is not valid");
         throw new RepositoryInvalid(xpdlValidationErrors,"Error while uploading package to repository");
      } else {
         xpdlManager.closeAllPackages();
      }
      cus.info("RepositoryManager -> file "+path+" is successfully uploaded into repository");
   }


   public String[] getPackagePaths () throws BaseException {
      List packageFiles=SharkUtilities.getDefinedPackageFiles(SharkUtilities.EXTERNAL_PACKAGES_REPOSITORY,true);
      Collection pfls=new ArrayList();
      Iterator pfi=packageFiles.iterator();
      Collections.sort(packageFiles);
      while (pfi.hasNext()) {
         File f=(File)pfi.next();
         String fileName;
         try {
            fileName=f.getCanonicalPath();
         } catch (Exception ex) {
            fileName=f.getAbsolutePath();
         }
         fileName=fileName.substring(SharkUtilities.EXTERNAL_PACKAGES_REPOSITORY.length()+1);
         pfls.add(fileName);
      }
      String[] pfs=new String[pfls.size()];
      pfls.toArray(pfs);
      return pfs;
   }

   public Map getPackagePathToIdMapping () throws BaseException {
      List packageFiles=SharkUtilities.getDefinedPackageFiles(SharkUtilities.EXTERNAL_PACKAGES_REPOSITORY,true);
      Map m=new SequencedHashMap();
      Iterator pfi=packageFiles.iterator();
      Collections.sort(packageFiles);
      while (pfi.hasNext()) {
         File f=(File)pfi.next();
         String fileName;
         try {
            fileName=f.getCanonicalPath();
         } catch (Exception ex) {
            fileName=f.getAbsolutePath();
         }
         String pkgId=xmlInterface.getIdFromFile(fileName);
         if (pkgId!=null && pkgId.length()>0) {
            fileName=fileName.substring(SharkUtilities.EXTERNAL_PACKAGES_REPOSITORY.length()+1);
            m.put(fileName,pkgId);
         }
      }
      return m;
   }

   public String getPackageId (String relativePath) throws BaseException {
      String pkgId="";
      relativePath=XMLUtil.convertToSystemPath(relativePath);
      List packageFiles=SharkUtilities.getDefinedPackageFiles(SharkUtilities.EXTERNAL_PACKAGES_REPOSITORY,true);
      Map m=new HashMap();
      Iterator pfi=packageFiles.iterator();
      while (pfi.hasNext()) {
         File f=(File)pfi.next();
         String fileName;
         try {
            fileName=f.getCanonicalPath();
         } catch (Exception ex) {
            fileName=f.getAbsolutePath();
         }
         String relFileName=fileName.substring(SharkUtilities.EXTERNAL_PACKAGES_REPOSITORY.length()+1);
         if (relFileName.equals(relativePath)) {
            return xmlInterface.getIdFromFile(fileName);
         }
      }
      return null;
   }

}
