package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLAttribute;
import org.enhydra.shark.xpdl.XMLComplexElement;
import org.enhydra.shark.xpdl.XPDLConstants;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class Deadline extends XMLComplexElement {

   public Deadline (Deadlines parent) {
      super(parent, true);
   }

   protected void fillStructure () {
      DeadlineCondition refDeadlineCondition=new DeadlineCondition(this); // min=1, max=1
      ExceptionName refExceptionName=new ExceptionName(this); // min=1, max=1
      XMLAttribute attrExecution=new XMLAttribute(this,"Execution",
         false,new String[] {
            "",
            XPDLConstants.EXECUTION_ASYNCHR,
            XPDLConstants.EXECUTION_SYNCHR
         }, 0);

      add(attrExecution);
      add(refDeadlineCondition);
      add(refExceptionName);
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
   public String getDeadlineCondition() {
      return get("DeadlineCondition").toValue();
   }
   public void setDeadlineCondition(String deadlineCondition) {
      set("DeadlineCondition",deadlineCondition);
   }
   public String getExceptionName() {
      return get("ExceptionName").toValue();
   }
   public void setExceptionName(String exceptionName) {
      set("ExceptionName",exceptionName);
   }
}
