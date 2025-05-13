// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

import java.util.Map;

public interface PropertyContainer
{
    Map<String, Object> getProperties();
    
    default <T> T getProperty(final String key) {
        return (T)this.getProperties().get(key);
    }
    
    default boolean hasProperty(final String key) {
        return this.getProperties().containsKey(key);
    }
    
    default <T> void setProperty(final String key, final T value) {
        this.getProperties().put(key, value);
    }
}
