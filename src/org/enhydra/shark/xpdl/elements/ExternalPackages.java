package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLCollection;
import org.enhydra.shark.xpdl.XMLElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class ExternalPackages extends XMLCollection {

   public ExternalPackages (Package parent) {
      super(parent, false);
   }

   public XMLElement generateNewElement() {
      return new ExternalPackage(this);
   }

}
