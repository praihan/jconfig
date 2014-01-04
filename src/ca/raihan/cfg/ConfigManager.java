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
import java.io.InputStream;
import java.io.Reader;

/**
 * {@code Config} manager.
 * 
 * @author Pranjal Raihan
 * 
 * @param <T> represents the type of {@code Config} that this manager manages.
 */
public interface ConfigManager<T extends Config> {
    
    /**
     * Constructs a new <b>empty</b> {@code Config}. Details are implementation 
     * specific.
     * 
     * @return the constructed {@code Config}
     * 
     * @throws  UnsupportedOperationException if this method of construction is 
     *          not supported
     */
    T newConfig();
    
    /**
     * Read a {@code Config} from a {@code File}. Details are implementation 
     * specific.
     * 
     * @param file the {@code File} to read from
     * 
     * @return the constructed {@code Config}
     * 
     * @throws  UnsupportedOperationException if this method of construction is 
     *          not supported
     */
    T read(File file);
    
    /**
     * Read a {@code Config} from a {@code InputStream}. Details are 
     * implementation specific.
     * 
     * @param inputStream the {@code InputStream} to read from
     * 
     * @return the constructed {@code Config}
     * 
     * @throws  UnsupportedOperationException if this method of construction is 
     *          not supported
     */
    T read(InputStream inputStream);
    
    /**
     * Read a {@code Config} from a {@code Reader}. Details are implementation 
     * specific.
     * 
     * @param reader the {@code Reader} to read from
     * 
     * @return the constructed {@code Config}
     * 
     * @throws  UnsupportedOperationException if this method of construction is 
     *          not supported
     */
    T read(Reader reader);
    
    /**
     * Read a {@code Config} from a canonical String representation. Details 
     * are implementation specific.
     * 
     * @param str the canonical representation of the {@code Config}
     * 
     * @return the constructed {@code Config}
     * 
     * @throws  UnsupportedOperationException if this method of construction is 
     *          not supported
     */
    T parse(String str);
    
}
