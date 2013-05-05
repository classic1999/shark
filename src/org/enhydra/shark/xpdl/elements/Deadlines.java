package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLCollection;
import org.enhydra.shark.xpdl.XMLElement;

/**
 *  Helper class for storing all the Deadline elements.
 * 
 *  @author Sasa Bojanic
 */
public class Deadlines extends XMLCollection {

   // min=0, max=unbounded
   public Deadlines (Activity parent){
      super(parent, false);
   }

   public XMLElement generateNewElement() {
      return new Deadline(this);
   }

}
