/**
 * SetDefinitionRq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package org.enhydra.shark.wfxml.types;

public class SetDefinitionRq  implements java.io.Serializable {
    private java.lang.String processLanguage;
    private org.enhydra.shark.wfxml.types.SetDefinitionRqDefinition definition;

    public SetDefinitionRq() {
    }

    public SetDefinitionRq(
           java.lang.String processLanguage,
           org.enhydra.shark.wfxml.types.SetDefinitionRqDefinition definition) {
           this.processLanguage = processLanguage;
           this.definition = definition;
    }


    /**
     * Gets the processLanguage value for this SetDefinitionRq.
     * 
     * @return processLanguage
     */
    public java.lang.String getProcessLanguage() {
        return processLanguage;
    }


    /**
     * Sets the processLanguage value for this SetDefinitionRq.
     * 
     * @param processLanguage
     */
    public void setProcessLanguage(java.lang.String processLanguage) {
        this.processLanguage = processLanguage;
    }


    /**
     * Gets the definition value for this SetDefinitionRq.
     * 
     * @return definition
     */
    public org.enhydra.shark.wfxml.types.SetDefinitionRqDefinition getDefinition() {
        return definition;
    }


    /**
     * Sets the definition value for this SetDefinitionRq.
     * 
     * @param definition
     */
    public void setDefinition(org.enhydra.shark.wfxml.types.SetDefinitionRqDefinition definition) {
        this.definition = definition;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SetDefinitionRq)) return false;
        SetDefinitionRq other = (SetDefinitionRq) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.processLanguage==null && other.getProcessLanguage()==null) || 
             (this.processLanguage!=null &&
              this.processLanguage.equals(other.getProcessLanguage()))) &&
            ((this.definition==null && other.getDefinition()==null) || 
             (this.definition!=null &&
              this.definition.equals(other.getDefinition())));
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
        if (getProcessLanguage() != null) {
            _hashCode += getProcessLanguage().hashCode();
        }
        if (getDefinition() != null) {
            _hashCode += getDefinition().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SetDefinitionRq.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.wfmc.org/wfxml/2.0/", ">SetDefinitionRq"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("processLanguage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.wfmc.org/wfxml/2.0/", "ProcessLanguage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("definition");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.wfmc.org/wfxml/2.0/", "Definition"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.wfmc.org/wfxml/2.0/", ">>SetDefinitionRq>Definition"));
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
