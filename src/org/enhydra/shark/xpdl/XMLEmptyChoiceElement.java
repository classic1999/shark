package org.enhydra.shark.xpdl;

import org.enhydra.shark.xpdl.XMLComplexElement;

/**
 * Helper class to allow an empty choice that will not write 
 * anything to XML.
 * 
 *  @author Sasa Bojanic
 */
public final class XMLEmptyChoiceElement extends XMLComplexElement {

   public XMLEmptyChoiceElement (XMLElement parent) {
      super(parent, false);
   }

   protected void fillStructure () {}
}
