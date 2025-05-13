// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

import java.util.Objects;

public class KeyValue<T>
{
    private final String key;
    private final T value;
    
    public KeyValue(final String key, final T value) {
        this.key = key;
        this.value = value;
    }
    
    public String getKey() {
        return this.key;
    }
    
    public T getValue() {
        return this.value;
    }
    
    @Override
    public String toString() {
        return this.key + ": " + this.value.toString();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final KeyValue<?> keyValue = (KeyValue<?>)o;
        return Objects.equals(this.key, keyValue.key);
    }
    
    @Override
    public int hashCode() {
        return this.key.hashCode();
    }
}
