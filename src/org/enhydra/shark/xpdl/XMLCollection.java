package org.enhydra.shark.xpdl;

import java.util.ArrayList;
import java.util.Iterator;

import org.enhydra.shark.utilities.SequencedHashMap;

/**
 *  Class that represents collection of elements from XML schema.
 * 
 *  @author Sasa Bojanic
 */
public abstract class XMLCollection extends XMLBaseForCollectionAndComplex {

   public XMLCollection (XMLComplexElement parent, boolean isRequired) {
      super(parent, isRequired);
   }

   public XMLCollection (XMLComplexChoice parent, boolean isRequired) {
      super(parent, isRequired);
   }

   public void initCaches () {
      super.initCaches();
      if (elements.size()>0 && elements.get(0) instanceof XMLCollectionElement) {
         elementMap.clear();
         Iterator it=elements.iterator();
         while (it.hasNext()) {
            XMLCollectionElement el=(XMLCollectionElement)it.next();
            elementMap.put(el.getId(),el);
         }         
      }      
      cachesInitialized=true;
   }
   
   public void clearCaches () {
      super.clearCaches();
      elementMap.clear();
   }

   /** NOTE: Method signature changed to public. */
   public void add (XMLElement el) {
      if (isReadOnly) {
         throw new RuntimeException("Can't set the value of read only element!");
      }
      elements.add(el);
   }

   /** NOTE: Method signature changed to public. */
   public boolean add (int no,XMLElement el) {
      if (isReadOnly) {
         throw new RuntimeException("Can't set the value of read only element!");
      }
      if (no<0 || no>size()) return false;
      elements.add(no,el);
      return true;
   }

   /** NOTE: Method signature changed to public. */   
   public int remove (XMLElement el) {
      return super.remove(el);
   }

   /** NOTE: Method signature changed to public. */
   protected XMLElement remove (int no) {
      return super.remove(no);
   }
   
   /**
    * Returns <tt>true</tt> if there are no elements within collection.
    */
   public boolean isEmpty () {
      return size()==0;
   }
   
   /**
    * Returns the element specified by Id attribute.
    * Use only if this is collection of XMLCollectionElements.
    */
   public XMLCollectionElement getCollectionElement (String id) {
      if (isReadOnly && cachesInitialized) {
         return (XMLCollectionElement)elementMap.get(id);
      } else {
         Iterator it=elements.iterator();
         while (it.hasNext()) {
            XMLCollectionElement ce=(XMLCollectionElement)it.next();         
            if (ce.getId().equals(id)) {
               return ce;
            }
         }
         return null;
      }
   }

   /**
    * Returns true if element with given Id exists in collection.
    */
   public boolean containsElement (String id) {
      return getCollectionElement(id)!=null;
   }

   /** Clears the collection. */
   public void clear () {
      elements.clear();
      elementMap.clear();
   }
   
   /**
    * Generates the new element that made collection. Derived classes
    * has to implement this method to create it's collection element.
    */
   public abstract XMLElement generateNewElement();
      
   public Object clone () {
      XMLCollection d=(XMLCollection)super.clone();
      d.elements=new ArrayList();
      d.elementMap=new SequencedHashMap();
      d.cachesInitialized=false;
      Iterator it=this.elements.iterator();
      while (it.hasNext()) {
         Object obj=it.next();
         XMLElement el=(XMLElement)obj;
         XMLElement cl=(XMLElement)el.clone();
         cl.setParent(d);
         d.elements.add(cl);
      }
      return d;           
   }
   
}
