package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLComplexElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class Member extends XMLComplexElement {

   public Member (RecordType parent) {
      super(parent,true);
   }

   public Member (UnionType parent) {
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
