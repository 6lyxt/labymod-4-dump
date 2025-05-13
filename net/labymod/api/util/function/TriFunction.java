// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.function;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface TriFunction<T, U, V, R>
{
    R apply(final T p0, final U p1, final V p2);
    
    default <W> TriFunction<T, U, V, W> andThen(final Function<? super R, ? extends W> after) {
        Objects.requireNonNull(after);
        return (t, u, v) -> after.apply(this.apply(t, u, v));
    }
}
