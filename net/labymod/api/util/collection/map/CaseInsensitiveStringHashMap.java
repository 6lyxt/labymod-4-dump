// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.collection.map;

import java.util.function.BiFunction;
import org.jetbrains.annotations.NotNull;
import java.util.function.Function;
import java.util.Iterator;
import java.util.Map;
import java.util.Locale;
import java.util.HashMap;

public class CaseInsensitiveStringHashMap<V> extends HashMap<String, V>
{
    public CaseInsensitiveStringHashMap() {
    }
    
    public CaseInsensitiveStringHashMap(final int initialCapacity) {
        super(initialCapacity);
    }
    
    public CaseInsensitiveStringHashMap(final int initialCapacity, final float loadFactor) {
        super(initialCapacity, loadFactor);
    }
    
    private String normalizeKey(final Object key) {
        if (key instanceof final String string) {
            return string.toLowerCase(Locale.ROOT);
        }
        return null;
    }
    
    @Override
    public V put(final String key, final V value) {
        return super.put(this.normalizeKey(key), value);
    }
    
    @Override
    public V get(final Object key) {
        return super.get(this.normalizeKey(key));
    }
    
    @Override
    public boolean containsKey(final Object key) {
        return super.containsKey(this.normalizeKey(key));
    }
    
    @Override
    public V remove(final Object key) {
        return super.remove(this.normalizeKey(key));
    }
    
    @Override
    public void putAll(final Map<? extends String, ? extends V> m) {
        if (m != null && !m.isEmpty()) {
            final Map<String, V> normalizedMap = new HashMap<String, V>(m.size());
            for (final Map.Entry<? extends String, ? extends V> entry : m.entrySet()) {
                normalizedMap.put(this.normalizeKey(entry.getKey()), (V)entry.getValue());
            }
            super.putAll((Map<? extends String, ? extends V>)normalizedMap);
        }
    }
    
    @Override
    public V getOrDefault(final Object key, final V defaultValue) {
        return super.getOrDefault(this.normalizeKey(key), defaultValue);
    }
    
    @Override
    public V computeIfAbsent(final String key, @NotNull final Function<? super String, ? extends V> mappingFunction) {
        return super.computeIfAbsent(this.normalizeKey(key), mappingFunction);
    }
    
    @Override
    public V computeIfPresent(final String key, @NotNull final BiFunction<? super String, ? super V, ? extends V> remappingFunction) {
        return super.computeIfPresent(this.normalizeKey(key), remappingFunction);
    }
    
    @Override
    public V compute(final String key, @NotNull final BiFunction<? super String, ? super V, ? extends V> remappingFunction) {
        return super.compute(this.normalizeKey(key), remappingFunction);
    }
    
    @Override
    public V merge(final String key, @NotNull final V value, @NotNull final BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return super.merge(this.normalizeKey(key), value, remappingFunction);
    }
    
    @Override
    public V replace(final String key, final V value) {
        return super.replace(this.normalizeKey(key), value);
    }
    
    @Override
    public boolean remove(final Object key, final Object value) {
        return super.remove(this.normalizeKey(key), value);
    }
    
    @Override
    public boolean replace(final String key, final V oldValue, final V newValue) {
        return super.replace(this.normalizeKey(key), oldValue, newValue);
    }
    
    @Override
    public V putIfAbsent(final String key, final V value) {
        return super.putIfAbsent(this.normalizeKey(key), value);
    }
}
