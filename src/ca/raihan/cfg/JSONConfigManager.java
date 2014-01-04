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
 *
 * @author Pranjal Raihan
 */
public class JSONConfigManager implements ConfigManager<JSONConfig> {
    
    public JSONConfig newConfig() {
        return JSONConfig.empty();
    }
    
    public JSONConfig read(File file) {
        verifyFileIntegrity(file);
        verifyFileExistence(file);
        return JSONConfig.read(file);
    }
    
    public JSONConfig read(InputStream inputStream) {
        return JSONConfig.read(inputStream);
    }
    
    public JSONConfig read(Reader reader) {
        return JSONConfig.read(reader);
    }
    
    public JSONConfig parse(String str) {
        return JSONConfig.parse(str);
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
