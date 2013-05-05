package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLComplexElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class PackageHeader extends XMLComplexElement {

   public PackageHeader (Package parent) {
      super(parent, true);
   }

   protected void fillStructure () {
      XPDLVersion refXPDLVersion=new XPDLVersion(this);
      Vendor refVendor=new Vendor(this);
      Created refCreated=new Created(this);
      Description refDescription=new Description(this); // min=0
      Documentation refDocumentation=new Documentation(this); // min=0
      PriorityUnit refPriorityUnit=new PriorityUnit(this); // min=0
      CostUnit refCostUnit=new CostUnit(this); // min=0

      add(refXPDLVersion);
      add(refVendor);
      add(refCreated);
      add(refDescription);
      add(refDocumentation);
      add(refPriorityUnit);
      add(refCostUnit);
   }

   public String getCostUnit() {
      return get("CostUnit").toValue();
   }
   public void setCostUnit(String costUnit) {
      set("CostUnit",costUnit);
   }
   public String getCreated() {
      return get("Created").toValue();
   }
   public void setCreated(String created) {
      set("Created",created);
   }
   public String getDescription() {
      return get("Description").toValue();
   }
   public void setDescription(String description) {
      set("Description",description);
   }
   public String getDocumentation() {
      return get("Documentation").toValue();
   }
   public void setDocumentation(String documentation) {
      set("Documentation",documentation);
   }
   public String getPriorityUnit() {
      return get("PriorityUnit").toValue();
   }
   public void setPriorityUnit(String priorityUnit) {
      set("PriorityUnit",priorityUnit);
   }
   public String getVendor() {
      return get("Vendor").toValue();
   }
   public void setVendor(String vendor) {
      set("Vendor",vendor);
   }
   public String getXPDLVersion() {
      return get("XPDLVersion").toValue();
   }
   public void setXPDLVersion(String xpdlVersion) {
      set("XPDLVersion",xpdlVersion);
   }
}
