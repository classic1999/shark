package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLCollection;
import org.enhydra.shark.xpdl.XMLElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class Transitions extends XMLCollection {

   public Transitions (WorkflowProcess parent) {
      super(parent, false);
   }

   public Transitions (ActivitySet parent) {
      super(parent, false);
   }

   public XMLElement generateNewElement() {
      return new Transition(this);
   }

   public Transition getTransition (String Id) {
      return (Transition)super.getCollectionElement(Id);
   }

}
