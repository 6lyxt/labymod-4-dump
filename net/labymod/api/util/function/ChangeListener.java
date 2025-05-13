// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.function;

@FunctionalInterface
public interface ChangeListener<T, V>
{
    void changed(final T p0, final V p1, final V p2);
}
