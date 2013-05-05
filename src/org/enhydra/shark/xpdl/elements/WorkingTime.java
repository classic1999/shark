package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLSimpleElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class WorkingTime extends XMLSimpleElement {

   public WorkingTime (TimeEstimation parent) {
      super(parent, false);
   }
}
