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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Pranjal Raihan
 */
public final strictfp class JSONConfig extends PrivateConfigBase {
    
    static final int DEFAULT_INDENT = 4;
    
    
    JSONObject rootElement;
    
    
    JSONObject numberElements;
    
    JSONObject stringElements;
    
    JSONObject booleanElements;
    
    
    
    
    private JSONConfig() {
    }
    
    static JSONConfig uninitialized() {
        return new JSONConfig();
    }
    
    static JSONConfig naked() {
        JSONConfig rv = uninitialized();
        
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
    
    static JSONConfig empty() {
        JSONConfig rv = naked();
        
        JSONObject rootElement = new JSONObject();
        
        JSONObject numberElements = new JSONObject();
        rootElement.put(Repository.NUMBER.getName(), numberElements);
        
        JSONObject booleanElements = new JSONObject();
        rootElement.put(Repository.BOOLEAN.getName(), booleanElements);
        
        JSONObject stringElements = new JSONObject();
        rootElement.put(Repository.STRING.getName(), stringElements);
        
        rv.stringElements = stringElements;
        rv.numberElements = numberElements;
        rv.booleanElements = booleanElements;
        rv.rootElement = rootElement;
        
        return rv;
    }
    
    static JSONConfig read(File file) throws ConfigException {
        Contract.nonNull(file);
        JSONConfig rv = naked();
        InputStreamReader in = null;
        try {
            
            in = new InputStreamReader(new FileInputStream(file), UTF_8);
            JSONObject doc = new JSONObject(new JSONTokener(in));
            return readAndSetupEntriesImpl(rv, doc);
            
        } catch (UnsupportedEncodingException ex) {
            
            throw new ConfigException(ex);
            
        } catch (FileNotFoundException ex) {
            
            throw new ConfigException(ex);
            
        } catch (JSONException ex) {
            
            throw new ConfigException(ex);
            
        } finally {
            
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                }
            }
            
        }
    }
    
    static JSONConfig read(InputStream in) throws ConfigException {
        Contract.nonNull(in);
        JSONConfig rv = naked();
        try {
            
            JSONObject doc = new JSONObject(new JSONTokener(in));
            return readAndSetupEntriesImpl(rv, doc);
            
        } catch (JSONException ex) {
            
            throw new ConfigException(ex);
            
        } finally {
            
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                }
            }
            
        }
    }
    
    static JSONConfig read(Reader reader) throws ConfigException {
        Contract.nonNull(reader);
        JSONConfig rv = naked();
        try {
            
            JSONObject doc = new JSONObject(new JSONTokener(reader));
            return readAndSetupEntriesImpl(rv, doc);
            
        } catch (JSONException ex) {
            
            throw new ConfigException(ex);
            
        } finally {
            
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                }
            }
            
        }
    }
    
    static JSONConfig parse(String strXML) throws ConfigException {
        Contract.nonNull(strXML);
        JSONConfig rv = naked();
        try {
            
            JSONObject doc = new JSONObject(new JSONTokener(strXML));
            return readAndSetupEntriesImpl(rv, doc);
            
        } catch (JSONException ex) {
            
            throw new ConfigException(ex);
            
        }
    }
    
    static JSONConfig readAndSetupEntriesImpl(final JSONConfig rv, 
            final JSONObject json) {
        try {
            
            rv.rootElement = json;
            
            JSONObject booleanElements = json.getJSONObject(
                    Repository.BOOLEAN.getName());
            
            JSONObject stringElements = 
                    json.getJSONObject(Repository.STRING.getName());
            
            JSONObject numberElements = 
                    json.getJSONObject(Repository.NUMBER.getName());
            
            for (Object keyObj : numberElements.keySet()) {
                String key = (String) keyObj;
                JSONObject entry = numberElements.getJSONObject(key);
                String value = entry.get(VALUE_FLAG).toString();
                String type = entry.get(TYPE_FLAG).toString();
                rv.flushedNumberElements.put(key, 
                        Configs.parseNumberFromType(
                                value, Configs.numberTypeValueOf(type)));
            }
            
            for (Object keyObj : booleanElements.keySet()) {
                String key = (String) keyObj;
                JSONObject entry = (JSONObject) booleanElements.get(key);
                String value = entry.get(VALUE_FLAG).toString();
                rv.flushedBooleanElements.put(key, Configs.parseBoolean(value));
            }
            
            for (Object keyObj : stringElements.keySet()) {
                String key = (String) keyObj;
                JSONObject entry = numberElements.getJSONObject(key);
                String value = entry.get(VALUE_FLAG).toString();
                rv.flushedStringElements.put(key, value);
            }
            
            rv.numberElements = numberElements;
            rv.stringElements = stringElements;
            rv.booleanElements = booleanElements;
            
        } catch (JSONException ex) {
            
            throw new ConfigException(ex);
            
        } catch (Configs.BooleanParsingException ex) {
            
            throw new ConfigException(ex);
            
        }
        // need to implement this
        return rv;
    }
    
    
    
    
    void _serializeBool(String key, boolean value) {
        try {
            booleanElements.put(key, new JSONObject().put(VALUE_FLAG, value));
        } catch (JSONException ex) {
            throw new ConfigException(ex);
        }
    }
    
    void _deleteBool(String key) {
        booleanElements.remove(key);
    }
    
    void _serializeString(String key, String value) {
        try {
            stringElements.put(key, new JSONObject().put(VALUE_FLAG, value));
        } catch (JSONException ex) {
        }
    }
    
    void _deleteString(String key) {
        stringElements.remove(key);
    }
    
    void _serializeNumber(String key, Number value) {
        numberElements.put(key, 
                new JSONObject()
                        .put(VALUE_FLAG, value)
                        .put(TYPE_FLAG, Configs.resolveNumberType(value)));
    }
    
    void _deleteNumber(String key) {
        numberElements.remove(key);
    }
    
    
    
    
    public String toCanonical() {
        return rootElement.toString(DEFAULT_INDENT);
    }
    
}
