package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLCollection;
import org.enhydra.shark.xpdl.XMLElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class ActualParameters extends XMLCollection {

   public ActualParameters (Tool parent) {
      super(parent, false);
   }

   public ActualParameters (SubFlow parent) {
      super(parent, false);
   }

   public XMLElement generateNewElement() {
      return new ActualParameter(this);
   }

}
