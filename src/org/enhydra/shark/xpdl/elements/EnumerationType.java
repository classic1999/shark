package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLCollection;
import org.enhydra.shark.xpdl.XMLElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class EnumerationType extends XMLCollection {

   public EnumerationType (DataTypes parent) {
      super(parent,true);
   }

   public XMLElement generateNewElement() {
      return new EnumerationValue(this);
   }

}
