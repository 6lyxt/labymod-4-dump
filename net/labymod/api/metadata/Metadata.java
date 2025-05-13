// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.metadata;

import java.util.function.Function;
import java.util.function.Supplier;

public interface Metadata
{
    boolean has(final String p0);
    
     <T> void set(final String p0, final T p1);
    
     <T> void set(final String p0, final Supplier<T> p1);
    
     <T> void set(final Metadata p0);
    
    default <T> Metadata with(final String key, final T value) {
        this.set(key, value);
        return this;
    }
    
    default <T> Metadata with(final String key, final Supplier<T> value) {
        this.set(key, value);
        return this;
    }
    
     <T> T get(final String p0);
    
     <T> T get(final String p0, final T p1);
    
     <T> T getSupplied(final String p0, final T p1);
    
    default boolean getBoolean(final String key) {
        final Object value = this.get(key);
        return value instanceof Boolean && (boolean)value;
    }
    
    default boolean getBoolean(final String key, final boolean defaultValue) {
        return this.get(key, defaultValue);
    }
    
     <T> T computeIfAbsent(final String p0, final Function<String, T> p1);
    
     <T> T remove(final String p0);
    
     <T> T remove(final String p0, final Runnable p1);
    
    default Metadata create() {
        return new DefaultMetadata();
    }
}
