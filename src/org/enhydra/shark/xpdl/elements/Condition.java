package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLAttribute;
import org.enhydra.shark.xpdl.XMLComplexElement;
import org.enhydra.shark.xpdl.XPDLConstants;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class Condition extends XMLComplexElement {
   
   public Condition (Transition parent) {
      super(parent, false);
   }

   protected void fillStructure () {
      XMLAttribute attrType=new XMLAttribute(this,"Type",
            false,new String[] {
               "",
               XPDLConstants.CONDITION_TYPE_CONDITION,
               XPDLConstants.CONDITION_TYPE_OTHERWISE,
               XPDLConstants.CONDITION_TYPE_EXCEPTION,
               XPDLConstants.CONDITION_TYPE_DEFAULTEXCEPTION
            }, 0);

      add(attrType);
   }

   public void setValue(String v) {
      if (isReadOnly) {
         throw new RuntimeException("Can't set the value of read only element!");
      }
      this.value = v;
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
   public void setTypeCONDITION () {
      getTypeAttribute().setValue(XPDLConstants.CONDITION_TYPE_CONDITION);
   }
   public void setTypeOTHERWISE () {
      getTypeAttribute().setValue(XPDLConstants.CONDITION_TYPE_OTHERWISE);      
   }
   public void setTypeEXCEPTION () {
      getTypeAttribute().setValue(XPDLConstants.CONDITION_TYPE_EXCEPTION);      
   }
   public void setTypeDEFAULTEXCEPTION () {
      getTypeAttribute().setValue(XPDLConstants.CONDITION_TYPE_DEFAULTEXCEPTION);      
   }

}
