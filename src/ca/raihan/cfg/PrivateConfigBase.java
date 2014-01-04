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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import java.math.BigDecimal;
import java.math.BigInteger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.w3c.dom.DOMException;

/**
 *
 * @author Pranjal Raihan
 */
abstract strictfp class PrivateConfigBase implements Config {
    
    /**
     * Encoding char-set name for UTF-8
     */
    static final String UTF_8 = "UTF-8";
    
    /**
     * The flag for a value
     */
    static final String VALUE_FLAG = "value";
    
    /**
     * The flag for an entry
     */
    static final String ENTRY_FLAG = "entry";
    
    /**
     * The flag for a key
     */
    static final String KEY_FLAG = "key";
    
    /**
     * The flag for a type
     */
    static final String TYPE_FLAG = "type";
    
    /**
     * The default {@code Put}. (for debugging)
     */
    static final Put DEFAULT_PUT = Put.OVERWRITE;
    
    
    /**
     * The linked {@code File}
     */
    File fileHandle;
    
    
    /**
     * All serialized {@code Number} elements.
     */
    Map<String, Number> flushedNumberElements;
    
    /**
     * All {@code Number} elements pending serialization
     */
    Map<String, Number> pendingNumberElements;
    
    /**
     * All deleted keys for {@code Number}
     */
    List<String> deletedNumberKeys;
    
    
    
    /**
     * All serialized {@code String} elements.
     */
    Map<String, String> flushedStringElements;
    
    /**
     * All {@code String} elements pending serialization
     */
    Map<String, String> pendingStringElements;
    
    /**
     * All deleted keys for {@code String}
     */
    List<String> deletedStringKeys;
    
    
    
    
    /**
     * All serialized {@code Boolean} elements.
     */
    Map<String, Boolean> flushedBooleanElements;
    
    /**
     * All {@code Boolean} elements pending serialization
     */
    Map<String, Boolean> pendingBooleanElements;
    
    /**
     * All deleted keys for {@code Boolean}
     */
    List<String> deletedBooleanKeys;
    
    
    
    
    /**
     * Event for {@code Number}
     */
    ConfigEvent<NumberRepoContext> numberRepoEvent;
    
    /**
     * Event for {@code String}
     */
    ConfigEvent<StringRepoContext> stringRepoEvent;
    
    /**
     * Event for {@code Boolean}
     */
    ConfigEvent<BooleanRepoContext> booleanRepoEvent;
    
    /**
     * The event key
     */
    protected final Object eventKey = new Object();
    
    
    
    
    public boolean getBoolean(String key) throws ConfigException {
        Boolean value = Configs.getOrDef(flushedBooleanElements, key, null);
        if (value != null)
            return value.booleanValue();
        throw ConfigException.unfoundKey(key);
    }
    
    public byte getByte(String key) throws ConfigException {
        Number value = Configs.getOrDef(flushedNumberElements, key, null);
        if (value != null)
            return value.byteValue();
        throw ConfigException.unfoundKey(key);
    }
    
    public short getShort(String key) throws ConfigException {
        Number value = Configs.getOrDef(flushedNumberElements, key, null);
        if (value != null)
            return value.shortValue();
        throw ConfigException.unfoundKey(key);
    }
    
    public int getInt(String key) throws ConfigException {
        Number value = Configs.getOrDef(flushedNumberElements, key, null);
        if (value != null)
            return value.intValue();
        throw ConfigException.unfoundKey(key);
    }
    
    public long getLong(String key) throws ConfigException {
        Number value = Configs.getOrDef(flushedNumberElements, key, null);
        if (value != null)
            return value.longValue();
        throw ConfigException.unfoundKey(key);
    }
    
    public float getFloat(String key) throws ConfigException {
        Number value = Configs.getOrDef(flushedNumberElements, key, null);
        if (value != null)
            return value.floatValue();
        throw ConfigException.unfoundKey(key);
    }
    
    public double getDouble(String key) throws ConfigException {
        Number value = Configs.getOrDef(flushedNumberElements, key, null);
        if (value != null)
            return value.doubleValue();
        throw ConfigException.unfoundKey(key);
    }
    
    public BigInteger getBigInteger(String key) throws ConfigException {
        Number value = Configs.getOrDef(flushedNumberElements, key, null);
        if (value != null)
            return value instanceof BigInteger ? 
                    (BigInteger) value : 
                    BigInteger.valueOf(value.longValue());
        throw ConfigException.unfoundKey(key);
    }
    
    public BigDecimal getBigDecimal(String key) throws ConfigException {
        Number value = Configs.getOrDef(flushedNumberElements, key, null);
        if (value != null) {
            if (value instanceof BigDecimal) {
                return (BigDecimal) value;
            }
            if (value instanceof Float || value instanceof Double) {
                return BigDecimal.valueOf(value.doubleValue());
            }
            return BigDecimal.valueOf(value.longValue());
        }
        throw ConfigException.unfoundKey(key);
    }
    
    public String getString(String key) throws ConfigException {
        String value = Configs.getOrDef(flushedStringElements, key, null);
        if (value != null)
            return value;
        throw ConfigException.unfoundKey(key);
    }
    
    
    
    
    public boolean getOrDefault(String key, boolean value) 
            throws ConfigException {
        Boolean v = Configs.getOrDef(flushedBooleanElements, key, null);
        if (v != null)
            return v.booleanValue();
        return value;
    }
    
    public byte getOrDefault(String key, byte value) 
            throws ConfigException {
        Number v = Configs.getOrDef(flushedNumberElements, key, null);
        if (v != null)
            return v.byteValue();
        return value;
    }
    
    public short getOrDefault(String key, short value) 
            throws ConfigException {
        Number v = Configs.getOrDef(flushedNumberElements, key, null);
        if (v != null)
            return v.shortValue();
        return value;
    }
    
    public int getOrDefault(String key, int value) 
            throws ConfigException {
        Number v = Configs.getOrDef(flushedNumberElements, key, null);
        if (v != null)
            return v.intValue();
        return value;
    }
    
    public long getOrDefault(String key, long value) 
            throws ConfigException {
        Number v = Configs.getOrDef(flushedNumberElements, key, null);
        if (v != null)
            return v.longValue();
        return value;
    }
    
    public float getOrDefault(String key, float value) 
            throws ConfigException {
        Number v = Configs.getOrDef(flushedNumberElements, key, null);
        if (v != null)
            return v.floatValue();
        return value;
    }
    
    public double getOrDefault(String key, double value) 
            throws ConfigException {
        Number v = Configs.getOrDef(flushedNumberElements, key, null);
        if (v != null)
            return v.doubleValue();
        return value;
    }
    
    public BigInteger getOrDefault(String key, BigInteger value) 
            throws ConfigException {
        Number v = Configs.getOrDef(flushedNumberElements, key, null);
        if (v != null)
            return v instanceof BigInteger ? 
                    (BigInteger) v : 
                    BigInteger.valueOf(v.longValue());
        return value;
    }
    
    public BigDecimal getOrDefault(String key, BigDecimal value) 
            throws ConfigException {
        Number v = Configs.getOrDef(flushedNumberElements, key, null);
        if (v != null) {
            if (v instanceof BigDecimal) {
                return (BigDecimal) v;
            }
            if (v instanceof Float || v instanceof Double) {
                return BigDecimal.valueOf(v.doubleValue());
            }
            return BigDecimal.valueOf(v.longValue());
        }
        return value;
    }
    
    public String getOrDefault(String key, String value) 
            throws ConfigException {
        String v = Configs.getOrDef(flushedStringElements, key, null);
        if (v != null)
            return v;
        return value;
    }
    
    
    
    
    public void putBoolean(final String key, final boolean value) 
            throws ConfigException {
        putBoolean(key, value, DEFAULT_PUT);
    }
    
    public void putBoolean(final String key, final boolean value, 
            final Put put) throws ConfigException {
        Contract.nonNull(key, "key");
        Contract.nonNull(put, "put");
        
        Boolean v = Configs.getOrDef(flushedBooleanElements, key, null);
        if (v != null) {
            if (put == Put.RETAIN)
                return;
        } else {
            v = Configs.getOrDef(pendingBooleanElements, key, null);
            if (v != null)
                if (put == Put.RETAIN)
                    return;
        }
        pendingBooleanElements.put(key, value);
        deletedBooleanKeys.remove(key);
        ConfigEvent<BooleanRepoContext> evt = booleanEvent();
        if (evt!= null && evt.hasListeners()) {
            evt.raise(eventKey, this, new BooleanRepoContext(
                            key, v, value, ChangeAction.PUT));
        }
    }
    
    public void putByte(final String key, final byte value) 
            throws ConfigException {
        putByte(key, value, DEFAULT_PUT);
    }
    
    public void putByte(String key, byte value, Put put) 
            throws ConfigException {
        _putNumber(key, value, put);
    }
    
    public void putShort(final String key, final short value) 
            throws ConfigException {
        putShort(key, value, DEFAULT_PUT);
    }
    
    public void putShort(String key, short value, Put put) 
            throws ConfigException {
        _putNumber(key, value, put);
    }
    
    public void putInt(final String key, final int value) 
            throws ConfigException {
        putInt(key, value, DEFAULT_PUT);
    }
    
    public void putInt(String key, int value, Put put) 
            throws ConfigException {
        _putNumber(key, value, put);
    }
    
    public void putLong(final String key, final long value) 
            throws ConfigException {
        putLong(key, value, DEFAULT_PUT);
    }
    
    public void putLong(String key, long value, Put put) 
            throws ConfigException {
        _putNumber(key, value, put);
    }
    
    public void putFloat(final String key, final float value) 
            throws ConfigException {
        putFloat(key, value, DEFAULT_PUT);
    }
    
    public void putFloat(String key, float value, Put put) 
            throws ConfigException {
        _putNumber(key, value, put);
    }
    
    public void putDouble(final String key, final double value) 
            throws ConfigException {
        putDouble(key, value, DEFAULT_PUT);
    }
    
    public void putDouble(String key, double value, Put put) 
            throws ConfigException {
        _putNumber(key, value, put);
    }
    
    public void putBigInteger(String key, BigInteger value) 
            throws ConfigException, NullPointerException {
        putBigInteger(key, value, DEFAULT_PUT);
    }
    
    public void putBigInteger(String key, BigInteger value, Put put) throws 
            ConfigException, NullPointerException {
        if (value == null)
            throw new NullPointerException("value is null");
        _putNumber(key, value, put);
    }
    
    public void putBigDecimal(String key, BigDecimal value) 
            throws ConfigException, NullPointerException {
        putBigDecimal(key, value, DEFAULT_PUT);
    }
    
    public void putBigDecimal(String key, BigDecimal value, Put put) throws 
            ConfigException, NullPointerException {
        if (value == null)
            throw new NullPointerException("value is null");
        _putNumber(key, value, put);
    }
    
    void _putNumber(String key, Number value, Put put) {
        Contract.nonNull(key, "key");
        Contract.nonNull(put, "put");
        
        Number v = Configs.getOrDef(flushedNumberElements, key, null);
        if (v != null) {
            if (put == Put.RETAIN)
                return;
        } else {
            v = Configs.getOrDef(pendingNumberElements, key, null);
            if (v != null)
                if (put == Put.RETAIN)
                    return;
        }
        pendingNumberElements.put(key, value);
        deletedNumberKeys.remove(key);
        ConfigEvent<NumberRepoContext> evt = numberEvent();
        if (evt != null && evt.hasListeners()) {
            evt.raise(eventKey, this,
                    new NumberRepoContext(key, v, value, ChangeAction.PUT));
        }
    }
    
    public void putString(final String key, final String value) 
            throws ConfigException {
        putString(key, value, DEFAULT_PUT);
    }
    
    public void putString(String key, String value, Put put) 
            throws ConfigException {
        Contract.nonNull(key, "key");
        Contract.nonNull(put, "put");
        
        String v = Configs.getOrDef(flushedStringElements, key, null);
        if (v != null) {
            if (put == Put.RETAIN)
                return;
        } else {
            v = Configs.getOrDef(pendingStringElements, key, null);
            if (v != null)
                if (put == Put.RETAIN)
                    return;
        }
        pendingStringElements.put(key, value);
        deletedStringKeys.remove(key);
        ConfigEvent<StringRepoContext> evt = stringEvent();
        if (evt != null && evt.hasListeners()) {
            evt.raise(eventKey, this,
                    new StringRepoContext(key, v, value, ChangeAction.PUT));
        }
    }
    
    
    
    
    public boolean containsKey(String key, Repository repository) {
        Contract.nonNull(key);
        switch (repository) {
            case BOOLEAN:
                return flushedBooleanElements.containsKey(key);
            case STRING:
                return flushedStringElements.containsKey(key);
            case NUMBER:
                return flushedNumberElements.containsKey(key);
            default:
                // at this point, it must be logically null
                throw new NullPointerException("category is null");
        }
    }
    
    public boolean delete(String key, Repository category) 
            throws ConfigException {
        boolean contains = containsKey(key, category);
        if (!contains)
            return false;
        switch (category) {
            case BOOLEAN:
                deletedBooleanKeys.add(key);
                Boolean b;
                b = pendingBooleanElements.remove(key);
                if (b == null) {
                    b = flushedBooleanElements.remove(key);
                }
                ConfigEvent<BooleanRepoContext> bEvt = booleanEvent();
                if (bEvt != null && bEvt.hasListeners())
                    bEvt.raise(eventKey, this, new BooleanRepoContext(
                            key, b, null, ChangeAction.DELETE));
                break;
            case STRING:
                deletedStringKeys.add(key);
                String str;
                str = pendingStringElements.remove(key);
                if (str == null) {
                    str = flushedStringElements.remove(key);
                }
                ConfigEvent<StringRepoContext> strEvt = stringEvent();
                if (strEvt != null && strEvt.hasListeners())
                    strEvt.raise(eventKey, this, new StringRepoContext(
                            key, str, null, ChangeAction.DELETE));
                break;
            case NUMBER:
                deletedNumberKeys.add(key);
                Number n;
                n = pendingNumberElements.remove(key);
                if (n == null) {
                    n = flushedNumberElements.remove(key);
                }
                ConfigEvent<NumberRepoContext> nEvt = numberEvent();
                if (nEvt != null && nEvt.hasListeners())
                    nEvt.raise(eventKey, this, new NumberRepoContext(
                            key, n, null, ChangeAction.DELETE));
                break;
        }
        return true;
    }
    
    
    
    
    public ConfigEvent<NumberRepoContext> numberEvent() {
        return numberRepoEvent;
    }
    
    public ConfigEvent<BooleanRepoContext> booleanEvent() {
        return booleanRepoEvent;
    }
    
    public ConfigEvent<StringRepoContext> stringEvent() {
        return stringRepoEvent;
    }
    
    
    
    
    @SuppressWarnings("unchecked")
    public Iterator<Map.Entry<String, ?>> iterator() {
        return new Configs.MergedIterableIterator(new Iterable[]
        {
            flushedBooleanElements.entrySet(),
            flushedNumberElements.entrySet(),
            flushedStringElements.entrySet(),
        });
    }
    
    public Iterable<Map.Entry<String, Boolean>> booleans() {
        return new Configs.IterableImpl<Map.Entry<String, Boolean>>(
                flushedBooleanElements.entrySet().iterator());
    }
    
    public Iterable<Map.Entry<String, Number>> numbers() {
        return new Configs.IterableImpl<Map.Entry<String, Number>>(
                flushedNumberElements.entrySet().iterator());
    }
    
    public Iterable<Map.Entry<String, String>> strings() {
        return new Configs.IterableImpl<Map.Entry<String, String>>(
                flushedStringElements.entrySet().iterator());
    }
    
    
    
    
    public Map<String, Boolean> mapBooleans() {
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        map.putAll(flushedBooleanElements);
        return map;
    }
    
    public Map<String, Number> mapNumbers() {
        Map<String, Number> map = new HashMap<String, Number>();
        map.putAll(flushedNumberElements);
        return map;
    }
    
    public Map<String, String> mapStrings() {
        Map<String, String> map = new HashMap<String, String>();
        map.putAll(flushedStringElements);
        return map;
    }
    
    
    
    
    public void flushState() throws ConfigException {
        try {
            
            // pendingBooleanElements.entrySet().stream().forEach(
            // (entry) ->
            // {
                // _flushBool(entry.getKey(), entry.getValue());
            // });
            for (Map.Entry<String, Boolean> entry : 
                    pendingBooleanElements.entrySet()) {
                _serializeBool(entry.getKey(), entry.getValue());
            }
            flushedBooleanElements.putAll(pendingBooleanElements);
            pendingBooleanElements.clear();
            for (String key : deletedBooleanKeys) {
                _deleteBool(key);
            }
            deletedBooleanKeys.clear();
            
            for (Map.Entry<String, String> entry : 
                    pendingStringElements.entrySet()) {
                _serializeString(entry.getKey(), entry.getValue());
            }
            flushedStringElements.putAll(pendingStringElements);
            pendingStringElements.clear();
            for (String key : deletedStringKeys) {
                _deleteString(key);
            }
            deletedStringKeys.clear();
            
            for (Map.Entry<String, Number> entry : 
                    pendingNumberElements.entrySet()) {
                _serializeNumber(entry.getKey(), entry.getValue());
            }
            flushedNumberElements.putAll(pendingNumberElements);
            pendingNumberElements.clear();
            for (String key : deletedNumberKeys) {
                _deleteNumber(key);
            }
            deletedNumberKeys.clear();
            
        } catch (DOMException ex) {
            // special case
            throw new ConfigException(ex);
            
        } catch (JSONException ex) {
            // special case
            throw new ConfigException(ex);
            
        }
    }
    
    /**
     * The implementation to serialize {@code boolean}.
     * 
     * @param key the key to serialize
     * @param value the value for the key
     */
    abstract void _serializeBool(String key, boolean value);
    
    /**
     * The implementation to remove the {@code boolean} with the specified key.
     * 
     * @param key the key to remove
     */
    abstract void _deleteBool(String key);
    
    /**
     * The implementation to serialize {@code String}.
     * 
     * @param key the key to serialize
     * @param value the value for the key
     */
    abstract void _serializeString(String key, String value);
    
    /**
     * The implementation to remove the {@code String} with the specified key.
     * 
     * @param key the key to remove
     */
    abstract void _deleteString(String key);
    
    /**
     * The implementation to serialize {@code Number}.
     * 
     * @param key the key to serialize
     * @param value the value for the key
     */
    abstract void _serializeNumber(String key, Number value);
    
    /**
     * The implementation to remove the {@code Number} with the specified key.
     * 
     * @param key the key to remove
     */
    abstract void _deleteNumber(String key);
    
    public void save(File file, WriteAccess overwrite) 
            throws ConfigException, UnsupportedOperationException {
        Contract.nonNull(overwrite, "FileWrtieAccess");
        if (Contract.nonNull(file, "file").exists() && 
            overwrite == WriteAccess.ONLY_IF_NOT_EXISTS)
            return;
        
        if (file.isDirectory())
            throw new ConfigException(file + " cannot be a directory");
        
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            save(outputStream);
            
        } catch (FileNotFoundException ex) {
            
            throw new ConfigException(ex);
            
        } finally {
            
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException ex) {
                }
            }
            
        }
    }
    
    public void save(OutputStream outputStream) 
            throws ConfigException, UnsupportedOperationException {
        Contract.nonNull(outputStream);
        
        PrintStream printStream = null;
        try {
            
            printStream = new PrintStream(outputStream, false, UTF_8);
            printStream.print(toCanonical());
            printStream.flush();
            
        } catch (UnsupportedEncodingException ex) {
            
            throw new InternalError(String.valueOf(ex));
            
        } finally {
            
            if (printStream != null) {
                printStream.close();
            }
            
        }
    }
    
    public void save() 
            throws ConfigException, UnsupportedOperationException, IOException {
        if (fileHandle == null)
            throw new IOException("No file linked to save to");
        save(fileHandle, WriteAccess.OVERWRITE_IF_NECESSARY);
    }
    
    public void linkToFile(File file) throws UnsupportedOperationException {
        if (file == null)
            return;
        if (!file.exists())
            throw new ConfigException("File must exist");
        if (file.isDirectory())
            throw new ConfigException("File cannot be a directory");
        this.fileHandle = file;
    }
    
}
