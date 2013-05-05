package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLCollection;
import org.enhydra.shark.xpdl.XMLElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class WorkflowProcesses extends XMLCollection {

   // min=0, max=unbounded
   public WorkflowProcesses(Package parent) {
      super(parent, false);
   }

   public XMLElement generateNewElement() {
      return new WorkflowProcess(this);
   }

   public WorkflowProcess getWorkflowProcess(String Id) {
      return (WorkflowProcess) super.getCollectionElement(Id);
   }

}