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
 * Helper class that checks user compliance.
 * 
 * @author Pranjal Raihan
 */
final class Contract {
    
    /**
     * Exception format
     */
    static final String NULL_POINTER_EXCEPTION_FORMAT = "%s cannot be null";
    
    
    
    
    private Contract() {
        throw new IllegalAccessError(getClass().getName());
    }
    
    
    
    
    static <T> T nonNull(T obj) {
        if (obj == null)
            throw new NullPointerException("unexpected null pointer");
        return obj;
    }
    
    static void nonNulls(Object... objects) {
        for (Object obj : objects) {
            nonNull(obj);
        }
    }
    
    static <T> T nonNull(T obj, String name) {
        if (obj == null)
            throw new NullPointerException(
                    String.format(NULL_POINTER_EXCEPTION_FORMAT, name));
        return obj;
    }
    
    static void require(boolean predicate) {
        if (!predicate)
            throw new IllegalArgumentException("Contract violated");
    }
    
    static void require(boolean predicate, String message) {
        if (!predicate)
            throw new IllegalArgumentException(message);
    }
    
}
