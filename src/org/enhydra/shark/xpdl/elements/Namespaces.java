package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLCollection;
import org.enhydra.shark.xpdl.XMLElement;

/**
 *  Helper class to properly write namespaces in XML.
 * 
 *  @author Sasa Bojanic
 */
public class Namespaces extends XMLCollection {

   public Namespaces (Package parent) {
      super(parent, true);
   }


   public XMLElement generateNewElement() {
      return new Namespace(this);
   }

}