package org.enhydra.shark.swingclient.workflowadmin.instantiation;

import java.util.ArrayList;
import java.util.List;

import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfmodel.InvalidPerformer;
import org.enhydra.shark.api.client.wfmodel.WfDataEventAudit;
import org.enhydra.shark.api.client.wfmodel.WfEventAudit;
import org.enhydra.shark.api.client.wfmodel.WfProcess;
import org.enhydra.shark.api.client.wfmodel.WfProcessIterator;
import org.enhydra.shark.api.client.wfmodel.WfRequester;
import org.enhydra.shark.api.client.wfmodel.WfStateEventAudit;
import org.enhydra.shark.swingclient.SharkClient;

/**
 * Required to crate the process.
 */
public class ProcessInstantiator implements WfRequester {

   private List performers=new ArrayList();

   /** Create a new WfRequester */
   public ProcessInstantiator() {
   }

   public void addPerformer (WfProcess pr) {
      try {
         performers.add(pr.key());
      } catch (Exception ex) {}
   }
   /**
    * Gets the number of processes.
    */
   public int how_many_performer () throws BaseException {
      return performers.size();
   }

   public int how_many_performer (SharkTransaction t) throws BaseException {
      return performers.size();
   }

   /** Gets an iterator of processes.
    */
   public WfProcessIterator get_iterator_performer () throws BaseException {
      throw new BaseException("Not implemented");
   }

   public WfProcessIterator get_iterator_performer (SharkTransaction t) throws BaseException {
      throw new BaseException("Not implemented");
   }

   /**
    * A list of processes
    * @param max_number
    */
   public WfProcess[] get_sequence_performer (int max_number) throws BaseException {
      return get_sequence_performer(null,max_number);
   }

   public WfProcess[] get_sequence_performer (SharkTransaction t,int max_number) throws BaseException {
      WfProcess[] processes=new WfProcess[performers.size()];
      for (int i=0; i<processes.length; i++) {
         try {
            processes[i]=SharkClient.getExecAmin().getProcess(performers.get(i).toString());
         } catch (Exception ex) {}
      }
      return processes;
   }

   /**
    * Checks if a WfProcess is associated with this requester object
    * @param member
    * @return true if the process is found.
    */
   public boolean is_member_of_performer (WfProcess member) throws BaseException {
      return performers.contains(member);
   }

   public boolean is_member_of_performer (SharkTransaction t,WfProcess member) throws BaseException {
      return performers.contains(member);
   }

   /**
    * Receives notice of event status changes
    */
   public void receive_event (WfEventAudit event) throws BaseException, InvalidPerformer {
      throw new BaseException("Not implemented");
   }

   public void receive_event (SharkTransaction t,WfEventAudit event) throws BaseException, InvalidPerformer {
      try {
         String msg="ProcessInstantiator -> Event received:"+
            " \n\tevent time --> "+event.time_stamp().getTimestamp()+
            " \n\tprocess id --> "+event.process_key()+
            " \n\tevent type --> "+event.event_type();
         if (event instanceof WfStateEventAudit) {
            msg+=" \n\told state  --> "+((WfStateEventAudit)event).old_state();
            msg+=" \n\tnew state  --> "+((WfStateEventAudit)event).new_state();
         }
         if (event instanceof WfDataEventAudit) {
            msg+=" \n\told data   --> "+((WfDataEventAudit)event).old_data();
            msg+=" \n\tnew data   --> "+((WfDataEventAudit)event).new_data();
         }         
         System.out.println(msg);
      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }

}
