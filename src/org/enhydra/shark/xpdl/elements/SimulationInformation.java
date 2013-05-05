package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLAttribute;
import org.enhydra.shark.xpdl.XMLComplexElement;
import org.enhydra.shark.xpdl.XPDLConstants;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class SimulationInformation extends XMLComplexElement {

   public SimulationInformation (Activity parent) {
      super(parent, false);
   }

   protected void fillStructure () {
      XMLAttribute attrInstantiation=new XMLAttribute(this,"Instantiation",
            false,new String[] {
               "",
               XPDLConstants.INSTANTIATION_ONCE,
               XPDLConstants.INSTANTIATION_MULTIPLE
            }, 0);
      Cost refCost=new Cost(this);
      TimeEstimation refTimeEstimation=new TimeEstimation(this);

      add(attrInstantiation);
      add(refCost);
      add(refTimeEstimation);
   }

   public XMLAttribute getInstantiationAttribute() {
      return (XMLAttribute)get("Instantiation");
   }
   public String getInstantiation() {
      return getInstantiationAttribute().toValue();
   }
   public void setInstantiationNONE() {
      getInstantiationAttribute().setValue("");
   }
   public void setInstantiationONCE() {
      getInstantiationAttribute().setValue(XPDLConstants.INSTANTIATION_ONCE);
   }
   public void setInstantiationMULTIPLE() {
      getInstantiationAttribute().setValue(XPDLConstants.INSTANTIATION_MULTIPLE);
   }
   public String getCost() {
      return get("Cost").toValue();
   }
   public void setCost(String cost) {
      set("Cost",cost);
   }
   public TimeEstimation getTimeEstimation() {
      return (TimeEstimation)get("TimeEstimation");
   }
}
