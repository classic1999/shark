package org.enhydra.shark.assignment;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.internal.assignment.AssignmentManager;
import org.enhydra.shark.api.internal.assignment.PerformerData;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

/**
 *
 * @author Sasa Bojanic
 *
 */
public class XPDLStraightParticipantMappingAssignmentManager implements AssignmentManager {

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

      List toRet=new ArrayList();
      if (xpdlParticipant!=null && 
            xpdlParticipant.participantIdOrExpression!=null && 
            !xpdlParticipant.participantIdOrExpression.equals("")) {
         toRet.add(xpdlParticipant.participantIdOrExpression);
         return toRet;
      }
      if (xpdlResponsibleParticipants!=null && xpdlResponsibleParticipants.size()>0) {
         Iterator it=xpdlResponsibleParticipants.iterator();
         while (it.hasNext()) {
            PerformerData pd=(PerformerData)it.next();
            if (pd.participantIdOrExpression!=null) {
               toRet.add(pd.participantIdOrExpression);
            }
         }
         if (toRet.size()>0) {
            return toRet;
         }
      }
      //if (extAttribs!=null)
      toRet.add(processRequesterId);
      return toRet;
   }

}
