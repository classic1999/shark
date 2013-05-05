/**
 * ListDefinitionsRs.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package org.enhydra.shark.wfxml.types;

public class ListDefinitionsRs  implements java.io.Serializable {
    private org.enhydra.shark.wfxml.types.DefinitionInfo[] definitionInfo;

    public ListDefinitionsRs() {
    }

    public ListDefinitionsRs(
           org.enhydra.shark.wfxml.types.DefinitionInfo[] definitionInfo) {
           this.definitionInfo = definitionInfo;
    }


    /**
     * Gets the definitionInfo value for this ListDefinitionsRs.
     * 
     * @return definitionInfo
     */
    public org.enhydra.shark.wfxml.types.DefinitionInfo[] getDefinitionInfo() {
        return definitionInfo;
    }


    /**
     * Sets the definitionInfo value for this ListDefinitionsRs.
     * 
     * @param definitionInfo
     */
    public void setDefinitionInfo(org.enhydra.shark.wfxml.types.DefinitionInfo[] definitionInfo) {
        this.definitionInfo = definitionInfo;
    }

    public org.enhydra.shark.wfxml.types.DefinitionInfo getDefinitionInfo(int i) {
        return this.definitionInfo[i];
    }

    public void setDefinitionInfo(int i, org.enhydra.shark.wfxml.types.DefinitionInfo _value) {
        this.definitionInfo[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ListDefinitionsRs)) return false;
        ListDefinitionsRs other = (ListDefinitionsRs) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.definitionInfo==null && other.getDefinitionInfo()==null) || 
             (this.definitionInfo!=null &&
              java.util.Arrays.equals(this.definitionInfo, other.getDefinitionInfo())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getDefinitionInfo() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDefinitionInfo());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDefinitionInfo(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ListDefinitionsRs.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.wfmc.org/wfxml/2.0/", ">ListDefinitionsRs"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("definitionInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.wfmc.org/wfxml/2.0/", "DefinitionInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.wfmc.org/wfxml/2.0/", ">DefinitionInfo"));
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.enhydra.shark.wfxml.util.BeanSerializerShark(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.enhydra.shark.wfxml.util.BeanDeserializerShark(
            _javaType, _xmlType, typeDesc);
    }

}
