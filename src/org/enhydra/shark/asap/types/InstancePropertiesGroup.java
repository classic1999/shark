/**
 * InstancePropertiesGroup.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package org.enhydra.shark.asap.types;

public class InstancePropertiesGroup  implements java.io.Serializable {
    private org.apache.axis.types.URI key;
    private org.enhydra.shark.asap.types.StateType state;
    private java.lang.String name;
    private java.lang.String subject;
    private java.lang.String description;
    private org.apache.axis.types.URI factoryKey;
    private org.enhydra.shark.asap.types.Observers observers;
    private org.enhydra.shark.asap.types.ContextData contextData;
    private org.enhydra.shark.asap.types.ResultData resultData;
    private org.enhydra.shark.asap.types.HistoryType history;

    public InstancePropertiesGroup() {
    }

    public InstancePropertiesGroup(
           org.apache.axis.types.URI key,
           org.enhydra.shark.asap.types.StateType state,
           java.lang.String name,
           java.lang.String subject,
           java.lang.String description,
           org.apache.axis.types.URI factoryKey,
           org.enhydra.shark.asap.types.Observers observers,
           org.enhydra.shark.asap.types.ContextData contextData,
           org.enhydra.shark.asap.types.ResultData resultData,
           org.enhydra.shark.asap.types.HistoryType history) {
           this.key = key;
           this.state = state;
           this.name = name;
           this.subject = subject;
           this.description = description;
           this.factoryKey = factoryKey;
           this.observers = observers;
           this.contextData = contextData;
           this.resultData = resultData;
           this.history = history;
    }


    /**
     * Gets the key value for this InstancePropertiesGroup.
     * 
     * @return key
     */
    public org.apache.axis.types.URI getKey() {
        return key;
    }


    /**
     * Sets the key value for this InstancePropertiesGroup.
     * 
     * @param key
     */
    public void setKey(org.apache.axis.types.URI key) {
        this.key = key;
    }


    /**
     * Gets the state value for this InstancePropertiesGroup.
     * 
     * @return state
     */
    public org.enhydra.shark.asap.types.StateType getState() {
        return state;
    }


    /**
     * Sets the state value for this InstancePropertiesGroup.
     * 
     * @param state
     */
    public void setState(org.enhydra.shark.asap.types.StateType state) {
        this.state = state;
    }


    /**
     * Gets the name value for this InstancePropertiesGroup.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this InstancePropertiesGroup.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the subject value for this InstancePropertiesGroup.
     * 
     * @return subject
     */
    public java.lang.String getSubject() {
        return subject;
    }


    /**
     * Sets the subject value for this InstancePropertiesGroup.
     * 
     * @param subject
     */
    public void setSubject(java.lang.String subject) {
        this.subject = subject;
    }


    /**
     * Gets the description value for this InstancePropertiesGroup.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this InstancePropertiesGroup.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the factoryKey value for this InstancePropertiesGroup.
     * 
     * @return factoryKey
     */
    public org.apache.axis.types.URI getFactoryKey() {
        return factoryKey;
    }


    /**
     * Sets the factoryKey value for this InstancePropertiesGroup.
     * 
     * @param factoryKey
     */
    public void setFactoryKey(org.apache.axis.types.URI factoryKey) {
        this.factoryKey = factoryKey;
    }


    /**
     * Gets the observers value for this InstancePropertiesGroup.
     * 
     * @return observers
     */
    public org.enhydra.shark.asap.types.Observers getObservers() {
        return observers;
    }


    /**
     * Sets the observers value for this InstancePropertiesGroup.
     * 
     * @param observers
     */
    public void setObservers(org.enhydra.shark.asap.types.Observers observers) {
        this.observers = observers;
    }


    /**
     * Gets the contextData value for this InstancePropertiesGroup.
     * 
     * @return contextData
     */
    public org.enhydra.shark.asap.types.ContextData getContextData() {
        return contextData;
    }


    /**
     * Sets the contextData value for this InstancePropertiesGroup.
     * 
     * @param contextData
     */
    public void setContextData(org.enhydra.shark.asap.types.ContextData contextData) {
        this.contextData = contextData;
    }


    /**
     * Gets the resultData value for this InstancePropertiesGroup.
     * 
     * @return resultData
     */
    public org.enhydra.shark.asap.types.ResultData getResultData() {
        return resultData;
    }


    /**
     * Sets the resultData value for this InstancePropertiesGroup.
     * 
     * @param resultData
     */
    public void setResultData(org.enhydra.shark.asap.types.ResultData resultData) {
        this.resultData = resultData;
    }


    /**
     * Gets the history value for this InstancePropertiesGroup.
     * 
     * @return history
     */
    public org.enhydra.shark.asap.types.HistoryType getHistory() {
        return history;
    }


    /**
     * Sets the history value for this InstancePropertiesGroup.
     * 
     * @param history
     */
    public void setHistory(org.enhydra.shark.asap.types.HistoryType history) {
        this.history = history;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InstancePropertiesGroup)) return false;
        InstancePropertiesGroup other = (InstancePropertiesGroup) obj;
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
            ((this.state==null && other.getState()==null) || 
             (this.state!=null &&
              this.state.equals(other.getState()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.subject==null && other.getSubject()==null) || 
             (this.subject!=null &&
              this.subject.equals(other.getSubject()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.factoryKey==null && other.getFactoryKey()==null) || 
             (this.factoryKey!=null &&
              this.factoryKey.equals(other.getFactoryKey()))) &&
            ((this.observers==null && other.getObservers()==null) || 
             (this.observers!=null &&
              this.observers.equals(other.getObservers()))) &&
            ((this.contextData==null && other.getContextData()==null) || 
             (this.contextData!=null &&
              this.contextData.equals(other.getContextData()))) &&
            ((this.resultData==null && other.getResultData()==null) || 
             (this.resultData!=null &&
              this.resultData.equals(other.getResultData()))) &&
            ((this.history==null && other.getHistory()==null) || 
             (this.history!=null &&
              this.history.equals(other.getHistory())));
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
        if (getState() != null) {
            _hashCode += getState().hashCode();
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
        if (getFactoryKey() != null) {
            _hashCode += getFactoryKey().hashCode();
        }
        if (getObservers() != null) {
            _hashCode += getObservers().hashCode();
        }
        if (getContextData() != null) {
            _hashCode += getContextData().hashCode();
        }
        if (getResultData() != null) {
            _hashCode += getResultData().hashCode();
        }
        if (getHistory() != null) {
            _hashCode += getHistory().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InstancePropertiesGroup.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "instancePropertiesGroup"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("key");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Key"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyURI"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("state");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "State"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "stateType"));
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
        elemField.setFieldName("factoryKey");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "FactoryKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyURI"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("observers");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Observers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Observers"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contextData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "ContextData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">ContextData"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "ResultData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">ResultData"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("history");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "History"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "historyType"));
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
