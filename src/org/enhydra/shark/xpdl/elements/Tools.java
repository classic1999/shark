package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLCollection;
import org.enhydra.shark.xpdl.XMLElement;

/**
 *  Helper class for storing all the Tool elements.
 * 
 *  @author Sasa Bojanic
 */
public class Tools extends XMLCollection {

   // min=0, max=unbounded
   public Tools (ImplementationTypes parent){
      super(parent, false);
   }

   public XMLElement generateNewElement() {
      return new Tool(this);
   }

}
