package org.enhydra.shark.xpdl;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *  Class that represents choice of complex elements from XML schema.
 *
 *  @author Sasa Bojanic
 */
public abstract class XMLComplexChoice extends XMLElement {

   protected ArrayList choices;

   protected XMLElement choosen;

   protected transient boolean cachesInitialized=false;

   public XMLComplexChoice(XMLComplexElement parent, String name, boolean isRequired) {
      super(parent, name, isRequired);
      fillChoices();
   }

   public void setValue(String v) {
      throw new RuntimeException("Can't set value for this type of element!");
   }

   /**
    * Overrides super-method to set this element and all of its
    * choice elements read only value to the one specified.
    */
   public void setReadOnly (boolean ro) {
      super.setReadOnly(ro);
      for (int i = 0; i < choices.size(); i++) {
         ((XMLElement) choices.get(i)).setReadOnly(ro);
      }
   }

   /** 
    * Initializes caches in read-only mode. If mode is not read-only,
    * throws RuntimeException.
    */
   public void initCaches () {
      if (!isReadOnly) {
         throw new RuntimeException("Caches can be initialized only in read-only mode!");
      }
      clearCaches();
      Iterator it=choices.iterator();
      while (it.hasNext()) {
         XMLElement el=(XMLElement)it.next();
         if (el instanceof XMLBaseForCollectionAndComplex) {
            ((XMLBaseForCollectionAndComplex)el).initCaches();
         } else if (el instanceof XMLComplexChoice) {
            ((XMLComplexChoice)el).initCaches();
         }
      }               
      cachesInitialized=true;
   }

   public void clearCaches () {
      Iterator it=choices.iterator();
      while (it.hasNext()) {
         XMLElement el=(XMLElement)it.next();
         if (el instanceof XMLBaseForCollectionAndComplex) {
            ((XMLBaseForCollectionAndComplex)el).clearCaches();
         } else if (el instanceof XMLComplexChoice) {
            ((XMLComplexChoice)el).clearCaches();
         }
      }
      cachesInitialized=false;
   }
   
   public boolean isEmpty() {
      return (choosen instanceof XMLEmptyChoiceElement);
   }

   /**
    * The possible choices - instances of XMLElement class.
    *
    * @return the possible choices for this element.
    */
   public ArrayList getChoices() {
      return choices;
   }

   public XMLElement getChoosen() {
      return choosen;
   }

   public void setChoosen(XMLElement ch) {
      if (isReadOnly) {
         throw new RuntimeException("Can't set the value of read only element!");
      }
      if (!choices.contains(ch)) {
         throw new RuntimeException("Incorrect value! The possible values are: " + choices);
      }
      choosen = ch;
   }

   protected abstract void fillChoices ();

   public Object clone() {
      XMLComplexChoice d = (XMLComplexChoice) super.clone();

      d.choices = new ArrayList();
      d.choosen = null;
      d.cachesInitialized=false;
      Iterator it=choices.iterator();
      while (it.hasNext()) {
         XMLElement c=(XMLElement)it.next();
         XMLElement cloned = (XMLElement) c.clone();
         d.choices.add(cloned);
         cloned.setParent(d);
         if (d.choosen==null && this.choosen.equals(c)) {
            d.choosen = cloned;
         }
      }
      return d;
   }

   public boolean equals (Object e) {
      boolean equals=super.equals(e);
      if (equals) {
         XMLComplexChoice el=(XMLComplexChoice)e;
         return (this.choices.equals(el.choices) && this.choosen.equals(el.choosen));
      }
      return false;
   }

}
