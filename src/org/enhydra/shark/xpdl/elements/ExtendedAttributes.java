package org.enhydra.shark.xpdl.elements;

import java.util.ArrayList;
import java.util.Iterator;

import org.enhydra.shark.utilities.SequencedHashMap;
import org.enhydra.shark.xpdl.XMLCollection;
import org.enhydra.shark.xpdl.XMLComplexElement;
import org.enhydra.shark.xpdl.XMLElement;
import org.enhydra.shark.xpdl.XMLUtil;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class ExtendedAttributes extends XMLCollection {

   public transient SequencedHashMap eaMap;  
   
   protected String extAttribsString;
   
   public ExtendedAttributes (XMLComplexElement parent) {
      super(parent, false);
   }

   public XMLElement generateNewElement() {
      return new ExtendedAttribute(this);
   }

   public ExtendedAttribute getFirstExtendedAttributeForName (String name) {
      ExtendedAttribute ea=null;
      ArrayList l=getElementsForName(name);
      if (l!=null && l.size()>0) {
         ea=(ExtendedAttribute)l.get(0);
      }
      return ea;
   }
   
   public void initCaches () {
      super.initCaches();
      Iterator it=elements.iterator();
      while (it.hasNext()) {
         ExtendedAttribute ea=(ExtendedAttribute)it.next();
         String eaName=ea.getName();
         ArrayList l=(ArrayList)eaMap.get(eaName);
         if (l==null) {
            l=new ArrayList();
         }
         l.add(ea);
         eaMap.put(eaName,l);
      }      
      if (parent instanceof Application) {
         getExtendedAttributesString();
      }
   }

   public void initExtAttribString () {
      if (!isReadOnly) {
         throw new RuntimeException("This method can be used only in read-only mode!");
      }
      if (this.size()>0) {
         try {
            extAttribsString=XMLUtil.stringifyExtendedAttributes(this);
         } catch (Throwable thr) {
            throw new RuntimeException("Can't stringify extended attributes!");
         }
      } else {
         extAttribsString="";
      }         
   }

   public void clearExtAttribString () {
      extAttribsString=null;
   }
   
   public String getExtendedAttributesString () {
      if (!isReadOnly) {
         throw new RuntimeException("This method can be used only in read-only mode!");
      }
      if (extAttribsString==null) {
         initExtAttribString();
      }
      return extAttribsString;
   }
   
   public void clearCaches () {
      eaMap=new SequencedHashMap();
      super.clearCaches();
   }
   
   public void clear () {
      if (eaMap!=null) {
         eaMap.clear();
      }
      super.clear();
   }
   
   /**
    * Returns true if there is at least one ExtendedAttribute with such name. 
    */
   public boolean containsElement (String name) {
      if (isReadOnly && cachesInitialized) {
         return eaMap.containsKey(name);
      } else {
         Iterator it=elements.iterator();
         while (it.hasNext()) {
            ExtendedAttribute ea=(ExtendedAttribute)it.next();         
            if (ea.getName().equals(name)) {
               return true;
            }
         }
         return false;
      }
   }
   
   /**
    * Returns true if there is at least one ExtendedAttribute with such value.
    */
   public boolean containsValue (String val) {
      Iterator it=elements.iterator();
      while (it.hasNext()) {
         ExtendedAttribute ea=(ExtendedAttribute)it.next();
         if (ea.getVValue().equals(val)) {
            return true;
         }
      }
      return false;
   }   

   /**
    * Returns all elements with specified name. 
    */
   public ArrayList getElementsForName (String name) {
      if (isReadOnly && cachesInitialized) {
         return (ArrayList)eaMap.get(name);
      } else {
         ArrayList l=new ArrayList();
         Iterator it=elements.iterator();
         while (it.hasNext()) {
            ExtendedAttribute ea=(ExtendedAttribute)it.next();         
            if (ea.getName().equals(name)) {
               l.add(ea);
            }
         }
         return l;
      }
   }
   
   public Object clone () {
      ExtendedAttributes d=(ExtendedAttributes)super.clone();
      d.extAttribsString=this.extAttribsString;
      
      return d;
   }
   
}
