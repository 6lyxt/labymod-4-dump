// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.blaze3d.program;

import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;

public interface Blaze3DRenderType
{
    void apply();
    
    void clear();
    
    void draw(final BufferConsumer p0, final int p1, final int p2, final int p3);
}
