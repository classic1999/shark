package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLComplexElement;
import org.enhydra.shark.xpdl.XMLSimpleElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class Documentation extends XMLSimpleElement {

   public Documentation (XMLComplexElement parent) {
      super(parent, false);
   }
}
