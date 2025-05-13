// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.function;

import java.io.IOException;

@FunctionalInterface
public interface IoSupplier<T>
{
    T get() throws IOException;
}
