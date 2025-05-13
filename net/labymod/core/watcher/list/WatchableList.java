// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.watcher.list;

import net.labymod.core.watcher.collection.WatchableCollection;

public interface WatchableList<T> extends WatchableCollection<T>
{
    void onAdd(final int p0, final T p1);
}
