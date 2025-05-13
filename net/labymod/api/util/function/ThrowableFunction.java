// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.function;

@FunctionalInterface
public interface ThrowableFunction<T, R, E extends Throwable>
{
    R apply(final T p0) throws E, Throwable;
}
