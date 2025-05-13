// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.watcher.collection;

import java.util.Iterator;
import java.util.Collection;

public interface WatchableCollection<T>
{
    void onAdd(final T p0);
    
    void onRemove(final T p0);
    
    void onClear();
    
    default void onAddAll(final Collection<? extends T> c) {
        for (final T t : c) {
            this.onAdd(t);
        }
    }
}
