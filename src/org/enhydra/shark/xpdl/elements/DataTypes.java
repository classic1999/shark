package org.enhydra.shark.xpdl.elements;

import java.util.ArrayList;
import java.util.Iterator;

import org.enhydra.shark.xpdl.XMLBaseForCollectionAndComplex;
import org.enhydra.shark.xpdl.XMLComplexChoice;
import org.enhydra.shark.xpdl.XMLComplexElement;
import org.enhydra.shark.xpdl.XMLElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class DataTypes extends XMLComplexChoice {

   private boolean isInitialized=false;
   
   public DataTypes (XMLComplexElement parent) {
      super(parent,"Type", true);
      isInitialized=true;
   }

   protected void fillChoices () {
      if (isInitialized) {
         choices=new ArrayList();
         choices.add(new BasicType(this));      
         choices.add(new DeclaredType(this));
         choices.add(new SchemaType(this));
         choices.add(new ExternalReference(this, true));
         choices.add(new RecordType(this));
         choices.add(new UnionType(this));
         choices.add(new EnumerationType(this));
         choices.add(new ArrayType(this));
         choices.add(new ListType(this));
         choosen=(XMLElement)choices.get(0);
         setReadOnly(isReadOnly);
         if (cachesInitialized) {
            initCaches();
         }
      }
   }
   
   public void setReadOnly (boolean ro) {
      this.isReadOnly=ro;
      if (choices==null) return;
      super.setReadOnly(ro);   
   }

   public void initCaches () {
      if (choices!=null) {
         super.initCaches();
      } else {
         cachesInitialized=true;         
      }
   }

   public void clearCaches () {
      if (choices!=null) {
         super.clearCaches();
      } else {
         cachesInitialized=false;
      }
   }
   
   public ArrayList getChoices () {
      if (choices==null) {
         fillChoices();
      }
      return choices;
   }
   
   public XMLElement getChoosen () {
      if (choosen==null) {
         fillChoices();
      }
      return choosen;
   }
   
   public BasicType getBasicType () {
      return (BasicType)getChoices().get(0);
   }

   public void setBasicType () {
      choosen=(BasicType)getChoices().get(0);
   }
   
   public DeclaredType getDeclaredType () {
      return (DeclaredType)getChoices().get(1);
   }

   public void setDeclaredType () {
      choosen=(DeclaredType)getChoices().get(1);
   }

   public SchemaType getSchemaType () {
      return (SchemaType)getChoices().get(2);
   }

   public void setSchemaType () {
      choosen=(SchemaType)getChoices().get(2);
   }

   public ExternalReference getExternalReference () {
      return (ExternalReference)getChoices().get(3);
   }

   public void setExternalReference () {
      choosen=(ExternalReference)getChoices().get(3);
   }
   
   public RecordType getRecordType () {
      return (RecordType)getChoices().get(4);
   }

   public void setRecordType () {
      choosen=(RecordType)getChoices().get(4);
   }

   public UnionType getUnionType () {
      return (UnionType)getChoices().get(5);
   }

   public void setUnionType () {
      choosen=(UnionType)getChoices().get(5);
   }

   public EnumerationType getEnumerationType () {
      return (EnumerationType)getChoices().get(6);
   }

   public void setEnumerationType () {
      choosen=(EnumerationType)getChoices().get(6);
   }
   
   public ArrayType getArrayType () {
      return (ArrayType)getChoices().get(7);
   }

   public void setArrayType () {
      choosen=(ArrayType)getChoices().get(7);
   }

   public ListType getListType () {
      return (ListType)getChoices().get(8);
   }

   public void setListType () {
      choosen=(ListType)getChoices().get(8);
   }
   
   public Object clone() {
      DataTypes d = null;
      if (choices!=null) {
         d = (DataTypes) super.clone();
         isInitialized = true;
      } else {
         d = new DataTypes((XMLComplexElement)this.parent);
         d.isReadOnly = this.isReadOnly;
      }
      return d;
   }
   
   public boolean equals (Object e) {
      if (this == e)
         return true;
      if (e == null || !(e instanceof DataTypes)) return false;
      DataTypes dt=(DataTypes)e;
      if ((choices==null && dt.choices!=null) || (choices!=null && dt.choices==null)) return false;
      if (choices!=null) {
         return super.equals(e);
      } else {
         return true;
      }
   }
   
}

