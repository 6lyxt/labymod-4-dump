// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io.web.result;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import java.util.NoSuchElementException;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractResult<T>
{
    protected T value;
    
    protected AbstractResult(@Nullable final T value) {
        this.value = value;
    }
    
    public boolean isPresent() {
        return this.value != null;
    }
    
    public boolean isEmpty() {
        return !this.isPresent();
    }
    
    @NotNull
    public T get() {
        if (!this.isPresent()) {
            throw new NoSuchElementException("No value present");
        }
        return this.value;
    }
    
    @Nullable
    public T getNullable() {
        return this.value;
    }
    
    @Nullable
    public T getOrDefault(@Nullable final T defaultValue) {
        return this.isPresent() ? this.value : defaultValue;
    }
    
    @Nullable
    public T getOrCompute(@NotNull final Supplier<T> supplier) {
        return this.isPresent() ? this.value : supplier.get();
    }
    
    public void ifPresent(@NotNull final Consumer<T> present) {
        Objects.requireNonNull(present, "Consumer cannot be null");
        if (this.isPresent()) {
            present.accept(this.value);
        }
    }
}
