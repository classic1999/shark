package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLAttribute;
import org.enhydra.shark.xpdl.XMLComplexElement;

/**
 *  Represents coresponding element from XPDL schema.
 *
 *  @author Sasa Bojanic
 */
public class DeclaredType extends XMLComplexElement {

   public DeclaredType (DataTypes parent) {
      super(parent, true);
   }

   protected void fillStructure () {
      XMLAttribute attrId=new XMLAttribute(this,"Id", true); // required

      add(attrId);
   }

   public String getId() {
      return get("Id").toValue();
   }
   public void setId(String id) {
      set("Id",id);
   }
}
