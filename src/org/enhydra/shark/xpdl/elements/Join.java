package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLAttribute;
import org.enhydra.shark.xpdl.XMLComplexElement;
import org.enhydra.shark.xpdl.XPDLConstants;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class Join extends XMLComplexElement {

   public Join (TransitionRestriction parent) {
      super(parent, false);
   }

   protected void fillStructure () {
      XMLAttribute attrType=new XMLAttribute(this,"Type",
            false,new String[] {
               "",
               XPDLConstants.JOIN_SPLIT_TYPE_AND,
               XPDLConstants.JOIN_SPLIT_TYPE_XOR
            }, 0);

      add(attrType);      
   }

   public XMLAttribute getTypeAttribute() {
      return (XMLAttribute)get("Type");
   }
   
   public String getType () {
      return getTypeAttribute().toValue();
   }
   public void setTypeNONE () {
      getTypeAttribute().setValue("");      
   }
   public void setTypeAND () {
      getTypeAttribute().setValue(XPDLConstants.JOIN_SPLIT_TYPE_AND);
   }
   public void setTypeXOR () {
      getTypeAttribute().setValue(XPDLConstants.JOIN_SPLIT_TYPE_XOR);      
   }
}
