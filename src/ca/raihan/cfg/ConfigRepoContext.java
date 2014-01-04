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
 * Base class for all old-new value {@code ConfigEventContext}
 * 
 * @author Pranjal Raihan
 * 
 * @param <T> any type
 */
abstract class ConfigRepoContext<T> implements ConfigEventContext {
    
    /**
     * toString format
     */
    static String TO_STRING_FORMAT = "%s {Type: %s    Change: %s}";
    
    /**
     * The old value
     */
    protected T oldValue;
    
    /**
     * the new value
     */
    protected T newValue;
    
    /**
     * The repository
     */
    protected Config.Repository type;
    
    /**
     * the change type
     */
    protected Config.ChangeAction change;
    
    /**
     * the name of the key
     */
    protected String key;
    
    
    
    
    /**
     * Constructs a new context.
     * 
     * @param key the key
     * @param oldValue the old value
     * @param newValue the new value
     * @param type the repository
     * @param change the change type
     */
    protected ConfigRepoContext(String key, T oldValue, T newValue, 
            Config.Repository type, Config.ChangeAction change) {
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.key = Contract.nonNull(key);
        this.type = Contract.nonNull(type, "type");
        this.change = Contract.nonNull(change, "change");
    }
    
    
    
    
    /**
     * Returns the {@code ChangeAction}.
     * 
     * @return the {@code ChangeAction}
     */
    public Config.ChangeAction getChangeAction() {
        return change;
    }
    
    /**
     * Returns the {@code Repository}.
     * 
     * @return the {@code Repository}
     */
    public Config.Repository getRepository() {
        return type;
    }
    
    /**
     * Returns the old value.
     * 
     * @return the old value
     */
    public T getOldValue() {
        return this.oldValue;
    }
    
    /**
     * Returns the new value.
     * 
     * @return the new value
     */
    public T getNewValue() {
        return this.newValue;
    }
    
    /**
     * Returns the key.
     * 
     * @return the key
     */
    public String getKey() {
        return key;
    }
    
    
    
    
    @Override
    public String toString() {
        return String.format(TO_STRING_FORMAT, 
                super.toString(), getRepository(), getChangeAction());
    }
    
}
