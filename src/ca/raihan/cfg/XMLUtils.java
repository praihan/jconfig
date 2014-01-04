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

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;

import javax.xml.transform.dom.DOMSource;

import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

/**
 *
 * @author Pranjal Raihan
 */
final class XMLUtils {
    
    static final int DEFAULT_INDENTATION = 4;
    
    
    
    
    private XMLUtils() {
        throw new IllegalAccessError(getClass().getName());
    }
    
    
    
    
    public static String toPrettifiedXML(Document document) throws 
            ConfigException {
        return toPrettifiedXML(
                document, DEFAULT_INDENTATION);
    }
    
    public static String toPrettifiedXML(Document document, int indent) 
            throws ConfigException {
        Contract.nonNull(document);
        Contract.require(indent >= 0);
        try {
            Transformer tf = TransformerFactory.newInstance().newTransformer();
            tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", 
                    Integer.toString(indent));
            tf.setOutputProperty(OutputKeys.STANDALONE, "yes");
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            tf.transform(new DOMSource(document), new StreamResult(out));
            return new String(out.toByteArray());
        } catch (TransformerException ex) {
            throw new ConfigException(ex);
        }
    }
    
    public static void writeDocument(Document document, OutputStream out) 
            throws TransformerException {
        writeDocument(document, out, DEFAULT_INDENTATION);
    }
    
    public static void writeDocument(Document document, OutputStream out, 
            int indent) throws TransformerException {
        Contract.nonNull(document);
        Contract.require(indent >= 0);
        Contract.nonNull(out);
        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tf.setOutputProperty(OutputKeys.INDENT, "yes"); 
        tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", 
                Integer.toString(indent));
        tf.transform(new DOMSource(document), new StreamResult(out));
    }
    
}
