package org.enhydra.shark.xpdl;

import java.util.ArrayList;
import java.util.Iterator;

import org.enhydra.shark.utilities.SequencedHashMap;

/**
 *  Class that represents complex element from XML schema.
 * 
 *  @author Sasa Bojanic
 */
public abstract class XMLComplexElement extends XMLBaseForCollectionAndComplex {

   public XMLComplexElement(XMLElement parent, boolean isRequired) {
      super(parent, isRequired);
      fillStructure();
   }

   protected void add (XMLElement el) {
      elements.add(el);
      elementMap.put(el.toName(),el);
   }

   protected boolean add (int no,XMLElement el) {
      if (no<0 || no>size()) return false;
      elements.add(no,el);
      // here, we don't care about position
      elementMap.put(el.toName(),el);
      return true;
   }
   
   
   /** 
    * It is empty if its value is not set, and if 
    * all elements in the structure are empty.
    */
   public boolean isEmpty () {
      boolean isEmpty = true;
      Iterator it = elements.iterator();
      while ( it.hasNext() ) {
         XMLElement el = ( XMLElement ) it.next();
         isEmpty = isEmpty && el.isEmpty();
      }
      isEmpty=isEmpty && value.trim().length()==0;
      return isEmpty;
   }

   /**
    * Returns the collection of XML elements this element is made of.
    */
   public ArrayList getXMLElements () {
      ArrayList els=new ArrayList();
      Iterator it=elements.iterator();
      while (it.hasNext()) {
         Object el=it.next();
         if (!(el instanceof XMLAttribute)) {
            els.add(el);
         }
      }
      return els;
   }

   /**
    * Returns the collection of XML attributes this element is made of.
    */
   public ArrayList getXMLAttributes () {
      ArrayList attribs=new ArrayList();
      Iterator it=elements.iterator();
      while (it.hasNext()) {
         Object el=it.next();
         if (el instanceof XMLAttribute) {
            attribs.add(el);
         }
      }
      return attribs;
   }


   /**
    * Sets the element from structure with specified name to
    * the specified value.
    */
   public void set(String name,String value) {
      if (isReadOnly) {
         throw new RuntimeException("Can't change element from read only structure!");
      }
      XMLElement el = get(name);
      if (el!=null) {
         el.setValue(value);
      } else {
         throw new RuntimeException("No such element!");
      }
   }

   /**
    * Sets the element that is placed at specified location within structure
    * to the specified value.
    */
   public boolean set(int no,String value) {
      if (no<0 || no>=size()) return false;
      if (isReadOnly) {
         throw new RuntimeException("Can't change element from read only structure!");
      }
      XMLElement el=(XMLElement)get(no);
      el.setValue(value);
      return true;
   }

   /** Gets the element with specified name from stucture. */
   public XMLElement get (String name) {
      return (XMLElement)elementMap.get(name);
   }

   /** Returns true if there is an element with such element in structure. */
   public boolean containsName (String name) {
      return elementMap.containsKey(name);
   }
   
   /**
    * The classes that are derived from this class has to give its
    * definition for this method. It is used to insert all members
    * of those classes that are derived from XMLElement.
    * <p>NOTE: The order of inserted elements is relevant for XML to be
    *          valid (members of classes derived from this class must be
    *          inserted into first mentioned list in the same order that
    *          they are within a corresponding tag for those classes
    *          within WfMC XML).
    */
   protected abstract void fillStructure();

   public Object clone() {
      XMLComplexElement d = (XMLComplexElement)super.clone();
      d.elements = new ArrayList();
      d.elementMap = new SequencedHashMap();
      d.cachesInitialized=false;
      Iterator it=elements.iterator();
      while (it.hasNext()) {
         XMLElement el=(XMLElement)it.next();
         XMLElement cl=(XMLElement)el.clone();
         cl.setParent(d);
         d.elements.add(cl);
         d.elementMap.put(cl.toName(),cl);
      }      
      return d;
   }
   
}
