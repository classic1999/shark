package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLAttribute;
import org.enhydra.shark.xpdl.XMLCollectionElement;
import org.enhydra.shark.xpdl.XPDLConstants;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class FormalParameter extends XMLCollectionElement {

   public FormalParameter (FormalParameters fps) {
      super(fps, true);
   }

   protected void fillStructure () {
      DataType refDataType=new DataType(this);
      Description refDescription=new Description(this); // min=0
      XMLAttribute attrIndex=new XMLAttribute(this,"Index", false);
      // default="IN"
      XMLAttribute attrMode=new XMLAttribute(this,"Mode",
         true,new String[] {
            XPDLConstants.FORMAL_PARAMETER_MODE_IN,
            XPDLConstants.FORMAL_PARAMETER_MODE_OUT,
            XPDLConstants.FORMAL_PARAMETER_MODE_INOUT
         }, 0);

      super.fillStructure();
      add(attrIndex);
      add(attrMode);      
      add(refDataType);      
      add(refDescription);
   }

   public DataType getDataType() {
      return (DataType)get("DataType");
   }
   public String getDescription() {
      return get("Description").toValue();
   }
   public void setDescription(String description) {
      set("Description",description);
   }
   public String getIndex() {
      return get("Index").toValue();
   }
   public void setIndex(String index) {
      set("Index",index);
   }

   public XMLAttribute getModeAttribute() {
      return (XMLAttribute)get("Mode");
   }
   public String getMode() {
      return getModeAttribute().toValue();
   }
   public void setModeIN() {
      getModeAttribute().setValue(XPDLConstants.FORMAL_PARAMETER_MODE_IN);
   }
   public void setModeOUT() {
      getModeAttribute().setValue(XPDLConstants.FORMAL_PARAMETER_MODE_OUT);
   }
   public void setModeINOUT() {
      getModeAttribute().setValue(XPDLConstants.FORMAL_PARAMETER_MODE_INOUT);
   }
   
}
