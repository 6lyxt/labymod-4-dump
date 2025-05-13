// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.function;

import java.util.function.BiFunction;
import java.util.function.Function;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface FunctionMemoizeStorage
{
     <T, R> Function<T, R> memoize(final Function<T, R> p0);
    
     <T, U, R> BiFunction<T, U, R> memoize(final BiFunction<T, U, R> p0);
    
     <T, U, V, R> TriFunction<T, U, V, R> memoize(final TriFunction<T, U, V, R> p0);
    
     <T, R> IntIntTriFunction<T, R> memoize(final IntIntTriFunction<T, R> p0);
}
