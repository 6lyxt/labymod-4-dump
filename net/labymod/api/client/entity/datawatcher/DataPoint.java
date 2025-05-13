// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.datawatcher;

public class DataPoint<T>
{
    private T value;
    
    public DataPoint(final T value) {
        this.value = value;
    }
    
    public T get() {
        return this.value;
    }
    
    public void set(final T value) {
        this.value = value;
    }
    
    public boolean isPresent() {
        return this.value != null;
    }
}
