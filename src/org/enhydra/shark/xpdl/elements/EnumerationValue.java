package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLAttribute;
import org.enhydra.shark.xpdl.XMLComplexElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class EnumerationValue extends XMLComplexElement {

   public EnumerationValue (EnumerationType parent) {
      super(parent,true);
   }

   protected void fillStructure () {
      XMLAttribute attrName=new XMLAttribute(parent,"Name",true); // required
      add(attrName);
   }

   public String getName() {
      return get("Name").toValue();
   }
   public void setName(String name) {
      set("Name",name);
   }
   
}
