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
import java.io.OutputStream;

import java.math.BigDecimal;
import java.math.BigInteger;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import ca.raihan.cfg.Config.Repository;

/**
 * Helper class for {@code Config}.
 * 
 * @author Pranjal Raihan
 */
public final class Configs {
    
    private Configs() {
        throw new IllegalAccessError(getClass().getName());
    }
    
    
    
    public static Config synchronizedConfig(Config config) {
        return new SynchronizedConfig(config);
    }
    
    public static boolean parseBoolean(String toParse) {
        if (toParse == null)
            throw new BooleanParsingException("Cannot parse null value");
        if (toParse.equalsIgnoreCase("true"))
            return true;
        if (toParse.equalsIgnoreCase("false"))
            return false;
        throw new BooleanParsingException("For string: " + toParse);
    }
    
    
    
    
    // package-private
    
    static String MISSING_REPO_FORMAT = "Missing Repository: \"%s\"";
    
    static String DUPLICATE_REPO_FORMAT = "Duplicate Repository: \"%s\"";
    
    
    
    
    static NumberType resolveNumberType(Number number) {
        Contract.nonNull(number);
        if (number instanceof Byte)
            return NumberType.BYTE;
        if (number instanceof Short)
            return NumberType.SHORT;
        if (number instanceof Integer)
            return NumberType.INT;
        if (number instanceof Long)
            return NumberType.LONG;
        if (number instanceof Float)
            return NumberType.FLOAT;
        if (number instanceof Double)
            return NumberType.DOUBLE;
        if (number instanceof BigInteger)
            return NumberType.BIG_INTEGER;
        if (number instanceof BigDecimal)
            return NumberType.BIG_DECIMAL;
        throw new ConfigException(
                "Supported NumberTypes are " + 
                        java.util.Arrays.toString(
                                Config.Repository.NUMBER.getJavaTypes()));
    }
    
    static Number parseNumberFromType(String str, NumberType type) {
        Contract.nonNull(type, "NumberType");
        Contract.nonNull(str, "str");
        
        if (type == NumberType.BYTE)
            return Byte.valueOf(str);
        else if (type == NumberType.SHORT)
            return Short.valueOf(str);
        else if (type == NumberType.INT)
            return Integer.valueOf(str); 
        else if (type == NumberType.LONG)
            return Long.valueOf(str); 
        else if (type == NumberType.FLOAT)
            return Float.valueOf(str); 
        else if (type == NumberType.DOUBLE)
            return Double.valueOf(str); 
        else if (type == NumberType.BIG_INTEGER)
            return new BigInteger(str); 
        else if (type == NumberType.BIG_DECIMAL)
            return new BigDecimal(str);
        
        throw new ConfigException("Unknown NumberType: " + type);
    }
    
    static NumberType numberTypeValueOf(String name) {
        if (name == null)
            throw new ConfigException("NumberType name is null");
        for (NumberType type : NumberType.DEFAULT_TYPES) {
            if (type.isTypeNameMatch(name))
                return type;
        }
        throw new ConfigException("Unknown NumberType name: " + name + 
                ". A NumberType can only take values of types: " + 
                java.util.Arrays.toString(
                        Config.Repository.NUMBER.getJavaTypes()));
    }
    
    static <K, V> V getOrDef(Map<K, V> map, K key, V defaultValue) {
        V v;
        return (((v = map.get(key)) != null) || map.containsKey(key))
            ? v
            : defaultValue;
    }
    
    
    
    
    static ConfigException newMissingRepoException(Repository c) {
        return new ConfigException(
                String.format(MISSING_REPO_FORMAT, c.getName()));
    }
    
    static ConfigException newDuplicateRepoException(Repository c) {
        return new ConfigException(
                String.format(DUPLICATE_REPO_FORMAT, c.getName()));
    }
    
    
    
    
    public static class BooleanParsingException extends IllegalArgumentException {
        
        public BooleanParsingException(String message)
        {
            super(message);
        }
        
    }
    
    static class MergedIterableIterator implements Iterator {
        
        final Iterable[] iterables;
                
        int cursor = 0;
        
        
        Iterator current;
        
        
        
        
        public MergedIterableIterator(Iterable... iterables) {
            this.iterables = Contract.nonNull(iterables, "iterables");
            nextIterator();
        }
        
        
        
        
        final void nextIterator() {
            for (int i = cursor; i < iterables.length; ++i) {
                Iterable iterable = iterables[i];
                if (iterable == null)
                    continue;
                Iterator iterator = iterable.iterator();
                if (iterator == null)
                    continue;
                current = iterator;
                cursor = i;
                return;
            }
            cursor = iterables.length;
            current = null;
        }
        
        public boolean hasNext() {
            if (current == null)
                return false;
            if (!current.hasNext()) {
                if (cursor == iterables.length)
                    return false;
                cursor++;
                nextIterator();
                return hasNext();
            }
            return true;
        }
        
        @SuppressWarnings("unchecked")
        public Object next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Iterator out of bounds");
            }
            return current.next();
        }
        
        public void remove() {
            throw new UnsupportedOperationException("remove not supported");
        }
        
    }
    
    static class IterableImpl<T> implements Iterable<T> {
        
        final Iterator<T> iterator;
        
        
        
        
        public IterableImpl(Iterator<T> iterator) {
            this.iterator = iterator;
        }
        
        
        
        
        public Iterator<T> iterator() {
            return iterator;
        }
        
    }
    
    static final class SynchronizedConfig implements Config {
        
        private final Object mutex = new Object();
        
        private final Config config;
        
        
        
        
        public SynchronizedConfig(Config config) {
            this.config = Contract.nonNull(config);
        }
        
        
        
        
        public boolean getBoolean(String key) throws ConfigException {
            synchronized (mutex) {
                return config.getBoolean(key);
            }
        }
        
        public byte getByte(String key) throws ConfigException {
            synchronized (mutex) {
                return config.getByte(key);
            }
        }
        
        public short getShort(String key) throws ConfigException {
            synchronized (mutex) {
                return config.getShort(key);
            }
        }
        
        public int getInt(String key) throws ConfigException {
            synchronized (mutex) {
                return config.getInt(key);
            }
        }
        
        public long getLong(String key) throws ConfigException {
            synchronized (mutex) {
                return config.getLong(key);
            }
        }
        
        public float getFloat(String key) throws ConfigException {
            synchronized (mutex) {
                return config.getFloat(key);
            }
        }
        
        public double getDouble(String key) throws ConfigException {
            synchronized (mutex) {
                return config.getDouble(key);
            }
        }
        
        public BigInteger getBigInteger(String key) 
                throws ConfigException {
            synchronized (mutex) {
                return config.getBigInteger(key);
            }
        }
        
        public BigDecimal getBigDecimal(String key) 
                throws ConfigException {
            synchronized (mutex) {
                return config.getBigDecimal(key);
            }
        }
        
        public String getString(String key) throws ConfigException {
            synchronized (mutex) {
                return config.getString(key);
            }
        }
        
        public boolean getOrDefault(String key, boolean value) 
                throws ConfigException  {
            synchronized (mutex) {
                return config.getOrDefault(key, value);
            }
        }
        
        public byte getOrDefault(String key, byte value) 
                throws ConfigException  {
            synchronized (mutex) {
                return config.getOrDefault(key, value);
            }
        }
        
        public short getOrDefault(String key, short value) 
                throws ConfigException  {
            synchronized (mutex) {
                return config.getOrDefault(key, value);
            }
        }
        
        public int getOrDefault(String key, int value) throws ConfigException  {
            synchronized (mutex) {
                return config.getOrDefault(key, value);
            }
        }
        
        public long getOrDefault(String key, long value) 
                throws ConfigException {
            synchronized (mutex) {
                return config.getOrDefault(key, value);
            }
        }
        
        public float getOrDefault(String key, float value) 
                throws ConfigException   {
            synchronized (mutex) {
                return config.getOrDefault(key, value);
            }
        }
        
        public double getOrDefault(String key, double value) 
                throws ConfigException   {
            synchronized (mutex) {
                return config.getOrDefault(key, value);
            }
        }
        
        public BigInteger getOrDefault(String key, BigInteger value) 
                throws ConfigException, NullPointerException {
            synchronized (mutex) {
                return config.getOrDefault(key, value);
            }
        }
        
        public BigDecimal getOrDefault(String key, BigDecimal value) 
                throws ConfigException, NullPointerException {
            synchronized (mutex) {
                return config.getOrDefault(key, value);
            }
        }
        
        public String getOrDefault(String key, String value) 
                throws ConfigException   {
            synchronized (mutex) {
                return config.getOrDefault(key, value);
            }
        }
        
        public void putBoolean(String key, boolean value) 
                throws ConfigException {
            synchronized (mutex) {
                config.putBoolean(key, value);
            }
        }
        
        public void putBoolean(String key, boolean value, Put put) 
                throws ConfigException {
            synchronized (mutex) {
                config.putBoolean(key, value, put);
            }
        }
        
        public void putByte(String key, byte value) throws ConfigException {
            synchronized (mutex) {
                config.putByte(key, value);
            }
        }
        
        public void putByte(String key, byte value, Put put) 
                throws ConfigException  {
            synchronized (mutex) {
                config.putByte(key, value, put);
            }
        }
        
        public void putShort(String key, short value) throws ConfigException {
            synchronized (mutex) {
                config.putShort(key, value);
            }
        }
        
        public void putShort(String key, short value, Put put) 
                throws ConfigException {
            synchronized (mutex) {
                config.putShort(key, value, put);
            }
        }
        
        public void putInt(String key, int value) throws ConfigException {
            synchronized (mutex) {
                config.putInt(key, value);
            }
        }
        
        public void putInt(String key, int value, Put put) 
                throws ConfigException {
            synchronized (mutex) {
                config.putInt(key, value, put);
            }
        }
        
        public void putLong(String key, long value) throws ConfigException {
            synchronized (mutex) {
                config.putLong(key, value);
            }
        }
        
        public void putLong(String key, long value, Put put) 
                throws ConfigException {
            synchronized (mutex) {
                config.putLong(key, value, put);
            }
        }
        
        public void putFloat(String key, float value) throws ConfigException {
            synchronized (mutex) {
                config.putFloat(key, value);
            }
        }
        
        public void putFloat(String key, float value, Put put) 
                throws ConfigException {
            synchronized (mutex) {
                config.putFloat(key, value, put);
            }
        }
        
        public void putDouble(String key, double value) throws ConfigException {
            synchronized (mutex) {
                config.putDouble(key, value);
            }
        }
        
        public void putDouble(String key, double value, Put put) 
                throws ConfigException {
            synchronized (mutex) {
                config.putDouble(key, value, put);
            }
        }
        
        public void putBigInteger(String key, BigInteger value) 
                throws ConfigException, NullPointerException {
            synchronized (mutex) {
                config.putBigInteger(key, value);
            }
        }
        
        public void putBigInteger(String key, BigInteger value, Put put) 
                throws ConfigException, NullPointerException {
            synchronized (mutex) {
                config.putBigInteger(key, value, put);
            }
        }
        
        public void putBigDecimal(String key, BigDecimal value) 
                throws ConfigException, NullPointerException {
            synchronized (mutex) {
                config.putBigDecimal(key, value);
            }
        }
        
        public void putBigDecimal(String key, BigDecimal value, Put put) 
                throws ConfigException, NullPointerException {
            synchronized (mutex) {
                config.putBigDecimal(key, value, put);
            }
        }
        
        public void putString(String key, String value) throws ConfigException {
            synchronized (mutex) {
                config.putString(key, value);
            }
        }
        
        public void putString(String key, String value, Put put) 
                throws ConfigException {
            synchronized (mutex) {
                config.putString(key, value, put);
            }
        }
        
        public boolean containsKey(String key, Repository repository) {
            synchronized (mutex) {
                return config.containsKey(key, repository);
            }
        }
        
        public boolean delete(String key, Repository repository) 
                throws ConfigException {
            synchronized (mutex) {
                return config.delete(key, repository);
            }
        }
        
        public ConfigEvent<BooleanRepoContext> booleanEvent() {
            synchronized (mutex) {
                return config.booleanEvent();
            }
        }
        
        public ConfigEvent<NumberRepoContext> numberEvent() {
            synchronized (mutex) {
                return config.numberEvent();
            }
        }
        
        public ConfigEvent<StringRepoContext> stringEvent() {
            synchronized (mutex) {
                return config.stringEvent();
            }
        }
        
        public Iterator<Map.Entry<String, ?>> iterator() {
            synchronized (mutex) {
                return config.iterator();
            }
        }
        
        public Iterable<Map.Entry<String, Boolean>> booleans() {
            synchronized (mutex) {
                return config.booleans();
            }
        }
        
        public Iterable<Map.Entry<String, Number>> numbers() {
            synchronized (mutex) {
                return config.numbers();
            }
        }
        
        public Iterable<Map.Entry<String, String>> strings() {
            synchronized (mutex) {
                return config.strings();
            }
        }
        
        public Map<String, Boolean> mapBooleans() {
            synchronized (mutex) {
                return config.mapBooleans();
            }
        }
        
        public Map<String, Number> mapNumbers() {
            synchronized (mutex) {
                return config.mapNumbers();
            }
        }
        
        public Map<String, String> mapStrings() {
            synchronized (mutex) {
                return config.mapStrings();
            }
        }
        
        public void flushState() {
            synchronized (mutex) {
                config.flushState();
            }
        }
        
        public void save(File file, WriteAccess fileMode) 
                throws ConfigException {
            synchronized (mutex) {
                config.save(file, fileMode);
            }
        }
        
        public void save(OutputStream outputStream) throws ConfigException {
            synchronized (mutex) {
                config.save(outputStream);
            }
        }
        
        public void save() throws ConfigException, IOException {
            synchronized (mutex) {
                config.save();
            }
        }
        
        public void linkToFile(File file) {
            synchronized (mutex) {
                config.linkToFile(file);
            }
        }
        
        public String toCanonical() {
            synchronized (mutex) {
                return config.toCanonical();
            }
        }
        
        
        
        
        @Override
        public String toString() {
            synchronized (mutex) {
                return config.toString();
            }
        }
        
        @Override
        public int hashCode() {
            synchronized (mutex) {
                return config.hashCode();
            }
        }
        
        @Override
        @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
        public boolean equals(Object o) {
            if (o == this)
                return true;
            synchronized (mutex) {
                return config.equals(o);
            }
        }
        
    }
    
}
