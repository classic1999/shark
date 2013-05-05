/**
 * FactoryPropertiesGroup.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package org.enhydra.shark.asap.types;

public class FactoryPropertiesGroup  implements java.io.Serializable {
    private org.apache.axis.types.URI key;
    private java.lang.String name;
    private java.lang.String subject;
    private java.lang.String description;
    private org.enhydra.shark.asap.types.SchemaType contextDataSchema;
    private org.enhydra.shark.asap.types.SchemaType resultDataSchema;
    private org.apache.axis.types.Duration expiration;

    public FactoryPropertiesGroup() {
    }

    public FactoryPropertiesGroup(
           org.apache.axis.types.URI key,
           java.lang.String name,
           java.lang.String subject,
           java.lang.String description,
           org.enhydra.shark.asap.types.SchemaType contextDataSchema,
           org.enhydra.shark.asap.types.SchemaType resultDataSchema,
           org.apache.axis.types.Duration expiration) {
           this.key = key;
           this.name = name;
           this.subject = subject;
           this.description = description;
           this.contextDataSchema = contextDataSchema;
           this.resultDataSchema = resultDataSchema;
           this.expiration = expiration;
    }


    /**
     * Gets the key value for this FactoryPropertiesGroup.
     * 
     * @return key
     */
    public org.apache.axis.types.URI getKey() {
        return key;
    }


    /**
     * Sets the key value for this FactoryPropertiesGroup.
     * 
     * @param key
     */
    public void setKey(org.apache.axis.types.URI key) {
        this.key = key;
    }


    /**
     * Gets the name value for this FactoryPropertiesGroup.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this FactoryPropertiesGroup.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the subject value for this FactoryPropertiesGroup.
     * 
     * @return subject
     */
    public java.lang.String getSubject() {
        return subject;
    }


    /**
     * Sets the subject value for this FactoryPropertiesGroup.
     * 
     * @param subject
     */
    public void setSubject(java.lang.String subject) {
        this.subject = subject;
    }


    /**
     * Gets the description value for this FactoryPropertiesGroup.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this FactoryPropertiesGroup.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the contextDataSchema value for this FactoryPropertiesGroup.
     * 
     * @return contextDataSchema
     */
    public org.enhydra.shark.asap.types.SchemaType getContextDataSchema() {
        return contextDataSchema;
    }


    /**
     * Sets the contextDataSchema value for this FactoryPropertiesGroup.
     * 
     * @param contextDataSchema
     */
    public void setContextDataSchema(org.enhydra.shark.asap.types.SchemaType contextDataSchema) {
        this.contextDataSchema = contextDataSchema;
    }


    /**
     * Gets the resultDataSchema value for this FactoryPropertiesGroup.
     * 
     * @return resultDataSchema
     */
    public org.enhydra.shark.asap.types.SchemaType getResultDataSchema() {
        return resultDataSchema;
    }


    /**
     * Sets the resultDataSchema value for this FactoryPropertiesGroup.
     * 
     * @param resultDataSchema
     */
    public void setResultDataSchema(org.enhydra.shark.asap.types.SchemaType resultDataSchema) {
        this.resultDataSchema = resultDataSchema;
    }


    /**
     * Gets the expiration value for this FactoryPropertiesGroup.
     * 
     * @return expiration
     */
    public org.apache.axis.types.Duration getExpiration() {
        return expiration;
    }


    /**
     * Sets the expiration value for this FactoryPropertiesGroup.
     * 
     * @param expiration
     */
    public void setExpiration(org.apache.axis.types.Duration expiration) {
        this.expiration = expiration;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FactoryPropertiesGroup)) return false;
        FactoryPropertiesGroup other = (FactoryPropertiesGroup) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.key==null && other.getKey()==null) || 
             (this.key!=null &&
              this.key.equals(other.getKey()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.subject==null && other.getSubject()==null) || 
             (this.subject!=null &&
              this.subject.equals(other.getSubject()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.contextDataSchema==null && other.getContextDataSchema()==null) || 
             (this.contextDataSchema!=null &&
              this.contextDataSchema.equals(other.getContextDataSchema()))) &&
            ((this.resultDataSchema==null && other.getResultDataSchema()==null) || 
             (this.resultDataSchema!=null &&
              this.resultDataSchema.equals(other.getResultDataSchema()))) &&
            ((this.expiration==null && other.getExpiration()==null) || 
             (this.expiration!=null &&
              this.expiration.equals(other.getExpiration())));
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
        if (getKey() != null) {
            _hashCode += getKey().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getSubject() != null) {
            _hashCode += getSubject().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getContextDataSchema() != null) {
            _hashCode += getContextDataSchema().hashCode();
        }
        if (getResultDataSchema() != null) {
            _hashCode += getResultDataSchema().hashCode();
        }
        if (getExpiration() != null) {
            _hashCode += getExpiration().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FactoryPropertiesGroup.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "factoryPropertiesGroup"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("key");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Key"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyURI"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subject");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Subject"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contextDataSchema");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "ContextDataSchema"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "schemaType"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultDataSchema");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "ResultDataSchema"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "schemaType"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expiration");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Expiration"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "duration"));
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
          new  org.enhydra.shark.asap.util.BeanSerializerShark(
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
          new  org.enhydra.shark.asap.util.BeanDeserializerShark(
            _javaType, _xmlType, typeDesc);
    }

}
