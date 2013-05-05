package org.enhydra.shark.xpdl.elements;

import java.util.ArrayList;

import org.enhydra.shark.xpdl.XMLComplexChoice;
import org.enhydra.shark.xpdl.XMLElement;
import org.enhydra.shark.xpdl.XMLEmptyChoiceElement;

/**
 *  Represents the choice for start and end modes for activity.
 * 
 *  @author Sasa Bojanic
 */
public class StartFinishModes extends XMLComplexChoice {

   public StartFinishModes (StartMode parent) {
      super(parent,"Mode", true);
      fillChoices();
   }
   public StartFinishModes (FinishMode parent) {
      super(parent,"Mode", true);
      fillChoices();
   }
   
   public void fillChoices () {
      choices=new ArrayList();
      choices.add(new XMLEmptyChoiceElement(this)); 
      choices.add(new Automatic(this)); 
      choices.add(new Manual(this));
      choosen=(XMLElement)choices.get(0);
   }

   public XMLEmptyChoiceElement getEmptyChoiceElement () {
      return (XMLEmptyChoiceElement)choices.get(0);
   }

   public void setEmptyChoiceElement () {
      choosen=(XMLEmptyChoiceElement)choices.get(0);
   }
   
   public Automatic getAutomatic () {
      return (Automatic)choices.get(1);
   }

   public void setAutomatic () {
      choosen=(Automatic)choices.get(1);
   }

   public Manual getManual () {
      return (Manual)choices.get(2);
   }

   public void setManual () {
      choosen=(Manual)choices.get(2);
   }

}

