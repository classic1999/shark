package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLCollection;
import org.enhydra.shark.xpdl.XMLElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class FormalParameters extends XMLCollection {

   public FormalParameters (ApplicationTypes parent) {
      super(parent, false);
   }

   public FormalParameters (WorkflowProcess parent) {
      super(parent, false);
   }

   public XMLElement generateNewElement() {
      return new FormalParameter(this);
   }

   public FormalParameter getFormalParameter (String Id) {
      return (FormalParameter)super.getCollectionElement(Id);
   }

}
