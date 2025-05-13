// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.collection.map;

import java.util.Map;

class SimpleEntry<K, V> implements Map.Entry<K, V>
{
    private final K key;
    private final V value;
    
    public SimpleEntry(final K key, final V value) {
        this.key = key;
        this.value = value;
    }
    
    @Override
    public K getKey() {
        return this.key;
    }
    
    @Override
    public V getValue() {
        return this.value;
    }
    
    @Override
    public V setValue(final V value) {
        throw new UnsupportedOperationException();
    }
}
