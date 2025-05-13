// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

import java.util.function.Function;

public final class ArrayUtil
{
    public static <T> T getOrDefault(final T[] array, final int index, final T defaultValue) {
        return getOrDefault(array, index, defaultValue, t -> t);
    }
    
    public static <A, T> T getOrDefault(final A[] array, final int index, final T defaultValue, final Function<A, T> mapperFunction) {
        if (index >= 0 && index < array.length) {
            return mapperFunction.apply(array[index]);
        }
        return defaultValue;
    }
}
