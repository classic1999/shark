package org.enhydra.shark.xpdl;

import java.util.ArrayList;
import java.util.Iterator;

import org.enhydra.shark.utilities.SequencedHashMap;

/**
 *  Base class for implementing XMLComplexElement and XMLCollection classes.
 * 
 *  @author Sasa Bojanic
 */
public abstract class XMLBaseForCollectionAndComplex extends XMLElement {

   protected SequencedHashMap elementMap;
   protected ArrayList elements;
   
   protected transient boolean cachesInitialized=false;
   
   public XMLBaseForCollectionAndComplex (XMLElement parent, boolean isRequired) {
      super(parent, isRequired);
      elements=new ArrayList();
      elementMap=new SequencedHashMap();   
   }

   public void setValue(String v) {
      throw new RuntimeException("Can't set value for this type of element!");
   }

   /**
    * Sets this element, and all contained elements to be read only or not.
    */
    public void setReadOnly (boolean ro) {
       super.setReadOnly(ro);
       Iterator it=elements.iterator();
       while (it.hasNext()) {
          XMLElement el=(XMLElement)it.next();
          el.setReadOnly(ro);
       }
       if (!ro) {
          clearCaches();
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
      Iterator it=elements.iterator();
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
      Iterator it=elements.iterator();
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
   
   /** Adds new element. */
   protected abstract void add (XMLElement el);         

   /** Adds new element to a certain position */
   protected abstract boolean add (int no,XMLElement el);
   
   /** Removes specified element. */
   protected int remove (XMLElement el) {
      if (isReadOnly) {
         throw new RuntimeException("Can't remove element from read only structure!");
      }
      int ind=elements.indexOf(el);
      if (ind>=0) {
         elements.remove(el);
         elementMap.remove(el);
      }
      return ind;
   }

   /** Removes element at specified location. */
   protected XMLElement remove (int no) {
      if (isReadOnly) {
         throw new RuntimeException("Can't remove element from read only structure!");
      }
      if (no<0 || no>=size()) return null;
      XMLElement el=(XMLElement)elements.remove(no);
      // here, we don't care about position
      elementMap.remove(el);
      return el;
   }
   
   /** Returns true if there is such element in collection. */
   public boolean contains (XMLElement el) {
      return elements.contains(el);
   }

   /** Gets the element from specified location. */
   public XMLElement get (int no) {
      if (no<0 || no>=size()) return null;
      return (XMLElement)elements.get(no);
   }

   /** Returns the number of elements. */
   public int size () {
      return elements.size();
   }

   /** Returns the copy of the list all elements within collection. */
   public ArrayList toElements() {
      return new ArrayList(elements);
   }
   
   /** Returns the copy of the map of all elements within collection. */
   public SequencedHashMap toElementMap () {
      return new SequencedHashMap(elementMap);
   }

   public boolean equals (Object e) {
      boolean equals=super.equals(e);
      if (equals) {
         XMLBaseForCollectionAndComplex el=(XMLBaseForCollectionAndComplex)e;
         return (this.elements.equals(el.elements));
      }
      return false;
   }
   
}
