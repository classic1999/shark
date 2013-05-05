package org.enhydra.shark.xpdl.elements;

import java.util.ArrayList;
import java.util.Iterator;

import org.enhydra.shark.xpdl.XMLAttribute;
import org.enhydra.shark.xpdl.XMLCollectionElement;
import org.enhydra.shark.xpdl.XMLElement;
import org.enhydra.shark.xpdl.XMLUtil;
import org.enhydra.shark.xpdl.XPDLConstants;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class Activity extends XMLCollectionElement {

   protected transient ArrayList outgoingTransitions;
   protected transient ArrayList incomingTransitions;
   protected transient ArrayList exceptionalOutgoingTransitions;
   protected transient ArrayList nonExceptionalOutgoingTransitions;
   
   public Activity (Activities acts) {
      super(acts, true);
   }

   protected void fillStructure () {
      XMLAttribute attrName=new XMLAttribute(this,"Name", false);
      Description refDescription=new Description(this); // min=0
      Limit refLimit=new Limit(this); // min=0
      // can be Route, BlockActivity or Implementation
      ActivityTypes refType=new ActivityTypes(this);
      Performer refPerformer=new Performer(this);// min=0
      StartMode refStartMode=new StartMode(this); // min=0
      FinishMode refFinishMode=new FinishMode(this); // min=0
      Priority refPriority=new Priority(this); // min=0
      // we use Deadlines instead of Deadline
      Deadlines refDeadlines=new Deadlines(this); // min=0
      SimulationInformation refSimulationInformation=new SimulationInformation(this); // min=0
      Icon refIcon=new Icon(this); // min=0
      Documentation refDocumentation=new Documentation(this); // min=0
      TransitionRestrictions refTransitionRestrictions=new TransitionRestrictions(this); // min=0
      ExtendedAttributes refExtendedAttributes=new ExtendedAttributes(this); // min=0

      super.fillStructure();
      add(attrName);
      add(refDescription);
      add(refLimit);
      add(refType);
      add(refPerformer);
      add(refStartMode);
      add(refFinishMode);
      add(refPriority);
      add(refDeadlines);
      add(refSimulationInformation);
      add(refIcon);
      add(refDocumentation);
      add(refTransitionRestrictions);
      add(refExtendedAttributes);
   }

   public void initCaches () {
      super.initCaches();
      Transitions ts;
      if (getParent().getParent() instanceof WorkflowProcess) {
         ts=((WorkflowProcess)getParent().getParent()).getTransitions();
      } else {
         ts=((ActivitySet)getParent().getParent()).getTransitions();
      }
      TransitionRestrictions trs=getTransitionRestrictions();
      ArrayList trefs=null;
      if (trs.size()>0) {
         trefs=((TransitionRestriction)trs.get(0)).getSplit().getTransitionRefs().toElements();
      } else {
         trefs=new ArrayList();
      }
      
      Iterator it=trefs.iterator();
      while (it.hasNext()) {
         TransitionRef tref=(TransitionRef)it.next();
         Transition t=ts.getTransition(tref.getId());
         outgoingTransitions.add(t);
         putTransitionInTheRightList(t);
      }      
      it=ts.toElements().iterator();
      while (it.hasNext()) {
         Transition t=(Transition)it.next();
         if (!outgoingTransitions.contains(t) && t.getFrom().equals(getId())) {
            outgoingTransitions.add(t);
            putTransitionInTheRightList(t);
         }
         if (t.getTo().equals(getId())) {
            incomingTransitions.add(t);
         }
      }
   }
   
   public void clearCaches () {
      clearInternalCaches();
      super.clearCaches();
   }
   
   protected void clearInternalCaches () {
      outgoingTransitions=new ArrayList();
      incomingTransitions=new ArrayList();
      exceptionalOutgoingTransitions=new ArrayList();
      nonExceptionalOutgoingTransitions=new ArrayList();      
   }

   protected void putTransitionInTheRightList (Transition t) {
      Condition condition = t.getCondition();
      String condType=condition.getType();
      if (condType.equals(XPDLConstants.CONDITION_TYPE_EXCEPTION) ||
          condType.equals(XPDLConstants.CONDITION_TYPE_DEFAULTEXCEPTION)) {
         exceptionalOutgoingTransitions.add(t);
      } else {
         nonExceptionalOutgoingTransitions.add(t);
      }      
   }

   public ArrayList getOutgoingTransitions () {
      if (!isReadOnly) {
         throw new RuntimeException("This method can be used only in read-only mode!");
      }
      return outgoingTransitions;
   }
   
   public ArrayList getIncomingTransitions () {
      if (!isReadOnly) {
         throw new RuntimeException("This method can be used only in read-only mode!");
      }
      return incomingTransitions;
   }

   public ArrayList getNonExceptionalOutgoingTransitions () {
      if (!isReadOnly) {
         throw new RuntimeException("This method can be used only in read-only mode!");
      }
      return nonExceptionalOutgoingTransitions;
   }

   public ArrayList getExceptionalOutgoingTransitions () {
      if (!isReadOnly) {
         throw new RuntimeException("This method can be used only in read-only mode!");
      }
      return exceptionalOutgoingTransitions;
   }

   public boolean isAndTypeSplit () {
      return XMLUtil.isANDTypeSplitOrJoin(this,0);
   }
   
   public boolean isAndTypeJoin () {
      return XMLUtil.isANDTypeSplitOrJoin(this,1);
   }

   public int getActivityStartMode () {
      return XMLUtil.getStartMode(this);
   }
   
   public int getActivityFinishMode () {
      return XMLUtil.getFinishMode(this);
   }
   
   public int getActivityType () {
      XMLElement ch=getActivityTypes().getChoosen();
      if (ch instanceof Route) {
         return XPDLConstants.ACTIVITY_TYPE_ROUTE;
      } else if (ch instanceof Implementation) {
         ch=((Implementation)ch).getImplementationTypes().getChoosen();
         if (ch instanceof Tools) {
            return XPDLConstants.ACTIVITY_TYPE_TOOL;
         } else if (ch instanceof SubFlow){
            return XPDLConstants.ACTIVITY_TYPE_SUBFLOW;
         } else {
            return XPDLConstants.ACTIVITY_TYPE_NO;
         }
      } else {
         return XPDLConstants.ACTIVITY_TYPE_BLOCK;
      }

   }
   
   public boolean isSubflowSynchronous () {
      if (getActivityType()!=XPDLConstants.ACTIVITY_TYPE_SUBFLOW) {
         throw new RuntimeException("The activity type is not SubFlow!");         
      }
      return XMLUtil.isSubflowSynchronous(this);
   }
   
   public String getName() {
      return get("Name").toValue();
   }
   public void setName(String name) {
      set("Name",name);
   }
   public Deadlines getDeadlines() {
      return (Deadlines)get("Deadlines");
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
   public ExtendedAttributes getExtendedAttributes() {
      return (ExtendedAttributes)get("ExtendedAttributes");
   }
   public FinishMode getFinishMode() {
      return (FinishMode)get("FinishMode");
   }
   public String getIcon() {
      return get("Icon").toValue();
   }
   public void setIcon(String icon) {
      set("Icon",icon);
   }
   public String getLimit() {
      return get("Limit").toValue();
   }
   public void setLimit(String limit) {
      set("Limit",limit);
   }
   public String getPerformer() {
      return get("Performer").toValue();
   }
   public void setPerformer(String performer) {
      set("Performer",performer);
   }
   public String getPriority() {
      return get("Priority").toValue();
   }
   public void setPriority(String priority) {
      set("Priority",priority);
   }
   public SimulationInformation getSimulationInformation() {
      return (SimulationInformation)get("SimulationInformation");
   }
   public StartMode getStartMode() {
      return (StartMode)get("StartMode");
   }
   public TransitionRestrictions getTransitionRestrictions() {
      return (TransitionRestrictions)get("TransitionRestrictions");
   }
   public ActivityTypes getActivityTypes() {
      return (ActivityTypes)get("Type");
   }

}

