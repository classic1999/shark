package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLCollection;
import org.enhydra.shark.xpdl.XMLElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class TransitionRestrictions extends XMLCollection {

   // min=0, max=unbounded
   public TransitionRestrictions (Activity act) {
      super(act, false);
   }

   public XMLElement generateNewElement() {
      return new TransitionRestriction(this);
   }

}
