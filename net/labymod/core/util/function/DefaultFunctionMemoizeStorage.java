// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.util.function;

import net.labymod.api.util.IntIntTriple;
import net.labymod.api.util.function.IntIntTriFunction;
import net.labymod.api.util.Triple;
import net.labymod.api.util.function.TriFunction;
import net.labymod.api.util.Pair;
import java.util.function.BiFunction;
import net.labymod.core.util.collection.TimestampedValue;
import java.util.function.Function;
import net.labymod.core.util.collection.TimestampedCache;
import javax.inject.Inject;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.util.function.FunctionMemoizeStorage;

@Singleton
@Implements(FunctionMemoizeStorage.class)
public final class DefaultFunctionMemoizeStorage implements FunctionMemoizeStorage
{
    @Inject
    public DefaultFunctionMemoizeStorage() {
    }
    
    private static <T, R> R computeIfAbsent(final TimestampedCache<T, R> cache, final T t, final Function<T, T> transformFunction, final Function<T, R> valueFactory) {
        TimestampedValue<R> value = cache.get(t);
        if (value == null) {
            final T newKey = transformFunction.apply(t);
            final TimestampedValue<R> newValue = new TimestampedValue<R>(valueFactory.apply(newKey));
            cache.put(newKey, newValue);
            value = newValue;
        }
        return value.getValue();
    }
    
    @Override
    public <T, R> Function<T, R> memoize(final Function<T, R> function) {
        return new Function<T, R>(this) {
            private final TimestampedCache<T, R> cache = new TimestampedCache<T, R>(8);
            
            @Override
            public R apply(final T t) {
                return DefaultFunctionMemoizeStorage.computeIfAbsent(this.cache, t, a -> a, (Function<T, R>)function);
            }
        };
    }
    
    @Override
    public <T, U, R> BiFunction<T, U, R> memoize(final BiFunction<T, U, R> function) {
        return new BiFunction<T, U, R>(this) {
            private final TimestampedCache<Pair<T, U>, R> cache = new TimestampedCache<Pair<T, U>, R>(8);
            private final Pair<T, U> sharedPair = Pair.of((T)null, (U)null);
            
            @Override
            public R apply(final T t, final U u) {
                this.sharedPair.set(t, u);
                return DefaultFunctionMemoizeStorage.computeIfAbsent(this.cache, this.sharedPair, Pair::copy, pair -> {
                    final Object val$function = function;
                    return function.apply(pair.getFirst(), pair.getSecond());
                });
            }
        };
    }
    
    @Override
    public <T, U, V, R> TriFunction<T, U, V, R> memoize(final TriFunction<T, U, V, R> function) {
        return new TriFunction<T, U, V, R>(this) {
            private final TimestampedCache<Triple<T, U, V>, R> cache = new TimestampedCache<Triple<T, U, V>, R>(8);
            private final Triple<T, U, V> sharedTriple = Triple.of((T)null, (U)null, (V)null);
            
            @Override
            public R apply(final T t, final U u, final V v) {
                this.sharedTriple.set(t, u, v);
                return DefaultFunctionMemoizeStorage.computeIfAbsent(this.cache, this.sharedTriple, Triple::copy, triple -> {
                    final Object val$function = function;
                    return function.apply(triple.getLeft(), triple.getMiddle(), triple.getRight());
                });
            }
        };
    }
    
    @Override
    public <T, R> IntIntTriFunction<T, R> memoize(final IntIntTriFunction<T, R> function) {
        return new IntIntTriFunction<T, R>(this) {
            private final TimestampedCache<IntIntTriple<T>, R> cache = new TimestampedCache<IntIntTriple<T>, R>(8);
            private final IntIntTriple<T> sharedTriple = new IntIntTriple<T>(0, 0, null);
            
            @Override
            public R apply(final int i0, final int i1, final T t) {
                this.sharedTriple.set(i0, i1, t);
                return DefaultFunctionMemoizeStorage.computeIfAbsent(this.cache, this.sharedTriple, IntIntTriple::copy, triple -> {
                    final Object val$function = function;
                    return function.apply(triple.getLeft(), triple.getMiddle(), triple.getRight());
                });
            }
        };
    }
}
