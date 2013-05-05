package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLComplexElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class Automatic extends XMLComplexElement {

   public Automatic (StartFinishModes parent) {
      super(parent, true);
   }
   
   public boolean isEmpty() {
      return false;
   }
   
   protected void fillStructure () {}
   
}
