package org.enhydra.shark.xpdl.elements;

import java.util.ArrayList;

import org.enhydra.shark.xpdl.XMLComplexChoice;
import org.enhydra.shark.xpdl.XMLElement;

/**
 *  Represents the type of implementations for Activity from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class ImplementationTypes extends XMLComplexChoice {

   public ImplementationTypes (Implementation parent) {
      super(parent,"Type", true);
   }

   protected void fillChoices () {
      choices=new ArrayList();
      choices.add(new No(this)); 
      // we use tools instead of tool
      choices.add(new Tools(this)); 
      choices.add(new SubFlow(this));
      choosen=(XMLElement)choices.get(0);      
   }
   
   public No getNo () {
      return (No)choices.get(0);
   }

   public void setNo () {
      choosen=(No)choices.get(0);
   }
   
   public Tools getTools () {
      return (Tools)choices.get(1);
   }

   public void setTools () {
      choosen=(Tools)choices.get(1);
   }

   public SubFlow getSubFlow () {
      return (SubFlow)choices.get(2);
   }

   public void setSubFlow () {
      choosen=(SubFlow)choices.get(2);
   }

}

