package org.enhydra.shark.eventaudit;

import java.util.*;
import org.enhydra.shark.api.internal.eventaudit.*;
import org.enhydra.shark.eventaudit.data.*;

import com.lutris.appserver.server.sql.DBTransaction;
import java.math.BigDecimal;
import org.enhydra.dods.DODS;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.internal.working.CallbackUtilities;
import org.enhydra.shark.transaction.SharkDODSTransaction;
import org.enhydra.shark.utilities.MiscUtilities;
import org.enhydra.shark.utilities.dods.DODSUtilities;

/**
 * Used to save, restore or delete relevant engine objects from database, using
 * DODS persistent layer.
 *
 * @author Tanja Jovanovic
 * @version 1.0
 */
public class DODSEventAuditManager implements EventAuditManagerInterface {

   protected static final String LOG_CHANNEL="Persistence";
   protected static final String PACKAGE_LOADED="packageLoaded";
   protected static final String PACKAGE_UNLOADED="packageUnloaded";
   protected static final String PACKAGE_UPDATED="packageUpdated";
   protected static final String PROCESS_CREATED="processCreated";
   protected static final String PROCESS_STATE_CHANGED="processStateChanged";
   protected static final String PROCESS_CONTEXT_CHANGED="processContextChanged";
   protected static final String ACTIVITY_STATE_CHANGED="activityStateChanged";
   protected static final String ACTIVITY_CONTEXT_CHANGED="activityContextChanged";
   protected static final String ACTIVITY_RESULT_CHANGED="activityResultChanged";
   protected static final String ACTIVITY_ASSIGNMENT_CHANGED="activityAssignmentChanged";
   protected static final int RESOURCE_TYPE_OBJECT=0;
   protected static final int PROCESS_DEFINITION_TYPE_OBJECT=1;
   protected static final int PROCESS_TYPE_OBJECT=2;
   protected static final int ACTIVITY_TYPE_OBJECT=3;
   protected static final int ASSIGNMENT_TYPE_OBJECT=4;
   protected static final int EVENT_AUDIT_ASSIGNMENT_TYPE_OBJECT=5;
   protected static final int EVENT_AUDIT_DATA_TYPE_OBJECT=6;
   protected static final int EVENT_AUDIT_STATE_TYPE_OBJECT=7;
   protected static final int EVENT_AUDIT_CREATE_PROCESS_TYPE_OBJECT=8;
   protected static final int EVENT_AUDIT_PACKAGE_TYPE_OBJECT=9;

   protected static final String[] activityAndProcessStates={"open.running",
         "open.not_running.not_started","open.not_running.suspended",
         "closed.completed", "closed.terminated", "closed.aborted"};

   protected static final String[] eventTypes={PACKAGE_LOADED,PACKAGE_UNLOADED,
         PACKAGE_UPDATED,PROCESS_CREATED,PROCESS_STATE_CHANGED,
         PROCESS_CONTEXT_CHANGED,ACTIVITY_STATE_CHANGED,ACTIVITY_CONTEXT_CHANGED,
         ACTIVITY_RESULT_CHANGED,ACTIVITY_ASSIGNMENT_CHANGED};

   protected static final short DB_TYPE_BOOLEAN=0;
   protected static final short DB_TYPE_LONG=1;
   protected static final short DB_TYPE_DOUBLE=2;
   protected static final short DB_TYPE_VCHAR=3;
   protected static final short DB_TYPE_DATE=4;
   protected static final short DB_TYPE_BLOB=5;
   
   protected static final int MAX_VCHAR_SIZE_LIMIT=4000;

   protected static int max_vchar_size=4000;   

   protected CallbackUtilities cus;
   protected Map _prStates;
   protected Map _acStates;
   protected Map _evTypes;

   protected boolean usingStandardVariableDataModel=true;
   protected boolean persistOldEventAuditData=true;
   
   public void configure (CallbackUtilities cus) throws RootException {
      this.cus=cus;
      String mvc=cus.getProperty("DODSEventAuditManager.maxVARCHARSize","4000");
      try {
         max_vchar_size=Integer.parseInt(mvc);
         if (max_vchar_size>MAX_VCHAR_SIZE_LIMIT || max_vchar_size<1) {
            max_vchar_size=MAX_VCHAR_SIZE_LIMIT;
            cus.warn("Invalid value "+mvc+" for property DODSEventAuditManager.maxVARCHARSize. Using default value "+max_vchar_size);
         }
      } catch (Exception e) {
         cus.warn("Invalid value "+mvc+" for property DODSEventAuditManager.maxVARCHARSize. Using default value "+max_vchar_size);
      }

      usingStandardVariableDataModel=new Boolean(cus.getProperty("DODSEventAuditManager.useStandardVariableDataModel","true")).booleanValue();
      persistOldEventAuditData=new Boolean(cus.getProperty("PERSIST_OLD_EVENT_AUDIT_DATA","true")).booleanValue();
      
      _prStates = new HashMap();
      _acStates = new HashMap();
      _evTypes = new HashMap();
      try {
         try {
            DODSUtilities.init(cus.getProperties());
         }
         catch (Throwable ex) {
            ex.printStackTrace();
         }
         initActivityAndProcessStatesTable();
         initEventTypesTable();
      }
      catch (Throwable tr) {
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
   protected void initActivityAndProcessStatesTable () throws EventAuditException {
      DBTransaction t=null;
      try {
         t = DODS.getDatabaseManager().createTransaction();
         for (int i=0; i<activityAndProcessStates.length; i++) {
            String state=activityAndProcessStates[i];
            ProcessStateEventAuditDO psDO=getPersistedProcessStateObject(state,true,t);
            if (!psDO.isPersistent()) {
               psDO.setKeyValue(state);
            }
            psDO.setName(state);
            psDO.save(t);
            _prStates.put(state, psDO.get_OId().toBigDecimal());
            ActivityStateEventAuditDO asDO=getPersistedActivityStateObject(state,true,t);
            if (!asDO.isPersistent()) {
               asDO.setKeyValue(state);
            }
            asDO.setName(state);
            asDO.save(t);
            _acStates.put(state, asDO.get_OId().toBigDecimal());
         }
         t.commit();
      }
      catch (Throwable thr) {
         throw new EventAuditException(thr);
      }
      finally {
         try {
            t.release();
         } catch (Exception ex) {}
      }
   }

   /**
    * Fills the event type table with possible event types. For now,
    * the 'keyValue' and the 'name' attribute of event type record has
    * the same value, but in the future, if the names of event types changes,
    * it will be very easy to change this table entries without affecting
    * other tables.
    * NOTE: When new names are introduced, the getPersistentXXX that use it
    * also has to be changed
    */
   protected void initEventTypesTable () throws EventAuditException {
      DBTransaction t=null;
      try {
         t = DODS.getDatabaseManager().createTransaction();
         for (int i=0; i<eventTypes.length; i++) {
            String eventType=eventTypes[i];
            EventTypeDO etDO=getPersistedEventTypeObject(eventType,true,t);
            if (!etDO.isPersistent()) {
               etDO.setKeyValue(eventType);
            }
            etDO.setName(eventType);
            etDO.save(t);
            _evTypes.put(eventType, etDO.get_OId().toBigDecimal());
         }
         t.commit();
      }
      catch (Throwable thr) {
         throw new EventAuditException(thr);
      }
      finally {
         try {
            t.release();
         } catch (Exception ex) {}
      }
   }

   public void persist(AssignmentEventAuditPersistenceInterface aea,SharkTransaction ti) throws EventAuditException {
      try {
         AssignmentEventAuditDO DO=AssignmentEventAuditDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         // when these attributes are persisted - they never change
         if (!DO.isPersistent()) {
            DO.setUTCTime(aea.getUTCTime());
            DO.oid_setTheType((BigDecimal)_evTypes.get(aea.getType()));
            DO.setProcessId(aea.getProcessId());
            DO.setActivityId(aea.getActivityId());
            if (aea.getActivityName()!=null) {
               DO.setActivityName(aea.getActivityName());
            }
            DO.setProcessName(aea.getProcessName());
            DO.setProcessDefinitionName(aea.getProcessDefinitionName());
            DO.setProcessDefinitionVersion(aea.getProcessDefinitionVersion());
            if (aea.getActivityDefinitionId()!=null) {
               DO.setActivityDefinitionId(aea.getActivityDefinitionId());
               DO.setActivitySetDefinitionId(aea.getActivitySetDefinitionId());
            }
            DO.setProcessDefinitionId(aea.getProcessDefinitionId());
            DO.setPackageId(aea.getPackageId());
            DO.setNewResourceUsername(aea.getNewResourceUsername());
            if (aea.getNewResourceName()!=null) {
               DO.setNewResourceName(aea.getNewResourceName());
            }
            if (aea.getOldResourceUsername()!=null) {
               DO.setOldResourceUsername(aea.getOldResourceUsername());
            }
            if (aea.getOldResourceName()!=null) {
               DO.setOldResourceName(aea.getOldResourceName());
            }
            DO.setIsAccepted(aea.getIsAccepted());
            DO.setCNT(getNextDecId("assignmenteventaudit"));
            ((SharkDODSTransaction)ti).store(DO);
         }
         cus.info(LOG_CHANNEL,"AssignmentEventAudit[processId="+aea.getProcessId()+",activityId="+aea.getActivityId()+"] persisted");
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Persisting of AssignmentEventAudit failed");
         //tr.printStackTrace();
         throw new EventAuditException
            ("Persisting of AssignmentEventAudit failed", tr);
      }
   }

   public void persist(DataEventAuditPersistenceInterface dea,SharkTransaction ti) throws EventAuditException {
      try {
         DataEventAuditDO DO=DataEventAuditDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         // when these attributes are persisted - they never change
         if (!DO.isPersistent()) {
            DO.setUTCTime(dea.getUTCTime());
            DO.oid_setTheType((BigDecimal)_evTypes.get(dea.getType()));
            DO.setProcessId(dea.getProcessId());
            if (dea.getActivityId()!=null) {
               DO.setActivityId(dea.getActivityId());
            }
            if (dea.getActivityName()!=null) {
               DO.setActivityName(dea.getActivityName());
            }
            DO.setProcessName(dea.getProcessName());
            DO.setProcessDefinitionName(dea.getProcessDefinitionName());
            DO.setProcessDefinitionVersion(dea.getProcessDefinitionVersion());
            if (dea.getActivityDefinitionId()!=null) {
               DO.setActivityDefinitionId(dea.getActivityDefinitionId());
               DO.setActivitySetDefinitionId(dea.getActivitySetDefinitionId());
            }
            DO.setProcessDefinitionId(dea.getProcessDefinitionId());
            DO.setPackageId(dea.getPackageId());
            DO.setCNT(getNextDecId("dataeventaudit"));
            ((SharkDODSTransaction)ti).store(DO);
            if (usingStandardVariableDataModel) {
               if (persistOldEventAuditData) {
                  persistOldEventAuditDataBLOB(dea,DO,ti);
               }
               persistNewEventAuditDataBLOB(dea,DO,ti);
            } else {
               if (persistOldEventAuditData) {
                  persistOldEventAuditDataWOB(dea,DO,ti);
               }
               persistNewEventAuditDataWOB(dea,DO,ti);               
            }
         }
         if(dea.getActivityId()!=null) {
            cus.info(LOG_CHANNEL,"DataEventAudit[processId="+dea.getProcessId()+",activityId="+dea.getActivityId()+"] persisted");
         }
         else {
            cus.info(LOG_CHANNEL,"DataEventAudit[processId="+dea.getProcessId()+"] persisted");
         }
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Persisting of DataEventAudit failed");
         throw new EventAuditException
            ("Persisting of DataEventAudit failed", tr);
      }
   }

   public void persist(StateEventAuditPersistenceInterface sea,SharkTransaction ti) throws EventAuditException {
      try {
         StateEventAuditDO DO=StateEventAuditDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         // when these attributes are persisted - they never change

         if (!DO.isPersistent()) {
            DO.setUTCTime(sea.getUTCTime());
            DO.oid_setTheType((BigDecimal)_evTypes.get(sea.getType()));
            DO.setProcessId(sea.getProcessId());
            if (sea.getActivityId()!=null) {
               DO.setActivityId(sea.getActivityId());
               DO.oid_setNewActivityState((BigDecimal)_acStates.get(sea.getNewState()));
               if (sea.getOldState()!=null) {
                  DO.oid_setOldActivityState((BigDecimal)_acStates.get(sea.getOldState()));
               }
            }
            else {
               DO.oid_setNewProcessState((BigDecimal)_prStates.get(sea.getNewState()));
               if (sea.getOldState()!=null) {
                  DO.oid_setOldProcessState((BigDecimal)_prStates.get(sea.getOldState()));
               }
            }

            if (sea.getActivityName()!=null) {
               DO.setActivityName(sea.getActivityName());
            }
            DO.setProcessName(sea.getProcessName());
            DO.setProcessDefinitionName(sea.getProcessDefinitionName());
            DO.setProcessDefinitionVersion(sea.getProcessDefinitionVersion());
            if (sea.getActivityDefinitionId()!=null) {
               DO.setActivityDefinitionId(sea.getActivityDefinitionId());
               DO.setActivitySetDefinitionId(sea.getActivitySetDefinitionId());
            }
            DO.setProcessDefinitionId(sea.getProcessDefinitionId());
            DO.setPackageId(sea.getPackageId());
            DO.setCNT(getNextDecId("stateeventaudit"));
            ((SharkDODSTransaction)ti).store(DO);
         }
         if(sea.getActivityId()!=null) {
            cus.info(LOG_CHANNEL,"StateEventAudit[processId="+sea.getProcessId()+",activityId="+sea.getActivityId()+"] persisted");
         }
         else {
            cus.info(LOG_CHANNEL,"StateEventAudit[processId="+sea.getProcessId()+"] persisted");
         }
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Persisting of StateEventAudit failed");
         //tr.printStackTrace();
         throw new EventAuditException
            ("Persisting of StateEventAudit failed", tr);
      }
   }

   public void persist(CreateProcessEventAuditPersistenceInterface cpea,SharkTransaction ti) throws EventAuditException {
      try {
         CreateProcessEventAuditDO DO=CreateProcessEventAuditDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         // when these attributes are persisted - they never change
         if (!DO.isPersistent()) {
            DO.setUTCTime(cpea.getUTCTime());
            DO.oid_setTheType((BigDecimal)_evTypes.get(cpea.getType()));
            DO.setProcessId(cpea.getProcessId());
            DO.setProcessName(cpea.getProcessName());
            DO.setProcessDefinitionId(cpea.getProcessDefinitionId());
            DO.setProcessDefinitionName(cpea.getProcessDefinitionName());
            DO.setProcessDefinitionVersion(cpea.getProcessDefinitionVersion());
            DO.setPackageId(cpea.getPackageId());
            if (cpea.getPProcessId()!=null) {
               DO.setPProcessId(cpea.getPProcessId());
            }
            if (cpea.getPProcessName()!=null) {
               DO.setPProcessName(cpea.getPProcessName());
            }
            if (cpea.getPActivityId()!=null) {
               DO.setPActivityId(cpea.getPActivityId());
            }
            if (cpea.getPPackageId()!=null) {
               DO.setPPackageId(cpea.getPPackageId());
            }
            if (cpea.getPProcessDefinitionId()!=null) {
               DO.setPProcessDefinitionId(cpea.getPProcessDefinitionId());
            }
            if (cpea.getPActivityDefinitionId()!=null) {
               DO.setPActivityDefinitionId(cpea.getPActivityDefinitionId());
               DO.setPActivitySetDefinitionId(cpea.getPActivitySetDefinitionId());
            }
            if (cpea.getPProcessDefinitionName()!=null) {
               DO.setPProcessDefinitionName(cpea.getPProcessDefinitionName());
            }
            if (cpea.getPProcessDefinitionVersion()!=null) {
               DO.setPProcessDefinitionVersion(cpea.getPProcessDefinitionVersion());
            }
            DO.setCNT(getNextDecId("createprocesseventaudit"));
            ((SharkDODSTransaction)ti).store(DO);
         }
         cus.info(LOG_CHANNEL,"CreateProcessEventAudit[processId="+cpea.getProcessId()+"] persisted");
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Persisting of CreateProcessEventAudit failed");
         throw new EventAuditException
            ("Persisting of CreateProcessEventAudit failed", tr);
      }
   }

   protected void persistOldEventAuditDataBLOB (DataEventAuditPersistenceInterface dea,
                                          DataEventAuditDO deaDO,SharkTransaction ti) throws EventAuditException {
      try {
         Map od=dea.getOldData();
         if (null != od) {
            Iterator it=od.entrySet().iterator();
            while (it.hasNext()) {
               Map.Entry me=(Map.Entry)it.next();
               String vdId=(String)me.getKey();
               Object val=me.getValue();
               OldEventAuditDataDO oedDO=OldEventAuditDataDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
               oedDO.setDataEventAudit(deaDO);
               oedDO.setVariableDefinitionId(vdId);

               boolean isBLOB=false;
               if (val instanceof Boolean) {
                  oedDO.setVariableValueBOOL(((Boolean)val).booleanValue());
                  oedDO.setVariableType(DODSEventAuditManager.DB_TYPE_BOOLEAN);
               } else if (val instanceof Long) {
                  oedDO.setVariableValueLONG(((Long)val).longValue());
                  oedDO.setVariableType(DODSEventAuditManager.DB_TYPE_LONG);
               } else if (val instanceof String) {
                  String sv=(String)val;
                  if (sv.length()<=max_vchar_size) {
                     oedDO.setVariableValueVCHAR(sv);
                     oedDO.setVariableType(DODSEventAuditManager.DB_TYPE_VCHAR);
                  } else {
                     isBLOB=true;
                  }
               } else if (val instanceof Double) {
                  oedDO.setVariableValueDBL(((Double)val).doubleValue());
                  oedDO.setVariableType(DODSEventAuditManager.DB_TYPE_DOUBLE);
               } else if (val instanceof java.util.Date) {
                  oedDO.setVariableValueDATE(new java.sql.Timestamp(((java.util.Date)val).getTime()));
                  oedDO.setVariableType(DODSEventAuditManager.DB_TYPE_DATE);
               } else if (val==null) {
                  short vt=oedDO.getVariableType();
                  if (vt==DODSEventAuditManager.DB_TYPE_DATE) {
                     oedDO.setVariableValueDATE(null);
                  } else if (vt==DODSEventAuditManager.DB_TYPE_VCHAR) {
                     oedDO.setVariableValueVCHAR(null);
                  } else {
                     oedDO.setVariableValue(null);
                     oedDO.setVariableType(DODSEventAuditManager.DB_TYPE_BLOB);
                  }
               } else {
                  isBLOB=true;
               }

               if (isBLOB) {
                  oedDO.setVariableValue(MiscUtilities.serialize(val));
                  oedDO.setVariableType(DODSEventAuditManager.DB_TYPE_BLOB);
               }

               ((SharkDODSTransaction)ti).store(oedDO);
            }
         }
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Persisting of OldEventAuditData failed");
         throw new EventAuditException
            ("Persisting of OldEventAuditData failed", tr);
      }
   }

   protected void persistOldEventAuditDataWOB (DataEventAuditPersistenceInterface dea,
         DataEventAuditDO deaDO,SharkTransaction ti) throws EventAuditException {
   
      try {
         Map od=dea.getOldData();
         if (null != od) {
            Iterator it=od.entrySet().iterator();
            while (it.hasNext()) {
               Map.Entry me=(Map.Entry)it.next();
               String vdId=(String)me.getKey();
               Object val=me.getValue();
               OldEventAuditDataWOBDO oedDO=OldEventAuditDataWOBDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
               oedDO.setDataEventAudit(deaDO);
               oedDO.setVariableDefinitionId(vdId);

               boolean isBLOB=false;
               if (val instanceof Boolean) {
                  oedDO.setVariableValueBOOL(((Boolean)val).booleanValue());
                  oedDO.setVariableType(DODSEventAuditManager.DB_TYPE_BOOLEAN);
               } else if (val instanceof Long) {
                  oedDO.setVariableValueLONG(((Long)val).longValue());
                  oedDO.setVariableType(DODSEventAuditManager.DB_TYPE_LONG);
               } else if (val instanceof String) {
                  String sv=(String)val;
                  if (sv.length()<=max_vchar_size) {
                     oedDO.setVariableValueVCHAR(sv);
                     oedDO.setVariableType(DODSEventAuditManager.DB_TYPE_VCHAR);
                  } else {
                     isBLOB=true;
                  }
               } else if (val instanceof Double) {
                  oedDO.setVariableValueDBL(((Double)val).doubleValue());
                  oedDO.setVariableType(DODSEventAuditManager.DB_TYPE_DOUBLE);
               } else if (val instanceof java.util.Date) {
                  oedDO.setVariableValueDATE(new java.sql.Timestamp(((java.util.Date)val).getTime()));
                  oedDO.setVariableType(DODSEventAuditManager.DB_TYPE_DATE);
               } else if (val==null) {
                  short vt=oedDO.getVariableType();
                  if (vt==DODSEventAuditManager.DB_TYPE_DATE) {
                     oedDO.setVariableValueDATE(null);
                  } else if (vt==DODSEventAuditManager.DB_TYPE_VCHAR) {
                     oedDO.setVariableValueVCHAR(null);
                  } else {
                     OldEventAuditDataBLOBDO bDO=OldEventAuditDataBLOBDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
                     bDO.setOldEventAuditDataWOB(oedDO);
                     bDO.setVariableValue(null);
                     oedDO.setVariableType(DODSEventAuditManager.DB_TYPE_BLOB);
                     ((SharkDODSTransaction)ti).store(bDO);               
                  }
               } else {
                  isBLOB=true;
               }

               if (isBLOB) {
                  OldEventAuditDataBLOBDO bDO=OldEventAuditDataBLOBDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
                  bDO.setOldEventAuditDataWOB(oedDO);
                  bDO.setVariableValue(MiscUtilities.serialize(val));
                  oedDO.setVariableType(DODSEventAuditManager.DB_TYPE_BLOB);
                  ((SharkDODSTransaction)ti).store(bDO);               
               }

               ((SharkDODSTransaction)ti).store(oedDO);
            }
         }
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Persisting of OldEventAuditData failed");
         throw new EventAuditException
            ("Persisting of OldEventAuditData failed", tr);
      }
   }
   
   protected void persistNewEventAuditDataBLOB (DataEventAuditPersistenceInterface dea,
                                          DataEventAuditDO deaDO,SharkTransaction ti) throws EventAuditException {
      try {
         Map nd=dea.getNewData();
         Iterator it=nd.entrySet().iterator();
         while (it.hasNext()) {
            Map.Entry me=(Map.Entry)it.next();
            String vdId=(String)me.getKey();
            Object val= me.getValue();
            NewEventAuditDataDO nedDO=NewEventAuditDataDO .createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
            nedDO.setDataEventAudit(deaDO);
            nedDO.setVariableDefinitionId(vdId);

            boolean isBLOB=false;
            if (val instanceof Boolean) {
               nedDO.setVariableValueBOOL(((Boolean)val).booleanValue());
               nedDO.setVariableType(DODSEventAuditManager.DB_TYPE_BOOLEAN);
            } else if (val instanceof Long) {
               nedDO.setVariableValueLONG(((Long)val).longValue());
               nedDO.setVariableType(DODSEventAuditManager.DB_TYPE_LONG);
            } else if (val instanceof String) {
               String sv=(String)val;
               if (sv.length()<=max_vchar_size) {
                  nedDO.setVariableValueVCHAR(sv);
                  nedDO.setVariableType(DODSEventAuditManager.DB_TYPE_VCHAR);
               } else {
                  isBLOB=true;
               }
            } else if (val instanceof Double) {
               nedDO.setVariableValueDBL(((Double)val).doubleValue());
               nedDO.setVariableType(DODSEventAuditManager.DB_TYPE_DOUBLE);
            } else if (val instanceof java.util.Date) {
               nedDO.setVariableValueDATE(new java.sql.Timestamp(((java.util.Date)val).getTime()));
               nedDO.setVariableType(DODSEventAuditManager.DB_TYPE_DATE);
            } else if (val==null) {
               short vt=nedDO.getVariableType();
               if (vt==DODSEventAuditManager.DB_TYPE_DATE) {
                  nedDO.setVariableValueDATE(null);
               } else if (vt==DODSEventAuditManager.DB_TYPE_VCHAR) {
                  nedDO.setVariableValueVCHAR(null);
               } else {
                  nedDO.setVariableValue(null);
                  nedDO.setVariableType(DODSEventAuditManager.DB_TYPE_BLOB);
               }
            } else {
               isBLOB=true;
            }

            if (isBLOB) {
               nedDO.setVariableValue(MiscUtilities.serialize(val));
               nedDO.setVariableType(DODSEventAuditManager.DB_TYPE_BLOB);
            }

            ((SharkDODSTransaction)ti).store(nedDO);
         }
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Persisting of NewEventAuditData failed");
         throw new EventAuditException
            ("Persisting of NewEventAuditData failed", tr);
      }
   }

   protected void persistNewEventAuditDataWOB (DataEventAuditPersistenceInterface dea,
         DataEventAuditDO deaDO,SharkTransaction ti) throws EventAuditException {

      try {
         Map nd=dea.getNewData();
         Iterator it=nd.entrySet().iterator();
         while (it.hasNext()) {
            Map.Entry me=(Map.Entry)it.next();
            String vdId=(String)me.getKey();
            Object val= me.getValue();
            NewEventAuditDataWOBDO nedDO=NewEventAuditDataWOBDO .createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
            nedDO.setDataEventAudit(deaDO);
            nedDO.setVariableDefinitionId(vdId);

            boolean isBLOB=false;
            if (val instanceof Boolean) {
               nedDO.setVariableValueBOOL(((Boolean)val).booleanValue());
               nedDO.setVariableType(DODSEventAuditManager.DB_TYPE_BOOLEAN);
            } else if (val instanceof Long) {
               nedDO.setVariableValueLONG(((Long)val).longValue());
               nedDO.setVariableType(DODSEventAuditManager.DB_TYPE_LONG);
            } else if (val instanceof String) {
               String sv=(String)val;
               if (sv.length()<=max_vchar_size) {
                  nedDO.setVariableValueVCHAR(sv);
                  nedDO.setVariableType(DODSEventAuditManager.DB_TYPE_VCHAR);
               } else {
                  isBLOB=true;
               }
            } else if (val instanceof Double) {
               nedDO.setVariableValueDBL(((Double)val).doubleValue());
               nedDO.setVariableType(DODSEventAuditManager.DB_TYPE_DOUBLE);
            } else if (val instanceof java.util.Date) {
               nedDO.setVariableValueDATE(new java.sql.Timestamp(((java.util.Date)val).getTime()));
               nedDO.setVariableType(DODSEventAuditManager.DB_TYPE_DATE);
            } else if (val==null) {
               short vt=nedDO.getVariableType();
               if (vt==DODSEventAuditManager.DB_TYPE_DATE) {
                  nedDO.setVariableValueDATE(null);
               } else if (vt==DODSEventAuditManager.DB_TYPE_VCHAR) {
                  nedDO.setVariableValueVCHAR(null);
               } else {
                  NewEventAuditDataBLOBDO bDO=NewEventAuditDataBLOBDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
                  bDO.setNewEventAuditDataWOB(nedDO);
                  bDO.setVariableValue(null);

                  nedDO.setVariableType(DODSEventAuditManager.DB_TYPE_BLOB);
                  ((SharkDODSTransaction)ti).store(bDO);               
               }
            } else {
               isBLOB=true;
            }

            if (isBLOB) {
               NewEventAuditDataBLOBDO bDO=NewEventAuditDataBLOBDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
               bDO.setNewEventAuditDataWOB(nedDO);
               bDO.setVariableValue(MiscUtilities.serialize(val));
               nedDO.setVariableType(DODSEventAuditManager.DB_TYPE_BLOB);
               ((SharkDODSTransaction)ti).store(bDO);               
            }

            ((SharkDODSTransaction)ti).store(nedDO);
         }
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Persisting of NewEventAuditData failed");
         throw new EventAuditException
            ("Persisting of NewEventAuditData failed", tr);
      }
   }
   
   public boolean restore(AssignmentEventAuditPersistenceInterface aea,SharkTransaction ti) throws EventAuditException {
      return restore(aea,getPersistedObject(aea,ti),ti);
   }

   protected boolean restore(
      AssignmentEventAuditPersistenceInterface engineObj,
      AssignmentEventAuditDO DO,SharkTransaction ti) throws EventAuditException {
      if (DO==null) return false;

      try {
         engineObj.setUTCTime(DO.getUTCTime());
         engineObj.setType(DO.getTheType().getName());
         engineObj.setProcessId(DO.getProcessId());
         engineObj.setActivityId(DO.getActivityId());
         if (DO.getActivityName()!=null) {
            engineObj.setActivityName(DO.getActivityName());
         }
         engineObj.setProcessName(DO.getProcessName());
         engineObj.setProcessDefinitionName(DO.getProcessDefinitionName());
         engineObj.setProcessDefinitionVersion(DO.getProcessDefinitionVersion());
         engineObj.setActivityDefinitionId(DO.getActivityDefinitionId());
         engineObj.setActivitySetDefinitionId(DO.getActivitySetDefinitionId());
         engineObj.setProcessDefinitionId(DO.getProcessDefinitionId());
         engineObj.setPackageId(DO.getPackageId());
         engineObj.setNewResourceUsername(DO.getNewResourceUsername());
         if (DO.getNewResourceName()!=null) {
            engineObj.setNewResourceName(DO.getNewResourceName());
         }
         if (DO.getOldResourceUsername()!=null) {
            engineObj.setOldResourceUsername(DO.getOldResourceUsername());
         }
         if (DO.getOldResourceName()!=null) {
            engineObj.setOldResourceName(DO.getOldResourceName());
         }
         engineObj.setIsAccepted(DO.getIsAccepted());
         cus.info(LOG_CHANNEL,"AssignmentEventAudit[processId="+DO.getProcessId()+",activityId="+DO.getActivityId()+"] restored");
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Restoring of AssignmentEventAudit failed");
         throw new EventAuditException
            ("Restoring of AssignmentEventAudit failed", tr);
      }
      return true;
   }

   public boolean restore(DataEventAuditPersistenceInterface dea,SharkTransaction ti) throws EventAuditException {
      return restore(dea,getPersistedObject(dea,ti),ti);
   }

   protected boolean restore(
      DataEventAuditPersistenceInterface engineObj,DataEventAuditDO DO,SharkTransaction ti) throws EventAuditException {
      if (DO==null) return false;

      try {
         engineObj.setUTCTime(DO.getUTCTime());
         engineObj.setType(DO.getTheType().getName());
         engineObj.setProcessId(DO.getProcessId());
         if (DO.getActivityId()!=null) {
            engineObj.setActivityId(DO.getActivityId());
         }
         if (DO.getActivityName()!=null) {
            engineObj.setActivityName(DO.getActivityName());
         }
         engineObj.setProcessName(DO.getProcessName());
         engineObj.setProcessDefinitionName(DO.getProcessDefinitionName());
         engineObj.setProcessDefinitionVersion(DO.getProcessDefinitionVersion());
         if (DO.getActivityDefinitionId()!=null) {
            engineObj.setActivityDefinitionId(DO.getActivityDefinitionId());
            engineObj.setActivitySetDefinitionId(DO.getActivitySetDefinitionId());
         }
         engineObj.setProcessDefinitionId(DO.getProcessDefinitionId());
         engineObj.setPackageId(DO.getPackageId());
         // First, the package and process definition ids must be set, and
         // then perform following operations
         if (usingStandardVariableDataModel) {
            if (persistOldEventAuditData) {
               restoreOldEventAuditDataBLOB(engineObj,DO,ti);
            }
            restoreNewEventAuditDataBLOB(engineObj,DO,ti);
         } else {
            if (persistOldEventAuditData) {
               restoreOldEventAuditDataWOB(engineObj,DO,ti);
            }
            restoreNewEventAuditDataWOB(engineObj,DO,ti);            
         }
         if(DO.getActivityId()!=null) {
            cus.info(LOG_CHANNEL,"DataEventAudit[processId="+DO.getProcessId()+",activityId="+DO.getActivityId()+"] restored");
         }
         else {
            cus.info(LOG_CHANNEL,"DataEventAudit[processId="+DO.getProcessId()+"] restored");
         }
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Restoring of DataEventAudit failed");
         throw new EventAuditException
            ("Restoring of DataEventAudit failed", tr);
      }
      return true;
   }

   public boolean restore(StateEventAuditPersistenceInterface sea,SharkTransaction ti) throws EventAuditException {
      return restore(sea,getPersistedObject(sea,ti),ti);
   }

   protected boolean restore(
      StateEventAuditPersistenceInterface engineObj,StateEventAuditDO DO,SharkTransaction ti) throws EventAuditException {
      if (DO==null) return false;

      try {
         engineObj.setUTCTime(DO.getUTCTime());
         engineObj.setType(DO.getTheType().getName());
         engineObj.setProcessId(DO.getProcessId());
         if (DO.getActivityId()!=null) {
            engineObj.setActivityId(DO.getActivityId());
            engineObj.setNewState(DO.getNewActivityState().getName());
            if (DO.getOldActivityState()!=null) {
               engineObj.setOldState(DO.getOldActivityState().getName());
            }
         }
         else {
            engineObj.setNewState(DO.getNewProcessState().getName());
            if (DO.getOldProcessState()!=null) {
               engineObj.setOldState(DO.getOldProcessState().getName());
            }
         }
         if (DO.getActivityName()!=null) {
            engineObj.setActivityName(DO.getActivityName());
         }
         engineObj.setProcessName(DO.getProcessName());
         engineObj.setProcessDefinitionName(DO.getProcessDefinitionName());
         engineObj.setProcessDefinitionVersion(DO.getProcessDefinitionVersion());
         if (DO.getActivityDefinitionId()!=null) {
            engineObj.setActivityDefinitionId(DO.getActivityDefinitionId());
            engineObj.setActivitySetDefinitionId(DO.getActivitySetDefinitionId());
         }
         engineObj.setProcessDefinitionId(DO.getProcessDefinitionId());
         engineObj.setPackageId(DO.getPackageId());
         if(DO.getActivityId()!=null) {
            cus.info(LOG_CHANNEL,"StateEventAudit[processId="+DO.getProcessId()+",activityId="+DO.getActivityId()+"] restored");
         }
         else {
            cus.info(LOG_CHANNEL,"StateEventAudit[processId="+DO.getProcessId()+"] restored");
         }
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Restoring of StateEventAudit failed");
         throw new EventAuditException
            ("Restoring of StateEventAudit failed", tr);
      }
      return true;
   }

   public boolean restore(CreateProcessEventAuditPersistenceInterface cpea,
                          SharkTransaction ti) throws EventAuditException {
      return restore(cpea,getPersistedObject(cpea,ti),ti);
   }

   protected boolean restore(
      CreateProcessEventAuditPersistenceInterface engineObj,
      CreateProcessEventAuditDO DO,SharkTransaction ti) throws EventAuditException {
      if (DO==null) return false;

      try {
         engineObj.setUTCTime(DO.getUTCTime());
         engineObj.setType(DO.getTheType().getName());
         engineObj.setProcessId(DO.getProcessId());
         engineObj.setProcessName(DO.getProcessName());
         engineObj.setProcessDefinitionId(DO.getProcessDefinitionId());
         engineObj.setProcessDefinitionName(DO.getProcessDefinitionName());
         engineObj.setProcessDefinitionVersion(DO.getProcessDefinitionVersion());
         engineObj.setPackageId(DO.getPackageId());
         if (DO.getPProcessId()!=null) {
            engineObj.setPProcessId(DO.getPProcessId());
         }
         if (DO.getPProcessName()!=null) {
            engineObj.setPProcessName(DO.getPProcessName());
         }
         if (DO.getPActivityId()!=null) {
            engineObj.setPActivityId(DO.getPActivityId());
         }
         if (DO.getPPackageId()!=null) {
            engineObj.setPPackageId(DO.getPPackageId());
         }
         if (DO.getPProcessDefinitionId()!=null) {
            engineObj.setPProcessDefinitionId(DO.getPProcessDefinitionId());
         }
         if (DO.getPActivityDefinitionId()!=null) {
            engineObj.setPActivityDefinitionId(DO.getPActivityDefinitionId());
            engineObj.setPActivitySetDefinitionId(DO.getPActivitySetDefinitionId());
         }
         if (DO.getPProcessDefinitionName()!=null) {
            engineObj.setPProcessDefinitionName(DO.getPProcessDefinitionName());
         }
         if (DO.getPProcessDefinitionVersion()!=null) {
            engineObj.setPProcessDefinitionVersion(DO.getPProcessDefinitionVersion());
         }
         cus.info(LOG_CHANNEL,"CreateProcessEventAudit[processId="+DO.getProcessId()+"] restored");
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Restoring of CreateProcessEventAudit failed");
         throw new EventAuditException
            ("Restoring of CreateProcessEventAudit failed", tr);
      }
      return true;
   }

   protected void restoreOldEventAuditDataBLOB (DataEventAuditPersistenceInterface dea,
                                          DataEventAuditDO deaDO,  SharkTransaction ti) throws EventAuditException {
      if (deaDO==null) return;

      try {
         Map od=new HashMap();
         OldEventAuditDataDO[] DOs=deaDO.getOldEventAuditDataDOArray();
         if (DOs!=null) {
            BigDecimal dOId = deaDO.get_OId().toBigDecimal();
            List list = new ArrayList(Arrays.asList(DOs));
            for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.eventaudit.data.OldEventAuditDataDO");
                 it.hasNext();) {
               OldEventAuditDataDO o = (OldEventAuditDataDO)it.next();
               if (dOId.equals(o.oid_getDataEventAudit())) {
                  int index = list.indexOf(o);
                  if (-1 == index)
                     list.add(o);
                  else {
                     list.set(index, o);
                  }
               }
            }
            DOs = new OldEventAuditDataDO[list.size()];
            DOs = (OldEventAuditDataDO[])list.toArray(DOs);
            if (DOs!=null) {
               for (int j=0; j<DOs.length; j++) {
                  String vdId=DOs[j].getVariableDefinitionId();


                  short vtype=DOs[j].getVariableType();
                  Object val;
                  switch (vtype) {
                     case DODSEventAuditManager.DB_TYPE_BOOLEAN :
                        val=new Boolean(DOs[j].getVariableValueBOOL());
                        break;
                     case DODSEventAuditManager.DB_TYPE_LONG:
                        val=new Long(DOs[j].getVariableValueLONG());
                        break;
                     case DODSEventAuditManager.DB_TYPE_DOUBLE:
                        val=new Double(DOs[j].getVariableValueDBL());
                        break;
                     case DODSEventAuditManager.DB_TYPE_VCHAR:
                        val=DOs[j].getVariableValueVCHAR();
                        break;
                     case DODSEventAuditManager.DB_TYPE_DATE:
                        java.sql.Timestamp d=DOs[j].getVariableValueDATE();
                        if (d!=null) {
                           val=new java.util.Date(d.getTime());
                        } else {
                           val=null;
                        }
                        break;
                     default:
                        byte[] v=DOs[j].getVariableValue();
                        if (v!=null && v.length>0) {
                           val=MiscUtilities.deserialize(v);
                        } else {
                           val=null;
                        }
                  }

                  od.put(vdId,val);
               }
            }
         }
         dea.setOldData(od);
      }
      catch (Throwable t) {
         throw new EventAuditException(t);
      }
   }

   protected void restoreOldEventAuditDataWOB (DataEventAuditPersistenceInterface dea,
         DataEventAuditDO deaDO,  SharkTransaction ti) throws EventAuditException {
      if (deaDO==null) return;

      try {
         Map od=new HashMap();
         OldEventAuditDataWOBDO[] DOs=deaDO.getOldEventAuditDataWOBDOArray();
         if (DOs!=null) {
            BigDecimal dOId = deaDO.get_OId().toBigDecimal();
            List list = new ArrayList(Arrays.asList(DOs));
            for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.eventaudit.data.OldEventAuditDataWOBDO");
                 it.hasNext();) {
               OldEventAuditDataWOBDO o = (OldEventAuditDataWOBDO)it.next();
               if (dOId.equals(o.oid_getDataEventAudit())) {
                  int index = list.indexOf(o);
                  if (-1 == index)
                     list.add(o);
                  else {
                     list.set(index, o);
                  }
               }
            }
            DOs = new OldEventAuditDataWOBDO[list.size()];
            DOs = (OldEventAuditDataWOBDO[])list.toArray(DOs);
            if (DOs!=null) {
               for (int j=0; j<DOs.length; j++) {
                  String vdId=DOs[j].getVariableDefinitionId();


                  short vtype=DOs[j].getVariableType();
                  Object val;
                  switch (vtype) {
                     case DODSEventAuditManager.DB_TYPE_BOOLEAN :
                        val=new Boolean(DOs[j].getVariableValueBOOL());
                        break;
                     case DODSEventAuditManager.DB_TYPE_LONG:
                        val=new Long(DOs[j].getVariableValueLONG());
                        break;
                     case DODSEventAuditManager.DB_TYPE_DOUBLE:
                        val=new Double(DOs[j].getVariableValueDBL());
                        break;
                     case DODSEventAuditManager.DB_TYPE_VCHAR:
                        val=DOs[j].getVariableValueVCHAR();
                        break;
                     case DODSEventAuditManager.DB_TYPE_DATE:
                        java.sql.Timestamp d=DOs[j].getVariableValueDATE();
                        if (d!=null) {
                           val=new java.util.Date(d.getTime());
                        } else {
                           val=null;
                        }
                        break;
                     default:
                        byte[] v=DOs[j].getOldEventAuditDataBLOBDO().getVariableValue();
                        if (v!=null && v.length>0) {
                           val=MiscUtilities.deserialize(v);
                        } else {
                           val=null;
                        }
                  }

                  od.put(vdId,val);
               }
            }
         }
         dea.setOldData(od);
      }
      catch (Throwable t) {
         throw new EventAuditException(t);
      }
   }   
   
   protected void restoreNewEventAuditDataBLOB (DataEventAuditPersistenceInterface dea,
                                          DataEventAuditDO deaDO,  SharkTransaction ti) throws EventAuditException {
      if (deaDO==null) return;

      try {
         Map nd=new HashMap();
         NewEventAuditDataDO[] DOs=deaDO.getNewEventAuditDataDOArray();
         if (DOs!=null) {
            BigDecimal dOId = deaDO.get_OId().toBigDecimal();
            List list = new ArrayList(Arrays.asList(DOs));
            for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.eventaudit.data.NewEventAuditDataDO");
                 it.hasNext();) {
               NewEventAuditDataDO o = (NewEventAuditDataDO)it.next();
               if (dOId.equals(o.oid_getDataEventAudit())) {
                  int index = list.indexOf(o);
                  if (-1 == index)
                     list.add(o);
                  else {
                     list.set(index, o);
                  }
               }
            }
            DOs = new NewEventAuditDataDO[list.size()];
            DOs = (NewEventAuditDataDO[])list.toArray(DOs);
            if (DOs!=null) {
               for (int j=0; j<DOs.length; j++) {
                  String vdId=DOs[j].getVariableDefinitionId();

                  short vtype=DOs[j].getVariableType();
                  Object val;
                  switch (vtype) {
                     case DODSEventAuditManager.DB_TYPE_BOOLEAN :
                        val=new Boolean(DOs[j].getVariableValueBOOL());
                        break;
                     case DODSEventAuditManager.DB_TYPE_LONG:
                        val=new Long(DOs[j].getVariableValueLONG());
                        break;
                     case DODSEventAuditManager.DB_TYPE_DOUBLE:
                        val=new Double(DOs[j].getVariableValueDBL());
                        break;
                     case DODSEventAuditManager.DB_TYPE_VCHAR:
                        val=DOs[j].getVariableValueVCHAR();
                        break;
                     case DODSEventAuditManager.DB_TYPE_DATE:
                        java.sql.Timestamp d=DOs[j].getVariableValueDATE();
                        if (d!=null) {
                           val=new java.util.Date(d.getTime());
                        } else {
                           val=null;
                        }
                        break;
                     default:
                        byte[] v=DOs[j].getVariableValue();
                        if (v!=null && v.length>0) {
                           val=MiscUtilities.deserialize(v);
                        } else {
                           val=null;
                        }
                  }

                  nd.put(vdId,val);
               }
            }
         }
         dea.setNewData(nd);
      }
      catch (Throwable t) {
         throw new EventAuditException(t);
      }
   }

   protected void restoreNewEventAuditDataWOB (DataEventAuditPersistenceInterface dea,
         DataEventAuditDO deaDO,  SharkTransaction ti) throws EventAuditException {
      if (deaDO==null) return;

      try {
         Map nd=new HashMap();
         NewEventAuditDataWOBDO[] DOs=deaDO.getNewEventAuditDataWOBDOArray();
         if (DOs!=null) {
            BigDecimal dOId = deaDO.get_OId().toBigDecimal();
            List list = new ArrayList(Arrays.asList(DOs));
            for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.eventaudit.data.NewEventAuditDataWOBDO");
                 it.hasNext();) {
               NewEventAuditDataWOBDO o = (NewEventAuditDataWOBDO)it.next();
               if (dOId.equals(o.oid_getDataEventAudit())) {
                  int index = list.indexOf(o);
                  if (-1 == index)
                     list.add(o);
                  else {
                     list.set(index, o);
                  }
               }
            }
            DOs = new NewEventAuditDataWOBDO[list.size()];
            DOs = (NewEventAuditDataWOBDO[])list.toArray(DOs);
            if (DOs!=null) {
               for (int j=0; j<DOs.length; j++) {
                  String vdId=DOs[j].getVariableDefinitionId();

                  short vtype=DOs[j].getVariableType();
                  Object val;
                  switch (vtype) {
                     case DODSEventAuditManager.DB_TYPE_BOOLEAN :
                        val=new Boolean(DOs[j].getVariableValueBOOL());
                        break;
                     case DODSEventAuditManager.DB_TYPE_LONG:
                        val=new Long(DOs[j].getVariableValueLONG());
                        break;
                     case DODSEventAuditManager.DB_TYPE_DOUBLE:
                        val=new Double(DOs[j].getVariableValueDBL());
                        break;
                     case DODSEventAuditManager.DB_TYPE_VCHAR:
                        val=DOs[j].getVariableValueVCHAR();
                        break;
                     case DODSEventAuditManager.DB_TYPE_DATE:
                        java.sql.Timestamp d=DOs[j].getVariableValueDATE();
                        if (d!=null) {
                           val=new java.util.Date(d.getTime());
                        } else {
                           val=null;
                        }
                        break;
                     default:
                        byte[] v=DOs[j].getNewEventAuditDataBLOBDO().getVariableValue();
                        if (v!=null && v.length>0) {
                           val=MiscUtilities.deserialize(v);
                        } else {
                           val=null;
                        }
                  }

                  nd.put(vdId,val);
               }
            }
         }
         dea.setNewData(nd);
      }
      catch (Throwable t) {
         throw new EventAuditException(t);
      }
   }
   
   public List restoreProcessHistory (String procId,
                                      SharkTransaction ti) throws EventAuditException {
      List history=new ArrayList();

      CreateProcessEventAuditDO[] createProcessHistoryDOs=
         getPersistedCreateProcessEventObjects(procId,ti);
      DataEventAuditDO[] dataHistoryDOs=getPersistedDataEventObjects(procId,null,ti);
      StateEventAuditDO[] stateHistoryDOs=getPersistedStateEventObjects(procId,null,ti);
      if (createProcessHistoryDOs!=null) {
         for (int i=0; i<createProcessHistoryDOs.length; i++) {
            CreateProcessEventAuditPersistenceInterface a=new DODSCreateProcessEventAudit();
            restore(a,createProcessHistoryDOs[i],ti);
            history.add(a);
         }
      }
      if (dataHistoryDOs!=null) {
         for (int i=0; i<dataHistoryDOs.length; i++) {
            DataEventAuditPersistenceInterface d=new DODSDataEventAudit();
            restore(d,dataHistoryDOs[i],ti);
            history.add(d);
         }
      }
      if (stateHistoryDOs!=null) {
         for (int i=0; i<stateHistoryDOs.length; i++) {
            StateEventAuditPersistenceInterface s=new DODSStateEventAudit();
            restore(s,stateHistoryDOs[i],ti);
            history.add(s);
         }
      }

      return history;
   }

   public List restoreActivityHistory (String procId,String actId,
                                       SharkTransaction ti) throws EventAuditException {
      List history=new ArrayList();

      AssignmentEventAuditDO[] assignmentHistoryDOs=getPersistedAssignmentEventObjects(procId,actId,ti);
      DataEventAuditDO[] dataHistoryDOs=getPersistedDataEventObjects(procId,actId,ti);
      StateEventAuditDO[] stateHistoryDOs=getPersistedStateEventObjects(procId,actId,ti);
      if (assignmentHistoryDOs!=null) {
         for (int i=0; i<assignmentHistoryDOs.length; i++) {
            AssignmentEventAuditPersistenceInterface a=
               new DODSAssignmentEventAudit();
            restore(a,assignmentHistoryDOs[i],ti);
            history.add(a);
         }
      }
      if (dataHistoryDOs!=null) {
         for (int i=0; i<dataHistoryDOs.length; i++) {
            DataEventAuditPersistenceInterface d=
               new DODSDataEventAudit();
            restore(d,dataHistoryDOs[i],ti);
            history.add(d);
         }
      }
      if (stateHistoryDOs!=null) {
         for (int i=0; i<stateHistoryDOs.length; i++) {
            StateEventAuditPersistenceInterface s=
               new DODSStateEventAudit();
            restore(s,stateHistoryDOs[i],ti);
            history.add(s);
         }
      }

      return history;
   }

   public void delete(AssignmentEventAuditPersistenceInterface aea,SharkTransaction ti) throws EventAuditException {
      // NEVER DELETE EVENTS
      if (true) return;
      try {
         AssignmentEventAuditDO DO=getPersistedObject(aea,ti);
         ((SharkDODSTransaction)ti).erase(DO);
         //DO.delete(((SharkDODSTransaction)ti).getDODSTransaction());
         //(((SharkDODSTransaction)ti).getDODSTransaction()).write();

         cus.info(LOG_CHANNEL,"AssignmentEventAudit[processId="+aea.getProcessId()+",activityId="+aea.getActivityId()+"] deleted");
      } catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Deleting of AssignmentEventAudit failed");
         throw new EventAuditException
            ("Deleting of AssignmentEventAudit failed", tr);
      }
   }

   public void delete(DataEventAuditPersistenceInterface dea,SharkTransaction ti) throws EventAuditException {
      // NEVER DELETE EVENTS
      if (true) return;
      try {
         DataEventAuditDO DO=getPersistedObject(dea,ti);
         ((SharkDODSTransaction)ti).erase(DO);
         //DO.delete(((SharkDODSTransaction)ti).getDODSTransaction());
         //(((SharkDODSTransaction)ti).getDODSTransaction()).write();
         if(dea.getActivityId()!=null) {
            cus.info(LOG_CHANNEL,"DataEventAudit[processId="+dea.getProcessId()+",activityId="+dea.getActivityId()+"] deleted");
         }
         else {
            cus.info(LOG_CHANNEL,"DataEventAudit[processId="+dea.getProcessId()+"] deleted");
         }
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Deleting of DataEventAudit failed");
         throw new EventAuditException
            ("Deleting of DataEventAudit failed", tr);
      }
   }

   public void delete(StateEventAuditPersistenceInterface sea,SharkTransaction ti) throws EventAuditException {
      // NEVER DELETE EVENTS
      if (true) return;
      try {
         StateEventAuditDO DO=getPersistedObject(sea,ti);
         ((SharkDODSTransaction)ti).erase(DO);
         //DO.delete(((SharkDODSTransaction)ti).getDODSTransaction());
         //(((SharkDODSTransaction)ti).getDODSTransaction()).write();
         if(sea.getActivityId()!=null) {
            cus.info(LOG_CHANNEL,"StateEventAudit[processId="+sea.getProcessId()+",activityId="+sea.getActivityId()+"] deleted");
         }
         else {
            cus.info(LOG_CHANNEL,"StateEventAudit[processId="+sea.getProcessId()+"] deleted");
         }
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Deleting of StateEventAudit failed");
         throw new EventAuditException
            ("Deleting of StateEventAudit failed", tr);
      }
   }

   public void delete(CreateProcessEventAuditPersistenceInterface cpea,SharkTransaction ti) throws EventAuditException {
      // NEVER DELETE EVENTS
      if (true) return;
      try {
         CreateProcessEventAuditDO DO=getPersistedObject(cpea,ti);
         ((SharkDODSTransaction)ti).erase(DO);
         //DO.delete(((SharkDODSTransaction)ti).getDODSTransaction());
         //(((SharkDODSTransaction)ti).getDODSTransaction()).write();
         cus.info(LOG_CHANNEL,"CreateProcessEventAudit[processId="+cpea.getProcessId()+"] deleted");
      }
      catch (Throwable tr) {
         cus.error(LOG_CHANNEL,"Deleting of CreateProcessEventAudit failed");
         throw new EventAuditException
            ("Deleting of CreateProcessEventAudit failed", tr);
      }
   }

   protected AssignmentEventAuditDO getPersistedObject (AssignmentEventAuditPersistenceInterface aea,
                                                      SharkTransaction ti) throws EventAuditException {
      AssignmentEventAuditDO DO=null;

      AssignmentEventAuditQuery query = null;
      try {
         String  procId = aea.getProcessId();
         String  actId = aea.getActivityId();
         String  utcTime = aea.getUTCTime();
         String  eType = aea.getType();
         String  newResUserName = aea.getNewResourceUsername();
         String  oldResUserName = aea.getOldResourceUsername();

         for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.eventaudit.data.AssignmentEventAuditDO");
              it.hasNext();) {
            DO = (AssignmentEventAuditDO)it.next();

            if (procId.equals(DO.getProcessId()) && actId.equals(DO.getActivityId())
                && utcTime.equals(DO.getUTCTime()) && eType.equals(DO.getTheType().getName())
                && newResUserName.equals(DO.getNewResourceUsername())
                && (oldResUserName==null || oldResUserName.equals(DO.getOldResourceName())))
               return DO;
         }
         query=new AssignmentEventAuditQuery(((SharkDODSTransaction)ti).getDODSTransaction());

         //set query
         query.setQueryProcessId(procId);
         query.setQueryActivityId(actId);
         query.setQueryUTCTime(utcTime);
         query.setQueryTheType(getPersistedEventTypeObject(eType,false,ti));
         query.setQueryNewResourceUsername(newResUserName);
         if (oldResUserName!=null) {
            query.setQueryOldResourceUsername(oldResUserName);
         }
         //query.setQueryIsAccepted(aea.getIsAccepted()); // THE QED is complaining if we uncomment this line

         // Throw an exception if more than one object is found
         query.requireUniqueInstance();

         DO = query.getNextDO();
         ((SharkDODSTransaction)ti)._read(DO);

         if (DO==null) {
            DO=AssignmentEventAuditDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         }
         return DO;
      }
      catch (Throwable t) {
         throw new EventAuditException(t);
      }
   }

   protected AssignmentEventAuditDO[] getPersistedAssignmentEventObjects (String pId,String aId,
                                                                        SharkTransaction ti) throws EventAuditException {
      AssignmentEventAuditDO[] DOs=null;

      AssignmentEventAuditQuery query = null;
      try {
         query=new AssignmentEventAuditQuery(((SharkDODSTransaction)ti).getDODSTransaction());
         //set query
         query.setQueryProcessId(pId);
         query.setQueryActivityId(aId);
         DOs = query.getDOArray();
         List list = new ArrayList(Arrays.asList(DOs));
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.eventaudit.data.AssignmentEventAuditDO");
              it.hasNext();) {
            AssignmentEventAuditDO DO = (AssignmentEventAuditDO)it.next();
            if (pId.equals(DO.getProcessId()) && aId.equals(DO.getActivityId())){
               int index = list.indexOf(DO);
               if (-1 == index)
                  list.add(DO);
               else {
                  list.set(index, DO);
               }
               //if (!list.contains(DO))
               //   list.add(o);
            }
         }
         DOs = new AssignmentEventAuditDO[list.size()];
         return (AssignmentEventAuditDO[])list.toArray(DOs);
         //return DOs;
      }
      catch (Throwable t) {
         throw new EventAuditException(t);
      }
   }

   protected DataEventAuditDO getPersistedObject (DataEventAuditPersistenceInterface dea,
                                                SharkTransaction ti) throws EventAuditException {
      DataEventAuditDO DO=null;

      DataEventAuditQuery query = null;
      try {

         String  procId = dea.getProcessId();
         String  actId = dea.getActivityId();
         String  utcTime = dea.getUTCTime();
         String  eType = dea.getType();

         for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.eventaudit.data.DataEventAuditDO");
              it.hasNext();) {
            DO = (DataEventAuditDO)it.next();

            if (procId.equals(DO.getProcessId()) && utcTime.equals(DO.getUTCTime()))  {
               if (actId != null){
                  if (actId.equals(DO.getActivityId())
                      && ( ACTIVITY_CONTEXT_CHANGED.equals(DO.getTheType().getName()) || ACTIVITY_RESULT_CHANGED.equals(DO.getTheType().getName())))
                     return DO;
               }
               else {
                  if (PROCESS_CONTEXT_CHANGED.equals(DO.getTheType().getName()))
                     return DO;
               }
            }
         }
         query=new DataEventAuditQuery(((SharkDODSTransaction)ti).getDODSTransaction());
         //set query
         query.setQueryProcessId(procId);
         if (actId!=null) {
            query.setQueryActivityId(actId);
            query.openParen();
            query.setQueryTheType(getPersistedEventTypeObject(ACTIVITY_CONTEXT_CHANGED,false,ti));
            query.or();
            query.setQueryTheType(getPersistedEventTypeObject(ACTIVITY_RESULT_CHANGED,false,ti));
            query.closeParen();
         }
         else {
            query.setQueryTheType(getPersistedEventTypeObject(PROCESS_CONTEXT_CHANGED,false,ti));
         }
         query.setQueryUTCTime(utcTime);
         //????         query.setQueryTheType(getPersistedEventTypeObject(dea.getType(),false,ti));

         // Throw an exception if more than one object is found
         query.requireUniqueInstance();

         DO = query.getNextDO();
         ((SharkDODSTransaction)ti)._read(DO);

         if (DO==null) {
            DO=DataEventAuditDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         }
         return DO;
      }
      catch (Throwable t) {
         throw new EventAuditException(t);
      }
   }

   protected DataEventAuditDO[] getPersistedDataEventObjects (String pId,String aId,
                                                            SharkTransaction ti) throws EventAuditException {
      DataEventAuditDO[] DOs=null;

      DataEventAuditQuery query = null;
      try {
         query=new DataEventAuditQuery(((SharkDODSTransaction)ti).getDODSTransaction());
         //set query
         query.setQueryProcessId(pId);
         if (aId!=null) {
            query.setQueryActivityId(aId);
            query.openParen();
            query.setQueryTheType(getPersistedEventTypeObject(ACTIVITY_CONTEXT_CHANGED,false,ti));
            query.or();
            query.setQueryTheType(getPersistedEventTypeObject(ACTIVITY_RESULT_CHANGED,false,ti));
            query.closeParen();
         }
         else {
            query.setQueryTheType(getPersistedEventTypeObject(PROCESS_CONTEXT_CHANGED,false,ti));
         }
         DOs = query.getDOArray();
         List list = new ArrayList(Arrays.asList(DOs));
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.eventaudit.data.DataEventAuditDO");
              it.hasNext();) {
            DataEventAuditDO DO = (DataEventAuditDO)it.next();
            if (pId.equals(DO.getProcessId())){
               if (aId != null){
                  if (aId.equals(DO.getActivityId()) && ( ACTIVITY_CONTEXT_CHANGED.equals(DO.getTheType().getName()) || ACTIVITY_RESULT_CHANGED.equals(DO.getTheType().getName()))){
                     int index = list.indexOf(DO);
                     if (-1 == index)
                        list.add(DO);
                     else
                        list.set(index, DO);
                  }
               }
               else {
                  if (PROCESS_CONTEXT_CHANGED.equals(DO.getTheType().getName())){
                     int index = list.indexOf(DO);
                     if (-1 == index)
                        list.add(DO);
                     else
                        list.set(index, DO);
                  }
               }
               //if (!list.contains(DO))
               //   list.add(DO);
            }
         }
         DOs = new DataEventAuditDO[list.size()];
         return (DataEventAuditDO[])list.toArray(DOs);
         //return DOs;
      }
      catch (Throwable t) {
         throw new EventAuditException(t);
      }
   }

   protected StateEventAuditDO getPersistedObject (StateEventAuditPersistenceInterface sea,
                                                 SharkTransaction ti) throws EventAuditException {
      StateEventAuditDO DO=null;

      StateEventAuditQuery query = null;
      try {
         String  procId = sea.getProcessId();
         String  actId = sea.getActivityId();
         String  utcTime = sea.getUTCTime();
         String  eType = sea.getType();
         String  nState = sea.getNewState();
         String  oState = sea.getOldState();

         for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.eventaudit.data.StateEventAuditDO");
              it.hasNext();) {
            DO = (StateEventAuditDO)it.next();

            if (procId.equals(DO.getProcessId()) && utcTime.equals(DO.getUTCTime())
                && eType.equals(DO.getTheType().getName()))  {
               if (actId != null){
                  if (actId.equals(DO.getActivityId()) && nState.equals(DO.getNewActivityState().getName())
                      && ((oState == null) || ((DO.getOldActivityState()!= null) &&  oState.equals(DO.getOldActivityState().getName()))))
                     return DO;
               }
               else {
                  if (nState.equals(DO.getNewProcessState().getName())
                      && ((oState == null) || ((DO.getOldProcessState()!= null) && oState.equals(DO.getOldProcessState().getName()))))
                     return DO;
               }
            }
         }
         query=new StateEventAuditQuery(((SharkDODSTransaction)ti).getDODSTransaction());
         //set query
         query.setQueryProcessId(procId);
         if (actId!=null) {
            query.setQueryActivityId(actId);
            query.setQueryNewActivityState(getPersistedActivityStateObject(sea.getNewState(),false,ti));
            query.setQueryOldActivityState(getPersistedActivityStateObject(sea.getOldState(),false,ti));
         } else {
            query.setQueryNewProcessState(getPersistedProcessStateObject(sea.getNewState(),false,ti));
            query.setQueryOldProcessState(getPersistedProcessStateObject(sea.getOldState(),false,ti));
         }
         query.setQueryTheType(getPersistedEventTypeObject(eType,false,ti));
         query.setQueryUTCTime(utcTime);

         // Throw an exception if more than one object is found
         query.requireUniqueInstance();

         DO = query.getNextDO();
         ((SharkDODSTransaction)ti)._read(DO);

         if (DO==null) {
            DO=StateEventAuditDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         }
         return DO;
      }
      catch (Throwable t) {
         throw new EventAuditException(t);
      }
   }

   protected StateEventAuditDO[] getPersistedStateEventObjects (String pId,String aId,
                                                              SharkTransaction ti) throws EventAuditException {
      StateEventAuditDO[] DOs=null;

      StateEventAuditQuery query = null;
      try {
         query=new StateEventAuditQuery(((SharkDODSTransaction)ti).getDODSTransaction());

         //set query
         query.setQueryProcessId(pId);
         if (aId!=null) {
            query.setQueryActivityId(aId);
            query.setQueryTheType(getPersistedEventTypeObject(ACTIVITY_STATE_CHANGED,false,ti));
         }
         else {
            query.setQueryTheType(getPersistedEventTypeObject(PROCESS_STATE_CHANGED,false,ti));
         }
         DOs = query.getDOArray();
         List list = new ArrayList(Arrays.asList(DOs));
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.eventaudit.data.StateEventAuditDO");
              it.hasNext();) {
            StateEventAuditDO DO = (StateEventAuditDO)it.next();
            if (pId.equals(DO.getProcessId())){
               if (aId != null){
                  if (aId.equals(DO.getActivityId()) && ACTIVITY_CONTEXT_CHANGED.equals(DO.getTheType().getName())){
                     int index = list.indexOf(DO);
                     if (-1 == index)
                        list.add(DO);
                     else
                        list.set(index, DO);
                  }
               }
               else {
                  if (PROCESS_CONTEXT_CHANGED.equals(DO.getTheType().getName())){
                     int index = list.indexOf(DO);
                     if (-1 == index)
                        list.add(DO);
                     else
                        list.set(index, DO);
                  }
               }
               //if (!list.contains(DO))
               //   list.add(DO);
            }
         }
         DOs = new StateEventAuditDO[list.size()];
         return (StateEventAuditDO[])list.toArray(DOs);
         //return DOs;
      }
      catch (Throwable t) {
         throw new EventAuditException(t);
      }
   }

   protected CreateProcessEventAuditDO getPersistedObject (CreateProcessEventAuditPersistenceInterface cpea,
                                                         SharkTransaction ti) throws EventAuditException {
      CreateProcessEventAuditDO DO=null;

      CreateProcessEventAuditQuery query = null;
      try {
         String  procId = cpea.getProcessId();

         for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.eventaudit.data.CreateProcessEventAuditDO");
              it.hasNext();) {
            DO = (CreateProcessEventAuditDO)it.next();

            if (procId.equals(DO.getProcessId())){
               return DO;
            }
         }
         query=new CreateProcessEventAuditQuery(((SharkDODSTransaction)ti).getDODSTransaction());
         //set query
         query.setQueryProcessId(procId);

         // Throw an exception if more than one object is found
         query.requireUniqueInstance();

         DO = query.getNextDO();
         ((SharkDODSTransaction)ti)._read(DO);

         if (DO==null) {
            DO=CreateProcessEventAuditDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         }
         return DO;
      }
      catch (Throwable t) {
         throw new EventAuditException(t);
      }
   }

   protected OldEventAuditDataDO getPersistedOldEventAuditDataObject (
      DataEventAuditDO deaDO,String vdId,SharkTransaction ti) throws EventAuditException {
      OldEventAuditDataDO DO=null;

      OldEventAuditDataQuery query = null;
      try {
         BigDecimal dOId = deaDO.get_OId().toBigDecimal();

         for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.eventaudit.data.OldEventAuditDataDO");
              it.hasNext();) {
            DO = (OldEventAuditDataDO)it.next();

            if (dOId.equals(DO.oid_getDataEventAudit()) && vdId.equals(DO.getVariableDefinitionId())){
               return DO;
            }
         }
         query=new OldEventAuditDataQuery(((SharkDODSTransaction)ti).getDODSTransaction());
         //set query
         query.setQueryDataEventAudit(deaDO);
         query.setQueryVariableDefinitionId(vdId);

         // Throw an exception if more than one object is found
         query.requireUniqueInstance();

         DO = query.getNextDO();
         ((SharkDODSTransaction)ti)._read(DO);

         if (DO==null) {
            DO=OldEventAuditDataDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         }
         return DO;

      }
      catch (Throwable t) {
         throw new EventAuditException(t);
      }
   }

   protected NewEventAuditDataDO getPersistedNewEventAuditDataObject (
      DataEventAuditDO deaDO,String vdId,SharkTransaction ti) throws EventAuditException {
      NewEventAuditDataDO DO=null;

      NewEventAuditDataQuery query = null;
      try {
         BigDecimal dOId = deaDO.get_OId().toBigDecimal();

         for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.eventaudit.data.NewEventAuditDataDO");
              it.hasNext();) {
            DO = (NewEventAuditDataDO)it.next();

            if (dOId.equals(DO.oid_getDataEventAudit()) && vdId.equals(DO.getVariableDefinitionId())){
               return DO;
            }
         }
         query=new NewEventAuditDataQuery(((SharkDODSTransaction)ti).getDODSTransaction());
         //set query
         query.setQueryDataEventAudit(deaDO);
         query.setQueryVariableDefinitionId(vdId);

         // Throw an exception if more than one object is found
         query.requireUniqueInstance();

         DO = query.getNextDO();
         ((SharkDODSTransaction)ti)._read(DO);

         if (DO==null) {
            DO=NewEventAuditDataDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         }
         return DO;
      }
      catch (Throwable t) {
         throw new EventAuditException(t);
      }
   }

   protected CreateProcessEventAuditDO[] getPersistedCreateProcessEventObjects (String pId,
                                                                              SharkTransaction ti) throws EventAuditException {

      CreateProcessEventAuditDO[] DOs=null;
      CreateProcessEventAuditQuery query = null;

      try {
         query=new CreateProcessEventAuditQuery(((SharkDODSTransaction)ti).getDODSTransaction());
         //set query
         query.setQueryProcessId(pId);

         DOs = query.getDOArray();
         List list = new ArrayList(Arrays.asList(DOs));
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.eventaudit.data.CreateProcessEventAuditDO");
              it.hasNext();) {
            CreateProcessEventAuditDO DO = (CreateProcessEventAuditDO)it.next();
            if (pId.equals(DO.getProcessId())){
               int index = list.indexOf(DO);
               if (-1 == index)
                  list.add(DO);
               else
                  list.set(index, DO);
            }
            //if (!list.contains(DO))
            //   list.add(DO);
         }
         DOs = new CreateProcessEventAuditDO[list.size()];
         return (CreateProcessEventAuditDO[])list.toArray(DOs);
         //return DOs;
      }
      catch (Throwable t) {
         throw new EventAuditException(t);
      }
   }

   protected ProcessStateEventAuditDO getPersistedProcessStateObject (
      String value,boolean searchByKeyValue,SharkTransaction ti) throws EventAuditException {
      ProcessStateEventAuditDO DO=null;

      ProcessStateEventAuditQuery query = null;
      try {
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.eventaudit.data.ProcessStateEventAuditDO");
              it.hasNext();) {
            DO = (ProcessStateEventAuditDO)it.next();
            if (searchByKeyValue){
               if (value.equals(DO.getKeyValue())){
                  return DO;
               }
            }
            else{
               if (value.equals(DO.getName())){
                  return DO;
               }
            }

         }
         query=new ProcessStateEventAuditQuery(((SharkDODSTransaction)ti).getDODSTransaction());
         //set query
         if (searchByKeyValue) {
            query.setQueryKeyValue(value);
         }
         else {
            query.setQueryName(value);
         }

         // Throw an exception if more than one object is found
         query.requireUniqueInstance();

         DO = query.getNextDO();
         ((SharkDODSTransaction)ti)._read(DO);

         if (DO==null) {
            DO=ProcessStateEventAuditDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         }
         return DO;
      }
      catch (Throwable t) {
         throw new EventAuditException(t);
      }
   }

   protected ActivityStateEventAuditDO getPersistedActivityStateObject (
      String value,boolean searchByKeyValue,DBTransaction ti) throws EventAuditException {
      ActivityStateEventAuditDO DO=null;

      ActivityStateEventAuditQuery query = null;
      try {
         query=new ActivityStateEventAuditQuery(ti);
         //set query
         if (searchByKeyValue) {
            query.setQueryKeyValue(value);
         }
         else {
            query.setQueryName(value);
         }

         // Throw an exception if more than one object is found
         query.requireUniqueInstance();

         DO = query.getNextDO();
         if (DO==null) {
            DO=ActivityStateEventAuditDO.createVirgin(ti);
         }
         return DO;
      }
      catch (Throwable t) {
         throw new EventAuditException(t);
      }
   }

   protected ActivityStateEventAuditDO getPersistedActivityStateObject (
      String value,boolean searchByKeyValue,SharkTransaction ti) throws EventAuditException {
      ActivityStateEventAuditDO DO=null;

      ActivityStateEventAuditQuery query = null;
      try {
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.eventaudit.data.ActivityStateEventAuditDO");
              it.hasNext();) {
            DO = (ActivityStateEventAuditDO)it.next();
            if (searchByKeyValue){
               if (value.equals(DO.getKeyValue())){
                  return DO;
               }
            }
            else{
               if (value.equals(DO.getName())){
                  return DO;
               }
            }
         }
         query=new ActivityStateEventAuditQuery(((SharkDODSTransaction)ti).getDODSTransaction());
         //set query
         if (searchByKeyValue) {
            query.setQueryKeyValue(value);
         }
         else {
            query.setQueryName(value);
         }

         // Throw an exception if more than one object is found
         query.requireUniqueInstance();

         DO = query.getNextDO();
         ((SharkDODSTransaction)ti)._read(DO);

         if (DO==null) {
            DO=ActivityStateEventAuditDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         }
         return DO;
      }
      catch (Throwable t) {
         throw new EventAuditException(t);
      }
   }

   protected ProcessStateEventAuditDO getPersistedProcessStateObject (
      String value,boolean searchByKeyValue,DBTransaction ti) throws EventAuditException {
      ProcessStateEventAuditDO DO=null;

      ProcessStateEventAuditQuery query = null;
      try {
         query=new ProcessStateEventAuditQuery(ti);
         //set query
         if (searchByKeyValue) {
            query.setQueryKeyValue(value);
         }
         else {
            query.setQueryName(value);
         }

         // Throw an exception if more than one object is found
         query.requireUniqueInstance();

         DO = query.getNextDO();
         if (DO==null) {
            DO=ProcessStateEventAuditDO.createVirgin(ti);
         }
         return DO;
      }
      catch (Throwable t) {
         throw new EventAuditException(t);
      }
   }

   protected EventTypeDO getPersistedEventTypeObject (String value,
                                                    boolean searchByKeyValue,SharkTransaction ti) throws EventAuditException {
      EventTypeDO DO=null;

      EventTypeQuery query = null;
      try {
         for (Iterator it = ((SharkDODSTransaction)ti).iterator4type("class org.enhydra.shark.eventaudit.data.EventTypeDO");
              it.hasNext();) {
            DO = (EventTypeDO)it.next();
            if (searchByKeyValue){
               if (value.equals(DO.getKeyValue())){
                  return DO;
               }
            }
            else{
               if (value.equals(DO.getName())){
                  return DO;
               }
            }
         }
         query=new EventTypeQuery(((SharkDODSTransaction)ti).getDODSTransaction());
         //set query
         if (searchByKeyValue) {
            query.setQueryKeyValue(value);
         }
         else {
            query.setQueryName(value);
         }

         // Throw an exception if more than one object is found
         query.requireUniqueInstance();

         DO = query.getNextDO();
         ((SharkDODSTransaction)ti)._read(DO);

         if (DO==null) {
            DO=EventTypeDO.createVirgin(((SharkDODSTransaction)ti).getDODSTransaction());
         }
         return DO;
      }
      catch (Throwable t) {
         throw new EventAuditException(t);
      }
   }

   protected EventTypeDO getPersistedEventTypeObject (String value,
                                                    boolean searchByKeyValue,DBTransaction ti) throws EventAuditException {
      EventTypeDO DO=null;

      EventTypeQuery query = null;
      try {
         query=new EventTypeQuery(ti);
         //set query
         if (searchByKeyValue) {
            query.setQueryKeyValue(value);
         }
         else {
            query.setQueryName(value);
         }

         // Throw an exception if more than one object is found
         query.requireUniqueInstance();

         DO = query.getNextDO();

         if (DO==null) {
            DO=EventTypeDO.createVirgin(ti);
         }
         return DO;
      }
      catch (Throwable t) {
         throw new EventAuditException(t);
      }
   }

   public AssignmentEventAuditPersistenceInterface createAssignmentEventAudit () {
      return new DODSAssignmentEventAudit();
   }

   public CreateProcessEventAuditPersistenceInterface createCreateProcessEventAudit () {
      return new DODSCreateProcessEventAudit();
   }

   public DataEventAuditPersistenceInterface createDataEventAudit () {
      return new DODSDataEventAudit();
   }

   public StateEventAuditPersistenceInterface createStateEventAudit () {
      return new DODSStateEventAudit();
   }

   public BigDecimal getNextDecId(String idName) throws EventAuditException {
      try {
         return DODSUtilities.getNext(idName);
      } catch (Exception e) {
         throw new EventAuditException("Couldn't allocate id");
      }
   }

   public String getNextId(String idName) throws EventAuditException {
      return getNextDecId(idName).toString();
   }
}

