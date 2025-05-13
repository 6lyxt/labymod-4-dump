// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.debug;

import java.util.Iterator;
import java.util.Collection;

public class Preconditions
{
    public static void notNull(final Object o, final String variable) {
        if (o == null) {
            throw new NullPointerException("Object must not be null: " + variable);
        }
    }
    
    public static <T> void noneNull(final Collection<T> objects, final String variable) {
        notNull(objects, variable);
        int i = 0;
        for (final T object : objects) {
            if (object == null) {
                throw new NullPointerException("Content of collection must not be null: " + variable + "[" + i);
            }
            ++i;
        }
    }
    
    public static <T> void noneNull(final T[] objects, final String variable) {
        notNull(objects, variable);
        for (int i = 0; i < objects.length; ++i) {
            if (objects[i] == null) {
                throw new NullPointerException("Content of array must not be null: " + variable + "[" + i);
            }
        }
    }
}
