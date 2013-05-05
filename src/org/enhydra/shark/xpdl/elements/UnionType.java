package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLCollection;
import org.enhydra.shark.xpdl.XMLElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class UnionType extends XMLCollection {

   public UnionType (DataTypes parent) {
      super(parent,true);
   }

   public XMLElement generateNewElement() {
      return new Member(this);
   }

}
