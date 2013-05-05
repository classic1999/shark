package org.enhydra.shark.corba;

import org.omg.TimeBase.*;
import org.omg.WfBase.*;
import org.omg.WorkflowModel.*;

/**
 * WfCreateProcessEventAuditImpl - Workflow Event Audit implementation
 */
public class WfCreateProcessEventAuditCORBA extends
                                           _WfCreateProcessEventAuditImplBase {

   private org.enhydra.shark.api.client.wfmodel.WfCreateProcessEventAudit ea;
   private Collective __collective;

   protected WfCreateProcessEventAuditCORBA(Collective toJoin,
                                            org.enhydra.shark.api.client.wfmodel.WfCreateProcessEventAudit ea) {
      __collective = toJoin;
      toJoin.__recruit(this);
      this.ea = ea;
   }

   public WfExecutionObject source() throws BaseException, SourceNotAvailable {
      try {
         return new WfProcessCORBA(__collective, (org.enhydra.shark.api.client.wfmodel.WfProcess) ea.source());
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

   public String p_activity_key() throws BaseException {
      try {
         return ea.p_activity_key();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String p_process_key() throws BaseException {
      try {
         return ea.p_process_key();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String p_process_name() throws BaseException {
      try {
         return ea.p_process_name();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String p_process_mgr_name() throws BaseException {
      try {
         return p_process_mgr_name();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String p_process_mgr_version() throws BaseException {
      try {
         return ea.p_process_mgr_version();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }
}