package org.enhydra.shark.xpdl.elements;


import org.enhydra.shark.xpdl.XMLCollection;
import org.enhydra.shark.xpdl.XMLElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class Responsibles extends XMLCollection {

   public Responsibles (RedefinableHeader parent) {
      super(parent, false);
   }

   public XMLElement generateNewElement() {
      return new Responsible(this);
   }

}
