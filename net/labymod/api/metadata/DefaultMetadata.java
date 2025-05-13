// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.metadata;

import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.Map;

public class DefaultMetadata implements Metadata
{
    private Map<String, Object> metadata;
    
    @Override
    public boolean has(final String key) {
        return this.metadata != null && this.metadata.containsKey(key);
    }
    
    @Override
    public <T> void set(final String key, final T value) {
        this.getMetadata().put(key, value);
    }
    
    @Override
    public <T> void set(final String key, final Supplier<T> value) {
        this.getMetadata().put(key, value);
    }
    
    @Override
    public <T> void set(final Metadata metadata) {
        if (metadata instanceof final DefaultMetadata defaultMetadata) {
            this.getMetadata().putAll(defaultMetadata.getMetadata());
        }
    }
    
    @Override
    public <T> T get(final String key) {
        return (T)((this.metadata == null) ? null : this.metadata.get(key));
    }
    
    @Override
    public <T> T get(final String key, final T defaultValue) {
        return (T)((this.metadata == null) ? defaultValue : this.metadata.getOrDefault(key, defaultValue));
    }
    
    @Override
    public <T> T getSupplied(final String key, final T defaultValue) {
        if (this.metadata == null) {
            return defaultValue;
        }
        final Object o = this.metadata.get(key);
        if (!(o instanceof Supplier)) {
            return defaultValue;
        }
        final Supplier<T> supplier = (Supplier<T>)o;
        return supplier.get();
    }
    
    @Override
    public <T> T computeIfAbsent(final String key, final Function<String, T> function) {
        return (T)this.getMetadata().computeIfAbsent(key, function);
    }
    
    @Override
    public <T> T remove(final String key) {
        if (this.metadata == null) {
            return null;
        }
        return (T)this.metadata.remove(key);
    }
    
    @Override
    public <T> T remove(final String key, final Runnable after) {
        final T value = this.remove(key);
        if (after == null) {
            return value;
        }
        after.run();
        return value;
    }
    
    private Map<String, Object> getMetadata() {
        if (this.metadata == null) {
            this.metadata = new HashMap<String, Object>();
        }
        return this.metadata;
    }
}
