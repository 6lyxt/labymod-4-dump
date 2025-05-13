// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.util;

import org.jetbrains.annotations.Nullable;
import java.util.function.IntFunction;

public class GrowableArrayIndex<T> extends ArrayIndex<T>
{
    public GrowableArrayIndex(final int capacity, final IntFunction<T[]> arrayConstructor) {
        super(capacity, arrayConstructor);
    }
    
    @Override
    public void set(final int index, final T value) {
        if (index >= this.size()) {
            this.grow(this.size() * 2);
        }
        super.set(index, value);
    }
    
    @Nullable
    @Override
    public T get(final int index) {
        if (index >= this.size()) {
            this.grow(this.size() * 2);
        }
        return super.get(index);
    }
}
