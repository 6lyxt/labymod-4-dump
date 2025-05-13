// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.function;

public interface ThrowableBiFunction<T, U, R, E extends Throwable>
{
    R apply(final T p0, final U p1) throws E, Throwable;
}
