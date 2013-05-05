package org.enhydra.shark.api.client.wfservice;

import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.RepositoryTransaction;


/**
 * Interface used to perform some administrative operations related
 * to packages (XPDLs).
 * @author Sasa Bojanic
 * @author Vladimir Puskas
 */
public interface PackageAdministration {

   /**
    * This method is used to let shark know who uses this API.
    *
    * @param    userId              String representing user Id.
    *
    */
   void connect (String userId);

   /**
    * Returns an array of strings representing Ids of packages that are
    * loaded into engine.
    *
    * @return Array of package Ids that are loaded into engine.
    * @throws BaseException If something unexpected happens.
    *
    */
   String[] getOpenedPackageIds () throws BaseException;

   /**
    * Returns an array of strings representing Ids of packages that are
    * loaded into engine.
    *
    * @param  t RepositoryTransaction.
    * @return Array of package Ids that are loaded into engine.
    * @throws   BaseException If something unexpected happens.
    *
    */
   String[] getOpenedPackageIds (RepositoryTransaction t) throws BaseException;

   /**
    * Returns an array of strings representing versions of package with given Id that are
    * loaded into engine.
    *
    * @return Array of versions of package with given Id that are loaded into engine.
    * @throws BaseException If something unexpected happens.
    *
    */
   String[] getPackageVersions (String pkgId) throws BaseException;

   /**
    * Returns an array of strings representing versions of package with given Id that are
    * loaded into engine.
    *
    * @param  t RepositoryTransaction.
    * @return Array of versions of package with given Id that are loaded into engine.
    * @throws   BaseException If something unexpected happens.
    *
    */
   String[] getPackageVersions (RepositoryTransaction t,String pkgId) throws BaseException;

   /**
    * Returns true if package with the given Id is loaded into engine.
    *
    * @param    pkgId  Id of package to be tested if it is loaded into engine.
    * @return true if package with the given Id is loaded into engine, otherwuse
    * false.
    * @throws   BaseException If something unexpected happens.
    *
    */
   boolean isPackageOpened (String pkgId)  throws BaseException;

   /**
    * Returns true if package with the given Id is loaded into engine.
    *
    * @param    t  RepositoryTransaction.
    * @param    pkgId  Id of package to be tested if it is loaded into engine.
    * @return true if package with the given Id is loaded into engine, otherwuse
    * false.
    * @throws   BaseException If something unexpected happens.
    *
    */
   boolean isPackageOpened (RepositoryTransaction t,String pkgId)  throws BaseException;

   /**
    * Returns the content of XPDL file containing package definition with the
    * given Id as an array of bytes. The package has to be loaded into shark
    * engine (it is the XPDL file from engine's internal repository).
    *
    * @param    pkgId  Id of package which XPDL file content has to be returned.
    * @return   Content of XPDL file.
    * @throws   BaseException If something unexpected happens.
    *
    */
   byte[] getPackageContent (String pkgId) throws BaseException;

   /**
    * Returns the content of XPDL file containing package definition with the
    * given Id as an array of bytes. The package has to be loaded into shark
    * engine (it is the XPDL file from engine's internal repository).
    *
    * @param    t RepositoryTransaction.
    * @param    pkgId  Id of package which XPDL file content has to be returned.
    * @return   Content of XPDL file.
    * @throws   BaseException If something unexpected happens.
    *
    */
   byte[] getPackageContent (RepositoryTransaction t,String pkgId) throws BaseException;

   /**
    * Returns the content of XPDL file containing package definition with the
    * given Id as an array of bytes. The package has to be loaded into shark
    * engine (it is the XPDL file from engine's internal repository).
    * <p> The method returns the content of the current version of XPDL.
    *
    * @param    pkgId  Id of package which XPDL file content has to be returned.
    * @param    pkgVer The version of package which XPDL file content has to be returned.
    * @return   Content of XPDL file.
    * @throws   BaseException If something unexpected happens.
    *
    */
   byte[] getPackageContent (String pkgId,String pkgVer) throws BaseException;

   /**
    * Returns the content of XPDL file containing package definition with the
    * given Id as an array of bytes. The package has to be loaded into shark
    * engine (it is the XPDL file from engine's internal repository).
    *
    * @param    t RepositoryTransaction.
    * @param    pkgId  Id of package which XPDL file content has to be returned.
    * @param    pkgVer The version of package which XPDL file content has to be returned.
    * @return   Content of XPDL file.
    * @throws   BaseException If something unexpected happens.
    *
    */
   byte[] getPackageContent (RepositoryTransaction t,String pkgId,String pkgVer) throws BaseException;

   /**
    * Returns the current version of the package.
    *
    * @param    pkgId  Id of package which current version we want to know.
    *
    * @return   the current package version
    *
    * @throws   BaseException If something unexpected happens.
    *
    */
   String getCurrentPackageVersion (String pkgId) throws BaseException;

   /**
    * Returns the current version of the package.
    *
    * @param    t RepositoryTransaction.
    * @param    pkgId  Id of package which current version we want to know.
    *
    * @return   the current package version
    *
    * @throws   BaseException If something unexpected happens.
    *
    */
   String getCurrentPackageVersion (RepositoryTransaction t,String pkgId) throws BaseException;

   /**
    * Allows administrator to load a package that is defined in XPDL file which
    * path (relative to the external repository folder) is given as a parameter.
    * After the package is loaded into shark engine, the processes can be
    * instantiated from process definitions that are contained within the package.
    *
    * @param    relativePath  path of package XPDL file relative to the
    * external repository folder.
    * @return   The id of package that is going to be opened.
    *
    * @throws   BaseException If something unexpected happens.
    * @throws   PackageInvalid If package that user wants to open hadn't passed
    * shark's validation process.
    * @throws   ExternalPackageInvalid If package that user wants to open has
    * a external package, and this external package hadn't passed shark's
    * validation.
    *
    */
   String openPackage (String relativePath) throws BaseException, PackageInvalid, ExternalPackageInvalid;

   /**
    * Allows administrator to load a package that is defined in XPDL file which
    * path (relative to the external repository folder) is given as a parameter.
    * After the package is loaded into shark engine, the processes can be
    * instantiated from process definitions that are contained within the package.
    *
    * @param    t  RepositoryTransaction.
    * @param    relativePath  path of package XPDL file relative to the
    * external repository folder.
    * @return   The id of package that is going to be opened.
    *
    * @throws   BaseException If something unexpected happens.
    * @throws   PackageInvalid If package that user wants to open hadn't passed
    * shark's validation process.
    * @throws   ExternalPackageInvalid If package that user wants to open has
    * a external package, and this external package hadn't passed shark's
    * validation.
    *
    */
   String openPackage (RepositoryTransaction t,String relativePath) throws BaseException, PackageInvalid, ExternalPackageInvalid;

   /**
    * Allows administrator to update a package that is loaded into shark and has
    * the given id, with the package that can be found in XPDL file that is placed
    * at given path (relative to shark's external repository). After successfull
    * update, every process that has already been instantiated from the process
    * definitions contained within the package will continue its execution
    * based on old definition, but one can instantiate processes based on
    * new (updated) process definition.
    *
    * @param    id  The id of package that is loaded into engine,
    * and has to be updated.
    * @param    relativePathToNewPackage  path of XPDL file containing the
    * package that we want to update package with (path is relative to
    * shark's external repository).
    *
    * @throws   BaseException If something unexpected happens.
    * @throws   PackageUpdateNotAllowed If package update is currently not
    * allowed
    * @throws   PackageInvalid If package that user wants to open hadn't passed
    * shark's validation process.
    * @throws   ExternalPackageInvalid If package that user wants to open has
    * an external package, and this external package hadn't passed shark's
    * validation.
    *
    */
   void updatePackage (String id, String relativePathToNewPackage) throws BaseException, PackageUpdateNotAllowed, PackageInvalid, ExternalPackageInvalid;

   /**
    * Allows administrator to update a package that is loaded into shark and has
    * the given id, with the package that can be found in XPDL file that is placed
    * at given path (relative to shark's external repository). After successfull
    * update, every process that has already been instantiated from the process
    * definitions contained within the package will continue its execution
    * based on old definition, but one can instantiate processes based on
    * new (updated) process definition.
    *
    * @param    t  RepositoryTransaction.
    * @param    id  The id of package that is loaded into engine,
    * and has to be updated.
    * @param    relativePathToNewPackage  path of XPDL file containing the
    * package that we want to update package with (path is relative to
    * shark's external repository).
    *
    * @throws   BaseException If something unexpected happens.
    * @throws   PackageUpdateNotAllowed If package update is currently not
    * allowed
    * @throws   PackageInvalid If package that user wants to open hadn't passed
    * shark's validation process.
    * @throws   ExternalPackageInvalid If package that user wants to open has
    * an external package, and this external package hadn't passed shark's
    * validation.
    *
    */
   void updatePackage (RepositoryTransaction t,String id, String relativePathToNewPackage) throws BaseException, PackageUpdateNotAllowed, PackageInvalid, ExternalPackageInvalid;

   /**
    * Allows administrator to unload all package versions with the given id from shark.
    * After successfull unload, there will no longer be possible to
    * instantiate processes from definitions that were contained within
    * that package.
    *
    * @param    id  The id of package that is loaded into engine,
    * and has to be closed.
    * @throws   BaseException If something unexpected happens.
    * @throws   PackageInUse If this package is used by any other package that
    * references it as an external package, and is also loaded into engine.
    * @throws   PackageHasActiveProcesses If there are processes written in
    * instance persistence repository, that are instantiated from process definitions
    * contained within package we want to unload.
    *
    */
   void closePackage (String id) throws BaseException, PackageInUse, PackageHasActiveProcesses;

   /**
    * Allows administrator to unload all package versions with the given id from shark.
    * After successfull unload, there will no longer be possible to
    * instantiate processes from definitions that were contained within
    * that package.
    *
    * @param    t RepositoryTransaction.
    * @param    id  The id of package that is loaded into engine,
    * and has to be closed.
    * @throws   BaseException If something unexpected happens.
    * @throws   PackageInUse If this package is used by any other package that
    * references it as an external package, and is also loaded into engine.
    * @throws   PackageHasActiveProcesses If there are processes written in
    * instance persistence repository, that are instantiated from process definitions
    * contained within package we want to unload.
    *
    */
   void closePackage (RepositoryTransaction t,String id) throws BaseException, PackageInUse, PackageHasActiveProcesses;

   /**
    * Allows administrator to unload the package with the given id and given version from shark.
    * After successfull unload, there will no longer be possible to
    * instantiate processes from definitions that were contained within
    * that package.
    *
    * @param    id  The id of package that is loaded into engine,
    * and has to be closed.
    * @throws   BaseException If something unexpected happens.
    * @throws   PackageInUse If this package is used by any other package that
    * references it as an external package, and is also loaded into engine.
    * @throws   PackageHasActiveProcesses If there are processes written in
    * instance persistence repository, that are instantiated from process definitions
    * contained within package we want to unload.
    *
    */
   void closePackage (String id,String ver) throws BaseException, PackageInUse, PackageHasActiveProcesses;

   /**
    * Allows administrator to unload the package with the given id and given version from shark.
    * After successfull unload, there will no longer be possible to
    * instantiate processes from definitions that were contained within
    * that package.
    *
    * @param    t RepositoryTransaction.
    * @param    id  The id of package that is loaded into engine,
    * and has to be closed.
    * @throws   BaseException If something unexpected happens.
    * @throws   PackageInUse If this package is used by any other package that
    * references it as an external package, and is also loaded into engine.
    * @throws   PackageHasActiveProcesses If there are active processes
    * that are instantiated from process definitions contained within package
    * we want to unload.
    *
    */
   void closePackage (RepositoryTransaction t,String id,String ver) throws BaseException, PackageInUse, PackageHasActiveProcesses;

   /**
    * Returns information if the current version of package with given id, that
    * is loaded into engine, is referenced from other package (as an external package)
    * that is also loaded into engine.
    *
    * @param    pkgId The Id of package we want to check if it is referenced.
    * @return   true if package is referenced from other package, otherwise
    * false.
    * @throws   BaseException If something unexpected happens.
    *
    */
   boolean isPackageReferenced (String pkgId) throws BaseException;

   /**
    * Returns information if the current version of package with given id, that
    * is loaded into engine, is referenced from other package (as an external package)
    * that is also loaded into engine.
    *
    * @param    t RepositoryTransaction.
    * @param    pkgId The Id of package we want to check if it is referenced.
    * @return   true if package is referenced from other package, otherwise
    * false.
    * @throws   BaseException If something unexpected happens.
    *
    */
   boolean isPackageReferenced (RepositoryTransaction t,String pkgId) throws BaseException;

   /**
    * Synchronizes XPDL caches with the repository state. It is supposed to be
    * used in scenario when there are several VMs using shark, and when one of
    * them loads new XPDL or updates existing.
    *<p> It checks for all XPDLs in repository, and compares it against the cached
    * ones, and determines which cached instances should be removed from cache
    * permanently, which new instances should be loaded, and which old instances
    * should be reloaded (because the change of their external package instance).
    *
    * @throws   BaseException If something unexpected happens.
    *
    */
   void synchronizeXPDLCache () throws BaseException;

   /**
    * Synchronizes XPDL caches with the repository state. It is supposed to be
    * used in scenario when there are several VMs using shark, and when one of
    * them loads new XPDL or updates existing.
    *<p> It checks for all XPDLs in repository, and compares it against the cached
    * ones, and determines which cached instances should be removed from cache
    * permanently, which new instances should be loaded, and which old instances
    * should be reloaded (because the change of their external package instance).
    *
    * @param    t RepositoryTransaction.
    * @throws   BaseException If something unexpected happens.
    *
    */
   void synchronizeXPDLCache (RepositoryTransaction t) throws BaseException;

   /**
    * Clears XPDL caches. After clearing caches, first time some XPDL
    * instance is required, the cache is being filled with all instances
    * that exist in DB.
    *
    * @throws   BaseException If something unexpected happens.
    *
    */
   void clearXPDLCache () throws BaseException;

   /**
    * Clears XPDL caches. After clearing caches, first time some XPDL
    * instance is required, the cache is being filled with all instances
    * that exist in DB.
    *
    * @param    t RepositoryTransaction.
    * @throws   BaseException If something unexpected happens.
    *
    */
   void clearXPDLCache (RepositoryTransaction t) throws BaseException;

   /**
    * Synchronizes XPDL caches with the repository state. It is supposed to be
    * used in scenario when there are several VMs using shark, and when one of
    * them loads new XPDL or updates existing.
    *<p> It first clears all instances from the cache, and then checks for all
    * XPDLs in repository, and loads them into cache.
    * <p><b>Note: for better performance, use #synchronizeXPDLCache method.
    *
    * @throws   BaseException If something unexpected happens.
    *
    */
   void refreshXPDLCache () throws BaseException;

   /**
    * Synchronizes XPDL caches with the repository state. It is supposed to be
    * used in scenario when there are several VMs using shark, and when one of
    * them loads new XPDL or updates existing.
    * <p> It first clears all instances from the cache, and then checks for all
    * XPDLs in repository, and loads them into cache.
    * <p><b>Note: for better performance, use #synchronizeXPDLCache method.
    *
    * @throws   BaseException If something unexpected happens.
    *
    */
   void refreshXPDLCache (RepositoryTransaction t) throws BaseException;
}
