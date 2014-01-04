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
 * An event listener.
 * 
 * @author Pranjal Raihan
 * 
 * @param <T> any type that extends {@link ConfigEventContext}
 * 
 * @see ConfigEvent#addListener(ConfigEventListener) 
 */
public interface ConfigEventListener<T extends ConfigEventContext> {
    
    /**
     * This method is called when an event this is registered to is raised.
     * 
     * @param sender the sender of the event.
     * 
     * @param context the context that provides details about the event.
     */
    void onEvent(Object sender, T context);
    
}
