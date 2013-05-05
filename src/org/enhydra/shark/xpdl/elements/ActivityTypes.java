package org.enhydra.shark.xpdl.elements;

import java.util.ArrayList;

import org.enhydra.shark.xpdl.XMLComplexChoice;
import org.enhydra.shark.xpdl.XMLElement;

/**
 *  Represents Activity types from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class ActivityTypes extends XMLComplexChoice {

   public ActivityTypes (Activity parent) {
      super(parent,"Type", true);
   }

   protected void fillChoices () {
      choices=new ArrayList();
      choices.add(new Route(this)); 
      choices.add(new Implementation(this)); 
      choices.add(new BlockActivity(this));
      choosen=(XMLElement)choices.get(0);      
   }
   
   public Route getRoute () {
      return (Route)choices.get(0);
   }

   public void setRoute () {
      choosen=(Route)choices.get(0);
   }
   
   public Implementation getImplementation () {
      return (Implementation)choices.get(1);
   }

   public void setImplementation () {
      choosen=(Implementation)choices.get(1);
   }

   public BlockActivity getBlockActivity () {
      return (BlockActivity)choices.get(2);
   }

   public void setBlockActivity () {
      choosen=(BlockActivity)choices.get(2);
   }

}

