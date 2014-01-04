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

/**
 * An unchecked exception that is used by {@code Config}.
 * 
 * @author Pranjal Raihan
 */
public class ConfigException extends RuntimeException {
    
    /**
     * @see RuntimeException#RuntimeException()
     */
    public ConfigException() {
        super();
    }
    
    /**
     * @param message the message
     * 
     * @see RuntimeException#RuntimeException(String)
     */
    public ConfigException(String message) {
        super(message);
    }
    
    /**
     * 
     * @param cause the cause
     * 
     * @see RuntimeException#RuntimeException(Throwable) 
     */
    public ConfigException(Throwable cause) {
        super(cause);
    }
    
    /**
     * 
     * @param message the message
     * @param cause the cause
     * 
     * @see RuntimeException#RuntimeException(String, Throwable) 
     */
    public ConfigException(String message, Throwable cause) {
        super(message, cause);
    }
    
    
    
    
    /**
     * Returns a {@code ConfigException} for a key.
     * 
     * @param key the problematic key
     * 
     * @return a {@code ConfigException} for a key
     */
    static ConfigException forKey(String key) {
        return new ConfigException("For key: " + key);
    }
    
    /**
     * Returns a {@code ConfigException} for an un-found key.
     * 
     * @param key the un-found key
     * 
     * @return a {@code ConfigException} for an un-found key
     */
    static ConfigException unfoundKey(String key) {
        return new ConfigException("Unfound key: " + key);
    }
    
}
