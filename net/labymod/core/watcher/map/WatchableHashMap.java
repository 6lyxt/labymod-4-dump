// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.watcher.map;

import org.jetbrains.annotations.ApiStatus;
import java.util.HashMap;

@ApiStatus.Experimental
public class WatchableHashMap<K, V> extends HashMap<K, V>
{
    private final WatchableMap<K, V> watchableMap;
    
    public WatchableHashMap() {
        this((WatchableMap)new NoOperationWatchableMap());
    }
    
    public WatchableHashMap(WatchableMap<K, V> watchableMap) {
        if (watchableMap == null) {
            watchableMap = new NoOperationWatchableMap<K, V>();
        }
        this.watchableMap = watchableMap;
    }
    
    @Override
    public V put(final K key, final V value) {
        this.watchableMap.onPut(key, value);
        return super.put(key, value);
    }
    
    @Override
    public V remove(final Object key) {
        this.watchableMap.onRemove((K)key);
        return super.remove(key);
    }
    
    @Override
    public boolean remove(final Object key, final Object value) {
        this.watchableMap.onRemove((K)key, (V)value);
        return super.remove(key, value);
    }
    
    @Override
    public void clear() {
        this.watchableMap.onClear();
        super.clear();
    }
}
