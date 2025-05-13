// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.watcher.map;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public final class NoOperationWatchableMap<K, V> implements WatchableMap<K, V>
{
    @Override
    public void onPut(final K key, final V value) {
    }
    
    @Override
    public void onClear() {
    }
    
    @Override
    public void onRemove(final K key) {
    }
    
    @Override
    public void onRemove(final K key, final V value) {
    }
}
