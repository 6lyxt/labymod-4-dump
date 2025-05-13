// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

public interface Disposable
{
    default void addDisposeListener(final Runnable listener) {
        throw new UnsupportedOperationException();
    }
    
    default boolean isDisposed() {
        return false;
    }
    
    void dispose();
}
