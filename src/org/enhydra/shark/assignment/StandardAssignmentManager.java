package org.enhydra.shark.assignment;


import org.enhydra.shark.api.*;
import org.enhydra.shark.api.internal.assignment.*;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

import java.util.*;

/**
 *
 * @author Sasa Bojanic
 *
 */
public class StandardAssignmentManager implements AssignmentManager {

   private CallbackUtilities cus;
   public void configure (CallbackUtilities cus) throws RootException {
      this.cus=cus;
   }


   public List getAssignments (SharkTransaction t,
                               String engineName,
                               String procId,
                               String actId,
                               List userIds,
                               List responsibleIds,
                               String processRequesterId,
                               PerformerData xpdlParticipant,
                               List xpdlResponsibleParticipants) throws RootException {

      if (userIds!=null && userIds.size()>0) return userIds;
      if (responsibleIds!=null && responsibleIds.size()>0) return responsibleIds;
      //if (extAttribs!=null)
      List ret=new ArrayList();
      ret.add(processRequesterId);
      return ret;
   }

}
