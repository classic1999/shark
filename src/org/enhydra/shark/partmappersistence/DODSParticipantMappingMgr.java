package org.enhydra.shark.partmappersistence;

import java.util.ArrayList;
import java.util.List;

import org.enhydra.dods.DODS;
import org.enhydra.shark.api.ParticipantMappingTransaction;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.TransactionException;
import org.enhydra.shark.api.internal.partmappersistence.ParticipantMap;
import org.enhydra.shark.api.internal.partmappersistence.ParticipantMappingManager;
import org.enhydra.shark.api.internal.working.CallbackUtilities;
import org.enhydra.shark.partmappersistence.data.*;

import com.lutris.appserver.server.sql.DBTransaction;

/**
 * Implementation of ParticipantMappingsManager interface
 * @author Zoran Milakovic
 */
public class DODSParticipantMappingMgr implements ParticipantMappingManager {

//TODO zoran Ako je processId == "" tretirati ga kao null.

  public static boolean _debug_ = false;

  private static final String DBG_PARAM_NAME = "DODSParticipantMappingMgr.debug";

  public void configure (CallbackUtilities cus) throws RootException {
     if (null == cus)
            throw new RootException("Cannot configure without call back impl.");
         _debug_ = Boolean
            .valueOf(cus.getProperty(DBG_PARAM_NAME, "false"))
            .booleanValue();
  }

   /**
    * Save new ParticipantMap to database.
    *
    * @param      trans            ParticipantMappingTransaction
    * @param      pm               ParticipantMap to save
    * @return     boolean          true if everything is ok, false otherwise
    * @exception  RootException    RootException will be thrown if error occure.
    *
    */
   public boolean saveParticipantMapping(
      ParticipantMappingTransaction trans,
      ParticipantMap pm)
      throws RootException {
      boolean retVal = true;
      if (!checkValidity(pm)) {
         throw new RootException("Participant mapping [ " + pm + " ] is not valid");
      }
      if (doesParticipantMappingExist(trans, pm)) {
         throw new RootException("Participant mapping already exists");
      }
      try {
         DBTransaction dbTrans = this.getDBTransaction(trans);
         //if group user
         if (pm.getIsGroupUser()) {
            GroupUserProcLevelParticipantDO mappDO =
               GroupUserProcLevelParticipantDO.createVirgin(dbTrans);
            GroupUserDO groupDO = this.checkGroups(trans, pm);
            //process level participant
            if (pm.getProcessDefinitionId() != null) {
               ProcLevelParticipantDO procLevPartDO =
                  this.checkProcLevelParticipant(trans, pm);
               GroupUserProcLevelParticipantDO gplPart =
                  GroupUserProcLevelParticipantDO.createVirgin(dbTrans);
               gplPart.setPARTICIPANTOID(procLevPartDO);
               gplPart.setUSEROID(groupDO);
               gplPart.save();
            }
            //package level participant
            else {
               PackLevelParticipantDO pckgLevPartDO =
                  this.checkPackLevelParticipant(trans, pm);
               GroupUserPackLevelParticipantDO gplPart =
                  GroupUserPackLevelParticipantDO.createVirgin(dbTrans);
               gplPart.setPARTICIPANTOID(pckgLevPartDO);
               gplPart.setUSEROID(groupDO);
               gplPart.save();
            }

         }
         //if single user
         else {
            UserProcLevelParticipantDO mappDO =
               UserProcLevelParticipantDO.createVirgin(dbTrans);
            NormalUserDO userDO = this.checkUsers(trans, pm);
            if (pm.getProcessDefinitionId() != null) {
               ProcLevelParticipantDO procLevPartDO =
                  this.checkProcLevelParticipant(trans, pm);
               UserProcLevelParticipantDO uplPart =
                  UserProcLevelParticipantDO.createVirgin(dbTrans);
               uplPart.setPARTICIPANTOID(procLevPartDO);
               uplPart.setUSEROID(userDO);
               uplPart.save();
            } else {
               PackLevelParticipantDO pckgLevPartDO =
                  this.checkPackLevelParticipant(trans, pm);
               UserPackLevelParticipantDO uplPart =
                  UserPackLevelParticipantDO.createVirgin(dbTrans);
               uplPart.setPARTICIPANTOID(pckgLevPartDO);
               uplPart.setUSEROID(userDO);
               uplPart.save();
            }
         }
         dbTrans.write();
      } catch (Exception e) {
         throw new RootException(e);
      }
      return retVal;
   }

   /**
    * Delete specified ParticipantMap from
    * database.
    *
    * @param     trans               ParticipantMappingTransaction
    * @param     pm                  ParticipantMap to delete
    * @return    boolean             true if everything is ok, false otherwise
    * @exception RootException       RootException will be thrown if error occure.
    *
    */
   public boolean deleteParticipantMapping(
      ParticipantMappingTransaction trans,
      ParticipantMap pm)
      throws RootException {

      boolean retVal = true;
      if (!checkValidity(pm)) {
         throw new RootException("Participant mapping [ " + pm + " ] is not valid");
      }
      try {
         DBTransaction dbTrans = this.getDBTransaction(trans);
         String processId = pm.getProcessDefinitionId();
         boolean isGroup = pm.getIsGroupUser();
         //process level part.
         if (processId != null) {
            //group user
            if (isGroup) {
               this.deleteGroupProcLevelPart(pm, dbTrans);
            }
            //single user
            else {
               this.deleteNormalProcLevelPart(pm, dbTrans);
            }
         }
         //package level part.
         else {
            //group user
            if (isGroup) {
               this.deleteGroupPackLevelPart(pm, dbTrans);
            }
            //single user
            else {
               this.deleteNormalPackLevelPart(pm, dbTrans);
            }
         }
         dbTrans.write();
      } catch (Exception e) {
         throw new RootException(e);
      }

      return retVal;

   }

   /**
    * Check if specified participant map exist in database.
    *
    * @param     trans               ParticipantMappingTransaction
    * @param     pm                  ParticipantMap to delete
    * @return    boolean             true if everything is ok, false otherwise
    * @exception RootException       RootException will be thrown if error occure.
    *
    */
   public boolean doesParticipantMappingExist(
      ParticipantMappingTransaction trans,
      ParticipantMap pm)
      throws RootException {
      boolean isExist = false;
      if (!checkValidity(pm)) {
         throw new RootException("Participant mapping [ " + pm + " ] is not valid");
      }
      try {
         DBTransaction dbTrans = this.getDBTransaction(trans);
         //if group user
         if (pm.getIsGroupUser()) {
            GroupUserProcLevelParticipantDO mappDO =
               GroupUserProcLevelParticipantDO.createVirgin(dbTrans);
            GroupUserDO groupDO = this.checkGroups(trans, pm);
            //process level participant
            if (pm.getProcessDefinitionId() != null) {
               ProcLevelParticipantDO procLevPartDO =
                  this.checkProcLevelParticipant(trans, pm);
               GroupUserProcLevelParticipantQuery gplPartQuery =
                  new GroupUserProcLevelParticipantQuery(dbTrans);
               gplPartQuery.setQueryPARTICIPANTOID(procLevPartDO);
               gplPartQuery.setQueryUSEROID(groupDO);
               if (gplPartQuery.getNextDO() != null) {
                  isExist = true;
               } else {
                  this.deleteParticipantMapping(trans,pm);
               }
            }
            //package level participant
            else {
               PackLevelParticipantDO pckgLevPartDO =
                  this.checkPackLevelParticipant(trans, pm);
               GroupUserPackLevelParticipantQuery gplPartQuery =
                  new GroupUserPackLevelParticipantQuery(dbTrans);
               gplPartQuery.setQueryPARTICIPANTOID(pckgLevPartDO);
               gplPartQuery.setQueryUSEROID(groupDO);
               if (gplPartQuery.getNextDO() != null) {
                  isExist = true;
               } else {
                  this.deleteParticipantMapping(trans,pm);
               }
            }
         }
         //if single user
         else {
            UserProcLevelParticipantDO mappDO =
               UserProcLevelParticipantDO.createVirgin(dbTrans);
            NormalUserDO userDO = this.checkUsers(trans, pm);
            if (pm.getProcessDefinitionId() != null) {
               ProcLevelParticipantDO procLevPartDO =
                  this.checkProcLevelParticipant(trans, pm);
               UserProcLevelParticipantQuery uplPartQuery =
                  new UserProcLevelParticipantQuery(dbTrans);
               uplPartQuery.setQueryPARTICIPANTOID(procLevPartDO);
               uplPartQuery.setQueryUSEROID(userDO);
               if (uplPartQuery.getNextDO() != null) {
                  isExist = true;
               } else {
                  this.deleteParticipantMapping(trans,pm);
               }
            } else {
               PackLevelParticipantDO pckgLevPartDO =
                  this.checkPackLevelParticipant(trans, pm);
               UserPackLevelParticipantQuery uplPartQuery =
                  new UserPackLevelParticipantQuery(dbTrans);
               uplPartQuery.setQueryPARTICIPANTOID(pckgLevPartDO);
               uplPartQuery.setQueryUSEROID(userDO);
               if (uplPartQuery.getNextDO() != null) {
                  isExist = true;
               } else {
                  this.deleteParticipantMapping(trans,pm);
               }
            }
         }

      } catch (Exception e) {
         throw new RootException(e);
      }
      return isExist;
   }

   /**
    * Gets all ParticipantMappings from database.
    * @param      trans             ParticipantMappingTransaction
    * @return     List              List with participant mappings.
    * @exception  RootException     RootException will be thrown if error occure.
    *
    */
   public List getAllParticipantMappings(ParticipantMappingTransaction trans)
      throws RootException {
      List list = new ArrayList();
      try {
         DBTransaction dbTrans = this.getDBTransaction(trans);
         //group users, package level
         GroupUserPackLevelParticipantQuery gplQuery =
            new GroupUserPackLevelParticipantQuery(dbTrans);
         GroupUserPackLevelParticipantDO[] gplDOArray =
            gplQuery.getDOArray();
         for (int i = 0; i < gplDOArray.length; i++) {
            ParticipantMap pm = this.createParticipantMap();
            pm.setParticipantId(
               gplDOArray[i].getPARTICIPANTOID().getPARTICIPANT_ID());
            pm.setPackageId(
               gplDOArray[i]
                  .getPARTICIPANTOID()
                  .getPACKAGEOID()
                  .getPACKAGE_ID());
            pm.setUsername(gplDOArray[i].getUSEROID().getUSERNAME());
            pm.setProcessDefinitionId(null);
            pm.setIsGroupUser(true);
            list.add(pm);
         }
         //group users, process level
         GroupUserProcLevelParticipantQuery gprlQuery =
            new GroupUserProcLevelParticipantQuery(dbTrans);
         GroupUserProcLevelParticipantDO[] gprlDOArray =
            gprlQuery.getDOArray();
         for (int i = 0; i < gprlDOArray.length; i++) {
            ParticipantMap pm = this.createParticipantMap();
            pm.setParticipantId(
               gprlDOArray[i].getPARTICIPANTOID().getPARTICIPANT_ID());
            pm.setPackageId(
               gprlDOArray[i]
                  .getPARTICIPANTOID()
                  .getPROCESSOID()
                  .getPACKAGEOID()
                  .getPACKAGE_ID());
            pm.setUsername(gprlDOArray[i].getUSEROID().getUSERNAME());
            pm.setProcessDefinitionId(
               gprlDOArray[i]
                  .getPARTICIPANTOID()
                  .getPROCESSOID()
                  .getPROCESS_ID());
            pm.setIsGroupUser(true);
            list.add(pm);
         }
         //single users, package level
         UserPackLevelParticipantQuery uplQuery =
            new UserPackLevelParticipantQuery(dbTrans);
         UserPackLevelParticipantDO[] uplDOArray = uplQuery.getDOArray();
         for (int i = 0; i < uplDOArray.length; i++) {
            ParticipantMap pm = this.createParticipantMap();
            pm.setParticipantId(
               uplDOArray[i].getPARTICIPANTOID().getPARTICIPANT_ID());
            pm.setPackageId(
               uplDOArray[i]
                  .getPARTICIPANTOID()
                  .getPACKAGEOID()
                  .getPACKAGE_ID());
            pm.setUsername(uplDOArray[i].getUSEROID().getUSERNAME());
            pm.setProcessDefinitionId(null);
            pm.setIsGroupUser(false);
            list.add(pm);
         }
         //single users, process level
         UserProcLevelParticipantQuery uprlQuery =
            new UserProcLevelParticipantQuery(dbTrans);
         UserProcLevelParticipantDO[] uprlDOArray = uprlQuery.getDOArray();
         for (int i = 0; i < uprlDOArray.length; i++) {
            ParticipantMap pm = this.createParticipantMap();
            pm.setParticipantId(
               uprlDOArray[i].getPARTICIPANTOID().getPARTICIPANT_ID());
            pm.setPackageId(
               uprlDOArray[i]
                  .getPARTICIPANTOID()
                  .getPROCESSOID()
                  .getPACKAGEOID()
                  .getPACKAGE_ID());
            pm.setUsername(uprlDOArray[i].getUSEROID().getUSERNAME());
            pm.setProcessDefinitionId(
               uprlDOArray[i]
                  .getPARTICIPANTOID()
                  .getPROCESSOID()
                  .getPROCESS_ID());
            pm.setIsGroupUser(false);
            list.add(pm);
         }
         //return list with all participant mappings
         return list;
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   /**
    * Method createParticipantMap create new ParticipantMap object.
    *
    * @return   a ParticipantMap
    *
    */
   public ParticipantMap createParticipantMap() {
      return new DODSParticipantMap();
   }

   /**
    * Gets all participant mappings for
    * specified parameters.
    *
    * @param     trans                ParticipantMappingTransaction
    * @param     packageId            Package id for this mapping.
    * @param     processDefinitionId  Process id for this mapping.
    * @param     participantId        Participant id for this mapping.
    * @return    a List               List with objects ParticipantMap.
    * @exception RootException        RootException will be thrown if error occure.
    *
    */
   public List getParticipantMappings(
      ParticipantMappingTransaction trans,
      String packageId,
      String processDefinitionId,
      String participantId)
      throws RootException {
      List list = new ArrayList();
      try {
         // empty string is same as null
         if( processDefinitionId != null && processDefinitionId.trim().equals("") )
            processDefinitionId = null;
         DBTransaction dbTrans = this.getDBTransaction(trans);
         if (processDefinitionId == null) {
            //group users, package level
            XPDLParticipantPackageQuery pQuery = new XPDLParticipantPackageQuery(dbTrans);
            pQuery.setQueryPACKAGE_ID(packageId);
            pQuery.requireUniqueInstance();
            XPDLParticipantPackageDO pDO = pQuery.getNextDO();

            PackLevelParticipantQuery plpQuery =
               new PackLevelParticipantQuery(dbTrans);
            plpQuery.setQueryPARTICIPANT_ID(participantId);
            plpQuery.setQueryPACKAGEOID(pDO);
            plpQuery.requireUniqueInstance();
            PackLevelParticipantDO plpDO = plpQuery.getNextDO();

            GroupUserPackLevelParticipantQuery gplQuery =
               new GroupUserPackLevelParticipantQuery(dbTrans);
            gplQuery.setQueryPARTICIPANTOID(plpDO);
            GroupUserPackLevelParticipantDO[] gplDOArray =
               gplQuery.getDOArray();

            for (int i = 0; i < gplDOArray.length; i++) {
               ParticipantMap pm = this.createParticipantMap();
               pm.setParticipantId(
                  gplDOArray[i].getPARTICIPANTOID().getPARTICIPANT_ID());
               pm.setPackageId(
                  gplDOArray[i]
                     .getPARTICIPANTOID()
                     .getPACKAGEOID()
                     .getPACKAGE_ID());
               pm.setUsername(gplDOArray[i].getUSEROID().getUSERNAME());
               pm.setProcessDefinitionId(null);
               pm.setIsGroupUser(true);
               list.add(pm);
            }
            //single users, package level
            UserPackLevelParticipantQuery uplQuery =
               new UserPackLevelParticipantQuery(dbTrans);
            uplQuery.setQueryPARTICIPANTOID(plpDO);
            UserPackLevelParticipantDO[] uplDOArray = uplQuery.getDOArray();

            for (int i = 0; i < uplDOArray.length; i++) {
               ParticipantMap pm = this.createParticipantMap();
               pm.setParticipantId(
                  uplDOArray[i].getPARTICIPANTOID().getPARTICIPANT_ID());
               pm.setPackageId(
                  uplDOArray[i]
                     .getPARTICIPANTOID()
                     .getPACKAGEOID()
                     .getPACKAGE_ID());
               pm.setUsername(uplDOArray[i].getUSEROID().getUSERNAME());
               pm.setProcessDefinitionId(null);
               pm.setIsGroupUser(false);
               list.add(pm);
            }
         } else {
            //group users, process level
            XPDLParticipantProcessQuery pQuery = new XPDLParticipantProcessQuery(dbTrans);
            pQuery.setQueryPROCESS_ID(processDefinitionId);
            pQuery.requireUniqueInstance();
            XPDLParticipantProcessDO pDO = pQuery.getNextDO();

            ProcLevelParticipantQuery plpQuery =
               new ProcLevelParticipantQuery(dbTrans);
            plpQuery.setQueryPARTICIPANT_ID(participantId);
            plpQuery.setQueryPROCESSOID(pDO);
            plpQuery.requireUniqueInstance();
            ProcLevelParticipantDO plpDO = plpQuery.getNextDO();

            GroupUserProcLevelParticipantQuery gprlQuery =
               new GroupUserProcLevelParticipantQuery(dbTrans);
            gprlQuery.setQueryPARTICIPANTOID(plpDO);
            GroupUserProcLevelParticipantDO[] gprlDOArray =
               gprlQuery.getDOArray();

            for (int i = 0; i < gprlDOArray.length; i++) {
               ParticipantMap pm = this.createParticipantMap();
               pm.setParticipantId(
                  gprlDOArray[i].getPARTICIPANTOID().getPARTICIPANT_ID());
               pm.setPackageId(
                  gprlDOArray[i]
                     .getPARTICIPANTOID()
                     .getPROCESSOID()
                     .getPACKAGEOID()
                     .getPACKAGE_ID());
               pm.setUsername(gprlDOArray[i].getUSEROID().getUSERNAME());
               pm.setProcessDefinitionId(
                  gprlDOArray[i]
                     .getPARTICIPANTOID()
                     .getPROCESSOID()
                     .getPROCESS_ID());
               pm.setIsGroupUser(true);
               list.add(pm);
            }
            //single users, process level
            UserProcLevelParticipantQuery uprlQuery =
               new UserProcLevelParticipantQuery(dbTrans);
            uprlQuery.setQueryPARTICIPANTOID(plpDO);
            UserProcLevelParticipantDO[] uprlDOArray =
               uprlQuery.getDOArray();
            for (int i = 0; i < uprlDOArray.length; i++) {
               ParticipantMap pm = this.createParticipantMap();
               pm.setParticipantId(
                  uprlDOArray[i].getPARTICIPANTOID().getPARTICIPANT_ID());
               pm.setPackageId(
                  uprlDOArray[i]
                     .getPARTICIPANTOID()
                     .getPROCESSOID()
                     .getPACKAGEOID()
                     .getPACKAGE_ID());
               pm.setUsername(uprlDOArray[i].getUSEROID().getUSERNAME());
               pm.setProcessDefinitionId(
                  uprlDOArray[i]
                     .getPARTICIPANTOID()
                     .getPROCESSOID()
                     .getPROCESS_ID());
               pm.setIsGroupUser(false);
               list.add(pm);
            }
         }
         //return list with all participant mappings
         return list;
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   /**
    * Method deleteParticipantMappings delete participant mappings
    * for specified parameters.
    *
    * @param      trans               ParticipantMappingTransaction
    * @param      packageId           Package id
    * @param      processDefinitionId Process id
    * @param      participantId       Participant id
    * @return     boolean             true if OK, false otherwise
    * @exception  RootException       RootException will be thrown if error occure.
    *
    */
   public boolean deleteParticipantMappings(
      ParticipantMappingTransaction trans,
      String packageId,
      String processDefinitionId,
      String participantId)
      throws RootException {
      boolean retVal = true;
      try {
         //empty string is same as null
         if( processDefinitionId != null && processDefinitionId.trim().equals("") )
          processDefinitionId = null;
         ParticipantMap pm = this.createParticipantMap();
         DBTransaction dbTrans = this.getDBTransaction(trans);
         pm.setPackageId(packageId);
         pm.setProcessDefinitionId(processDefinitionId);
         pm.setParticipantId(participantId);
         //normal users
         NormalUserQuery nuQuery = new NormalUserQuery(dbTrans);
         NormalUserDO[] nuDOArr = nuQuery.getDOArray();
         for (int i = 0; i < nuDOArr.length; i++) {
            pm.setIsGroupUser(false);
            pm.setUsername(nuDOArr[i].getUSERNAME());
            if (processDefinitionId != null) {
               this.deleteNormalProcLevelPart(pm, dbTrans);
            } else {
               this.deleteNormalPackLevelPart(pm, dbTrans);
            }
         }
         //group users
         GroupUserQuery guQuery = new GroupUserQuery(dbTrans);
         GroupUserDO[] guDOArr = guQuery.getDOArray();
         for (int i = 0; i < guDOArr.length; i++) {
            pm.setIsGroupUser(true);
            pm.setUsername(guDOArr[i].getUSERNAME());
            if (processDefinitionId != null) {
               this.deleteGroupProcLevelPart(pm, dbTrans);
            } else {
               this.deleteGroupPackLevelPart(pm, dbTrans);
            }
         }
         dbTrans.write();
      } catch (Exception e) {
         throw new RootException(e);
      }
      return retVal;
   }

   /**
    * Method deleteParticipantMappings delete participant mappings
    * for specified parameters.
    *
    * @param     trans               ParticipantMappingTransaction
    * @param     username            username(s) to delete
    * @return    boolean             true if everything is OK, false otherwise
    * @exception RootException       RootException will be thrown if error occure.
    *
    */
   public boolean deleteParticipantMappings(
      ParticipantMappingTransaction trans,
      String username)
      throws RootException {
      boolean retVal = true;
      try {
         DBTransaction dbTrans = this.getDBTransaction(trans);
         String packageId = null;
         String processId = null;
         String participantId = null;

         NormalUserQuery userQuery = new NormalUserQuery(dbTrans);
         userQuery.setQueryUSERNAME(username);
         userQuery.requireUniqueInstance();
         NormalUserDO userDO = userQuery.getNextDO();

         //process level part.
         UserProcLevelParticipantQuery uplpQuery =
            new UserProcLevelParticipantQuery(dbTrans);
         uplpQuery.setQueryUSEROID(userDO);
         UserProcLevelParticipantDO[] uplpDOArray = uplpQuery.getDOArray();
         for (int i = 0; i < uplpDOArray.length; i++) {
            XPDLParticipantProcessDO procDO =
               uplpDOArray[i].getPARTICIPANTOID().getPROCESSOID();
            uplpDOArray[i].delete();
            dbTrans.write();
            this.deleteProcLevParticipant(
               uplpDOArray[i].getPARTICIPANTOID(),
               dbTrans);
            dbTrans.write();
            //process
            this.deleteProcess(procDO, dbTrans);
            XPDLParticipantPackageDO pckDO = procDO.getPACKAGEOID();
            this.deletePackage(pckDO, dbTrans);
         }

         //package level part.
         UserPackLevelParticipantQuery upclpQuery =
            new UserPackLevelParticipantQuery(dbTrans);
         upclpQuery.setQueryUSEROID(userDO);
         UserPackLevelParticipantDO[] upclpDOArray = upclpQuery.getDOArray();
         for (int i = 0; i < upclpDOArray.length; i++) {
            upclpDOArray[i].delete();
            dbTrans.write();
            this.deletePackLevParticipant(
               upclpDOArray[i].getPARTICIPANTOID(),
               dbTrans);
            XPDLParticipantPackageDO pckDO =
               upclpDOArray[i].getPARTICIPANTOID().getPACKAGEOID();
            this.deletePackage(pckDO, dbTrans);
         }
         if (userDO != null)
            this.deleteNormalUser(userDO, dbTrans);
         //group user
         GroupUserQuery groupQuery = new GroupUserQuery(dbTrans);
         groupQuery.requireUniqueInstance();
         groupQuery.setQueryUSERNAME(username);
         GroupUserDO groupDO = groupQuery.getNextDO();

         //process level part.
         GroupUserProcLevelParticipantQuery gplpQuery =
            new GroupUserProcLevelParticipantQuery(dbTrans);
         gplpQuery.setQueryUSEROID(groupDO);
         GroupUserProcLevelParticipantDO[] gplpDOArray =
            gplpQuery.getDOArray();
         for (int i = 0; i < gplpDOArray.length; i++) {
            XPDLParticipantProcessDO procDO =
               gplpDOArray[i].getPARTICIPANTOID().getPROCESSOID();
            gplpDOArray[i].delete();
            dbTrans.write();
            this.deleteProcLevParticipant(
               gplpDOArray[i].getPARTICIPANTOID(),
               dbTrans);
            dbTrans.write();
            //process
            this.deleteProcess(procDO, dbTrans);
            XPDLParticipantPackageDO pckDO = procDO.getPACKAGEOID();
            this.deletePackage(pckDO, dbTrans);
         }

         //package level part.
         GroupUserPackLevelParticipantQuery gpclpQuery =
            new GroupUserPackLevelParticipantQuery(dbTrans);
         gpclpQuery.setQueryUSEROID(groupDO);
         GroupUserPackLevelParticipantDO[] gpclpDOArray =
            gpclpQuery.getDOArray();
         for (int i = 0; i < gpclpDOArray.length; i++) {
            gpclpDOArray[i].delete();
            dbTrans.write();
            this.deletePackLevParticipant(
               gpclpDOArray[i].getPARTICIPANTOID(),
               dbTrans);
            XPDLParticipantPackageDO pckDO =
               gpclpDOArray[i].getPARTICIPANTOID().getPACKAGEOID();
            this.deletePackage(pckDO, dbTrans);
         }
         if (groupDO != null)
            this.deleteGroupUser(groupDO, dbTrans);

      } catch (Exception e) {
         e.printStackTrace();
         throw new RootException(e);
      }
      return retVal;
   }

   /**
    * Method getDBTransaction create DODS DBTransaction from ParticipantMappingTransaction.
    * @param    t                   ParticipantMappingTransaction
    * @return   DBTransaction
    *
    */
   private DBTransaction getDBTransaction(ParticipantMappingTransaction t) throws Exception {
      try {
         if (t instanceof DODSParticipantMappingTransaction)
            return ((DODSParticipantMappingTransaction) t).getDODSTransaction();
      } catch (Exception e) {
         throw e;
      }
      //error
      return null;
   }

   public boolean checkValidity(ParticipantMap pm) {
      if (pm == null
         || pm.getPackageId() == null
         || pm.getPackageId().trim().equals("")
         || pm.getParticipantId() == null
         || pm.getParticipantId().trim().equals("")
         || pm.getUsername() == null
         || pm.getUsername().trim().equals("")) {
         return false;
      } else {
         return true;
      }
   }

   private XPDLParticipantPackageDO checkPackage(ParticipantMappingTransaction trans, ParticipantMap pm)
      throws RootException {

      try {
         DBTransaction dbTrans = this.getDBTransaction(trans);
         //package
         XPDLParticipantPackageQuery pckgQuery = new XPDLParticipantPackageQuery(dbTrans);
         XPDLParticipantPackageDO pckgDO = null;

         pckgQuery.setQueryPACKAGE_ID(pm.getPackageId());
         pckgQuery.requireUniqueInstance();
         pckgDO = pckgQuery.getNextDO();
         if (pckgDO == null) {
            //insert new package
            pckgDO = XPDLParticipantPackageDO.createVirgin(dbTrans);
            pckgDO.setPACKAGE_ID(pm.getPackageId());
            pckgDO.save();
            dbTrans.write();
         }
         return pckgDO;
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   private XPDLParticipantProcessDO checkProcess(ParticipantMappingTransaction trans, ParticipantMap pm)
      throws RootException {
      try {
         DBTransaction dbTrans = this.getDBTransaction(trans);
         //process and package
         XPDLParticipantProcessQuery procQuery = new XPDLParticipantProcessQuery(dbTrans);
         XPDLParticipantProcessDO processDO = null;
         XPDLParticipantPackageQuery pckgQuery = new XPDLParticipantPackageQuery(dbTrans);
         XPDLParticipantPackageDO pckgDO = null;

         String processID = pm.getProcessDefinitionId();
         String packageID = pm.getPackageId();
         if (processID != null) {
            pckgQuery.setQueryPACKAGE_ID(pm.getPackageId());
            pckgQuery.requireUniqueInstance();
            pckgDO = pckgQuery.getNextDO();
            if (pckgDO == null) {
               //insert new package
               pckgDO = XPDLParticipantPackageDO.createVirgin(dbTrans);
               pckgDO.setPACKAGE_ID(pm.getPackageId());
               pckgDO.save();
            }

            procQuery.setQueryPROCESS_ID(processID);
            procQuery.setQueryPACKAGEOID(pckgDO);
            procQuery.requireUniqueInstance();
            processDO = procQuery.getNextDO();
            if (processDO == null) {
               //insert new process
               processDO = XPDLParticipantProcessDO.createVirgin(dbTrans);
               processDO.setPROCESS_ID(pm.getProcessDefinitionId());

               processDO.setPACKAGEOID(pckgDO);
               processDO.save();
               dbTrans.write();
            }
         }
         return processDO;
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   private ProcLevelParticipantDO checkProcLevelParticipant(
      ParticipantMappingTransaction trans,
      ParticipantMap pm)
      throws RootException {
      try {
         DBTransaction dbTrans = this.getDBTransaction(trans);
         //participants
         ProcLevelParticipantQuery procLevPartQuery =
            new ProcLevelParticipantQuery(dbTrans);
         ProcLevelParticipantDO procLevPartDO = null;
         XPDLParticipantProcessDO procDO = this.checkProcess(trans, pm);

         procLevPartQuery.setQueryPARTICIPANT_ID(pm.getParticipantId());
         procLevPartQuery.setQueryPROCESSOID(procDO);
         procLevPartQuery.requireUniqueInstance();
         procLevPartDO = procLevPartQuery.getNextDO();
         if (procLevPartDO == null) {
            procLevPartDO = ProcLevelParticipantDO.createVirgin(dbTrans);
            procLevPartDO.setPARTICIPANT_ID(pm.getParticipantId());
            procLevPartDO.setPROCESSOID(procDO);
            procLevPartDO.save();
            dbTrans.write();
         }
         return procLevPartDO;
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   private PackLevelParticipantDO checkPackLevelParticipant(
      ParticipantMappingTransaction trans,
      ParticipantMap pm)
      throws RootException {
      try {
         DBTransaction dbTrans = this.getDBTransaction(trans);
         //participants
         PackLevelParticipantQuery packLevPartQuery =
            new PackLevelParticipantQuery(dbTrans);
         PackLevelParticipantDO packLevPartDO = null;
         XPDLParticipantPackageDO packDO = this.checkPackage(trans, pm);

         packLevPartQuery.setQueryPARTICIPANT_ID(pm.getParticipantId());
         packLevPartQuery.setQueryPACKAGEOID(packDO);
         packLevPartQuery.requireUniqueInstance();
         packLevPartDO = packLevPartQuery.getNextDO();
         if (packLevPartDO == null) {
            packLevPartDO = PackLevelParticipantDO.createVirgin(dbTrans);
            packLevPartDO.setPARTICIPANT_ID(pm.getParticipantId());
            packLevPartDO.setPACKAGEOID(packDO);
            packLevPartDO.save();
            dbTrans.write();
         }
         return packLevPartDO;
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   private NormalUserDO checkUsers(
      ParticipantMappingTransaction trans,
      ParticipantMap pm)
      throws RootException {
      try {
         DBTransaction dbTrans = this.getDBTransaction(trans);
         //participants
         NormalUserQuery userQuery = new NormalUserQuery(dbTrans);
         NormalUserDO userDO = null;

         userQuery.setQueryUSERNAME(pm.getUsername());
         userQuery.requireUniqueInstance();
         userDO = userQuery.getNextDO();
         if (userDO == null) {
            userDO = NormalUserDO.createVirgin(dbTrans);
            userDO.setUSERNAME(pm.getUsername());
            userDO.save();
            dbTrans.write();
         }
         return userDO;
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   private GroupUserDO checkGroups(
      ParticipantMappingTransaction trans,
      ParticipantMap pm)
      throws RootException {
      try {
         DBTransaction dbTrans = this.getDBTransaction(trans);

         GroupUserQuery groupQuery = new GroupUserQuery(dbTrans);
         GroupUserDO groupDO = null;

         groupQuery.setQueryUSERNAME(pm.getUsername());
         groupQuery.requireUniqueInstance();
         groupDO = groupQuery.getNextDO();
         if (groupDO == null) {
            groupDO = GroupUserDO.createVirgin(dbTrans);
            groupDO.setUSERNAME(pm.getUsername());
            groupDO.save();
            dbTrans.write();
         }
         return groupDO;
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   private void deleteProcLevParticipant(
      ProcLevelParticipantDO plpDO,
      DBTransaction dbTrans)
      throws RootException {
      try {
         //proc level participant
         if( plpDO == null )
            return;
         GroupUserProcLevelParticipantQuery gulQuery =
            new GroupUserProcLevelParticipantQuery(dbTrans);
         gulQuery.setQueryPARTICIPANTOID(plpDO);

         UserProcLevelParticipantQuery ulQuery =
            new UserProcLevelParticipantQuery(dbTrans);
         ulQuery.setQueryPARTICIPANTOID(plpDO);

         if (gulQuery.getDOArray().length == 0
            && ulQuery.getDOArray().length == 0)
            plpDO.delete();
         dbTrans.write();
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   private void deletePackLevParticipant(
      PackLevelParticipantDO plpDO,
      DBTransaction dbTrans)
      throws RootException {
      try {
         //pack level participant
         if( plpDO == null )
            return;
         GroupUserPackLevelParticipantQuery gulQuery =
            new GroupUserPackLevelParticipantQuery(dbTrans);
         gulQuery.setQueryPARTICIPANTOID(plpDO);
         UserPackLevelParticipantQuery ulQuery =
            new UserPackLevelParticipantQuery(dbTrans);
         ulQuery.setQueryPARTICIPANTOID(plpDO);
         UserPackLevelParticipantDO[] ulDOArr = ulQuery.getDOArray();
         if (gulQuery.getDOArray().length == 0 && ulDOArr.length == 0)
            plpDO.delete();
         dbTrans.write();
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   private void deleteNormalUser(NormalUserDO userDO, DBTransaction dbTrans)
      throws RootException {
      try {
         if( userDO == null )
            return;
         UserProcLevelParticipantQuery uprlQuery =
            new UserProcLevelParticipantQuery(dbTrans);
         uprlQuery.setQueryUSEROID(userDO);
         UserPackLevelParticipantQuery upclQuery =
            new UserPackLevelParticipantQuery(dbTrans);
         upclQuery.setQueryUSEROID(userDO);
         if (uprlQuery.getDOArray().length == 0
            && upclQuery.getDOArray().length == 0) {
            userDO.delete();
         }
         dbTrans.write();
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   private void deleteGroupUser(GroupUserDO userDO, DBTransaction dbTrans)
      throws RootException {
      try {
         if( userDO == null )
            return;
         GroupUserProcLevelParticipantQuery uprlQuery =
            new GroupUserProcLevelParticipantQuery(dbTrans);
         uprlQuery.setQueryUSEROID(userDO);
         GroupUserPackLevelParticipantQuery upclQuery =
            new GroupUserPackLevelParticipantQuery(dbTrans);
         upclQuery.setQueryUSEROID(userDO);
         if (uprlQuery.getDOArray().length == 0
            && upclQuery.getDOArray().length == 0) {
            userDO.delete();
         }
         dbTrans.write();
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   private void deleteProcess(XPDLParticipantProcessDO procDO, DBTransaction dbTrans)
      throws RootException {
      try {
         //process
         if( procDO == null )
            return;
         ProcLevelParticipantQuery plpQuery =
            new ProcLevelParticipantQuery(dbTrans);
         plpQuery.setQueryPROCESSOID(procDO);
         ProcLevelParticipantDO[] plpDOArr = plpQuery.getDOArray();
         if (plpDOArr.length == 0)
            procDO.delete();
         dbTrans.write();
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   private void deletePackage(XPDLParticipantPackageDO pckDO, DBTransaction dbTrans)
      throws RootException {
      try {
         //package
         if( pckDO == null )
            return;
         XPDLParticipantProcessQuery pQuery = new XPDLParticipantProcessQuery(dbTrans);
         pQuery.setQueryPACKAGEOID(pckDO);
         PackLevelParticipantQuery pcLQuery =
            new PackLevelParticipantQuery(dbTrans);
         pcLQuery.setQueryPACKAGEOID(pckDO);
         if (pQuery.getDOArray().length == 0
            && pcLQuery.getDOArray().length == 0)
            pckDO.delete();
         dbTrans.write();
      } catch (Exception e) {
         throw new RootException(e);
      }
   }
   private void deleteGroupProcLevelPart(
      ParticipantMap pm,
      DBTransaction dbTrans)
      throws RootException {
      try {
         String username = pm.getUsername();
         String packageId = pm.getPackageId();
         String processId = pm.getProcessDefinitionId();
         String participantId = pm.getParticipantId();

         GroupUserQuery gQuery = null;
         GroupUserDO gDO = null;
         if (username != null && !username.trim().equals("")) {
            gQuery = new GroupUserQuery(dbTrans);
            gQuery.setQueryUSERNAME(username);
            gQuery.requireUniqueInstance();
            gDO = gQuery.getNextDO();
         }

         XPDLParticipantPackageQuery pckQuery = new XPDLParticipantPackageQuery(dbTrans);
         pckQuery.setQueryPACKAGE_ID(packageId);
         pckQuery.requireUniqueInstance();
         XPDLParticipantPackageDO pckDO = pckQuery.getNextDO();

         XPDLParticipantProcessQuery pQuery = new XPDLParticipantProcessQuery(dbTrans);
         pQuery.setQueryPROCESS_ID(processId);
         pQuery.setQueryPACKAGEOID(pckDO);
         pQuery.requireUniqueInstance();
         XPDLParticipantProcessDO procDO = pQuery.getNextDO();

         ProcLevelParticipantQuery plpQuery =
            new ProcLevelParticipantQuery(dbTrans);
         plpQuery.setQueryPARTICIPANT_ID(participantId);
         plpQuery.setQueryPROCESSOID(procDO);
         plpQuery.requireUniqueInstance();
         ProcLevelParticipantDO plpDO = plpQuery.getNextDO();

         GroupUserProcLevelParticipantQuery gulQuery =
            new GroupUserProcLevelParticipantQuery(dbTrans);
         if (gDO != null)
            gulQuery.setQueryUSEROID(gDO);
         gulQuery.setQueryPARTICIPANTOID(plpDO);
         GroupUserProcLevelParticipantDO[] gulDOArr = gulQuery.getDOArray();
         for (int i = 0; i < gulDOArr.length; i++) {
            gulDOArr[i].delete();
         }
         dbTrans.write();
         //delete unnecessairly data
         //proc level participant
         this.deleteProcLevParticipant(plpDO, dbTrans);
         //process
         this.deleteProcess(procDO, dbTrans);
         //package
         this.deletePackage(pckDO, dbTrans);
         //group user
         if (gDO != null)
            this.deleteGroupUser(gDO, dbTrans);
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   private void deleteNormalProcLevelPart(
      ParticipantMap pm,
      DBTransaction dbTrans)
      throws RootException {
      try {
         String username = pm.getUsername();
         String packageId = pm.getPackageId();
         String processId = pm.getProcessDefinitionId();
         String participantId = pm.getParticipantId();

         NormalUserQuery gQuery = null;
         NormalUserDO gDO = null;
         if (username != null && !username.trim().equals("")) {
            gQuery = new NormalUserQuery(dbTrans);
            gQuery.setQueryUSERNAME(username);
            gQuery.requireUniqueInstance();
            gDO = gQuery.getNextDO();
         }

         XPDLParticipantPackageQuery pckQuery = new XPDLParticipantPackageQuery(dbTrans);
         pckQuery.setQueryPACKAGE_ID(packageId);
         pckQuery.requireUniqueInstance();
         XPDLParticipantPackageDO pckDO = pckQuery.getNextDO();

         XPDLParticipantProcessQuery pQuery = new XPDLParticipantProcessQuery(dbTrans);
         pQuery.setQueryPROCESS_ID(processId);
         pQuery.setQueryPACKAGEOID(pckDO);
         pQuery.requireUniqueInstance();
         XPDLParticipantProcessDO procDO = pQuery.getNextDO();

         ProcLevelParticipantQuery plpQuery =
            new ProcLevelParticipantQuery(dbTrans);
         plpQuery.setQueryPARTICIPANT_ID(participantId);
         plpQuery.setQueryPROCESSOID(procDO);
         plpQuery.requireUniqueInstance();
         ProcLevelParticipantDO plpDO = plpQuery.getNextDO();

         UserProcLevelParticipantQuery gulQuery =
            new UserProcLevelParticipantQuery(dbTrans);
         if (gDO != null)
            gulQuery.setQueryUSEROID(gDO);
         gulQuery.setQueryPARTICIPANTOID(plpDO);
         UserProcLevelParticipantDO[] gulDOArr = gulQuery.getDOArray();
         for (int i = 0; i < gulDOArr.length; i++) {
            gulDOArr[i].delete();
         }
         dbTrans.write();
         //delete unnecessairly data
         //proc level participant
         this.deleteProcLevParticipant(plpDO, dbTrans);
         //process
         this.deleteProcess(procDO, dbTrans);
         //package
         this.deletePackage(pckDO, dbTrans);
         //single user
         if (gDO != null)
            this.deleteNormalUser(gDO, dbTrans);
      } catch (Exception e) {
         e.printStackTrace();
         throw new RootException(e);
      }
   }

   private void deleteGroupPackLevelPart(
      ParticipantMap pm,
      DBTransaction dbTrans)
      throws RootException {
      try {
         String username = pm.getUsername();
         String packageId = pm.getPackageId();
         String participantId = pm.getParticipantId();

         GroupUserQuery gQuery = null;
         GroupUserDO gDO = null;
         if (username != null && !username.trim().equals("")) {
            gQuery = new GroupUserQuery(dbTrans);
            gQuery.setQueryUSERNAME(username);
            gQuery.requireUniqueInstance();
            gDO = gQuery.getNextDO();
         }

         XPDLParticipantPackageQuery pckQuery = new XPDLParticipantPackageQuery(dbTrans);
         pckQuery.setQueryPACKAGE_ID(packageId);
         pckQuery.requireUniqueInstance();
         XPDLParticipantPackageDO pckDO = pckQuery.getNextDO();

         PackLevelParticipantQuery plpQuery =
            new PackLevelParticipantQuery(dbTrans);
         plpQuery.setQueryPARTICIPANT_ID(participantId);
         plpQuery.setQueryPACKAGEOID(pckDO);
         plpQuery.requireUniqueInstance();
         PackLevelParticipantDO plpDO = plpQuery.getNextDO();

         GroupUserPackLevelParticipantQuery gulQuery =
            new GroupUserPackLevelParticipantQuery(dbTrans);
         if (gDO != null)
            gulQuery.setQueryUSEROID(gDO);
         gulQuery.setQueryPARTICIPANTOID(plpDO);
         GroupUserPackLevelParticipantDO[] gulDOArr = gulQuery.getDOArray();
         for (int i = 0; i < gulDOArr.length; i++) {
            gulDOArr[i].delete();
         }
         dbTrans.write();
         //delete unnecessairly data
         //pack level participant
         this.deletePackLevParticipant(plpDO, dbTrans);
         //package
         this.deletePackage(pckDO, dbTrans);
         //group user
         if (gDO != null)
            this.deleteGroupUser(gDO, dbTrans);
      } catch (Exception e) {
         e.printStackTrace();
         throw new RootException(e);
      }
   }

   private void deleteNormalPackLevelPart(
      ParticipantMap pm,
      DBTransaction dbTrans)
      throws RootException {
      try {
         String username = pm.getUsername();
         String packageId = pm.getPackageId();
         String participantId = pm.getParticipantId();

         NormalUserQuery gQuery = null;
         NormalUserDO gDO = null;
         if (username != null && !username.trim().equals("")) {
            gQuery = new NormalUserQuery(dbTrans);
            gQuery.setQueryUSERNAME(username);
            gQuery.requireUniqueInstance();
            gDO = gQuery.getNextDO();
         }

         XPDLParticipantPackageQuery pckQuery = new XPDLParticipantPackageQuery(dbTrans);
         pckQuery.setQueryPACKAGE_ID(packageId);
         pckQuery.requireUniqueInstance();
         XPDLParticipantPackageDO pckDO = pckQuery.getNextDO();

         PackLevelParticipantQuery plpQuery =
            new PackLevelParticipantQuery(dbTrans);
         plpQuery.setQueryPARTICIPANT_ID(participantId);
         plpQuery.setQueryPACKAGEOID(pckDO);
         plpQuery.requireUniqueInstance();
         PackLevelParticipantDO plpDO = plpQuery.getNextDO();

         UserPackLevelParticipantQuery gulQuery =
            new UserPackLevelParticipantQuery(dbTrans);
         if (gDO != null)
            gulQuery.setQueryUSEROID(gDO);
         gulQuery.setQueryPARTICIPANTOID(plpDO);
         UserPackLevelParticipantDO[] gulDOArr = gulQuery.getDOArray();
         for (int i = 0; i < gulDOArr.length; i++) {
            gulDOArr[i].delete();
         }
         dbTrans.write();
         //delete unnecessairly data
         //pack level participant
         this.deletePackLevParticipant(plpDO, dbTrans);
         //package
         this.deletePackage(pckDO, dbTrans);
         //single user
         if (gDO != null)
            this.deleteNormalUser(gDO, dbTrans);
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   public ParticipantMappingTransaction getParticipantMappingTransaction() throws TransactionException {
          try {
            return new DODSParticipantMappingTransaction(DODS.getDatabaseManager().createTransaction());
          } catch (Exception ex) {
            throw new TransactionException(ex);
          }
   }
}
