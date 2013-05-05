package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLAttribute;
import org.enhydra.shark.xpdl.XMLComplexElement;
import org.enhydra.shark.xpdl.XPDLConstants;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class BasicType extends XMLComplexElement {

   public BasicType (DataTypes parent) {
      super(parent, true);
   }

   protected void fillStructure () {
      // must be one of following: STRING, FLOAT, INTEGER, REFERENCE, DATETIME,
      // BOOLEAN OR PERFORMER
      //required
      XMLAttribute attrType=new XMLAttribute(this,"Type",
         true,new String[] {
            XPDLConstants.BASIC_TYPE_STRING,
            XPDLConstants.BASIC_TYPE_FLOAT,
            XPDLConstants.BASIC_TYPE_INTEGER,
            XPDLConstants.BASIC_TYPE_REFERENCE,
            XPDLConstants.BASIC_TYPE_DATETIME,
            XPDLConstants.BASIC_TYPE_BOOLEAN,
            XPDLConstants.BASIC_TYPE_PERFORMER
         }, 0);

      add(attrType);
   }

   public XMLAttribute getTypeAttribute() {
      return (XMLAttribute)get("Type");
   }
   
   public String getType () {
      return getTypeAttribute().toValue();
   }
   public void setTypeSTRING () {
      getTypeAttribute().setValue(XPDLConstants.BASIC_TYPE_STRING);
   }
   public void setTypeFLOAT () {
      getTypeAttribute().setValue(XPDLConstants.BASIC_TYPE_FLOAT);      
   }
   public void setTypeINTEGER () {
      getTypeAttribute().setValue(XPDLConstants.BASIC_TYPE_INTEGER);      
   }
   public void setTypeREFERENCE () {
      getTypeAttribute().setValue(XPDLConstants.BASIC_TYPE_REFERENCE);      
   }
   public void setTypeDATETIME () {
      getTypeAttribute().setValue(XPDLConstants.BASIC_TYPE_DATETIME);            
   }
   public void setTypeBOOLEAN () {
      getTypeAttribute().setValue(XPDLConstants.BASIC_TYPE_BOOLEAN);
   }
   public void setTypePERFORMER () {
      getTypeAttribute().setValue(XPDLConstants.BASIC_TYPE_PERFORMER);            
   }
      
}
