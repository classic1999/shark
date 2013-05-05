package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLComplexElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class TimeEstimation extends XMLComplexElement {

   public TimeEstimation (SimulationInformation parent) {
      super(parent, true);
   }

   public TimeEstimation (ProcessHeader parent) {
      super(parent, false);
   }

   protected void fillStructure () {
      WaitingTime refWaitingTime=new WaitingTime(this); // min=0
      WorkingTime refWorkingTime=new WorkingTime(this); // min=0
      Duration refDuration=new Duration(this); // min=0

      add(refWaitingTime);
      add(refWorkingTime);
      add(refDuration);      
   }

   public String getDuration() {
      return get("Duration").toValue();
   }
   public void setDuration(String duration) {
      set("Duration",duration);
   }
   public String getWaitingTime() {
      return get("WaitingTime").toValue();
   }
   public void setWaitingTime(String waitingTime) {
      set("WaitingTime",waitingTime);
   }
   public String getWorkingTime() {
      return get("WorkingTime").toValue();
   }
   public void setWorkingTime(String workingTime) {
      set("WorkingTime",workingTime);
   }
}
