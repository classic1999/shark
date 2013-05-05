package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLCollection;
import org.enhydra.shark.xpdl.XMLElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class Participants extends XMLCollection {

   public Participants(Package parent) {
      super(parent, false);
   }

   public Participants(WorkflowProcess parent) {
      super(parent, false);
   }

   public XMLElement generateNewElement() {
      return new Participant(this);
   }

   public Participant getParticipant (String Id) {
      return (Participant)super.getCollectionElement(Id);
   }

}
