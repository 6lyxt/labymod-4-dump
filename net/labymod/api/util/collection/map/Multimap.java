// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.collection.map;

import java.util.function.Consumer;
import java.util.Map;
import java.util.Collection;

public interface Multimap<K, V>
{
    int size();
    
    boolean isEmpty();
    
    void put(final K p0, final V p1);
    
    boolean remove(final K p0);
    
    boolean remove(final K p0, final V p1);
    
    void clear();
    
    Collection<V> get(final K p0);
    
    Collection<V> values();
    
    Map<K, Collection<V>> asMap();
    
    void forEach(final Consumer<Map.Entry<K, V>> p0);
}
