/*
 * Copyright 2001-2002,2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.enhydra.shark.wfxml.util;

import org.apache.axis.Constants;
import org.apache.axis.components.logger.LogFactory;
import org.apache.axis.description.FieldDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.DeserializationContext;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.DeserializerImpl;
import org.apache.axis.encoding.TypeMapping;
import org.apache.axis.message.MessageElement;
import org.apache.axis.message.SOAPHandler;
import org.apache.axis.utils.BeanPropertyDescriptor;
import org.apache.axis.utils.Messages;
import org.apache.axis.soap.SOAPConstants;
import org.apache.commons.logging.Log;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.apache.axis.encoding.ser.*;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.io.CharArrayWriter;
import java.util.Map;

/**
 * General purpose deserializer for an arbitrary java bean.
 *
 * @author Sam Ruby <rubys@us.ibm.com>
 * @author Rich Scheuerle <scheu@us.ibm.com>
 * @author Tom Jordahl <tomj@macromedia.com>
 */
public class BeanDeserializerShark extends DeserializerImpl implements Serializable
{
    protected static Log log =
        LogFactory.getLog(BeanDeserializerShark.class.getName());

    private final CharArrayWriter val = new CharArrayWriter();

    private BeanDeserializerShark[] additional = null;
    private boolean alreadyFailed [] = null;
    private String[] _addLocalNames = {
        "observerPropertiesGroup",
        "instancePropertiesGroup",
        "factoryPropertiesGroup",
        "registryPropertiesGroup"
    };

    QName xmlType;
    Class javaType;
    protected Map propertyMap = null;
    protected QName prevQName;

    /** Type metadata about this class for XML deserialization */
    protected TypeDesc typeDesc = null;

    // This counter is updated to deal with deserialize collection properties
    protected int collectionIndex = -1;

    protected SimpleDeserializer cacheStringDSer = null;
    protected QName cacheXMLType = null;

    // Construct BeanSerializer for the indicated class/qname
    public BeanDeserializerShark(Class javaType, QName xmlType) {
        this(javaType, xmlType, TypeDesc.getTypeDescForClass(javaType));
    }

    // Construct BeanDeserializer for the indicated class/qname and meta Data
    public BeanDeserializerShark(Class javaType, QName xmlType, TypeDesc typeDesc ) {
        this(javaType, xmlType, typeDesc,
             AltBeanDeserializerFactory.getProperties(javaType, typeDesc));
    }

    // Construct BeanDeserializer for the indicated class/qname and meta Data
    public BeanDeserializerShark(Class javaType, QName xmlType, TypeDesc typeDesc,
                            Map propertyMap ) {
        this.xmlType = xmlType;
        this.javaType = javaType;
        this.typeDesc = typeDesc;
        this.propertyMap = propertyMap;

        // create a value
        try {
            value=javaType.newInstance();
        } catch (Exception e) {
            // Don't process the exception at this point.
            // This is defered until the call to startElement
            // which will throw the exception.
        }
    }

    /**
     * startElement
     *
     * The ONLY reason that this method is overridden is so that
     * the object value can be set or a reasonable exception is thrown
     * indicating that the object cannot be created.  This is done
     * at this point so that it occurs BEFORE href/id processing.
     * @param namespace is the namespace of the element
     * @param localName is the name of the element
     * @param prefix is the prefix of the element
     * @param attributes are the attributes on the element...used to get the
     *                   type
     * @param context is the DeserializationContext
     */
    public void startElement(String namespace, String localName,
                             String prefix, Attributes attributes,
                             DeserializationContext context)
        throws SAXException
    {
        // Create the bean object if it was not already
        // created in the constructor.
        if (value == null) {
            try {
                value=javaType.newInstance();
            } catch (Exception e) {
                // Failed to create an object.
                throw new SAXException(Messages.getMessage("cantCreateBean00",
                                                            javaType.getName(),
                                                            e.toString()));
            }
        }
        // Invoke super.startElement to do the href/id processing.
        super.startElement(namespace, localName,
                           prefix, attributes, context);
    }

    /**
     * Deserializer interface called on each child element encountered in
     * the XML stream.
     * @param namespace is the namespace of the child element
     * @param localName is the local name of the child element
     * @param prefix is the prefix used on the name of the child element
     * @param attributes are the attributes of the child element
     * @param context is the deserialization context.
     * @return is a Deserializer to use to deserialize a child (must be
     * a derived class of SOAPHandler) or null if no deserialization should
     * be performed.
     */
    public SOAPHandler onStartChild(String namespace,
                                    String localName,
                                    String prefix,
                                    Attributes attributes,
                                    DeserializationContext context)
        throws SAXException
    {
        //System.err.println("onStartChild namespace:"+namespace);
        //System.err.println("onStartChild localName:"+localName);
        //System.err.println("onStartChild prefix   :"+prefix);
        if (xmlType.toString().endsWith("GetPropertiesRs")
           ||xmlType.toString().endsWith("SetPropertiesRs")) {
            //new Throwable("onStartChild namespace:"+namespace
            //    + "\nonStartChild localName:"+localName
            //    + "\nonStartChild prefix   :"+prefix).printStackTrace();
            int failures = 0;
            SOAPHandler sHnd = null;
            for (int n = 0; n < additional.length; ++n) {
                try {
                    if (alreadyFailed[n]) {
                        ++failures;
                        continue;
                    }
                    sHnd = additional[n].onStartChild(namespace, localName, prefix, attributes, context);
                } catch (Throwable t) {
                    //t.printStackTrace();
                    alreadyFailed[n] = true;
                    ++failures;
                }
            }
            if (additional.length == failures)
                throw new SAXException(Messages.getMessage("cantCreateBean00",
                                                           _addLocalNames[0],
                                                           ""));
            return sHnd;
        }

        handleMixedContent();

        BeanPropertyDescriptor propDesc = null;
        FieldDesc fieldDesc = null;

        SOAPConstants soapConstants = context.getSOAPConstants();
        String encodingStyle = context.getMessageContext().getEncodingStyle();
        boolean isEncoded = Constants.isSOAP_ENC(encodingStyle);

        QName elemQName = new QName(namespace, localName);
        // The collectionIndex needs to be reset for Beans with multiple arrays
        if ((prevQName == null) || (!prevQName.equals(elemQName))) {
            collectionIndex = -1;
        }
        prevQName = elemQName;

        if (typeDesc != null) {
            // Lookup the name appropriately (assuming an unqualified
            // name for SOAP encoding, using the namespace otherwise)
            String fieldName = typeDesc.getFieldNameForElement(elemQName,
                                                               isEncoded);
            propDesc = (BeanPropertyDescriptor)propertyMap.get(fieldName);
            fieldDesc = typeDesc.getFieldByName(fieldName);
        }

        if (propDesc == null) {
            // look for a field by this name.
            propDesc = (BeanPropertyDescriptor) propertyMap.get(localName);
        }

        // try and see if this is an xsd:any namespace="##any" element before
        // reporting a problem
        if (propDesc == null) {
            // try to put unknown elements into a SOAPElement property, if
            // appropriate
            propDesc = getAnyPropertyDesc();
            if (propDesc != null) {
                try {
                    MessageElement [] curElements = (MessageElement[])propDesc.get(value);
                    int length = 0;
                    if (curElements != null) {
                        length = curElements.length;
                    }
                    MessageElement [] newElements = new MessageElement[length + 1];
                    if (curElements != null) {
                        System.arraycopy(curElements, 0,
                                         newElements, 0, length);
                    }
                    MessageElement thisEl = context.getCurElement();

                    newElements[length] = thisEl;
                    propDesc.set(value, newElements);
                    // if this is the first pass through the MessageContexts
                    // make sure that the correct any element is set,
                    // that is the child of the current MessageElement, however
                    // on the first pass this child has not been set yet, so
                    // defer it to the child SOAPHandler
                    if (!localName.equals(thisEl.getName())) {
                        return new SOAPHandler(newElements, length);
                    }
                    return new SOAPHandler();
                } catch (Exception e) {
                    throw new SAXException(e);
                }
            }
        }


        if (propDesc == null) {
            // No such field
            throw new SAXException(
                    Messages.getMessage("badElem00", javaType.getName(),
                                         localName));
        }

        // Get the child's xsi:type if available
        QName childXMLType = context.getTypeFromAttributes(namespace,
                                                            localName,
                                                            attributes);

        String href = attributes.getValue(soapConstants.getAttrHref());

        // If no xsi:type or href, check the meta-data for the field
        if (childXMLType == null && fieldDesc != null && href == null) {
            childXMLType = fieldDesc.getXmlType();
        }

        // Get Deserializer for child, default to using DeserializerImpl
        Deserializer dSer = getDeserializer(childXMLType, propDesc.getType(),
                                            href,
                                            context);

        // It is an error if the dSer is not found - the only case where we
        // wouldn't have a deserializer at this point is when we're trying
        // to deserialize something we have no clue about (no good xsi:type,
        // no good metadata).
        if (dSer == null) {
            dSer = context.getDeserializerForClass(propDesc.getType());
        }

        // Fastpath nil checks...
        if (context.isNil(attributes)) {
            if (propDesc != null && propDesc.isIndexed()) {
                if (!((dSer != null) && (dSer instanceof ArrayDeserializer)) ||
                        propDesc.getType().isArray()) {
                    collectionIndex++;
                    dSer.registerValueTarget(new BeanPropertyTarget(value,
                            propDesc, collectionIndex));
                    addChildDeserializer(dSer);
                    return (SOAPHandler)dSer;
                }
            }
            return null;
        }

        if (dSer == null) {
            throw new SAXException(Messages.getMessage("noDeser00",
                                                       childXMLType.toString()));
        }

        // Register value target
        if (propDesc.isWriteable()) {
            // If this is an indexed property, and the deserializer we found
            // was NOT the ArrayDeserializer, this is a non-SOAP array:
            // <bean>
            //   <field>value1</field>
            //   <field>value2</field>
            // ...
            // In this case, we want to use the collectionIndex and make sure
            // the deserialized value for the child element goes into the
            // right place in the collection.
            if (propDesc.isIndexed() && (
                    !(dSer instanceof ArrayDeserializer) ||
                    propDesc.getType().isArray())) {
                    collectionIndex++;
                    dSer.registerValueTarget(new BeanPropertyTarget(value,
                                                    propDesc, collectionIndex));
            } else {
                // If we're here, the element maps to a single field value,
                // whether that be a "basic" type or an array, so use the
                // normal (non-indexed) BeanPropertyTarget form.
                collectionIndex = -1;
                dSer.registerValueTarget(new BeanPropertyTarget(value,
                                                                propDesc));
            }
        }

        // Let the framework know that we need this deserializer to complete
        // for the bean to complete.
        addChildDeserializer(dSer);

        return (SOAPHandler)dSer;
    }

    /**
     * Get a BeanPropertyDescriptor which indicates where we should
     * put extensibility elements (i.e. XML which falls under the
     * auspices of an &lt;xsd:any&gt; declaration in the schema)
     *
     * @return an appropriate BeanPropertyDescriptor, or null
     */
    public BeanPropertyDescriptor getAnyPropertyDesc() {
        if (typeDesc == null)
            return null;

       return typeDesc.getAnyDesc();
    }

    /**
     * Set the bean properties that correspond to element attributes.
     *
     * This method is invoked after startElement when the element requires
     * deserialization (i.e. the element is not an href and the value is not
     * nil.)
     * @param namespace is the namespace of the element
     * @param localName is the name of the element
     * @param prefix is the prefix of the element
     * @param attributes are the attributes on the element...used to get the
     *                   type
     * @param context is the DeserializationContext
     */
    public void onStartElement(String namespace, String localName,
                               String prefix, Attributes attributes,
                               DeserializationContext context)
            throws SAXException {

        if (xmlType.toString().endsWith("GetPropertiesRs")
           ||xmlType.toString().endsWith("SetPropertiesRs")) {
            //new Throwable("onStartElement namespace:"+namespace
            //    + "\nonStartElement localName:"+localName
            //    + "\nonStartElement prefix   :"+prefix).printStackTrace();
            String pp = xmlType.toString().substring(0, xmlType.toString().length() - "GetPropertiesRs".length());
            try {
                additional = new BeanDeserializerShark[4];
                additional[0] = new BeanDeserializerShark(Class.forName("org.enhydra.shark.asap.types.ObserverPropertiesGroup"),
                                                     new QName(pp + _addLocalNames[0]));
                additional[1] = new BeanDeserializerShark(Class.forName("org.enhydra.shark.asap.types.InstancePropertiesGroup"),
                                                     new QName(pp + _addLocalNames[1]));
                additional[2] = new BeanDeserializerShark(Class.forName("org.enhydra.shark.asap.types.FactoryPropertiesGroup"),
                                                     new QName(pp + _addLocalNames[2]));
                additional[3] = new BeanDeserializerShark(Class.forName("org.enhydra.shark.wfxml.types.RegistryPropertiesGroup"),
                                                     new QName(pp + _addLocalNames[3]));
                } catch (Throwable t) {
                t.printStackTrace();
                throw new SAXException(t.getMessage());
            }
            alreadyFailed = new boolean[additional.length];
            int failures = 0;
            for (int n = 0; n < additional.length; ++n) {
                try {
                    alreadyFailed[n] = false;
                    additional[n].startElement(namespace, _addLocalNames[n], prefix, attributes, context);
                } catch (Throwable t) {
                    t.printStackTrace();
                    alreadyFailed[n] = true;
                    ++failures;
                }
            }
            if (additional.length == failures)
                throw new SAXException(Messages.getMessage("cantCreateBean00",
                                                           _addLocalNames[0],
                                                           ""));
        }

        // The value should have been created or assigned already.
        // This code may no longer be needed.
        if (value == null) {
            // create a value
            try {
                value=javaType.newInstance();
            } catch (Exception e) {
                throw new SAXException(Messages.getMessage("cantCreateBean00",
                                                            javaType.getName(),
                                                            e.toString()));
            }
        }

        // If no type description meta data, there are no attributes,
        // so we are done.
        if (typeDesc == null)
            return;

        // loop through the attributes and set bean properties that
        // correspond to attributes
        for (int i=0; i < attributes.getLength(); i++) {
            QName attrQName = new QName(attributes.getURI(i),
                                        attributes.getLocalName(i));
            String fieldName = typeDesc.getFieldNameForAttribute(attrQName);
            if (fieldName == null)
                continue;

            FieldDesc fieldDesc = typeDesc.getFieldByName(fieldName);

            // look for the attribute property
            BeanPropertyDescriptor bpd =
                    (BeanPropertyDescriptor) propertyMap.get(fieldName);
            if (bpd != null) {
                if (!bpd.isWriteable() || bpd.isIndexed() ) continue ;

                // Get the Deserializer for the attribute
                Deserializer dSer = getDeserializer(fieldDesc.getXmlType(),
                                                    bpd.getType(),
                                                    null,
                                                    context);
                if (dSer == null) {
                    dSer = context.getDeserializerForClass(bpd.getType());
                }
                if (dSer == null)
                    throw new SAXException(
                            Messages.getMessage("unregistered00",
                                                 bpd.getType().toString()));

                if (! (dSer instanceof SimpleDeserializer))
                    throw new SAXException(
                            Messages.getMessage("AttrNotSimpleType00",
                                                 bpd.getName(),
                                                 bpd.getType().toString()));

                // Success!  Create an object from the string and set
                // it in the bean
                try {
                    dSer.onStartElement(namespace, localName, prefix,
                                        attributes, context);
                    Object val = ((SimpleDeserializer)dSer).
                        makeValue(attributes.getValue(i));
                    bpd.set(value, val);
                } catch (Exception e) {
                    throw new SAXException(e);
                }

            } // if
        } // attribute loop
    }

    /**
     * Get the Deserializer for the attribute or child element.
     * @param xmlType QName of the attribute/child element or null if not known.
     * @param javaType Class of the corresponding property
     * @param href String is the value of the href attribute, which is used
     *             to determine whether the child element is complete or an
     *             href to another element.
     * @param context DeserializationContext
     * @return Deserializer or null if not found.
    */
    protected Deserializer getDeserializer(QName xmlType,
                                           Class javaType,
                                           String href,
                                           DeserializationContext context) {
        if (javaType.isArray()) {
            context.setDestinationClass(javaType);
        }
        // See if we have a cached deserializer
        if (cacheStringDSer != null) {
            if (String.class.equals(javaType) &&
                href == null &&
                (cacheXMLType == null && xmlType == null ||
                 cacheXMLType != null && cacheXMLType.equals(xmlType))) {
                cacheStringDSer.reset();
                return cacheStringDSer;
            }
        }

        Deserializer dSer = null;

        if (xmlType != null && href == null) {
            // Use the xmlType to get the deserializer.
            dSer = context.getDeserializerForType(xmlType);
        } else {
            // If the xmlType is not set, get a default xmlType
            TypeMapping tm = context.getTypeMapping();
            QName defaultXMLType = tm.getTypeQName(javaType);
            // If there is not href, then get the deserializer
            // using the javaType and default XMLType,
            // If there is an href, the create the generic
            // DeserializerImpl and set its default type (the
            // default type is used if the href'd element does
            // not have an xsi:type.
            if (href == null) {
                dSer = context.getDeserializer(javaType, defaultXMLType);
            } else {
                dSer = new DeserializerImpl();
                context.setDestinationClass(javaType);
                dSer.setDefaultType(defaultXMLType);
            }
        }
        if (javaType.equals(String.class) &&
            dSer instanceof SimpleDeserializer) {
            cacheStringDSer = (SimpleDeserializer) dSer;
            cacheXMLType = xmlType;
        }
        return dSer;
    }

    public void characters(char[] chars, int start, int end) throws SAXException {
        val.write(chars, start, end);
    }

    public void onEndElement(String namespace, String localName,
                             DeserializationContext context) throws SAXException {

        if (xmlType.toString().endsWith("GetPropertiesRs")
           ||xmlType.toString().endsWith("SetPropertiesRs")) {
            for (int n = 0; n < additional.length; ++n) {
                if (!alreadyFailed[n]) {
                    BeanPropertyDescriptor propDesc = (BeanPropertyDescriptor) propertyMap.get(_addLocalNames[n]);
                    System.err.println("localName:"+_addLocalNames[n]+", propDesc:"+propDesc);
                    try {
                        propDesc.set(value, additional[n].getValue());
                    } catch (Throwable t) {
                        t.printStackTrace();
                        throw new SAXException(t.getMessage());
                    }
                }
            }

        }

        handleMixedContent();
    }

    protected void handleMixedContent() throws SAXException {
        BeanPropertyDescriptor propDesc = getAnyPropertyDesc();
        if (propDesc == null || val.size() == 0) {
            return;
        }
        String textValue = val.toString().trim();
        val.reset();
        if (textValue.length() == 0) {
            return;
        }
        try {
            MessageElement[] curElements = (MessageElement[]) propDesc.get(value);
            int length = 0;
            if (curElements != null) {
                length = curElements.length;
            }
            MessageElement[] newElements = new MessageElement[length + 1];
            if (curElements != null) {
                System.arraycopy(curElements, 0,
                        newElements, 0, length);
            }
            MessageElement thisEl = new MessageElement(new org.apache.axis.message.Text(textValue));
            newElements[length] = thisEl;
            propDesc.set(value, newElements);
        } catch (Exception e) {
            throw new SAXException(e);
        }
    }
}
