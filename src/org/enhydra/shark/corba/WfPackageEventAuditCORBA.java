package org.enhydra.shark.corba;

import org.omg.TimeBase.*;
import org.omg.WfBase.*;
import org.omg.WorkflowModel.*;
import org.enhydra.shark.corba.WorkflowService.*;

/**
 * WfPackageEventAuditImpl - Workflow Event Audit implementation
 */
public class WfPackageEventAuditCORBA extends _WfPackageEventAuditImplBase {
   private SharkCORBAServer myServer;

   private org.enhydra.shark.api.client.wfservice.WfPackageEventAudit ea;

   protected WfPackageEventAuditCORBA (SharkCORBAServer server,org.enhydra.shark.api.client.wfservice.WfPackageEventAudit ea) {
      this.myServer=server;
      this.ea=ea;
   }

   public WfExecutionObject source () throws BaseException, SourceNotAvailable {
      throw new SourceNotAvailable();
   }

   public UtcT time_stamp () throws BaseException {
      try {
         org.enhydra.shark.api.client.timebase.UtcT t=ea.time_stamp();
         return new UtcT(t.time,t.inacclo,t.inacchi,t.tdf);
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String event_type () throws BaseException {
      try {
         return ea.event_type();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String activity_key () throws BaseException {
      try {
         return ea.activity_key();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String activity_name () throws BaseException {
      try {
         return ea.activity_name();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String process_key () throws BaseException {
      try {
         return ea.process_key();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String process_name () throws BaseException {
      try {
         return ea.process_name();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String process_mgr_name () throws BaseException {
      try {
         return ea.process_mgr_name();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String process_mgr_version () throws BaseException {
      try {
         return ea.process_mgr_version();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String package_id () throws BaseException {
      try {
         return ea.package_id();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String package_version () throws BaseException {
      try {
         return ea.package_version();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String resource_username () throws BaseException {
      try {
         return ea.resource_username();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

}
