package org.enhydra.shark.xpdl.elements;

import org.enhydra.shark.xpdl.XMLComplexElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class TransitionRestriction extends XMLComplexElement {
   
   public TransitionRestriction (TransitionRestrictions parent) {
      super(parent, false);
   }

   protected void fillStructure () {
      Join refJoin=new Join(this); // min=0
      Split refSplit=new Split(this); // min=0

      add(refJoin);
      add(refSplit);
   }

   public Join getJoin() {
      return (Join)get("Join");
   }
   public Split getSplit() {
      return (Split)get("Split");
   }
}
