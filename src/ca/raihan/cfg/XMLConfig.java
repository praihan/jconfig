/*******************************************************************************
 * Copyright 2014 See AUTHORS file.
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
 ******************************************************************************/

package ca.raihan.cfg;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * XML-based implementation of {@code Config}.
 * 
 * @author Pranjal Raihan
 */
public final strictfp class XMLConfig extends PrivateConfigBase {
    
    /**
     * The root element name
     */
    static final String ROOT_PREFIX = "Config";
    
    /**
     * Exception format
     */
    static String UNEXPECTED_DUPLICATE_FORMAT = 
            "Unexpected duplicate key \"%s\" in Repository \"%s\"";
    
    /**
     * Exception format
     */
    static String MALFORMED_KEY_ATTR_FORMAT = 
            "The \"key\" attribute in Repository: \"%s\" is either "
            + "non-existent or malformed";
    
    /**
     * Exception format
     */
    static String MALFORMED_VALUE_ATTR_FORMAT = 
            "The \"value\" attribute for key \"%s\" in Repository: \"%s\" is "
            + "either non-existent or malformed";
    
    /**
     * Exception format
     */
    static String MALFORMED_TYPE_ATTR_FORMAT = 
            "The \"type\" attribute for key \"%s\" in Repository: \"%s\" is "
            + "either non-existent or malformed";
    
    
    /**
     * The base XML document
     */
    Document xmlDoc;
    
    
    /**
     * The XML element corresponding to {@link Repository#NUMBER}
     */
    Element numberElements;
    
    /**
     * The XML element corresponding to {@link Repository#STRING}
     */
    Element stringElements;
    
    /**
     * The XML element corresponding to {@link Repository#BOOLEAN}
     */
    Element booleanElements;
    
    
    
    
    /**
     * private constructor, does nothing
     */
    private XMLConfig() {
    }
    
    
    
    
    /**
     * Returns an uninitialized {@code XMLConfig}.
     * 
     * @return an uninitialized {@code XMLConfig}
     */
    static XMLConfig uninitialized() {
        return new XMLConfig();
    }
    
    /**
     * Returns an {@code XMLConfig} without any XML bindings.
     * 
     * @return an {@code XMLConfig} without any XML bindings
     */
    static XMLConfig naked() {
        XMLConfig rv = uninitialized();
        
        rv.flushedBooleanElements = new HashMap<String, Boolean>();
        rv.pendingBooleanElements = new HashMap<String, Boolean>();
        rv.deletedBooleanKeys = new ArrayList<String>();
        
        rv.flushedStringElements = new HashMap<String, String>();
        rv.pendingStringElements = new HashMap<String, String>();
        rv.deletedStringKeys = new ArrayList<String>();
        
        rv.flushedNumberElements = new HashMap<String, Number>();
        rv.pendingNumberElements = new HashMap<String, Number>();
        rv.deletedNumberKeys = new ArrayList<String>();
        
        rv.booleanRepoEvent = 
                new ConfigEvent<BooleanRepoContext>(rv.eventKey);
        rv.stringRepoEvent = 
                new ConfigEvent<StringRepoContext>(rv.eventKey);
        rv.numberRepoEvent = 
                new ConfigEvent<NumberRepoContext>(rv.eventKey);
        
        return rv;
    }
    
    /**
     * Returns a new {@code XMLConfig} with no entries.
     * 
     * @return a new {@code XMLConfig} with no entries
     */
    static XMLConfig empty() {
        XMLConfig rv = naked();
        Document xmlDoc;
        try {
            xmlDoc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder().newDocument();
        } catch (ParserConfigurationException ex) {
            throw new ConfigException(ex);
        }
        
        Element rootElement = xmlDoc.createElement(ROOT_PREFIX);
        
        Element numberElements = xmlDoc.createElement(
                Repository.NUMBER.getName());
        rootElement.appendChild(numberElements);
        
        Element stringElements = xmlDoc.createElement(
                Repository.STRING.getName());
        rootElement.appendChild(stringElements);
        
        Element booleanElements = xmlDoc.createElement(
                Repository.BOOLEAN.getName());
        rootElement.appendChild(booleanElements);
        
        xmlDoc.appendChild(rootElement);
        xmlDoc.setXmlStandalone(true);
        
        rv.stringElements = stringElements;
        rv.numberElements = numberElements;
        rv.booleanElements = booleanElements;
        rv.xmlDoc = xmlDoc;
        
        return rv;
    }
    
    /**
     * Reads and parses XML from a {@code File}.
     * 
     * @param file the {@code File} to parse from
     * 
     * @return the parsed {@code XMLConfig}
     * 
     * @throws ConfigException if the parsing failed
     */
    static XMLConfig read(File file) throws ConfigException {
        XMLConfig rv = naked();
        try {
            Document xmlDoc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder().parse(file);
            return readAndSetupEntriesImpl(rv, xmlDoc);
        } catch (SAXException ex) {
            throw new ConfigException(ex);
        } catch (ParserConfigurationException ex) {
            throw new ConfigException(ex);
        } catch (IOException ex) {
            throw new ConfigException(ex);
        }
    }
    
    /**
     * Reads and parses XML from a {@code InputStream}.
     * 
     * @param in the {@code InputStream} to parse from
     * 
     * @return the parsed {@code XMLConfig}
     * 
     * @throws ConfigException if the parsing failed
     */
    static XMLConfig read(InputStream in) throws ConfigException {
        Contract.nonNull(in);
        XMLConfig rv = naked();
        try {
            Document xmlDoc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder().parse(in);
            return readAndSetupEntriesImpl(rv, xmlDoc);
        } catch (SAXException ex) {
            throw new ConfigException(ex);
        } catch (ParserConfigurationException ex) {
            throw new ConfigException(ex);
        } catch (IOException ex) {
            throw new ConfigException(ex);
        }
    }
    
    /**
     * Reads and parses XML from a {@code Reader}.
     * 
     * @param reader the {@code Reader} to parse from
     * 
     * @return the parsed {@code XMLConfig}
     * 
     * @throws ConfigException if the parsing failed
     */
    static XMLConfig read(Reader reader) throws ConfigException {
        Contract.nonNull(reader);
        XMLConfig rv = naked();
        try {
            Document xmlDoc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder().parse(new InputSource(reader));
            return readAndSetupEntriesImpl(rv, xmlDoc);
        } catch (SAXException ex) {
            throw new ConfigException(ex);
        } catch (ParserConfigurationException ex) {
            throw new ConfigException(ex);
        } catch (IOException ex) {
            throw new ConfigException(ex);
        }
    }
    
    /**
     * Reads and parses XML from a {@code String}.
     * 
     * @param strXML the {@code String} to parse from
     * 
     * @return the parsed {@code XMLConfig}
     * 
     * @throws ConfigException if the parsing failed
     */
    static XMLConfig parse(String strXML) throws ConfigException {
        Contract.nonNull(strXML);
        return read((Reader) new StringReader(strXML));
    }
    
    /**
     * Sets up the document for use.
     * 
     * @param rv the {@code XMLConfig} to set up
     * @param xmlDoc
     * 
     * @return 
     */
    static XMLConfig readAndSetupEntriesImpl(final XMLConfig rv, 
            final Document xmlDoc) {
        try {
            
            xmlDoc.setXmlStandalone(true);
            rv.xmlDoc = xmlDoc;
            Element root = xmlDoc.getDocumentElement();
            
            if (root == null) {
                throw new ConfigException("missing root element");
            }
            
            NodeList nodeList;
            Element repoElement;
            Node repoNode;
            NodeList elementNodes;
            int len;
            
            for (Repository repository : Repository.values()) {
                nodeList = root.getElementsByTagName(
                        repository.getName());
                len = nodeList.getLength();
                if (len == 0) {
                    throw Configs.newMissingRepoException(repository);
                } else if (len > 1) {
                    throw Configs.newDuplicateRepoException(repository);
                }
                repoNode = nodeList.item(0);
                if (!(repoNode instanceof Element)) {
                    throw newInvalidNodeClassException(Element.class);
                }
                repoElement = (Element) repoNode;
                
                switch (repository) {
                    case NUMBER:
                        rv.numberElements = repoElement;
                        break;
                    case BOOLEAN:
                        rv.booleanElements = repoElement;
                        break;
                    case STRING:
                        rv.stringElements = repoElement;
                        break;
                }
                
                elementNodes = repoElement.getElementsByTagName(ENTRY_FLAG);
                len = elementNodes.getLength();
                for (int j = 0; j < len; ++j) {
                    Node n = elementNodes.item(j);
                    if (n instanceof Element) {
                        Element e = (Element) n;
                        Attr nameAttr = e.getAttributeNode(KEY_FLAG);
                        if (nameAttr == null) {
                            throw newMalformedKeyAttrException(repository);
                        }
                        String key = nameAttr.getValue();
                        Attr valueAttr = e.getAttributeNode(VALUE_FLAG);
                        if (valueAttr == null) {
                            throw newMalformedValueAttrException(
                                    key, repository);
                        }
                        String value = valueAttr.getValue();
                        // Number uses a different format
                        if (repository == Repository.NUMBER) {
                            Attr typeAttr = e.getAttributeNode(
                                    TYPE_FLAG);
                            if (typeAttr == null) {
                                throw newMalformedValueAttrException(
                                        key, repository);
                            }
                            rv.flushedNumberElements.put(
                                    key, 
                                    Configs.parseNumberFromType(
                                            value, 
                                            Configs.numberTypeValueOf(
                                                    typeAttr.getValue())));
                            continue;
                        }
                        
                        if (repository == Repository.BOOLEAN) {
                            rv.flushedBooleanElements.put(
                                    key, Configs.parseBoolean(value));
                        } else {
                            rv.flushedStringElements.put(key, value);
                        }
                        
                    }
                }
            }
            
        } catch (Configs.BooleanParsingException ex) {
            
            throw new ConfigException(ex);
            
        } catch (NumberFormatException ex) {
            
            throw new ConfigException(ex);
            
        } catch (DOMException ex) {
            
            throw new ConfigException(ex);
            
        }
        return rv;
    }
    
    
    
    
    void _serializeBool(String key, boolean value) {
        NodeList elements = booleanElements.getElementsByTagName(ENTRY_FLAG);
        
        final int size = elements.getLength();
        
        for (int i = 0; i < size; ++i) {
            Element e = (Element) elements.item(i);
            Attr nameAttr = e.getAttributeNode(KEY_FLAG);
            if (nameAttr == null) {
                throw newMalformedKeyAttrException(Repository.BOOLEAN);
            }
            if (key.equals(nameAttr.getValue())) {
                Attr valueAttr = e.getAttributeNode(VALUE_FLAG);
                if (valueAttr == null) {
                    throw newMalformedValueAttrException(key, 
                            Repository.BOOLEAN);
                }
                valueAttr.setValue(Boolean.toString(value));
                return;
            }
        }
        
        // no existing element found
        Element element = xmlDoc.createElement(ENTRY_FLAG);
        element.setAttribute(KEY_FLAG, key);
        element.setAttribute(VALUE_FLAG, Boolean.toString(value));
        booleanElements.appendChild(element);
    }
    
    void _deleteBool(String key) {
        NodeList elements = booleanElements.getElementsByTagName(ENTRY_FLAG);
        try {
            final int len = elements.getLength();
            for (int i = 0; i < len; ++i) {
                Element e = (Element) elements.item(i);
                String actualKey = e.getAttribute(KEY_FLAG);
                if (key.equals(actualKey)) {
                    booleanElements.removeChild(e);
                }
            }
        } catch (DOMException ex) {
            throw new ConfigException(ex);
        }
    }
    
    void _serializeString(String key, String value) {
        NodeList elements = stringElements.getElementsByTagName(ENTRY_FLAG);    
        
        final int size = elements.getLength();
        
        for (int i = 0; i < size; ++i) {
            Element e = (Element) elements.item(i);
            Attr nameAttr = e.getAttributeNode(KEY_FLAG);
            if (nameAttr == null) {
                throw newMalformedKeyAttrException(Repository.STRING);
            }
            if (key.equals(nameAttr.getValue())) {
                Attr valueAttr = e.getAttributeNode(VALUE_FLAG);
                if (valueAttr == null) {
                    throw newMalformedValueAttrException(key, 
                            Repository.STRING);
                }
                valueAttr.setValue(value);
                return;
            }
        }
        
        // no existing element found
        Element element = xmlDoc.createElement(ENTRY_FLAG);
        element.setAttribute(KEY_FLAG, key);
        element.setAttribute(VALUE_FLAG, value);
        stringElements.appendChild(element);
    }
    
    void _deleteString(String key) {
        NodeList elements = stringElements.getElementsByTagName(ENTRY_FLAG);
        try {
            final int len = elements.getLength();
            for (int i = 0; i < len; ++i) {
                Element e = (Element) elements.item(i);
                String actualKey = e.getAttribute(KEY_FLAG);
                if (key.equals(actualKey)) {
                    stringElements.removeChild(e);
                }
            }
        } catch (DOMException ex) {
            throw new ConfigException(ex);
        }
    }
    
    void _serializeNumber(String key, Number value) {
        NodeList elements = numberElements.getElementsByTagName(ENTRY_FLAG);    
        
        final int size = elements.getLength();
        
        for (int i = 0; i < size; ++i) {
            Element e = (Element) elements.item(i);
            Attr nameAttr = e.getAttributeNode(KEY_FLAG);
            if (nameAttr == null) {
                throw newMalformedKeyAttrException(Repository.NUMBER);
            }
            if (key.equals(nameAttr.getValue())) {
                Attr valueAttr = e.getAttributeNode(VALUE_FLAG);
                if (valueAttr == null) {
                    throw newMalformedValueAttrException(key, 
                            Repository.NUMBER);
                }
                Attr typeAttr = e.getAttributeNode(TYPE_FLAG);
                if (typeAttr == null) {
                    throw newMalformedTypeAttrException(key, Repository.NUMBER);
                }
                typeAttr.setValue(Configs.resolveNumberType(value).name());
                valueAttr.setValue(value.toString());
                return;
            }
        }
        // no existing element found
        Element element = xmlDoc.createElement(ENTRY_FLAG);
        element.setAttribute(KEY_FLAG, key);
        element.setAttribute(VALUE_FLAG, value.toString());
        element.setAttribute(TYPE_FLAG, 
                Configs.resolveNumberType(value).name());
        numberElements.appendChild(element);
        
    }
    
    void _deleteNumber(String key) {
        NodeList elements = numberElements.getElementsByTagName(ENTRY_FLAG);
        try {
            final int len = elements.getLength();
            for (int i = 0; i < len; ++i) {
                Element e = (Element) elements.item(i);
                String actualKey = e.getAttribute(KEY_FLAG);
                if (key.equals(actualKey)) {
                    numberElements.removeChild(e);
                }
            }
        } catch (DOMException ex) {
            throw new ConfigException(ex);
        }
    }
    
    
    
    
    public String toCanonical() {
        return XMLUtils.toPrettifiedXML(xmlDoc);
    }
    
    
    
    
    static ConfigException newMalformedKeyAttrException(Repository r) {
        return new ConfigException(String.format(
                MALFORMED_KEY_ATTR_FORMAT, r.getName()));
    }
    
    static ConfigException newMalformedValueAttrException(String key, 
            Repository r) {
        return new ConfigException(String.format(
                MALFORMED_VALUE_ATTR_FORMAT, key, r.getName()));
    }
    
    static ConfigException newMalformedTypeAttrException(String key, 
            Repository r) {
        return new ConfigException(String.format(
                MALFORMED_TYPE_ATTR_FORMAT, key, r.getName()));
    }
    
    static ConfigException newInvalidNodeClassException(
            Class<? extends Node> nodeType) {
        return new ConfigException("Expected node of type: " + nodeType);
    }
    
}
