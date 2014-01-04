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

/**
 * A map of key-value properties of {@code String} to {@code boolean}, 
 * {@code String} to {@code Number} and {@code String} to {@code String}. Each  
 * combination is stored in its own individual repository such that the same 
 * key with two distinct values can exist as long as they don't produce 
 * duplicates in one repository. No permanent changes are made unless the state 
 * is flushed. The key-value properties can be saved to a persistent form when 
 * necessary or requested.
 * <p>
 * Instances can be acquired via a {@link ConfigManager}.
 * 
 * @see ConfigManager
 * @see XMLConfigManager
 * @see JSONConfigManager
 * 
 * @author Pranjal Raihan
 */
public interface Config extends Iterable<Map.Entry<String, ?>> {
    
    /**
     * Returns a {@code boolean} from the repository of {@code boolean} values.
     * 
     * @param key the key too look for
     * 
     * @return the found value for the key
     * 
     * @throws  ConfigException if the key could not be found in the 
     *          repository of {@code boolean} values
     */
    boolean getBoolean(String key) throws ConfigException;
    
    /**
     * Returns a {@code byte} from the repository of {@code Number} values.
     * 
     * @param key the key too look for
     * 
     * @return the found value for the key
     * 
     * @throws  ConfigException if the key could not be found in the 
     *          repository of {@code Number} values
     */
    byte getByte(String key) throws ConfigException;
    
    /**
     * Returns a {@code short} from the repository of {@code Number} values.
     * 
     * @param key the key too look for
     * 
     * @return the found value for the key
     * 
     * @throws  ConfigException if the key could not be found in the 
     *          repository of {@code Number} values
     */
    short getShort(String key) throws ConfigException;
    
    /**
     * Returns a {@code int} from the repository of {@code Number} values.
     * 
     * @param key the key too look for
     * 
     * @return the found value for the key
     * 
     * @throws  ConfigException if the key could not be found in the 
     *          repository of {@code Number} values
     */
    int getInt(String key) throws ConfigException;
    
    /**
     * Returns a {@code long} from the repository of {@code Number} values.
     * 
     * @param key the key too look for
     * 
     * @return the found value for the key
     * 
     * @throws  ConfigException if the key could not be found in the 
     *          repository of {@code Number} values
     */
    long getLong(String key) throws ConfigException;
    
    /**
     * Returns a {@code float} from the repository of {@code Number} values.
     * 
     * @param key the key too look for
     * 
     * @return the found value for the key
     * 
     * @throws  ConfigException if the key could not be found in the 
     *          repository of {@code Number} values
     */
    float getFloat(String key) throws ConfigException;
    
    /**
     * Returns a {@code double} from the repository of {@code Number} values.
     * 
     * @param key the key too look for
     * 
     * @return the found value for the key
     * 
     * @throws  ConfigException if the key could not be found in the 
     *          repository of {@code Number} values
     */
    double getDouble(String key) throws ConfigException;
    
    /**
     * Returns a {@code BigInteger} from the repository of {@code Number} 
     * values.
     * 
     * @param key the key too look for
     * 
     * @return the found value for the key
     * 
     * @throws  ConfigException if the key could not be found in the 
     *          repository of {@code Number} values
     */
    BigInteger getBigInteger(String key) throws ConfigException;
    
    /**
     * Returns a {@code BigDecimal} from the repository of {@code Number} values.
     * 
     * @param key the key too look for
     * 
     * @return the found value for the key
     * 
     * @throws  ConfigException if the key could not be found in the 
     *          repository of {@code Number} values
     */
    BigDecimal getBigDecimal(String key) throws ConfigException;
    
    /**
     * Returns a {@code String} from the repository of {@code String} values.
     * 
     * @param key the key too look for
     * 
     * @return the found value for the key
     * 
     * @throws  ConfigException if the key could not be found in the 
     *          repository of {@code String} values
     */
    String getString(String key) throws ConfigException;
    
    
    
    
    /**
     * Returns a {@code boolean} from the repository of {@code boolean} values. 
     * If none is found, returns the specified default value.
     * 
     * @param key the key too look for
     * @param value the default value
     * 
     * @return the found value for the key
     * 
     * @throws ConfigException if this implementation does not support this
     */
    boolean getOrDefault(String key, boolean value) throws ConfigException;
    
    /**
     * Returns a {@code byte} from the repository of {@code Number} values. 
     * If none is found, returns the specified default value.
     * 
     * @param key the key too look for
     * @param value the default value
     * 
     * @return the found value for the key
     * 
     * @throws ConfigException if this implementation does not support this
     */
    byte getOrDefault(String key, byte value) throws ConfigException;
    
    /**
     * Returns a {@code short} from the repository of {@code Number} values. 
     * If none is found, returns the specified default value.
     * 
     * @param key the key too look for
     * @param value the default value
     * 
     * @return the found value for the key
     * 
     * @throws ConfigException if this implementation does not support this
     */
    short getOrDefault(String key, short value) throws ConfigException;
    
    /**
     * Returns a {@code int} from the repository of {@code Number} values. 
     * If none is found, returns the specified default value.
     * 
     * @param key the key too look for
     * @param value the default value
     * 
     * @return the found value for the key
     * 
     * @throws ConfigException if this implementation does not support this
     */
    int getOrDefault(String key, int value) throws ConfigException;
    
    /**
     * Returns a {@code long} from the repository of {@code Number} values. 
     * If none is found, returns the specified default value.
     * 
     * @param key the key too look for
     * @param value the default value
     * 
     * @return the found value for the key
     * 
     * @throws ConfigException if this implementation does not support this
     */
    long getOrDefault(String key, long value) throws ConfigException;
    
    /**
     * Returns a {@code float} from the repository of {@code Number} values. 
     * If none is found, returns the specified default value.
     * 
     * @param key the key too look for
     * @param value the default value
     * 
     * @return the found value for the key
     * 
     * @throws ConfigException if this implementation does not support this
     */
    float getOrDefault(String key, float value) throws ConfigException;
    
    /**
     * Returns a {@code double} from the repository of {@code Number} values. 
     * If none is found, returns the specified default value.
     * 
     * @param key the key too look for
     * @param value the default value
     * 
     * @return the found value for the key
     * 
     * @throws ConfigException if this implementation does not support this
     */
    double getOrDefault(String key, double value) throws ConfigException;
    
    /**
     * Returns a {@code BigInteger} from the repository of {@code Number} 
     * values. If none is found, returns the specified default value.
     * 
     * @param key the key too look for
     * @param value the default value
     * 
     * @return the found value for the key
     * 
     * @throws ConfigException if this implementation does not support this
     */
    BigInteger getOrDefault(String key, BigInteger value) 
            throws ConfigException;
    
    /**
     * Returns a {@code BigDecimal} from the repository of {@code Number} 
     * values. If none is found, returns the specified default value.
     * 
     * @param key the key too look for
     * @param value the default value
     * 
     * @return the found value for the key
     * 
     * @throws ConfigException if this implementation does not support this
     */
    BigDecimal getOrDefault(String key, BigDecimal value) 
            throws ConfigException;
    
    /**
     * Returns a {@code String} from the repository of {@code String} values. 
     * If none is found, returns the specified default value.
     * 
     * @param key the key too look for
     * @param value the default value
     * 
     * @return the found value for the key
     * 
     * @throws ConfigException if this implementation does not support this
     */
    String getOrDefault(String key, String value) throws ConfigException;
    
    
    
    
    /**
     * Puts a {@code boolean} in the repository of {@code boolean} values with 
     * a specified key, if the key already exists, then the effect is 
     * implementation specific.
     * 
     * @param key the key to put to recall the value
     * @param value the value to put
     * 
     * @throws  ConfigException if the specified key or value are 
     *          disallowed by the implementation
     */
    void putBoolean(String key, boolean value) throws ConfigException;
    
    /**
     * Puts a {@code boolean} in the repository of {@code boolean} values with 
     * a specified key, if {@code Put} specified is {@link Put#RETAIN} and the 
     * key already exists, then the new value is ignored.
     * 
     * @param key the key to put to recall the value
     * @param value the value to put
     * @param put overwriting permission
     * 
     * @throws  ConfigException if the specified key or value or {@code Put} 
     *          are disallowed by the implementation
     */
    void putBoolean(String key, boolean value, Put put) throws ConfigException;
    
    /**
     * Puts a {@code byte} in the repository of {@code Number} values with 
     * a specified key, if the key already exists, then the effect is 
     * implementation specific.
     * 
     * @param key the key to put to recall the value
     * @param value the value to put
     * 
     * @throws  ConfigException if the specified key or value are 
     *          disallowed by the implementation
     */
    void putByte(String key, byte value) throws ConfigException;
    
    /**
     * Puts a {@code byte} in the repository of {@code Number} values with 
     * a specified key, if {@code Put} specified is {@link Put#RETAIN} and the 
     * key already exists, then the new value is ignored.
     * 
     * @param key the key to put to recall the value
     * @param value the value to put
     * @param put overwriting permission
     * 
     * @throws  ConfigException if the specified key or value or {@code Put} 
     *          are disallowed by the implementation
     */
    void putByte(String key, byte value, Put put) throws ConfigException;
    
    /**
     * Puts a {@code short} in the repository of {@code Number} values with 
     * a specified key, if the key already exists, then the effect is 
     * implementation specific.
     * 
     * @param key the key to put to recall the value
     * @param value the value to put
     * 
     * @throws  ConfigException if the specified key or value are 
     *          disallowed by the implementation
     */
    void putShort(String key, short value) throws ConfigException;
    
    /**
     * Puts a {@code short} in the repository of {@code Number} values with 
     * a specified key, if {@code Put} specified is {@link Put#RETAIN} and the 
     * key already exists, then the new value is ignored.
     * 
     * @param key the key to put to recall the value
     * @param value the value to put
     * @param put overwriting permission
     * 
     * @throws  ConfigException if the specified key or value or {@code Put} 
     *          are disallowed by the implementation
     */
    void putShort(String key, short value, Put put) throws ConfigException;
    
    /**
     * Puts a {@code int} in the repository of {@code Number} values with 
     * a specified key, if the key already exists, then the effect is 
     * implementation specific.
     * 
     * @param key the key to put to recall the value
     * @param value the value to put
     * 
     * @throws  ConfigException if the specified key or value are 
     *          disallowed by the implementation
     */
    void putInt(String key, int value) throws ConfigException;
    
    /**
     * Puts a {@code int} in the repository of {@code Number} values with 
     * a specified key, if {@code Put} specified is {@link Put#RETAIN} and the 
     * key already exists, then the new value is ignored.
     * 
     * @param key the key to put to recall the value
     * @param value the value to put
     * @param put overwriting permission
     * 
     * @throws  ConfigException if the specified key or value or {@code Put} 
     *          are disallowed by the implementation
     */
    void putInt(String key, int value, Put put) throws ConfigException;
    
    /**
     * Puts a {@code long} in the repository of {@code Number} values with 
     * a specified key, if the key already exists, then the effect is 
     * implementation specific.
     * 
     * @param key the key to put to recall the value
     * @param value the value to put
     * 
     * @throws  ConfigException if the specified key or value are 
     *          disallowed by the implementation
     */
    void putLong(String key, long value) throws ConfigException;
    
    /**
     * Puts a {@code long} in the repository of {@code Number} values with 
     * a specified key, if {@code Put} specified is {@link Put#RETAIN} and the 
     * key already exists, then the new value is ignored.
     * 
     * @param key the key to put to recall the value
     * @param value the value to put
     * @param put overwriting permission
     * 
     * @throws  ConfigException if the specified key or value or {@code Put} 
     *          are disallowed by the implementation
     */
    void putLong(String key, long value, Put put) throws ConfigException;
    
    /**
     * Puts a {@code float} in the repository of {@code Number} values with 
     * a specified key, if the key already exists, then the effect is 
     * implementation specific.
     * 
     * @param key the key to put to recall the value
     * @param value the value to put
     * 
     * @throws  ConfigException if the specified key or value are 
     *          disallowed by the implementation
     */
    void putFloat(String key, float value) throws ConfigException;
    
    /**
     * Puts a {@code float} in the repository of {@code Number} values with 
     * a specified key, if {@code Put} specified is {@link Put#RETAIN} and the 
     * key already exists, then the new value is ignored.
     * 
     * @param key the key to put to recall the value
     * @param value the value to put
     * @param put overwriting permission
     * 
     * @throws  ConfigException if the specified key or value or {@code Put} 
     *          are disallowed by the implementation
     */
    void putFloat(String key, float value, Put put) throws ConfigException;
    
    /**
     * Puts a {@code double} in the repository of {@code Number} values with 
     * a specified key, if the key already exists, then the effect is 
     * implementation specific.
     * 
     * @param key the key to put to recall the value
     * @param value the value to put
     * 
     * @throws  ConfigException if the specified key or value are 
     *          disallowed by the implementation
     */
    void putDouble(String key, double value) throws ConfigException;
    
    /**
     * Puts a {@code double} in the repository of {@code Number} values with 
     * a specified key, if {@code Put} specified is {@link Put#RETAIN} and the 
     * key already exists, then the new value is ignored.
     * 
     * @param key the key to put to recall the value
     * @param value the value to put
     * @param put overwriting permission
     * 
     * @throws  ConfigException if the specified key or value or {@code Put} 
     *          are disallowed by the implementation
     */
    void putDouble(String key, double value, Put put) throws ConfigException;
    
    /**
     * Puts a {@code BigInteger} in the repository of {@code Number} values with 
     * a specified key, if the key already exists, then the effect is 
     * implementation specific.
     * 
     * @param key the key to put to recall the value
     * @param value the value to put
     * 
     * @throws  ConfigException if the specified key or value are 
     *          disallowed by the implementation
     * @throws  NullPointerException if the specified value is {@code null}
     */
    void putBigInteger(String key, BigInteger value) 
            throws ConfigException, NullPointerException;
    
    /**
     * Puts a {@code BigInteger} in the repository of {@code Number} values with 
     * a specified key, if {@code Put} specified is {@link Put#RETAIN} and the 
     * key already exists, then the new value is ignored.
     * 
     * @param key the key to put to recall the value
     * @param value the value to put
     * @param put overwriting permission
     * 
     * @throws  ConfigException if the specified key or value or {@code Put} 
     *          are disallowed by the implementation
     * @throws  NullPointerException if the specified value is {@code null}
     */
    void putBigInteger(String key, BigInteger value, Put put) 
            throws ConfigException, NullPointerException;
    
    /**
     * Puts a {@code BigDecimal} in the repository of {@code Number} values with 
     * a specified key, if the key already exists, then the effect is 
     * implementation specific.
     * 
     * @param key the key to put to recall the value
     * @param value the value to put
     * 
     * @throws  ConfigException if the specified key or value are 
     *          disallowed by the implementation
     * @throws  NullPointerException if the specified value is {@code null}
     */
    void putBigDecimal(String key, BigDecimal value) 
            throws ConfigException, NullPointerException;
    
    /**
     * Puts a {@code BigDecimal} in the repository of {@code Number} values with 
     * a specified key, if {@code Put} specified is {@link Put#RETAIN} and the 
     * key already exists, then the new value is ignored.
     * 
     * @param key the key to put to recall the value
     * @param value the value to put
     * @param put overwriting permission
     * 
     * @throws  ConfigException if the specified key or value or {@code Put} 
     *          are disallowed by the implementation
     * @throws  NullPointerException if the specified value is {@code null}
     */
    void putBigDecimal(String key, BigDecimal value, Put put) 
            throws ConfigException, NullPointerException;
    
    /**
     * Puts a {@code String} in the repository of {@code String} values with 
     * a specified key, if the key already exists, then the effect is 
     * implementation specific.
     * 
     * @param key the key to put to recall the value
     * @param value the value to put
     * 
     * @throws  ConfigException if the specified key or value are 
     *          disallowed by the implementation
     */
    void putString(String key, String value) throws ConfigException;
    
    /**
     * Puts a {@code String} in the repository of {@code String} values with 
     * a specified key, if {@code Put} specified is {@link Put#RETAIN} and the 
     * key already exists, then the new value is ignored.
     * 
     * @param key the key to put to recall the value
     * @param value the value to put
     * @param put overwriting permission
     * 
     * @throws  ConfigException if the specified key or value or {@code Put} 
     *          are disallowed by the implementation
     */
    void putString(String key, String value, Put put) throws ConfigException;
    
    
    
    
    /**
     * Returns {@code true} if the key exists in the {@code Repository} 
     * specified, else returns {@code false}.
     * 
     * @param key the key to look for
     * @param repository the {@code Repository} to look in
     * 
     * @return {@code true} if the {@code key} exists in the {@code Repository} 
     * specified, else returns {@code false}
     */
    boolean containsKey(String key, Repository repository);
    
    
    
    
    /**
     * Deletes a key from the specified {@code Repository}
     * 
     * @param key the key to look for
     * @param repository the {@code Repository} to look in
     * 
     * @return  {@code true} if any modification occurs, else returns 
     *          {@code false}
     * 
     * @throws ConfigException if deletion is disallowed by this implementation
     */
    boolean delete(String key, Repository repository) throws ConfigException;
    
    
    
    
    /**
     * Returns the {@code Event} for the {@code boolean} {@code Repository}.
     * 
     * @return the {@code Event} for the {@code boolean} {@code Repository}
     */
    ConfigEvent<BooleanRepoContext> booleanEvent();
    
    /**
     * Returns the {@code Event} for the {@code Number} {@code Repository}.
     * 
     * @return the {@code Event} for the {@code Number} {@code Repository}
     */
    ConfigEvent<NumberRepoContext> numberEvent();
    
    /**
     * Returns the {@code Event} for the {@code String} {@code Repository}.
     * 
     * @return the {@code Event} for the {@code String} {@code Repository}
     */
    ConfigEvent<StringRepoContext> stringEvent();
    
    
    
    
    /**
     * Returns an {@code Iterator} of {@code Map.Entry} of all entries in all 
     * {@code Repository}.
     * 
     * @return an {@code Iterator} of {@code Map.Entry} of all entries in all 
     * {@code Repository}
     */
    Iterator<Map.Entry<String, ?>> iterator();
    
    /**
     * Returns an {@code Iterator} of {@code Map.Entry} for values in the 
     * {@code boolean} {@code Repository}.
     * 
     * @return an {@code Iterator} of {@code Map.Entry} for values in the 
     * {@code boolean} {@code Repository}
     */
    Iterable<Map.Entry<String, Boolean>> booleans();
    
    /**
     * Returns an {@code Iterator} of {@code Map.Entry} for values in the 
     * {@code Number} {@code Repository}.
     * 
     * @return an {@code Iterator} of {@code Map.Entry} for values in the 
     * {@code Number} {@code Repository}
     */
    Iterable<Map.Entry<String, Number>> numbers();
    
    /**
     * Returns an {@code Iterator} of {@code Map.Entry} for values in the 
     * {@code String} {@code Repository}.
     * 
     * @return an {@code Iterator} of {@code Map.Entry} for values in the 
     * {@code String} {@code Repository}
     */
    Iterable<Map.Entry<String, String>> strings();
    
    
    
    
    /**
     * Returns a {@code Map} that contains all keys and values in the 
     * {@code boolean} repository.
     * 
     * @return a {@code Map} that contains all keys and values in the 
     * {@code boolean} repository.
     */
    Map<String, Boolean> mapBooleans();
    
    /**
     * Returns a {@code Map} that contains all keys and values in the 
     * {@code Number} repository.
     * 
     * @return a {@code Map} that contains all keys and values in the 
     * {@code Number} repository.
     */
    Map<String, Number> mapNumbers();
    
    /**
     * Returns a {@code Map} that contains all keys and values in the 
     * {@code String} repository.
     * 
     * @return a {@code Map} that contains all keys and values in the 
     * {@code String} repository.
     */
    Map<String, String> mapStrings();
    
    
    
    
    /**
     * Flushes all newly added entries and voids them of a transient state. A 
     * flush needs to be called to save the values to the repositories. 
     * This method should be called before getting, saving or canonicalizing. 
     * Details are implementation specific.
     */
    void flushState() throws ConfigException;
    
    /**
     * Saves all repositories to a {@code File} with a specified 
     * {@link Access.WriteAccess}
     * 
     * @param file the file to save to
     * @param fileMode the {@code WriteAccess}, if overwriting occurs or not
     * 
     * @throws ConfigException if the serialization failed
     */
    void save(File file, WriteAccess fileMode) 
            throws ConfigException;
    
    /**
     * Saves all repositories to an {@code OutputStream}
     * 
     * @param outputStream the {@code OutputStream} to save to
     * 
     * @throws ConfigException if the serialization failed
     */
    void save(OutputStream outputStream) throws ConfigException;
    
    /**
     * The default save implementation. Saves to a persistent form. Generally 
     * it should default to saving to a {@code File} specified by 
     * {@link #linkToFile(File)}. However, details are implementation specific.
     * 
     * @throws ConfigException if the saving failed
     * 
     * @throws IOException if a IO problem was encountered
     */
    void save() 
            throws ConfigException, UnsupportedOperationException, IOException;
    
    /**
     * Associates this {@code Config} with a {@code File} to save to.
     * 
     * @param file the {@code File} to associate with, should not be 
     * non-existent or be a directory.
     */
    void linkToFile(File file) throws UnsupportedOperationException;
    
    
    
    
    /**
     * Returns a canonical representation of the {@code Config}
     * 
     * @return a canonical representation of the {@code Config}
     */
    String toCanonical();
    
    
    
    
    /**
     * The different repositories
     */
    static enum Repository {
        
        BOOLEAN("Booleans", boolean.class, Boolean.class),
        STRING("Strings", String.class),
        NUMBER("Numbers", 
            byte.class, Byte.class, 
            short.class, Short.class, 
            int.class, Integer.class, 
            long.class, Long.class,
            float.class, Float.class, 
            double.class, Double.class,
            BigInteger.class,
            BigDecimal.class);
        
        
        
        
        private final String sectionTag;
        
        private final Class<?>[] types;
        
        
        
        
        private Repository(String sectionTag, Class<?>... types) {
            this.sectionTag = sectionTag;
            this.types = types;
        }
        
        
        
        
        public String getName() {
            return this.sectionTag;
        }
        
        
        
        
        public Class<?>[] getJavaTypes() {
            return types;
        }
        
    }
    
    /**
     * Write permission
     */
    static enum Put {
        OVERWRITE,
        RETAIN;
    }
    
    /**
     * Modification flag
     */
    static enum ChangeAction {
        PUT,
        DELETE,
    }
    
    /**
     * The writing access
     */
    static enum WriteAccess {
        OVERWRITE_IF_NECESSARY,
        ONLY_IF_NOT_EXISTS
    }
    
    
    
    
    /**
     * The change context used by {@link Repository#NUMBER}.
     */
    static final class NumberRepoContext extends 
            ConfigRepoContext<Number> {
        
        public NumberRepoContext(String name, Number oldValue, 
                Number newValue, ChangeAction change) {
            super(name, oldValue, newValue, Repository.NUMBER, change);
        }
        
    }
    
    /**
     * The change context used by {@link Repository#STRING}.
     */
    static final class StringRepoContext extends 
            ConfigRepoContext<String> {
        
        public StringRepoContext(String name, String oldValue, 
                String newValue, ChangeAction change) {
            super(name, oldValue, newValue, Repository.STRING, change);
        }
        
    }
    
    /**
     * The change context used by {@link Repository#BOOLEAN}.
     */
    static final class BooleanRepoContext extends 
            ConfigRepoContext<Boolean> {
        
        public BooleanRepoContext(String name, Boolean oldValue, 
                Boolean newValue, ChangeAction change) {
            super(name, oldValue, newValue, Repository.BOOLEAN, change);
        }
        
    }
    
}
