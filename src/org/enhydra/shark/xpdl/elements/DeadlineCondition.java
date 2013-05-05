package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLSimpleElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class DeadlineCondition extends XMLSimpleElement {

   public DeadlineCondition (Deadline parent) {
      super(parent, true);
   }
   
}
