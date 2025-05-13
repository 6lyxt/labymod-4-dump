// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.reference;

import java.util.function.Supplier;

public class NullableDynamicReference<T> extends Reference<T>
{
    public NullableDynamicReference(final Class<T> referenceClass, final Supplier<T> referenceSupplier) {
        super(referenceClass, referenceSupplier);
    }
    
    public T get() {
        return this.referenceSupplier.get();
    }
}
