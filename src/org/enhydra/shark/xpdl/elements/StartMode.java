package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLComplexElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class StartMode extends XMLComplexElement {

   public StartMode (Activity parent) {
      super(parent, false);
   }

   protected void fillStructure () {
      StartFinishModes refMode=new  StartFinishModes(this);

      //refMode.setRequired(true);
      add(refMode);
   }

   public StartFinishModes getStartFinishModes() {
      return (StartFinishModes)get("Mode");
   }

}
