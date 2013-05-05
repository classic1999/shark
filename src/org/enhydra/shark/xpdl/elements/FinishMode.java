package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLComplexElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class FinishMode extends XMLComplexElement {

   public FinishMode (Activity parent) {
      super(parent, false);
   }

   protected void fillStructure () {
      StartFinishModes refMode=new  StartFinishModes(this);

      add(refMode);
   }

   public StartFinishModes getStartFinishModes() {
      return (StartFinishModes)get("Mode");
   }
}
