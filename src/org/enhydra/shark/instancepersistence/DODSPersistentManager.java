package org.enhydra.shark.instancepersistence;

import java.math.BigDecimal;
import java.util.*;

import org.enhydra.dods.DODS;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.internal.instancepersistence.*;
import org.enhydra.shark.api.internal.working.CallbackUtilities;
import org.enhydra.shark.instancepersistence.data.*;
import org.enhydra.shark.transaction.SharkDODSTransaction;
import org.enhydra.shark.utilities.MiscUtilities;
import org.enhydra.shark.utilities.dods.DODSUtilities;

import com.lutris.appserver.server.sql.DBTransaction;
import com.lutris.dods.builder.generator.query.QueryBuilder;


/**
 * Used to save, restore or delete relevant engine objects from database, using
 * DODS persistent layer.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class DODSPersistentManager implements PersistentManagerInterface {

   protected static final String LOG_CHANNEL="Persistence";
   protected static final int RESOURCE_TYPE_OBJECT=0;
   protected static final int PROCESS_DEFINITION_TYPE_OBJECT=1;
   protected static final int PROCESS_TYPE_OBJECT=2;
   protected static final int ACTIVITY_TYPE_OBJECT=3;
   protected static final int ASSIGNMENT_TYPE_OBJECT=4;

   protected static final String[] activityAndProcessStates={"open.running",
         "open.not_running.not_started","open.not_running.suspended",
         "closed.completed", "closed.terminated", "closed.aborted"};


   protected static final short DB_TYPE_BOOLEAN=0;
   protected static final short DB_TYPE_LONG=1;
   protected static final short DB_TYPE_DOUBLE=2;
   protected static final short DB_TYPE_VCHAR=3;
   protected static final short DB_TYPE_DATE=4;
   protected static final short DB_TYPE_BLOB=5;

   protected static final int MAX_VCHAR_SIZE_LIMIT=4000;

   protected static int max_vchar_size=4000;

   protected CallbackUtilities cus;
   protected boolean deleteFinishedProcesses=false;
   protected Map _prStates;
   protected Map _acStates;

   protected List _prClosedStatesBigDecimals;
   protected List _actClosedStatesBigDecimals;
   protected List _actOpenStatesBigDecimals;

   protected boolean usingStandardVariableDataModel=true;
   
   public void configure (CallbackUtilities cus) throws RootException {
      this.cus=cus;
      String del=cus.getProperty("DODSPersistentManager.deleteFinishedProcesses","false");
      deleteFinishedProcesses=new Boolean(del).booleanValue();
      String shtd=cus.getProperty("DatabaseManager.DB.sharkdb.Connection.ShutDownString","");
      String mvc=cus.getProperty("DODSPersistentManager.maxVARCHARSize","4000");
      try {
         max_vchar_size=Integer.parseInt(mvc);
         if (max_vchar_size>MAX_VCHAR_SIZE_LIMIT || max_vchar_size<1) {
            max_vchar_size=MAX_VCHAR_SIZE_LIMIT;
            cus.warn("Invalid value "+mvc+" for property DODSPersistentManager.maxVARCHARSize. Using default value "+max_vchar_size);
         }
      } catch (Exception e) {
         cus.warn("Invalid value "+mvc+" for property DODSPersistentManager.maxVARCHARSize. Using default value "+max_vchar_size);
      }
      
      if (shtd.equals("SHUTDOWN")) {
         cus.info(LOG_CHANNEL,"DODSPersistentManager -> Adding shutdown hook for HSQL DB");
         Runtime.getRuntime().addShutdownHook(
            new Thread() {
               public void run()  {
                  try {
                     org.enhydra.dods.DODS.shutdown();
                  } catch (Throwable thr) {
                     thr.printStackTrace();
                  }
               }
            }
         );
      }
      usingStandardVariableDataModel=new Boolean(cus.getProperty("DODSPersistentManager.useStandardVariableDataModel","true")).booleanValue();
      
      _prStates = new HashMap();
      _acStates = new HashMap();
      _prClosedStatesBigDecimals = new ArrayList();
      _actClosedStatesBigDecimals = new ArrayList();
      _actOpenStatesBigDecimals = new ArrayList();

      try {
         try {
            DODSUtilities.init(cus.getProperties());
         }
         catch (Throwable ex) {
            ex.printStackTrace();
         }
         initActivityAndProcessStatesTable();
         cus.info("DODSPersistentManager -> manager configured - working with DB "+cus.getProperty("DatabaseManager.DB.sharkdb.Connection.Url"));
         cus.info("DODSPersistentManager ->                    - using DB Driver "+cus.getProperty("DatabaseManager.DB.sharkdb.JdbcDriver"));
         cus.info("DODSPersistentManager ->                    - persisting String variables into BLOB for sizes greater than "+max_vchar_size);
      }
      catch (Throwable tr) {
         tr.printStackTrace();
         cus.error(LOG_CHANNEL,"Problem with registering database manager with DODS !", new RootException(tr));
         throw new RootException("Problem with registering database manager with DODS !",tr);
      }
   }

   /**
    * Fills the state table with possible activity and process states. For now,
    * the 'keyValue' and the 'name' attribute of state record has the same value,
    * but in the future, if the names of states changes, it will be very easy
    * to change this table entries without affecting other tables.
    * NOTE: When new names are introduced, the getPersistentXXX that use it
    * also has to be changed
    */
   protected void initActivityAndProcessStatesTable () throws PersistenceException {
      DBTransaction t=null;
      try {
         t = DODS.getDatabaseManager().createTransaction();
         for (int i=0; i<activityAndProcessStates.length; i++) {
            String state=activityAndProcessStates[i];
            ProcessStateDO psDO=getPersistedProcessStateObject(state,true,t);
            if (!psDO.isPersistent()) {
               psDO.setKeyValue(state);
            }
            psDO.setName(state);
            psDO.save(t);
            BigDecimal bd=psDO.get_OId().toBigDecimal();
            _prStates.put(state, bd);
            if (state.startsWith("closed.")) {
               _prClosedStatesBigDecimals.add(bd);
            }
            ActivityStateDO asDO=getPersistedActivityStateObject(state,true,t);
            if (!asDO.isPersistent()) {
               asDO.setKeyValue(state);
            }
            asDO.setName(state);
            asDO.save(t);

            BigDecimal bda=asDO.get_OId().toBigDecimal();
            _acStates.put(state, bda);
            if (state.startsWith("closed.")) {
               _actClosedStatesBigDecimals.add(bda);
            } else {
               _actOpenStatesBigDecimals.add(bda);
            }
         }
         t.commit();
      }
      catch (Throwable thr) {
         throw new PersistenceException(thr);
      }
      finally {
         try {
            t.release();
         } catch (Exception ex) {}
      }
   }

   public void shutdownDatabase () throws PersistenceException {
      try {
         DODS.shutdown();
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Database is not properly shudown !", new RootException(tr));
         throw new PersistenceException("Database is not properly shudown !",tr);
      }
   }

   public void persist (ProcessMgrPersistenceInterface pm,boolean isInitialPersistence,SharkTransaction ti) throws PersistenceException {
      try {
         ProcessDefinitionDO DO=null;
         if (isInitialPersistence) {
            DO=ProcessDefinitionDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         } else {
            DO=getPersistedObject(pm,ti);
         }
         // when these attributes are persisted - they never change
         if (isInitialPersistence) {
            DO.setName(pm.getName());
            DO.setPackageId(pm.getPackageId());
            DO.setProcessDefinitionId(pm.getProcessDefinitionId());
            DO.setProcessDefinitionVersion(pm.getVersion());
            DO.setProcessDefinitionCreated(pm.getCreated());
         }
         DO.setState(pm.getState());
         ((SharkDODSTransaction)ti).store(DO);
         //DO.save(((SharkDODSTransaction)ti).getDODSTransaction());
         //(((SharkDODSTransaction)ti).getDODSTransaction()).write();
         cus.info(LOG_CHANNEL,"ProcessDefinition[packageId="+pm.getPackageId()+",id="+pm.getProcessDefinitionId()+"] persisted");
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Persisting of ProcessDefinition "+pm.getProcessDefinitionId()+" failed");
         throw new PersistenceException("Persisting of ProcessDefinition "
                                           + pm.getProcessDefinitionId()
                                           +" failed", tr);
      }
   }

   public void persist (ProcessPersistenceInterface pr,boolean isInitialPersistence,SharkTransaction ti) throws PersistenceException {
      try {
         ProcessDO DO=null;
         if (isInitialPersistence) {
            DO=ProcessDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         } else {
            DO=getPersistedObject(pr,ti);
         }
         // when these attributes are persisted - they never change
         if (isInitialPersistence) {
            DO.setId(pr.getId());
            DO.setProcessDefinition(getPersistedProcessMgrObject(pr.getProcessMgrName(),ti));
            //ProcessRequesterDO rDO = getProcessRequester(pr.getId(),ti);
            ProcessRequesterDO rDO = ProcessRequesterDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
            rDO.setId(pr.getId());
            if (pr.getActivityRequesterId()!=null) {
               rDO.setActivityRequester(getPersistedActivityObject(pr.getActivityRequesterId(),ti));
            }
            rDO.setResourceRequester(getPersistedResourceObject(pr.getResourceRequesterId(),ti));
            DO.setPDefName(pr.getProcessMgrName());
            DO.setActivityRequesterId(pr.getActivityRequesterId());
            DO.setActivityRequesterProcessId(pr.getActivityRequestersProcessId());
            DO.setResourceRequesterId(pr.getResourceRequesterId());
            ((SharkDODSTransaction)ti).store(rDO);
         }
         DO.setExternalRequesterClassName(pr.getExternalRequesterClassName());

         //System.out.println("Going to persist process by filling DO "+DO);
         DO.setName(pr.getName());
         DO.setDescription(pr.getDescription());
         DO.setPriority(pr.getPriority());
         DO.oid_setState((BigDecimal)_prStates.get(pr.getState()));
         DO.setCreated(pr.getCreatedTime());
         DO.setStarted(pr.getStartedTime());
         DO.setLastStateTime(pr.getLastStateTime());
         DO.setLimitTime(pr.getLimitTime());
         ((SharkDODSTransaction)ti).store(DO);
         //if (DO.isDirty()) DO.save(((SharkDODSTransaction)ti).getDODSTransaction());
         //if (DO.isVirgin()) {(((SharkDODSTransaction)ti).getDODSTransaction()).write();}
         cus.info(LOG_CHANNEL,"Process[id="+pr.getId()+"] persisted");
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Persisting of Process "+pr.getId()+" failed");
         throw new PersistenceException("Persisting of Process "
                                           + pr.getId()
                                           +" failed", tr);
      }
   }

   public void persist (ActivityPersistenceInterface act,boolean isInitialPersistence,SharkTransaction ti) throws PersistenceException {
      try {
         //if (act.getActivityDefinitionId().equals("seeFirstRes")) System.out.println("HHH1");
         ActivityDO DO=null;
         if (isInitialPersistence) {
            DO=ActivityDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         } else {
            DO=getPersistedObject(act,ti);
         }
         //if (act.getActivityDefinitionId().equals("seeFirstRes")) System.out.println("HHH2");
         // when these attributes are persisted - they never change
         if (isInitialPersistence) {
            DO.setId(act.getId());
            DO.setActivitySetDefinitionId(act.getActivitySetDefinitionId());
            DO.setActivityDefinitionId(act.getActivityDefinitionId());
            DO.setPDefName(act.getProcessMgrName());
            DO.setProcess(getPersistedProcessObject(act.getProcessId(),ti));
            DO.setProcessId(act.getProcessId());
            String bActId=act.getBlockActivityId();
            if (bActId!=null) {
               //DO.setBlockActivity(getPersistedActivityObject(bActId,ti));
               DO.setBlockActivityId(bActId);
            }
         }
         DO.setPerformer(act.getSubflowProcessId());
         DO.setIsPerformerAsynchronous(act.isSubflowAsynchronous());
         DO.setName(act.getName());
         DO.setDescription(act.getDescription());
         DO.setPriority(act.getPriority());
         if (!isInitialPersistence && DO.getResourceId()!=act.getResourceUsername()) {
            DO.setTheResource(getPersistedResourceObject(act.getResourceUsername(),ti));
            DO.setResourceId(act.getResourceUsername());
         }  
         DO.oid_setState((BigDecimal)_acStates.get(act.getState()));
         DO.setLastStateTime(act.getLastStateTime());
         DO.setLimitTime(act.getLimitTime());
         DO.setAccepted(act.getAcceptedTime());
         DO.setActivated(act.getActivatedTime());

         ((SharkDODSTransaction)ti).store(DO);
         //if (DO.isDirty()) DO.save(((SharkDODSTransaction)ti).getDODSTransaction());
         //if (DO.isVirgin()) {(((SharkDODSTransaction)ti).getDODSTransaction()).write();}
         cus.info(LOG_CHANNEL,"Activity[id="+act.getId()+",definitionId="+act.getActivityDefinitionId()+"] persisted");
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Persisting of Activity "+act.getId()+" failed");
         throw new PersistenceException("Persisting of Activity "
                                           + act.getId()
                                           +" failed",tr);
      }
   }

   public void persist (ResourcePersistenceInterface res,boolean isInitialPersistence,SharkTransaction ti) throws PersistenceException {
      try {
         ResourceDO DO=null;
         if (isInitialPersistence) {
            DO=ResourceDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         } else {
            DO=getPersistedObject(res,ti);
         }
         // when this attribute is persisted - it is never changed
         if (isInitialPersistence) {
            DO.setUsername(res.getUsername());
            DO.setName(res.getName());
         }
         ((SharkDODSTransaction)ti).store(DO);
         //if (DO.isDirty()) DO.save(((SharkDODSTransaction)ti).getDODSTransaction());
         //if (DO.isVirgin()) {(((SharkDODSTransaction)ti).getDODSTransaction()).write();}
         cus.info(LOG_CHANNEL,"Resource[username="+res.getUsername()+"] persisted");
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Persisting of Resource "+res.getUsername()+" failed");
         throw new PersistenceException("Persisting of Resource "
                                           + res.getUsername()+" failed", tr);
      }
   }

   public void persist (AssignmentPersistenceInterface ass,boolean isInitialPersistence,SharkTransaction ti) throws PersistenceException {
      try {
         AssignmentDO DO=null;
         if (isInitialPersistence) {
            DO=AssignmentDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         } else {
            DO=getPersistedObject(ass,ti);
         }
//         System.out.println("IIP="+isInitialPersistence+", DO="+DO);
         // when this attribute is persisted - it is never changed
         if (isInitialPersistence) {
            DO.setActivity(getPersistedActivityObject(ass.getActivityId(),ti));
            DO.setCNT(getNextDecId("_assignment_"));
            DO.setActivityId(ass.getActivityId());            
            DO.setActivityProcessId(ass.getProcessId());
            DO.setActivityProcessDefName(ass.getProcessMgrName());
         }
         DO.setIsValid(ass.isValid());
         DO.setIsAccepted(ass.isAccepted());
         if (ass.getResourceUsername()!=DO.getResourceId()) {
            DO.setResourceId(ass.getResourceUsername());
            DO.setTheResource(getPersistedResourceObject(ass.getResourceUsername(),ti));
         }
         ((SharkDODSTransaction)ti).store(DO);
         //if (DO.isDirty()) DO.save(((SharkDODSTransaction)ti).getDODSTransaction());
         //if (DO.isVirgin()) {(((SharkDODSTransaction)ti).getDODSTransaction()).write();}
         cus.info(LOG_CHANNEL,"Assignment[activityId="+ass.getActivityId()+", username="+ass.getResourceUsername()+"] persisted");
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Persisting of  Assignment failed");
         throw new PersistenceException("Persisting of  Assignment failed", tr);
      }
   }

   public void persist (AssignmentPersistenceInterface ass,String oldResUname,SharkTransaction ti) throws PersistenceException {
      try {
         AssignmentDO DO=getPersistedAssignmentObject(ass.getActivityId(),oldResUname,ti);
         DO.setTheResource(getPersistedResourceObject(ass.getResourceUsername(),ti));
         DO.setResourceId(ass.getResourceUsername());
         DO.setIsValid(ass.isValid());
         DO.setIsAccepted(ass.isAccepted());
         ((SharkDODSTransaction)ti).store(DO);
         //if (DO.isDirty()) DO.save(((SharkDODSTransaction)ti).getDODSTransaction());
         //if (DO.isVirgin()) {(((SharkDODSTransaction)ti).getDODSTransaction()).write();}
         cus.info(LOG_CHANNEL,"Assignment[activityId="+ass.getActivityId()+", username="+ass.getResourceUsername()+"] persisted");
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Persisting of  Assignment failed");
         throw new PersistenceException("Persisting of  Assignment failed", tr);
      }
   }

   public void persist (ProcessVariablePersistenceInterface var,boolean isInitialPersistence,SharkTransaction ti) throws PersistenceException {
      if (usingStandardVariableDataModel) {
         persistVariablesBLOB(var, isInitialPersistence, ti);
      } else {
         persistVariablesWOB(var, isInitialPersistence, ti);
      }
   }
   
   protected void persistVariablesBLOB (ProcessVariablePersistenceInterface var,boolean isInitialPersistence,SharkTransaction ti) throws PersistenceException {
      try {
         ProcessDataDO DO=null;
         if (isInitialPersistence) {
            DO=ProcessDataDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         } else {
            DO=getPersistedObject(var,ti);
         }
         // when this attribute is persisted - it is never changed
         if (isInitialPersistence) {
            DO.setProcess(getPersistedProcessObject(var.getProcessId(),ti));
            DO.setVariableDefinitionId(var.getDefinitionId());
         }

         Object vv=var.getValue();
         boolean isBLOB=false;
         boolean wasBLOB=false;
         if (vv instanceof Boolean) {
            DO.setVariableValueBOOL(((Boolean)vv).booleanValue());
            DO.setVariableType(DODSPersistentManager.DB_TYPE_BOOLEAN);
         } else if (vv instanceof Long) {
            DO.setVariableValueLONG(((Long)vv).longValue());
            DO.setVariableType(DODSPersistentManager.DB_TYPE_LONG);
         } else if (vv instanceof String) {
            String sv=(String)vv;
            if (sv.length()<=max_vchar_size) {
               DO.setVariableValueVCHAR(sv);
               wasBLOB=true;
               DO.setVariableType(DODSPersistentManager.DB_TYPE_VCHAR);
            } else {
               DO.setVariableValueVCHAR(null);
               isBLOB=true;
            }
         } else if (vv instanceof Double) {
            DO.setVariableValueDBL(((Double)vv).doubleValue());
            DO.setVariableType(DODSPersistentManager.DB_TYPE_DOUBLE);
         } else if (vv instanceof java.util.Date) {
            DO.setVariableValueDATE(new java.sql.Timestamp(((java.util.Date)vv).getTime()));
            DO.setVariableType(DODSPersistentManager.DB_TYPE_DATE);
         } else if (vv==null) {
            short vt=DO.getVariableType();
            if (vt==DODSPersistentManager.DB_TYPE_DATE) {
               DO.setVariableValueDATE(null);
            } else if (vt==DODSPersistentManager.DB_TYPE_VCHAR) {
               DO.setVariableValueVCHAR(null);
            } else {
               DO.setVariableValue(null);
               DO.setVariableType(DODSPersistentManager.DB_TYPE_BLOB);
            }
         } else {
            isBLOB=true;
         }

         if (isBLOB) {
            DO.setVariableValue(MiscUtilities.serialize(vv));
            DO.setVariableType(DODSPersistentManager.DB_TYPE_BLOB);
         }
         if (wasBLOB) {
            DO.setVariableValue(null);
         }
         
         ((SharkDODSTransaction)ti).store(DO);
         //if (DO.isDirty()) DO.save(((SharkDODSTransaction)ti).getDODSTransaction());
         //if (DO.isVirgin()) {(((SharkDODSTransaction)ti).getDODSTransaction()).write();}
         cus.info(LOG_CHANNEL,"ProcessVariable[processId="+var.getProcessId()+", definitionId="+var.getDefinitionId()+"] persisted");
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Persisting of ProcessVariable failed");
         throw new PersistenceException("Persisting of ProcessVariable failed", tr);
      }      
   }
      
   protected void persistVariablesWOB (ProcessVariablePersistenceInterface var,boolean isInitialPersistence,SharkTransaction ti) throws PersistenceException {
      try {
         ProcessDataWOBDO DO=null;
         if (isInitialPersistence) {
            DO=ProcessDataWOBDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         } else {
            DO=getPersistedObject2(var,ti);
         }
         // when this attribute is persisted - it is never changed
         if (isInitialPersistence) {
            DO.setProcess(getPersistedProcessObject(var.getProcessId(),ti));
            DO.setVariableDefinitionId(var.getDefinitionId());
         }

         Object vv=var.getValue();
         boolean isBLOB=false;
         boolean wasBLOB=false;
         if (vv instanceof Boolean) {
            DO.setVariableValueBOOL(((Boolean)vv).booleanValue());
            DO.setVariableType(DODSPersistentManager.DB_TYPE_BOOLEAN);
         } else if (vv instanceof Long) {
            DO.setVariableValueLONG(((Long)vv).longValue());
            DO.setVariableType(DODSPersistentManager.DB_TYPE_LONG);
         } else if (vv instanceof String) {
            String sv=(String)vv;
            if (sv.length()<=max_vchar_size) {
               DO.setVariableValueVCHAR(sv);
               wasBLOB=true;
               DO.setVariableType(DODSPersistentManager.DB_TYPE_VCHAR);
            } else {
               DO.setVariableValueVCHAR(null);
               isBLOB=true;
            }
         } else if (vv instanceof Double) {
            DO.setVariableValueDBL(((Double)vv).doubleValue());
            DO.setVariableType(DODSPersistentManager.DB_TYPE_DOUBLE);
         } else if (vv instanceof java.util.Date) {
            DO.setVariableValueDATE(new java.sql.Timestamp(((java.util.Date)vv).getTime()));
            DO.setVariableType(DODSPersistentManager.DB_TYPE_DATE);
         } else if (vv==null) {
            short vt=DO.getVariableType();
            if (vt==DODSPersistentManager.DB_TYPE_DATE) {
               DO.setVariableValueDATE(null);
            } else if (vt==DODSPersistentManager.DB_TYPE_VCHAR) {
               DO.setVariableValueVCHAR(null);
            } else {
               ProcessDataBLOBDO bDO=null;
               if (isInitialPersistence) {
                  bDO=ProcessDataBLOBDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
               } else {
                  bDO=getPersistedObject(DO, ti);                  
               }
               bDO.setProcessDataWOB(DO);
               bDO.setVariableValue(null);
               DO.setVariableType(DODSPersistentManager.DB_TYPE_BLOB);
               ((SharkDODSTransaction)ti).store(bDO);               
            }
         } else {
            isBLOB=true;
         }

         if (isBLOB) {
            ProcessDataBLOBDO bDO=null;
            if (isInitialPersistence) {
               bDO=ProcessDataBLOBDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
            } else {
               bDO=getPersistedObject(DO, ti);                  
            }
            bDO.setProcessDataWOB(DO);
            bDO.setVariableValue(MiscUtilities.serialize(vv));
            DO.setVariableType(DODSPersistentManager.DB_TYPE_BLOB);
            ((SharkDODSTransaction)ti).store(bDO);
         }
//         if (wasBLOB) {
//            DO.setVariableValue(null);
//         }
         
         ((SharkDODSTransaction)ti).store(DO);
         //if (DO.isDirty()) DO.save(((SharkDODSTransaction)ti).getDODSTransaction());
         //if (DO.isVirgin()) {(((SharkDODSTransaction)ti).getDODSTransaction()).write();}
         cus.info(LOG_CHANNEL,"ProcessVariable[processId="+var.getProcessId()+", definitionId="+var.getDefinitionId()+"] persisted");
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Persisting of ProcessVariable failed");
         throw new PersistenceException("Persisting of ProcessVariable failed", tr);
      }      
   }

   public void persist (ActivityVariablePersistenceInterface var,boolean isInitialPersistence,SharkTransaction ti) throws PersistenceException {
      if (usingStandardVariableDataModel) {
         persistVariablesBLOB(var, isInitialPersistence, ti);
      } else {
         persistVariablesWOB(var, isInitialPersistence, ti);         
      }
   }

   public void persistVariablesBLOB (ActivityVariablePersistenceInterface var,boolean isInitialPersistence,SharkTransaction ti) throws PersistenceException {
      try {
         ActivityDataDO DO=null;
         if (isInitialPersistence) {
            DO=ActivityDataDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         } else {
            DO=getPersistedObject(var,ti);
         }
         // when this attribute is persisted - it is never changed
         if (isInitialPersistence) {
            DO.setActivity(getPersistedActivityObject(var.getActivityId(),ti));
            DO.setVariableDefinitionId(var.getDefinitionId());
         }

         Object vv=var.getValue();
         boolean isBLOB=false;
         boolean wasBLOB=false;
         if (vv instanceof Boolean) {
            DO.setVariableValueBOOL(((Boolean)vv).booleanValue());
            DO.setVariableType(DODSPersistentManager.DB_TYPE_BOOLEAN);
         } else if (vv instanceof Long) {
            DO.setVariableValueLONG(((Long)vv).longValue());
            DO.setVariableType(DODSPersistentManager.DB_TYPE_LONG);
         } else if (vv instanceof String) {
            String sv=(String)vv;
            if (sv.length()<=max_vchar_size) {
               DO.setVariableValueVCHAR(sv);
               wasBLOB=true;
               DO.setVariableType(DODSPersistentManager.DB_TYPE_VCHAR);
            } else {
               DO.setVariableValueVCHAR(null);
               isBLOB=true;
            }
         } else if (vv instanceof Double) {
            DO.setVariableValueDBL(((Double)vv).doubleValue());
            DO.setVariableType(DODSPersistentManager.DB_TYPE_DOUBLE);
         } else if (vv instanceof java.util.Date) {
            DO.setVariableValueDATE(new java.sql.Timestamp(((java.util.Date)vv).getTime()));
            DO.setVariableType(DODSPersistentManager.DB_TYPE_DATE);
         } else if (vv==null) {
            short vt=DO.getVariableType();
            if (vt==DODSPersistentManager.DB_TYPE_DATE) {
               DO.setVariableValueDATE(null);
            } else if (vt==DODSPersistentManager.DB_TYPE_VCHAR) {
               DO.setVariableValueVCHAR(null);
            } else {
               DO.setVariableValue(null);
               DO.setVariableType(DODSPersistentManager.DB_TYPE_BLOB);
            }
         } else {
            isBLOB=true;
         }

         if (isBLOB) {
            DO.setVariableValue(MiscUtilities.serialize(vv));
            DO.setVariableType(DODSPersistentManager.DB_TYPE_BLOB);
         }
         if (wasBLOB) {
            DO.setVariableValue(null);
         }

         DO.setIsResult(var.isResultVariable());
         ((SharkDODSTransaction)ti).store(DO);
         //if (DO.isDirty()) DO.save(((SharkDODSTransaction)ti).getDODSTransaction());
         //if (DO.isVirgin()) {(((SharkDODSTransaction)ti).getDODSTransaction()).write();}
         cus.info(LOG_CHANNEL,"ActivityVariable[activityId="+var.getActivityId()+", definitionId="+var.getDefinitionId()+"] persisted");
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Persisting of ActivityVariable failed");
         throw new PersistenceException("Persisting of ActivityVariable failed", tr);
      }
   }   
   
   public void persistVariablesWOB (ActivityVariablePersistenceInterface var,boolean isInitialPersistence,SharkTransaction ti) throws PersistenceException {
      try {
         ActivityDataWOBDO DO=null;
         if (isInitialPersistence) {
            DO=ActivityDataWOBDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         } else {
            DO=getPersistedObject2(var,ti);
         }
         // when this attribute is persisted - it is never changed
         if (isInitialPersistence) {
            DO.setActivity(getPersistedActivityObject(var.getActivityId(),ti));
            DO.setVariableDefinitionId(var.getDefinitionId());
         }

         Object vv=var.getValue();
         boolean isBLOB=false;
         boolean wasBLOB=false;
         if (vv instanceof Boolean) {
            DO.setVariableValueBOOL(((Boolean)vv).booleanValue());
            DO.setVariableType(DODSPersistentManager.DB_TYPE_BOOLEAN);
         } else if (vv instanceof Long) {
            DO.setVariableValueLONG(((Long)vv).longValue());
            DO.setVariableType(DODSPersistentManager.DB_TYPE_LONG);
         } else if (vv instanceof String) {
            String sv=(String)vv;
            if (sv.length()<=max_vchar_size) {
               DO.setVariableValueVCHAR(sv);
               wasBLOB=true;
               DO.setVariableType(DODSPersistentManager.DB_TYPE_VCHAR);
            } else {
               DO.setVariableValueVCHAR(null);
               isBLOB=true;
            }
         } else if (vv instanceof Double) {
            DO.setVariableValueDBL(((Double)vv).doubleValue());
            DO.setVariableType(DODSPersistentManager.DB_TYPE_DOUBLE);
         } else if (vv instanceof java.util.Date) {
            DO.setVariableValueDATE(new java.sql.Timestamp(((java.util.Date)vv).getTime()));
            DO.setVariableType(DODSPersistentManager.DB_TYPE_DATE);
         } else if (vv==null) {
            short vt=DO.getVariableType();
            if (vt==DODSPersistentManager.DB_TYPE_DATE) {
               DO.setVariableValueDATE(null);
            } else if (vt==DODSPersistentManager.DB_TYPE_VCHAR) {
               DO.setVariableValueVCHAR(null);
            } else {
               ActivityDataBLOBDO bDO=null;
               if (isInitialPersistence) {
                  bDO=ActivityDataBLOBDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
               } else {
                  bDO=getPersistedObject(DO, ti);                  
               }
               bDO.setActivityDataWOB(DO);
               bDO.setVariableValue(null);
               DO.setVariableType(DODSPersistentManager.DB_TYPE_BLOB);
               ((SharkDODSTransaction)ti).store(bDO);               
            }
         } else {
            isBLOB=true;
         }

         if (isBLOB) {
            ActivityDataBLOBDO bDO=null;
            if (isInitialPersistence) {
               bDO=ActivityDataBLOBDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
            } else {
               bDO=getPersistedObject(DO, ti);                  
            }
            bDO.setActivityDataWOB(DO);
            bDO.setVariableValue(MiscUtilities.serialize(vv));
            DO.setVariableType(DODSPersistentManager.DB_TYPE_BLOB);
            ((SharkDODSTransaction)ti).store(bDO);
         }
//         if (wasBLOB) {
//            DO.setVariableValue(null);
//         }

         DO.setIsResult(var.isResultVariable());
         ((SharkDODSTransaction)ti).store(DO);
         //if (DO.isDirty()) DO.save(((SharkDODSTransaction)ti).getDODSTransaction());
         //if (DO.isVirgin()) {(((SharkDODSTransaction)ti).getDODSTransaction()).write();}
         cus.info(LOG_CHANNEL,"ActivityVariable[activityId="+var.getActivityId()+", definitionId="+var.getDefinitionId()+"] persisted");
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Persisting of ActivityVariable failed");
         throw new PersistenceException("Persisting of ActivityVariable failed", tr);
      }
   }   
   
   public void persist (AndJoinEntryInterface aje,SharkTransaction ti) throws PersistenceException {
      try {
         AndJoinEntryDO DO=AndJoinEntryDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         DO.setProcess(getPersistedProcessObject(aje.getProcessId(),ti));
         DO.setActivitySetDefinitionId(aje.getActivitySetDefinitionId());
         DO.setActivityDefinitionId(aje.getActivityDefinitionId());
         DO.setActivity(getPersistedActivityObject(aje.getActivityId(),ti));
         DO.setCNT(getNextDecId("andjoinentry"));
         ((SharkDODSTransaction)ti).store(DO);
         //if (DO.isDirty()) DO.save(((SharkDODSTransaction)ti).getDODSTransaction());
         //if (DO.isVirgin()) {(((SharkDODSTransaction)ti).getDODSTransaction()).write();}
         cus.info(LOG_CHANNEL,"AndJoinEntry[id="+aje.getProcessId()+",aDefId="+aje.getActivityDefinitionId()+",actId="+aje.getActivityId()+"] persisted");
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Persisting of AndJoinEntry [id="+aje.getProcessId()+",aDefId="+aje.getActivityDefinitionId()+",actId="+aje.getActivityId()+"] failed");
         throw new PersistenceException(
            "Persisting of AndJoinEntry [id="+aje.getProcessId()+
               ",aDefId="+aje.getActivityDefinitionId()+
               ",actId="+aje.getActivityId()+"] failed",tr);
      }
   }

   public void persist(DeadlinePersistenceInterface dpe, boolean isInitialPersistence, SharkTransaction ti) throws PersistenceException {
      try {
         DeadlineDO DO=null;
         if (isInitialPersistence) {
            DO=DeadlineDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         } else {
            DO=getPersistedObject(dpe,ti);
         }
         // when this attribute is persisted - it is never changed
         if (isInitialPersistence) {
            DO.setProcess(getPersistedProcessObject(dpe.getProcessId(),ti));
            DO.setActivity(getPersistedActivityObject(dpe.getActivityId(),ti));
            DO.setExceptionName(dpe.getExceptionName());
            DO.setTimeLimit(dpe.getTimeLimit());
            DO.setIsSynchronous(dpe.isSynchronous());
            DO.setCNT(getNextDecId("deadline"));
         } else {
            DO.setIsExecuted(dpe.isExecuted());
         }
         ((SharkDODSTransaction)ti).store(DO);
         //if (DO.isDirty()) DO.save(((SharkDODSTransaction)ti).getDODSTransaction());
         //if (DO.isVirgin()) {(((SharkDODSTransaction)ti).getDODSTransaction()).write();}
         cus.info(LOG_CHANNEL,"Deadline[actId="+dpe.getActivityId()+",ExcName="+dpe.getExceptionName()+"] persisted");
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Persisting of Deadline[actId="+dpe.getActivityId()+",ExcName="+dpe.getExceptionName()+"] failed");
         throw new PersistenceException(
            "Persisting of Deadline[actId="+dpe.getActivityId()+",ExcName="+dpe.getExceptionName()+"] failed",tr);
      }
   }

   public ProcessMgrPersistenceInterface restoreProcessMgr (String mgrName,SharkTransaction ti) throws PersistenceException {
      return restore(getPersistedProcessMgrObject(mgrName,ti));
   }

   protected ProcessMgrPersistenceInterface restore (ProcessDefinitionDO DO) throws PersistenceException {
      if (DO==null) return null;
      ProcessMgrPersistenceInterface engineObj=new DODSProcessMgr();

      try {
         engineObj.setName(DO.getName());
         engineObj.setPackageId(DO.getPackageId());
         engineObj.setProcessDefinitionId(DO.getProcessDefinitionId());
         engineObj.setVersion(DO.getProcessDefinitionVersion());
         engineObj.setCreated(DO.getProcessDefinitionCreated());
         engineObj.setState(DO.getState());
         cus.debug(LOG_CHANNEL,"ProcessDefinition[packageId="+DO.getPackageId()+",id="+DO.getProcessDefinitionId()+" restored");
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Restoring of ProcessDefinition failed");
         throw new PersistenceException
            ("Restoring of ProcessDefinition failed", tr);
      }
      return engineObj;
   }

   public ProcessPersistenceInterface restoreProcess (String procId,SharkTransaction ti) throws PersistenceException {
      return restore(getPersistedProcessObject(procId,ti));
   }

   protected ProcessPersistenceInterface restore (ProcessDO DO) throws PersistenceException {
      if (DO==null) return null;

      ProcessPersistenceInterface engineObj=new DODSProcess();
      try {
         engineObj.setId(DO.getId());
         //engineObj.setProcessMgrName(DO.getProcessDefinition().getName());
         engineObj.setProcessMgrName(DO.getPDefName());
         engineObj.setName(DO.getName());
         engineObj.setDescription(DO.getDescription());
         engineObj.setPriority(DO.getPriority());
         engineObj.setState(DO.getState().getName());
         engineObj.setCreatedTime(DO.getCreated());
         engineObj.setStartedTime(DO.getStarted());
         engineObj.setLastStateTime(DO.getLastStateTime());
         engineObj.setLimitTime(DO.getLimitTime());

         /*if (rDO!=null) {
            ActivityDO ar = rDO.getActivityRequester();
            if (null != ar) {
               engineObj.setActivityRequesterId(ar.getId());
               engineObj.setActivityRequestersProcessId(ar.getProcess().getId());
            }
            ResourceDO rr = rDO.getResourceRequester();
            if (null != rr) {
               engineObj.setResourceRequesterId(rr.getUsername());
            }
            byte[] er=rDO.getExternalRequester();

            if (er!=null && er.length>0) {
               engineObj.setExternalRequester(MiscUtilities.deserialize(er));
            }
         }*/
         engineObj.setActivityRequesterId(DO.getActivityRequesterId());
         engineObj.setActivityRequestersProcessId(DO.getActivityRequesterProcessId());
         engineObj.setResourceRequesterId(DO.getResourceRequesterId());
         engineObj.setExternalRequesterClassName(DO.getExternalRequesterClassName());

         cus.debug(LOG_CHANNEL,"Process[id="+DO.getId()+"] restored");
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Restoring of Process failed");
         throw new PersistenceException
            ("Restoring of Process "+engineObj.getId()+" failed", tr);
      }
      return engineObj;
   }

   public ActivityPersistenceInterface restoreActivity (String actId,SharkTransaction ti) throws PersistenceException {
      return restore(getPersistedActivityObject(actId,ti));
   }

   protected ActivityPersistenceInterface restore (ActivityDO DO) throws PersistenceException {
      if (DO==null) return null;

      ActivityPersistenceInterface engineObj=new DODSActivity();
      try {
         engineObj.setActivitySetDefinitionId(DO.getActivitySetDefinitionId());
         engineObj.setActivityDefinitionId(DO.getActivityDefinitionId());
         engineObj.setId(DO.getId());
         engineObj.setProcessMgrName(DO.getPDefName());
         //engineObj.setProcessId(DO.getProcess().getId());
         engineObj.setProcessId(DO.getProcessId());
         /*if (DO.getTheResource()!=null) {
            engineObj.setResourceUsername(DO.getTheResource().getUsername());
         }*/
         engineObj.setResourceUsername(DO.getResourceId());

         engineObj.setSubflowProcessId(DO.getPerformer());
         engineObj.setSubflowAsynchronous(DO.getIsPerformerAsynchronous());
         engineObj.setName(DO.getName());
         engineObj.setDescription(DO.getDescription());
         engineObj.setPriority(DO.getPriority());
         engineObj.setState(DO.getState().getName());
         engineObj.setLastStateTime(DO.getLastStateTime());
         engineObj.setLimitTime(DO.getLimitTime());
         engineObj.setAcceptedTime(DO.getAccepted());
         engineObj.setActivatedTime(DO.getActivated());
         /*if (DO.getBlockActivity()!=null) {
            engineObj.setBlockActivityId(DO.getBlockActivity().getId());
         }*/
         engineObj.setBlockActivityId(DO.getBlockActivityId());
         cus.debug(LOG_CHANNEL,"Activity[id="+DO.getId()+",definitionId="+DO.getActivityDefinitionId()+"] restored");
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Restoring of Activity failed");
         throw new PersistenceException
            ("Restoring of Activity failed", tr);
      }
      return engineObj;
   }

   public ResourcePersistenceInterface restoreResource (String resUsername,SharkTransaction ti) throws PersistenceException {
      return restore(getPersistedResourceObject(resUsername,ti));
   }

   protected ResourcePersistenceInterface restore (ResourceDO DO) throws PersistenceException {
      if (DO==null) return null;

      ResourcePersistenceInterface engineObj=new DODSResource();
      try {
         engineObj.setName(DO.getName());
         engineObj.setUsername(DO.getUsername());
         cus.debug(LOG_CHANNEL,"Resource[username="+DO.getUsername()+"] restored");
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Restoring of Resource failed");
         throw new PersistenceException
            ("Restoring of Resource failed", tr);
      }
      return engineObj;
   }

   public AssignmentPersistenceInterface restoreAssignment (String actId,String resUsername,SharkTransaction ti) throws PersistenceException {
      return restore(getPersistedAssignmentObject(actId,resUsername,ti));
   }

   protected AssignmentPersistenceInterface restore (AssignmentDO DO) throws PersistenceException {
      if (DO==null) return null;

      AssignmentPersistenceInterface engineObj=new DODSAssignment();
      try {
         String actId=DO.getActivityId();
         String username=DO.getResourceId();
         String processId=DO.getActivityProcessId();         
         /*engineObj.setActivityId(actId);
         engineObj.setResourceUsername(username);
         engineObj.setProcessId(processId);*/
         engineObj.setActivityId(actId);
         engineObj.setResourceUsername(username);
         engineObj.setProcessId(processId);
         engineObj.setProcessMgrName(DO.getActivityProcessDefName());
         engineObj.setValid(DO.getIsValid());
         engineObj.setAccepted(DO.getIsAccepted());
         cus.debug(LOG_CHANNEL,"Assignment[activityId="+actId+", username="+username+"] restored");
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Restoring of Assignment failed");
         throw new PersistenceException
            ("Restoring of Assignment failed", tr);
      }
      return engineObj;
   }

   public boolean restore (ProcessVariablePersistenceInterface var,SharkTransaction ti) throws PersistenceException {
      if (usingStandardVariableDataModel) {
         return restoreBLOB(var,getPersistedObject(var,ti));
      } else {
         return restoreWOB(var,getPersistedObject2(var,ti));
      }
   }

   protected boolean restoreBLOB (ProcessVariablePersistenceInterface engineObj,
                              ProcessDataDO DO) throws PersistenceException {
      if (DO==null) return false;

      try {
         engineObj.setProcessId(DO.getProcess().getId());
         engineObj.setDefinitionId(DO.getVariableDefinitionId());

         short vtype=DO.getVariableType();
         switch (vtype) {
            case DODSPersistentManager.DB_TYPE_BOOLEAN :
               engineObj.setValue(new Boolean(DO.getVariableValueBOOL()));
               break;
            case DODSPersistentManager.DB_TYPE_LONG:
               engineObj.setValue(new Long(DO.getVariableValueLONG()));
               break;
            case DODSPersistentManager.DB_TYPE_DOUBLE:
               engineObj.setValue(new Double(DO.getVariableValueDBL()));
               break;
            case DODSPersistentManager.DB_TYPE_VCHAR:
               engineObj.setValue(DO.getVariableValueVCHAR());
               break;
            case DODSPersistentManager.DB_TYPE_DATE:
               java.sql.Timestamp d=DO.getVariableValueDATE();
               if (d!=null) {
                  engineObj.setValue(new java.util.Date(d.getTime()));
               } else {
                  engineObj.setValue(null);
               }
               break;
            default:
               byte[] v=DO.getVariableValue();
               if (v!=null && v.length>0) {
                  engineObj.setValue(MiscUtilities.deserialize(v));
               } else {
                  engineObj.setValue(null);
               }
         }


         cus.debug(LOG_CHANNEL,"ProcessVariable[processId="+engineObj.getProcessId()+", definitionId="+engineObj.getDefinitionId()+"] restored");
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Restoring of ProcessVariable failed");
         throw new PersistenceException
            ("Restoring of ProcessVariable failed", tr);
      }
      return true;
   }

   protected boolean restoreWOB (ProcessVariablePersistenceInterface engineObj,
         ProcessDataWOBDO DO) throws PersistenceException {

      if (DO==null) return false;

      try {
         engineObj.setProcessId(DO.getProcess().getId());
         engineObj.setDefinitionId(DO.getVariableDefinitionId());
   
         short vtype=DO.getVariableType();
         switch (vtype) {
            case DODSPersistentManager.DB_TYPE_BOOLEAN :
               engineObj.setValue(new Boolean(DO.getVariableValueBOOL()));
               break;
            case DODSPersistentManager.DB_TYPE_LONG:
               engineObj.setValue(new Long(DO.getVariableValueLONG()));
               break;
            case DODSPersistentManager.DB_TYPE_DOUBLE:
               engineObj.setValue(new Double(DO.getVariableValueDBL()));
               break;
            case DODSPersistentManager.DB_TYPE_VCHAR:
               engineObj.setValue(DO.getVariableValueVCHAR());
               break;
            case DODSPersistentManager.DB_TYPE_DATE:
               java.sql.Timestamp d=DO.getVariableValueDATE();
               if (d!=null) {
                  engineObj.setValue(new java.util.Date(d.getTime()));
               } else {
                  engineObj.setValue(null);
               }
               break;
            default:
               byte[] v=DO.getProcessDataBLOBDO().getVariableValue();
               if (v!=null && v.length>0) {
                  engineObj.setValue(MiscUtilities.deserialize(v));
               } else {
                  engineObj.setValue(null);
               }
         }
      
         cus.debug(LOG_CHANNEL,"ProcessVariable[processId="+engineObj.getProcessId()+", definitionId="+engineObj.getDefinitionId()+"] restored");
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Restoring of ProcessVariable failed");
         throw new PersistenceException
            ("Restoring of ProcessVariable failed", tr);
      }
      return true;      
   }
   
   public boolean restore (ActivityVariablePersistenceInterface var,SharkTransaction ti) throws PersistenceException {
      if (usingStandardVariableDataModel) {
         return restoreBLOB(var,getPersistedObject(var,ti));
      } else {
         return restoreWOB(var,getPersistedObject2(var,ti));
      }
   }

   protected boolean restoreBLOB (ActivityVariablePersistenceInterface engineObj,
                              ActivityDataDO DO) throws PersistenceException {
      if (DO==null) return false;

      try {
         engineObj.setActivityId(DO.getActivity().getId());
         engineObj.setDefinitionId(DO.getVariableDefinitionId());

         short vtype=DO.getVariableType();
         switch (vtype) {
            case DODSPersistentManager.DB_TYPE_BOOLEAN :
               engineObj.setValue(new Boolean(DO.getVariableValueBOOL()));
               break;
            case DODSPersistentManager.DB_TYPE_LONG:
               engineObj.setValue(new Long(DO.getVariableValueLONG()));
               break;
            case DODSPersistentManager.DB_TYPE_DOUBLE:
               engineObj.setValue(new Double(DO.getVariableValueDBL()));
               break;
            case DODSPersistentManager.DB_TYPE_VCHAR:
               engineObj.setValue(DO.getVariableValueVCHAR());
               break;
            case DODSPersistentManager.DB_TYPE_DATE:
               java.sql.Timestamp d=DO.getVariableValueDATE();
               if (d!=null) {
                  engineObj.setValue(new java.util.Date(d.getTime()));
               } else {
                  engineObj.setValue(null);
               }
               break;
            default:
               byte[] v=DO.getVariableValue();
               if (v!=null && v.length>0) {
                  engineObj.setValue(MiscUtilities.deserialize(v));
               } else {
                  engineObj.setValue(null);
               }
         }

         engineObj.setResultVariable(DO.getIsResult());
         cus.debug(LOG_CHANNEL,"ActivityVariable[activityId="+engineObj.getActivityId()+", definitionId="+engineObj.getDefinitionId()+"] restored");
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Restoring of Variable failed");
         throw new PersistenceException
            ("Restoring of Variable failed", tr);
      }
      return true;
   }

   protected boolean restoreWOB (ActivityVariablePersistenceInterface engineObj,
         ActivityDataWOBDO DO) throws PersistenceException {
      if (DO==null) return false;
      try {
         engineObj.setActivityId(DO.getActivity().getId());
         engineObj.setDefinitionId(DO.getVariableDefinitionId());

         short vtype=DO.getVariableType();
         switch (vtype) {
            case DODSPersistentManager.DB_TYPE_BOOLEAN :
               engineObj.setValue(new Boolean(DO.getVariableValueBOOL()));
               break;
            case DODSPersistentManager.DB_TYPE_LONG:
               engineObj.setValue(new Long(DO.getVariableValueLONG()));
               break;
            case DODSPersistentManager.DB_TYPE_DOUBLE:
               engineObj.setValue(new Double(DO.getVariableValueDBL()));
               break;
            case DODSPersistentManager.DB_TYPE_VCHAR:
               engineObj.setValue(DO.getVariableValueVCHAR());
               break;
            case DODSPersistentManager.DB_TYPE_DATE:
               java.sql.Timestamp d=DO.getVariableValueDATE();
               if (d!=null) {
                  engineObj.setValue(new java.util.Date(d.getTime()));
               } else {
                  engineObj.setValue(null);
               }
               break;
            default:
               byte[] v=DO.getActivityDataBLOBDO().getVariableValue();
               if (v!=null && v.length>0) {
                  engineObj.setValue(MiscUtilities.deserialize(v));
               } else {
                  engineObj.setValue(null);
               }
         }

         engineObj.setResultVariable(DO.getIsResult());
         cus.debug(LOG_CHANNEL,"ActivityVariable[activityId="+engineObj.getActivityId()+", definitionId="+engineObj.getDefinitionId()+"] restored");
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Restoring of Variable failed");
         throw new PersistenceException
            ("Restoring of Variable failed", tr);
      }
      return true;
   }   
   
   public void deleteProcessMgr (String mgrName,SharkTransaction ti) throws PersistenceException {
      try {
         ProcessDefinitionDO DO=getPersistedProcessMgrObject(mgrName,ti);
         if (DO.getProcessDOArrayCount()>0) return;
         ((SharkDODSTransaction)ti).erase(DO);
         //DO.delete(((SharkDODSTransaction)ti).getDODSTransaction());
         //(((SharkDODSTransaction)ti).getDODSTransaction()).write();
         cus.info(LOG_CHANNEL,"ProcessDefinition[name="+mgrName+"] deleted");
      } catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Deleting of ProcessDefinition failed");
         throw new PersistenceException
            ("Deleting of ProcessDefinition failed", tr);
      }
   }

   public void deleteProcess (String procId,boolean administrative,SharkTransaction ti) throws PersistenceException {
      if (!deleteFinishedProcesses && !administrative) return;
      try {
         Set procIds=new HashSet();
         performCascadeDeletion(procId,procIds,(SharkDODSTransaction)ti);
         procIds.remove(procId);
         cus.info(LOG_CHANNEL,"Main Process[id="+procId+"] deleted");
         cus.info(LOG_CHANNEL,"Sub-Processes "+procIds+"] deleted");
//         System.out.println("Process[id="+procId+"] deleted");
//         System.out.println("Subprocesses "+procIds+"] deleted");
      } catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Deleting of Process failed");
         throw new PersistenceException
            ("Deleting of Process failed", tr);
      }
   }

   protected void performCascadeDeletion (String procId,Set procIds,SharkDODSTransaction ti) throws Exception {
      ProcessDO pDO=getPersistedProcessObject(procId,ti);
      ProcessRequesterDO pReqDO=getPersistedRequesterObject(procId,ti);
      ((SharkDODSTransaction)ti).erase(pDO);
      ((SharkDODSTransaction)ti).erase(pReqDO);
      if (pDO==null) {
//         System.out.println("PRoc "+procId+" can't be found!");
         return;
      }
      procIds.add(procId);
      performCascadeErasageFromBuffer(pDO,procIds,(SharkDODSTransaction)ti);
//      if (true) return;
      String SQLWHERE="Activities.Process="+pDO.get_Handle()+" AND Activities.Performer IS NOT NULL"; //AND Activities.IsPerformerAsynchronous='false'";;
      ActivityQuery query=new ActivityQuery(ti.getDODSTransaction());
      QueryBuilder qb=qb=query.getQueryBuilder();
      qb.addWhere(SQLWHERE);
//      System.out.println("SQLWP="+qb.getSQLwithParms());
      ActivityDO[] DOs = query.getDOArray();
      Set deleteFurther=new HashSet();
      if (DOs!=null) {
         for (int i=0;i<DOs.length; i++) {
            if (!DOs[i].getIsPerformerAsynchronous()) {
               deleteFurther.add(DOs[i].getPerformer());
            }
         }
      }
      deleteFurther.removeAll(procIds);
//      System.out.println("Further deleting subprocesses "+deleteFurther+" for process "+procId);
      Iterator it=deleteFurther.iterator();
      while (it.hasNext()) {
         String fpid=(String)it.next();
         performCascadeDeletion(fpid, procIds, ti);
      }
      
   }
   
   protected void performCascadeErasageFromBuffer (ProcessDO pDO,Set procIds,SharkDODSTransaction sdt) throws Exception {
      try {
//System.out.println("PCEFB for "+pDO);         
         BigDecimal pOId = pDO.get_OId().toBigDecimal();
         Set actOIdsToRemove=new HashSet();
         Set procIdsToRemove=new HashSet();
         Set toRemove=new HashSet();
         for (Iterator it = sdt.iterator4type("class org.enhydra.shark.instancepersistence.data.ActivityDO");
              it.hasNext();) {
            ActivityDO o = (ActivityDO)it.next();
            if (pOId.equals(o.oid_getProcess())) {
               toRemove.add(o);
               actOIdsToRemove.add(o.get_OId().toBigDecimal());
               if (o.getPerformer()!=null && !o.getIsPerformerAsynchronous()) {
                  procIdsToRemove.add(o.getPerformer());
               }
            }
         }
         for (Iterator it = sdt.iterator4type("class org.enhydra.shark.instancepersistence.data.ProcessDataDO");
              it.hasNext();) {
            ProcessDataDO o = (ProcessDataDO)it.next();
            if (pOId.equals(o.oid_getProcess())) {
               toRemove.add(o);
            }
         }
         for (Iterator it = sdt.iterator4type("class org.enhydra.shark.instancepersistence.data.DeadlineDO");
              it.hasNext();) {
            DeadlineDO o = (DeadlineDO)it.next();
            if (pOId.equals(o.oid_getProcess())) {
               toRemove.add(o);
            }
         }
         for (Iterator it = sdt.iterator4type("class org.enhydra.shark.instancepersistence.data.AndJoinEntryDO");
              it.hasNext();) {
            AndJoinEntryDO o = (AndJoinEntryDO)it.next();
            if (pOId.equals(o.oid_getProcess())) {
               toRemove.add(o);
            }
         }

         for (Iterator it = sdt.iterator4type("class org.enhydra.shark.instancepersistence.data.AssignmentDO");
              it.hasNext();) {
            AssignmentDO o = (AssignmentDO)it.next();
            if (actOIdsToRemove.contains(o.oid_getActivity())) {
               toRemove.add(o);
            }
         }
         for (Iterator it = sdt.iterator4type("class org.enhydra.shark.instancepersistence.data.ActivityDataDO");
              it.hasNext();) {
            ActivityDataDO o = (ActivityDataDO)it.next();
            if (actOIdsToRemove.contains(o.oid_getActivity())) {
               toRemove.add(o);
            }
         }
         for (Iterator it = sdt.iterator4type("class org.enhydra.shark.instancepersistence.data.ProcessRequesterDO");
              it.hasNext();) {
            ProcessRequesterDO o = (ProcessRequesterDO)it.next();
            if (actOIdsToRemove.contains(o.oid_getActivityRequester()) || o.getId().equals(pDO.getId())) {
               toRemove.add(o);
            }
         }
         sdt.erase(toRemove);
         Iterator it=procIdsToRemove.iterator();
         while (it.hasNext()) {
            performCascadeDeletion((String)it.next(), procIds, sdt);
         }
      } catch (Throwable tr) {
         throw new PersistenceException("Problems while performing cascade deletion from Buffer",tr);
      }
   }

   public void deleteActivity (String actId,SharkTransaction ti) throws PersistenceException {
      // NEVER DELETE ACTIVITIES
      if (true) return;
      try {
         ActivityDO DO=getPersistedActivityObject(actId,ti);
         DO.delete(((SharkDODSTransaction)ti).getDODSTransaction());
         (((SharkDODSTransaction)ti).getDODSTransaction()).write();
         cus.info(LOG_CHANNEL,"Activity[id="+actId+"] deleted");
      } catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Deleting of Activity failed");
         throw new PersistenceException
            ("Deleting of Activity failed", tr);
      }
   }

   public void deleteResource (String resUsername,SharkTransaction ti) throws PersistenceException {
      // NEVER DELETE RESOURCES
      if (true) return;
      try {
         ResourceDO DO=getPersistedResourceObject(resUsername,ti);
         DO.delete(((SharkDODSTransaction)ti).getDODSTransaction());
         (((SharkDODSTransaction)ti).getDODSTransaction()).write();
         cus.info(LOG_CHANNEL,"Resource[username="+resUsername+"] deleted");
      } catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Deleting of Resource failed");
         throw new PersistenceException
            ("Deleting of Resource failed", tr);
      }
   }

   public void deleteAssignment(String actId,String resUsername,SharkTransaction ti) throws PersistenceException {
      //if (!delete) return;
      try {
         AssignmentDO DO=getPersistedAssignmentObject(actId,resUsername,ti);
         ((SharkDODSTransaction)ti).erase(DO);
         //DO.delete(((SharkDODSTransaction)ti).getDODSTransaction());
         //(((SharkDODSTransaction)ti).getDODSTransaction()).write();
         cus.info(LOG_CHANNEL,"Assignment[activityId="+actId+", username="+resUsername+"] deleted");
      } catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Deleting of Assignment failed");
         throw new PersistenceException
            ("Deleting of Assignment failed", tr);
      }
   }

   public void deleteAndJoinEntries (String procId,
                                     String asDefId,
                                     String aDefId,
                                     SharkTransaction ti) throws PersistenceException {
      try {
         AndJoinEntryDO[] DOs=getPersistedAndJoinEntries(procId,asDefId,aDefId,ti);
         if (DOs!=null) {
            for (int i=0; i<DOs.length; i++) {
               ((SharkDODSTransaction)ti).erase(DOs[i]);
               //DOs[i].delete(((SharkDODSTransaction)ti).getDODSTransaction());
            }
            cus.info(LOG_CHANNEL,"AndJoinEntries for [procId="+procId+", aDefId="+aDefId+"] deleted");
         } else {
            cus.info(LOG_CHANNEL,"There are no AndJoinEntries for [procId="+procId+", aDefId="+aDefId+"] to delete");
         }
         //(((SharkDODSTransaction)ti).getDODSTransaction()).write();
      } catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Deleting of AndJoinEntries for [procId="+procId+", aDefId="+aDefId+"] failed");
         throw new PersistenceException
            ("Deleting of AndJoinEntries for [procId="+procId+", aDefId="+aDefId+"] failed", tr);
      }

   }

   public void deleteDeadlines (String procId,
                                SharkTransaction ti) throws PersistenceException {
      if (true) return;
      try {
         DeadlineDO[] DOs=getPersistedDeadlines(procId,-1,ti);
         if (DOs!=null) {
            for (int i=0; i<DOs.length; i++) {
               ((SharkDODSTransaction)ti).erase(DOs[i]);
               //DOs[i].delete(((SharkDODSTransaction)ti).getDODSTransaction());
            }
            cus.info(LOG_CHANNEL,"Deadlines for process "+procId+" deleted");
         } else {
            cus.info(LOG_CHANNEL,"There are no Deadlines defined for the proces "+procId);
         }
         //(((SharkDODSTransaction)ti).getDODSTransaction()).write();
      } catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Deleting deadlines for the process "+procId+" failed");
         throw new PersistenceException
            ("Deleting deadlines for the process "+procId+" failed", tr);
      }
   }

   public void deleteDeadlines (String procId,
                                String actId,
                                SharkTransaction ti) throws PersistenceException {
      if (true) return;
      try {
         DeadlineDO[] DOs=getActivitiesPersistedDeadlines(actId,-1,ti);
         if (DOs!=null) {
            for (int i=0; i<DOs.length; i++) {
               ((SharkDODSTransaction)ti).erase(DOs[i]);
               //DOs[i].delete(((SharkDODSTransaction)ti).getDODSTransaction());
            }
            cus.info(LOG_CHANNEL,"Deadlines for activity "+actId+" deleted");
         } else {
            cus.info(LOG_CHANNEL,"There are no Deadlines defined for the activity "+actId);
         }
         //(((SharkDODSTransaction)ti).getDODSTransaction()).write();
      } catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Deleting deadlines for the activity "+actId+" failed");
         throw new PersistenceException
            ("Deleting deadlines for the activity "+actId+" failed", tr);
      }
   }

   public void delete (ProcessVariablePersistenceInterface var,SharkTransaction ti) throws PersistenceException {
      // NEVER DELETE PROCESS VARIABLES
      if (true) return;
      try {
         ProcessDataDO DO=getPersistedObject(var,ti);
         DO.delete(((SharkDODSTransaction)ti).getDODSTransaction());
         (((SharkDODSTransaction)ti).getDODSTransaction()).write();
         cus.info(LOG_CHANNEL,"ProcessVariable[processId="+var.getProcessId()+", definitionId="+var.getDefinitionId()+"] deleted");
      } catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Deleting of ProcessVariable failed");
         throw new PersistenceException
            ("Deleting of ProcessVariable failed", tr);
      }
   }

   public void delete (ActivityVariablePersistenceInterface var,SharkTransaction ti) throws PersistenceException {
      // NEVER DELETE ACTIVITY VARIABLES
      if (true) return;
      try {
         ActivityDataDO DO=getPersistedObject(var,ti);
         DO.delete(((SharkDODSTransaction)ti).getDODSTransaction());
         (((SharkDODSTransaction)ti).getDODSTransaction()).write();
         cus.info(LOG_CHANNEL,"ActivityVariable[activityId="+var.getActivityId()+", definitionId="+var.getDefinitionId()+"] deleted");
      } catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Deleting of ActivityVariable failed");
         throw new PersistenceException
            ("Deleting of ActivityVariable failed", tr);
      }
   }

   public List getAllProcessMgrs (SharkTransaction ti) throws PersistenceException {
      List ret=new ArrayList();

      ProcessDefinitionDO[] mgrs=getAllPersistedProcessMgrs(ti,null,null,null,null);
      if (mgrs!=null) {
         for (int i=0;i<mgrs.length; i++) {
            ProcessMgrPersistenceInterface mgr=restore(mgrs[i]);
            ret.add(mgr);
         }
      }
      return ret;
   }

   public List getAllProcesses (SharkTransaction ti) throws PersistenceException {
      List ret=new ArrayList();

      ProcessDO[] procs=getPersistedProcesses(ti,0,null,null,null,null,null);
      try {
         if (procs!=null) {
            for (int i=0;i<procs.length; i++) {
               ProcessPersistenceInterface proc=restore(procs[i]);
               ret.add(proc);
            }
         }
         return ret;
      } catch (Throwable e) {
         throw new PersistenceException(e);
      }
   }

   public List getAllRunningProcesses(SharkTransaction ti) throws PersistenceException {
      List ret=new ArrayList();

      ProcessDO[] procs=getPersistedProcesses(ti,1,null,null,null,null,null);
      try {
         if (procs!=null) {
            for (int i=0;i<procs.length; i++) {
               ProcessPersistenceInterface proc=restore(procs[i]);
               ret.add(proc);
            }
         }
         return ret;
      } catch (Throwable e) {
         throw new PersistenceException(e);
      }
   }

   public List getAllFinishedProcesses(SharkTransaction ti) throws PersistenceException {
      List ret=new ArrayList();

      ProcessDO[] procs=getPersistedProcesses(ti,-1,null,null,null,null,null);
      try {
         if (procs!=null) {
            for (int i=0;i<procs.length; i++) {
               ProcessPersistenceInterface proc=restore(procs[i]);
               ret.add(proc);
            }
         }
         return ret;
      } catch (Throwable e) {
         throw new PersistenceException(e);
      }
   }

   public List getAllFinishedProcesses(SharkTransaction ti,java.util.Date finishedBefore) throws PersistenceException {
      List ret=new ArrayList();

      ProcessDO[] procs=getPersistedProcesses(ti,-1,null,null,null,finishedBefore,null);
      try {
         if (procs!=null) {
            for (int i=0;i<procs.length; i++) {
               ProcessPersistenceInterface proc=restore(procs[i]);
               ret.add(proc);
            }
         }
         return ret;
      } catch (Throwable e) {
         throw new PersistenceException(e);
      }
   }

   public List getAllFinishedProcesses(SharkTransaction ti,String pkgId) throws PersistenceException {
      List ret=new ArrayList();

      ProcessDO[] procs=getPersistedProcesses(ti,-1,pkgId,null,null,null,null);
      try {
         if (procs!=null) {
            for (int i=0;i<procs.length; i++) {
               ProcessPersistenceInterface proc=restore(procs[i]);
               ret.add(proc);
            }
         }
         return ret;
      } catch (Throwable e) {
         throw new PersistenceException(e);
      }
   }

   public List getAllFinishedProcesses(SharkTransaction ti,String pkgId,String procDefId) throws PersistenceException {
      List ret=new ArrayList();

      ProcessDO[] procs=getPersistedProcesses(ti,-1,pkgId,procDefId,null,null,null);
      try {
         if (procs!=null) {
            for (int i=0;i<procs.length; i++) {
               ProcessPersistenceInterface proc=restore(procs[i]);
               ret.add(proc);
            }
         }
         return ret;
      } catch (Throwable e) {
         throw new PersistenceException(e);
      }
   }

   public List getAllFinishedProcesses(SharkTransaction ti,String pkgId,String procDefId,String pkgVer) throws PersistenceException {
      List ret=new ArrayList();

      ProcessDO[] procs=getPersistedProcesses(ti,-1,pkgId,procDefId,pkgVer,null,null);
      try {
         if (procs!=null) {
            for (int i=0;i<procs.length; i++) {
               ProcessPersistenceInterface proc=restore(procs[i]);
               ret.add(proc);
            }
         }
         return ret;
      } catch (Throwable e) {
         throw new PersistenceException(e);
      }
   }

   public List getAllActivities (SharkTransaction ti) throws PersistenceException {
      List ret=new ArrayList();

      ActivityDO[] acts=getPersistedActivities(ti,0,null,null);
      if (acts!=null) {
         for (int i=0;i<acts.length; i++) {
            ActivityPersistenceInterface act=restore(acts[i]);
            ret.add(act);
         }
      }
      return ret;
   }

   public List getAllResources (SharkTransaction ti) throws PersistenceException {
      List ret=new ArrayList();

      ResourceDO[] ress=getAllPersistedResources(ti, null);
      if (ress!=null) {
         for (int i=0;i<ress.length; i++) {
            ResourcePersistenceInterface res=restore(ress[i]);
            ret.add(res);
         }
      }
      return ret;
   }

   public List getAllAssignments (SharkTransaction ti) throws PersistenceException {
      List ret=new ArrayList();

      AssignmentDO[] ass=getAllPersistedAssignments(ti,null);
      if (ass!=null) {
         for (int i=0;i<ass.length; i++) {
            AssignmentPersistenceInterface as=restore(ass[i]);
            ret.add(as);
         }
      }
      return ret;
   }

   public List getAllProcessesForMgr (String mgrName,SharkTransaction ti) throws PersistenceException {
      List ret=new ArrayList();

      try {
         ProcessDefinitionDO pDO=getPersistedProcessMgrObject(mgrName,ti);
         if (pDO!=null) {
            ProcessDO[] procs=pDO.getProcessDOArray();
            BigDecimal pOId = pDO.get_OId().toBigDecimal();
            List list = new ArrayList(Arrays.asList(procs));

            for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.ProcessDO");
                 it.hasNext();) {
               ProcessDO o = (ProcessDO)it.next();
               if (pOId.equals(o.oid_getProcessDefinition())) {
                  int index = list.indexOf(o);
                  if (-1 != index)
                     list.remove(o);
               }
            }
            for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.ProcessDO");
                 it.hasNext();) {
               ProcessDO o = (ProcessDO)it.next();
               if (pOId.equals(o.oid_getProcessDefinition())) {
                  int index = list.indexOf(o);
                  if (-1 == index)
                     list.add(o);
                  else {
                     list.set(index, o);
                  }
               }
            }
            procs = new ProcessDO[list.size()];
            list.toArray(procs);
            for (int j=0; j<procs.length; j++) {
               ProcessPersistenceInterface proc=restore(procs[j]);
               ret.add(proc);
            }
         }
         return ret;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   public List getProcessesForMgr (String mgrName,String procState,SharkTransaction ti) throws PersistenceException {
      List ret=new ArrayList();

      ProcessDO[] DOs=null;
      ProcessQuery query = null;
      try {
         query=new ProcessQuery(((SharkDODSTransaction)ti).getDODSTransaction());
         BigDecimal state=(BigDecimal)_prStates.get(procState);
         QueryBuilder qb=qb=query.getQueryBuilder();
         qb.addWhere(ProcessDO.State,state,QueryBuilder.EQUAL);
         DOs = query.getDOArray();
         List list = new ArrayList(Arrays.asList(DOs));

         for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.ProcessDO");
              it.hasNext();) {
            list.remove(it.next());
         }
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.ProcessDO");
              it.hasNext();) {
            ProcessDO o = (ProcessDO)it.next();
            boolean stateOK=o.oid_getState().equals(state);
            int index = list.indexOf(o);
            if (-1 == index) {
               if (stateOK) {
                  list.add(o);
               }
            } else {
               if (stateOK) {
                  list.set(index, o);
               } else {
                  list.remove(o);
               }
            }
         }
         DOs = new ProcessDO[list.size()];
         list.toArray(DOs);
         for (int j=0; j<DOs.length; j++) {
            ProcessPersistenceInterface proc=restore(DOs[j]);
            ret.add(proc);
         }

         return ret;
      } catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   public List getAllActivitiesForProcess(String procId,SharkTransaction ti) throws PersistenceException {
      List ret=new ArrayList();

      try {
         ProcessDO pDO=getPersistedProcessObject(procId,ti);
         if (pDO!=null) {
            ActivityDO[] acts=pDO.getActivityDOArray();
            BigDecimal pOId = pDO.get_OId().toBigDecimal();
            List list = new ArrayList(Arrays.asList(acts));

            for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.ActivityDO");
                 it.hasNext();) {
               ActivityDO o = (ActivityDO)it.next();
               if (pOId.equals(o.oid_getProcess())) {
                  int index = list.indexOf(o);
                  if (-1 != index)
                     list.remove(o);
               }
            }
            for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.ActivityDO");
                 it.hasNext();) {
               ActivityDO o = (ActivityDO)it.next();
               if (pOId.equals(o.oid_getProcess())) {
                  int index = list.indexOf(o);
                  if (-1 == index)
                     list.add(o);
                  else {
                     list.set(index, o);
                  }
               }
            }
            acts = new ActivityDO[list.size()];
            list.toArray(acts);
            for (int j=0; j<acts.length; j++) {
               //System.err.println("getAllActivitiesForProcess:"+acts[j].getId()+"("+acts[j].getState().getKeyValue()+", "+acts[j].isVirgin()+")"+ti);
               ActivityPersistenceInterface act=restore(acts[j]);
               ret.add(act);
            }
         }
         return ret;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   public List getActivitiesForProcess(String procId,String actState,SharkTransaction ti) throws PersistenceException {
      List ret=new ArrayList();
      try {
         ProcessDO pDO=getPersistedProcessObject(procId,ti);
         BigDecimal pOId = pDO.get_OId().toBigDecimal();
         BigDecimal state=(BigDecimal)_acStates.get(actState);

         ActivityDO[] DOs=null;
         ActivityQuery query = null;
         query=new ActivityQuery(((SharkDODSTransaction)ti).getDODSTransaction());
         QueryBuilder qb=qb=query.getQueryBuilder();

         qb.addWhere(ActivityDO.Process,pOId,QueryBuilder.EQUAL);
         qb.addWhere(ActivityDO.State,state,QueryBuilder.EQUAL);
         DOs = query.getDOArray();
         List list = new ArrayList(Arrays.asList(DOs));
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.ActivityDO");
              it.hasNext();) {
            list.remove(it.next());
         }
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.ActivityDO");
              it.hasNext();) {
            ActivityDO o = (ActivityDO)it.next();
            boolean stateOK=o.oid_getState().equals(state);
            int index = list.indexOf(o);
            if (-1 == index) {
               if (stateOK) {
                  list.add(o);
               }
            } else {
               if (stateOK) {
                  list.set(index, o);
               } else {
                  list.remove(o);
               }
            }
         }
         DOs = new ActivityDO[list.size()];
         list.toArray(DOs);
         for (int j=0; j<DOs.length; j++) {
            ActivityPersistenceInterface act=restore(DOs[j]);
            ret.add(act);
         }

         return ret;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }

   }

   public List getAllFinishedActivitiesForProcess(String procId,SharkTransaction ti) throws PersistenceException {
      List ret=new ArrayList();

      try {
         ProcessDO pDO=getPersistedProcessObject(procId,ti);
         if (pDO!=null) {
            BigDecimal pOId = pDO.get_OId().toBigDecimal();
            ActivityDO[] acts=getPersistedActivities(ti,-1,pOId,null);

            if (acts!=null) {
               for (int j=0; j<acts.length; j++) {
                  //System.err.println("getAllActivitiesForProcess:"+acts[j].getId()+"("+acts[j].getState().getKeyValue()+", "+acts[j].isVirgin()+")"+ti);
                  ActivityPersistenceInterface act=restore(acts[j]);
                  ret.add(act);
               }
            }
         }
         return ret;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }


   public List getAllActiveActivitiesForProcess(String procId,SharkTransaction ti) throws PersistenceException {
      List ret=new ArrayList();

      try {
         ProcessDO pDO=getPersistedProcessObject(procId,ti);
         if (pDO!=null) {
            BigDecimal pOId = pDO.get_OId().toBigDecimal();
            ActivityDO[] acts=getPersistedActivities(ti,1,pOId,null);

            if (acts!=null) {
               for (int j=0; j<acts.length; j++) {
                  //System.err.println("getAllActivitiesForProcess:"+acts[j].getId()+"("+acts[j].getState().getKeyValue()+", "+acts[j].isVirgin()+")"+ti);
                  ActivityPersistenceInterface act=restore(acts[j]);
                  ret.add(act);
               }
            }
         }
         return ret;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   /**
    * Returns all assignments for the resource, no matter if its activity
    * is in "closed" state (or some of its sub-states).
    */
   public List getAllAssignmentsForResource (String resUsername,SharkTransaction ti) throws PersistenceException {
      List ret=new ArrayList();

      try {
         // restore all assignments
         ResourceDO rDO=getPersistedResourceObject(resUsername,ti);
         if (rDO!=null) {
            AssignmentDO[] ass=rDO.getAssignmentDOArray();
            BigDecimal rOId = rDO.get_OId().toBigDecimal();
            List list = new ArrayList(Arrays.asList(ass));

            for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.AssignmentDO");
                 it.hasNext();) {
               AssignmentDO o = (AssignmentDO)it.next();
               if (rOId.equals(o.oid_getTheResource())) {
                  int index = list.indexOf(o);
                  if (-1 != index)
                     list.remove(o);
               }
            }
            for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.AssignmentDO");
                 it.hasNext();) {
               AssignmentDO o = (AssignmentDO)it.next();
               if (rOId.equals(o.oid_getTheResource())) {
                  int index = list.indexOf(o);
                  if (-1 == index)
                     list.add(o);
                  else {
                     list.set(index, o);
                  }
               }
            }
            ass = new AssignmentDO[list.size()];
            list.toArray(ass);
            for (int i=0;i<ass.length; i++) {
               AssignmentDO assDO=ass[i];
               AssignmentPersistenceInterface as=restore(assDO);
               ret.add(as);
            }
         }
         return ret;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   /**
    * Returns all assignments which activity is not in "closed" state, or some
    * of its sub-states.
    */
   /*public List getAllAssignmentsForNotClosedActivitiesForResource (String resUsername,SharkTransaction ti) throws PersistenceException {
      List ret=new ArrayList();

      try {
         // restore all assignments
         ResourceDO rDO=getPersistedResourceObject(resUsername,ti);
         if (rDO!=null) {
            AssignmentDO[] ass=rDO.getAssignmentDOArray();
            BigDecimal rOId = rDO.get_OId().toBigDecimal();
            List list = new ArrayList(Arrays.asList(ass));

            for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.AssignmentDO");
                 it.hasNext();) {
               AssignmentDO o = (AssignmentDO)it.next();
               if (rOId.equals(o.oid_getTheResource())) {
                  int index = list.indexOf(o);
                  if (-1 != index)
                     list.remove(o);
               }
            }
            for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.AssignmentDO");
                 it.hasNext();) {
               AssignmentDO o = (AssignmentDO)it.next();
               if (rOId.equals(o.oid_getTheResource())) {
                  int index = list.indexOf(o);
                  if (-1 == index)
                     list.add(o);
                  else {
                     list.set(index, o);
                  }
               }
            }
            ass = new AssignmentDO[list.size()];
            list.toArray(ass);
            for (int i=0;i<ass.length; i++) {
               AssignmentDO assDO=ass[i];
               String state=assDO.getActivity().getState().getKeyValue();
               // restore assignment
               if (!state.startsWith("closed")) {
                  AssignmentPersistenceInterface as=restore(assDO);
                  ret.add(as);
               }
            }
         }
         return ret;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }*/

   /**
    * Returns only the assignments that can be currently executed by the resource
    * with a given username. This means the ones which activity is not finished
    * and   not accepted (it doesn't have TheResource field set), and the ones
    * which activity is accepted by this resource (its TheResource field is set
    * to the resource with given username).
    */
   public List getAllValidAssignmentsForResource (String resUsername,SharkTransaction ti) throws PersistenceException {
      List ret=new ArrayList();

      try {
         // restore all assignments
         ResourceDO rDO=getPersistedResourceObject(resUsername,ti);
         if (rDO!=null) {
            AssignmentDO[] ass=rDO.getAssignmentDOArray();
            BigDecimal rOId = rDO.get_OId().toBigDecimal();
            List list = new ArrayList(Arrays.asList(ass));

            for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.AssignmentDO");
                 it.hasNext();) {
               AssignmentDO o = (AssignmentDO)it.next();
               //if (rOId.equals(o.getResourceId())) {
               if (rOId.equals(o.oid_getTheResource())) {
                  int index = list.indexOf(o);
                  if (-1 != index)
                     list.remove(o);
               }
            }
            for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.AssignmentDO");
                 it.hasNext();) {
               AssignmentDO o = (AssignmentDO)it.next();
               if (rOId.equals(o.oid_getTheResource()) && o.getIsValid()) {
                  int index = list.indexOf(o);
                  if (-1 == index)
                     list.add(o);
                  else {
                     list.set(index, o);
                  }
               }
            }
            ass = new AssignmentDO[list.size()];
            list.toArray(ass);
            for (int i=0;i<ass.length; i++) {
               AssignmentDO assDO=ass[i];
               //ActivityDO actDO=assDO.getActivity();
               /*if (assDO==null) {
                System.out.println("ASSDO is null");
                } else if (assDO.getActivity()==null) {
                System.out.println("ASSDOACT for ass "+assDO+" is null and is"+(assDO.isVirgin()?"":"n't")+" virgin.");
                }*/
               //BigDecimal arOId=actDO.oid_getTheResource();
               //String state=actDO.getState().getKeyValue();
               //if (!state.startsWith("closed") && (arOId==null || rOId.equals(arOId))) {
               if (assDO.getIsValid()) {
                  AssignmentPersistenceInterface as=restore(assDO);
                  ret.add(as);
               }
            }
         }
         return ret;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   /**
    * Returns all assignments that are ever created for that activity, no
    * matter if activity is already in "closed" state or some of its sub-states.
    */
   public List getAllAssignmentsForActivity (String actId,SharkTransaction ti) throws PersistenceException {
      List ret=new ArrayList();
      try {
         // restore all assignments
         ActivityDO aDO=getPersistedActivityObject(actId,ti);
         if (aDO!=null) {
            AssignmentDO[] ass=aDO.getAssignmentDOArray();
            BigDecimal aOId = aDO.get_OId().toBigDecimal();
            List list = new ArrayList(Arrays.asList(ass));

            for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.AssignmentDO");
                 it.hasNext();) {
               AssignmentDO o = (AssignmentDO)it.next();
               if (aOId.equals(o.oid_getActivity())) {
                  int index = list.indexOf(o);
                  if (-1 != index)
                     list.remove(o);
               }
            }
            for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.AssignmentDO");
                 it.hasNext();) {
               AssignmentDO o = (AssignmentDO)it.next();
               if (aOId.equals(o.oid_getActivity())) {
                  int index = list.indexOf(o);
                  if (-1 == index)
                     list.add(o);
                  else {
                     list.set(index, o);
                  }
               }
            }
            ass = new AssignmentDO[list.size()];
            list.toArray(ass);
            for (int i=0;i<ass.length; i++) {
               AssignmentDO assDO=ass[i];
               AssignmentPersistenceInterface as=restore(assDO);
               ret.add(as);
            }
         }

         return ret;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   /**
    * If activity is in "closed" state, or some of its sub-states, returns an
    * empty list, otherwise returns all assignments that are ever created for
    * that activity.
    */
   /*public List getAllAssignmentsForNotClosedActivity (String actId,SharkTransaction ti) throws PersistenceException {
      List ret=new ArrayList();
      try {
         // restore all assignments
         ActivityDO aDO=getPersistedActivityObject(actId,ti);
         if (aDO!=null) {
            String actState=aDO.getState().getKeyValue();
            if (!actState.startsWith("closed")) {
               AssignmentDO[] ass=aDO.getAssignmentDOArray();
               BigDecimal aOId = aDO.get_OId().toBigDecimal();
               List list = new ArrayList(Arrays.asList(ass));

               for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.AssignmentDO");
                    it.hasNext();) {
                  AssignmentDO o = (AssignmentDO)it.next();
                  if (aOId.equals(o.oid_getActivity())) {
                     int index = list.indexOf(o);
                     if (-1 != index)
                        list.remove(o);
                  }
               }
               for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.AssignmentDO");
                    it.hasNext();) {
                  AssignmentDO o = (AssignmentDO)it.next();
                  if (aOId.equals(o.oid_getActivity())) {
                     int index = list.indexOf(o);
                     if (-1 == index)
                        list.add(o);
                     else {
                        list.set(index, o);
                     }
                  }
               }
               ass = new AssignmentDO[list.size()];
               list.toArray(ass);
               for (int i=0;i<ass.length; i++) {
                  AssignmentDO assDO=ass[i];
                  AssignmentPersistenceInterface as=restore(assDO);
                  ret.add(as);
               }
            }
         }

         return ret;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }*/

   /**
    * If activity is in "closed" state, or some of its sub-states, returns an
    * empty list, otherwise it returns either all assignments that are ever
    * created for that activity if activity is not accepted, or just the
    * assignment for the resource that accepted activity.
    */
   public List getAllValidAssignmentsForActivity (String actId,SharkTransaction ti) throws PersistenceException {
      List ret=new ArrayList();
      try {
         // restore all assignments
         ActivityDO aDO=getPersistedActivityObject(actId,ti);
         //BigDecimal rOId=aDO.oid_getTheResource();

         if (aDO!=null) {
            String actState=aDO.getState().getKeyValue();
            if (!actState.startsWith("closed")) {
               AssignmentDO[] ass=aDO.getAssignmentDOArray();
               BigDecimal aOId = aDO.get_OId().toBigDecimal();
               List list = new ArrayList(Arrays.asList(ass));

               for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.AssignmentDO");
                    it.hasNext();) {
                  AssignmentDO o = (AssignmentDO)it.next();
                  if (aOId.equals(o.oid_getActivity())) {
                     int index = list.indexOf(o);
                     if (-1 != index)
                        list.remove(o);
                  }
               }
               for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.AssignmentDO");
                    it.hasNext();) {
                  AssignmentDO o = (AssignmentDO)it.next();
                  if (aOId.equals(o.oid_getActivity()) && o.getIsValid()) {
                     int index = list.indexOf(o);
                     if (-1 == index)
                        list.add(o);
                     else {
                        list.set(index, o);
                     }
                  }
               }
               ass = new AssignmentDO[list.size()];
               list.toArray(ass);
               for (int i=0;i<ass.length; i++) {
                  AssignmentDO assDO=ass[i];
                  //ResourceDO arDO=assDO.getTheResource();
                  //if (rOId==null || rOId.equals(assDO.oid_getTheResource())) {
                  if (assDO.getIsValid()) {
                     AssignmentPersistenceInterface as=restore(assDO);
                     ret.add(as);
                  }
               }
            }
         }

         return ret;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   public List getAllVariablesForProcess (String procId,SharkTransaction ti) throws PersistenceException {
      if (usingStandardVariableDataModel) {
         return getAllVariablesForProcessBLOB(procId, ti);         
      } else {
         return getAllVariablesForProcessWOB(procId, ti);
      }
   }

   protected List getAllVariablesForProcessBLOB (String procId,SharkTransaction ti) throws PersistenceException {
      List ret=new ArrayList();
      try {
         ProcessDO pDO=getPersistedProcessObject(procId,ti);
         if (pDO!=null) {
            ProcessDataDO[] DOs=pDO.getProcessDataDOArray();
            BigDecimal pOId = pDO.get_OId().toBigDecimal();
            List list = new ArrayList(Arrays.asList(DOs));

            for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.ProcessDataDO");
                 it.hasNext();) {
               ProcessDataDO o = (ProcessDataDO)it.next();
               if (pOId.equals(o.oid_getProcess())) {
                  int index = list.indexOf(o);
                  if (-1 != index)
                     list.remove(o);
               }
            }
            for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.ProcessDataDO");
                 it.hasNext();) {
               ProcessDataDO o = (ProcessDataDO)it.next();
               if (pOId.equals(o.oid_getProcess())) {
                  int index = list.indexOf(o);
                  if (-1 == index)
                     list.add(o);
                  else {
                     list.set(index, o);
                  }
               }
            }
            DOs = new ProcessDataDO[list.size()];
            list.toArray(DOs);
            for (int i=0;i<DOs.length; i++) {
               ProcessDataDO DO=DOs[i];
               ProcessVariablePersistenceInterface var=new DODSProcessVariable();
               restoreBLOB(var,DO);
               ret.add(var);
            }
         }

         return ret;
      }
      catch (Throwable t) {
         //t.printStackTrace();
         throw new PersistenceException(t);
      }
   }
   
   protected List getAllVariablesForProcessWOB (String procId,SharkTransaction ti) throws PersistenceException {
      List ret=new ArrayList();
      try {
         ProcessDO pDO=getPersistedProcessObject(procId,ti);
         if (pDO!=null) {
            ProcessDataWOBDO[] DOs=pDO.getProcessDataWOBDOArray();
            BigDecimal pOId = pDO.get_OId().toBigDecimal();
            List list = new ArrayList(Arrays.asList(DOs));

            for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.ProcessDataWOBDO");
                 it.hasNext();) {
               ProcessDataWOBDO o = (ProcessDataWOBDO)it.next();
               if (pOId.equals(o.oid_getProcess())) {
                  int index = list.indexOf(o);
                  if (-1 != index)
                     list.remove(o);
               }
            }
            for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.ProcessDataWOBDO");
                 it.hasNext();) {
               ProcessDataWOBDO o = (ProcessDataWOBDO)it.next();
               if (pOId.equals(o.oid_getProcess())) {
                  int index = list.indexOf(o);
                  if (-1 == index)
                     list.add(o);
                  else {
                     list.set(index, o);
                  }
               }
            }
            DOs = new ProcessDataWOBDO[list.size()];
            list.toArray(DOs);
            for (int i=0;i<DOs.length; i++) {
               ProcessDataWOBDO DO=DOs[i];
               ProcessVariablePersistenceInterface var=new DODSProcessVariable();
               restoreWOB(var,DO);
               ret.add(var);
            }
         }

         return ret;
      }
      catch (Throwable t) {
         //t.printStackTrace();
         throw new PersistenceException(t);
      }
   }

   public List getProcessVariables(String procId,List variableIds,SharkTransaction ti) throws PersistenceException {
      if (usingStandardVariableDataModel) {
         return getProcessVariablesBLOB(procId, variableIds, ti);
      } else {
         return getProcessVariablesWOB(procId, variableIds, ti);
      }
   }

   public List getProcessVariablesBLOB(String procId,List variableIds,SharkTransaction ti) throws PersistenceException {
      List ret=new ArrayList();
      try {
         Map map=new HashMap();
         ProcessDO pDO=getPersistedProcessObject(procId,ti);
         if (pDO!=null) {
            BigDecimal pOId = pDO.get_OId().toBigDecimal();
            for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.ProcessDataDO");
                 it.hasNext();) {
               ProcessDataDO o = (ProcessDataDO)it.next();
               if (pOId.equals(o.oid_getProcess()) && variableIds.contains(o.getVariableDefinitionId())) {
                  map.put(o.getVariableDefinitionId(),o);
                  //System.out.println("FOUND variable in Buffer: id="+o.getVariableDefinitionId()+", val="+MiscUtilities.deserialize(o.getVariableValue()));
               }
            }
         }
         
//         System.out.println("FOUND "+map.size()+" variables in Buffer");
         
         if (pDO.isPersistent() && map.size()!=variableIds.size()) {
            if (variableIds.size()-map.size()>1) { 
//               System.out.println("QUERY ON PDATA DB EXECUTED for "+procId+", there are already "+map.size()+" vars found !");
               ProcessDataDO[] DOs=pDO.getProcessDataDOArray();
               for (int i=0; i<DOs.length; i++) {
                  if (!map.containsKey(DOs[i].getVariableDefinitionId())) {
                     map.put(DOs[i].getVariableDefinitionId(),DOs[i]);
                     ((SharkDODSTransaction)ti)._read(DOs[i]);
                  }
//                  System.out.println("FOUND variable in DB: id="+DOs[i].getVariableDefinitionId()+", val="+MiscUtilities.deserialize(DOs[i].getVariableValue()));
               }
            } else {
               DBTransaction dbt=((SharkDODSTransaction)ti).getDODSTransaction();
               for (int i=0; i<variableIds.size(); i++) {
                  String varId=(String)variableIds.get(i);
                  if (map.containsKey(varId)) continue;
                  ProcessDataQuery q=new ProcessDataQuery(dbt);
                  q.setQueryProcess(pDO);
                  q.setQueryVariableDefinitionId(varId);
                  ProcessDataDO DO=q.getNextDO();
                  if (DO!=null) {
//                     System.out.println("FOUND single variable in DB: id="+DO.getVariableDefinitionId()+", val="+MiscUtilities.deserialize(DO.getVariableValue()));
                     map.put(varId,DO);
                     ((SharkDODSTransaction)ti)._read(DO);
                  } else {
//                     System.out.println("Can't find variable "+varId+" for process "+procId);
                  }
               }
            }
         }
//         System.out.println("FOUND "+map.size()+" variables in Buffer and DB");
         
         ProcessDataDO[] DOs = new ProcessDataDO[map.size()];
         DOs = (ProcessDataDO[])map.values().toArray(DOs);
         if (DOs!=null) {
            for (int i=0;i<DOs.length; i++) {
               ProcessDataDO DO=DOs[i];
               ProcessVariablePersistenceInterface var=new DODSProcessVariable();
               restoreBLOB(var,DO);
               ret.add(var);
            }
         }


         return ret;
      }
      catch (Throwable t) {
         //t.printStackTrace();
         throw new PersistenceException(t);
      }

   }
      
   public List getProcessVariablesWOB(String procId,List variableIds,SharkTransaction ti) throws PersistenceException {
      List ret=new ArrayList();
      try {
         Map map=new HashMap();
         ProcessDO pDO=getPersistedProcessObject(procId,ti);
         if (pDO!=null) {
            BigDecimal pOId = pDO.get_OId().toBigDecimal();
            for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.ProcessDataWOBDO");
                 it.hasNext();) {
               ProcessDataWOBDO o = (ProcessDataWOBDO)it.next();
               if (pOId.equals(o.oid_getProcess()) && variableIds.contains(o.getVariableDefinitionId())) {
                  map.put(o.getVariableDefinitionId(),o);
                  //System.out.println("FOUND variable in Buffer: id="+o.getVariableDefinitionId()+", val="+MiscUtilities.deserialize(o.getVariableValue()));
               }
            }
         }
         
//         System.out.println("FOUND "+map.size()+" variables in Buffer");
         
         if (pDO.isPersistent() && map.size()!=variableIds.size()) {
            if (variableIds.size()-map.size()>1) { 
//               System.out.println("QUERY ON PDATA DB EXECUTED for "+procId+", there are already "+map.size()+" vars found !");
               ProcessDataWOBDO[] DOs=pDO.getProcessDataWOBDOArray();
               for (int i=0; i<DOs.length; i++) {
                  if (!map.containsKey(DOs[i].getVariableDefinitionId())) {
                     map.put(DOs[i].getVariableDefinitionId(),DOs[i]);
                     ((SharkDODSTransaction)ti)._read(DOs[i]);
                  }
//                  System.out.println("FOUND variable in DB: id="+DOs[i].getVariableDefinitionId()+", val="+MiscUtilities.deserialize(DOs[i].getVariableValue()));
               }
            } else {
               DBTransaction dbt=((SharkDODSTransaction)ti).getDODSTransaction();
               for (int i=0; i<variableIds.size(); i++) {
                  String varId=(String)variableIds.get(i);
                  if (map.containsKey(varId)) continue;
                  ProcessDataWOBQuery q=new ProcessDataWOBQuery(dbt);
                  q.setQueryProcess(pDO);
                  q.setQueryVariableDefinitionId(varId);
                  ProcessDataWOBDO DO=q.getNextDO();
                  if (DO!=null) {
//                     System.out.println("FOUND single variable in DB: id="+DO.getVariableDefinitionId()+", val="+MiscUtilities.deserialize(DO.getVariableValue()));
                     map.put(varId,DO);
                     ((SharkDODSTransaction)ti)._read(DO);
                  } else {
//                     System.out.println("Can't find variable "+varId+" for process "+procId);
                  }
               }
            }
         }
//         System.out.println("FOUND "+map.size()+" variables in Buffer and DB");
         
         ProcessDataWOBDO[] DOs = new ProcessDataWOBDO[map.size()];
         DOs = (ProcessDataWOBDO[])map.values().toArray(DOs);
         if (DOs!=null) {
            for (int i=0;i<DOs.length; i++) {
               ProcessDataWOBDO DO=DOs[i];
               ProcessVariablePersistenceInterface var=new DODSProcessVariable();
               restoreWOB(var,DO);
               ret.add(var);
            }
         }


         return ret;
      }
      catch (Throwable t) {
         //t.printStackTrace();
         throw new PersistenceException(t);
      }

   }

   public List getAllVariablesForActivity (String actId,SharkTransaction ti) throws PersistenceException {
      if (usingStandardVariableDataModel) {
         return getAllVariablesForActivityBLOB(actId, ti);         
      } else {
         return getAllVariablesForActivityWOB(actId, ti);
      }
   }

   public List getAllVariablesForActivityBLOB (String actId,SharkTransaction ti) throws PersistenceException {
      List ret=new ArrayList();
      try {
         ActivityDO aDO=getPersistedActivityObject(actId,ti);
         if (aDO!=null) {
            ActivityDataDO[] DOs=aDO.getActivityDataDOArray();
            BigDecimal aOId = aDO.get_OId().toBigDecimal();
            List list = new ArrayList(Arrays.asList(DOs));

            for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.ActivityDataDO");
                 it.hasNext();) {
               ActivityDataDO o = (ActivityDataDO)it.next();
               if (aOId.equals(o.oid_getActivity())) {
                  int index = list.indexOf(o);
                  if (-1 != index)
                     list.remove(o);
               }
            }
            for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.ActivityDataDO");
                 it.hasNext();) {
               ActivityDataDO o = (ActivityDataDO)it.next();
               if (aOId.equals(o.oid_getActivity())) {
                  int index = list.indexOf(o);
                  if (-1 == index)
                     list.add(o);
                  else {
                     list.set(index, o);
                  }
               }
            }
            DOs = new ActivityDataDO[list.size()];
            list.toArray(DOs);
            for (int i=0;i<DOs.length; i++) {
               ActivityDataDO DO=DOs[i];
               ActivityVariablePersistenceInterface var=new DODSActivityVariable();
               restoreBLOB(var,DO);
               ret.add(var);
            }
         }

         return ret;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   public List getAllVariablesForActivityWOB (String actId,SharkTransaction ti) throws PersistenceException {
      List ret=new ArrayList();
      try {
         ActivityDO aDO=getPersistedActivityObject(actId,ti);
         if (aDO!=null) {
            ActivityDataWOBDO[] DOs=aDO.getActivityDataWOBDOArray();
            BigDecimal aOId = aDO.get_OId().toBigDecimal();
            List list = new ArrayList(Arrays.asList(DOs));

            for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.ActivityDataWOBDO");
                 it.hasNext();) {
               ActivityDataWOBDO o = (ActivityDataWOBDO)it.next();
               if (aOId.equals(o.oid_getActivity())) {
                  int index = list.indexOf(o);
                  if (-1 != index)
                     list.remove(o);
               }
            }
            for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.ActivityDataWOBDO");
                 it.hasNext();) {
               ActivityDataWOBDO o = (ActivityDataWOBDO)it.next();
               if (aOId.equals(o.oid_getActivity())) {
                  int index = list.indexOf(o);
                  if (-1 == index)
                     list.add(o);
                  else {
                     list.set(index, o);
                  }
               }
            }
            DOs = new ActivityDataWOBDO[list.size()];
            list.toArray(DOs);
            for (int i=0;i<DOs.length; i++) {
               ActivityDataWOBDO DO=DOs[i];
               ActivityVariablePersistenceInterface var=new DODSActivityVariable();
               restoreWOB(var,DO);
               ret.add(var);
            }
         }

         return ret;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }
   
   public List getActivityVariables(String actId,List variableIds,SharkTransaction ti) throws PersistenceException {
      if (usingStandardVariableDataModel) {
         return getActivityVariablesBLOB(actId, variableIds, ti);
      } else {
         return getActivityVariablesWOB(actId, variableIds, ti);
      }
   }

   public List getActivityVariablesBLOB(String actId,List variableIds,SharkTransaction ti) throws PersistenceException {
      List ret=new ArrayList();
      try {
         Map map=new HashMap();
         ActivityDO aDO=getPersistedActivityObject(actId,ti);
         if (aDO!=null) {
            BigDecimal aOId = aDO.get_OId().toBigDecimal();
            for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.ActivityDataDO");
                 it.hasNext();) {
               ActivityDataDO o = (ActivityDataDO)it.next();
               if (aOId.equals(o.oid_getActivity()) && variableIds.contains(o.getVariableDefinitionId())) {
                  map.put(o.getVariableDefinitionId(),o);
                  //System.out.println("FOUND variable in Buffer: id="+o.getVariableDefinitionId()+", val="+MiscUtilities.deserialize(o.getVariableValue()));
               }
            }
         }
         
//         System.out.println("FOUND "+map.size()+" variables in Buffer");
         
         if (aDO.isPersistent() && map.size()!=variableIds.size()) {
            if (map.size()==0) { 
//               System.out.println("QUERY ON PDATA DB EXECUTED for "+procId+", there are already "+map.size()+" vars found !");
               ActivityDataDO[] DOs=aDO.getActivityDataDOArray();
               for (int i=0; i<DOs.length; i++) {
                  if (!map.containsKey(DOs[i].getVariableDefinitionId())) {
                     map.put(DOs[i].getVariableDefinitionId(),DOs[i]);
                     ((SharkDODSTransaction)ti)._read(DOs[i]);
                  }
//                  System.out.println("FOUND variable in DB: id="+DOs[i].getVariableDefinitionId()+", val="+MiscUtilities.deserialize(DOs[i].getVariableValue()));
               }
            } else {
               DBTransaction dbt=((SharkDODSTransaction)ti).getDODSTransaction();
               for (int i=0; i<variableIds.size(); i++) {
                  String varId=(String)variableIds.get(i);
                  if (map.containsKey(varId)) continue;
                  ActivityDataQuery q=new ActivityDataQuery(dbt);
                  q.setQueryActivity(aDO);
                  q.setQueryVariableDefinitionId(varId);
                  ActivityDataDO DO=q.getNextDO();
                  if (DO!=null) {
//                     System.out.println("FOUND single variable in DB: id="+DO.getVariableDefinitionId()+", val="+MiscUtilities.deserialize(DO.getVariableValue()));
                     map.put(varId,DO);
                     ((SharkDODSTransaction)ti)._read(DO);
                  } else {
//                     System.out.println("Can't find variable "+varId+" for process "+procId);
                  }
               }
            }
         }
//         System.out.println("FOUND "+map.size()+" variables in Buffer and DB");
         
         ActivityDataDO[] DOs = new ActivityDataDO[map.size()];
         DOs = (ActivityDataDO[])map.values().toArray(DOs);
         if (DOs!=null) {
            for (int i=0;i<DOs.length; i++) {
               ActivityDataDO DO=DOs[i];
               ActivityVariablePersistenceInterface var=new DODSActivityVariable();
               restoreBLOB(var,DO);
               ret.add(var);
            }
         }


         return ret;
      }
      catch (Throwable t) {
         //t.printStackTrace();
         throw new PersistenceException(t);
      }

   }

   public List getActivityVariablesWOB(String actId,List variableIds,SharkTransaction ti) throws PersistenceException {
      List ret=new ArrayList();
      try {
         Map map=new HashMap();
         ActivityDO aDO=getPersistedActivityObject(actId,ti);
         if (aDO!=null) {
            BigDecimal aOId = aDO.get_OId().toBigDecimal();
            for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.ActivityDataWOBDO");
                 it.hasNext();) {
               ActivityDataWOBDO o = (ActivityDataWOBDO)it.next();
               if (aOId.equals(o.oid_getActivity()) && variableIds.contains(o.getVariableDefinitionId())) {
                  map.put(o.getVariableDefinitionId(),o);
                  //System.out.println("FOUND variable in Buffer: id="+o.getVariableDefinitionId()+", val="+MiscUtilities.deserialize(o.getVariableValue()));
               }
            }
         }
         
//         System.out.println("FOUND "+map.size()+" variables in Buffer");
         
         if (aDO.isPersistent() && map.size()!=variableIds.size()) {
            if (map.size()==0) { 
//               System.out.println("QUERY ON PDATA DB EXECUTED for "+procId+", there are already "+map.size()+" vars found !");
               ActivityDataWOBDO[] DOs=aDO.getActivityDataWOBDOArray();
               for (int i=0; i<DOs.length; i++) {
                  if (!map.containsKey(DOs[i].getVariableDefinitionId())) {
                     map.put(DOs[i].getVariableDefinitionId(),DOs[i]);
                     ((SharkDODSTransaction)ti)._read(DOs[i]);
                  }
//                  System.out.println("FOUND variable in DB: id="+DOs[i].getVariableDefinitionId()+", val="+MiscUtilities.deserialize(DOs[i].getVariableValue()));
               }
            } else {
               DBTransaction dbt=((SharkDODSTransaction)ti).getDODSTransaction();
               for (int i=0; i<variableIds.size(); i++) {
                  String varId=(String)variableIds.get(i);
                  if (map.containsKey(varId)) continue;
                  ActivityDataWOBQuery q=new ActivityDataWOBQuery(dbt);
                  q.setQueryActivity(aDO);
                  q.setQueryVariableDefinitionId(varId);
                  ActivityDataWOBDO DO=q.getNextDO();
                  if (DO!=null) {
//                     System.out.println("FOUND single variable in DB: id="+DO.getVariableDefinitionId()+", val="+MiscUtilities.deserialize(DO.getVariableValue()));
                     map.put(varId,DO);
                     ((SharkDODSTransaction)ti)._read(DO);
                  } else {
//                     System.out.println("Can't find variable "+varId+" for process "+procId);
                  }
               }
            }
         }
//         System.out.println("FOUND "+map.size()+" variables in Buffer and DB");
         
         ActivityDataWOBDO[] DOs = new ActivityDataWOBDO[map.size()];
         DOs = (ActivityDataWOBDO[])map.values().toArray(DOs);
         if (DOs!=null) {
            for (int i=0;i<DOs.length; i++) {
               ActivityDataWOBDO DO=DOs[i];
               ActivityVariablePersistenceInterface var=new DODSActivityVariable();
               restoreWOB(var,DO);
               ret.add(var);
            }
         }

         return ret;
      }
      catch (Throwable t) {
         //t.printStackTrace();
         throw new PersistenceException(t);
      }

   }
   
   public List getResourceRequestersProcessIds (String resUsername,SharkTransaction ti) throws PersistenceException {
      List ret=new ArrayList();
      try {
         ResourceDO rDO=getPersistedResourceObject(resUsername,ti);
         if (rDO!=null) {
            ProcessRequesterDO[] DOs=rDO.getProcessRequesterDOArray();
            BigDecimal rOId = rDO.get_OId().toBigDecimal();
            List list = new ArrayList(Arrays.asList(DOs));
            // remove DOs that also have activity requester
            if (DOs!=null) {
               for (int i=0; i<DOs.length; i++) {
                  if (DOs[i].getActivityRequester()!=null) {
                     list.remove(DOs[i]);
                  }
               }
            }

            for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.ProcessRequesterDO");
                 it.hasNext();) {
               ProcessRequesterDO o = (ProcessRequesterDO)it.next();
               if (rOId.equals(o.oid_getResourceRequester())) {
                  int index = list.indexOf(o);
                  if (-1 != index)
                     list.remove(o);
               }
            }
            for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.ProcessRequesterDO");
                 it.hasNext();) {
               ProcessRequesterDO o = (ProcessRequesterDO)it.next();
               if (rOId.equals(o.oid_getResourceRequester()) && o.getActivityRequester()==null) {
                  int index = list.indexOf(o);
                  if (-1 == index)
                     list.add(o);
                  else {
                     list.set(index, o);
                  }
               }
            }
            DOs = new ProcessRequesterDO[list.size()];
            list.toArray(DOs);
            for (int i=0;i<DOs.length; i++) {
               ret.add(DOs[i].getId());
            }
         }
         return ret;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   public List getAndJoinEntries (String procId,
                                  String asDefId,
                                  String aDefId,
                                  SharkTransaction ti) throws PersistenceException {
      List ret=new ArrayList();
      try {
         AndJoinEntryDO[] DOs=getPersistedAndJoinEntries(procId,asDefId,aDefId,ti);
         if (DOs!=null) {
            for (int i=0;i<DOs.length; i++) {
               AndJoinEntryDO DO=DOs[i];
               AndJoinEntryInterface aj=new DODSAndJoinEntry();
               // restore AndJoinEntry
               aj.setProcessId(DO.getProcess().getId());
               aj.setActivitySetDefinitionId(DO.getActivitySetDefinitionId());
               aj.setActivityDefinitionId(DO.getActivityDefinitionId());
               aj.setActivityId(DO.getActivity().getId());
               ((SharkDODSTransaction)ti)._read(DO);
               ret.add(aj);
            }
         }

         return ret;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   public int howManyAndJoinEntries (String procId,
                                     String asDefId,
                                     String aDefId,
                                     SharkTransaction ti) throws PersistenceException {
      try {
         AndJoinEntryDO[] DOs=getPersistedAndJoinEntries(procId,asDefId,aDefId,ti);
         if (DOs!=null) {
            return DOs.length;
         } else {
            return 0;
         }
      } catch (Throwable tr) {
         throw new PersistenceException(tr);
      }
   }

   public int getExecuteCount(String procId,
                              String asDefId,
                              String aDefId,
                              SharkTransaction ti) throws PersistenceException {
      ActivityQuery qry = new ActivityQuery(((SharkDODSTransaction)ti).getDODSTransaction());
      try {
         qry.setQueryProcess(getPersistedProcessObject(procId, ti));
         qry.setQueryActivitySetDefinitionId(asDefId);
         qry.setQueryActivityDefinitionId(aDefId);
         return qry.getCount();
      } catch (Throwable e) {
         throw new PersistenceException(e);
      }
   }

   public List getAllDeadlinesForProcess (String procId,
                                          SharkTransaction ti) throws PersistenceException {
      List ret=new ArrayList();
      try {
         DeadlineDO[] DOs=getPersistedDeadlines(procId,-1,ti);
         if (DOs!=null) {
            for (int i=0;i<DOs.length; i++) {
               DeadlineDO DO=DOs[i];
               DeadlinePersistenceInterface dl=new DODSDeadline();
               // restore Deadline
               dl.setProcessId(procId);
               dl.setActivityId(DO.getActivity().getId());
               dl.setExceptionName(DO.getExceptionName());
               dl.setSynchronous(DO.getIsSynchronous());
               dl.setExecuted(DO.getIsExecuted());
               dl.setUniqueId(DO.getCNT().toString());
               dl.setTimeLimit(DO.getTimeLimit());
               ((SharkDODSTransaction)ti)._read(DO);
               ret.add(dl);
            }
         }

         return ret;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   public List getAllDeadlinesForProcess (String procId,
                                          long timeLimitBoundary,
                                          SharkTransaction ti) throws PersistenceException {
      List ret=new ArrayList();
      try {
         DeadlineDO[] DOs=getPersistedDeadlines(procId,timeLimitBoundary,ti);
         if (DOs!=null) {
            for (int i=0;i<DOs.length; i++) {
               DeadlineDO DO=DOs[i];
               DeadlinePersistenceInterface dl=new DODSDeadline();
               // restore Deadline
               dl.setProcessId(procId);
               dl.setActivityId(DO.getActivity().getId());
               dl.setExceptionName(DO.getExceptionName());
               dl.setSynchronous(DO.getIsSynchronous());
               dl.setExecuted(DO.getIsExecuted());
               dl.setUniqueId(DO.getCNT().toString());
               dl.setTimeLimit(DO.getTimeLimit());
               ((SharkDODSTransaction)ti)._read(DO);
               ret.add(dl);
            }
         }

         return ret;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   public List getAllDeadlinesForActivity (String procId,
                                           String actId,
                                           SharkTransaction ti) throws PersistenceException {
      List ret=new ArrayList();
      try {
         DeadlineDO[] DOs=getActivitiesPersistedDeadlines(actId,-1,ti);
         if (DOs!=null) {
            for (int i=0;i<DOs.length; i++) {
               DeadlineDO DO=DOs[i];
               DeadlinePersistenceInterface dl=new DODSDeadline();
               // restore Deadline
               dl.setProcessId(procId);
               dl.setActivityId(actId);
               dl.setExceptionName(DO.getExceptionName());
               dl.setSynchronous(DO.getIsSynchronous());
               dl.setExecuted(DO.getIsExecuted());
               dl.setUniqueId(DO.getCNT().toString());
               dl.setTimeLimit(DO.getTimeLimit());
               ((SharkDODSTransaction)ti)._read(DO);
               ret.add(dl);
            }
         }

         return ret;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   public List getAllDeadlinesForActivity (String procId,
                                           String actId,
                                           long timeLimitBoundary,
                                           SharkTransaction ti) throws PersistenceException {
      List ret=new ArrayList();
      try {
         DeadlineDO[] DOs=getActivitiesPersistedDeadlines(actId,timeLimitBoundary,ti);
         if (DOs!=null) {
            for (int i=0;i<DOs.length; i++) {
               DeadlineDO DO=DOs[i];
               DeadlinePersistenceInterface dl=new DODSDeadline();
               // restore Deadline
               dl.setProcessId(procId);
               dl.setActivityId(actId);
               dl.setExceptionName(DO.getExceptionName());
               dl.setSynchronous(DO.getIsSynchronous());
               dl.setExecuted(DO.getIsExecuted());
               dl.setUniqueId(DO.getCNT().toString());
               dl.setTimeLimit(DO.getTimeLimit());
               ((SharkDODSTransaction)ti)._read(DO);
               ret.add(dl);
            }
         }

         return ret;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   public List getAllIdsForProcessesWithExpiriedDeadlines (long timeLimitBoundary,SharkTransaction ti) throws PersistenceException {
      List ret=new ArrayList();
      try {
         BigDecimal procOpenRunningState=(BigDecimal)_prStates.get("open.running");
         BigDecimal actNotStartedState=(BigDecimal)_acStates.get("open.not_running.not_started");
         BigDecimal actRunningState=(BigDecimal)_acStates.get("open.running");
         String oidCol=ProcessDO.get_OIdColumnName();
         String sqlWherePS="Processes.State="+procOpenRunningState;
         String sqlWhereLT="Processes.Id NOT IN (SELECT LockTable.Id FROM LockTable)";
         String sqlWhereDL="Processes."+oidCol+" IN (SELECT Deadlines.Process FROM Deadlines WHERE Deadlines.TimeLimit < "+timeLimitBoundary+" AND (SELECT COUNT(*) AS counter FROM Activities WHERE Activities."+oidCol+"=Deadlines.Activity AND (Activities.State="+actNotStartedState+" OR Activities.State="+actRunningState+"))>0)";
         ProcessDO[] DOs=null;
         ProcessQuery query = null;
         query=new ProcessQuery(((SharkDODSTransaction)ti).getDODSTransaction());
         QueryBuilder qb=qb=query.getQueryBuilder();
         qb.addWhere(sqlWherePS);
         qb.addWhere(sqlWhereLT);
         qb.addWhere(sqlWhereDL);
         //System.out.println("SQLWP="+qb.getSQLwithParms());
         DOs = query.getDOArray();
         if (DOs!=null) {
            for (int i=0;i<DOs.length; i++) {
               ret.add(DOs[i].getId());
            }
         }

      } catch (Throwable t) {
         throw new PersistenceException(t);
      }
      return ret;
   }

   protected ProcessDefinitionDO getPersistedObject (ProcessMgrPersistenceInterface mgr,SharkTransaction ti) throws PersistenceException {
      ProcessDefinitionDO DO=getPersistedProcessMgrObject(mgr.getName(),ti);

      try {
         if (DO==null) {
            DO=ProcessDefinitionDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         }
         return DO;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }

   }

   protected ProcessDefinitionDO getPersistedProcessMgrObject (String name,SharkTransaction ti) throws PersistenceException {
      ProcessDefinitionDO DO=null;
      ProcessDefinitionQuery query = null;
      try {
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.ProcessDefinitionDO");
              it.hasNext();) {
            DO = (ProcessDefinitionDO)it.next();
            if (name.equals(DO.getName()))
               return DO;
         }
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.ProcessDefinitionDO");
              it.hasNext();) {
            DO = (ProcessDefinitionDO)it.next();
            if (name.equals(DO.getName()))
               return null;
         }
         query=new ProcessDefinitionQuery(((SharkDODSTransaction)ti).getDODSTransaction());
         //set query
         query.setQueryName(name);
         // Throw an exception if more than one object is found
         query.requireUniqueInstance();
         DO = query.getNextDO();
         ((SharkDODSTransaction)ti)._read(DO);
         return DO;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   protected ProcessDefinitionDO[] getAllPersistedProcessMgrs (SharkTransaction ti,String pkgId,String pDefId,String pkgVersion, String where) throws PersistenceException {
      ProcessDefinitionDO[] DOs=null;

      ProcessDefinitionQuery query = null;
      try {
         query=new ProcessDefinitionQuery(((SharkDODSTransaction)ti).getDODSTransaction());
         if (pkgId!=null) {
            query.setQueryPackageId(pkgId);
            if (pkgVersion!=null) {
               query.setQueryProcessDefinitionVersion(pkgVersion);
            }
            if (pDefId!=null) {
               query.setQueryProcessDefinitionId(pDefId);
            }
         } else if (null != where) {
            query.getQueryBuilder().addWhere(where);
         }
         DOs = query.getDOArray();
         List list = new ArrayList(Arrays.asList(DOs));
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.ProcessDefinitionDO");
              it.hasNext();) {
            ProcessDefinitionDO o = (ProcessDefinitionDO)it.next();
            if (pkgId!=null) {
               if(!pkgId.equals(o.getPackageId())) continue;
               if (pkgVersion!=null && !pkgVersion.equals(o.getProcessDefinitionVersion())) continue;
               if (pDefId!=null && !pDefId.equals(o.getProcessDefinitionId())) continue;
            }

            int index = list.indexOf(o);
            if (-1 != index)
               list.remove(o);
         }
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.ProcessDefinitionDO");
              it.hasNext();) {
            ProcessDefinitionDO o = (ProcessDefinitionDO)it.next();
            if (pkgId!=null) {
               if(!pkgId.equals(o.getPackageId())) continue;
               if (pkgVersion!=null && !pkgVersion.equals(o.getProcessDefinitionVersion())) continue;
               if (pDefId!=null && !pDefId.equals(o.getProcessDefinitionId())) continue;
            }

            int index = list.indexOf(o);
            if (-1 == index)
               list.add(o);
            else {
               list.set(index, o);
            }
            //if (!list.contains(o))
            //   list.add(o);
         }
         DOs = new ProcessDefinitionDO[list.size()];
         list.toArray(DOs);
         return DOs;
      } catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   protected ProcessDO getPersistedObject (ProcessPersistenceInterface proc,SharkTransaction ti) throws PersistenceException {
      ProcessDO DO=getPersistedProcessObject(proc.getId(),ti);

      try {
         if (DO==null) {
            DO=ProcessDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         }
         return DO;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   // type=-1 -> closed.xxx
   // type=0 -> all
   // type=1 -> open.running
   //
   protected ProcessDO[] getPersistedProcesses(SharkTransaction ti,
                                               int type,
                                               String pkgId,
                                               String pDefId,
                                               String pkgVer,
                                               java.util.Date lastStateTimeBoundary,
                                               String sqlWhere) throws PersistenceException {

      ProcessDefinitionDO[] pdefDOs=null;
      if (pkgId!=null) {
         pdefDOs = getAllPersistedProcessMgrs(ti, pkgId, pDefId, pkgVer, null);
      }

      long lstb=-1;
      if (lastStateTimeBoundary!=null) {
         lstb=lastStateTimeBoundary.getTime();
      }

      ProcessDO[] DOs=null;
      ProcessQuery query = null;
      try {
         query=new ProcessQuery(((SharkDODSTransaction)ti).getDODSTransaction());
         BigDecimal openRunningState=null;
         QueryBuilder qb=qb=query.getQueryBuilder();
         if (lastStateTimeBoundary!=null) {
            qb.addWhere(ProcessDO.LastStateTime,lstb,QueryBuilder.LESS_THAN);
         }
         if (pkgId!=null) {
            if (pdefDOs!=null && pdefDOs.length>0) {
               qb.addWhereOpenParen();
               for (int i=0; i<pdefDOs.length; i++) {
                  qb.addWhere(ProcessDO.ProcessDefinition,
                              pdefDOs[i].get_OId().toBigDecimal(),
                              QueryBuilder.EQUAL);
                  if (i<pdefDOs.length-1) {
                     qb.addWhereOr();
                  }

               }
               qb.addWhereCloseParen();
            }

         }

         if (type==1) {
            openRunningState=(BigDecimal)_prStates.get("open.running");
            qb.addWhere(ProcessDO.State,openRunningState,QueryBuilder.EQUAL);
         } else if (type==-1) {
            qb.addWhereOpenParen();
            for (int i=0; i<_prClosedStatesBigDecimals.size(); i++) {
               qb.addWhere(ProcessDO.State,
                              (BigDecimal)_prClosedStatesBigDecimals.get(i),
                           QueryBuilder.EQUAL);
               if (i<_prClosedStatesBigDecimals.size()-1) {
                  qb.addWhereOr();
               }
            }
            qb.addWhereCloseParen();
         }
         if (null != sqlWhere) {
            qb.addWhere(sqlWhere);
         }
         DOs = query.getDOArray();
         List list = new ArrayList(Arrays.asList(DOs));

         for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.ProcessDO");
              it.hasNext();) {
            ProcessDO o = (ProcessDO)it.next();
            if (type==1 && !o.oid_getState().equals(openRunningState)) continue;
            if (type==-1 && !_prClosedStatesBigDecimals.contains(o.oid_getState())) continue;
            if (lastStateTimeBoundary!=null && o.getLastStateTime()>lstb) continue;
            if (pkgId!=null) {
               ProcessDefinitionDO pDefDO=o.getProcessDefinition();
               if (!pkgId.equals(pDefDO.getPackageId())) continue;
               if (pDefId!=null && !pDefId.equals(pDefDO.getProcessDefinitionId())) continue;
            }
            int index = list.indexOf(o);
            if (-1 != index)
               list.remove(o);
         }
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.ProcessDO");
              it.hasNext();) {
            ProcessDO o = (ProcessDO)it.next();
            if (type==1 && !o.oid_getState().equals(openRunningState)) continue;
            if (type==-1 && !_prClosedStatesBigDecimals.contains(o.oid_getState())) continue;
            if (lastStateTimeBoundary!=null && o.getLastStateTime()>lstb) continue;
            if (pkgId!=null) {
               ProcessDefinitionDO pDefDO=o.getProcessDefinition();
               if (!pkgId.equals(pDefDO.getPackageId())) continue;
               if (pDefId!=null && !pDefId.equals(pDefDO.getProcessDefinitionId())) continue;
            }
            int index = list.indexOf(o);
            if (-1 == index)
               list.add(o);
            else {
               list.set(index, o);
            }
            //if (!list.contains(o))
            //   list.add(o);
         }
         DOs = new ProcessDO[list.size()];
         list.toArray(DOs);
         return DOs;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   protected ProcessDO getPersistedProcessObject (String procId,SharkTransaction ti) throws PersistenceException {
      ProcessDO DO=null;

      ProcessQuery query = null;
      try {
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.ProcessDO");
              it.hasNext();) {
            DO = (ProcessDO)it.next();
            if (procId.equals(DO.getId()))
               return DO;
         }
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.ProcessDO");
              it.hasNext();) {
            DO = (ProcessDO)it.next();
            if (procId.equals(DO.getId()))
               return null;
         }
         query=new ProcessQuery(((SharkDODSTransaction)ti).getDODSTransaction());
         //set query
         query.setQueryId(procId);
         // Throw an exception if more than one object is found
         query.requireUniqueInstance();
         DO = query.getNextDO();
         ((SharkDODSTransaction)ti)._read(DO);
         return DO;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   protected ProcessRequesterDO getProcessRequester(String procId,SharkTransaction ti) throws PersistenceException {
      ProcessRequesterDO DO=getPersistedRequesterObject(procId,ti);
      try {
         if (null == DO) {
            DO=ProcessRequesterDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         }
         return DO;
      } catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   protected ProcessRequesterDO getPersistedRequesterObject (String procId,SharkTransaction ti) throws PersistenceException {
      ProcessRequesterDO DO=null;
      ProcessRequesterQuery query = null;
      try {
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.ProcessRequesterDO");
              it.hasNext();) {
            DO = (ProcessRequesterDO)it.next();
            if (procId.equals(DO.getId()))
               return DO;
         }
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.ProcessRequesterDO");
              it.hasNext();) {
            DO = (ProcessRequesterDO)it.next();
            if (procId.equals(DO.getId()))
               return null;
         }
         query=new ProcessRequesterQuery(((SharkDODSTransaction)ti).getDODSTransaction());
         //set query
         query.setQueryId(procId);
         // Throw an exception if more than one object is found
         query.requireUniqueInstance();
         DO = query.getNextDO();
         ((SharkDODSTransaction)ti)._read(DO);
         return DO;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   /*protected ProcessRequesterDO getProcessRequesterByActId (String actId,SharkTransaction ti) throws PersistenceException {
    ProcessRequesterDO DO=null;
    ProcessRequesterQuery query = null;
    try {
    for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.ProcessRequesterDO");
    it.hasNext();) {
    DO = (ProcessRequesterDO)it.next();
    ActivityDO aDO=DO.getActivityRequester();
    if (aDO!=null && actId.equals(aDO.getId()))
    return DO;
    }
    return null;
    }
    catch (Throwable t) {
    throw new PersistenceException(t);
    }
    }*/

   protected ActivityDO getPersistedObject (ActivityPersistenceInterface act,SharkTransaction ti) throws PersistenceException {
      //if (act.getActivityDefinitionId().equals("seeFirstRes")) System.out.println("GPO1");
      ActivityDO DO=getPersistedActivityObject(act.getId(),ti);
      //if (act.getActivityDefinitionId().equals("seeFirstRes")) System.out.println("GPO2");

      try {
         if (DO==null) {
            DO=ActivityDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         }
         return DO;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   // type=-1 -> closed.xxx
   // type=0 -> all
   // type=1 -> open.xxx
//
   protected ActivityDO[] getPersistedActivities (SharkTransaction ti,int type,BigDecimal procOId, String sqlWhere) throws PersistenceException {

      ActivityDO[] DOs=null;
      ActivityQuery query = null;
      try {
         query=new ActivityQuery(((SharkDODSTransaction)ti).getDODSTransaction());
         QueryBuilder qb=qb=query.getQueryBuilder();

         if (procOId!=null) {
            qb.addWhere(ActivityDO.Process,procOId,QueryBuilder.EQUAL);
         }

         if (sqlWhere!=null) {
            qb.addWhere(sqlWhere);
         }

         if (type==1) {
            qb.addWhereOpenParen();
            for (int i=0; i<_actOpenStatesBigDecimals.size(); i++) {
               qb.addWhere(ActivityDO.State,
                              (BigDecimal)_actOpenStatesBigDecimals.get(i),
                           QueryBuilder.EQUAL);
               if (i<_actOpenStatesBigDecimals.size()-1) {
                  qb.addWhereOr();
               }
            }
            qb.addWhereCloseParen();
         } else if (type==-1) {
            qb.addWhereOpenParen();
            for (int i=0; i<_actClosedStatesBigDecimals.size(); i++) {
               qb.addWhere(ActivityDO.State,
                              (BigDecimal)_actClosedStatesBigDecimals.get(i),
                           QueryBuilder.EQUAL);
               if (i<_actClosedStatesBigDecimals.size()-1) {
                  qb.addWhereOr();
               }
            }
            qb.addWhereCloseParen();
         }
         DOs = query.getDOArray();
         List list = new ArrayList(Arrays.asList(DOs));
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.ActivityDO");
              it.hasNext();) {
            ActivityDO o = (ActivityDO)it.next();
            if (procOId!=null && !o.oid_getProcess().equals(procOId)) continue;
            if (type==1 && !_actOpenStatesBigDecimals.contains(o.oid_getState())) continue;
            if (type==-1 && !_actClosedStatesBigDecimals.contains(o.oid_getState())) continue;
            int index = list.indexOf(o);
            if (-1 != index)
               list.remove(o);
         }
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.ActivityDO");
              it.hasNext();) {
            ActivityDO o = (ActivityDO)it.next();
            if (procOId!=null && !o.oid_getProcess().equals(procOId)) continue;
            if (type==1 && !_actOpenStatesBigDecimals.contains(o.oid_getState())) {
               list.remove(o);
               continue;
            }
            if (type==-1 && !_actClosedStatesBigDecimals.contains(o.oid_getState())) continue;
            int index = list.indexOf(o);
            if (-1 == index) {
               list.add(o);
            }
            else {
               list.set(index, o);
            }
            //if (!list.contains(o))
            //   list.add(o);
         }
         DOs = new ActivityDO[list.size()];
         list.toArray(DOs);
         return DOs;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   protected ActivityDO getPersistedActivityObject (String actId,SharkTransaction ti) throws PersistenceException {
      if (null == actId)
         return null;

      ActivityDO DO=null;
      ActivityQuery query = null;

      try {
         //System.err.print("getPersistedActivityObject:"+actId+""+ti+"[");
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.ActivityDO");
              it.hasNext();) {
            DO = (ActivityDO)it.next();
            //System.err.print("\n\t"+DO.getId()+"("+DO.getState().getKeyValue()+")");
            if (actId.equals(DO.getId())) {
               //System.err.println("<<<]");
               return DO;
            }
         }
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.ActivityDO");
              it.hasNext();) {
            DO = (ActivityDO)it.next();
            //System.err.print("\n\t"+DO.getId()+"("+DO.getState().getKeyValue()+")");
            if (actId.equals(DO.getId())) {
               //System.err.println("<<<]");
               return null;
            }
         }
         //System.err.println("]");
         //if (actId!=null && actId.equals("3_1_test_testSubflow")) System.out.println("GPAO1");
         query=new ActivityQuery(((SharkDODSTransaction)ti).getDODSTransaction());
         //if (actId!=null && actId.equals("3_1_test_testSubflow")) System.out.println("GPAO2");
         //set query
         query.setQueryId(actId);
         //if (actId!=null && actId.equals("3_1_test_testSubflow")) System.out.println("GPAO3");
         // Throw an exception if more than one object is found
         query.requireUniqueInstance();
         //if (actId!=null && actId.equals("3_1_test_testSubflow")) System.out.println("GPAO4");

         DO = query.getNextDO();
         //if (actId!=null && actId.equals("3_1_test_testSubflow")) System.out.println("GPAO5");
         ((SharkDODSTransaction)ti)._read(DO);
         return DO;
      }
      catch (Throwable t) {
         //t.printStackTrace();
         throw new PersistenceException(t);
      }
   }

   protected ResourceDO getPersistedObject (ResourcePersistenceInterface res,SharkTransaction ti) throws PersistenceException {
      ResourceDO DO=getPersistedResourceObject(res.getUsername(),ti);

      try {
         if (DO==null) {
            DO=ResourceDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         }
         return DO;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   protected ResourceDO getPersistedResourceObject (String username,SharkTransaction ti) throws PersistenceException {
      if (null == username)
         return null;
      ResourceDO DO=null;
      ResourceQuery query = null;
      try {
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.ResourceDO");
              it.hasNext();) {
            DO = (ResourceDO)it.next();
            if (username.equals(DO.getUsername()))
               return DO;
         }
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.ResourceDO");
              it.hasNext();) {
            DO = (ResourceDO)it.next();
            if (username.equals(DO.getUsername()))
               return null;
         }
         query=new ResourceQuery(((SharkDODSTransaction)ti).getDODSTransaction());
         //set query
         query.setQueryUsername(username);
         // Throw an exception if more than one object is found
         query.requireUniqueInstance();
         DO = query.getNextDO();
         ((SharkDODSTransaction)ti)._read(DO);

         return DO;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   protected ResourceDO[] getAllPersistedResources (SharkTransaction ti, String sqlWhere) throws PersistenceException {
      ResourceDO[] DOs=null;

      ResourceQuery query = null;
      try {
         query=new ResourceQuery(((SharkDODSTransaction)ti).getDODSTransaction());
         if (null != sqlWhere) {
            query.getQueryBuilder().addWhere(sqlWhere);
         }
         DOs = query.getDOArray();
         List list = new ArrayList(Arrays.asList(DOs));
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.ResourceDO");
              it.hasNext();) {
            Object o = it.next();
            int index = list.indexOf(o);
            if (-1 != index)
               list.remove(o);
         }
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.ResourceDO");
              it.hasNext();) {
            Object o = it.next();
            int index = list.indexOf(o);
            if (-1 == index)
               list.add(o);
            else {
               list.set(index, o);
            }
            //if (!list.contains(o))
            //   list.add(o);
         }
         DOs = new ResourceDO[list.size()];
         list.toArray(DOs);
         return DOs;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   protected AssignmentDO getPersistedObject (AssignmentPersistenceInterface ass,SharkTransaction ti) throws PersistenceException {
      AssignmentDO DO=getPersistedAssignmentObject(ass.getActivityId(),ass.getResourceUsername(),ti);

      try {
         if (DO==null) {
            DO=AssignmentDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         }
         return DO;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   protected AssignmentDO getPersistedAssignmentObject (String actId,String username,SharkTransaction ti) throws PersistenceException {
      AssignmentDO DO=null;

      //System.out.println("Searching for ass["+actId+","+username+"]");

      AssignmentQuery query = null;
      try {
         //ResourceDO rDO = getPersistedResourceObject(username,ti);
         //ActivityDO aDO = getPersistedActivityObject(actId,ti);
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.AssignmentDO");
              it.hasNext();) {
            DO = (AssignmentDO)it.next();
            if (username.equals(DO.getResourceId())
                && actId.equals(DO.getActivity().getId())) {
               //System.out.println("DO from Buffer:"+DO);
               return DO;
            }
         }
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.AssignmentDO");
              it.hasNext();) {
            DO = (AssignmentDO)it.next();
            if (username.equals(DO.getResourceId())
                && actId.equals(DO.getActivityId())) {
               //System.out.println("DO from Deleted Buffer:"+DO);
               return null;
            }
         }
         DBTransaction dbt = ((SharkDODSTransaction)ti).getDODSTransaction();
         query=new AssignmentQuery(dbt);
         //set query
         query.setQueryActivityId(actId);
         query.setQueryResourceId(username);
         // Throw an exception if more than one object is found
         query.requireUniqueInstance();
         DO = query.getNextDO();
         ((SharkDODSTransaction)ti)._read(DO);

         //System.out.println("RDO from DB:"+DO);
         return DO;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   protected AssignmentDO[] getAllPersistedAssignments (SharkTransaction ti, String sqlWhere) throws PersistenceException {
      AssignmentDO[] DOs=null;

      AssignmentQuery query = null;
      try {
         query=new AssignmentQuery(((SharkDODSTransaction)ti).getDODSTransaction());
         if (null != sqlWhere) {
            query.getQueryBuilder().addWhere(sqlWhere);
         }
         DOs = query.getDOArray();
         List list = new ArrayList(Arrays.asList(DOs));
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.AssignmentDO");
              it.hasNext();) {
            Object o = it.next();
            int index = list.indexOf(o);
            if (-1 != index)
               list.remove(o);
         }
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.AssignmentDO");
              it.hasNext();) {
            Object o = it.next();
            int index = list.indexOf(o);
            if (-1 == index)
               list.add(o);
            else {
               list.set(index, o);
            }
            //if (!list.contains(o))
            //   list.add(o);
         }
         DOs = new AssignmentDO[list.size()];
         list.toArray(DOs);
         return DOs;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   protected AndJoinEntryDO[] getPersistedAndJoinEntries (String procId,
                                                          String asDefId,
                                                          String aDefId,
                                                          SharkTransaction ti) throws PersistenceException {
      AndJoinEntryDO[] DOs=null;

      AndJoinEntryQuery query = null;
      try {
         query=new AndJoinEntryQuery(((SharkDODSTransaction)ti).getDODSTransaction());
         query.setQueryProcess(getPersistedProcessObject(procId,ti));
         query.setQueryActivitySetDefinitionId(asDefId);
         query.setQueryActivityDefinitionId(aDefId);
         DOs = query.getDOArray();
         List list = new ArrayList(Arrays.asList(DOs));
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.AndJoinEntryDO");
              it.hasNext();) {
            AndJoinEntryDO ajedo = (AndJoinEntryDO)it.next();
            int index = list.indexOf(ajedo);
            if (((asDefId==null && ajedo.getActivitySetDefinitionId()==null) || (asDefId!=null && asDefId.equals(ajedo.getActivitySetDefinitionId())))
                && (aDefId.equals(ajedo.getActivityDefinitionId()))
                && procId.equals(ajedo.getProcess().getId())) {
               if (-1 != index)
                  list.remove(ajedo);
            }
         }
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.AndJoinEntryDO");
              it.hasNext();) {
            AndJoinEntryDO ajedo = (AndJoinEntryDO)it.next();
            int index = list.indexOf(ajedo);
            if (((asDefId==null && ajedo.getActivitySetDefinitionId()==null) || (asDefId!=null && asDefId.equals(ajedo.getActivitySetDefinitionId())))
                && (aDefId.equals(ajedo.getActivityDefinitionId()))
                && procId.equals(ajedo.getProcess().getId())) {
               if (-1 == index)
                  list.add(ajedo);
               else {
                  list.set(index, ajedo);
               }
            }
         }
         DOs = new AndJoinEntryDO[list.size()];
         list.toArray(DOs);
         return DOs;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

      protected DeadlineDO[] getPersistedDeadlines (String procId,long timeLimitBoundary,SharkTransaction ti) throws PersistenceException {
      DeadlineDO[] DOs=null;

      DeadlineQuery query = null;
      try {
         query=new DeadlineQuery(((SharkDODSTransaction)ti).getDODSTransaction());
         query.setQueryProcess(getPersistedProcessObject(procId,ti));
         if (timeLimitBoundary>=0) {
            query.setQueryTimeLimit(timeLimitBoundary,QueryBuilder.LESS_THAN);
         }
         DOs = query.getDOArray();
         List list = new ArrayList(Arrays.asList(DOs));
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.DeadlineDO");
              it.hasNext();) {
            DeadlineDO ddo = (DeadlineDO)it.next();
            int index = list.indexOf(ddo);
            if (procId.equals(ddo.getProcess().getId())) {
               if (-1 != index)
                  list.remove(ddo);
            }
         }
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.DeadlineDO");
              it.hasNext();) {
            DeadlineDO ddo = (DeadlineDO)it.next();
            int index = list.indexOf(ddo);
            if (procId.equals(ddo.getProcess().getId())) {
               if (-1 == index)
                  list.add(ddo);
               else {
                  list.set(index, ddo);
               }
            }
         }
         DOs = new DeadlineDO[list.size()];
         list.toArray(DOs);
         return DOs;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   protected DeadlineDO[] getActivitiesPersistedDeadlines (String actId,long timeLimitBoundary,SharkTransaction ti) throws PersistenceException {
      DeadlineDO[] DOs=null;

      DeadlineQuery query = null;
      try {
         query=new DeadlineQuery(((SharkDODSTransaction)ti).getDODSTransaction());
         query.setQueryActivity(getPersistedActivityObject(actId,ti));
         if (timeLimitBoundary>=0) {
            query.setQueryTimeLimit(timeLimitBoundary,QueryBuilder.LESS_THAN);
         }
         DOs = query.getDOArray();
         List list = new ArrayList(Arrays.asList(DOs));
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.DeadlineDO");
              it.hasNext();) {
            DeadlineDO ddo = (DeadlineDO)it.next();
            int index = list.indexOf(ddo);
            if (actId.equals(ddo.getActivity().getId())) {
               if (-1 != index)
                  list.remove(ddo);
            }
         }
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.DeadlineDO");
              it.hasNext();) {
            DeadlineDO ddo = (DeadlineDO)it.next();
            int index = list.indexOf(ddo);
            if (actId.equals(ddo.getActivity().getId())) {
               if (-1 == index)
                  list.add(ddo);
               else {
                  list.set(index, ddo);
               }
            }
         }
         DOs = new DeadlineDO[list.size()];
         list.toArray(DOs);
         return DOs;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   protected DeadlineDO getPersistedObject (DeadlinePersistenceInterface dpe,SharkTransaction ti) throws PersistenceException {
      DeadlineDO DO=getPersistedDeadlineObject(dpe.getUniqueId(),ti);

      try {
         if (DO==null) {
            DO=DeadlineDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         }
         return DO;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   protected DeadlineDO getPersistedDeadlineObject (String uniqueId,SharkTransaction ti) throws PersistenceException {

      if (uniqueId==null) return null;

      DeadlineDO DO=null;
      DeadlineQuery query = null;
      try {
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.DeadlineDO");
              it.hasNext();) {
            DO = (DeadlineDO)it.next();
            if (uniqueId.equals(DO.getCNT()))
               return DO;
         }
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.DeadlineDO");
              it.hasNext();) {
            DO = (DeadlineDO)it.next();
            if (uniqueId.equals(DO.getCNT()))
               return null;
         }
         query=new DeadlineQuery(((SharkDODSTransaction)ti).getDODSTransaction());
         //set query
         query.setQueryCNT(new BigDecimal(uniqueId));
         // Throw an exception if more than one object is found
         query.requireUniqueInstance();
         DO = query.getNextDO();
         ((SharkDODSTransaction)ti)._read(DO);

         return DO;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   protected ProcessDataDO getPersistedObject (ProcessVariablePersistenceInterface var,SharkTransaction ti) throws PersistenceException {
      ProcessDataDO DO=
         getPersistedProcessVariableObject(var.getProcessId(),var.getDefinitionId(),ti);

      try {
         if (DO==null) {
            DO=ProcessDataDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         }
         return DO;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   protected ProcessDataDO getPersistedProcessVariableObject (String procId,
                                                              String defId,SharkTransaction ti) throws PersistenceException {
      ProcessDataDO DO=null;

      ProcessDataQuery query = null;
      try {
         ProcessDO pDO=getPersistedProcessObject(procId,ti);
         if (null != pDO) {
            BigDecimal pOId = pDO.get_OId().toBigDecimal();
            for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.ProcessDataDO");
                 it.hasNext();) {
               DO = (ProcessDataDO)it.next();
               if (defId.equals(DO.getVariableDefinitionId())
                   && pOId.equals(DO.oid_getProcess()))
                  return DO;
            }
            for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.ProcessDataDO");
                 it.hasNext();) {
               DO = (ProcessDataDO)it.next();
               if (defId.equals(DO.getVariableDefinitionId())
                   && pOId.equals(DO.oid_getProcess()))
                  return null;
            }
         }
         query=new ProcessDataQuery(((SharkDODSTransaction)ti).getDODSTransaction());
         //set query
         query.setQueryProcess(pDO);
         query.setQueryVariableDefinitionId(defId);
         // Throw an exception if more than one object is found
         query.requireUniqueInstance();
         DO = query.getNextDO();
         ((SharkDODSTransaction)ti)._read(DO);

         return DO;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }
   
   protected ProcessDataWOBDO getPersistedObject2 (ProcessVariablePersistenceInterface var,SharkTransaction ti) throws PersistenceException {
      ProcessDataWOBDO DO=
         getPersistedProcessVariableObject2(var.getProcessId(),var.getDefinitionId(),ti);

      try {
         if (DO==null) {
            DO=ProcessDataWOBDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         }
         return DO;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   protected ProcessDataWOBDO getPersistedProcessVariableObject2 (String procId,
                                                              String defId,SharkTransaction ti) throws PersistenceException {
      ProcessDataWOBDO DO=null;

      try {
         ProcessDO pDO=getPersistedProcessObject(procId,ti);
         if (null != pDO) {
            BigDecimal pOId = pDO.get_OId().toBigDecimal();
            for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.ProcessDataWOBDO");
                 it.hasNext();) {
               DO = (ProcessDataWOBDO)it.next();
               if (defId.equals(DO.getVariableDefinitionId())
                   && pOId.equals(DO.oid_getProcess()))
                  return DO;
            }
            for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.ProcessDataDO");
                 it.hasNext();) {
               DO = (ProcessDataWOBDO)it.next();
               if (defId.equals(DO.getVariableDefinitionId())
                   && pOId.equals(DO.oid_getProcess()))
                  return null;
            }
         }
         ProcessDataWOBQuery query=new ProcessDataWOBQuery(((SharkDODSTransaction)ti).getDODSTransaction());
         //set query
         query.setQueryProcess(pDO);
         query.setQueryVariableDefinitionId(defId);
         // Throw an exception if more than one object is found
         query.requireUniqueInstance();
         DO = query.getNextDO();
         ((SharkDODSTransaction)ti)._read(DO);

         return DO;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   protected ProcessDataBLOBDO getPersistedObject (ProcessDataWOBDO pdDO,SharkTransaction ti) throws PersistenceException {
      ProcessDataBLOBDO DO=
         getPersistedProcessVariableBLOBObject(pdDO,ti);

      try {
         if (DO==null) {
            DO=ProcessDataBLOBDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         }
         return DO;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }  

   protected ProcessDataBLOBDO getPersistedProcessVariableBLOBObject (ProcessDataWOBDO pdDO,SharkTransaction ti) throws PersistenceException {
      ProcessDataBLOBDO DO=null;

      try {
         if (null != pdDO) {         
            BigDecimal pdOId = pdDO.get_OId().toBigDecimal();
            for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.ProcessDataBLOBDO");
                 it.hasNext();) {
               DO = (ProcessDataBLOBDO)it.next();
               if (pdOId.equals(DO.oid_getProcessDataWOB()))
                  return DO;
            }
            for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.ProcessDataBLOBDO");
                 it.hasNext();) {
               DO = (ProcessDataBLOBDO)it.next();
               if (pdOId.equals(DO.oid_getProcessDataWOB()))
                  return null;
            }
         }
         ProcessDataBLOBQuery query=new ProcessDataBLOBQuery(((SharkDODSTransaction)ti).getDODSTransaction());
         //set query
         query.setQueryProcessDataWOB(pdDO);
         // Throw an exception if more than one object is found
         query.requireUniqueInstance();
         DO = query.getNextDO();
         ((SharkDODSTransaction)ti)._read(DO);

         return DO;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   protected ActivityDataDO getPersistedObject (ActivityVariablePersistenceInterface var,SharkTransaction ti) throws PersistenceException {
      ActivityDataDO DO=
         getPersistedActivityVariableObject(var.getActivityId(),var.getDefinitionId(),ti);

      try {
         if (DO==null) {
            DO=ActivityDataDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         }
         return DO;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   protected ActivityDataDO getPersistedActivityVariableObject (String actId,
                                                                String defId,SharkTransaction ti) throws PersistenceException {
      ActivityDataDO DO=null;

      ActivityDataQuery query = null;
      try {
         ActivityDO aDO=getPersistedActivityObject(actId,ti);
         if (null != aDO) {
            BigDecimal aOId = aDO.get_OId().toBigDecimal();
            for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.ActivityDataDO");
                 it.hasNext();) {
               DO = (ActivityDataDO)it.next();
               if (defId.equals(DO.getVariableDefinitionId())
                   && aOId.equals(DO.oid_getActivity()))
                  return DO;
            }
            for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.ActivityDataDO");
                 it.hasNext();) {
               DO = (ActivityDataDO)it.next();
               if (defId.equals(DO.getVariableDefinitionId())
                   && aOId.equals(DO.oid_getActivity()))
                  return null;
            }
         }
         query=new ActivityDataQuery(((SharkDODSTransaction)ti).getDODSTransaction());
         //set query

         query.setQueryActivity(aDO);
         query.setQueryVariableDefinitionId(defId);
         // Throw an exception if more than one object is found
         query.requireUniqueInstance();
         DO = query.getNextDO();
         ((SharkDODSTransaction)ti)._read(DO);

         return DO;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   protected ActivityDataWOBDO getPersistedObject2 (ActivityVariablePersistenceInterface var,SharkTransaction ti) throws PersistenceException {
      ActivityDataWOBDO DO=
         getPersistedActivityVariableObject2(var.getActivityId(),var.getDefinitionId(),ti);

      try {
         if (DO==null) {
            DO=ActivityDataWOBDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         }
         return DO;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   protected ActivityDataWOBDO getPersistedActivityVariableObject2 (String actId,
                                                                String defId,SharkTransaction ti) throws PersistenceException {
      ActivityDataWOBDO DO=null;

      try {
         ActivityDO aDO=getPersistedActivityObject(actId,ti);
         if (null != aDO) {
            BigDecimal aOId = aDO.get_OId().toBigDecimal();
            for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.ActivityDataWOBDO");
                 it.hasNext();) {
               DO = (ActivityDataWOBDO)it.next();
               if (defId.equals(DO.getVariableDefinitionId())
                   && aOId.equals(DO.oid_getActivity()))
                  return DO;
            }
            for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.ActivityDataWOBDO");
                 it.hasNext();) {
               DO = (ActivityDataWOBDO)it.next();
               if (defId.equals(DO.getVariableDefinitionId())
                   && aOId.equals(DO.oid_getActivity()))
                  return null;
            }
         }
         ActivityDataWOBQuery query = new ActivityDataWOBQuery(((SharkDODSTransaction)ti).getDODSTransaction());
         //set query

         query.setQueryActivity(aDO);
         query.setQueryVariableDefinitionId(defId);
         // Throw an exception if more than one object is found
         query.requireUniqueInstance();
         DO = query.getNextDO();
         ((SharkDODSTransaction)ti)._read(DO);

         return DO;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   protected ActivityDataBLOBDO getPersistedObject (ActivityDataWOBDO adDO,SharkTransaction ti) throws PersistenceException {
      ActivityDataBLOBDO DO=
         getPersistedActivityVariableBLOBObject(adDO,ti);

      try {
         if (DO==null) {
            DO=ActivityDataBLOBDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         }
         return DO;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }  

   protected ActivityDataBLOBDO getPersistedActivityVariableBLOBObject (ActivityDataWOBDO adDO,SharkTransaction ti) throws PersistenceException {
      ActivityDataBLOBDO DO=null;

      try {
         if (null != adDO) {         
            BigDecimal pdOId = adDO.get_OId().toBigDecimal();
            for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.instancepersistence.data.ActivityDataBLOBDO");
                 it.hasNext();) {
               DO = (ActivityDataBLOBDO)it.next();
               if (pdOId.equals(DO.oid_getActivityDataWOB()))
                  return DO;
            }
            for (Iterator it = ((SharkDODSTransaction)ti).iterator4typeDeleted("class org.enhydra.shark.instancepersistence.data.ActivityDataBLOBDO");
                 it.hasNext();) {
               DO = (ActivityDataBLOBDO)it.next();
               if (pdOId.equals(DO.oid_getActivityDataWOB()))
                  return null;
            }
         }
         ActivityDataBLOBQuery query=new ActivityDataBLOBQuery(((SharkDODSTransaction)ti).getDODSTransaction());
         //set query
         query.setQueryActivityDataWOB(adDO);
         // Throw an exception if more than one object is found
         query.requireUniqueInstance();
         DO = query.getNextDO();
         ((SharkDODSTransaction)ti)._read(DO);

         return DO;
      }
      catch (Throwable t) {
         throw new PersistenceException(t);
      }
   }

   protected ActivityStateDO getPersistedActivityStateObject (
      String value,boolean searchByKeyValue,DBTransaction ti) throws PersistenceException {
      ActivityStateDO DO=null;

      ActivityStateQuery query = null;
      try {
         query=new ActivityStateQuery(ti);
         //set query
         if (searchByKeyValue) {
            query.setQueryKeyValue(value);
         }
         else {
            query.setQueryName(value);
         }
         query.requireUniqueInstance();
         DO = query.getNextDO();

         if (DO==null) {
            DO=ActivityStateDO.createVirgin(ti);
         }
         return DO;
      }
      catch (Throwable t) {
         t.printStackTrace();
         throw new PersistenceException(t);
      }
   }

   protected ProcessStateDO getPersistedProcessStateObject (
      String value,boolean searchByKeyValue,DBTransaction ti) throws PersistenceException {
      ProcessStateDO DO=null;

      ProcessStateQuery query = null;
      try {
         query=new ProcessStateQuery(ti);
         //set query
         if (searchByKeyValue) {
            query.setQueryKeyValue(value);
         }
         else {
            query.setQueryName(value);
         }
         query.requireUniqueInstance();
         DO = query.getNextDO();

         if (DO==null) {
            DO=ProcessStateDO.createVirgin(ti);
         }
         return DO;
      }
      catch (Throwable t) {
         t.printStackTrace();
         throw new PersistenceException(t);
      }
   }

   // creation of empty objects
   public ActivityPersistenceInterface createActivity () {
      return new DODSActivity();
   }

   public ProcessPersistenceInterface createProcess () {
      return new DODSProcess();
   }

   public ProcessMgrPersistenceInterface createProcessMgr () {
      return new DODSProcessMgr();
   }

   public AssignmentPersistenceInterface createAssignment () {
      return new DODSAssignment();
   }

   public ResourcePersistenceInterface createResource () {
      return new DODSResource();
   }

   public ProcessVariablePersistenceInterface createProcessVariable () {
      return new DODSProcessVariable();
   }

   public ActivityVariablePersistenceInterface createActivityVariable () {
      return new DODSActivityVariable();
   }

   public AndJoinEntryInterface createAndJoinEntry () {
      return new DODSAndJoinEntry();
   }

   public DeadlinePersistenceInterface createDeadline() {
      return new DODSDeadline();
   }

   public BigDecimal getNextDecId(String idName) throws PersistenceException {
      try {
         return DODSUtilities.getNext(idName);
      } catch (Exception e) {
         throw new PersistenceException("Couldn't allocate id",e);
      }
   }

   public String getNextId(String idName) throws PersistenceException {
      return getNextDecId(idName).toString();
   }

   public List getProcessMgrsWhere(SharkTransaction ti, String sqlWhere) throws PersistenceException {
      List ret = new ArrayList();

      ProcessDefinitionDO[] mgrs = getAllPersistedProcessMgrs(ti,
                                                              null,
                                                              null,
                                                              null,
                                                              sqlWhere);
      if (mgrs != null) {
         for (int i = 0; i < mgrs.length; i++) {
            ProcessMgrPersistenceInterface mgr = restore(mgrs[i]);
            ret.add(mgr);
         }
      }
      return ret;
   }

   public List getResourcesWhere(SharkTransaction t, String sqlWhere) throws PersistenceException {
      List ret=new ArrayList();
      if (null != sqlWhere && sqlWhere.trim().length() < 1)
         sqlWhere = null;
      ResourceDO[] ress=getAllPersistedResources(t, sqlWhere);
      if (ress!=null) {
         for (int i=0;i<ress.length; i++) {
            ResourcePersistenceInterface res=restore(ress[i]);
            ret.add(res);
         }
      }
      return ret;
   }

   public List getAssignmentsWhere(SharkTransaction t, String sqlWhere) throws PersistenceException {
      List ret=new ArrayList();

      /*long t1,t2,t3;
      System.out.println("GPASS start");
      t1=System.currentTimeMillis();*/
      AssignmentDO[] ass=getAllPersistedAssignments(t, sqlWhere);
      //t2=System.currentTimeMillis();
      if (ass!=null) {
         for (int i=0;i<ass.length; i++) {
            try {
               if (ass[i].getIsValid()) {
                  AssignmentPersistenceInterface as=restore(ass[i]);
                  ret.add(as);
               }
            } catch (Throwable ex) {
               throw new PersistenceException(ex);
            }
         }
      }
      //t3=System.currentTimeMillis();
      //System.out.println("GPASS: T="+(t3-t1)+", T1="+(t2-t1)+", SW="+sqlWhere);
      return ret;
   }

   public List getProcessesWhere(SharkTransaction ti, String sqlWhere) throws PersistenceException {
         List ret=new ArrayList();
         /*long t1,t2,t3;
         System.out.println("GPW start");
         t1=System.currentTimeMillis();*/
         ProcessDO[] procs=getPersistedProcesses(ti,0,null,null,null,null,sqlWhere);

         try {
            //t2=System.currentTimeMillis();
            if (procs!=null) {
               for (int i=0;i<procs.length; i++) {
                  ProcessPersistenceInterface proc=restore(procs[i]);
                  ret.add(proc);
               }
            }
            //t3=System.currentTimeMillis();
            //System.out.println("GPW: T="+(t3-t1)+", T1="+(t2-t1)+", SW="+sqlWhere);
            return ret;
         } catch (Exception e) {
            throw new PersistenceException(e);
         }
      }

   public List getActivitiesWhere(SharkTransaction ti, String sqlWhere) throws PersistenceException {
      List ret=new ArrayList();
      /*System.out.println("GAW start");
      long t1,t2,t3;
      t1=System.currentTimeMillis();*/
      ActivityDO[] acts=getPersistedActivities(ti,0,null,sqlWhere);
      //t2=System.currentTimeMillis();
      if (acts!=null) {
         for (int i=0;i<acts.length; i++) {
            ActivityPersistenceInterface act=restore(acts[i]);
            ret.add(act);
         }
      }
      //t3=System.currentTimeMillis();
      //System.out.println("GAW: T="+(t3-t1)+", T1="+(t2-t1));
      return ret;
   }


//    protected static final String bigStringA="AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
//    protected static final String bigStringB="BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
//    protected static final String bigStringC="CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC";
//
//    protected long initNoOfPDefs, initNoOfProcesses, initNoOfActivities;
//    protected long curNoOfPDefs, curNoOfProcesses, curNoOfActivities;
//
//    protected boolean initedForTests=false;
//
//    protected long curentTime;
//
//    protected PrintStream testOverallCreationStream=null;
//    protected PrintStream testProcDefCreationStream=null;
//    protected PrintStream testProcessCreationStream=null;
//    protected PrintStream testActivityCreationStream=null;
//    protected PrintStream testIndexedProcDefQueriesStream=null;
//    protected PrintStream testIndexedProcessQueriesStream=null;
//    protected PrintStream testIndexedActivityQueriesStream=null;
//    protected PrintStream testNonIndexedProcDefQueriesStream=null;
//    protected PrintStream testNonIndexedProcessQueriesStream=null;
//    protected PrintStream testNonIndexedActivityQueriesStream=null;
//    protected PrintStream testProcDefDeletionStream=null;
//    protected PrintStream testProcessDeletionStream=null;
//    protected PrintStream testActivityDeletionStream=null;
//
//    protected String commentForLog="";
//
//    public static void main (String[] args) throws Throwable {
//
//    if (args==null || args.length<5) {
//    printUsage();
//    System.exit(1);
//    }
//    String configFilePath=args[0];
//    String logFilePrefix=args[1];
//    String testType=args[2];
//    int transactionCount = new Integer(args[3]).intValue();
//    int noOfOpPerTransaction = new Integer(args[4]).intValue();
//
//    DODSPersistentManager pManager=new DODSPersistentManager();
//    if (args.length>5) {
//    pManager.commentForLog = args[5];
//    }
//    pManager.initForTests(configFilePath,logFilePrefix);
//
//    if (testType.equals("all") || testType.equals("allnodel")) {
//    pManager.testOverallCreation(transactionCount,noOfOpPerTransaction);
//    pManager.testProcDefCreation(transactionCount,noOfOpPerTransaction);
//    pManager.testProcessCreation(transactionCount,noOfOpPerTransaction);
//    pManager.testActivityCreation(transactionCount,noOfOpPerTransaction);
//    pManager.testIndexedPDefQueries(transactionCount,noOfOpPerTransaction);
//    pManager.testIndexedProcessQueries(transactionCount,noOfOpPerTransaction);
//    pManager.testIndexedActivityQueries(transactionCount,noOfOpPerTransaction);
//    pManager.testNonIndexedPDefQueries(transactionCount,noOfOpPerTransaction);
//    pManager.testNonIndexedProcessQueries(transactionCount,noOfOpPerTransaction);
//    pManager.testNonIndexedActivityQueries(transactionCount,noOfOpPerTransaction);
//    if (testType.equals("all")) {
//    pManager.testActivityDeletion(transactionCount,noOfOpPerTransaction);
//    pManager.testProcessDeletion(transactionCount,noOfOpPerTransaction);
//    pManager.testProcDefDeletion(transactionCount,noOfOpPerTransaction);
//    }
//    } else if (testType.equals("ocre")) {
//    pManager.testOverallCreation(transactionCount,noOfOpPerTransaction);
//    } else if (testType.equals("pdcre")) {
//    pManager.testProcDefCreation(transactionCount,noOfOpPerTransaction);
//    } else if (testType.equals("pcre")) {
//    pManager.testProcessCreation(transactionCount,noOfOpPerTransaction);
//    } else if (testType.equals("acre")) {
//    pManager.testActivityCreation(transactionCount,noOfOpPerTransaction);
//    } else if (testType.equals("ipdq")) {
//    pManager.testIndexedPDefQueries(transactionCount,noOfOpPerTransaction);
//    } else if (testType.equals("ipq")) {
//    pManager.testIndexedProcessQueries(transactionCount,noOfOpPerTransaction);
//    } else if (testType.equals("iaq")) {
//    pManager.testIndexedActivityQueries(transactionCount,noOfOpPerTransaction);
//    } else if (testType.equals("nipdq")) {
//    pManager.testNonIndexedPDefQueries(transactionCount,noOfOpPerTransaction);
//    } else if (testType.equals("nipq")) {
//    pManager.testNonIndexedProcessQueries(transactionCount,noOfOpPerTransaction);
//    } else if (testType.equals("niaq")) {
//    pManager.testNonIndexedActivityQueries(transactionCount,noOfOpPerTransaction);
//    } else if (testType.equals("pddel")) {
//    pManager.testProcDefDeletion(transactionCount,noOfOpPerTransaction);
//    } else if (testType.equals("pdel")) {
//    pManager.testProcessDeletion(transactionCount,noOfOpPerTransaction);
//    } else if (testType.equals("adel")) {
//    pManager.testActivityDeletion(transactionCount,noOfOpPerTransaction);
//    } else {
//    printUsage();
//    System.exit(1);
//    }
//
//    pManager.testCleanup();
//    }
//
//    public void initForTests (String configFilePath,String logFilePrefix) {
//    _prStates = new HashMap();
//    _acStates = new HashMap();
//    _prClosedStatesBigDecimals = new ArrayList();
//    _actClosedStatesBigDecimals = new ArrayList();
//    _actOpenStatesBigDecimals = new ArrayList();
//
//    String userDir=System.getProperty("user.dir");
//
//    File configFile=new File(configFilePath);
//    if (!configFile.exists()) {
//    String cfp=userDir+File.separator+configFilePath;
//    configFile=new File(cfp);
//    if (!configFile.exists()) {
//    System.out.println("Configuration file can't be found at "+cfp);
//    System.exit(1);
//    }
//
//    }
//    try {
//
//    Properties props=new Properties();
//    FileInputStream fis=new FileInputStream(configFile);
//    props.load(fis);
//    fis.close();
//    DODSUtilities.init(props);
//    //DODS.startup(userDir+File.separator+"conf/Shark.conf");
//    initActivityAndProcessStatesTable();
//    initObjectCount();
//
//    File testFile = new File(logFilePrefix+" - testOverallCreationLog");
//    OutputStream fos = new FileOutputStream(testFile,true);
//    testOverallCreationStream = new PrintStream(fos);
//
//    testFile = new File(logFilePrefix+" - testProcDefCreationLog");
//    fos = new FileOutputStream(testFile,true);
//    testProcDefCreationStream = new PrintStream(fos);
//
//    testFile = new File(logFilePrefix+" - testProcessCreationLog");
//    fos = new FileOutputStream(testFile,true);
//    testProcessCreationStream = new PrintStream(fos);
//
//    testFile = new File(logFilePrefix+" - testActivityCreationLog");
//    fos = new FileOutputStream(testFile,true);
//    testActivityCreationStream = new PrintStream(fos);
//
//    testFile = new File(logFilePrefix+" - testIndexedProcDefQueriesLog");
//    fos = new FileOutputStream(testFile,true);
//    testIndexedProcDefQueriesStream = new PrintStream(fos);
//
//    testFile = new File(logFilePrefix+" - testIndexedProcessQueriesLog");
//    fos = new FileOutputStream(testFile,true);
//    testIndexedProcessQueriesStream = new PrintStream(fos);
//
//    testFile = new File(logFilePrefix+" - testIndexedActivityQueriesLog");
//    fos = new FileOutputStream(testFile,true);
//    testIndexedActivityQueriesStream = new PrintStream(fos);
//
//    testFile = new File(logFilePrefix+" - testNonIndexedProcDefQueriesLog");
//    fos = new FileOutputStream(testFile,true);
//    testNonIndexedProcDefQueriesStream = new PrintStream(fos);
//
//    testFile = new File(logFilePrefix+" - testNonIndexedProcessQueriesLog");
//    fos = new FileOutputStream(testFile,true);
//    testNonIndexedProcessQueriesStream = new PrintStream(fos);
//
//    testFile = new File(logFilePrefix+" - testNonIndexedActivityQueriesLog");
//    fos = new FileOutputStream(testFile,true);
//    testNonIndexedActivityQueriesStream = new PrintStream(fos);
//
//    testFile = new File(logFilePrefix+" - testProcDefDeletionLog");
//    fos = new FileOutputStream(testFile,true);
//    testProcDefDeletionStream = new PrintStream(fos);
//
//    testFile = new File(logFilePrefix+" - testProcessDeletionLog");
//    fos = new FileOutputStream(testFile,true);
//    testProcessDeletionStream = new PrintStream(fos);
//
//    testFile = new File(logFilePrefix+" - testActivityDeletionLog");
//    fos = new FileOutputStream(testFile,true);
//    testActivityDeletionStream = new PrintStream(fos);
//
//    } catch (Exception ex) {
//    ex.printStackTrace();
//    System.exit(1);
//    }
//
//    initedForTests=true;
//    }
//
//    public void testCleanup () {
//    initedForTests=false;
//    try {
//    testOverallCreationStream.close();
//    testProcDefCreationStream.close();
//    testProcessCreationStream.close();
//    testActivityCreationStream.close();
//    testIndexedProcDefQueriesStream.close();
//    testIndexedProcessQueriesStream.close();
//    testIndexedActivityQueriesStream.close();
//    testNonIndexedProcDefQueriesStream.close();
//    testNonIndexedProcessQueriesStream.close();
//    testNonIndexedActivityQueriesStream.close();
//    testProcDefDeletionStream.close();
//    testProcessDeletionStream.close();
//    testActivityDeletionStream.close();
//    DODS.shutdown();
//    } catch (Exception ex) {
//    ex.printStackTrace();
//    }
//    System.exit(0);
//    }
//
//    public void testOverallCreation (int transactionCount,int topObjectRowsToInsertPerTransaction) {
//    if (!initedForTests) {
//    initForTests("conf/Shark.conf","Default");
//    }
//
//    String testStartMsg=transactionCount+" times creating  "+topObjectRowsToInsertPerTransaction+" proc defs and its children: \ntest started at "+(new java.util.Date().toString())+" - Initial no of objects in DB=["+curNoOfPDefs+","+curNoOfProcesses+","+curNoOfActivities+"]";
//    writeInitMsg(testOverallCreationStream,testStartMsg,-1);
//
//    try{
//    DBTransaction dbTrans = null;
//    long overallTime=0;
//    for(int k=0;k<transactionCount;k++){
//    resetCurentTime();
//    dbTrans = DODS.getDatabaseManager().createTransaction();
//    for (int i=0; i<topObjectRowsToInsertPerTransaction;i++){
//    ProcessDefinitionDO pdefDO= ProcessDefinitionDO.createVirgin(dbTrans);
//    String keyAStr=String.valueOf(curNoOfPDefs);
//    pdefDO.setName(keyAStr);
//    pdefDO.setPackageId(keyAStr);
//    pdefDO.setProcessDefinitionId(bigStringA);
//    pdefDO.setProcessDefinitionCreated(bigStringA);
//    pdefDO.setProcessDefinitionVersion("ThisMustBeShort");
//    pdefDO.setState(0);
//
//    for (int i2=0; i2<topObjectRowsToInsertPerTransaction; i2++) {
//    String keyBStr=String.valueOf(curNoOfProcesses);
//    ProcessDO pDO=ProcessDO.createVirgin(dbTrans);
//    pDO.setId(keyBStr);
//    pDO.setProcessDefinition(pdefDO);
//    pDO.setName(keyBStr);
//    pDO.setDescription(bigStringB);
//    pDO.oid_setState((BigDecimal)_prStates.get("open.running"));
//
//    for (int i3=0; i3<topObjectRowsToInsertPerTransaction; i3++) {
//    String keyCStr=String.valueOf(curNoOfActivities);
//    ActivityDO aDO=ActivityDO.createVirgin(dbTrans);
//    aDO.setId(keyCStr);
//    aDO.setProcess(pDO);
//    aDO.setActivityDefinitionId(keyCStr);
//    aDO.setDescription(bigStringC);
//    aDO.setName(bigStringC);
//    aDO.oid_setState((BigDecimal)_acStates.get("open.running"));
//    aDO.save();
//    curNoOfActivities++;
//    }
//    pDO.save();
//    curNoOfProcesses++;
//    }
//    pdefDO.save();
//    curNoOfPDefs++;
//    }
//    dbTrans.commit();
//    overallTime+=writeTimeDiff(testOverallCreationStream);
//    }
//    String summary=String.valueOf(curNoOfPDefs)+";"+String.valueOf(overallTime/transactionCount);
//    writeSummary(testOverallCreationStream,summary);
//    } catch (Exception e){
//    e.printStackTrace();
//    System.exit(1);
//    }
//    }
//
//    public void testProcDefCreation (int transactionCount,int howManyPerTransaction) {
//    if (!initedForTests) {
//    initForTests("conf/Shark.conf","Default");
//    }
//
//    String testStartMsg=transactionCount+" times creating  "+howManyPerTransaction+" proc defs: \ntest started at "+(new java.util.Date().toString());
//    writeInitMsg(testProcDefCreationStream,testStartMsg,curNoOfPDefs);
//
//    try{
//    DBTransaction dbTrans = null;
//    long overallTime=0;
//    for(int k=0;k<transactionCount;k++){
//    resetCurentTime();
//    dbTrans = DODS.getDatabaseManager().createTransaction();
//    for (int i=0; i<howManyPerTransaction; i++){
//    ProcessDefinitionDO pdefDO= ProcessDefinitionDO.createVirgin(dbTrans);
//    String keyAStr=String.valueOf(curNoOfPDefs);
//    pdefDO.setName(keyAStr);
//    pdefDO.setPackageId(keyAStr);
//    pdefDO.setProcessDefinitionId(bigStringA);
//    pdefDO.setProcessDefinitionCreated(bigStringA);
//    pdefDO.setProcessDefinitionVersion("ThisMustBeShort");
//    pdefDO.setState(0);
//    pdefDO.save();
//    curNoOfPDefs++;
//    }
//    dbTrans.commit();
//    overallTime+=writeTimeDiff(testProcDefCreationStream);
//    }
//    String summary=String.valueOf(curNoOfPDefs)+";"+String.valueOf(overallTime/transactionCount);
//    writeSummary(testProcDefCreationStream,summary);
//    } catch (Exception e){
//    e.printStackTrace();
//    System.exit(1);
//    }
//    }
//
//    public void testProcessCreation (int transactionCount,int howManyPerTransaction) {
//    if (!initedForTests) {
//    initForTests("conf/Shark.conf","Default");
//    }
//
//    String testStartMsg=transactionCount+" times creating  "+howManyPerTransaction+" processes: \ntest started at "+(new java.util.Date().toString());
//    writeInitMsg(testProcessCreationStream,testStartMsg,curNoOfProcesses);
//
//    try{
//    long overallTime=0;
//    DBTransaction dbTrans = null;
//    for(int k=0;k<transactionCount;k++){
//    dbTrans = DODS.getDatabaseManager().createTransaction();
//
//    ProcessDefinitionDO pdefDO=null;
//    if (curNoOfPDefs>0) {
//    ProcessDefinitionQuery query = new ProcessDefinitionQuery(dbTrans);
//    query.requireUniqueInstance();
//    String uniqueFieldValue=String.valueOf((long)(curNoOfPDefs*Math.random()));
//    query.setQueryName(uniqueFieldValue);
//    pdefDO=query.getNextDO();
//    } else {
//    System.out.println("Can't create processes because there are no process definitions in DB.");
//    dbTrans.release();
//    return;
//    }
//
//    resetCurentTime();
//    for (int i2=0; i2<howManyPerTransaction; i2++) {
//    String keyBStr=String.valueOf(curNoOfProcesses);
//    ProcessDO pDO=ProcessDO.createVirgin(dbTrans);
//    pDO.setId(keyBStr);
//    pDO.setProcessDefinition(pdefDO);
//    pDO.setName(keyBStr);
//    pDO.setDescription(bigStringB);
//    pDO.oid_setState((BigDecimal)_prStates.get("open.running"));
//    pDO.save();
//    curNoOfProcesses++;
//    }
//    dbTrans.commit();
//    overallTime+=writeTimeDiff(testProcessCreationStream);
//    }
//    String summary=String.valueOf(curNoOfProcesses)+";"+String.valueOf(overallTime/transactionCount);
//    writeSummary(testProcessCreationStream,summary);
//    } catch (Exception e){
//    e.printStackTrace();
//    System.exit(1);
//    }
//    }
//
//    public void testActivityCreation (int transactionCount,int howManyPerTransaction) {
//    if (!initedForTests) {
//    initForTests("conf/Shark.conf","Default");
//    }
//
//    String testStartMsg=transactionCount+" times creating  "+howManyPerTransaction+" activities: \ntest started at "+(new java.util.Date().toString());
//    writeInitMsg(testActivityCreationStream,testStartMsg,curNoOfActivities);
//
//    try{
//    DBTransaction dbTrans = null;
//    long overallTime=0;
//    for(int k=0;k<transactionCount;k++){
//
//    dbTrans = DODS.getDatabaseManager().createTransaction();
//
//    ProcessDO pDO=null;
//    if (curNoOfProcesses>0) {
//    ProcessQuery query = new ProcessQuery(dbTrans);
//    query.requireUniqueInstance();
//    String uniqueFieldValue=String.valueOf((long)(curNoOfProcesses*Math.random()));
//    query.setQueryName(uniqueFieldValue);
//    pDO=query.getNextDO();
//    } else {
//    System.out.println("Can't create activities because there are no processes in DB.");
//    dbTrans.release();
//    return;
//    }
//
//    resetCurentTime();
//    for (int i3=0; i3<howManyPerTransaction; i3++) {
//    String keyCStr=String.valueOf(curNoOfActivities);
//    ActivityDO aDO=ActivityDO.createVirgin(dbTrans);
//    aDO.setId(keyCStr);
//    aDO.setProcess(pDO);
//    aDO.setActivityDefinitionId(keyCStr);
//    aDO.setDescription(bigStringC);
//    aDO.setName(bigStringC);
//    aDO.oid_setState((BigDecimal)_acStates.get("open.running"));
//    aDO.save();
//    curNoOfActivities++;
//    }
//    dbTrans.commit();
//    overallTime+=writeTimeDiff(testActivityCreationStream);
//    }
//    String summary=String.valueOf(curNoOfActivities)+";"+String.valueOf(overallTime/transactionCount);
//    writeSummary(testActivityCreationStream,summary);
//
//    } catch (Exception e){
//    e.printStackTrace();
//    System.exit(1);
//    }
//    }
//
//    public void testIndexedPDefQueries (int transactionCount,int queryCount) {
//    if (!initedForTests) {
//    initForTests("conf/Shark.conf","Default");
//    }
//
//    String testStartMsg=transactionCount+" times querying for "+queryCount+" proc defs: \ntest started at "+(new java.util.Date().toString());
//    writeInitMsg(testIndexedProcDefQueriesStream,testStartMsg,curNoOfPDefs);
//
//    try{
//    DBTransaction dbTrans=null;
//    long overallTime=0;
//    for(int k=0;k<transactionCount;k++){
//    resetCurentTime();
//    dbTrans = DODS.getDatabaseManager().createTransaction();
//    for (int i=0; i<queryCount;i++){
//    ProcessDefinitionQuery query = new ProcessDefinitionQuery(dbTrans);
//    query.requireUniqueInstance();
//    String uniqueFieldValue=String.valueOf((long)(curNoOfPDefs*Math.random()));
//    query.setQueryName(uniqueFieldValue);
//    ProcessDefinitionDO DO=query.getNextDO();
//    }
//    dbTrans.release();
//    overallTime+=writeTimeDiff(testIndexedProcDefQueriesStream);
//    }
//    String summary=String.valueOf(curNoOfPDefs)+";"+String.valueOf(overallTime/transactionCount);
//    writeSummary(testIndexedProcDefQueriesStream,summary);
//    } catch (Exception e){
//    e.printStackTrace();
//    System.exit(1);
//    }
//    }
//
//    public void testIndexedProcessQueries (int transactionCount,int queryCount) {
//    if (!initedForTests) {
//    initForTests("conf/Shark.conf","Default");
//    }
//
//    String testStartMsg=transactionCount+" times querying for "+queryCount+" processes: \ntest started at "+(new java.util.Date().toString());
//    writeInitMsg(testIndexedProcessQueriesStream,testStartMsg,curNoOfProcesses);
//    try{
//    DBTransaction dbTrans=null;
//    long overallTime=0;
//    for(int k=0;k<transactionCount;k++){
//    resetCurentTime();
//    dbTrans = DODS.getDatabaseManager().createTransaction();
//    for (int i=0; i<queryCount;i++){
//    ProcessQuery query = new ProcessQuery(dbTrans);
//    query.requireUniqueInstance();
//    String uniqueFieldValue=String.valueOf((long)(curNoOfProcesses*Math.random()));
//    query.setQueryId(uniqueFieldValue);
//    ProcessDO DO=query.getNextDO();
//    }
//    dbTrans.release();
//    overallTime+=writeTimeDiff(testIndexedProcessQueriesStream);
//    }
//    String summary=String.valueOf(curNoOfProcesses)+";"+String.valueOf(overallTime/transactionCount);
//    writeSummary(testIndexedProcessQueriesStream,summary);
//    } catch (Exception e){
//    e.printStackTrace();
//    System.exit(1);
//    }
//    }
//
//    public void testIndexedActivityQueries (int transactionCount,int queryCount) {
//    if (!initedForTests) {
//    initForTests("conf/Shark.conf","Default");
//    }
//
//    String testStartMsg=transactionCount+" times querying for "+queryCount+" activities: \ntest started at "+(new java.util.Date().toString());
//    writeInitMsg(testIndexedActivityQueriesStream,testStartMsg,curNoOfActivities);
//    try{
//    DBTransaction dbTrans=null;
//    long overallTime=0;
//    for(int k=0;k<transactionCount;k++){
//    resetCurentTime();
//    dbTrans = DODS.getDatabaseManager().createTransaction();
//    for (int i=0; i<queryCount;i++){
//    ActivityQuery query = new ActivityQuery(dbTrans);
//    //query.requireUniqueInstance();
//    String uniqueFieldValue=String.valueOf((long)(curNoOfActivities*Math.random()));
//    query.setQueryId(uniqueFieldValue);
//    ActivityDO DO=query.getNextDO();
//    }
//    dbTrans.release();
//    overallTime+=writeTimeDiff(testIndexedActivityQueriesStream);
//    }
//    String summary=String.valueOf(curNoOfActivities)+";"+String.valueOf(overallTime/transactionCount);
//    writeSummary(testIndexedActivityQueriesStream,summary);
//    } catch (Exception e){
//    e.printStackTrace();
//    System.exit(1);
//    }
//    }
//
//    public void testNonIndexedPDefQueries (int transactionCount,int queryCount) {
//    if (!initedForTests) {
//    initForTests("conf/Shark.conf","Default");
//    }
//
//    String testStartMsg=transactionCount+" times querying for "+queryCount+" proc defs: \ntest started at "+(new java.util.Date().toString());
//    writeInitMsg(testNonIndexedProcDefQueriesStream,testStartMsg,curNoOfPDefs);
//    try{
//    DBTransaction dbTrans=null;
//    long overallTime=0;
//    for(int k=0;k<transactionCount;k++){
//    resetCurentTime();
//    dbTrans = DODS.getDatabaseManager().createTransaction();
//    for (int i=0; i<queryCount;i++){
//    ProcessDefinitionQuery query = new ProcessDefinitionQuery(dbTrans);
//    String uniqueFieldValue=String.valueOf((long)(curNoOfPDefs*Math.random()));
//    query.setQueryPackageId(uniqueFieldValue);
//    ProcessDefinitionDO DO=query.getNextDO();
//    }
//    dbTrans.release();
//    overallTime+=writeTimeDiff(testNonIndexedProcDefQueriesStream);
//    }
//    String summary=String.valueOf(curNoOfPDefs)+";"+String.valueOf(overallTime/transactionCount);
//    writeSummary(testNonIndexedProcDefQueriesStream,summary);
//    } catch (Exception e){
//    e.printStackTrace();
//    System.exit(1);
//    }
//    }
//
//    public void testNonIndexedProcessQueries (int transactionCount,int queryCount) {
//    if (!initedForTests) {
//    initForTests("conf/Shark.conf","Default");
//    }
//
//    String testStartMsg=transactionCount+" times querying for "+queryCount+" processes: \ntest started at "+(new java.util.Date().toString());
//    writeInitMsg(testNonIndexedProcessQueriesStream,testStartMsg,curNoOfProcesses);
//    try{
//    DBTransaction dbTrans=null;
//    long overallTime=0;
//    for(int k=0;k<transactionCount;k++){
//    resetCurentTime();
//    dbTrans = DODS.getDatabaseManager().createTransaction();
//    for (int i=0; i<queryCount;i++){
//    ProcessQuery query = new ProcessQuery(dbTrans);
//    String uniqueFieldValue=String.valueOf((long)(curNoOfProcesses*Math.random()));
//    query.setQueryName(uniqueFieldValue);
//    ProcessDO DO=query.getNextDO();
//    }
//    dbTrans.release();
//    overallTime+=writeTimeDiff(testNonIndexedProcessQueriesStream);
//    }
//    String summary=String.valueOf(curNoOfProcesses)+";"+String.valueOf(overallTime/transactionCount);
//    writeSummary(testNonIndexedProcessQueriesStream,summary);
//    } catch (Exception e){
//    e.printStackTrace();
//    System.exit(1);
//    }
//    }
//
//    public void testNonIndexedActivityQueries (int transactionCount,int queryCount) {
//    if (!initedForTests) {
//    initForTests("conf/Shark.conf","Default");
//    }
//
//    String testStartMsg=transactionCount+" times querying for "+queryCount+" activities: \ntest started at "+(new java.util.Date().toString());
//    writeInitMsg(testNonIndexedActivityQueriesStream,testStartMsg,curNoOfActivities);
//    try{
//    DBTransaction dbTrans=null;
//    long overallTime=0;
//    for(int k=0;k<transactionCount;k++){
//    resetCurentTime();
//    dbTrans = DODS.getDatabaseManager().createTransaction();
//    for (int i=0; i<queryCount;i++){
//    ActivityQuery query = new ActivityQuery(dbTrans);
//    query.requireUniqueInstance();
//    String uniqueFieldValue=String.valueOf((long)(curNoOfActivities*Math.random()));
//    query.setQueryActivityDefinitionId(uniqueFieldValue);
//    ActivityDO DO=query.getNextDO();
//    }
//    dbTrans.release();
//    overallTime+=writeTimeDiff(testNonIndexedActivityQueriesStream);
//    }
//    String summary=String.valueOf(curNoOfActivities)+";"+String.valueOf(overallTime/transactionCount);
//    writeSummary(testNonIndexedActivityQueriesStream,summary);
//
//    } catch (Exception e){
//    e.printStackTrace();
//    System.exit(1);
//    }
//    }
//
//    public void testProcDefDeletion (int transactionCount,int deleteCount) {
//    if (!initedForTests) {
//    initForTests("conf/Shark.conf","Default");
//    }
//
//    String testStartMsg=transactionCount+" times deleting "+deleteCount+" proc defs: \ntest started at "+(new java.util.Date().toString());
//    writeInitMsg(testProcDefDeletionStream,testStartMsg,curNoOfPDefs);
//    try{
//    DBTransaction dbTrans=null;
//    long overallTime=0;
//    for(int k=0;k<transactionCount;k++){
//    resetCurentTime();
//    dbTrans = DODS.getDatabaseManager().createTransaction();
//    for (int i=0; i<deleteCount;i++){
//    ProcessDefinitionQuery query = new ProcessDefinitionQuery(dbTrans);
//    query.requireUniqueInstance();
//    if (curNoOfPDefs<=0) {
//    System.out.println("No more Process definitions to delete");
//    break;
//    }
//    String uniqueFieldValue=String.valueOf(curNoOfPDefs-1);
//    query.setQueryName(uniqueFieldValue);
//    ProcessDefinitionDO DO=query.getNextDO();
//    DO.delete(dbTrans);
//    curNoOfPDefs--;
//    }
//    dbTrans.commit();
//    overallTime+=writeTimeDiff(testProcDefDeletionStream);
//    }
//    String summary=String.valueOf(curNoOfPDefs)+";"+String.valueOf(overallTime/transactionCount);
//    writeSummary(testProcDefDeletionStream,summary);
//    } catch (Exception e){
//    e.printStackTrace();
//    System.exit(1);
//    }
//    }
//
//    public void testProcessDeletion (int transactionCount,int deleteCount) {
//    if (!initedForTests) {
//    initForTests("conf/Shark.conf","Default");
//    }
//
//    String testStartMsg=transactionCount+" times deleting "+deleteCount+" processes: \ntest started at "+(new java.util.Date().toString());
//    writeInitMsg(testProcessDeletionStream,testStartMsg,curNoOfProcesses);
//    try{
//    DBTransaction dbTrans=null;
//    long overallTime=0;
//    for(int k=0;k<transactionCount;k++){
//    resetCurentTime();
//    dbTrans = DODS.getDatabaseManager().createTransaction();
//    for (int i=0; i<deleteCount;i++){
//    ProcessQuery query = new ProcessQuery(dbTrans);
//    query.requireUniqueInstance();
//    if (curNoOfProcesses<=0) {
//    System.out.println("No more Processes to delete");
//    break;
//    }
//    String uniqueFieldValue=String.valueOf(curNoOfProcesses-1);
//    query.setQueryId(uniqueFieldValue);
//    ProcessDO DO=query.getNextDO();
//    DO.delete(dbTrans);
//    curNoOfProcesses--;
//    }
//    dbTrans.commit();
//    overallTime+=writeTimeDiff(testProcessDeletionStream);
//    }
//    String summary=String.valueOf(curNoOfProcesses)+";"+String.valueOf(overallTime/transactionCount);
//    writeSummary(testProcessDeletionStream,summary);
//    } catch (Exception e){
//    e.printStackTrace();
//    System.exit(1);
//    }
//    }
//
//    public void testActivityDeletion (int transactionCount,int deleteCount) {
//    if (!initedForTests) {
//    initForTests("conf/Shark.conf","Default");
//    }
//
//    String testStartMsg=transactionCount+" times deleting "+deleteCount+" activities: \ntest started at "+(new java.util.Date().toString());
//    writeInitMsg(testActivityDeletionStream,testStartMsg,curNoOfActivities);
//    try{
//    DBTransaction dbTrans=null;
//    long overallTime=0;
//    for(int k=0;k<transactionCount;k++){
//    resetCurentTime();
//    dbTrans = DODS.getDatabaseManager().createTransaction();
//    for (int i=0; i<deleteCount;i++){
//    ActivityQuery query = new ActivityQuery(dbTrans);
//    query.requireUniqueInstance();
//    if (curNoOfActivities<=0) {
//    System.out.println("No more Activities to delete");
//    break;
//    }
//    String uniqueFieldValue=String.valueOf(curNoOfActivities-1);
//    query.setQueryId(uniqueFieldValue);
//    ActivityDO DO=query.getNextDO();
//    DO.delete(dbTrans);
//    curNoOfActivities--;
//    }
//    dbTrans.commit();
//    overallTime+=writeTimeDiff(testActivityDeletionStream);
//    }
//    String summary=String.valueOf(curNoOfActivities)+";"+String.valueOf(overallTime/transactionCount);
//    writeSummary(testActivityDeletionStream,summary);
//    } catch (Exception e){
//    e.printStackTrace();
//    System.exit(1);
//    }
//    }
//
//    protected void initObjectCount () {
//    try {
//    DBTransaction dbTrans = DODS.getDatabaseManager().createTransaction();
//    ProcessDefinitionQuery queryA = new ProcessDefinitionQuery(dbTrans);
//    initNoOfPDefs=queryA.getCount();
//    curNoOfPDefs=initNoOfPDefs;
//
//    ProcessQuery queryB = new ProcessQuery(dbTrans);
//    initNoOfProcesses=queryB.getCount();
//    curNoOfProcesses=initNoOfProcesses;
//
//    ActivityQuery queryC = new ActivityQuery(dbTrans);
//    initNoOfActivities=queryC.getCount();
//    curNoOfActivities=initNoOfActivities;
//    dbTrans.release();
//    } catch (Exception ex) {
//    ex.printStackTrace();
//    System.exit(1);
//    }
//    }
//
//    protected void resetCurentTime() {
//    curentTime=System.currentTimeMillis();
//    }
//
//    protected long writeTimeDiff (PrintStream ps) {
//    long diff=System.currentTimeMillis()-curentTime;
//    ps.print(System.currentTimeMillis()-curentTime);
//    ps.println();
//    return diff;
//    }
//
//    protected void writeInitMsg (PrintStream ps,String mainMsg,long noOfObjs) {
//    ps.println();
//    ps.println("-------------------------------------------------------------------------------------");
//    ps.print(mainMsg);
//    if (noOfObjs>=0) {
//    ps.println(" - initial No of objects in DB="+noOfObjs);
//    ps.println(" Comment: "+this.commentForLog);
//    } else {
//    ps.println();
//    ps.println(" Comment: "+this.commentForLog);
//    }
//    ps.println("-------------------------------------------------------------------------------------");
//    ps.println();
//    }
//
//    protected void writeSummary (PrintStream ps,String summary) {
//    ps.println();
//    ps.println("summary:");
//    ps.println(summary);
//    }
//
//    protected static void printUsage () {
//    System.err.println("This application is used to start DB tests.");
//    System.err.println();
//    System.err.println("usage: java DODSPersistentManager confFilePath logFilePrefix testType noOfTransactions noOfOpPerTransaction comment");
//    System.err.println();
//    System.err.println("arguments:");
//    System.err.println("  confFilePath -----------> the path to configuration file.");
//    System.err.println("  logFilePrefix-----------> the prefix for log file name.");
//    System.err.println("  testType ---------------> the type of test:");
//    System.err.println("                               - all      = test all");
//    System.err.println("                               - allnodel = test all");
//    System.err.println("                               - ocre     = test overall creation");
//    System.err.println("                               - pdcre    = test process definition creation");
//    System.err.println("                               - pcre     = test process creation");
//    System.err.println("                               - acre     = test activity creation");
//    System.err.println("                               - ipdq     = test indexed process definition queries");
//    System.err.println("                               - ipq      = test indexed process queries");
//    System.err.println("                               - iaq      = test indexed activity queries");
//    System.err.println("                               - nipdq    = test non indexed process definition queries");
//    System.err.println("                               - nipq     = test non indexed process queries");
//    System.err.println("                               - niaq     = test non indexed activity queries");
//    System.err.println("                               - pddel    = test process definition deletion");
//    System.err.println("                               - pdel     = test process deletion");
//    System.err.println("                               - adel     = test activity deletion");
//    System.err.println("  noOfTransaction --------> the number of transactions for test operations (the time is measured per transaction).");
//    System.err.println("  noOfOpPerTransaction ---> the number of operations that will be performed within one transaction.");
//    System.err.println("  comment ----------------> comment that will be written into log file.");
//    }

}
