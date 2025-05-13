// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Contract;
import java.util.function.Supplier;

public final class Lazy<T>
{
    private final Supplier<T> supplier;
    private T value;
    
    private Lazy(final Supplier<T> supplier) {
        this.supplier = supplier;
    }
    
    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public static <C> Lazy<C> of(final Supplier<C> supplier) {
        return new Lazy<C>(supplier);
    }
    
    public T get() {
        if (this.value == null) {
            this.value = this.supplier.get();
        }
        return this.value;
    }
    
    public void reset() {
        this.value = null;
    }
    
    public void make(final Consumer<T> t) {
        t.accept(this.get());
    }
}
