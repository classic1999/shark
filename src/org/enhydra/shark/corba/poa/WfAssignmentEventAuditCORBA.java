package org.enhydra.shark.corba.poa;

import org.omg.TimeBase.*;
import org.omg.WfBase.*;
import org.omg.WorkflowModel.*;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.enhydra.shark.corba.poa.Collective;
import org.enhydra.shark.corba.poa.SharkCORBAUtilities;
import org.enhydra.shark.corba.poa.WfActivityCORBA;

/**
 * WfEventAuditImpl - Workflow Event Audit implementation
 */
public class WfAssignmentEventAuditCORBA extends
                                        WfAssignmentEventAuditPOA {

   private org.enhydra.shark.api.client.wfmodel.WfAssignmentEventAudit ea;

   private Collective __collective;
    private ORB orb;

   protected WfAssignmentEventAuditCORBA(ORB orb, Collective toJoin,
                                         org.enhydra.shark.api.client.wfmodel.WfAssignmentEventAudit ea) {
      this.orb = orb;
      __collective = toJoin;

     // toJoin.__recruit(this);
      this.ea = ea;
   }

   public WfExecutionObject source() throws BaseException, SourceNotAvailable {

      try {
         WfExecutionObject obj = SharkCORBAUtilities.makeWfActivity(new WfActivityCORBA(orb, __collective,
                                    (org.enhydra.shark.api.client.wfmodel.WfActivity) ea.source()));
          __collective.__recruit(obj);
          return obj;

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

   public String old_resource_key() throws BaseException {
      try {
         return ea.old_resource_key();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String old_resource_name() throws BaseException {
      try {
         return ea.old_resource_name();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String new_resource_key() throws BaseException {
      try {
         return ea.new_resource_key();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String new_resource_name() throws BaseException {
      try {
         return ea.new_resource_name();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public boolean is_accepted() throws BaseException {
      try {
         return ea.is_accepted();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

}