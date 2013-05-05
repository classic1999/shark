package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLComplexElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class Implementation extends XMLComplexElement {

   public Implementation (ActivityTypes parent) {
      super(parent, true);
   }

   protected void fillStructure () {
      ImplementationTypes refType=new ImplementationTypes(this);

      add(refType);      
   }

   public ImplementationTypes getImplementationTypes() {
      return (ImplementationTypes)get("Type");
   }
}
