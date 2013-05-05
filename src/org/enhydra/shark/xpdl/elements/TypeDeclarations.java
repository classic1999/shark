package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLCollection;
import org.enhydra.shark.xpdl.XMLElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class TypeDeclarations extends XMLCollection {

   // min=0, max=unbounded
   public TypeDeclarations (Package p) {
      super(p, false);
   }

   public XMLElement generateNewElement() {
      return new TypeDeclaration(this);
   }

   public TypeDeclaration getTypeDeclaration (String Id) {
      return (TypeDeclaration)getCollectionElement(Id);
   }

}
