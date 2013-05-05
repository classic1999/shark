package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLAttribute;
import org.enhydra.shark.xpdl.XMLComplexElement;
import org.enhydra.shark.xpdl.XMLElement;


/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class ExternalReference extends XMLComplexElement {

   public ExternalReference (XMLElement parent, boolean isRequired) {
      super(parent, isRequired);
      //isRequired=true;
   }

   protected void fillStructure () {
      XMLAttribute attrXref=new XMLAttribute(this,"xref", false); // optional
      XMLAttribute attrLocation=new XMLAttribute(this,"location", true); // required
      XMLAttribute attrNamespace=new XMLAttribute(this,"namespace", false); // optional

      add(attrXref);
      add(attrLocation);
      add(attrNamespace);
   }

   public String getLocation() {
      return get("location").toValue();
   }
   public void setLocation(String location) {
      set("location",location);
   }
   public String getNamespace() {
      return get("namespace").toValue();
   }
   public void setNamespace(String namespace) {
      set("namespace",namespace);
   }
   public String getXref() {
      return get("xref").toValue();
   }
   public void setXref(String xref) {
      set("xref",xref);
   }
}
