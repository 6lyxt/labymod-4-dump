// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.function;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class Consumers
{
    public static <T> void accept(final Consumer<T> consumer, final T value) {
        if (consumer == null) {
            return;
        }
        consumer.accept(value);
    }
    
    public static <T, U> void accept(final BiConsumer<T, U> consumer, final T first, final U second) {
        if (consumer == null) {
            return;
        }
        consumer.accept(first, second);
    }
}
