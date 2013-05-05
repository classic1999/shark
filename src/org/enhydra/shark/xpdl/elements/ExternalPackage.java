package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLAttribute;
import org.enhydra.shark.xpdl.XMLComplexElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class ExternalPackage extends XMLComplexElement {

   public ExternalPackage (ExternalPackages parent) {
      super(parent, true);
   }

   protected void fillStructure () {
      XMLAttribute attrHref=new XMLAttribute(this,"href", true);
      ExtendedAttributes refExtendedAttributes=new ExtendedAttributes(this); // min=0

      add(attrHref);
      add(refExtendedAttributes);
   }

   public String getHref() {
      return get("href").toValue();
   }
   public void setHref(String href) {
      set("href",href);
   }
   public ExtendedAttributes getExtendedAttributes() {
      return (ExtendedAttributes)get("ExtendedAttributes");
   }
}
