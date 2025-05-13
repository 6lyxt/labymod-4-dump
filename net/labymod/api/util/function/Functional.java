// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.function;

import net.labymod.api.util.CollectionHelper;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class Functional
{
    public static <T> T of(final Supplier<T> supplier) {
        return supplier.get();
    }
    
    public static <T> T of(final T value, final Consumer<T> consumer) {
        consumer.accept(value);
        return value;
    }
    
    public static <T> T[] toArray(final Collection<T> value, final Class<T> type, final Consumer<Collection<T>> consumer) {
        consumer.accept(value);
        return CollectionHelper.mapArray(value.toArray(), type, f -> f);
    }
}
