// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.util;

import java.util.function.Consumer;
import java.util.Arrays;
import java.util.function.Supplier;
import org.jetbrains.annotations.Nullable;
import java.util.Locale;
import java.util.function.IntFunction;

public class ArrayIndex<T>
{
    private final IntFunction<T[]> arrayConstructor;
    private T[] elementData;
    
    public ArrayIndex(final int capacity, final IntFunction<T[]> arrayConstructor) {
        this.elementData = arrayConstructor.apply(capacity);
        this.arrayConstructor = arrayConstructor;
    }
    
    public void set(final int index, final T value) {
        if (index < 0 || index >= this.elementData.length) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "Index %s out of bounds for length %s", index, this.elementData.length));
        }
        this.elementData[index] = value;
    }
    
    @Nullable
    public T get(final int index) {
        return (index < 0 || index >= this.elementData.length) ? null : this.elementData[index];
    }
    
    public void grow(final int capacity) {
        final int length = this.elementData.length;
        if (capacity >= length) {
            final T[] newValue = this.arrayConstructor.apply(capacity);
            System.arraycopy(this.elementData, 0, newValue, 0, length);
            this.elementData = newValue;
        }
    }
    
    public void fill(final Supplier<T> factory) {
        for (int i = 0; i < this.elementData.length; ++i) {
            final T element = factory.get();
            this.elementData[i] = element;
        }
    }
    
    public void fill(final IntFunction<T> factory) {
        for (int i = 0; i < this.elementData.length; ++i) {
            this.elementData[i] = factory.apply(i);
        }
    }
    
    public void clear() {
        Arrays.fill(this.elementData, null);
    }
    
    public int size() {
        return this.elementData.length;
    }
    
    public void forEach(final Consumer<T> consumer) {
        if (consumer == null) {
            return;
        }
        for (final T t : this.elementData) {
            consumer.accept(t);
        }
    }
}
