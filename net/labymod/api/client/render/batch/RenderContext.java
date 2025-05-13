// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.batch;

import net.labymod.api.client.render.vertex.BufferBuilder;

public interface RenderContext<T extends RenderContext<T>>
{
    void initialize();
    
    BufferBuilder getBuilder();
    
    float zOffset();
    
    void zOffset(final float p0);
    
    default RenderContext<T> uploadToBuffer() {
        this.getBuilder().uploadToBuffer();
        return this;
    }
}
