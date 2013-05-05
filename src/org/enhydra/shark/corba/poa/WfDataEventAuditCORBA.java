package org.enhydra.shark.corba.poa;

import org.omg.TimeBase.*;
import org.omg.WfBase.*;
import org.omg.WorkflowModel.*;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.enhydra.shark.corba.poa.Collective;
import org.enhydra.shark.corba.poa.SharkCORBAUtilities;
import org.enhydra.shark.corba.poa.WfActivityCORBA;

/**
 * WfDataEventAuditImpl - Workflow Event Audit implementation
 */

public class WfDataEventAuditCORBA extends WfDataEventAuditPOA {


   private org.enhydra.shark.api.client.wfmodel.WfDataEventAudit ea;

   private Collective __collective;
    private ORB orb;

   protected WfDataEventAuditCORBA(ORB orb, Collective toJoin,
                                   org.enhydra.shark.api.client.wfmodel.WfDataEventAudit ea) {
      this.orb = orb;

      __collective = toJoin;
  //    toJoin.__recruit(this);
      this.ea = ea;
   }

   public WfExecutionObject source() throws BaseException, SourceNotAvailable {
      try {
         if (ea.source() instanceof org.enhydra.shark.api.client.wfmodel.WfProcess) {

            WfExecutionObject obj = SharkCORBAUtilities.makeWfProcess( new WfProcessCORBA(orb, __collective,
                                      (org.enhydra.shark.api.client.wfmodel.WfProcess) ea.source()));
         __collective.__recruit(obj);
          return obj;
         } else {

            WfExecutionObject obj = SharkCORBAUtilities.makeWfActivity(new WfActivityCORBA(orb, __collective,
                                       (org.enhydra.shark.api.client.wfmodel.WfActivity) ea.source()));
         __collective.__recruit(obj);
          return obj;
         }
      } catch (org.enhydra.shark.api.client.wfmodel.SourceNotAvailable sna) {
         throw new SourceNotAvailable();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public UtcT time_stamp() throws BaseException {
      try {
         org.enhydra.shark.api.client.timebase.UtcT t = ea.time_stamp();
         return new UtcT(t.time, t.inacclo, t.inacchi, t.tdf);
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String event_type() throws BaseException {
      try {
         return ea.event_type();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String activity_key() throws BaseException {
      try {
         return ea.activity_key();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String activity_name() throws BaseException {
      try {
         return ea.activity_name();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String process_key() throws BaseException {
      try {
         return ea.process_key();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String process_name() throws BaseException {
      try {
         return ea.process_name();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String process_mgr_name() throws BaseException {
      try {
         return ea.process_mgr_name();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String process_mgr_version() throws BaseException {
      try {
         return ea.process_mgr_version();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public NameValue[] old_data() throws BaseException {
      try {
         return SharkCORBAUtilities.makeCORBANameValueArray(orb,
                                                            ea.old_data());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public NameValue[] new_data() throws BaseException {
      try {
         return SharkCORBAUtilities.makeCORBANameValueArray(orb,
                                                            ea.new_data());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

}