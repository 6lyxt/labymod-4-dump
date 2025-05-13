// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.pipeline.state;

import java.util.function.Supplier;

public class StateStorageApplier<T>
{
    private final StateStorage<T> stateStorage;
    private final Supplier<T> storageType;
    
    public StateStorageApplier(final StateStorage<T> stateStorage, final Supplier<T> storageType) {
        this.stateStorage = stateStorage;
        this.storageType = storageType;
    }
    
    public void store() {
        this.stateStorage.store(this.storageType.get());
    }
    
    public void restore() {
        this.stateStorage.restore();
    }
    
    public StateStorageApplier<T> copy() {
        return new StateStorageApplier<T>(this.stateStorage.copy(), this.storageType);
    }
    
    @Override
    public String toString() {
        return this.stateStorage.toString();
    }
}
