package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLSimpleElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class Created extends XMLSimpleElement {

   public Created (PackageHeader parent) {
      super(parent, true);
   }

   public Created (ProcessHeader parent) {
      super(parent, false);
   }
   
}
