package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLAttribute;
import org.enhydra.shark.xpdl.XMLComplexElement;
import org.enhydra.shark.xpdl.XPDLConstants;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class ConformanceClass extends XMLComplexElement {
   
   public ConformanceClass (Package parent) {
      super(parent, false);
   }

   protected void fillStructure () {
      XMLAttribute attrGraphConformance=new XMLAttribute(this,"GraphConformance",
            false,new String[] {
               "",
               XPDLConstants.GRAPH_CONFORMANCE_FULL_BLOCKED,
               XPDLConstants.GRAPH_CONFORMANCE_LOOP_BLOCKED,
               XPDLConstants.GRAPH_CONFORMANCE_NON_BLOCKED
            }, 0);

      add(attrGraphConformance);
   }

   public XMLAttribute getGraphConformanceAttribute() {
      return (XMLAttribute)get("GraphConformance");
   }   
   public String getGraphConformance () {
      return getGraphConformanceAttribute().toValue();
   }
   public void setGraphConformanceNONE () {
      getGraphConformanceAttribute().setValue("");      
   }
   public void setGraphConformanceFULL_BLOCKED () {
      getGraphConformanceAttribute().setValue(XPDLConstants.GRAPH_CONFORMANCE_FULL_BLOCKED);
   }
   public void setGraphConformanceLOOP_BLOCKED () {
      getGraphConformanceAttribute().setValue(XPDLConstants.GRAPH_CONFORMANCE_LOOP_BLOCKED);      
   }
   public void setGraphConformanceNON_BLOCKED () {
      getGraphConformanceAttribute().setValue(XPDLConstants.GRAPH_CONFORMANCE_NON_BLOCKED);      
   }
   
}
