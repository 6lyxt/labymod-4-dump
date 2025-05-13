// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.watcher.set;

import org.jetbrains.annotations.NotNull;
import java.util.Collection;
import java.util.HashSet;

public class WatchableHashSet<T> extends HashSet<T>
{
    private final WatchableSet<T> watchableSet;
    
    public WatchableHashSet(final WatchableSet<T> watchableSet) {
        this.watchableSet = watchableSet;
    }
    
    @Override
    public boolean add(final T t) {
        this.watchableSet.onAdd(t);
        return super.add(t);
    }
    
    @Override
    public boolean addAll(@NotNull final Collection<? extends T> c) {
        this.watchableSet.onAddAll((Collection<?>)c);
        return super.addAll(c);
    }
    
    @Override
    public boolean remove(final Object o) {
        this.watchableSet.onRemove((T)o);
        return super.remove(o);
    }
    
    @Override
    public void clear() {
        this.watchableSet.onClear();
        super.clear();
    }
}
