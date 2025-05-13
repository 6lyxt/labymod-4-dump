// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.watcher.list;

import java.util.Collection;
import java.util.ArrayList;

public class WatchableArrayList<T> extends ArrayList<T>
{
    private final WatchableList<T> watchableList;
    
    public WatchableArrayList(final WatchableList<T> watchableList) {
        this.watchableList = watchableList;
    }
    
    @Override
    public boolean add(final T t) {
        this.watchableList.onAdd(t);
        return super.add(t);
    }
    
    @Override
    public void add(final int index, final T element) {
        this.watchableList.onAdd(index, element);
        super.add(index, element);
    }
    
    @Override
    public boolean addAll(final Collection<? extends T> c) {
        this.watchableList.onAddAll((Collection<?>)c);
        return super.addAll(c);
    }
    
    @Override
    public T remove(final int index) {
        final T t = super.remove(index);
        this.watchableList.onRemove(t);
        return t;
    }
    
    @Override
    public boolean remove(final Object o) {
        this.watchableList.onRemove((T)o);
        return super.remove(o);
    }
    
    @Override
    public void clear() {
        this.watchableList.onClear();
        super.clear();
    }
    
    public void addUnwatched(final T t) {
        super.add(t);
    }
}
