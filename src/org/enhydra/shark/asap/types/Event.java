/**
 * Event.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package org.enhydra.shark.asap.types;

public class Event  implements java.io.Serializable {
    private java.util.Calendar time;
    private org.enhydra.shark.asap.types.EventEventType eventType;
    private org.apache.axis.types.URI sourceKey;
    private java.lang.Object details;
    private org.enhydra.shark.asap.types.StateType oldState;
    private org.enhydra.shark.asap.types.StateType newState;

    public Event() {
    }

    public Event(
           java.util.Calendar time,
           org.enhydra.shark.asap.types.EventEventType eventType,
           org.apache.axis.types.URI sourceKey,
           java.lang.Object details,
           org.enhydra.shark.asap.types.StateType oldState,
           org.enhydra.shark.asap.types.StateType newState) {
           this.time = time;
           this.eventType = eventType;
           this.sourceKey = sourceKey;
           this.details = details;
           this.oldState = oldState;
           this.newState = newState;
    }


    /**
     * Gets the time value for this Event.
     * 
     * @return time
     */
    public java.util.Calendar getTime() {
        return time;
    }


    /**
     * Sets the time value for this Event.
     * 
     * @param time
     */
    public void setTime(java.util.Calendar time) {
        this.time = time;
    }


    /**
     * Gets the eventType value for this Event.
     * 
     * @return eventType
     */
    public org.enhydra.shark.asap.types.EventEventType getEventType() {
        return eventType;
    }


    /**
     * Sets the eventType value for this Event.
     * 
     * @param eventType
     */
    public void setEventType(org.enhydra.shark.asap.types.EventEventType eventType) {
        this.eventType = eventType;
    }


    /**
     * Gets the sourceKey value for this Event.
     * 
     * @return sourceKey
     */
    public org.apache.axis.types.URI getSourceKey() {
        return sourceKey;
    }


    /**
     * Sets the sourceKey value for this Event.
     * 
     * @param sourceKey
     */
    public void setSourceKey(org.apache.axis.types.URI sourceKey) {
        this.sourceKey = sourceKey;
    }


    /**
     * Gets the details value for this Event.
     * 
     * @return details
     */
    public java.lang.Object getDetails() {
        return details;
    }


    /**
     * Sets the details value for this Event.
     * 
     * @param details
     */
    public void setDetails(java.lang.Object details) {
        this.details = details;
    }


    /**
     * Gets the oldState value for this Event.
     * 
     * @return oldState
     */
    public org.enhydra.shark.asap.types.StateType getOldState() {
        return oldState;
    }


    /**
     * Sets the oldState value for this Event.
     * 
     * @param oldState
     */
    public void setOldState(org.enhydra.shark.asap.types.StateType oldState) {
        this.oldState = oldState;
    }


    /**
     * Gets the newState value for this Event.
     * 
     * @return newState
     */
    public org.enhydra.shark.asap.types.StateType getNewState() {
        return newState;
    }


    /**
     * Sets the newState value for this Event.
     * 
     * @param newState
     */
    public void setNewState(org.enhydra.shark.asap.types.StateType newState) {
        this.newState = newState;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Event)) return false;
        Event other = (Event) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.time==null && other.getTime()==null) || 
             (this.time!=null &&
              this.time.equals(other.getTime()))) &&
            ((this.eventType==null && other.getEventType()==null) || 
             (this.eventType!=null &&
              this.eventType.equals(other.getEventType()))) &&
            ((this.sourceKey==null && other.getSourceKey()==null) || 
             (this.sourceKey!=null &&
              this.sourceKey.equals(other.getSourceKey()))) &&
            ((this.details==null && other.getDetails()==null) || 
             (this.details!=null &&
              this.details.equals(other.getDetails()))) &&
            ((this.oldState==null && other.getOldState()==null) || 
             (this.oldState!=null &&
              this.oldState.equals(other.getOldState()))) &&
            ((this.newState==null && other.getNewState()==null) || 
             (this.newState!=null &&
              this.newState.equals(other.getNewState())));
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
        if (getTime() != null) {
            _hashCode += getTime().hashCode();
        }
        if (getEventType() != null) {
            _hashCode += getEventType().hashCode();
        }
        if (getSourceKey() != null) {
            _hashCode += getSourceKey().hashCode();
        }
        if (getDetails() != null) {
            _hashCode += getDetails().hashCode();
        }
        if (getOldState() != null) {
            _hashCode += getOldState().hashCode();
        }
        if (getNewState() != null) {
            _hashCode += getNewState().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Event.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Event"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("time");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Time"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eventType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "EventType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">>Event>EventType"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sourceKey");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "SourceKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyURI"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("details");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Details"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oldState");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "OldState"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "stateType"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("newState");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "NewState"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "stateType"));
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
