package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLAttribute;
import org.enhydra.shark.xpdl.XMLComplexElement;
import org.enhydra.shark.xpdl.XPDLConstants;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class Tool extends XMLComplexElement {

   public Tool (Tools parent){
      super(parent, true);
   }

   protected void fillStructure () {
      XMLAttribute attrId=new XMLAttribute(this,"Id", true); // required
      XMLAttribute attrType=new XMLAttribute(this,"Type",
            false,new String[] {
               "",
               XPDLConstants.TOOL_TYPE_APPLICATION,
               XPDLConstants.TOOL_TYPE_PROCEDURE
            }, 0);
      ActualParameters refActualParameters=new ActualParameters(this); // min=0
      Description refDescription=new Description(this); // min=0
      ExtendedAttributes refExtendedAttributes=new ExtendedAttributes(this); // min=0

      add(attrId);
      add(attrType);
      add(refActualParameters);
      add(refDescription);      
      add(refExtendedAttributes);      
   }

   public String getId () {
      return get("Id").toValue();
   }
   public void setId (String id) {
      set("Id",id);
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
   public void setTypeAPPLICATION () {
      getTypeAttribute().setValue(XPDLConstants.TOOL_TYPE_APPLICATION);
   }
   public void setTypePROCEDURE () {
      getTypeAttribute().setValue(XPDLConstants.TOOL_TYPE_PROCEDURE);      
   }
   public String getDescription() {
      return get("Description").toValue();
   }
   public void setDescription(String description) {
      set("Description",description);
   }
   public ActualParameters getActualParameters() {
      return (ActualParameters)get("ActualParameters");
   }
   public ExtendedAttributes getExtendedAttributes() {
      return (ExtendedAttributes)get("ExtendedAttributes");
   }
}
