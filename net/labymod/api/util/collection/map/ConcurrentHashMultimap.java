// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.collection.map;

import java.util.function.Consumer;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Collection;
import java.util.Map;

public class ConcurrentHashMultimap<K, V> implements Multimap<K, V>
{
    private final Map<K, Collection<V>> map;
    
    public ConcurrentHashMultimap() {
        this.map = new ConcurrentHashMap<K, Collection<V>>();
    }
    
    public ConcurrentHashMultimap(final int initialCapacity) {
        this.map = new ConcurrentHashMap<K, Collection<V>>(initialCapacity);
    }
    
    public ConcurrentHashMultimap(final int initialCapacity, final float loadFactor) {
        this.map = new ConcurrentHashMap<K, Collection<V>>(initialCapacity, loadFactor);
    }
    
    public ConcurrentHashMultimap(final Map<K, Collection<V>> entries) {
        this.map = new ConcurrentHashMap<K, Collection<V>>((Map<? extends K, ? extends Collection<V>>)entries);
    }
    
    @Override
    public int size() {
        return this.map.size();
    }
    
    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }
    
    @Override
    public void put(final K key, final V value) {
        final Collection<V> values = this.map.computeIfAbsent(key, k -> new ArrayList());
        values.add(value);
    }
    
    @Override
    public boolean remove(final K key) {
        final Collection<V> values = this.map.remove(key);
        return values != null;
    }
    
    @Override
    public boolean remove(final K key, final V value) {
        final Collection<V> values = this.map.get(key);
        return values != null && values.remove(value);
    }
    
    @Override
    public void clear() {
        this.map.clear();
    }
    
    @Override
    public Collection<V> get(final K key) {
        return this.map.computeIfAbsent(key, k -> new ArrayList());
    }
    
    @Override
    public Collection<V> values() {
        final Collection<V> values = new ArrayList<V>();
        for (final Collection<V> value : this.map.values()) {
            values.addAll((Collection<? extends V>)value);
        }
        return values;
    }
    
    @Override
    public Map<K, Collection<V>> asMap() {
        return Map.copyOf((Map<? extends K, ? extends Collection<V>>)this.map);
    }
    
    @Override
    public void forEach(final Consumer<Map.Entry<K, V>> entryConsumer) {
        for (final Map.Entry<K, Collection<V>> entry : this.map.entrySet()) {
            final K key = entry.getKey();
            for (final V value : entry.getValue()) {
                entryConsumer.accept(new SimpleEntry<K, V>(key, value));
            }
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ConcurrentHashMultimap<?, ?> that = (ConcurrentHashMultimap<?, ?>)o;
        return this.map.equals(that.map);
    }
    
    @Override
    public int hashCode() {
        return this.map.hashCode();
    }
    
    @Override
    public String toString() {
        return this.map.toString();
    }
}
