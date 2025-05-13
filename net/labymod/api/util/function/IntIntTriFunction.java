// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.function;

import java.util.Objects;
import java.util.function.Function;

public interface IntIntTriFunction<T, R>
{
    R apply(final int p0, final int p1, final T p2);
    
    default <W> IntIntTriFunction<T, W> andThen(final Function<? super R, ? extends W> after) {
        Objects.requireNonNull(after);
        return (i0, i1, t) -> after.apply(this.apply(i0, i1, t));
    }
}
