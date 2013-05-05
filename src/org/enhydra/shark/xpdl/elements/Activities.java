package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLCollection;
import org.enhydra.shark.xpdl.XMLElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class Activities extends XMLCollection {

   public Activities (WorkflowProcess parent) {
      super (parent, false);
   }

   public Activities (ActivitySet parent) {
      super (parent, false);
   }

   public XMLElement generateNewElement() {
      return new Activity(this);
   }

   public Activity getActivity (String Id) {
      return (Activity)super.getCollectionElement(Id);
   }
   
}
