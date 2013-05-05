package org.enhydra.shark.corba;

import org.omg.WfBase.BaseException;
import org.omg.WfBase.NameValueInfo;
import org.omg.WorkflowModel.*;

/**
 * WfProcessMgrImpl - Workflow Process Manager implementation
 */

public class WfProcessMgrCORBA extends _WfProcessMgrImplBase {

   org.enhydra.shark.api.client.wfmodel.WfProcessMgr sharkProcMgr;

   private Collective __collective;

   /**
    * Creates new WfProcessMgrImpl
    * 
    * @param sharkProcMgr a WfProcessMgr
    */
   protected WfProcessMgrCORBA(Collective toJoin,
                               org.enhydra.shark.api.client.wfmodel.WfProcessMgr sharkProcMgr) {
      __collective = toJoin;
      toJoin.__recruit(this);
      this.sharkProcMgr = sharkProcMgr;
   }

   public int how_many_process() throws BaseException {
      try {
         return sharkProcMgr.how_many_process();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public WfProcessIterator get_iterator_process() throws BaseException {
      try {
         return new WfProcessIteratorCORBA(__collective,
                                           sharkProcMgr.get_iterator_process());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public WfProcess[] get_sequence_process(int max_number) throws BaseException {
      try {
         return SharkCORBAUtilities.makeCORBAProcesses(__collective,
                                                       sharkProcMgr.get_sequence_process(max_number));
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public boolean is_member_of_process(WfProcess member) throws BaseException {
      try {
         WfProcess[] procs = get_sequence_process(0);
         boolean ret = false;
         if (procs != null) {
            for (int i = 0; i < procs.length; i++) {
               if (procs[i].key().equals(member.key())) {
                  ret = true;
                  break;
               }
            }
         }
         return ret;
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public process_mgr_stateType process_mgr_state() throws BaseException {
      try {
         return process_mgr_stateType.from_int(sharkProcMgr.process_mgr_state()
            .value());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void set_process_mgr_state(process_mgr_stateType new_state) throws BaseException,
                                                                     TransitionNotAllowed {
      try {
         sharkProcMgr.set_process_mgr_state(org.enhydra.shark.api.client.wfmodel.process_mgr_stateType.from_int(new_state.value()));
      } catch (org.enhydra.shark.api.client.wfmodel.TransitionNotAllowed tna) {
         throw new TransitionNotAllowed();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String name() throws BaseException {
      try {
         return sharkProcMgr.name();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String description() throws BaseException {
      try {
         return sharkProcMgr.description();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String category() throws BaseException {
      try {
         return sharkProcMgr.category();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String version() throws BaseException {
      try {
         return sharkProcMgr.version();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public NameValueInfo[] context_signature() throws BaseException {
      try {
         return SharkCORBAUtilities.makeCORBANameValueInfoArray(sharkProcMgr.context_signature());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public NameValueInfo[] result_signature() throws BaseException {
      try {
         return SharkCORBAUtilities.makeCORBANameValueInfoArray(sharkProcMgr.result_signature());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Create a WfProcess object
    */
   public WfProcess create_process(WfRequester requester) throws BaseException,
                                                         NotEnabled,
                                                         InvalidRequester,
                                                         RequesterRequired {

      if (requester == null) throw new RequesterRequired();

      if (requester instanceof WfActivity) { throw new BaseException(); }

      try {
         WfLinkingRequesterForCORBA lr = new WfLinkingRequesterForCORBA();
         org.enhydra.shark.api.client.wfmodel.WfProcess procInternal=
            sharkProcMgr.create_process(lr);
         WfLinkingRequesterForCORBA.setCORBARequester(procInternal.key(), requester);
         WfProcess pr = new WfProcessCORBA(__collective,procInternal);
         return pr;
      } catch (org.enhydra.shark.api.client.wfmodel.NotEnabled ne) {
         throw new NotEnabled();
      } catch (org.enhydra.shark.api.client.wfmodel.InvalidRequester ir) {
         throw new InvalidRequester();
      } catch (org.enhydra.shark.api.client.wfmodel.RequesterRequired rr) {
         throw new RequesterRequired();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * It is assumed that there can't be two or more processes mgrs
    * having the same name.
    */
   public boolean equals(Object obj) {
      if (!(obj instanceof WfProcessMgr)) return false;
      WfProcessMgr mgr = (WfProcessMgr) obj;
      try {
         return mgr.name().equals(name());
      } catch (Exception ex) {
         return false;
      }
   }

   public String toString() {
      return sharkProcMgr.toString();
   }

}

