// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.function;

public interface ThrowableSupplier<T, E extends Throwable>
{
    T get() throws E, Throwable;
}
