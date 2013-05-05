package org.enhydra.shark.corba;

import org.omg.TimeBase.UtcT;
import org.omg.WfBase.BaseException;
import org.omg.WfBase.NameValue;
import org.omg.WorkflowModel.*;

/**
 * WfProcessImpl - Workflow Process Object implementation
 * 
 * @author Sasa Bojanic
 * @version 1.0
 */
public class WfProcessCORBA extends _WfProcessImplBase {

   org.enhydra.shark.api.client.wfmodel.WfProcess sharkProc;

   private Collective __collective;

   /**
    * Creates new WfProcessImpl
    * 
    * @param sharkProc a WfProcess
    */
   protected WfProcessCORBA(Collective toJoin,
                            org.enhydra.shark.api.client.wfmodel.WfProcess sharkProc) {
      __collective = toJoin;
      toJoin.__recruit(this);
      this.sharkProc = sharkProc;
   }

   /**
    * Retrieve the requestor of this process.
    */
   public WfRequester requester() throws BaseException {
      try {
         WfRequester req;
         org.enhydra.shark.api.client.wfmodel.WfRequester requester = sharkProc.requester();
         if (requester instanceof org.enhydra.shark.api.client.wfmodel.WfActivity) {
            req = new WfActivityCORBA(__collective,
                                      (org.enhydra.shark.api.client.wfmodel.WfActivity) requester);
         } else if (requester instanceof WfLinkingRequesterForCORBA) {
            req = WfLinkingRequesterForCORBA.getCORBARequester(sharkProc.key());
         } else {
            req = new WfRequesterCORBA(__collective, requester);
         }
         return req;
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Set the requester for this process.
    */
   public void set_requester(WfRequester new_value) throws BaseException,
                                                   CannotChangeRequester {
      if (new_value == null) throw new CannotChangeRequester("Trying to set external requester which is null!");

      if (new_value instanceof WfActivity) { throw new CannotChangeRequester("Can't change requester to activity requester"); }

      try {
         WfLinkingRequesterForCORBA lr=new WfLinkingRequesterForCORBA();
         WfLinkingRequesterForCORBA.setCORBARequester(sharkProc.key(),new_value);
         sharkProc.set_requester(lr);
      } catch (org.enhydra.shark.api.client.wfmodel.CannotChangeRequester ccr) {
         throw new CannotChangeRequester();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Retrieve the no of activities in this process.
    */
   public int how_many_step() throws BaseException {
      try {
         return sharkProc.how_many_step();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Retrieve the Iterator of active activities of this process.
    */
   public WfActivityIterator get_iterator_step() throws BaseException {
      try {
         return new WfActivityIteratorCORBA(__collective,
                                            sharkProc.get_iterator_step());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Retrieve the List of activities of this process.
    * 
    * @param max_number High limit of elements in the result set
    *           (0->all).
    */
   public WfActivity[] get_sequence_step(int max_number) throws BaseException {
      try {
         return SharkCORBAUtilities.makeCORBAActivities(__collective,
                                                        sharkProc.get_sequence_step(max_number));
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Check if some activity is a member of this process.
    * 
    * @return true if the specific activity is a member of this process,
    *         false otherwise.
    */
   public boolean is_member_of_step(WfActivity member) throws BaseException {
      try {
         WfActivity[] acts = get_sequence_step(0);
         boolean ret = false;
         if (acts != null) {
            for (int i = 0; i < acts.length; i++) {
               WfActivity act = acts[i];
               if (act.key().equals(member.key())) {
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

   /**
    * Retrieve the WfProcessMgr of this process.
    */
   public WfProcessMgr manager() throws BaseException {
      try {
         return new WfProcessMgrCORBA(__collective, sharkProc.manager());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Retrieve the result for this process.
    */
   public NameValue[] result() throws BaseException, ResultNotAvailable {
      try {
         return SharkCORBAUtilities.makeCORBANameValueArray(this._orb(),
                                                            sharkProc.result());
      } catch (org.enhydra.shark.api.client.wfmodel.ResultNotAvailable rna) {
         throw new ResultNotAvailable();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Starts the process - creates a separate thread.
    */
   public void start() throws BaseException, CannotStart, AlreadyRunning {
      try {
         sharkProc.start();
      } catch (org.enhydra.shark.api.client.wfmodel.CannotStart cns) {
         throw new CannotStart();
      } catch (org.enhydra.shark.api.client.wfmodel.AlreadyRunning ar) {
         throw new AlreadyRunning();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Retrieve the iterator of activities in some specific state.
    */
   public WfActivityIterator get_activities_in_state(String state) throws BaseException,
                                                                  InvalidState {
      try {
         return new WfActivityIteratorCORBA(__collective,
                                            sharkProc.get_activities_in_state(state));
      } catch (org.enhydra.shark.api.client.wfmodel.InvalidState is) {
         throw new InvalidState();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public workflow_stateType workflow_state() throws BaseException {
      try {
         return workflow_stateType.from_int(sharkProc.workflow_state()
            .value());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public while_openType while_open() throws BaseException {
      try {
         return while_openType.from_int(sharkProc.while_open().value());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public why_not_runningType why_not_running() throws BaseException {
      try {
         return why_not_runningType.from_int(sharkProc.why_not_running()
            .value());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public how_closedType how_closed() throws BaseException {
      try {
         return how_closedType.from_int(sharkProc.how_closed().value());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String[] valid_states() throws BaseException {
      try {
         return sharkProc.valid_states();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String state() throws BaseException {
      try {
         return sharkProc.state();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void change_state(String new_state) throws BaseException,
                                             InvalidState,
                                             TransitionNotAllowed {
      try {
         sharkProc.change_state(new_state);
      } catch (org.enhydra.shark.api.client.wfmodel.InvalidState is) {
         throw new InvalidState();
      } catch (org.enhydra.shark.api.client.wfmodel.TransitionNotAllowed tna) {
         throw new TransitionNotAllowed();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String name() throws BaseException {
      try {
         return sharkProc.name();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void set_name(String new_value) throws BaseException {
      try {
         sharkProc.set_name(new_value);
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String key() throws BaseException {
      try {
         return sharkProc.key();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String description() throws BaseException {
      try {
         return sharkProc.description();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void set_description(String new_value) throws BaseException {
      try {
         sharkProc.set_description(new_value);
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public NameValue[] process_context() throws BaseException {
      try {
         return SharkCORBAUtilities.makeCORBANameValueArray(this._orb(),
                                                            sharkProc.process_context());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void set_process_context(NameValue[] new_value) throws BaseException,
                                                         InvalidData,
                                                         UpdateNotAllowed {
      try {
         sharkProc.set_process_context(SharkCORBAUtilities.makeMap(new_value));
      } catch (org.enhydra.shark.api.client.wfmodel.InvalidData id) {
         throw new InvalidData();
      } catch (org.enhydra.shark.api.client.wfmodel.UpdateNotAllowed una) {
         throw new UpdateNotAllowed();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public short priority() throws BaseException {
      try {
         return sharkProc.priority();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void set_priority(short new_value) throws BaseException {
      try {
         sharkProc.set_priority(new_value);
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Resume this process.
    */
   public void resume() throws BaseException, CannotResume, NotSuspended {
      try {
         sharkProc.resume();
      } catch (org.enhydra.shark.api.client.wfmodel.CannotResume cnr) {
         throw new CannotResume();
      } catch (org.enhydra.shark.api.client.wfmodel.NotSuspended ns) {
         throw new NotSuspended();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Suspend this process.
    */
   public void suspend() throws BaseException,
                        CannotSuspend,
                        NotRunning,
                        AlreadySuspended {
      try {
         sharkProc.suspend();
      } catch (org.enhydra.shark.api.client.wfmodel.CannotSuspend cns) {
         throw new CannotSuspend();
      } catch (org.enhydra.shark.api.client.wfmodel.NotRunning nr) {
         throw new NotRunning();
      } catch (org.enhydra.shark.api.client.wfmodel.AlreadySuspended as) {
         throw new AlreadySuspended();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Terminate this process.
    */
   public synchronized void terminate() throws BaseException,
                                       CannotStop,
                                       NotRunning {
      try {
         sharkProc.terminate();
      } catch (org.enhydra.shark.api.client.wfmodel.CannotStop cns) {
         throw new CannotStop();
      } catch (org.enhydra.shark.api.client.wfmodel.NotRunning nr) {
         throw new NotRunning();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Abort the execution of this process.
    */
   public synchronized void abort() throws BaseException,
                                   CannotStop,
                                   NotRunning {
      try {
         sharkProc.abort();
      } catch (org.enhydra.shark.api.client.wfmodel.CannotStop cns) {
         throw new CannotStop();
      } catch (org.enhydra.shark.api.client.wfmodel.NotRunning nr) {
         throw new NotRunning();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public int how_many_history() throws BaseException, HistoryNotAvailable {
      try {
         return sharkProc.how_many_history();
      } catch (org.enhydra.shark.api.client.wfmodel.HistoryNotAvailable hna) {
         throw new HistoryNotAvailable();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Search in the history for specific elements.
    * 
    * @return Found history elements that meet the search criteria.
    */
   public WfEventAuditIterator get_iterator_history(String query,
                                                    NameValue[] names_in_query) throws BaseException,
                                                                               HistoryNotAvailable {
      try {
         return new WfEventAuditIteratorCORBA(__collective, sharkProc.get_iterator_history(query,
                                                                             SharkCORBAUtilities.makeMap(names_in_query)));
      } catch (org.enhydra.shark.api.client.wfmodel.HistoryNotAvailable hna) {
         throw new HistoryNotAvailable();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Getter for history sequence.
    * 
    * @return List of History objects.
    */
   public WfEventAudit[] get_sequence_history(int max_number) throws BaseException,
                                                             HistoryNotAvailable {
      try {
         return SharkCORBAUtilities.makeCORBAEventAudits(__collective, sharkProc.get_sequence_history(max_number));
      } catch (org.enhydra.shark.api.client.wfmodel.HistoryNotAvailable hna) {
         throw new HistoryNotAvailable();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Checks if a 'member' is an element of the history.
    * 
    * @return true if the element of the history, false otherwise.
    */
   public boolean is_member_of_history(WfExecutionObject member) throws BaseException {
      try {
         boolean ret = false;
         WfEventAudit[] evs = SharkCORBAUtilities.makeCORBAEventAudits(__collective, sharkProc.get_sequence_history(0));
         if (evs != null) {
            for (int i = 0; i < evs.length; i++) {
               WfEventAudit ea = evs[i];
               if (member instanceof WfActivity) {
                  WfActivity act = (WfActivity) member;
                  if (act.container().key().equals(ea.process_key())
                      && act.key().equals(ea.activity_key())) {
                     ret = true;
                     break;
                  }
               } else {
                  if (member.key().equals(ea.process_key())) {
                     ret = true;
                     break;
                  }
               }
            }
         }
         return ret;
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public UtcT last_state_time() throws BaseException {
      try {
         org.enhydra.shark.api.client.timebase.UtcT t = sharkProc.last_state_time();
         return new UtcT(t.time, t.inacclo, t.inacchi, t.tdf);
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * It is assumed that there can't be two or more processes having the
    * same key.
    */
   public boolean equals(java.lang.Object obj) {
      if (!(obj instanceof WfProcess)) return false;
      WfProcess proc = (WfProcess) obj;
      try {
         return proc.key().equals(key());
      } catch (Exception ex) {
         return false;
      }
   }

   public String toString() {
      return sharkProc.toString();
   }
}