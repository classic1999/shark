package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLAttribute;
import org.enhydra.shark.xpdl.XMLComplexElement;

/**
 *  Helper class to properly write namespaces in XML.
 * 
 *  @author Sasa Bojanic
 */
public class Namespace extends XMLComplexElement {

   public Namespace (Namespaces parent) {
      super(parent, true);
   }

   protected void fillStructure () {
      XMLAttribute attrName=new XMLAttribute(this,"Name", true); // required
      XMLAttribute attrLocation=new XMLAttribute(this,"location", true); //required

      add(attrName);
      add(attrLocation);
   }

   public String getName() {
      return get("Name").toValue();
   }
   public void setName(String name) {
      set("Name",name);
   }
   public String getLocation() {
      return get("location").toValue();
   }
   public void setLocation(String location) {
      set("location",location);
   }
   
}
