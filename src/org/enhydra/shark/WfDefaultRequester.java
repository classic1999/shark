package org.enhydra.shark;

import java.util.List;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfmodel.InvalidPerformer;
import org.enhydra.shark.api.client.wfmodel.WfEventAudit;
import org.enhydra.shark.api.client.wfmodel.WfRequester;
import org.enhydra.shark.api.internal.working.WfProcessInternal;
import org.enhydra.shark.api.internal.working.WfRequesterInternal;

/**
 * WfRequesterImpl - Workflow Requester implementation
 *
 * @author Sasa Bojanic
 */
public class WfDefaultRequester implements WfRequesterInternal {

   private String resourceUsername;

   private WfRequester externalRequester;

   protected WfDefaultRequester (String resourceUsername,WfRequester extRequester) {
      this.resourceUsername=resourceUsername;
      this.externalRequester=extRequester;
   }

   public String getResourceRequesterUsername (SharkTransaction t) throws BaseException {
      return resourceUsername;
   }

   public WfRequester getExternalRequester(SharkTransaction t) throws BaseException {
      return externalRequester;
   }

   /**
    * Receives notice of event status changes
    */
   public void receive_event (SharkTransaction t,WfEventAudit event,WfProcessInternal process) throws BaseException, InvalidPerformer {
      //WfRequester external=SharkUtilities.getProcessRequester(process.key(t));
      if (externalRequester!=null) {
         try {
            externalRequester.receive_event(t,event);
         } catch (Throwable thr) {}
      }
   }

   /**
    * A list of processes
    * @return List of WfProcessInternal objects.
    */
   private List getPerformerIds (SharkTransaction t) throws BaseException {
      try {
         return SharkEngineManager.getInstance().getInstancePersistenceManager().getResourceRequestersProcessIds(resourceUsername,t);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String toString () {
      return "Default requester - "+resourceUsername;
   }

}

