package org.enhydra.shark.xpdl.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.enhydra.shark.xpdl.XMLAttribute;
import org.enhydra.shark.xpdl.XMLCollectionElement;
import org.enhydra.shark.xpdl.XMLUtil;
import org.enhydra.shark.xpdl.XPDLConstants;

/**
 *  Represents coresponding element from XPDL schema.
 *
 *  @author Sasa Bojanic
 */
public class WorkflowProcess extends XMLCollectionElement {

   protected transient ArrayList startingActivities;
   protected transient ArrayList endingActivities;

   public WorkflowProcess (WorkflowProcesses parent) {
      super(parent, true);
   }

   protected void fillStructure () {
      XMLAttribute attrName=new XMLAttribute(this,"Name", false);
      XMLAttribute attrAccessLevel=
         new XMLAttribute(this,
               "AccessLevel",
               false,new String[] {
               "",
               XPDLConstants.ACCESS_LEVEL_PUBLIC,
               XPDLConstants.ACCESS_LEVEL_PRIVATE
        }, 0);
      ProcessHeader refProcessHeader=new ProcessHeader(this);
      RedefinableHeader refRedefinableHeader=new RedefinableHeader(this); // min=0
      FormalParameters refFormalParameters=new FormalParameters(this);
      DataFields refDataFields=new DataFields(this); // min=0
      Participants refParticipants=new Participants(this); // min=0
      Applications refApplications=new Applications(this); // min=0
      ActivitySets refActivitySets=new ActivitySets(this); // min=0
      Activities refActivities=new Activities(this); // min=0
      Transitions refTransitions=new Transitions(this); // min=0
      ExtendedAttributes refExtendedAttributes=new ExtendedAttributes(this); // min=0

      super.fillStructure();
      add(attrName);
      add(attrAccessLevel);
      add(refProcessHeader);
      add(refRedefinableHeader);
      add(refFormalParameters);
      add(refDataFields);
      add(refParticipants);
      add(refApplications);
      add(refActivitySets);
      add(refActivities);
      add(refTransitions);
      add(refExtendedAttributes);
   }

   public void initCaches () {
      super.initCaches();
      Iterator it=getActivities().toElements().iterator();
      while (it.hasNext()) {
         Activity act=(Activity)it.next();
         ArrayList trsI=act.getIncomingTransitions();
         ArrayList trsNEO=act.getNonExceptionalOutgoingTransitions();
         // the activity is starting one if it has no input transitions ...
         if (trsI.size()==0) {
            startingActivities.add(act);
            // or there is a one input transition, but it is a selfreference
         } else if (trsI.size()==1) {
            Transition t=(Transition)trsI.get(0);
            if (t.getFrom().equals(t.getTo())) {
               startingActivities.add(act);
            }
         }
         if (trsNEO.size()==0) {
            endingActivities.add(act);
         } else if (trsNEO.size()==1) {
            Transition t=(Transition)trsNEO.get(0);
            if (t.getFrom().equals(t.getTo())) {
               endingActivities.add(act);
            }
         }
      }
   }

   public void clearCaches () {
      startingActivities=new ArrayList();
      endingActivities=new ArrayList();
      super.clearCaches();
   }

   public ArrayList getStartingActivities () {
      if (!isReadOnly) {
         throw new RuntimeException("This method can be used only in read-only mode!");
      }
      return startingActivities;
   }

   public ArrayList getEndingActivities () {
      if (!isReadOnly) {
         throw new RuntimeException("This method can be used only in read-only mode!");
      }
      return endingActivities;
   }

   /**
    * Returns a list of all WorkflowProcess and Package DataFields, as well as
    * all WorkflowProcess FormalParameters.
    */
   public Map getAllVariables () {
      Map m=new HashMap();
      Iterator it=getDataFields().toElements().iterator();
      while (it.hasNext()) {
         DataField df=(DataField)it.next();
         m.put(df.getId(),df);
      }
      it=XMLUtil.getPackage(this).getDataFields().toElements().iterator();
      while (it.hasNext()) {
         DataField df=(DataField)it.next();
         if (!m.containsKey(df.getId())) {
            m.put(df.getId(),df);
         }
      }
      it=getFormalParameters().toElements().iterator();
      while (it.hasNext()) {
         FormalParameter fp=(FormalParameter)it.next();
         if (!m.containsKey(fp.getId())) {
            m.put(fp.getId(),fp);
         }
      }
      return m;
   }

   public Application getApplication (String Id) {
      return getApplications().getApplication(Id);
   }

   public Participant getParticipant(String Id) {
      return getParticipants().getParticipant(Id);
   }

   public DataField getDataField (String Id) {
      return getDataFields().getDataField(Id);
   }

   public FormalParameter getFormalParameter (String Id) {
      return getFormalParameters().getFormalParameter(Id);
   }

   public ActivitySet getActivitySet (String Id) {
      return getActivitySets().getActivitySet(Id);
   }

   public Activity getActivity (String Id) {
      return getActivities().getActivity(Id);
   }

   public Transition getTransition (String Id) {
      return getTransitions().getTransition(Id);
   }

   public String getName() {
      return get("Name").toValue();
   }
   public void setName(String name) {
      set("Name",name);
   }
   public XMLAttribute getAccessLevelAttribute() {
      return (XMLAttribute)get("AccessLevel");
   }
   public String getAccessLevel() {
      return getAccessLevelAttribute().toValue();
   }
   public void setAccessLevelNONE() {
      getAccessLevelAttribute().setValue("");
   }
   public void setAccessLevelPUBLIC() {
      getAccessLevelAttribute().setValue(XPDLConstants.ACCESS_LEVEL_PUBLIC);
   }
   public void setAccessLevelPRIVATE() {
      getAccessLevelAttribute().setValue(XPDLConstants.ACCESS_LEVEL_PRIVATE);
   }
   public Applications getApplications() {
      return (Applications)get("Applications");
   }
   public DataFields getDataFields() {
      return (DataFields)get("DataFields");
   }
   public ExtendedAttributes getExtendedAttributes() {
      return (ExtendedAttributes)get("ExtendedAttributes");
   }
   public ProcessHeader getProcessHeader() {
      return (ProcessHeader)get("ProcessHeader");
   }
   public Participants getParticipants() {
      return (Participants)get("Participants");
   }
   public RedefinableHeader getRedefinableHeader() {
      return (RedefinableHeader)get("RedefinableHeader");
   }
   public Activities getActivities() {
      return (Activities)get("Activities");
   }
   public Transitions getTransitions() {
      return (Transitions)get("Transitions");
   }
   public ActivitySets getActivitySets() {
      return (ActivitySets)get("ActivitySets");
   }
   public FormalParameters getFormalParameters() {
      return (FormalParameters)get("FormalParameters");
   }

}
