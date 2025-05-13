// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.event.method;

import java.util.Comparator;
import java.util.Collection;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.ArrayList;
import net.labymod.api.event.method.SubscribeMethod;
import java.util.List;
import net.labymod.api.event.method.SubscribeMethodList;

public class DefaultSubscribeMethodList implements SubscribeMethodList
{
    private final List<SubscribeMethod> subscribeMethods;
    private SubscribeMethod[] bakedSubscribeMethods;
    
    public DefaultSubscribeMethodList() {
        this.subscribeMethods = new ArrayList<SubscribeMethod>();
        this.bakedSubscribeMethods = new SubscribeMethod[0];
    }
    
    @Override
    public void add(final SubscribeMethod method) {
        this.subscribeMethods.add(method);
        this.buildBakedArray();
    }
    
    @Override
    public boolean remove(final SubscribeMethod method) {
        final boolean remove = this.subscribeMethods.remove(method);
        if (remove) {
            this.buildBakedArray();
        }
        return remove;
    }
    
    @Override
    public boolean isEmpty() {
        return this.bakedSubscribeMethods.length == 0;
    }
    
    @Override
    public void removeIf(final Predicate<SubscribeMethod> filter) {
        if (this.subscribeMethods.removeIf(filter)) {
            this.buildBakedArray();
        }
    }
    
    @Override
    public void merge(final SubscribeMethodList other) {
        this.subscribeMethods.addAll(Arrays.asList(other.getSubscribeMethods()));
        this.buildBakedArray();
    }
    
    @Override
    public void sort() {
        this.sortMethods();
        this.buildBakedArray();
    }
    
    @Override
    public void mergeSort(final SubscribeMethodList other) {
        this.subscribeMethods.addAll(Arrays.asList(other.getSubscribeMethods()));
        this.sortMethods();
        this.buildBakedArray();
    }
    
    private void sortMethods() {
        this.subscribeMethods.sort(Comparator.comparingInt(SubscribeMethod::getPriority));
    }
    
    @Override
    public SubscribeMethod[] getSubscribeMethods() {
        return this.bakedSubscribeMethods;
    }
    
    private void buildBakedArray() {
        this.bakedSubscribeMethods = this.subscribeMethods.toArray(new SubscribeMethod[0]);
    }
}
