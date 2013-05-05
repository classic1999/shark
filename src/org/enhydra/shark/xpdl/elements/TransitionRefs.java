package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLCollection;
import org.enhydra.shark.xpdl.XMLElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class TransitionRefs extends XMLCollection {

   // min=0, max=unbounded
   public TransitionRefs (Split parent) {
      super(parent, false);
   }

   public XMLElement generateNewElement() {
      return new TransitionRef(this);
   }

}
