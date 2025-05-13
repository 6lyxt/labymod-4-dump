// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.datawatcher;

import java.util.function.Function;

public interface DataWatcher
{
     <T> void set(final String p0, final T p1);
    
    void remove(final String p0);
    
     <T> DataPoint<T> get(final String p0);
    
     <T> DataPoint<T> get(final String p0, final T p1);
    
     <T> DataPoint<T> computeIfAbsent(final String p0, final Function<String, ? extends T> p1);
    
    boolean has(final String p0);
}
