package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLSimpleElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class Duration extends XMLSimpleElement {

   public Duration (TimeEstimation parent) {
      super(parent, false);
   }
}
