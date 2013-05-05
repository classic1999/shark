package org.enhydra.shark.corba.poa;

import org.omg.WfBase.BaseException;
import org.omg.WorkflowModel.*;
import org.omg.CORBA.ORB;
import org.enhydra.shark.corba.poa.Collective;
import org.enhydra.shark.corba.poa.SharkCORBAUtilities;
import org.enhydra.shark.corba.poa.WfProcessIteratorCORBA;


/**
 * WfRequesterImpl - Workflow Requester implementation
 */
public class WfRequesterCORBA extends WfRequesterPOA {

   org.enhydra.shark.api.client.wfmodel.WfRequester reqInternal;

   private Collective __collective;
   private ORB orb;

   public WfRequesterCORBA(ORB orb, Collective toJoin,
                           org.enhydra.shark.api.client.wfmodel.WfRequester reqInternal) {
      __collective = toJoin;

      this.orb = orb;
      //toJoin.__recruit(this);
      this.reqInternal = reqInternal;
   }

   /**
    * Gets the number of processes.
    */
   public int how_many_performer() throws BaseException {
      try {
         return reqInternal.how_many_performer();
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
                                           reqInternal.get_iterator_performer()));
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
         return SharkCORBAUtilities.makeCORBAProcesses(__collective, reqInternal.get_sequence_performer(max_number));
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
         WfProcess[] perfs = SharkCORBAUtilities.makeCORBAProcesses(__collective, reqInternal.get_sequence_performer(0));
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
                                                InvalidPerformer {
      throw new BaseException();
   }

}

