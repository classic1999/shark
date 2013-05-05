package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLAttribute;
import org.enhydra.shark.xpdl.XMLComplexElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class BlockActivity extends XMLComplexElement {

   public BlockActivity (ActivityTypes parent) {
      super(parent, true);
   }

   protected void fillStructure () {
      XMLAttribute attrBlockId=new XMLAttribute(this,"BlockId", true); // required

      add(attrBlockId);      
   }

   public String getBlockId() {
      return get("BlockId").toValue();
   }
   public void setBlockId(String blockId) {
      set("BlockId",blockId);
   }
}
