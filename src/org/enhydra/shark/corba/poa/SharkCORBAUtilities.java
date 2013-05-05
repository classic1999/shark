package org.enhydra.shark.corba.poa;

import org.omg.WfBase.*;
import org.omg.WorkflowModel.*;
import org.enhydra.shark.corba.WorkflowService.*;
import org.enhydra.shark.corba.poa.ApplicationMapCORBA;
import org.enhydra.shark.corba.poa.Collective;
import org.enhydra.shark.corba.poa.ParticipantMapCORBA;
import org.omg.CORBA.*;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import org.omg.PortableServer.POAPackage.ServantNotActive;

import java.util.*;

import org.enhydra.shark.utilities.SequencedHashMap;

import java.io.Serializable;

/**
 * The various utilities needed by engine.
 *
 * @author David Forslund
 * @version 1.1
 */
public class SharkCORBAUtilities {

    static POA rootPOA;
    static ORB orb;

    static void setPOA(POA poa) {
        rootPOA = poa;
    }
    static void setORB(ORB the_orb) {
        orb = the_orb;
    }
    static POA getPOA() {
        return rootPOA;
    }
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

    static DeadlineInfo[] makeDeadlineInfoArray(org.enhydra.shark.api.common.DeadlineInfo[] di) {
        DeadlineInfo[] cdi = new DeadlineInfo[di.length];
        for (int i = 0; i < di.length; i++) {
            cdi[i] = new DeadlineInfo();
            cdi[i].procId = di[i].procId;
            cdi[i].actId = di[i].actId;
            cdi[i].isExecuted = di[i].isExecuted;
            cdi[i].timeLimit = di[i].timeLimit;
            cdi[i].exceptionName = di[i].exceptionName;
            cdi[i].isSynchronous = di[i].isSynchronous;
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
            System.out.println("Extracting value failed - bo exception "+bo);
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

    static WfProcessMgr[] makeCORBAProcessMgrs(org.omg.CORBA.ORB orb, Collective toJoin,
                                               org.enhydra.shark.api.client.wfmodel.WfProcessMgr[] objs) {
        int size = 0;
        if (objs != null) size = objs.length;
        WfProcessMgr[] cobjs = new WfProcessMgr[size];
        for (int i = 0; i < cobjs.length; i++) {
            try {
                cobjs[i] = makeWfProcessMgr(new WfProcessMgrCORBA(orb, toJoin, objs[i]));
                toJoin.__recruit(cobjs[i]);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return cobjs;
    }

    static WfProcess[] makeCORBAProcesses(Collective toJoin,
                                          org.enhydra.shark.api.client.wfmodel.WfProcess[] objs) {
        int size = 0;
        if (objs != null) size = objs.length;
        WfProcess[] cobjs = new WfProcess[size];
        for (int i = 0; i < cobjs.length; i++) {
            cobjs[i] = makeWfProcess(new WfProcessCORBA(orb, toJoin, objs[i]));
            toJoin.__recruit(cobjs[i]);
        }
        return cobjs;
    }

    static WfActivity[] makeCORBAActivities(Collective toJoin,
                                            org.enhydra.shark.api.client.wfmodel.WfActivity[] objs) {
        int size = 0;
        if (objs != null) size = objs.length;
        WfActivity[] cobjs = new WfActivity[size];
        for (int i = 0; i < cobjs.length; i++) {
            cobjs[i] = makeWfActivity(new WfActivityCORBA(orb, toJoin, objs[i]));
            toJoin.__recruit(cobjs[i]);
        }
        return cobjs;
    }

    static WfAssignment[] makeCORBAAssignments( Collective toJoin,
                                               org.enhydra.shark.api.client.wfmodel.WfAssignment[] objs) {
        int size = 0;
        if (objs != null) size = objs.length;
        WfAssignment[] cobjs = new WfAssignment[size];
        for (int i = 0; i < cobjs.length; i++) {
            try {
                cobjs[i] = makeWfAssignment(new WfAssignmentCORBA(orb, toJoin, objs[i]));
                toJoin.__recruit(cobjs[i]);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return cobjs;
    }

    static WfResource[] makeCORBAResources(Collective toJoin,
                                           org.enhydra.shark.api.client.wfmodel.WfResource[] objs) {
        int size = 0;
        if (objs != null) size = objs.length;
        WfResource[] cobjs = new WfResource[size];
        for (int i = 0; i < cobjs.length; i++) {
            try {
                cobjs[i] = makeWfResource( new WfResourceCORBA(orb, toJoin, objs[i]));
                toJoin.__recruit(cobjs[i]);
            } catch (Exception ex) {
               ex.printStackTrace();
            }
        }
        return cobjs;
    }

    static WfEventAudit makeCORBAEventAudit(Collective toJoin,
                                            org.enhydra.shark.api.client.wfmodel.WfEventAudit obj) {
        try {
            if (obj instanceof org.enhydra.shark.api.client.wfmodel.WfCreateProcessEventAudit) {
                WfCreateProcessEventAuditCORBA impl = new WfCreateProcessEventAuditCORBA(orb, toJoin,
                        (org.enhydra.shark.api.client.wfmodel.WfCreateProcessEventAudit) obj);
                WfCreateProcessEventAudit ret = null;
                try {
                    byte[] o = rootPOA.activate_object(impl);
                    ret = WfCreateProcessEventAuditHelper.narrow(rootPOA.id_to_reference(o));
                    toJoin.__recruit(ret);
                } catch (ServantAlreadyActive servantAlreadyActive) {
                    servantAlreadyActive.printStackTrace();
                } catch (WrongPolicy wrongPolicy) {
                    wrongPolicy.printStackTrace();
                } catch (Exception ex) {
                    System.err.println("WfCreateProcessEventAudit: "+ex);
                }
                return ret;
            } else if (obj instanceof org.enhydra.shark.api.client.wfmodel.WfAssignmentEventAudit) {
                WfAssignmentEventAuditCORBA impl = new WfAssignmentEventAuditCORBA(orb, toJoin,
                        (org.enhydra.shark.api.client.wfmodel.WfAssignmentEventAudit) obj);
                WfAssignmentEventAudit ret = null;
                try {
                  //  POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
                    byte[] o =  rootPOA.activate_object(impl);
                    ret = WfAssignmentEventAuditHelper.narrow(rootPOA.id_to_reference(o));
                   toJoin.__recruit(ret);
                } catch (ServantAlreadyActive servantAlreadyActive) {
                    servantAlreadyActive.printStackTrace();
                } catch (WrongPolicy wrongPolicy) {
                    wrongPolicy.printStackTrace();
         //       } catch (ServantNotActive servantNotActive) {
         //           servantNotActive.printStackTrace();

                } catch (Exception ex) {
                    System.err.println("WfAssignmentEventAudit creation: "+ex);
                }
                return ret;
            } else if (obj instanceof org.enhydra.shark.api.client.wfmodel.WfDataEventAudit) {
                WfDataEventAuditCORBA impl = new WfDataEventAuditCORBA(orb, toJoin,
                        (org.enhydra.shark.api.client.wfmodel.WfDataEventAudit) obj);
                WfDataEventAudit ret = null;
                try {
                  // POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
                 //   rootPOA.the_POAManager().activate();

                    byte[] o = rootPOA.activate_object(impl);
                    ret = WfDataEventAuditHelper.narrow(rootPOA.id_to_reference(o));
                   toJoin.__recruit(ret);
                } catch (ServantAlreadyActive servantAlreadyActive) {
                    servantAlreadyActive.printStackTrace();
                } catch (WrongPolicy wrongPolicy) {
                    wrongPolicy.printStackTrace();
          //      } catch (ServantNotActive servantNotActive) {
          //          servantNotActive.printStackTrace();

                } catch (Exception ex) {
                    System.err.println("WfDataEventAudit creation: "+ex);
                }
                return ret;
            } else if (obj instanceof org.enhydra.shark.api.client.wfmodel.WfStateEventAudit) {
                WfStateEventAuditCORBA impl = new WfStateEventAuditCORBA(toJoin,
                        (org.enhydra.shark.api.client.wfmodel.WfStateEventAudit) obj);
                WfStateEventAudit ret = null;
                try {
                  //  POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
                 //   rootPOA.the_POAManager().activate();

                    byte[] o = rootPOA.activate_object(impl);
                    ret = WfStateEventAuditHelper.narrow(rootPOA.id_to_reference(o));
                    toJoin.__recruit(ret);
                } catch (ServantAlreadyActive servantAlreadyActive) {
                    servantAlreadyActive.printStackTrace();
                } catch (WrongPolicy wrongPolicy) {
                    wrongPolicy.printStackTrace();
  //              } catch (ServantNotActive servantNotActive) {
  //                  servantNotActive.printStackTrace();

                } catch (Exception ex) {
                    System.err.println("WfStateEventAudit creation: "+ex);
                }
                return ret;
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
            toJoin.__recruit(cobjs[i]);
        }
        return cobjs;
    }

    static WfStateEventAudit[] makeCORBAStateEventAudits(Collective toJoin,
                                                         org.enhydra.shark.api.client.wfmodel.WfEventAudit[] objs) throws Exception {
        int size = 0;
        if (objs != null) size = objs.length;
        WfStateEventAudit[] cobjs = new WfStateEventAudit[size];
        for (int i = 0; i < cobjs.length; i++) {
            cobjs[i] = (WfStateEventAudit) makeCORBAEventAudit(toJoin, objs[i]);
            toJoin.__recruit(cobjs[i]);
        }
        return cobjs;
    }

    static WfDataEventAudit[] makeCORBADataEventAudits(Collective toJoin,
                                                       org.enhydra.shark.api.client.wfmodel.WfEventAudit[] objs) throws Exception {
        int size = 0;
        if (objs != null) size = objs.length;
        WfDataEventAudit[] cobjs = new WfDataEventAudit[size];
        for (int i = 0; i < cobjs.length; i++) {
            cobjs[i] = (WfDataEventAudit) makeCORBAEventAudit(toJoin, objs[i]);
            toJoin.__recruit(cobjs[i]);
        }
        return cobjs;
    }

    static WfAssignmentEventAudit[] makeCORBAAssignmentEventAudits(Collective toJoin,
                                                                   org.enhydra.shark.api.client.wfmodel.WfEventAudit[] objs) throws Exception {
        int size = 0;
        if (objs != null) size = objs.length;
        WfAssignmentEventAudit[] cobjs = new WfAssignmentEventAudit[size];
        for (int i = 0; i < cobjs.length; i++) {
            cobjs[i] = (WfAssignmentEventAudit) makeCORBAEventAudit(toJoin,
                    objs[i]);
            toJoin.__recruit(cobjs[i]);
        }
        return cobjs;
    }

    static ParticipantMap[] makeCORBAParticipantMaps(Collective toJoin,
                                                     org.enhydra.shark.api.client.wfservice.ParticipantMap[] objs) {
        int size = 0;
        if (objs != null) size = objs.length;
        ParticipantMap[] cobjs = new ParticipantMap[size];
        for (int i = 0; i < cobjs.length; i++) {
            cobjs[i] = makeParticipantMap(new ParticipantMapCORBA(objs[i]));
            toJoin.__recruit(cobjs[i]);
        }
        return cobjs;
    }

    static ApplicationMap[] makeCORBAApplicationMaps(ORB orb, Collective toJoin,
                                                     org.enhydra.shark.api.client.wfservice.ApplicationMap[] objs) {
        int size = 0;
        if (objs != null) size = objs.length;
        ApplicationMap[] cobjs = new ApplicationMap[size];
        for (int i = 0; i < cobjs.length; i++) {
            cobjs[i] = makeApplicationMap(new ApplicationMapCORBA(toJoin, objs[i]));
            toJoin.__recruit(cobjs[i]);
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

    static WfActivity makeWfActivity(WfActivityCORBA impl) {
        WfActivity ret = null;
        try {
         //   POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
         //               rootPOA.the_POAManager().activate();
            byte[] o = rootPOA.activate_object(impl);
            ret = WfActivityHelper.narrow(rootPOA.id_to_reference(o));

        } catch (ServantAlreadyActive servantAlreadyActive) {
            servantAlreadyActive.printStackTrace();
        } catch (WrongPolicy wrongPolicy) {
            wrongPolicy.printStackTrace();
      //  } catch (ServantNotActive servantNotActive) {
       //     servantNotActive.printStackTrace();


        } catch (Exception ex) {
                 ex.printStackTrace();
        }
        return ret;
    }

    static WfActivityIterator makeWfActivityIterator(WfActivityIteratorCORBA impl) {
        WfActivityIterator ret = null;
        try {
        //    POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
        //    rootPOA.the_POAManager().activate();

            byte[] o = rootPOA.activate_object(impl);
            ret = WfActivityIteratorHelper.narrow(rootPOA.id_to_reference(o));

        } catch (ServantAlreadyActive servantAlreadyActive) {
            servantAlreadyActive.printStackTrace();
        } catch (WrongPolicy wrongPolicy) {
            wrongPolicy.printStackTrace();
        //} catch (ServantNotActive servantNotActive) {
       //     servantNotActive.printStackTrace();


        } catch (Exception ex) {
                   ex.printStackTrace();
        }
        return ret;
    }

    static WfAssignmentIterator makeWfAssignmentIterator(WfAssignmentIteratorCORBA impl) {
        WfAssignmentIterator ret = null;
        try {
        //    POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
        //    rootPOA.the_POAManager().activate();
           byte[] o =  rootPOA.activate_object(impl);
            ret = WfAssignmentIteratorHelper.narrow(rootPOA.id_to_reference(o));

        } catch (ServantAlreadyActive servantAlreadyActive) {
            servantAlreadyActive.printStackTrace();
        } catch (WrongPolicy wrongPolicy) {
            wrongPolicy.printStackTrace();
      //  } catch (ServantNotActive servantNotActive) {
     //       servantNotActive.printStackTrace();
        } catch (Exception ex) {
               ex.printStackTrace();
        }
        return ret;
    }

    static ApplicationMap makeApplicationMap(ApplicationMapCORBA impl) {
        ApplicationMap ret = null;
        try {
       //     POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
       //     rootPOA.the_POAManager().activate();

            byte[] o = rootPOA.activate_object(impl);
            ret = ApplicationMapHelper.narrow(rootPOA.id_to_reference(o));

        } catch (ServantAlreadyActive servantAlreadyActive) {
            servantAlreadyActive.printStackTrace();
        } catch (WrongPolicy wrongPolicy) {
            wrongPolicy.printStackTrace();
   //     } catch (ServantNotActive servantNotActive) {
   //         servantNotActive.printStackTrace();
        } catch (Exception ex) {
                ex.printStackTrace();
        }
        return ret;
    }

    static WfEventAuditIterator makeWfEventAuditIterator(WfEventAuditIteratorCORBA impl) {

        WfEventAuditIterator ret = null;
        try {
         //   POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
         //   rootPOA.the_POAManager().activate();

           byte[] o = rootPOA.activate_object(impl);
            ret = WfEventAuditIteratorHelper.narrow(rootPOA.id_to_reference(o));

        } catch (ServantAlreadyActive servantAlreadyActive) {
            servantAlreadyActive.printStackTrace();
        } catch (WrongPolicy wrongPolicy) {
            wrongPolicy.printStackTrace();
   //     } catch (ServantNotActive servantNotActive) {
   //         servantNotActive.printStackTrace();
        } catch (Exception ex) {
                 ex.printStackTrace();
        }
        return ret;
    }

    static WfProcess makeWfProcess(WfProcessCORBA impl) {
        WfProcess ret = null;
        try {
         //   POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
         //   rootPOA.the_POAManager().activate();
            byte[] b = rootPOA.activate_object(impl);
            ret = WfProcessHelper.narrow(rootPOA.id_to_reference(b));

        } catch (ServantAlreadyActive servantAlreadyActive) {
            servantAlreadyActive.printStackTrace();
        } catch (WrongPolicy wrongPolicy) {
            wrongPolicy.printStackTrace();
     //   } catch (ServantNotActive servantNotActive) {
    //        servantNotActive.printStackTrace();
        } catch (Exception ex) {
              ex.printStackTrace();
        }
        return ret;
    }

    static WfProcessIterator makeWfProcessIterator(WfProcessIteratorCORBA impl) {
        WfProcessIterator ret = null;
        try {
            //POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
           rootPOA.the_POAManager().activate();

            byte[] o = rootPOA.activate_object(impl);
          //  ret = WfProcessIteratorHelper.narrow(rootPOA.servant_to_reference(impl));
            ret = WfProcessIteratorHelper.narrow(rootPOA.id_to_reference(o));

        } catch (ServantAlreadyActive servantAlreadyActive) {
            servantAlreadyActive.printStackTrace();
        } catch (WrongPolicy wrongPolicy) {
            wrongPolicy.printStackTrace();
      //  } catch (ServantNotActive servantNotActive) {
      //      servantNotActive.printStackTrace();


        } catch (Exception ex) {
              ex.printStackTrace();
        }
        return ret;
    }

    static WfProcessMgr makeWfProcessMgr(WfProcessMgrCORBA impl) {
        WfProcessMgr ret = null;
        try {
         //   POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
         //   rootPOA.the_POAManager().activate();
            byte[] b = rootPOA.activate_object(impl);
          //  ret = WfProcessMgrHelper.narrow(rootPOA.servant_to_reference(impl));
         //   System.out.println("makeWfProcessMgr: "+new String(b));
            ret = WfProcessMgrHelper.narrow(rootPOA.id_to_reference(b));

        } catch (ServantAlreadyActive servantAlreadyActive) {
            servantAlreadyActive.printStackTrace();
        } catch (WrongPolicy wrongPolicy) {
            wrongPolicy.printStackTrace();
     //   } catch (ServantNotActive servantNotActive) {
     //       servantNotActive.printStackTrace();
        } catch (Exception ex) {
                ex.printStackTrace();
        }
        return ret;
    }

    static WfProcessMgrIterator makeWfProcessMgrIterator(WfProcessMgrIteratorCORBA impl) {
        WfProcessMgrIterator ret = null;
        try {
        //    POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
        //    rootPOA.the_POAManager().activate();
            byte[] b = rootPOA.activate_object(impl);
            ret = WfProcessMgrIteratorHelper.narrow(rootPOA.id_to_reference(b));
        } catch (ServantAlreadyActive servantAlreadyActive) {
            servantAlreadyActive.printStackTrace();
        } catch (WrongPolicy wrongPolicy) {
            wrongPolicy.printStackTrace();
       // } catch (ServantNotActive servantNotActive) {
       //     servantNotActive.printStackTrace();


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ret;
    }

    static ParticipantMap makeParticipantMap(ParticipantMapCORBA impl) {
        ParticipantMap ret = null;
        try {
        //    POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
        //    rootPOA.the_POAManager().activate();
            byte[] b = rootPOA.activate_object(impl);
            ret = ParticipantMapHelper.narrow(rootPOA.id_to_reference(b));
        } catch (ServantAlreadyActive servantAlreadyActive) {
            servantAlreadyActive.printStackTrace();
        } catch (WrongPolicy wrongPolicy) {
            wrongPolicy.printStackTrace();
    //    } catch (ServantNotActive servantNotActive) {
    //        servantNotActive.printStackTrace();
        } catch (Exception ex) {
               ex.printStackTrace();
        }
        return ret;
    }

    static WfRequester makeWfRequester(WfRequesterCORBA impl) {
        WfRequester ret = null;
        try {
       //     POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
       //     rootPOA.the_POAManager().activate();

           byte[] o =  rootPOA.activate_object(impl);
            ret = org.omg.WorkflowModel.WfRequesterHelper.narrow(rootPOA.id_to_reference(o));
        } catch (ServantAlreadyActive servantAlreadyActive) {
            servantAlreadyActive.printStackTrace();
        } catch (WrongPolicy wrongPolicy) {
            wrongPolicy.printStackTrace();
   //     } catch (ServantNotActive servantNotActive) {
  //          servantNotActive.printStackTrace();

        } catch (Exception ex) {
               ex.printStackTrace();
        }
        return ret;
    }

    static org.omg.WorkflowModel.WfResource makeWfResource( WfResourceCORBA impl) {
        org.omg.WorkflowModel.WfResource ret = null;
        try {
         //   POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
         //   rootPOA.the_POAManager().activate();
            byte[] o = rootPOA.activate_object(impl);
        //    ret = org.omg.WorkflowModel.WfResourceHelper.narrow(rootPOA.servant_to_reference(impl));
            ret = org.omg.WorkflowModel.WfResourceHelper.narrow(rootPOA.id_to_reference(o));
           // System.out.println("makeWfResource: "+ret.toString());
        } catch (ServantAlreadyActive servantAlreadyActive) {
            servantAlreadyActive.printStackTrace();
        } catch (WrongPolicy wrongPolicy) {
            wrongPolicy.printStackTrace();
       // } catch (ServantNotActive servantNotActive) {
       //     servantNotActive.printStackTrace();
        } catch (Exception ex) {
             ex.printStackTrace();
        }
        return ret;
    }

    static WfResourceIterator makeWfResourceIterator( WfResourceIteratorCORBA impl) {
        WfResourceIterator ret = null;
        try {
         //   POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
        //   rootPOA.the_POAManager().activate();

            byte[] o = rootPOA.activate_object(impl);
            ret = WfResourceIteratorHelper.narrow(rootPOA.id_to_reference(o));

        } catch (ServantAlreadyActive servantAlreadyActive) {
            servantAlreadyActive.printStackTrace();
        } catch (WrongPolicy wrongPolicy) {
            wrongPolicy.printStackTrace();
       // } catch (ServantNotActive servantNotActive) {
       //     servantNotActive.printStackTrace();

        } catch (Exception ex) {
               ex.printStackTrace();
        }
        return ret;
    }

    static WfAssignment makeWfAssignment(WfAssignmentCORBA impl) {
        WfAssignment ret = null;
        try {
      //      POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
      //     rootPOA.the_POAManager().activate();

            byte[] o = rootPOA.activate_object(impl);
            ret = WfAssignmentHelper.narrow(rootPOA.id_to_reference(o));

        } catch (ServantAlreadyActive servantAlreadyActive) {
            servantAlreadyActive.printStackTrace();
        } catch (WrongPolicy wrongPolicy) {
            wrongPolicy.printStackTrace();
       // } catch (ServantNotActive servantNotActive) {
       //     servantNotActive.printStackTrace();

        } catch (Exception ex) {
             ex.printStackTrace();
        }
        return ret;
    }
}