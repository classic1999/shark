package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLCollection;
import org.enhydra.shark.xpdl.XMLElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class Applications extends XMLCollection {

   public Applications (Package parent) {
      super(parent, false);
   }

   public Applications (WorkflowProcess parent) {
      super(parent, false);
   }

   public XMLElement generateNewElement() {
      return new Application(this);
   }

   public Application getApplication (String Id) {
      return (Application)super.getCollectionElement(Id);
   }

}
