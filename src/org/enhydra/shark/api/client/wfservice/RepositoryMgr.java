package org.enhydra.shark.api.client.wfservice;


import java.util.Map;

import org.enhydra.shark.api.client.wfbase.BaseException;

/**
 * Interface used to manage shark's external repository.
 * @author Sasa Bojanic
 * @author Vladimir Puskas
 */
public interface RepositoryMgr {

   /**
    * This method is used to let shark know who uses this API.
    *
    * @param    userId              String representing user Id.
    *
    */
   void connect (String userId);

   /**
    * Returns an array of strings representing relative path to XPDL files in
    * shark's external repository. The file paths are relative to the external
    * repository folder.
    *
    * @return   an array of relative paths for XPDLs in external repository.
    *
    * @throws   BaseException If something unexpected happens.
    * (wasn't called or authentication failed).
    *
    */
   String[] getPackagePaths () throws BaseException;

   /**
    * Returns a Map which keys are paths of XPDL files in external repository,
    * and relative to this repository, and values are the Ids of package defined
    * within this XPDL files.
    *
    * @return   Map whose keys are paths of XPDL files in external repository
    * and values are the Ids of package defined within this XPDL files.
    *
    * @throws   BaseException If something unexpected happens.
    * (wasn't called or authentication failed).
    *
    */
   Map getPackagePathToIdMapping () throws BaseException;

   /**
    * Returns the id of the package defined in XPDL file in external repository.
    *
    * @param    relativePath   path of package file, relative
    * to the shark's external repository location.
    * @return   id of the package defined in XPDL file in external repository
    * as String.
    *
    * @throws   BaseException If something unexpected happens.
    * (wasn't called or authentication failed).
    *
    */
   String getPackageId(String relativePath) throws BaseException;

   /**
    * Uploads a package given as a byte array into shark external repository.
    * It saves it in this repository as a file which path is given by a
    * second parameter, and is relative to the location of external repository.
    *
    * @param    pkgContent                 array of bytes representing XPDL package.
    * @param    relativePath        path of newly uploaded package file, relative
    * to the shark's external repository location.
    *
    * @throws   BaseException If something unexpected happens.
    * @throws   RepositoryInvalid If package didn't pass shark's validation, and
    * thus can't be uploaded.
    *
    */
   void uploadPackage (byte[] pkgContent, String relativePath) throws BaseException, RepositoryInvalid;

   /**
    * Removes a package file, given by the path relative to the shark's
    * external repository, from the shark's external repository.
    *
    * @param    relativePath   relative path of package file that need to be removed.
    *
    * @throws   BaseException If something unexpected happens.
    * @throws   RepositoryInvalid If by removing this package, repository could
    * become invalid (i.e. this package is referenced by some others).
    *
    */
   void deletePackage (String relativePath) throws BaseException, RepositoryInvalid;

}
