
package org.enhydra.shark.appmappersistence;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.enhydra.dods.DODS;
import org.enhydra.shark.api.ApplicationMappingTransaction;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.TransactionException;
import org.enhydra.shark.api.internal.appmappersistence.ApplicationMap;
import org.enhydra.shark.api.internal.appmappersistence.ApplicationMappingManager;
import org.enhydra.shark.api.internal.working.CallbackUtilities;
import org.enhydra.shark.appmappersistence.data.PackLevelXPDLAppDO;
import org.enhydra.shark.appmappersistence.data.PackLevelXPDLAppQuery;
import org.enhydra.shark.appmappersistence.data.PackLevelXPDLAppTAAppDetailDO;
import org.enhydra.shark.appmappersistence.data.PackLevelXPDLAppTAAppDetailQuery;
import org.enhydra.shark.appmappersistence.data.PackLevelXPDLAppTAAppDetailUsrDO;
import org.enhydra.shark.appmappersistence.data.PackLevelXPDLAppTAAppDetailUsrQuery;
import org.enhydra.shark.appmappersistence.data.PackLevelXPDLAppTAAppUserDO;
import org.enhydra.shark.appmappersistence.data.PackLevelXPDLAppTAAppUserQuery;
import org.enhydra.shark.appmappersistence.data.PackLevelXPDLAppToolAgentAppDO;
import org.enhydra.shark.appmappersistence.data.PackLevelXPDLAppToolAgentAppQuery;
import org.enhydra.shark.appmappersistence.data.ProcLevelXPDLAppDO;
import org.enhydra.shark.appmappersistence.data.ProcLevelXPDLAppQuery;
import org.enhydra.shark.appmappersistence.data.ProcLevelXPDLAppTAAppDetailDO;
import org.enhydra.shark.appmappersistence.data.ProcLevelXPDLAppTAAppDetailQuery;
import org.enhydra.shark.appmappersistence.data.ProcLevelXPDLAppTAAppDetailUsrDO;
import org.enhydra.shark.appmappersistence.data.ProcLevelXPDLAppTAAppDetailUsrQuery;
import org.enhydra.shark.appmappersistence.data.ProcLevelXPDLAppTAAppUserDO;
import org.enhydra.shark.appmappersistence.data.ProcLevelXPDLAppTAAppUserQuery;
import org.enhydra.shark.appmappersistence.data.ProcLevelXPDLAppToolAgentAppDO;
import org.enhydra.shark.appmappersistence.data.ProcLevelXPDLAppToolAgentAppQuery;
import org.enhydra.shark.appmappersistence.data.ToolAgentAppDO;
import org.enhydra.shark.appmappersistence.data.ToolAgentAppDetailDO;
import org.enhydra.shark.appmappersistence.data.ToolAgentAppDetailQuery;
import org.enhydra.shark.appmappersistence.data.ToolAgentAppDetailUserDO;
import org.enhydra.shark.appmappersistence.data.ToolAgentAppDetailUserQuery;
import org.enhydra.shark.appmappersistence.data.ToolAgentAppQuery;
import org.enhydra.shark.appmappersistence.data.ToolAgentAppUserDO;
import org.enhydra.shark.appmappersistence.data.ToolAgentAppUserQuery;
import org.enhydra.shark.appmappersistence.data.ToolAgentUserDO;
import org.enhydra.shark.appmappersistence.data.ToolAgentUserQuery;
import org.enhydra.shark.appmappersistence.data.XPDLApplicationPackageDO;
import org.enhydra.shark.appmappersistence.data.XPDLApplicationPackageQuery;
import org.enhydra.shark.appmappersistence.data.XPDLApplicationProcessDO;
import org.enhydra.shark.appmappersistence.data.XPDLApplicationProcessQuery;

import com.lutris.appserver.server.sql.DBTransaction;


/**
 * Implementation of ApplicationMappingsManager interface
 *
 * @author Zoran Milakovic
 * @author Tanja Jovanovic
 */
public class DODSApplicationMappingMgr implements ApplicationMappingManager
{
   private final String COUNTER_NAME = "APPLICATIONMAPPINGS";

   public static boolean _debug_ = false;

   private static final String DBG_PARAM_NAME = "DODSApplicationMappingMgr.debug";

   public void configure (CallbackUtilities cus) throws RootException {
      if (null == cus)
             throw new RootException("Cannot configure without call back impl.");
           _debug_ = Boolean
             .valueOf(cus.getProperty(DBG_PARAM_NAME, "false"))
             .booleanValue();
   }

   /**
    * Method saveApplicationMapping save new application mapping in database.
    *
    * @param     trans               a  MappingTransaction
    * @param     am                  an ApplicationMap
    * @return    a boolean
    * @exception RootException
    *
    */
   public boolean saveApplicationMapping( ApplicationMappingTransaction trans, ApplicationMap am ) throws RootException {
      boolean retVal = true;
      if (!checkValidity(am)) {
         throw new RootException("Application mapping is not valid");
      }
      try {
         DBTransaction dbTrans = this.getDBTransaction(trans);
         if (am.getProcessDefinitionId() != null){
            if (am.getApplicationMode() != null){
               if(am.getUsername() != null) {
                  ProcLevelXPDLAppDO procLevelXPDLAppDO =
                    this.checkProcLevelXPDLApp(trans, am);
                  ToolAgentAppDetailUserDO toolAgentAppDetailUserDO =
                    this.checkToolAgentAppDetailUser(trans, am);
                  ProcLevelXPDLAppTAAppDetailUsrDO saveDO =
                  ProcLevelXPDLAppTAAppDetailUsrDO.createVirgin(dbTrans);
                  saveDO.setXPDL_APPOID(procLevelXPDLAppDO);
                  saveDO.setTOOLAGENTOID(toolAgentAppDetailUserDO);
                  saveDO.save();
               }
               else { // if(am.getUsername() != null)
                  ProcLevelXPDLAppDO procLevelXPDLAppDO =
                    this.checkProcLevelXPDLApp(trans, am);
                  ToolAgentAppDetailDO toolAgentAppDetailDO =
                    this.checkToolAgentAppDetail(trans, am);
                  ProcLevelXPDLAppTAAppDetailDO saveDO =
                  ProcLevelXPDLAppTAAppDetailDO.createVirgin(dbTrans);
                  saveDO.setXPDL_APPOID(procLevelXPDLAppDO);
                  saveDO.setTOOLAGENTOID(toolAgentAppDetailDO);
                  saveDO.save();
               }
            }
            else { // if (am.getApplicationMode() != null)
               if(am.getUsername() != null) {
                  ProcLevelXPDLAppDO procLevelXPDLAppDO =
                    this.checkProcLevelXPDLApp(trans, am);
                  ToolAgentAppUserDO toolAgentAppUserDO =
                    this.checkToolAgentAppUser(trans, am);
                  ProcLevelXPDLAppTAAppUserDO saveDO =
                  ProcLevelXPDLAppTAAppUserDO.createVirgin(dbTrans);
                  saveDO.setXPDL_APPOID(procLevelXPDLAppDO);
                  saveDO.setTOOLAGENTOID(toolAgentAppUserDO);
                  saveDO.save();
               }
               else { // if(am.getUsername() != null)
                  ProcLevelXPDLAppDO procLevelXPDLAppDO =
                    this.checkProcLevelXPDLApp(trans, am);
                  ToolAgentAppDO toolAgentAppDO =
                    this.checkToolAgentApp(trans, am);
                  ProcLevelXPDLAppToolAgentAppDO saveDO =
                  ProcLevelXPDLAppToolAgentAppDO.createVirgin(dbTrans);
                  saveDO.setXPDL_APPOID(procLevelXPDLAppDO);
                  saveDO.setTOOLAGENTOID(toolAgentAppDO);
                  saveDO.save();
               }
            }

          }
          else { // if (am.getProcessDefinitionId())
             if (am.getApplicationMode() != null){
               if(am.getUsername() != null) {
                  PackLevelXPDLAppDO packLevelXPDLAppDO =
                    this.checkPackLevelXPDLApp(trans, am);
                  ToolAgentAppDetailUserDO toolAgentAppDetailUserDO =
                    this.checkToolAgentAppDetailUser(trans, am);
                  PackLevelXPDLAppTAAppDetailUsrDO saveDO =
                  PackLevelXPDLAppTAAppDetailUsrDO.createVirgin(dbTrans);
                  saveDO.setXPDL_APPOID(packLevelXPDLAppDO);
                  saveDO.setTOOLAGENTOID(toolAgentAppDetailUserDO);
                  saveDO.save();
               }
               else { // if(am.getUsername() != null)
                  PackLevelXPDLAppDO packLevelXPDLAppDO =
                    this.checkPackLevelXPDLApp(trans, am);
                  ToolAgentAppDetailDO toolAgentAppDetailDO =
                    this.checkToolAgentAppDetail(trans, am);
                  PackLevelXPDLAppTAAppDetailDO saveDO =
                  PackLevelXPDLAppTAAppDetailDO.createVirgin(dbTrans);
                  saveDO.setXPDL_APPOID(packLevelXPDLAppDO);
                  saveDO.setTOOLAGENTOID(toolAgentAppDetailDO);
                  saveDO.save();
               }
            }
            else { // if (am.getApplicationMode() != null)
               if(am.getUsername() != null) {
                  PackLevelXPDLAppDO packLevelXPDLAppDO =
                    this.checkPackLevelXPDLApp(trans, am);
                  ToolAgentAppUserDO toolAgentAppUserDO =
                    this.checkToolAgentAppUser(trans, am);
                  PackLevelXPDLAppTAAppUserDO saveDO =
                  PackLevelXPDLAppTAAppUserDO.createVirgin(dbTrans);
                  saveDO.setXPDL_APPOID(packLevelXPDLAppDO);
                  saveDO.setTOOLAGENTOID(toolAgentAppUserDO);
                  saveDO.save();
               }
               else { // if(am.getUsername() != null)
                  PackLevelXPDLAppDO packLevelXPDLAppDO =
                    this.checkPackLevelXPDLApp(trans, am);
                  ToolAgentAppDO toolAgentAppDO =
                    this.checkToolAgentApp(trans, am);
                  PackLevelXPDLAppToolAgentAppDO saveDO =
                  PackLevelXPDLAppToolAgentAppDO.createVirgin(dbTrans);
                  saveDO.setXPDL_APPOID(packLevelXPDLAppDO);
                  saveDO.setTOOLAGENTOID(toolAgentAppDO);
                  saveDO.save();
               }
            }
          }

      }catch(Exception e) {
         throw new RootException(e);
      }
      return retVal;
   }

   /**
    * Method deleteApplicationMapping delete specified application mapping
    * from database.
    *
    * @param    trans               a  MappingTransaction
    * @param    am                  an ApplicationMap
    * @return   a boolean
    * @exception   RootException
    *
    */
   public boolean deleteApplicationMapping( ApplicationMappingTransaction trans, ApplicationMap am ) throws RootException {
      boolean retVal = true;
      try {
         DBTransaction dbTrans = this.getDBTransaction(trans);

         String processId = am.getProcessDefinitionId();
         Integer appModeInt = am.getApplicationMode();
         String username = am.getUsername();

         if (processId != null){
            if (appModeInt != null){
               if(username != null) {
                  this.deleteProcLevelXPDLAppToolAgentAppDetailUser(am, dbTrans);
               }
               else { // if(username != null)
                  this.deleteProcLevelXPDLAppToolAgentAppDetail(am, dbTrans);
               }
            }
            else { // if appModeInt != nul)
               if(username != null) {
                  this.deleteProcLevelXPDLAppToolAgentAppUser(am, dbTrans);
               }
               else { // if(username != null)
                  this.deleteProcLevelXPDLAppToolAgentApp(am, dbTrans);
               }
            }
          }
          else { // if (processId != null)
             if (appModeInt != null){
                 if (username != null){
                  this.deletePackLevelXPDLAppToolAgentAppDetailUser(am, dbTrans);
                 }
                 else { // if(username() != null)
                  this.deletePackLevelXPDLAppToolAgentAppDetail(am, dbTrans);
                }
            }
            else { // if (appModeInt != null)
               if (username != null){
               this.deletePackLevelXPDLAppToolAgentAppUser(am, dbTrans);
               }
               else { // if(username() != null)
                  this.deletePackLevelXPDLAppToolAgentApp(am, dbTrans);
               }
            }
         }
      }
      catch(Exception e) {
         throw new RootException(e);
      }
    return retVal;
   }

   public List getAllApplicationMappings( ApplicationMappingTransaction trans ) throws RootException {
      List list = new ArrayList();
      try {
         DBTransaction dbTrans = this.getDBTransaction(trans);

         //package level,  mode, username and password not defined
         PackLevelXPDLAppToolAgentAppQuery packLevelXPDLAppToolAgentAppQuery =
            new PackLevelXPDLAppToolAgentAppQuery(dbTrans);
         PackLevelXPDLAppToolAgentAppDO[] packLevelXPDLAppToolAgentAppDOArray =
            packLevelXPDLAppToolAgentAppQuery.getDOArray();
         for (int i = 0; i < packLevelXPDLAppToolAgentAppDOArray.length; i++) {
            ApplicationMap am = this.createApplicationMap();
            am.setApplicationDefinitionId(packLevelXPDLAppToolAgentAppDOArray[i].
               getXPDL_APPOID().getAPPLICATION_ID());
            am.setPackageId(packLevelXPDLAppToolAgentAppDOArray[i].
               getXPDL_APPOID().getPACKAGEOID().getPACKAGE_ID());
            am.setProcessDefinitionId(null);
            am.setToolAgentClassName(packLevelXPDLAppToolAgentAppDOArray[i].getTOOLAGENTOID().getTOOL_AGENT_NAME());
            am.setApplicationName(packLevelXPDLAppToolAgentAppDOArray[i].getTOOLAGENTOID().getAPP_NAME());
            am.setApplicationMode(null);
            am.setUsername(null);
            am.setPassword(null);
            list.add(am);
         }

         //package level,  mode defined, username and password not defined
         PackLevelXPDLAppTAAppDetailQuery packLevelXPDLAppToolAgentAppDetailQuery =
            new PackLevelXPDLAppTAAppDetailQuery(dbTrans);
         PackLevelXPDLAppTAAppDetailDO[] packLevelXPDLAppToolAgentAppDetailDOArray =
            packLevelXPDLAppToolAgentAppDetailQuery.getDOArray();
         for (int i = 0; i < packLevelXPDLAppToolAgentAppDetailDOArray.length; i++) {
            ApplicationMap am = this.createApplicationMap();
            am.setApplicationDefinitionId(packLevelXPDLAppToolAgentAppDetailDOArray[i].
               getXPDL_APPOID().getAPPLICATION_ID());
            am.setPackageId(packLevelXPDLAppToolAgentAppDetailDOArray[i].
               getXPDL_APPOID().getPACKAGEOID().getPACKAGE_ID());
            am.setProcessDefinitionId(null);
            am.setToolAgentClassName(packLevelXPDLAppToolAgentAppDetailDOArray[i].
               getTOOLAGENTOID().getTOOLAGENT_APPOID().getTOOL_AGENT_NAME());
            am.setApplicationName(packLevelXPDLAppToolAgentAppDetailDOArray[i].
               getTOOLAGENTOID().getTOOLAGENT_APPOID().getAPP_NAME());
            am.setApplicationMode(new Integer(packLevelXPDLAppToolAgentAppDetailDOArray[i].
               getTOOLAGENTOID().getAPP_MODE().intValue()));
            am.setUsername(null);
            am.setPassword(null);
            list.add(am);
         }

         //package level,  mode not defined, username and password defined
         PackLevelXPDLAppTAAppUserQuery packLevelXPDLAppToolAgentAppUserQuery =
            new PackLevelXPDLAppTAAppUserQuery(dbTrans);
         PackLevelXPDLAppTAAppUserDO[] packLevelXPDLAppToolAgentAppUserDOArray =
            packLevelXPDLAppToolAgentAppUserQuery.getDOArray();
         for (int i = 0; i < packLevelXPDLAppToolAgentAppUserDOArray.length; i++) {
            ApplicationMap am = this.createApplicationMap();
            am.setApplicationDefinitionId(packLevelXPDLAppToolAgentAppUserDOArray[i].
               getXPDL_APPOID().getAPPLICATION_ID());
            am.setPackageId(packLevelXPDLAppToolAgentAppUserDOArray[i].
               getXPDL_APPOID().getPACKAGEOID().getPACKAGE_ID());
            am.setProcessDefinitionId(null);
            am.setToolAgentClassName(packLevelXPDLAppToolAgentAppUserDOArray[i].
               getTOOLAGENTOID().getTOOLAGENT_APPOID().getTOOL_AGENT_NAME());
            am.setApplicationName(packLevelXPDLAppToolAgentAppUserDOArray[i].
               getTOOLAGENTOID().getTOOLAGENT_APPOID().getAPP_NAME());
            am.setApplicationMode(null);
            am.setUsername(packLevelXPDLAppToolAgentAppUserDOArray[i].
               getTOOLAGENTOID().getUSEROID().getUSERNAME());
            am.setPassword(packLevelXPDLAppToolAgentAppUserDOArray[i].
               getTOOLAGENTOID().getUSEROID().getPWD());
            list.add(am);
         }

         //package level,  mode, username and password defined
         PackLevelXPDLAppTAAppDetailUsrQuery packLevelXPDLAppToolAgentAppDetailUserQuery =
            new PackLevelXPDLAppTAAppDetailUsrQuery(dbTrans);
         PackLevelXPDLAppTAAppDetailUsrDO[] packLevelXPDLAppToolAgentAppDetailUserDOArray =
            packLevelXPDLAppToolAgentAppDetailUserQuery.getDOArray();
         for (int i = 0; i < packLevelXPDLAppToolAgentAppDetailUserDOArray.length; i++) {
            ApplicationMap am = this.createApplicationMap();
            am.setApplicationDefinitionId(packLevelXPDLAppToolAgentAppDetailUserDOArray[i].
               getXPDL_APPOID().getAPPLICATION_ID());
            am.setPackageId(packLevelXPDLAppToolAgentAppDetailUserDOArray[i].
               getXPDL_APPOID().getPACKAGEOID().getPACKAGE_ID());
            am.setProcessDefinitionId(null);
            am.setToolAgentClassName(packLevelXPDLAppToolAgentAppDetailUserDOArray[i].
               getTOOLAGENTOID().getTOOLAGENT_APPOID().getTOOLAGENT_APPOID().getTOOL_AGENT_NAME());
            am.setApplicationName(packLevelXPDLAppToolAgentAppDetailUserDOArray[i].
               getTOOLAGENTOID().getTOOLAGENT_APPOID().getTOOLAGENT_APPOID().getAPP_NAME());
            am.setApplicationMode(new Integer(packLevelXPDLAppToolAgentAppDetailUserDOArray[i].
               getTOOLAGENTOID().getTOOLAGENT_APPOID().getAPP_MODE().intValue()));
            am.setUsername(packLevelXPDLAppToolAgentAppDetailUserDOArray[i].
               getTOOLAGENTOID().getUSEROID().getUSERNAME());
            am.setPassword(packLevelXPDLAppToolAgentAppDetailUserDOArray[i].
               getTOOLAGENTOID().getUSEROID().getPWD());
            list.add(am);
         }

         //process level,  mode, username and password not defined
         ProcLevelXPDLAppToolAgentAppQuery procLevelXPDLAppToolAgentAppQuery =
            new ProcLevelXPDLAppToolAgentAppQuery(dbTrans);
         ProcLevelXPDLAppToolAgentAppDO[] procLevelXPDLAppToolAgentAppDOArray =
            procLevelXPDLAppToolAgentAppQuery.getDOArray();
         for (int i = 0; i < procLevelXPDLAppToolAgentAppDOArray.length; i++) {
            ApplicationMap am = this.createApplicationMap();
            am.setApplicationDefinitionId(procLevelXPDLAppToolAgentAppDOArray[i].
               getXPDL_APPOID().getAPPLICATION_ID());
            am.setPackageId(procLevelXPDLAppToolAgentAppDOArray[i].
               getXPDL_APPOID().getPROCESSOID().getPACKAGEOID().getPACKAGE_ID());
            am.setProcessDefinitionId(procLevelXPDLAppToolAgentAppDOArray[i].
               getXPDL_APPOID().getPROCESSOID().getPROCESS_ID());
            am.setToolAgentClassName(procLevelXPDLAppToolAgentAppDOArray[i].
               getTOOLAGENTOID().getTOOL_AGENT_NAME());
            am.setApplicationName(procLevelXPDLAppToolAgentAppDOArray[i].
               getTOOLAGENTOID().getAPP_NAME());
            am.setApplicationMode(null);
            am.setUsername(null);
            am.setPassword(null);
            list.add(am);
         }

         //process level,  mode defined, username and password not defined
         ProcLevelXPDLAppTAAppDetailQuery procLevelXPDLAppToolAgentAppDetailQuery =
            new ProcLevelXPDLAppTAAppDetailQuery(dbTrans);
         ProcLevelXPDLAppTAAppDetailDO[] procLevelXPDLAppToolAgentAppDetailDOArray =
            procLevelXPDLAppToolAgentAppDetailQuery.getDOArray();
         for (int i = 0; i < procLevelXPDLAppToolAgentAppDetailDOArray.length; i++) {
            ApplicationMap am = this.createApplicationMap();
            am.setApplicationDefinitionId(procLevelXPDLAppToolAgentAppDetailDOArray[i].
               getXPDL_APPOID().getAPPLICATION_ID());
            am.setPackageId(procLevelXPDLAppToolAgentAppDetailDOArray[i].
               getXPDL_APPOID().getPROCESSOID().getPACKAGEOID().getPACKAGE_ID());
            am.setProcessDefinitionId(procLevelXPDLAppToolAgentAppDetailDOArray[i].
               getXPDL_APPOID().getPROCESSOID().getPROCESS_ID());
            am.setToolAgentClassName(procLevelXPDLAppToolAgentAppDetailDOArray[i].
               getTOOLAGENTOID().getTOOLAGENT_APPOID().getTOOL_AGENT_NAME());
            am.setApplicationName(procLevelXPDLAppToolAgentAppDetailDOArray[i].
               getTOOLAGENTOID().getTOOLAGENT_APPOID().getAPP_NAME());
            am.setApplicationMode(new Integer(procLevelXPDLAppToolAgentAppDetailDOArray[i].
               getTOOLAGENTOID().getAPP_MODE().intValue()));
            am.setUsername(null);
            am.setPassword(null);
            list.add(am);
         }

         //process level,  mode not defined, username and password defined
         ProcLevelXPDLAppTAAppUserQuery procLevelXPDLAppToolAgentAppUserQuery =
            new ProcLevelXPDLAppTAAppUserQuery(dbTrans);
         ProcLevelXPDLAppTAAppUserDO[] procLevelXPDLAppToolAgentAppUserDOArray =
            procLevelXPDLAppToolAgentAppUserQuery.getDOArray();
         for (int i = 0; i < procLevelXPDLAppToolAgentAppUserDOArray.length; i++) {
            ApplicationMap am = this.createApplicationMap();
            am.setApplicationDefinitionId(procLevelXPDLAppToolAgentAppUserDOArray[i].
               getXPDL_APPOID().getAPPLICATION_ID());
            am.setPackageId(procLevelXPDLAppToolAgentAppUserDOArray[i].
               getXPDL_APPOID().getPROCESSOID().getPACKAGEOID().getPACKAGE_ID());
            am.setProcessDefinitionId(procLevelXPDLAppToolAgentAppUserDOArray[i].
               getXPDL_APPOID().getPROCESSOID().getPROCESS_ID());
            am.setToolAgentClassName(procLevelXPDLAppToolAgentAppUserDOArray[i].
               getTOOLAGENTOID().getTOOLAGENT_APPOID().getTOOL_AGENT_NAME());
            am.setApplicationName(procLevelXPDLAppToolAgentAppUserDOArray[i].
               getTOOLAGENTOID().getTOOLAGENT_APPOID().getAPP_NAME());
            am.setApplicationMode(null);
            am.setUsername(procLevelXPDLAppToolAgentAppUserDOArray[i].
               getTOOLAGENTOID().getUSEROID().getUSERNAME());
            am.setPassword(procLevelXPDLAppToolAgentAppUserDOArray[i].
               getTOOLAGENTOID().getUSEROID().getPWD());
            list.add(am);
         }

         //process level,  mode, username and password defined
         ProcLevelXPDLAppTAAppDetailUsrQuery procLevelXPDLAppToolAgentAppDetailUserQuery =
            new ProcLevelXPDLAppTAAppDetailUsrQuery(dbTrans);
         ProcLevelXPDLAppTAAppDetailUsrDO[] procLevelXPDLAppToolAgentAppDetailUserDOArray =
            procLevelXPDLAppToolAgentAppDetailUserQuery.getDOArray();
         for (int i = 0; i < procLevelXPDLAppToolAgentAppDetailUserDOArray.length; i++) {
            ApplicationMap am = this.createApplicationMap();
            am.setApplicationDefinitionId(procLevelXPDLAppToolAgentAppDetailUserDOArray[i].
               getXPDL_APPOID().getAPPLICATION_ID());
            am.setPackageId(procLevelXPDLAppToolAgentAppDetailUserDOArray[i].
               getXPDL_APPOID().getPROCESSOID().getPACKAGEOID().getPACKAGE_ID());
            am.setProcessDefinitionId(procLevelXPDLAppToolAgentAppDetailUserDOArray[i].
               getXPDL_APPOID().getPROCESSOID().getPROCESS_ID());
            am.setToolAgentClassName(procLevelXPDLAppToolAgentAppDetailUserDOArray[i].
               getTOOLAGENTOID().getTOOLAGENT_APPOID().getTOOLAGENT_APPOID().getTOOL_AGENT_NAME());
            am.setApplicationName(procLevelXPDLAppToolAgentAppDetailUserDOArray[i].
               getTOOLAGENTOID().getTOOLAGENT_APPOID().getTOOLAGENT_APPOID().getAPP_NAME());
            am.setApplicationMode(new Integer(procLevelXPDLAppToolAgentAppDetailUserDOArray[i].
               getTOOLAGENTOID().getTOOLAGENT_APPOID().getAPP_MODE().intValue()));
            am.setUsername(procLevelXPDLAppToolAgentAppDetailUserDOArray[i].
               getTOOLAGENTOID().getUSEROID().getUSERNAME());
            am.setPassword(procLevelXPDLAppToolAgentAppDetailUserDOArray[i].
               getTOOLAGENTOID().getUSEROID().getPWD());
            list.add(am);
         }

         //return list with all application mappings
         return list;
      }catch(Exception e) {
         throw new RootException(e);
      }
   }

   public ApplicationMap createApplicationMap () {
      return new DODSApplicationMap();
   }

   public boolean deleteApplicationMapping (
      ApplicationMappingTransaction trans,
      String packageId,
      String processDefinitionId,
      String applicationId ) throws RootException {

      boolean retVal = true;
      try {
         //empty string is same as null
         if( processDefinitionId != null && processDefinitionId.trim().equals("") )
          processDefinitionId = null;
         ApplicationMap am = this.createApplicationMap();
         DBTransaction dbTrans = this.getDBTransaction(trans);
         am.setPackageId(packageId);
         am.setProcessDefinitionId(processDefinitionId);
         am.setApplicationDefinitionId(applicationId);

         // appMode, username and password not defined
         ToolAgentAppQuery toolAgentAppQuery = new ToolAgentAppQuery(dbTrans);
         ToolAgentAppDO[] toolAgentAppDOArr = toolAgentAppQuery.getDOArray();
         for (int i = 0; i < toolAgentAppDOArr.length; i++) {
            am.setToolAgentClassName(toolAgentAppDOArr[i].getTOOL_AGENT_NAME());
            am.setApplicationName(toolAgentAppDOArr[i].getAPP_NAME());
            am.setApplicationMode(null);
            am.setUsername(null);
            am.setPassword(null);
            if (processDefinitionId != null) {
               this.deleteProcLevelXPDLAppToolAgentApp(am, dbTrans);
            } else {
               this.deletePackLevelXPDLAppToolAgentApp(am, dbTrans);
            }
         }

         // appMode defined, username and password not defined
         ToolAgentAppDetailQuery toolAgentAppDetailQuery = new ToolAgentAppDetailQuery(dbTrans);
         ToolAgentAppDetailDO[] toolAgentAppDetailDOArr = toolAgentAppDetailQuery.getDOArray();
         for (int i = 0; i < toolAgentAppDetailDOArr.length; i++) {
            am.setApplicationMode(new Integer(toolAgentAppDetailDOArr[i].getAPP_MODE().intValue()));
            am.setToolAgentClassName(toolAgentAppDetailDOArr[i].getTOOLAGENT_APPOID().getTOOL_AGENT_NAME());
            am.setApplicationName(toolAgentAppDetailDOArr[i].getTOOLAGENT_APPOID().getAPP_NAME());
            am.setUsername(null);
            am.setPassword(null);
            if (processDefinitionId != null) {
               this.deleteProcLevelXPDLAppToolAgentAppDetail(am, dbTrans);
            } else {
               this.deletePackLevelXPDLAppToolAgentAppDetail(am, dbTrans);
            }
         }

         // appMode not defined, username and password defined
         ToolAgentAppUserQuery toolAgentAppUserQuery = new ToolAgentAppUserQuery(dbTrans);
         ToolAgentAppUserDO[] toolAgentAppUserDOArr = toolAgentAppUserQuery.getDOArray();
         for (int i = 0; i < toolAgentAppUserDOArr.length; i++) {
            am.setUsername(toolAgentAppUserDOArr[i].getUSEROID().getUSERNAME());
            am.setPassword(toolAgentAppUserDOArr[i].getUSEROID().getPWD());
            am.setToolAgentClassName(toolAgentAppUserDOArr[i].getTOOLAGENT_APPOID().getTOOL_AGENT_NAME());
            am.setApplicationName(toolAgentAppUserDOArr[i].getTOOLAGENT_APPOID().getAPP_NAME());
            am.setApplicationMode(null);
            if (processDefinitionId != null) {
               this.deleteProcLevelXPDLAppToolAgentAppUser(am, dbTrans);
            } else {
               this.deletePackLevelXPDLAppToolAgentAppUser(am, dbTrans);
            }
         }

         // appMode, username and password defined
         ToolAgentAppDetailUserQuery toolAgentAppDetailUserQuery = new ToolAgentAppDetailUserQuery(dbTrans);
         ToolAgentAppDetailUserDO[] toolAgentAppDetailUserDOArr = toolAgentAppDetailUserQuery.getDOArray();
         for (int i = 0; i < toolAgentAppDetailUserDOArr.length; i++) {
            am.setApplicationMode(new Integer (toolAgentAppDetailUserDOArr[i].getTOOLAGENT_APPOID().getAPP_MODE().intValue()));
            am.setUsername(toolAgentAppDetailUserDOArr[i].getUSEROID().getUSERNAME());
            am.setPassword(toolAgentAppDetailUserDOArr[i].getUSEROID().getPWD());
            am.setToolAgentClassName(toolAgentAppDetailUserDOArr[i].getTOOLAGENT_APPOID().getTOOLAGENT_APPOID().getTOOL_AGENT_NAME());
            am.setApplicationName(toolAgentAppDetailUserDOArr[i].getTOOLAGENT_APPOID().getTOOLAGENT_APPOID().getAPP_NAME());
            if (processDefinitionId != null) {
               this.deleteProcLevelXPDLAppToolAgentAppDetailUser(am, dbTrans);
            } else {
               this.deletePackLevelXPDLAppToolAgentAppDetailUser(am, dbTrans);
            }
         }

         dbTrans.write();
      } catch (Exception e) {
         throw new RootException(e);
      }
      return retVal;
/*
      ApplicationMap am = this.createApplicationMap();
      am.setPackageId(packageId);
      am.setProcessDefinitionId(processDefinitionId);
      am.setApplicationDefinitionId(applicationId);

      return this.deleteApplicationMapping(trans, am);
*/
   }

   /**
    * Method getApplicationMap finds application map in database.
    *
    * @param    trans               a  MappingTransaction
    * @param    packageId               an Integer
    * @param    processDefinitionId              an Integer
    * @param    applicationDefinitionId            an Integer
    * @return   an ApplicationMap, or null if map do not exist for specified params
    * @exception   RootException
    *
    */
   public ApplicationMap getApplicationMap (
   ApplicationMappingTransaction trans,
      String packageId,
      String processDefinitionId,
      String applicationDefinitionId) throws RootException {
      ApplicationMap am = null;
       try {
         // empty string is same as null
         if( processDefinitionId != null && processDefinitionId.trim().equals("") )
            processDefinitionId = null;
         DBTransaction dbTrans = this.getDBTransaction(trans);
         if (processDefinitionId == null) {

            XPDLApplicationPackageQuery packageQuery = new XPDLApplicationPackageQuery(dbTrans);
            packageQuery.setQueryPACKAGE_ID(packageId);
            packageQuery.requireUniqueInstance();
            XPDLApplicationPackageDO packageDO = packageQuery.getNextDO();

            PackLevelXPDLAppQuery packLevelXPDLAppQuery =
               new PackLevelXPDLAppQuery(dbTrans);
            packLevelXPDLAppQuery.setQueryAPPLICATION_ID(applicationDefinitionId);
            packLevelXPDLAppQuery.setQueryPACKAGEOID(packageDO);
            packLevelXPDLAppQuery.requireUniqueInstance();
            PackLevelXPDLAppDO packLevelXPDLAppDO = packLevelXPDLAppQuery.getNextDO();

            //package level, mode, username and password not defined
            PackLevelXPDLAppToolAgentAppQuery packLevelXPDLAppToolAgentAppQuery =
               new PackLevelXPDLAppToolAgentAppQuery(dbTrans);
            packLevelXPDLAppToolAgentAppQuery.setQueryXPDL_APPOID(packLevelXPDLAppDO);
            packLevelXPDLAppToolAgentAppQuery.requireUniqueInstance();
            PackLevelXPDLAppToolAgentAppDO packLevelXPDLAppToolAgentAppDO =
               packLevelXPDLAppToolAgentAppQuery.getNextDO();

            if (packLevelXPDLAppToolAgentAppDO != null) {
               am = this.createApplicationMap();
               am.setApplicationDefinitionId(packLevelXPDLAppToolAgentAppDO
                  .getXPDL_APPOID().getAPPLICATION_ID());
               am.setPackageId(packLevelXPDLAppToolAgentAppDO
                   .getXPDL_APPOID().getPACKAGEOID().getPACKAGE_ID());
               am.setProcessDefinitionId(null);
               am.setToolAgentClassName(packLevelXPDLAppToolAgentAppDO
                  .getTOOLAGENTOID().getTOOL_AGENT_NAME());
               am.setApplicationName(packLevelXPDLAppToolAgentAppDO
                  .getTOOLAGENTOID().getAPP_NAME());
               am.setApplicationMode(null);
               am.setUsername(null);
               am.setPassword(null);
               return am;
            }

            //package level, mode defined, username and password not defined
            PackLevelXPDLAppTAAppDetailQuery packLevelXPDLAppToolAgentAppDetailQuery =
               new PackLevelXPDLAppTAAppDetailQuery(dbTrans);
            packLevelXPDLAppToolAgentAppDetailQuery.setQueryXPDL_APPOID(packLevelXPDLAppDO);
            packLevelXPDLAppToolAgentAppDetailQuery.requireUniqueInstance();
            PackLevelXPDLAppTAAppDetailDO packLevelXPDLAppToolAgentAppDetailDO =
               packLevelXPDLAppToolAgentAppDetailQuery.getNextDO();

            if (packLevelXPDLAppToolAgentAppDetailDO != null){
               am = this.createApplicationMap();
               am.setApplicationDefinitionId(packLevelXPDLAppToolAgentAppDetailDO
                  .getXPDL_APPOID().getAPPLICATION_ID());
               am.setPackageId(packLevelXPDLAppToolAgentAppDetailDO
                   .getXPDL_APPOID().getPACKAGEOID().getPACKAGE_ID());
               am.setProcessDefinitionId(null);
               am.setToolAgentClassName(packLevelXPDLAppToolAgentAppDetailDO
                  .getTOOLAGENTOID().getTOOLAGENT_APPOID().getTOOL_AGENT_NAME());
               am.setApplicationName(packLevelXPDLAppToolAgentAppDetailDO
                  .getTOOLAGENTOID().getTOOLAGENT_APPOID().getAPP_NAME());
               am.setApplicationMode(new Integer(packLevelXPDLAppToolAgentAppDetailDO
                  .getTOOLAGENTOID().getAPP_MODE().intValue()));
               am.setUsername(null);
               am.setPassword(null);
               return am;
            }

            //package level, mode not defined, username and password defined

            PackLevelXPDLAppTAAppUserQuery packLevelXPDLAppToolAgentAppUserQuery =
               new PackLevelXPDLAppTAAppUserQuery(dbTrans);
            packLevelXPDLAppToolAgentAppUserQuery.setQueryXPDL_APPOID(packLevelXPDLAppDO);
            packLevelXPDLAppToolAgentAppUserQuery.requireUniqueInstance();
            PackLevelXPDLAppTAAppUserDO packLevelXPDLAppToolAgentAppUserDO =
               packLevelXPDLAppToolAgentAppUserQuery.getNextDO();

            if (packLevelXPDLAppToolAgentAppUserDO != null) {
               am = this.createApplicationMap();
               am.setApplicationDefinitionId(packLevelXPDLAppToolAgentAppUserDO
                  .getXPDL_APPOID().getAPPLICATION_ID());
               am.setPackageId(packLevelXPDLAppToolAgentAppUserDO
                   .getXPDL_APPOID().getPACKAGEOID().getPACKAGE_ID());
               am.setProcessDefinitionId(null);
               am.setToolAgentClassName(packLevelXPDLAppToolAgentAppUserDO
                  .getTOOLAGENTOID().getTOOLAGENT_APPOID().getTOOL_AGENT_NAME());
               am.setApplicationName(packLevelXPDLAppToolAgentAppUserDO
                  .getTOOLAGENTOID().getTOOLAGENT_APPOID().getAPP_NAME());
               am.setApplicationMode(null);
               am.setUsername(packLevelXPDLAppToolAgentAppUserDO
                  .getTOOLAGENTOID().getUSEROID().getUSERNAME());
               am.setPassword(packLevelXPDLAppToolAgentAppUserDO
                  .getTOOLAGENTOID().getUSEROID().getPWD());
               return am;
            }

            //package level, mode, username and password defined
            PackLevelXPDLAppTAAppDetailUsrQuery packLevelXPDLAppToolAgentAppDetailUserQuery =
               new PackLevelXPDLAppTAAppDetailUsrQuery(dbTrans);
            packLevelXPDLAppToolAgentAppDetailUserQuery.setQueryXPDL_APPOID(packLevelXPDLAppDO);
            packLevelXPDLAppToolAgentAppDetailUserQuery.requireUniqueInstance();
            PackLevelXPDLAppTAAppDetailUsrDO packLevelXPDLAppToolAgentAppDetailUserDO =
               packLevelXPDLAppToolAgentAppDetailUserQuery.getNextDO();

            if (packLevelXPDLAppToolAgentAppDetailUserDO != null){
               am = this.createApplicationMap();
               am.setApplicationDefinitionId(packLevelXPDLAppToolAgentAppDetailUserDO
                  .getXPDL_APPOID().getAPPLICATION_ID());
               am.setPackageId(packLevelXPDLAppToolAgentAppDetailUserDO
                   .getXPDL_APPOID().getPACKAGEOID().getPACKAGE_ID());
               am.setProcessDefinitionId(null);
               am.setToolAgentClassName(packLevelXPDLAppToolAgentAppDetailUserDO
                  .getTOOLAGENTOID().getTOOLAGENT_APPOID().getTOOLAGENT_APPOID().getTOOL_AGENT_NAME());
               am.setApplicationName(packLevelXPDLAppToolAgentAppDetailUserDO
                  .getTOOLAGENTOID().getTOOLAGENT_APPOID().getTOOLAGENT_APPOID().getAPP_NAME());
               am.setApplicationMode(new Integer(packLevelXPDLAppToolAgentAppDetailUserDO
                  .getTOOLAGENTOID().getTOOLAGENT_APPOID().getAPP_MODE().intValue()));
               am.setUsername(packLevelXPDLAppToolAgentAppDetailUserDO
                  .getTOOLAGENTOID().getUSEROID().getUSERNAME());
               am.setPassword(packLevelXPDLAppToolAgentAppDetailUserDO
                  .getTOOLAGENTOID().getUSEROID().getPWD());
               return am;
            }

         } else {

            XPDLApplicationPackageQuery packageQuery = new XPDLApplicationPackageQuery(dbTrans);
            packageQuery.setQueryPACKAGE_ID(packageId);
            packageQuery.requireUniqueInstance();
            XPDLApplicationPackageDO packageDO = packageQuery.getNextDO();

            XPDLApplicationProcessQuery processQuery = new XPDLApplicationProcessQuery(dbTrans);
            processQuery.setQueryPROCESS_ID(processDefinitionId);
            processQuery.setQueryPACKAGEOID(packageDO);
            processQuery.requireUniqueInstance();
            XPDLApplicationProcessDO processDO = processQuery.getNextDO();

            ProcLevelXPDLAppQuery procLevelXPDLAppQuery =
               new ProcLevelXPDLAppQuery(dbTrans);
            procLevelXPDLAppQuery.setQueryAPPLICATION_ID(applicationDefinitionId);
            procLevelXPDLAppQuery.setQueryPROCESSOID(processDO);
            procLevelXPDLAppQuery.requireUniqueInstance();
            ProcLevelXPDLAppDO procLevelXPDLAppDO = procLevelXPDLAppQuery.getNextDO();

            //process level, mode, username and password not defined
            ProcLevelXPDLAppToolAgentAppQuery procLevelXPDLAppToolAgentAppQuery =
               new ProcLevelXPDLAppToolAgentAppQuery(dbTrans);
            procLevelXPDLAppToolAgentAppQuery.setQueryXPDL_APPOID(procLevelXPDLAppDO);
            procLevelXPDLAppToolAgentAppQuery.requireUniqueInstance();
            ProcLevelXPDLAppToolAgentAppDO procLevelXPDLAppToolAgentAppDO =
               procLevelXPDLAppToolAgentAppQuery.getNextDO();

            if (procLevelXPDLAppToolAgentAppDO != null){
               am = this.createApplicationMap();
               am.setApplicationDefinitionId(procLevelXPDLAppToolAgentAppDO
                  .getXPDL_APPOID().getAPPLICATION_ID());
               am.setPackageId(procLevelXPDLAppToolAgentAppDO
                   .getXPDL_APPOID().getPROCESSOID().getPACKAGEOID().getPACKAGE_ID());
               am.setProcessDefinitionId(procLevelXPDLAppToolAgentAppDO
                   .getXPDL_APPOID().getPROCESSOID().getPROCESS_ID());
               am.setToolAgentClassName(procLevelXPDLAppToolAgentAppDO
                  .getTOOLAGENTOID().getTOOL_AGENT_NAME());
               am.setApplicationName(procLevelXPDLAppToolAgentAppDO
                  .getTOOLAGENTOID().getAPP_NAME());
               am.setApplicationMode(null);
               am.setUsername(null);
               am.setPassword(null);
               return am;
            }

            //process level, mode defined, username and password not defined
            ProcLevelXPDLAppTAAppDetailQuery procLevelXPDLAppToolAgentAppDetailQuery =
               new ProcLevelXPDLAppTAAppDetailQuery(dbTrans);
            procLevelXPDLAppToolAgentAppDetailQuery.setQueryXPDL_APPOID(procLevelXPDLAppDO);
            procLevelXPDLAppToolAgentAppDetailQuery.requireUniqueInstance();
            ProcLevelXPDLAppTAAppDetailDO procLevelXPDLAppToolAgentAppDetailDO =
               procLevelXPDLAppToolAgentAppDetailQuery.getNextDO();

            if (procLevelXPDLAppToolAgentAppDetailDO != null){
               am = this.createApplicationMap();
               am.setApplicationDefinitionId(procLevelXPDLAppToolAgentAppDetailDO
                  .getXPDL_APPOID().getAPPLICATION_ID());
               am.setPackageId(procLevelXPDLAppToolAgentAppDetailDO
                   .getXPDL_APPOID().getPROCESSOID().getPACKAGEOID().getPACKAGE_ID());
               am.setProcessDefinitionId(procLevelXPDLAppToolAgentAppDetailDO
                   .getXPDL_APPOID().getPROCESSOID().getPROCESS_ID());
               am.setToolAgentClassName(procLevelXPDLAppToolAgentAppDetailDO
                  .getTOOLAGENTOID().getTOOLAGENT_APPOID().getTOOL_AGENT_NAME());
               am.setApplicationName(procLevelXPDLAppToolAgentAppDetailDO
                  .getTOOLAGENTOID().getTOOLAGENT_APPOID().getAPP_NAME());
               am.setApplicationMode(new Integer(procLevelXPDLAppToolAgentAppDetailDO
                  .getTOOLAGENTOID().getAPP_MODE().intValue()));
               am.setUsername(null);
               am.setPassword(null);
               return am;
            }

           //process level, mode not defined, username and password defined
            ProcLevelXPDLAppTAAppUserQuery procLevelXPDLAppToolAgentAppUserQuery =
               new ProcLevelXPDLAppTAAppUserQuery(dbTrans);
            procLevelXPDLAppToolAgentAppUserQuery.setQueryXPDL_APPOID(procLevelXPDLAppDO);
            procLevelXPDLAppToolAgentAppUserQuery.requireUniqueInstance();
            ProcLevelXPDLAppTAAppUserDO procLevelXPDLAppToolAgentAppUserDO =
               procLevelXPDLAppToolAgentAppUserQuery.getNextDO();

            if (procLevelXPDLAppToolAgentAppUserDO != null){
               am = this.createApplicationMap();
               am.setApplicationDefinitionId(procLevelXPDLAppToolAgentAppUserDO
                  .getXPDL_APPOID().getAPPLICATION_ID());
               am.setPackageId(procLevelXPDLAppToolAgentAppUserDO
                   .getXPDL_APPOID().getPROCESSOID().getPACKAGEOID().getPACKAGE_ID());
               am.setProcessDefinitionId(procLevelXPDLAppToolAgentAppUserDO
                   .getXPDL_APPOID().getPROCESSOID().getPROCESS_ID());
               am.setToolAgentClassName(procLevelXPDLAppToolAgentAppUserDO
                  .getTOOLAGENTOID().getTOOLAGENT_APPOID().getTOOL_AGENT_NAME());
               am.setApplicationName(procLevelXPDLAppToolAgentAppUserDO
                  .getTOOLAGENTOID().getTOOLAGENT_APPOID().getAPP_NAME());
               am.setApplicationMode(null);
               am.setUsername(procLevelXPDLAppToolAgentAppUserDO
                  .getTOOLAGENTOID().getUSEROID().getUSERNAME());
               am.setPassword(procLevelXPDLAppToolAgentAppUserDO
                  .getTOOLAGENTOID().getUSEROID().getPWD());
               return am;
            }

            //process level, mode, username and password defined
            ProcLevelXPDLAppTAAppDetailUsrQuery procLevelXPDLAppToolAgentAppDetailUserQuery =
               new ProcLevelXPDLAppTAAppDetailUsrQuery(dbTrans);
            procLevelXPDLAppToolAgentAppDetailUserQuery.setQueryXPDL_APPOID(procLevelXPDLAppDO);
            procLevelXPDLAppToolAgentAppDetailUserQuery.requireUniqueInstance();
            ProcLevelXPDLAppTAAppDetailUsrDO procLevelXPDLAppToolAgentAppDetailUserDO =
               procLevelXPDLAppToolAgentAppDetailUserQuery.getNextDO();

            if (procLevelXPDLAppToolAgentAppDetailUserDO != null){
               am = this.createApplicationMap();
               am.setApplicationDefinitionId(procLevelXPDLAppToolAgentAppDetailUserDO
                  .getXPDL_APPOID().getAPPLICATION_ID());
               am.setPackageId(procLevelXPDLAppToolAgentAppDetailUserDO
                   .getXPDL_APPOID().getPROCESSOID().getPACKAGEOID().getPACKAGE_ID());
               am.setProcessDefinitionId(procLevelXPDLAppToolAgentAppDetailUserDO
                   .getXPDL_APPOID().getPROCESSOID().getPROCESS_ID());
               am.setToolAgentClassName(procLevelXPDLAppToolAgentAppDetailUserDO
                  .getTOOLAGENTOID().getTOOLAGENT_APPOID().getTOOLAGENT_APPOID().getTOOL_AGENT_NAME());
               am.setApplicationName(procLevelXPDLAppToolAgentAppDetailUserDO
                  .getTOOLAGENTOID().getTOOLAGENT_APPOID().getTOOLAGENT_APPOID().getAPP_NAME());
               am.setApplicationMode(new Integer(procLevelXPDLAppToolAgentAppDetailUserDO
                  .getTOOLAGENTOID().getTOOLAGENT_APPOID().getAPP_MODE().intValue()));
               am.setUsername(procLevelXPDLAppToolAgentAppDetailUserDO
                  .getTOOLAGENTOID().getUSEROID().getUSERNAME());
               am.setPassword(procLevelXPDLAppToolAgentAppDetailUserDO
                  .getTOOLAGENTOID().getUSEROID().getPWD());
               return am;
            }

         }
      }
      catch(Exception e) {
         throw new RootException(e);
      }
      return am;
   }

   /**
    * Method getDBTransaction create DODS DBTransaction.
    * @param    t                   ApplicationMappingTransaction
    * @return   a DBTransaction
    *
    */
   private DBTransaction getDBTransaction(ApplicationMappingTransaction t) {
      try {
         if( t instanceof DODSApplicationMappingTransaction )
            return ((DODSApplicationMappingTransaction)t).getDODSTransaction();
      }catch(Exception e) {
      }
      //error
      return null;
   }

   /**
    * Method getMapFromDO transfer DO object to application map object.
    * @param    amDO                an ApplicationMappingsDO
    * @return   an ApplicationMap
    * @exception   Exception
    *
    */
//  private ApplicationMap getMapFromDO(ApplicationMappingsDO amDO) throws Exception {
//      DODSApplicationMap am = (DODSApplicationMap)this.createApplicationMap();
//      try {

/*
         if( amDO.getAPP_MODE() != null )
            am.setApplicationMode( new Integer( amDO.getAPP_MODE().intValue() ) );
         else
            am.setApplicationMode(null);
         am.setApplicationName( amDO.getAPP_NAME() );
         am.setUsername( amDO.getUSERNAME() );
         am.setPassword( amDO.getTHE_PASSWORD() );
         am.setPackageId(amDO.getPACKAGE_ID());
         String pDefId = amDO.getPROCESS_DEFINITION_ID();
         if( pDefId.equalsIgnoreCase(DODSUtilities.NULL_VALUE_FOR_PROCID))
            pDefId = null;
         am.setProcessDefinitionId( pDefId );
         am.setToolAgentClassName( amDO.getTOOL_AGENT_NAME() );
         am.setApplicationDefinitionId( amDO.getAPPLICATION_DEFINITION_ID() );
*/

//      }catch(Exception e) {
//         throw e;
//      }
//      return am;
//   }

   // private ApplicationMappingsDO getDOfromMap(ApplicationMap am, ApplicationMappingsDO amDO) throws Exception {
   //    try {
   //       amDO.setAPP_MODE( new BigDecimal( am.getApplicationMode().intValue() ) );
   //       amDO.setAPP_NAME( am.getApplicationName() );
   //       amDO.setUSERNAME( am.getUsername() );
   //       amDO.setPASSWORD( am.getPassword() );
   //       amDO.setPACKAGE_ID( new BigDecimal( am.getPackageId().intValue() ));
   //       amDO.setPROCESS_DEFINITION_ID( new BigDecimal( am.getProcessDefinitionId().intValue() ) );
   //       amDO.setTOOL_AGENT_NAME( am.getToolAgentClassName() );
   //       amDO.setAPPLICATION_DEFINITION_ID( new BigDecimal( am.getApplicationDefinitionId().intValue() ));
   //    }catch(Exception e) {
   //       throw e;
   //    }
   // }


   /**
    * Checks validity of application mapping.
    * @param    am  an ApplicationMapping
    * @return   a boolean
    *
    */
   public boolean checkValidity (ApplicationMap am) {
      if (am==null ||
          am.getPackageId()==null || am.getPackageId().trim().equals("") ||
          am.getApplicationDefinitionId()==null || am.getApplicationDefinitionId().trim().equals("") ||
          am.getToolAgentClassName()==null || am.getToolAgentClassName().trim().equals("") ||
          am.getApplicationName()==null || am.getApplicationName().trim().equals("")) {
         return false;
      } else {
         return true;
      }
   }

   /**
    * Gets application mapping transaction.
    * @return   an ApplicationMappingTransaction
    * @exception   TransactionException
    *
    */
   public ApplicationMappingTransaction getApplicationMappingTransaction() throws TransactionException {
       try {
         return new DODSApplicationMappingTransaction(DODS.getDatabaseManager().createTransaction());
       } catch (Exception ex) {
         throw new TransactionException(ex);
       }
    }


    private ProcLevelXPDLAppDO checkProcLevelXPDLApp(
      ApplicationMappingTransaction trans,
      ApplicationMap am)
      throws RootException {
      try {
         DBTransaction dbTrans = this.getDBTransaction(trans);

         ProcLevelXPDLAppQuery procLevelXPDLAppQuery =
            new ProcLevelXPDLAppQuery(dbTrans);
         ProcLevelXPDLAppDO procLevelXPDLAppDO = null;

         XPDLApplicationProcessDO procDO = this.checkProcess(trans, am);

         procLevelXPDLAppQuery.setQueryAPPLICATION_ID(am.getApplicationDefinitionId());
         procLevelXPDLAppQuery.setQueryPROCESSOID(procDO);
         procLevelXPDLAppQuery.requireUniqueInstance();
         procLevelXPDLAppDO = procLevelXPDLAppQuery.getNextDO();
         if (procLevelXPDLAppDO == null) {
            procLevelXPDLAppDO = ProcLevelXPDLAppDO.createVirgin(dbTrans);
            procLevelXPDLAppDO.setAPPLICATION_ID(am.getApplicationDefinitionId());
            procLevelXPDLAppDO.setPROCESSOID(procDO);
            procLevelXPDLAppDO.save();
            dbTrans.write();
         }
         return procLevelXPDLAppDO;
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   private PackLevelXPDLAppDO checkPackLevelXPDLApp(
      ApplicationMappingTransaction trans,
      ApplicationMap am)
      throws RootException {
      try {
         DBTransaction dbTrans = this.getDBTransaction(trans);

         PackLevelXPDLAppQuery packLevelXPDLAppQuery =
            new PackLevelXPDLAppQuery(dbTrans);
         PackLevelXPDLAppDO packLevelXPDLAppDO = null;

         XPDLApplicationPackageDO packDO = this.checkPackage(trans, am);

         packLevelXPDLAppQuery.setQueryAPPLICATION_ID(am.getApplicationDefinitionId());
         packLevelXPDLAppQuery.setQueryPACKAGEOID(packDO);
         packLevelXPDLAppQuery.requireUniqueInstance();
         packLevelXPDLAppDO = packLevelXPDLAppQuery.getNextDO();
         if (packLevelXPDLAppDO == null) {
            packLevelXPDLAppDO = PackLevelXPDLAppDO.createVirgin(dbTrans);
            packLevelXPDLAppDO.setAPPLICATION_ID(am.getApplicationDefinitionId());
            packLevelXPDLAppDO.setPACKAGEOID(packDO);
            packLevelXPDLAppDO.save();
            dbTrans.write();
         }
         return packLevelXPDLAppDO;
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   private ToolAgentAppDetailUserDO checkToolAgentAppDetailUser (
      ApplicationMappingTransaction trans,
      ApplicationMap am)
      throws RootException {
      try {
         DBTransaction dbTrans = this.getDBTransaction(trans);

         ToolAgentAppDetailUserQuery toolAgentAppDetailUserQuery =
            new ToolAgentAppDetailUserQuery(dbTrans);
         ToolAgentAppDetailUserDO toolAgentAppDetailUserDO = null;

         ToolAgentAppDetailDO toolAgentAppDetailDO = this.checkToolAgentAppDetail(trans, am);
         ToolAgentUserDO userDO = this.checkUser(trans, am);

         toolAgentAppDetailUserQuery.setQueryTOOLAGENT_APPOID(toolAgentAppDetailDO);
         toolAgentAppDetailUserQuery.setQueryUSEROID(userDO);
         toolAgentAppDetailUserQuery.requireUniqueInstance();
         toolAgentAppDetailUserDO = toolAgentAppDetailUserQuery.getNextDO();
         if (toolAgentAppDetailUserDO == null) {
            toolAgentAppDetailUserDO = ToolAgentAppDetailUserDO.createVirgin(dbTrans);
            toolAgentAppDetailUserDO.setTOOLAGENT_APPOID(toolAgentAppDetailDO);
            toolAgentAppDetailUserDO.setUSEROID(userDO);
            toolAgentAppDetailUserDO.save();
            dbTrans.write();
         }
         return toolAgentAppDetailUserDO;
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   private ToolAgentAppDetailDO checkToolAgentAppDetail (
      ApplicationMappingTransaction trans,
      ApplicationMap am)
      throws RootException {
      try {
         DBTransaction dbTrans = this.getDBTransaction(trans);

         ToolAgentAppDetailQuery toolAgentAppDetailQuery =
            new ToolAgentAppDetailQuery(dbTrans);
         ToolAgentAppDetailDO toolAgentAppDetailDO = null;

         ToolAgentAppDO toolAgentAppDO = this.checkToolAgentApp(trans, am);

         toolAgentAppDetailQuery.setQueryTOOLAGENT_APPOID(toolAgentAppDO);
         toolAgentAppDetailQuery.setQueryAPP_MODE( new BigDecimal(am.getApplicationMode().intValue()));
         toolAgentAppDetailQuery.requireUniqueInstance();
         toolAgentAppDetailDO = toolAgentAppDetailQuery.getNextDO();
         if (toolAgentAppDetailDO == null) {
            toolAgentAppDetailDO = ToolAgentAppDetailDO.createVirgin(dbTrans);
            toolAgentAppDetailDO.setTOOLAGENT_APPOID(toolAgentAppDO);
            toolAgentAppDetailDO.setAPP_MODE( new BigDecimal(am.getApplicationMode().intValue()));
            toolAgentAppDetailDO.save();
            dbTrans.write();
         }
         return toolAgentAppDetailDO;
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   private ToolAgentAppUserDO checkToolAgentAppUser (
      ApplicationMappingTransaction trans,
      ApplicationMap am)
      throws RootException {
      try {
         DBTransaction dbTrans = this.getDBTransaction(trans);

         ToolAgentAppUserQuery toolAgentAppUserQuery =
            new ToolAgentAppUserQuery(dbTrans);
         ToolAgentAppUserDO toolAgentAppUserDO = null;

         ToolAgentAppDO toolAgentAppDO = this.checkToolAgentApp(trans, am);
         ToolAgentUserDO userDO = this.checkUser(trans, am);

         toolAgentAppUserQuery.setQueryTOOLAGENT_APPOID(toolAgentAppDO);
         toolAgentAppUserQuery.setQueryUSEROID(userDO);
         toolAgentAppUserQuery.requireUniqueInstance();
         toolAgentAppUserDO = toolAgentAppUserQuery.getNextDO();
         if (toolAgentAppUserDO == null) {
            toolAgentAppUserDO = ToolAgentAppUserDO.createVirgin(dbTrans);
            toolAgentAppUserDO.setTOOLAGENT_APPOID(toolAgentAppDO);
            toolAgentAppUserDO.setUSEROID(userDO);
            toolAgentAppUserDO.save();
            dbTrans.write();
         }
         return toolAgentAppUserDO;
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   private ToolAgentAppDO checkToolAgentApp (
      ApplicationMappingTransaction trans,
      ApplicationMap am)
      throws RootException {
      try {
         DBTransaction dbTrans = this.getDBTransaction(trans);

         ToolAgentAppQuery toolAgentAppQuery =
            new ToolAgentAppQuery(dbTrans);
         ToolAgentAppDO toolAgentAppDO = null;

         toolAgentAppQuery.setQueryTOOL_AGENT_NAME(am.getToolAgentClassName());
         toolAgentAppQuery.setQueryAPP_NAME(am.getApplicationName());
         toolAgentAppQuery.requireUniqueInstance();
         toolAgentAppDO = toolAgentAppQuery.getNextDO();
         if (toolAgentAppDO == null) {
            toolAgentAppDO = ToolAgentAppDO.createVirgin(dbTrans);
            toolAgentAppDO.setTOOL_AGENT_NAME(am.getToolAgentClassName());
            toolAgentAppDO.setAPP_NAME(am.getApplicationName());
            toolAgentAppDO.save();
            dbTrans.write();
         }
         return toolAgentAppDO;
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   private XPDLApplicationProcessDO checkProcess(ApplicationMappingTransaction trans, ApplicationMap am)
      throws RootException {
      try {
         DBTransaction dbTrans = this.getDBTransaction(trans);

         XPDLApplicationProcessDO processDO = null;

         String processID = am.getProcessDefinitionId();

         if (processID != null) {
            XPDLApplicationPackageQuery pckgQuery = new XPDLApplicationPackageQuery(dbTrans);
            XPDLApplicationPackageDO pckgDO = this.checkPackage(trans, am);

            XPDLApplicationProcessQuery procQuery = new XPDLApplicationProcessQuery(dbTrans);
            procQuery.setQueryPROCESS_ID(processID);
            procQuery.setQueryPACKAGEOID(pckgDO);
            procQuery.requireUniqueInstance();
            processDO = procQuery.getNextDO();
            if (processDO == null) {
               //insert new process
               processDO = XPDLApplicationProcessDO.createVirgin(dbTrans);
               processDO.setPROCESS_ID(am.getProcessDefinitionId());
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

   private XPDLApplicationPackageDO checkPackage(ApplicationMappingTransaction trans, ApplicationMap am)
      throws RootException {
      try {
         DBTransaction dbTrans = this.getDBTransaction(trans);

         XPDLApplicationPackageQuery packQuery = new XPDLApplicationPackageQuery(dbTrans);
         XPDLApplicationPackageDO packageDO = null;
         packQuery.setQueryPACKAGE_ID(am.getPackageId());
         packQuery.requireUniqueInstance();
         packageDO = packQuery.getNextDO();
         if (packageDO == null) {
            //insert new package
            packageDO = XPDLApplicationPackageDO.createVirgin(dbTrans);
            packageDO.setPACKAGE_ID(am.getPackageId());
            packageDO.save();
            dbTrans.write();
         }
         return packageDO;
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   private ToolAgentUserDO checkUser(ApplicationMappingTransaction trans, ApplicationMap am)
      throws RootException {
      try {
         DBTransaction dbTrans = this.getDBTransaction(trans);

         ToolAgentUserQuery userQuery = new ToolAgentUserQuery(dbTrans);
         ToolAgentUserDO userDO = null;

         userQuery.setQueryUSERNAME(am.getUsername());
         userQuery.setQueryPWD(am.getPassword());
         userQuery.requireUniqueInstance();
         userDO = userQuery.getNextDO();

         if (userDO == null) {
            userDO = ToolAgentUserDO.createVirgin(dbTrans);
            userDO.setUSERNAME(am.getUsername());
            userDO.setPWD(am.getPassword());
            userDO.save();
            dbTrans.write();
         }
         return userDO;
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

    private void deleteProcLevelXPDLAppToolAgentAppDetailUser(
      ApplicationMap am,
      DBTransaction dbTrans)
      throws RootException {
      try {
         String packageId = am.getPackageId();
         String processId = am.getProcessDefinitionId();
         String applicationId = am.getApplicationDefinitionId();

         String toolagent = am.getToolAgentClassName();
         String username = am.getUsername();
         String password = am.getPassword();
         String appName = am.getApplicationName();
         Integer appMode = am.getApplicationMode();

         // package
         XPDLApplicationPackageQuery packageQuery = new XPDLApplicationPackageQuery(dbTrans);
         packageQuery.setQueryPACKAGE_ID(packageId);
         packageQuery.requireUniqueInstance();
         XPDLApplicationPackageDO packageDO = packageQuery.getNextDO();

         // process
         XPDLApplicationProcessQuery processQuery = new XPDLApplicationProcessQuery(dbTrans);
         processQuery.setQueryPROCESS_ID(processId);
         processQuery.setQueryPACKAGEOID(packageDO);
         processQuery.requireUniqueInstance();
         XPDLApplicationProcessDO processDO = processQuery.getNextDO();

         // procLevelXPDLApp
         ProcLevelXPDLAppQuery procLevelXPDLAppQuery = new ProcLevelXPDLAppQuery(dbTrans);
         procLevelXPDLAppQuery.setQueryAPPLICATION_ID(applicationId);
         procLevelXPDLAppQuery.setQueryPROCESSOID(processDO);
         procLevelXPDLAppQuery.requireUniqueInstance();
         ProcLevelXPDLAppDO procLevelXPDLAppDO = procLevelXPDLAppQuery.getNextDO();

         // user
         ToolAgentUserQuery userQuery = new ToolAgentUserQuery(dbTrans);
         userQuery.setQueryUSERNAME(username);
         userQuery.setQueryPWD(password);
         userQuery.requireUniqueInstance();
         ToolAgentUserDO userDO = userQuery.getNextDO();

         // toolagentApp
         ToolAgentAppQuery toolAgentAppQuery = new ToolAgentAppQuery(dbTrans);
         toolAgentAppQuery.setQueryTOOL_AGENT_NAME(toolagent);
         toolAgentAppQuery.setQueryAPP_NAME(appName);
         toolAgentAppQuery.requireUniqueInstance();
         ToolAgentAppDO toolAgentAppDO = toolAgentAppQuery.getNextDO();

         // toolagentAppDetail
         ToolAgentAppDetailQuery toolAgentAppDetailQuery = new ToolAgentAppDetailQuery(dbTrans);
         toolAgentAppDetailQuery.setQueryAPP_MODE(new BigDecimal(appMode.intValue()));
         toolAgentAppDetailQuery.setQueryTOOLAGENT_APPOID(toolAgentAppDO);
         toolAgentAppDetailQuery.requireUniqueInstance();
         ToolAgentAppDetailDO toolAgentAppDetailDO = toolAgentAppDetailQuery.getNextDO();

         // toolAgentAppDetailUser
         ToolAgentAppDetailUserQuery toolAgentAppDetailUserQuery = new ToolAgentAppDetailUserQuery(dbTrans);
         toolAgentAppDetailUserQuery.setQueryTOOLAGENT_APPOID(toolAgentAppDetailDO);
         toolAgentAppDetailUserQuery.setQueryUSEROID(userDO);
         toolAgentAppDetailUserQuery.requireUniqueInstance();
         ToolAgentAppDetailUserDO toolAgentAppDetailUserDO = toolAgentAppDetailUserQuery.getNextDO();

         // ProcLevelXPDLAppToolAgentAppDetailUser
         ProcLevelXPDLAppTAAppDetailUsrQuery procLevelXPDLAppToolAgentAppDetailUserQuery =
            new ProcLevelXPDLAppTAAppDetailUsrQuery(dbTrans);
         procLevelXPDLAppToolAgentAppDetailUserQuery.setQueryXPDL_APPOID(procLevelXPDLAppDO);
         procLevelXPDLAppToolAgentAppDetailUserQuery.setQueryTOOLAGENTOID(toolAgentAppDetailUserDO);

         ProcLevelXPDLAppTAAppDetailUsrDO[] procLevelXPDLAppToolAgentAppDetailUserDOArr =
            procLevelXPDLAppToolAgentAppDetailUserQuery.getDOArray();
         for (int i = 0; i < procLevelXPDLAppToolAgentAppDetailUserDOArr.length; i++) {
            procLevelXPDLAppToolAgentAppDetailUserDOArr[i].delete();
         }
         dbTrans.write();

         //delete unnecessairly data
         // ToolAgentAppDetailUser
         this.deleteToolAgentAppDetailUser(toolAgentAppDetailUserDO, dbTrans);
         // ToolagentAppDetail
         this.deleteToolAgentAppDetail(toolAgentAppDetailDO, dbTrans);
         // ToolAgentApp
         this.deleteToolAgentApp(toolAgentAppDO, dbTrans);
         // user
         this.deleteUser(userDO, dbTrans);
         // procLevelXPDLApp
         this.deleteProcLevelXPDLApp(procLevelXPDLAppDO, dbTrans);
         // process
         this.deleteProcess(processDO, dbTrans);
         // package
         this.deletePackage(packageDO, dbTrans);
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   private void deleteProcLevelXPDLAppToolAgentAppDetail (
      ApplicationMap am,
      DBTransaction dbTrans)
      throws RootException {
      try {
         String packageId = am.getPackageId();
         String processId = am.getProcessDefinitionId();
         String applicationId = am.getApplicationDefinitionId();

         String toolagent = am.getToolAgentClassName();
         String appName = am.getApplicationName();
         Integer appMode = am.getApplicationMode();

         // package
         XPDLApplicationPackageQuery packageQuery = new XPDLApplicationPackageQuery(dbTrans);
         packageQuery.setQueryPACKAGE_ID(packageId);
         packageQuery.requireUniqueInstance();
         XPDLApplicationPackageDO packageDO = packageQuery.getNextDO();

         // process
         XPDLApplicationProcessQuery processQuery = new XPDLApplicationProcessQuery(dbTrans);
         processQuery.setQueryPROCESS_ID(processId);
         processQuery.setQueryPACKAGEOID(packageDO);
         processQuery.requireUniqueInstance();
         XPDLApplicationProcessDO processDO = processQuery.getNextDO();

         // procLevelXPDLApp
         ProcLevelXPDLAppQuery procLevelXPDLAppQuery = new ProcLevelXPDLAppQuery(dbTrans);
         procLevelXPDLAppQuery.setQueryAPPLICATION_ID(applicationId);
         procLevelXPDLAppQuery.setQueryPROCESSOID(processDO);
         procLevelXPDLAppQuery.requireUniqueInstance();
         ProcLevelXPDLAppDO procLevelXPDLAppDO = procLevelXPDLAppQuery.getNextDO();

         // toolagentApp
         ToolAgentAppQuery toolAgentAppQuery = new ToolAgentAppQuery(dbTrans);
         toolAgentAppQuery.setQueryTOOL_AGENT_NAME(toolagent);
         toolAgentAppQuery.setQueryAPP_NAME(appName);
         toolAgentAppQuery.requireUniqueInstance();
         ToolAgentAppDO toolAgentAppDO = toolAgentAppQuery.getNextDO();

         // toolAgentAppDetail
         ToolAgentAppDetailQuery toolAgentAppDetailQuery = new ToolAgentAppDetailQuery(dbTrans);
         toolAgentAppDetailQuery.setQueryTOOLAGENT_APPOID(toolAgentAppDO);
         toolAgentAppDetailQuery.setQueryAPP_MODE( new BigDecimal(appMode.intValue()));
         toolAgentAppDetailQuery.requireUniqueInstance();
         ToolAgentAppDetailDO toolAgentAppDetailDO = toolAgentAppDetailQuery.getNextDO();

         // ProcLevelXPDLAppToolAgentAppDetail
         ProcLevelXPDLAppTAAppDetailQuery procLevelXPDLAppToolAgentAppDetailQuery =
            new ProcLevelXPDLAppTAAppDetailQuery(dbTrans);
         procLevelXPDLAppToolAgentAppDetailQuery.setQueryXPDL_APPOID(procLevelXPDLAppDO);
         procLevelXPDLAppToolAgentAppDetailQuery.setQueryTOOLAGENTOID(toolAgentAppDetailDO);

         ProcLevelXPDLAppTAAppDetailDO[] procLevelXPDLAppToolAgentAppDetailDOArr =
            procLevelXPDLAppToolAgentAppDetailQuery.getDOArray();
         for (int i = 0; i < procLevelXPDLAppToolAgentAppDetailDOArr.length; i++) {
            procLevelXPDLAppToolAgentAppDetailDOArr[i].delete();
         }
         dbTrans.write();

         //delete unnecessairly data
         // ToolagentAppDetail
         this.deleteToolAgentAppDetail(toolAgentAppDetailDO, dbTrans);
         // ToolAgentApp
         this.deleteToolAgentApp(toolAgentAppDO, dbTrans);
         // procLevelXPDLApp
            this.deleteProcLevelXPDLApp(procLevelXPDLAppDO, dbTrans);
         // process
            this.deleteProcess(processDO, dbTrans);
         // package
            this.deletePackage(packageDO, dbTrans);
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   private void deleteProcLevelXPDLAppToolAgentAppUser(
      ApplicationMap am,
      DBTransaction dbTrans)
      throws RootException {
      try {
         String packageId = am.getPackageId();
         String processId = am.getProcessDefinitionId();
         String applicationId = am.getApplicationDefinitionId();

         String toolagent = am.getToolAgentClassName();
         String username = am.getUsername();
         String password = am.getPassword();
         String appName = am.getApplicationName();

         // package
         XPDLApplicationPackageQuery packageQuery = new XPDLApplicationPackageQuery(dbTrans);
         packageQuery.setQueryPACKAGE_ID(packageId);
         packageQuery.requireUniqueInstance();
         XPDLApplicationPackageDO packageDO = packageQuery.getNextDO();

         // process
         XPDLApplicationProcessQuery processQuery = new XPDLApplicationProcessQuery(dbTrans);
         processQuery.setQueryPROCESS_ID(processId);
         processQuery.setQueryPACKAGEOID(packageDO);
         processQuery.requireUniqueInstance();
         XPDLApplicationProcessDO processDO = processQuery.getNextDO();

         // procLevelXPDLApp
         ProcLevelXPDLAppQuery procLevelXPDLAppQuery = new ProcLevelXPDLAppQuery(dbTrans);
         procLevelXPDLAppQuery.setQueryAPPLICATION_ID(applicationId);
         procLevelXPDLAppQuery.setQueryPROCESSOID(processDO);
         procLevelXPDLAppQuery.requireUniqueInstance();
         ProcLevelXPDLAppDO procLevelXPDLAppDO = procLevelXPDLAppQuery.getNextDO();

         // user
         ToolAgentUserQuery userQuery = new ToolAgentUserQuery(dbTrans);
         userQuery.setQueryUSERNAME(username);
         userQuery.setQueryPWD(password);
         userQuery.requireUniqueInstance();
         ToolAgentUserDO userDO = userQuery.getNextDO();

         // toolagentApp
         ToolAgentAppQuery toolAgentAppQuery = new ToolAgentAppQuery(dbTrans);
         toolAgentAppQuery.setQueryTOOL_AGENT_NAME(toolagent);
         toolAgentAppQuery.setQueryAPP_NAME(appName);
         toolAgentAppQuery.requireUniqueInstance();
         ToolAgentAppDO toolAgentAppDO = toolAgentAppQuery.getNextDO();

         // toolAgentAppUser
         ToolAgentAppUserQuery toolAgentAppUserQuery = new ToolAgentAppUserQuery(dbTrans);
         toolAgentAppUserQuery.setQueryTOOLAGENT_APPOID(toolAgentAppDO);
         toolAgentAppUserQuery.setQueryUSEROID(userDO);
         toolAgentAppUserQuery.requireUniqueInstance();
         ToolAgentAppUserDO toolAgentAppUserDO = toolAgentAppUserQuery.getNextDO();

         // ProcLevelXPDLAppToolAgentAppUser
         ProcLevelXPDLAppTAAppUserQuery procLevelXPDLAppToolAgentAppUserQuery =
            new ProcLevelXPDLAppTAAppUserQuery(dbTrans);
         procLevelXPDLAppToolAgentAppUserQuery.setQueryXPDL_APPOID(procLevelXPDLAppDO);
         procLevelXPDLAppToolAgentAppUserQuery.setQueryTOOLAGENTOID(toolAgentAppUserDO);

         ProcLevelXPDLAppTAAppUserDO[] procLevelXPDLAppToolAgentAppUserDOArr =
            procLevelXPDLAppToolAgentAppUserQuery.getDOArray();
         for (int i = 0; i < procLevelXPDLAppToolAgentAppUserDOArr.length; i++) {
            procLevelXPDLAppToolAgentAppUserDOArr[i].delete();
         }
         dbTrans.write();

         //delete unnecessairly data
         // ToolAgentAppUser
         this.deleteToolAgentAppUser(toolAgentAppUserDO, dbTrans);
         // ToolAgentApp
         this.deleteToolAgentApp(toolAgentAppDO, dbTrans);
         // user
         this.deleteUser(userDO, dbTrans);
         // procLevelXPDLApp
         this.deleteProcLevelXPDLApp(procLevelXPDLAppDO, dbTrans);
         // process
         this.deleteProcess(processDO, dbTrans);
         // package
         this.deletePackage(packageDO, dbTrans);
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   private void deleteProcLevelXPDLAppToolAgentApp(
      ApplicationMap am,
      DBTransaction dbTrans)
      throws RootException {
      try {
         String packageId = am.getPackageId();
         String processId = am.getProcessDefinitionId();
         String applicationId = am.getApplicationDefinitionId();

         String toolagent = am.getToolAgentClassName();
         String appName = am.getApplicationName();

         // package
         XPDLApplicationPackageQuery packageQuery = new XPDLApplicationPackageQuery(dbTrans);
         packageQuery.setQueryPACKAGE_ID(packageId);
         packageQuery.requireUniqueInstance();
         XPDLApplicationPackageDO packageDO = packageQuery.getNextDO();

         // process
         XPDLApplicationProcessQuery processQuery = new XPDLApplicationProcessQuery(dbTrans);
         processQuery.setQueryPROCESS_ID(processId);
         processQuery.setQueryPACKAGEOID(packageDO);
         processQuery.requireUniqueInstance();
         XPDLApplicationProcessDO processDO = processQuery.getNextDO();

         // procLevelXPDLApp
         ProcLevelXPDLAppQuery procLevelXPDLAppQuery = new ProcLevelXPDLAppQuery(dbTrans);
         procLevelXPDLAppQuery.setQueryAPPLICATION_ID(applicationId);
         procLevelXPDLAppQuery.setQueryPROCESSOID(processDO);
         procLevelXPDLAppQuery.requireUniqueInstance();
         ProcLevelXPDLAppDO procLevelXPDLAppDO = procLevelXPDLAppQuery.getNextDO();

         // toolagentApp
         ToolAgentAppQuery toolAgentAppQuery = new ToolAgentAppQuery(dbTrans);
         toolAgentAppQuery.setQueryTOOL_AGENT_NAME(toolagent);
         toolAgentAppQuery.setQueryAPP_NAME(appName);
         toolAgentAppQuery.requireUniqueInstance();
         ToolAgentAppDO toolAgentAppDO = toolAgentAppQuery.getNextDO();

         // ProcLevelXPDLAppToolAgentApp
         ProcLevelXPDLAppToolAgentAppQuery procLevelXPDLAppToolAgentAppQuery =
            new ProcLevelXPDLAppToolAgentAppQuery(dbTrans);
         procLevelXPDLAppToolAgentAppQuery.setQueryXPDL_APPOID(procLevelXPDLAppDO);
         procLevelXPDLAppToolAgentAppQuery.setQueryTOOLAGENTOID(toolAgentAppDO);

         ProcLevelXPDLAppToolAgentAppDO[] procLevelXPDLAppToolAgentAppDOArr =
            procLevelXPDLAppToolAgentAppQuery.getDOArray();
         for (int i = 0; i < procLevelXPDLAppToolAgentAppDOArr.length; i++) {
            procLevelXPDLAppToolAgentAppDOArr[i].delete();
         }
         dbTrans.write();

         //delete unnecessairly data
         // ToolAgentApp
         this.deleteToolAgentApp(toolAgentAppDO, dbTrans);
         // procLevelXPDLApp
         this.deleteProcLevelXPDLApp(procLevelXPDLAppDO, dbTrans);
         // process
         this.deleteProcess(processDO, dbTrans);
         // package
         this.deletePackage(packageDO, dbTrans);
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   private void deletePackLevelXPDLAppToolAgentAppDetailUser(
      ApplicationMap am,
      DBTransaction dbTrans)
      throws RootException {
      try {
         String packageId = am.getPackageId();
         String applicationId = am.getApplicationDefinitionId();

         String toolagent = am.getToolAgentClassName();
         String username = am.getUsername();
         String password = am.getPassword();
         String appName = am.getApplicationName();
         Integer appMode = am.getApplicationMode();

         // package
         XPDLApplicationPackageQuery packageQuery = new XPDLApplicationPackageQuery(dbTrans);
         packageQuery.setQueryPACKAGE_ID(packageId);
         packageQuery.requireUniqueInstance();
         XPDLApplicationPackageDO packageDO = packageQuery.getNextDO();

         // packLevelXPDLApp
         PackLevelXPDLAppQuery packLevelXPDLAppQuery = new PackLevelXPDLAppQuery(dbTrans);
         packLevelXPDLAppQuery.setQueryAPPLICATION_ID(applicationId);
         packLevelXPDLAppQuery.setQueryPACKAGEOID(packageDO);
         packLevelXPDLAppQuery.requireUniqueInstance();
         PackLevelXPDLAppDO packLevelXPDLAppDO = packLevelXPDLAppQuery.getNextDO();

         // user
         ToolAgentUserQuery userQuery = new ToolAgentUserQuery(dbTrans);
         userQuery.setQueryUSERNAME(username);
         userQuery.setQueryPWD(password);
         userQuery.requireUniqueInstance();
         ToolAgentUserDO userDO = userQuery.getNextDO();

         // toolagentApp
         ToolAgentAppQuery toolAgentAppQuery = new ToolAgentAppQuery(dbTrans);
         toolAgentAppQuery.setQueryTOOL_AGENT_NAME(toolagent);
         toolAgentAppQuery.setQueryAPP_NAME(appName);
         toolAgentAppQuery.requireUniqueInstance();
         ToolAgentAppDO toolAgentAppDO = toolAgentAppQuery.getNextDO();

         // toolagentAppDetail
         ToolAgentAppDetailQuery toolAgentAppDetailQuery = new ToolAgentAppDetailQuery(dbTrans);
         toolAgentAppDetailQuery.setQueryAPP_MODE( new BigDecimal(appMode.intValue()));
         toolAgentAppDetailQuery.setQueryTOOLAGENT_APPOID(toolAgentAppDO);
         toolAgentAppDetailQuery.requireUniqueInstance();
         ToolAgentAppDetailDO toolAgentAppDetailDO = toolAgentAppDetailQuery.getNextDO();

         // toolAgentAppDetailUser
         ToolAgentAppDetailUserQuery toolAgentAppDetailUserQuery = new ToolAgentAppDetailUserQuery(dbTrans);
         toolAgentAppDetailUserQuery.setQueryTOOLAGENT_APPOID(toolAgentAppDetailDO);
         toolAgentAppDetailUserQuery.setQueryUSEROID(userDO);
         toolAgentAppDetailUserQuery.requireUniqueInstance();
         ToolAgentAppDetailUserDO toolAgentAppDetailUserDO = toolAgentAppDetailUserQuery.getNextDO();

         // PackLevelXPDLAppToolAgentAppDetailUser
         PackLevelXPDLAppTAAppDetailUsrQuery packLevelXPDLAppToolAgentAppDetailUserQuery =
            new PackLevelXPDLAppTAAppDetailUsrQuery(dbTrans);
         packLevelXPDLAppToolAgentAppDetailUserQuery.setQueryXPDL_APPOID(packLevelXPDLAppDO);
         packLevelXPDLAppToolAgentAppDetailUserQuery.setQueryTOOLAGENTOID(toolAgentAppDetailUserDO);

         PackLevelXPDLAppTAAppDetailUsrDO[] packLevelXPDLAppToolAgentAppDetailUserDOArr =
            packLevelXPDLAppToolAgentAppDetailUserQuery.getDOArray();
         for (int i = 0; i < packLevelXPDLAppToolAgentAppDetailUserDOArr.length; i++) {
            packLevelXPDLAppToolAgentAppDetailUserDOArr[i].delete();
         }
         dbTrans.write();

         //delete unnecessairly data
         // ToolAgentAppDetailUser
         this.deleteToolAgentAppDetailUser(toolAgentAppDetailUserDO, dbTrans);
         // ToolagentAppDetail
         this.deleteToolAgentAppDetail(toolAgentAppDetailDO, dbTrans);
         // ToolAgentApp
         this.deleteToolAgentApp(toolAgentAppDO, dbTrans);
         // user
         this.deleteUser(userDO, dbTrans);
         // packLevelXPDLApp
         this.deletePackLevelXPDLApp(packLevelXPDLAppDO, dbTrans);
         // package
         this.deletePackage(packageDO, dbTrans);
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   private void deletePackLevelXPDLAppToolAgentAppDetail (
      ApplicationMap am,
      DBTransaction dbTrans)
      throws RootException {
      try {
         String packageId = am.getPackageId();
         String applicationId = am.getApplicationDefinitionId();

         String toolagent = am.getToolAgentClassName();
         String appName = am.getApplicationName();
         Integer appMode = am.getApplicationMode();

         // package
         XPDLApplicationPackageQuery packageQuery = new XPDLApplicationPackageQuery(dbTrans);
         packageQuery.setQueryPACKAGE_ID(packageId);
         packageQuery.requireUniqueInstance();
         XPDLApplicationPackageDO packageDO = packageQuery.getNextDO();

         // packLevelXPDLApp
         PackLevelXPDLAppQuery packLevelXPDLAppQuery = new PackLevelXPDLAppQuery(dbTrans);
         packLevelXPDLAppQuery.setQueryAPPLICATION_ID(applicationId);
         packLevelXPDLAppQuery.setQueryPACKAGEOID(packageDO);
         packLevelXPDLAppQuery.requireUniqueInstance();
         PackLevelXPDLAppDO packLevelXPDLAppDO = packLevelXPDLAppQuery.getNextDO();

         // toolagentApp
         ToolAgentAppQuery toolAgentAppQuery = new ToolAgentAppQuery(dbTrans);
         toolAgentAppQuery.setQueryTOOL_AGENT_NAME(toolagent);
         toolAgentAppQuery.setQueryAPP_NAME(appName);
         toolAgentAppQuery.requireUniqueInstance();
         ToolAgentAppDO toolAgentAppDO = toolAgentAppQuery.getNextDO();

         // toolAgentAppDetail
         ToolAgentAppDetailQuery toolAgentAppDetailQuery = new ToolAgentAppDetailQuery(dbTrans);
         toolAgentAppDetailQuery.setQueryTOOLAGENT_APPOID(toolAgentAppDO);
         toolAgentAppDetailQuery.setQueryAPP_MODE( new BigDecimal(appMode.intValue()));
         toolAgentAppDetailQuery.requireUniqueInstance();
         ToolAgentAppDetailDO toolAgentAppDetailDO = toolAgentAppDetailQuery.getNextDO();

         // ProcLevelXPDLAppToolAgentAppDetail
         PackLevelXPDLAppTAAppDetailQuery packLevelXPDLAppToolAgentAppDetailQuery =
            new PackLevelXPDLAppTAAppDetailQuery(dbTrans);
         packLevelXPDLAppToolAgentAppDetailQuery.setQueryXPDL_APPOID(packLevelXPDLAppDO);
         packLevelXPDLAppToolAgentAppDetailQuery.setQueryTOOLAGENTOID(toolAgentAppDetailDO);

         PackLevelXPDLAppTAAppDetailDO[] packLevelXPDLAppToolAgentAppDetailDOArr =
            packLevelXPDLAppToolAgentAppDetailQuery.getDOArray();
         for (int i = 0; i < packLevelXPDLAppToolAgentAppDetailDOArr.length; i++) {
            packLevelXPDLAppToolAgentAppDetailDOArr[i].delete();
         }
         dbTrans.write();

         //delete unnecessairly data
         // ToolAgentAppDetail
         this.deleteToolAgentAppDetail(toolAgentAppDetailDO, dbTrans);
         // ToolAgentApp
         this.deleteToolAgentApp(toolAgentAppDO, dbTrans);
         // packLevelXPDLApp
         this.deletePackLevelXPDLApp(packLevelXPDLAppDO, dbTrans);
         // package
         this.deletePackage(packageDO, dbTrans);
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   private void deletePackLevelXPDLAppToolAgentAppUser(
      ApplicationMap am,
      DBTransaction dbTrans)
      throws RootException {
      try {
         String packageId = am.getPackageId();
         String applicationId = am.getApplicationDefinitionId();

         String toolagent = am.getToolAgentClassName();
         String username = am.getUsername();
         String password = am.getPassword();
         String appName = am.getApplicationName();

         // package
         XPDLApplicationPackageQuery packageQuery = new XPDLApplicationPackageQuery(dbTrans);
         packageQuery.setQueryPACKAGE_ID(packageId);
         packageQuery.requireUniqueInstance();
         XPDLApplicationPackageDO packageDO = packageQuery.getNextDO();

         // packLevelXPDLApp
         PackLevelXPDLAppQuery packLevelXPDLAppQuery = new PackLevelXPDLAppQuery(dbTrans);
         packLevelXPDLAppQuery.setQueryAPPLICATION_ID(applicationId);
         packLevelXPDLAppQuery.setQueryPACKAGEOID(packageDO);
         packLevelXPDLAppQuery.requireUniqueInstance();
         PackLevelXPDLAppDO packLevelXPDLAppDO = packLevelXPDLAppQuery.getNextDO();

         // user
         ToolAgentUserQuery userQuery = new ToolAgentUserQuery(dbTrans);
         userQuery.setQueryUSERNAME(username);
         userQuery.setQueryPWD(password);
         userQuery.requireUniqueInstance();
         ToolAgentUserDO userDO = userQuery.getNextDO();

         // toolagentApp
         ToolAgentAppQuery toolAgentAppQuery = new ToolAgentAppQuery(dbTrans);
         toolAgentAppQuery.setQueryTOOL_AGENT_NAME(toolagent);
         toolAgentAppQuery.setQueryAPP_NAME(appName);
         toolAgentAppQuery.requireUniqueInstance();
         ToolAgentAppDO toolAgentAppDO = toolAgentAppQuery.getNextDO();

         // toolAgentAppUser
         ToolAgentAppUserQuery toolAgentAppUserQuery = new ToolAgentAppUserQuery(dbTrans);
         toolAgentAppUserQuery.setQueryTOOLAGENT_APPOID(toolAgentAppDO);
         toolAgentAppUserQuery.setQueryUSEROID(userDO);
         toolAgentAppUserQuery.requireUniqueInstance();
         ToolAgentAppUserDO toolAgentAppUserDO = toolAgentAppUserQuery.getNextDO();

         // ProcLevelXPDLAppToolAgentAppUser
         PackLevelXPDLAppTAAppUserQuery packLevelXPDLAppToolAgentAppUserQuery =
            new PackLevelXPDLAppTAAppUserQuery(dbTrans);
         packLevelXPDLAppToolAgentAppUserQuery.setQueryXPDL_APPOID(packLevelXPDLAppDO);
         packLevelXPDLAppToolAgentAppUserQuery.setQueryTOOLAGENTOID(toolAgentAppUserDO);

         PackLevelXPDLAppTAAppUserDO[] packLevelXPDLAppToolAgentAppUserDOArr =
            packLevelXPDLAppToolAgentAppUserQuery.getDOArray();
         for (int i = 0; i < packLevelXPDLAppToolAgentAppUserDOArr.length; i++) {
            packLevelXPDLAppToolAgentAppUserDOArr[i].delete();
         }
         dbTrans.write();

         //delete unnecessairly data
         // ToolAgentAppUser
         this.deleteToolAgentAppUser(toolAgentAppUserDO, dbTrans);
         // ToolAgentApp
         this.deleteToolAgentApp(toolAgentAppDO, dbTrans);
         // user
         this.deleteUser(userDO, dbTrans);
         // packLevelXPDLApp
         this.deletePackLevelXPDLApp(packLevelXPDLAppDO, dbTrans);
         // package
         this.deletePackage(packageDO, dbTrans);
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   private void deletePackLevelXPDLAppToolAgentApp(
      ApplicationMap am,
      DBTransaction dbTrans)
      throws RootException {
      try {
         String packageId = am.getPackageId();
         String applicationId = am.getApplicationDefinitionId();

         String toolagent = am.getToolAgentClassName();
         String appName = am.getApplicationName();

         // package
         XPDLApplicationPackageQuery packageQuery = new XPDLApplicationPackageQuery(dbTrans);
         packageQuery.setQueryPACKAGE_ID(packageId);
         packageQuery.requireUniqueInstance();
         XPDLApplicationPackageDO packageDO = packageQuery.getNextDO();

         // packLevelXPDLApp
         PackLevelXPDLAppQuery packLevelXPDLAppQuery = new PackLevelXPDLAppQuery(dbTrans);
         packLevelXPDLAppQuery.setQueryAPPLICATION_ID(applicationId);
         packLevelXPDLAppQuery.setQueryPACKAGEOID(packageDO);
         packLevelXPDLAppQuery.requireUniqueInstance();
         PackLevelXPDLAppDO packLevelXPDLAppDO = packLevelXPDLAppQuery.getNextDO();

         // toolagentApp
         ToolAgentAppQuery toolAgentAppQuery = new ToolAgentAppQuery(dbTrans);
         toolAgentAppQuery.setQueryTOOL_AGENT_NAME(toolagent);
         toolAgentAppQuery.setQueryAPP_NAME(appName);
         toolAgentAppQuery.requireUniqueInstance();
         ToolAgentAppDO toolAgentAppDO = toolAgentAppQuery.getNextDO();

         // PackLevelXPDLAppToolAgentApp
         PackLevelXPDLAppToolAgentAppQuery packLevelXPDLAppToolAgentAppQuery =
            new PackLevelXPDLAppToolAgentAppQuery(dbTrans);
         packLevelXPDLAppToolAgentAppQuery.setQueryXPDL_APPOID(packLevelXPDLAppDO);
         packLevelXPDLAppToolAgentAppQuery.setQueryTOOLAGENTOID(toolAgentAppDO);

         PackLevelXPDLAppToolAgentAppDO[] packLevelXPDLAppToolAgentAppDOArr =
            packLevelXPDLAppToolAgentAppQuery.getDOArray();
         for (int i = 0; i < packLevelXPDLAppToolAgentAppDOArr.length; i++) {
            packLevelXPDLAppToolAgentAppDOArr[i].delete();
         }
         dbTrans.write();

         //delete unnecessairly data
         // ToolAgentApp
         this.deleteToolAgentApp(toolAgentAppDO, dbTrans);
         // packLevelXPDLApp
         this.deletePackLevelXPDLApp(packLevelXPDLAppDO, dbTrans);
         // package
         this.deletePackage(packageDO, dbTrans);
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   private void deletePackage(XPDLApplicationPackageDO packageDO, DBTransaction dbTrans)
      throws RootException {
      try {
         if( packageDO == null )
            return;
         XPDLApplicationProcessQuery processQuery = new XPDLApplicationProcessQuery(dbTrans);
         processQuery.setQueryPACKAGEOID(packageDO);

         PackLevelXPDLAppQuery packLevelXPDLAppQuery =
            new PackLevelXPDLAppQuery(dbTrans);
         packLevelXPDLAppQuery.setQueryPACKAGEOID(packageDO);

         if (processQuery.getDOArray().length == 0
            && packLevelXPDLAppQuery.getDOArray().length == 0)
            packageDO.delete();
         dbTrans.write();
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   private void deleteProcess(XPDLApplicationProcessDO processDO, DBTransaction dbTrans)
      throws RootException {
      try {
         if( processDO == null )
            return;

         ProcLevelXPDLAppQuery procLevelXPDLAppQuery =
            new ProcLevelXPDLAppQuery(dbTrans);
         procLevelXPDLAppQuery.setQueryPROCESSOID(processDO);

         if (procLevelXPDLAppQuery.getDOArray().length == 0)
            processDO.delete();
         dbTrans.write();
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   private void deleteUser(ToolAgentUserDO userDO, DBTransaction dbTrans)
      throws RootException {
      try {
         if( userDO == null )
            return;

         ToolAgentAppUserQuery toolAgentAppUserQuery  =
            new ToolAgentAppUserQuery(dbTrans);
         toolAgentAppUserQuery.setQueryUSEROID(userDO);

         ToolAgentAppDetailUserQuery toolAgentAppDetailUserQuery =
            new ToolAgentAppDetailUserQuery (dbTrans);
         toolAgentAppDetailUserQuery.setQueryUSEROID(userDO);

         if (toolAgentAppUserQuery.getDOArray().length == 0
            && toolAgentAppDetailUserQuery.getDOArray().length == 0) {
            userDO.delete();
         }
         dbTrans.write();
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   private void deleteToolAgentApp(ToolAgentAppDO toolAgentAppDO, DBTransaction dbTrans)
      throws RootException {
      try {
         if( toolAgentAppDO == null )
            return;

         ToolAgentAppDetailQuery toolAgentAppDetailQuery  =
            new ToolAgentAppDetailQuery(dbTrans);
         toolAgentAppDetailQuery.setQueryTOOLAGENT_APPOID(toolAgentAppDO);

         ToolAgentAppUserQuery toolAgentAppUserQuery =
            new ToolAgentAppUserQuery (dbTrans);
         toolAgentAppUserQuery.setQueryTOOLAGENT_APPOID(toolAgentAppDO);

         PackLevelXPDLAppToolAgentAppQuery packLevelXPDLAppToolAgentAppQuery =
            new PackLevelXPDLAppToolAgentAppQuery (dbTrans);
         packLevelXPDLAppToolAgentAppQuery.setQueryTOOLAGENTOID(toolAgentAppDO);

         ProcLevelXPDLAppToolAgentAppQuery procLevelXPDLAppToolAgentAppQuery =
            new ProcLevelXPDLAppToolAgentAppQuery (dbTrans);
         procLevelXPDLAppToolAgentAppQuery.setQueryTOOLAGENTOID(toolAgentAppDO);

         if (toolAgentAppDetailQuery.getDOArray().length == 0
            && toolAgentAppUserQuery.getDOArray().length == 0
            && packLevelXPDLAppToolAgentAppQuery.getDOArray().length == 0
            && procLevelXPDLAppToolAgentAppQuery.getDOArray().length == 0) {
            toolAgentAppDO.delete();
         }
         dbTrans.write();
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   private void deleteToolAgentAppUser (ToolAgentAppUserDO toolAgentAppUserDO, DBTransaction dbTrans)
      throws RootException {
      try {
         if( toolAgentAppUserDO == null )
            return;

         PackLevelXPDLAppTAAppUserQuery packLevelXPDLAppToolAgentAppUserQuery =
            new PackLevelXPDLAppTAAppUserQuery (dbTrans);
         packLevelXPDLAppToolAgentAppUserQuery.setQueryTOOLAGENTOID(toolAgentAppUserDO);

         ProcLevelXPDLAppTAAppUserQuery procLevelXPDLAppToolAgentAppUserQuery =
            new ProcLevelXPDLAppTAAppUserQuery (dbTrans);
         procLevelXPDLAppToolAgentAppUserQuery.setQueryTOOLAGENTOID(toolAgentAppUserDO);

         if (packLevelXPDLAppToolAgentAppUserQuery.getDOArray().length == 0
            && procLevelXPDLAppToolAgentAppUserQuery.getDOArray().length == 0) {
            toolAgentAppUserDO.delete();
         }
         dbTrans.write();
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   private void deleteToolAgentAppDetail (ToolAgentAppDetailDO toolAgentAppDetailDO, DBTransaction dbTrans)
      throws RootException {
      try {
         if( toolAgentAppDetailDO == null )
            return;

         ToolAgentAppDetailUserQuery toolAgentAppDetailUserQuery =
            new ToolAgentAppDetailUserQuery (dbTrans);
         toolAgentAppDetailUserQuery.setQueryTOOLAGENT_APPOID(toolAgentAppDetailDO);

         PackLevelXPDLAppTAAppDetailQuery packLevelXPDLAppToolAgentAppDetailQuery =
            new PackLevelXPDLAppTAAppDetailQuery (dbTrans);
         packLevelXPDLAppToolAgentAppDetailQuery.setQueryTOOLAGENTOID(toolAgentAppDetailDO);

         ProcLevelXPDLAppTAAppDetailQuery procLevelXPDLAppToolAgentAppDetailQuery =
            new ProcLevelXPDLAppTAAppDetailQuery (dbTrans);
         procLevelXPDLAppToolAgentAppDetailQuery.setQueryTOOLAGENTOID(toolAgentAppDetailDO);

         if (toolAgentAppDetailUserQuery.getDOArray().length == 0
            && packLevelXPDLAppToolAgentAppDetailQuery.getDOArray().length == 0
            && procLevelXPDLAppToolAgentAppDetailQuery.getDOArray().length == 0) {
            toolAgentAppDetailDO.delete();
         }
         dbTrans.write();
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   private void deleteToolAgentAppDetailUser (ToolAgentAppDetailUserDO toolAgentAppDetailUserDO, DBTransaction dbTrans)
      throws RootException {
      try {
         if( toolAgentAppDetailUserDO == null )
            return;

         PackLevelXPDLAppTAAppDetailUsrQuery packLevelXPDLAppToolAgentAppDetailUserQuery =
            new PackLevelXPDLAppTAAppDetailUsrQuery (dbTrans);
         packLevelXPDLAppToolAgentAppDetailUserQuery.setQueryTOOLAGENTOID(toolAgentAppDetailUserDO);

         ProcLevelXPDLAppTAAppDetailUsrQuery procLevelXPDLAppToolAgentAppDetailUserQuery =
            new ProcLevelXPDLAppTAAppDetailUsrQuery (dbTrans);
         procLevelXPDLAppToolAgentAppDetailUserQuery.setQueryTOOLAGENTOID(toolAgentAppDetailUserDO);

         if (packLevelXPDLAppToolAgentAppDetailUserQuery.getDOArray().length == 0
            && procLevelXPDLAppToolAgentAppDetailUserQuery.getDOArray().length == 0) {
            toolAgentAppDetailUserDO.delete();
         }
         dbTrans.write();
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   private void deletePackLevelXPDLApp (PackLevelXPDLAppDO packLevelXPDLAppDO,
       DBTransaction dbTrans) throws RootException {
      try {
         if( packLevelXPDLAppDO == null )
            return;

         PackLevelXPDLAppToolAgentAppQuery packLevelXPDLAppToolAgentAppQuery =
            new PackLevelXPDLAppToolAgentAppQuery (dbTrans);
         packLevelXPDLAppToolAgentAppQuery.setQueryXPDL_APPOID(packLevelXPDLAppDO);

         PackLevelXPDLAppTAAppDetailQuery packLevelXPDLAppToolAgentAppDetailQuery =
            new PackLevelXPDLAppTAAppDetailQuery (dbTrans);
         packLevelXPDLAppToolAgentAppDetailQuery.setQueryXPDL_APPOID(packLevelXPDLAppDO);

         PackLevelXPDLAppTAAppUserQuery packLevelXPDLAppToolAgentAppUserQuery =
            new PackLevelXPDLAppTAAppUserQuery (dbTrans);
         packLevelXPDLAppToolAgentAppUserQuery.setQueryXPDL_APPOID(packLevelXPDLAppDO);

         PackLevelXPDLAppTAAppDetailUsrQuery packLevelXPDLAppToolAgentAppDetailUserQuery =
            new PackLevelXPDLAppTAAppDetailUsrQuery (dbTrans);
         packLevelXPDLAppToolAgentAppDetailUserQuery.setQueryXPDL_APPOID(packLevelXPDLAppDO);

         if (packLevelXPDLAppToolAgentAppQuery.getDOArray().length == 0
            && packLevelXPDLAppToolAgentAppDetailQuery.getDOArray().length == 0
            && packLevelXPDLAppToolAgentAppUserQuery.getDOArray().length == 0
            && packLevelXPDLAppToolAgentAppDetailUserQuery.getDOArray().length == 0) {
            packLevelXPDLAppDO.delete();
         }
         dbTrans.write();
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   private void deleteProcLevelXPDLApp(ProcLevelXPDLAppDO procLevelXPDLAppDO, DBTransaction dbTrans)
      throws RootException {
      try {
         if( procLevelXPDLAppDO == null )
            return;

         ProcLevelXPDLAppToolAgentAppQuery procLevelXPDLAppToolAgentAppQuery =
            new ProcLevelXPDLAppToolAgentAppQuery (dbTrans);
         procLevelXPDLAppToolAgentAppQuery.setQueryXPDL_APPOID(procLevelXPDLAppDO);

         ProcLevelXPDLAppTAAppDetailQuery procLevelXPDLAppToolAgentAppDetailQuery =
            new ProcLevelXPDLAppTAAppDetailQuery (dbTrans);
         procLevelXPDLAppToolAgentAppDetailQuery.setQueryXPDL_APPOID(procLevelXPDLAppDO);

         ProcLevelXPDLAppTAAppUserQuery procLevelXPDLAppToolAgentAppUserQuery =
            new ProcLevelXPDLAppTAAppUserQuery (dbTrans);
         procLevelXPDLAppToolAgentAppUserQuery.setQueryXPDL_APPOID(procLevelXPDLAppDO);

         ProcLevelXPDLAppTAAppDetailUsrQuery procLevelXPDLAppToolAgentAppDetailUserQuery =
            new ProcLevelXPDLAppTAAppDetailUsrQuery (dbTrans);
         procLevelXPDLAppToolAgentAppDetailUserQuery.setQueryXPDL_APPOID(procLevelXPDLAppDO);

         if (procLevelXPDLAppToolAgentAppQuery.getDOArray().length == 0
            && procLevelXPDLAppToolAgentAppDetailQuery.getDOArray().length == 0
            && procLevelXPDLAppToolAgentAppUserQuery.getDOArray().length == 0
            && procLevelXPDLAppToolAgentAppDetailUserQuery.getDOArray().length == 0) {
            procLevelXPDLAppDO.delete();
         }
         dbTrans.write();
      } catch (Exception e) {
         throw new RootException(e);
      }
   }
}
