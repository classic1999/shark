package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLAttribute;
import org.enhydra.shark.xpdl.XMLComplexElement;
import org.enhydra.shark.xpdl.XPDLConstants;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class SubFlow extends XMLComplexElement {

   public SubFlow (ImplementationTypes parent) {
      super(parent, true);
   }

   protected void fillStructure () {
      XMLAttribute attrId=new XMLAttribute(this,"Id", true); // required
      XMLAttribute attrExecution=new XMLAttribute(this,"Execution",
         false,new String[] {
            "",
            XPDLConstants.EXECUTION_ASYNCHR,
            XPDLConstants.EXECUTION_SYNCHR
         }, 0);
      ActualParameters refActualParameters=new ActualParameters(this); // min=0

      add(attrId);      
      add(attrExecution);      
      add(refActualParameters);      
   }

   public String getId () {
      return get("Id").toValue();
   }
   public void setId (String id) {
      set("Id",id);
   }
   public XMLAttribute getExecutionAttribute () {
      return (XMLAttribute)get("Execution");
   }
   public String getExecution() {
      return getExecutionAttribute().toValue();
   }
   public void setExecutionNONE() {
      getExecutionAttribute().setValue("");
   }
   public void setExecutionASYNCHR() {
      getExecutionAttribute().setValue(XPDLConstants.EXECUTION_ASYNCHR);
   }
   public void setExecutionSYNCHR() {
      getExecutionAttribute().setValue(XPDLConstants.EXECUTION_SYNCHR);
   }   
   public ActualParameters getActualParameters() {
      return (ActualParameters)get("ActualParameters");
   }
   
}
