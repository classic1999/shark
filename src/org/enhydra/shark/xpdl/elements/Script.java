package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLAttribute;
import org.enhydra.shark.xpdl.XMLComplexElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class Script extends XMLComplexElement {

   public Script (Package parent) {
      super(parent, false);
   }

   protected void fillStructure () {
      XMLAttribute attrType=new XMLAttribute(this,"Type", true); // required
      XMLAttribute attrVersion=new XMLAttribute(this,"Version", false); 
      XMLAttribute attrGrammar=new XMLAttribute(this,"Grammar", false); 

      add(attrType);
      add(attrVersion);
      add(attrGrammar);
   }

   public String getGrammar() {
      return get("Grammar").toValue();
   }
   public void setGrammar(String grammar) {
      set("Grammar",grammar);
   }
   public String getType() {
      return get("Type").toValue();
   }
   public void setType(String type) {
      set("Type",type);
   }
   public String getVersion() {
      return get("Version").toValue();
   }
   public void setVersion(String version) {
      set("Version",version);
   }
}
