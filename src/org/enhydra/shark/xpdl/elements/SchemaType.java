package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLComplexElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class SchemaType extends XMLComplexElement {

   public SchemaType (DataTypes parent) {
      super(parent, true);
   }

   public void setValue(String v) {
      if (isReadOnly) {
         throw new RuntimeException("Can't set the value of read only element!");
      }
      this.value = v;
   }
   
   protected void fillStructure () {}

}

