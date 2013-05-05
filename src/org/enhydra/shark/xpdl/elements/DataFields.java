package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLCollection;
import org.enhydra.shark.xpdl.XMLElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class DataFields extends XMLCollection {

   public DataFields (Package parent) {
      super(parent, false);
   }

   public DataFields (WorkflowProcess parent) {
      super(parent, false);
   }

   public XMLElement generateNewElement() {
      return new DataField(this);
   }

   public DataField getDataField (String Id) {
      return (DataField)super.getCollectionElement(Id);
   }

}
