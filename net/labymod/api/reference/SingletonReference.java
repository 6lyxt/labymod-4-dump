// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.reference;

import java.util.function.Supplier;

public class SingletonReference<T> extends NullableSingletonReference<T>
{
    public SingletonReference(final Class<T> referenceClass, final Supplier<T> referenceSupplier) {
        super(referenceClass, referenceSupplier);
    }
    
    @Override
    public T get() {
        final T value = super.get();
        if (value == null) {
            throw new NullPointerException("Reference " + String.valueOf(this.referenceClass) + " cannot be null!");
        }
        return value;
    }
}
