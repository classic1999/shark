/**
 * CreateInstanceRq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package org.enhydra.shark.asap.types;

public class CreateInstanceRq  implements java.io.Serializable {
    private boolean startImmediately;
    private org.apache.axis.types.URI observerKey;
    private java.lang.String name;
    private java.lang.String subject;
    private java.lang.String description;
    private org.enhydra.shark.asap.types.CreateInstanceRqContextData contextData;

    public CreateInstanceRq() {
    }

    public CreateInstanceRq(
           boolean startImmediately,
           org.apache.axis.types.URI observerKey,
           java.lang.String name,
           java.lang.String subject,
           java.lang.String description,
           org.enhydra.shark.asap.types.CreateInstanceRqContextData contextData) {
           this.startImmediately = startImmediately;
           this.observerKey = observerKey;
           this.name = name;
           this.subject = subject;
           this.description = description;
           this.contextData = contextData;
    }


    /**
     * Gets the startImmediately value for this CreateInstanceRq.
     * 
     * @return startImmediately
     */
    public boolean isStartImmediately() {
        return startImmediately;
    }


    /**
     * Sets the startImmediately value for this CreateInstanceRq.
     * 
     * @param startImmediately
     */
    public void setStartImmediately(boolean startImmediately) {
        this.startImmediately = startImmediately;
    }


    /**
     * Gets the observerKey value for this CreateInstanceRq.
     * 
     * @return observerKey
     */
    public org.apache.axis.types.URI getObserverKey() {
        return observerKey;
    }


    /**
     * Sets the observerKey value for this CreateInstanceRq.
     * 
     * @param observerKey
     */
    public void setObserverKey(org.apache.axis.types.URI observerKey) {
        this.observerKey = observerKey;
    }


    /**
     * Gets the name value for this CreateInstanceRq.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this CreateInstanceRq.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the subject value for this CreateInstanceRq.
     * 
     * @return subject
     */
    public java.lang.String getSubject() {
        return subject;
    }


    /**
     * Sets the subject value for this CreateInstanceRq.
     * 
     * @param subject
     */
    public void setSubject(java.lang.String subject) {
        this.subject = subject;
    }


    /**
     * Gets the description value for this CreateInstanceRq.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this CreateInstanceRq.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the contextData value for this CreateInstanceRq.
     * 
     * @return contextData
     */
    public org.enhydra.shark.asap.types.CreateInstanceRqContextData getContextData() {
        return contextData;
    }


    /**
     * Sets the contextData value for this CreateInstanceRq.
     * 
     * @param contextData
     */
    public void setContextData(org.enhydra.shark.asap.types.CreateInstanceRqContextData contextData) {
        this.contextData = contextData;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CreateInstanceRq)) return false;
        CreateInstanceRq other = (CreateInstanceRq) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.startImmediately == other.isStartImmediately() &&
            ((this.observerKey==null && other.getObserverKey()==null) || 
             (this.observerKey!=null &&
              this.observerKey.equals(other.getObserverKey()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.subject==null && other.getSubject()==null) || 
             (this.subject!=null &&
              this.subject.equals(other.getSubject()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.contextData==null && other.getContextData()==null) || 
             (this.contextData!=null &&
              this.contextData.equals(other.getContextData())));
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
        _hashCode += (isStartImmediately() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getObserverKey() != null) {
            _hashCode += getObserverKey().hashCode();
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
        if (getContextData() != null) {
            _hashCode += getContextData().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CreateInstanceRq.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">CreateInstanceRq"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startImmediately");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "StartImmediately"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("observerKey");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "ObserverKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyURI"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subject");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Subject"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contextData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "ContextData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">>CreateInstanceRq>ContextData"));
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
