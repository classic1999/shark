package org.enhydra.shark.xpdl.elements;

import java.util.HashMap;
import java.util.Map;

import org.enhydra.shark.xpdl.XMLAttribute;
import org.enhydra.shark.xpdl.XMLCollectionElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class Transition extends XMLCollectionElement {

   protected transient Activity toActivity;
   protected transient Activity fromActivity;
   
   public Transition (Transitions parent) {
      super(parent, true);
   }

   protected void fillStructure () {
      XMLAttribute attrFrom=new XMLAttribute(this,"From", true); //required
      XMLAttribute attrTo=new XMLAttribute(this,"To", true); //required
      XMLAttribute attrName=new XMLAttribute(this,"Name", false);
      Condition refCondition=new Condition(this); // min==0
      Description refDescription=new Description(this); // min=0
      ExtendedAttributes refExtendedAttributes=new ExtendedAttributes(this); // min=0


      super.fillStructure();
      add(attrName);
      add(attrFrom);
      add(attrTo);
      add(refCondition);
      add(refDescription);
      add(refExtendedAttributes);
   }

   public void initCaches () {
      super.initCaches();
      Activities acts;
      if (getParent().getParent() instanceof WorkflowProcess) {
         acts=((WorkflowProcess)getParent().getParent()).getActivities();
      } else {
         acts=((ActivitySet)getParent().getParent()).getActivities();
      }
      toActivity=acts.getActivity(getTo());
      fromActivity=acts.getActivity(getFrom());      
   }
   
   public void clearCaches () {
      toActivity=null;
      fromActivity=null;
      super.clearCaches();
   }
   
   public Activity getToActivity () {
      if (!isReadOnly) {
         throw new RuntimeException("This method can be used only in read-only mode!");
      }
      return toActivity;
   }
      
   public Activity getFromActivity () {
      if (!isReadOnly) {
         throw new RuntimeException("This method can be used only in read-only mode!");
      }
      return fromActivity;
   }

   public String getFrom() {
      return get("From").toValue();
   }
   public void setFrom(String from) {
      set("From",from);
   }
   public String getTo() {
      return get("To").toValue();
   }
   public void setTo(String to) {
      set("To",to);
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
   public Condition getCondition() {
      return (Condition)get("Condition");
   }
   public ExtendedAttributes getExtendedAttributes() {
      return (ExtendedAttributes)get("ExtendedAttributes");
   }
}
