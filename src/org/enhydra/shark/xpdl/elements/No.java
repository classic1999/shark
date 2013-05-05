package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLComplexElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class No extends XMLComplexElement {
   
   public No (ImplementationTypes parent) {
      super(parent, true);
   }
   
   /**
   * Overrides super-class method to indicate that this element
   * is never empty, so it's tag will always be written into
   * XML file.
   *
   * @return <tt>false</tt>
   */
   public boolean isEmpty() {
      return false;
   }

   protected void fillStructure () {}
}
