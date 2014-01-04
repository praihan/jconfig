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
import java.io.InputStream;
import java.io.Reader;

/**
 * XML-based implementation of a {@link ConfigManager}. Churns out instances of 
 * {@link XMLConfig}.
 * 
 * @author Pranjal Raihan
 */
public final class XMLConfigManager implements ConfigManager<XMLConfig> {
    
    public XMLConfig newConfig() {
        return XMLConfig.empty();
    }
    
    public XMLConfig read(File file) {
        verifyFileIntegrity(file);
        verifyFileExistence(file);
        return XMLConfig.read(file);
    }
    
    public XMLConfig read(InputStream inputStream) {
        return XMLConfig.read(inputStream);
    }
    
    public XMLConfig read(Reader reader) {
        return XMLConfig.read(reader);
    }
    
    public XMLConfig parse(String strXML) {
        return XMLConfig.parse(strXML);
    }
    
    
    
    
    static void verifyFileIntegrity(File file) {
        Contract.nonNull(file, "file");
        if (file != null && file.isDirectory())
            throw new ConfigException(file + " is a directory");
    }

    static void verifyFileExistence(File file) {
        if (!file.exists()) {
            throw new ConfigException(
                new FileNotFoundException(file.toString()));
        }
    }
    
}
