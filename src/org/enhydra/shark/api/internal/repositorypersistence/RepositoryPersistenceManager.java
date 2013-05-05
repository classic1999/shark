package org.enhydra.shark.api.internal.repositorypersistence;

import java.util.List;
import org.enhydra.shark.api.RepositoryTransaction;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.TransactionException;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

/**
 * Interface for implementing Repository persistence interface.
 *
 * @author Sasa Bojanic
 * @version 1.0
 *
 */
public interface RepositoryPersistenceManager {

   /**
    * Method configure is called at Shark start up, to configure
    * implementation of RepositoryPersistenceManager.
    *
    * @param cus an instance of CallbackUtilities used to get
    *            properties for configuring Repository Manager in Shark.
    *
    * @exception RootException Thrown if configuring doesn't succeed.
    */
   void configure(CallbackUtilities cus) throws RootException;

   /**
    * Uploads XPDL represented by byte array into repository, and gives it
    * specified Id. The version of XPDL is automatically set.
    *
    * @param    t                   a  RepositoryTransaction
    * @param    xpdlId              a  String representing Id of xpdl
    * @param    xpdl                a  byte[] representing XPDL
    *
    * @throws   RepositoryException if something goes wrong
    *
    */
   void uploadXPDL (RepositoryTransaction t,String xpdlId,byte[] xpdl,byte[] serializedPkg,long xpdlClassVer) throws RepositoryException;

   /**
    * Updates XPDL file in the repository.
    *
    * @param    t                   a  RepositoryTransaction
    * @param    xpdlId              a  String representing Id of xpdl
    * @param    xpdlVersion         a  String representing xpdl version
    * @param    xpdl                a  byte[] representing XPDL
    *
    * @throws   RepositoryException if something goes wrong
    *
    */
   void updateXPDL (RepositoryTransaction t,String xpdlId,String xpdlVersion,byte[] xpdl,byte[] serializedPkg,long xpdlClassVer) throws RepositoryException;

   /**
    * Deletes xpdl specified by given parameters from the repository.
    *
    * @param    t                   a  RepositoryTransaction
    * @param    xpdlId              a  String representing Id of xpdl
    * @param    xpdlVersion         a  String representing xpdl version
    *
    * @throws   RepositoryException if something goes wrong
    *
    */
   void deleteXPDL (RepositoryTransaction t,String xpdlId,String xpdlVersion) throws RepositoryException;

   /**
    * Moves the specified XPDL into history. All referrences to external
    * xpdls are removed also.
    *
    * @param    t                   a  RepositoryTransaction
    * @param    xpdlId              a  String representing Id of xpdl
    * @param    xpdlVersion         a  String representing xpdl version
    *
    * @throws   RepositoryException if something goes wrong
    *
    */
   void moveToHistory (RepositoryTransaction t,String xpdlId,String xpdlVersion) throws RepositoryException;

   /**
    * Deletes specified file from history.
    *
    * @param    t                   a  RepositoryTransaction
    * @param    xpdlId              a  String representing Id of xpdl
    * @param    xpdlVersion         a  String representing xpdl version
    *
    * @throws   RepositoryException if something goes wrong
    *
    */
   void deleteFromHistory (RepositoryTransaction t,String xpdlId,String xpdlVersion) throws RepositoryException;

   /**
    * Clears the repository.
    *
    * @param    t                   a  RepositoryTransaction
    *
    * @throws   RepositoryException if something goes wrong
    *
    */
   void clearRepository (RepositoryTransaction t) throws RepositoryException;

   /**
    * Gets the current version of package with specified Id.
    *
    * @param    t                   a  RepositoryTransaction
    * @param    xpdlId              a  String representing Id of xpdl
    *
    * @return   a String representing the current version of XPDL specified
    * by given Id.
    *
    * @throws   RepositoryException if something goes wrong
    *
    */
   String getCurrentVersion (RepositoryTransaction t,String xpdlId) throws RepositoryException;

   /**
    * Gets the next version of specified Id.
    *
    * @param    t                   a  RepositoryTransaction
    * @param    xpdlId              a  String representing Id of xpdl
    *
    * @return   a String representing the next version of XPDL specified
    * by given Id.
    *
    * @throws   RepositoryException if something goes wrong
    *
    */
   String getNextVersion (RepositoryTransaction t,String xpdlId) throws RepositoryException;

   /**
    * Gets the version of serialized object that represents given XPDL.
    *
    * @param    t                   a  RepositoryTransaction
    * @param    xpdlId              a  String representing Id of xpdl
    * @param    xpdlVersion         a  String representing xpdl version
    *
    * @return   a String representing the version of serialized XPDL object
    *
    * @throws   RepositoryException if something goes wrong
    *
    */
   long getSerializedXPDLObjectVersion (RepositoryTransaction t,String xpdlId,String xpdlVersion) throws RepositoryException;
   
   /**
    * Gets the byte array for the last version of specified XPDL.
    *
    * @param    t                   a  RepositoryTransaction
    * @param    xpdlId              a  String representing Id of xpdl
    *
    * @return   a byte[] representing last version of XPDL specified
    * by given Id.
    *
    * @throws   RepositoryException if repository does not contain
    * the xpdl with given Id, or something else goes wrong.
    *
    */
   byte[] getXPDL (RepositoryTransaction t,String xpdlId) throws RepositoryException;
  
   /**
    * Gets the byte array for the last version of specified XPDL.
    * This is actually serialized Java object representing the
    * main object of XPDL object model.
    *
    * @param    t                   a  RepositoryTransaction
    * @param    xpdlId              a  String representing Id of xpdl
    *
    * @return   a byte[] representing last version of XPDL specified 
    * by given Id.
    *
    * @throws   RepositoryException if repository does not contain
    * the xpdl with given Id, or something else goes wrong.
    *
    */
   byte[] getSerializedXPDLObject (RepositoryTransaction t,String xpdlId) throws RepositoryException;

   /**
    * Gets the byte array representing specified XPDL.
    *
    * @param    t                   a  RepositoryTransaction
    * @param    xpdlId              a  String representing Id of xpdl
    * @param    xpdlVersion         a  String representing xpdl version
    *
    * @return   a byte[] representing XPDL specified by given Id and version.
    *
    * @throws   RepositoryException if repository does not contain
    * the xpdl with given Id and version, or something else goes wrong.
    *
    */
   byte[] getXPDL (RepositoryTransaction t,String xpdlId,String xpdlVersion) throws RepositoryException;

   /**
    * Gets the byte array representing specified XPDL.
    * This is actually serialized Java object representing the
    * main object of XPDL object model.
    *
    * @param    t                   a  RepositoryTransaction
    * @param    xpdlId              a  String representing Id of xpdl
    * @param    xpdlVersion         a  String representing xpdl version
    *
    * @return   a byte[] representing XPDL specified by given Id and version.
    *
    * @throws   RepositoryException if repository does not contain
    * the xpdl with given Id and version, or something else goes wrong.
    *
    */
   byte[] getSerializedXPDLObject (RepositoryTransaction t,String xpdlId,String xpdlVersion) throws RepositoryException;

   /**
    * Gets a list of all versions for specified XPDL.
    *
    * @param    t                   a  RepositoryTransaction
    * @param    xpdlId              a  String representing Id of xpdl
    *
    * @return   a List of Strings representing versions of xpdl
    * specified by given Id.
    *
    * @throws   RepositoryException if repository does not contain
    * the xpdl with given Id, or something else goes wrong.
    *
    */
   List getXPDLVersions (RepositoryTransaction t,String xpdlId) throws RepositoryException;

   /**
    * Checks if xpdl with given id exists.
    *
    * @param    t                   a  RepositoryTransaction
    * @param    xpdlId              a  String representing Id of xpdl
    *
    * @return   true if xpdl with given Id exists in repository.
    *
    * @throws   RepositoryException if something goes wrong
    *
    */
   boolean doesXPDLExist (RepositoryTransaction t,String xpdlId) throws RepositoryException;

   /**
    * Checks if xpdl with given id and version exists.
    *
    * @param    t                   a  RepositoryTransaction
    * @param    xpdlId              a  String representing Id of xpdl
    * @param    xpdlVersion         a  String representing xpdl version
    *
    * @return   true if xpdl with given Id and version exists in repository.
    *
    * @throws   RepositoryException if something goes wrong
    *
    */
   boolean doesXPDLExist (RepositoryTransaction t,String xpdlId,String xpdlVersion) throws RepositoryException;

   /**
    * Used to retrieve the list of xpdl Ids currently hold in repository.
    *
    * @param    t                   a  RepositoryTransaction
    *
    * @return   A list of strings representing the Ids of xpdls that
    * currently exist in repository.
    *
    * @throws   RepositoryException if something goes wrong
    *
    */
   List getExistingXPDLIds (RepositoryTransaction t) throws RepositoryException;


   /**
    * Adds a information on referenced XPDL.
    *
    * @param    t                      a  RepositoryTransaction
    * @param    referredXPDLId         Id of XPDL that is referred by some other XPDL
    * @param    referringXPDLId        Id of XPDL that referrs some other XPDL
    * @param    referringXPDLVersion   Version of XPDL that referrs some other XPDL
    * @param    referredXPDLNumber     The ordinal numbe of referred XPDL
    *
    * @throws   RepositoryException
    *
    */
   void addXPDLReference (RepositoryTransaction t,
                          String referredXPDLId,
                          String referringXPDLId,
                          String referringXPDLVersion,
                          int referredXPDLNumber) throws RepositoryException;


   /**
    * Gets a list of Ids of XPDLs that referr to the given one.
    *
    * @param    t                   a  RepositoryTransaction
    * @param    referredXPDLId      Id of XPDL that is referred by some other XPDLs.
    *
    * @return   a List
    *
    * @throws   RepositoryException
    *
    */
   List getReferringXPDLIds (RepositoryTransaction t,String referredXPDLId) throws RepositoryException;

   /**
    * Gets a list of versions of XPDLs with given Id that referr to the given one.
    *
    * @param    t                   a  RepositoryTransaction
    * @param    referredXPDLId      Id of XPDL that is referred by some other XPDLs.
    * @param    referringXPDLId     Id of XPDL that referrs some other XPDL
    *
    * @return   a List
    *
    * @throws   RepositoryException
    *
    */
   List getReferringXPDLVersions (RepositoryTransaction t,String referredXPDLId,String referringXPDLId) throws RepositoryException;


   /**
    * Gets a list of Ids of referred XPDLs Ids.
    *
    * @param    t                      a  RepositoryTransaction
    * @param    referringXPDLId        Id of XPDL that referrs some other XPDLs
    * @param    referringXPDLVersion   Version of XPDL that referrs some other XPDLs
    *
    * @return   a List
    *
    * @throws   RepositoryException
    *
    */
   List getReferredXPDLIds (RepositoryTransaction t,String referringXPDLId,String referringXPDLVersion) throws RepositoryException;

   /**
    * Creates repository transaction.
    *
    * @return Created repository transaction.
    *
    * @exception TransactionException If something unexpected happens.
    */
   RepositoryTransaction createTransaction() throws TransactionException;

}
