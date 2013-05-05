package org.enhydra.shark.corba;

import org.omg.WfBase.*;
import org.omg.WorkflowModel.*;
import org.enhydra.shark.corba.WorkflowService.*;
import org.omg.CORBA.*;

import java.util.*;
import org.enhydra.shark.utilities.SequencedHashMap;

import java.io.Serializable;

/**
 * The various utilities needed by engine.
 * 
 * @author Sasa Bojanic
 * @version 1.1
 */
public class SharkCORBAUtilities {

   static NameValueInfo makeCORBANameValueInfo(String attribute_name,
                                               String type_name) {
      NameValueInfo nvic = new NameValueInfo();
      nvic.attribute_name = attribute_name;
      nvic.type_name = type_name;
      return nvic;
   }

   static NameValueInfo[] makeCORBANameValueInfoArray(Map nvia) {
      int size = 0;
      if (nvia != null) size = nvia.size();
      NameValueInfo[] nvica = new NameValueInfo[size];
      int i = 0;
      if (nvia != null) {
         for (Iterator it = nvia.entrySet().iterator(); it.hasNext(); i++) {
            Map.Entry me = (Map.Entry) it.next();
            nvica[i] = makeCORBANameValueInfo(me.getKey().toString(),
                                              me.getValue().toString());
         }
      }
      return nvica;
   }

   static NameValue makeCORBANameValue(ORB orb,
                                       String the_name,
                                       java.lang.Object the_value) {
      NameValue nvc = new NameValue();
      nvc.the_name = the_name;
      nvc.the_value = createAnyFromValue(orb, the_value);
      return nvc;
   }

   static NameValue[] makeCORBANameValueArray(ORB orb, Map nva) {
      int size = 0;
      if (nva != null) size = nva.size();
      NameValue[] nvca = new NameValue[size];
      int i = 0;
      if (nva != null) {
         for (Iterator it = nva.entrySet().iterator(); it.hasNext(); i++) {
            Map.Entry me = (Map.Entry) it.next();
            nvca[i] = makeCORBANameValue(orb,
                                         me.getKey().toString(),
                                         me.getValue());
         }
      }
      return nvca;
   }

   static NameValue[] makeCORBANameValueArray(ORB orb, String[][] nva) {
      int size = 0;
      if (nva != null) size = nva.length;
      NameValue[] nvca = new NameValue[size];
      if (nva != null) {
         for (int i = 0; i < size; i++) {
            nvca[i] = makeCORBANameValue(orb, nva[i][0], nva[i][1]);
         }
      }
      return nvca;
   }

   static NameValue[] makeCORBANameValueArray(ORB orb, Properties props) {
      int size = 0;
      if (props != null) size = props.size();
      NameValue[] nvca = new NameValue[size];
      if (props != null) {
         Iterator it = props.entrySet().iterator();
         int i = 0;
         while (it.hasNext()) {
            Map.Entry me = (Map.Entry) it.next();
            nvca[i] = makeCORBANameValue(orb,
                                         (String) me.getKey(),
                                         (String) me.getValue());
            i++;
         }
      }
      return nvca;
   }

   static Map makeMap(NameValue[] nvca) {
      Map m = new SequencedHashMap();
      if (nvca != null) {
         for (int i = 0; i < nvca.length; i++) {
            m.put(nvca[i].the_name, extractValueFromAny(nvca[i].the_value));
         }
      }
      return m;
   }

   static DeadlineInfo[] makeDeadlineInfoArray (org.enhydra.shark.api.common.DeadlineInfo[] di) {
      DeadlineInfo[] cdi=new DeadlineInfo[di.length];
      for (int i=0; i<di.length; i++) {
         cdi[i]=new DeadlineInfo();
         cdi[i].procId=di[i].procId;
         cdi[i].actId=di[i].actId;
         cdi[i].isExecuted=di[i].isExecuted;
         cdi[i].timeLimit=di[i].timeLimit;
         cdi[i].exceptionName=di[i].exceptionName;
         cdi[i].isSynchronous=di[i].isSynchronous;            
      }
      return cdi;
   }
   
   static java.lang.Object extractValueFromAny(Any any) {
      java.lang.Object value = null;
      try {
         int kind = any.type().kind().value();
         if (kind == TCKind._tk_boolean) {
            value = new Boolean(any.extract_boolean());
         } else if (kind == TCKind._tk_wstring) {
            value = new String(any.extract_wstring());
         } else if (kind == TCKind._tk_long) {
            value = new Integer(any.extract_long());
         } else if (kind == TCKind._tk_longlong) {
            value = new Long(any.extract_longlong());
         } else if (kind == TCKind._tk_double) {
            value = new Double(any.extract_double());
         } else if (kind == TCKind._tk_value) {
            value = any.extract_Value();
         }
      } catch (BAD_OPERATION bo) {
         System.out.println("Extracting value failed - bo exception");
         //bo.printStackTrace();
         return null;
      }
      return value;
   }

   static Any createAnyFromValue(ORB orb, java.lang.Object value) {
      Any any = orb.create_any();

      try {
         if (value instanceof Boolean) {
            any.insert_boolean(((Boolean) value).booleanValue());
         } else if (value instanceof String) {
            any.insert_wstring((String) value);
         } else if (value instanceof Long) {
            any.insert_longlong(((Long) value).longValue());
         } else if (value instanceof Double) {
            any.insert_double(((Double) value).doubleValue());
         } else if (value instanceof Serializable) {
            any.insert_Value((Serializable) value);
         }
      } catch (BAD_OPERATION bo) {
         System.out.println("Creating any from val failed - bo exception");
         //bo.printStackTrace();
      }
      return any;
   }

   static WfProcessMgr[] makeCORBAProcessMgrs(Collective toJoin,
                                              org.enhydra.shark.api.client.wfmodel.WfProcessMgr[] objs) {
      int size = 0;
      if (objs != null) size = objs.length;
      WfProcessMgrCORBA[] cobjs = new WfProcessMgrCORBA[size];
      for (int i = 0; i < cobjs.length; i++) {
         try {
            cobjs[i] = new WfProcessMgrCORBA(toJoin, objs[i]);
         } catch (Exception ex) {}
      }
      return cobjs;
   }

   static WfProcess[] makeCORBAProcesses(Collective toJoin,
                                         org.enhydra.shark.api.client.wfmodel.WfProcess[] objs) {
      int size = 0;
      if (objs != null) size = objs.length;
      WfProcessCORBA[] cobjs = new WfProcessCORBA[size];
      for (int i = 0; i < cobjs.length; i++) {
         try {
            cobjs[i] = new WfProcessCORBA(toJoin, objs[i]);
         } catch (Exception ex) {}
      }
      return cobjs;
   }

   static WfActivity[] makeCORBAActivities(Collective toJoin,
                                           org.enhydra.shark.api.client.wfmodel.WfActivity[] objs) {
      int size = 0;
      if (objs != null) size = objs.length;
      WfActivityCORBA[] cobjs = new WfActivityCORBA[size];
      for (int i = 0; i < cobjs.length; i++) {
         try {
            cobjs[i] = new WfActivityCORBA(toJoin, objs[i]);
         } catch (Exception ex) {}
      }
      return cobjs;
   }

   static WfAssignment[] makeCORBAAssignments(Collective toJoin,
                                              org.enhydra.shark.api.client.wfmodel.WfAssignment[] objs) {
      int size = 0;
      if (objs != null) size = objs.length;
      WfAssignmentCORBA[] cobjs = new WfAssignmentCORBA[size];
      for (int i = 0; i < cobjs.length; i++) {
         try {
            cobjs[i] = new WfAssignmentCORBA(toJoin, objs[i]);
         } catch (Exception ex) {}
      }
      return cobjs;
   }

   static WfResource[] makeCORBAResources(Collective toJoin,
                                          org.enhydra.shark.api.client.wfmodel.WfResource[] objs) {
      int size = 0;
      if (objs != null) size = objs.length;
      WfResourceCORBA[] cobjs = new WfResourceCORBA[size];
      for (int i = 0; i < cobjs.length; i++) {
         try {
            cobjs[i] = new WfResourceCORBA(toJoin, objs[i]);
         } catch (Exception ex) {}
      }
      return cobjs;
   }

   static WfEventAudit makeCORBAEventAudit(Collective toJoin,
                                           org.enhydra.shark.api.client.wfmodel.WfEventAudit obj) {
      try {
         if (obj instanceof org.enhydra.shark.api.client.wfmodel.WfCreateProcessEventAudit) {
            return new WfCreateProcessEventAuditCORBA(toJoin,
                                                      (org.enhydra.shark.api.client.wfmodel.WfCreateProcessEventAudit) obj);
         } else if (obj instanceof org.enhydra.shark.api.client.wfmodel.WfAssignmentEventAudit) {
            return new WfAssignmentEventAuditCORBA(toJoin,
                                                   (org.enhydra.shark.api.client.wfmodel.WfAssignmentEventAudit) obj);
         } else if (obj instanceof org.enhydra.shark.api.client.wfmodel.WfDataEventAudit) {
            return new WfDataEventAuditCORBA(toJoin,
                                             (org.enhydra.shark.api.client.wfmodel.WfDataEventAudit) obj);
         } else if (obj instanceof org.enhydra.shark.api.client.wfmodel.WfStateEventAudit) {
            return new WfStateEventAuditCORBA(toJoin,
                                              (org.enhydra.shark.api.client.wfmodel.WfStateEventAudit) obj);
         } else {
            return null;
         }
      } catch (Exception ex) {
         return null;
      }
   }

   static WfEventAudit[] makeCORBAEventAudits(Collective toJoin,
                                              org.enhydra.shark.api.client.wfmodel.WfEventAudit[] objs) {
      int size = 0;
      if (objs != null) size = objs.length;
      WfEventAudit[] cobjs = new WfEventAudit[size];
      for (int i = 0; i < cobjs.length; i++) {
         cobjs[i] = makeCORBAEventAudit(toJoin, objs[i]);
      }
      return cobjs;
   }

   static WfStateEventAudit[] makeCORBAStateEventAudits(Collective toJoin,
                                                        org.enhydra.shark.api.client.wfmodel.WfEventAudit[] objs) throws Exception {
      int size = 0;
      if (objs != null) size = objs.length;
      WfStateEventAudit[] cobjs = new WfStateEventAuditCORBA[size];
      for (int i = 0; i < cobjs.length; i++) {
         cobjs[i] = (WfStateEventAudit) makeCORBAEventAudit(toJoin, objs[i]);
      }
      return cobjs;
   }

   static WfDataEventAudit[] makeCORBADataEventAudits(Collective toJoin,
                                                      org.enhydra.shark.api.client.wfmodel.WfEventAudit[] objs) throws Exception {
      int size = 0;
      if (objs != null) size = objs.length;
      WfDataEventAudit[] cobjs = new WfDataEventAuditCORBA[size];
      for (int i = 0; i < cobjs.length; i++) {
         cobjs[i] = (WfDataEventAudit) makeCORBAEventAudit(toJoin, objs[i]);
      }
      return cobjs;
   }

   static WfAssignmentEventAudit[] makeCORBAAssignmentEventAudits(Collective toJoin,
                                                                  org.enhydra.shark.api.client.wfmodel.WfEventAudit[] objs) throws Exception {
      int size = 0;
      if (objs != null) size = objs.length;
      WfAssignmentEventAudit[] cobjs = new WfAssignmentEventAuditCORBA[size];
      for (int i = 0; i < cobjs.length; i++) {
         cobjs[i] = (WfAssignmentEventAudit) makeCORBAEventAudit(toJoin,
                                                                 objs[i]);
      }
      return cobjs;
   }

   static ParticipantMap[] makeCORBAParticipantMaps(Collective toJoin,
                                                    org.enhydra.shark.api.client.wfservice.ParticipantMap[] objs) {
      int size = 0;
      if (objs != null) size = objs.length;
      ParticipantMap[] cobjs = new ParticipantMap[size];
      for (int i = 0; i < cobjs.length; i++) {
         cobjs[i] = new ParticipantMapCORBA(toJoin, objs[i]);
      }
      return cobjs;
   }

   static ApplicationMap[] makeCORBAApplicationMaps(Collective toJoin,
                                                    org.enhydra.shark.api.client.wfservice.ApplicationMap[] objs) {
      int size = 0;
      if (objs != null) size = objs.length;
      ApplicationMap[] cobjs = new ApplicationMap[size];
      for (int i = 0; i < cobjs.length; i++) {
         cobjs[i] = new ApplicationMapCORBA(toJoin, objs[i]);
      }
      return cobjs;
   }

   static org.enhydra.shark.api.client.wfservice.ApplicationMap fillApplicationMap(org.enhydra.shark.api.client.wfservice.ApplicationMap nm,
                                                                                   ApplicationMap am) {
      nm.setToolAgentClassName(am.getToolAgentClassName());
      nm.setUsername(am.getUsername());
      nm.setPassword(am.getPassword());
      nm.setApplicationName(am.getApplicationName());
      if (am.getApplicationMode() != -Integer.MAX_VALUE) {
         nm.setApplicationMode(new Integer(am.getApplicationMode()));
      }
      nm.setPackageId(am.getPackageId());
      nm.setProcessDefinitionId(am.getProcessDefinitionId());
      nm.setApplicationDefinitionId(am.getApplicationDefinitionId());
      return nm;
   }

   static org.enhydra.shark.api.client.wfservice.ParticipantMap fillParticipantMap(org.enhydra.shark.api.client.wfservice.ParticipantMap nm,
                                                                                   ParticipantMap pm) {
      nm.setParticipantId(pm.getParticipantId());
      nm.setPackageId(pm.getPackageId());
      nm.setProcessDefinitionId(pm.getProcessDefinitionId());
      nm.setUsername(pm.getUsername());
      nm.setIsGroupUser(pm.getIsGroupUser());
      return nm;
   }

}