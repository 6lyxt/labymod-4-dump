// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

import net.labymod.api.util.function.Functional;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Collection;
import net.labymod.api.util.reflection.Reflection;
import java.lang.reflect.Type;

public final class PrimitiveHelper
{
    public static final Class<?>[] BOOLEAN;
    public static final Class<?>[] CHARACTER;
    public static final Class<?>[] BYTE;
    public static final Class<?>[] DOUBLE;
    public static final Class<?>[] FLOAT;
    public static final Class<?>[] INTEGER;
    public static final Class<?>[] LONG;
    public static final Class<?>[] SHORT;
    public static final Class<?>[] NUMBER_PRIMITIVES;
    public static final Class<?>[] PRIMITIVES;
    
    private PrimitiveHelper() {
    }
    
    public static Number convertToTarget(final Number number, final Type targetType) {
        if (Reflection.isType(targetType, PrimitiveHelper.BYTE)) {
            return number.byteValue();
        }
        if (Reflection.isType(targetType, PrimitiveHelper.DOUBLE)) {
            return number.doubleValue();
        }
        if (Reflection.isType(targetType, PrimitiveHelper.FLOAT)) {
            return number.floatValue();
        }
        if (Reflection.isType(targetType, PrimitiveHelper.INTEGER)) {
            return number.intValue();
        }
        if (Reflection.isType(targetType, PrimitiveHelper.LONG)) {
            return number.longValue();
        }
        if (Reflection.isType(targetType, PrimitiveHelper.SHORT)) {
            return number.shortValue();
        }
        return null;
    }
    
    public static Class<?> wrap(final Class<?> cls) {
        if (Reflection.isType(cls, PrimitiveHelper.BOOLEAN)) {
            return Boolean.class;
        }
        if (Reflection.isType(cls, PrimitiveHelper.BYTE)) {
            return Byte.class;
        }
        if (Reflection.isType(cls, PrimitiveHelper.CHARACTER)) {
            return Character.class;
        }
        if (Reflection.isType(cls, PrimitiveHelper.DOUBLE)) {
            return Double.class;
        }
        if (Reflection.isType(cls, PrimitiveHelper.FLOAT)) {
            return Float.class;
        }
        if (Reflection.isType(cls, PrimitiveHelper.INTEGER)) {
            return Integer.class;
        }
        if (Reflection.isType(cls, PrimitiveHelper.LONG)) {
            return Long.class;
        }
        if (Reflection.isType(cls, PrimitiveHelper.SHORT)) {
            return Short.class;
        }
        return cls;
    }
    
    public static boolean isNumber(final Class<?> cls) {
        for (final Class<?> c : PrimitiveHelper.NUMBER_PRIMITIVES) {
            if (c == cls) {
                return true;
            }
        }
        return false;
    }
    
    static {
        BOOLEAN = new Class[] { Boolean.class, Boolean.TYPE };
        CHARACTER = new Class[] { Character.class, Character.TYPE };
        BYTE = new Class[] { Byte.class, Byte.TYPE };
        DOUBLE = new Class[] { Double.class, Double.TYPE };
        FLOAT = new Class[] { Float.class, Float.TYPE };
        INTEGER = new Class[] { Integer.class, Integer.TYPE };
        LONG = new Class[] { Long.class, Long.TYPE };
        SHORT = new Class[] { Short.class, Long.TYPE };
        NUMBER_PRIMITIVES = Functional.toArray(new HashSet<Class<?>>(), (Class<Class<?>>)Class.class, classes -> {
            classes.addAll(Arrays.asList(PrimitiveHelper.BYTE));
            classes.addAll(Arrays.asList(PrimitiveHelper.DOUBLE));
            classes.addAll(Arrays.asList(PrimitiveHelper.FLOAT));
            classes.addAll(Arrays.asList(PrimitiveHelper.INTEGER));
            classes.addAll(Arrays.asList(PrimitiveHelper.LONG));
            classes.addAll(Arrays.asList(PrimitiveHelper.SHORT));
            return;
        });
        PRIMITIVES = Functional.toArray(new HashSet<Class<?>>(), (Class<Class<?>>)Class.class, classes -> {
            classes.addAll(Arrays.asList(PrimitiveHelper.BOOLEAN));
            classes.addAll(Arrays.asList(PrimitiveHelper.BYTE));
            classes.addAll(Arrays.asList(PrimitiveHelper.CHARACTER));
            classes.addAll(Arrays.asList(PrimitiveHelper.DOUBLE));
            classes.addAll(Arrays.asList(PrimitiveHelper.FLOAT));
            classes.addAll(Arrays.asList(PrimitiveHelper.INTEGER));
            classes.addAll(Arrays.asList(PrimitiveHelper.LONG));
            classes.addAll(Arrays.asList(PrimitiveHelper.SHORT));
        });
    }
}
