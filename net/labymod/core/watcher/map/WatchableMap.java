// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.watcher.map;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public interface WatchableMap<K, V>
{
    void onPut(final K p0, final V p1);
    
    void onClear();
    
    void onRemove(final K p0);
    
    void onRemove(final K p0, final V p1);
}
