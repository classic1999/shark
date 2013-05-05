package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLComplexElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class ListType extends XMLComplexElement {

   public ListType (DataTypes parent) {
      super(parent,true);
   }

   protected void fillStructure () {
      DataTypes refType=new DataTypes(this);
      add(refType);
   }

   public DataTypes getDataTypes() {
      return (DataTypes)get("Type");
   }      
   
}
