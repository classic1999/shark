package org.enhydra.shark.xpdl.elements;

import java.util.ArrayList;
import java.util.Iterator;

import org.enhydra.shark.xpdl.XMLCollectionElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class ActivitySet extends XMLCollectionElement {

   protected transient ArrayList startingActivities;
   protected transient ArrayList endingActivities;
   
   public ActivitySet (ActivitySets parent) {
      super (parent, true);
   }

   protected void fillStructure () {
      Activities refActivities=new Activities(this);
      Transitions refTransitions=new Transitions(this);

      super.fillStructure();
      add(refActivities);      
      add(refTransitions);
      
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

   public Activity getActivity (String Id) {
      return getActivities().getActivity(Id);
   }

   public Transition getTransition (String Id) {
      return getTransitions().getTransition(Id);
   }
   
   public Activities getActivities() {
      return (Activities)get("Activities");
   }
   public Transitions getTransitions() {
      return (Transitions)get("Transitions");
   }
}
