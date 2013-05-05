package org.enhydra.shark.xpdl;

import java.io.Serializable;

/**
 * Base class for representing elements from XML schema.
 *
 * @author Sasa Bojanic
 */
public abstract class XMLElement implements Serializable, Cloneable {

   /**
    * Equivalent for XML element name. Used when writting instance of this class to XML file.
    */
   private String name;

   /**
    * Indicates if element is required - corresponds to the same XML element definition.
    */
   private boolean isRequired = false;

   /**
    * Supposed to contain the value for XML element. This is true for simple elements and
    * attributes, more complex elements uses it as they need.
    */
   protected String value;

   /**
    * Indicates if an element is read only.
    */
   protected boolean isReadOnly = false;

   /**
    * Reference to parent object in DOM tree.
    */
   protected XMLElement parent;

   /**
    * Creates a new instance of element: sets <code>name</code> to name of concrete class
    * implementation of this abstract class, and <code>parent</code> and <code>isRequired</code>
    * properties to the specified ones.
    * <p>
    * It also sets the value of this element to an empty String.
    */
   public XMLElement(XMLElement parent, boolean isRequired) {
      this.parent = parent;
      this.isRequired = isRequired;
      this.name = getClass().getName();
      this.name = XMLUtil.getShortClassName(name);
      this.value = new String();
   }

   /**
    * Creates a new instance of element: sets <code>name</code>,
    * <code>parent</code> <code>isRequired</code> properties to specified ones.
    * <p>
    * It also sets the value of this element to an empty String.
    */
   public XMLElement(XMLElement parent, String name, boolean isRequired) {
      this.parent = parent;
      this.name = name;
      this.isRequired = isRequired;
      this.value = new String();
   }

   /**
    * Sets 'read only' property of element to specified value. This enables/disables editing of the
    * element value for the simple elements and attributes, or changes to attributes and elements of
    * complex objects and collections.
    * <p>
    * If element is read only, and one wants to change its property, the RuntimeException will be
    * thrown.
    */
   public void setReadOnly(boolean ro) {
      this.isReadOnly = ro;
   }

   /**
    * Returns the 'read only' status of element.
    * <p>
    * If element is read only, and one wants to change its property, the RuntimeException will be
    * thrown.
    */
   public boolean isReadOnly() {
      return isReadOnly;
   }

   /**
    * Returns if the element is required or not, which is defined by XPDL schema. If element is
    * required, its value must be defined (In the case of complex elements, all the required
    * subelements must be defined). Otherwise, the whole Package won't be valid by the XPDL schema.
    */
   public boolean isRequired() {
      return isRequired;
   }

   /**
    * Indicates if element is empty.
    */
   public boolean isEmpty() {
      return !(value != null && value.trim().length() > 0);
   }

   /**
    * Sets the element value. If it is simple element or an non-choice attribute, this sets the
    * actual value of the element. If it is choice attribute, it sets the choosen value. Only some
    * complex elements (Condition, SchemaType, and ExtendedAttribute) allows you to use this method,
    * while others will throw RuntimeException.
    */
   public void setValue(String v) {
      if (isReadOnly) {
         throw new RuntimeException("Can't set the value of read only element!");
      }
      this.value = v;
   }

   /**
    * Returns the element value.
    */
   public String toValue() {
      return value;
   }

   /**
    * Returns the name of element.
    */
   public String toName() {
      return name;
   }

   /** Gets the parent element in DOM tree. */
   public XMLElement getParent() {
      return parent;
   }

   /**
    * Sets the parent element in DOM tree.
    * <p>
    * This method is used when collection, complex element or complex choice is cloned, to set new
    * parent element of the cloned sub-elements.
    */
   public void setParent(XMLElement el) {
      this.parent = el;
   }

   /**
    * Used to create exact copy of the element.
    */
   public Object clone() {
      XMLElement d = null;
      try {
         //System.out.println("Cloning XMLELement "+name);
         d = (XMLElement) super.clone();
         d.name = new String(this.name);
         d.value = new String(this.value);
         d.isRequired = this.isRequired;
         d.isReadOnly = this.isReadOnly;
         d.parent = this.parent;
      } catch (CloneNotSupportedException ex) {
         // Won't happen because we implement Cloneable
         throw new Error(ex.toString());
      }
      return d;
   }

   public boolean equals(Object e) {
      if (this == e)
         return true;
//System.out.println("Checking eq for el "+e+" with el "+this);
      if (e != null && e instanceof XMLElement && e.getClass().equals(this.getClass())) {
//System.out.println("Els are the same class");
         XMLElement el = (XMLElement) e;
         // TODO: do we need to check isReadOnly for equality?
         return (this.name.equals(el.name) &&
                  this.value.equals(el.value) &&
                  this.isRequired == el.isRequired);
         //&& (this.parent == null ? el.parent == null : this.parent.equals(el.parent)));
      }/* else {
         System.out.println("Els are not the same class: el="+e+",  this="+this);

      }*/
      return false;
   }

}

