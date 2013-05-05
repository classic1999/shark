/**
 * Request.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package org.enhydra.shark.asap.types;

public class Request  implements java.io.Serializable {
    private org.apache.axis.types.URI senderKey;
    private org.apache.axis.types.URI receiverKey;
    private org.enhydra.shark.asap.types.YesNoIfError responseRequired;
    private java.lang.String requestID;

    public Request() {
    }

    public Request(
           org.apache.axis.types.URI senderKey,
           org.apache.axis.types.URI receiverKey,
           org.enhydra.shark.asap.types.YesNoIfError responseRequired,
           java.lang.String requestID) {
           this.senderKey = senderKey;
           this.receiverKey = receiverKey;
           this.responseRequired = responseRequired;
           this.requestID = requestID;
    }


    /**
     * Gets the senderKey value for this Request.
     * 
     * @return senderKey
     */
    public org.apache.axis.types.URI getSenderKey() {
        return senderKey;
    }


    /**
     * Sets the senderKey value for this Request.
     * 
     * @param senderKey
     */
    public void setSenderKey(org.apache.axis.types.URI senderKey) {
        this.senderKey = senderKey;
    }


    /**
     * Gets the receiverKey value for this Request.
     * 
     * @return receiverKey
     */
    public org.apache.axis.types.URI getReceiverKey() {
        return receiverKey;
    }


    /**
     * Sets the receiverKey value for this Request.
     * 
     * @param receiverKey
     */
    public void setReceiverKey(org.apache.axis.types.URI receiverKey) {
        this.receiverKey = receiverKey;
    }


    /**
     * Gets the responseRequired value for this Request.
     * 
     * @return responseRequired
     */
    public org.enhydra.shark.asap.types.YesNoIfError getResponseRequired() {
        return responseRequired;
    }


    /**
     * Sets the responseRequired value for this Request.
     * 
     * @param responseRequired
     */
    public void setResponseRequired(org.enhydra.shark.asap.types.YesNoIfError responseRequired) {
        this.responseRequired = responseRequired;
    }


    /**
     * Gets the requestID value for this Request.
     * 
     * @return requestID
     */
    public java.lang.String getRequestID() {
        return requestID;
    }


    /**
     * Sets the requestID value for this Request.
     * 
     * @param requestID
     */
    public void setRequestID(java.lang.String requestID) {
        this.requestID = requestID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Request)) return false;
        Request other = (Request) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.senderKey==null && other.getSenderKey()==null) || 
             (this.senderKey!=null &&
              this.senderKey.equals(other.getSenderKey()))) &&
            ((this.receiverKey==null && other.getReceiverKey()==null) || 
             (this.receiverKey!=null &&
              this.receiverKey.equals(other.getReceiverKey()))) &&
            ((this.responseRequired==null && other.getResponseRequired()==null) || 
             (this.responseRequired!=null &&
              this.responseRequired.equals(other.getResponseRequired()))) &&
            ((this.requestID==null && other.getRequestID()==null) || 
             (this.requestID!=null &&
              this.requestID.equals(other.getRequestID())));
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
        if (getSenderKey() != null) {
            _hashCode += getSenderKey().hashCode();
        }
        if (getReceiverKey() != null) {
            _hashCode += getReceiverKey().hashCode();
        }
        if (getResponseRequired() != null) {
            _hashCode += getResponseRequired().hashCode();
        }
        if (getRequestID() != null) {
            _hashCode += getRequestID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Request.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Request"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("senderKey");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "SenderKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyURI"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("receiverKey");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "ReceiverKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyURI"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("responseRequired");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "ResponseRequired"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "YesNoIfError"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "RequestID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
