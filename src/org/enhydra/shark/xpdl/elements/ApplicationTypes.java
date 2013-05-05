package org.enhydra.shark.xpdl.elements;

import java.util.ArrayList;

import org.enhydra.shark.xpdl.XMLComplexChoice;
import org.enhydra.shark.xpdl.XMLElement;

/**
 *  Represents choice of application types from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class ApplicationTypes extends XMLComplexChoice {

   public ApplicationTypes (Application parent) {
      super(parent,"Choice", true);
   }

   protected void fillChoices () {
      choices=new ArrayList();
      choices.add(new FormalParameters(this)); 
      choices.add(new ExternalReference(this, false)); 
      choosen=(XMLElement)choices.get(0);      
   }
   
   public FormalParameters getFormalParameters () {
      return (FormalParameters)choices.get(0);
   }

   public void setFormalParameters () {
      choosen=(FormalParameters)choices.get(0);
   }
   
   public ExternalReference getExternalReference () {
      return (ExternalReference)choices.get(1);
   }

   public void setExternalReference () {
      choosen=(ExternalReference)choices.get(1);
   }

}

