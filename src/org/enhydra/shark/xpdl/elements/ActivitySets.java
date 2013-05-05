package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLCollection;
import org.enhydra.shark.xpdl.XMLElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class ActivitySets extends XMLCollection {

   public ActivitySets (WorkflowProcess parent) {
      super (parent, false);
   }

   public XMLElement generateNewElement() {
      return new ActivitySet(this);
   }

   public ActivitySet getActivitySet (String Id) {
      return (ActivitySet)super.getCollectionElement(Id);
   }

}
