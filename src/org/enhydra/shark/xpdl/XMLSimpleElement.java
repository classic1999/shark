package org.enhydra.shark.xpdl;

/**
 *  Class that represents simple element from XML schema.
 * 
 *  @author Sasa Bojanic
 */
public abstract class XMLSimpleElement extends XMLElement {

   public XMLSimpleElement (XMLElement parent, boolean isRequired) {
      super(parent, isRequired);
   }

}
