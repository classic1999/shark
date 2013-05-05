package org.enhydra.shark.corbaclient.workflowadmin.instantiation;

import java.sql.Timestamp;
import java.util.*;

import org.enhydra.shark.corba.WorkflowService.ExecutionAdministration;
import org.enhydra.shark.corbaclient.*;

import org.omg.WfBase.*;
import org.omg.WorkflowModel.*;

/**
* Required to crate the process.
*/
public class ProcessInstantiatorCORBA extends _WfRequesterImplBase implements ProcessInstantiatorInterface {

   static List instantiators=new ArrayList();

   public static List getInstantiators () {
      return instantiators;
   }

   private List performers=new ArrayList();

   /** Create a new WfRequester */
   public ProcessInstantiatorCORBA() {
      instantiators.add(this);
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

   /** Gets an iterator of processes.
    */
   public WfProcessIterator get_iterator_performer () throws BaseException {
      throw new BaseException();
   }

   /**
    * A list of processes
    * @param max_number
    */
   public WfProcess[] get_sequence_performer (int max_number) throws BaseException {
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

   /**
    * Receives notice of event status changes
    */
   public void receive_event (WfEventAudit event) throws BaseException, InvalidPerformer {
      try {
         String msg="ProcessInstantiator -> Event received:"+
            " \n\tevent time --> "+new Timestamp(event.time_stamp().time)+
            " \n\tprocess id --> "+event.process_key()+
            " \n\tevent type --> "+event.event_type();
         System.out.println(msg);
         SharkClient.getServer().doneWith(event);
      } catch (Throwable ex) {
      }
   }

   // Prevents memory leak if client forgets to disconnect
   public void releaseProcesses() {
      ExecutionAdministration ea=SharkClient.getExecAmin();
      Iterator it=performers.iterator();
      while (it.hasNext()) {
         String procId=(String)it.next();
         try {
            ea.removeProcessRequester(procId);
         } catch (Exception ex) {
         }
      }
   }

}
