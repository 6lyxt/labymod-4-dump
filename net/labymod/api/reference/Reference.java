// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.reference;

import java.util.function.Supplier;
import javax.inject.Provider;

public abstract class Reference<T> implements Provider<T>
{
    protected final Class<T> referenceClass;
    protected final Supplier<T> referenceSupplier;
    
    public Reference(final Class<T> referenceClass, final Supplier<T> referenceSupplier) {
        this.referenceClass = referenceClass;
        this.referenceSupplier = referenceSupplier;
    }
    
    public Class<T> getReferenceClass() {
        return this.referenceClass;
    }
}
