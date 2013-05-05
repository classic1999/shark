package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLAttribute;
import org.enhydra.shark.xpdl.XMLComplexElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class ArrayType extends XMLComplexElement {

   public ArrayType (DataTypes parent) {
      super(parent,true);
   }

   protected void fillStructure () {
      XMLAttribute attrLowerIndex=new XMLAttribute(this,"LowerIndex",true); // required
      XMLAttribute attrUpperIndex=new XMLAttribute(this,"UpperIndex",true); // required
      DataTypes refType=new DataTypes(this);

      add(attrLowerIndex);
      add(attrUpperIndex);
      add(refType);
   }

   public DataTypes getDataTypes() {
      return (DataTypes)get("Type");
   }      

   public String getLowerIndex() {
      return get("LowerIndex").toValue();
   }
   public void setLowerIndex(String li) {
      set("LowerIndex",li);
   }

   public String getUpperIndex() {
      return get("UpperIndex").toValue();
   }
   public void setUpperIndex(String ui) {
      set("UpperIndex",ui);
   }
   
   
}
