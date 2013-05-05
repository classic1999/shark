package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLAttribute;
import org.enhydra.shark.xpdl.XMLCollectionElement;

/**
 *  Represents coresponding element from XPDL schema.
 *
 *  @author Sasa Bojanic
 */
public class Application extends XMLCollectionElement {

   public Application(Applications aps) {
      super(aps, true);
   }

   protected void fillStructure() {
      Description refDescription = new Description(this); // min=0
      // can be FormalParameters or ExternalReference
      // if fp->must be defined,if er->min=0
      ApplicationTypes refChoice = new ApplicationTypes(this);
      ExtendedAttributes refExtendedAttributes = new ExtendedAttributes(this); // min=0
      XMLAttribute attrName = new XMLAttribute(this, "Name", false);

      super.fillStructure();
      //attrName.setRequired(true);
      add(attrName);
      add(refDescription);
      add(refChoice);
      add(refExtendedAttributes);
   }

   public String getName() {
      return get("Name").toValue();
   }
   public void setName(String name) {
      set("Name",name);
   }
   public String getDescription() {
      return get("Description").toValue();
   }
   public void setDescription(String description) {
      set("Description",description);
   }
   public ApplicationTypes getApplicationTypes() {
      return (ApplicationTypes)get("Choice");
   }
   public ExtendedAttributes getExtendedAttributes() {
      return (ExtendedAttributes)get("ExtendedAttributes");
   }
}