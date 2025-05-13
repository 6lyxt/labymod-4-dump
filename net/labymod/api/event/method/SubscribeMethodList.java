// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.method;

import java.util.function.Predicate;

public interface SubscribeMethodList
{
    void add(final SubscribeMethod p0);
    
    boolean remove(final SubscribeMethod p0);
    
    boolean isEmpty();
    
    void removeIf(final Predicate<SubscribeMethod> p0);
    
    void merge(final SubscribeMethodList p0);
    
    void sort();
    
    void mergeSort(final SubscribeMethodList p0);
    
    SubscribeMethod[] getSubscribeMethods();
}
