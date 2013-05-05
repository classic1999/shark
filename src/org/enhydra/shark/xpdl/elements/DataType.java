package org.enhydra.shark.xpdl.elements;

import java.util.HashMap;
import java.util.Map;

import org.enhydra.shark.xpdl.XMLComplexElement;
import org.enhydra.shark.xpdl.XMLElement;
import org.enhydra.shark.xpdl.XMLUtil;

/**
 *  Represents coresponding element from XPDL schema.
 *
 *  @author Sasa Bojanic
 */
public class DataType extends XMLComplexElement {

   private static Map basicTypesMap=new HashMap();
   static {
      basicTypesMap.put("BOOLEAN","java.lang.Boolean");
      basicTypesMap.put("STRING","java.lang.String");
      basicTypesMap.put("INTEGER","java.lang.Long");
      basicTypesMap.put("FLOAT","java.lang.Double");
      basicTypesMap.put("DATETIME","java.util.Date");
   }

   protected transient String javaType;
   
   public DataType (XMLComplexElement parent) {
      super(parent, true);
   }

   protected void fillStructure () {
      DataTypes refDataTypes=new DataTypes(this);

      add(refDataTypes);
   }

   public void initCaches () {
      super.initCaches();
      javaType=getChoosenType(getDataTypes(), XMLUtil.getPackage(this));
   }
   
   public void clearCaches () {
      javaType=null;
      super.clearCaches();
   }

   public String getJavaType () {
      if (!isReadOnly) {
         throw new RuntimeException("This method can be used only in read-only mode!");
      }      
      return javaType;
   }
   
   private static String getChoosenType (DataTypes types,Package pkg) {
      XMLElement choosenType=types.getChoosen();
      if (choosenType instanceof BasicType) {
         String subTypeName=((BasicType)choosenType).getType();
         if (DataType.basicTypesMap.containsKey(subTypeName)) {
            return (String)DataType.basicTypesMap.get(subTypeName);
         }
      } else if (choosenType instanceof DeclaredType) {
         TypeDeclaration td=pkg.getTypeDeclaration(((DeclaredType)choosenType).getId());
         DataTypes dtypes=td.getDataTypes();
         return getChoosenType(dtypes,pkg);
      } else if (choosenType instanceof ExternalReference) {
         return ((ExternalReference)choosenType).getLocation();
      }
      return "java.lang.Object";

   }

   public DataTypes getDataTypes() {
      return (DataTypes)get("Type");
   }      
   
}
