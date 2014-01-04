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

import java.math.BigDecimal;
import java.math.BigInteger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Known type of {@code Number}s
 */
public class NumberType {
    
    public static final NumberType BYTE = 
            new NumberType(byte.class, Byte.class);
    
    public static final NumberType SHORT = 
            new NumberType(short.class, Short.class);
    
    public static final NumberType INT 
            = new NumberType(int.class, Integer.class);
    
    public static final NumberType LONG = 
            new NumberType(long.class, Long.class);
    
    public static final NumberType FLOAT = 
            new NumberType(float.class, Float.class);
    
    public static final NumberType DOUBLE = 
            new NumberType(double.class, Double.class);
    
    public static final NumberType BIG_INTEGER = 
            new NumberType(BigInteger.class);
    
    public static final NumberType BIG_DECIMAL = 
            new NumberType(BigDecimal.class);
    
    public static final Collection<NumberType> DEFAULT_TYPES;
    
    static {
        List<NumberType> tmp = new ArrayList<NumberType>(8);
        tmp.add(BYTE);
        tmp.add(SHORT);
        tmp.add(INT);
        tmp.add(LONG);
        tmp.add(FLOAT);
        tmp.add(DOUBLE);
        tmp.add(BIG_INTEGER);
        tmp.add(BIG_DECIMAL);
        DEFAULT_TYPES = Collections.unmodifiableList(tmp);
    }
    
    
    
    
    private final Class<?>[] types;
    
    
    
    
    protected NumberType(Class<?>... types) {
        this.types = Contract.nonNull(types);
    }
    
    
    
    
    public String name() {
        return types[0].getName();
    }
    
    public final Class<?>[] getTypes() {
        return types;
    }
    
    public boolean isTypeNameMatch(String name) {
        for (Class<?> cls : types) {
            if (cls.getName().equals(name))
                return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return name();
    }
    
}
