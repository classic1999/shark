package org.enhydra.shark.corba;

import org.omg.TimeBase.UtcT;
import org.omg.WfBase.BaseException;
import org.omg.WorkflowModel.*;

/**
 * WfStateEventAuditImpl - Workflow Event Audit implementation
 */
public class WfStateEventAuditCORBA extends _WfStateEventAuditImplBase {
   
   private org.enhydra.shark.api.client.wfmodel.WfStateEventAudit ea;

   private Collective __collective;

   protected WfStateEventAuditCORBA(Collective toJoin,
                                    org.enhydra.shark.api.client.wfmodel.WfStateEventAudit ea) {
      __collective = toJoin;
      toJoin.__recruit(this);
      this.ea = ea;
   }

   public WfExecutionObject source() throws BaseException, SourceNotAvailable {
      try {
         if (ea.source() instanceof org.enhydra.shark.api.client.wfmodel.WfProcess) {
            return new WfProcessCORBA(__collective,
                                      (org.enhydra.shark.api.client.wfmodel.WfProcess) ea.source());
         } else {
            return new WfActivityCORBA(__collective,
                                       (org.enhydra.shark.api.client.wfmodel.WfActivity) ea.source());
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

   public String old_state() throws BaseException {
      try {
         return ea.old_state();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String new_state() throws BaseException {
      try {
         return ea.new_state();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

}