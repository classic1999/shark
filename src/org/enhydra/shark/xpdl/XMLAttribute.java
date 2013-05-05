package org.enhydra.shark.xpdl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 *  Represents attribute element from XML schema.
 * 
 *  @author Sasa Bojanic
 */
public class XMLAttribute extends XMLElement {

   /** The possible choices. */
   protected ArrayList choices;

   public XMLAttribute(XMLElement parent, String name, boolean isRequired) {
      super(parent, name, isRequired);
   }

   public XMLAttribute(XMLElement parent, String name, boolean isRequired, String[] choices, int choosenIndex) {
      super(parent, name, isRequired);
      this.choices = new ArrayList(Arrays.asList(choices));
      this.value = choices[choosenIndex];
   }

   public void setValue (String v) {
      if (choices != null) {
         if (!choices.contains(v)) {
            throw new RuntimeException("Incorrect value! Possible values are: " + choices);
         }
      }
      super.setValue(v);
   }

   /**
    * The possible String choices.
    * 
    * @return the possible choices for this element.
    */
   public ArrayList getChoices() {
      return choices;
   }

   public Object clone() {
      XMLAttribute d = (XMLAttribute) super.clone();

      if (choices != null) {
         d.choices = new ArrayList();
         Iterator it=choices.iterator();
         while (it.hasNext()) {
            d.choices.add(new String(it.next().toString()));
         }         
      }
      return d;
   }

   public boolean equals (Object e) {
      boolean equals=super.equals(e);
      if (equals) {
         XMLAttribute el=(XMLAttribute)e;
         return (this.choices == null ? el.choices == null : this.choices.equals(el.choices));
      }
      return false;
   }
   

}

