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

import java.util.HashSet;
import java.util.Set;

/**
 * A class that represents an event. Listeners can be added or removed and 
 * events can be raised in any scope with access to a key.
 * 
 * @author Pranjal Raihan
 * 
 * @param <T> any type that extends {@link ConfigEventContext}
 */
public final class ConfigEvent<T extends ConfigEventContext> {
    
    private final Set<ConfigEventListener<T>> listeners = 
            new HashSet<ConfigEventListener<T>>();
    
    private final Object key;
    
    private final Object mutex = new Object();
    
    
    
    
    /**
     * Construct a new event with the specified key. This key will be used to 
     * raise events.
     * 
     * @param key the key to save a reference to
     * 
     * @throws NullPointerException if {@code key} is {@code null}
     */
    public ConfigEvent(final Object key) {
        Contract.nonNull(key);
        this.key = key;
    }
    
    
    
    
    /**
     * Adds a new listener to the event, 
     * {@link ConfigEventListener#onEvent(Object, ConfigEventContext) 
     * onEvent(sender, context)} will be called when the event is raised.
     * 
     * @param listener the listener to add
     * 
     * @throws NullPointerException if {@code listener} is {@code null}
     */
    public void addListener(ConfigEventListener<T> listener) {
        Contract.nonNull(listener);
        synchronized (mutex) {
            listeners.add(listener);
        }
    }
    
    /**
     * Removes a specified listener from the event. Returns {@code true} if the 
     * listener was removed, otherwise returns {@code false}.
     * 
     * @param listener the listener to remove. a {@code null} value will always 
     * return {@code false}
     * 
     * @return {@code true} if the listener was removed, otherwise 
     * {@code false}.
     */
    public boolean removeListener(ConfigEventListener<T> listener) {
        if (listener == null)
            return false;
        synchronized (mutex) {
            return listeners.remove(listener);
        }
    }
    
    /**
     * Raises the event. Signals all listeners.
     * 
     * @param key the key to verify that the sender has permission to raise 
     * this event, a {@code ConfigEventException} will be thrown if the key 
     * specified here does not pass an identity check with the key that was 
     * specified during the event's construction.
     * 
     * @param sender the sender of the event
     * 
     * @param context the context of the event
     */
    public void raise(Object key, Object sender, T context) {
        if (this.key != key)
            throw new ConfigEventException("Invalid key: " + key);
        for (ConfigEventListener<T> listener : listeners)
            if (listener != null)
                listener.onEvent(sender, context);
    }
    
    /**
     * Returns {@code true} if any listeners are currently listening to this 
     * event, else returns {@code false}.
     * 
     * @return {@code true} if any listeners are currently listening to this 
     * event, else {@code false}.
     */
    public boolean hasListeners() {
        return !listeners.isEmpty();
    }
    
}
