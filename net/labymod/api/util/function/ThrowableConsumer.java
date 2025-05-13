// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.function;

@FunctionalInterface
public interface ThrowableConsumer<T, E extends Throwable>
{
    void accept(final T p0) throws E, Throwable;
}
