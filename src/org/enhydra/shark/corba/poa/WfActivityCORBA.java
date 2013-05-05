package org.enhydra.shark.corba.poa;

import org.omg.TimeBase.UtcT;
import org.omg.WfBase.BaseException;
import org.omg.WfBase.NameValue;
import org.omg.WorkflowModel.*;
import org.omg.CORBA.ORB;
import org.enhydra.shark.corba.poa.Collective;
import org.enhydra.shark.corba.poa.SharkCORBAUtilities;

/**
 * WfActivityImpl - Workflow Activity Object implementation
 *
 * @author David Forslund
 * @version 1.0
 */
public class WfActivityCORBA extends WfActivityPOA {

   org.enhydra.shark.api.client.wfmodel.WfActivity sharkAct;

   private Collective __collective;
  private ORB orb;

   /**
    * Create a new WfActivityImpl
    */
   protected WfActivityCORBA(ORB orb, Collective toJoin,
                             org.enhydra.shark.api.client.wfmodel.WfActivity sharkAct) {
      __collective = toJoin;
      this.orb = orb;
      //toJoin.__recruit(this);
      this.sharkAct = sharkAct;
   }

   /**
    * Retrieve the no. of Assignment objects.
    *
    * @throws BaseException General workflow exception.
    * @return No. of current assignments.
    */
   public int how_many_assignment() throws BaseException {
      try {
         return sharkAct.how_many_assignment();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Retrieve the Iterator of Assignments objects.
    */
   public WfAssignmentIterator get_iterator_assignment() throws BaseException {
      try {
         WfAssignmentIterator iter = SharkCORBAUtilities.makeWfAssignmentIterator(new WfAssignmentIteratorCORBA(orb, __collective,
                                              sharkAct.get_iterator_assignment()));
          __collective.__recruit(iter);
          return iter;
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Retrieve all assignments of this activity.
    *
    * @return array of WfAssignment objects.
    */
   public WfAssignment[] get_sequence_assignment(int max_number) throws BaseException {
      try {
         return SharkCORBAUtilities.makeCORBAAssignments( __collective,
                                                         sharkAct.get_sequence_assignment(max_number));
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Check if a specific assignment is a member of this activity.
    *
    * @return true if the assignment is a member of this activity.
    */
   public boolean is_member_of_assignment(WfAssignment member) throws BaseException {
      try {
         String actId = member.activity().key();
         return actId.equals(sharkAct.key());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Getter for the process of this activity.
    */
   public WfProcess container() throws BaseException {
      try {
         WfProcess proc = SharkCORBAUtilities.makeWfProcess(new WfProcessCORBA(orb, __collective, sharkAct.container()));
        __collective.__recruit(proc);
          return proc;
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Retrieve the Result map of this activity.
    *
    * @return Map of results from this activity
    */
   public NameValue[] result() throws BaseException, ResultNotAvailable {
      try {
         return SharkCORBAUtilities.makeCORBANameValueArray(orb,
                                                            sharkAct.result());
      } catch (org.enhydra.shark.api.client.wfmodel.ResultNotAvailable rna) {
         throw new ResultNotAvailable();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Assign Result for this activity.
    */
   public void set_result(NameValue[] results) throws BaseException,
                                              InvalidData {
      try {
         sharkAct.set_result(SharkCORBAUtilities.makeMap(results));
      } catch (org.enhydra.shark.api.client.wfmodel.InvalidData id) {
         throw new InvalidData();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Complete this activity.
    */
   public void complete() throws BaseException, CannotComplete {
      try {
         sharkAct.complete();
      } catch (org.enhydra.shark.api.client.wfmodel.CannotComplete cnc) {
         throw new CannotComplete();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public workflow_stateType workflow_state() throws BaseException {
      try {
         return workflow_stateType.from_int(sharkAct.workflow_state().value());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public while_openType while_open() throws BaseException {
      try {
         return while_openType.from_int(sharkAct.while_open().value());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public why_not_runningType why_not_running() throws BaseException {
      try {
         return why_not_runningType.from_int(sharkAct.why_not_running()
            .value());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public how_closedType how_closed() throws BaseException {
      try {
         return how_closedType.from_int(sharkAct.how_closed().value());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String[] valid_states() throws BaseException {
      try {
         return sharkAct.valid_states();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String state() throws BaseException {
      try {
         return sharkAct.state();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void change_state(String new_state) throws BaseException,
                                             InvalidState,
                                             TransitionNotAllowed {
      try {
         sharkAct.change_state(new_state);
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
         return sharkAct.name();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void set_name(String new_value) throws BaseException {
      try {
         sharkAct.set_name(new_value);
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String key() throws BaseException {
      try {
         return sharkAct.key();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String description() throws BaseException {
      try {
         return sharkAct.description();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void set_description(String new_value) throws BaseException {
      try {
         sharkAct.set_description(new_value);
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public NameValue[] process_context() throws BaseException {
      try {
         return SharkCORBAUtilities.makeCORBANameValueArray(orb,
                                                            sharkAct.process_context());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void set_process_context(NameValue[] new_value) throws BaseException,
                                                         InvalidData,
                                                         UpdateNotAllowed {
      try {
         sharkAct.set_process_context(SharkCORBAUtilities.makeMap(new_value));
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
         return sharkAct.priority();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void set_priority(short new_value) throws BaseException {
      try {
         sharkAct.set_priority(new_value);
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Resume this process or activity.
    */
   public void resume() throws BaseException, CannotResume, NotSuspended {
      try {
         sharkAct.resume();
      } catch (org.enhydra.shark.api.client.wfmodel.CannotResume cnr) {
         throw new CannotResume();
      } catch (org.enhydra.shark.api.client.wfmodel.NotSuspended ns) {
         throw new NotSuspended();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Suspend this process or activity.
    */
   public void suspend() throws BaseException,
                        CannotSuspend,
                        NotRunning,
                        AlreadySuspended {
      try {
         sharkAct.suspend();
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
    * Terminate this process or activity.
    */
   public void terminate() throws BaseException, CannotStop, NotRunning {
      try {
         sharkAct.terminate();
      } catch (org.enhydra.shark.api.client.wfmodel.CannotStop cns) {
         throw new CannotStop();
      } catch (org.enhydra.shark.api.client.wfmodel.NotRunning nr) {
         throw new NotRunning();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Abort the execution of this process or activity.
    */
   public void abort() throws BaseException, CannotStop, NotRunning {
      try {
         sharkAct.abort();
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
         return sharkAct.how_many_history();
      } catch (org.enhydra.shark.api.client.wfmodel.HistoryNotAvailable hna) {
         throw new HistoryNotAvailable();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Search in the history for specific elements.
    *
    * @param query Search criteria.
    * @param names_in_query elements to search.
    * @return Found history elements that meet the search criteria.
    */
   public WfEventAuditIterator get_iterator_history(String query,
                                                    NameValue[] names_in_query) throws BaseException,
                                                                               HistoryNotAvailable {
      try {
         WfEventAuditIterator iter = SharkCORBAUtilities.makeWfEventAuditIterator(new WfEventAuditIteratorCORBA(orb, __collective,
                                              sharkAct.get_iterator_history(query,
                                                                            SharkCORBAUtilities.makeMap(names_in_query))));
         __collective.__recruit(iter);
          return iter;
      } catch (org.enhydra.shark.api.client.wfmodel.HistoryNotAvailable hna) {
         throw new HistoryNotAvailable();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Getter for history sequence.
    *
    * @param max_number Maximum number of element in result list.
    * @return List of History objects.
    */
   public WfEventAudit[] get_sequence_history(int max_number) throws BaseException,
                                                             HistoryNotAvailable {
      try {
         return SharkCORBAUtilities.makeCORBAEventAudits(__collective,
                                                         sharkAct.get_sequence_history(max_number));
      } catch (org.enhydra.shark.api.client.wfmodel.HistoryNotAvailable hna) {
         throw new HistoryNotAvailable();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Predicate to check if a 'member' is an element of the history.
    *
    * @return true if the element of the history, false otherwise.
    */
   public boolean is_member_of_history(WfExecutionObject member) throws BaseException {
      try {
         boolean ret = false;
         WfEventAudit[] evs = SharkCORBAUtilities.makeCORBAEventAudits(__collective,
                                                                       sharkAct.get_sequence_history(0));
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
         org.enhydra.shark.api.client.timebase.UtcT t = sharkAct.last_state_time();
         return new UtcT(t.time, t.inacclo, t.inacchi, t.tdf);
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Gets the number of processes.
    */
   public int how_many_performer() throws BaseException {
      try {
         return sharkAct.how_many_performer();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Gets an iterator of processes.
    */
   public WfProcessIterator get_iterator_performer() throws BaseException {
      try {
         WfProcessIterator iter = SharkCORBAUtilities.makeWfProcessIterator(new WfProcessIteratorCORBA(orb, __collective,
                                           sharkAct.get_iterator_performer()));
          __collective.__recruit(iter);
          return iter;

      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * A list of processes
    *
    * @return List of WfProcess objects.
    */
   public WfProcess[] get_sequence_performer(int max_number) throws BaseException {
      try {
         return SharkCORBAUtilities.makeCORBAProcesses(__collective,
                                                       sharkAct.get_sequence_performer(max_number));
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /**
    * Checks if a WfProcess is associated with this requester object
    *
    * @return true if the process is found.
    */
   public boolean is_member_of_performer(WfProcess member) throws BaseException {
      try {
         boolean ret = false;
         WfProcess[] perfs = SharkCORBAUtilities.makeCORBAProcesses(__collective,
                                                                    sharkAct.get_sequence_performer(0));
         if (perfs != null) {
            for (int i = 0; i < perfs.length; i++) {
               WfProcess perf = perfs[i];
               if (perf.key().equals(member.key())) {
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

   public void receive_event(WfEventAudit event) throws BaseException,
                                                InvalidPerformer {}

   /**
    * It is assumed that there can't be two or more activities having
    * the same key.
    */
   public boolean equals(java.lang.Object obj) {
      if (!(obj instanceof WfActivity)) return false;
      WfActivity act = (WfActivity) obj;
      try {
         return act.key().equals(key());
      } catch (Exception ex) {
         return false;
      }
   }

   public String toString() {
      return sharkAct.toString();
   }

}