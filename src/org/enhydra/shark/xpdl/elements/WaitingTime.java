package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLSimpleElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class WaitingTime extends XMLSimpleElement {

   public WaitingTime (TimeEstimation parent) {
      super(parent, false);
   }

}
