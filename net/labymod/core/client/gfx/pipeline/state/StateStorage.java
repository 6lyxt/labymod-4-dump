// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.pipeline.state;

public interface StateStorage<T>
{
    void store(final T p0);
    
    void restore();
    
    StateStorage<T> copy();
}
