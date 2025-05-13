// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.function;

@FunctionalInterface
public interface ThrowableRunnable<E extends Throwable>
{
    void run() throws E, Throwable;
}
