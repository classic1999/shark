package org.enhydra.shark.xpdl;

/**
 *  Class that represents the member of collection from XML schema
 *  that has unique Id attribute.
 * 
 *  @author Sasa Bojanic
 */
public abstract class XMLCollectionElement extends XMLComplexElement {

   public XMLCollectionElement (XMLCollection parent, boolean isRequired) {
      super(parent, isRequired);
   }

   protected void fillStructure () {
      XMLAttribute attrId=new XMLAttribute(this,"Id", true); //required      
      super.add(attrId);
   }
   
   public final String getId () {
      return get("Id").toValue();
   }
   public final void setId (String id) {
      set("Id",id);
   }

}
