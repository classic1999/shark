package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLAttribute;
import org.enhydra.shark.xpdl.XMLComplexElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class ExtendedAttribute extends XMLComplexElement {

   public ExtendedAttribute (ExtendedAttributes parent) {
      super(parent, true);
   }

   protected void fillStructure () {
      XMLAttribute attrName=new XMLAttribute(this,"Name", true); // required
      XMLAttribute attrValue=new XMLAttribute(this,"Value", false);

      add(attrName);
      add(attrValue);
   }

   public void setValue(String v) {
      if (isReadOnly) {
         throw new RuntimeException("Can't set the value of read only element!");
      }
      this.value = v;
   }
   
   public String getName() {
      return get("Name").toValue();
   }
   public void setName(String name) {
      set("Name",name);
   }
   public String getVValue() {
      return get("Value").toValue();
   }
   public void setVValue(String value) {
      set("Value",value);
   }

}
